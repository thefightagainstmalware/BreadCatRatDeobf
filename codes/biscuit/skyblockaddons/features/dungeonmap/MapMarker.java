/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EnumPlayerModelParts
 *  net.minecraft.util.MathHelper
 */
package codes.biscuit.skyblockaddons.features.dungeonmap;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.features.dungeonmap.DungeonMapManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

public class MapMarker {
    private SkyblockAddons main = SkyblockAddons.getInstance();
    private byte iconType;
    private float x;
    private float z;
    private float rotation;
    private String playerName;
    private String mapMarkerName;
    private boolean wearingHat;

    public MapMarker(EntityPlayer player) {
        this.playerName = player.func_70005_c_();
        this.wearingHat = player.func_175148_a(EnumPlayerModelParts.HAT);
        this.iconType = player == Minecraft.func_71410_x().field_71439_g ? (byte)1 : (byte)3;
        this.updateXZRot(player);
    }

    public MapMarker(byte iconType, float x, float z, float rotation) {
        this.iconType = iconType;
        this.x = x;
        this.z = z;
        this.rotation = rotation;
    }

    public void setMapMarkerName(String mapMarkerName) {
        this.mapMarkerName = mapMarkerName;
    }

    public EntityPlayer getPlayer() {
        for (EntityPlayer player : Minecraft.func_71410_x().field_71441_e.field_73010_i) {
            if (!player.func_70005_c_().equals(this.playerName)) continue;
            return player;
        }
        return null;
    }

    public void updateXZRot(EntityPlayer player) {
        this.x = (((float)player.field_70165_t - DungeonMapManager.getMapStartX()) / 1.5f - 64.0f) * 2.0f;
        this.z = (((float)player.field_70161_v - DungeonMapManager.getMapStartZ()) / 1.5f - 64.0f) * 2.0f;
        this.rotation = MathHelper.func_76142_g((float)player.field_70177_z) / 360.0f * 16.0f;
    }

    public void setMain(SkyblockAddons main) {
        this.main = main;
    }

    public void setIconType(byte iconType) {
        this.iconType = iconType;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setWearingHat(boolean wearingHat) {
        this.wearingHat = wearingHat;
    }

    public SkyblockAddons getMain() {
        return this.main;
    }

    public byte getIconType() {
        return this.iconType;
    }

    public float getX() {
        return this.x;
    }

    public float getZ() {
        return this.z;
    }

    public float getRotation() {
        return this.rotation;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getMapMarkerName() {
        return this.mapMarkerName;
    }

    public boolean isWearingHat() {
        return this.wearingHat;
    }
}

