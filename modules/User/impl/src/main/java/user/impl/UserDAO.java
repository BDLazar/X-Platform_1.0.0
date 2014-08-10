package user.impl;

import org.slf4j.Logger;
import user.api.UserAccount;
import user.api.UserProfile;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.slf4j.LoggerFactory.getLogger;

public class UserDAO
{
    private static final Logger LOGGER = getLogger(UserDAO.class);
    private EntityManager postgresSQLDB;

    public UserDAO(EntityManager postgresSQLDB){
        this.postgresSQLDB = postgresSQLDB;
    }

    //region User Account
    //region Insert

    /*Inserts a new UserAccount record into the database,
     * Before calling this function please make sure :
     * 1) UserAccount id is null or negative
     * 2) UserAccount userAccountType is not null*/
    public UserAccount insertUserAccount(UserAccount newUserAccount) {

        try
        {
            postgresSQLDB.persist(newUserAccount);
            postgresSQLDB.flush();

            //return the user account persisted to the database
            return newUserAccount;
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not insert user account: {}", ex);
            return null;
        }

    }

    //endregion

    //region Update

    public UserAccount updateUserAccount(UserAccount userAccountUpdates){

        try
        {
            //retrieve existing user account from the database
            UserAccount existingUserAccount = this.getUserAccount(userAccountUpdates.getId(), true);

            if(existingUserAccount != null)
            {
                //apply updates to the existing user account
                if(existingUserAccount.updateFrom(userAccountUpdates) > 0)
                {
                    //persist the updates into the database
                    postgresSQLDB.persist(existingUserAccount);
                    postgresSQLDB.flush();
                }

                //return the object persisted to the database
                return existingUserAccount;
            }

            return null;

        }
        catch (Exception ex)
        {
            LOGGER.error("Could not update user account: {}", ex);
            return null;
        }

    }

    //endregion

    //region Get
    /*Get user account by id
    *DO NOT call getters of lazy loaded properties of UserAccount object returned by this function*/
    public UserAccount getUserAccount(Long id){

        try
        {
            Query query = postgresSQLDB.createQuery("SELECT userAccount FROM UserAccount userAccount WHERE userAccount.id=:id");
            query.setParameter("id", id);
            UserAccount userAccount = (UserAccount)query.getResultList().get(0);

            return userAccount;
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not retrieve user account: {}", ex);
        }

        return null;
    }

    /*Get user account by email
     *DO NOT call getters of lazy loaded properties of UserAccount object returned by this function*/
    public UserAccount getUserAccount(String email){

        try
        {
            Query query = postgresSQLDB.createQuery("SELECT userAccount FROM UserAccount userAccount WHERE userAccount.email=:email");
            query.setParameter("email", email);
            UserAccount userAccount = (UserAccount)query.getResultList().get(0);

            return userAccount;
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not retrieve user account: ", ex);
        }

        return null;
    }

    /*Get user account by id
     *YOU MAY ONLY call getters of lazy loaded properties of UserAccount object returned by this function if you pass in true for the equivalent loadUser? parameter*/
    public UserAccount getUserAccount(Long id, boolean loadUserProfiles){

        String selectFromClause = "SELECT userAccount FROM UserAccount userAccount ";
        String joinClause = ""; //we populate the join clause based on the boolean parameters passed into this function
        String whereClause = "WHERE userAccount.id = :id";


        if(loadUserProfiles)
        {
            //we also want to load the user profiles if loadUserProfiles is true
            joinClause = joinClause + "LEFT JOIN FETCH userAccount.userProfiles ";
        }

        try
        {
            Query query = postgresSQLDB.createQuery(selectFromClause + joinClause + whereClause);
            query.setParameter("id", id);
            UserAccount userAccount = (UserAccount)query.getResultList().get(0);

            return userAccount;
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not retrieve user account: ", ex);
        }

        return null;

    }

