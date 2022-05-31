/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.gui.IslandWarpGui;
import codes.biscuit.skyblockaddons.gui.buttons.IslandMarkerButton;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class IslandButton
extends GuiButton {
    private List<IslandMarkerButton> markerButtons = new ArrayList<IslandMarkerButton>();
    private boolean disableHover = false;
    private long startedHover = -1L;
    private long stoppedHover = -1L;
    private IslandWarpGui.Island island;
    private static int ANIMATION_TIME = 200;
    private IslandWarpGui.UnlockedStatus unlockedStatus;
    private Map<IslandWarpGui.Marker, IslandWarpGui.UnlockedStatus> markers;

    public IslandButton(IslandWarpGui.Island island, IslandWarpGui.UnlockedStatus unlockedStatus, Map<IslandWarpGui.Marker, IslandWarpGui.UnlockedStatus> markers) {
        super(0, island.getX(), island.getY(), island.getLabel());
        this.island = island;
        this.unlockedStatus = unlockedStatus;
        this.markers = markers;
        for (IslandWarpGui.Marker marker : IslandWarpGui.Marker.values()) {
            if (marker.getIsland() != island) continue;
            this.markerButtons.add(new IslandMarkerButton(marker));
        }
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        this.drawButton(mc, mouseX, mouseY, true);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean actuallyDraw) {
        int timeSoFar;
        boolean unlocked;
        int hoverTime;
        int minecraftScale = new ScaledResolution(mc).func_78325_e();
        float islandGuiScale = IslandWarpGui.ISLAND_SCALE;
        mouseX *= minecraftScale;
        mouseY *= minecraftScale;
        mouseX = (int)((float)mouseX / islandGuiScale);
        mouseY = (int)((float)mouseY / islandGuiScale);
        mouseX = (int)((float)mouseX - IslandWarpGui.SHIFT_LEFT);
        mouseY = (int)((float)mouseY - IslandWarpGui.SHIFT_TOP);
        float x = this.island.getX();
        float y = this.island.getY();
        float h = this.island.getH();
        float w = this.island.getW();
        float centerX = x + w / 2.0f;
        float centerY = y + h / 2.0f;
        float expansion = 1.0f;
        boolean hovered = false;
        if (this.isHovering()) {
            hoverTime = (int)(System.currentTimeMillis() - this.startedHover);
            if (hoverTime > ANIMATION_TIME) {
                hoverTime = ANIMATION_TIME;
            }
            expansion = (float)hoverTime / (float)ANIMATION_TIME;
            expansion = (float)((double)expansion * 0.1);
            x = centerX - (w *= expansion) / 2.0f;
            y = centerY - (h *= (expansion += 1.0f)) / 2.0f;
        } else if (this.isStoppingHovering()) {
            hoverTime = (int)(System.currentTimeMillis() - this.stoppedHover);
            if (hoverTime < ANIMATION_TIME) {
                hoverTime = ANIMATION_TIME - hoverTime;
                expansion = (float)hoverTime / (float)ANIMATION_TIME;
                expansion = (float)((double)expansion * 0.1);
                x = centerX - (w *= expansion) / 2.0f;
                y = centerY - (h *= (expansion += 1.0f)) / 2.0f;
            } else {
                this.stoppedHover = -1L;
            }
        }
        boolean bl = unlocked = this.unlockedStatus == IslandWarpGui.UnlockedStatus.UNLOCKED || this.unlockedStatus == IslandWarpGui.UnlockedStatus.IN_COMBAT;
        if (!unlocked) {
            expansion = 1.0f;
            x = this.island.getX();
            y = this.island.getY();
            h = this.island.getH();
            w = this.island.getW();
        }
        if ((float)mouseX > x && (float)mouseY > y && (float)mouseX < x + w && (float)mouseY < y + h) {
            if (this.island.getBufferedImage() != null) {
                int xPixel = Math.round(((float)mouseX - x) * IslandWarpGui.IMAGE_SCALED_DOWN_FACTOR / expansion);
                int yPixel = Math.round(((float)mouseY - y) * IslandWarpGui.IMAGE_SCALED_DOWN_FACTOR / expansion);
                try {
                    int rgb = this.island.getBufferedImage().getRGB(xPixel, yPixel);
                    int alpha = (rgb & 0xFF000000) >> 24;
                    if (alpha != 0) {
                        hovered = true;
                    }
                }
                catch (IndexOutOfBoundsException rgb) {}
            } else {
                hovered = true;
            }
        }
        if (this.disableHover) {
            this.disableHover = false;
            hovered = false;
        }
        if (hovered) {
            if (!this.isHovering()) {
                this.startedHover = System.currentTimeMillis();
                if (this.isStoppingHovering()) {
                    timeSoFar = (int)(System.currentTimeMillis() - this.stoppedHover);
                    if (timeSoFar > ANIMATION_TIME) {
                        timeSoFar = ANIMATION_TIME;
                    }
                    this.startedHover -= (long)(ANIMATION_TIME - timeSoFar);
                    this.stoppedHover = -1L;
                }
            }
        } else if (this.isHovering()) {
            this.stoppedHover = System.currentTimeMillis();
            timeSoFar = (int)(System.currentTimeMillis() - this.startedHover);
            if (timeSoFar > ANIMATION_TIME) {
                timeSoFar = ANIMATION_TIME;
            }
            this.stoppedHover -= (long)(ANIMATION_TIME - timeSoFar);
            this.startedHover = -1L;
        }
        if (actuallyDraw) {
            if (unlocked) {
                if (this.unlockedStatus == IslandWarpGui.UnlockedStatus.IN_COMBAT) {
                    GlStateManager.func_179131_c((float)1.0f, (float)0.6f, (float)0.6f, (float)1.0f);
                } else if (hovered) {
                    GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                } else {
                    GlStateManager.func_179131_c((float)0.9f, (float)0.9f, (float)0.9f, (float)1.0f);
                }
            } else {
                GlStateManager.func_179131_c((float)0.3f, (float)0.3f, (float)0.3f, (float)1.0f);
            }
            mc.func_110434_K().func_110577_a(this.island.getResourceLocation());
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, w, h, w, h);
            for (IslandMarkerButton marker : this.markerButtons) {
                marker.drawButton(x, y, expansion, hovered, unlocked, this.markers.get((Object)marker.getMarker()));
            }
            GlStateManager.func_179094_E();
            float textScale = 3.0f;
            GlStateManager.func_179152_a((float)(textScale *= expansion), (float)textScale, (float)1.0f);
            int alpha = Math.max(255 - (int)((double)(expansion - 1.0f) / 0.1 * 255.0), 4);
            int color = unlocked ? ColorCode.WHITE.getColor() : ColorUtils.setColorAlpha(0x999999, alpha);
            mc.field_71466_p.func_175063_a(this.field_146126_j, centerX / textScale - (float)mc.field_71466_p.func_78256_a(this.field_146126_j) / 2.0f, centerY / textScale, color);
            if (this.unlockedStatus != IslandWarpGui.UnlockedStatus.UNLOCKED) {
                mc.field_71466_p.func_175063_a(this.unlockedStatus.getMessage(), centerX / textScale - (float)mc.field_71466_p.func_78256_a(this.unlockedStatus.getMessage()) / 2.0f, (centerY + 30.0f) / textScale, color);
            }
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179121_F();
        }
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        return false;
    }

    public boolean isHovering() {
        return this.startedHover != -1L;
    }

    private boolean isStoppingHovering() {
        return this.stoppedHover != -1L;
    }

    public void setDisableHover(boolean disableHover) {
        this.disableHover = disableHover;
    }

    public List<IslandMarkerButton> getMarkerButtons() {
        return this.markerButtons;
    }
}

