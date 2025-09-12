# 7) Gateway Service

## 🌐 Purpose
Expose APIs for querying events, alerts, and system health.

---

## ✨ Features
- `GET /health` for service health.
- `GET /metrics` for Prometheus metrics.
- `GET /events` (query Postgres).
- `GET /search` (query Elasticsearch).
- `GET /alerts` (optional, if persisted).

---

## 🔧 Implementation Notes
- Use Spring Boot WebFlux.
- Expose OpenAPI/Swagger documentation.
- Simple security with API keys.

---

## ✅ Verification Checklist
- [ ] All endpoints respond with real data.
- [ ] Swagger UI available.
- [ ] Security layer applied (API key).
