/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.gui.SkyblockAddonsGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonColorBox;
import codes.biscuit.skyblockaddons.gui.buttons.NewButtonSlider;
import codes.biscuit.skyblockaddons.gui.elements.CheckBox;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class ColorSelectionGui
extends GuiScreen {
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private static final ResourceLocation COLOR_PICKER = new ResourceLocation("skyblockaddons", "gui/colorpicker.png");
    private BufferedImage COLOR_PICKER_IMAGE;
    private final Feature feature;
    private final EnumUtils.GUIType lastGUI;
    private final EnumUtils.GuiTab lastTab;
    private final int lastPage;
    private int imageX;
    private int imageY;
    private GuiTextField hexColorField;
    private CheckBox chromaCheckbox;

    ColorSelectionGui(Feature feature, EnumUtils.GUIType lastGUI, EnumUtils.GuiTab lastTab, int lastPage) {
        this.feature = feature;
        this.lastTab = lastTab;
        this.lastGUI = lastGUI;
        this.lastPage = lastPage;
        try {
            this.COLOR_PICKER_IMAGE = TextureUtil.func_177053_a((InputStream)Minecraft.func_71410_x().func_110442_L().func_110536_a(COLOR_PICKER).func_110527_b());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void func_73866_w_() {
        this.chromaCheckbox = new CheckBox(this.field_146297_k, this.field_146294_l / 2 + 88, 170, 12, Translations.getMessage("messages.chroma", new Object[0]), false);
        this.chromaCheckbox.setValue(this.main.getConfigValues().getChromaFeatures().contains((Object)this.feature));
        this.chromaCheckbox.setOnToggleListener(value -> {
            this.main.getConfigValues().setChroma(this.feature, value);
            this.removeChromaButtons();
            if (value) {
                this.addChromaButtons();
            }
        });
        this.hexColorField = new GuiTextField(0, Minecraft.func_71410_x().field_71466_p, this.field_146294_l / 2 + 110 - 50, 220, 100, 15);
        this.hexColorField.func_146203_f(7);
        this.hexColorField.func_146195_b(true);
        this.setTextBoxHex(this.main.getConfigValues().getColor(this.feature));
        if (this.feature.getGuiFeatureData().isColorsRestricted()) {
            int collumn = 1;
            int x = this.field_146294_l / 2 - 160;
            int y = 120;
            for (ColorCode colorCode : ColorCode.values()) {
                if (colorCode.isFormat() || colorCode == ColorCode.RESET) continue;
                this.field_146292_n.add(new ButtonColorBox(x, y, colorCode));
                if (collumn < 6) {
                    ++collumn;
                    x += 55;
                    continue;
                }
                y += 40;
                collumn = 1;
                x = this.field_146294_l / 2 - 160;
            }
        }
        if (this.main.getConfigValues().getChromaFeatures().contains((Object)this.feature) && !this.feature.getGuiFeatureData().isColorsRestricted()) {
            this.addChromaButtons();
        }
        Keyboard.enableRepeatEvents((boolean)true);
        super.func_73866_w_();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int startColor = new Color(0, 0, 0, 128).getRGB();
        int endColor = new Color(0, 0, 0, 192).getRGB();
        this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, startColor, endColor);
        SkyblockAddonsGui.drawDefaultTitleText(this, 255);
        int defaultBlue = this.main.getUtils().getDefaultBlue(255);
        if (this.feature.getGuiFeatureData() != null) {
            if (this.feature.getGuiFeatureData().isColorsRestricted()) {
                SkyblockAddonsGui.drawScaledString(this, Message.MESSAGE_CHOOSE_A_COLOR.getMessage(new String[0]), 90, defaultBlue, 1.5, 0);
            } else {
                int pickerWidth = this.COLOR_PICKER_IMAGE.getWidth();
                int pickerHeight = this.COLOR_PICKER_IMAGE.getHeight();
                this.imageX = this.field_146294_l / 2 - 200;
                this.imageY = 90;
                if (this.main.getConfigValues().getChromaFeatures().contains((Object)this.feature)) {
                    GlStateManager.func_179131_c((float)0.5f, (float)0.5f, (float)0.5f, (float)0.7f);
                    GlStateManager.func_179147_l();
                } else {
                    GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                }
                this.field_146297_k.func_110434_K().func_110577_a(COLOR_PICKER);
                Gui.func_146110_a((int)this.imageX, (int)this.imageY, (float)0.0f, (float)0.0f, (int)pickerWidth, (int)pickerHeight, (float)pickerWidth, (float)pickerHeight);
                SkyblockAddonsGui.drawScaledString(this, Message.MESSAGE_SELECTED_COLOR.getMessage(new String[0]), 120, defaultBlue, 1.5, 75);
                ColorSelectionGui.func_73734_a((int)(this.field_146294_l / 2 + 90), (int)140, (int)(this.field_146294_l / 2 + 130), (int)160, (int)this.main.getConfigValues().getColor(this.feature));
                if (this.chromaCheckbox != null) {
                    this.chromaCheckbox.draw();
                }
                if (!this.main.getConfigValues().getChromaFeatures().contains((Object)this.feature)) {
                    SkyblockAddonsGui.drawScaledString(this, Message.MESSAGE_SET_HEX_COLOR.getMessage(new String[0]), 200, defaultBlue, 1.5, 75);
                    this.hexColorField.func_146194_f();
                }
                if (this.main.getConfigValues().getChromaFeatures().contains((Object)this.feature)) {
                    SkyblockAddonsGui.drawScaledString(this, Message.SETTING_CHROMA_SPEED.getMessage(new String[0]), 195, defaultBlue, 1.0, 110);
                    SkyblockAddonsGui.drawScaledString(this, Message.SETTING_CHROMA_FADE_WIDTH.getMessage(new String[0]), 230, defaultBlue, 1.0, 110);
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (!this.feature.getGuiFeatureData().isColorsRestricted() && !this.main.getConfigValues().getChromaFeatures().contains((Object)this.feature)) {
            int selectedColor;
            int xPixel = mouseX - this.imageX;
            int yPixel = mouseY - this.imageY;
            if (xPixel > 0 && xPixel < this.COLOR_PICKER_IMAGE.getWidth() && yPixel > 0 && yPixel < this.COLOR_PICKER_IMAGE.getHeight() && ColorUtils.getAlpha(selectedColor = this.COLOR_PICKER_IMAGE.getRGB(xPixel, yPixel)) == 255) {
                this.main.getConfigValues().setColor(this.feature, selectedColor);
                this.setTextBoxHex(selectedColor);
                this.main.getUtils().playSound("gui.button.press", 0.25, 1.0);
            }
            this.hexColorField.func_146192_a(mouseX, mouseY, mouseButton);
        }
        if (this.chromaCheckbox != null) {
            this.chromaCheckbox.onMouseClick(mouseX, mouseY, mouseButton);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private void setTextBoxHex(int color) {
        this.hexColorField.func_146180_a(String.format("#%02x%02x%02x", ColorUtils.getRed(color), ColorUtils.getGreen(color), ColorUtils.getBlue(color)));
    }

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        if (this.hexColorField.func_146206_l()) {
            this.hexColorField.func_146201_a(typedChar, keyCode);
            String text = this.hexColorField.func_146179_b();
            if (text.startsWith("#")) {
                text = text.substring(1);
            }
            if (text.length() == 6) {
                int typedColor;
                try {
                    typedColor = Integer.parseInt(text, 16);
                }
                catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    return;
                }
                this.main.getConfigValues().setColor(this.feature, typedColor);
            }
        }
    }

    protected void func_146284_a(GuiButton button) throws IOException {
        if (button instanceof ButtonColorBox) {
            ButtonColorBox colorBox = (ButtonColorBox)button;
            this.main.getConfigValues().setChroma(this.feature, colorBox.getColor() == ColorCode.CHROMA);
            this.main.getConfigValues().setColor(this.feature, colorBox.getColor().getColor());
            this.field_146297_k.func_147108_a(null);
        }
        super.func_146284_a(button);
    }

    public void func_73876_c() {
        this.hexColorField.func_146178_a();
        super.func_73876_c();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        if (this.feature == Feature.ENCHANTMENT_PERFECT_COLOR || this.feature == Feature.ENCHANTMENT_GREAT_COLOR || this.feature == Feature.ENCHANTMENT_GOOD_COLOR || this.feature == Feature.ENCHANTMENT_POOR_COLOR || this.feature == Feature.ENCHANTMENT_COMMA_COLOR) {
            this.main.getRenderListener().setGuiToOpen(this.lastGUI, this.lastPage, this.lastTab, Feature.ENCHANTMENT_LORE_PARSING);
        } else {
            this.main.getRenderListener().setGuiToOpen(this.lastGUI, this.lastPage, this.lastTab, this.feature);
        }
    }

    private void removeChromaButtons() {
        this.field_146292_n.removeIf(button -> button instanceof NewButtonSlider);
    }

    private void addChromaButtons() {
        this.field_146292_n.add(new NewButtonSlider(this.field_146294_l / 2 + 76, 205.0, 70, 15, this.main.getConfigValues().getChromaSpeed().floatValue(), 0.5f, 20.0f, 0.5f, updatedValue -> this.main.getConfigValues().getChromaSpeed().setValue((Number)updatedValue)));
        this.field_146292_n.add(new NewButtonSlider(this.field_146294_l / 2 + 76, 240.0, 70, 15, this.main.getConfigValues().getChromaSize().floatValue(), 1.0f, 100.0f, 1.0f, updatedValue -> this.main.getConfigValues().getChromaSize().setValue((Number)updatedValue)));
    }
}

