# 1) Environment and Repository Setup

## 🛠 Prerequisites
- Java 21
- Gradle 8+
- Docker & Docker Compose
- (Optional) Make for scripting tasks

---

## 📂 Repository Structure
```
analytics-platform/
├─ README.md
├─ docker-compose.yml
├─ infra/                   # docker/prometheus/grafana configs
├─ common/                  # shared DTOs, utilities
├─ ingestion-service/       # REST -> Kafka (producer)
├─ processor-service/       # Kafka -> business transforms
├─ alert-service/           # rules -> notifications
├─ sink-service/            # persistence (Postgres/Elastic)
└─ gateway/                 # public API & health
```

*Explanation:* Each folder represents a bounded microservice with its own `build.gradle`.

---

## ⚙️ Gradle Setup
- Root `settings.gradle` includes all subprojects.
- Root `build.gradle` configures:
  - Java 21 toolchain
  - Common dependency versions
  - Quality plugins (Spotless, Checkstyle)
  - Test framework dependencies

---

## ✅ Verification Checklist
- [ ] `./gradlew build` compiles successfully
- [ ] `docker-compose up -d` starts Kafka, PostgreSQL, Elasticsearch, Prometheus, and Grafana
- [ ] Repo structure matches intended design
