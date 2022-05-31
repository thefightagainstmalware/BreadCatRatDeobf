/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 */
package codes.biscuit.skyblockaddons.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.gui.ColorSelectionGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonColorWheel;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonLocation;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonResize;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSolid;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import com.google.common.collect.Sets;
import java.awt.Color;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class LocationEditGui
extends GuiScreen {
    private EditMode editMode = EditMode.RESCALE;
    private boolean showColorIcons = true;
    private boolean enableSnapping = true;
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private Feature dragging;
    private boolean resizing;
    private ButtonResize.Corner resizingCorner;
    private int originalHeight;
    private int originalWidth;
    private float xOffset;
    private float yOffset;
    private final int lastPage;
    private final EnumUtils.GuiTab lastTab;
    private final Map<Feature, ButtonLocation> buttonLocations = new EnumMap<Feature, ButtonLocation>(Feature.class);
    private boolean closing = false;
    private static final int SNAPPING_RADIUS = 120;
    private static final int SNAP_PULL = 1;

    public LocationEditGui(int lastPage, EnumUtils.GuiTab lastTab) {
        this.lastPage = lastPage;
        this.lastTab = lastTab;
    }

    public void func_73866_w_() {
        ButtonLocation buttonLocation;
        for (Feature feature : Feature.getGuiFeatures()) {
            if (feature.getGuiFeatureData() != null && feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.TEXT || this.main.getConfigValues().isDisabled(feature)) continue;
            buttonLocation = new ButtonLocation(feature);
            this.field_146292_n.add(buttonLocation);
            this.buttonLocations.put(feature, buttonLocation);
        }
        for (Feature feature : Feature.getGuiFeatures()) {
            if (feature.getGuiFeatureData() == null || feature.getGuiFeatureData().getDrawType() != EnumUtils.DrawType.TEXT || this.main.getConfigValues().isDisabled(feature)) continue;
            buttonLocation = new ButtonLocation(feature);
            this.field_146292_n.add(buttonLocation);
            this.buttonLocations.put(feature, buttonLocation);
        }
        if (this.editMode == EditMode.RESIZE_BARS) {
            this.addResizeButtonsToBars();
        } else if (this.editMode == EditMode.RESCALE) {
            this.addResizeButtonsToAllFeatures();
        }
        this.addColorWheelsToAllFeatures();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        int boxHeight = 20;
        int numButtons = 5;
        int y = scaledResolution.func_78328_b() / 2;
        y = numButtons % 2 == 0 ? (int)((double)y - ((double)Math.round((float)numButtons / 2.0f * (float)(boxHeight + 5)) - 2.5)) : (y -= Math.round((float)(numButtons - 1) / 2.0f * (float)(boxHeight + 5)) + 10);
        String text = Message.SETTING_RESET_LOCATIONS.getMessage(new String[0]);
        int boxWidth = this.field_146297_k.field_71466_p.func_78256_a(text) + 10;
        if (boxWidth > 140) {
            boxWidth = 140;
        }
        int x = scaledResolution.func_78326_a() / 2 - boxWidth / 2;
        this.field_146292_n.add(new ButtonSolid(x, y, boxWidth, boxHeight, text, this.main, Feature.RESET_LOCATION));
        text = Feature.RESCALE_FEATURES.getMessage(new String[0]);
        boxWidth = this.field_146297_k.field_71466_p.func_78256_a(text) + 10;
        if (boxWidth > 140) {
            boxWidth = 140;
        }
        x = scaledResolution.func_78326_a() / 2 - boxWidth / 2;
        this.field_146292_n.add(new ButtonSolid(x, y += boxHeight + 5, boxWidth, boxHeight, text, this.main, Feature.RESCALE_FEATURES));
        text = Feature.RESIZE_BARS.getMessage(new String[0]);
        boxWidth = this.field_146297_k.field_71466_p.func_78256_a(text) + 10;
        if (boxWidth > 140) {
            boxWidth = 140;
        }
        x = scaledResolution.func_78326_a() / 2 - boxWidth / 2;
        this.field_146292_n.add(new ButtonSolid(x, y += boxHeight + 5, boxWidth, boxHeight, text, this.main, Feature.RESIZE_BARS));
        text = Feature.SHOW_COLOR_ICONS.getMessage(new String[0]);
        boxWidth = this.field_146297_k.field_71466_p.func_78256_a(text) + 10;
        if (boxWidth > 140) {
            boxWidth = 140;
        }
        x = scaledResolution.func_78326_a() / 2 - boxWidth / 2;
        this.field_146292_n.add(new ButtonSolid(x, y += boxHeight + 5, boxWidth, boxHeight, text, this.main, Feature.SHOW_COLOR_ICONS));
        text = Feature.ENABLE_FEATURE_SNAPPING.getMessage(new String[0]);
        boxWidth = this.field_146297_k.field_71466_p.func_78256_a(text) + 10;
        if (boxWidth > 140) {
            boxWidth = 140;
        }
        x = scaledResolution.func_78326_a() / 2 - boxWidth / 2;
        this.field_146292_n.add(new ButtonSolid(x, y += boxHeight + 5, boxWidth, boxHeight, text, this.main, Feature.ENABLE_FEATURE_SNAPPING));
    }

    private void clearAllResizeButtons() {
        this.field_146292_n.removeIf(button -> button instanceof ButtonResize);
    }

    private void clearAllColorWheelButtons() {
        this.field_146292_n.removeIf(button -> button instanceof ButtonColorWheel);
    }

    private void addResizeButtonsToAllFeatures() {
        this.clearAllResizeButtons();
        for (Feature feature : Feature.getGuiFeatures()) {
            if (this.main.getConfigValues().isDisabled(feature)) continue;
            this.addResizeCorners(feature);
        }
    }

    private void addResizeButtonsToBars() {
        this.clearAllResizeButtons();
        for (Feature feature : Feature.getGuiFeatures()) {
            if (this.main.getConfigValues().isDisabled(feature) || feature.getGuiFeatureData() == null || feature.getGuiFeatureData().getDrawType() != EnumUtils.DrawType.BAR) continue;
            this.addResizeCorners(feature);
        }
    }

    private void addColorWheelsToAllFeatures() {
        for (ButtonLocation buttonLocation : this.buttonLocations.values()) {
            Feature feature = buttonLocation.getFeature();
            if (feature.getGuiFeatureData() == null || feature.getGuiFeatureData().getDefaultColor() == null) continue;
            EnumUtils.AnchorPoint anchorPoint = this.main.getConfigValues().getAnchorPoint(feature);
            float scaleX = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesX(feature) : 1.0f;
            float scaleY = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesY(feature) : 1.0f;
            float boxXOne = buttonLocation.getBoxXOne() * scaleX;
            float boxXTwo = buttonLocation.getBoxXTwo() * scaleX;
            float boxYOne = buttonLocation.getBoxYOne() * scaleY;
            float boxYTwo = buttonLocation.getBoxYTwo() * scaleY;
            float y = boxYOne + (boxYTwo - boxYOne) / 2.0f - (float)ButtonColorWheel.getSize() / 2.0f;
            float x = anchorPoint == EnumUtils.AnchorPoint.TOP_LEFT || anchorPoint == EnumUtils.AnchorPoint.BOTTOM_LEFT ? boxXTwo + 2.0f : boxXOne - (float)ButtonColorWheel.getSize() - 2.0f;
            this.field_146292_n.add(new ButtonColorWheel(Math.round(x), Math.round(y), feature));
        }
    }

    private void addResizeCorners(Feature feature) {
        this.field_146292_n.removeIf(button -> button instanceof ButtonResize && ((ButtonResize)((Object)button)).getFeature() == feature);
        ButtonLocation buttonLocation = this.buttonLocations.get((Object)feature);
        if (buttonLocation == null) {
            return;
        }
        float boxXOne = buttonLocation.getBoxXOne();
        float boxXTwo = buttonLocation.getBoxXTwo();
        float boxYOne = buttonLocation.getBoxYOne();
        float boxYTwo = buttonLocation.getBoxYTwo();
        float scaleX = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesX(feature) : 1.0f;
        float scaleY = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesY(feature) : 1.0f;
        this.field_146292_n.add(new ButtonResize(boxXOne * scaleX, boxYOne * scaleY, feature, ButtonResize.Corner.TOP_LEFT));
        this.field_146292_n.add(new ButtonResize(boxXTwo * scaleX, boxYOne * scaleY, feature, ButtonResize.Corner.TOP_RIGHT));
        this.field_146292_n.add(new ButtonResize(boxXOne * scaleX, boxYTwo * scaleY, feature, ButtonResize.Corner.BOTTOM_LEFT));
        this.field_146292_n.add(new ButtonResize(boxXTwo * scaleX, boxYTwo * scaleY, feature, ButtonResize.Corner.BOTTOM_RIGHT));
    }

    private void recalculateResizeButtons() {
        for (GuiButton button : this.field_146292_n) {
            if (!(button instanceof ButtonResize)) continue;
            ButtonResize buttonResize = (ButtonResize)button;
            ButtonResize.Corner corner = buttonResize.getCorner();
            Feature feature = buttonResize.getFeature();
            ButtonLocation buttonLocation = this.buttonLocations.get((Object)feature);
            if (buttonLocation == null) continue;
            float scaleX = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesX(feature) : 1.0f;
            float scaleY = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesY(feature) : 1.0f;
            float boxXOne = buttonLocation.getBoxXOne() * scaleX;
            float boxXTwo = buttonLocation.getBoxXTwo() * scaleX;
            float boxYOne = buttonLocation.getBoxYOne() * scaleY;
            float boxYTwo = buttonLocation.getBoxYTwo() * scaleY;
            if (corner == ButtonResize.Corner.TOP_LEFT) {
                buttonResize.x = boxXOne;
                buttonResize.y = boxYOne;
                continue;
            }
            if (corner == ButtonResize.Corner.TOP_RIGHT) {
                buttonResize.x = boxXTwo;
                buttonResize.y = boxYOne;
                continue;
            }
            if (corner == ButtonResize.Corner.BOTTOM_LEFT) {
                buttonResize.x = boxXOne;
                buttonResize.y = boxYTwo;
                continue;
            }
            if (corner != ButtonResize.Corner.BOTTOM_RIGHT) continue;
            buttonResize.x = boxXTwo;
            buttonResize.y = boxYTwo;
        }
    }

    private void recalculateColorWheels() {
        for (GuiButton button : this.field_146292_n) {
            ButtonColorWheel buttonColorWheel;
            Feature feature;
            ButtonLocation buttonLocation;
            if (!(button instanceof ButtonColorWheel) || (buttonLocation = this.buttonLocations.get((Object)(feature = (buttonColorWheel = (ButtonColorWheel)button).getFeature()))) == null) continue;
            EnumUtils.AnchorPoint anchorPoint = this.main.getConfigValues().getAnchorPoint(feature);
            float scaleX = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesX(feature) : 1.0f;
            float scaleY = feature.getGuiFeatureData().getDrawType() == EnumUtils.DrawType.BAR ? this.main.getConfigValues().getSizesY(feature) : 1.0f;
            float boxXOne = buttonLocation.getBoxXOne() * scaleX;
            float boxXTwo = buttonLocation.getBoxXTwo() * scaleX;
            float boxYOne = buttonLocation.getBoxYOne() * scaleY;
            float boxYTwo = buttonLocation.getBoxYTwo() * scaleY;
            float y = boxYOne + (boxYTwo - boxYOne) / 2.0f - (float)ButtonColorWheel.getSize() / 2.0f;
            float x = anchorPoint == EnumUtils.AnchorPoint.TOP_LEFT || anchorPoint == EnumUtils.AnchorPoint.BOTTOM_LEFT ? boxXTwo + 2.0f : boxXOne - (float)ButtonColorWheel.getSize() - 2.0f;
            buttonColorWheel.x = x;
            buttonColorWheel.y = y;
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        Snap[] snaps = this.checkSnapping();
        this.onMouseMove(mouseX, mouseY, snaps);
        if (this.editMode == EditMode.RESCALE) {
            this.recalculateResizeButtons();
        }
        this.recalculateColorWheels();
        int startColor = new Color(0, 0, 0, 64).getRGB();
        int endColor = new Color(0, 0, 0, 128).getRGB();
        this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, startColor, endColor);
        for (EnumUtils.AnchorPoint anchorPoint : EnumUtils.AnchorPoint.values()) {
            ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
            int x = anchorPoint.getX(sr.func_78326_a());
            int y = anchorPoint.getY(sr.func_78328_b());
            int color = ColorCode.RED.getColor(127);
            Feature lastHovered = ButtonLocation.getLastHoveredFeature();
            if (lastHovered != null && this.main.getConfigValues().getAnchorPoint(lastHovered) == anchorPoint) {
                color = ColorCode.YELLOW.getColor(127);
            }
            DrawUtils.drawRectAbsolute(x - 4, y - 4, x + 4, y + 4, color);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (snaps != null) {
            for (Snap snap : snaps) {
                if (snap == null) continue;
                float left = snap.getRectangle().get((Object)Edge.LEFT).floatValue();
                float top = snap.getRectangle().get((Object)Edge.TOP).floatValue();
                float right = snap.getRectangle().get((Object)Edge.RIGHT).floatValue();
                float bottom = snap.getRectangle().get((Object)Edge.BOTTOM).floatValue();
                if ((double)snap.getWidth() < 0.5) {
                    float averageX = (left + right) / 2.0f;
                    left = averageX - 0.25f;
                    right = averageX + 0.25f;
                }
                if ((double)snap.getHeight() < 0.5) {
                    float averageY = (top + bottom) / 2.0f;
                    top = averageY - 0.25f;
                    bottom = averageY + 0.25f;
                }
                if ((double)(right - left) == 0.5 || (double)(bottom - top) == 0.5) {
                    DrawUtils.drawRectAbsolute(left, top, right, bottom, -16711936);
                    continue;
                }
                DrawUtils.drawRectAbsolute(left, top, right, bottom, -65536);
            }
        }
    }

    public Snap[] checkSnapping() {
        if (!this.enableSnapping) {
            return null;
        }
        if (this.dragging != null) {
            ButtonLocation thisButton = this.buttonLocations.get((Object)this.dragging);
            if (thisButton == null) {
                return null;
            }
            Snap horizontalSnap = null;
            Snap verticalSnap = null;
            for (Map.Entry<Feature, ButtonLocation> buttonLocationEntry : this.buttonLocations.entrySet()) {
                Snap thisSnap;
                ButtonLocation otherButton = buttonLocationEntry.getValue();
                if (otherButton == thisButton) continue;
                for (Edge otherEdge : Edge.getHorizontalEdges()) {
                    for (Edge thisEdge : Edge.getHorizontalEdges()) {
                        float bottomY;
                        float topY;
                        float deltaX = otherEdge.getCoordinate(otherButton) - thisEdge.getCoordinate(thisButton);
                        if (!(Math.abs(deltaX) <= 1.0f)) continue;
                        float deltaY = Edge.TOP.getCoordinate(otherButton) - Edge.TOP.getCoordinate(thisButton);
                        if (deltaY > 0.0f) {
                            topY = Edge.BOTTOM.getCoordinate(thisButton);
                            bottomY = Edge.TOP.getCoordinate(otherButton);
                        } else {
                            topY = Edge.BOTTOM.getCoordinate(otherButton);
                            bottomY = Edge.TOP.getCoordinate(thisButton);
                        }
                        float snapX = otherEdge.getCoordinate(otherButton);
                        thisSnap = new Snap(otherEdge.getCoordinate(otherButton), topY, thisEdge.getCoordinate(thisButton), bottomY, thisEdge, otherEdge, snapX);
                        if (!(thisSnap.getHeight() < 120.0f) || horizontalSnap != null && !(thisSnap.getHeight() < horizontalSnap.getHeight())) continue;
                        if (this.main.isDevMode()) {
                            DrawUtils.drawRectAbsolute((double)snapX - 0.5, 0.0, (double)snapX + 0.5, this.field_146297_k.field_71440_d, -16776961);
                        }
                        horizontalSnap = thisSnap;
                    }
                }
                for (Edge otherEdge : Edge.getVerticalEdges()) {
                    for (Edge thisEdge : Edge.getVerticalEdges()) {
                        float rightX;
                        float leftX;
                        float deltaY = otherEdge.getCoordinate(otherButton) - thisEdge.getCoordinate(thisButton);
                        if (!(Math.abs(deltaY) <= 1.0f)) continue;
                        float deltaX = Edge.LEFT.getCoordinate(otherButton) - Edge.LEFT.getCoordinate(thisButton);
                        if (deltaX > 0.0f) {
                            leftX = Edge.RIGHT.getCoordinate(thisButton);
                            rightX = Edge.LEFT.getCoordinate(otherButton);
                        } else {
                            leftX = Edge.RIGHT.getCoordinate(otherButton);
                            rightX = Edge.LEFT.getCoordinate(thisButton);
                        }
                        float snapY = otherEdge.getCoordinate(otherButton);
                        thisSnap = new Snap(leftX, otherEdge.getCoordinate(otherButton), rightX, thisEdge.getCoordinate(thisButton), thisEdge, otherEdge, snapY);
                        if (!(thisSnap.getWidth() < 120.0f) || verticalSnap != null && !(thisSnap.getWidth() < verticalSnap.getWidth())) continue;
                        if (this.main.isDevMode()) {
                            DrawUtils.drawRectAbsolute(0.0, (double)snapY - 0.5, this.field_146297_k.field_71443_c, (double)snapY + 0.5, -16776961);
                        }
                        verticalSnap = thisSnap;
                    }
                }
            }
            return new Snap[]{horizontalSnap, verticalSnap};
        }
        return null;
    }

    protected void onMouseMove(int mouseX, int mouseY, Snap[] snaps) {
        ScaledResolution sr = new ScaledResolution(this.field_146297_k);
        float minecraftScale = sr.func_78325_e();
        float floatMouseX = (float)Mouse.getX() / minecraftScale;
        float floatMouseY = (float)(this.field_146297_k.field_71440_d - Mouse.getY()) / minecraftScale;
        if (this.resizing) {
            float x = (float)mouseX - this.xOffset;
            float y = (float)mouseY - this.yOffset;
            if (this.editMode == EditMode.RESIZE_BARS) {
                ButtonLocation buttonLocation = this.buttonLocations.get((Object)this.dragging);
                if (buttonLocation == null) {
                    return;
                }
                float middleX = (buttonLocation.getBoxXTwo() + buttonLocation.getBoxXOne()) / 2.0f;
                float middleY = (buttonLocation.getBoxYTwo() + buttonLocation.getBoxYOne()) / 2.0f;
                float scaleX = (floatMouseX - middleX) / (this.xOffset - middleX);
                float scaleY = (floatMouseY - middleY) / (this.yOffset - middleY);
                scaleX = (float)Math.max((double)Math.min(scaleX, 5.0f), 0.25);
                scaleY = (float)Math.max((double)Math.min(scaleY, 5.0f), 0.25);
                this.main.getConfigValues().setScaleX(this.dragging, scaleX);
                this.main.getConfigValues().setScaleY(this.dragging, scaleY);
                buttonLocation.func_146112_a(this.field_146297_k, mouseX, mouseY);
                this.recalculateResizeButtons();
            } else if (this.editMode == EditMode.RESCALE) {
                ButtonLocation buttonLocation = this.buttonLocations.get((Object)this.dragging);
                if (buttonLocation == null) {
                    return;
                }
                float scale = buttonLocation.getScale();
                float scaledX1 = buttonLocation.getBoxXOne() * buttonLocation.getScale();
                float scaledY1 = buttonLocation.getBoxYOne() * buttonLocation.getScale();
                float scaledX2 = buttonLocation.getBoxXTwo() * buttonLocation.getScale();
                float scaledY2 = buttonLocation.getBoxYTwo() * buttonLocation.getScale();
                float scaledWidth = scaledX2 - scaledX1;
                float scaledHeight = scaledY2 - scaledY1;
                float width = buttonLocation.getBoxXTwo() - buttonLocation.getBoxXOne();
                float height = buttonLocation.getBoxYTwo() - buttonLocation.getBoxYOne();
                float middleX = scaledX1 + scaledWidth / 2.0f;
                float middleY = scaledY1 + scaledHeight / 2.0f;
                float xOffset = floatMouseX - this.xOffset * scale - middleX;
                float yOffset = floatMouseY - this.yOffset * scale - middleY;
                if (this.resizingCorner == ButtonResize.Corner.TOP_LEFT) {
                    xOffset *= -1.0f;
                    yOffset *= -1.0f;
                } else if (this.resizingCorner == ButtonResize.Corner.TOP_RIGHT) {
                    yOffset *= -1.0f;
                } else if (this.resizingCorner == ButtonResize.Corner.BOTTOM_LEFT) {
                    xOffset *= -1.0f;
                }
                float newWidth = xOffset * 2.0f;
                float newHeight = yOffset * 2.0f;
                float scaleX = newWidth / width;
                float scaleY = newHeight / height;
                float newScale = Math.max(scaleX, scaleY);
                float normalizedScale = ConfigValues.normalizeValueNoStep(newScale);
                this.main.getConfigValues().setGuiScale(this.dragging, normalizedScale);
                buttonLocation.func_146112_a(this.field_146297_k, mouseX, mouseY);
                this.recalculateResizeButtons();
            }
        } else if (this.dragging != null) {
            float snapOffset;
            ButtonLocation buttonLocation = this.buttonLocations.get((Object)this.dragging);
            if (buttonLocation == null) {
                return;
            }
            Snap horizontalSnap = null;
            Snap verticalSnap = null;
            if (snaps != null) {
                horizontalSnap = snaps[0];
                verticalSnap = snaps[1];
            }
            float x = floatMouseX - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getX(sr.func_78326_a());
            float y = floatMouseY - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getY(sr.func_78328_b());
            float scaledX1 = buttonLocation.getBoxXOne() * buttonLocation.getScale();
            float scaledY1 = buttonLocation.getBoxYOne() * buttonLocation.getScale();
            float scaledX2 = buttonLocation.getBoxXTwo() * buttonLocation.getScale();
            float scaledY2 = buttonLocation.getBoxYTwo() * buttonLocation.getScale();
            float scaledWidth = scaledX2 - scaledX1;
            float scaledHeight = scaledY2 - scaledY1;
            boolean xSnapped = false;
            boolean ySnapped = false;
            if (horizontalSnap != null) {
                float snapX = horizontalSnap.getSnapValue();
                if (horizontalSnap.getThisSnapEdge() == Edge.LEFT) {
                    snapOffset = Math.abs(floatMouseX - this.xOffset - (snapX + scaledWidth / 2.0f));
                    if (snapOffset <= 1.0f * minecraftScale) {
                        xSnapped = true;
                        x = snapX - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getX(sr.func_78326_a()) + scaledWidth / 2.0f;
                    }
                } else if (horizontalSnap.getThisSnapEdge() == Edge.RIGHT) {
                    snapOffset = Math.abs(floatMouseX - this.xOffset - (snapX - scaledWidth / 2.0f));
                    if (snapOffset <= 1.0f * minecraftScale) {
                        xSnapped = true;
                        x = snapX - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getX(sr.func_78326_a()) - scaledWidth / 2.0f;
                    }
                } else if (horizontalSnap.getThisSnapEdge() == Edge.VERTICAL_MIDDLE && (snapOffset = Math.abs(floatMouseX - this.xOffset - snapX)) <= 1.0f * minecraftScale) {
                    xSnapped = true;
                    x = snapX - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getX(sr.func_78326_a());
                }
            }
            if (verticalSnap != null) {
                float snapY = verticalSnap.getSnapValue();
                if (verticalSnap.getThisSnapEdge() == Edge.TOP) {
                    snapOffset = Math.abs(floatMouseY - this.yOffset - (snapY + scaledHeight / 2.0f));
                    if (snapOffset <= 1.0f * minecraftScale) {
                        ySnapped = true;
                        y = snapY - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getY(sr.func_78328_b()) + scaledHeight / 2.0f;
                    }
                } else if (verticalSnap.getThisSnapEdge() == Edge.BOTTOM) {
                    snapOffset = Math.abs(floatMouseY - this.yOffset - (snapY - scaledHeight / 2.0f));
                    if (snapOffset <= 1.0f * minecraftScale) {
                        ySnapped = true;
                        y = snapY - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getY(sr.func_78328_b()) - scaledHeight / 2.0f;
                    }
                } else if (verticalSnap.getThisSnapEdge() == Edge.HORIZONTAL_MIDDLE && (snapOffset = Math.abs(floatMouseY - this.yOffset - snapY)) <= 1.0f * minecraftScale) {
                    ySnapped = true;
                    y = snapY - (float)this.main.getConfigValues().getAnchorPoint(this.dragging).getY(sr.func_78328_b());
                }
            }
            if (!xSnapped) {
                x -= this.xOffset;
            }
            if (!ySnapped) {
                y -= this.yOffset;
            }
            if (xSnapped || ySnapped) {
                float xChange = Math.abs(this.main.getConfigValues().getRelativeCoords(this.dragging).getX() - x);
                float yChange = Math.abs(this.main.getConfigValues().getRelativeCoords(this.dragging).getY() - y);
                if ((double)xChange < 0.001 && (double)yChange < 0.001) {
                    return;
                }
            }
            this.main.getConfigValues().setCoords(this.dragging, x, y);
            this.main.getConfigValues().setClosestAnchorPoint(this.dragging);
            if (this.dragging == Feature.HEALTH_BAR || this.dragging == Feature.MANA_BAR || this.dragging == Feature.DRILL_FUEL_BAR) {
                this.addResizeCorners(this.dragging);
            }
        }
    }

    protected void func_146284_a(GuiButton abstractButton) {
        if (abstractButton instanceof ButtonLocation) {
            ButtonLocation buttonLocation = (ButtonLocation)abstractButton;
            this.dragging = buttonLocation.getFeature();
            ScaledResolution sr = new ScaledResolution(this.field_146297_k);
            float minecraftScale = sr.func_78325_e();
            float floatMouseX = (float)Mouse.getX() / minecraftScale;
            float floatMouseY = (float)(this.field_146297_k.field_71440_d - Mouse.getY()) / minecraftScale;
            this.xOffset = floatMouseX - this.main.getConfigValues().getActualX(buttonLocation.getFeature());
            this.yOffset = floatMouseY - this.main.getConfigValues().getActualY(buttonLocation.getFeature());
        } else if (abstractButton instanceof ButtonSolid) {
            ButtonSolid buttonSolid = (ButtonSolid)abstractButton;
            Feature feature = buttonSolid.getFeature();
            if (feature == Feature.RESET_LOCATION) {
                this.main.getConfigValues().setAllCoordinatesToDefault();
                this.main.getConfigValues().putDefaultBarSizes();
                for (Feature guiFeature : Feature.getGuiFeatures()) {
                    if (this.main.getConfigValues().isDisabled(guiFeature) || guiFeature != Feature.HEALTH_BAR && guiFeature != Feature.MANA_BAR && guiFeature != Feature.DRILL_FUEL_BAR) continue;
                    this.addResizeCorners(guiFeature);
                }
            } else if (feature == Feature.RESIZE_BARS) {
                if (this.editMode != EditMode.RESIZE_BARS) {
                    this.editMode = EditMode.RESIZE_BARS;
                    this.addResizeButtonsToBars();
                } else {
                    this.editMode = null;
                    this.clearAllResizeButtons();
                }
            } else if (feature == Feature.RESCALE_FEATURES) {
                if (this.editMode != EditMode.RESCALE) {
                    this.editMode = EditMode.RESCALE;
                    this.addResizeButtonsToAllFeatures();
                } else {
                    this.editMode = null;
                    this.clearAllResizeButtons();
                }
            } else if (feature == Feature.SHOW_COLOR_ICONS) {
                if (this.showColorIcons) {
                    this.showColorIcons = false;
                    this.clearAllColorWheelButtons();
                } else {
                    this.showColorIcons = true;
                    this.addColorWheelsToAllFeatures();
                }
            } else if (feature == Feature.ENABLE_FEATURE_SNAPPING) {
                this.enableSnapping = !this.enableSnapping;
            }
        } else if (abstractButton instanceof ButtonResize) {
            ButtonResize buttonResize = (ButtonResize)abstractButton;
            this.dragging = buttonResize.getFeature();
            this.resizing = true;
            ScaledResolution sr = new ScaledResolution(this.field_146297_k);
            float minecraftScale = sr.func_78325_e();
            float floatMouseX = (float)Mouse.getX() / minecraftScale;
            float floatMouseY = (float)(this.field_146297_k.field_71440_d - Mouse.getY()) / minecraftScale;
            float scale = SkyblockAddons.getInstance().getConfigValues().getGuiScale(buttonResize.getFeature());
            if (this.editMode == EditMode.RESCALE) {
                this.xOffset = (floatMouseX - buttonResize.getX() * scale) / scale;
                this.yOffset = (floatMouseY - buttonResize.getY() * scale) / scale;
            } else {
                this.xOffset = floatMouseX;
                this.yOffset = floatMouseY;
            }
            this.resizingCorner = buttonResize.getCorner();
        } else if (abstractButton instanceof ButtonColorWheel) {
            ButtonColorWheel buttonColorWheel = (ButtonColorWheel)abstractButton;
            this.closing = true;
            this.field_146297_k.func_147108_a((GuiScreen)new ColorSelectionGui(buttonColorWheel.getFeature(), EnumUtils.GUIType.EDIT_LOCATIONS, this.lastTab, this.lastPage));
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        Feature hoveredFeature = ButtonLocation.getLastHoveredFeature();
        if (hoveredFeature != null) {
            int xOffset = 0;
            int yOffset = 0;
            if (keyCode == 203) {
                --xOffset;
            } else if (keyCode == 200) {
                --yOffset;
            } else if (keyCode == 205) {
                ++xOffset;
            } else if (keyCode == 208) {
                ++yOffset;
            }
            if (keyCode == 30) {
                xOffset -= 10;
            } else if (keyCode == 17) {
                yOffset -= 10;
            } else if (keyCode == 32) {
                xOffset += 10;
            } else if (keyCode == 31) {
                yOffset += 10;
            }
            this.main.getConfigValues().setCoords(hoveredFeature, this.main.getConfigValues().getRelativeCoords(hoveredFeature).getX() + (float)xOffset, this.main.getConfigValues().getRelativeCoords(hoveredFeature).getY() + (float)yOffset);
        }
    }

    protected void func_146286_b(int mouseX, int mouseY, int state) {
        super.func_146286_b(mouseX, mouseY, state);
        this.dragging = null;
        this.resizing = false;
    }

    public void func_146281_b() {
        this.main.getConfigValues().saveConfig();
        if (this.lastTab != null && !this.closing) {
            this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.MAIN, this.lastPage, this.lastTab);
        }
    }

    private static enum EditMode {
        RESCALE,
        RESIZE_BARS;

    }

    static class Snap {
        private final Edge thisSnapEdge;
        private final Edge otherSnapEdge;
        private final float snapValue;
        private final Map<Edge, Float> rectangle = new EnumMap<Edge, Float>(Edge.class);

        public Snap(float left, float top, float right, float bottom, Edge thisSnapEdge, Edge otherSnapEdge, float snapValue) {
            this.rectangle.put(Edge.LEFT, Float.valueOf(left));
            this.rectangle.put(Edge.TOP, Float.valueOf(top));
            this.rectangle.put(Edge.RIGHT, Float.valueOf(right));
            this.rectangle.put(Edge.BOTTOM, Float.valueOf(bottom));
            this.rectangle.put(Edge.HORIZONTAL_MIDDLE, Float.valueOf(top + this.getHeight() / 2.0f));
            this.rectangle.put(Edge.VERTICAL_MIDDLE, Float.valueOf(left + this.getWidth() / 2.0f));
            this.otherSnapEdge = otherSnapEdge;
            this.thisSnapEdge = thisSnapEdge;
            this.snapValue = snapValue;
        }

        public float getHeight() {
            return this.rectangle.get((Object)Edge.BOTTOM).floatValue() - this.rectangle.get((Object)Edge.TOP).floatValue();
        }

        public float getWidth() {
            return this.rectangle.get((Object)Edge.RIGHT).floatValue() - this.rectangle.get((Object)Edge.LEFT).floatValue();
        }

        public Edge getThisSnapEdge() {
            return this.thisSnapEdge;
        }

        public Edge getOtherSnapEdge() {
            return this.otherSnapEdge;
        }

        public float getSnapValue() {
            return this.snapValue;
        }

        public Map<Edge, Float> getRectangle() {
            return this.rectangle;
        }
    }

    static enum Edge {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        HORIZONTAL_MIDDLE,
        VERTICAL_MIDDLE;

        private static final Set<Edge> verticalEdges;
        private static final Set<Edge> horizontalEdges;

        public float getCoordinate(ButtonLocation button) {
            switch (this) {
                case LEFT: {
                    return button.getBoxXOne() * button.getScale();
                }
                case TOP: {
                    return button.getBoxYOne() * button.getScale();
                }
                case RIGHT: {
                    return button.getBoxXTwo() * button.getScale();
                }
                case BOTTOM: {
                    return button.getBoxYTwo() * button.getScale();
                }
                case HORIZONTAL_MIDDLE: {
                    return TOP.getCoordinate(button) + (BOTTOM.getCoordinate(button) - TOP.getCoordinate(button)) / 2.0f;
                }
                case VERTICAL_MIDDLE: {
                    return LEFT.getCoordinate(button) + (RIGHT.getCoordinate(button) - LEFT.getCoordinate(button)) / 2.0f;
                }
            }
            return 0.0f;
        }

        public static Set<Edge> getVerticalEdges() {
            return verticalEdges;
        }

        public static Set<Edge> getHorizontalEdges() {
            return horizontalEdges;
        }

        static {
            verticalEdges = Sets.newHashSet((Object[])new Edge[]{TOP, BOTTOM, HORIZONTAL_MIDDLE});
            horizontalEdges = Sets.newHashSet((Object[])new Edge[]{LEFT, RIGHT, VERTICAL_MIDDLE});
        }
    }
}

