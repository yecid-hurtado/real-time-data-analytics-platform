# 6) Sink Service

## 🗄 Purpose
Persist processed events to PostgreSQL and Elasticsearch.

---

## ✨ Features
- PostgreSQL schema for structured queries.
- Elasticsearch index for fast searches.
- Retry and error handling with DLQ.

---

## 🔧 Implementation Notes
### PostgreSQL Schema
```sql
CREATE TABLE processed_events (
  id VARCHAR PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  value_transformed NUMERIC,
  tags JSONB,
  meta JSONB,
  source TEXT
);
CREATE INDEX idx_events_ts ON processed_events(ts);
CREATE INDEX idx_events_tags ON processed_events USING gin(tags);
```

### Elasticsearch Mapping
```json
{
  "mappings": {
    "properties": {
      "id": { "type": "keyword" },
      "ts": { "type": "date" },
      "valueTransformed": { "type": "float" },
      "tags": { "type": "keyword" },
      "source": { "type": "keyword" }
    }
  }
}
```

---

## ✅ Verification Checklist
- [ ] Events persisted in Postgres.
- [ ] Events indexed in Elasticsearch.
- [ ] Search queries return expected results.
