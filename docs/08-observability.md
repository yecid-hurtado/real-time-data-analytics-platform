# 8) Observability

## 👀 Purpose
Ensure visibility into metrics, logs, and traces.

---

## ✨ Features
- Metrics with Micrometer → Prometheus.
- Dashboards in Grafana for throughput, latency, errors.
- Structured JSON logging.
- (Optional) Tracing with OpenTelemetry.

---

## 🔧 Implementation Notes
- Metrics: count of events per stage, processing latencies, error rates.
- Dashboards: topic throughput, alerts triggered, DLQ counts.
- Logs: include `service`, `traceId`, `eventId`.

---

## ✅ Verification Checklist
- [ ] Prometheus scrapes all services.
- [ ] Grafana dashboards display key metrics.
- [ ] Logs structured and parseable.
