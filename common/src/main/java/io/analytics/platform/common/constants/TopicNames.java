package io.analytics.platform.common.constants;

/**
 * Contains constants representing the names of Kafka topics used across the analytics platform.
 * These topic names follow a consistent naming convention: {domain}.{purpose}.v{version}
 */
public final class TopicNames {

  /** Topic for raw events ingested into the system. Format: {domain}.raw.v{version} */
  public static final String RAW_EVENTS = "ingestion.raw.v1";

  /**
   * Topic for events that have been processed by the analytics pipeline. Format:
   * {domain}.processed.v{version}
   */
  public static final String PROCESSED_EVENTS = "processing.processed.v1";

  /**
   * Topic for alert notifications that have been triggered. Format: {domain}.triggered.v{version}
   */
  public static final String ALERTS = "alerts.triggered.v1";

  /**
   * Dead Letter Queue topic for messages that could not be processed successfully. Format:
   * {purpose}.v{version}
   */
  public static final String DLQ = "dlq.v1";

  private TopicNames() {
    // Prevent instantiation - this is a utility class
  }
}
