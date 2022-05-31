/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package codes.biscuit.skyblockaddons.newgui;

import codes.biscuit.skyblockaddons.newgui.GuiBase;
import net.minecraft.client.gui.GuiScreen;

public class SkyblockAddonsMinecraftGui
extends GuiScreen {
    private GuiBase gui;

    public SkyblockAddonsMinecraftGui(GuiBase gui) {
        this.gui = gui;
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.gui.render();
    }

    public void func_146281_b() {
        this.gui.close();
    }
}

