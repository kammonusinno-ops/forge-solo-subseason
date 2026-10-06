# Kickoff Report — M0 Forge Solo Subseason

## 1. Understanding

1. Forge Magic is intended to turn vanilla survival into a transparent, cooperative economy game.
2. Players use Tomaterrency (TMT), with money represented as integer centavos.
3. Five classes create interdependence through gathering, crafting, healing, and ranged restrictions.
4. Players begin as Novices, then choose a permanent seasonal class and receive one server-authoritative route roll.
5. Routes contain skills whose power, mana cost, and cooldown are tunable and rarity-aware.
6. Seasons use a server-owned economy clock rather than world time.
7. Loans, interest, quests, mob rewards, shops, and companies teach business concepts.
8. Progression has levels, nine stages, ascension, subclasses, and five Soul God seats.
9. PvP is opt-in and must be fair, auditable, and safe for a teenage community.
10. Java and Bedrock players must receive equivalent functionality, with no client mod or Java-only key requirement.

## 2. Work plan

The build follows M0–M16 in order. M0 establishes contracts and decisions. M1 will implement the foundation: configuration/i18n, migration runner, async profile repository, and test seams. M2 will add the ledger and wallet. M3–M9 form the single-server MVP. M10–M15 add subclasses, market, PvP, companies, Soul Gods, Bedrock polish, and admin tooling. M16 is network scaling only after the MVP is stable.

## 3. Proposed pinned versions

| Component | Proposal | Verification |
|---|---|---|
| Java | 21 | Installed in the sandbox: OpenJDK 21.0.12.1. Official Geyser page requires Java 21+ for Geyser-Spigot. |
| Minecraft | **[VERIFY]** exact version after loader decision | Geyser official page checked 2026-10-06 says current Java protocol support is 26.2 and Paper/Spigot is supported from 1.20.5+. |
| Paper | **[VERIFY]** exact build | Must be pinned from the Paper downloads/API after Minecraft target is chosen. |
| Geyser | Latest compatible release at M1, exact build pinned in Gradle/docs | Official download page checked 2026-10-06 listed Build #1248 dated 2026-10-04 and a Spigot/Paper artifact. |
| Floodgate | Matching Geyser release/build | **[VERIFY]** exact build and API surface at M1. |
| Gradle | 8.10+ proposal, wrapper committed at M1 | Gradle is not installed in the current sandbox, so the wrapper is not generated at M0. |
| PostgreSQL | 16+ proposal | **[VERIFY]** integration image and driver at M1. |
| Redis | 7+ proposal | **[VERIFY]** integration image and client at M1. |

**Important:** these are proposals, not silent changes to the Design Bible. The official Geyser compatibility source is [Supported versions](https://geysermc.org/wiki/geyser/supported-versions/), and the release source is [Download](https://geysermc.org/download/).

## 4. Repository skeleton and CI

- `core-api`: platform-free interfaces, DTOs, events, errors.
- `core-common`: config, i18n, scheduling, results, utilities.
- `module-data`: PostgreSQL/Redis adapters and migrations.
- `platform-paper`: the only Bukkit/Paper bootstrap and listeners.
- `platform-velocity`: reserved for the later network milestone.
- `tools`: reserved for load tests and season simulation.
- `.github/workflows/ci.yml`: Java 21 setup and `./gradlew test` gate.
- `docs/`: prompt copy, Design Bible, state, contracts, decisions, risks, questions, and this report.

## 5. Contracts v0

The public contracts are in [`CONTRACTS.md`](CONTRACTS.md). The key boundaries are `ProfileRepository`, `LedgerService`, `SeasonClock`, `ClassRestrictionService`, `RouteRollService`, and `SkillService`. Domain events cover profile persistence, ledger commits/invariant failures, season/class/route transitions, skill casts, and audit entries. Error codes are stable strings/enums and no platform types cross into `core-api`.

## 6. Verification list

| API / integration | Status |
|---|---|
| Bukkit block-break event | **[VERIFY]** exact event and Paper version at M3 |
| Bukkit block interaction event | **[VERIFY]** exact event and cancellation semantics at M3 |
| Bukkit craft/prepare-craft events | **[VERIFY]** exact event behavior at M3 |
| Bukkit bow shoot / crossbow load / projectile launch | **[VERIFY]** exact event coverage at M3 |
| Bukkit dispense event | **[VERIFY]** logging and cancellation policy at M3 |
| Bukkit attribute modifiers with stable NamespacedKey | **[VERIFY]** exact current API at M9 |
| Paper scheduler and async boundaries | **[VERIFY]** current scheduler contract at M1 |
| Geyser-Spigot integration | **[VERIFY]** supported API and optional dependency at M15 |
| Floodgate API / Bedrock identity | **[VERIFY]** current API and failure behavior at M15 |
| Java GUI and Bedrock forms | **[VERIFY]** UI adapter approach at M15 |

No unverified API is used in M0.

## 7. Top 10 technical risks

See [`RISKS.md`](RISKS.md) for the full register. The highest risks are loader mismatch, version drift, economy duplication, main-thread blocking, restriction bypasses, route rerolls, Bedrock parity, teen-player harm, loan laundering, and scope explosion.

## 8. Contradictions and gaps

- **Forge vs Paper:** the user’s repository name says Forge, while the supplied Design Bible specifies Paper/Bukkit/Geyser/Floodgate/Velocity. **Default:** preserve the supplied Paper architecture and treat Forge as branding until the owner decides.
- **Minecraft version:** the prompt says verify at kickoff but does not name a fixed version. **Default:** choose the newest version supported by the selected loader and crossplay stack after verification.
- **Coordinates:** locations are not supplied. **Default:** config placeholders that fail loudly and keep events closed.
- **Claims integration:** required but no provider is named. **Default:** an interface-only seam in M1–M3; do not enable destructive restrictions without protection coverage.
- **“Forge” could mean Forge/NeoForge:** Geyser’s official page says non-latest Forge/NeoForge support is constrained, which further increases the cost of changing loaders. **Default:** do not silently convert this into a client mod.

## 9. Questions for the owner

1. Does “Forge” mean a Forge/NeoForge loader mod, or is it only the Forge Magic project name while using Paper?
2. Should `forge-solo-subseason` remain private, or should it be public?
3. If Paper is intended, may I pin the latest Paper version that passes Geyser/Floodgate compatibility checks?
4. Which claims/protection integration should the MVP use?
5. Should M1 use PostgreSQL immediately, or is a test-only embedded adapter acceptable while keeping the production interface PostgreSQL-first?

## 10. M1 task breakdown

1. Resolve loader and visibility decisions.
2. Install/generate and commit the Gradle wrapper.
3. Pin Java, Paper, Geyser/Floodgate, PostgreSQL, Redis, and test dependency versions.
4. Add module build files and dependency constraints.
5. Implement platform-free `Result`, IDs, profile DTO, and error taxonomy from Contracts v0.
6. Add YAML configuration schema, validation, and fail-loud placeholder handling.
7. Add language bundle loading with fallback and missing-key diagnostics.
8. Add SQL migration runner and initial profile schema.
9. Add async `ProfileRepository` interface and PostgreSQL adapter seam.
10. Add profile load/save tests, migration tests, config tests, and failure-path tests.
11. Run the complete suite and update `STATE.md`, `DECISIONS.md`, and the M1 report.

**M0 gate:** wait for owner approval and answers before starting M1.
