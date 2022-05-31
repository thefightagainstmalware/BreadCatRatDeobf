/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.utils.objects;

import codes.biscuit.skyblockaddons.utils.objects.FloatPair;

public class FloatPairString {
    private FloatPair floatPair;
    private String enchant;

    public FloatPairString(float x, float y, String enchant) {
        this.floatPair = new FloatPair(x, y);
        this.enchant = enchant;
    }

    public float getX() {
        return this.floatPair.getX();
    }

    public float getY() {
        return this.floatPair.getY();
    }

    public FloatPair getFloatPair() {
        return this.floatPair;
    }

    public String getEnchant() {
        return this.enchant;
    }
}

