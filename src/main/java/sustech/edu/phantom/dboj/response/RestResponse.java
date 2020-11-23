package sustech.edu.phantom.dboj.response;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sustech.edu.phantom.dboj.response.serializer.JsonDeserializer;
import sustech.edu.phantom.dboj.response.serializer.JsonSerializer;

import java.util.Map;
import java.util.Optional;

/**
 * @author Lori
 */
@JsonDeserialize(using = JsonDeserializer.class)
@JsonSerialize(using = JsonSerializer.class)
public interface RestResponse<T> extends IErrorMsg {
    /**
     *
     * @return
     */
    Optional<Map<String, Object>> getFields();

    /**
     *
     * @return
     */
    Optional<T> getData();
}
