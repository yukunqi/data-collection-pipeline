package per.harris.data.core.monitor;

import per.harris.data.core.PipelineContext;

public interface NodeStatusCollector {

    void collect(PipelineContext pipelineContext);
}
