/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.dragontracker;

import codes.biscuit.skyblockaddons.features.dragontracker.DragonType;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonsSince;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DragonTrackerData {
    private List<DragonType> recentDragons = new LinkedList<DragonType>();
    private Map<DragonsSince, Integer> dragonsSince = new EnumMap<DragonsSince, Integer>(DragonsSince.class);
    private int eyesPlaced = 0;

    public List<DragonType> getRecentDragons() {
        return this.recentDragons;
    }

    public Map<DragonsSince, Integer> getDragonsSince() {
        return this.dragonsSince;
    }

    public int getEyesPlaced() {
        return this.eyesPlaced;
    }

    public void setEyesPlaced(int eyesPlaced) {
        this.eyesPlaced = eyesPlaced;
    }
}

