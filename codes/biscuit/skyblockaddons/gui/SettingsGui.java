/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraftforge.client.GuiIngameForge
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Language;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.features.discordrpc.DiscordStatus;
import codes.biscuit.skyblockaddons.gui.ColorSelectionGui;
import codes.biscuit.skyblockaddons.gui.EnchantmentSettingsGui;
import codes.biscuit.skyblockaddons.gui.SkyblockAddonsGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonArrow;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonGuiScale;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonInputFieldWrapper;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonLanguage;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonOpenColorMenu;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSelect;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSlider;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSolid;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSwitchTab;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonTextNew;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonToggleTitle;
import codes.biscuit.skyblockaddons.gui.buttons.NewButtonSlider;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DataUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.input.Keyboard;

public class SettingsGui
extends GuiScreen {
    final SkyblockAddons main = SkyblockAddons.getInstance();
    final Feature feature;
    final int lastPage;
    final EnumUtils.GuiTab lastTab;
    final List<EnumUtils.FeatureSetting> settings;
    final long timeOpened = System.currentTimeMillis();
    int page;
    float row = 1.0f;
    int column = 1;
    int displayCount;
    boolean closingGui;
    boolean reInit = false;

    public SettingsGui(Feature feature, int page, int lastPage, EnumUtils.GuiTab lastTab, List<EnumUtils.FeatureSetting> settings) {
        this.feature = feature;
        this.page = page;
        this.lastPage = lastPage;
        this.lastTab = lastTab;
        this.settings = settings;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.row = 1.0f;
        this.column = 1;
        this.field_146292_n.clear();
        if (this.feature == Feature.LANGUAGE) {
            this.displayCount = this.findDisplayCount();
            int skip = (this.page - 1) * this.displayCount;
            boolean max = this.page == 1;
            this.field_146292_n.add(new ButtonArrow(this.field_146294_l / 2 - 15 - 50, this.field_146295_m - 70, this.main, ButtonArrow.ArrowType.LEFT, max));
            max = Language.values().length - skip - this.displayCount <= 0;
            this.field_146292_n.add(new ButtonArrow(this.field_146294_l / 2 - 15 + 50, this.field_146295_m - 70, this.main, ButtonArrow.ArrowType.RIGHT, max));
            for (Language language : Language.values()) {
                if (skip == 0) {
                    if (language == Language.ENGLISH) continue;
                    if (language == Language.CHINESE_TRADITIONAL) {
                        this.addLanguageButton(Language.ENGLISH);
                    }
                    this.addLanguageButton(language);
                    continue;
                }
                --skip;
            }
        } else {
            for (EnumUtils.FeatureSetting setting : this.settings) {
                this.addButton(setting);
            }
        }
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
            width -= x;
            float numSettings = this.settings.size();
            if (this.settings.contains((Object)EnumUtils.FeatureSetting.DISCORD_RP_STATE)) {
                if (this.main.getConfigValues().getDiscordStatus() == DiscordStatus.CUSTOM) {
                    numSettings += 1.0f;
                }
                if (this.main.getConfigValues().getDiscordStatus() == DiscordStatus.AUTO_STATUS) {
                    numSettings += 1.0f;
                    if (this.main.getConfigValues().getDiscordAutoDefault() == DiscordStatus.CUSTOM) {
                        numSettings += 1.0f;
                    }
                }
                numSettings = (float)((double)numSettings + 0.4);
            }
            if (this.settings.contains((Object)EnumUtils.FeatureSetting.DISCORD_RP_DETAILS)) {
                if (this.main.getConfigValues().getDiscordDetails() == DiscordStatus.CUSTOM) {
                    numSettings += 1.0f;
                }
                if (this.main.getConfigValues().getDiscordDetails() == DiscordStatus.AUTO_STATUS) {
                    numSettings += 1.0f;
                    if (this.main.getConfigValues().getDiscordAutoDefault() == DiscordStatus.CUSTOM) {
                        numSettings += 1.0f;
                    }
                }
                numSettings = (float)((double)numSettings + 0.4);
            }
            int height = (int)(this.getRowHeightSetting(numSettings) - 50.0);
            int y = (int)this.getRowHeight(1.0);
            GlStateManager.func_179147_l();
            if (!(this instanceof EnchantmentSettingsGui)) {
                DrawUtils.drawRect((double)x, (double)y, (double)width, (double)height, ColorUtils.getDummySkyblockColor(28, 29, 41, 230), 4);
            }
            SkyblockAddonsGui.drawScaledString(this, Message.SETTING_SETTINGS.getMessage(new String[0]), 110, defaultBlue, 1.5, 0);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

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
                this.field_146297_k.func_147108_a((GuiScreen)new ColorSelectionGui(f, EnumUtils.GUIType.SETTINGS, this.lastTab, this.lastPage));
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
                if (feature == Feature.HIDE_FOOD_ARMOR_BAR) {
                    GuiIngameForge.renderArmor = true;
                } else if (feature == Feature.HIDE_HEALTH_BAR) {
                    GuiIngameForge.renderHealth = true;
                } else if (feature == Feature.REPEAT_FULL_INVENTORY_WARNING) {
                    this.main.getScheduler().removeQueuedFullInventoryWarnings();
                }
            }
        } else if (this.feature == Feature.SHOW_BACKPACK_PREVIEW) {
            this.main.getConfigValues().setBackpackStyle(this.main.getConfigValues().getBackpackStyle().getNextType());
            this.closingGui = true;
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new SettingsGui(this.feature, this.page, this.lastPage, this.lastTab, this.settings));
            this.closingGui = false;
        } else if (this.feature == Feature.POWER_ORB_STATUS_DISPLAY && abstractButton instanceof ButtonSolid) {
            this.main.getConfigValues().setPowerOrbDisplayStyle(this.main.getConfigValues().getPowerOrbDisplayStyle().getNextType());
            this.closingGui = true;
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new SettingsGui(this.feature, this.page, this.lastPage, this.lastTab, this.settings));
            this.closingGui = false;
        } else if (abstractButton instanceof ButtonArrow && (arrow = (ButtonArrow)abstractButton).isNotMax()) {
            this.main.getUtils().setFadingIn(false);
            if (arrow.getArrowType() == ButtonArrow.ArrowType.RIGHT) {
                this.closingGui = true;
                this.field_146297_k.func_147108_a((GuiScreen)new SettingsGui(this.feature, ++this.page, this.lastPage, this.lastTab, this.settings));
            } else {
                this.closingGui = true;
                this.field_146297_k.func_147108_a((GuiScreen)new SettingsGui(this.feature, --this.page, this.lastPage, this.lastTab, this.settings));
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
        } else if (setting == EnumUtils.FeatureSetting.GUI_SCALE) {
            this.field_146292_n.add(new ButtonGuiScale(x, y, 100, 20, this.main, this.feature));
        } else if (setting == EnumUtils.FeatureSetting.GUI_SCALE_X) {
            this.field_146292_n.add(new ButtonGuiScale(x, y, 100, 20, this.main, this.feature, true));
        } else if (setting == EnumUtils.FeatureSetting.GUI_SCALE_Y) {
            this.field_146292_n.add(new ButtonGuiScale(x, y, 100, 20, this.main, this.feature, false));
        } else if (setting == EnumUtils.FeatureSetting.REPEATING) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            Feature settingFeature = null;
            if (this.feature == Feature.FULL_INVENTORY_WARNING) {
                settingFeature = Feature.REPEAT_FULL_INVENTORY_WARNING;
            } else if (this.feature == Feature.BOSS_APPROACH_ALERT) {
                settingFeature = Feature.REPEAT_SLAYER_BOSS_WARNING;
            }
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, Message.SETTING_REPEATING.getMessage(new String[0]), this.main, settingFeature));
        } else if (setting == EnumUtils.FeatureSetting.ENABLED_IN_OTHER_GAMES) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            Feature settingFeature = null;
            if (this.feature == Feature.MAGMA_BOSS_TIMER) {
                settingFeature = Feature.SHOW_MAGMA_TIMER_IN_OTHER_GAMES;
            } else if (this.feature == Feature.DARK_AUCTION_TIMER) {
                settingFeature = Feature.SHOW_DARK_AUCTION_TIMER_IN_OTHER_GAMES;
            } else if (this.feature == Feature.DROP_CONFIRMATION) {
                settingFeature = Feature.DOUBLE_DROP_IN_OTHER_GAMES;
            }
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, Message.SETTING_SHOW_IN_OTHER_GAMES.getMessage(new String[0]), this.main, settingFeature));
        } else if (setting == EnumUtils.FeatureSetting.BACKPACK_STYLE) {
            boxWidth = 140;
            x = halfWidth - boxWidth / 2;
            this.field_146292_n.add(new ButtonSolid(x, y, 140, 20, Message.SETTING_BACKPACK_STYLE.getMessage(new String[0]), this.main, this.feature));
        } else if (setting == EnumUtils.FeatureSetting.ENABLE_MESSAGE_WHEN_ACTION_PREVENTED) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            Feature settingFeature = null;
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, Message.SETTING_ENABLE_MESSAGE_WHEN_ACTION_PREVENTED.getMessage(new String[0]), this.main, settingFeature));
        } else if (setting == EnumUtils.FeatureSetting.POWER_ORB_DISPLAY_STYLE) {
            boxWidth = 140;
            x = halfWidth - boxWidth / 2;
            this.field_146292_n.add(new ButtonSolid(x, y, 140, 20, Message.SETTING_POWER_ORB_DISPLAY_STYLE.getMessage(new String[0]), this.main, this.feature));
        } else if (setting == EnumUtils.FeatureSetting.DISCORD_RP_DETAILS || setting == EnumUtils.FeatureSetting.DISCORD_RP_STATE) {
            boxWidth = 140;
            x = halfWidth - boxWidth / 2;
            DiscordStatus currentStatus = setting == EnumUtils.FeatureSetting.DISCORD_RP_STATE ? this.main.getConfigValues().getDiscordStatus() : this.main.getConfigValues().getDiscordDetails();
            this.field_146292_n.add(new ButtonTextNew(halfWidth, (int)y - 10, setting == EnumUtils.FeatureSetting.DISCORD_RP_DETAILS ? Message.MESSAGE_FIRST_STATUS.getMessage(new String[0]) : Message.MESSAGE_SECOND_STATUS.getMessage(new String[0]), true, -1));
            this.field_146292_n.add(new ButtonSelect(x, (int)y, boxWidth, 20, Arrays.asList(DiscordStatus.values()), currentStatus.ordinal(), index -> {
                DiscordStatus selectedStatus = DiscordStatus.values()[index];
                if (setting == EnumUtils.FeatureSetting.DISCORD_RP_STATE) {
                    this.main.getDiscordRPCManager().setStateLine(selectedStatus);
                    this.main.getConfigValues().setDiscordStatus(selectedStatus);
                } else {
                    this.main.getDiscordRPCManager().setDetailsLine(selectedStatus);
                    this.main.getConfigValues().setDiscordDetails(selectedStatus);
                }
                this.reInit = true;
            }));
            if (currentStatus == DiscordStatus.AUTO_STATUS) {
                this.row += 1.0f;
                this.row = (float)((double)this.row + 0.4);
                boxWidth = 140;
                x = halfWidth - boxWidth / 2;
                y = this.getRowHeightSetting(this.row);
                this.field_146292_n.add(new ButtonTextNew(halfWidth, (int)y - 10, Message.MESSAGE_FALLBACK_STATUS.getMessage(new String[0]), true, -1));
                currentStatus = this.main.getConfigValues().getDiscordAutoDefault();
                this.field_146292_n.add(new ButtonSelect(x, (int)y, boxWidth, 20, Arrays.asList(DiscordStatus.values()), currentStatus.ordinal(), index -> {
                    DiscordStatus selectedStatus = DiscordStatus.values()[index];
                    this.main.getConfigValues().setDiscordAutoDefault(selectedStatus);
                    this.reInit = true;
                }));
            }
            if (currentStatus == DiscordStatus.CUSTOM) {
                this.row += 1.0f;
                halfWidth = this.field_146294_l / 2;
                boxWidth = 200;
                x = halfWidth - boxWidth / 2;
                y = this.getRowHeightSetting(this.row);
                EnumUtils.DiscordStatusEntry discordStatusEntry = EnumUtils.DiscordStatusEntry.DETAILS;
                if (setting == EnumUtils.FeatureSetting.DISCORD_RP_STATE) {
                    discordStatusEntry = EnumUtils.DiscordStatusEntry.STATE;
                }
                EnumUtils.DiscordStatusEntry finalDiscordStatusEntry = discordStatusEntry;
                ButtonInputFieldWrapper inputField = new ButtonInputFieldWrapper(x, (int)y, 200, 20, this.main.getConfigValues().getCustomStatus(discordStatusEntry), null, 100, false, updatedValue -> this.main.getConfigValues().setCustomStatus(finalDiscordStatusEntry, (String)updatedValue));
                this.field_146292_n.add(inputField);
            }
            this.row = (float)((double)this.row + 0.4);
        } else if (setting == EnumUtils.FeatureSetting.MAP_ZOOM) {
            boxWidth = 100;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            this.field_146292_n.add(new ButtonSlider(x, y, 100, 20, this.main.getConfigValues().getMapZoom().getValue().floatValue(), 0.5f, 5.0f, 0.1f, new ButtonSlider.OnSliderChangeCallback(){

                @Override
                public void sliderUpdated(float value) {
                    SettingsGui.this.main.getConfigValues().getMapZoom().setValue(value);
                }
            }).setPrefix("Map Zoom: "));
        } else if (setting == EnumUtils.FeatureSetting.COLOUR_BY_RARITY) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            Feature settingFeature = null;
            if (this.feature == Feature.SHOW_BASE_STAT_BOOST_PERCENTAGE) {
                settingFeature = Feature.BASE_STAT_BOOST_COLOR_BY_RARITY;
            } else if (this.feature == Feature.REVENANT_SLAYER_TRACKER) {
                settingFeature = Feature.REVENANT_COLOR_BY_RARITY;
            } else if (this.feature == Feature.TARANTULA_SLAYER_TRACKER) {
                settingFeature = Feature.TARANTULA_COLOR_BY_RARITY;
            } else if (this.feature == Feature.SVEN_SLAYER_TRACKER) {
                settingFeature = Feature.SVEN_COLOR_BY_RARITY;
            } else if (this.feature == Feature.VOIDGLOOM_SLAYER_TRACKER) {
                settingFeature = Feature.ENDERMAN_COLOR_BY_RARITY;
            } else if (this.feature == Feature.DRAGON_STATS_TRACKER) {
                settingFeature = Feature.DRAGON_STATS_TRACKER_COLOR_BY_RARITY;
            }
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, Message.SETTING_COLOR_BY_RARITY.getMessage(new String[0]), this.main, settingFeature));
        } else if (setting == EnumUtils.FeatureSetting.TEXT_MODE) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            Feature settingFeature = null;
            if (this.feature == Feature.REVENANT_SLAYER_TRACKER) {
                settingFeature = Feature.REVENANT_TEXT_MODE;
            } else if (this.feature == Feature.TARANTULA_SLAYER_TRACKER) {
                settingFeature = Feature.TARANTULA_TEXT_MODE;
            } else if (this.feature == Feature.SVEN_SLAYER_TRACKER) {
                settingFeature = Feature.SVEN_TEXT_MODE;
            } else if (this.feature == Feature.VOIDGLOOM_SLAYER_TRACKER) {
                settingFeature = Feature.ENDERMAN_TEXT_MODE;
            } else if (this.feature == Feature.DRAGON_STATS_TRACKER_TEXT_MODE) {
                settingFeature = Feature.DRAGON_STATS_TRACKER_TEXT_MODE;
            }
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, Message.SETTING_TEXT_MODE.getMessage(new String[0]), this.main, settingFeature));
        } else if (setting == EnumUtils.FeatureSetting.DRAGONS_NEST_ONLY) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            Feature settingFeature = null;
            if (this.feature == Feature.DRAGON_STATS_TRACKER) {
                settingFeature = Feature.DRAGON_STATS_TRACKER_NEST_ONLY;
            } else if (this.feature == Feature.ZEALOT_COUNTER) {
                settingFeature = Feature.ZEALOT_COUNTER_NEST_ONLY;
            } else if (this.feature == Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE) {
                settingFeature = Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE_NEST_ONLY;
            } else if (this.feature == Feature.SHOW_TOTAL_ZEALOT_COUNT) {
                settingFeature = Feature.SHOW_TOTAL_ZEALOT_COUNT_NEST_ONLY;
            } else if (this.feature == Feature.SHOW_SUMMONING_EYE_COUNT) {
                settingFeature = Feature.SHOW_SUMMONING_EYE_COUNT_NEST_ONLY;
            }
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, setting.getMessage(new String[0]), this.main, settingFeature));
        } else if (setting == EnumUtils.FeatureSetting.HEALING_CIRCLE_OPACITY) {
            boxWidth = 150;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            this.field_146292_n.add(new NewButtonSlider(x, y, boxWidth, 20, this.main.getConfigValues().getHealingCircleOpacity().getValue().floatValue(), 0.0f, 1.0f, 0.01f, updatedValue -> this.main.getConfigValues().getHealingCircleOpacity().setValue((Number)updatedValue)).setPrefix("Healing Circle Opacity: "));
        } else if (setting == EnumUtils.FeatureSetting.TREVOR_SHOW_QUEST_COOLDOWN) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, setting.getMessage(new String[0]), this.main, setting.getFeatureEquivalent()));
            this.row = (float)((double)this.row + 0.1);
            y = this.getRowHeightSetting(this.row);
            this.field_146292_n.add(new ButtonTextNew(halfWidth, (int)y + 15, Translations.getMessage("settings.trevorTheTrapper.showQuestCooldownDescription", new Object[0]), true, ColorCode.GRAY.getColor()));
            this.row = (float)((double)this.row + 0.4);
        } else if (setting == EnumUtils.FeatureSetting.TREVOR_HIGHLIGHT_TRACKED_ENTITY && this.feature == Feature.ENTITY_OUTLINES) {
            boxWidth = 31;
            x = halfWidth - boxWidth / 2;
            y = this.getRowHeightSetting(this.row);
            this.field_146292_n.add(new ButtonToggleTitle((double)x, y, setting.getMessage(new String[0]), this.main, setting.getFeatureEquivalent()));
            this.row = (float)((double)this.row + 0.4);
            y = this.getRowHeightSetting(this.row);
            this.field_146292_n.add(new ButtonTextNew(halfWidth, (int)y + 15, Translations.getMessage("messages.entityOutlinesRequirement", new Object[0]), true, ColorCode.GRAY.getColor()));
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

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        ButtonInputFieldWrapper.callKeyTyped(this.field_146292_n, typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        super.func_73876_c();
        ButtonInputFieldWrapper.callUpdateScreen(this.field_146292_n);
    }
}

