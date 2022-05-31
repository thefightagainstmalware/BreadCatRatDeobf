/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.gui.IslandWarpGui;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import java.awt.geom.Point2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class IslandMarkerButton
extends GuiButton {
    public static final int MAX_SELECT_RADIUS = 90;
    private static final ResourceLocation PORTAL_ICON = new ResourceLocation("skyblockaddons", "portal.png");
    private IslandWarpGui.Marker marker;
    private float centerX;
    private float centerY;
    private boolean unlocked;

    public IslandMarkerButton(IslandWarpGui.Marker marker) {
        super(0, 0, 0, marker.getLabel());
        this.marker = marker;
    }

    public void drawButton(float islandX, float islandY, float expansion, boolean hovered, boolean islandUnlocked, IslandWarpGui.UnlockedStatus status) {
        Minecraft mc = Minecraft.func_71410_x();
        SkyblockAddons main = SkyblockAddons.getInstance();
        float width = 50.0f * expansion;
        float height = width * 1.2345679f;
        float centerX = islandX + (float)this.marker.getX() * expansion;
        float centerY = islandY + (float)this.marker.getY() * expansion;
        this.centerX = centerX;
        this.centerY = centerY;
        this.unlocked = status == IslandWarpGui.UnlockedStatus.UNLOCKED || status == IslandWarpGui.UnlockedStatus.IN_COMBAT;
        float x = centerX - width / 2.0f;
        float y = centerY - height / 2.0f;
        if (this.unlocked) {
            if (hovered) {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            } else {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)0.6f);
            }
        } else if (islandUnlocked) {
            GlStateManager.func_179131_c((float)0.3f, (float)0.3f, (float)0.3f, (float)1.0f);
        } else {
            GlStateManager.func_179131_c((float)0.3f, (float)0.3f, (float)0.3f, (float)0.6f);
        }
        mc.func_110434_K().func_110577_a(PORTAL_ICON);
        DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height, true);
        if (hovered) {
            GlStateManager.func_179094_E();
            float textScale = 2.5f;
            GlStateManager.func_179152_a((float)(textScale *= expansion), (float)textScale, (float)1.0f);
            int alpha = Math.max((int)((double)(expansion - 1.0f) / 0.1 * 255.0), 4);
            int color = this.unlocked ? ColorCode.WHITE.getColor() : ColorUtils.setColorAlpha(0x999999, alpha);
            mc.field_71466_p.func_175063_a(this.field_146126_j, (x + width / 2.0f) / textScale - (float)mc.field_71466_p.func_78256_a(this.field_146126_j) / 2.0f, (y - 20.0f) / textScale, color);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179121_F();
        }
    }

    public double getDistance(int mouseX, int mouseY) {
        double distance = new Point2D.Double(mouseX, mouseY).distance(new Point2D.Double(this.centerX, this.centerY));
        if (distance > 90.0 || !this.unlocked) {
            distance = -1.0;
        }
        return distance;
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        return false;
    }

    public IslandWarpGui.Marker getMarker() {
        return this.marker;
    }
}

