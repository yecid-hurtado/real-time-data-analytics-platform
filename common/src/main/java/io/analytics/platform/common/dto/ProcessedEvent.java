package io.analytics.platform.common.dto;

import java.time.Instant;
import java.util.Map;

public class ProcessedEvent {
    private String id;
    private Instant ts;
    private Double valueTransformed;
    private Map<String, Object> tags;
    private Map<String, Object> meta;

    public ProcessedEvent() {}
    public ProcessedEvent(String id, Instant ts, Double valueTransformed,
                          Map<String, Object> tags, Map<String, Object> meta) {
        this.id = id;
        this.ts = ts;
        this.valueTransformed = valueTransformed;
        this.tags = tags;
        this.meta = meta;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Instant getTs() { return ts; }
    public void setTs(Instant ts) { this.ts = ts; }

    public Double getValueTransformed() { return valueTransformed; }
    public void setValueTransformed(Double valueTransformed) { this.valueTransformed = valueTransformed; }

    public Map<String, Object> getTags() { return tags; }
    public void setTags(Map<String, Object> tags) { this.tags = tags; }

    public Map<String, Object> getMeta() { return meta; }
    public void setMeta(Map<String, Object> meta) { this.meta = meta; }
}
