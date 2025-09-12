PROJECT_NAME=analytics-platform

# -----------------------------
# Infra management
# -----------------------------

up:
	@echo "Starting infrastructure..."
	docker compose up -d

down:
	@echo "Stopping infrastructure..."
	docker compose down

restart: down up

logs:
	@echo "Showing logs (Ctrl+C to exit)..."
	docker compose logs -f

ps:
	@echo "Active containers:"
	docker ps --filter "name=$(PROJECT_NAME)" || true

clean:
	@echo "Removing containers, volumes, and networks..."
	docker compose down -v --remove-orphans

# -----------------------------
# Database helpers
# -----------------------------

db-shell:
	@echo "Connecting to PostgreSQL shell..."
	docker exec -it postgres psql -U analytics -d analyticsdb

db-tables:
	@echo "Listing tables in analyticsdb..."
	docker exec -it postgres psql -U analytics -d analyticsdb -c "\dt"

# -----------------------------
# Kafka helpers
# -----------------------------

kafka-topics:
	@echo "Listing Kafka topics..."
	docker exec -it kafka kafka-topics.sh --list --bootstrap-server localhost:9092

# -----------------------------
# Elasticsearch helpers
# -----------------------------

es-health:
	@echo "Checking Elasticsearch health..."
	curl -s http://localhost:9200/_cluster/health?pretty

# -----------------------------
# Spring Boot services
# -----------------------------

ingestion:
	@echo "Running ingestion-service..."
	./gradlew :ingestion-service:bootRun

processor:
	@echo "Running processor-service..."
	./gradlew :processor-service:bootRun

alert:
	@echo "Running alert-service..."
	./gradlew :alert-service:bootRun

sink:
	@echo "Running sink-service..."
	./gradlew :sink-service:bootRun

gateway:
	@echo "Running gateway..."
	./gradlew :gateway:bootRun

# -----------------------------
# Code quality (Spotless)
# -----------------------------

lint:
	@echo "Checking code formatting..."
	./gradlew spotlessCheck

format:
	@echo "Auto-formatting code..."
	./gradlew spotlessApply

build:
	@echo "Building all modules with tests..."
	./gradlew clean build
