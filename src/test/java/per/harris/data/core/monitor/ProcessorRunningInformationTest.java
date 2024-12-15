package per.harris.data.core.monitor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorRunningInformationTest {
    /**
     * 测试场景：验证构造函数正确初始化processorName和instanceId
     **/
    @Test
    @DisplayName("验证构造函数正确初始化processorName和instanceId")
    public void testConstructorInitialization() {
        String processorName = "TestProcessor";
        String instanceId = "TestInstanceId";

        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertEquals(processorName, info.getProcessorName(), "Processor name should be initialized correctly");
        assertEquals(instanceId, info.getInstanceId(), "Instance ID should be initialized correctly");
    }

    /**
     * 场景1: 测试正常完成情况下的持续时间计算
     * 预期结果: 持续时间应该大于0
     **/
    @Test
    @DisplayName("测试正常完成情况下的持续时间计算")
    public void testNormalCompletionDuration() throws InterruptedException {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "1");
        TimeUnit.MILLISECONDS.sleep(100); // 模拟处理时间
        info.processFinished();
        long duration = info.getDuration();
        assertTrue(duration > 0, "持续时间应该大于0");
    }

    /**
     * 场景2: 测试异常情况下的持续时间计算
     * 预期结果: 持续时间应该大于0
     **/
    @Test
    @DisplayName("测试异常情况下的持续时间计算")
    public void testExceptionDuration() throws InterruptedException {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "1");
        TimeUnit.MILLISECONDS.sleep(100); // 模拟处理时间
        info.processException();
        long duration = info.getDuration();
        assertTrue(duration > 0, "持续时间应该大于0");
    }

    /**
     * 场景3: 测试在未完成状态下调用getDuration方法
     * 预期结果: 应该抛出NullPointerException，因为endTime为null
     **/
    @Test
    @DisplayName("测试在未完成状态下调用getDuration方法")
    public void testDurationWithoutCompletion() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "1");
        assertThrows(NullPointerException.class, info::getDuration, "应该抛出NullPointerException");
    }

    /**
     * 场景3: 测试在调用processException方法后，getEndTime方法返回一个非null的Instant对象
     **/
    @Test
    @DisplayName("测试在调用processException方法后，getEndTime方法返回一个非null的Instant对象")
    public void testEndTimeAfterProcessException() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        info.processException();
        assertNotNull(info.getEndTime(), "End time should not be null after processException");
    }

    /**
     * 场景5: 测试在调用processException方法后，getEndTime方法返回的时间应该大于或等于startTime
     **/
    @Test
    @DisplayName("测试在调用processException方法后，getEndTime方法返回的时间应该大于或等于startTime")
    public void testEndTimeAfterProcessExceptionIsAfterStartTime() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        Instant startTime = info.getStartTime();
        info.processException();
        Instant endTime = info.getEndTime();
        assertTrue(endTime.isAfter(startTime) || endTime.equals(startTime), "End time should be after or equal to start time");
    }

    /**
     * 场景2: 测试在调用processFinished方法后，getEndTime方法返回一个非null的Instant对象
     **/
    @Test
    @DisplayName("测试在调用processFinished方法后，getEndTime方法返回一个非null的Instant对象")
    public void testEndTimeAfterProcessFinished() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        info.processFinished();
        assertNotNull(info.getEndTime(), "End time should not be null after processFinished");
    }

    /**
     * 场景4: 测试在调用processFinished方法后，getEndTime方法返回的时间应该大于或等于startTime
     **/
    @Test
    @DisplayName("测试在调用processFinished方法后，getEndTime方法返回的时间应该大于或等于startTime")
    public void testEndTimeAfterProcessFinishedIsAfterStartTime() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        Instant startTime = info.getStartTime();
        info.processFinished();
        Instant endTime = info.getEndTime();
        assertTrue(endTime.isAfter(startTime) || endTime.equals(startTime), "End time should be after or equal to start time");
    }

    /**
     * 测试场景：验证构造函数初始化后，endTime为null
     **/
    @Test
    @DisplayName("验证构造函数初始化后，endTime为null")
    public void testEndTimeIsNullAfterInitialization() {
        String processorName = "TestProcessor";
        String instanceId = "TestInstanceId";

        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);
        {
            assertNull(info.getEndTime(), "End time should be null after initialization");
        }
    }

    /**
     * 场景1: 测试在对象创建后，未调用processFinished或processException方法时，getEndTime方法返回null
     **/
    @Test
    @DisplayName("测试在对象创建后，未调用processFinished或processException方法时，getEndTime方法返回null")
    public void testEndTimeIsNullInitially() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        assertNull(info.getEndTime(), "End time should be null initially");
    }

    /**
     * 场景2: 测试getInstanceId方法返回的实例ID是否为空字符串
     **/
    @Test
    @DisplayName("测试getInstanceId方法返回的实例ID是否为空字符串")
    public void testGetInstanceId_EmptyString() {
        String processorName = "TestProcessor";
        String instanceId = "";
        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertEquals(instanceId, info.getInstanceId(), "getInstanceId方法返回的实例ID应为空字符串");
    }

    /**
     * 场景4: 测试getInstanceId方法返回的实例ID是否为非空且非null的字符串
     **/
    @Test
    @DisplayName("测试getInstanceId方法返回的实例ID是否为非空且非null的字符串")
    public void testGetInstanceId_NonEmptyString() {
        String processorName = "TestProcessor";
        String instanceId = "NonEmptyInstanceId";
        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertNotNull(info.getInstanceId(), "getInstanceId方法返回的实例ID不应为null");
        assertNotEquals("", info.getInstanceId(), "getInstanceId方法返回的实例ID不应为空字符串");
    }

    /**
     * 场景3: 测试getInstanceId方法返回的实例ID是否为null
     **/
    @Test
    @DisplayName("测试getInstanceId方法返回的实例ID是否为null")
    public void testGetInstanceId_Null() {
        String processorName = "TestProcessor";
        String instanceId = null;
        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertNull(info.getInstanceId(), "getInstanceId方法返回的实例ID应为null");
    }

    /**
     * 场景1: 测试getInstanceId方法返回的实例ID是否与构造函数中传入的值一致
     **/
    @Test
    @DisplayName("测试getInstanceId方法返回的实例ID是否与构造函数中传入的值一致")
    public void testGetInstanceId_SameAsConstructor() {
        String processorName = "TestProcessor";
        String instanceId = "TestInstanceId";
        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertEquals(instanceId, info.getInstanceId(), "getInstanceId方法返回的实例ID应与构造函数中传入的值一致");
    }

    /**
     * 场景1: 测试getProcessorName方法返回正确的处理器名称
     */
    @Test
    @DisplayName("测试getProcessorName方法返回正确的处理器名称")
    public void testGetProcessorName_ReturnsCorrectName() {
        String expectedProcessorName = "TestProcessor";
        ProcessorRunningInformation info = new ProcessorRunningInformation(expectedProcessorName, "instance1");
        assertEquals(expectedProcessorName, info.getProcessorName(), "处理器名称不匹配");
    }

    /**
     * 场景3: 测试getProcessorName方法在处理器名称包含特殊字符时返回正确的名称
     */
    @Test
    @DisplayName("测试getProcessorName方法在处理器名称包含特殊字符时返回正确的名称")
    public void testGetProcessorName_ReturnsCorrectNameWithSpecialCharacters() {
        String expectedProcessorName = "Test_Processor-123";
        ProcessorRunningInformation info = new ProcessorRunningInformation(expectedProcessorName, "instance1");
        assertEquals(expectedProcessorName, info.getProcessorName(), "处理器名称包含特殊字符时不匹配");
    }

    /**
     * 场景2: 测试getProcessorName方法在处理器名称为空时返回空字符串
     */
    @Test
    @DisplayName("测试getProcessorName方法在处理器名称为空时返回空字符串")
    public void testGetProcessorName_ReturnsEmptyStringForNullName() {
        String expectedProcessorName = "";
        ProcessorRunningInformation info = new ProcessorRunningInformation(null, "instance1");
        assertEquals(expectedProcessorName, info.getProcessorName(), "处理器名称为空时应返回空字符串");
    }

    /**
     * 场景3: 测试在不同对象之间getStartTime方法返回的时间是否不同
     */
    @Test
    @DisplayName("测试在不同对象之间getStartTime方法返回的时间是否不同")
    public void testGetStartTime_DifferentObjects() {
        ProcessorRunningInformation info1 = new ProcessorRunningInformation("Processor1", "Instance1");
        ProcessorRunningInformation info2 = new ProcessorRunningInformation("Processor2", "Instance2");
        Instant startTime1 = info1.getStartTime();
        Instant startTime2 = info2.getStartTime();
        assertNotEquals(startTime1, startTime2, "Start times of different objects should be different");
    }

    /**
     * 场景2: 测试在创建对象后立即调用getStartTime方法，时间差是否在合理范围内
     */
    @Test
    @DisplayName("测试在创建对象后立即调用getStartTime方法，时间差是否在合理范围内")
    public void testGetStartTime_ImmediateCall() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        Instant startTime = info.getStartTime();
        Instant now = Instant.now();
        long timeDifference = now.toEpochMilli() - startTime.toEpochMilli();
        assertTrue(timeDifference >= 0 && timeDifference < 1000, "Time difference should be within a reasonable range");
    }

    /**
     * 场景1: 测试getStartTime方法是否返回正确的初始化时间
     */
    @Test
    @DisplayName("测试getStartTime方法是否返回正确的初始化时间")
    public void testGetStartTime_InitialTime() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        Instant startTime = info.getStartTime();
        assertNotNull(startTime, "StartTime should not be null");
        assertTrue(startTime.isBefore(Instant.now()), "StartTime should be before the current time");
    }

    /**
     * 场景4: 测试在同一对象上多次调用getStartTime方法是否返回相同的时间
     */
    @Test
    @DisplayName("测试在同一对象上多次调用getStartTime方法是否返回相同的时间")
    public void testGetStartTime_MultipleCalls() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("Processor1", "Instance1");
        Instant startTime1 = info.getStartTime();
        Instant startTime2 = info.getStartTime();
        assertEquals(startTime1, startTime2, "Multiple calls to getStartTime should return the same time");
    }

    /**
     * 场景1: 测试初始状态为running
     **/
    @Test
    @DisplayName("测试初始状态为running")
    public void testInitialStatusIsRunning() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processor1", "instance1");
        assertEquals(0, info.getStatus(), "初始状态应为running");
    }

    /**
     * 测试场景：验证构造函数初始化后，isRunning方法返回true
     **/
    @Test
    @DisplayName("验证构造函数初始化后，isRunning方法返回true")
    public void testIsRunningAfterInitialization() {
        String processorName = "TestProcessor";
        String instanceId = "TestInstanceId";

        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertTrue(info.isRunning(), "isRunning should return true after initialization");
    }

    /**
     * 场景3: 测试当状态为2（exception）时，isRunning方法返回false
     **/
    @Test
    @DisplayName("测试当状态为2（exception）时，isRunning方法返回false")
    public void testIsRunningWhenStatusIsException() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processorName", "instanceId");
        info.processException();
        assertFalse(info.isRunning());
    }

    /**
     * 场景2: 测试当状态为1（finished）时，isRunning方法返回false
     **/
    @Test
    @DisplayName("测试当状态为1（finished）时，isRunning方法返回false")
    public void testIsRunningWhenStatusIsFinished() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processorName", "instanceId");
        info.processFinished();
        assertFalse(info.isRunning());
    }

    /**
     * 场景1: 测试当状态为0（running）时，isRunning方法返回true
     **/
    @Test
    @DisplayName("测试当状态为0（running）时，isRunning方法返回true")
    public void testIsRunningWhenStatusIsRunning() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processorName", "instanceId");
        assertTrue(info.isRunning());
    }

    /**
     * 场景2: 测试processException方法在多次调用时是否保持状态为2（异常状态）
     * 并且结束时间不会被多次更新。
     **/
    @Test
    @DisplayName("测试多次调用processException方法")
    public void testMultipleCallsToProcessException() {
        // Arrange
        ProcessorRunningInformation processor = new ProcessorRunningInformation("TestProcessor", "12345");
        processor.processException();
        Instant firstEndTime = processor.getEndTime();

        // Act
        processor.processException();

        // Assert
        assertEquals(2, processor.getStatus(), "状态应保持为2（异常状态）");
        assertEquals(firstEndTime, processor.getEndTime(), "结束时间不应被多次更新");
    }

    /**
     * 场景5: 测试多次调用processException后状态仍为exception
     **/
    @Test
    @DisplayName("测试多次调用processException后状态仍为exception")
    public void testMultipleProcessExceptionCalls() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processor1", "instance1");
        info.processException();
        info.processException();
        assertEquals(2, info.getStatus(), "多次调用processException后状态应仍为exception");
    }

    /**
     * 场景4: 测试多次调用processFinished后状态仍为finished
     **/
    @Test
    @DisplayName("测试多次调用processFinished后状态仍为finished")
    public void testMultipleProcessFinishedCalls() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processor1", "instance1");
        info.processFinished();
        info.processFinished();
        assertEquals(1, info.getStatus(), "多次调用processFinished后状态应仍为finished");
    }

    /**
     * 场景5: 测试在不同时间点完成和开始的情况
     * 预期结果: 持续时间应该大于0
     **/
    @Test
    @DisplayName("测试在不同时间点完成和开始的情况")
    public void testNonZeroDuration() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "1");
        info.processFinished();
        info.processFinished(); // 再次调用processFinished以确保endTime被更新
        long duration = info.getDuration();
        assertTrue(duration > 0, "持续时间应该大于0");
    }

    /**
     * 场景3: 测试processException方法在调用后，处理器是否仍然被认为是非运行状态。
     **/
    @Test
    @DisplayName("测试processException方法后处理器是否为非运行状态")
    public void testProcessExceptionSetsProcessorToNonRunning() {
        // Arrange
        ProcessorRunningInformation processor = new ProcessorRunningInformation("TestProcessor", "12345");

        // Act
        processor.processException();

        // Assert
        assertFalse(processor.isRunning(), "处理器应为非运行状态");
    }

    /**
     * 场景1: 测试processException方法是否正确地将状态设置为2（异常状态）
     * 并且正确地设置了结束时间。
     **/
    @Test
    @DisplayName("测试processException方法设置状态和结束时间")
    public void testProcessExceptionSetsStatusAndEndTime() {
        // Arrange
        ProcessorRunningInformation processor = new ProcessorRunningInformation("TestProcessor", "12345");
        Instant startTime = processor.getStartTime();

        // Act
        processor.processException();

        // Assert
        assertEquals(2, processor.getStatus(), "状态应设置为2（异常状态）");
        assertNotNull(processor.getEndTime(), "结束时间应被设置");
        assertTrue(processor.getEndTime().isAfter(startTime) || processor.getEndTime().equals(startTime), "结束时间应晚于或等于开始时间");
    }

    /**
     * 场景7: 测试在调用processException后再调用processFinished的状态
     **/
    @Test
    @DisplayName("测试在调用processException后再调用processFinished的状态")
    public void testProcessExceptionThenFinished() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processor1", "instance1");
        info.processException();
        info.processFinished();
        assertEquals(1, info.getStatus(), "在调用processException后再调用processFinished状态应为finished");
    }

    /**
     * 场景3: 测试processFinished方法在调用后，计算的持续时间是否为非负数。
     **/
    @Test
    @DisplayName("测试processFinished方法在调用后，计算的持续时间是否为非负数。")
    public void testProcessFinishedCalculatesNonNegativeDuration() {
        // Arrange
        ProcessorRunningInformation processor = new ProcessorRunningInformation("TestProcessor", "12345");

        // Act
        processor.processFinished();

        // Assert
        assertTrue(processor.getDuration() >= 0, "持续时间应为非负数");
    }

    /**
     * 场景1: 测试processFinished方法是否正确地将状态设置为1（finished），并且正确地设置了结束时间。
     **/
    @Test
    @DisplayName("测试processFinished方法是否正确地将状态设置为1（finished），并且正确地设置了结束时间。")
    public void testProcessFinishedSetsStatusAndEndTime() {
        // Arrange
        ProcessorRunningInformation processor = new ProcessorRunningInformation("TestProcessor", "12345");
        Instant startTime = processor.getStartTime();

        // Act
        processor.processFinished();

        // Assert
        assertEquals(1, processor.getStatus(), "状态应设置为1（finished）");
        assertNotNull(processor.getEndTime(), "结束时间应被设置");
        assertTrue(processor.getEndTime().isAfter(startTime) || processor.getEndTime().equals(startTime), "结束时间应晚于或等于开始时间");
    }

    /**
     * 场景2: 测试processFinished方法在调用后，处理器的状态是否不再是running。
     **/
    @Test
    @DisplayName("测试processFinished方法在调用后，处理器的状态是否不再是running。")
    public void testProcessFinishedSetsStatusToNotRunning() {
        // Arrange
        ProcessorRunningInformation processor = new ProcessorRunningInformation("TestProcessor", "12345");

        // Act
        processor.processFinished();

        // Assert
        assertFalse(processor.isRunning(), "处理器的状态应不再是running");
    }

    /**
     * 场景6: 测试在调用processFinished后再调用processException的状态
     **/
    @Test
    @DisplayName("测试在调用processFinished后再调用processException的状态")
    public void testProcessFinishedThenException() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processor1", "instance1");
        info.processFinished();
        info.processException();
        assertEquals(2, info.getStatus(), "在调用processFinished后再调用processException状态应为exception");
    }

    /**
     * 测试场景：验证构造函数正确初始化startTime并设置status为0
     **/
    @Test
    @DisplayName("验证构造函数正确初始化startTime并设置status为0")
    public void testStartTimeAndStatusInitialization() {
        String processorName = "TestProcessor";
        String instanceId = "TestInstanceId";

        ProcessorRunningInformation info = new ProcessorRunningInformation(processorName, instanceId);

        assertNotNull(info.getStartTime(), "Start time should be initialized");
        assertEquals(0, info.getStatus(), "Status should be initialized to 0 (running)");
    }

    /**
     * 场景3: 测试调用processException后状态为exception
     **/
    @Test
    @DisplayName("测试调用processException后状态为exception")
    public void testStatusAfterProcessException() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processor1", "instance1");
        info.processException();
        assertEquals(2, info.getStatus(), "调用processException后状态应为exception");
    }

    /**
     * 场景2: 测试调用processFinished后状态为finished
     **/
    @Test
    @DisplayName("测试调用processFinished后状态为finished")
    public void testStatusAfterProcessFinished() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("processor1", "instance1");
        info.processFinished();
        assertEquals(1, info.getStatus(), "调用processFinished后状态应为finished");
    }

    /**
     * 场景4: 测试在同一时间点完成和开始的情况
     * 预期结果: 持续时间应该为0
     **/
    @Test
    @DisplayName("测试在同一时间点完成和开始的情况")
    public void testZeroDuration() {
        ProcessorRunningInformation info = new ProcessorRunningInformation("TestProcessor", "1");
        info.processFinished();
        long duration = info.getDuration();
        assertEquals(0, duration, "持续时间应该为0");
    }

}
