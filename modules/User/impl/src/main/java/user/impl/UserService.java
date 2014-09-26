package user.impl;

import user.api.data.UserAccount;
import user.api.data.UserProfile;
import user.api.data.UserSession;
import user.api.exceptions.InvalidUserAccountException;
import user.api.exceptions.InvalidUserCredentialsException;
import user.api.exceptions.UserAccountFoundException;
import user.api.exceptions.UserAccountNotFoundException;
import user.api.services.IUserService;


public class UserService implements IUserService
{
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //region User Account
    @Override
    public Long createUserAccount(UserAccount newUserAccount) throws UserAccountFoundException, InvalidUserAccountException {

        //region Validate New User Account
        String exceptionMessage = "Invalid New User Account: ";

        if(newUserAccount == null)
        {
            exceptionMessage = exceptionMessage + "User Account must not be null!";
            throw new InvalidUserAccountException(exceptionMessage);
        }

        if(newUserAccount.getId() != null)
        {
            exceptionMessage = exceptionMessage + "User Account ID must be null";
        }

        if(newUserAccount.getEmail() == null || newUserAccount.getEmail().equals(""))
        {
            exceptionMessage = exceptionMessage + ", Email must not be null or empty";
        }

        if(newUserAccount.getUserAccountType() == null)
        {
            exceptionMessage = exceptionMessage + ", Type must not be null";
        }

        if(newUserAccount.isWithUserProfiles() && (newUserAccount.getUserProfiles() == null || newUserAccount.getUserProfiles().size() == 0))
        {
            exceptionMessage = exceptionMessage + ", user profiles flag must be false";
        }
        else if(!newUserAccount.isWithUserProfiles() && (newUserAccount.getUserProfiles() != null && newUserAccount.getUserProfiles().size() > 0))
        {
            exceptionMessage = exceptionMessage + ", user profiles flag must be true";
        }

        if(newUserAccount.isWithUserProfiles())
        {
            for(Long userProfileKey : newUserAccount.getUserProfiles().keySet())
            {
                UserProfile newUserProfile = newUserAccount.getUserProfiles().get(userProfileKey);
                //check the profile ids
                if(newUserProfile.getId() != null)
                {
                    exceptionMessage = exceptionMessage + " ,user profile with key " + userProfileKey + " ID must be null";
                }
                //check the profile types
                if(newUserProfile.getUserProfileType() == null)
                {
                    exceptionMessage = exceptionMessage + " ,user profile type with key " + userProfileKey + " must not be null";
                }
            }
        }

        if(!exceptionMessage.equals("Invalid New User Account: "))
        {
            throw new InvalidUserAccountException(exceptionMessage);
        }
        //endregion

        //region Insert New User Account into Database
         return userDAO.insertUserAccount(newUserAccount);
        //endregion
    }

    @Override
    public UserAccount getUserAccount(Long userAccountId, boolean withUserProfiles) throws UserAccountNotFoundException, InvalidUserAccountException {

        //region Validate parameters
        if(userAccountId == null)
        {
            throw new InvalidUserAccountException("User Account Id must not be null ");
        }
        //endregion

        //region Retrieve User Account from Database
         return userDAO.getUserAccount(userAccountId, withUserProfiles);
        //endregion
    }

    @Override
    public UserAccount getUserAccount(String email, boolean withUserProfiles) throws UserAccountNotFoundException, InvalidUserAccountException {
        //region Validate parameters
        if(email == null)
        {
            throw new InvalidUserAccountException("User Account email must not be null ");
        }
        //endregion

        //region Retrieve User Account from Database
        return userDAO.getUserAccount(email, withUserProfiles);
        //endregion
    }

/*
    @Override
    public Long updateUserAccount(UserAccount userAccountUpdates) throws UserServiceException {


        //do security checks
        WarnErrReport securityReport = userSecurity.validateUpdateUserAccount(userAccountUpdates);
        if(securityReport.getGrade() == ReportGrade.FAIL)
        {
            throw new UserServiceException("User Service security check failed, see report for details", securityReport);
        }

        //do database transaction
        WarnErrReport daoReport = userDAO.updateUserAccount(userAccountUpdates);
        if(daoReport.getGrade() == ReportGrade.FAIL)
        {
            throw new UserServiceException("User Database transaction failed, see report for details", daoReport);
        }

         //return the user account we just updated
         return userDAO.getUserAccount(userAccountUpdates.getId(),userAccountUpdates.isWithUserProfiles());

    }



    @Override
    public boolean deleteUserAccount(Long id) {

        return userDAO.deleteUserAccount(id);
    }
    //endregion

    //region User Profile
    @Override
    public UserProfile createUserProfile(Long userAccountId, UserProfile newUserProfile) {

        if(userDAO.insertUserProfile(userAccountId,newUserProfile))
        {
            return userDAO.getUserProfile(new)
        }
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfileUpdates) {
        return userDAO.updateUserProfile(userProfileUpdates);
    }

    @Override
    public UserProfile getUserProfile(Long profileId) {
        return userDAO.getUserProfile(profileId);
    }

    @Override
    public boolean deleteUserProfile(Long userAccountId, Long userProfileId) {
       return userDAO.deleteUserProfile(userAccountId,userProfileId);
    }
    //endregion
    */

    @Override
    public UserSession login(String email, String password) throws InvalidUserCredentialsException {

        try
        {
            UserAccount userAccount = this.getUserAccount(email, false);
            if(userAccount != null && userAccount.getEmail().equals(email) && userAccount.getPassword().equals(password) )
            {
                return new UserSession(userAccount.getId(), generateUserToken(userAccount));
            }
        }
        catch (UserAccountNotFoundException ex)
        {
            throw new InvalidUserCredentialsException("No user with matching credentials found");
        }
        catch (InvalidUserAccountException ex)
        {
            throw new InvalidUserCredentialsException("Invalid user credentials");
        }

        return null;
    }

    @Override
    public String generateUserToken(UserAccount userAccount) {

        //TODO GENERATE UNIQUE TOKEN
        return "abc123";
    }

    @Override
    public boolean validateUserSession(UserSession userSession) {

        //TODO IMPLEMENT
        return userSession.getToken() != null && userSession.getToken().equals("abc123");
    }
}
