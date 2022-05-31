/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonWarning
extends GuiButton {
    private static final ResourceLocation WEB = new ResourceLocation("skyblockaddons", "gui/web.png");
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private long timeOpened = System.currentTimeMillis();

    public ButtonWarning(double x, double y, String warningText) {
        super(0, (int)x, (int)y, warningText);
        this.field_146120_f = 12;
        this.field_146121_g = 12;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            float scale;
            int fadeMilis;
            long timeSinceOpen;
            float alphaMultiplier = 1.0f;
            if (this.main.getUtils().isFadingIn() && (timeSinceOpen = System.currentTimeMillis() - this.timeOpened) <= (long)(fadeMilis = 500)) {
                alphaMultiplier = (float)timeSinceOpen / (float)fadeMilis;
            }
            this.field_146123_n = (float)mouseX >= (float)this.field_146128_h * (scale = 0.8f) && (float)mouseY >= (float)this.field_146129_i * scale && (float)mouseX < (float)this.field_146128_h * scale + (float)this.field_146120_f * scale && (float)mouseY < (float)this.field_146129_i * scale + (float)this.field_146121_g * scale;
            GlStateManager.func_179147_l();
            if (this.field_146123_n) {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 1.0f));
            } else {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 0.7f));
            }
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
            mc.func_110434_K().func_110577_a(WEB);
            ButtonWarning.func_146110_a((int)this.field_146128_h, (int)this.field_146129_i, (float)0.0f, (float)0.0f, (int)12, (int)12, (float)12.0f, (float)12.0f);
            GlStateManager.func_179121_F();
        }
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        float scale = 0.8f;
        return (float)mouseX >= (float)this.field_146128_h * scale && (float)mouseY >= (float)this.field_146129_i * scale && (float)mouseX < (float)this.field_146128_h * scale + (float)this.field_146120_f * scale && (float)mouseY < (float)this.field_146129_i * scale + (float)this.field_146121_g * scale;
    }
}

