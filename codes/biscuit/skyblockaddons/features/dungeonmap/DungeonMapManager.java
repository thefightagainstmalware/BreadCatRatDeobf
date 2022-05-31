/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.MapItemRenderer
 *  net.minecraft.client.gui.MapItemRenderer$Instance
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec4b
 *  net.minecraft.world.World
 *  net.minecraft.world.storage.MapData
 *  org.lwjgl.opengl.GL11
 */
package codes.biscuit.skyblockaddons.features.dungeonmap;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.chroma.ManualChromaManager;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonPlayer;
import codes.biscuit.skyblockaddons.features.dungeonmap.MapMarker;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonLocation;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.TreeSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class DungeonMapManager {
    private static SkyblockAddons main = SkyblockAddons.getInstance();
    private static final ResourceLocation DUNGEON_MAP = new ResourceLocation("skyblockaddons", "dungeonsmap.png");
    private static final Comparator<MapMarker> MAP_MARKER_COMPARATOR = (first, second) -> {
        boolean secondIsNull;
        boolean firstIsNull = first.getMapMarkerName() == null;
        boolean bl = secondIsNull = second.getMapMarkerName() == null;
        if (first.getIconType() != second.getIconType()) {
            return Byte.compare(second.getIconType(), first.getIconType());
        }
        if (firstIsNull && secondIsNull) {
            return 0;
        }
        if (firstIsNull) {
            return 1;
        }
        if (secondIsNull) {
            return -1;
        }
        return second.getMapMarkerName().compareTo(first.getMapMarkerName());
    };
    private static MapData mapData;
    private static float mapStartX;
    private static float mapStartZ;
    private static NavigableMap<Long, Vec3> previousLocations;
    private static Map<String, Vec4b> savedMapDecorations;

    public static void drawDungeonsMap(Minecraft mc, float scale, ButtonLocation buttonLocation) {
        if (buttonLocation == null && !main.getUtils().isInDungeon()) {
            mapStartX = -1.0f;
            mapStartZ = -1.0f;
            mapData = null;
        }
        ItemStack possibleMapItemStack = mc.field_71439_g.field_71071_by.func_70301_a(8);
        if (!(buttonLocation != null || possibleMapItemStack != null && possibleMapItemStack.func_77973_b() == Items.field_151098_aY && possibleMapItemStack.func_82837_s() || mapData != null)) {
            return;
        }
        boolean isScoreSummary = false;
        if (buttonLocation == null && possibleMapItemStack != null && possibleMapItemStack.func_77973_b() == Items.field_151098_aY) {
            isScoreSummary = possibleMapItemStack.func_82833_r().contains("Your Score Summary");
            if (!possibleMapItemStack.func_82833_r().contains("Magical Map") && !isScoreSummary) {
                return;
            }
        }
        float x = main.getConfigValues().getActualX(Feature.DUNGEONS_MAP_DISPLAY);
        float y = main.getConfigValues().getActualY(Feature.DUNGEONS_MAP_DISPLAY);
        GlStateManager.func_179094_E();
        int originalSize = 128;
        float initialScaleFactor = 0.5f;
        int size = (int)((float)originalSize * initialScaleFactor);
        int minecraftScale = new ScaledResolution(mc).func_78325_e();
        GL11.glEnable((int)3089);
        GL11.glScissor((int)Math.round((x - (float)size / 2.0f * scale) * (float)minecraftScale), (int)(mc.field_71440_d - Math.round((y + (float)size / 2.0f * scale) * (float)minecraftScale)), (int)Math.round((float)(size * minecraftScale) * scale), (int)Math.round((float)(size * minecraftScale) * scale));
        x = main.getRenderListener().transformXY(x, size, scale);
        y = main.getRenderListener().transformXY(y, size, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)size, y, y + (float)size, scale);
        }
        GL11.glDisable((int)3089);
        int color = main.getConfigValues().getColor(Feature.DUNGEONS_MAP_DISPLAY);
        DrawUtils.drawRectAbsolute(x, y, x + (float)size, y + (float)size, 0x55000000);
        ManualChromaManager.renderingText(Feature.DUNGEONS_MAP_DISPLAY);
        DrawUtils.drawRectOutline(x, y, size, size, 1, color, main.getConfigValues().getChromaFeatures().contains((Object)Feature.DUNGEONS_MAP_DISPLAY));
        ManualChromaManager.doneRenderingText();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3089);
        main.getUtils().enableStandardGLOptions();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float rotation = 180.0f - MathHelper.func_76142_g((float)mc.field_71439_g.field_70177_z);
        float zoomScaleFactor = MathUtils.denormalizeSliderValue(main.getConfigValues().getMapZoom().getValue().floatValue(), 0.5f, 5.0f, 0.1f);
        if (isScoreSummary) {
            zoomScaleFactor = 1.0f;
        }
        float totalScaleFactor = initialScaleFactor * zoomScaleFactor;
        float mapSize = (float)originalSize * totalScaleFactor;
        GlStateManager.func_179152_a((float)totalScaleFactor, (float)totalScaleFactor, (float)1.0f);
        GlStateManager.func_179109_b((float)(x /= totalScaleFactor), (float)(y /= totalScaleFactor), (float)0.0f);
        float rotationCenterX = (float)originalSize * initialScaleFactor;
        float rotationCenterY = (float)originalSize * initialScaleFactor;
        float centerOffset = -((mapSize - (float)size) / zoomScaleFactor);
        GlStateManager.func_179109_b((float)centerOffset, (float)centerOffset, (float)0.0f);
        boolean rotate = main.getConfigValues().isEnabled(Feature.ROTATE_MAP);
        boolean rotateOnPlayer = main.getConfigValues().isEnabled(Feature.CENTER_ROTATION_ON_PLAYER);
        if (isScoreSummary) {
            rotate = false;
        }
        if (buttonLocation == null) {
            try {
                boolean foundMapData = false;
                MapData newMapData = null;
                if (possibleMapItemStack != null) {
                    newMapData = Items.field_151098_aY.func_77873_a(possibleMapItemStack, (World)mc.field_71441_e);
                }
                if (newMapData != null) {
                    mapData = newMapData;
                    foundMapData = true;
                }
                if (mapData != null) {
                    Object lastSecondVector;
                    float playerX = (float)mc.field_71439_g.field_70165_t;
                    float playerZ = (float)mc.field_71439_g.field_70161_v;
                    long now = System.currentTimeMillis();
                    previousLocations.entrySet().removeIf(entry -> (Long)entry.getKey() < now - 1000L);
                    Vec3 currentVector = mc.field_71439_g.func_174791_d();
                    previousLocations.put(now, currentVector);
                    double lastSecondTravel = -1.0;
                    Map.Entry<Long, Vec3> closestEntry = previousLocations.ceilingEntry(now - 1000L);
                    if (closestEntry != null && (lastSecondVector = closestEntry.getValue()) != null) {
                        lastSecondTravel = lastSecondVector.func_72438_d(currentVector);
                    }
                    if (foundMapData && (mapStartX == -1.0f || mapStartZ == -1.0f || lastSecondTravel == 0.0) && DungeonMapManager.mapData.field_76203_h != null) {
                        for (Map.Entry entry2 : DungeonMapManager.mapData.field_76203_h.entrySet()) {
                            if (((Vec4b)entry2.getValue()).func_176110_a() != 1) continue;
                            float mapMarkerX = (float)((Vec4b)entry2.getValue()).func_176112_b() / 2.0f + 64.0f;
                            float mapMarkerZ = (float)((Vec4b)entry2.getValue()).func_176113_c() / 2.0f + 64.0f;
                            float mapStartX = playerX - mapMarkerX * 1.5f;
                            float mapStartZ = playerZ - mapMarkerZ * 1.5f;
                            DungeonMapManager.mapStartX = (float)Math.round(mapStartX / 16.0f) * 16.0f;
                            DungeonMapManager.mapStartZ = (float)Math.round(mapStartZ / 16.0f) * 16.0f;
                        }
                    }
                    float playerMarkerX = (playerX - mapStartX) / 1.5f;
                    float f = (playerZ - mapStartZ) / 1.5f;
                    if (rotate && rotateOnPlayer) {
                        rotationCenterX = playerMarkerX;
                        rotationCenterY = f;
                    }
                    if (rotate) {
                        if (rotateOnPlayer) {
                            GlStateManager.func_179109_b((float)((float)size - rotationCenterX), (float)((float)size - rotationCenterY), (float)0.0f);
                        }
                        GlStateManager.func_179109_b((float)rotationCenterX, (float)rotationCenterY, (float)0.0f);
                        GlStateManager.func_179114_b((float)rotation, (float)0.0f, (float)0.0f, (float)1.0f);
                        GlStateManager.func_179109_b((float)(-rotationCenterX), (float)(-rotationCenterY), (float)0.0f);
                    }
                    MapItemRenderer.Instance instance = mc.field_71460_t.func_147701_i().func_148248_b(mapData);
                    DungeonMapManager.drawMapEdited(instance, isScoreSummary, zoomScaleFactor);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            if (rotate) {
                float ticks = (float)(System.currentTimeMillis() % 18000L) / 50.0f;
                GlStateManager.func_179109_b((float)rotationCenterX, (float)rotationCenterY, (float)0.0f);
                GlStateManager.func_179114_b((float)ticks, (float)0.0f, (float)0.0f, (float)1.0f);
                GlStateManager.func_179109_b((float)(-rotationCenterX), (float)(-rotationCenterY), (float)0.0f);
            }
            mc.func_110434_K().func_110577_a(DUNGEON_MAP);
            DrawUtils.drawModalRectWithCustomSizedTexture(0.0f, 0.0f, 0.0f, 0.0f, 128.0f, 128.0f, 128.0f, 128.0f);
        }
        GL11.glDisable((int)3089);
        GlStateManager.func_179121_F();
        main.getUtils().restoreGLOptions();
    }

    public static void drawMapEdited(MapItemRenderer.Instance instance, boolean isScoreSummary, float zoom) {
        Minecraft mc = Minecraft.func_71410_x();
        int startX = 0;
        int startY = 0;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        float f = 0.0f;
        GlStateManager.func_179098_w();
        mc.func_110434_K().func_110577_a(instance.field_148240_d);
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)1, (int)771, (int)0, (int)1);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)((float)startX + f), (double)((float)(startY + 128) - f), (double)-0.01f).func_181673_a(0.0, 1.0).func_181675_d();
        worldrenderer.func_181662_b((double)((float)(startX + 128) - f), (double)((float)(startY + 128) - f), (double)-0.01f).func_181673_a(1.0, 1.0).func_181675_d();
        worldrenderer.func_181662_b((double)((float)(startX + 128) - f), (double)((float)startY + f), (double)-0.01f).func_181673_a(1.0, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)((float)startX + f), (double)((float)startY + f), (double)-0.01f).func_181673_a(0.0, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        mc.func_110434_K().func_110577_a(MapItemRenderer.field_148253_a);
        int decorationCount = 0;
        if (isScoreSummary) {
            return;
        }
        if (!instance.field_148242_b.field_76203_h.isEmpty()) {
            savedMapDecorations.clear();
            savedMapDecorations.putAll(instance.field_148242_b.field_76203_h);
        }
        HashSet<String> dontAddMarkerNames = new HashSet<String>();
        TreeSet<MapMarker> allMarkers = new TreeSet<MapMarker>(MAP_MARKER_COMPARATOR);
        Map<String, DungeonPlayer> teammates = main.getDungeonManager().getTeammates();
        for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
            MapMarker playerMarker = null;
            if (teammates.containsKey(entityPlayer.func_70005_c_())) {
                DungeonPlayer dungeonTeammate = teammates.get(entityPlayer.func_70005_c_());
                playerMarker = DungeonMapManager.getMapMarkerForPlayer(dungeonTeammate, entityPlayer);
            } else if (entityPlayer == mc.field_71439_g) {
                playerMarker = DungeonMapManager.getMapMarkerForPlayer(null, entityPlayer);
            }
            if (playerMarker == null) continue;
            if (playerMarker.getMapMarkerName() != null) {
                dontAddMarkerNames.add(playerMarker.getMapMarkerName());
            }
            allMarkers.add(playerMarker);
        }
        for (Map.Entry entry : savedMapDecorations.entrySet()) {
            if (dontAddMarkerNames.contains(entry.getKey()) || ((Vec4b)entry.getValue()).func_176110_a() == 1) continue;
            DungeonPlayer foundDungeonPlayer = null;
            boolean linkedToPlayer = false;
            for (DungeonPlayer dungeonPlayer : teammates.values()) {
                if (dungeonPlayer.getMapMarker() == null || dungeonPlayer.getMapMarker().getMapMarkerName() == null || !((String)entry.getKey()).equals(dungeonPlayer.getMapMarker().getMapMarkerName())) continue;
                linkedToPlayer = true;
                foundDungeonPlayer = dungeonPlayer;
                break;
            }
            if (!linkedToPlayer) {
                allMarkers.add(new MapMarker(((Vec4b)entry.getValue()).func_176110_a(), ((Vec4b)entry.getValue()).func_176112_b(), ((Vec4b)entry.getValue()).func_176113_c(), ((Vec4b)entry.getValue()).func_176111_d()));
                continue;
            }
            MapMarker mapMarker = foundDungeonPlayer.getMapMarker();
            mapMarker.setX(((Vec4b)entry.getValue()).func_176112_b());
            mapMarker.setZ(((Vec4b)entry.getValue()).func_176113_c());
            mapMarker.setRotation(((Vec4b)entry.getValue()).func_176111_d());
            allMarkers.add(mapMarker);
        }
        for (MapMarker mapMarker : allMarkers) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)((float)startX + mapMarker.getX() / 2.0f + 64.0f), (float)((float)startY + mapMarker.getZ() / 2.0f + 64.0f), (float)-0.02f);
            GlStateManager.func_179114_b((float)(mapMarker.getRotation() * 360.0f / 16.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.func_179152_a((float)(4.0f / zoom), (float)(4.0f / zoom), (float)3.0f);
            byte iconType = mapMarker.getIconType();
            float f1 = (float)(iconType % 4) / 4.0f;
            float f2 = (float)(iconType / 4) / 4.0f;
            float f3 = (float)(iconType % 4 + 1) / 4.0f;
            float f4 = (float)(iconType / 4 + 1) / 4.0f;
            NetworkPlayerInfo markerNetworkPlayerInfo = null;
            if (main.getConfigValues().isEnabled(Feature.SHOW_PLAYER_HEADS_ON_MAP) && mapMarker.getPlayerName() != null) {
                for (NetworkPlayerInfo networkPlayerInfo : mc.func_147114_u().func_175106_d()) {
                    if (!mapMarker.getPlayerName().equals(networkPlayerInfo.func_178845_a().getName())) continue;
                    markerNetworkPlayerInfo = networkPlayerInfo;
                    break;
                }
            }
            if (markerNetworkPlayerInfo != null) {
                GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                DrawUtils.drawRectAbsolute(-1.2, -1.2, 1.2, 1.2, -16777216);
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                if (main.getConfigValues().isEnabled(Feature.SHOW_CRITICAL_DUNGEONS_TEAMMATES) && teammates.containsKey(mapMarker.getPlayerName())) {
                    DungeonPlayer dungeonPlayer = teammates.get(mapMarker.getPlayerName());
                    if (dungeonPlayer.isLow()) {
                        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)0.5f, (float)1.0f);
                    } else if (dungeonPlayer.isCritical()) {
                        GlStateManager.func_179131_c((float)1.0f, (float)0.5f, (float)0.5f, (float)1.0f);
                    }
                }
                mc.func_110434_K().func_110577_a(markerNetworkPlayerInfo.func_178837_g());
                DrawUtils.drawScaledCustomSizeModalRect(-1.0f, -1.0f, 8.0f, 8.0f, 8.0f, 8.0f, 2.0f, 2.0f, 64.0f, 64.0f, false);
                if (mapMarker.isWearingHat()) {
                    DrawUtils.drawScaledCustomSizeModalRect(-1.0f, -1.0f, 40.0f, 8.0f, 8.0f, 8.0f, 2.0f, 2.0f, 64.0f, 64.0f, false);
                }
            } else {
                GlStateManager.func_179109_b((float)-0.125f, (float)0.125f, (float)0.0f);
                mc.func_110434_K().func_110577_a(MapItemRenderer.field_148253_a);
                worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                float eachDecorationZOffset = -0.001f;
                worldrenderer.func_181662_b(-1.0, 1.0, (double)((float)decorationCount * eachDecorationZOffset)).func_181673_a((double)f1, (double)f2).func_181675_d();
                worldrenderer.func_181662_b(1.0, 1.0, (double)((float)decorationCount * eachDecorationZOffset)).func_181673_a((double)f3, (double)f2).func_181675_d();
                worldrenderer.func_181662_b(1.0, -1.0, (double)((float)decorationCount * eachDecorationZOffset)).func_181673_a((double)f3, (double)f4).func_181675_d();
                worldrenderer.func_181662_b(-1.0, -1.0, (double)((float)decorationCount * eachDecorationZOffset)).func_181673_a((double)f1, (double)f4).func_181675_d();
                tessellator.func_78381_a();
            }
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179121_F();
            ++decorationCount;
        }
    }

    public static MapMarker getMapMarkerForPlayer(DungeonPlayer dungeonPlayer, EntityPlayer player) {
        MapMarker mapMarker;
        if (dungeonPlayer != null) {
            if (dungeonPlayer.getMapMarker() == null) {
                mapMarker = new MapMarker(player);
                dungeonPlayer.setMapMarker(mapMarker);
            } else {
                mapMarker = dungeonPlayer.getMapMarker();
                mapMarker.updateXZRot(player);
            }
        } else {
            mapMarker = new MapMarker(player);
        }
        int duplicates = 0;
        Map.Entry<String, Vec4b> duplicate = null;
        for (Map.Entry<String, Vec4b> vec4b : savedMapDecorations.entrySet()) {
            if (vec4b.getValue().func_176110_a() != mapMarker.getIconType() || !(Math.abs((float)vec4b.getValue().func_176112_b() - mapMarker.getX()) <= 5.0f) || !(Math.abs((float)vec4b.getValue().func_176113_c() - mapMarker.getZ()) <= 5.0f)) continue;
            ++duplicates;
            duplicate = vec4b;
        }
        if (duplicates == 1) {
            mapMarker.setMapMarkerName((String)duplicate.getKey());
        }
        return mapMarker;
    }

    public static float getMapStartX() {
        return mapStartX;
    }

    public static float getMapStartZ() {
        return mapStartZ;
    }

    static {
        mapStartX = -1.0f;
        mapStartZ = -1.0f;
        previousLocations = new TreeMap<Long, Vec3>();
        savedMapDecorations = new HashMap<String, Vec4b>();
    }
}

