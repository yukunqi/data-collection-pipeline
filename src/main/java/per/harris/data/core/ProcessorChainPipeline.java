package per.harris.data.core;

import per.harris.data.core.exception.ProcessorExecutedException;
import per.harris.data.core.monitor.NodeStatusCollector;
import per.harris.data.core.pojo.BaseDataModel;
import per.harris.data.core.processor.*;

import java.util.*;

public class ProcessorChainPipeline<T, U extends BaseDataModel, R> implements Pipeline<T, U, R> {

    private AbstractInputDataProcessor<T, U> startingProcessor;
    private AbstractDataOutputProcessor<U, R> endingProcessor;
    private final List<DataProcessor<U, U>> processorList;
    private NodeStatusCollector nodeStatusCollector;

    public ProcessorChainPipeline() {
        this.processorList = new ArrayList<>();
    }

    public void setNodeStatusCollector(NodeStatusCollector nodeStatusCollector) {
        this.nodeStatusCollector = nodeStatusCollector;
    }

    @Override
    public Optional<R> inPipeline(T input) {

        DataProcessor<?, ?> currentProcessor = null;
        String instanceId = this.idGenerate();
        PipelineContext startPipelineContext = new PipelineContext(instanceId);

        this.register(startPipelineContext);

        try {

            currentProcessor = this.startingProcessor;

            U u = new BaseMonitorDataProcessor<>(startingProcessor).processWithinPipeline(startPipelineContext, input);

            for (DataProcessor<U, U> processor : this.processorList) {
                currentProcessor = processor;
                u = new BaseMonitorDataProcessor<>(processor).processWithinPipeline(startPipelineContext, u);

                if (processor instanceof DataFilterProcessor && u == null) {
                    return Optional.empty();
                }
            }

            currentProcessor = this.endingProcessor;
            R r = new BaseMonitorDataProcessor<>(this.endingProcessor).processWithinPipeline(startPipelineContext, u);
            return Optional.ofNullable(r);
        } catch (Exception e) {
            throw new ProcessorExecutedException(e, currentProcessor.getName(), this.getName());
        } finally {
            if (nodeStatusCollector != null) {
                nodeStatusCollector.collect(startPipelineContext);
            }
        }

    }

    private void register(PipelineContext startPipelineContext) {
        startPipelineContext.registerProcessorName(this.startingProcessor.getName());
        startPipelineContext.registerProcessorName(Collections.unmodifiableList(this.processorList));
        startPipelineContext.registerProcessorName(this.endingProcessor.getName());
    }

    private String idGenerate() {
        return String.format("%s-%s", this.getName(), UUID.randomUUID());
    }

    public void addStartingProcessor(AbstractInputDataProcessor<T, U> startingProcessor) {
        this.startingProcessor = startingProcessor;
    }

    public void addEndingProcessor(AbstractDataOutputProcessor<U, R> endingProcessor) {
        this.endingProcessor = endingProcessor;
    }

    @Override
    public void addProcessor(DataProcessor<U, U> processor) {
        this.processorList.add(processor);
    }


}
