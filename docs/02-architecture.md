# 2) System Architecture

## 🏗 High-Level Design
- Event-driven microservices.
- Services communicate asynchronously via Kafka topics.
- Persistence handled by PostgreSQL and Elasticsearch.

---

## 🔧 Services Overview
- **Ingestion Service:** REST API → produces raw events into Kafka.
- **Processor Service:** Consumes raw events → applies transformations → publishes processed events.
- **Alert Service:** Consumes processed events → applies rules → triggers alerts/notifications.
- **Sink Service:** Persists processed events to PostgreSQL and Elasticsearch.
- **Gateway:** Provides REST API to query events, alerts, and metrics.

---

## 📊 Architecture Diagram
```mermaid
flowchart LR
    subgraph Ingestion
        A[REST API] -->|RawEvent| K1((Kafka))
    end

    subgraph Processor
        K1 --> P[Processor Service] --> K2((Kafka))
    end

    subgraph Alert
        K2 --> AL[Alert Service] --> K3((Kafka))
    end

    subgraph Sinks
        K2 --> S[Sink Service] --> DB[(Postgres)]
        S --> ES[(Elasticsearch)]
    end

    subgraph Gateway
        G[API Gateway] --> DB
        G --> ES
        G --> K3
    end
```
