# WitherHosting upload bundle

This directory is arranged for a **Paper Java server** on WitherHosting. It contains the verified Paper server jar for **Minecraft 26.2 / Paper build 130**, the currently built Forge Solo Subseason plugin jar, safe baseline `server.properties`, and this guide.

## Upload

1. Create a Java server in WitherHosting and select Java 25 or newer. Paper 26.2 build 130 requires Java 25.
2. Stop the server before uploading files.
3. Upload `paper-26.2-130.jar` to the server root and set the host startup jar/name to `paper-26.2-130.jar`.
4. Upload `plugins/forge-solo-subseason.jar` into the server's `plugins/` directory.
5. Upload `server.properties` to the server root. Keep any host-provided port value if WitherHosting assigns a non-default port.
6. Start once so Paper generates its folders and asks for the EULA. Review and accept Mojang's EULA in the host panel; this bundle intentionally does not include `eula=true`.
7. The plugin creates `plugins/ForgeSoloSubseason/ledger.properties` on first boot. Back up this file with the server backups; it contains wallet balances and idempotency records.
8. Stop, upload the optional Geyser/Floodgate plugins from their official release pages if Bedrock cross-play is needed, then start again.

## Current commands

- `/wallet` or `/balance` — show the player’s durable TMT balance.

The wallet command is read-only. No client-provided amount, mint command, RCON command, or admin economy shortcut is included.

## Important limitations

The uploaded plugin is the current **safe foundation slice**. It has real durable wallet storage and Paper bootstrap, but it does not yet expose `/pay`, class-change menus, Bedrock forms, or the full gameplay suite. Those systems are still being implemented in the repository.

Do not upload `.gradle`, `build` directories, database credentials, Redis credentials, private keys, or local world data from the source checkout. Do not disable `online-mode` for a public server.

## Startup settings

Use the host panel's Java command with at least the memory assigned by your plan. A generic self-host command is:

```text
java -Xms2G -Xmx4G -jar paper-26.2-130.jar --nogui
```

On WitherHosting, prefer the panel's managed startup command and assigned port instead of overriding it manually.
