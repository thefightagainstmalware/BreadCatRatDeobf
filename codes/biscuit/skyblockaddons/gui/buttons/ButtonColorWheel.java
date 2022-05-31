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

public class ButtonColorWheel
extends ButtonFeature {
    private static final ResourceLocation COLOR_WHEEL = new ResourceLocation("skyblockaddons", "gui/colorwheel.png");
    private static final int SIZE = 10;
    public float x;
    public float y;

    public ButtonColorWheel(float x, float y, Feature feature) {
        super(0, 0, 0, "", feature);
        this.field_146120_f = 10;
        this.field_146121_g = 10;
        this.x = x;
        this.y = y;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        float scale = SkyblockAddons.getInstance().getConfigValues().getGuiScale(this.feature);
        this.field_146123_n = (float)mouseX >= this.x * scale && (float)mouseY >= this.y * scale && (float)mouseX < this.x * scale + (float)this.field_146120_f * scale && (float)mouseY < this.y * scale + (float)this.field_146121_g * scale;
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(this.field_146123_n ? 1.0f : 0.5f));
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
        GlStateManager.func_179147_l();
        mc.func_110434_K().func_110577_a(COLOR_WHEEL);
        DrawUtils.drawModalRectWithCustomSizedTexture(this.x, this.y, 0.0f, 0.0f, 10.0f, 10.0f, 10.0f, 10.0f, true);
        GlStateManager.func_179121_F();
    }

    public static int getSize() {
        return 10;
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        float scale = SkyblockAddons.getInstance().getConfigValues().getGuiScale(this.feature);
        return (float)mouseX >= this.x * scale && (float)mouseY >= this.y * scale && (float)mouseX < this.x * scale + (float)this.field_146120_f * scale && (float)mouseY < this.y * scale + (float)this.field_146121_g * scale;
    }
}

