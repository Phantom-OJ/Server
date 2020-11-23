package sustech.edu.phantom.dboj.response.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import sustech.edu.phantom.dboj.response.RestResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sustech.edu.phantom.dboj.response.GenericResponse;
import sustech.edu.phantom.dboj.response.RestResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Lori
 */
@Slf4j
public class JsonDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<RestResponse>{
    @Override
    public RestResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        log.trace("starting deserializer response...");
        ObjectNode nodes = jsonParser.getCodec().readTree(jsonParser);
        ResponseParser parser = new ResponseParser(nodes.get(KeyDictionary.MESSAGE_KEY).asText());
        if (nodes.has(KeyDictionary.DATA_KEY)) {
            parser.withData(nodes.get(KeyDictionary.DATA_KEY));
        }
        ObjectNode jsonNodes = nodes.remove(Arrays.asList(KeyDictionary.MESSAGE_KEY, KeyDictionary.DATA_KEY));
        int size = jsonNodes.size();
        if (size != 0) {
            Iterator<Map.Entry<String, JsonNode>> iterator = jsonNodes.fields();
            iterator.forEachRemaining(x -> parser.appendField(x.getKey(), x.getValue()));
        }
        log.trace("deserializer response finished...");
        return parser;
    }
    private static class ResponseParser extends GenericResponse {
        ResponseParser(String message) {
            super(message);
        }
    }
}
