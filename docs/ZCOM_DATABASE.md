# Z.com PostgreSQL deployment contract

The repository is prepared for the decision to host the database on Z.com, but no Z.com hostname, database name, username, or password was supplied in this session. No credential is committed.

## Required host environment

Set these in the WitherHosting/server environment or secret manager:

```text
FORGE_DB_URL=jdbc:postgresql://<z-com-host>:5432/<database>?sslmode=verify-full
FORGE_DB_USER=<database-user>
FORGE_DB_PASSWORD=<database-password>
```

The URL must use PostgreSQL JDBC and TLS. Never put the password in `server.properties`, `config.yml`, GitHub, or the ZIP bundle.

## Schema

Apply `module-data/src/main/resources/db/migration/V1__create_profiles.sql` and `V2__zcom_postgresql_economy.sql` with a migration runner before enabling database-backed economy. V2 creates wallet accounts and an idempotency-keyed transfer journal.

## Current implementation boundary

The live Paper plugin still uses its safe file-backed ledger because the Z.com connection details and JDBC production adapter were not supplied. `PostgresConfig` validates the required contract and TLS policy; it does not claim to connect or migrate automatically. This avoids silently running an unverified remote database configuration.

Before switching production writes to Z.com, implement and test:

1. A PostgreSQL `LedgerService` adapter using transactions and row locks.
2. Startup migration execution and schema-version checks.
3. Connection-pool sizing and timeouts.
4. Backup/restore and failover procedure.
5. A one-time import from `ledger.properties` with reconciliation against balances.
6. A kill switch that disables minting if database health or invariant checks fail.
