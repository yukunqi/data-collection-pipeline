package per.harris.data.core.processor;

import per.harris.data.core.PipelineContext;
import per.harris.data.core.monitor.ProcessorRunningInformation;

public class BaseMonitorDataProcessor<T, R> {

    private final DataProcessor<T, R> dataProcessor;
    protected final ThreadLocal<PipelineContext> runningContextThreadLocal = new ThreadLocal<>();

    public BaseMonitorDataProcessor(DataProcessor<T, R> dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    public R processWithinPipeline(PipelineContext pipelineContext, T t) {

        runningContextThreadLocal.set(pipelineContext);

        ProcessorRunningInformation runningInformation = new ProcessorRunningInformation(this.dataProcessor.getName(), pipelineContext.getInstanceId());

        try {
            R r = this.dataProcessor.processData(t);
            runningInformation.processFinished();
            return r;
        } catch (Exception e) {
            runningInformation.processException();
            throw e;
        } finally {
            pipelineContext.addRunningInformation(runningInformation);
            runningContextThreadLocal.remove();
        }
    }

}
