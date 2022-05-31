/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.client.MinecraftForgeClient
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL30
 */
package codes.biscuit.skyblockaddons.features.EntityOutlines;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.events.RenderEntityOutlineEvent;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL30;

public class EntityOutlineRenderer {
    private static final Logger logger = SkyblockAddons.getLogger();
    private static final CachedInfo entityRenderCache = new CachedInfo();
    private static boolean stopLookingForOptifine = false;
    private static Method isFastRender = null;
    private static Method isShaders = null;
    private static Method isAntialiasing = null;
    private static Framebuffer swapBuffer = null;
    private static boolean emptyLastTick = false;

    private static Framebuffer initSwapBuffer() {
        Framebuffer main = Minecraft.func_71410_x().func_147110_a();
        Framebuffer framebuffer = new Framebuffer(main.field_147622_a, main.field_147620_b, true);
        framebuffer.func_147607_a(9728);
        framebuffer.func_147604_a(0.0f, 0.0f, 0.0f, 0.0f);
        return framebuffer;
    }

    private static void updateFramebufferSize() {
        if (swapBuffer == null) {
            swapBuffer = EntityOutlineRenderer.initSwapBuffer();
        }
        int width = Minecraft.func_71410_x().field_71443_c;
        int height = Minecraft.func_71410_x().field_71440_d;
        if (EntityOutlineRenderer.swapBuffer.field_147621_c != width || EntityOutlineRenderer.swapBuffer.field_147618_d != height) {
            swapBuffer.func_147613_a(width, height);
        }
        RenderGlobal rg = Minecraft.func_71410_x().field_71438_f;
        Framebuffer outlineBuffer = rg.field_175015_z;
        if (outlineBuffer.field_147621_c != width || outlineBuffer.field_147618_d != height) {
            outlineBuffer.func_147613_a(width, height);
            rg.field_174991_A.func_148026_a(width, height);
        }
    }

    public static boolean renderEntityOutlines(ICamera camera, float partialTicks, double x, double y, double z) {
        boolean shouldRenderOutlines = EntityOutlineRenderer.shouldRenderEntityOutlines();
        if (shouldRenderOutlines && !EntityOutlineRenderer.isCacheEmpty() && MinecraftForgeClient.getRenderPass() == 0) {
            Minecraft mc = Minecraft.func_71410_x();
            RenderGlobal renderGlobal = mc.field_71438_f;
            RenderManager renderManager = mc.func_175598_ae();
            mc.field_71441_e.field_72984_F.func_76318_c("entityOutlines");
            EntityOutlineRenderer.updateFramebufferSize();
            renderGlobal.field_175015_z.func_147614_f();
            renderGlobal.field_175015_z.func_147610_a(false);
            RenderHelper.func_74518_a();
            GlStateManager.func_179106_n();
            mc.func_175598_ae().func_178632_c(true);
            DrawUtils.enableOutlineMode();
            if (!EntityOutlineRenderer.isXrayCacheEmpty()) {
                GlStateManager.func_179143_c((int)519);
                for (Map.Entry<Entity, Integer> entityAndColor : entityRenderCache.getXrayCache().entrySet()) {
                    if (!EntityOutlineRenderer.shouldRender(camera, entityAndColor.getKey(), x, y, z)) continue;
                    try {
                        if (!(entityAndColor.getKey() instanceof EntityLivingBase)) {
                            DrawUtils.outlineColor(entityAndColor.getValue());
                        }
                        renderManager.func_147936_a(entityAndColor.getKey(), partialTicks, true);
                    }
                    catch (Exception exception) {}
                }
                GlStateManager.func_179143_c((int)515);
            }
            if (!EntityOutlineRenderer.isNoXrayCacheEmpty()) {
                if (!EntityOutlineRenderer.isNoOutlineCacheEmpty()) {
                    swapBuffer.func_147614_f();
                    EntityOutlineRenderer.copyBuffers(mc.func_147110_a(), swapBuffer, 256);
                    swapBuffer.func_147610_a(false);
                    if (entityRenderCache.getNoOutlineCache() != null) {
                        for (Entity entity : entityRenderCache.getNoOutlineCache()) {
                            if (!EntityOutlineRenderer.shouldRender(camera, entity, x, y, z)) continue;
                            try {
                                renderManager.func_147936_a(entity, partialTicks, true);
                            }
                            catch (Exception exception) {}
                        }
                    }
                    EntityOutlineRenderer.copyBuffers(swapBuffer, renderGlobal.field_175015_z, 256);
                    renderGlobal.field_175015_z.func_147610_a(false);
                } else {
                    EntityOutlineRenderer.copyBuffers(mc.func_147110_a(), renderGlobal.field_175015_z, 256);
                }
                for (Map.Entry<Entity, Integer> entityAndColor : entityRenderCache.getNoXrayCache().entrySet()) {
                    if (!EntityOutlineRenderer.shouldRender(camera, entityAndColor.getKey(), x, y, z)) continue;
                    try {
                        if (!(entityAndColor.getKey() instanceof EntityLivingBase)) {
                            DrawUtils.outlineColor(entityAndColor.getValue());
                        }
                        renderManager.func_147936_a(entityAndColor.getKey(), partialTicks, true);
                    }
                    catch (Exception exception) {}
                }
            }
            DrawUtils.disableOutlineMode();
            RenderHelper.func_74519_b();
            mc.func_175598_ae().func_178632_c(false);
            GlStateManager.func_179132_a((boolean)false);
            renderGlobal.field_174991_A.func_148018_a(partialTicks);
            GlStateManager.func_179132_a((boolean)true);
            GlStateManager.func_179145_e();
            mc.func_147110_a().func_147610_a(false);
            GlStateManager.func_179127_m();
            GlStateManager.func_179147_l();
            GlStateManager.func_179142_g();
            GlStateManager.func_179126_j();
            GlStateManager.func_179141_d();
        }
        return !shouldRenderOutlines;
    }

