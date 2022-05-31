/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.MathHelper
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import java.math.BigDecimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class ButtonSlider
extends GuiButton {
    private float min;
    private float max;
    private float step;
    private float valuePercentage = 0.0f;
    private boolean dragging;
    private SkyblockAddons main = SkyblockAddons.getInstance();
    private OnSliderChangeCallback sliderCallback;
    private String prefix = null;

    public ButtonSlider(double x, double y, int width, int height, float initialValue, float min, float max, float step, OnSliderChangeCallback sliderCallback) {
        super(0, (int)x, (int)y, "");
        this.field_146126_j = "";
        this.valuePercentage = initialValue;
        this.field_146120_f = width;
        this.field_146121_g = height;
        this.sliderCallback = sliderCallback;
        this.min = min;
        this.max = max;
        this.step = step;
        this.field_146126_j = String.valueOf(this.getRoundedValue(this.denormalizeScale(this.valuePercentage)));
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
        ButtonSlider.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)this.main.getUtils().getDefaultColor(boxAlpha));
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
                this.valuePercentage = (float)(mouseX - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
                this.valuePercentage = MathHelper.func_76131_a((float)this.valuePercentage, (float)0.0f, (float)1.0f);
                this.valueUpdated();
            }
            mc.func_110434_K().func_110577_a(field_146122_a);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ButtonSlider.func_73734_a((int)(this.field_146128_h + (int)(this.valuePercentage * (float)(this.field_146120_f - 8)) + 1), (int)this.field_146129_i, (int)(this.field_146128_h + (int)(this.valuePercentage * (float)(this.field_146120_f - 8)) + 7), (int)(this.field_146129_i + this.field_146121_g), (int)ColorCode.GRAY.getColor());
        }
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        if (super.func_146116_c(mc, mouseX, mouseY)) {
            this.valuePercentage = (float)(mouseX - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            this.valuePercentage = MathHelper.func_76131_a((float)this.valuePercentage, (float)0.0f, (float)1.0f);
            this.valueUpdated();
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

    public float denormalizeScale(float value) {
        return MathUtils.denormalizeSliderValue(value, this.min, this.max, this.step);
    }

    public void valueUpdated() {
        this.sliderCallback.sliderUpdated(this.valuePercentage);
        this.field_146126_j = (this.prefix != null ? this.prefix : "") + this.getRoundedValue(this.denormalizeScale(this.valuePercentage));
    }

    public ButtonSlider setPrefix(String text) {
        this.prefix = text;
        this.field_146126_j = this.prefix + this.getRoundedValue(this.denormalizeScale(this.valuePercentage));
        return this;
    }

    public static abstract class OnSliderChangeCallback {
        public abstract void sliderUpdated(float var1);
    }
}

