CREATE TABLE IF NOT EXISTS processed_events (
    id VARCHAR PRIMARY KEY,
    ts TIMESTAMP NOT NULL,
    value_transformed NUMERIC,
    tags JSONB,
    meta JSONB,
    source TEXT
);

CREATE INDEX IF NOT EXISTS idx_events_ts ON processed_events(ts);
CREATE INDEX IF NOT EXISTS idx_events_tags ON processed_events USING gin(tags);
