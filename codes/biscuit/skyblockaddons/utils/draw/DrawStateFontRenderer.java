/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.utils.draw;

import codes.biscuit.skyblockaddons.core.chroma.MulticolorShaderManager;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import codes.biscuit.skyblockaddons.utils.draw.DrawState2D;
import net.minecraft.client.renderer.GlStateManager;

public class DrawStateFontRenderer
extends DrawState2D {
    protected boolean multicolorFeatureOverride;
    protected boolean isActive;
    protected float featureScale = 1.0f;

    public DrawStateFontRenderer(SkyblockColor theColor) {
        super(theColor, true, false);
    }

    public DrawStateFontRenderer setupMulticolorFeature(float theFeatureScale) {
        if (this.color.drawMulticolorManually()) {
            this.featureScale = theFeatureScale;
        }
        this.multicolorFeatureOverride = true;
        return this;
    }

    public DrawStateFontRenderer endMulticolorFeature() {
        if (this.color.drawMulticolorManually()) {
            this.featureScale = 1.0f;
        }
        this.multicolorFeatureOverride = false;
        return this;
    }

    public DrawStateFontRenderer loadFeatureColorEnv() {
        if (this.multicolorFeatureOverride) {
            this.newColorEnv();
        }
        return this;
    }

    public DrawStateFontRenderer restoreColorEnv() {
        if (this.color.drawMulticolorUsingShader() && !this.multicolorFeatureOverride) {
            MulticolorShaderManager.end();
        }
        this.isActive = false;
        return this;
    }

    @Override
    public DrawStateFontRenderer newColorEnv() {
        super.newColorEnv();
        this.isActive = true;
        return this;
    }

    @Override
    public DrawStateFontRenderer endColorEnv() {
        super.endColorEnv();
        this.isActive = false;
        return this;
    }

    @Override
    public DrawStateFontRenderer bindAnimatedColor(float x, float y) {
        int colorInt = this.color.getTintAtPosition(x * this.featureScale, y * this.featureScale);
        GlStateManager.func_179131_c((float)((float)ColorUtils.getRed(colorInt) / 255.0f), (float)((float)ColorUtils.getGreen(colorInt) / 255.0f), (float)((float)ColorUtils.getBlue(colorInt) / 255.0f), (float)((float)ColorUtils.getAlpha(colorInt) / 255.0f));
        return this;
    }

    public boolean shouldManuallyRecolorFont() {
        return (this.multicolorFeatureOverride || this.isActive) && this.color.drawMulticolorManually();
    }
}

