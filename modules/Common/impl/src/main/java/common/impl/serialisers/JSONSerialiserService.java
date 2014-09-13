package common.impl.serialisers;

import common.api.serialisers.IJSONSerialiserService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;
import java.util.Iterator;

public class JSONSerialiserService implements IJSONSerialiserService{

    //Serialise Java object to JSON
    @Override
    public JsonNode serialize(Object objectToSerialise) {

        ObjectMapper jsonMapper = new ObjectMapper();

        try {

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonString = objectWriter.writeValueAsString(objectToSerialise);
            JsonNode jsonNode = jsonMapper.readTree(jsonString);
            return jsonNode;

        } catch (Exception ex){

            return null;

        }

    }

    @Override
    public Object deserialise(JsonNode jsonNode) {
        return null;
        //TODO NOT IMPLEMETED
    }

    // Merge 2 JSON Nodes
    @Override
    public JsonNode merge(JsonNode mainNode, JsonNode updateNode) {

        Iterator<String> fieldNames = updateNode.getFieldNames();
        while (fieldNames.hasNext()) {

            String fieldName = fieldNames.next();
            JsonNode jsonNode = mainNode.get(fieldName);

            // if field exists and is an embedded object
            if (jsonNode != null && jsonNode.isObject())
            {
                this.merge(jsonNode, updateNode.get(fieldName));
            }
            else
            {
                if (mainNode instanceof ObjectNode) {
                    // Overwrite field
                    JsonNode value = updateNode.get(fieldName);
                    ((ObjectNode) mainNode).put(fieldName, value);
                }
            }
        }

        return mainNode;
    }


}
