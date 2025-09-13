package io.analytics.platform.common.dto;

import static org.junit.jupiter.api.Assertions.*;

import io.analytics.platform.common.dto.AlertEvent.Severity;
import io.analytics.platform.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AlertEvent} class.
 *
 * <p>Verifies the behavior of alert event creation, validation, and severity handling. Tests
 * include valid and invalid scenarios to ensure proper error handling and enum conversion
 * functionality.
 */
class AlertEventTest {

  /**
   * Tests that a valid alert event can be created and validated successfully. Verifies that all
   * fields are set correctly and no validation exceptions are thrown.
   */
  @Test
  void validAlertEvent_shouldPassValidation() {
    AlertEvent alert = AlertEvent.of("rule-1", "Threshold exceeded", "HIGH");
    assertEquals("rule-1", alert.ruleId());
    assertEquals(Severity.HIGH, alert.getSeverityEnum());
    assertDoesNotThrow(alert::validate);
  }

  /**
   * Tests that an invalid severity level triggers a validation exception. Verifies the exception
   * message contains details about the invalid severity.
   */
  @Test
  void invalidSeverity_shouldThrowValidationException() {
    ValidationException ex =
        assertThrows(
            ValidationException.class, () -> AlertEvent.of("rule-1", "Bad severity", "INVALID"));
    assertTrue(ex.getMessage().contains("Invalid severity level"));
  }

  /**
   * Tests that a blank alert message triggers a validation exception. Verifies the exception
   * message indicates the message cannot be blank.
   */
  @Test
  void blankMessage_shouldThrowValidationException() {
    ValidationException ex =
        assertThrows(ValidationException.class, () -> AlertEvent.of("rule-1", "   ", "LOW"));
    assertTrue(ex.getMessage().contains("Alert message cannot be blank"));
  }

  /**
   * Tests that severity levels are properly normalized when using the enum factory method. Verifies
   * both the string and enum representations of the severity are set correctly.
   */
  @Test
  void enumFactory_shouldNormalizeSeverity() {
    AlertEvent alert = AlertEvent.of("rule-1", "All good", Severity.CRITICAL);
    assertEquals("CRITICAL", alert.severity());
    assertEquals(Severity.CRITICAL, alert.getSeverityEnum());
  }
}
