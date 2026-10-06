# Forge Solo Subseason — State

## Current milestone

**Wallet integration and loophole audit complete.** The Paper plugin now loads a durable atomic file-backed ledger from its data folder and exposes a real read-only `/wallet` command. The upload bundle has been rebuilt from this plugin.

## Verified host baseline

The latest stable Paper baseline checked on 2026-10-06 is **Minecraft/Paper 26.2, Paper build 130**. Paper 26.2 build 130 requires **Java 25 or newer**, so the host instructions and build toolchain use Java 25. Geyser’s current reference release is build 1248, with Java 26.2 and Bedrock 26.30–26.52 listed on its supported-versions page.

## Done

M0 kickoff, M1 foundation, M2 ledger foundation, M3 restrictions, M4 clock/routes, M5 skill math, and M6 anti-farm/quest foundations are complete. The Paper plugin now uses `FileLedgerService`, persists balances and idempotency keys under its plugin data folder, and registers `/wallet` and `/balance` aliases. The crash-safety audit added rollback on persistence failure and filesystem-safe replacement fallback. The WitherHosting bundle is prepared under `deploy/witherhosting/`.

## Tests

```text
Java 21: ./gradlew clean test --no-daemon — PASS
Java 25: ./gradlew :platform-paper:jar --no-daemon — PASS
```

## Known gaps

The JAR is a real, installable foundation plugin, not the entire prompt suite. Production PostgreSQL/multi-server wiring, `/pay`, admin/audit tooling, durable route seeds, gameplay listeners, skill effects/UI, full route skill catalog, claims integration, loans, Bedrock forms, and manual host QA remain incomplete. See `docs/LOOPHOLE_AUDIT.md`.

## Next milestone

M7 — Orbs and skill pricing: implement tier pricing, daily caps, signed item payload boundary, claim-box fallback, and atomic purchase orchestration through `LedgerService`.

## Last updated

2026-10-06
