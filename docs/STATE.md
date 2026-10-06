# Forge Solo Subseason — State

## Current milestone

**Z.com database contract and balanced mob bounties.** The repository is cleanly inventoried and the plugin now has configurable mob bounties, spawner provenance tracking, global/player issuance caps, player-share caps, and anti-grinder controls. Z.com PostgreSQL environment validation and wallet/transfer schema are prepared without committing credentials.

## Host baseline

Minecraft/Paper 26.2 build 130, Java 25+ for the Paper plugin, and Geyser reference build 1248. The WitherHosting bundle is under `deploy/witherhosting/`.

## Tests

```text
Java 21: ./gradlew clean test --no-daemon — pending latest bounty/config changes
Java 25: ./gradlew :platform-paper:jar --no-daemon — pending latest bounty/config changes
```

## Known blockers

The live plugin still uses the safe local ledger until the Z.com JDBC URL, credentials, PostgreSQL adapter, migrations, backups, and reconciliation are supplied and tested. Claims remain fail closed without a provider adapter. Public launch also requires full skill effects/UI, quest and loan runtime, Bedrock forms, and live host QA. See `docs/IMPLEMENTATION_TALLY.md` and `docs/ZCOM_DATABASE.md`.

## Last updated

2026-10-07
