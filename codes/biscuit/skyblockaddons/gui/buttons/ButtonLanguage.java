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
import codes.biscuit.skyblockaddons.core.Language;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DataUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonLanguage
extends GuiButton {
    private static ResourceLocation FEATURE_BACKGROUND = new ResourceLocation("skyblockaddons", "gui/featurebackground.png");
    private Language language;
    private SkyblockAddons main;

    public ButtonLanguage(double x, double y, String buttonText, SkyblockAddons main, Language language) {
        super(0, (int)x, (int)y, buttonText);
        this.language = language;
        this.main = main;
        this.field_146120_f = 140;
        this.field_146121_g = 25;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            DrawUtils.drawRect((double)this.field_146128_h, (double)this.field_146129_i, (double)this.field_146120_f, (double)this.field_146121_g, ColorUtils.getDummySkyblockColor(28, 29, 41, 230), 4);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            try {
                mc.func_110434_K().func_110577_a(this.language.getResourceLocation());
                DrawUtils.drawModalRectWithCustomSizedTexture(this.field_146128_h + this.field_146120_f - 32, this.field_146129_i, 0.0f, 0.0f, 30.0f, 26.0f, 30.0f, 26.0f, true);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            int fontColor = this.main.getUtils().getDefaultBlue(255);
            if (this.field_146123_n) {
                fontColor = new Color(255, 255, 160, 255).getRGB();
            }
            DataUtils.loadLocalizedStrings(this.language, false);
            this.func_73732_a(mc.field_71466_p, Message.LANGUAGE.getMessage(new String[0]), this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + 10, fontColor);
        }
    }

    public Language getLanguage() {
        return this.language;
    }
}

