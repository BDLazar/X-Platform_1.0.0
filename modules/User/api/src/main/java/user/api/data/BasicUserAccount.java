package user.api.data;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="BASIC_USER_ACCOUNT")
@PrimaryKeyJoinColumn(name="id")
public class BasicUserAccount extends UserAccount {

    //region Properties
    private String testProperty;
    //endregion

    //region Constructors
    public BasicUserAccount() {
        super(UserAccountType.BASIC);
    }

    public BasicUserAccount(String testProperty) {
        super(UserAccountType.BASIC);
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

              //if anything goes wrong with serialisation of user profiles map just serialise an empty object
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
        jsonObj.put("email", super.getEmail());
        jsonObj.put("password", super.getPassword());

        return jsonObj;

    }
    //endregion

    //region Business Methods
    @Override
    public int updateFrom(UserAccount userAccountUpdates)
    {
        int updatesAmount = 0;

        //try cast to Basic User Profile and apply the updates
        if(userAccountUpdates.getUserAccountType() == UserAccountType.BASIC)
        {
            //Update superclass UserAccount class
            updatesAmount = updatesAmount + super.updateFrom(userAccountUpdates);

            BasicUserAccount basicUserAccountUpdates = (BasicUserAccount) userAccountUpdates;

            String newTestProperty = basicUserAccountUpdates.getTestProperty();

            if (newTestProperty != null) {this.testProperty = newTestProperty; updatesAmount++;}

        }

        return updatesAmount;
    }
    //endregion
}

