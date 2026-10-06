# WitherHosting upload bundle

This directory is arranged for a **Paper Java server** on WitherHosting. It contains Paper **Minecraft 26.2 / build 130**, the current self-contained Forge Solo Subseason plugin, safe baseline `server.properties`, and this guide.

## Upload

1. Create a Java server and select Java 25 or newer.
2. Stop the server before uploading files.
3. Upload `paper-26.2-130.jar` to the server root and select it as the startup jar.
4. Upload `plugins/forge-solo-subseason.jar` into `plugins/`.
5. Upload `server.properties` and preserve the host-assigned port.
6. Start once and accept Mojang's EULA through the host panel; this bundle intentionally does not include `eula=true`.
7. The plugin creates `plugins/ForgeSoloSubseason/ledger.properties` and `classes.properties`. Back both up with the server.
8. Install a supported claims plugin and its adapter before public survival play. Until configured, restricted class block breaks fail closed for safety.
9. Install Geyser/Floodgate separately from official releases only if Bedrock cross-play is needed.

## Available commands

- `/wallet` or `/balance` — read the durable TMT balance.
- `/pay <online-player> <whole-positive-TMT>` — transfer money atomically through the ledger.
- `/class choose <lumber|miner|craftsman|marksman|healer>` — make the permanent class choice.

Payments have no offline target, no self-payment, no decimal amount, and no client-controlled mint input. Class choices cannot be changed by players.

## Mob rewards

Valid player kills can mint TMT through the server ledger. The runtime excludes named/leashed mobs and AFK killers, applies chunk/hour diminishing returns and grinder reduction, and caps mob minting at 20,000 TMT per player per UTC day. Spawner provenance and claims-aware mob rules still require the next provider integrations.

## Important limitations

This is a real installable foundation slice, not the complete Forge Magic suite. Full claims adapter behavior, complete skills, Bedrock forms, loans, orbs, ascension, and other systems remain under implementation. Do not treat the server as a public launch until the remaining blockers in `docs/IMPLEMENTATION_TALLY.md` are resolved.

Do not upload credentials, private keys, database/Redis secrets, local worlds, `.gradle`, or build directories. Keep `online-mode=true`, RCON disabled, and command blocks disabled.

## Startup

Prefer the host panel's managed command. Generic command:

```text
java -Xms2G -Xmx4G -jar paper-26.2-130.jar --nogui
```
