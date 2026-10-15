# Forge Solo Subseason — State

## Current milestone

**Vanilla-safe skill redesign, offline-owner base purge defense, and Termux test layout.** Skill definitions now carry named actions and descriptions tied to normal Minecraft mechanics. The catalog covers 75 route skills and 270 subclass skill slots without client mods, one-shot kills, invulnerability, or class-bypass claims. `/baseguard set <1-64>` persists a base region; while its owner is offline, destructive block actions, interaction, fire, pistons, and explosions are blocked.

## Termux layout

`termux/` contains the build script, server-property template, secret-free plugin configuration, Android runtime ignore rules, and a usage guide. Generated host Paper binaries and the oversized WitherHosting ZIP were removed from the checkout. The verified plugin is emitted to `termux/out/forge-solo-subseason.jar`.

## Tests

```text
Java 25: ./gradlew clean test --no-daemon — PASS
Java 25: ./gradlew :platform-paper:jar --no-daemon — PASS
```

## Known blockers

The live plugin still uses the safe local ledger until the Z.com JDBC URL, credentials, PostgreSQL adapter, migrations, backups, and reconciliation are supplied and tested. Claims remain fail closed without a provider adapter. Catalog definitions are vanilla-safe, but dedicated Paper effect handlers, durable route/subclass/player progression, Hall region protection, Bedrock forms, and live Termux server QA remain required for a public launch.

## Last updated

2026-10-15
