/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
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
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

public class ButtonLocation
extends ButtonFeature {
    private static Feature lastHoveredFeature = null;
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private float boxXOne;
    private float boxXTwo;
    private float boxYOne;
    private float boxYTwo;
    private float scale;

    public ButtonLocation(Feature feature) {
        super(-1, 0, 0, null, feature);
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        float scale = this.main.getConfigValues().getGuiScale(this.feature);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
        if (this.feature == Feature.DEFENCE_ICON) {
            this.main.getRenderListener().drawIcon(scale, mc, this);
        } else {
            this.feature.draw(scale, mc, this);
        }
        GlStateManager.func_179121_F();
        if (this.field_146123_n) {
            lastHoveredFeature = this.feature;
        }
    }

    public void checkHoveredAndDrawBox(float boxXOne, float boxXTwo, float boxYOne, float boxYTwo, float scale) {
        ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
        float minecraftScale = sr.func_78325_e();
        float floatMouseX = (float)Mouse.getX() / minecraftScale;
        float floatMouseY = (float)(Minecraft.func_71410_x().field_71440_d - Mouse.getY()) / minecraftScale;
        this.field_146123_n = floatMouseX >= boxXOne * scale && floatMouseY >= boxYOne * scale && floatMouseX < boxXTwo * scale && floatMouseY < boxYTwo * scale;
        int boxAlpha = 70;
        if (this.field_146123_n) {
            boxAlpha = 120;
        }
        int boxColor = ColorCode.GRAY.getColor(boxAlpha);
        DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);
        this.boxXOne = boxXOne;
        this.boxXTwo = boxXTwo;
        this.boxYOne = boxYOne;
        this.boxYTwo = boxYTwo;
        this.scale = scale;
    }

    public void checkHoveredAndDrawBox(float boxXOne, float boxXTwo, float boxYOne, float boxYTwo, float scale, float scaleX, float scaleY) {
        ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
        float minecraftScale = sr.func_78325_e();
        float floatMouseX = (float)Mouse.getX() / minecraftScale;
        float floatMouseY = (float)(Minecraft.func_71410_x().field_71440_d - Mouse.getY()) / minecraftScale;
        this.field_146123_n = floatMouseX >= boxXOne * scale * scaleX && floatMouseY >= boxYOne * scale * scaleY && floatMouseX < boxXTwo * scale * scaleX && floatMouseY < boxYTwo * scale * scaleY;
        int boxAlpha = 70;
        if (this.field_146123_n) {
            boxAlpha = 120;
        }
        int boxColor = ColorCode.GRAY.getColor(boxAlpha);
        DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);
        this.boxXOne = boxXOne;
        this.boxXTwo = boxXTwo;
        this.boxYOne = boxYOne;
        this.boxYTwo = boxYTwo;
        this.scale = scale;
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        return this.field_146124_l && this.field_146125_m && this.field_146123_n;
    }

    public void func_146113_a(SoundHandler soundHandlerIn) {
    }

    public SkyblockAddons getMain() {
        return this.main;
    }

    public float getBoxXOne() {
        return this.boxXOne;
    }

    public float getBoxXTwo() {
        return this.boxXTwo;
    }

    public float getBoxYOne() {
        return this.boxYOne;
    }

    public float getBoxYTwo() {
        return this.boxYTwo;
    }

    public float getScale() {
        return this.scale;
    }

    public static Feature getLastHoveredFeature() {
        return lastHoveredFeature;
    }
}

