/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.themes;

import codes.biscuit.skyblockaddons.newgui.themes.DarkTheme;
import codes.biscuit.skyblockaddons.newgui.themes.Theme;

public class ThemeManager {
    private static final ThemeManager INSTANCE = new ThemeManager();
    private Theme currentTheme = new DarkTheme();

    public static ThemeManager getInstance() {
        return INSTANCE;
    }

    public Theme getCurrentTheme() {
        return this.currentTheme;
    }

    public void setCurrentTheme(Theme currentTheme) {
        this.currentTheme = currentTheme;
    }
}

