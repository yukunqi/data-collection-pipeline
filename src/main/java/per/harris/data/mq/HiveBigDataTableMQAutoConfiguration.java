package per.harris.data.mq;

import per.harris.data.core.monitor.HashMapNodeStatusCollector;
import per.harris.data.core.pipeline.ProcessorChainPipeline;
import per.harris.data.mq.pojo.UniformDataModel;
import per.harris.data.mq.processor.DataModelingProcessor;
import per.harris.data.mq.processor.DataProcessEndingEventPublisherProcessor;
import per.harris.data.mq.processor.DataSendingProcessor;
import per.harris.data.mq.processor.JsonDataReadProcessor;

public class HiveBigDataTableMQAutoConfiguration {

    public ProcessorChainPipeline<String, UniformDataModel,Void> processorChainPipeline(){
        HashMapNodeStatusCollector hashMapNodeStatusCollector = new HashMapNodeStatusCollector();

        ProcessorChainPipeline<String, UniformDataModel,Void> processorChainPipeline = new ProcessorChainPipeline<>();
        processorChainPipeline.setNodeStatusCollector(hashMapNodeStatusCollector);

        //start step parse mq data into UniformDataModel
        processorChainPipeline.addStartingProcessor(new JsonDataReadProcessor());
        //second step modeling data
        processorChainPipeline.addProcessor(new DataModelingProcessor());
        //third step send data to downstream server
        processorChainPipeline.addProcessor(new DataSendingProcessor());
        //final step publish event
        processorChainPipeline.addEndingProcessor(new DataProcessEndingEventPublisherProcessor());


        return processorChainPipeline;
    }
}
