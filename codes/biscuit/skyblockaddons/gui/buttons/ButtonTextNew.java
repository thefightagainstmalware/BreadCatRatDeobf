/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ButtonTextNew
extends GuiButton {
    private boolean centered;
    private int color;

    public ButtonTextNew(int x, int y, String text, boolean centered, int color) {
        super(0, x, y, text);
        this.centered = centered;
        this.color = color;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        int x = this.field_146128_h;
        int y = this.field_146129_i;
        if (this.centered) {
            x -= mc.field_71466_p.func_78256_a(this.field_146126_j) / 2;
        }
        mc.field_71466_p.func_78276_b(this.field_146126_j, x, y, this.color);
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        return false;
    }
}

