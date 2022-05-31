/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui;

import codes.biscuit.skyblockaddons.newgui.MouseButton;
import codes.biscuit.skyblockaddons.newgui.sizes.SizeBase;
import codes.biscuit.skyblockaddons.newgui.sizes.parent.ParentOffsetSize;
import codes.biscuit.skyblockaddons.newgui.sizes.screen.ScreenPercentageSize;
import codes.biscuit.skyblockaddons.newgui.sizes.staticc.StaticSize;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import java.util.LinkedList;
import java.util.List;

public abstract class GuiElement<T extends GuiElement> {
    private GuiElement<?> parent;
    private List<GuiElement<?>> elements = new LinkedList();
    private SizeBase position;
    private SizeBase size;
    private Runnable onClickCallback;

    public void init() {
    }

    public void render() {
    }

    public boolean onMouseClick(float x, float y, MouseButton mouseButton) {
        if (this.onClickCallback != null) {
            this.onClickCallback.run();
        }
        return true;
    }

    public T xy(float x, float y) {
        this.position = new StaticSize().xy(x, y);
        return (T)this;
    }

    public T wh(float w, float h) {
        this.size = new StaticSize().wh(w, h);
        return (T)this;
    }

    public T screenPercentPosition(float percent) {
        this.position = new ScreenPercentageSize(percent);
        return (T)this;
    }

    public T screenPercentSize(float percent) {
        this.size = new ScreenPercentageSize(percent);
        return (T)this;
    }

    public T relativeXY(float xOffset, float yOffset) {
        this.position = new ParentOffsetSize(this, xOffset, yOffset);
        return (T)this;
    }

    public T relativeWH(float wOffset, float hOffset) {
        this.size = new ParentOffsetSize(this, wOffset, hOffset);
        return (T)this;
    }

    public T relativeXYPercent(float xPercent, float yPercent) {
        this.position = new ParentOffsetSize(this, xPercent, yPercent);
        return (T)this;
    }

    public T relativeWHPercent(float wPercent, float hPercent) {
        this.size = new ParentOffsetSize(this, wPercent, hPercent);
        return (T)this;
    }

    public T fillToScreen() {
        return this.fillToScreenWithMargin(0.0f);
    }

    public T fillToScreenWithMargin(float marginPercentage) {
        this.screenPercentPosition(marginPercentage);
        this.screenPercentSize(1.0f - marginPercentage * 2.0f);
        return (T)this;
    }

    public float getX() {
        return this.position.getX();
    }

    public float getY() {
        return this.position.getY();
    }

    public float getX2() {
        return this.getX() + this.getW();
    }

    public float getY2() {
        return this.getY() + this.getH();
    }

    public float getW() {
        return this.size.getW();
    }

    public float getH() {
        return this.size.getH();
    }

    public boolean isInside(float x, float y) {
        return MathUtils.isInside(x, y, this.getX(), this.getY(), this.getX2(), this.getY2());
    }

    public T add(GuiElement<?> guiElement) {
        this.elements.add(guiElement);
        guiElement.parent = this;
        return (T)this;
    }

    public T add(GuiElement<?> ... guiElements) {
        for (GuiElement<?> guiElement : guiElements) {
            this.elements.add(guiElement);
            guiElement.parent = this;
        }
        return (T)this;
    }

    public GuiElement<?> getParent() {
        return this.parent;
    }

    public void setOnClickCallback(Runnable onClickCallback) {
        this.onClickCallback = onClickCallback;
    }
}