    public static Integer getCustomOutlineColor(EntityLivingBase entity) {
        if (entityRenderCache.getXrayCache() != null && entityRenderCache.getXrayCache().containsKey(entity)) {
            return entityRenderCache.getXrayCache().get(entity);
        }
        if (entityRenderCache.getNoXrayCache() != null && entityRenderCache.getNoXrayCache().containsKey(entity)) {
            return entityRenderCache.getNoXrayCache().get(entity);
        }
        return null;
    }

    public static boolean shouldRenderEntityOutlines() {
        Minecraft mc = Minecraft.func_71410_x();
        RenderGlobal renderGlobal = mc.field_71438_f;
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (renderGlobal.field_175015_z == null || renderGlobal.field_174991_A == null || mc.field_71439_g == null) {
            return false;
        }
        if (!main.getUtils().isOnSkyblock()) {
            return false;
        }
        if (main.getConfigValues().isDisabled(Feature.ENTITY_OUTLINES)) {
            return false;
        }
        if (!stopLookingForOptifine && isFastRender == null) {
            try {
                Class<?> config = Class.forName("Config");
                try {
                    isFastRender = config.getMethod("isFastRender", new Class[0]);
                    isShaders = config.getMethod("isShaders", new Class[0]);
                    isAntialiasing = config.getMethod("isAntialiasing", new Class[0]);
                }
                catch (Exception ex) {
                    logger.warn("Couldn't find Optifine methods for entity outlines.");
                    stopLookingForOptifine = true;
                }
            }
            catch (Exception ex) {
                logger.info("Couldn't find Optifine for entity outlines.");
                stopLookingForOptifine = true;
            }
        }
        boolean isFastRenderValue = false;
        boolean isShadersValue = false;
        boolean isAntialiasingValue = false;
        if (isFastRender != null) {
            try {
                isFastRenderValue = (Boolean)isFastRender.invoke(null, new Object[0]);
                isShadersValue = (Boolean)isShaders.invoke(null, new Object[0]);
                isAntialiasingValue = (Boolean)isAntialiasing.invoke(null, new Object[0]);
            }
            catch (IllegalAccessException | InvocationTargetException ex) {
                logger.warn("An error occurred while calling Optifine methods for entity outlines...", (Throwable)ex);
            }
        }
        return !isFastRenderValue && !isShadersValue && !isAntialiasingValue;
    }

