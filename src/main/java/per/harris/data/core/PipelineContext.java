package per.harris.data.core;

import per.harris.data.core.monitor.ProcessorRunningInformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineContext {

    private final Map<String, Object> headers;
    private final String instanceId;
    private Map<String, ProcessorRunningInformation> runningInformationMap;

    public PipelineContext(String instanceId) {
        this.instanceId = instanceId;
        this.headers = new HashMap<>();
        this.runningInformationMap = new HashMap<>();
    }

    public void addRunningInformation(ProcessorRunningInformation runningInformation) {
        if (runningInformationMap.containsKey(runningInformation.getProcessorName())) {
            runningInformationMap.put(runningInformation.getProcessorName(), runningInformation);
        } else {
            //todo 换合适的exception
            throw new RuntimeException("ProcessorRunningInformation is not exist");
        }
    }

    public void registerProcessorName(List<? extends AbstractMiddleProcessor<?, ?>> processorList) {
        for (AbstractMiddleProcessor<?, ?> processor : processorList) {
            this.runningInformationMap.put(processor.getName(), null);
        }
    }

    public void registerProcessorName(String processorName) {
        this.runningInformationMap.put(processorName, null);
    }

    public long getRunningTime() {
        return this.runningInformationMap
                .values()
                .stream()
                .filter(v -> v != null && !v.isRunning())
                .mapToLong(ProcessorRunningInformation::getDuration)
                .sum();
    }

    public int getStatus() {
        //当存在处理器节点是运行中的，则当前状态也为运行中
        if (this.runningInformationMap
                .values()
                .stream().anyMatch(v -> v == null || v.isRunning())) {
            return 0;
        }

        //当处理器节点全部不是运行中，说明是终态了。若全部成功则返回成功，存在一个失败则返回失败状态
        boolean allFinished = this.runningInformationMap
                .values()
                .stream()
                .filter(v -> v != null && !v.isRunning())
                .map(ProcessorRunningInformation::getStatus)
                .allMatch(v -> v == 1);

        return allFinished ? 1 : 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("instanceId:%s\nrunningTime:%d\n", instanceId, getRunningTime()));

        this.runningInformationMap.forEach((k, v) -> {
            sb.append(String.format("processorName:%s status:%d duration:%d\n", k, v.getStatus(), v.getDuration()));
        });

        return sb.toString();
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void addHeader(String key, Object value) {
        this.headers.put(key, value);
    }

    public Object getHeader(String key) {
        return this.headers.get(key);
    }

    public void removeHeader(String key) {
        this.headers.remove(key);
    }


}

