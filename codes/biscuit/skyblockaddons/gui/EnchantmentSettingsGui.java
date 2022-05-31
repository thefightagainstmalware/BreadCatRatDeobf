/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.gui;

import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Language;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.features.enchants.EnchantListLayout;
import codes.biscuit.skyblockaddons.gui.ColorSelectionGui;
import codes.biscuit.skyblockaddons.gui.SettingsGui;
import codes.biscuit.skyblockaddons.gui.SkyblockAddonsGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonArrow;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonInputFieldWrapper;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonLanguage;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonOpenColorMenu;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSelect;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSwitchTab;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonTextNew;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonToggleTitle;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DataUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

public class EnchantmentSettingsGui
extends SettingsGui {
    private final List<EnumUtils.FeatureSetting> ENCHANT_COLORING = Arrays.asList(EnumUtils.FeatureSetting.HIGHLIGHT_ENCHANTMENTS, EnumUtils.FeatureSetting.PERFECT_ENCHANT_COLOR, EnumUtils.FeatureSetting.GREAT_ENCHANT_COLOR, EnumUtils.FeatureSetting.GOOD_ENCHANT_COLOR, EnumUtils.FeatureSetting.POOR_ENCHANT_COLOR, EnumUtils.FeatureSetting.COMMA_ENCHANT_COLOR);
    private final List<EnumUtils.FeatureSetting> ORGANIZATION = Arrays.asList(EnumUtils.FeatureSetting.ENCHANT_LAYOUT, EnumUtils.FeatureSetting.HIDE_ENCHANTMENT_LORE, EnumUtils.FeatureSetting.HIDE_GREY_ENCHANTS);
    private int maxPage = 1;

    public EnchantmentSettingsGui(Feature feature, int page, int lastPage, EnumUtils.GuiTab lastTab, List<EnumUtils.FeatureSetting> settings) {
        super(feature, page, lastPage, lastTab, settings);
        for (EnumUtils.FeatureSetting setting : settings) {
            if (this.ENCHANT_COLORING.contains((Object)setting) || this.ORGANIZATION.contains((Object)setting)) continue;
            this.maxPage = 2;
            break;
        }
    }

    @Override
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.row = 1.0f;
        this.column = 1;
        this.field_146292_n.clear();
        for (EnumUtils.FeatureSetting setting : this.settings) {
            if (this.page == 0 && this.ORGANIZATION.contains((Object)setting)) {
                this.addButton(setting);
            }
            if (this.page == 1) {
                if (!this.ENCHANT_COLORING.contains((Object)setting)) continue;
                this.addButton(setting);
                continue;
            }
            if (this.page != 2 || this.ENCHANT_COLORING.contains((Object)setting) || this.ORGANIZATION.contains((Object)setting)) continue;
            this.addButton(setting);
        }
        this.field_146292_n.add(new ButtonArrow(this.field_146294_l / 2 - 15 - 150, this.field_146295_m - 70, this.main, ButtonArrow.ArrowType.LEFT, this.page == 0));
        this.field_146292_n.add(new ButtonArrow(this.field_146294_l / 2 - 15 + 150, this.field_146295_m - 70, this.main, ButtonArrow.ArrowType.RIGHT, this.page == this.maxPage));
    }

    private int findDisplayCount() {
        int maxX = new ScaledResolution(this.field_146297_k).func_78328_b() - 70 - 25;
        int displayCount = 0;
        for (int row = 1; row < 99; ++row) {
            if (this.getRowHeight(row) < (double)maxX) {
                displayCount += 3;
                continue;
            }
            return displayCount;
        }
        return displayCount;
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int fadeMilis;
        if (this.reInit) {
            this.reInit = false;
            this.func_73866_w_();
        }
        long timeSinceOpen = System.currentTimeMillis() - this.timeOpened;
        float alphaMultiplier = 0.5f;
        if (this.main.getUtils().isFadingIn() && timeSinceOpen <= (long)(fadeMilis = 500)) {
            alphaMultiplier = (float)timeSinceOpen / (float)(fadeMilis * 2);
        }
        int alpha = (int)(255.0f * alphaMultiplier);
        int startColor = new Color(0, 0, 0, (int)((double)alpha * 0.5)).getRGB();
        int endColor = new Color(0, 0, 0, alpha).getRGB();
        this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, startColor, endColor);
        GlStateManager.func_179147_l();
        if (alpha < 4) {
            alpha = 4;
        }
        int defaultBlue = this.main.getUtils().getDefaultBlue(alpha * 2);
        SkyblockAddonsGui.drawDefaultTitleText(this, alpha * 2);
        if (this.feature != Feature.LANGUAGE) {
            int halfWidth = this.field_146294_l / 2;
            int boxWidth = 140;
            int x = halfWidth - 90 - boxWidth;
            int width = halfWidth + 90 + boxWidth;
            float numSettings = this.page == 0 ? (float)this.ORGANIZATION.size() : (this.page == 1 ? (float)this.ENCHANT_COLORING.size() : (float)Math.max(this.settings.size() - this.ORGANIZATION.size() - this.ENCHANT_COLORING.size(), 1));
            int height = (int)(this.getRowHeightSetting(numSettings) - 50.0);
            int y = (int)this.getRowHeight(1.0);
            GlStateManager.func_179147_l();
            DrawUtils.drawRect((double)x, (double)y, (double)(width -= x), (double)height, ColorUtils.getDummySkyblockColor(28, 29, 41, 230), 4);
            SkyblockAddonsGui.drawScaledString(this, Message.SETTING_SETTINGS.getMessage(new String[0]), 110, defaultBlue, 1.5, 0);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void func_146284_a(GuiButton abstractButton) {
        ButtonArrow arrow;
        if (abstractButton instanceof ButtonLanguage) {
            Language language = ((ButtonLanguage)abstractButton).getLanguage();
            this.main.getConfigValues().setLanguage(language);
            DataUtils.loadLocalizedStrings(true);
            this.main.setKeyBindingDescriptions();
            this.returnToGui();
        } else if (abstractButton instanceof ButtonSwitchTab) {
            ButtonSwitchTab tab = (ButtonSwitchTab)abstractButton;
            this.field_146297_k.func_147108_a((GuiScreen)new SkyblockAddonsGui(1, tab.getTab()));
        } else if (abstractButton instanceof ButtonOpenColorMenu) {
            this.closingGui = true;
            Feature f = ((ButtonOpenColorMenu)abstractButton).feature;
            if (f == Feature.ENCHANTMENT_PERFECT_COLOR || f == Feature.ENCHANTMENT_GREAT_COLOR || f == Feature.ENCHANTMENT_GOOD_COLOR || f == Feature.ENCHANTMENT_POOR_COLOR || f == Feature.ENCHANTMENT_COMMA_COLOR) {
                this.field_146297_k.func_147108_a((GuiScreen)new ColorSelectionGui(f, EnumUtils.GUIType.SETTINGS, this.lastTab, this.page));
            } else {
                this.field_146297_k.func_147108_a((GuiScreen)new ColorSelectionGui(this.feature, EnumUtils.GUIType.SETTINGS, this.lastTab, this.lastPage));
            }
        } else if (abstractButton instanceof ButtonToggleTitle) {
            ButtonFeature button = (ButtonFeature)abstractButton;
            Feature feature = button.getFeature();
            if (feature == null) {
                return;
            }
            if (this.main.getConfigValues().isDisabled(feature)) {
                this.main.getConfigValues().getDisabledFeatures().remove((Object)feature);
            } else {
                this.main.getConfigValues().getDisabledFeatures().add(feature);
            }
        } else if (abstractButton instanceof ButtonArrow && (arrow = (ButtonArrow)abstractButton).isNotMax()) {
            this.main.getUtils().setFadingIn(false);
            if (arrow.getArrowType() == ButtonArrow.ArrowType.RIGHT) {
                this.closingGui = true;
                this.field_146297_k.func_147108_a((GuiScreen)new EnchantmentSettingsGui(this.feature, ++this.page, this.lastPage, this.lastTab, this.settings));
            } else {
                this.closingGui = true;
                this.field_146297_k.func_147108_a((GuiScreen)new EnchantmentSettingsGui(this.feature, --this.page, this.lastPage, this.lastTab, this.settings));
            }
        }
    }

    private void addLanguageButton(Language language) {
        if (this.displayCount == 0) {
            return;
        }
        String text = this.feature.getMessage(new String[0]);
        int halfWidth = this.field_146294_l / 2;
        int boxWidth = 140;
        int x = 0;
        if (this.column == 1) {
            x = halfWidth - 90 - boxWidth;
        } else if (this.column == 2) {
            x = halfWidth - boxWidth / 2;
        } else if (this.column == 3) {
            x = halfWidth + 90;
        }
        double y = this.getRowHeight(this.row);
        this.field_146292_n.add(new ButtonLanguage(x, y, text, this.main, language));
        ++this.column;
        if (this.column > 3) {
            this.column = 1;
            this.row += 1.0f;
        }
        --this.displayCount;
    }

    private void addButton(EnumUtils.FeatureSetting setting) {
        int halfWidth = this.field_146294_l / 2;
        int boxWidth = 100;
        int x = halfWidth - boxWidth / 2;
        double y = this.getRowHeightSetting(this.row);
        if (setting == EnumUtils.FeatureSetting.COLOR) {
            this.field_146292_n.add(new ButtonOpenColorMenu(x, y, 100, 20, Message.SETTING_CHANGE_COLOR.getMessage(new String[0]), this.main, this.feature));
        } else if (setting == EnumUtils.FeatureSetting.PERFECT_ENCHANT_COLOR || setting == EnumUtils.FeatureSetting.GREAT_ENCHANT_COLOR || setting == EnumUtils.FeatureSetting.GOOD_ENCHANT_COLOR || setting == EnumUtils.FeatureSetting.POOR_ENCHANT_COLOR || setting == EnumUtils.FeatureSetting.COMMA_ENCHANT_COLOR) {
            this.field_146292_n.add(new ButtonOpenColorMenu(x, y, 100, 20, setting.getMessage(new String[0]), this.main, setting.getFeatureEquivalent()));
        } else if (setting == EnumUtils.FeatureSetting.ENCHANT_LAYOUT) {
            boxWidth = 140;
            x = halfWidth - boxWidth / 2;
            EnchantListLayout currentStatus = this.main.getConfigValues().getEnchantLayout();
            this.field_146292_n.add(new ButtonTextNew(halfWidth, (int)y - 10, Translations.getMessage("enchantLayout.title", new Object[0]), true, -1));
            this.field_146292_n.add(new ButtonSelect(x, (int)y, boxWidth, 20, Arrays.asList(EnchantListLayout.values()), currentStatus.ordinal(), index -> {
                EnchantListLayout enchantLayout = EnchantListLayout.values()[index];
                this.main.getConfigValues().setEnchantLayout(enchantLayout);
                this.reInit = true;
            }));
            this.row = (float)((double)this.row + 0.4);
        } else {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, setting.getMessage(new String[0]), this.main, setting.getFeatureEquivalent()));
        }
        this.row += 1.0f;
    }

    private double getRowHeight(double row) {
        return 95.0 + (row -= 1.0) * 30.0;
    }

    private double getRowHeightSetting(double row) {
        return 140.0 + (row -= 1.0) * 35.0;
    }

    @Override
    public void func_146281_b() {
        if (!this.closingGui) {
            this.returnToGui();
        }
        Keyboard.enableRepeatEvents((boolean)false);
    }

    private void returnToGui() {
        this.closingGui = true;
        this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.MAIN, this.lastPage, this.lastTab);
    }

    @Override
    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        ButtonInputFieldWrapper.callKeyTyped(this.field_146292_n, typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    @Override
    public void func_73876_c() {
        super.func_73876_c();
        ButtonInputFieldWrapper.callUpdateScreen(this.field_146292_n);
    }
}

