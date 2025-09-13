package io.analytics.platform.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utility class providing JSON serialization and deserialization functionality using Jackson's
 * {@link ObjectMapper}.
 *
 * <p>This class is thread-safe and uses a single shared {@link ObjectMapper} instance that
 * automatically discovers and registers all available modules.
 *
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public final class JsonUtil {

  /** Shared ObjectMapper instance with auto-registered modules. */
  private static final ObjectMapper MAPPER =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .findAndRegisterModules();

  private JsonUtil() {
    // Prevent instantiation
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Serializes an object to its JSON representation.
   *
   * @param obj the object to serialize (may be null)
   * @return JSON string representation of the object
   * @throws RuntimeException if serialization fails
   */
  public static String toJson(Object obj) {
    try {
      return MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize object to JSON", e);
    }
  }

  /**
   * Deserializes a JSON string to an object of the specified type.
   *
   * @param <T> the type of the desired object
   * @param json the JSON string to deserialize (may be null or empty)
   * @param clazz the class of T
   * @return an object of type T
   * @throws IllegalArgumentException if json is null or empty
   * @throws RuntimeException if deserialization fails
   */
  public static <T> T fromJson(String json, Class<T> clazz) {
    if (json == null || json.trim().isEmpty()) {
      throw new IllegalArgumentException("JSON string cannot be null or empty");
    }
    try {
      return MAPPER.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to deserialize JSON", e);
    }
  }
}
