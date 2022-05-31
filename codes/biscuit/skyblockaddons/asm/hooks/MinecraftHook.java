/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.Message;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class MinecraftHook {
    private static long lastLockedSlotItemChange = -1L;
    private static final Set<Location> DEEP_CAVERNS_LOCATIONS = EnumSet.of(Location.DEEP_CAVERNS, new Location[]{Location.GUNPOWDER_MINES, Location.LAPIS_QUARRY, Location.PIGMAN_DEN, Location.SLIMEHILL, Location.DIAMOND_RESERVE, Location.OBSIDIAN_SANCTUARY});
    private static final Set<Location> DWARVEN_MINES_LOCATIONS = EnumSet.of(Location.DWARVEN_MINES, new Location[]{Location.THE_LIFT, Location.DWARVEN_VILLAGE, Location.GATES_TO_THE_MINES, Location.THE_FORGE, Location.FORGE_BASIN, Location.LAVA_SPRINGS, Location.PALACE_BRIDGE, Location.ROYAL_PALACE, Location.ARISTOCRAT_PASSAGE, Location.HANGING_TERRACE, Location.CLIFFSIDE_VEINS, Location.RAMPARTS_QUARRY, Location.DIVANS_GATEWAY, Location.FAR_RESERVE, Location.GOBLIN_BURROWS, Location.UPPER_MINES, Location.MINERS_GUILD, Location.GREAT_ICE_WALL, Location.THE_MIST, Location.CC_MINECARTS_CO, Location.GRAND_LIBRARY, Location.HANGING_COURT, Location.ROYAL_MINES});
    private static final AxisAlignedBB DWARVEN_PUZZLE_ROOM = new AxisAlignedBB(171.0, 195.0, 125.0, 192.0, 196.0, 146.0);
    private static final Set<Block> DEEP_CAVERNS_MINEABLE_BLOCKS = new HashSet<Block>(Arrays.asList(Blocks.field_150365_q, Blocks.field_150366_p, Blocks.field_150352_o, Blocks.field_150450_ax, Blocks.field_150412_bA, Blocks.field_150482_ag, Blocks.field_150484_ah, Blocks.field_150343_Z, Blocks.field_150369_x, Blocks.field_150439_ay));
    private static final Set<Block> NETHER_MINEABLE_BLOCKS = new HashSet<Block>(Arrays.asList(Blocks.field_150426_aN, Blocks.field_150449_bY, Blocks.field_150388_bm));
    private static final Set<String> DWARVEN_MINEABLE_BLOCKS = new HashSet<String>(Arrays.asList("minecraft:prismarine0", "minecraft:prismarine1", "minecraft:prismarine2", "minecraft:stone4", "minecraft:wool3", "minecraft:wool7", "minecraft:stained_hardened_clay9"));
    private static final Set<Location> PARK_LOCATIONS = EnumSet.of(Location.BIRCH_PARK, Location.SPRUCE_WOODS, Location.SAVANNA_WOODLAND, Location.DARK_THICKET, Location.JUNGLE_ISLAND);
    private static final Set<Block> LOGS = new HashSet<Block>(Arrays.asList(Blocks.field_150364_r, Blocks.field_150363_s));
    private static final long lastStemMessage = -1L;
    private static final long lastUnmineableMessage = -1L;
    public static BlockPos prevClickBlock = new BlockPos(-1, -1, -1);
    public static long startMineTime = Long.MAX_VALUE;
    public static LinkedHashMap<BlockPos, Long> recentlyClickedBlocks = new LinkedHashMap();

    public static void rightClickMouse(ReturnValue<?> returnValue) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock()) {
            Minecraft mc = Minecraft.func_71410_x();
            if (mc.field_71476_x != null && mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY) {
                Entity entityIn = mc.field_71476_x.field_72308_g;
                if (main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && entityIn instanceof EntityItemFrame && ((EntityItemFrame)entityIn).func_82335_i() == null) {
                    int slot = mc.field_71439_g.field_71071_by.field_70461_c + 36;
                    if (main.getConfigValues().getLockedSlots().contains(slot) && slot >= 9) {
                        main.getUtils().playLoudSound("note.bass", 0.5);
                        main.getUtils().sendMessage((Object)((Object)main.getConfigValues().getRestrictedColor(Feature.DROP_CONFIRMATION)) + Message.MESSAGE_SLOT_LOCKED.getMessage(new String[0]));
                        returnValue.cancel();
                    }
                }
            }
        }
    }

    public static void updatedCurrentItem() {
        Minecraft mc = Minecraft.func_71410_x();
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && (main.getUtils().isOnSkyblock() || main.getPlayerListener().aboutToJoinSkyblockServer())) {
            ItemStack heldItemStack;
            int slot = mc.field_71439_g.field_71071_by.field_70461_c + 36;
            if (main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && main.getConfigValues().getLockedSlots().contains(slot) && (slot >= 9 || mc.field_71439_g.field_71070_bA instanceof ContainerPlayer && slot >= 5)) {
                lastLockedSlotItemChange = System.currentTimeMillis();
            }
            if ((heldItemStack = mc.field_71439_g.func_70694_bm()) != null && main.getConfigValues().isEnabled(Feature.STOP_DROPPING_SELLING_RARE_ITEMS) && !main.getUtils().isInDungeon() && !main.getUtils().getItemDropChecker().canDropItem(heldItemStack, true, false)) {
                lastLockedSlotItemChange = System.currentTimeMillis();
            }
        }
    }

    public static void onClickMouse(ReturnValue<?> returnValue) {
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71476_x == null || mc.field_71476_x.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK) {
            return;
        }
        BlockPos blockPos = mc.field_71476_x.func_178782_a();
        if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149688_o() == Material.field_151579_a) {
            return;
        }
        if (!returnValue.isCancelled() && !prevClickBlock.equals((Object)blockPos)) {
            startMineTime = System.currentTimeMillis();
        }
        prevClickBlock = blockPos;
        if (!returnValue.isCancelled()) {
            recentlyClickedBlocks.put(blockPos, System.currentTimeMillis());
        }
    }

    public static void onSendClickBlockToController(boolean leftClick, ReturnValue<?> returnValue) {
        if (!leftClick) {
            return;
        }
        MinecraftHook.onClickMouse(returnValue);
        if (returnValue.isCancelled()) {
            Minecraft.func_71410_x().field_71442_b.func_78767_c();
            Minecraft.func_71410_x().field_71442_b.field_178895_c = new BlockPos(-1, -1, -1);
        }
    }

    public static long getLastLockedSlotItemChange() {
        return lastLockedSlotItemChange;
    }
}

