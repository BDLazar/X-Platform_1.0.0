package user.api;

public interface IUserAccountService
{
    public boolean createUserAccount(UserAccount newUserAccount);
    public boolean updateUserAccount(UserAccount userAccountUpdates);
    public UserAccount getUserAccount(Long userAccountId);
    public boolean deleteUserAccount(Long userAccountId);
}
