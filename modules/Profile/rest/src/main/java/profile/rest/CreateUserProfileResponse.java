package profile.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import profile.api.UserProfile;

/**
 * Created by Onis on 05/07/14.
 */
public class CreateUserProfileResponse {

    private ResponseStatus responseStatus;
    private String serverMessage;
    private UserProfile userProfile;

    public CreateUserProfileResponse() {
    }

    public CreateUserProfileResponse(ResponseStatus responseStatus, String serverMessage, UserProfile userProfile) {
        this.responseStatus = responseStatus;
        this.serverMessage = serverMessage;
        this.userProfile = userProfile;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public ObjectNode toJson(){

        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonObj = jsonMapper.createObjectNode();

        if(this.responseStatus != null)
        {
            jsonObj.put("responseStatus", this.responseStatus.name());
        }
        else
        {
            jsonObj.put("responseStatus", "");
        }

        if(this.serverMessage != null)
        {
            jsonObj.put("serverMessage", this.serverMessage);
        }
        else
        {
            jsonObj.put("serverMessage", "");
        }

        if(this.userProfile != null)
        {
            jsonObj.put("userProfile", this.userProfile.toJson());
        }
        else
        {
            jsonObj.put("userProfile", "");
        }

        return jsonObj;
    }
}
