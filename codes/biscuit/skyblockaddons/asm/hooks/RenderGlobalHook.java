/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.util.BlockPos
 *  org.apache.logging.log4j.Logger
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.hooks.MinecraftHook;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.features.EntityOutlines.EntityOutlineRenderer;
import java.lang.reflect.Method;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.BlockPos;
import org.apache.logging.log4j.Logger;

public class RenderGlobalHook {
    private static final boolean stopLookingForOptifine = false;
    private static final Method isFastRender = null;
    private static final Method isShaders = null;
    private static final Method isAntialiasing = null;
    private static final Logger logger = SkyblockAddons.getLogger();

    public static boolean shouldRenderSkyblockItemOutlines() {
        return EntityOutlineRenderer.shouldRenderEntityOutlines();
    }

    public static void afterFramebufferDraw() {
        GlStateManager.func_179126_j();
    }

    public static boolean blockRenderingSkyblockItemOutlines(ICamera camera, float partialTicks, double x, double y, double z) {
        return EntityOutlineRenderer.renderEntityOutlines(camera, partialTicks, x, y, z);
    }

    public static void onAddBlockBreakParticle(int breakerId, BlockPos pos, int progress) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (breakerId == 0 && main.getUtils().getLocation() != Location.ISLAND && pos.equals((Object)MinecraftHook.prevClickBlock) && progress == 10) {
            MinecraftHook.startMineTime = System.currentTimeMillis();
        }
    }
}

