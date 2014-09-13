package user.api.data;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="BASIC_USER_PROFILE")
@PrimaryKeyJoinColumn(name="id")
public class BasicUserProfile extends UserProfile {

    //region Properties
    private String firstName;
    private String middleName;
    private String lastName;
    //endregion

    //region Constructors
    public BasicUserProfile() {
        super(UserProfileType.BASIC);
    }

    public BasicUserProfile(String firstName, String middleName, String lastName) {
        super(UserProfileType.BASIC);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
    //endregion

    //region Getters & Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

        //try cast to Basic User Profile and apply the updates
        if(userProfileUpdates.getUserProfileType() == UserProfileType.BASIC)
        {
            //update UserProfile superclass
            updatesAmount = updatesAmount + super.updateFrom(userProfileUpdates);

            BasicUserProfile basicUserProfileUpdates = (BasicUserProfile) userProfileUpdates;

            String newFirstName = basicUserProfileUpdates.getFirstName();
            String newMiddleName = basicUserProfileUpdates.getMiddleName();
            String newLastName = basicUserProfileUpdates.getLastName();

            if(newFirstName != null){this.firstName = newFirstName; updatesAmount++;}
            if(newMiddleName != null){this.middleName = newMiddleName; updatesAmount++;}
            if(newLastName != null){this.lastName = newLastName; updatesAmount++;}

        }

        return updatesAmount;
    }
    //endregion
}
