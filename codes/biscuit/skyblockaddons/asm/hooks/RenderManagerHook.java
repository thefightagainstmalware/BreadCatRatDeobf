/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.init.Items
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.npc.NPCUtils;
import codes.biscuit.skyblockaddons.features.JerryPresent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;

public class RenderManagerHook {
    private static final int HIDE_RADIUS_SQUARED = 49;

    public static void shouldRender(Entity entityIn, ReturnValue<Boolean> returnValue) {
        Minecraft mc = Minecraft.func_71410_x();
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock()) {
            JerryPresent present;
            EntityItem entityItem;
            Location currentLocation = main.getUtils().getLocation();
            if (main.getConfigValues().isEnabled(Feature.HIDE_BONES) && main.getInventoryUtils().isWearingSkeletonHelmet() && entityIn instanceof EntityItem && entityIn.field_70154_o instanceof EntityArmorStand && entityIn.field_70154_o.func_82150_aj() && (entityItem = (EntityItem)entityIn).func_92059_d().func_77973_b().equals(Items.field_151103_aS)) {
                returnValue.cancel();
            }
            if (main.getUtils().getLocation() != Location.THE_CATACOMBS && main.getConfigValues().isEnabled(Feature.HIDE_PLAYERS_NEAR_NPCS) && mc.field_71441_e != null && entityIn instanceof EntityOtherPlayerMP && !NPCUtils.isNPC(entityIn) && NPCUtils.isNearNPC(entityIn)) {
                returnValue.cancel();
            }
            if (main.getConfigValues().isEnabled(Feature.HIDE_PLAYERS_IN_LOBBY) && (currentLocation == Location.VILLAGE || currentLocation == Location.AUCTION_HOUSE || currentLocation == Location.BANK) && (entityIn instanceof EntityOtherPlayerMP || entityIn instanceof EntityFX || entityIn instanceof EntityItemFrame) && !NPCUtils.isNPC(entityIn) && entityIn.func_70068_e((Entity)mc.field_71439_g) > 49.0) {
                returnValue.cancel();
            }
            if (main.getConfigValues().isEnabled(Feature.HIDE_OTHER_PLAYERS_PRESENTS) && (present = JerryPresent.getJerryPresents().get(entityIn.func_110124_au())) != null && present.shouldHide()) {
                returnValue.cancel();
            }
        }
    }
}

