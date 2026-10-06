# Decisions

## [DECISION] Continue with the specified Paper architecture

The user asked to continue. The repository name is treated as the Forge Magic brand, while implementation follows the supplied Paper plugin design. No Forge loader APIs are introduced silently.

## [DECISION] Pin the latest compatible Paper release

Official Paper downloads checked 2026-10-06 list **Paper 26.2 build 129** as the latest stable build. Official Geyser supported versions list Java 26.2 and Bedrock 26.30–26.52, and the Geyser download page lists build 1248. The repository pins Minecraft 26.2, Paper build 129, Geyser build 1248 reference, Java 21, and Gradle 8.10.2 in `gradle.properties`.

## [DECISION] Keep money movement in one service

M2 introduces only `LedgerService` as the money-moving boundary. The in-memory implementation is for tests and design validation; durable PostgreSQL ledger wiring remains required before production use.

## [DECISION] Keep profile persistence platform-free

`ProfileRepository` is an async API in `core-api`; M1 includes an in-memory implementation for deterministic tests and a PostgreSQL schema migration as the production seam.

## Defaults adopted from section 14

Hall and NPC coordinates remain config placeholders. Simple 10-tier zone scaling is the fallback. Season persistence/reset follows section 14 item 4. `/sg kick` and `/sg mute` remain off. Domain Seats remain off. 2×2 inventory crafting remains enabled.
