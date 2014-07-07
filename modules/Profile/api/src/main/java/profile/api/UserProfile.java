package profile.api;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created by Onis on 04/07/14.
 */
public class UserProfile {

    private String id;
    private UserDetails userDetails;

    public UserProfile() {
    }

    public UserProfile(String id, UserDetails userDetails) {
        this.id = id;
        this.userDetails = userDetails;
    }

    public UserProfile(DBObject mongoDBObj) {

        Object db_id = mongoDBObj.get("id");
        if(db_id != null)
        {
            this.id = (String)db_id;
        }
        DBObject db_userDetails = (DBObject)mongoDBObj.get("userDetails");
        if(db_userDetails != null)
        {
            this.userDetails = new UserDetails(db_userDetails);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public ObjectNode toJson(){

        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonObj = jsonMapper.createObjectNode();
        jsonObj.put("id", this.id);
        if(userDetails != null)
        {
            jsonObj.put("userDetails", this.userDetails.toJson());
        }
        else
        {
            jsonObj.put("userDetails", "");
        }

        return jsonObj;
    }

    public BasicDBObject toMongoDBObj()
    {
        BasicDBObject mongoDBObj = new BasicDBObject();
        mongoDBObj.append("id", this.id);
        if(this.userDetails != null)
        {
            mongoDBObj.append("userDetails", this.userDetails.toMongoDBObj());
        }
        else
        {
            mongoDBObj.append("userDetails", null);
        }

        return mongoDBObj;
    }
}
