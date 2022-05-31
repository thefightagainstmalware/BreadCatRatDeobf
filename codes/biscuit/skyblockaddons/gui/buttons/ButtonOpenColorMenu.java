/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonText;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class ButtonOpenColorMenu
extends ButtonText {
    private SkyblockAddons main;

    public ButtonOpenColorMenu(double x, double y, int width, int height, String buttonText, SkyblockAddons main, Feature feature) {
        super(0, (int)x, (int)y, buttonText, feature);
        this.main = main;
        this.field_146120_f = width;
        this.field_146121_g = height;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
        int fontColor = new Color(224, 224, 224, 255).getRGB();
        int boxAlpha = 100;
        if (this.field_146123_n) {
            boxAlpha = 170;
            fontColor = new Color(255, 255, 160, 255).getRGB();
        }
        int boxColor = this.main.getConfigValues().getColor(this.feature, boxAlpha);
        GlStateManager.func_179147_l();
        float scale = 1.0f;
        int stringWidth = mc.field_71466_p.func_78256_a(this.field_146126_j);
        float widthLimit = 130.0f;
        if ((float)stringWidth > widthLimit) {
            scale = 1.0f / ((float)stringWidth / widthLimit);
        }
        this.drawButtonBoxAndText(boxColor, scale, fontColor);
    }
}

