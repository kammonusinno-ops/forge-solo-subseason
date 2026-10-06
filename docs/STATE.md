# Forge Solo Subseason — State

## Current milestone

**M0 — Kickoff.** The repository skeleton and project memory files are prepared. Gameplay code is intentionally not started because the prompt requires owner approval after the kickoff report.

## Done

- Preserved the supplied prompt at `docs/forge-magic-solo-prompt.md`.
- Copied Design Bible section 6 to `docs/SPEC.md`.
- Added Gradle multi-module skeleton and Java 21 toolchain configuration.
- Added CI workflow for `./gradlew test`.
- Added Contracts v0, decisions, risks, open questions, and kickoff report.

## Not done / known gaps

- No Gradle wrapper has been generated in this sandbox because Gradle is not installed.
- No gameplay implementation or server artifact exists by design.
- Exact Paper version must be pinned after the owner resolves the Forge-versus-Paper loader question.
- Bedrock behavior has not been tested; this is an M0 documentation-only repository.

## Next action after approval

Start M1 in the smallest steps: install/pin Gradle wrapper, add dependency versions, implement config and i18n abstractions, add migration runner, then add async profile load/save with tests.

## Verification commands

```bash
./gradlew test
```

## Last updated

2026-10-06
