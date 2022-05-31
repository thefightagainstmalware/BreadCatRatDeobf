/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 */
package codes.biscuit.skyblockaddons.core.chroma;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ManualChromaManager {
    private static boolean coloringTextChroma;
    private static float featureScale;
    private static float[] defaultColorHSB;

    public static void renderingText(Feature feature) {
        if (SkyblockAddons.getInstance().getConfigValues().getChromaMode() == EnumUtils.ChromaMode.FADE && SkyblockAddons.getInstance().getConfigValues().getChromaFeatures().contains((Object)feature)) {
            coloringTextChroma = true;
            featureScale = SkyblockAddons.getInstance().getConfigValues().getGuiScale(feature);
        }
    }

    public static int getChromaColor(float x, float y, int alpha) {
        return ManualChromaManager.getChromaColor(x, y, defaultColorHSB, alpha);
    }

    public static int getChromaColor(float x, float y, float[] currentHSB, int alpha) {
        if (SkyblockAddons.getInstance().getConfigValues().getChromaMode() == EnumUtils.ChromaMode.ALL_SAME_COLOR) {
            x = 0.0f;
            y = 0.0f;
        }
        if (coloringTextChroma) {
            x *= featureScale;
            y *= featureScale;
        }
        int scale = new ScaledResolution(Minecraft.func_71410_x()).func_78325_e();
        float chromaSize = SkyblockAddons.getInstance().getConfigValues().getChromaSize().floatValue() * ((float)Minecraft.func_71410_x().field_71443_c / 100.0f);
        float chromaSpeed = SkyblockAddons.getInstance().getConfigValues().getChromaSpeed().floatValue() / 360.0f;
        float ticks = (float)SkyblockAddons.getInstance().getNewScheduler().getTotalTicks() + Utils.getPartialTicks();
        float timeOffset = ticks * chromaSpeed;
        float newHue = (((x *= (float)scale) + (y *= (float)scale)) / chromaSize - timeOffset) % 1.0f;
        float saturation = SkyblockAddons.getInstance().getConfigValues().getChromaSaturation().floatValue();
        float brightness = SkyblockAddons.getInstance().getConfigValues().getChromaBrightness().floatValue() * currentHSB[2];
        return ColorUtils.setColorAlpha(Color.HSBtoRGB(newHue, saturation, brightness), alpha);
    }

    public static int getChromaColor(float x, float y, float z, int alpha) {
        if (SkyblockAddons.getInstance().getConfigValues().getChromaMode() == EnumUtils.ChromaMode.ALL_SAME_COLOR) {
            x = 0.0f;
            y = 0.0f;
            z = 0.0f;
        }
        float chromaSize = SkyblockAddons.getInstance().getConfigValues().getChromaSize().floatValue() * ((float)Minecraft.func_71410_x().field_71443_c / 100.0f);
        float chromaSpeed = SkyblockAddons.getInstance().getConfigValues().getChromaSpeed().floatValue() / 360.0f;
        float ticks = (float)SkyblockAddons.getInstance().getNewScheduler().getTotalTicks() + Utils.getPartialTicks();
        float timeOffset = ticks * chromaSpeed;
        float newHue = ((x - y + z) / (chromaSize / 20.0f) - timeOffset) % 1.0f;
        float saturation = SkyblockAddons.getInstance().getConfigValues().getChromaSaturation().floatValue();
        float brightness = SkyblockAddons.getInstance().getConfigValues().getChromaBrightness().floatValue();
        return ColorUtils.setColorAlpha(Color.HSBtoRGB(newHue, saturation, brightness), alpha);
    }

    public static void doneRenderingText() {
        coloringTextChroma = false;
        featureScale = 1.0f;
    }

    public static boolean isColoringTextChroma() {
        return coloringTextChroma;
    }

    public static void setColoringTextChroma(boolean coloringTextChroma) {
        ManualChromaManager.coloringTextChroma = coloringTextChroma;
    }

    public static float getFeatureScale() {
        return featureScale;
    }

    static {
        defaultColorHSB = new float[]{0.0f, 0.75f, 0.9f};
    }
}

