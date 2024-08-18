package per.harris.data.core.processor;

import per.harris.data.core.pojo.BaseDataModel;

public interface DataInputProcessor<T, U extends BaseDataModel> extends DataProcessor<T, U> {

    @Override
    U processData(T input);
}
