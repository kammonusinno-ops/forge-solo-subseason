# Forge Solo Subseason

A safe, server-authoritative Minecraft survival RPG/economy MVP based on the supplied **Forge Magic** design bible.

> **Current status:** M6 foundation is implemented and a WitherHosting upload bundle is prepared.

## Verified server baseline

The latest stable baseline checked on 2026-10-06 is **Minecraft/Paper 26.2, Paper build 130**. Paper 26.2 build 130 requires **Java 25 or newer**. Geyser’s official supported-versions page lists Java 26.2 and Bedrock 26.30–26.52; its current download page listed build 1248.

## WitherHosting package

Upload [`deploy/witherhosting/`](deploy/witherhosting/) to a Java server following [`deploy/witherhosting/README.md`](deploy/witherhosting/README.md). The package includes Paper 26.2-130, the current bootable `forge-solo-subseason.jar`, safe server properties, and a checklist. It intentionally does not include `eula=true`, credentials, RCON secrets, or world data.

## Implemented foundations

The project includes Java 25/21-compatible build paths with Gradle 9.0.0, platform-free core contracts, YAML configuration, language fallback, async profiles, an initial PostgreSQL schema, integer-centavo idempotent double-entry accounting, permanent class restrictions, the server-owned season clock, one-time seeded route rolls, rarity-aware mana/skill formulas, anti-farm mob reward calculation, and quest progress. Foundation tests pass under Java 21, and the Paper plugin artifact builds under Java 25.

## Documents

See [`docs/SPEC.md`](docs/SPEC.md), [`docs/STATE.md`](docs/STATE.md), [`docs/WITHERHOSTING.md`](docs/WITHERHOSTING.md), [`docs/M6_REPORT.md`](docs/M6_REPORT.md), [`docs/CONTRACTS.md`](docs/CONTRACTS.md), [`docs/DECISIONS.md`](docs/DECISIONS.md), and [`docs/RISKS.md`](docs/RISKS.md).

## Test

```bash
# Foundation tests
JAVA_HOME=/path/to/java-21 ./gradlew clean test

# Paper plugin artifact
JAVA_HOME=/path/to/java-25 ./gradlew :platform-paper:jar
```

Production database wiring, Paper gameplay listeners, `/wallet`, UI, and Bedrock QA remain explicit follow-up work.
