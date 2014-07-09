package user.impl;


import com.mongodb.*;
import org.slf4j.Logger;
import persistence.api.IPersistenceService;
import user.api.UserDetails;

import static org.slf4j.LoggerFactory.getLogger;

public class UserDAO {

    private static final Logger LOGGER = getLogger(UserDAO.class);
    private IPersistenceService persistenceService;
    private DB database;
    private static final String USER_COLLECTION = "UserDetails";

    public UserDAO(IPersistenceService persistenceService) {

        this.persistenceService = persistenceService;
        MongoClient dbClient = (MongoClient)persistenceService.getDBClient();
        this.database = dbClient.getDB("X_Platform_1");
    }

    public UserDetails getUser(String userId)
    {
        UserDetails userDetails = null;

        DBCollection collection = this.database.getCollection(USER_COLLECTION);
        if(collection == null)
        {return null;}

        BasicDBObject detailsToFind = new BasicDBObject("id", userId);
        DBCursor cursor = collection.find(detailsToFind);

        try
        {
            if(cursor.hasNext())
            {
                DBObject detailsObj = cursor.next();
                userDetails = new UserDetails(detailsObj);
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not get user details: {}", ex);
        }
        finally
        {
            cursor.close();
        }

        return userDetails;
    }

    public boolean saveUserDetails(UserDetails userDetails)
    {
        if(this.database == null)
        {
            return false;
        }

        DBCollection collection = this.database.getCollection(USER_COLLECTION);
        if(collection == null)
        {
            return false;
        }

        boolean userExists = this.getUser(userDetails.getId()) != null;

        try {
            //update existing user
            if(userExists)
            {
                BasicDBObject userUpdates = userDetails.toMongoDBObj();
                BasicDBObject whichUserToUpdate = new BasicDBObject().append("id", userDetails.getId());
                collection.update(whichUserToUpdate, new BasicDBObject("$set", userUpdates));
            }
            //save new user
            else
            {
                BasicDBObject newUser = userDetails.toMongoDBObj();
                collection.insert(newUser);
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not save user details: {}", ex);
        }
        return true;
    }

    public boolean deleteUser(String userId)
    {
        if(this.database == null)
        {
            return false;
        }

        DBCollection collection = this.database.getCollection(USER_COLLECTION);
        if(collection == null)
        {
            return false;
        }

        BasicDBObject userToDelete = new BasicDBObject()
                .append("id", userId);

        try
        {
            collection.remove(userToDelete);
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not delete user details: {}", ex);
            return false;
        }

        return true;

    }

    public long countUsers()
    {
        if(this.database == null)
        {
            return -1;
        }

        if(this.database.collectionExists(USER_COLLECTION))
        {
            DBCollection collection = this.database.getCollection(USER_COLLECTION);
            if(collection == null)
            {
                return 0;
            }
            else
            {
                return collection.getCount();
            }
        }

        return 0;

    }
}
