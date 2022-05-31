/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonArrow
extends GuiButton {
    private static ResourceLocation ARROW_RIGHT = new ResourceLocation("skyblockaddons", "gui/arrowright.png");
    private static ResourceLocation ARROW_LEFT = new ResourceLocation("skyblockaddons", "gui/arrowleft.png");
    private SkyblockAddons main;
    private long timeOpened = System.currentTimeMillis();
    private ArrowType arrowType;
    private boolean max;

    public ButtonArrow(double x, double y, SkyblockAddons main, ArrowType arrowType, boolean max) {
        super(0, (int)x, (int)y, null);
        this.main = main;
        this.field_146120_f = 30;
        this.field_146121_g = 30;
        this.arrowType = arrowType;
        this.max = max;
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
            if (this.arrowType == ArrowType.RIGHT) {
                mc.func_110434_K().func_110577_a(ARROW_RIGHT);
            } else {
                mc.func_110434_K().func_110577_a(ARROW_LEFT);
            }
            if (this.max) {
                GlStateManager.func_179131_c((float)0.5f, (float)0.5f, (float)0.5f, (float)(alphaMultiplier * 0.5f));
            } else {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 0.7f));
                if (this.field_146123_n) {
                    GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                }
            }
            ButtonArrow.func_146110_a((int)this.field_146128_h, (int)this.field_146129_i, (float)0.0f, (float)0.0f, (int)this.field_146120_f, (int)this.field_146121_g, (float)this.field_146120_f, (float)this.field_146121_g);
        }
    }

    public void func_146113_a(SoundHandler soundHandlerIn) {
        if (!this.max) {
            super.func_146113_a(soundHandlerIn);
        }
    }

    public boolean isNotMax() {
        return !this.max;
    }

    public ArrowType getArrowType() {
        return this.arrowType;
    }

    public static enum ArrowType {
        LEFT,
        RIGHT;

    }
}

