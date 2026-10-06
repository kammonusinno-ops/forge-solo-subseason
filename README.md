# Forge Solo Subseason

A safe, server-authoritative Minecraft survival RPG/economy MVP based on the supplied **Forge Magic** design bible.

> **Current status:** M6 foundation is implemented and tested.

## Verified server baseline

The latest compatible stable baseline checked on 2026-10-06 is **Minecraft/Paper 26.2, Paper build 129**, with Java 21. Geyser’s official supported-versions page lists Java 26.2 and Bedrock 26.30–26.52; its current download page listed build 1248. These pins are recorded in `gradle.properties`.

## Implemented foundations

The project includes Java 21 and Gradle 8.10.2, platform-free core contracts, YAML configuration, language fallback, async profiles, an initial PostgreSQL schema, integer-centavo idempotent double-entry accounting, permanent class restrictions, the server-owned season clock, one-time seeded route rolls, rarity-aware mana/skill formulas, anti-farm mob reward calculation, and quest progress. The complete test suite passes.

## Documents

See [`docs/SPEC.md`](docs/SPEC.md), [`docs/STATE.md`](docs/STATE.md), [`docs/M1_REPORT.md`](docs/M1_REPORT.md), [`docs/M2_REPORT.md`](docs/M2_REPORT.md), [`docs/M3_M5_REPORT.md`](docs/M3_M5_REPORT.md), [`docs/M6_REPORT.md`](docs/M6_REPORT.md), [`docs/CONTRACTS.md`](docs/CONTRACTS.md), [`docs/DECISIONS.md`](docs/DECISIONS.md), and [`docs/RISKS.md`](docs/RISKS.md).

## Test

```bash
./gradlew clean test
```

Production database wiring, Paper bootstrap/event adapters, `/wallet`, UI, and Bedrock QA remain explicit follow-up work.
