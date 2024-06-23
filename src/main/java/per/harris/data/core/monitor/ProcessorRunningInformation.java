package per.harris.data.core.monitor;

import java.time.Instant;

public class ProcessorRunningInformation {

    //0-running 1-finished 2-exception
    private int status;
    private final Instant startTime;
    private Instant endTime;
    private final String processorName;
    private final String instanceId;

    public ProcessorRunningInformation(String processorName, String instanceId) {
        this.processorName = processorName;
        this.instanceId = instanceId;
        this.startTime = Instant.now();
        this.status = 0;
    }

    public void processFinished() {
        this.status = 1;
        this.endTime = Instant.now();
    }

    public void processException() {
        this.status = 2;
        this.endTime = Instant.now();
    }

    public boolean isRunning() {
        return status == 0;
    }

    public int getStatus() {
        return status;
    }

    public long getDuration() {
        return endTime.toEpochMilli() - startTime.toEpochMilli();
    }

    public String getProcessorName() {
        return processorName;
    }

    public String getInstanceId() {
        return instanceId;
    }
}
