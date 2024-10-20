package per.harris.data.core.monitor;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorRunningInformationTest {

    /**
     * 测试场景：当调用processFinished方法时，状态应该从0（running）变为1（finished），并且endTime应该被设置。
     **/
    @Test
    public void testProcessFinished_StatusChangeAndEndTimeSet() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "12345");
        assertTrue(info.isRunning());
        assertEquals(0, info.getStatus());
        assertNull(info.getEndTime());

        info.processFinished();

        assertEquals(1, info.getStatus());
        assertNotNull(info.getEndTime());
    }

    /**
     * 测试场景：当调用processFinished方法后，getDuration方法应该返回一个正的时间差值。
     **/
    @Test
    public void testProcessFinished_DurationCalculation() throws InterruptedException {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "12345");

        Thread.sleep(1000L);

        info.processFinished();

        assertTrue(info.getDuration() > 0);
    }

    /**
     * 测试场景：当调用processFinished方法后，再次调用processFinished方法，状态和endTime不应该被再次改变。
     **/
    @Test
    public void testProcessFinished_Idempotent() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "12345");
        info.processFinished();
        Instant firstEndTime = info.getEndTime();
        int firstStatus = info.getStatus();

        info.processFinished();

        assertEquals(firstStatus, info.getStatus());
        assertEquals(firstEndTime, info.getEndTime());
    }

    /**
     * 测试场景：当调用processFinished方法后，状态应该不再是running。
     **/
    @Test
    public void testProcessFinished_NotRunningAfterFinished() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "12345");
        info.processFinished();

        assertFalse(info.isRunning());
    }
}