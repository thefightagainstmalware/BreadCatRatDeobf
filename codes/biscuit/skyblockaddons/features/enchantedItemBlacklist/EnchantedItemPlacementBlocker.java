/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  lombok.NonNull
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBucket
 *  net.minecraft.item.ItemRedstone
 *  net.minecraft.item.ItemReed
 *  net.minecraft.item.ItemSeedFood
 *  net.minecraft.item.ItemSeeds
 *  net.minecraft.item.ItemSkull
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraftforge.event.entity.player.FillBucketEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$Action
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package codes.biscuit.skyblockaddons.features.enchantedItemBlacklist;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.features.enchantedItemBlacklist.EnchantedItemLists;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import lombok.NonNull;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EnchantedItemPlacementBlocker {
    private static final ArrayList<Block> INTERACTIVE_BLOCKS = Lists.newArrayList((Object[])new Block[]{Blocks.field_180410_as, Blocks.field_150467_bQ, Blocks.field_150461_bJ, Blocks.field_180412_aq, Blocks.field_150382_bo, Blocks.field_150486_ae, Blocks.field_150455_bV, Blocks.field_150441_bU, Blocks.field_150462_ai, Blocks.field_180409_at, Blocks.field_150453_bW, Blocks.field_180402_cm, Blocks.field_150367_z, Blocks.field_150409_cd, Blocks.field_150381_bn, Blocks.field_150460_al, Blocks.field_150438_bZ, Blocks.field_150454_av, Blocks.field_180400_cw, Blocks.field_180411_ar, Blocks.field_150442_at, Blocks.field_150470_am, Blocks.field_180413_ao, Blocks.field_150416_aS, Blocks.field_150413_aR, Blocks.field_150430_aB, Blocks.field_150415_aT, Blocks.field_150447_bR, Blocks.field_150471_bO});
    private static final ArrayList<Class<?>> CLASSES_OF_ITEMS_THAT_CAN_BE_PLACED = Lists.newArrayList((Object[])new Class[]{ItemBucket.class, ItemRedstone.class, ItemReed.class, ItemSeedFood.class, ItemSeeds.class, ItemSkull.class});
    private static EnchantedItemLists itemLists;
    private static ItemStack lastItemStack;
    private static boolean lastBucketEventBlocked;

    public static boolean shouldBlockPlacement(@NonNull ItemStack itemStack, Event event) {
        if (itemStack == null) {
            throw new NullPointerException("itemStack is marked non-null but is null");
        }
        if (SkyblockAddons.getInstance().getUtils().getLocation() == Location.ISLAND && EnchantedItemPlacementBlocker.canBePlaced(itemStack.func_77973_b())) {
            String heldItemId = ItemUtils.getSkyblockItemID(itemStack);
            if (heldItemId == null || !itemStack.func_77948_v() || ItemUtils.isMaterialForRecipe(itemStack)) {
                return false;
            }
            if (event instanceof PlayerInteractEvent) {
                PlayerInteractEvent interactEvent = (PlayerInteractEvent)event;
                if (interactEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                    return false;
                }
                if (interactEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                    lastItemStack = itemStack;
                } else if (EnchantedItemPlacementBlocker.isSecondaryRightClickAirEvent(interactEvent, itemStack)) {
                    return true;
                }
            }
            if (EnchantedItemPlacementBlocker.itemLists.whitelistedIDs.contains(heldItemId)) {
                return false;
            }
            if (EnchantedItemPlacementBlocker.itemLists.blacklistedIDs.contains(heldItemId)) {
                return EnchantedItemPlacementBlocker.willBePlaced(event, itemStack);
            }
            ItemRarity rarity = ItemUtils.getRarity(itemStack);
            if (rarity != null && EnchantedItemPlacementBlocker.itemLists.rarityLimit.compareTo(rarity) <= 0) {
                return EnchantedItemPlacementBlocker.willBePlaced(event, itemStack);
            }
        }
        return false;
    }

    private static boolean isSecondaryRightClickAirEvent(PlayerInteractEvent event, ItemStack itemStack) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR && lastItemStack != null && itemStack.func_179549_c(lastItemStack)) {
            lastItemStack = null;
            return true;
        }
        return false;
    }

    private static boolean canBePlaced(Item item) {
        return Block.func_149634_a((Item)item) != null || CLASSES_OF_ITEMS_THAT_CAN_BE_PLACED.contains(item.getClass());
    }

    private static boolean willBePlaced(Event event, ItemStack itemStack) {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interactEvent = (PlayerInteractEvent)event;
            if (interactEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = Minecraft.func_71410_x().field_71441_e.func_180495_p(interactEvent.pos).func_177230_c();
                return EnchantedItemPlacementBlocker.willNotActivateBlock(interactEvent.action, interactEvent.entityPlayer, clickedBlock);
            }
            if (itemStack.func_77973_b() instanceof ItemBucket) {
                itemStack.func_77973_b().func_77659_a(itemStack, interactEvent.world, interactEvent.entityPlayer);
                if (lastBucketEventBlocked) {
                    lastBucketEventBlocked = false;
                    return true;
                }
            }
            return false;
        }
        if (((FillBucketEvent)event).target.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            lastBucketEventBlocked = true;
            return true;
        }
        return false;
    }

    private static boolean willNotActivateBlock(PlayerInteractEvent.Action action, EntityPlayer player, Block block) {
        if (block == null) {
            throw new IllegalArgumentException("Block cannot be null!");
        }
        return action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || player.func_70093_af() || !INTERACTIVE_BLOCKS.contains(block);
    }

    public static void setItemLists(EnchantedItemLists itemLists) {
        EnchantedItemPlacementBlocker.itemLists = itemLists;
    }
}

