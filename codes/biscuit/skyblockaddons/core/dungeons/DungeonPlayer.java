/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core.dungeons;

import codes.biscuit.skyblockaddons.core.dungeons.DungeonClass;
import codes.biscuit.skyblockaddons.features.dungeonmap.MapMarker;
import codes.biscuit.skyblockaddons.utils.ColorCode;

public class DungeonPlayer {
    private String name;
    private DungeonClass dungeonClass;
    private ColorCode healthColor;
    private MapMarker mapMarker;
    private int health;

    public DungeonPlayer(String name) {
        this.name = name;
    }

    public DungeonPlayer(String name, DungeonClass dungeonClass, ColorCode healthColor, int health) {
        this.name = name;
        this.dungeonClass = dungeonClass;
        this.healthColor = healthColor;
        this.health = health;
    }

    public boolean isLow() {
        return this.healthColor == ColorCode.YELLOW;
    }

    public boolean isCritical() {
        return this.healthColor == ColorCode.RED && this.health > 0;
    }

    public boolean isGhost() {
        return this.health == 0;
    }

    public String getName() {
        return this.name;
    }

    public DungeonClass getDungeonClass() {
        return this.dungeonClass;
    }

    public ColorCode getHealthColor() {
        return this.healthColor;
    }

    public MapMarker getMapMarker() {
        return this.mapMarker;
    }

    public int getHealth() {
        return this.health;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDungeonClass(DungeonClass dungeonClass) {
        this.dungeonClass = dungeonClass;
    }

    public void setHealthColor(ColorCode healthColor) {
        this.healthColor = healthColor;
    }

    public void setMapMarker(MapMarker mapMarker) {
        this.mapMarker = mapMarker;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

