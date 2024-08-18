package per.harris.data.core.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import per.harris.data.core.ProcessorChainPipeline;
import per.harris.data.examples.filter.IdExampleDO;

import java.util.Optional;

/**
 * @author Harris
 * @date 2020/08/04
 */

@SpringJUnitConfig
public class AbstractFilterDataProcessorTest {

    private ProcessorChainPipeline<String, IdExampleDO, String> processorChainPipeline;

    @BeforeEach
    void before() {
        processorChainPipeline = new ProcessorChainPipeline<>();
        processorChainPipeline.addStartingProcessor(new FilterTestJsonInputProcessor());
        processorChainPipeline.addProcessor(new IdFilterTestProcessor());
        processorChainPipeline.addEndingProcessor(new FilterTestJsonOutputProcessor());
    }

    @Test
    void filter_when_Id_contains_abc_then_expected_result_is_empty() {
        String id = "abc_123";
        Optional<String> result = processorChainPipeline.inPipeline(id);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void filter_when_Id_contains_abc_then_expected_result_is_not_null() {
        String id = "123";
        Optional<String> result = processorChainPipeline.inPipeline(id);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
    }

}

class IdFilterTestProcessor extends AbstractFilterDataProcessor<IdExampleDO> {

    @Override
    public boolean filterData(IdExampleDO input) {
        return input.getId().contains("abc");
    }
}

class FilterTestJsonInputProcessor extends AbstractInputDataProcessor<String, IdExampleDO> {

    public IdExampleDO processData(String input) {
        return new IdExampleDO(input);
    }
}

class FilterTestJsonOutputProcessor extends AbstractDataOutputProcessor<IdExampleDO, String> {

    public String transformToOutput(IdExampleDO input) {
        return input.getId();
    }
}