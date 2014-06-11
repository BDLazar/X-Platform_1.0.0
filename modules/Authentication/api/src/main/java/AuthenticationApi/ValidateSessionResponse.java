package AuthenticationApi;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created by Onis on 07/06/14.
 */
public class ValidateSessionResponse {

    private ValidateSessionResponseType validateSessionResponseType;

    public ValidateSessionResponse() {
    }

    public ValidateSessionResponse(ValidateSessionResponseType validateResponseType) {
        this.validateSessionResponseType = validateResponseType;
    }

    public ValidateSessionResponseType getValidateSessionResponseType() {
        return validateSessionResponseType;
    }

    public void setValidateSessionResponseType(ValidateSessionResponseType validateSessionResponseType) {
        this.validateSessionResponseType = validateSessionResponseType;
    }

    public ObjectNode toJson()
    {
        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonObj = jsonMapper.createObjectNode();
        jsonObj.put("validateSessionResponseType", validateSessionResponseType.name());
        return jsonObj;
    }
}