    /*Get user account by email
     *YOU MAY ONLY call getters of lazy loaded properties of UserAccount object returned by this function if you pass in true for the equivalent loadUser? parameter*/
    public UserAccount getUserAccount(String email, boolean loadUserProfiles){

        String selectFromClause = "SELECT userAccount FROM UserAccount userAccount ";
        String joinClause = ""; //we populate the join clause based on the boolean parameters passed into this function
        String whereClause = "WHERE userAccount.email = :email";


        if(loadUserProfiles)
        {
            //we also want to load the user profiles if loadUserProfiles is true
            joinClause = joinClause + "LEFT JOIN FETCH userAccount.userProfiles ";
        }

        try
        {
            Query query = postgresSQLDB.createQuery(selectFromClause+joinClause+whereClause);
            query.setParameter("email", email);
            UserAccount userAccount = (UserAccount)query.getResultList().get(0);

            return userAccount;
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not retrieve user account: ", ex);
        }

        return null;

    }
    //endregion

    //region Delete
    public boolean deleteUserAccount(Long userAccountId)
    {
        try
        {
           UserAccount userAccountToDelete = this.getUserAccount(userAccountId,true);
           if(userAccountToDelete != null)
           {
               this.postgresSQLDB.remove(userAccountToDelete);
               return true;
           }

           return false;
        }
        catch(Exception ex)
        {
            LOGGER.error("Could not delete user account: {}", ex);
        }

        return false;
    }
    //endregion

    //endregion

    //region User Profile

    //region Insert
    public UserProfile insertUserProfile(Long userAccountId, UserProfile newUserProfile) {

        try
        {

            //get user account which the new user profile will belong to (load the existing user profiles too)
            UserAccount userAccount = this.getUserAccount(userAccountId,true);

            //make sure the user account exists
            //add the new user profile to the user account's list of user profiles and save the user account to the database.
            if(userAccount != null && userAccount.addUserProfile(newUserProfile))
            {
                postgresSQLDB.persist(userAccount);
                postgresSQLDB.flush();
                return newUserProfile;
            }

            return null;
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not insert user account: {}", ex);
            return null;
        }

    }

    //endregion

    //region Update
    public UserProfile updateUserProfile(UserProfile userProfileUpdates){

        try
        {
            //retrieve existing user profile from the database
            UserProfile existingUserProfile = this.getUserProfile(userProfileUpdates.getId());

            if(existingUserProfile != null)
            {
                //apply updates to the existing user account
                if(existingUserProfile.updateFrom(userProfileUpdates) > 0)
                {
                    //persist the updates into the database
                    postgresSQLDB.persist(existingUserProfile);
                    postgresSQLDB.flush();
                }

                //return the object persisted to the database
                return existingUserProfile;
            }

            return null;

        }
        catch (Exception ex)
        {
            LOGGER.error("Could not update user account: {}", ex);
            return null;
        }

    }

    //endregion

    //region Get

    /*Get user account by id*/
    public UserProfile getUserProfile(Long userProfileId){

        try
        {
            Query query = postgresSQLDB.createQuery("SELECT userProfile FROM UserProfile userProfile WHERE userProfile.id=:userProfileId");
            query.setParameter("userProfileId", userProfileId);
            UserProfile userProfile = (UserProfile)query.getResultList().get(0);

            return userProfile;
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not retrieve user account: {}", ex);
        }

        return null;
    }

    //endregion

    //region Delete
    public boolean deleteUserProfile(Long userAccountId, Long userProfileId)
    {
        try
        {
            //get user account which the new user profile belongs to (load the existing user profiles too)
            UserAccount userAccount = this.getUserAccount(userAccountId,true);

            //make sure the user account exists
            //remove the userProfile from the user account's list of profiles and save the user account to the database
            if(userAccount != null && userAccount.removeUserProfile(userProfileId))
            {
                postgresSQLDB.persist(userAccount);
                postgresSQLDB.flush();
                return true;
            }

            return false;
        }

        catch(Exception ex)
        {
            LOGGER.error("Could not delete user account: {}", ex);
        }

        return false;
    }
    //endregion

    //endregion
}
