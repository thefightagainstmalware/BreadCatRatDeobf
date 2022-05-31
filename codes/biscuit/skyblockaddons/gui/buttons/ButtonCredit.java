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
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonCredit
extends ButtonFeature {
    private static final ResourceLocation WEB = new ResourceLocation("skyblockaddons", "gui/web.png");
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private EnumUtils.FeatureCredit credit;
    private boolean smaller;
    private long timeOpened = System.currentTimeMillis();

    public ButtonCredit(double x, double y, String buttonText, EnumUtils.FeatureCredit credit, Feature feature, boolean smaller) {
        super(0, (int)x, (int)y, buttonText, feature);
        this.feature = feature;
        this.field_146120_f = 12;
        this.field_146121_g = 12;
        this.credit = credit;
        this.smaller = smaller;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            int fadeMilis;
            long timeSinceOpen;
            float alphaMultiplier = 1.0f;
            if (this.main.getUtils().isFadingIn() && (timeSinceOpen = System.currentTimeMillis() - this.timeOpened) <= (long)(fadeMilis = 500)) {
                alphaMultiplier = (float)timeSinceOpen / (float)fadeMilis;
            }
            float scale = 0.8f;
            if (this.smaller) {
                scale = 0.6f;
            }
            this.field_146123_n = (float)mouseX >= (float)this.field_146128_h * scale && (float)mouseY >= (float)this.field_146129_i * scale && (float)mouseX < (float)this.field_146128_h * scale + (float)this.field_146120_f * scale && (float)mouseY < (float)this.field_146129_i * scale + (float)this.field_146121_g * scale;
            GlStateManager.func_179147_l();
            if (this.field_146123_n) {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 1.0f));
            } else {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 0.7f));
            }
            if (this.main.getConfigValues().isRemoteDisabled(this.feature)) {
                GlStateManager.func_179131_c((float)0.3f, (float)0.3f, (float)0.3f, (float)0.7f);
            }
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
            mc.func_110434_K().func_110577_a(WEB);
            ButtonCredit.func_146110_a((int)this.field_146128_h, (int)this.field_146129_i, (float)0.0f, (float)0.0f, (int)12, (int)12, (float)12.0f, (float)12.0f);
            GlStateManager.func_179121_F();
        }
    }

    public EnumUtils.FeatureCredit getCredit() {
        return this.credit;
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        float scale = 0.8f;
        return (float)mouseX >= (float)this.field_146128_h * scale && (float)mouseY >= (float)this.field_146129_i * scale && (float)mouseX < (float)this.field_146128_h * scale + (float)this.field_146120_f * scale && (float)mouseY < (float)this.field_146129_i * scale + (float)this.field_146121_g * scale;
    }
}

