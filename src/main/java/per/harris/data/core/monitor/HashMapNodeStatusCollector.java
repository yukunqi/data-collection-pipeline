package per.harris.data.core.monitor;

import per.harris.data.core.PipelineContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapNodeStatusCollector implements NodeStatusCollector {

    private Map<String, PipelineContext> pipelineContextMap;

    public HashMapNodeStatusCollector() {
        pipelineContextMap = new ConcurrentHashMap<>();
    }

    @Override
    public void collect(PipelineContext pipelineContext) {
        System.out.println(pipelineContext.toString());

        pipelineContextMap.put(pipelineContext.getInstanceId(), pipelineContext);
    }
}
