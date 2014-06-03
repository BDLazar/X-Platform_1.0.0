package PersistenceImpl;

import PersistenceApi.IPersistenceService;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class MongoDBService implements IPersistenceService
{
    private static final Logger LOGGER = getLogger(MongoDBService.class);
    private MongoClient mongoDBClient = null;

    public MongoDBService() {
        //Initialise the DB client
        getDBInstance();
    }

    @Override
    public Object getDBInstance() {

        if(mongoDBClient == null)
        {
            try
            {
                mongoDBClient = new MongoClient("localhost",27017);
                LOGGER.info("Mongo DB connection established");
            }
            catch (UnknownHostException ex)
            {
                LOGGER.error("Could not establish connection to Mongo DB", ex);
            }

        }

        return mongoDBClient;
    }
}
