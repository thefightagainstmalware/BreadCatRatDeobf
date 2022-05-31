/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core;

public enum Attribute {
    DEFENCE(0),
    HEALTH(100),
    MAX_HEALTH(100),
    MANA(100),
    MAX_MANA(100),
    FUEL(3000),
    MAX_FUEL(3000),
    OVERFLOW_MANA(20);

    private final int defaultValue;

    private Attribute(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getDefaultValue() {
        return this.defaultValue;
    }
}

