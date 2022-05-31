/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.chronomatron;

import codes.biscuit.skyblockaddons.newgui.GuiBase;
import codes.biscuit.skyblockaddons.newgui.GuiElement;
import codes.biscuit.skyblockaddons.newgui.elements.ContainerElement;

public class ChronomatronGui
extends GuiBase {
    @Override
    protected void init() {
        this.add((GuiElement<?>)new ContainerElement().fillToScreenWithMargin(0.1f));
    }

    @Override
    protected void render() {
        super.render();
    }
}

