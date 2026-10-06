# Implementation Tally — 2026-10-07

## Completed and tested

| Area | Status |
|---|---|
| Build and Paper 26.2-130 packaging | Complete; Java 25 plugin build passes |
| Durable wallet ledger | Complete for single-server file persistence; atomic replacement, rollback, idempotency |
| Z.com PostgreSQL contract | Prepared: TLS-validated environment contract and V2 wallet/transfer schema; live adapter and credentials are intentionally not claimed complete |
| `/wallet`, `/balance`, `/pay` | Complete; `/pay` requires an online target and whole positive TMT |
| Persistent class choice | Complete; `/class choose` is permanent and saved |
| Claims boundary | Implemented with provider-neutral contract and fail-closed default |
| Wood/ore/crafting restrictions | Paper listeners complete with claims gate; real claims provider adapter remains required |
| Configurable mob kill bounties | Complete: schedule in plugin config, server-side fallback values, spawner/named/leashed/AFK exclusions |
| Anti-monopoly economy controls | Complete for mob bounty issuance: player cap, server cap, 20% player-share cap, chunk/hour diminishing, grinder reduction |
| Mob reward event wiring | Complete for the supported bounty schedule; async idempotent ledger mint |
| Season clock and route roll | Core logic complete; durable route repository and Hall UI remain |
| Mana and skill formulas/runtime | Core math and executable mana/cooldown runtime complete; Paper skill UI/effects remain |
| Anti-farm reward calculator and quest progress | Core logic and Paper mob payout path complete; quest event/reward orchestration remains |
| Signed orbs and pricing | Core signing, expiry, price scaling, and daily cap complete; seller/claim box remains |
| Boombai math | Interest cap, repayment split, credit score complete; loan storage/UI/collections remain |
| Leveling math | XP and stage boundaries complete; persistent player progression and effects remain |

## Still incomplete

The full prompt remains incomplete in these high-scope areas: real claims-plugin adapter, live Z.com PostgreSQL adapter/migration execution, transaction history/audit dashboard, complete 75 skill implementations, route/class-change Hall, orb seller atomic purchase UI, Boombai loan lifecycle, bank/central bank, full quests, zones/fatigue, ascension/subclasses/Soul Gods, market/contracts/companies, opt-in PvP, teleport/homes, moderation/admin dashboards, Geyser/Floodgate forms, and manual WitherHosting QA.

## Loophole tally

- Fixed: persistence loss, failed-write mutation, missing runtime dependency, unrestricted remote control defaults, implicit EULA acceptance, client minting surface, duplicate payments through ledger idempotency, insufficient-mana cooldown abuse, unrestricted destructive class path, mob grinder/named/AFK/spawner over-rewarding, mob daily mint runaway, one-player mob bounty monopoly.
- Remaining high risk: fail-closed claims means restricted wood/ore breaks are unavailable until a provider adapter is installed; file-backed storage is not suitable for multiple servers; Z.com is not live-wired; no transaction journal/audit viewer; no live Paper test server; and no Bedrock QA.

## Anthropic check

Anthropic credentials were present, but a direct Claude review request returned HTTP 400. No Anthropic-generated conclusion was used or represented as a successful review.
