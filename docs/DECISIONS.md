# Decisions

## [DECISION] Continue with the specified Paper architecture

The user asked to continue. The repository name is treated as the Forge Magic brand, while implementation follows the supplied Paper plugin design. No Forge loader APIs are introduced silently.

## [DECISION] Pin latest stable Paper 26.2 build 130

The live Paper API was checked on 2026-10-06. Paper 26.2 build 130 is marked `STABLE` and is newer than the previously pinned build 129. The API artifact `io.papermc.paper:paper-api:26.2.build.130-stable` requires JVM 25 or newer. The repository now pins Paper build 130, Java 25, and Gradle 9.0.0. Geyser build 1248 remains the current crossplay reference.

## [DECISION] Keep money, class rules, clock, routes, and skill math platform-free

These systems are implemented in core contracts and testable modules without Bukkit imports. Platform adapters will be added only after exact APIs are verified.

## [DECISION] Vanilla-first skill redesign — 2026-10-15

Skill definitions now carry a `SkillAction`, display name, and description. The catalog uses bounded vanilla actions such as Haste, Night Vision, Speed, Regeneration, Healing, Purify, Arrow Accuracy, Slow, Root, Tree Fell, Ore Sense, Area Mine, Furnace Speed, Tool Repair, and Material Bonus. No catalog entry promises client-only controls, invulnerability, one-shot kills, or class-bypass behavior. Mythic catalog entries use long cooldowns, and old five-argument constructors remain compatible for tests.

## [DECISION] Offline-owner absolute defense — 2026-10-15

A player may register one cubic base region with `/baseguard set <1-64>`. The region is protected only while the owner is offline, so the owner retains access while online. Protection covers breaking, placement, interaction, fire, pistons, and explosions. The registry is atomic file-backed. This requested purge-defense feature supplements, but does not replace, a real claims provider.

## [DECISION] Termux source layout — 2026-10-15

The source checkout does not carry host server binaries or a generated 63 MB ZIP. Termux receives a single self-contained plugin JAR from `termux/build-plugin.sh`; server runtime data remains on the Android device and is ignored.

## Defaults adopted from section 14

Hall and NPC coordinates remain config placeholders. Simple 10-tier zone scaling is the fallback. Season persistence/reset follows section 14 item 4. `/sg kick` and `/sg mute` remain off. Domain Seats remain off. 2×2 inventory crafting remains enabled.
