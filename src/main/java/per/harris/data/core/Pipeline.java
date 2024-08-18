package per.harris.data.core;

import per.harris.data.core.pojo.BaseDataModel;
import per.harris.data.core.processor.DataProcessor;

import java.util.Optional;

public interface Pipeline<T, U extends BaseDataModel, R> {

    Optional<R> inPipeline(T input);

    void addProcessor(DataProcessor<U, U> processor);

    default String getName() {
        return this.getClass().getName();
    }
}
