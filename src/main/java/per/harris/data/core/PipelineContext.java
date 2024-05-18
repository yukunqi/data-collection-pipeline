package per.harris.data.core;

import java.util.HashMap;
import java.util.Map;

public class PipelineContext<T> {

    private final T data;
    private Map<String,Object> headers;

    public PipelineContext(T data){
        this.data = data;
        this.headers = new HashMap<>();
    }

    public void addHeader(String key,Object value){
        this.headers.put(key,value);
    }

    public Object getHeader(String key){
        return this.headers.get(key);
    }

    public T getData(){
        return this.data;
    }

    public void removeHeader(String key){
        this.headers.remove(key);
    }
}

