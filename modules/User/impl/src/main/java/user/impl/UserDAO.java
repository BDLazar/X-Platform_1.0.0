package user.impl;

import org.slf4j.Logger;
import user.api.data.UserAccount;
import user.api.exceptions.UserAccountFoundException;
import user.api.exceptions.UserAccountNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UserDAO
{
    private static final Logger LOGGER = getLogger(UserDAO.class);
    private EntityManager postgresSQLDB;

    public UserDAO(EntityManager postgresSQLDB){
        this.postgresSQLDB = postgresSQLDB;
    }

    //region User Account
    public Long insertUserAccount(UserAccount newUserAccount) throws UserAccountFoundException {

        boolean accountAlreadyExists = true;

        try
        {
            this.getUserAccount(newUserAccount.getEmail());
        }
        catch (UserAccountNotFoundException e)
        {
            accountAlreadyExists = false;
        }

        if(!accountAlreadyExists)
        {
            postgresSQLDB.persist(newUserAccount);
            postgresSQLDB.flush();

            //return the id of the user account we have just inserted into the database
            return newUserAccount.getId();
        }

        throw new UserAccountFoundException("A user account with email: "+ newUserAccount.getEmail() + " already exists");
    }

    //Get user account by id
    //DO NOT call getters of lazy loaded properties of UserAccount object returned by this function
    public UserAccount getUserAccount(Long userAccountId) throws UserAccountNotFoundException{

        //do not load any lazy loaded components
        return this.getUserAccount(userAccountId,false);
    }

    //Get user account by id
    //YOU MAY ONLY call getters of lazy loaded properties of UserAccount object returned by this function if you pass in true for the equivalent loadUser? parameter
    public UserAccount getUserAccount(Long userAccountId, boolean loadUserProfiles) throws UserAccountNotFoundException {

        //Initialise the jpql query
        String selectFromClause = "SELECT userAccount FROM UserAccount userAccount ";
        String joinClause = ""; //we populate the join clause based on the boolean parameters passed into this function
        String whereClause = "WHERE userAccount.id = :userAccountId";

        //we also want to load the user profiles if loadUserProfiles is true
        if(loadUserProfiles)
        {
            joinClause = joinClause + "LEFT JOIN FETCH userAccount.userProfiles ";
        }

        //retrieve the user account from the database
        Query query = postgresSQLDB.createQuery(selectFromClause + joinClause + whereClause);
        query.setParameter("userAccountId", userAccountId);
        List<UserAccount> queryResultList = query.getResultList();

        //throw exception if we do not find the user account
        if(queryResultList == null || queryResultList.size() < 1 )
        {
            throw new UserAccountNotFoundException("User Account with id "+ userAccountId +" was not found in the database.");
        }

        UserAccount userAccount = (UserAccount)query.getResultList().get(0); //we take the first result we find
        userAccount.setWithUserProfiles(loadUserProfiles);

        //return the user account
        return userAccount;

    }

    //Get user account by email
    //DO NOT call getters of lazy loaded properties of UserAccount object returned by this function
    public UserAccount getUserAccount(String email) throws UserAccountNotFoundException{

        //do not load any lazy loaded components
        return this.getUserAccount(email,false);
    }

    //Get user account by email
    //YOU MAY ONLY call getters of lazy loaded properties of UserAccount object returned by this function if you pass in true for the equivalent loadUser? parameter
    public UserAccount getUserAccount(String userAccountEmail, boolean loadUserProfiles) throws UserAccountNotFoundException{


        //Initialise the jpql query
        String selectFromClause = "SELECT userAccount FROM UserAccount userAccount ";
        String joinClause = ""; //we populate the join clause based on the boolean parameters passed into this function
        String whereClause = "WHERE userAccount.email = :userAccountEmail";

        //we also want to load the user profiles if loadUserProfiles is true
        if(loadUserProfiles)
        {
            joinClause = joinClause + "LEFT JOIN FETCH userAccount.userProfiles ";
        }

        //try retrieve the user account from the database

        Query query = postgresSQLDB.createQuery(selectFromClause + joinClause + whereClause);
        query.setParameter("userAccountEmail", userAccountEmail);
        List<UserAccount> queryResultList = query.getResultList();

        //throw exception if we do not find the user account
        if(queryResultList == null || queryResultList.size() < 1 )
        {
            throw new UserAccountNotFoundException("User Account with email "+ userAccountEmail +" was not found in the database.");
        }

        UserAccount userAccount = (UserAccount)query.getResultList().get(0); //we take the first result we find
        userAccount.setWithUserProfiles(loadUserProfiles);

        //return the user account from the database
        return userAccount;

    }

    /*
    public WarnErrReport updateUserAccount(UserAccount userAccountUpdate){

        //retrieve existing user account from the database
        boolean loadUserProfiles = true;
        UserAccount existingUserAccount = this.getUserAccount(userAccountUpdate.getId(), loadUserProfiles);

        //try apply the userAccountUpdate to an existing userAccount in the database
        try
        {
            if(existingUserAccount != null)
            {
                //apply updates to the existing user account
                if(existingUserAccount.updateFrom(userAccountUpdate) > 0)
                {
                    //persist the updates into the database
                    postgresSQLDB.persist(existingUserAccount);
                    postgresSQLDB.flush();

                    //update applied successfully
                    return new ErrorReport(DBTransactionStatus.UPDATE_RECORD_SUCCESS, "User account update success");
                }

                //the updates do not contain any new data so we didnt do anything but we treat it as success
                return new ErrorReport(DBTransactionStatus.RECORD_UP_TO_DATE, "User account is already up to date");
            }

            //trying to apply updates to an user account that does not exist
            return new ErrorReport(DBTransactionStatus.RECORD_DOES_NOT_EXIST, "User account can not be updated because it does not exist");

        }
        catch (Exception ex)
        {
            //something went wrong while trying to apply user account updates
            LOGGER.error("Could not update user account : ", ex);
            return new ErrorReport(DBTransactionStatus.UPDATE_RECORD_FAIL, "User account update fail");
        }

    }
    public boolean validateUserAccountUpdate(UserAccount existingUserAccountUpdated){
        //validate existing user account updates
        if(existingUserAccountUpdated.getId() == null ||
           existingUserAccountUpdated.getId() < 1 ||
           existingUserAccountUpdated.getUserAccountType() == null)
        {
            //trying to update a user account that has an unknown type or that does not exist in the database
            return false;
        }

        //validate potential existing user profiles updates that belong to the potentially existing user account updates
        if(existingUserAccountUpdated.isWithUserProfiles())
        {
            for(UserProfile existingUserProfileUpdated : existingUserAccountUpdated.getUserProfiles().values())
            {
                if(!validateUserProfileUpdate(existingUserProfileUpdated))
                {
                    //trying to insert into database an invalid user profile update
                    return false;
                }
            }
        }

        //safe to apply updates
        return true;
    }

    public ErrorReport deleteUserAccount(Long userAccountId)
    {
        //retrieve user account from database
        UserAccount userAccountToDelete = this.getUserAccount(userAccountId,true);

        //try delete user account from the database
        try
        {
           if(userAccountToDelete != null)
           {
               this.postgresSQLDB.remove(userAccountToDelete);

               //removed successfully
               return new ErrorReport(DBTransactionStatus.DELETE_RECORD_SUCCESS, "User account delete success");
           }

            //could not retrieve user account to delete (may not exist or something may have went wrong in the retrieval process)
            return new ErrorReport(DBTransactionStatus.RECORD_DOES_NOT_EXIST, "User account to be deleted does not exist");
        }
        catch(Exception ex)
        {
            //something went wrong while trying to delete the user account
            LOGGER.error("Could not delete user account: {}", ex);
            return new ErrorReport(DBTransactionStatus.DELETE_RECORD_FAIL, "Failed to delete user account");
        }

    }


    //endregion

    //region User Profile


    public boolean insertUserProfile(Long userAccountId, UserProfile newUserProfile) {

        //check if newUserProfile is allowed to be inserted into the database
        if(!validateUserProfileInsert(userAccountId,newUserProfile))
        {
            return false;
        }

        //try to insert new user profile into the database
        try
        {
            //get user account which the new user profile will belong to (load the existing user profiles too)
            boolean loadUserProfiles = true;
            UserAccount userAccount = this.getUserAccount(userAccountId,loadUserProfiles);

            //make sure the user account exists
            if(userAccount != null)
            {
                //assign new user profile to existing user account
                if(userAccount.assignUserProfile(newUserProfile))
                {
                    postgresSQLDB.persist(userAccount);
                    postgresSQLDB.flush();

                    //inserted successfully
                    return true;
                }

                //could not assign user profile to the user account
                return false;
            }

            //user account which user profile should belong to was not found
            return false;
        }
        catch (Exception ex)
        {
            //something went wrong while trying to insert user profile into the database
            LOGGER.error("Could not insert user account: {}", ex);
            return false;
        }

    }
    public boolean validateUserProfileInsert(Long userAccountId, UserProfile newUserProfile)
    {
        if(userAccountId == null)
        {
            //a user profile must be owned by a user profile with a valid id
            return false;
        }


        if(newUserProfile.getId() != null || newUserProfile.getUserProfileType() == null)
        {
            //trying to insert into database an invalid user profile
            return false;
        }

        return true;

    }



    public boolean updateUserProfile(UserProfile userProfileUpdate){

        //check if userProfileUpdate is allowed to be applied to an existing user profile in the database
        if(!validateUserProfileUpdate(userProfileUpdate))
        {
            return false;
        }

        //retrieve existing user profile from the database
        UserProfile existingUserProfile = this.getUserProfile(userProfileUpdate.getId());

        try
        {
            if(existingUserProfile != null)
            {
                //apply updates to the existing user profile
                if(existingUserProfile.updateFrom(userProfileUpdate) > 0)
                {
                    //persist the updates into the database
                    postgresSQLDB.persist(existingUserProfile);
                    postgresSQLDB.flush();

                    //updates applied successfully
                    return true;
                }

                //no updates to apply (we treat this as success)
                return true;
            }

            //user profile to be updated does not exist
            return false;

        }
        catch (Exception ex)
        {
            //something went wrong while trying to update user profile
            LOGGER.error("Could not update user account: {}", ex);
            return false;
        }

    }
    private boolean validateUserProfileUpdate(UserProfile existingUserProfileUpdated){
        //validate existing user profile updates
        if(existingUserProfileUpdated.getId() == null ||
                existingUserProfileUpdated.getId() < 1 ||
                existingUserProfileUpdated.getUserProfileType() == null)
        {
            //trying to update a user profile that has an unknown type or that does not exist in the database
            return false;
        }

        //safe to apply user profile updates
        return true;
    }

    //Get user account by id
    public UserProfile getUserProfile(Long userProfileId){

        if(userProfileId == null)
        {
            //requested user profile with invalid id
            return null;
        }

        //try retrieve the user profile from the database
        try
        {
            Query query = postgresSQLDB.createQuery("SELECT userProfile FROM UserProfile userProfile WHERE userProfile.id=:userProfileId");
            query.setParameter("userProfileId", userProfileId);
            UserProfile userProfile = (UserProfile)query.getResultList().get(0); // we take the first result we find

            return userProfile;
        }
        catch (Exception ex)
        {
            //something went wrong while trying to retrieve user profile
            LOGGER.error("Could not retrieve user account: {}", ex);
            return null;
        }

    }

    public boolean deleteUserProfile(Long userAccountId, Long userProfileId){
        if(userAccountId == null || userProfileId == null)
        {
            //insufficient information provided in order to delete the the user profile
            return false;
        }

        //get user account which user profile to be deleted belongs to (load the existing user profiles too)
        UserAccount userAccount = this.getUserAccount(userAccountId,true);

        //try to delete the user profile that belongs to the given user account
        try
        {
            //make sure the user account exists
            //remove the userProfile from the user account's list of profiles and save the user account to the database
            if(userAccount != null)
            {
                //unassign the user profile from the user account
                if(userAccount.unassignUserProfile(userProfileId))
                {
                    postgresSQLDB.persist(userAccount);
                    postgresSQLDB.flush();

                    //user profile removed successfully
                    return true;
                }

                //user profile could not be unassigned from its user account
                return false;
            }

            //user account was not found
            return false;
        }

        catch(Exception ex)
        {
            //something went wrong while trying to delete the user profile from the database+
            LOGGER.error("Could not delete user account: {}", ex);
            return false;
        }

    }

    //endregion
    */

}
