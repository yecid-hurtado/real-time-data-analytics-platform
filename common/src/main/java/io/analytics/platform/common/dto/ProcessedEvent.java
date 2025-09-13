package io.analytics.platform.common.dto;

import io.analytics.platform.common.exception.ValidationException;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an event after it has been processed and transformed by the analytics pipeline. This
 * is the enriched version of a {@link RawEvent} with additional processing information.
 *
 * @param id Unique identifier of the processed event, must not be blank
 * @param ts Timestamp when the event was originally created, must not be in the future
 * @param valueTransformed The transformed numeric value, must be finite and within valid range
 * @param tags Metadata tags associated with the event, never null
 * @param meta Processing metadata, never null
 * @param source Identifier of the source system/pipeline, must not be blank
 */
public record ProcessedEvent(
    @NotBlank(message = "Processed event ID cannot be blank")
        @Size(max = 255, message = "Processed event ID cannot exceed 255 characters")
        String id,
    @NotNull(message = "Event timestamp cannot be null")
        @PastOrPresent(message = "Event timestamp cannot be in the future")
        Instant ts,
    @NotNull(message = "Transformed value cannot be null")
        @DecimalMin(value = "-999999999.999999", message = "Transformed value is too small")
        @DecimalMax(value = "999999999.999999", message = "Transformed value is too large")
        @Digits(integer = 9, fraction = 6, message = "Transformed value has invalid format")
        Double valueTransformed,
    @NotNull(message = "Tags map cannot be null")
        Map<@NotBlank(message = "Tag key cannot be blank") String, Object> tags,
    @NotNull(message = "Metadata map cannot be null") Map<String, Object> meta,
    @NotBlank(message = "Source identifier cannot be blank")
        @Size(max = 100, message = "Source identifier cannot exceed 100 characters")
        String source) {

  /**
   * Validates the ProcessedEvent object and throws a ValidationException if any constraints are
   * violated.
   */
  public void validate() {
    try {
      if (id == null || id.trim().isEmpty()) {
        throw new ValidationException("Processed event ID cannot be blank");
      }
      if (id.length() > 255) {
        throw new ValidationException("Processed event ID cannot exceed 255 characters");
      }

      if (ts == null) {
        throw new ValidationException("Event timestamp cannot be blank");
      }
      if (ts.isAfter(Instant.now())) {
        throw new ValidationException("Event timestamp cannot be in the future");
      }

      if (valueTransformed == null
          || Double.isNaN(valueTransformed)
          || Double.isInfinite(valueTransformed)) {
        throw new ValidationException("Transformed value cannot be blank or invalid");
      }

      if (tags == null) {
        throw new ValidationException("Tags map cannot be blank");
      }
      tags.keySet()
          .forEach(
              key -> {
                if (key == null || key.trim().isEmpty()) {
                  throw new ValidationException("Tag key cannot be blank");
                }
              });

      if (meta == null) {
        throw new ValidationException("Metadata map cannot be blank");
      }

      if (source == null || source.trim().isEmpty()) {
        throw new ValidationException("Source identifier cannot be blank");
      }
      if (source.length() > 100) {
        throw new ValidationException("Source identifier cannot exceed 100 characters");
      }

    } catch (NullPointerException e) {
      throw new ValidationException(e.getMessage(), e);
    }
  }

  /** Factory method with validation. */
  public static ProcessedEvent of(
      String id,
      Instant ts,
      Double valueTransformed,
      Map<String, Object> tags,
      Map<String, Object> meta,
      String source) {
    ProcessedEvent event =
        new ProcessedEvent(
            id,
            ts,
            valueTransformed,
            tags != null ? tags : Collections.emptyMap(),
            meta != null ? meta : Collections.emptyMap(),
            source);
    event.validate();
    return event;
  }

  /** Creates a ProcessedEvent from a RawEvent with additional processing info. */
  public static ProcessedEvent fromRawEvent(
      RawEvent rawEvent, Double valueTransformed, Map<String, Object> meta, String source) {
    Objects.requireNonNull(rawEvent, "Raw event cannot be null");
    return of(rawEvent.id(), rawEvent.ts(), valueTransformed, rawEvent.tags(), meta, source);
  }
}
