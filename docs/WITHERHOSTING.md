# WitherHosting Packaging Report

The repository now includes `deploy/witherhosting/`, a self-contained upload layout for a WitherHosting Java/Paper server. It contains Paper `26.2-130`, the built `forge-solo-subseason.jar`, safe baseline server properties, a setup guide, and an installation checklist.

## Verified build

Paper’s live API was checked on 2026-10-06. Paper `26.2` build `130` is marked `STABLE`, and its API artifact requires JVM 25 or newer. The project therefore uses Java 25 for the Paper plugin build and host recommendation. Foundation tests pass under Java 21; the Paper plugin artifact builds under Java 25.

## Current plugin scope

The uploaded JAR is a bootable foundation plugin. It logs enable/disable status and does not yet expose the full gameplay suite. `/wallet`, class-change UI, Geyser/Floodgate forms, database wiring, and gameplay listeners remain implementation work.

## Validation

```text
JAVA_HOME=...java-21... ./gradlew clean test --no-daemon — PASS
JAVA_HOME=...java-25... ./gradlew :platform-paper:jar --no-daemon — PASS
```

No EULA acceptance, credentials, payment data, world data, or RCON secrets are included.
