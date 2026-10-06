# Milestone Report — M1 Foundation

## 1. Summary

The repository now has a working Java 21 / Gradle 8.10.2 foundation. Core domain contracts are platform-free. YAML configuration validates required values, language lookup exposes missing keys, profile persistence is asynchronous behind `ProfileRepository`, and the first PostgreSQL schema migration is recorded. No Bukkit/Paper or Geyser/Floodgate API is used yet.

## 2. Files added/changed

- **Build:** Gradle wrapper, module build files, Java 21 toolchain, SnakeYAML, PostgreSQL driver, JUnit 5.
- **core-api:** `Profile`, `ProfileRepository`, `Result`, `ErrorCode`, and tests.
- **core-common:** `ConfigLoader`, `LanguageBundle`, config and English language resources, and tests.
- **module-data:** `InMemoryProfileRepository`, `MigrationRunner`, profile migration, and tests.
- **Docs:** state and decisions updated; this report added.

## 3. Tests

- Written: API, configuration, language, migration, async save/load, and missing-profile tests.
- Run: `./gradlew clean test --no-daemon`
- Passed: **all tests; Gradle build successful**.
- Not run: Paper server boot, PostgreSQL container integration, Redis integration, Java/Bedrock manual QA.

## 4. Invariants covered

M1 does not own the economy invariants. It covers deterministic migration ordering, async profile save/load round-trip, and explicit missing-profile failure.

## 5. Decisions and defaults

See [`DECISIONS.md`](DECISIONS.md). The implementation follows the previously documented Paper default, pins Gradle 8.10.2, and keeps production persistence behind an interface.

## 6. [VERIFY] items still open

- Exact Paper/Minecraft version and Paper API event names.
- Geyser-Spigot and Floodgate API versions.
- PostgreSQL pool/migration runtime wiring.
- Claims/protection provider.

## 7. Known gaps and risks

The current module-data implementation is not yet a production database adapter. The Paper platform has no bootstrap, so “server boots” is not yet an M1 acceptance claim. These are explicit follow-up tasks, not hidden stubs.

## 8. Questions for the owner

No new blocking question beyond the loader decision already recorded. The implementation uses the approved default to keep progress moving.

## 9. Next milestone plan

M2 will implement the ledger boundary, account identifiers, integer centavos, idempotency keys, MINT/SINK accounts, and invariant tests before any wallet command is added.
