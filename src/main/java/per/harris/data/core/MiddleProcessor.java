package per.harris.data.core;

import per.harris.data.pojo.UniformDataModel;

public interface MiddleProcessor<T extends UniformDataModel, R extends UniformDataModel> extends DataProcessor<T, R> {
}
