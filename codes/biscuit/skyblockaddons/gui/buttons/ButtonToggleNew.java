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
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonToggleNew
extends GuiButton {
    private static ResourceLocation TOGGLE_INSIDE_CIRCLE = new ResourceLocation("skyblockaddons", "gui/toggleinsidecircle.png");
    private static ResourceLocation TOGGLE_BORDER = new ResourceLocation("skyblockaddons", "gui/toggleborder.png");
    private static ResourceLocation TOGGLE_INSIDE_BACKGROUND = new ResourceLocation("skyblockaddons", "gui/toggleinsidebackground.png");
    private int circlePaddingLeft;
    private int animationSlideDistance;
    private int animationSlideTime = 150;
    private long timeOpened = System.currentTimeMillis();
    private long animationButtonClicked = -1L;
    private Supplier<Boolean> enabledSupplier;
    private Runnable onClickRunnable;

    public ButtonToggleNew(double x, double y, int height, Supplier<Boolean> enabledSupplier, Runnable onClickRunnable) {
        super(0, (int)x, (int)y, "");
        this.field_146120_f = (int)Math.round((double)height * 2.07);
        this.field_146121_g = height;
        this.enabledSupplier = enabledSupplier;
        this.onClickRunnable = onClickRunnable;
        this.circlePaddingLeft = height / 3;
        this.animationSlideDistance = Math.round((float)height * 0.8f);
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        int fadeMilis;
        long timeSinceOpen;
        SkyblockAddons main = SkyblockAddons.getInstance();
        float alphaMultiplier = 1.0f;
        if (main.getUtils().isFadingIn() && (timeSinceOpen = System.currentTimeMillis() - this.timeOpened) <= (long)(fadeMilis = 500)) {
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
        boolean enabled = this.enabledSupplier.get();
        if (enabled) {
            ColorUtils.bindColor(36, 255, 98, 255);
        } else {
            ColorUtils.bindColor(222, 68, 76, 255);
        }
        mc.func_110434_K().func_110577_a(TOGGLE_INSIDE_BACKGROUND);
        DrawUtils.drawModalRectWithCustomSizedTexture(this.field_146128_h, this.field_146129_i, 0.0f, 0.0f, this.field_146120_f, this.field_146121_g, this.field_146120_f, this.field_146121_g, true);
        int startingX = this.getButtonStartingX(enabled);
        int slideAnimationOffset = 0;
        if (this.animationButtonClicked != -1L) {
            int animationTime;
            startingX = this.getButtonStartingX(!enabled);
            int timeSinceOpen2 = (int)(System.currentTimeMillis() - this.animationButtonClicked);
            if (timeSinceOpen2 > (animationTime = this.animationSlideTime)) {
                timeSinceOpen2 = animationTime;
            }
            slideAnimationOffset = this.animationSlideDistance * timeSinceOpen2 / animationTime;
        }
        int n = enabled ? slideAnimationOffset : -slideAnimationOffset;
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        mc.func_110434_K().func_110577_a(TOGGLE_INSIDE_CIRCLE);
        int circleSize = Math.round((float)this.field_146121_g * 0.6f);
        int y = Math.round((float)this.field_146129_i + (float)this.field_146121_g * 0.2f);
        DrawUtils.drawModalRectWithCustomSizedTexture(startingX += n, y, 0.0f, 0.0f, circleSize, circleSize, circleSize, circleSize, true);
    }

    private int getButtonStartingX(boolean enabled) {
        if (!enabled) {
            return this.field_146128_h + this.circlePaddingLeft;
        }
        return this.getButtonStartingX(false) + this.animationSlideDistance;
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        boolean pressed;
        boolean bl = pressed = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
        if (pressed) {
            this.animationButtonClicked = System.currentTimeMillis();
            this.onClickRunnable.run();
        }
        return pressed;
    }
}

