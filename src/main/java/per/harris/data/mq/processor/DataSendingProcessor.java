package per.harris.data.mq.processor;

import per.harris.data.core.AbstractMiddleProcessor;
import per.harris.data.core.PipelineContext;
import per.harris.data.pojo.DataSendResponse;
import per.harris.data.pojo.UniformDataModel;


/**
 * 数据发送处理节点 将数据发送给第三方、逃离本管道流范围的其他组件或系统
 */
public class DataSendingProcessor extends AbstractMiddleProcessor<UniformDataModel, UniformDataModel> {
    @Override
    public UniformDataModel processData(UniformDataModel input) {
        PipelineContext pipelineContext = this.runningContextThreadLocal.get();

        //sending data to third party
        System.out.print("send data to third party and received sendResponse\n");

        DataSendResponse dataSendResponse = new DataSendResponse(true,null);

        System.out.println("add send response to header and transfer to next");

        pipelineContext.addHeader("x-send-result", dataSendResponse);

        return input;
    }
}
