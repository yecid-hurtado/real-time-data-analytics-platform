package io.analytics.platform.common.dto;

import java.time.Instant;
import java.util.Map;

public class RawEvent {
    private String id;
    private Instant ts;
    private Double value;
    private Map<String, Object> tags;

    // Constructors
    public RawEvent() {}
    public RawEvent(String id, Instant ts, Double value, Map<String, Object> tags) {
        this.id = id;
        this.ts = ts;
        this.value = value;
        this.tags = tags;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Instant getTs() { return ts; }
    public void setTs(Instant ts) { this.ts = ts; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public Map<String, Object> getTags() { return tags; }
    public void setTags(Map<String, Object> tags) { this.tags = tags; }
}
