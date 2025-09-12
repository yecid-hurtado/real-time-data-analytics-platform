# 0) Scope, Assumptions, and Principles

## ✅ Scope (What’s included)
- Ingestion → Processing → Alerts → Sinks (PostgreSQL/Elasticsearch) → API Gateway + Metrics.
- A complete end-to-end slice demonstrating event-driven microservices in Java.

*Explanation:* Covers all major stages of a real-time pipeline without overextending.

---

## ❌ Out of Scope (Deferred for later iterations)
- Rich UI (React/Angular) — Swagger UI and Grafana screenshots only.
- Kubernetes deployment — MVP uses Docker Compose for orchestration.
- Full RBAC/OAuth2 — replaced with simple API keys or headers.

*Explanation:* Keeps MVP small, achievable, and demo-ready. Deferred items are excellent “future enhancements.”

---

## 🎯 Guiding Principles
- **Separation of Concerns:** Each microservice owns a single responsibility.
- **12-Factor Configurations:** Externalized configs via `.env` and environment variables.
- **Idempotency:** Reprocessed/replayed events should not cause duplicates.
- **Backpressure:** Kafka partitions + consumer tuning to handle load safely.
- **Observability First:** Logging, metrics, and tracing are mandatory, not optional.

*Explanation:* These principles make the project portfolio-ready and aligned with real-world enterprise practices.

---

## 📝 Verification Checklist
- [ ] E2E flow defined and stable
- [ ] No “critical” dependencies outside Docker Compose
