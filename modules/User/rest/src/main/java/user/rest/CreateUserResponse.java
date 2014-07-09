package user.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import user.api.*;

public class CreateUserResponse {

    private ResponseStatus responseStatus;
    private String serverMessage;
    private UserDetails userDetails;

    public CreateUserResponse() {
    }

    public CreateUserResponse(ResponseStatus responseStatus, String serverMessage, UserDetails userDetails) {
        this.responseStatus = responseStatus;
        this.serverMessage = serverMessage;
        this.userDetails = userDetails;
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

    public UserDetails getUser() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
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

        if(this.userDetails != null)
        {
            jsonObj.put("userDetails", this.userDetails.toJson());
        }
        else
        {
            jsonObj.put("userDetails", "");
        }

        return jsonObj;
    }
}
