PROJECT_NAME=analytics-platform

# -----------------------------
# Infra management
# -----------------------------

up: ## Start infrastructure
	@echo "Starting infrastructure..."
	docker compose up -d

down: ## Stop infrastructure
	@echo "Stopping infrastructure..."
	docker compose down

restart: down up ## Restart infrastructure

logs: ## Show container logs
	@echo "Showing logs (Ctrl+C to exit)..."
	docker compose logs -f

ps: ## List active containers
	@echo "Active containers:"
	docker ps --filter "name=$(PROJECT_NAME)" || true

clean: ## Remove containers, volumes, and networks
	@echo "Removing containers, volumes, and networks..."
	docker compose down -v --remove-orphans

# -----------------------------
# Database helpers
# -----------------------------

db-shell: ## Connect to PostgreSQL shell
	@echo "Connecting to PostgreSQL shell..."
	docker exec -it postgres psql -U analytics -d analyticsdb

db-tables: ## List tables in analyticsdb
	@echo "Listing tables in analyticsdb..."
	docker exec -it postgres psql -U analytics -d analyticsdb -c "\dt"

# -----------------------------
# Kafka helpers
# -----------------------------

kafka-topics: ## List Kafka topics
	@echo "Listing Kafka topics..."
	docker exec -it kafka kafka-topics.sh --list --bootstrap-server localhost:9092

# -----------------------------
# Elasticsearch helpers
# -----------------------------

es-health: ## Check Elasticsearch health
	@echo "Checking Elasticsearch health..."
	curl -s http://localhost:9200/_cluster/health?pretty

# -----------------------------
# Spring Boot services
# -----------------------------

ingestion: ## Run ingestion-service
	@echo "Running ingestion-service..."
	./gradlew :ingestion-service:bootRun

processor: ## Run processor-service
	@echo "Running processor-service..."
	./gradlew :processor-service:bootRun

alert: ## Run alert-service
	@echo "Running alert-service..."
	./gradlew :alert-service:bootRun

sink: ## Run sink-service
	@echo "Running sink-service..."
	./gradlew :sink-service:bootRun

gateway: ## Run gateway
	@echo "Running gateway..."
	./gradlew :gateway:bootRun

# -----------------------------
# Code quality (Spotless)
# -----------------------------

lint: ## Check code formatting
	@echo "Checking code formatting..."
	./gradlew spotlessCheck

format: ## Auto-format code
	@echo "Auto-formatting code..."
	./gradlew spotlessApply

build: ## Build all modules with tests
	@echo "Building all modules with tests..."
	./gradlew clean build

# -----------------------------
# Tests
# -----------------------------

test-common: ## Run unit tests only for the common module
	@echo "Running unit tests for common module..."
	./gradlew clean :common:test

test-all: ## Run all tests across all modules
	@echo "Running all tests..."
	./gradlew clean test

# -----------------------------
# Help
# -----------------------------

help: ## List all available make commands
	@echo "Available commands:"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "  %-20s %s\n", $$1, $$2}'
