/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import org.lwjgl.input.Mouse;

public class MouseHelperHook {
    public static void ungrabMouseCursor(int new_x, int new_y) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isDisabled(Feature.DONT_RESET_CURSOR_INVENTORY) || main.getPlayerListener().shouldResetMouse()) {
            Mouse.setCursorPosition((int)new_x, (int)new_y);
            Mouse.setGrabbed((boolean)false);
        }
    }
}

