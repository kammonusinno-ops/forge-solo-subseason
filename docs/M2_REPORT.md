# Milestone Report — M2 Ledger and Wallet Foundation

## Summary

M2 now has a platform-free double-entry ledger boundary. Money is stored as integer centavos, transfers are asynchronous and idempotent, `SYSTEM:MINT` and `SYSTEM:SINK` are reserved accounts, and insufficient-funds failures are soft. The wallet summary contract is ready for a later platform command.

## Implementation

`Money`, `LedgerTransfer`, `LedgerReceipt`, `LedgerService`, `LedgerError`, `LedgerInvariantReport`, and `WalletService` live in `core-api`. `InMemoryLedgerService` provides deterministic test behavior while the production PostgreSQL adapter remains a documented follow-up.

## Tests

`./gradlew clean test --no-daemon` passed with all tests successful. Coverage includes minting, player-to-player transfers, idempotency, insufficient funds, and ledger balance invariants.

## Known gaps

There is no Paper `/wallet` command yet, no durable ledger table, and no nightly scheduler. These belong to the platform/data integration work before M2 can be considered production-ready.

## Next

Begin M3 with class identity and restriction decisions in the platform-free core, followed by recipe/block/projectile enforcement adapters only after the exact Paper API is pinned.
