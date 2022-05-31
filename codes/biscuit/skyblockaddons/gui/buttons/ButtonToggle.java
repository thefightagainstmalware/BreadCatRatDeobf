/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonToggle
extends ButtonFeature {
    private static ResourceLocation TOGGLE_INSIDE_CIRCLE = new ResourceLocation("skyblockaddons", "gui/toggleinsidecircle.png");
    private static ResourceLocation TOGGLE_BORDER = new ResourceLocation("skyblockaddons", "gui/toggleborder.png");
    private static ResourceLocation TOGGLE_INSIDE_BACKGROUND = new ResourceLocation("skyblockaddons", "gui/toggleinsidebackground.png");
    private static final int CIRCLE_PADDING_LEFT = 5;
    private static final int ANIMATION_SLIDE_DISTANCE = 12;
    private static final int ANIMATION_SLIDE_TIME = 150;
    private SkyblockAddons main;
    private long timeOpened = System.currentTimeMillis();
    private long animationButtonClicked = -1L;

    public ButtonToggle(double x, double y, SkyblockAddons main, Feature feature) {
        super(0, (int)x, (int)y, "", feature);
        this.main = main;
        this.feature = feature;
        this.field_146120_f = 31;
        this.field_146121_g = 15;
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
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 0.7f));
        if (this.field_146123_n) {
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        ColorUtils.bindColor(-14801618);
        mc.func_110434_K().func_110577_a(TOGGLE_BORDER);
        DrawUtils.drawModalRectWithCustomSizedTexture(this.field_146128_h, this.field_146129_i, 0.0f, 0.0f, this.field_146120_f, this.field_146121_g, this.field_146120_f, this.field_146121_g, true);
        boolean enabled = this.main.getConfigValues().isEnabled(this.feature);
        boolean remoteDisabled = this.main.getConfigValues().isRemoteDisabled(this.feature);
        if (enabled) {
            ColorUtils.bindColor(36, 255, 98, remoteDisabled ? 25 : 255);
        } else {
            ColorUtils.bindColor(222, 68, 76, remoteDisabled ? 25 : 255);
        }
        mc.func_110434_K().func_110577_a(TOGGLE_INSIDE_BACKGROUND);
        DrawUtils.drawModalRectWithCustomSizedTexture(this.field_146128_h, this.field_146129_i, 0.0f, 0.0f, this.field_146120_f, this.field_146121_g, this.field_146120_f, this.field_146121_g, true);
        int startingX = this.getStartingPosition(enabled);
        int slideAnimationOffset = 0;
        if (this.animationButtonClicked != -1L) {
            int animationTime;
            startingX = this.getStartingPosition(!enabled);
            int timeSinceOpen2 = (int)(System.currentTimeMillis() - this.animationButtonClicked);
            if (timeSinceOpen2 > (animationTime = 150)) {
                timeSinceOpen2 = animationTime;
            }
            slideAnimationOffset = 12 * timeSinceOpen2 / animationTime;
        }
        int n = enabled ? slideAnimationOffset : -slideAnimationOffset;
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        mc.func_110434_K().func_110577_a(TOGGLE_INSIDE_CIRCLE);
        DrawUtils.drawModalRectWithCustomSizedTexture(startingX += n, this.field_146129_i + 3, 0.0f, 0.0f, 9.0f, 9.0f, 9.0f, 9.0f, true);
    }

    private int getStartingPosition(boolean enabled) {
        if (!enabled) {
            return this.field_146128_h + 5;
        }
        return this.getStartingPosition(false) + 12;
    }

    public void onClick() {
        this.animationButtonClicked = System.currentTimeMillis();
    }

    public void func_146113_a(SoundHandler soundHandler) {
        if (!this.main.getConfigValues().isRemoteDisabled(this.feature)) {
            super.func_146113_a(soundHandler);
        }
    }
}

