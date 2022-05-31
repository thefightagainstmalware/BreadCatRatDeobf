/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class ButtonSocial
extends GuiButton {
    private SkyblockAddons main;
    private EnumUtils.Social social;
    private long timeOpened = System.currentTimeMillis();

    public ButtonSocial(double x, double y, SkyblockAddons main, EnumUtils.Social social) {
        super(0, (int)x, (int)y, "");
        this.main = main;
        this.field_146120_f = 20;
        this.field_146121_g = 20;
        this.social = social;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        int fadeMilis;
        long timeSinceOpen;
        float alphaMultiplier = 1.0f;
        if (this.main.getUtils().isFadingIn() && (timeSinceOpen = System.currentTimeMillis() - this.timeOpened) <= (long)(fadeMilis = 500)) {
            alphaMultiplier = (float)timeSinceOpen / (float)fadeMilis;
        }
        this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
        GlStateManager.func_179147_l();
        if (this.field_146123_n) {
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 1.0f));
        } else {
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 0.7f));
        }
        mc.func_110434_K().func_110577_a(this.social.getResourceLocation());
        DrawUtils.drawModalRectWithCustomSizedTexture(this.field_146128_h, this.field_146129_i, 0.0f, 0.0f, this.field_146120_f, this.field_146121_g, this.field_146120_f, this.field_146121_g, true);
    }

    public EnumUtils.Social getSocial() {
        return this.social;
    }
}

