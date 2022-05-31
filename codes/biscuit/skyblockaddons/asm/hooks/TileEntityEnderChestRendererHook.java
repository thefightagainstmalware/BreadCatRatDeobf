/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnderChestRendererHook {
    private static final ResourceLocation BLANK_ENDERCHEST = new ResourceLocation("skyblockaddons", "blankenderchest.png");

    public static void bindTexture(TileEntityEnderChestRenderer tileEntityEnderChestRenderer, ResourceLocation enderChestTexture) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && Minecraft.func_71410_x().field_71462_r == null && main.getConfigValues().isEnabled(Feature.MAKE_ENDERCHESTS_GREEN_IN_END) && (main.getUtils().getLocation() == Location.THE_END || main.getUtils().getLocation() == Location.DRAGONS_NEST)) {
            tileEntityEnderChestRenderer.func_147499_a(BLANK_ENDERCHEST);
        } else {
            tileEntityEnderChestRenderer.func_147499_a(enderChestTexture);
        }
    }

    public static void setEnderchestColor() {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && Minecraft.func_71410_x().field_71462_r == null && main.getConfigValues().isEnabled(Feature.MAKE_ENDERCHESTS_GREEN_IN_END) && main.getUtils().getLocation() == Location.DRAGONS_NEST) {
            int color = main.getConfigValues().getColor(Feature.MAKE_ENDERCHESTS_GREEN_IN_END);
            if (color == ColorCode.GREEN.getColor()) {
                GlStateManager.func_179124_c((float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                ColorUtils.bindColor(color);
            }
        }
    }
}

