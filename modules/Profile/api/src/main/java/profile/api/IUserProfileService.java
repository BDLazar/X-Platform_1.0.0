package profile.api;

import java.util.Date;

/**
 * Created by Onis on 29/06/14.
 */
public interface IUserProfileService {

    public UserProfile getUserProfile(String profileId);
    public String createUserProfile(UserProfile userProfile);
    public boolean deleteUserProfile(String profileId);
    public boolean modifyUserProfile(String profileId, UserDetails userDetails);

}
