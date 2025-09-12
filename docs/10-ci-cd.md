# 10) CI/CD with GitHub Actions

## 🔄 Purpose
Automate build, test, and artifact publishing.

---

## ✨ Features
- `build.yml`: checkout → setup JDK 21 → cache Gradle → build/test → publish reports.
- Optional `it.yml`: run integration tests with Testcontainers.
- Publish Docker images tagged `:sha` and `:latest`.

---

## ✅ Verification Checklist
- [ ] CI badge in README.
- [ ] Build/test pipeline passes.
- [ ] Artifacts available (JARs, Docker images).
