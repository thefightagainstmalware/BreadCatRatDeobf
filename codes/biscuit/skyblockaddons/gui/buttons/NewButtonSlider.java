/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.MathHelper
 *  org.lwjgl.input.Mouse
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.gui.buttons.UpdateCallback;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class NewButtonSlider
extends GuiButton {
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private final float min;
    private final float max;
    private final float step;
    private final UpdateCallback<Float> sliderCallback;
    private String prefix = "";
    private boolean dragging;
    private float normalizedValue;

    public NewButtonSlider(double x, double y, int width, int height, float value, float min, float max, float step, UpdateCallback<Float> sliderCallback) {
        super(0, (int)x, (int)y, "");
        this.field_146120_f = width;
        this.field_146121_g = height;
        this.sliderCallback = sliderCallback;
        this.min = min;
        this.max = max;
        this.step = step;
        this.normalizedValue = MathUtils.normalizeSliderValue(value, min, max, step);
        this.field_146126_j = Utils.roundForString(value, 2);
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
        NewButtonSlider.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)this.main.getUtils().getDefaultColor(boxAlpha));
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
            ScaledResolution sr = new ScaledResolution(mc);
            float minecraftScale = sr.func_78325_e();
            float floatMouseX = (float)Mouse.getX() / minecraftScale;
            if (this.dragging) {
                this.normalizedValue = (floatMouseX - (float)(this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
                this.normalizedValue = MathHelper.func_76131_a((float)this.normalizedValue, (float)0.0f, (float)1.0f);
                this.onUpdate();
            }
            mc.func_110434_K().func_110577_a(field_146122_a);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            NewButtonSlider.func_73734_a((int)(this.field_146128_h + (int)(this.normalizedValue * (float)(this.field_146120_f - 8)) + 1), (int)this.field_146129_i, (int)(this.field_146128_h + (int)(this.normalizedValue * (float)(this.field_146120_f - 8)) + 7), (int)(this.field_146129_i + this.field_146121_g), (int)ColorCode.GRAY.getColor());
        }
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        if (super.func_146116_c(mc, mouseX, mouseY)) {
            this.normalizedValue = (float)(mouseX - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            this.normalizedValue = MathHelper.func_76131_a((float)this.normalizedValue, (float)0.0f, (float)1.0f);
            this.onUpdate();
            this.dragging = true;
            return true;
        }
        return false;
    }

    public void func_146118_a(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public NewButtonSlider setPrefix(String text) {
        this.prefix = text;
        this.updateDisplayString();
        return this;
    }

    private void onUpdate() {
        this.sliderCallback.onUpdate(Float.valueOf(this.denormalize()));
        this.updateDisplayString();
    }

    private void updateDisplayString() {
        this.field_146126_j = this.prefix + Utils.roundForString(this.denormalize(), 2);
    }

    public float denormalize() {
        return MathUtils.denormalizeSliderValue(this.normalizedValue, this.min, this.max, this.step);
    }
}

