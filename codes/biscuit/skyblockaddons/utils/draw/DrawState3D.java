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

public class DrawState3D
extends DrawState {
    private static final WorldRenderer worldRenderer = Tessellator.func_178181_a().func_178180_c();

    public DrawState3D(SkyblockColor theColor, int theDrawType, VertexFormat theFormat, boolean isTextured, boolean shouldIgnoreTexture) {
        super(theColor, theDrawType, theFormat, isTextured, shouldIgnoreTexture);
    }

    public DrawState3D(SkyblockColor theColor, boolean isTextured, boolean shouldIgnoreTexture) {
        super(theColor, isTextured, shouldIgnoreTexture);
    }

    public DrawState3D newColorEnv() {
        super.newColor(true);
        return this;
    }

    public DrawState3D endColorEnv() {
        super.endColor();
        return this;
    }

    public DrawState3D setColor(SkyblockColor color) {
        super.reColor(color);
        return this;
    }

    public DrawState3D beginWorldRenderer() {
        super.beginWorld();
        return this;
    }

    public DrawState3D bindColor(float x, float y, float z) {
        super.bindColor(this.color.getColorAtPosition(x, y, z));
        return this;
    }

    public DrawState3D addColoredVertex(float x, float y, float z) {
        if (this.canAddVertices) {
            if (this.color.drawMulticolorManually()) {
                int colorInt = this.color.getColorAtPosition(x, y);
                worldRenderer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(ColorUtils.getRed(colorInt), ColorUtils.getGreen(colorInt), ColorUtils.getBlue(colorInt), ColorUtils.getAlpha(colorInt)).func_181675_d();
            } else {
                worldRenderer.func_181662_b((double)x, (double)y, (double)z).func_181675_d();
            }
        } else {
            this.bindColor(x, y, z);
        }
        return this;
    }
}

