/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector3d
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.vector.Matrix4f
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.chroma.ManualChromaManager;
import codes.biscuit.skyblockaddons.shader.ShaderManager;
import codes.biscuit.skyblockaddons.shader.chroma.Chroma3DShader;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaScreenShader;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaScreenTexturedShader;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import java.awt.Color;
import java.nio.FloatBuffer;
import javax.vecmath.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class DrawUtils {
    private static final double HALF_PI = 1.5707963267948966;
    private static final double PI = Math.PI;
    private static final Tessellator tessellator = Tessellator.func_178181_a();
    private static final WorldRenderer worldRenderer = tessellator.func_178180_c();
    private static boolean previousTextureState;
    private static boolean previousBlendState;
    private static boolean previousCullState;
    private static final FloatBuffer BUF_FLOAT_4;

    public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight, boolean linearTexture) {
        if (linearTexture) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        }
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)x, (double)(y + height), 0.0).func_181673_a((double)(u * f), (double)((v + vHeight) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), 0.0).func_181673_a((double)((u + uWidth) * f), (double)((v + vHeight) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)y, 0.0).func_181673_a((double)((u + uWidth) * f), (double)(v * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)x, (double)y, 0.0).func_181673_a((double)(u * f), (double)(v * f1)).func_181675_d();
        tessellator.func_78381_a();
        if (linearTexture) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        }
    }

    public static void drawCylinder(double x, double y, double z, float radius, float height, SkyblockColor color) {
        DrawUtils.begin3D(7, color);
        Vector3d viewPosition = Utils.getPlayerViewPosition();
        double startAngle = Math.atan2(viewPosition.z - z, viewPosition.x - x) + Math.PI;
        x -= viewPosition.x;
        y -= viewPosition.y;
        z -= viewPosition.z;
        int segments = 64;
        double angleStep = Math.PI * 2 / (double)segments;
        for (int segment = 0; segment < segments / 2; ++segment) {
            double previousAngleOffset = (double)segment * angleStep;
            double currentAngleOffset = (double)(segment + 1) * angleStep;
            double previousRotatedX = x + (double)radius * Math.cos(startAngle + previousAngleOffset);
            double previousRotatedZ = z + (double)radius * Math.sin(startAngle + previousAngleOffset);
            double rotatedX = x + (double)radius * Math.cos(startAngle + currentAngleOffset);
            double rotatedZ = z + (double)radius * Math.sin(startAngle + currentAngleOffset);
            DrawUtils.add3DVertex(previousRotatedX, y + (double)height, previousRotatedZ, color);
            DrawUtils.add3DVertex(rotatedX, y + (double)height, rotatedZ, color);
            DrawUtils.add3DVertex(rotatedX, y, rotatedZ, color);
            DrawUtils.add3DVertex(previousRotatedX, y, previousRotatedZ, color);
            previousRotatedX = x + (double)radius * Math.cos(startAngle - previousAngleOffset);
            previousRotatedZ = z + (double)radius * Math.sin(startAngle - previousAngleOffset);
            rotatedX = x + (double)radius * Math.cos(startAngle - currentAngleOffset);
            rotatedZ = z + (double)radius * Math.sin(startAngle - currentAngleOffset);
            DrawUtils.add3DVertex(previousRotatedX, y + (double)height, previousRotatedZ, color);
            DrawUtils.add3DVertex(previousRotatedX, y, previousRotatedZ, color);
            DrawUtils.add3DVertex(rotatedX, y, rotatedZ, color);
            DrawUtils.add3DVertex(rotatedX, y + (double)height, rotatedZ, color);
        }
        DrawUtils.end(color);
    }

    public static void begin2D(int drawType, SkyblockColor color) {
        if (color.drawMulticolorManually()) {
            worldRenderer.func_181668_a(drawType, DefaultVertexFormats.field_181706_f);
            GlStateManager.func_179103_j((int)7425);
        } else {
            worldRenderer.func_181668_a(drawType, DefaultVertexFormats.field_181705_e);
            if (color.drawMulticolorUsingShader()) {
                ColorUtils.bindWhite();
                if (GlStateManager.field_179174_p[GlStateManager.field_179162_o].field_179060_a.field_179201_b) {
                    ShaderManager.getInstance().enableShader(ChromaScreenTexturedShader.class);
                } else {
                    ShaderManager.getInstance().enableShader(ChromaScreenShader.class);
                }
            } else {
                ColorUtils.bindColor(color.getColor());
            }
        }
    }

    public static void begin3D(int drawType, SkyblockColor color) {
        if (color.drawMulticolorManually()) {
            worldRenderer.func_181668_a(drawType, DefaultVertexFormats.field_181706_f);
            GlStateManager.func_179103_j((int)7425);
        } else {
            worldRenderer.func_181668_a(drawType, DefaultVertexFormats.field_181705_e);
            if (color.drawMulticolorUsingShader()) {
                Chroma3DShader chroma3DShader = ShaderManager.getInstance().enableShader(Chroma3DShader.class);
                chroma3DShader.setAlpha(ColorUtils.getAlphaFloat(color.getColor()));
            } else {
                ColorUtils.bindColor(color.getColor());
            }
        }
    }

    public static void end(SkyblockColor color) {
        if (color.drawMulticolorManually()) {
            tessellator.func_78381_a();
            GlStateManager.func_179103_j((int)7424);
        } else {
            tessellator.func_78381_a();
            if (color.drawMulticolorUsingShader()) {
                ShaderManager.getInstance().disableShader();
            }
        }
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        DrawUtils.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight, false);
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight, boolean linearTexture) {
        if (linearTexture) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        }
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)x, (double)(y + height), 0.0).func_181673_a((double)(u * f), (double)((v + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), 0.0).func_181673_a((double)((u + width) * f), (double)((v + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)y, 0.0).func_181673_a((double)((u + width) * f), (double)(v * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)x, (double)y, 0.0).func_181673_a((double)(u * f), (double)(v * f1)).func_181675_d();
        tessellator.func_78381_a();
        if (linearTexture) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        }
    }

    public static void drawRectAbsolute(double left, double top, double right, double bottom, int color) {
        DrawUtils.drawRectAbsolute(left, top, right, bottom, color, false);
    }

    public static void drawRectAbsolute(double left, double top, double right, double bottom, int color, boolean chroma) {
        if (left < right) {
            double savedLeft = left;
            left = right;
            right = savedLeft;
        }
        if (top < bottom) {
            double savedTop = top;
            top = bottom;
            bottom = savedTop;
        }
        DrawUtils.drawRectInternal(left, top, right - left, bottom - top, color, chroma);
    }

    public static void drawRect(double x, double y, double w, double h, SkyblockColor color, int rounding) {
        DrawUtils.drawRectInternal(x, y, w, h, color, rounding);
    }

    public static void drawRect(double x, double y, double w, double h, int color) {
        DrawUtils.drawRectInternal(x, y, w, h, color, false);
    }

    public static void drawRect(double x, double y, double w, double h, int color, boolean chroma) {
        DrawUtils.drawRectInternal(x, y, w, h, color, chroma);
    }

    private static void drawRectInternal(double x, double y, double w, double h, int color, boolean chroma) {
        DrawUtils.drawRectInternal(x, y, w, h, ColorUtils.getDummySkyblockColor(chroma ? SkyblockColor.ColorAnimation.CHROMA : SkyblockColor.ColorAnimation.NONE, color));
    }

    private static void drawRectInternal(double x, double y, double w, double h, SkyblockColor color) {
        DrawUtils.drawRectInternal(x, y, w, h, color, 0);
    }

    private static void drawRectInternal(double x, double y, double w, double h, SkyblockColor color, int rounding) {
        if (rounding > 0) {
            DrawUtils.drawRoundedRectangle(x, y, w, h, color, rounding);
            return;
        }
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        DrawUtils.begin2D(7, color);
        DrawUtils.addQuadVertices(x, y, w, h, color);
        DrawUtils.end(color);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    private static void addQuadVertices(double x, double y, double w, double h, SkyblockColor color) {
        DrawUtils.addQuadVerticesAbsolute(x, y, x + w, y + h, color);
    }

    private static void addQuadVerticesAbsolute(double left, double top, double right, double bottom, SkyblockColor color) {
        DrawUtils.addVertex(left, bottom, color);
        DrawUtils.addVertex(right, bottom, color);
        DrawUtils.addVertex(right, top, color);
        DrawUtils.addVertex(left, top, color);
    }

    private static void addVertex(double x, double y, SkyblockColor color) {
        if (color.drawMulticolorManually()) {
            int colorInt = color.getColorAtPosition((float)x, (float)y);
            worldRenderer.func_181662_b(x, y, 0.0).func_181669_b(ColorUtils.getRed(colorInt), ColorUtils.getGreen(colorInt), ColorUtils.getBlue(colorInt), ColorUtils.getAlpha(colorInt)).func_181675_d();
        } else {
            worldRenderer.func_181662_b(x, y, 0.0).func_181675_d();
        }
    }

    private static void add3DVertex(double x, double y, double z, SkyblockColor color) {
        if (color.drawMulticolorManually()) {
            Vector3d viewPosition = Utils.getPlayerViewPosition();
            int colorInt = color.getColorAtPosition(x + viewPosition.x, y + viewPosition.y, z + viewPosition.z);
            worldRenderer.func_181662_b(x, y, z).func_181669_b(ColorUtils.getRed(colorInt), ColorUtils.getGreen(colorInt), ColorUtils.getBlue(colorInt), ColorUtils.getAlpha(colorInt)).func_181675_d();
        } else {
            worldRenderer.func_181662_b(x, y, z).func_181675_d();
        }
    }

    private static void drawRoundedRectangle(double x, double y, double w, double h, SkyblockColor color, double rounding) {
        double angle;
        int segment;
        DrawUtils.enableBlend();
        DrawUtils.disableCull();
        DrawUtils.disableTexture();
        DrawUtils.begin2D(7, color);
        double x1 = x + rounding;
        double x2 = x + w - rounding;
        double y1 = y;
        double y2 = y + h;
        DrawUtils.addVertex(x1, y2, color);
        DrawUtils.addVertex(x2, y2, color);
        DrawUtils.addVertex(x2, y1, color);
        DrawUtils.addVertex(x1, y1, color);
        x1 = x;
        x2 = x + rounding;
        y1 = y + rounding;
        y2 = y + h - rounding;
        DrawUtils.addVertex(x1, y2, color);
        DrawUtils.addVertex(x2, y2, color);
        DrawUtils.addVertex(x2, y1, color);
        DrawUtils.addVertex(x1, y1, color);
        x1 = x + w - rounding;
        x2 = x + w;
        y1 = y + rounding;
        y2 = y + h - rounding;
        DrawUtils.addVertex(x1, y2, color);
        DrawUtils.addVertex(x2, y2, color);
        DrawUtils.addVertex(x2, y1, color);
        DrawUtils.addVertex(x1, y1, color);
        DrawUtils.end(color);
        int segments = 64;
        double angleStep = 1.5707963267948966 / (double)segments;
        DrawUtils.begin2D(6, color);
        double startAngle = -1.5707963267948966;
        double startX = x + rounding;
        double startY = y + rounding;
        DrawUtils.addVertex(startX, startY, color);
        for (segment = 0; segment <= segments; ++segment) {
            angle = startAngle - angleStep * (double)segment;
            DrawUtils.addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        DrawUtils.end(color);
        DrawUtils.begin2D(6, color);
        startAngle = 0.0;
        startX = x + w - rounding;
        startY = y + rounding;
        DrawUtils.addVertex(startX, startY, color);
        for (segment = 0; segment <= segments; ++segment) {
            angle = startAngle - angleStep * (double)segment;
            DrawUtils.addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        DrawUtils.end(color);
        DrawUtils.begin2D(6, color);
        startAngle = 1.5707963267948966;
        startX = x + w - rounding;
        startY = y + h - rounding;
        DrawUtils.addVertex(startX, startY, color);
        for (segment = 0; segment <= segments; ++segment) {
            angle = startAngle - angleStep * (double)segment;
            DrawUtils.addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        DrawUtils.end(color);
        DrawUtils.begin2D(6, color);
        startAngle = Math.PI;
        startX = x + rounding;
        startY = y + h - rounding;
        DrawUtils.addVertex(startX, startY, color);
        for (segment = 0; segment <= segments; ++segment) {
            angle = startAngle - angleStep * (double)segment;
            DrawUtils.addVertex(startX + rounding * Math.cos(angle), startY + rounding * Math.sin(angle), color);
        }
        DrawUtils.end(color);
        DrawUtils.restoreCull();
        DrawUtils.restoreTexture();
        DrawUtils.restoreBlend();
    }

    public static void drawRectOutline(float x, float y, int w, int h, int thickness, int color, boolean chroma) {
        DrawUtils.drawRectOutline(x, y, w, h, thickness, ColorUtils.getDummySkyblockColor(color, chroma));
    }

    public static void drawRectOutline(float x, float y, int w, int h, int thickness, SkyblockColor color) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        DrawUtils.begin2D(7, color);
        if (color.drawMulticolorManually()) {
            DrawUtils.drawSegmentedLineVertical(x - (float)thickness, y, thickness, h, color);
            DrawUtils.drawSegmentedLineHorizontal(x - (float)thickness, y - (float)thickness, w + thickness * 2, thickness, color);
            DrawUtils.drawSegmentedLineVertical(x + (float)w, y, thickness, h, color);
            DrawUtils.drawSegmentedLineHorizontal(x - (float)thickness, y + (float)h, w + thickness * 2, thickness, color);
        } else {
            DrawUtils.addQuadVertices(x - (float)thickness, y, thickness, h, color);
            DrawUtils.addQuadVertices(x - (float)thickness, y - (float)thickness, w + thickness * 2, thickness, color);
            DrawUtils.addQuadVertices(x + (float)w, y, thickness, h, color);
            DrawUtils.addQuadVertices(x - (float)thickness, y + (float)h, w + thickness * 2, thickness, color);
        }
        DrawUtils.end(color);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawSegmentedLineHorizontal(float x, float y, float w, float h, SkyblockColor color) {
        int segments = (int)(w * ManualChromaManager.getFeatureScale() / 10.0f);
        float length = w / (float)segments;
        for (int segment = 0; segment < segments; ++segment) {
            float start = x + length * (float)segment;
            DrawUtils.addQuadVertices(start, y, length, h, color);
        }
    }

    public static void drawSegmentedLineVertical(float x, float y, float w, float h, SkyblockColor color) {
        int segments = (int)(h * ManualChromaManager.getFeatureScale() / 10.0f);
        float length = h / (float)segments;
        for (int segment = 0; segment < segments; ++segment) {
            float start = y + length * (float)segment;
            DrawUtils.addQuadVertices(x, start, w, length, color);
        }
    }

    public static void drawText(String text, float x, float y, int color) {
        if (text == null) {
            return;
        }
        FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
        if (SkyblockAddons.getInstance().getConfigValues().getTextStyle() == EnumUtils.TextStyle.STYLE_TWO) {
            int colorAlpha = Math.max(ColorUtils.getAlpha(color), 4);
            int colorBlack = new Color(0.0f, 0.0f, 0.0f, (float)colorAlpha / 255.0f).getRGB();
            String strippedText = TextUtils.stripColor(text);
            fontRenderer.func_175065_a(strippedText, x + 1.0f, y + 0.0f, colorBlack, false);
            fontRenderer.func_175065_a(strippedText, x + -1.0f, y + 0.0f, colorBlack, false);
            fontRenderer.func_175065_a(strippedText, x + 0.0f, y + 1.0f, colorBlack, false);
            fontRenderer.func_175065_a(strippedText, x + 0.0f, y + -1.0f, colorBlack, false);
            fontRenderer.func_175065_a(text, x + 0.0f, y + 0.0f, color, false);
        } else {
            fontRenderer.func_175065_a(text, x + 0.0f, y + 0.0f, color, true);
        }
    }

    public static void drawCenteredText(String text, float x, float y, int color) {
        DrawUtils.drawText(text, x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) / 2.0f, y, color);
    }

    public static void printCurrentGLTransformations() {
        FloatBuffer buf = BufferUtils.createFloatBuffer((int)16);
        GL11.glGetFloat((int)2982, (FloatBuffer)buf);
        buf.rewind();
        Matrix4f mat = new Matrix4f();
        mat.load(buf);
        float x = mat.m30;
        float y = mat.m31;
        float z = mat.m32;
        float scale = (float)Math.sqrt(mat.m00 * mat.m00 + mat.m01 * mat.m01 + mat.m02 * mat.m02);
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("z: " + z);
        System.out.println("scale: " + scale);
    }

    public static void enableBlend() {
        GlStateManager.func_179147_l();
        GlStateManager.func_179141_d();
    }

    public static void disableBlend() {
        GlStateManager.func_179084_k();
    }

    public static void restoreBlend() {
    }

    public static void enableCull() {
        previousCullState = GlStateManager.field_179167_h.field_179054_a.field_179201_b;
        GlStateManager.func_179089_o();
    }

    public static void disableCull() {
        previousCullState = GlStateManager.field_179167_h.field_179054_a.field_179201_b;
        GlStateManager.func_179129_p();
    }

    public static void restoreCull() {
        if (previousCullState) {
            GlStateManager.func_179089_o();
        } else {
            GlStateManager.func_179129_p();
        }
    }

    public static void enableTexture() {
        previousTextureState = GlStateManager.field_179174_p[GlStateManager.field_179162_o].field_179060_a.field_179201_b;
        GlStateManager.func_179098_w();
    }

    public static void disableTexture() {
        previousTextureState = GlStateManager.field_179174_p[GlStateManager.field_179162_o].field_179060_a.field_179201_b;
        GlStateManager.func_179090_x();
    }

    public static void restoreTexture() {
        if (previousTextureState) {
            GlStateManager.func_179098_w();
        } else {
            GlStateManager.func_179090_x();
        }
    }

    public static void enableOutlineMode() {
        GL11.glTexEnvi((int)8960, (int)8704, (int)34160);
        GL11.glTexEnvi((int)8960, (int)34161, (int)7681);
        GL11.glTexEnvi((int)8960, (int)34176, (int)34166);
        GL11.glTexEnvi((int)8960, (int)34192, (int)768);
        GL11.glTexEnvi((int)8960, (int)34162, (int)7681);
        GL11.glTexEnvi((int)8960, (int)34184, (int)5890);
        GL11.glTexEnvi((int)8960, (int)34200, (int)770);
    }

    public static void outlineColor(int color) {
        BUF_FLOAT_4.put(0, (float)(color >> 16 & 0xFF) / 255.0f);
        BUF_FLOAT_4.put(1, (float)(color >> 8 & 0xFF) / 255.0f);
        BUF_FLOAT_4.put(2, (float)(color & 0xFF) / 255.0f);
        BUF_FLOAT_4.put(3, 1.0f);
        GL11.glTexEnv((int)8960, (int)8705, (FloatBuffer)BUF_FLOAT_4);
    }

    public static void disableOutlineMode() {
        GL11.glTexEnvi((int)8960, (int)8704, (int)8448);
        GL11.glTexEnvi((int)8960, (int)34161, (int)8448);
        GL11.glTexEnvi((int)8960, (int)34176, (int)5890);
        GL11.glTexEnvi((int)8960, (int)34192, (int)768);
        GL11.glTexEnvi((int)8960, (int)34162, (int)8448);
        GL11.glTexEnvi((int)8960, (int)34184, (int)5890);
        GL11.glTexEnvi((int)8960, (int)34200, (int)770);
    }

    public static void drawTextInWorld(String str, double x, double y, double z) {
        Minecraft mc = Minecraft.func_71410_x();
        FontRenderer fontrenderer = mc.field_71466_p;
        RenderManager renderManager = mc.func_175598_ae();
        float f = 1.6f;
        float f1 = 0.016666668f * f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)x, (double)y, (double)z);
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(-renderManager.field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)renderManager.field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179152_a((float)(-f1), (float)(-f1), (float)f1);
        GlStateManager.func_179140_f();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        int j = fontrenderer.func_78256_a(str) / 2;
        GlStateManager.func_179090_x();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b((double)(-j - 1), -1.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
        worldrenderer.func_181662_b((double)(-j - 1), 8.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
        worldrenderer.func_181662_b((double)(j + 1), 8.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
        worldrenderer.func_181662_b((double)(j + 1), -1.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.25f).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, 0, 0x20FFFFFF);
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a((boolean)true);
        fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, 0, -1);
        GlStateManager.func_179145_e();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
    }

    static {
        BUF_FLOAT_4 = BufferUtils.createFloatBuffer((int)4);
    }
}

