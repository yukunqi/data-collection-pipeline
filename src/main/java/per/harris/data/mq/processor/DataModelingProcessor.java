package per.harris.data.mq.processor;

import per.harris.data.core.AbstractMiddleProcessor;
import per.harris.data.pojo.UniformDataModel;

import java.util.Map;

/**
 * 根据CTL文件，完成数据建模过程
 * 功能包括：自定义的数据字段解析、数据类型转换、数据值处理等
 */
public class DataModelingProcessor extends AbstractMiddleProcessor<UniformDataModel, UniformDataModel> {


    @Override
    public UniformDataModel processData(UniformDataModel input) {

        Map<String, Object> data = input.getData();

        for(Map.Entry<String,Object> entry : data.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            System.out.printf("mapping Key from %s to %s%n", key, key+"_afterMapping");

            System.out.printf("convert value from %s to %s\n", value, value+"_afterConvert");

            data.put(key,value+"_afterConvert");

        }

        return input;
    }
}
