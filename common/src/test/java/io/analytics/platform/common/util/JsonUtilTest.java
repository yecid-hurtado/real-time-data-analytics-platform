package io.analytics.platform.common.util;

import static org.junit.jupiter.api.Assertions.*;

import io.analytics.platform.common.dto.AlertEvent;
import io.analytics.platform.common.dto.ProcessedEvent;
import io.analytics.platform.common.dto.RawEvent;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link JsonUtil} class.
 *
 * <p>These tests verify the JSON serialization and deserialization functionality for various DTOs
 * in the system.
 *
 * @see JsonUtil
 */
class JsonUtilTest {

  /**
   * Tests that a {@link RawEvent} can be serialized to JSON and deserialized back to an equivalent
   * object.
   */
  @Test
  void testSerializeAndDeserializeRawEvent() {
    // Given
    RawEvent event = new RawEvent("evt-1", Instant.now(), 123.45, Map.of("source", "unit-test"));

    // When
    String json = JsonUtil.toJson(event);

    // Then
    assertNotNull(json, "JSON string should not be null");
    assertTrue(json.contains("evt-1"), "JSON should contain the event ID");

    // When (deserialize)
    RawEvent result = JsonUtil.fromJson(json, RawEvent.class);

    // Then
    assertAll(
        () -> assertEquals("evt-1", result.id(), "ID should match"),
        () -> assertEquals(123.45, result.value(), 0.001, "Value should match"),
        () -> assertEquals("unit-test", result.tags().get("source"), "Tag should match"));
  }

  /**
   * Tests that a {@link ProcessedEvent} can be serialized to JSON and deserialized back to an
   * equivalent object with all fields preserved.
   */
  @Test
  void testSerializeAndDeserializeProcessedEvent() {
    // Given
    ProcessedEvent event =
        new ProcessedEvent(
            "proc-1",
            Instant.now(),
            42.5,
            Map.of("source", "unit-test"),
            Map.of("version", "1.0"),
            "test-service");

    // When
    String json = JsonUtil.toJson(event);

    // Then
    assertNotNull(json, "JSON string should not be null");
    assertTrue(json.contains("proc-1"), "JSON should contain the processed event ID");

    // When (deserialize)
    ProcessedEvent result = JsonUtil.fromJson(json, ProcessedEvent.class);

    // Then
    assertAll(
        () -> assertEquals("proc-1", result.id(), "ID should match"),
        () ->
            assertEquals(42.5, result.valueTransformed(), 0.001, "Transformed value should match"),
        () -> assertEquals("unit-test", result.tags().get("source"), "Tag should match"),
        () -> assertEquals("1.0", result.meta().get("version"), "Meta version should match"),
        () -> assertEquals("test-service", result.source(), "Source should match"));
  }

  /**
   * Tests that an {@link AlertEvent} can be serialized to JSON and deserialized back to an
   * equivalent object.
   */
  @Test
  void testSerializeAndDeserializeAlertEvent() {
    // Given
    AlertEvent event = new AlertEvent("rule-123", "Threshold exceeded", "HIGH");

    // When
    String json = JsonUtil.toJson(event);

    // Then
    assertNotNull(json, "JSON string should not be null");
    assertTrue(json.contains("Threshold exceeded"), "JSON should contain the alert message");

    // When (deserialize)
    AlertEvent result = JsonUtil.fromJson(json, AlertEvent.class);

    // Then
    assertAll(
        () -> assertEquals("rule-123", result.ruleId(), "Rule ID should match"),
        () -> assertEquals("HIGH", result.severity(), "Severity should match"),
        () -> assertEquals("Threshold exceeded", result.message(), "Message should match"));
  }

  /** Tests that attempting to deserialize invalid JSON throws an appropriate exception. */
  @Test
  void testInvalidJsonThrowsException() {
    // Given
    String invalidJson = "{not-valid-json}";

    // When/Then
    RuntimeException ex =
        assertThrows(
            RuntimeException.class,
            () -> JsonUtil.fromJson(invalidJson, RawEvent.class),
            "Should throw exception for invalid JSON");

    assertTrue(
        ex.getMessage().contains("Failed to deserialize JSON"),
        "Exception message should indicate deserialization failure");
  }
}
