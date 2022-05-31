/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.VertexFormat
 */
package codes.biscuit.skyblockaddons.utils.draw;

import codes.biscuit.skyblockaddons.core.chroma.MulticolorShaderManager;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;

public abstract class DrawState {
    private static final Tessellator tessellator = Tessellator.func_178181_a();
    private static final WorldRenderer worldRenderer = tessellator.func_178180_c();
    protected boolean canAddVertices;
    protected int drawType;
    protected VertexFormat format;
    protected boolean textured;
    protected boolean ignoreTexture;
    protected SkyblockColor color;

    public DrawState(SkyblockColor theColor, int theDrawType, VertexFormat theFormat, boolean isTextured, boolean shouldIgnoreTexture) {
        this.color = theColor;
        this.drawType = theDrawType;
        this.format = theFormat;
        this.textured = isTextured;
        this.ignoreTexture = shouldIgnoreTexture;
        this.canAddVertices = true;
    }

    public DrawState(SkyblockColor theColor, boolean isTextured, boolean shouldIgnoreTexture) {
        this.color = theColor;
        this.ignoreTexture = shouldIgnoreTexture;
        this.textured = isTextured;
        this.canAddVertices = false;
    }

    public void beginWorld() {
        if (this.canAddVertices) {
            worldRenderer.func_181668_a(this.drawType, this.format);
        }
    }

    public void draw() {
        if (this.canAddVertices) {
            tessellator.func_78381_a();
        }
    }

    protected void newColor(boolean is3D) {
        if (this.color.drawMulticolorUsingShader()) {
            MulticolorShaderManager.begin(this.textured, this.ignoreTexture, is3D);
            GlStateManager.func_179103_j((int)7425);
        }
        if (this.textured && this.ignoreTexture) {
            DrawUtils.enableOutlineMode();
            if (this.color.drawMulticolorUsingShader()) {
                DrawUtils.outlineColor(-1);
            } else {
                DrawUtils.outlineColor(this.color.getColor());
            }
        }
    }

    protected void bindColor(int colorInt) {
        if (this.textured && this.ignoreTexture) {
            if (this.color.isPositionalMulticolor() && this.color.drawMulticolorManually()) {
                DrawUtils.outlineColor(colorInt);
            }
        } else {
            GlStateManager.func_179131_c((float)((float)ColorUtils.getRed(colorInt) / 255.0f), (float)((float)ColorUtils.getGreen(colorInt) / 255.0f), (float)((float)ColorUtils.getBlue(colorInt) / 255.0f), (float)((float)ColorUtils.getAlpha(colorInt) / 255.0f));
        }
    }

    protected void endColor() {
        if (this.color.drawMulticolorUsingShader()) {
            MulticolorShaderManager.end();
            GlStateManager.func_179103_j((int)7424);
        }
        if (this.textured && this.ignoreTexture) {
            DrawUtils.disableOutlineMode();
        }
    }

    public void reColor(SkyblockColor newColor) {
        this.color = newColor;
    }
}

