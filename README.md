# Forge Solo Subseason

A safe, server-authoritative Minecraft survival RPG/economy MVP based on the supplied **Forge Magic** design bible.

> **Current status:** M2 ledger foundation is implemented and tested.

## Verified server baseline

The latest compatible stable baseline checked on 2026-10-06 is **Minecraft/Paper 26.2, Paper build 129**, with Java 21. Geyser’s official supported-versions page lists Java 26.2 and Bedrock 26.30–26.52; its current download page listed build 1248. These pins are recorded in `gradle.properties`.

## M1/M2 foundation

The project includes a Java 21 / Gradle 8.10.2 wrapper, platform-free core contracts, YAML configuration, language fallback, async profile persistence, the initial profile migration, integer-centavo money, an idempotent double-entry ledger, MINT/SINK accounts, invariant checks, and JUnit tests.

## Source-of-truth documents

See [`docs/SPEC.md`](docs/SPEC.md), [`docs/STATE.md`](docs/STATE.md), [`docs/M1_REPORT.md`](docs/M1_REPORT.md), [`docs/M2_REPORT.md`](docs/M2_REPORT.md), [`docs/CONTRACTS.md`](docs/CONTRACTS.md), [`docs/DECISIONS.md`](docs/DECISIONS.md), and [`docs/RISKS.md`](docs/RISKS.md).

## Test

```bash
./gradlew clean test
```

Production database wiring, Paper bootstrap, `/wallet`, and Bedrock QA remain explicit follow-up work.
