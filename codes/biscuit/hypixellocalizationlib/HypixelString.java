/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.hypixellocalizationlib;

public enum HypixelString {
    SKYBLOCK_COOP(103356),
    SKYBLOCK_GUEST(103354),
    ENCHANTING(45822),
    COMBAT(99526),
    FARMING(99528),
    FISHING(99530),
    MINING(99532),
    FORAGING(99534),
    ALCHEMY(99538),
    CARPENTRY(99540),
    RUNECRAFTING(99542);

    private int id;

    private HypixelString(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

