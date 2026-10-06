# Implementation Tally — 2026-10-06

## Completed and tested

| Area | Status |
|---|---|
| Build and Paper 26.2-130 packaging | Complete; Java 25 plugin build passes |
| Durable wallet ledger | Complete for single-server file persistence; atomic replacement, rollback, idempotency |
| `/wallet`, `/balance`, `/pay` | Complete; `/pay` requires an online target and whole positive TMT |
| Persistent class choice | Complete; `/class choose` is permanent and saved |
| Claims boundary | Implemented with a provider-neutral contract and fail-closed default; restricted destructive class breaks are denied until a real claims provider is configured |
| Wood/ore/crafting restrictions | Paper listeners complete with claims gate; claims provider adapter remains required for permissive claimed/unclaimed behavior |
| Mob reward event wiring | Implemented for valid player kills; server-side base values, named/leashed exclusion, AFK exclusion, chunk/hour diminishing, grinder reduction, daily 20,000 TMT cap, idempotent entity payout, async ledger mint |
| Season clock and route roll | Core logic complete; durable route repository and Hall UI remain |
| Mana and skill formulas/runtime | Core math and executable mana/cooldown runtime complete; Paper skill UI/effects remain |
| Anti-farm reward calculator and quest progress | Core logic and Paper mob payout path complete; quest event/reward orchestration remains |
| Signed orbs and pricing | Core signing, expiry, price scaling, and daily cap complete; seller/claim box remains |
| Boombai math | Interest cap, repayment split, credit score complete; loan storage/UI/collections remain |
| Leveling math | XP and stage boundaries complete; persistent player progression and effects remain |

## Still incomplete

The full prompt remains incomplete in these high-scope areas: real claims-plugin adapter, PostgreSQL/multi-server persistence, transaction history/audit dashboard, complete 75 skill implementations, route/class-change Hall, orb seller atomic purchase UI, Boombai loan lifecycle, bank/central bank, full quests, zones/fatigue, ascension/subclasses/Soul Gods, market/contracts/companies, opt-in PvP, teleport/homes, moderation/admin dashboards, Geyser/Floodgate forms, and manual WitherHosting QA.

## Loophole tally

- Fixed: persistence loss, failed-write mutation, missing runtime dependency, unrestricted remote control defaults, implicit EULA acceptance, client minting surface, duplicate payments through ledger idempotency, insufficient-mana cooldown abuse, unrestricted destructive class path, mob grinder/named/AFK over-rewarding, mob daily mint runaway.
- Remaining high risk: fail-closed claims means restricted wood/ore breaks are unavailable until a provider adapter is installed; file-backed storage is not suitable for multiple servers; no transaction journal/audit viewer; no live Paper test server; and no Bedrock QA.

## Anthropic check

Anthropic credentials were present, but a direct Claude review request returned HTTP 400. No Anthropic-generated conclusion was used or represented as a successful review. The audit is the agent’s own verified review.
