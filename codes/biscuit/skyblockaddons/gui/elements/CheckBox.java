/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.elements;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.features.craftingpatterns.CraftingPattern;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class CheckBox {
    private static final int ICON_SIZE = 16;
    private final float scale;
    private final Minecraft mc;
    private final int x;
    private final int y;
    private final String text;
    private final int textWidth;
    private final int size;
    private boolean value;
    private OnToggleListener onToggleListener;

    public CheckBox(Minecraft mc, int x, int y, int size, String text, boolean value) {
        this(mc, x, y, size, text);
        this.value = value;
    }

    CheckBox(Minecraft mc, int x, int y, int size, String text) {
        this.mc = mc;
        this.x = x;
        this.y = y;
        this.scale = (float)size / 16.0f;
        this.text = text;
        this.textWidth = mc.field_71466_p.func_78256_a(text);
        this.size = size;
    }

    public void draw() {
        int scaledX = Math.round((float)this.x / this.scale);
        int scaledY = Math.round((float)this.y / this.scale);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a((float)this.scale, (float)this.scale, (float)1.0f);
        int color = this.value ? ColorCode.WHITE.getColor() : ColorCode.GRAY.getColor();
        DrawUtils.drawText(this.text, scaledX + Math.round((float)this.size * 1.5f / this.scale), scaledY + this.size / 2, color);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        Minecraft.func_71410_x().func_110434_K().func_110577_a(CraftingPattern.ICONS);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (this.value) {
            this.mc.field_71456_v.func_73729_b(scaledX, scaledY, 49, 34, 16, 16);
        } else {
            this.mc.field_71456_v.func_73729_b(scaledX, scaledY, 33, 34, 16, 16);
        }
        GlStateManager.func_179126_j();
        GlStateManager.func_179121_F();
    }

    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX > this.x && mouseX < this.x + this.size + this.textWidth && mouseY > this.y && mouseY < this.y + this.size) {
            this.value = !this.value;
            SkyblockAddons.getInstance().getUtils().playSound("gui.button.press", 0.25, 1.0);
            if (this.onToggleListener != null) {
                this.onToggleListener.onToggle(this.value);
            }
            Utils.blockNextClick = true;
        }
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    boolean getValue() {
        return this.value;
    }

    public void setOnToggleListener(OnToggleListener listener) {
        this.onToggleListener = listener;
    }

    @FunctionalInterface
    public static interface OnToggleListener {
        public void onToggle(boolean var1);
    }
}

