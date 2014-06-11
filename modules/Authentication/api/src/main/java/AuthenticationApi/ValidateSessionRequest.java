package AuthenticationApi;

/**
 * Created by Onis on 07/06/14.
 */
public class ValidateSessionRequest {

    private String loginID;
    private String token;

    public ValidateSessionRequest() {
    }

    public ValidateSessionRequest(String loginID, String token) {
        this.loginID = loginID;
        this.token = token;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
