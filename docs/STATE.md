# Forge Solo Subseason — State

## Current milestone

**M1 — Foundation: substantially complete, with explicit follow-up gaps.** The platform-free API, YAML configuration, language fallback, migration metadata, async profile repository seam, Gradle wrapper, and automated tests are implemented.

## Done

- Preserved the supplied prompt at `docs/forge-magic-solo-prompt.md`.
- Copied Design Bible section 6 to `docs/SPEC.md`.
- Added Gradle multi-module skeleton and Java 21 toolchain configuration.
- Pinned and generated Gradle wrapper 8.10.2.
- Added platform-free `Profile`, `ProfileRepository`, `Result`, and `ErrorCode` contracts.
- Added YAML configuration loading and visible missing-translation fallback.
- Added async in-memory profile repository for deterministic foundation tests.
- Added initial PostgreSQL profile schema migration.
- Added CI workflow for `./gradlew test`.
- Added M1 tests for profile persistence, missing profiles, migrations, config, language fallback, and API behavior.

## Known gaps

- Production PostgreSQL repository wiring and migration execution are not complete; the current repository is a testable seam plus SQL migration.
- No Paper plugin bootstrap or server boot test exists yet; Paper APIs remain unverified until the exact loader/version is pinned.
- Redis is not wired; it is optional for the single-server MVP foundation.
- Bedrock behavior has not been tested; no platform UI exists yet.

## Tests

```text
./gradlew clean test --no-daemon — PASS
```

## Next milestone

M2 — Ledger and wallet: implement the double-entry `LedgerService`, idempotency, MINT/SINK accounts, invariant tests, and the platform-neutral wallet service before adding any command listener.

## Last updated

2026-10-06
