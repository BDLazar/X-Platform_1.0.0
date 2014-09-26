package user.api.data;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

public class UserSession
{
    Long userAccountId;
    String token;

    public UserSession()
    {
    }

    public UserSession(Long userAccountId, String token){
        this.userAccountId = userAccountId;
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Long getUserAccountId()
    {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId)
    {
        this.userAccountId = userAccountId;
    }

    public ObjectNode toJson(){

        ObjectMapper jsonMapper = new ObjectMapper();

        try {

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonString = objectWriter.writeValueAsString(this);
            JsonNode jsonObj = jsonMapper.readTree(jsonString);

            return (ObjectNode)jsonObj;

        } catch (Exception ex){

            return null;
        }
    }
}
