package io.analytics.platform.common.dto;

import static org.junit.jupiter.api.Assertions.*;

import io.analytics.platform.common.exception.ValidationException;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RawEvent} class.
 *
 * <p>Tests the validation rules and behavior of raw event data, including:
 *
 * <ul>
 *   <li>Required field validation
 *   <li>Timestamp validation
 *   <li>Tag validation rules
 * </ul>
 */
class RawEventTest {

  /**
   * Tests that a properly constructed raw event passes validation. Verifies all fields are set
   * correctly and no validation exceptions are thrown.
   */
  @Test
  void validRawEvent_shouldPassValidation() {
    RawEvent event = RawEvent.of("id-1", Instant.now(), 42.5, Map.of("source", "test"));
    assertEquals("id-1", event.id());
    assertDoesNotThrow(event::validate);
  }

  /**
   * Tests that a null event ID triggers a validation exception. Verifies the exception message
   * indicates the ID cannot be blank.
   */
  @Test
  void nullId_shouldThrowValidationException() {
    ValidationException ex =
        assertThrows(
            ValidationException.class, () -> RawEvent.of(null, Instant.now(), 42.5, Map.of()));
    assertTrue(ex.getMessage().contains("Event ID cannot be blank"));
  }

  /**
   * Tests that a future timestamp triggers a validation exception. Verifies the exception message
   * indicates timestamps cannot be in the future.
   */
  @Test
  void futureTimestamp_shouldThrowValidationException() {
    Instant future = Instant.now().plusSeconds(3600);
    ValidationException ex =
        assertThrows(ValidationException.class, () -> RawEvent.of("id", future, 42.5, Map.of()));
    assertTrue(ex.getMessage().contains("cannot be in the future"));
  }

  /**
   * Tests that a blank tag key triggers a validation exception. Verifies the exception message
   * indicates tag keys cannot be blank.
   */
  @Test
  void blankTagKey_shouldThrowValidationException() {
    ValidationException ex =
        assertThrows(
            ValidationException.class,
            () -> RawEvent.of("id", Instant.now(), 42.5, Map.of("", "oops")));
    assertTrue(ex.getMessage().contains("Tag key cannot be blank"));
  }
}
