/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.gui.GuiButton
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.utils.ColorCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class ButtonNewTag
extends GuiButton {
    public ButtonNewTag(int x, int y) {
        super(0, x, y, "NEW");
        this.field_146120_f = 25;
        this.field_146121_g = 11;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        ButtonNewTag.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)ColorCode.RED.getColor());
        mc.field_71466_p.func_78276_b(this.field_146126_j, this.field_146128_h + 4, this.field_146129_i + 2, ColorCode.WHITE.getColor());
    }

    public void func_146113_a(SoundHandler soundHandlerIn) {
    }
}

