# Risks

1. **Loader mismatch:** Forge mod versus Paper plugin changes every API and deployment artifact. Mitigation: require owner decision before M1 code.
2. **Version drift:** Minecraft, Paper, Geyser, and Floodgate compatibility changes. Mitigation: pin versions and verify against official docs before implementation.
3. **Economy duplication:** retries or crashes could mint or duplicate TMT. Mitigation: single LedgerService, idempotency keys, double-entry invariant tests.
4. **Main-thread blocking:** database or Redis work could lag the server. Mitigation: async repositories and bounded scheduling; never block event handlers.
5. **Class restriction bypass:** alternate interactions, pistons, explosions, or plugins may bypass checks. Mitigation: fail-closed claims gate and threat-model every event before enabling permissive restrictions.
6. **RNG rerolls:** crashes/relogs could reroll routes. Mitigation: persist the roll seed before reveal and make the roll idempotent.
7. **Bedrock parity gaps:** Java-only UI or key chords could exclude Bedrock players. Mitigation: server-authoritative commands/forms and parity QA.
8. **Teen player harm:** debt, PvP, rankings, and chat could pressure or harass minors. Mitigation: transparent costs, opt-in PvP, moderation/audit tools, no debt item/land seizure, and no pay-to-win mechanics.
9. **Loan abuse and laundering:** loan-tagged funds could be transferred or laundered. Mitigation: transfer restrictions, row locks, expiry, and audit trails.
10. **Scope explosion:** 17 milestones cannot be safely implemented as one dump. Mitigation: one milestone at a time with approval gates and automated invariants.
11. **Z.com migration outage:** switching from local files to remote PostgreSQL can duplicate or lose balances if imported twice. Mitigation: schema idempotency key, reconciliation report, read-only cutover, backup, and kill switch before enabling minting.
12. **Bounty monopolization:** a single grinder can absorb the server's mint supply. Mitigation: low configurable bounty values, chunk/hour diminishing, grinder reduction, 20,000 TMT player cap, 100,000 TMT server cap, and 20% player-share cap.
