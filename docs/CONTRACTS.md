# Contracts v0

These are design contracts for M0. Signatures are intentionally language-level contracts, not implemented APIs yet.

## Boundary rules

- `core-api` has no Bukkit, Paper, Geyser, Floodgate, JDBC, Redis, or platform imports.
- Only `platform-paper` and its listeners may touch Bukkit/Paper classes.
- Only `LedgerService` may move money.
- Persistence operations are asynchronous and must expose idempotency where a retry can change state.
- All player-facing text and tunable numbers come from config/language data.

## Core interfaces

```java
interface ProfileRepository {
    CompletionStage<Profile> load(UUID playerId);
    CompletionStage<Void> save(Profile profile);
}

interface LedgerService {
    CompletionStage<Result<LedgerReceipt, LedgerError>> transfer(LedgerTransfer transfer);
    CompletionStage<MoneyBalance> balance(AccountId accountId);
    CompletionStage<LedgerInvariantReport> checkInvariant();
}

interface SeasonClock {
    EconomyPhase phase();
    Duration remaining();
    long economyDay();
}

interface ClassRestrictionService {
    RestrictionDecision canBreak(PlayerId player, BlockKind block);
    RestrictionDecision canUse(PlayerId player, Interaction interaction);
}

interface RouteRollService {
    CompletionStage<Result<RouteRoll, RouteRollError>> rollOnce(PlayerId player, ClassId classId, RollSeed seed);
}

interface SkillService {
    CompletionStage<Result<SkillCastReceipt, SkillCastError>> cast(PlayerId player, SkillId skillId, CastContext context);
}
```

## Domain events

- `ProfileLoaded`
- `ProfileSaved`
- `LedgerTransferCommitted`
- `LedgerInvariantFailed`
- `SeasonPhaseChanged`
- `ClassChosen`
- `RouteRollSeedStored`
- `RouteRolled`
- `SkillCastSucceeded`
- `SkillCastRejected`
- `AuditEntryCreated`

## Error codes

- `PROFILE_NOT_FOUND`
- `PROFILE_LOAD_FAILED`
- `PROFILE_SAVE_FAILED`
- `INSUFFICIENT_FUNDS`
- `DUPLICATE_IDEMPOTENCY_KEY`
- `LEDGER_INVARIANT_FAILED`
- `PHASE_NOT_ALLOWED`
- `CLASS_ALREADY_CHOSEN`
- `RESTRICTION_DENIED`
- `ROUTE_ROLL_ALREADY_EXISTS`
- `INSUFFICIENT_MANA`
- `SKILL_ON_COOLDOWN`
- `INVALID_CONFIGURATION`

## M1 compatibility note

The exact Java records/enums and dependency versions will be implemented only after the owner resolves the loader question and approves M0.
