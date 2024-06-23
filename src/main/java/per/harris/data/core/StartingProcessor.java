package per.harris.data.core;

import per.harris.data.pojo.UniformDataModel;

public interface StartingProcessor<T, U extends UniformDataModel> extends DataProcessor<T, U> {

    U processData(T input);
}
