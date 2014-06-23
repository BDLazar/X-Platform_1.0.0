package persistence.impl;

import persistence.api.IPersistenceService;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;

import com.mongodb.MongoException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class MongoDBService implements IPersistenceService
{
    private static final Logger LOGGER = getLogger(MongoDBService.class);
    private MongoClient mongoDBClient = null;

    public MongoDBService() {
        testDBConnection();
    }

    @Override
    public Object getDBClient() {

        if(mongoDBClient == null)
        {
            try
            {
                mongoDBClient = new MongoClient("localhost",27017);
            }
            catch (UnknownHostException ex)
            {
                LOGGER.error("Could not create Mongo DB client", ex);
                mongoDBClient = null;
            }

        }

        return mongoDBClient;
    }

    @Override
    public boolean testDBConnection() {


        if(this.getDBClient() != null)
        {
            try
            {
                mongoDBClient.getDatabaseNames();
                LOGGER.info("Mongo DB connection established");
                return true;

            }
            catch (MongoException ex)
            {
                LOGGER.error("Could not establish connection to Mongo DB", ex);
                return false;
            }

        }

        LOGGER.error("Could not establish connection to Mongo DB");
        return false;
    }

}
