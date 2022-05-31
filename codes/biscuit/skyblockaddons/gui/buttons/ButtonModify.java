/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonText;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;

public class ButtonModify
extends ButtonText {
    private SkyblockAddons main;
    private Feature feature;

    public ButtonModify(double x, double y, int width, int height, String buttonText, SkyblockAddons main, Feature feature) {
        super(0, (int)x, (int)y, buttonText, feature);
        this.main = main;
        this.feature = feature;
        this.field_146120_f = width;
        this.field_146121_g = height;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
        int boxAlpha = 100;
        if (this.field_146123_n && !this.hitMaximum()) {
            boxAlpha = 170;
        }
        int boxColor = this.hitMaximum() ? ColorCode.GRAY.getColor(boxAlpha) : (this.feature == Feature.ADD ? ColorCode.GREEN.getColor(boxAlpha) : ColorCode.RED.getColor(boxAlpha));
        GlStateManager.func_179147_l();
        int fontColor = ColorCode.WHITE.getColor();
        if (this.field_146123_n && !this.hitMaximum()) {
            fontColor = new Color(255, 255, 160, 255).getRGB();
        }
        this.drawButtonBoxAndText(boxColor, 1.0f, fontColor);
    }

    public void func_146113_a(SoundHandler soundHandlerIn) {
        if (!this.hitMaximum()) {
            super.func_146113_a(soundHandlerIn);
        }
    }

    private boolean hitMaximum() {
        return this.feature == Feature.SUBTRACT && this.main.getConfigValues().getWarningSeconds() == 1 || this.feature == Feature.ADD && this.main.getConfigValues().getWarningSeconds() == 99;
    }
}

