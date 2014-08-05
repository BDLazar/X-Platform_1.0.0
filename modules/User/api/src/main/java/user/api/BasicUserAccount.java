package user.api;

/**
 * Created by Onis on 02/08/14.
 */

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Iterator;

@Entity
@Table(name="BASIC_USER_ACCOUNT")
@PrimaryKeyJoinColumn(name="id")
public class BasicUserAccount extends UserAccount {

    //region Properties
    private String userName;
    private String email;
    private String password;
    //endregion

    //region Constructors
    public BasicUserAccount() {
        super(UserAccountType.BASIC);
    }

    public BasicUserAccount(String userName, String email, String password) {
        super(UserAccountType.BASIC);
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    //endregion

    //region Getters & Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //endregion

    //region Parsers
    @Override
    public ObjectNode toJson(){

        ObjectMapper jsonMapper = new ObjectMapper();

        try {

            //serialise superclass
            JsonNode superJsonObj = super.toJson();

            //Serialise subclass
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonString = objectWriter.writeValueAsString(this);
            JsonNode subJsonObj = jsonMapper.readTree(jsonString);

            //merge subclass and superclass
            JsonNode jsonObj = mergeJsonNodes(superJsonObj,subJsonObj);

            return (ObjectNode)jsonObj;

        } catch (Exception ex){

            return null;
        }

    }
    private static JsonNode mergeJsonNodes(JsonNode mainNode, JsonNode updateNode){
        Iterator<String> fieldNames = updateNode.getFieldNames();

        while (fieldNames.hasNext()) {

            String fieldName = fieldNames.next();
            JsonNode jsonNode = mainNode.get(fieldName);

            // if field exists and is an embedded object
            if (jsonNode != null && jsonNode.isObject())
            {
                mergeJsonNodes(jsonNode, updateNode.get(fieldName));
            }
            else
            {
                if (mainNode instanceof ObjectNode) {
                    // Overwrite field
                    JsonNode value = updateNode.get(fieldName);
                    ((ObjectNode) mainNode).put(fieldName, value);
                }
            }
        }

        return mainNode;
    }
    //endregion
}

