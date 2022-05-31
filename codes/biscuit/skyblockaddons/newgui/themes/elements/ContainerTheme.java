/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.themes.elements;

import codes.biscuit.skyblockaddons.utils.SkyblockColor;

public class ContainerTheme {
    private int rounding;
    private SkyblockColor color;

    public ContainerTheme(int rounding, SkyblockColor color) {
        this.rounding = rounding;
        this.color = color;
    }

    public int getRounding() {
        return this.rounding;
    }

    public SkyblockColor getColor() {
        return this.color;
    }
}

