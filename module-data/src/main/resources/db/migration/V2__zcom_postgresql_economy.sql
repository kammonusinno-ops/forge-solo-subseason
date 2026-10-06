CREATE TABLE IF NOT EXISTS wallet_accounts (
    account_id TEXT PRIMARY KEY,
    balance_centavos BIGINT NOT NULL DEFAULT 0 CHECK (balance_centavos >= 0),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS ledger_transfers (
    idempotency_key TEXT PRIMARY KEY,
    debit_account TEXT NOT NULL,
    credit_account TEXT NOT NULL,
    amount_centavos BIGINT NOT NULL CHECK (amount_centavos > 0),
    reason TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS ledger_transfers_created_at_idx ON ledger_transfers(created_at);
