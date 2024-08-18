package per.harris.data.core.processor;

import per.harris.data.core.pojo.BaseDataModel;

public abstract class AbstractFilterDataProcessor<U extends BaseDataModel> implements DataFilterProcessor<U> {

    @Override
    public U processData(U input) {
        return filterData(input) ? null : input;
    }

    abstract public boolean filterData(U input);
}
