package per.harris.data.mq.processor;

import per.harris.data.core.AbstractEndingProcessor;
import per.harris.data.pojo.UniformDataModel;

/**
 * 数据处理完毕事件发布器节点，发布数据处理完成的事件给到事件的订阅者
 */
public class DataProcessEndingEventPublisherProcessor extends AbstractEndingProcessor<UniformDataModel, Void> {


    @Override
    public Void processData(UniformDataModel input) {
        System.out.println("publish event with pipeline data");
        return null;
    }
}
