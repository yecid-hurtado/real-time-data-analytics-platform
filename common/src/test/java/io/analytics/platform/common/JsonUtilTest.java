package io.analytics.platform.common;

import io.analytics.platform.common.dto.RawEvent;
import io.analytics.platform.common.util.JsonUtil;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonUtilTest {

    @Test
    void testSerializationAndDeserialization() {
        RawEvent event = new RawEvent("1", Instant.now(), 42.0, Map.of("device", "A1"));

        String json = JsonUtil.toJson(event);
        RawEvent result = JsonUtil.fromJson(json, RawEvent.class);

        assertEquals(event.getId(), result.getId());
        assertEquals(event.getValue(), result.getValue());
        assertEquals(event.getTags().get("device"), result.getTags().get("device"));
    }
}
