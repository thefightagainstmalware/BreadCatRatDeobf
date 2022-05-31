/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features;

import codes.biscuit.skyblockaddons.core.SkillType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillXpManager {
    private static final HashMap<SkillType, SkillXp> skillLevelXp = new HashMap();
    private static final HashMap<SkillType, Integer> playerSkillLevel = new HashMap();

    public int getSkillXpForNextLevel(SkillType type, int level) {
        return skillLevelXp.containsKey((Object)type) ? skillLevelXp.get((Object)type).getXpForNextLevel(level) : 0;
    }

    public void initialize(JsonInput input) {
        for (Map.Entry skill : input.entrySet()) {
            skillLevelXp.put((SkillType)((Object)skill.getKey()), new SkillXp((List)skill.getValue()));
        }
    }

    public void setSkillLevel(SkillType type, int level) {
        playerSkillLevel.put(type, level);
    }

    public int getSkillLevel(SkillType type) {
        return playerSkillLevel.getOrDefault((Object)type, -1);
    }

    private static class SkillXp {
        private final List<Integer> cumulativeXp;
        private final List<Integer> xpForNext;
        private final int maxLevel;

        public SkillXp(List<Integer> cumulativeXp) {
            this.cumulativeXp = cumulativeXp;
            this.maxLevel = cumulativeXp.size() - 1;
            this.xpForNext = new ArrayList<Integer>(cumulativeXp.size());
            for (int i = 0; i < cumulativeXp.size() - 1; ++i) {
                this.xpForNext.add(cumulativeXp.get(i + 1) - cumulativeXp.get(i));
            }
        }

        public int getXpForNextLevel(int level) {
            return level >= this.maxLevel || level < 0 ? 0 : this.xpForNext.get(level);
        }

        public int getMaxLevel() {
            return this.maxLevel;
        }
    }

    public static class JsonInput
    extends HashMap<SkillType, List<Integer>> {
    }
}

