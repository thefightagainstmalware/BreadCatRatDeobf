/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.backpacks;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Set;

public enum BackpackColor {
    BLACK(29, 29, 33),
    RED(176, 46, 38),
    GREEN(94, 124, 22),
    BROWN(131, 84, 50),
    BLUE(60, 68, 170),
    PURPLE(137, 50, 184),
    CYAN(22, 156, 156),
    LIGHT_GREY(157, 157, 151),
    GREY(71, 79, 82),
    PINK(243, 139, 170),
    LIME(128, 199, 31),
    YELLOW(254, 216, 61),
    LIGHT_BLUE(58, 179, 218),
    MAGENTA(199, 78, 189),
    ORANGE(249, 128, 29),
    WHITE(255, 255, 255);

    private int r;
    private int g;
    private int b;
    private static Set<BackpackColor> darkColors;

    private BackpackColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getR() {
        return (float)this.r / 255.0f;
    }

    public float getG() {
        return (float)this.g / 255.0f;
    }

    public float getB() {
        return (float)this.b / 255.0f;
    }

    public int getInventoryTextColor() {
        int rgb = 0x404040;
        if (darkColors.contains((Object)this)) {
            rgb = new Color(219, 219, 219, 255).getRGB();
        }
        return rgb;
    }

    static {
        darkColors = EnumSet.of(BLACK, new BackpackColor[]{PURPLE, GREEN, MAGENTA, RED, BROWN, BLUE, GREY});
    }
}

