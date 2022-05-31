/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.Vec3
 */
package codes.biscuit.skyblockaddons.core.npc;

import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class NPCUtils {
    private static final int HIDE_RADIUS_SQUARED = (int)Math.round(6.25);
    private static Map<UUID, Vec3> npcLocations = new HashMap<UUID, Vec3>();

    public static boolean isSellMerchant(IInventory inventory) {
        int sellSlot = inventory.func_70302_i_() - 4 - 1;
        ItemStack itemStack = inventory.func_70301_a(sellSlot);
        if (itemStack != null) {
            if (itemStack.func_77973_b() == Item.func_150898_a((Block)Blocks.field_150438_bZ) && itemStack.func_82837_s() && TextUtils.stripColor(itemStack.func_82833_r()).equals("Sell Item")) {
                return true;
            }
            List<String> tooltip = ItemUtils.getItemLore(itemStack);
            for (String line : tooltip) {
                if (!TextUtils.stripColor(line).equals("Click to buyback!")) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isNearNPC(Entity entityToCheck) {
        for (Vec3 npcLocation : npcLocations.values()) {
            if (!(entityToCheck.func_70092_e(npcLocation.field_72450_a, npcLocation.field_72448_b, npcLocation.field_72449_c) <= (double)HIDE_RADIUS_SQUARED)) continue;
            return true;
        }
        return false;
    }

    public static boolean isNPC(Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
        return entity.func_110124_au().version() == 2 && entityLivingBase.func_110143_aJ() == 20.0f && !entityLivingBase.func_70608_bn();
    }

    public static Map<UUID, Vec3> getNpcLocations() {
        return npcLocations;
    }
}

