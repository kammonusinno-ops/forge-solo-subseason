# Forge Solo Subseason — State

## Current milestone

**All-mob bounty schedule, anti-entity-cramming, Hall design, and data-driven content catalogs.** The plugin now assigns fallback bounties across supported passive, hostile, aquatic, elite, and boss mobs; removes over-crammed non-player mobs at 15 per chunk with named/tamed protection; exposes `/class hall` and `/skills`; and packages 75 route skill definitions plus 135 subclass definitions / 270 subclass skill slots through generic registries.

## Host baseline

Minecraft/Paper 26.2 build 130, Java 25+ for the Paper plugin, and Geyser reference build 1248. The WitherHosting bundle is under `deploy/witherhosting/`.

## Tests

```text
Java 21: ./gradlew clean test --no-daemon — PASS (72 tasks before latest tracker-only correction)
Java 25: ./gradlew :platform-paper:jar --no-daemon — PASS
```

## Known blockers

The live plugin still uses the safe local ledger until the Z.com JDBC URL, credentials, PostgreSQL adapter, migrations, backups, and reconciliation are supplied and tested. Claims remain fail closed without a provider adapter. Generated catalogs provide the full data-driven definitions, but individual skill effect handlers, durable route/subclass/player progression, Hall region protection, Bedrock forms, and live host QA remain required for a public launch.

## Last updated

2026-10-07
