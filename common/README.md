# Common Module

## 📖 Overview

The `common` module provides **shared components** for the Real-Time Data Analytics Platform.

## 🏗️ Dependencies

- Java 17+
- Spring Boot 3.x
- Lombok
- Jackson (for JSON processing)
- SLF4J (for logging)
- JUnit 5 (for testing)
- AssertJ (for test assertions)

## 📂 Structure

``` plaintext
common/
├── src/main/java/io/analytics/platform/common/
│   ├── constants/   # Application constants
│   ├── dto/         # Data Transfer Objects
│   ├── exception/   # Custom exceptions
│   ├── logging/     # Logging utilities
│   └── util/        # Utility classes
└── src/test/        # Test classes
```

## 🚀 Key Components

### DTOs

- `RawEvent`: Represents incoming raw data
- `ProcessedEvent`: Contains processed/transformed data
- `AlertEvent`: Used for system alerts and notifications

### Utilities

- `JsonUtil`: JSON serialization/deserialization
- `Logging`: Centralized logging with SLF4J

## 🛠️ Usage Examples

### Using JsonUtil

```java
// Convert object to JSON string
String json = JsonUtil.toJson(myObject);

// Parse JSON to object
MyClass obj = JsonUtil.fromJson(jsonString, MyClass.class);
```

### Using Logging

```java
import static io.analytics.platform.common.logging.Logging.*;

// Basic logging
logInfo("Processing started");
logError("An error occurred", exception);

// With performance timing
try (var timer = startTimer("Operation")) {
    // Your code here
}
```

## 🧪 Testing

Run all tests:

```bash
./gradlew :common:test
```

Run specific test class:

```bash
./gradlew :common:test --tests "io.analytics.platform.common.util.JsonUtilTest"
```

## 📦 Building

Build the module:

```bash
./gradlew :common:build
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```bash
./gradlew :common:clean :common:build
```

Artifacts will be generated under:

```text
common/build/libs/common-0.0.1-SNAPSHOT.jar
```

---

## ✅ Features

- Immutable DTOs using modern **Java records** (Java 21+).
- JSON serialization with Jackson (`JsonUtil`).
- Logging with SLF4J markers (`SERVICE`, `SECURITY`, `PERF`).
- Unit tests for DTOs and logging utilities.

---

## 🔜 Next Steps

- Expand DTOs with validation annotations.
- Provide common exception handling utilities.
- Extend logging for distributed tracing (e.g., OpenTelemetry integration).
