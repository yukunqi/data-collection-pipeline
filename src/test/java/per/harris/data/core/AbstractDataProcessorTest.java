package per.harris.data.core;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import per.harris.data.mq.processor.DataProcessEndingEventPublisherProcessor;
import per.harris.data.mq.processor.JsonDataReadProcessor;
import per.harris.data.pojo.UniformDataModel;

public class AbstractDataProcessorTest {

    private ProcessorChainPipeline<String, UniformDataModel, Void> processorChainPipeline;

    @BeforeClass
    void before() {
        DoNothingProcessor doNothingProcessor = new DoNothingProcessor();
        processorChainPipeline = new ProcessorChainPipeline<>();
        processorChainPipeline.addProcessor(doNothingProcessor);
        processorChainPipeline.addStartingProcessor(new JsonDataReadProcessor());
        processorChainPipeline.addEndingProcessor(new DataProcessEndingEventPublisherProcessor());
    }


    @Test(threadPoolSize = 10, invocationCount = 10, timeOut = 10000)
    void processWithinPipeline() {
        processorChainPipeline.inPipeline("{\"user\":\"aaa\"}");
    }

}

class DoNothingProcessor extends AbstractMiddleProcessor<UniformDataModel, UniformDataModel> {

    @Override
    public UniformDataModel processData(UniformDataModel input) {
        return input;
    }
}