/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package codes.biscuit.skyblockaddons.events;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SkyblockBlockBreakEvent
extends Event {
    public BlockPos blockPos;
    public long timeToBreak;

    public SkyblockBlockBreakEvent(BlockPos pos) {
        this(pos, 0L);
    }

    public SkyblockBlockBreakEvent(BlockPos pos, long breakTime) {
        this.blockPos = pos;
        this.timeToBreak = breakTime;
    }
}

