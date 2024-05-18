package per.harris.data.mq.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import per.harris.data.core.PipelineContext;
import per.harris.data.core.StartingProcessor;
import per.harris.data.pojo.UniformDataModel;

import java.util.HashMap;

public class JsonDataReadProcessor implements StartingProcessor<String, UniformDataModel> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PipelineContext<UniformDataModel> processData(String input) {
        HashMap<String,Object> jsonMap = null;
        try {
            jsonMap = objectMapper.readValue(input, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        UniformDataModel uniformDataModel = new UniformDataModel(jsonMap);
        return new PipelineContext<>(uniformDataModel);
    }
}
