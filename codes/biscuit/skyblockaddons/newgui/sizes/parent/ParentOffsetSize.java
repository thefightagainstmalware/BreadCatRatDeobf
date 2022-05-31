/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.sizes.parent;

import codes.biscuit.skyblockaddons.newgui.GuiElement;
import codes.biscuit.skyblockaddons.newgui.sizes.SizeBase;

public class ParentOffsetSize
extends SizeBase {
    private GuiElement<?> guiElement;
    private float x;
    private float y;

    public ParentOffsetSize(GuiElement<?> guiElement, float x, float y) {
        this.guiElement = guiElement;
        this.x = x;
        this.y = y;
    }

    public ParentOffsetSize(GuiElement<?> guiElement, float offset) {
        this(guiElement, offset, offset);
    }

    @Override
    public void updateSizes() {
        this.h = this.guiElement.getParent().getH() + this.x;
        this.w = this.guiElement.getParent().getW() + this.y;
    }

    @Override
    public void updatePositions() {
        this.y = this.guiElement.getParent().getY() + this.x;
        this.x = this.guiElement.getParent().getX() + this.y;
    }
}

