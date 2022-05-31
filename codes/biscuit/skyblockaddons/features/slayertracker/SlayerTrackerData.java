/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.slayertracker;

import codes.biscuit.skyblockaddons.features.slayertracker.SlayerBoss;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerDrop;
import java.util.EnumMap;
import java.util.Map;

public class SlayerTrackerData {
    private Map<SlayerBoss, Integer> slayerKills = new EnumMap<SlayerBoss, Integer>(SlayerBoss.class);
    private Map<SlayerDrop, Integer> slayerDropCounts = new EnumMap<SlayerDrop, Integer>(SlayerDrop.class);
    private SlayerBoss lastKilledBoss;

    public Map<SlayerBoss, Integer> getSlayerKills() {
        return this.slayerKills;
    }

    public Map<SlayerDrop, Integer> getSlayerDropCounts() {
        return this.slayerDropCounts;
    }

    public SlayerBoss getLastKilledBoss() {
        return this.lastKilledBoss;
    }

    public void setLastKilledBoss(SlayerBoss lastKilledBoss) {
        this.lastKilledBoss = lastKilledBoss;
    }
}

