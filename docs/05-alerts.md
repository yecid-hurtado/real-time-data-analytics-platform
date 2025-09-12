# 5) Alert Service

## 🚨 Purpose
Consume processed events, apply rules, and generate alerts.

---

## ✨ Features
- Rule model: `{ ruleId, description, predicate, severity }`.
- Notifier strategy (Console, Slack webhook).
- Publish `AlertEvent` to `alerts.triggered.v1`.

---

## 🔧 Implementation Notes
- Store rules in memory or Postgres.
- Apply rules on each processed event.
- Metrics: number of rules evaluated, alerts triggered.

---

## ✅ Verification Checklist
- [ ] Processed events trigger alerts correctly.
- [ ] Alerts published to `alerts.triggered.v1`.
- [ ] Notifier invoked (Console/Slack).
