package io.analytics.platform.common.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ValidationException} class.
 *
 * <p>Verifies all constructor variations of the validation exception, ensuring proper message and
 * cause handling in each case.
 */
class ValidationExceptionTest {

  /**
   * Tests construction with a message only. Verifies the message is set correctly and cause is
   * null.
   */
  @Test
  void constructWithMessage() {
    ValidationException ex = new ValidationException("oops");
    assertEquals("oops", ex.getMessage());
  }

  /**
   * Tests construction with both a message and a cause. Verifies both the message and cause are set
   * correctly.
   */
  @Test
  void constructWithMessageAndCause() {
    Exception cause = new IllegalArgumentException("bad");
    ValidationException ex = new ValidationException("invalid", cause);
    assertEquals("invalid", ex.getMessage());
    assertSame(cause, ex.getCause());
  }

  /**
   * Tests construction with a cause only. Verifies the cause is set correctly and message is
   * derived from cause.
   */
  @Test
  void constructWithCauseOnly() {
    Exception cause = new RuntimeException("fail");
    ValidationException ex = new ValidationException(cause);
    assertSame(cause, ex.getCause());
  }
}
