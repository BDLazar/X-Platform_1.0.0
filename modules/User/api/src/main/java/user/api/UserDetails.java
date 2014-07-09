package user.api;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.util.Date;

public class UserDetails {

    private String id;
    private String name;
    private String gender;
    private Date dob;
    private String pictureUrl;

    public UserDetails() {
    }

    public UserDetails( String id, String name, String gender, Date dob, String pictureUrl) {

        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.pictureUrl = pictureUrl;
    }

    public UserDetails(DBObject mongoDBObj) {

        Object db_id = mongoDBObj.get("id");
        if(db_id != null)
        {
            this.id = (String)db_id;
        }
        Object db_name = mongoDBObj.get("name");
        if(db_name != null)
        {
            this.name = (String)db_name;
        }
        Object db_gender = mongoDBObj.get("gender");
        if(db_gender != null)
        {
            this.gender = (String)db_gender;
        }
        Object db_dob = mongoDBObj.get("dob");
        if(db_dob != null)
        {
            this.dob = (Date)db_dob;
        }
        Object db_pictureUrl = mongoDBObj.get("pictureUrl");
        if(db_pictureUrl != null)
        {
            this.pictureUrl = (String)db_pictureUrl;
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public ObjectNode toJson(){

        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonObj = jsonMapper.createObjectNode();
        jsonObj.put("id", this.id);
        jsonObj.put("name", this.name);
        jsonObj.put("gender", this.gender);
        if(dob!=null)
        {
            jsonObj.put("dob", this.dob.toString());
        }
        else
        {
            jsonObj.put("dob", "");
        }
        jsonObj.put("pictureUrl", this.pictureUrl);

        return jsonObj;
    }

    public BasicDBObject toMongoDBObj()
    {
        BasicDBObject mongoDBObj = new BasicDBObject()
                .append("id", this.id)
                .append("name", this.name)
                .append("dob", this.dob)
                .append("gender", this.gender)
                .append("pictureUrl", this.pictureUrl);

        return mongoDBObj;
    }
}
