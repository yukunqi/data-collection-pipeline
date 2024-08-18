package per.harris.data.mq.processor;

import per.harris.data.core.processor.AbstractMiddleProcessor;
import per.harris.data.mq.pojo.DataSendResponse;
import per.harris.data.mq.pojo.UniformDataModel;


/**
 * 数据发送处理节点 将数据发送给第三方、逃离本管道流范围的其他组件或系统
 */
public class DataSendingProcessor extends AbstractMiddleProcessor<UniformDataModel, UniformDataModel> {
    @Override
    public UniformDataModel processData(UniformDataModel input) {
        //sending data to third party
        System.out.print("send data to third party and received sendResponse\n");

        DataSendResponse dataSendResponse = new DataSendResponse(true,null);

        if (dataSendResponse.isSuccess()) {
            System.out.println("success so we count success in redis");
        }

        return input;
    }
}
