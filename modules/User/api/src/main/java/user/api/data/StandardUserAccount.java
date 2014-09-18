package user.api.data;

import org.codehaus.jackson.map.ObjectMapper;
        import org.codehaus.jackson.map.ObjectWriter;
        import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.Entity;
        import javax.persistence.PrimaryKeyJoinColumn;
        import javax.persistence.Table;

@Entity
@Table(name="STANDARD_USER_ACCOUNT")
@PrimaryKeyJoinColumn(name="id")
public class StandardUserAccount extends UserAccount{

    //region Properties
    String testProperty;
    //endregion

    //region Constructors
    public StandardUserAccount() {
        super(UserAccountType.STANDARD);
    }

    public StandardUserAccount(String testProperty) {
        super(UserAccountType.STANDARD);
        this.testProperty = testProperty;
    }
    //endregion

    //region Getters & Setters
    public String getTestProperty() {
        return testProperty;
    }

    public void setTestProperty(String testProperty) {
        this.testProperty = testProperty;
    }
    //endregion

    //region Parsers
    @Override
    public ObjectNode toJson(){

        /*we do manual serialisation because some properties have been lazy loaded
          so we need to ignore them to avoid the JPA lazy loading initialisation exception*/

        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonObj = jsonMapper.createObjectNode();

        jsonObj.put("id", super.getId());
        if(this.getUserAccountType() == null)
        {
            jsonObj.put("userAccountType",jsonObj.nullNode());
        }
        else
        {
            jsonObj.put("userAccountType",this.getUserAccountType().name());
        }

        //serialise the user profiles if they were loaded only
        if(super.isWithUserProfiles())
        {
            ObjectNode userProfilesAsJson = null;
            try {

                ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String jsonString = objectWriter.writeValueAsString(this.getUserProfiles());
                userProfilesAsJson = (ObjectNode)jsonMapper.readTree(jsonString);

            } catch (Exception ex){

                userProfilesAsJson = jsonMapper.createObjectNode();

            }

            jsonObj.put("userProfiles",userProfilesAsJson);
        }
        //serialise user profiles map as an empty object if the profiles were not loaded
        else
        {

            jsonObj.put("userProfiles",jsonMapper.createObjectNode());

        }

        jsonObj.put("withUserProfiles", super.isWithUserProfiles());

        jsonObj.put("testProperty", this.testProperty);

        return jsonObj;

    }
    //endregion

    //region Business Methods
    @Override
    public int updateFrom(UserAccount userAccountUpdates)
    {
        int updatesAmount = 0;

        //try cast to Standard User Profile and apply the updates
        if(userAccountUpdates.getUserAccountType() == UserAccountType.STANDARD)
        {
            //Update superclass UserAccount class
            super.updateFrom(userAccountUpdates);

            StandardUserAccount standardUserAccount = (StandardUserAccount) userAccountUpdates;

            String newTestProperty = standardUserAccount.getTestProperty();

            if (newTestProperty != null) {this.testProperty = newTestProperty; updatesAmount++;}

        }

        return updatesAmount;
    }
    //endregion
}
