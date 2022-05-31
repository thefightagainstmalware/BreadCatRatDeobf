/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.MathHelper
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.objects.FloatPair;
import java.math.BigDecimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class ButtonGuiScale
extends ButtonFeature {
    private float sliderValue;
    private boolean dragging;
    private final Boolean isXScale;
    private final SkyblockAddons main;

    public ButtonGuiScale(double x, double y, int width, int height, SkyblockAddons main, Feature feature) {
        super(0, (int)x, (int)y, "", feature);
        this.sliderValue = main.getConfigValues().getGuiScale(feature, false);
        this.field_146126_j = Message.SETTING_GUI_SCALE.getMessage(String.valueOf(this.getRoundedValue(main.getConfigValues().getGuiScale(feature))));
        this.main = main;
        this.field_146120_f = width;
        this.field_146121_g = height;
        this.isXScale = null;
    }

    public ButtonGuiScale(double x, double y, int width, int height, SkyblockAddons main, Feature feature, boolean isXScale) {
        super(0, (int)x, (int)y, "", feature);
        FloatPair sizes = main.getConfigValues().getSizes(feature);
        if (isXScale) {
            this.sliderValue = sizes.getX();
            this.field_146126_j = EnumUtils.FeatureSetting.GUI_SCALE_X.getMessage(String.valueOf(this.getRoundedValue(main.getConfigValues().getSizesX(feature))));
        } else {
            this.sliderValue = sizes.getY();
            this.field_146126_j = EnumUtils.FeatureSetting.GUI_SCALE_Y.getMessage(String.valueOf(this.getRoundedValue(main.getConfigValues().getSizesX(feature))));
        }
        this.isXScale = isXScale;
        this.main = main;
        this.field_146120_f = width;
        this.field_146121_g = height;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        mc.func_110434_K().func_110577_a(field_146122_a);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179112_b((int)770, (int)771);
        int boxAlpha = 100;
        if (this.field_146123_n) {
            boxAlpha = 170;
        }
        ButtonGuiScale.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)this.main.getUtils().getDefaultColor(boxAlpha));
        this.func_146119_b(mc, mouseX, mouseY);
        int j = 0xE0E0E0;
        if (this.packedFGColour != 0) {
            j = this.packedFGColour;
        } else if (!this.field_146124_l) {
            j = 0xA0A0A0;
        } else if (this.field_146123_n) {
            j = 0xFFFFA0;
        }
        this.func_73732_a(mc.field_71466_p, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, j);
    }

    protected int func_146114_a(boolean mouseOver) {
        return 0;
    }

    protected void func_146119_b(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            if (this.dragging) {
                this.sliderValue = (float)(mouseX - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
                this.sliderValue = MathHelper.func_76131_a((float)this.sliderValue, (float)0.0f, (float)1.0f);
                this.setNewScale();
            }
            mc.func_110434_K().func_110577_a(field_146122_a);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ButtonGuiScale.func_73734_a((int)(this.field_146128_h + (int)(this.sliderValue * (float)(this.field_146120_f - 8)) + 1), (int)this.field_146129_i, (int)(this.field_146128_h + (int)(this.sliderValue * (float)(this.field_146120_f - 8)) + 7), (int)(this.field_146129_i + this.field_146121_g), (int)ColorCode.GRAY.getColor());
        }
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        if (super.func_146116_c(mc, mouseX, mouseY)) {
            this.sliderValue = (float)(mouseX - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            this.sliderValue = MathHelper.func_76131_a((float)this.sliderValue, (float)0.0f, (float)1.0f);
            this.setNewScale();
            this.dragging = true;
            return true;
        }
        return false;
    }

    public void func_146118_a(int mouseX, int mouseY) {
        this.dragging = false;
    }

    private float getRoundedValue(float value) {
        return new BigDecimal(String.valueOf(value)).setScale(2, 4).floatValue();
    }

    private void setNewScale() {
        if (this.isXScale == null) {
            this.main.getConfigValues().setGuiScale(this.feature, this.sliderValue);
            this.field_146126_j = Message.SETTING_GUI_SCALE.getMessage(String.valueOf(this.getRoundedValue(this.main.getConfigValues().getGuiScale(this.feature))));
        } else {
            FloatPair sizes = this.main.getConfigValues().getSizes(this.feature);
            if (this.isXScale.booleanValue()) {
                sizes.setX(this.sliderValue);
                this.field_146126_j = EnumUtils.FeatureSetting.GUI_SCALE_X.getMessage(String.valueOf(this.getRoundedValue(this.main.getConfigValues().getSizesX(this.feature))));
            } else {
                sizes.setY(this.sliderValue);
                this.field_146126_j = EnumUtils.FeatureSetting.GUI_SCALE_Y.getMessage(String.valueOf(this.getRoundedValue(this.main.getConfigValues().getSizesY(this.feature))));
            }
        }
    }
}

