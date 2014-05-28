package AuthenticationApi;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class RegisterResponse
{
    RegisterResponseType signUpResponseType;
    String email;

    public RegisterResponse() {
    }

    public RegisterResponse(RegisterResponseType signUpResponseType, String email) {
        this.signUpResponseType = signUpResponseType;
        this.email = email;
    }

    public RegisterResponseType getSignUpResponseType() {
        return signUpResponseType;
    }

    public void setSignUpResponseType(RegisterResponseType signUpResponseType) {
        this.signUpResponseType = signUpResponseType;
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
        jsonObj.put("signUpResponseType", signUpResponseType.name());
        jsonObj.put("email",email);

        return jsonObj;
    }
}


