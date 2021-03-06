/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import java.util.List;
import net.minecraft.client.gui.GuiButton;

public class GuiIngameMenuHook {
    public static void addMenuButtons(List<GuiButton> buttonList, int width, int height) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.SKYBLOCK_ADDONS_BUTTON_IN_PAUSE_MENU)) {
            buttonList.add(new GuiButton(53, width - 120 - 5, height - 20 - 5, 120, 20, "SkyblockAddons Menu"));
        }
    }

    public static void onButtonClick() {
        SkyblockAddons skyblockAddons = SkyblockAddons.getInstance();
        skyblockAddons.getRenderListener().setGuiToOpen(EnumUtils.GUIType.MAIN, 1, EnumUtils.GuiTab.MAIN);
    }
}

