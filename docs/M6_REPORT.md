# Milestone Report — M6 Mob Coins and Quests Foundation

M6 adds server-neutral valid-kill classification and quest progress. Mob rewards reject spawner mobs, named/leashed mobs, AFK players, and non-player deaths. Chunk returns are 100% for the first 20 kills, 50% for kills 21–50, and 10% afterward. Grinder activity above 40 kills per minute is reduced by 90%. Quest progress counts only matching mob types and never exceeds its requirement.

The implementation is intentionally a pure calculator/progress layer. It does not yet issue money; all future reward issuance must call `LedgerService` with a reason such as `MOB:<type>` or `QUEST:<id>`.

`./gradlew clean test --no-daemon` passed with all tests successful. Paper event adapters, persistent hourly counters, daily 20,000 TMT caps, healer assist splits, and ledger-backed quest payout transactions remain open.
