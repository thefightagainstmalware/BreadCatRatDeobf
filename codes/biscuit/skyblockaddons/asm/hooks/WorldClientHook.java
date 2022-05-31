/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.DestroyBlockProgress
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.hooks.MinecraftHook;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.npc.NPCUtils;
import codes.biscuit.skyblockaddons.events.SkyblockBlockBreakEvent;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class WorldClientHook {
    public static void onEntityRemoved(Entity entityIn) {
        NPCUtils.getNpcLocations().remove(entityIn.func_110124_au());
    }

    public static void blockUpdated(BlockPos pos, IBlockState state) {
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71439_g != null) {
            Map.Entry<BlockPos, Long> entry;
            int BEDROCK_STATE = Block.func_176210_f((IBlockState)Blocks.field_150357_h.func_176223_P());
            int AIR_STATE = Block.func_176210_f((IBlockState)Blocks.field_150350_a.func_176223_P());
            int stateBefore = Block.func_176210_f((IBlockState)mc.field_71441_e.func_180495_p(pos));
            Iterator<Map.Entry<BlockPos, Long>> itr = MinecraftHook.recentlyClickedBlocks.entrySet().iterator();
            long currTime = System.currentTimeMillis();
            while (itr.hasNext() && currTime - (entry = itr.next()).getValue() >= 300L) {
                itr.remove();
            }
            if (MinecraftHook.recentlyClickedBlocks.containsKey(pos) && stateBefore != Block.func_176210_f((IBlockState)state) && stateBefore != BEDROCK_STATE && stateBefore != AIR_STATE) {
                Location location = SkyblockAddons.getInstance().getUtils().getLocation();
                if (location == Location.GUEST_ISLAND || location == Location.ISLAND) {
                    return;
                }
                int playerID = 0;
                boolean noOneElseMining = true;
                for (Map.Entry block : mc.field_71438_f.field_72738_E.entrySet()) {
                    if ((Integer)block.getKey() == playerID || !((DestroyBlockProgress)block.getValue()).func_180246_b().equals((Object)pos)) continue;
                    noOneElseMining = false;
                }
                if (noOneElseMining) {
                    long mineTime = Math.max(System.currentTimeMillis() - MinecraftHook.startMineTime, 0L);
                    MinecraftForge.EVENT_BUS.post((Event)new SkyblockBlockBreakEvent(pos, mineTime));
                }
            }
        }
    }
}

