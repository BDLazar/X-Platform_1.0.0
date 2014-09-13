package user.api.data;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
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

    private String userName;

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

    public void setUserProfileType(UserProfileType userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //endregion

    //region Parsers
    public abstract ObjectNode toJson();
    //endregion

    //region Business Methods
    public int updateFrom(UserProfile userProfileUpdates)
    {
        //do not update id and userProfileType, they should not be changed once initialised

        int updatesAmount = 0;

        String newUserName = userProfileUpdates.getUserName();

        if (newUserName != null) {this.userName = newUserName; updatesAmount++;}

        //return the amount of updates that were applied
        return updatesAmount;
    }
    //endregion
}
