/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc;

import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.IPCClient;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.Packet;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.User;
import org.json.JSONObject;

public interface IPCListener {
    default public void onPacketSent(IPCClient client, Packet packet) {
    }

    default public void onPacketReceived(IPCClient client, Packet packet) {
    }

    default public void onActivityJoin(IPCClient client, String secret) {
    }

    default public void onActivitySpectate(IPCClient client, String secret) {
    }

    default public void onActivityJoinRequest(IPCClient client, String secret, User user) {
    }

    default public void onReady(IPCClient client) {
    }

    default public void onClose(IPCClient client, JSONObject json) {
    }

    default public void onDisconnect(IPCClient client, Throwable t) {
    }
}

