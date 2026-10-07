# Hall of Tadhana — implementation design

## Safety default

The Hall is disabled until the owner fills `class_change.yml` with the exact world and coordinates. The plugin must log a loud warning and never guess a world or silently relocate players.

## Layout

- **Arrival plaza:** central safe floor, no PvP, no mob spawning, readable notice board.
- **Odds board:** route tiers, class restrictions, trade dependencies, permanence warning.
- **Five booths:** Lumber, Miner, Craftsman, Marksman, Healer. Each booth contains a test dummy area, a short description, and a preview of beginner skills.
- **Confirmation dais:** the only class-selection interaction. Java uses a timed confirmation command; Bedrock will use a ModalForm adapter when Floodgate is connected.
- **Route reveal stage:** the seed is persisted before any animation; no client-side random result.
- **Exit/appeal desk:** informational only; it cannot change a permanent class choice.

## Runtime behavior

1. `/class hall` shows the configured location and rules; when enabled it directs the player to the Hall.
2. A player may preview, but preview has no wallet, inventory, XP, or permanent state effect.
3. `/class choose <class>` is the final action and remains permanent for the season.
4. A re-login, crash, or duplicate command cannot change the result.
5. Missing coordinates keep the Hall disabled and fail loudly.

## Owner configuration

Edit `plugins/ForgeSoloSubseason/class_change.yml` after first boot:

```yaml
hall:
  enabled: true
  world: "world"
  x: 100
  y: 65
  z: -200
  radius: 12
```

The actual protection-region integration remains provider-specific; keep the Hall fail-closed until the claims adapter is installed.
