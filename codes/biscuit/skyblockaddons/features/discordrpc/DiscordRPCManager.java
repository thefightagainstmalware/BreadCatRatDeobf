/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package codes.biscuit.skyblockaddons.features.discordrpc;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.SkyblockDate;
import codes.biscuit.skyblockaddons.features.discordrpc.DiscordStatus;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.IPCClient;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.IPCListener;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.DiscordBuild;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.RichPresence;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class DiscordRPCManager
implements IPCListener {
    private EnumUtils.DiscordStatusEntry currentEntry;
    private static final long APPLICATION_ID = 653443797182578707L;
    private static final long UPDATE_PERIOD = 4200L;
    private SkyblockAddons main = SkyblockAddons.getInstance();
    private Logger logger = SkyblockAddons.getLogger();
    private IPCClient client;
    private DiscordStatus detailsLine;
    private DiscordStatus stateLine;
    private OffsetDateTime startTimestamp;
    private Timer updateTimer;
    private boolean connected;

    public void start() {
        SkyblockAddons.runAsync(() -> {
            try {
                this.logger.info("Starting Discord RPC...");
                if (this.isActive()) {
                    return;
                }
                this.stateLine = this.main.getConfigValues().getDiscordStatus();
                this.detailsLine = this.main.getConfigValues().getDiscordDetails();
                this.startTimestamp = OffsetDateTime.now();
                this.client = new IPCClient(653443797182578707L);
                this.client.setListener(this);
                try {
                    this.client.connect(new DiscordBuild[0]);
                }
                catch (Exception ex) {
                    this.logger.warn("Failed to connect to Discord RPC!");
                    this.logger.catching((Throwable)ex);
                }
            }
            catch (Throwable ex) {
                this.logger.error("Discord RPC has thrown an unexpected error while trying to start...");
                this.logger.catching(ex);
            }
        });
    }

    public void stop() {
        SkyblockAddons.runAsync(() -> {
            if (this.isActive()) {
                this.connected = false;
                this.client.close();
            }
        });
    }

    public boolean isActive() {
        return this.client != null && this.connected;
    }

    public void updatePresence() {
        Location location = SkyblockAddons.getInstance().getUtils().getLocation();
        SkyblockDate skyblockDate = SkyblockAddons.getInstance().getUtils().getCurrentDate();
        String skyblockDateString = skyblockDate != null ? skyblockDate.toString() : "";
        String largeImageDescription = String.format("%s - %s", skyblockDateString, location.getScoreboardName());
        String smallImageDescription = String.format("Using SkyblockAddons v%s", SkyblockAddons.VERSION + " by Biscuit | Icons by Hypixel Packs HQ");
        RichPresence presence = new RichPresence.Builder().setState(this.stateLine.getDisplayString(EnumUtils.DiscordStatusEntry.STATE)).setDetails(this.detailsLine.getDisplayString(EnumUtils.DiscordStatusEntry.DETAILS)).setStartTimestamp(this.startTimestamp).setLargeImage(location.getDiscordIconKey(), largeImageDescription).setSmallImage("skyblockicon", smallImageDescription).build();
        this.client.sendRichPresence(presence);
    }

    public void setStateLine(DiscordStatus status) {
        this.stateLine = status;
        if (this.isActive()) {
            this.updatePresence();
        }
    }

    public void setDetailsLine(DiscordStatus status) {
        this.detailsLine = status;
        if (this.isActive()) {
            this.updatePresence();
        }
    }

    @Override
    public void onReady(IPCClient client) {
        this.logger.info("Discord RPC started.");
        this.connected = true;
        this.updateTimer = new Timer();
        this.updateTimer.schedule(new TimerTask(){

            @Override
            public void run() {
                DiscordRPCManager.this.updatePresence();
            }
        }, 0L, 4200L);
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {
        this.logger.info("Discord RPC closed.");
        this.client = null;
        this.connected = false;
        this.cancelTimer();
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        this.logger.warn("Discord RPC disconnected.");
        this.client = null;
        this.connected = false;
        this.cancelTimer();
    }

    private void cancelTimer() {
        if (this.updateTimer != null) {
            this.updateTimer.cancel();
            this.updateTimer = null;
        }
    }

    public EnumUtils.DiscordStatusEntry getCurrentEntry() {
        return this.currentEntry;
    }

    public void setCurrentEntry(EnumUtils.DiscordStatusEntry currentEntry) {
        this.currentEntry = currentEntry;
    }
}

