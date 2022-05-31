/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.chroma.ManualChromaManager;
import codes.biscuit.skyblockaddons.shader.ShaderManager;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import java.awt.Color;
import java.util.LinkedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class SkyblockColor {
    private static final Tessellator tessellator = Tessellator.func_178181_a();
    private static final WorldRenderer worldRenderer = tessellator.func_178180_c();
    private static final int DEFAULT_COLOR = -1;
    private ColorAnimation colorAnimation = ColorAnimation.NONE;
    private LinkedList<Integer> colors = new LinkedList();

    public SkyblockColor() {
        this(-1);
    }

    public SkyblockColor(int color) {
        this.colors.add(color);
    }

    public SkyblockColor(int color, float alpha) {
        this.colors.add(ColorUtils.setColorAlpha(color, alpha));
    }

    public SkyblockColor(int r, int g, int b, int a) {
        this.colors.add(ColorUtils.getColor(r, g, b, a));
    }

    public SkyblockColor(int r, int g, int b, float a) {
        this.colors.add(ColorUtils.getColor(r, g, b, ColorUtils.getAlphaIntFromFloat(a)));
    }

    public int getColorAtPosition(float x, float y) {
        if (this.colorAnimation == ColorAnimation.CHROMA) {
            return ManualChromaManager.getChromaColor(x, y, ColorUtils.getAlpha(this.getColor()));
        }
        return this.colors.get(0);
    }

    public int getTintAtPosition(float x, float y) {
        if (this.colorAnimation == ColorAnimation.CHROMA) {
            return ManualChromaManager.getChromaColor(x, y, Color.RGBtoHSB(ColorUtils.getRed(this.getColor()), ColorUtils.getGreen(this.getColor()), ColorUtils.getBlue(this.getColor()), null), ColorUtils.getAlpha(this.getColor()));
        }
        return this.colors.get(0);
    }

    public int getColorAtPosition(double x, double y, double z) {
        return this.getColorAtPosition((float)x, (float)y, (float)z);
    }

    public int getColorAtPosition(float x, float y, float z) {
        if (this.colorAnimation == ColorAnimation.CHROMA) {
            return ManualChromaManager.getChromaColor(x, y, z, ColorUtils.getAlpha(this.getColor()));
        }
        return this.colors.get(0);
    }

    public SkyblockColor setColor(int color) {
        return this.setColor(0, color);
    }

    public SkyblockColor setColor(int index, int color) {
        if (index >= this.colors.size()) {
            this.colors.add(color);
        } else {
            this.colors.set(index, color);
        }
        return this;
    }

    public boolean isMulticolor() {
        return this.colorAnimation != ColorAnimation.NONE;
    }

    public boolean isPositionalMulticolor() {
        return this.colorAnimation != ColorAnimation.NONE && SkyblockAddons.getInstance().getConfigValues().getChromaMode() != EnumUtils.ChromaMode.ALL_SAME_COLOR;
    }

    public int getColor() {
        return this.getColorSafe(0);
    }

    private int getColorSafe(int index) {
        while (index >= this.colors.size()) {
            this.colors.add(-1);
        }
        return this.colors.get(index);
    }

    public boolean drawMulticolorManually() {
        return this.colorAnimation == ColorAnimation.CHROMA && !SkyblockColor.shouldUseChromaShaders();
    }

    public boolean drawMulticolorUsingShader() {
        return this.colorAnimation == ColorAnimation.CHROMA && SkyblockColor.shouldUseChromaShaders();
    }

    public static boolean shouldUseChromaShaders() {
        ConfigValues config = SkyblockAddons.getInstance().getConfigValues();
        return config.getChromaMode() != EnumUtils.ChromaMode.ALL_SAME_COLOR && ShaderManager.getInstance().areShadersSupported() && config.isEnabled(Feature.USE_NEW_CHROMA_EFFECT);
    }

    public ColorAnimation getColorAnimation() {
        return this.colorAnimation;
    }

    public SkyblockColor setColorAnimation(ColorAnimation colorAnimation) {
        this.colorAnimation = colorAnimation;
        return this;
    }

    public static enum ColorAnimation {
        NONE,
        CHROMA;

    }
}

