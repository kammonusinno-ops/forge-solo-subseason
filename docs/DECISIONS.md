# Decisions

## [DECISION] M0 did not write gameplay code

The prompt's kickoff instructions explicitly required a kickoff report and owner approval before M1. M0 contained architecture and documentation only.

## [DECISION] Continue with the specified Paper architecture

The user asked to continue after the M0 report. I am using the documented default: the repository name is treated as the Forge Magic brand, while implementation follows the supplied Paper plugin design. No Forge loader APIs are introduced silently.

## [DECISION] Pin Gradle wrapper to 8.10.2

Gradle 8.10.2 was downloaded and the wrapper was generated during M1. Java 21 is used through the Gradle toolchain.

## [DECISION] Keep profile persistence platform-free

`ProfileRepository` is an async API in `core-api`; M1 includes an in-memory implementation for deterministic tests and a PostgreSQL schema migration as the production seam. Full connection/pool wiring remains a follow-up task rather than being presented as complete.

## [DECISION] Use visible language fallback

Missing translations return `[missing translation: key]` so missing player-facing text cannot silently become blank or misleading.

## Defaults adopted from section 14

- Hall and NPC coordinates remain config placeholders and refuse to open until configured.
- Simple 10-tier zone scheme by distance/dimension is the fallback.
- Season persistence/reset rules follow section 14 item 4.
- `/sg kick` and `/sg mute` remain off.
- Domain Seats remain off.
- 2×2 inventory crafting remains enabled.
