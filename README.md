# Forge Solo Subseason

A server-authoritative Paper plugin based on the supplied Forge Magic design bible. This checkout is arranged for a **Termux Paper test server** rather than a bundled host image.

## Current verified baseline

Minecraft/Paper 26.2 build 130, Java 25+ for the Paper artifact, and Gradle 9.0.0. The plugin is self-contained and does not require a client mod.

## Termux workflow

Read [`termux/README.md`](termux/README.md). Build the plugin with:

```bash
./termux/build-plugin.sh
```

Transfer only `termux/out/forge-solo-subseason.jar` to the Termux server's `plugins/` directory. Use [`termux/server.properties.example`](termux/server.properties.example) as a safe test baseline and copy [`termux/config.yml`](termux/config.yml) into the plugin data directory after first boot if needed.

## Gameplay systems in this checkout

The skill catalog now uses named, server-authoritative actions that map to vanilla mechanics: bounded potion/status effects, healing, arrows, tools, ore/wood restrictions, crafting/material modifiers, and small area actions. The catalog covers 75 route skills and 270 subclass skill slots without inventing custom client actions, one-shot effects, invulnerability, or class-bypass behavior.

Owners can configure one bounded base defense with `/baseguard set <1-64>`. While the owner is offline, the base becomes an absolute defense against block breaking, placing, interaction, fire, pistons, and explosions. The owner can use `/baseguard status` and `/baseguard off` while online. Data is saved atomically in `base-defenses.properties`.

Mob bounties, anti-monopoly caps, 15-mob chunk anti-cramming, permanent class choice, `/class hall`, `/skills`, claims fail-closed behavior, and the local test ledger remain available.

## Build and validation

```bash
export JAVA_HOME=/path/to/java-25
./gradlew clean test --no-daemon
./gradlew :platform-paper:jar --no-daemon
```

The last verified build passed all 72 Gradle tasks and produced a 1.7 MB self-contained Termux artifact.

## Explicit boundaries

The Z.com PostgreSQL contract exists but is not live-wired; the local ledger remains the safe default. A real claims provider, Bedrock forms, persistent route/subclass progression, and individual effect handlers for executing every catalog action are still required before public launch. No host server binary, generated ZIP, world, credentials, or runtime data is kept in this source checkout.
