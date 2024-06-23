package per.harris.data.mq.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import per.harris.data.core.AbstractStartingProcessor;
import per.harris.data.pojo.UniformDataModel;

import java.util.HashMap;

public class JsonDataReadProcessor extends AbstractStartingProcessor<String, UniformDataModel> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UniformDataModel processData(String input) {
        HashMap<String, Object> jsonMap;
        try {
            jsonMap = objectMapper.readValue(input, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new UniformDataModel(jsonMap);
    }
}
