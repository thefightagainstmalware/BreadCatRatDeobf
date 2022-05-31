/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.network.FMLNetworkEvent$ClientDisconnectionFromServerEvent
 */
package codes.biscuit.skyblockaddons.listeners;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.events.SkyblockJoinedEvent;
import codes.biscuit.skyblockaddons.events.SkyblockLeftEvent;
import codes.biscuit.skyblockaddons.misc.scheduler.ScheduledTask;
import codes.biscuit.skyblockaddons.misc.scheduler.SkyblockRunnable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class NetworkListener {
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private ScheduledTask updateHealth;

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        MinecraftForge.EVENT_BUS.post((Event)new SkyblockLeftEvent());
    }

    @SubscribeEvent
    public void onSkyblockJoined(SkyblockJoinedEvent event) {
        SkyblockAddons.getLogger().info("Detected joining skyblock!");
        this.main.getUtils().setOnSkyblock(true);
        if (this.main.getConfigValues().isEnabled(Feature.DISCORD_RPC)) {
            this.main.getDiscordRPCManager().start();
        }
        this.updateHealth = this.main.getNewScheduler().scheduleRepeatingTask(new SkyblockRunnable(){

            @Override
            public void run() {
                NetworkListener.this.main.getPlayerListener().updateLastSecondHealth();
            }
        }, 0, 20);
    }

    @SubscribeEvent
    public void onSkyblockLeft(SkyblockLeftEvent event) {
        SkyblockAddons.getLogger().info("Detected leaving skyblock!");
        this.main.getUtils().setOnSkyblock(false);
        this.main.getUtils().setProfileName("Unknown");
        if (this.main.getDiscordRPCManager().isActive()) {
            this.main.getDiscordRPCManager().stop();
        }
        if (this.updateHealth != null) {
            this.main.getNewScheduler().cancel(this.updateHealth);
            this.updateHealth = null;
        }
    }
}

