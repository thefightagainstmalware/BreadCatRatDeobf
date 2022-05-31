/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.elements;

import codes.biscuit.skyblockaddons.newgui.GuiElement;
import codes.biscuit.skyblockaddons.newgui.themes.DarkTheme;
import codes.biscuit.skyblockaddons.newgui.themes.Theme;
import codes.biscuit.skyblockaddons.newgui.themes.ThemeManager;
import codes.biscuit.skyblockaddons.utils.DrawUtils;

public class ContainerElement
extends GuiElement<ContainerElement> {
    @Override
    public void render() {
        ThemeManager.getInstance().setCurrentTheme(new DarkTheme());
        Theme theme = ThemeManager.getInstance().getCurrentTheme();
        DrawUtils.drawRect((double)this.getX(), (double)this.getY(), (double)this.getW(), (double)this.getH(), theme.getContainerOne().getColor(), theme.getContainerOne().getRounding());
    }
}

