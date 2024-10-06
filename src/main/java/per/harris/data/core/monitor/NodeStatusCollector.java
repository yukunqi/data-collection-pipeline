package per.harris.data.core.monitor;

import per.harris.data.core.pipeline.PipelineContext;

public interface NodeStatusCollector {

    void collect(PipelineContext pipelineContext);
}
