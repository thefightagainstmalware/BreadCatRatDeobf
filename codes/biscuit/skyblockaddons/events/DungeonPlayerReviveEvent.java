/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package codes.biscuit.skyblockaddons.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class DungeonPlayerReviveEvent
extends Event {
    public final EntityPlayer revivedPlayer;
    public final EntityPlayer reviverPlayer;

    public DungeonPlayerReviveEvent(EntityPlayer revivedPlayer, EntityPlayer reviverPlayer) {
        this.revivedPlayer = revivedPlayer;
        this.reviverPlayer = reviverPlayer;
    }
}

