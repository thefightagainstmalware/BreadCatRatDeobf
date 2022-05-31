/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package codes.biscuit.skyblockaddons.newgui;

import codes.biscuit.skyblockaddons.newgui.GuiBase;
import codes.biscuit.skyblockaddons.newgui.MouseButton;
import codes.biscuit.skyblockaddons.newgui.SkyblockAddonsMinecraftGui;
import java.util.LinkedList;
import java.util.ListIterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiManager {
    private LinkedList<GuiBase> openGuis = new LinkedList();
    private GuiBase focused;

    public void render() {
    }

    public void onMouseClick(float x, float y, int keyCode) {
        MouseButton mouseButton = MouseButton.fromKeyCode(keyCode);
        ListIterator openGuisIterator = this.openGuis.listIterator();
        while (openGuisIterator.hasPrevious()) {
            GuiBase openGui = (GuiBase)openGuisIterator.previous();
            if (!openGui.isInside(x, y)) continue;
            if (!openGui.isFocused()) {
                this.setFocused(openGui, true);
                return;
            }
            if (!openGui.onMouseClick(x, y, mouseButton)) continue;
            return;
        }
    }

    public void onKeyPress(int keyCode, char key) {
        if (keyCode == 1) {
            this.openGuis.removeLast();
        }
    }

    public void openAsGUI(GuiBase gui) {
        Minecraft.func_71410_x().func_147108_a((GuiScreen)new SkyblockAddonsMinecraftGui(gui));
    }

    public void openAsOverlay(GuiBase gui) {
        this.openGuis.add(gui);
        gui.setFocused(true);
    }

    public void setFocused(GuiBase gui, boolean focused) {
        if (focused) {
            this.focused = gui;
            if (this.openGuis.get(this.openGuis.size() - 1) != gui) {
                this.openGuis.remove(gui);
                this.openGuis.add(gui);
            }
        } else if (this.focused == gui) {
            this.focused = null;
        }
    }

    public boolean isOpen(GuiBase gui) {
        return this.isOpen(gui.getClass());
    }

    public boolean isOpen(Class<? extends GuiBase> gui) {
        for (GuiBase openGui : this.openGuis) {
            if (!openGui.getClass().equals(gui)) continue;
            return true;
        }
        return false;
    }

    public boolean isFocused(GuiBase gui) {
        return gui == this.focused;
    }

    public void close(GuiBase gui) {
        if (gui.isFocused()) {
            this.focused = null;
        }
        this.openGuis.remove(gui);
    }
}

