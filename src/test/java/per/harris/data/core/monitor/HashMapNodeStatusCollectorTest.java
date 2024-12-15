package per.harris.data.core.monitor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import per.harris.data.core.pipeline.PipelineContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapNodeStatusCollectorTest {
    // 使用反射获取私有字段 pipelineContextMap
    private Map<String, PipelineContext> getPipelineContextMap(HashMapNodeStatusCollector collector) {
        try {
            java.lang.reflect.Field field = HashMapNodeStatusCollector.class.getDeclaredField("pipelineContextMap");
            field.setAccessible(true);
            return (Map<String, PipelineContext>) field.get(collector);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 场景1：测试collect方法是否能够正确地将PipelineContext对象添加到pipelineContextMap中
     */
    @Test
    @DisplayName("测试collect方法是否能够正确地将PipelineContext对象添加到pipelineContextMap中")
    public void testCollectAddsToMap() {
        // Arrange
        HashMapNodeStatusCollector collector = new HashMapNodeStatusCollector();
        PipelineContext pipelineContext = new PipelineContext("instance1");

        // Act
        collector.collect(pipelineContext);

        // Assert
        Map<String, PipelineContext> pipelineContextMap = getPipelineContextMap(collector);
        assertNotNull(pipelineContextMap);
        assertEquals(1, pipelineContextMap.size());
        assertEquals(pipelineContext, pipelineContextMap.get("instance1"));
    }

    /**
     * 场景4：测试collect方法是否能够正确处理null值的PipelineContext对象
     */
    @Test
    @DisplayName("测试collect方法是否能够正确处理null值的PipelineContext对象")
    public void testCollectHandlesNullContext() {
        // Arrange
        HashMapNodeStatusCollector collector = new HashMapNodeStatusCollector();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            collector.collect(null);
        });
    }

    /**
     * 测试场景：验证collect方法是否正确地将PipelineContext添加到pipelineContextMap中
     */
    @Test
    @DisplayName("验证collect方法是否正确地将PipelineContext添加到pipelineContextMap中")
    public void testCollectMethod() {
        HashMapNodeStatusCollector collector = new HashMapNodeStatusCollector();
        PipelineContext context = new PipelineContext("testInstanceId");

        collector.collect(context);

        // 使用反射获取私有字段 pipelineContextMap
        Map<String, PipelineContext> pipelineContextMap = getPipelineContextMap(collector);
        assertTrue(pipelineContextMap.containsKey("testInstanceId"));
        assertEquals(context, pipelineContextMap.get("testInstanceId"));
    }

    /**
     * 场景2：测试collect方法是否能够正确处理多个PipelineContext对象的添加
     */
    @Test
    @DisplayName("测试collect方法是否能够正确处理多个PipelineContext对象的添加")
    public void testCollectMultipleContexts() {
        // Arrange
        HashMapNodeStatusCollector collector = new HashMapNodeStatusCollector();
        PipelineContext context1 = new PipelineContext("instance1");
        PipelineContext context2 = new PipelineContext("instance2");

        // Act
        collector.collect(context1);
        collector.collect(context2);

        // Assert
        Map<String, PipelineContext> pipelineContextMap = getPipelineContextMap(collector);
        assertNotNull(pipelineContextMap);
        assertEquals(2, pipelineContextMap.size());
        assertEquals(context1, pipelineContextMap.get("instance1"));
        assertEquals(context2, pipelineContextMap.get("instance2"));
    }

    /**
     * 测试场景：验证collect方法是否能够覆盖已有的PipelineContext
     */
    @Test
    @DisplayName("验证collect方法是否能够覆盖已有的PipelineContext")
    public void testCollectOverwriteExistingContext() {
        HashMapNodeStatusCollector collector = new HashMapNodeStatusCollector();
        PipelineContext context1 = new PipelineContext("instanceId");
        PipelineContext context2 = new PipelineContext("instanceId");

        collector.collect(context1);
        collector.collect(context2);

        // 使用反射获取私有字段 pipelineContextMap
        Map<String, PipelineContext> pipelineContextMap = getPipelineContextMap(collector);
        assertEquals(1, pipelineContextMap.size());
        assertEquals(context2, pipelineContextMap.get("instanceId"));
    }

    /**
     * 场景3：测试collect方法是否能够正确覆盖已存在的PipelineContext对象
     */
    @Test
    @DisplayName("测试collect方法是否能够正确覆盖已存在的PipelineContext对象")
    public void testCollectOverwritesExistingContext() {
        // Arrange
        HashMapNodeStatusCollector collector = new HashMapNodeStatusCollector();
        PipelineContext context1 = new PipelineContext("instance1");
        PipelineContext context2 = new PipelineContext("instance1");

        // Act
        collector.collect(context1);
        collector.collect(context2);

        // Assert
        Map<String, PipelineContext> pipelineContextMap = getPipelineContextMap(collector);
        assertNotNull(pipelineContextMap);
        assertEquals(1, pipelineContextMap.size());
        assertEquals(context2, pipelineContextMap.get("instance1"));
    }

    /**
     * 测试场景：验证HashMapNodeStatusCollector构造函数是否正确初始化了pipelineContextMap
     */
    @Test
    @DisplayName("验证HashMapNodeStatusCollector构造函数是否正确初始化了pipelineContextMap")
    public void testConstructorInitialization() {
        HashMapNodeStatusCollector collector = new HashMapNodeStatusCollector();
        // 使用反射获取私有字段 pipelineContextMap
        assertNotNull(getPipelineContextMap(collector));
        assertTrue(getPipelineContextMap(collector) instanceof ConcurrentHashMap);
    }

}
