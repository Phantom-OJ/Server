package sustech.edu.phantom.dboj.response.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sustech.edu.phantom.dboj.response.RestResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @author Lori
 */
@Slf4j
public class JsonSerializer extends com.fasterxml.jackson.databind.JsonSerializer<RestResponse<Object>> {
    @Override
    public void serialize(RestResponse<Object> response, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        log.debug("Start serialize Response: {}", response);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(KeyDictionary.MESSAGE_KEY, response.getMessage());
        if (response.getData().isPresent()) {
            jsonGenerator.writeObjectField(KeyDictionary.DATA_KEY, response.getData().get());
        }

        if (response.getFields().isPresent()) {
            Map<String, Object> fields = response.getFields().get();
            fields.forEach((k, v) -> {
                try {
                    jsonGenerator.writeObjectField(k, v);
                } catch (IOException e) {
                    log.error("write object field fail,key:[{}],value:[{}]", k, v);
                }
            });
        }

        jsonGenerator.writeEndObject();
        log.debug("Finished serialize Response：{}", response);
    }
}
