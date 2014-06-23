package authentication.api;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class RegisterResponse
{
    RegisterResponseType registerResponseType;
    String email;

    public RegisterResponse() {
    }

    public RegisterResponse(RegisterResponseType registerResponseType, String email) {
        this.registerResponseType = registerResponseType;
        this.email = email;
    }

    public RegisterResponseType getRegisterResponseType() {
        return registerResponseType;
    }

    public void setRegisterResponseType(RegisterResponseType registerResponseType) {
        this.registerResponseType = registerResponseType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ObjectNode toJson(){
        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonObj = jsonMapper.createObjectNode();
        jsonObj.put("registerResponseType", registerResponseType.name());
        jsonObj.put("email",email);

        return jsonObj;
    }
}


