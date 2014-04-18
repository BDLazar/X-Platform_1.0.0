package AuthenticationApi;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class SignUpResponse
{
    SignUpResponseType signUpResponseType;
    String email;

    public SignUpResponse() {
    }

    public SignUpResponse(SignUpResponseType signUpResponseType, String email) {
        this.signUpResponseType = signUpResponseType;
        this.email = email;
    }

    public SignUpResponseType getSignUpResponseType() {
        return signUpResponseType;
    }

    public void setSignUpResponseType(SignUpResponseType signUpResponseType) {
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


