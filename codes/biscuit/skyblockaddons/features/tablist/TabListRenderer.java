/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiPlayerTabOverlay
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EnumPlayerModelParts
 */
package codes.biscuit.skyblockaddons.features.tablist;

import codes.biscuit.skyblockaddons.features.tablist.RenderColumn;
import codes.biscuit.skyblockaddons.features.tablist.TabLine;
import codes.biscuit.skyblockaddons.features.tablist.TabListParser;
import codes.biscuit.skyblockaddons.features.tablist.TabStringType;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class TabListRenderer {
    public static final int MAX_LINES = 22;
    private static final int LINE_HEIGHT = 9;
    private static final int PADDING = 3;
    private static final int COLUMN_SPACING = 6;

    /*
     * WARNING - void declaration
     */
    public static void render() {
        void var4_9;
        int n;
        Minecraft mc = Minecraft.func_71410_x();
        List<RenderColumn> columns = TabListParser.getRenderColumns();
        if (columns == null) {
            return;
        }
        int maxLines = 0;
        for (RenderColumn renderColumn : columns) {
            maxLines = Math.max(maxLines, renderColumn.getLines().size());
        }
        int totalWidth = 0;
        for (RenderColumn renderColumn : columns) {
            totalWidth += renderColumn.getMaxWidth() + 6;
        }
        totalWidth -= 6;
        int n2 = maxLines * 9;
        GuiPlayerTabOverlay tabList = mc.field_71456_v.func_175181_h();
        ArrayList<String> header = null;
        if (tabList.field_175256_i != null) {
            header = new ArrayList<String>(Arrays.asList(tabList.field_175256_i.func_150254_d().split("\n")));
            header.removeIf(line -> !line.contains(TabListParser.HYPIXEL_ADVERTISEMENT_CONTAINS));
            n = n2 + (header.size() * 9 + 3);
        }
        ArrayList<String> footer = null;
        if (tabList.field_175255_h != null) {
            footer = new ArrayList<String>(Arrays.asList(tabList.field_175255_h.func_150254_d().split("\n")));
            footer.removeIf(line -> !line.contains(TabListParser.HYPIXEL_ADVERTISEMENT_CONTAINS));
            var4_9 = n + (footer.size() * 9 + 3);
        }
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.func_78326_a() / 2;
        int x = screenWidth - totalWidth / 2;
        int y = 10;
        Gui.func_73734_a((int)(x - 6), (int)(y - 3), (int)(screenWidth + totalWidth / 2 + 6), (int)(10 + var4_9 + 3), (int)Integer.MIN_VALUE);
        int headerY = y;
        if (header != null) {
            for (String string : header) {
                mc.field_71466_p.func_175063_a(string, (float)x + (float)totalWidth / 2.0f - (float)mc.field_71466_p.func_78256_a(string) / 2.0f, (float)headerY, -1);
                headerY += 9;
            }
            headerY += 3;
        }
        int middleX = x;
        for (RenderColumn renderColumn : columns) {
            int middleY = headerY;
            Gui.func_73734_a((int)(middleX - 3 + 1), (int)(middleY - 3 + 1), (int)(middleX + renderColumn.getMaxWidth() + 3 - 2), (int)(middleY + renderColumn.getLines().size() * 9 + 3 - 2), (int)0x20AAAAAA);
            for (TabLine tabLine : renderColumn.getLines()) {
                int savedX = middleX;
                if (tabLine.getType() == TabStringType.PLAYER) {
                    NetworkPlayerInfo networkPlayerInfo = mc.func_147114_u().func_175104_a(TextUtils.stripColor(tabLine.getText()));
                    if (networkPlayerInfo != null) {
                        EntityPlayer entityPlayer = mc.field_71441_e.func_152378_a(networkPlayerInfo.func_178845_a().getId());
                        mc.func_110434_K().func_110577_a(networkPlayerInfo.func_178837_g());
                        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        Gui.func_152125_a((int)middleX, (int)middleY, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)8, (int)8, (float)64.0f, (float)64.0f);
                        if (entityPlayer != null && entityPlayer.func_175148_a(EnumPlayerModelParts.HAT)) {
                            Gui.func_152125_a((int)middleX, (int)middleY, (float)40.0f, (float)8.0f, (int)8, (int)8, (int)8, (int)8, (float)64.0f, (float)64.0f);
                        }
                    }
                    middleX += 10;
                }
                if (tabLine.getType() == TabStringType.TITLE) {
                    mc.field_71466_p.func_175063_a(tabLine.getText(), (float)middleX + (float)renderColumn.getMaxWidth() / 2.0f - (float)tabLine.getWidth() / 2.0f, (float)middleY, -1);
                } else {
                    mc.field_71466_p.func_175063_a(tabLine.getText(), (float)middleX, (float)middleY, -1);
                }
                middleY += 9;
                middleX = savedX;
            }
            middleX += renderColumn.getMaxWidth() + 6;
        }
        if (footer != null) {
            int n3 = y + var4_9 - footer.size() * 9;
            for (String line3 : footer) {
                void var14_23;
                mc.field_71466_p.func_175063_a(line3, (float)x + (float)totalWidth / 2.0f - (float)mc.field_71466_p.func_78256_a(line3) / 2.0f, (float)var14_23, -1);
                var14_23 += 9;
            }
        }
    }
}

