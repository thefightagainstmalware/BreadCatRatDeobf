/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonSelect
extends GuiButton {
    private static final ResourceLocation ARROW_LEFT = new ResourceLocation("skyblockaddons", "gui/flatarrowleft.png");
    private static final ResourceLocation ARROW_RIGHT = new ResourceLocation("skyblockaddons", "gui/flatarrowright.png");
    private final List<SelectItem> itemList;
    private int index = 0;
    private final int textWidth;
    private final OnItemSelectedCallback callback;

    public ButtonSelect(int x, int y, int width, int height, List<SelectItem> items, int selectedIndex, OnItemSelectedCallback callback) {
        super(0, x, y, "");
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Item list must have at least one element.");
        }
        this.textWidth = width - 2 * height - 6;
        this.field_146120_f = width;
        this.field_146121_g = height;
        this.itemList = items;
        this.index = selectedIndex > 0 && selectedIndex < this.itemList.size() ? selectedIndex : 0;
        this.callback = callback;
    }

    public void func_146112_a(Minecraft minecraft, int mouseX, int mouseY) {
        String trimmedName;
        int endX = this.field_146128_h + this.field_146120_f;
        int color = SkyblockAddons.getInstance().getUtils().getDefaultColor(100.0f);
        int leftColor = SkyblockAddons.getInstance().getUtils().getDefaultColor(this.isOverLeftButton(mouseX, mouseY) ? 200.0f : 90.0f);
        int rightColor = SkyblockAddons.getInstance().getUtils().getDefaultColor(this.isOverRightButton(mouseX, mouseY) ? 200.0f : 90.0f);
        String name = this.itemList.get(this.index).getName();
        if (!name.equals(trimmedName = minecraft.field_71466_p.func_78269_a(name, this.textWidth))) {
            trimmedName = this.ellipsize(trimmedName);
        }
        String description = this.itemList.get(this.index).getDescription();
        ButtonSelect.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)endX, (int)(this.field_146129_i + this.field_146121_g), (int)color);
        ButtonSelect.func_73734_a((int)this.field_146128_h, (int)this.field_146129_i, (int)(this.field_146128_h + this.field_146121_g), (int)(this.field_146129_i + this.field_146121_g), (int)leftColor);
        ButtonSelect.func_73734_a((int)(endX - this.field_146121_g), (int)this.field_146129_i, (int)endX, (int)(this.field_146129_i + this.field_146121_g), (int)rightColor);
        this.func_73732_a(minecraft.field_71466_p, trimmedName, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + this.field_146121_g / 4, ColorCode.WHITE.getColor());
        this.func_73732_a(minecraft.field_71466_p, description, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + this.field_146121_g + 2, ColorCode.GRAY.getColor());
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        minecraft.func_110434_K().func_110577_a(ARROW_LEFT);
        ButtonSelect.func_146110_a((int)this.field_146128_h, (int)this.field_146129_i, (float)0.0f, (float)0.0f, (int)this.field_146121_g, (int)this.field_146121_g, (float)this.field_146121_g, (float)this.field_146121_g);
        minecraft.func_110434_K().func_110577_a(ARROW_RIGHT);
        ButtonSelect.func_146110_a((int)(endX - this.field_146121_g), (int)this.field_146129_i, (float)0.0f, (float)0.0f, (int)this.field_146121_g, (int)this.field_146121_g, (float)this.field_146121_g, (float)this.field_146121_g);
        if (!name.equals(trimmedName) && this.isOverText(mouseX, mouseY)) {
            int stringWidth = minecraft.field_71466_p.func_78256_a(name);
            int rectLeft = mouseX + 3;
            int rectTop = mouseY + 3;
            int rectRight = rectLeft + stringWidth + 8;
            int rectBottom = rectTop + 12;
            ButtonSelect.func_73734_a((int)rectLeft, (int)rectTop, (int)rectRight, (int)rectBottom, (int)ColorCode.BLACK.getColor());
            minecraft.field_71466_p.func_78276_b(name, rectLeft + 4, rectTop + 2, ColorCode.WHITE.getColor());
        }
    }

    public boolean func_146116_c(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.isOverLeftButton(mouseX, mouseY)) {
            this.index = this.index == this.itemList.size() - 1 ? 0 : this.index + 1;
            this.notifyCallback(this.index);
            return true;
        }
        if (this.isOverRightButton(mouseX, mouseY)) {
            this.index = this.index == 0 ? this.itemList.size() - 1 : this.index - 1;
            this.notifyCallback(this.index);
            return true;
        }
        return false;
    }

    private void notifyCallback(int index) {
        if (this.callback != null) {
            this.callback.onItemSelected(index);
        }
    }

    private boolean isOverText(int mouseX, int mouseY) {
        return mouseX > this.field_146128_h + this.field_146121_g && mouseX < this.field_146128_h + this.field_146120_f - this.field_146121_g && mouseY > this.field_146129_i && mouseY < this.field_146129_i + this.field_146121_g;
    }

    private boolean isOverLeftButton(int mouseX, int mouseY) {
        return mouseX > this.field_146128_h && mouseX < this.field_146128_h + this.field_146121_g && mouseY > this.field_146129_i && mouseY < this.field_146129_i + this.field_146121_g;
    }

    private boolean isOverRightButton(int mouseX, int mouseY) {
        return mouseX > this.field_146128_h + this.field_146120_f - this.field_146121_g && mouseX < this.field_146128_h + this.field_146120_f && mouseY > this.field_146129_i && mouseY < this.field_146129_i + this.field_146121_g;
    }

    private String ellipsize(String text) {
        return new StringBuilder(text).replace(text.length() - 1, text.length(), "\u2026").toString();
    }

    @FunctionalInterface
    public static interface OnItemSelectedCallback {
        public void onItemSelected(int var1);
    }

    public static interface SelectItem {
        public String getName();

        public String getDescription();
    }
}

