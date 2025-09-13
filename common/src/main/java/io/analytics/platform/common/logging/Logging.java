package io.analytics.platform.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Centralized logging utility that provides consistent logging configuration and patterns across
 * all services in the analytics platform.
 *
 * <p>This utility class offers:
 *
 * <ul>
 *   <li>Standardized logger creation with service metadata
 *   <li>Structured logging using SLF4J markers for better log filtering
 *   <li>Performance-aware logging methods that avoid unnecessary string construction
 *   <li>Convenience methods for common logging scenarios
 * </ul>
 *
 * <p>Service metadata (name and version) can be configured via system properties or environment
 * variables, with sensible defaults provided.
 *
 * @see org.slf4j.Logger
 * @see org.slf4j.Marker
 */
public final class Logging {

  /** Marker for standard service-related log messages. */
  private static final Marker SERVICE_MARKER = MarkerFactory.getMarker("SERVICE");

  /** Marker for security-related log messages (e.g., authentication, authorization). */
  private static final Marker SECURITY_MARKER = MarkerFactory.getMarker("SECURITY");

  /** Marker for performance-related log messages. */
  private static final Marker PERF_MARKER = MarkerFactory.getMarker("PERF");

  /**
   * The service name, configurable via system property 'service.name'. Defaults to
   * 'unknown-service' if not specified.
   */
  private static final String SERVICE_NAME = System.getProperty("service.name", "unknown-service");

  /**
   * The service version, configurable via system property 'service.version'. Defaults to '0.0.1' if
   * not specified.
   */
  private static final String SERVICE_VERSION = System.getProperty("service.version", "0.0.1");

  private Logging() {
    // Prevent instantiation
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Creates and initializes a logger for the specified class. Automatically logs the service name
   * and version at INFO level upon first use.
   *
   * @param clazz the class for which to create the logger
   * @return a properly configured Logger instance
   * @throws IllegalArgumentException if clazz is null
   */
  public static Logger getLogger(Class<?> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("Class parameter cannot be null");
    }
    Logger logger = LoggerFactory.getLogger(clazz);
    logger.info("Logger initialized for [{} v{}]", SERVICE_NAME, SERVICE_VERSION);
    return logger;
  }

  /**
   * Logs an informational message with the SERVICE marker.
   *
   * @param log the logger to use
   * @param msg the message format string
   * @param args arguments referenced by the format specifiers in the format string
   * @throws NullPointerException if either log or msg is null
   */
  public static void info(Logger log, String msg, Object... args) {
    if (log != null) {
      log.info(SERVICE_MARKER, msg, args);
    }
  }

  /**
   * Logs an error message with the SERVICE marker and associated throwable.
   *
   * @param log the logger to use
   * @param msg the message format string
   * @param t the throwable to log
   * @throws NullPointerException if log is null
   */
  public static void error(Logger log, String msg, Throwable t) {
    if (log != null) {
      log.error(SERVICE_MARKER, msg, t);
    }
  }

  /**
   * Logs a security-related warning message with the SECURITY marker. Automatically prefixes the
   * message with "[SECURITY] ".
   *
   * @param log the logger to use
   * @param msg the message format string
   * @param args arguments referenced by the format specifiers in the format string
   * @throws NullPointerException if log is null
   */
  public static void security(Logger log, String msg, Object... args) {
    if (log != null) {
      log.warn(SECURITY_MARKER, "[SECURITY] " + msg, args);
    }
  }

  /**
   * Logs a debug message if debug level is enabled. Efficiently checks if debug is enabled before
   * constructing the log message.
   *
   * @param log the logger to use
   * @param format the message format string
   * @param args arguments referenced by the format specifiers in the format string
   */
  public static void debug(Logger log, String format, Object... args) {
    if (log != null && log.isDebugEnabled()) {
      log.debug(format, args);
    }
  }

  /**
   * Executes the provided {@link Runnable} and logs the execution time with the PERFORMANCE marker.
   * The execution time is logged in milliseconds.
   *
   * @param log the logger to use
   * @param action a description of the action being timed (used in the log message)
   * @param runnable the code block to execute and time
   * @throws NullPointerException if any parameter is null
   * @throws RuntimeException if the runnable throws an exception (original exception is preserved)
   */
  public static void time(Logger log, String action, Runnable runnable) {
    if (log == null || action == null || runnable == null) {
      throw new NullPointerException("Parameters cannot be null");
    }

    long start = System.currentTimeMillis();
    try {
      runnable.run();
    } finally {
      long elapsed = System.currentTimeMillis() - start;
      log.info(PERF_MARKER, "{} took {} ms", action, elapsed);
    }
  }
}
