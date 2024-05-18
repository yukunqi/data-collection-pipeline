package per.harris.data.mq.processor;

import per.harris.data.core.MiddleProcessor;
import per.harris.data.core.PipelineContext;
import per.harris.data.pojo.DataSendResponse;
import per.harris.data.pojo.UniformDataModel;

public class RedisCountProcessor implements MiddleProcessor<UniformDataModel, UniformDataModel> {


    @Override
    public PipelineContext<UniformDataModel> processData(PipelineContext<UniformDataModel> input) {

        DataSendResponse dataSendResponse = (DataSendResponse) input.getHeader("x-send-result");

        System.out.println("do redis count failed or success based on x-sender-result header");

        if(dataSendResponse.isSuccess()){
            System.out.println("success so we count success in redis");
        }

        return input;
    }
}
