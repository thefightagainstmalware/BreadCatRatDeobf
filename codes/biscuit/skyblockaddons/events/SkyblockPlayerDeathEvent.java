/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.event.entity.player.PlayerEvent
 */
package codes.biscuit.skyblockaddons.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkyblockPlayerDeathEvent
extends PlayerEvent {
    public final String username;
    public final String cause;

    public SkyblockPlayerDeathEvent(EntityPlayer player, String username, String cause) {
        super(player);
        this.username = username;
        this.cause = cause;
    }
}

