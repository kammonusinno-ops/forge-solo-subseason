# Forge Solo Subseason

A safe, server-authoritative Minecraft survival RPG/economy MVP based on the supplied **Forge Magic** design bible.

> **Current status:** M1 foundation is implemented and tested. Gameplay systems begin with M2 after the ledger boundary is added.

## Source-of-truth documents

- [`docs/SPEC.md`](docs/SPEC.md) — copied Design Bible (owner-owned)
- [`docs/KICKOFF_REPORT.md`](docs/KICKOFF_REPORT.md) — M0 architecture, versions, risks, and questions
- [`docs/M1_REPORT.md`](docs/M1_REPORT.md) — foundation milestone report
- [`docs/STATE.md`](docs/STATE.md) — current milestone state and next action
- [`docs/CONTRACTS.md`](docs/CONTRACTS.md) — Contracts v0
- [`docs/DECISIONS.md`](docs/DECISIONS.md) — defaults and decisions
- [`docs/RISKS.md`](docs/RISKS.md) — known technical and safety risks

## M1 foundation

- Java 21 and Gradle wrapper 8.10.2
- Platform-free core API contracts
- YAML config loading and validation
- Visible missing-language-key fallback
- Async profile repository interface and deterministic in-memory implementation
- Initial PostgreSQL profile schema migration
- JUnit 5 tests and GitHub Actions CI

## Planned build

The supplied specification describes a **Paper plugin suite**, not a Forge mod. This repository therefore treats “Forge” as the project/brand name and follows the specified Paper runtime unless the owner changes that decision. Paper APIs are intentionally not used until the exact Minecraft/Paper version is pinned and verified.

## Test

```bash
./gradlew clean test
```
