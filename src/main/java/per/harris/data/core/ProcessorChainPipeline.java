package per.harris.data.core;

import per.harris.data.core.exception.ProcessorExecutedException;
import per.harris.data.core.monitor.NodeStatusCollector;
import per.harris.data.pojo.UniformDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ProcessorChainPipeline<T, U extends UniformDataModel, R> implements Pipeline<T, U, R> {

    private AbstractStartingProcessor<T, U> startingProcessor;
    private AbstractEndingProcessor<U, R> endingProcessor;
    private final List<AbstractMiddleProcessor<U, U>> processorList;
    private NodeStatusCollector nodeStatusCollector;

    public ProcessorChainPipeline() {
        this.processorList = new ArrayList<>();
    }

    public void setNodeStatusCollector(NodeStatusCollector nodeStatusCollector) {
        this.nodeStatusCollector = nodeStatusCollector;
    }

    @Override
    public R inPipeline(T input) {

        DataProcessor<?, ?> currentProcessor = null;
        String instanceId = this.idGenerate();
        PipelineContext startPipelineContext = new PipelineContext(instanceId);

        this.register(startPipelineContext);

        try {

            currentProcessor = this.startingProcessor;

            U u = this.startingProcessor.processWithinPipeline(startPipelineContext, input);

            for (AbstractMiddleProcessor<U, U> processor : this.processorList) {
                currentProcessor = processor;
                u = processor.processWithinPipeline(startPipelineContext, u);
            }

            currentProcessor = this.endingProcessor;
            return this.endingProcessor.processWithinPipeline(startPipelineContext, u);

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

    public void addStartingProcessor(AbstractStartingProcessor<T, U> startingProcessor) {
        this.startingProcessor = startingProcessor;
    }

    public void addEndingProcessor(AbstractEndingProcessor<U, R> endingProcessor) {
        this.endingProcessor = endingProcessor;
    }

    @Override
    public void addProcessor(AbstractMiddleProcessor<U, U> processor) {
        this.processorList.add(processor);
    }


}
