/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.themes;

import codes.biscuit.skyblockaddons.newgui.themes.elements.ContainerTheme;

public abstract class Theme {
    protected ContainerTheme containerOne;

    public abstract String getName();

    public ContainerTheme getContainerOne() {
        return this.containerOne;
    }
}

