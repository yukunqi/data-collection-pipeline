package per.harris.data.core.exception;

public class ProcessorExecutedException extends RuntimeException {

    private String processorName;
    private String pipelineName;

    public ProcessorExecutedException(String processorName, String pipelineName) {
        this.processorName = processorName;
        this.pipelineName = pipelineName;
    }

    public ProcessorExecutedException(String message, String processorName, String pipelineName) {
        super(message);
        this.processorName = processorName;
        this.pipelineName = pipelineName;
    }

    public ProcessorExecutedException(String message, Throwable cause, String processorName, String pipelineName) {
        super(message, cause);
        this.processorName = processorName;
        this.pipelineName = pipelineName;
    }

    public ProcessorExecutedException(Throwable cause, String processorName, String pipelineName) {
        super(cause);
        this.processorName = processorName;
        this.pipelineName = pipelineName;
    }

    @Override
    public String toString() {
        String s = String.format("ProcessorExecutedException pipeline[%s] processorName[%s] : ", pipelineName, processorName);
        return s + super.toString();
    }
}
