package common.api.serialisers;

import org.codehaus.jackson.JsonNode;

public interface IJSONSerialiserService {

    public JsonNode serialize(Object object);
    public Object deserialise(JsonNode jsonNode);
    public JsonNode merge(JsonNode mainNode, JsonNode updateNode);
}
