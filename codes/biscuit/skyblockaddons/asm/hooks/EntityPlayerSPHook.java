/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.hooks.MinecraftHook;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;

public class EntityPlayerSPHook {
    private static String lastItemName = null;
    private static long lastDrop = Minecraft.func_71386_F();

    public static EntityItem dropOneItemConfirmation(ReturnValue<?> returnValue) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        Minecraft mc = Minecraft.func_71410_x();
        ItemStack heldItemStack = mc.field_71439_g.func_70694_bm();
        if (main.getUtils().isOnSkyblock() || main.getPlayerListener().aboutToJoinSkyblockServer()) {
            if (main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && !main.getUtils().isInDungeon()) {
                int slot = mc.field_71439_g.field_71071_by.field_70461_c + 36;
                if (main.getConfigValues().getLockedSlots().contains(slot) && (slot >= 9 || mc.field_71439_g.field_71070_bA instanceof ContainerPlayer && slot >= 5)) {
                    main.getUtils().playLoudSound("note.bass", 0.5);
                    SkyblockAddons.getInstance().getUtils().sendMessage((Object)((Object)main.getConfigValues().getRestrictedColor(Feature.DROP_CONFIRMATION)) + Message.MESSAGE_SLOT_LOCKED.getMessage(new String[0]));
                    returnValue.cancel();
                    return null;
                }
                if (System.currentTimeMillis() - MinecraftHook.getLastLockedSlotItemChange() < 200L) {
                    main.getUtils().playLoudSound("note.bass", 0.5);
                    SkyblockAddons.getInstance().getUtils().sendMessage((Object)((Object)main.getConfigValues().getRestrictedColor(Feature.DROP_CONFIRMATION)) + Message.MESSAGE_SWITCHED_SLOTS.getMessage(new String[0]));
                    returnValue.cancel();
                    return null;
                }
            }
            if (heldItemStack != null && main.getConfigValues().isEnabled(Feature.STOP_DROPPING_SELLING_RARE_ITEMS) && !main.getUtils().isInDungeon()) {
                if (!main.getUtils().getItemDropChecker().canDropItem(heldItemStack, true)) {
                    main.getUtils().sendMessage((Object)((Object)main.getConfigValues().getRestrictedColor(Feature.STOP_DROPPING_SELLING_RARE_ITEMS)) + Message.MESSAGE_CANCELLED_DROPPING.getMessage(new String[0]));
                    returnValue.cancel();
                    return null;
                }
                if (System.currentTimeMillis() - MinecraftHook.getLastLockedSlotItemChange() < 200L) {
                    main.getUtils().playLoudSound("note.bass", 0.5);
                    SkyblockAddons.getInstance().getUtils().sendMessage((Object)((Object)main.getConfigValues().getRestrictedColor(Feature.DROP_CONFIRMATION)) + Message.MESSAGE_SWITCHED_SLOTS.getMessage(new String[0]));
                    returnValue.cancel();
                    return null;
                }
            }
        }
        if (heldItemStack != null && main.getConfigValues().isEnabled(Feature.DROP_CONFIRMATION) && !main.getUtils().isInDungeon() && (main.getUtils().isOnSkyblock() || main.getPlayerListener().aboutToJoinSkyblockServer() || main.getConfigValues().isEnabled(Feature.DOUBLE_DROP_IN_OTHER_GAMES))) {
            String heldItemName;
            lastDrop = Minecraft.func_71386_F();
            String string = heldItemName = heldItemStack.func_82837_s() ? heldItemStack.func_82833_r() : heldItemStack.func_77977_a();
            if (lastItemName == null || !lastItemName.equals(heldItemName) || Minecraft.func_71386_F() - lastDrop >= 3000L) {
                SkyblockAddons.getInstance().getUtils().sendMessage((Object)((Object)main.getConfigValues().getRestrictedColor(Feature.DROP_CONFIRMATION)) + Message.MESSAGE_DROP_CONFIRMATION.getMessage(new String[0]));
                lastItemName = heldItemName;
                returnValue.cancel();
            }
        }
        return null;
    }
}

