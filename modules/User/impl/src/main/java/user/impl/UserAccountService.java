package user.impl;

import user.api.*;


public class UserAccountService implements IUserAccountService
{
    private UserAccountDAO userAccountDAO;

    public UserAccountService(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public boolean createUserAccount(UserAccount newUserAccount) {

        // if the user account already has an id or we do not know what type of userAccount we should create return false
        if(newUserAccount.getId() != null || newUserAccount.getUserAccountType() == null)
        {
            return false;
        }

        //insert the new userAccount into the database
        return userAccountDAO.insert(newUserAccount);
    }

    @Override
    public UserAccount getUserAccount(Long userAccountId) {

        boolean loadUserProfiles = true;
        return userAccountDAO.get(userAccountId,loadUserProfiles);

    }

    @Override
    public boolean updateUserAccount(UserAccount userAccountUpdates) {

        // if the user account does not have an id or we do not know what type of userAccount we should create return false
        if(userAccountUpdates.getId() == null || userAccountUpdates.getUserAccountType() == null)
        {
            return false;
        }

        return userAccountDAO.update(userAccountUpdates);
    }

    @Override
    public boolean deleteUserAccount(Long id) {
        return false;
    }
}
