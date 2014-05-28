package AuthenticationApi;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class LoginResponse
{
    private LoginResponseType loginResponseType;
    private String email;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String email, LoginResponseType loginResponseType, String token) {
        this.loginResponseType = loginResponseType;
        this.email = email;
        this.token = token;
    }

    public LoginResponseType getLoginResponseType() {
        return loginResponseType;
    }

    public void setLoginResponseType(LoginResponseType loginResponseType) {
        this.loginResponseType = loginResponseType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ObjectNode toJson()
    {
        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonObj = jsonMapper.createObjectNode();
        jsonObj.put("loginResponseType", loginResponseType.name());
        jsonObj.put("email", email);
        jsonObj.put("token", token);

        return jsonObj;
    }
}
