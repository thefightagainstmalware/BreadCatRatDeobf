/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonToggle;
import net.minecraft.client.Minecraft;

public class ButtonToggleTitle
extends ButtonToggle {
    private SkyblockAddons main;

    public ButtonToggleTitle(double x, double y, String buttonText, SkyblockAddons main, Feature feature) {
        super(x, y, main, feature);
        this.field_146126_j = buttonText;
        this.main = main;
    }

    @Override
    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        super.func_146112_a(mc, mouseX, mouseY);
        int fontColor = this.main.getUtils().getDefaultBlue(255);
        this.func_73732_a(mc.field_71466_p, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i - 10, fontColor);
    }
}

