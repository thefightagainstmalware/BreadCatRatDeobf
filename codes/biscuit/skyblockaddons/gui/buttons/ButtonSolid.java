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
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonText;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;

public class ButtonSolid
extends ButtonText {
    private SkyblockAddons main;
    private Feature feature;
    private long timeOpened = System.currentTimeMillis();

    public ButtonSolid(double x, double y, int width, int height, String buttonText, SkyblockAddons main, Feature feature) {
        super(0, (int)x, (int)y, buttonText, feature);
        this.main = main;
        this.feature = feature;
        this.field_146120_f = width;
        this.field_146121_g = height;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        int alpha;
        if (this.feature == Feature.TEXT_STYLE) {
            this.field_146126_j = this.main.getConfigValues().getTextStyle().getMessage();
        } else if (this.feature == Feature.CHROMA_MODE) {
            this.field_146126_j = this.main.getConfigValues().getChromaMode().getMessage();
        } else if (this.feature == Feature.WARNING_TIME) {
            this.field_146126_j = this.main.getConfigValues().getWarningSeconds() + "s";
        } else if (this.feature == Feature.TURN_ALL_FEATURES_CHROMA) {
            boolean enable = false;
            for (Feature loopFeature : Feature.values()) {
                if (loopFeature.getGuiFeatureData() == null || loopFeature.getGuiFeatureData().getDefaultColor() == null || this.main.getConfigValues().getChromaFeatures().contains((Object)loopFeature)) continue;
                enable = true;
                break;
            }
            this.field_146126_j = (enable ? Message.MESSAGE_ENABLE_ALL : Message.MESSAGE_DISABLE_ALL).getMessage(new String[0]);
        }
        float alphaMultiplier = 1.0f;
        if (this.main.getUtils().isFadingIn()) {
            int fadeMilis;
            long timeSinceOpen = System.currentTimeMillis() - this.timeOpened;
            if (timeSinceOpen <= (long)(fadeMilis = 500)) {
                alphaMultiplier = (float)timeSinceOpen / (float)fadeMilis;
            }
            alpha = (int)(255.0f * alphaMultiplier);
        } else {
            alpha = 255;
        }
        this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
        int boxAlpha = 100;
        if (this.field_146123_n && this.feature != Feature.WARNING_TIME) {
            boxAlpha = 170;
        }
        boxAlpha = (int)((float)boxAlpha * alphaMultiplier);
        int boxColor = this.main.getUtils().getDefaultColor(boxAlpha);
        if (this.feature == Feature.RESET_LOCATION) {
            boxColor = ColorUtils.setColorAlpha(0xFF7878, boxAlpha);
        }
        GlStateManager.func_179147_l();
        if (alpha < 4) {
            alpha = 4;
        }
        int fontColor = new Color(224, 224, 224, alpha).getRGB();
        if (this.field_146123_n && this.feature != Feature.WARNING_TIME) {
            fontColor = new Color(255, 255, 160, alpha).getRGB();
        }
        float scale = 1.0f;
        int stringWidth = mc.field_71466_p.func_78256_a(this.field_146126_j);
        float widthLimit = 130.0f;
        if (this.feature == Feature.WARNING_TIME) {
            widthLimit = 90.0f;
        }
        if ((float)stringWidth > widthLimit) {
            scale = 1.0f / ((float)stringWidth / widthLimit);
        }
        this.drawButtonBoxAndText(boxColor, scale, fontColor);
    }

    public void func_146113_a(SoundHandler soundHandlerIn) {
        if (this.feature != Feature.WARNING_TIME) {
            super.func_146113_a(soundHandlerIn);
        }
    }
}

