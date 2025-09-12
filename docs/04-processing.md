# 4) Processor Service

## ⚙️ Purpose
Consume raw events, apply transformations, and publish processed events.

---

## ✨ Features
- Simple transformation rules:
  - Filter: `value > threshold`.
  - Map: `value * 2`.
  - Enrich: add tags or metadata.

---

## 🔧 Implementation Notes
- Use Spring Kafka or Reactor Kafka.
- Retry on transient errors, DLQ on permanent errors.
- Emit metrics for throughput, latency, error rate.

---

## ✅ Verification Checklist
- [ ] Raw events consumed from `ingestion.raw.v1`.
- [ ] Processed events published to `processing.processed.v1`.
- [ ] DLQ populated on permanent failure.
