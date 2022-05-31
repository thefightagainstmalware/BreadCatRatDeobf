/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import net.minecraft.client.renderer.GlStateManager;

public class ColorUtils {
    private static final SkyblockColor SKYBLOCK_COLOR = new SkyblockColor();

    public static void bindWhite() {
        ColorUtils.bindColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void bindColor(float r, float g, float b, float a) {
        GlStateManager.func_179131_c((float)r, (float)g, (float)b, (float)a);
    }

    public static void bindColor(int r, int g, int b, int a) {
        ColorUtils.bindColor((float)r / 255.0f, (float)g / 255.0f, (float)b / 255.0f, (float)a / 255.0f);
    }

    private static void bindColor(int r, int g, int b, int a, float colorMultiplier) {
        ColorUtils.bindColor((float)r / 255.0f * colorMultiplier, (float)g / 255.0f * colorMultiplier, (float)b / 255.0f * colorMultiplier, (float)a / 255.0f);
    }

    public static void bindColor(int color) {
        ColorUtils.bindColor(ColorUtils.getRed(color), ColorUtils.getGreen(color), ColorUtils.getBlue(color), ColorUtils.getAlpha(color));
    }

    public static void bindColor(int color, float colorMultiplier) {
        ColorUtils.bindColor(ColorUtils.getRed(color), ColorUtils.getGreen(color), ColorUtils.getBlue(color), ColorUtils.getAlpha(color), colorMultiplier);
    }

    public static int setColorAlpha(int color, float alpha) {
        return ColorUtils.setColorAlpha(color, ColorUtils.getAlphaIntFromFloat(alpha));
    }

    public static int setColorAlpha(int color, int alpha) {
        return alpha << 24 | color & 0xFFFFFF;
    }

    public static int getRed(int color) {
        return color >> 16 & 0xFF;
    }

    public static int getGreen(int color) {
        return color >> 8 & 0xFF;
    }

    public static int getBlue(int color) {
        return color & 0xFF;
    }

    public static int getAlpha(int color) {
        return color >> 24 & 0xFF;
    }

    public static float getAlphaFloat(int color) {
        return (float)ColorUtils.getAlpha(color) / 255.0f;
    }

    public static int getAlphaIntFromFloat(float alpha) {
        return (int)(alpha * 255.0f);
    }

    public static int getColor(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    public static SkyblockColor getDummySkyblockColor(int color) {
        return ColorUtils.getDummySkyblockColor(SkyblockColor.ColorAnimation.NONE, color);
    }

    public static SkyblockColor getDummySkyblockColor(int r, int g, int b, int a) {
        return ColorUtils.getDummySkyblockColor(SkyblockColor.ColorAnimation.NONE, ColorUtils.getColor(r, g, b, a));
    }

    public static SkyblockColor getDummySkyblockColor(int r, int g, int b, float a) {
        return ColorUtils.getDummySkyblockColor(r, g, b, ColorUtils.getAlphaIntFromFloat(a));
    }

    public static SkyblockColor getDummySkyblockColor(SkyblockColor.ColorAnimation colorAnimation) {
        return ColorUtils.getDummySkyblockColor(colorAnimation, -1);
    }

    public static SkyblockColor getDummySkyblockColor(int color, boolean chroma) {
        return ColorUtils.getDummySkyblockColor(chroma ? SkyblockColor.ColorAnimation.CHROMA : SkyblockColor.ColorAnimation.NONE, color);
    }

    public static SkyblockColor getDummySkyblockColor(SkyblockColor.ColorAnimation colorAnimation, int color) {
        return SKYBLOCK_COLOR.setColorAnimation(colorAnimation).setColor(color);
    }
}

