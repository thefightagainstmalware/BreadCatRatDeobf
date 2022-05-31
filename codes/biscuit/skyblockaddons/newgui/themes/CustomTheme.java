/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.themes;

import codes.biscuit.skyblockaddons.newgui.themes.DefaultTheme;
import codes.biscuit.skyblockaddons.newgui.themes.Theme;
import codes.biscuit.skyblockaddons.newgui.themes.elements.ContainerTheme;
import java.io.File;

public class CustomTheme
extends Theme {
    private DefaultTheme baseTheme;
    private File file;

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public ContainerTheme getContainerOne() {
        return this.containerOne == null ? this.baseTheme.containerOne : this.containerOne;
    }

    public CustomTheme(DefaultTheme baseTheme, File file) {
        this.baseTheme = baseTheme;
        this.file = file;
    }
}

