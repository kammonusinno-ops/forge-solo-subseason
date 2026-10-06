# Forge Solo Subseason — State

## Current milestone

**M5 foundations complete.** M3 class rules, M4 server-owned season/route foundations, and M5 mana/skill math are implemented in platform-free modules with automated tests.

## Verified baseline

The latest compatible stable baseline checked on 2026-10-06 is **Minecraft/Paper 26.2, Paper build 129**, Java 21, and Geyser build 1248 as the current crossplay release reference. These values are pinned in `gradle.properties`.

## Done

M0 kickoff and M1 foundation are complete. M2 adds integer-centavo money, idempotent double-entry transfers, MINT/SINK accounts, invariant checks, and a wallet contract. M3 adds permanent class choice and grind-aware wood/ore/crafting/ranged restrictions. M4 adds the fixed server-owned economy clock and one-time seeded route roll. M5 adds the rarity tier multipliers, mana formulas, skill definitions, and cooldown/effect math.

## Tests

```text
./gradlew clean test --no-daemon — PASS
39 actionable tasks completed successfully
```

## Known gaps

Production PostgreSQL ledger/profile wiring, durable route seeds, Paper bootstrap/event adapters, `/wallet`, skill effects/UI, route skill catalog, claims integration, and Bedrock QA remain incomplete. These are explicit gaps, not hidden stubs.

## Next milestone

M6 — Mob coins and quests: implement valid-kill classification, diminishing returns, AFK/grinder guards, quest progress, and ledger-backed mint rewards.

## Last updated

2026-10-06
