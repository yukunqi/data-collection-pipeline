package per.harris.data.core.processor;

import per.harris.data.core.pojo.BaseDataModel;

public abstract class AbstractDataOutputProcessor<U extends BaseDataModel, R> implements DataOutputProcessor<U, R> {
    @Override
    public R processData(U input) {
        R r = transformToOutput(input);
        return r;
    }

    abstract public R transformToOutput(U input);
}
