# Forge Solo Subseason — State

## Current milestone

**M6 foundation complete; WitherHosting package prepared.** M3 class rules, M4 season/route foundations, M5 mana/skill math, and M6 anti-farm mob rewards plus quest progress are implemented with automated tests. A bootable Paper foundation plugin and upload bundle are now arranged under `deploy/witherhosting/`.

## Verified host baseline

The latest stable Paper baseline checked on 2026-10-06 is **Minecraft/Paper 26.2, Paper build 130**. Paper 26.2 build 130 requires **Java 25 or newer**, so the host instructions and build toolchain use Java 25. Geyser’s current reference release is build 1248, with Java 26.2 and Bedrock 26.30–26.52 listed on its supported-versions page.

## Done

M0 kickoff, M1 foundation, M2 ledger foundation, M3 restrictions, M4 clock/routes, M5 skill math, and M6 anti-farm/quest foundations are complete. The repository also contains a host-ready Paper JAR, Paper server JAR, safe `server.properties`, and WitherHosting upload documentation.

## Tests

```text
Java 21: ./gradlew clean test --no-daemon — PASS
Java 25: ./gradlew :platform-paper:jar --no-daemon — PASS
```

## Known gaps

The uploaded plugin is currently a foundation bootstrap. Production PostgreSQL wiring, durable route seeds, `/wallet`, skill effects/UI, full route skill catalog, Paper gameplay listeners, claims integration, Geyser/Floodgate forms, and Bedrock QA remain incomplete and explicitly tracked.

## Next milestone

M7 — Orbs and skill pricing: implement tier pricing, daily caps, signed item payload boundary, claim-box fallback, and atomic purchase orchestration through `LedgerService`.

## Last updated

2026-10-06
