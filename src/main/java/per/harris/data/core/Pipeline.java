package per.harris.data.core;

import per.harris.data.pojo.UniformDataModel;

public interface Pipeline<T,U extends UniformDataModel,R> {

    R inPipeline(T input);

    void addProcessor(AbstractMiddleProcessor<U, U> processor);

    default String getName() {
        return this.getClass().getName();
    }
}
