/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.core.chroma.ManualChromaManager;
import codes.biscuit.skyblockaddons.core.chroma.MulticolorShaderManager;
import codes.biscuit.skyblockaddons.shader.ShaderManager;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaScreenShader;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ButtonColorBox
extends GuiButton {
    public static final int WIDTH = 40;
    public static final int HEIGHT = 20;
    private final ColorCode color;

    public ButtonColorBox(int x, int y, ColorCode color) {
        super(0, x, y, null);
        this.field_146120_f = 40;
        this.field_146121_g = 20;
        this.color = color;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        boolean bl = this.field_146123_n = mouseX > this.field_146128_h && mouseX < this.field_146128_h + this.field_146120_f && mouseY > this.field_146129_i && mouseY < this.field_146129_i + this.field_146121_g;
        if (this.color == ColorCode.CHROMA && !MulticolorShaderManager.shouldUseChromaShaders()) {
            if (this.field_146123_n) {
                ButtonColorBox.drawChromaRect(this.field_146128_h, this.field_146129_i, this.field_146128_h + this.field_146120_f, this.field_146129_i + this.field_146121_g, 255);
            } else {
                ButtonColorBox.drawChromaRect(this.field_146128_h, this.field_146129_i, this.field_146128_h + this.field_146120_f, this.field_146129_i + this.field_146121_g, 127);
            }
        } else {
            if (this.color == ColorCode.CHROMA && MulticolorShaderManager.shouldUseChromaShaders()) {
                ShaderManager.getInstance().enableShader(ChromaScreenShader.class);
            }
            if (this.field_146123_n) {
                ButtonColorBox.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)this.color.getColor());
            } else {
                ButtonColorBox.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146120_f), (int)(this.field_146129_i + this.field_146121_g), (int)this.color.getColor(127));
            }
            if (this.color == ColorCode.CHROMA && MulticolorShaderManager.shouldUseChromaShaders()) {
                ShaderManager.getInstance().disableShader();
            }
        }
    }

    public ColorCode getColor() {
        return this.color;
    }

    public static void drawChromaRect(int left, int top, int right, int bottom, int alpha) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        int colorLB = ManualChromaManager.getChromaColor(left, bottom, 1);
        int colorRB = ManualChromaManager.getChromaColor(right, bottom, 1);
        int colorLT = ManualChromaManager.getChromaColor(left, top, 1);
        int colorRT = ManualChromaManager.getChromaColor(right, top, 1);
        int colorMM = ManualChromaManager.getChromaColor((right + left) / 2, (top + bottom) / 2, 1);
        worldrenderer.func_181662_b((double)right, (double)bottom, 0.0).func_181669_b(ColorUtils.getRed(colorRB), ColorUtils.getGreen(colorRB), ColorUtils.getBlue(colorRB), alpha).func_181675_d();
        worldrenderer.func_181662_b((double)((right + left) / 2), (double)((top + bottom) / 2), 0.0).func_181669_b(ColorUtils.getRed(colorMM), ColorUtils.getGreen(colorMM), ColorUtils.getBlue(colorMM), alpha).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, 0.0).func_181669_b(ColorUtils.getRed(colorLT), ColorUtils.getGreen(colorLT), ColorUtils.getBlue(colorLT), alpha).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)bottom, 0.0).func_181669_b(ColorUtils.getRed(colorLB), ColorUtils.getGreen(colorLB), ColorUtils.getBlue(colorLB), alpha).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)bottom, 0.0).func_181669_b(ColorUtils.getRed(colorRB), ColorUtils.getGreen(colorRB), ColorUtils.getBlue(colorRB), alpha).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)top, 0.0).func_181669_b(ColorUtils.getRed(colorRT), ColorUtils.getGreen(colorRT), ColorUtils.getBlue(colorRT), alpha).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, 0.0).func_181669_b(ColorUtils.getRed(colorLT), ColorUtils.getGreen(colorLT), ColorUtils.getBlue(colorLT), alpha).func_181675_d();
        worldrenderer.func_181662_b((double)((right + left) / 2), (double)((top + bottom) / 2), 0.0).func_181669_b(ColorUtils.getRed(colorMM), ColorUtils.getGreen(colorMM), ColorUtils.getBlue(colorMM), alpha).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
}

