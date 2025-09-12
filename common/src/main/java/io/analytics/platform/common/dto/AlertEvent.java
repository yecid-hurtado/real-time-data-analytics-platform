package io.analytics.platform.common.dto;

public class AlertEvent {
    private String id;
    private String ruleId;
    private String severity;
    private String message;
    private String eventId;

    public AlertEvent() {}
    public AlertEvent(String id, String ruleId, String severity, String message, String eventId) {
        this.id = id;
        this.ruleId = ruleId;
        this.severity = severity;
        this.message = message;
        this.eventId = eventId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRuleId() { return ruleId; }
    public void setRuleId(String ruleId) { this.ruleId = ruleId; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
}
