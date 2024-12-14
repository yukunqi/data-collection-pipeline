package per.harris.data.core.monitor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorRunningInformationTest {
    /**
     * 测试场景1：验证构造函数正确初始化ProcessorRunningInformation对象
     * 1. 验证processorName和instanceId是否正确设置
     * 2. 验证startTime是否被正确设置为当前时间
     * 3. 验证status是否被正确设置为0（running）
     **/
    @Test
    @DisplayName("验证构造函数正确初始化ProcessorRunningInformation对象")
    public void testConstructorInitialization() {
        String processorName = "TestProcessor";
        String instanceId = "TestInstance";
        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertEquals(processorName, info.getProcessorName(), "Processor name should be set correctly");
        assertEquals(instanceId, info.getInstanceId(), "Instance ID should be set correctly");
        assertEquals(0, info.getStatus(), "Status should be set to 0 (running)");
        assertTrue(info.getStartTime().isBefore(Instant.now()), "Start time should be set to current time");
    }

    /**
     * 测试场景5：验证getDuration方法在不同状态下的返回值
     * 1. 在初始状态下，getDuration方法应抛出NullPointerException
     * 2. 在调用processFinished方法后，getDuration方法应返回一个正数
     * 3. 在调用processException方法后，getDuration方法应返回一个正数
     **/
    @Test
    @DisplayName("验证getDuration方法在不同状态下的返回值")
    public void testGetDuration() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "TestInstance");

        assertThrows(NullPointerException.class, info::getDuration, "getDuration should throw NullPointerException when endTime is null");

        info.processFinished();
        assertTrue(info.getDuration() > 0, "getDuration should return a positive number after processFinished");

        info = new ProcessorRunningInformation("TestProcessor", "TestInstance");
        info.processException();
        assertTrue(info.getDuration() > 0, "getDuration should return a positive number after processException");
    }

    /**
     * 测试场景4：验证isRunning方法在不同状态下的返回值
     * 1. 在初始状态下，isRunning方法应返回true
     * 2. 在调用processFinished方法后，isRunning方法应返回false
     * 3. 在调用processException方法后，isRunning方法应返回false
     **/
    @Test
    @DisplayName("验证isRunning方法在不同状态下的返回值")
    public void testIsRunning() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "TestInstance");

        assertTrue(info.isRunning(), "isRunning should return true when status is 0 (running)");

        info.processFinished();
        assertFalse(info.isRunning(), "isRunning should return false when status is 1 (finished)");

        info = new ProcessorRunningInformation("TestProcessor", "TestInstance");
        info.processException();
        assertFalse(info.isRunning(), "isRunning should return false when status is 2 (exception)");
    }

    /**
     * 测试场景3：验证processException方法正确设置状态和结束时间
     * 1. 调用processException方法后，验证status是否被设置为2（exception）
     * 2. 验证endTime是否被正确设置为当前时间
     **/
    @Test
    @DisplayName("验证processException方法正确设置状态和结束时间")
    public void testProcessException() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "TestInstance");
        info.processException();

        assertEquals(2, info.getStatus(), "Status should be set to 2 (exception)");
        assertTrue(info.getEndTime().isBefore(Instant.now()), "End time should be set to current time");
    }

    /**
     * 测试场景2：验证processFinished方法正确设置状态和结束时间
     * 1. 调用processFinished方法后，验证status是否被设置为1（finished）
     * 2. 验证endTime是否被正确设置为当前时间
     **/
    @Test
    @DisplayName("验证processFinished方法正确设置状态和结束时间")
    public void testProcessFinished() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "TestInstance");
        info.processFinished();

        assertEquals(1, info.getStatus(), "Status should be set to 1 (finished)");
        assertTrue(info.getEndTime().isBefore(Instant.now()), "End time should be set to current time");
    }

}
