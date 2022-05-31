/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;

public class GuiIngameCustomHook {
    public static boolean shouldRenderArmor() {
        return GuiIngameCustomHook.shouldRender(Feature.HIDE_FOOD_ARMOR_BAR);
    }

    public static boolean shouldRenderHealth() {
        return GuiIngameCustomHook.shouldRender(Feature.HIDE_HEALTH_BAR);
    }

    public static boolean shouldRenderFood() {
        return GuiIngameCustomHook.shouldRender(Feature.HIDE_FOOD_ARMOR_BAR);
    }

    public static boolean shouldRenderMountHealth() {
        return GuiIngameCustomHook.shouldRender(Feature.HIDE_PET_HEALTH_BAR);
    }

    public static boolean shouldRender(Feature feature) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (!main.getUtils().isOnSkyblock()) {
            return true;
        }
        return !main.getConfigValues().isEnabled(feature);
    }
}

