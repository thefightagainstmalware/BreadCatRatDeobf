/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.features.cooldowns.CooldownManager;
import net.minecraft.item.ItemStack;

public class ItemHook {
    public static boolean isItemDamaged(ItemStack stack) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.SHOW_ITEM_COOLDOWNS) && CooldownManager.isOnCooldown(stack)) {
            return true;
        }
        return stack.func_77951_h();
    }

    public static void getDurabilityForDisplay(ItemStack stack, ReturnValue<Double> returnValue) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.SHOW_ITEM_COOLDOWNS) && CooldownManager.isOnCooldown(stack)) {
            returnValue.cancel(CooldownManager.getRemainingCooldownPercent(stack));
        }
    }
}

