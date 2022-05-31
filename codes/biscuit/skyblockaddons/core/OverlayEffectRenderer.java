/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.particle.EffectRenderer
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.core;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.hooks.EffectRendererHook;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import codes.biscuit.skyblockaddons.utils.draw.DrawState3D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class OverlayEffectRenderer {
    protected static DrawState3D DRAW_PARTICLE = new DrawState3D(new SkyblockColor(-1), 7, DefaultVertexFormats.field_181704_d, true, true);
    private Set<EntityFX>[][] overlayParticles;
    protected Feature feature = null;

    private void initParticles() {
        this.overlayParticles = new Set[4][2];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.overlayParticles[i][j] = new HashSet<EntityFX>();
            }
        }
    }

    public OverlayEffectRenderer() {
        this.initParticles();
        EffectRendererHook.registerOverlay(this);
    }

    public void addParticle(EntityFX particle) {
        int j;
        if (particle == null) {
            return;
        }
        int i = particle.func_70537_b();
        int n = j = particle.func_174838_j() != 1.0f ? 0 : 1;
        if (this.overlayParticles[i][j].size() >= 100) {
            Iterator<EntityFX> itr = this.overlayParticles[i][j].iterator();
            itr.next();
            itr.remove();
        }
        this.overlayParticles[i][j].add(particle);
    }

    public void clearParticles() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.overlayParticles[i][j].clear();
            }
        }
    }

    public boolean shouldRenderOverlay() {
        return SkyblockAddons.getInstance().getUtils().isOnSkyblock();
    }

    public void setupRenderEnvironment() {
        if (this.feature != null) {
            DRAW_PARTICLE.setColor(SkyblockAddons.getInstance().getConfigValues().getSkyblockColor(this.feature)).newColorEnv();
        }
    }

    public void endRenderEnvironment() {
        DRAW_PARTICLE.endColorEnv();
    }

    public void setupRenderEffect(EntityFX effect) {
    }

    public void endRenderEffect(EntityFX effect) {
    }

    public void renderOverlayParticles(EffectRendererHook.OverlayInfo info) {
        if (!this.shouldRenderOverlay()) {
            return;
        }
        float partialTicks = info.getPartialTicks();
        float rotationX = info.getRotationX();
        float rotationZ = info.getRotationZ();
        float rotationYZ = info.getRotationYZ();
        float rotationXY = info.getRotationXY();
        float rotationXZ = info.getRotationXZ();
        TextureManager renderer = info.getRenderer();
        Entity entity = info.getRenderViewEntity();
        WorldRenderer worldRenderer = info.getWorldRenderer();
        ResourceLocation particleTextures = EffectRenderer.field_110737_b;
        this.setupRenderEnvironment();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.overlayParticles[i][j].removeIf(entityFX -> entityFX.field_70128_L);
                if (this.overlayParticles[i][j].isEmpty()) continue;
                GlStateManager.func_179132_a((j == 1 ? 1 : 0) != 0);
                if (i == 1) {
                    renderer.func_110577_a(TextureMap.field_110575_b);
                } else {
                    renderer.func_110577_a(particleTextures);
                }
                GlStateManager.func_179142_g();
                for (EntityFX effect : this.overlayParticles[i][j]) {
                    try {
                        DRAW_PARTICLE.beginWorldRenderer().bindColor((float)effect.field_70165_t, (float)effect.field_70163_u, (float)effect.field_70161_v);
                        this.setupRenderEffect(effect);
                        effect.func_180434_a(worldRenderer, entity, partialTicks, rotationX, rotationXZ, rotationZ, rotationYZ, rotationXY);
                        this.endRenderEffect(effect);
                        DRAW_PARTICLE.draw();
                    }
                    catch (Throwable throwable) {}
                }
                GlStateManager.func_179119_h();
            }
        }
        this.endRenderEnvironment();
    }
}

