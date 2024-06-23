package per.harris.data.core;

import per.harris.data.pojo.UniformDataModel;

public interface EndingProcessor<U extends UniformDataModel, R> extends DataProcessor<U, R> {

    R processData(U input);
}
