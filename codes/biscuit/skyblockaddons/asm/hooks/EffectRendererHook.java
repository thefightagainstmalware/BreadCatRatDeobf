/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.particle.EntityAuraFX
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.client.particle.EntityFishWakeFX
 *  net.minecraft.client.renderer.ActiveRenderInfo
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.entity.Entity
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.OverlayEffectRenderer;
import codes.biscuit.skyblockaddons.features.fishParticles.FishParticleManager;
import codes.biscuit.skyblockaddons.features.healingcircle.HealingCircleManager;
import codes.biscuit.skyblockaddons.features.healingcircle.HealingCircleParticle;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFishWakeFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;

public class EffectRendererHook {
    private static Set<OverlayEffectRenderer> effectRenderers = new HashSet<OverlayEffectRenderer>();

    public static void onAddParticle(EntityFX entity) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        Minecraft mc = Minecraft.func_71410_x();
        EntityPlayerSP player = mc.field_71439_g;
        if (main.getUtils().isOnSkyblock()) {
            if (main.getUtils().isInDungeon() && main.getConfigValues().isEnabled(Feature.SHOW_HEALING_CIRCLE_WALL) && entity instanceof EntityAuraFX && entity.field_70163_u % 1.0 == 0.0) {
                HealingCircleManager.addHealingCircleParticle(new HealingCircleParticle(entity.field_70165_t, entity.field_70161_v));
            } else if (player != null && player.field_71104_cf != null && main.getConfigValues().isEnabled(Feature.FISHING_PARTICLE_OVERLAY) && entity instanceof EntityFishWakeFX) {
                FishParticleManager.onFishWakeSpawn((EntityFishWakeFX)entity);
            }
        }
    }

    public static void renderParticleOverlays(float partialTicks) {
        OverlayInfo info = new OverlayInfo(partialTicks);
        for (OverlayEffectRenderer renderer : effectRenderers) {
            renderer.renderOverlayParticles(info);
        }
    }

    public static void registerOverlay(OverlayEffectRenderer renderer) {
        effectRenderers.add(renderer);
    }

    public static class OverlayInfo {
        private final float rotationX = ActiveRenderInfo.func_178808_b();
        private final float rotationZ = ActiveRenderInfo.func_178803_d();
        private final float rotationYZ = ActiveRenderInfo.func_178805_e();
        private final float rotationXY = ActiveRenderInfo.func_178807_f();
        private final float rotationXZ = ActiveRenderInfo.func_178809_c();
        private final float partialTicks;
        private final TextureManager renderer;
        private final WorldRenderer worldRenderer;
        private final Entity renderViewEntity;

        public OverlayInfo(float thePartialTicks) {
            this.partialTicks = thePartialTicks;
            this.renderer = Minecraft.func_71410_x().field_71452_i.field_78877_c;
            this.worldRenderer = Tessellator.func_178181_a().func_178180_c();
            this.renderViewEntity = Minecraft.func_71410_x().func_175606_aa();
        }

        public float getRotationX() {
            return this.rotationX;
        }

        public float getRotationZ() {
            return this.rotationZ;
        }

        public float getRotationYZ() {
            return this.rotationYZ;
        }

        public float getRotationXY() {
            return this.rotationXY;
        }

        public float getRotationXZ() {
            return this.rotationXZ;
        }

        public float getPartialTicks() {
            return this.partialTicks;
        }

        public TextureManager getRenderer() {
            return this.renderer;
        }

        public WorldRenderer getWorldRenderer() {
            return this.worldRenderer;
        }

        public Entity getRenderViewEntity() {
            return this.renderViewEntity;
        }
    }
}

