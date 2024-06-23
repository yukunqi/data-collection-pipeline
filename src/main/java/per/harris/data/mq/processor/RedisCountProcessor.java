package per.harris.data.mq.processor;

import per.harris.data.core.AbstractMiddleProcessor;
import per.harris.data.core.PipelineContext;
import per.harris.data.pojo.DataSendResponse;
import per.harris.data.pojo.UniformDataModel;

public class RedisCountProcessor extends AbstractMiddleProcessor<UniformDataModel, UniformDataModel> {


    @Override
    public UniformDataModel processData(UniformDataModel input) {
        PipelineContext pipelineContext = this.runningContextThreadLocal.get();

        DataSendResponse dataSendResponse = (DataSendResponse) pipelineContext.getHeader("x-send-result");

        System.out.println("do redis count failed or success based on x-sender-result header");

        if(dataSendResponse.isSuccess()){
            System.out.println("success so we count success in redis");
        }

        return input;
    }
}
