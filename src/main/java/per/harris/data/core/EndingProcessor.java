package per.harris.data.core;

import per.harris.data.pojo.UniformDataModel;

public interface EndingProcessor<U extends UniformDataModel,R> extends DataProcessor<PipelineContext<U>,R>{

    public R processData(PipelineContext<U> input);
}
