package common.api.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created by Onis on 15/08/14.
 */
public class Error {

    String id;
    String title;
    Severity severity;
    String simpleDescription;
    String verboseDescription;

    public Error() {
    }

    public Error(String id, String title, Severity severity, String simpleDescription, String verboseDescription) {
        this.id = "ERROR_" + id;
        this.title = title;
        this.severity = severity;
        this.simpleDescription = simpleDescription;
        this.verboseDescription = verboseDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = "ERROR_" + id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getSimpleDescription() {
        return simpleDescription;
    }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription;
    }

    public String getVerboseDescription() {
        return verboseDescription;
    }

    public void setVerboseDescription(String verboseDescription) {
        this.verboseDescription = verboseDescription;
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
