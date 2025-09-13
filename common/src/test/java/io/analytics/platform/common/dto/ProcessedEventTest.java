package io.analytics.platform.common.dto;

import static org.junit.jupiter.api.Assertions.*;

import io.analytics.platform.common.exception.ValidationException;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ProcessedEvent} class.
 *
 * <p>Validates the creation and transformation of processed events, including:
 *
 * <ul>
 *   <li>Direct instantiation with all required fields
 *   <li>Conversion from raw events
 *   <li>Input validation for required fields
 * </ul>
 */
class ProcessedEventTest {

  /**
   * Tests creation and validation of a processed event with all required fields. Verifies that a
   * properly constructed event passes validation and retains all field values.
   */
  @Test
  void validProcessedEvent_shouldPassValidation() {
    ProcessedEvent event =
        ProcessedEvent.of(
            "proc-1",
            Instant.now(),
            99.9,
            Map.of("source", "unit"),
            Map.of("meta", "ok"),
            "pipeline-1");
    assertEquals("proc-1", event.id());
    assertDoesNotThrow(event::validate);
  }

  /**
   * Tests the conversion from a raw event to a processed event. Verifies that the processed event
   * maintains the original ID and properly sets transformed values.
   */
  @Test
  void fromRawEvent_shouldWorkCorrectly() {
    RawEvent raw = RawEvent.of("raw-1", Instant.now(), 50.0, Map.of("tag", "x"));
    ProcessedEvent proc =
        ProcessedEvent.fromRawEvent(raw, 123.4, Map.of("stage", "processed"), "etl");
    assertEquals("raw-1", proc.id());
    assertEquals(123.4, proc.valueTransformed());
  }

  /**
   * Tests that a blank source identifier triggers a validation exception. Verifies the exception
   * message indicates the source cannot be blank.
   */
  @Test
  void blankSource_shouldThrowValidationException() {
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () -> ProcessedEvent.of("id", Instant.now(), 12.0, Map.of(), Map.of(), " "));
    assertTrue(ex.getMessage().contains("Source identifier cannot be blank"));
  }
}
