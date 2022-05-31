/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonSettings
extends ButtonFeature {
    private static ResourceLocation GEAR = new ResourceLocation("skyblockaddons", "gui/gear.png");
    private SkyblockAddons main;
    private long timeOpened = System.currentTimeMillis();

    public ButtonSettings(double x, double y, String buttonText, SkyblockAddons main, Feature feature) {
        super(0, (int)x, (int)y, buttonText, feature);
        this.main = main;
        this.feature = feature;
        this.field_146120_f = 15;
        this.field_146121_g = 15;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            int fadeMilis;
            long timeSinceOpen;
            float alphaMultiplier = 1.0f;
            if (this.main.getUtils().isFadingIn() && (timeSinceOpen = System.currentTimeMillis() - this.timeOpened) <= (long)(fadeMilis = 500)) {
                alphaMultiplier = (float)timeSinceOpen / (float)fadeMilis;
            }
            this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            GlStateManager.func_179147_l();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 0.7f));
            if (this.field_146123_n) {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
            mc.func_110434_K().func_110577_a(GEAR);
            DrawUtils.drawModalRectWithCustomSizedTexture(this.field_146128_h, this.field_146129_i, 0.0f, 0.0f, this.field_146120_f, this.field_146121_g, this.field_146120_f, this.field_146121_g, true);
        }
    }
}

