# 3) Ingestion Service

## 📥 Purpose
Accept raw events from external clients via REST API and publish them into Kafka.

---

## ✨ Features
- `POST /produce` to accept single or batch `RawEvent`.
- Input validation with Bean Validation (Hibernate Validator).
- Idempotency by generating `id` if missing.

---

## 🔧 Implementation Notes
- Use `KafkaTemplate` or `ReactiveKafkaProducerTemplate`.
- Partition key = event `id`.
- Emit metrics: received, published, failed.

---

## ✅ Verification Checklist
- [ ] `POST /produce` publishes events to `ingestion.raw.v1`.
- [ ] Prometheus metrics exposed for ingestion counts.
- [ ] Integration test with Testcontainers Kafka passes.
