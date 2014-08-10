package user.api;

public interface IUserService
{
    //region User Account
    public UserAccount createUserAccount(UserAccount newUserAccount);
    public UserAccount updateUserAccount(UserAccount userAccountUpdates);
    public UserAccount getUserAccount(Long userAccountId, boolean withUserProfiles);
    public boolean deleteUserAccount(Long userAccountId);
    //endregion

    //region User Profile
    public UserProfile createUserProfile(Long userAccountId, UserProfile newUserProfile);
    public UserProfile updateUserProfile(UserProfile userProfileUpdates);
    public UserProfile getUserProfile(Long profileId);
    public boolean deleteUserProfile(Long userAccountId, Long userProfileId);
    //endregion
}
