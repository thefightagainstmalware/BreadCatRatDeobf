/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core;

import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.EnumUtils;

public class GuiFeatureData {
    private ColorCode defaultColor = null;
    private EnumUtils.DrawType drawType = null;
    private boolean colorsRestricted;

    public GuiFeatureData(ColorCode defaultColor) {
        this(defaultColor, false);
    }

    public GuiFeatureData(ColorCode defaultColor, boolean colorsRestricted) {
        this.defaultColor = defaultColor;
        this.colorsRestricted = colorsRestricted;
    }

    public GuiFeatureData(EnumUtils.DrawType drawType) {
        this(drawType, false);
    }

    public GuiFeatureData(EnumUtils.DrawType drawType, ColorCode defaultColor) {
        this(drawType, defaultColor, false);
    }

    private GuiFeatureData(EnumUtils.DrawType drawType, boolean colorsRestricted) {
        this.drawType = drawType;
        this.colorsRestricted = colorsRestricted;
    }

    public GuiFeatureData(EnumUtils.DrawType drawType, ColorCode defaultColor, boolean colorsRestricted) {
        this.drawType = drawType;
        this.defaultColor = defaultColor;
        this.colorsRestricted = colorsRestricted;
    }

    public ColorCode getDefaultColor() {
        return this.defaultColor;
    }

    public EnumUtils.DrawType getDrawType() {
        return this.drawType;
    }

    public boolean isColorsRestricted() {
        return this.colorsRestricted;
    }
}

