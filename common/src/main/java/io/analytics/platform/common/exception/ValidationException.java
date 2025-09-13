package io.analytics.platform.common.exception;

/**
 * Exception thrown when validation of a domain object fails.
 *
 * <p>Used across DTOs (e.g., {@code RawEvent}, {@code ProcessedEvent}, {@code AlertEvent}) to
 * indicate that one or more constraints were violated.
 */
public final class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new validation exception with the specified detail message.
   *
   * @param message the detail message
   */
  public ValidationException(String message) {
    super(message);
  }

  /**
   * Constructs a new validation exception with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new validation exception with the specified cause.
   *
   * @param cause the cause of the exception
   */
  public ValidationException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new validation exception with full control over suppression and stack trace
   * writability.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   * @param enableSuppression whether suppression is enabled or disabled
   * @param writableStackTrace whether the stack trace should be writable
   */
  protected ValidationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
