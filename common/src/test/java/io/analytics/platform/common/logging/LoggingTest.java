package io.analytics.platform.common.logging;

import static org.junit.jupiter.api.Assertions.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.analytics.platform.common.dto.RawEvent;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the {@link Logging} utility class.
 *
 * <p>These tests verify the behavior of all logging methods in the Logging utility, including
 * message formatting, log levels, and markers.
 *
 * @see Logging
 */
class LoggingTest {

  private Logger logger;
  private ListAppender<ILoggingEvent> listAppender;

  /**
   * Sets up the test environment before each test method. Configures a logger with an in-memory
   * appender for verification.
   */
  @BeforeEach
  void setup() {
    logger = (Logger) LoggerFactory.getLogger(LoggingTest.class);
    logger.detachAndStopAllAppenders();

    listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);
  }

  /**
   * Tests that {@link Logging#getLogger(Class)} properly initializes a logger and logs the
   * initialization message.
   */
  @Test
  void testGetLoggerInitializes() {
    Logger log = (Logger) Logging.getLogger(LoggingTest.class);
    assertNotNull(log, "Logger should not be null");
    assertTrue(
        listAppender.list.stream()
            .anyMatch(e -> e.getFormattedMessage().contains("Logger initialized")),
        "Logger initialization message should be logged");
  }

  /**
   * Tests that {@link Logging#info(Logger, String, Object...)} correctly logs an INFO level message
   * with the SERVICE marker.
   */
  @Test
  void testInfoLog() {
    Logging.info(logger, "Hello {}", "World");
    ILoggingEvent event = listAppender.list.get(0);
    assertEquals(
        "Hello World", event.getFormattedMessage(), "Message should be properly formatted");
    assertEquals(Level.INFO, event.getLevel(), "Log level should be INFO");
    assertEquals("SERVICE", event.getMarkerList().get(0).getName(), "Should use SERVICE marker");
  }

  /**
   * Tests that {@link Logging#error(Logger, String, Throwable)} correctly logs an ERROR level
   * message with the SERVICE marker and exception.
   */
  @Test
  void testErrorLog() {
    Exception ex = new RuntimeException("boom");
    Logging.error(logger, "Something failed", ex);
    ILoggingEvent event = listAppender.list.get(0);
    assertEquals("Something failed", event.getFormattedMessage(), "Error message should be logged");
    assertEquals(Level.ERROR, event.getLevel(), "Log level should be ERROR");
    assertEquals("SERVICE", event.getMarkerList().get(0).getName(), "Should use SERVICE marker");
    assertNotNull(event.getThrowableProxy(), "Exception should be included in the log");
    assertTrue(
        event.getThrowableProxy().getMessage().contains("boom"),
        "Exception message should be included");
  }

  /**
   * Tests that {@link Logging#security(Logger, String, Object...)} correctly logs a WARN level
   * message with the SECURITY marker.
   */
  @Test
  void testSecurityLog() {
    Logging.security(logger, "User {} not authorized", "alice");
    ILoggingEvent event = listAppender.list.get(0);
    assertTrue(
        event.getFormattedMessage().contains("[SECURITY] User alice not authorized"),
        "Security message should be properly formatted");
    assertEquals(Level.WARN, event.getLevel(), "Security logs should be at WARN level");
    assertEquals("SECURITY", event.getMarkerList().get(0).getName(), "Should use SECURITY marker");
  }

  /**
   * Tests that {@link Logging#debug(Logger, String, Object...)} only logs when debug level is
   * enabled.
   */
  @Test
  void testDebugLogOnlyFiresWhenEnabled() {
    // Test debug logging when enabled
    logger.setLevel(Level.DEBUG);
    Logging.debug(logger, "Debug {}", "msg");
    assertEquals(
        "Debug msg",
        listAppender.list.get(0).getFormattedMessage(),
        "Debug message should be logged when debug is enabled");

    // Test debug logging when disabled
    logger.setLevel(Level.INFO);
    listAppender.list.clear();
    Logging.debug(logger, "Should not appear {}", "X");
    assertTrue(
        listAppender.list.isEmpty(), "No debug messages should be logged when debug is disabled");
  }

  /**
   * Tests that {@link Logging#time(Logger, String, Runnable)} correctly measures and logs the
   * execution time of a runnable.
   *
   * @throws InterruptedException if the sleep is interrupted
   */
  @Test
  void testTimeLogsDuration() throws InterruptedException {
    Logging.time(
        logger,
        "TestAction",
        () -> {
          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
          }
        });
    ILoggingEvent event = listAppender.list.get(0);
    assertTrue(
        event.getFormattedMessage().startsWith("TestAction took"),
        "Should log the action with execution time");
    assertEquals(
        "PERF", event.getMarkerList().get(0).getName(), "Should use PERF marker for timing logs");
    assertEquals(Level.INFO, event.getLevel(), "Timing logs should be at INFO level");
  }

  /** Tests that DTOs are properly converted to string when logged. */
  @Test
  void testLogDtoToStringOutput() {
    RawEvent event =
        new RawEvent(
            "evt-123", Instant.parse("2025-09-12T12:00:00Z"), 42.5, Map.of("source", "unit-test"));
    Logging.info(logger, "Logging DTO: {}", event);

    ILoggingEvent logEvent = listAppender.list.get(0);
    String message = logEvent.getFormattedMessage();

    assertTrue(message.contains("evt-123"));
    assertTrue(message.contains("42.5"));
    assertTrue(message.contains("unit-test"));
  }
}
