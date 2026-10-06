# Forge Solo Subseason

A safe, server-authoritative Minecraft survival RPG/economy MVP based on the supplied **Forge Magic** design bible.

> **M0 status:** kickoff only. No gameplay code is intentionally included until the owner approves the kickoff report.

## Source-of-truth documents

- [`docs/SPEC.md`](docs/SPEC.md) — copied Design Bible (owner-owned)
- [`docs/KICKOFF_REPORT.md`](docs/KICKOFF_REPORT.md) — M0 architecture, versions, risks, and questions
- [`docs/STATE.md`](docs/STATE.md) — current milestone state and next action
- [`docs/CONTRACTS.md`](docs/CONTRACTS.md) — Contracts v0
- [`docs/DECISIONS.md`](docs/DECISIONS.md) — defaults and decisions
- [`docs/RISKS.md`](docs/RISKS.md) — known technical and safety risks

## Planned build

The supplied specification describes a **Paper plugin suite**, not a Forge mod. This repository therefore reserves `platform-paper` as the planned runtime and treats “Forge” as the project/brand name unless the owner approves a loader change. See the kickoff report before implementation.

Java 21 and Gradle are required for M1. M0 does not claim that gameplay or a server artifact is buildable yet.
