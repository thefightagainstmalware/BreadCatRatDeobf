/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class ButtonSwitchTab
extends GuiButton {
    private SkyblockAddons main;
    private EnumUtils.GuiTab currentTab;
    private EnumUtils.GuiTab tab;
    private long timeOpened = System.currentTimeMillis();

    public ButtonSwitchTab(double x, double y, int width, int height, String buttonText, SkyblockAddons main, EnumUtils.GuiTab tab, EnumUtils.GuiTab currentTab) {
        super(0, (int)x, (int)y, width, height, buttonText);
        this.main = main;
        this.field_146120_f = width;
        this.field_146121_g = height;
        this.currentTab = currentTab;
        this.tab = tab;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            int fadeMilis;
            long timeSinceOpen;
            float alphaMultiplier = 1.0f;
            if (this.main.getUtils().isFadingIn() && (timeSinceOpen = System.currentTimeMillis() - this.timeOpened) <= (long)(fadeMilis = 500)) {
                alphaMultiplier = (float)timeSinceOpen / (float)fadeMilis;
            }
            boolean bl = this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            if (this.currentTab == this.tab) {
                this.field_146123_n = false;
            }
            if ((double)alphaMultiplier < 0.1) {
                alphaMultiplier = 0.1f;
            }
            int boxColor = this.main.getUtils().getDefaultBlue((int)(alphaMultiplier * 50.0f));
            int fontColor = this.currentTab != this.tab ? this.main.getUtils().getDefaultBlue((int)(alphaMultiplier * 255.0f)) : this.main.getUtils().getDefaultBlue((int)(alphaMultiplier * 127.0f));
            if (this.field_146123_n) {
                fontColor = new Color(255, 255, 160, (int)(alphaMultiplier * 255.0f)).getRGB();
            }
            ButtonSwitchTab.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)boxColor);
            float scale = 1.4f;
            float scaleMultiplier = 1.0f / scale;
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
            GlStateManager.func_179147_l();
            this.func_73732_a(mc.field_71466_p, this.field_146126_j, (int)((float)(this.field_146128_h + this.field_146120_f / 2) * scaleMultiplier), (int)(((float)this.field_146129_i + ((float)this.field_146121_g - 8.0f / scaleMultiplier) / 2.0f) * scaleMultiplier), fontColor);
            GlStateManager.func_179121_F();
        }
    }

    public void func_146113_a(SoundHandler soundHandlerIn) {
        if (this.currentTab != this.tab) {
            super.func_146113_a(soundHandlerIn);
        }
    }

    public EnumUtils.GuiTab getTab() {
        return this.tab;
    }
}

