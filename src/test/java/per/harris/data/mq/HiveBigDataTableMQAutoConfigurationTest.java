package per.harris.data.mq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import per.harris.data.core.AbstractMiddleProcessor;
import per.harris.data.core.ProcessorChainPipeline;
import per.harris.data.pojo.UniformDataModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringJUnitConfig
public class HiveBigDataTableMQAutoConfigurationTest {

    private HiveBigDataTableMQAutoConfiguration configuration;

    @BeforeEach
    public void setUp() {
        configuration = new HiveBigDataTableMQAutoConfiguration();
    }

    @Test
    public void testProcessorChainPipeline() {
        ProcessorChainPipeline<String, UniformDataModel, Void> pipeline = configuration.processorChainPipeline();

        assertNotNull(pipeline, "ProcessorChainPipeline should not be null");

        // Optional: Add more assertions here to verify each processor in the pipeline if needed.
        // For example, you can check if the starting processor is an instance of JsonDataReadProcessor.
        // However, this might require you to make some of the processors' constructors package-private or public.
    }

    @Test
    public void runInPipeline() {
        ProcessorChainPipeline<String, UniformDataModel, Void> pipeline = configuration.processorChainPipeline();
        String jsonString = "{\"bizChannel\":\"test\",\"transactionDate\":\"2023-07-01\",\"data\":{\"key\":\"value\"}}";
        pipeline.inPipeline(jsonString);
    }

    @Test
    public void testThreadSleepingProcessor() {
        ProcessorChainPipeline<String, UniformDataModel, Void> pipeline = configuration.processorChainPipeline();
        ThreadSleepingProcessor threadSleepingProcessor = new ThreadSleepingProcessor(3);
        pipeline.addProcessor(threadSleepingProcessor);
        String jsonString = "{\"bizChannel\":\"test\",\"transactionDate\":\"2023-07-01\",\"data\":{\"key\":\"value\"}}";
        pipeline.inPipeline(jsonString);

    }
}


class ThreadSleepingProcessor extends AbstractMiddleProcessor<UniformDataModel, UniformDataModel> {

    private final int sleepTimeSeconds;

    public ThreadSleepingProcessor(int sleepTimeSeconds) {
        this.sleepTimeSeconds = sleepTimeSeconds;
    }

    @Override
    public UniformDataModel processData(UniformDataModel input) {
        try {
            Thread.sleep(sleepTimeSeconds * 1000L);
            return input;
        } catch (InterruptedException e) {
            return input;
        }
    }

}
