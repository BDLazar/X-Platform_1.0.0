package profile.impl;

import profile.api.IUserProfileService;
import profile.api.UserDetails;
import profile.api.UserProfile;

import java.util.Date;

/**
 * Created by Onis on 29/06/14.
 */
public class UserProfileService implements IUserProfileService {

    private  UserProfileDAO userProfileDAO;

    public UserProfileService(UserProfileDAO userProfileDAO) {

        this.userProfileDAO = userProfileDAO;
    }

    @Override
    public UserProfile getUserProfile(String profileId) {
       return userProfileDAO.getUserProfile(profileId);
    }

    @Override
    public String createUserProfile(UserProfile userProfile) {

        if(userProfile.getId() == null || userProfile.getId().equals(""))
        {
            userProfile.setId(generateNewProfileId());
            if(userProfileDAO.saveUserProfile(userProfile))
            {
                return userProfile.getId();
            }
        }

        return null;
    }

    @Override
    public boolean deleteUserProfile(String profileId) {
        return false;
    }

    @Override
    public boolean modifyUserProfile(String profileId, UserDetails userDetails) {
        return false;
    }


    private String generateNewProfileId()
    {
        long nextID = userProfileDAO.countProfiles() + 1;
        return "UP_ID_"+ nextID;
    }
}
