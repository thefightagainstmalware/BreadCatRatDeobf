/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockPrismarine$EnumType
 *  net.minecraft.block.BlockStone$EnumType
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.hooks.MinecraftHook;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.events.SkyblockBlockBreakEvent;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackColor;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackInventoryManager;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerControllerMPHook {
    private static final int SHIFTCLICK_CLICK_TYPE = 1;
    private static final int CRAFTING_PATTERN_SOUND_COOLDOWN = 400;
    private static long lastCraftingSoundPlayed = 0L;
    private static final Set<Integer> ORES = Sets.newHashSet((Object[])new Integer[]{Block.func_149682_b((Block)Blocks.field_150365_q), Block.func_149682_b((Block)Blocks.field_150366_p), Block.func_149682_b((Block)Blocks.field_150352_o), Block.func_149682_b((Block)Blocks.field_150450_ax), Block.func_149682_b((Block)Blocks.field_150412_bA), Block.func_149682_b((Block)Blocks.field_150369_x), Block.func_149682_b((Block)Blocks.field_150482_ag), Block.func_149682_b((Block)Blocks.field_150439_ay), Utils.getBlockMetaId(Blocks.field_150348_b, BlockStone.EnumType.DIORITE_SMOOTH.func_176642_a()), Utils.getBlockMetaId(Blocks.field_150406_ce, EnumDyeColor.CYAN.func_176765_a()), Utils.getBlockMetaId(Blocks.field_180397_cI, BlockPrismarine.EnumType.ROUGH.func_176807_a()), Utils.getBlockMetaId(Blocks.field_180397_cI, BlockPrismarine.EnumType.DARK.func_176807_a()), Utils.getBlockMetaId(Blocks.field_180397_cI, BlockPrismarine.EnumType.BRICKS.func_176807_a()), Utils.getBlockMetaId(Blocks.field_150325_L, EnumDyeColor.LIGHT_BLUE.func_176765_a()), Utils.getBlockMetaId(Blocks.field_150325_L, EnumDyeColor.GRAY.func_176765_a())});

    public static boolean checkItemDrop(int clickModifier, int slotNum, ItemStack heldStack) {
        if ((clickModifier == 0 || clickModifier == 1) && slotNum == -999 && heldStack != null) {
            return !SkyblockAddons.getInstance().getUtils().getItemDropChecker().canDropItem(heldStack);
        }
        return false;
    }

    public static void onPlayerDestroyBlock(BlockPos blockPos) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        Minecraft mc = Minecraft.func_71410_x();
        if (main.getUtils().isOnSkyblock()) {
            IBlockState block = mc.field_71441_e.func_180495_p(blockPos);
            double perTickIncrease = block.func_177230_c().func_180647_a((EntityPlayer)mc.field_71439_g, mc.field_71439_g.field_70170_p, blockPos);
            int MILLISECONDS_PER_TICK = 50;
            MinecraftForge.EVENT_BUS.post((Event)new SkyblockBlockBreakEvent(blockPos, (long)((double)MILLISECONDS_PER_TICK / perTickIncrease)));
        }
    }

    public static void onResetBlockRemoving() {
        MinecraftHook.prevClickBlock = new BlockPos(-1, -1, -1);
    }

    public static void onWindowClick(int slotNum, int mouseButtonClicked, int mode, EntityPlayer player, ReturnValue<ItemStack> returnValue) {
        if (Utils.blockNextClick) {
            Utils.blockNextClick = false;
            returnValue.cancel();
            return;
        }
        SkyblockAddons main = SkyblockAddons.getInstance();
        int slotId = slotNum;
        ItemStack itemStack = player.field_71071_by.func_70445_o();
        if (main.getUtils().isOnSkyblock()) {
            if (main.getConfigValues().isEnabled(Feature.STOP_DROPPING_SELLING_RARE_ITEMS) && !main.getUtils().isInDungeon() && PlayerControllerMPHook.checkItemDrop(mode, slotNum, itemStack)) {
                returnValue.cancel();
            }
            if (player.field_71070_bA != null) {
                BackpackColor color;
                Slot slotIn;
                slotNum += main.getInventoryUtils().getSlotDifference(player.field_71070_bA);
                Container slots = player.field_71070_bA;
                try {
                    slotIn = slots.func_75139_a(slotId);
                }
                catch (IndexOutOfBoundsException e) {
                    slotIn = null;
                }
                if (mouseButtonClicked == 1 && slotIn != null && slotIn.func_75216_d() && slotIn.func_75211_c().func_77973_b() == Items.field_151144_bL && (color = ItemUtils.getBackpackColor(slotIn.func_75211_c())) != null) {
                    BackpackInventoryManager.setBackpackColor(color);
                }
                if (main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && main.getConfigValues().getLockedSlots().contains(slotNum) && (slotNum >= 9 || player.field_71070_bA instanceof ContainerPlayer && slotNum >= 5)) {
                    if (mouseButtonClicked == 1 && mode == 0 && slotIn != null && slotIn.func_75216_d() && slotIn.func_75211_c().func_77973_b() == Items.field_151144_bL) {
                        String itemID = ItemUtils.getSkyblockItemID(slotIn.func_75211_c());
                        if (itemID == null) {
                            itemID = "";
                        }
                        if (itemID.contains("SACK")) {
                            return;
                        }
                    }
                    main.getUtils().playLoudSound("note.bass", 0.5);
                    returnValue.cancel();
                }
            }
        } else if (PlayerControllerMPHook.checkItemDrop(mode, slotNum, itemStack)) {
            returnValue.cancel();
        }
    }
}

