package per.harris.data.mq;

import per.harris.data.core.*;
import per.harris.data.mq.processor.*;
import per.harris.data.pojo.UniformDataModel;

public class HiveBigDataTableMQAutoConfiguration {

    public ProcessorChainPipeline<String, UniformDataModel,Void> processorChainPipeline(){
        ProcessorChainPipeline<String, UniformDataModel,Void> processorChainPipeline = new ProcessorChainPipeline<>();

        //start step parse mq data into UniformDataModel
        processorChainPipeline.addStartingProcessor(new JsonDataReadProcessor());
        //second step modeling data
        processorChainPipeline.addProcessor(new DataModelingProcessor());
        //third step send data to downstream server
        processorChainPipeline.addProcessor(new DataSendingProcessor());
        //fourth step count data in redis
        processorChainPipeline.addProcessor(new RedisCountProcessor());
        //final step publish event
        processorChainPipeline.addEndingProcessor(new DataProcessEndingEventPublisherProcessor());


        return processorChainPipeline;
    }
}
