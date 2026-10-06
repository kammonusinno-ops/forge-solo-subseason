# Forge Solo Subseason — State

## Current milestone

**Continued implementation: wallet, commands, restrictions, orb/loan/leveling foundations.** The Paper plugin now has durable wallet storage, `/wallet`, `/balance`, `/pay`, persistent `/class choose`, wood/ore restriction listeners, and crafting-table enforcement. Core orb signing/pricing, Boombai math, and leveling math are implemented and tested.

## Host baseline

Minecraft/Paper 26.2 build 130, Java 25+ for the Paper plugin, and Geyser reference build 1248. The WitherHosting bundle is under `deploy/witherhosting/`.

## Tests

```text
Java 21: ./gradlew clean test --no-daemon — PASS
Java 25: ./gradlew :platform-paper:jar --no-daemon — PASS
60 Gradle tasks completed successfully in the latest full suite
```

## Known blockers

The plugin is installable and has real wallet/class behavior, but public survival launch still requires claims integration before destructive class restrictions, a transaction journal/audit trail, database persistence for network scale, full skill/quest/loan/ascension runtime, Bedrock forms, and live host QA. The detailed tally is in `docs/IMPLEMENTATION_TALLY.md`.

## Next work

Continue implementing the remaining runtime systems in priority order: claims-safe restrictions, ledger transaction history, skill runtime and mana UI, mob reward event wiring, quest rewards, orb seller, Boombai lifecycle, leveling persistence, and Bedrock parity.

## Last updated

2026-10-06
