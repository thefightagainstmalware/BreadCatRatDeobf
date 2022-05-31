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
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.objects.IntPair;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonNormal
extends ButtonFeature {
    private SkyblockAddons main;
    private long timeOpened = System.currentTimeMillis();

    public ButtonNormal(double x, double y, String buttonText, SkyblockAddons main, Feature feature) {
        this((int)x, (int)y, 140, 50, buttonText, main, feature);
    }

    public ButtonNormal(double x, double y, int width, int height, String buttonText, SkyblockAddons main, Feature feature) {
        super(0, (int)x, (int)y, buttonText, feature);
        this.main = main;
        this.feature = feature;
        this.field_146120_f = width;
        this.field_146121_g = height;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_146125_m) {
            int alpha;
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
            boolean bl = this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            if (alpha < 4) {
                alpha = 4;
            }
            int fontColor = this.main.getUtils().getDefaultBlue(alpha);
            if (this.main.getConfigValues().isRemoteDisabled(this.feature)) {
                fontColor = new Color(60, 60, 60).getRGB();
            }
            GlStateManager.func_179147_l();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)0.7f);
            if (this.main.getConfigValues().isRemoteDisabled(this.feature)) {
                GlStateManager.func_179131_c((float)0.3f, (float)0.3f, (float)0.3f, (float)0.7f);
            }
            DrawUtils.drawRect((double)this.field_146128_h, (double)this.field_146129_i, (double)this.field_146120_f, (double)this.field_146121_g, ColorUtils.getDummySkyblockColor(27, 29, 41, 230), 4);
            EnumUtils.FeatureCredit creditFeature = EnumUtils.FeatureCredit.fromFeature(this.feature);
            String[] wrappedString = this.main.getUtils().wrapSplitText(this.field_146126_j, 28);
            if (wrappedString.length > 2) {
                StringBuilder lastLineString = new StringBuilder();
                for (int i = 1; i < wrappedString.length; ++i) {
                    lastLineString.append(wrappedString[i]);
                    if (i == wrappedString.length - 1) continue;
                    lastLineString.append(" ");
                }
                wrappedString = new String[]{wrappedString[0], lastLineString.toString()};
            }
            int textX = this.field_146128_h + this.field_146120_f / 2;
            int textY = this.field_146129_i;
            boolean multiline = wrappedString.length > 1;
            for (int i = 0; i < wrappedString.length; ++i) {
                String line = wrappedString[i];
                float scale = 1.0f;
                int stringWidth = mc.field_71466_p.func_78256_a(line);
                float widthLimit = 130.0f;
                if (this.feature == Feature.WARNING_TIME) {
                    widthLimit = 90.0f;
                }
                if ((float)stringWidth > widthLimit) {
                    scale = 1.0f / ((float)stringWidth / widthLimit);
                }
                if (this.feature == Feature.GENERAL_SETTINGS) {
                    textY -= 5;
                }
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
                int offset = 9;
                if (creditFeature != null) {
                    offset -= 4;
                }
                offset = (int)((float)offset + (10.0f - 10.0f * scale));
                DrawUtils.drawCenteredText(line, (float)textX / scale, (float)textY / scale + (float)offset, fontColor);
                GlStateManager.func_179121_F();
                if (!multiline || i != 0) continue;
                textY += 10;
            }
            if (creditFeature != null) {
                float scale = 0.8f;
                if (multiline) {
                    scale = 0.6f;
                }
                float creditsY = (float)textY / scale + 23.0f;
                if (multiline) {
                    creditsY += 3.0f;
                }
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
                DrawUtils.drawCenteredText(creditFeature.getAuthor(), (float)textX / scale, creditsY, fontColor);
                GlStateManager.func_179084_k();
                GlStateManager.func_179121_F();
            }
            if (this.feature == Feature.LANGUAGE) {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                try {
                    mc.func_110434_K().func_110577_a(this.main.getConfigValues().getLanguage().getResourceLocation());
                    if (this.main.getUtils().isHalloween()) {
                        mc.func_110434_K().func_110577_a(new ResourceLocation("skyblockaddons", "flags/halloween.png"));
                    }
                    DrawUtils.drawModalRectWithCustomSizedTexture((float)this.field_146128_h + (float)this.field_146120_f / 2.0f - 20.0f, this.field_146129_i + 20, 0.0f, 0.0f, 38.0f, 30.0f, 38.0f, 30.0f, true);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (this.feature == Feature.EDIT_LOCATIONS) {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                try {
                    mc.func_110434_K().func_110577_a(new ResourceLocation("skyblockaddons", "gui/move.png"));
                    DrawUtils.drawModalRectWithCustomSizedTexture((float)this.field_146128_h + (float)this.field_146120_f / 2.0f - 12.0f, this.field_146129_i + 22, 0.0f, 0.0f, 25.0f, 25.0f, 25.0f, 25.0f, true);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (this.main.getConfigValues().isRemoteDisabled(this.feature)) {
                this.func_73732_a(mc.field_71466_p, Message.MESSAGE_FEATURE_DISABLED.getMessage(new String[0]), textX, textY + 6, this.main.getUtils().getDefaultBlue(alpha));
            }
        }
    }

    public IntPair getCreditsCoords(EnumUtils.FeatureCredit credit) {
        String[] wrappedString = this.main.getUtils().wrapSplitText(this.field_146126_j, 28);
        boolean multiLine = wrappedString.length > 1;
        float scale = 0.8f;
        if (multiLine) {
            scale = 0.6f;
        }
        int y = (int)((float)this.field_146129_i / scale + (float)(multiLine ? 30 : 21));
        if (multiLine) {
            y += 10;
        }
        int x = (int)((float)(this.field_146128_h + this.field_146120_f / 2) / scale) - Minecraft.func_71410_x().field_71466_p.func_78256_a(credit.getAuthor()) / 2 - 17;
        return new IntPair(x, y);
    }

    public boolean isMultilineButton() {
        String[] wrappedString = this.main.getUtils().wrapSplitText(this.field_146126_j, 28);
        return wrappedString.length > 1;
    }

    public void func_146113_a(SoundHandler soundHandlerIn) {
        if (this.feature == Feature.LANGUAGE || this.feature == Feature.EDIT_LOCATIONS || this.feature == Feature.GENERAL_SETTINGS) {
            super.func_146113_a(soundHandlerIn);
        }
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        if (this.feature == Feature.LANGUAGE || this.feature == Feature.EDIT_LOCATIONS || this.feature == Feature.GENERAL_SETTINGS) {
            return super.func_146116_c(mc, mouseX, mouseY);
        }
        return false;
    }
}

