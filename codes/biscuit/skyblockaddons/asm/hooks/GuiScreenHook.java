/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IChatComponent
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.InventoryType;
import codes.biscuit.skyblockaddons.features.backpacks.ContainerPreviewManager;
import codes.biscuit.skyblockaddons.features.cooldowns.CooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;

public class GuiScreenHook {
    private static final int MADDOX_BATPHONE_COOLDOWN = 20000;

    public static boolean onRenderTooltip(ItemStack itemStack, int x, int y) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isEnabled(Feature.DISABLE_EMPTY_GLASS_PANES) && main.getUtils().isEmptyGlassPane(itemStack)) {
            return true;
        }
        if (main.getConfigValues().isDisabled(Feature.SHOW_EXPERIMENTATION_TABLE_TOOLTIPS) && (main.getInventoryUtils().getInventoryType() == InventoryType.ULTRASEQUENCER || main.getInventoryUtils().getInventoryType() == InventoryType.CHRONOMATRON)) {
            return true;
        }
        return ContainerPreviewManager.onRenderTooltip(itemStack, x, y);
    }

    public static void handleComponentClick(IChatComponent component) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && component != null && "\u00a72\u00a7l[OPEN MENU]".equals(component.func_150260_c()) && !CooldownManager.isOnCooldown("AATROX_BATPHONE")) {
            CooldownManager.put("AATROX_BATPHONE");
        }
    }
}

