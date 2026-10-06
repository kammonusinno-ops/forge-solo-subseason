# Milestone Report — M3 to M5 Foundations

## Summary

The project now contains platform-free foundations for class restrictions, the server-owned season clock, one-time route rolls, and the mana/skill scaling formulas. The verified server baseline is Minecraft/Paper 26.2 build 129 with Java 21; Geyser build 1248 is the current crossplay reference.

## M3 — classes and restrictions

`ClassId`, `BlockKind`, `RestrictionDecision`, `ClassRestrictionService`, and `DefaultClassRestrictionService` implement permanent class choice, grind-phase exemption, Lumber wood access, Miner ore access, Craftsman crafting-table access, and Marksman bow/crossbow access. Tests cover the acceptance rules and permanence.

Paper event adapters, recipe removal, claims integration, explosions/fire/water/piston policy, and Bedrock manual testing remain open because exact Paper APIs and the protection provider must be verified before implementation.

## M4 — season and route foundations

`FixedSeasonClock` models a server-owned 20-minute Minecraft day and seven-day grind period without using world time. `DefaultRouteRollService` uses the Design Bible odds and persists the player’s first seed/roll in memory so a second roll is rejected. Tests cover clock expiry, deterministic seeds, and reroll prevention.

Durable seed persistence, class-change Hall UI, reveal animation, cohort storage, and config-driven coordinates remain open.

## M5 — mana and skill foundation

`SkillTier`, `SkillDefinition`, `ManaState`, and `SkillMath` implement the specified mana maximum, capped mana cost, effect scaling, cooldown scaling, and 40% cooldown floor. Tests cover the formulas and rarity behavior.

The 75 route skills, skill wand, cooldown storage, effects, action-bar UI, and Bedrock forms remain open.

## Tests

`./gradlew clean test --no-daemon` passed with all tests successful.

## Next

Continue with M6 mob coins and quests, keeping reward creation routed through `LedgerService` and anti-farm checks server-authoritative.
