# Decisions

## [DECISION] M0 does not write gameplay code

The prompt's kickoff instructions explicitly require a kickoff report and owner approval before M1. This repository contains architecture and documentation only at M0.

## [DECISION] Treat “Forge” as the project/brand name for now

The supplied design bible specifies Paper, Bukkit/Paper events, Geyser, Floodgate, and Velocity. The user named the repository `forge-solo-subseason`, which could mean a Forge loader mod. These are materially different runtimes. Until the owner answers, the proposed implementation target remains the specified Paper plugin suite; no Forge loader APIs will be introduced silently.

## [DECISION] Proposed compatibility baseline

Based on the official Geyser supported-versions page checked 2026-10-06, Geyser supports Java 26.2 and Bedrock 26.30–26.52, and Geyser-Spigot supports Paper/Spigot 1.20.5+ with Java 21+. I propose pinning a specific Paper build only after the loader decision and a live Paper compatibility check. This is a proposal, not an owner decision.

## Defaults adopted from section 14

- Hall and NPC coordinates remain config placeholders and refuse to open until configured.
- Simple 10-tier zone scheme by distance/dimension is the fallback.
- Season persistence/reset rules follow section 14 item 4.
- `/sg kick` and `/sg mute` remain off.
- Domain Seats remain off.
- 2×2 inventory crafting remains enabled.
