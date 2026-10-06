# Forge Solo Subseason — State

## Current milestone

**M6 foundation complete.** M3 class rules, M4 season/route foundations, M5 mana/skill math, and M6 anti-farm mob rewards plus quest progress are implemented with automated tests.

## Verified baseline

The latest compatible stable baseline checked on 2026-10-06 is **Minecraft/Paper 26.2, Paper build 129**, Java 21, and Geyser build 1248 as the current crossplay reference. These values are pinned in `gradle.properties`.

## Done

M0 kickoff, M1 foundation, and M2 ledger foundation are complete. M3 adds permanent class choice and grind-aware restrictions. M4 adds the server-owned economy clock and one-time seeded route roll. M5 adds rarity-aware mana, effect, and cooldown math. M6 adds valid-kill checks, chunk diminishing returns, grinder reduction, AFK/spawner/named-mob exclusion, and quest progress.

## Tests

```text
./gradlew clean test --no-daemon — PASS
48 actionable tasks completed successfully
```

## Known gaps

Production PostgreSQL wiring, durable route seeds, Paper bootstrap/event adapters, `/wallet`, skill effects/UI, full 75-skill data catalog, claims integration, Bedrock QA, and atomic ledger-backed quest reward issuance remain incomplete and are tracked explicitly.

## Next milestone

M7 — Orbs and skill pricing: implement tier pricing, daily caps, signed item payload boundary, claim-box fallback, and atomic purchase orchestration through `LedgerService`.

## Last updated

2026-10-06
