/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.features.fishParticles;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.OverlayEffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GlStateManager;

public class FishParticleOverlay
extends OverlayEffectRenderer {
    private boolean biggerWakeCache;

    public FishParticleOverlay() {
        this.feature = Feature.FISHING_PARTICLE_OVERLAY;
    }

    @Override
    public boolean shouldRenderOverlay() {
        return super.shouldRenderOverlay() && SkyblockAddons.getInstance().getConfigValues().isEnabled(this.feature);
    }

    @Override
    public void setupRenderEnvironment() {
        super.setupRenderEnvironment();
        GlStateManager.func_179143_c((int)515);
        this.biggerWakeCache = SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.BIGGER_WAKE);
    }

    @Override
    public void endRenderEnvironment() {
        super.endRenderEnvironment();
        GlStateManager.func_179143_c((int)515);
    }

    @Override
    public void setupRenderEffect(EntityFX effect) {
        if (this.biggerWakeCache) {
            effect.field_70544_f *= 2.0f;
            effect.field_70163_u += 0.1;
            effect.field_70167_r += 0.1;
        }
    }

    @Override
    public void endRenderEffect(EntityFX effect) {
        if (this.biggerWakeCache) {
            effect.field_70544_f /= 2.0f;
            effect.field_70163_u -= 0.1;
            effect.field_70167_r -= 0.1;
        }
    }
}