    private static boolean shouldRender(ICamera camera, Entity entity, double x, double y, double z) {
        Minecraft mc = Minecraft.func_71410_x();
        if (!(entity != mc.func_175606_aa() || mc.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)mc.func_175606_aa()).func_70608_bn() || mc.field_71474_y.field_74320_O != 0)) {
            return false;
        }
        return mc.field_71441_e.func_175667_e(new BlockPos(entity)) && (mc.func_175598_ae().func_178635_a(entity, camera, x, y, z) || entity.field_70153_n == mc.field_71439_g);
    }

    private static void copyBuffers(Framebuffer frameToCopy, Framebuffer frameToPaste, int buffersToCopy) {
        if (OpenGlHelper.func_148822_b()) {
            OpenGlHelper.func_153171_g((int)36008, (int)frameToCopy.field_147616_f);
            OpenGlHelper.func_153171_g((int)36009, (int)frameToPaste.field_147616_f);
            GL30.glBlitFramebuffer((int)0, (int)0, (int)frameToCopy.field_147621_c, (int)frameToCopy.field_147618_d, (int)0, (int)0, (int)frameToPaste.field_147621_c, (int)frameToPaste.field_147618_d, (int)buffersToCopy, (int)9728);
        }
    }

    public static boolean isCacheEmpty() {
        return EntityOutlineRenderer.isXrayCacheEmpty() && EntityOutlineRenderer.isNoXrayCacheEmpty();
    }

    private static boolean isXrayCacheEmpty() {
        return entityRenderCache.xrayCache == null || entityRenderCache.xrayCache.isEmpty();
    }

    private static boolean isNoXrayCacheEmpty() {
        return entityRenderCache.noXrayCache == null || entityRenderCache.noXrayCache.isEmpty();
    }

    private static boolean isNoOutlineCacheEmpty() {
        return entityRenderCache.noOutlineCache == null || entityRenderCache.noOutlineCache.isEmpty();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.func_71410_x();
            if (mc.field_71441_e != null && EntityOutlineRenderer.shouldRenderEntityOutlines()) {
                RenderEntityOutlineEvent xrayOutlineEvent = new RenderEntityOutlineEvent(RenderEntityOutlineEvent.Type.XRAY, null);
                MinecraftForge.EVENT_BUS.post((Event)xrayOutlineEvent);
                RenderEntityOutlineEvent noxrayOutlineEvent = new RenderEntityOutlineEvent(RenderEntityOutlineEvent.Type.NO_XRAY, xrayOutlineEvent.getEntitiesToChooseFrom());
                MinecraftForge.EVENT_BUS.post((Event)noxrayOutlineEvent);
                entityRenderCache.setXrayCache(xrayOutlineEvent.getEntitiesToOutline());
                entityRenderCache.setNoXrayCache(noxrayOutlineEvent.getEntitiesToOutline());
                entityRenderCache.setNoOutlineCache(noxrayOutlineEvent.getEntitiesToChooseFrom());
                if (EntityOutlineRenderer.isCacheEmpty()) {
                    if (!emptyLastTick) {
                        mc.field_71438_f.field_175015_z.func_147614_f();
                    }
                    emptyLastTick = true;
                } else {
                    emptyLastTick = false;
                }
            } else if (!emptyLastTick) {
                entityRenderCache.setXrayCache(null);
                entityRenderCache.setNoXrayCache(null);
                entityRenderCache.setNoOutlineCache(null);
                mc.field_71438_f.field_175015_z.func_147614_f();
                emptyLastTick = true;
            }
        }
    }

    private static class CachedInfo {
        private HashMap<Entity, Integer> xrayCache = null;
        private HashMap<Entity, Integer> noXrayCache = null;
        private HashSet<Entity> noOutlineCache = null;

        private CachedInfo() {
        }

        public HashMap<Entity, Integer> getXrayCache() {
            return this.xrayCache;
        }

        public void setXrayCache(HashMap<Entity, Integer> xrayCache) {
            this.xrayCache = xrayCache;
        }

        public HashMap<Entity, Integer> getNoXrayCache() {
            return this.noXrayCache;
        }

        public void setNoXrayCache(HashMap<Entity, Integer> noXrayCache) {
            this.noXrayCache = noXrayCache;
        }

        public HashSet<Entity> getNoOutlineCache() {
            return this.noOutlineCache;
        }

        public void setNoOutlineCache(HashSet<Entity> noOutlineCache) {
            this.noOutlineCache = noOutlineCache;
        }
    }
}

