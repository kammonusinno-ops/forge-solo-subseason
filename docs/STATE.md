# Forge Solo Subseason — State

## Current milestone

**Continued implementation: wallet, commands, restrictions, economy foundations, and skill runtime.** The Paper plugin has durable wallet storage, `/wallet`, `/balance`, `/pay`, persistent `/class choose`, wood/ore restriction listeners, and crafting-table enforcement. Core orb signing/pricing, Boombai math, leveling math, and executable mana/cooldown skill runtime are implemented and tested.

## Host baseline

Minecraft/Paper 26.2 build 130, Java 25+ for the Paper plugin, and Geyser reference build 1248. The WitherHosting bundle is under `deploy/witherhosting/`.

## Tests

```text
Java 21: ./gradlew clean test --no-daemon — PASS
Java 25: ./gradlew :platform-paper:jar --no-daemon — PASS
Latest full suite before skill runtime: 64 tasks passed
```

## Known blockers

The plugin is installable and has real wallet/class behavior, but public survival launch still requires claims integration before destructive class restrictions, a transaction journal/audit trail, database persistence for network scale, full skill effects/UI, mob/quest payout wiring, loan/orb runtime, ascension/subclasses, Bedrock forms, and live host QA. The detailed tally is in `docs/IMPLEMENTATION_TALLY.md`.

## Next work

Continue implementing runtime systems in priority order: claims-safe restrictions, ledger transaction history, skill effects and action-bar UI, mob reward event wiring, quest rewards, orb seller, Boombai lifecycle, leveling persistence, and Bedrock parity.

## Last updated

2026-10-06
