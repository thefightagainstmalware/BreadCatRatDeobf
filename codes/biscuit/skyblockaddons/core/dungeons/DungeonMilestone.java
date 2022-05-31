/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core.dungeons;

import codes.biscuit.skyblockaddons.core.dungeons.DungeonClass;

public class DungeonMilestone {
    private DungeonClass dungeonClass;
    private String level;
    private String value;

    public DungeonMilestone(DungeonClass dungeonClass) {
        this(dungeonClass, "\u24ff", "0");
    }

    public DungeonMilestone(DungeonClass dungeonClass, String level, String value) {
        this.dungeonClass = dungeonClass;
        this.level = level;
        this.value = value;
    }

    public DungeonClass getDungeonClass() {
        return this.dungeonClass;
    }

    public String getLevel() {
        return this.level;
    }

    public String getValue() {
        return this.value;
    }
}

