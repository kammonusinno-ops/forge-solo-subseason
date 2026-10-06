# Decisions

## [DECISION] Continue with the specified Paper architecture

The user asked to continue. The repository name is treated as the Forge Magic brand, while implementation follows the supplied Paper plugin design. No Forge loader APIs are introduced silently.

## [DECISION] Pin latest stable Paper 26.2 build 130

The live Paper API was checked on 2026-10-06. Paper 26.2 build 130 is marked `STABLE` and is newer than the previously pinned build 129. The API artifact `io.papermc.paper:paper-api:26.2.build.130-stable` requires JVM 25 or newer. The repository now pins Paper build 130, Java 25, and Gradle 9.0.0. Geyser build 1248 remains the current crossplay reference.

## [DECISION] Arrange a safe WitherHosting upload bundle

The bundle uses `online-mode=true`, disables RCON and command blocks by default, excludes `eula=true`, and does not include credentials, payment data, private keys, or world data. The host panel should retain any assigned server port and manage the startup command.

## [DECISION] Keep money, class rules, clock, routes, and skill math platform-free

These systems are implemented in core contracts and testable modules without Bukkit imports. Platform adapters will be added only after exact APIs are verified.

## [DECISION] Persist route seeds before reveal

M4 rejects a second route roll for a player. The current implementation is an in-memory proof of the invariant; durable storage is required before production.

## Defaults adopted from section 14

Hall and NPC coordinates remain config placeholders. Simple 10-tier zone scaling is the fallback. Season persistence/reset follows section 14 item 4. `/sg kick` and `/sg mute` remain off. Domain Seats remain off. 2×2 inventory crafting remains enabled.
