/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.input.Mouse
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

public class ButtonResize
extends ButtonFeature {
    private static final int SIZE = 2;
    private SkyblockAddons main = SkyblockAddons.getInstance();
    private Corner corner;
    public float x;
    public float y;
    private float cornerOffsetX;
    private float cornerOffsetY;

    public ButtonResize(float x, float y, Feature feature, Corner corner) {
        super(0, 0, 0, "", feature);
        this.corner = corner;
        this.x = x;
        this.y = y;
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        float scale = this.main.getConfigValues().getGuiScale(this.feature);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
        this.field_146123_n = (float)mouseX >= (this.x - 2.0f) * scale && (float)mouseY >= (this.y - 2.0f) * scale && (float)mouseX < (this.x + 2.0f) * scale && (float)mouseY < (this.y + 2.0f) * scale;
        int color = this.field_146123_n ? ColorCode.WHITE.getColor() : ColorCode.WHITE.getColor(70);
        DrawUtils.drawRectAbsolute(this.x - 2.0f, this.y - 2.0f, this.x + 2.0f, this.y + 2.0f, color);
        GlStateManager.func_179121_F();
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(mc);
        float minecraftScale = sr.func_78325_e();
        float floatMouseX = (float)Mouse.getX() / minecraftScale;
        float floatMouseY = (float)(mc.field_71440_d - Mouse.getY()) / minecraftScale;
        this.cornerOffsetX = floatMouseX;
        this.cornerOffsetY = floatMouseY;
        return this.field_146123_n;
    }

    public SkyblockAddons getMain() {
        return this.main;
    }

    public Corner getCorner() {
        return this.corner;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getCornerOffsetX() {
        return this.cornerOffsetX;
    }

    public float getCornerOffsetY() {
        return this.cornerOffsetY;
    }

    public static enum Corner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT;

    }
}

