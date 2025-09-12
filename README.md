# Real-Time Data Analytics Platform (Java MVP)

## 📖 Overview
This project is a **real-time data analytics platform** built with Java and Spring Boot.  
It demonstrates senior-level architecture skills by combining **event-driven microservices**, **stream processing**, **alerting**, and **observability** into a complete end-to-end system.

---

## 📂 Documentation
The project documentation is organized into modular sections:

1. [Scope, Assumptions, and Principles](docs/00-scope.md)
2. [Environment and Repository Setup](docs/01-setup.md)
3. [System Architecture](docs/02-architecture.md)
4. [Ingestion Service](docs/03-ingestion.md)
5. [Processor Service](docs/04-processing.md)
6. [Alert Service](docs/05-alerts.md)
7. [Sink Service](docs/06-sinks.md)
8. [Gateway Service](docs/07-gateway.md)
9. [Observability](docs/08-observability.md)
10. [Testing Strategy](docs/09-testing.md)
11. [CI/CD](docs/10-ci-cd.md)
12. [Future Extensions](docs/11-future.md)

---

## 🚀 Quick Start
1. Clone this repository
```bash
git clone https://github.com/your-username/real-time-data-analytics-platform.git
cd real-time-data-analytics-platform
```

2. Start infrastructure with Docker Compose
```bash
docker-compose up -d
```

3. Build and run services
```bash
./gradlew bootRun
```

---

## 🛠 Tech Stack
- **Java 21 + Spring Boot**
- **Kafka** for messaging
- **PostgreSQL** for structured storage
- **Elasticsearch** for search
- **Prometheus + Grafana** for observability
- **Docker Compose** for local orchestration
