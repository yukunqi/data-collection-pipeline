package per.harris.data.core;


import per.harris.data.core.monitor.ProcessorRunningInformation;

public abstract class AbstractDataProcessor<T, R> implements DataProcessor<T, R> {

    //todo 有状态变量 那么此时是可能存在线程安全的问题的
    protected final ThreadLocal<PipelineContext> runningContextThreadLocal = new ThreadLocal<>();

    protected R processWithinPipeline(PipelineContext pipelineContext, T t) {

        runningContextThreadLocal.set(pipelineContext);

        ProcessorRunningInformation runningInformation = new ProcessorRunningInformation(this.getName(), pipelineContext.getInstanceId());

        try {
            R r = this.processData(t);
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
