/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import net.minecraft.client.renderer.GlStateManager;

class ButtonText
extends ButtonFeature {
    ButtonText(int buttonId, int x, int y, String buttonText, Feature feature) {
        super(buttonId, x, y, buttonText, feature);
    }

    void drawButtonBoxAndText(int boxColor, float scale, int fontColor) {
        ButtonText.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)boxColor);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
        DrawUtils.drawCenteredText(this.field_146126_j, (float)(this.field_146128_h + this.field_146120_f / 2) / scale, ((float)this.field_146129_i + ((float)this.field_146121_g - 8.0f * scale) / 2.0f) / scale, fontColor);
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
}

