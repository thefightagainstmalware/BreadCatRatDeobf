/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.newgui.GuiElement;
import codes.biscuit.skyblockaddons.newgui.MouseButton;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class GuiBase {
    private Map<Integer, List<GuiElement<?>>> elements = new TreeMap();
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    private void initInternal() {
        this.init();
        this.calculateBounds();
    }

    protected void init() {
    }

    protected void render() {
        this.renderElements();
    }

    protected void renderElements() {
        for (List<GuiElement<?>> layerElements : this.elements.values()) {
            for (GuiElement<?> element : layerElements) {
                element.render();
            }
        }
    }

    protected boolean onMouseClick(float x, float y, MouseButton mouseButton) {
        for (List<GuiElement<?>> layerElements : this.elements.values()) {
            for (GuiElement<?> element : layerElements) {
                if (!element.isInside(x, y) || !element.onMouseClick(x, y, mouseButton)) continue;
                return true;
            }
        }
        return false;
    }

    public GuiBase openAsGUI() {
        this.initInternal();
        SkyblockAddons.getInstance().getGuiManager().openAsGUI(this);
        return this;
    }

    public GuiBase openAsOverlay() {
        this.initInternal();
        SkyblockAddons.getInstance().getGuiManager().openAsOverlay(this);
        return this;
    }

    public boolean isInside(int x, int y) {
        return this.isInside((float)x, (float)y);
    }

    public boolean isInside(float x, float y) {
        return MathUtils.isInside(x, y, this.minX, this.minY, this.maxX, this.maxY);
    }

    private void calculateBounds() {
        for (List<GuiElement<?>> elements : this.elements.values()) {
            for (GuiElement<?> element : elements) {
                this.minX = Math.min(this.minX, element.getX());
                this.maxX = Math.max(this.maxX, element.getX2());
                this.minY = Math.min(this.minY, element.getY());
                this.maxY = Math.max(this.maxY, element.getY2());
            }
        }
    }

    public GuiBase add(GuiElement<?> guiElement) {
        return this.add(0, guiElement);
    }

    public GuiBase add(GuiElement<?> ... guiElements) {
        return this.add(0, guiElements);
    }

    public GuiBase add(int layer, GuiElement<?> guiElement) {
        if (!this.elements.containsKey(layer)) {
            this.elements.put(layer, new LinkedList());
        }
        this.elements.get(layer).add(guiElement);
        return this;
    }

    public GuiBase add(int layer, GuiElement<?> ... guiElements) {
        if (!this.elements.containsKey(layer)) {
            this.elements.put(layer, new LinkedList());
        }
        Collections.addAll((Collection)this.elements.get(layer), guiElements);
        return this;
    }

    public void close() {
        SkyblockAddons.getInstance().getGuiManager().close(this);
    }

    public void setFocused(boolean focused) {
        SkyblockAddons.getInstance().getGuiManager().setFocused(this, focused);
    }

    public boolean isFocused() {
        return SkyblockAddons.getInstance().getGuiManager().isFocused(this);
    }
}

