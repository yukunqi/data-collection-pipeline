package per.harris.data.pojo;

import java.util.Map;

public class UniformDataModel {

    private Map<String,Object> data;

    private String bizChannel;
    private String transactionDate;

    public UniformDataModel(Map<String,Object> data){
        this.data = data;
    }

    public String getBizChannel() {
        return bizChannel;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
