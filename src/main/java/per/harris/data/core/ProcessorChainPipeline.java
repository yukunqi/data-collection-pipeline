package per.harris.data.core;

import per.harris.data.pojo.UniformDataModel;

import java.util.ArrayList;
import java.util.List;

public class ProcessorChainPipeline<T,U extends UniformDataModel,R> implements Pipeline<T,U,R> {

    private StartingProcessor<T,U> startingProcessor;
    private EndingProcessor<U,R> endingProcessor;
    private final List<MiddleProcessor<U,U> > processorList;

    public ProcessorChainPipeline(){
        this.processorList = new ArrayList<>();
    }

    @Override
    public R inPipeline(T input) {


        PipelineContext<U> uPipelineContext = this.startingProcessor.processData(input);


        for(MiddleProcessor<U,U> processor : this.processorList){
            uPipelineContext = processor.processData(uPipelineContext);
        }

        return this.endingProcessor.processData(uPipelineContext);
    }

    @Override
    public void addStartingProcessor(StartingProcessor<T, U> startingProcessor) {
        this.startingProcessor = startingProcessor;
    }

    @Override
    public void addEndingProcessor(EndingProcessor<U, R> endingProcessor) {
        this.endingProcessor = endingProcessor;
    }

    @Override
    public void addProcessor(MiddleProcessor<U, U> processor) {
        this.processorList.add(processor);
    }


}
