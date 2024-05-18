package per.harris.data.mq.processor;

import per.harris.data.core.EndingProcessor;
import per.harris.data.core.PipelineContext;
import per.harris.data.pojo.UniformDataModel;

/**
 * 数据处理完毕事件发布器节点，发布数据处理完成的事件给到事件的订阅者
 */
public class DataProcessEndingEventPublisherProcessor implements EndingProcessor<UniformDataModel,Void> {


    @Override
    public Void processData(PipelineContext<UniformDataModel> input) {
        System.out.println("publish event with pipeline data");
        return null;
    }
}
