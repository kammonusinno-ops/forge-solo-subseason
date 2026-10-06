# Forge Solo Subseason — State

## Current milestone

**M2 — Ledger and wallet foundation: platform-free core complete.** Integer-centavo money, double-entry transfers, idempotency, MINT/SINK accounts, invariant checks, and a wallet summary contract are implemented and tested.

## Done

M0 kickoff and M1 foundation are complete. M1 includes Gradle 8.10.2, Java 21, platform-free profile contracts, YAML config, language fallback, async profile persistence, and the initial profile schema. M2 adds `Money`, ledger transfer contracts, `InMemoryLedgerService`, the wallet summary contract, and automated ledger tests.

The server baseline is pinned to **Minecraft/Paper 26.2, Paper build 129**, with **Geyser build 1248** as the verified crossplay release reference. This was checked against the official Paper and Geyser pages on 2026-10-06.

## Tests

```text
./gradlew clean test --no-daemon — PASS
```

## Known gaps

Production PostgreSQL ledger/profile wiring, Paper bootstrap, `/wallet`, nightly invariant scheduling, and Bedrock QA remain incomplete. These are explicit gaps, not hidden stubs.

## Next milestone

M3 — Classes and restrictions: platform-free class identity and restriction decisions first, then verified Paper event adapters for recipes, crafting table use, blocks, bows, crossbows, projectiles, and claims integration.

## Last updated

2026-10-06
