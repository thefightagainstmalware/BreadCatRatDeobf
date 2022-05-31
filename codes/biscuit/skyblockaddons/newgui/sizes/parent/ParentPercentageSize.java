/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.sizes.parent;

import codes.biscuit.skyblockaddons.newgui.GuiElement;
import codes.biscuit.skyblockaddons.newgui.sizes.SizeBase;

public class ParentPercentageSize
extends SizeBase {
    private GuiElement<?> guiElement;
    private float xPercentage;
    private float yPercentage;

    public ParentPercentageSize(GuiElement<?> guiElement, float xPercentage, float yPercentage) {
        this.guiElement = guiElement;
        this.xPercentage = xPercentage;
        this.yPercentage = yPercentage;
    }

    public ParentPercentageSize(GuiElement<?> guiElement, float percentage) {
        this(guiElement, percentage, percentage);
    }

    @Override
    public void updateSizes() {
        this.h = this.guiElement.getParent().getH() * this.xPercentage;
        this.w = this.guiElement.getParent().getW() * this.yPercentage;
    }

    @Override
    public void updatePositions() {
        this.y = this.guiElement.getParent().getY() * this.xPercentage;
        this.x = this.guiElement.getParent().getX() * this.yPercentage;
    }
}

