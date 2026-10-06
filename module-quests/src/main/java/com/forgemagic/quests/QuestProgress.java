package com.forgemagic.quests;

public record QuestProgress(String questId, String mobType, int required, int completed) {
    public QuestProgress {
        if (questId == null || questId.isBlank() || mobType == null || mobType.isBlank() || required < 1 || completed < 0)
            throw new IllegalArgumentException("invalid quest");
    }
    public QuestProgress onValidKill(String killedMobType) {
        if (completed >= required || !mobType.equals(killedMobType)) return this;
        return new QuestProgress(questId, mobType, required, completed + 1);
    }
    public boolean complete() { return completed >= required; }
}
