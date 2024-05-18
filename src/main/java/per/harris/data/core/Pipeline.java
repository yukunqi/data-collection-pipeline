package per.harris.data.core;

import per.harris.data.pojo.UniformDataModel;

public interface Pipeline<T,U extends UniformDataModel,R> {

    R inPipeline(T input);

    void addStartingProcessor(StartingProcessor<T,U> startingProcessor);

    void addEndingProcessor(EndingProcessor<U,R> endingProcessor);

    void addProcessor(MiddleProcessor<U,U> processor);
}
