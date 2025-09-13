package io.analytics.platform.common.dto;

import io.analytics.platform.common.exception.ValidationException;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

/**
 * Represents an incoming raw event from the ingestion layer. This is the initial representation of
 * an event before any processing is applied.
 *
 * <p>The event must have a non-null ID, a timestamp that's not in the future, and a valid numeric
 * value. Tags are optional but cannot be null.
 *
 * @param id Unique identifier for the event, must not be blank and have a maximum of 255 characters
 * @param ts Timestamp when the event occurred, must not be null and cannot be in the future
 * @param value Numeric measurement or value associated with the event, must not be null
 * @param tags Key-value pairs of metadata associated with the event, cannot be null but can be
 *     empty
 * @throws ValidationException if any validation constraints are violated
 */
public record RawEvent(
    @NotBlank(message = "Event ID cannot be blank")
        @Size(max = 255, message = "Event ID cannot exceed 255 characters")
        String id,
    @NotNull(message = "Event timestamp cannot be null")
        @PastOrPresent(message = "Event timestamp cannot be in the future")
        Instant ts,
    @NotNull(message = "Event value cannot be null")
        @DecimalMin(value = "-999999999.999999", message = "Event value is too small")
        @DecimalMax(value = "999999999.999999", message = "Event value is too large")
        @Digits(integer = 9, fraction = 6, message = "Event value has invalid format")
        Double value,
    @NotNull(message = "Tags map cannot be null")
        Map<@NotBlank(message = "Tag key cannot be blank") String, Object> tags) {
  /**
   * Validates the RawEvent object and throws a ValidationException if any constraints are violated.
   *
   * @throws ValidationException if the event is not valid
   */
  public void validate() {
    try {
      if (id == null || id.trim().isEmpty()) {
        throw new ValidationException("Event ID cannot be blank");
      }
      if (id.length() > 255) {
        throw new ValidationException("Event ID cannot exceed 255 characters");
      }

      if (ts == null) {
        throw new ValidationException("Event timestamp cannot be blank");
      }
      if (ts.isAfter(Instant.now())) {
        throw new ValidationException("Event timestamp cannot be in the future");
      }

      if (value == null
          || Double.isNaN(value)
          || Double.isInfinite(value)
          || value > 999999999.999999
          || value < -999999999.999999) {
        throw new ValidationException("Event value is invalid or out of range");
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

    } catch (NullPointerException e) {
      throw new ValidationException(e.getMessage(), e);
    }
  }

  /**
   * Creates a new RawEvent with the specified parameters and validates it.
   *
   * @param id the event ID
   * @param ts the event timestamp
   * @param value the event value
   * @param tags the event tags
   * @return a new validated RawEvent
   * @throws ValidationException if the event is not valid
   */
  public static RawEvent of(String id, Instant ts, Double value, Map<String, Object> tags) {
    RawEvent event = new RawEvent(id, ts, value, tags != null ? tags : Collections.emptyMap());
    event.validate();
    return event;
  }
}
