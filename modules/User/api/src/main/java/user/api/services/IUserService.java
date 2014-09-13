package user.api.services;

import user.api.data.UserAccount;
import user.api.exceptions.InvalidUserAccountException;
import user.api.exceptions.UserAccountFoundException;
import user.api.exceptions.UserAccountNotFoundException;

public interface IUserService
{

    public Long createUserAccount(UserAccount newUserAccount) throws UserAccountFoundException, InvalidUserAccountException;
    public UserAccount getUserAccount(Long userAccountId, boolean withUserProfiles) throws UserAccountNotFoundException, InvalidUserAccountException;


    /*public Long updateUserAccount(UserAccount userAccountUpdates) throws UserServiceException;
    public boolean deleteUserAccount(Long userAccountId);
    //endregion

    //region User Profile
    public Long createUserProfile(Long userAccountId, UserProfile newUserProfile);
    public Long updateUserProfile(UserProfile userProfileUpdates);
    public UserProfile getUserProfile(Long profileId);
    public boolean deleteUserProfile(Long userAccountId, Long userProfileId);
    //endregion
    */
}
