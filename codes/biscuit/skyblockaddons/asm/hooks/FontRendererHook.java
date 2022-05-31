/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import codes.biscuit.skyblockaddons.utils.draw.DrawStateFontRenderer;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class FontRendererHook {
    private static final SkyblockColor CHROMA_COLOR = new SkyblockColor(-1).setColorAnimation(SkyblockColor.ColorAnimation.CHROMA);
    private static final DrawStateFontRenderer DRAW_CHROMA = new DrawStateFontRenderer(CHROMA_COLOR);
    private static final SkyblockColor CHROMA_COLOR_SHADOW = new SkyblockColor(-11184811).setColorAnimation(SkyblockColor.ColorAnimation.CHROMA);
    private static final DrawStateFontRenderer DRAW_CHROMA_SHADOW = new DrawStateFontRenderer(CHROMA_COLOR_SHADOW);
    private static DrawStateFontRenderer currentDrawState = null;
    private static final MaxSizeHashMap<String, Boolean> stringsWithChroma = new MaxSizeHashMap(1000);

    public static void changeTextColor() {
        if (currentDrawState != null && currentDrawState.shouldManuallyRecolorFont()) {
            FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
            currentDrawState.bindAnimatedColor(fontRenderer.field_78295_j, fontRenderer.field_78296_k);
        }
    }

    public static void setupFeatureFont(Feature feature) {
        if (SkyblockAddons.getInstance().getConfigValues().getChromaMode() == EnumUtils.ChromaMode.FADE && SkyblockAddons.getInstance().getConfigValues().getChromaFeatures().contains((Object)feature)) {
            DRAW_CHROMA.setupMulticolorFeature(SkyblockAddons.getInstance().getConfigValues().getGuiScale(feature));
            DRAW_CHROMA_SHADOW.setupMulticolorFeature(SkyblockAddons.getInstance().getConfigValues().getGuiScale(feature));
        }
    }

    public static void endFeatureFont() {
        DRAW_CHROMA.endMulticolorFeature();
        DRAW_CHROMA_SHADOW.endMulticolorFeature();
    }

    public static boolean shouldOverridePatcher(String s) {
        if (stringsWithChroma.get(s) != null) {
            return (Boolean)stringsWithChroma.get(s);
        }
        boolean hasChroma = false;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) != '\u00a7' || ++i >= s.length() || s.charAt(i) != 'z' && s.charAt(i) != 'Z') continue;
            hasChroma = true;
            break;
        }
        stringsWithChroma.put(s, hasChroma);
        return hasChroma;
    }

    public static void beginRenderString(boolean shadow) {
        float alpha;
        float f = alpha = Minecraft.func_71410_x() == null || Minecraft.func_71410_x().field_71466_p == null ? 1.0f : Minecraft.func_71410_x().field_71466_p.field_78305_q;
        if (shadow) {
            currentDrawState = DRAW_CHROMA_SHADOW;
            CHROMA_COLOR_SHADOW.setColor((int)(255.0f * alpha) << 24 | 0x555555);
        } else {
            currentDrawState = DRAW_CHROMA;
            CHROMA_COLOR.setColor((int)(255.0f * alpha) << 24 | 0xFFFFFF);
        }
        if (SkyblockAddons.isFullyInitialized()) {
            currentDrawState.loadFeatureColorEnv();
        }
    }

    public static void restoreChromaState() {
        if (SkyblockAddons.isFullyInitialized()) {
            currentDrawState.restoreColorEnv();
        }
    }

    public static void toggleChromaOn() {
        if (SkyblockAddons.isFullyInitialized()) {
            currentDrawState.newColorEnv().bindActualColor();
        }
    }

    public static void endRenderString() {
        if (SkyblockAddons.isFullyInitialized()) {
            currentDrawState.endColorEnv();
        }
    }

    public static class MaxSizeHashMap<K, V>
    extends LinkedHashMap<K, V> {
        private final int maxSize;

        public MaxSizeHashMap(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return this.size() > this.maxSize;
        }
    }
}

