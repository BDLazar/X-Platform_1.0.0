package profile.impl;

import com.mongodb.*;
import org.slf4j.Logger;
import persistence.api.IPersistenceService;
import profile.api.UserDetails;
import profile.api.UserProfile;

import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Onis on 29/06/14.
 */
public class UserProfileDAO {

    private static final Logger LOGGER = getLogger(UserProfileDAO.class);
    private IPersistenceService persistenceService;
    private DB database;
    private static final String USER_PROFILE_COLLECTION = "UserProfiles";

    public UserProfileDAO(IPersistenceService persistenceService) {

        this.persistenceService = persistenceService;
        MongoClient dbClient = (MongoClient)persistenceService.getDBClient();
        this.database = dbClient.getDB("X_Platform_1");
    }

    public UserProfile getUserProfile(String profileId)
    {
        UserProfile userProfile = null;

        DBCollection collection = this.database.getCollection(USER_PROFILE_COLLECTION);
        if(collection == null)
        {return null;}

        BasicDBObject profileToFind = new BasicDBObject("id", profileId);
        DBCursor cursor = collection.find(profileToFind);

        try
        {
            if(cursor.hasNext())
            {
                DBObject profileObj = cursor.next();
                userProfile = new UserProfile(profileObj);
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not get user profile: {}", ex);
        }
        finally
        {
            cursor.close();
        }

        return userProfile;
    }

    public boolean saveUserProfile(UserProfile userProfile)
    {
        if(this.database == null)
        {
            return false;
        }

        DBCollection collection = this.database.getCollection(USER_PROFILE_COLLECTION);
        if(collection == null)
        {
            return false;
        }

        boolean profileExists = this.getUserProfile(userProfile.getId()) != null;

        try {
            //update existing profile
            if(profileExists)
            {
                BasicDBObject profileUpdates = userProfile.toMongoDBObj();
                BasicDBObject whichProfileToUpdate = new BasicDBObject().append("id", userProfile.getId());
                collection.update(whichProfileToUpdate, new BasicDBObject("$set", profileUpdates));
            }
            //save new profile
            else
            {
                BasicDBObject newProfile = userProfile.toMongoDBObj();
                collection.insert(newProfile);
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not save user profile: {}", ex);
        }
        return true;
    }

    public boolean deleteUserProfile(String profileId)
    {
        if(this.database == null)
        {
            return false;
        }

        DBCollection collection = this.database.getCollection(USER_PROFILE_COLLECTION);
        if(collection == null)
        {
            return false;
        }

        BasicDBObject profileToDelete = new BasicDBObject()
            .append("id", profileId);

        try
        {
            collection.remove(profileToDelete);
        }
        catch (Exception ex)
        {
            LOGGER.error("Could not delete user profile: {}", ex);
            return false;
        }

        return true;

    }

    public long countProfiles()
    {
        if(this.database == null)
        {
            return -1;
        }

        if(this.database.collectionExists(USER_PROFILE_COLLECTION))
        {
            DBCollection collection = this.database.getCollection(USER_PROFILE_COLLECTION);
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
