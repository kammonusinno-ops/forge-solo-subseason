# WitherHosting upload bundle

This directory is arranged for a **Paper Java server** on WitherHosting. It contains Paper **Minecraft 26.2 / build 130**, the current self-contained Forge Solo Subseason plugin, safe baseline `server.properties`, and this guide.

## Upload

1. Create a Java server and select Java 25 or newer.
2. Stop the server before uploading files.
3. Upload `paper-26.2-130.jar` to the server root and select it as the startup jar.
4. Upload `plugins/forge-solo-subseason.jar` into `plugins/`.
5. Upload `server.properties` and preserve the host-assigned port.
6. Start once and accept Mojang's EULA through the host panel; this bundle intentionally does not include `eula=true`.
7. The plugin creates `plugins/ForgeSoloSubseason/ledger.properties`, `classes.properties`, and `config.yml`. Back them up.
8. Install a supported claims plugin and its adapter before public survival play. Until configured, restricted class block breaks fail closed.
9. Install Geyser/Floodgate separately from official releases only if Bedrock cross-play is needed.

## Hall of Tadhana

The Hall is disabled until the owner sets `hall.enabled: true` and fills the world and coordinates in the plugin config. `/class hall` reports the configured location or clearly reports that it is not ready. See [Hall design](../../docs/HALL_OF_TADHANA.md). The layout includes an arrival plaza, odds board, five class booths, test dummies, a confirmation dais, and a route reveal stage.

## Available commands

- `/wallet` or `/balance` — read the durable TMT balance.
- `/pay <online-player> <whole-positive-TMT>` — transfer money atomically through the ledger.
- `/class hall` — show Hall readiness/location.
- `/class choose <lumber|miner|craftsman|marksman|healer>` — permanently choose a class.
- `/skills` — show the selected class’s 15 route definitions and 54 subclass skill slots.

## Mob bounties and anti-cramming

All supported common passive, hostile, aquatic, elite, and boss mobs have a server-side bounty fallback. The config schedule overrides the principal mobs. Bounties are intentionally low and capped so one player cannot monopolize server-wide money or supplies.

The anti-cramming guard scans loaded chunks every configured interval. When a chunk reaches **15 non-player living mobs**, it removes the mobs in that chunk. Named and tamed mobs are protected by default; set that option only after staging tests if the owner explicitly accepts pet deletion risk.

Default controls: 20,000 TMT player/day, 100,000 TMT server/day, 20% player share, chunk/hour diminishing returns, and grinder reduction over 40 kills/minute. Spawner, named, leashed, AFK, and invalid kills pay nothing.

## Z.com database

The repository includes a Z.com PostgreSQL contract and schema, but the live JAR remains on the safe local ledger until the Z.com JDBC URL and credentials are configured and the production adapter is tested. See `docs/ZCOM_DATABASE.md`. Never upload database passwords in this ZIP.

## Important limitations

This is an installable foundation with real wallet, class, catalog, bounty, and anti-cramming behavior—not the complete Forge Magic suite. Full claims adapter behavior, skill effects, Bedrock forms, loans, orbs, ascension, and other systems remain under implementation. Do not treat the server as a public launch until the remaining blockers in `docs/IMPLEMENTATION_TALLY.md` are resolved.

Do not upload credentials, private keys, database/Redis secrets, local worlds, `.gradle`, or build directories. Keep `online-mode=true`, RCON disabled, and command blocks disabled.

## Startup

Prefer the host panel's managed command. Generic command:

```text
java -Xms2G -Xmx4G -jar paper-26.2-130.jar --nogui
```
