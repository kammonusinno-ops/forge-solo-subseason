CREATE TABLE IF NOT EXISTS profiles (
    player_id UUID PRIMARY KEY,
    display_name VARCHAR(16) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);
