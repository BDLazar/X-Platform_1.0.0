package user.api;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.*;

@Entity
@Table(name="USER_PROFILE")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "userProfileType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BasicUserProfile.class, name = "BASIC"),
        @JsonSubTypes.Type(value = StandardUserProfile.class, name = "STANDARD") })
public abstract class UserProfile {

    //region Properties
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserProfileType userProfileType;
    //endregion

    //region Constructors
    public UserProfile() {
    }

    public UserProfile(UserProfileType userProfileType) {
        this.userProfileType = userProfileType;
    }
    //endregion

    //region Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfileType getUserProfileType() {
        return userProfileType;
    }

    public void setUserProfileTypeserProfileType(UserProfileType type) {
        this.userProfileType = userProfileType;
    }
    //endregion

    //region Parsers
    public ObjectNode toJson(){

        ObjectMapper jsonMapper = new ObjectMapper();

        try {

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonString = ow.writeValueAsString(this);
            JsonNode jsonNode = jsonMapper.readTree(jsonString);
            return (ObjectNode)jsonNode;

        } catch (Exception ex){

            return null;
        }

    }
    //endregion

    //region Business Methods
    public int updateFrom(UserProfile userProfileUpdates)
    {
        //do not update id and userProfileType, they should not be changed once initialised

        //return the amount of updates that were applied
        return 0;
    }
    //endregion
}
