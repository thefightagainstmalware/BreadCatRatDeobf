/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.VertexFormat
 */
package codes.biscuit.skyblockaddons.utils.draw;

import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import codes.biscuit.skyblockaddons.utils.draw.DrawState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class DrawState2D
extends DrawState {
    private static final WorldRenderer worldRenderer = Tessellator.func_178181_a().func_178180_c();

    public DrawState2D(SkyblockColor theColor, int theDrawType, VertexFormat theFormat, boolean isTextured, boolean shouldIgnoreTexture) {
        super(theColor, theDrawType, theFormat, isTextured, shouldIgnoreTexture);
    }

    public DrawState2D(SkyblockColor theColor, boolean isTextured, boolean shouldIgnoreTexture) {
        super(theColor, isTextured, shouldIgnoreTexture);
    }

    public DrawState2D newColorEnv() {
        super.newColor(false);
        return this;
    }

    public DrawState2D endColorEnv() {
        super.endColor();
        return this;
    }

    public DrawState2D setColor(SkyblockColor color) {
        super.reColor(color);
        return this;
    }

    public DrawState2D bindActualColor() {
        super.bindColor(this.color.getColor());
        return this;
    }

    public DrawState2D bindAnimatedColor(float x, float y) {
        super.bindColor(this.color.getColorAtPosition(x, y));
        return this;
    }

    public DrawState2D addColoredVertex(float x, float y) {
        if (this.canAddVertices) {
            if (this.color.drawMulticolorManually()) {
                int colorInt = this.color.getColorAtPosition(x, y);
                worldRenderer.func_181662_b((double)x, (double)y, 0.0).func_181669_b(ColorUtils.getRed(colorInt), ColorUtils.getGreen(colorInt), ColorUtils.getBlue(colorInt), ColorUtils.getAlpha(colorInt)).func_181675_d();
            } else {
                worldRenderer.func_181662_b((double)x, (double)y, 0.0).func_181675_d();
            }
        } else {
            this.bindAnimatedColor(x, y);
        }
        return this;
    }
}

