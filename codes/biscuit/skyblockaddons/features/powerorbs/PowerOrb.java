/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.features.powerorbs;

import net.minecraft.util.ResourceLocation;

public enum PowerOrb {
    RADIANT("\u00a7aRadiant", 0.01, 0.0, 0, 0.0, 324, "radiant"),
    MANA_FLUX("\u00a79Mana Flux", 0.02, 0.5, 10, 0.0, 324, "manaflux"),
    OVERFLUX("\u00a75Overflux", 0.025, 1.0, 25, 0.05, 324, "overflux"),
    PLASMAFLUX("\u00a7d\u00a7lPlasmaflux", 0.03, 1.25, 35, 0.075, 400, "plasmaflux");

    private String display;
    private double healthRegen;
    private double manaRegen;
    private int strength;
    private double healIncrease;
    private int rangeSquared;
    private ResourceLocation resourceLocation;

    private PowerOrb(String display, double healthRegen, double manaRegen, int strength, double healIncrease, int rangeSquared, String resourcePath) {
        this.display = display;
        this.healthRegen = healthRegen;
        this.manaRegen = manaRegen;
        this.strength = strength;
        this.healIncrease = healIncrease;
        this.rangeSquared = rangeSquared;
        this.resourceLocation = new ResourceLocation("skyblockaddons", "powerorbs/" + resourcePath + ".png");
    }

    public boolean isInRadius(double distanceSquared) {
        return distanceSquared <= (double)this.rangeSquared;
    }

    public static PowerOrb getByDisplayname(String displayName) {
        for (PowerOrb powerOrb : PowerOrb.values()) {
            if (!displayName.startsWith(powerOrb.display)) continue;
            return powerOrb;
        }
        return null;
    }

    public String getDisplay() {
        return this.display;
    }

    public double getHealthRegen() {
        return this.healthRegen;
    }

    public double getManaRegen() {
        return this.manaRegen;
    }

    public int getStrength() {
        return this.strength;
    }

    public double getHealIncrease() {
        return this.healIncrease;
    }

    public int getRangeSquared() {
        return this.rangeSquared;
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
}

