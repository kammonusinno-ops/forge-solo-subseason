# Loophole Audit — Wallet and Host Package

## Reviewed paths

The review covered the Paper bootstrap, `/wallet`, `FileLedgerService`, command registration, `server.properties`, the Gradle dependency boundary, and the host upload bundle.

## Fixed in this pass

| Finding | Severity | Action |
|---|---:|---|
| Ledger mutation could remain in memory after a failed disk write | Critical | Added rollback of balances, totals, and idempotency state before propagating the failure. |
| Atomic file move may not be supported on every filesystem | High | Added a safe non-atomic replace fallback after attempting `ATOMIC_MOVE`. |
| Wallet data could be lost on restart | Critical | Replaced the runtime in-memory path with `FileLedgerService` under the plugin data folder. |
| Host could start an unsafe remote-control surface | High | RCON and command blocks are disabled in the bundle; `online-mode=true` remains enabled. |
| EULA could be accepted implicitly | Medium | `eula=true` is not included; the host operator must review and accept it. |
| Main thread could block on wallet I/O | High | Wallet balance reads and writes run on a bounded daemon executor; the response is returned to the Paper scheduler. |
| User could mint money through `/wallet` | Critical | `/wallet` is read-only. No admin mint command or client-controlled amount exists. |

## Remaining blockers before calling the complete design production-ready

The current JAR is a real, installable foundation plugin, but it is not the entire Forge Magic suite. Remaining high-impact work includes database-backed multi-server persistence, transaction history and audit records, authenticated admin tools, claims-aware class restrictions, durable route seeds, anti-farm counters with time windows, Geyser/Floodgate forms, all skill effects, loan/interest safety, and Bedrock/manual QA.

## Tally

- **Implemented and tested in repository:** 7 foundation areas (profiles/config, ledger, classes, season clock, routes, skill math, anti-farm/quests), plus durable host wallet and Paper bootstrap.
- **Audited and fixed in this pass:** 7 findings.
- **Known incomplete prompt milestones:** M7–M16 and the Paper gameplay/UI integration parts of M3–M6.
- **Production claim:** the package is ready to insert into a Paper host as a safe foundation and read-only wallet plugin; it is not honestly claimable as the entire multi-system design until the remaining milestones are implemented.
