package per.harris.data.mq.processor;

import per.harris.data.core.processor.AbstractDataOutputProcessor;
import per.harris.data.mq.pojo.UniformDataModel;

/**
 * 数据处理完毕事件发布器节点，发布数据处理完成的事件给到事件的订阅者
 */
public class DataProcessEndingEventPublisherProcessor extends AbstractDataOutputProcessor<UniformDataModel, Void> {

    @Override
    public Void transformToOutput(UniformDataModel input) {
        System.out.println("publish event with pipeline data");
        return null;
    }
}
