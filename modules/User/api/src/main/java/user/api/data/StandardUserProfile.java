package user.api.data;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="STANDARD_USER_PROFILE")
@PrimaryKeyJoinColumn(name="id")
public class StandardUserProfile extends UserProfile{

    //region Properties
    String testProperty;
    //endregion

    //region Constructors
    public StandardUserProfile() {
        super(UserProfileType.STANDARD);
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

        ObjectMapper jsonMapper = new ObjectMapper();

        try {

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonString = objectWriter.writeValueAsString(this);
            JsonNode jsonObj = jsonMapper.readTree(jsonString);

            return (ObjectNode)jsonObj;

        } catch (Exception ex){

            return null;
        }

    }
    //endregion

    //region Business Methods
    @Override
    public int updateFrom(UserProfile userProfileUpdates)
    {
        int updatesAmount = 0;

        //try cast to Standard User Profile and apply the updates
        if(userProfileUpdates.getUserProfileType() == UserProfileType.STANDARD)
        {
            //Update superclass UserProfile class
            updatesAmount = updatesAmount + super.updateFrom(userProfileUpdates);

            StandardUserProfile standardUserProfile = (StandardUserProfile) userProfileUpdates;

            String newTestProperty = standardUserProfile.getTestProperty();

            if(newTestProperty != null){this.testProperty = newTestProperty; updatesAmount++;}

        }

        return updatesAmount;
    }
    //endregion
}
