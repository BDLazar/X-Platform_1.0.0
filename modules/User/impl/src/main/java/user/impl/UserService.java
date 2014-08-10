package user.impl;

import user.api.*;


public class UserService implements IUserService
{
    private UserDAO userAccountDAO;

    public UserService(UserDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    //region User Account
    @Override
    public UserAccount createUserAccount(UserAccount newUserAccount) {

        /*
         To create a user account :
         1) The user account id must null or negative because the id gets automatically generated
         2) The user account type must not be null because we need to know how to treat the user account specifically
        */
        if((newUserAccount.getId() == null || newUserAccount.getId() < 0) && newUserAccount.getUserAccountType() != null)
        {
            //insert the new userAccount into the database and return the object stored in database (may return null if the object could not be inserted in database)
            return userAccountDAO.insertUserAccount(newUserAccount);
        }

        //return null if the conditions of creating a new user account were not met
        return null;
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccountUpdates) {

       /*
         To updateUserAccount a user account :
         1) The id must be positive because we are going to look for an existing user account in the database using the id
         2) The user account type must not be null because we need to know how to treat the user account specifically
        */
        if(userAccountUpdates.getId() != null && userAccountUpdates.getId() > 0 && userAccountUpdates.getUserAccountType() != null)
        {
            return userAccountDAO.updateUserAccount(userAccountUpdates);
        }

        //return null if the conditions of updating an existing user account were not met
        return null;
    }

    @Override
    public UserAccount getUserAccount(Long userAccountId, boolean loadUserProfiles) {

        return userAccountDAO.getUserAccount(userAccountId, loadUserProfiles);

    }

    @Override
    public boolean deleteUserAccount(Long id) {

        return userAccountDAO.deleteUserAccount(id);
    }
    //endregion

    //region User Profile
    @Override
    public UserProfile createUserProfile(Long userAccountId, UserProfile newUserProfile) {

        return userAccountDAO.insertUserProfile(userAccountId,newUserProfile);
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfileUpdates) {
        return userAccountDAO.updateUserProfile(userProfileUpdates);
    }

    @Override
    public UserProfile getUserProfile(Long profileId) {
        return userAccountDAO.getUserProfile(profileId);
    }

    @Override
    public boolean deleteUserProfile(Long userAccountId, Long userProfileId) {
       return userAccountDAO.deleteUserProfile(userAccountId,userProfileId);
    }
    //endregion
}
