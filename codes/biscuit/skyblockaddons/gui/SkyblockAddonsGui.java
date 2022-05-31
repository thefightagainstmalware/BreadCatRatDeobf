/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.GuiIngameForge
 *  org.apache.commons.lang3.StringUtils
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.gui.EnchantmentSettingsGui;
import codes.biscuit.skyblockaddons.gui.LocationEditGui;
import codes.biscuit.skyblockaddons.gui.SettingsGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonArrow;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonBanner;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonCredit;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonModify;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonNewTag;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonNormal;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSettings;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSocial;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSolid;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSwitchTab;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonToggle;
import codes.biscuit.skyblockaddons.gui.buttons.NewButtonSlider;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.objects.IntPair;
import com.google.common.collect.Sets;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.TreeSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class SkyblockAddonsGui
extends GuiScreen {
    public static final ResourceLocation LOGO = new ResourceLocation("skyblockaddons", "logo.png");
    public static final ResourceLocation LOGO_GLOW = new ResourceLocation("skyblockaddons", "logoglow.png");
    public static final int BUTTON_MAX_WIDTH = 140;
    private static String searchString;
    private GuiTextField featureSearchBar;
    private final EnumUtils.GuiTab tab;
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private int page;
    private int row = 1;
    private int collumn = 1;
    private int displayCount;
    private final long timeOpened = System.currentTimeMillis();
    private boolean cancelClose;

    public SkyblockAddonsGui(int page, EnumUtils.GuiTab tab) {
        this.tab = tab;
        this.page = page;
    }

    public void func_73866_w_() {
        this.row = 1;
        this.collumn = 1;
        this.displayCount = this.findDisplayCount();
        this.addLanguageButton();
        this.addEditLocationsButton();
        this.addFeaturedBanner();
        this.addGeneralSettingsButton();
        if (this.featureSearchBar == null) {
            this.featureSearchBar = new GuiTextField(2, this.field_146289_q, this.field_146294_l / 2 - 220, 69, 120, 15);
            this.featureSearchBar.func_146203_f(500);
            this.featureSearchBar.func_146195_b(true);
            if (searchString != null) {
                this.featureSearchBar.func_146180_a(searchString);
            }
        } else {
            this.featureSearchBar.field_146209_f = this.field_146294_l / 2 - 220;
        }
        TreeSet<Feature> features = new TreeSet<Feature>(Comparator.comparing(rec$ -> ((Feature)((Object)((Object)rec$))).getMessage(new String[0])));
        for (Feature feature : this.tab != EnumUtils.GuiTab.GENERAL_SETTINGS ? Sets.newHashSet((Object[])Feature.values()) : Feature.getGeneralTabFeatures()) {
            if (!feature.isActualFeature() && this.tab != EnumUtils.GuiTab.GENERAL_SETTINGS || this.main.getConfigValues().isRemoteDisabled(feature)) continue;
            if (this.matchesSearch(feature.getMessage(new String[0]))) {
                features.add(feature);
                continue;
            }
            for (EnumUtils.FeatureSetting setting : feature.getSettings()) {
                try {
                    if (!this.matchesSearch(setting.getMessage(new String[0]))) continue;
                    features.add(feature);
                }
                catch (Exception exception) {}
            }
        }
        if (this.tab != EnumUtils.GuiTab.GENERAL_SETTINGS) {
            for (Feature feature : Feature.values()) {
                if (!this.main.getConfigValues().isRemoteDisabled(feature) || !this.matchesSearch(feature.getMessage(new String[0]))) continue;
                features.add(feature);
            }
        }
        int skip = (this.page - 1) * this.displayCount;
        boolean max = this.page == 1;
        this.field_146292_n.add(new ButtonArrow(this.field_146294_l / 2 - 15 - 50, this.field_146295_m - 70, this.main, ButtonArrow.ArrowType.LEFT, max));
        max = features.size() - skip - this.displayCount <= 0;
        this.field_146292_n.add(new ButtonArrow(this.field_146294_l / 2 - 15 + 50, this.field_146295_m - 70, this.main, ButtonArrow.ArrowType.RIGHT, max));
        this.field_146292_n.add(new ButtonSocial(this.field_146294_l / 2 + 200, 30.0, this.main, EnumUtils.Social.YOUTUBE));
        this.field_146292_n.add(new ButtonSocial(this.field_146294_l / 2 + 175, 30.0, this.main, EnumUtils.Social.DISCORD));
        this.field_146292_n.add(new ButtonSocial(this.field_146294_l / 2 + 150, 30.0, this.main, EnumUtils.Social.GITHUB));
        this.field_146292_n.add(new ButtonSocial(this.field_146294_l / 2 + 125, 30.0, this.main, EnumUtils.Social.PATREON));
        for (Feature feature : features) {
            if (skip == 0) {
                if (feature == Feature.TEXT_STYLE || feature == Feature.WARNING_TIME || feature == Feature.CHROMA_MODE || feature == Feature.TURN_ALL_FEATURES_CHROMA) {
                    this.addButton(feature, EnumUtils.ButtonType.SOLID);
                    continue;
                }
                if (feature == Feature.CHROMA_SPEED || feature == Feature.CHROMA_SIZE || feature == Feature.CHROMA_SATURATION || feature == Feature.CHROMA_BRIGHTNESS) {
                    this.addButton(feature, EnumUtils.ButtonType.CHROMA_SLIDER);
                    continue;
                }
                this.addButton(feature, EnumUtils.ButtonType.TOGGLE);
                continue;
            }
            --skip;
        }
        Keyboard.enableRepeatEvents((boolean)true);
    }

    private boolean matchesSearch(String textToSearch) {
        String searchBarText = this.featureSearchBar.func_146179_b();
        if (searchBarText == null || searchBarText.isEmpty()) {
            return true;
        }
        String[] searchTerms = searchBarText.toLowerCase().split(" ");
        textToSearch = textToSearch.toLowerCase();
        for (String searchTerm : searchTerms) {
            if (textToSearch.contains(searchTerm)) continue;
            return false;
        }
        return true;
    }

    private int findDisplayCount() {
        int maxX = new ScaledResolution(this.field_146297_k).func_78328_b() - 70 - 50;
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
        SkyblockAddonsGui.drawDefaultTitleText(this, alpha * 2);
        this.featureSearchBar.func_146194_f();
        if (StringUtils.isEmpty((CharSequence)this.featureSearchBar.func_146179_b())) {
            Minecraft.func_71410_x().field_71466_p.func_78276_b(Message.MESSAGE_SEARCH_FEATURES.getMessage(new String[0]), this.featureSearchBar.field_146209_f + 4, this.featureSearchBar.field_146210_g + 3, ColorCode.DARK_GRAY.getColor());
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_146284_a(GuiButton abstractButton) {
        block34: {
            block45: {
                block44: {
                    block43: {
                        block32: {
                            Feature feature;
                            block41: {
                                block42: {
                                    block38: {
                                        block40: {
                                            block39: {
                                                block37: {
                                                    block36: {
                                                        block35: {
                                                            block33: {
                                                                if (!(abstractButton instanceof ButtonFeature)) break block32;
                                                                feature = ((ButtonFeature)abstractButton).getFeature();
                                                                if (abstractButton instanceof ButtonSettings) {
                                                                    this.main.getUtils().setFadingIn(false);
                                                                    if (((ButtonSettings)abstractButton).feature == Feature.ENCHANTMENT_LORE_PARSING) {
                                                                        Minecraft.func_71410_x().func_147108_a((GuiScreen)new EnchantmentSettingsGui(feature, 0, this.page, this.tab, feature.getSettings()));
                                                                    } else {
                                                                        Minecraft.func_71410_x().func_147108_a((GuiScreen)new SettingsGui(feature, 1, this.page, this.tab, feature.getSettings()));
                                                                    }
                                                                    return;
                                                                }
                                                                if (feature != Feature.LANGUAGE) break block33;
                                                                this.main.getUtils().setFadingIn(false);
                                                                Minecraft.func_71410_x().func_147108_a((GuiScreen)new SettingsGui(Feature.LANGUAGE, 1, this.page, this.tab, null));
                                                                break block34;
                                                            }
                                                            if (feature != Feature.EDIT_LOCATIONS) break block35;
                                                            this.main.getUtils().setFadingIn(false);
                                                            Minecraft.func_71410_x().func_147108_a((GuiScreen)new LocationEditGui(this.page, this.tab));
                                                            break block34;
                                                        }
                                                        if (feature != Feature.GENERAL_SETTINGS) break block36;
                                                        if (this.tab == EnumUtils.GuiTab.GENERAL_SETTINGS) {
                                                            this.main.getUtils().setFadingIn(false);
                                                            Minecraft.func_71410_x().func_147108_a((GuiScreen)new SkyblockAddonsGui(1, EnumUtils.GuiTab.MAIN));
                                                        } else {
                                                            this.main.getUtils().setFadingIn(false);
                                                            Minecraft.func_71410_x().func_147108_a((GuiScreen)new SkyblockAddonsGui(1, EnumUtils.GuiTab.GENERAL_SETTINGS));
                                                        }
                                                        break block34;
                                                    }
                                                    if (!(abstractButton instanceof ButtonToggle)) break block37;
                                                    if (this.main.getConfigValues().isRemoteDisabled(feature)) {
                                                        return;
                                                    }
                                                    if (this.main.getConfigValues().isDisabled(feature)) {
                                                        this.main.getConfigValues().getDisabledFeatures().remove((Object)feature);
                                                        if (feature == Feature.DISCORD_RPC && this.main.getUtils().isOnSkyblock()) {
                                                            this.main.getDiscordRPCManager().start();
                                                        } else if (feature == Feature.ZEALOT_COUNTER_EXPLOSIVE_BOW_SUPPORT) {
                                                            this.main.getConfigValues().getDisabledFeatures().remove((Object)Feature.DISABLE_ENDERMAN_TELEPORTATION_EFFECT);
                                                        }
                                                    } else {
                                                        this.main.getConfigValues().getDisabledFeatures().add(feature);
                                                        if (feature == Feature.HIDE_FOOD_ARMOR_BAR) {
                                                            GuiIngameForge.renderArmor = true;
                                                        } else if (feature == Feature.HIDE_HEALTH_BAR) {
                                                            GuiIngameForge.renderHealth = true;
                                                        } else if (feature == Feature.FULL_INVENTORY_WARNING) {
                                                            this.main.getInventoryUtils().setInventoryWarningShown(false);
                                                            this.main.getScheduler().removeQueuedFullInventoryWarnings();
                                                        } else if (feature == Feature.DISCORD_RPC) {
                                                            this.main.getDiscordRPCManager().stop();
                                                        } else if (feature == Feature.DISABLE_ENDERMAN_TELEPORTATION_EFFECT) {
                                                            this.main.getConfigValues().getDisabledFeatures().remove((Object)Feature.ZEALOT_COUNTER_EXPLOSIVE_BOW_SUPPORT);
                                                        }
                                                    }
                                                    ((ButtonToggle)abstractButton).onClick();
                                                    break block34;
                                                }
                                                if (!(abstractButton instanceof ButtonSolid)) break block38;
                                                if (feature != Feature.TEXT_STYLE) break block39;
                                                this.main.getConfigValues().setTextStyle(this.main.getConfigValues().getTextStyle().getNextType());
                                                this.cancelClose = true;
                                                Minecraft.func_71410_x().func_147108_a((GuiScreen)new SkyblockAddonsGui(this.page, this.tab));
                                                this.cancelClose = false;
                                                break block34;
                                            }
                                            if (feature != Feature.CHROMA_MODE) break block40;
                                            this.main.getConfigValues().setChromaMode(this.main.getConfigValues().getChromaMode().getNextType());
                                            this.cancelClose = true;
                                            Minecraft.func_71410_x().func_147108_a((GuiScreen)new SkyblockAddonsGui(this.page, this.tab));
                                            this.cancelClose = false;
                                            break block34;
                                        }
                                        if (feature != Feature.TURN_ALL_FEATURES_CHROMA) break block34;
                                        boolean enable = false;
                                        for (Feature loopFeature : Feature.values()) {
                                            if (loopFeature.getGuiFeatureData() == null || loopFeature.getGuiFeatureData().getDefaultColor() == null || this.main.getConfigValues().getChromaFeatures().contains((Object)loopFeature)) continue;
                                            enable = true;
                                            break;
                                        }
                                        for (Feature loopFeature : Feature.values()) {
                                            if (loopFeature.getGuiFeatureData() == null || loopFeature.getGuiFeatureData().getDefaultColor() == null) continue;
                                            if (enable) {
                                                this.main.getConfigValues().getChromaFeatures().add(loopFeature);
                                                continue;
                                            }
                                            this.main.getConfigValues().getChromaFeatures().remove((Object)loopFeature);
                                        }
                                        break block34;
                                    }
                                    if (!(abstractButton instanceof ButtonModify)) break block41;
                                    if (feature != Feature.ADD) break block42;
                                    if (this.main.getConfigValues().getWarningSeconds() >= 99) break block34;
                                    this.main.getConfigValues().setWarningSeconds(this.main.getConfigValues().getWarningSeconds() + 1);
                                    break block34;
                                }
                                if (this.main.getConfigValues().getWarningSeconds() <= 1) break block34;
                                this.main.getConfigValues().setWarningSeconds(this.main.getConfigValues().getWarningSeconds() - 1);
                                break block34;
                            }
                            if (!(abstractButton instanceof ButtonCredit)) break block34;
                            if (this.main.getConfigValues().isRemoteDisabled(feature)) {
                                return;
                            }
                            EnumUtils.FeatureCredit credit = ((ButtonCredit)abstractButton).getCredit();
                            try {
                                Desktop.getDesktop().browse(new URI(credit.getUrl()));
                            }
                            catch (Exception exception) {}
                            break block34;
                        }
                        if (!(abstractButton instanceof ButtonArrow)) break block43;
                        ButtonArrow arrow = (ButtonArrow)abstractButton;
                        if (!arrow.isNotMax()) break block34;
                        this.main.getUtils().setFadingIn(false);
                        if (arrow.getArrowType() == ButtonArrow.ArrowType.RIGHT) {
                            this.field_146297_k.func_147108_a((GuiScreen)new SkyblockAddonsGui(++this.page, this.tab));
                        } else {
                            this.field_146297_k.func_147108_a((GuiScreen)new SkyblockAddonsGui(--this.page, this.tab));
                        }
                        break block34;
                    }
                    if (!(abstractButton instanceof ButtonSwitchTab)) break block44;
                    ButtonSwitchTab tab = (ButtonSwitchTab)abstractButton;
                    if (tab.getTab() == this.tab) break block34;
                    this.main.getUtils().setFadingIn(false);
                    this.field_146297_k.func_147108_a((GuiScreen)new SkyblockAddonsGui(1, tab.getTab()));
                    break block34;
                }
                if (!(abstractButton instanceof ButtonSocial)) break block45;
                EnumUtils.Social social = ((ButtonSocial)abstractButton).getSocial();
                try {
                    Desktop.getDesktop().browse(social.getUrl());
                }
                catch (Exception exception) {}
                break block34;
            }
            if (!(abstractButton instanceof ButtonBanner)) break block34;
            try {
                Desktop.getDesktop().browse(new URI(this.main.getOnlineData().getBannerLink()));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    static void drawDefaultTitleText(GuiScreen gui, int alpha) {
        int defaultBlue = SkyblockAddons.getInstance().getUtils().getDefaultBlue(alpha);
        int height = 85;
        int width = height * 2;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
        SkyblockAddons.getInstance().getUtils().enableStandardGLOptions();
        textureManager.func_110577_a(LOGO);
        DrawUtils.drawModalRectWithCustomSizedTexture((float)scaledResolution.func_78326_a() / 2.0f - (float)width / 2.0f, 5.0f, 0.0f, 0.0f, width, height, width, height, true);
        int animationMillis = 4000;
        float glowAlpha = System.currentTimeMillis() % (long)animationMillis;
        glowAlpha = glowAlpha > (float)animationMillis / 2.0f ? ((float)animationMillis - glowAlpha) / ((float)animationMillis / 2.0f) : (glowAlpha /= (float)animationMillis / 2.0f);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)glowAlpha);
        textureManager.func_110577_a(LOGO_GLOW);
        DrawUtils.drawModalRectWithCustomSizedTexture((float)scaledResolution.func_78326_a() / 2.0f - (float)width / 2.0f, 5.0f, 0.0f, 0.0f, width, height, width, height, true);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        String version = "v" + SkyblockAddons.VERSION.replace("beta", "b") + " by Biscut";
        SkyblockAddonsGui.drawScaledString(gui, version, 55, defaultBlue, 1.3, 170 - Minecraft.func_71410_x().field_71466_p.func_78256_a(version), false);
        if (gui instanceof SkyblockAddonsGui) {
            SkyblockAddonsGui.drawScaledString(gui, "Special Credits: InventiveTalent - Magma Boss Timer API", gui.field_146295_m - 22, defaultBlue, 1.0, 0);
        }
        SkyblockAddons.getInstance().getUtils().restoreGLOptions();
    }

    static void drawScaledString(GuiScreen guiScreen, String text, int y, int color, double scale, int xOffset) {
        SkyblockAddonsGui.drawScaledString(guiScreen, text, y, color, scale, xOffset, true);
    }

    static void drawScaledString(GuiScreen guiScreen, String text, int y, int color, double scale, int xOffset, boolean centered) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179139_a((double)scale, (double)scale, (double)1.0);
        if (centered) {
            DrawUtils.drawCenteredText(text, Math.round((double)((float)guiScreen.field_146294_l / 2.0f) / scale) + (long)xOffset, Math.round((double)y / scale), color);
        } else {
            Minecraft.func_71410_x().field_71466_p.func_175065_a(text, (float)(Math.round((double)((float)guiScreen.field_146294_l / 2.0f) / scale) + (long)xOffset), (float)Math.round((double)y / scale), color, true);
        }
        GlStateManager.func_179121_F();
    }

    private void addButton(Feature feature, EnumUtils.ButtonType buttonType) {
        if (this.displayCount == 0) {
            return;
        }
        String text = feature.getMessage(new String[0]);
        int halfWidth = this.field_146294_l / 2;
        int boxWidth = 140;
        int boxHeight = 50;
        int x = 0;
        if (this.collumn == 1) {
            x = halfWidth - 90 - boxWidth;
        } else if (this.collumn == 2) {
            x = halfWidth - boxWidth / 2;
        } else if (this.collumn == 3) {
            x = halfWidth + 90;
        }
        double y = this.getRowHeight(this.row);
        if (buttonType == EnumUtils.ButtonType.TOGGLE) {
            ButtonNormal button = new ButtonNormal((double)x, y, text, this.main, feature);
            this.field_146292_n.add(button);
            EnumUtils.FeatureCredit credit = EnumUtils.FeatureCredit.fromFeature(feature);
            if (credit != null) {
                IntPair coords = button.getCreditsCoords(credit);
                this.field_146292_n.add(new ButtonCredit(coords.getX(), coords.getY(), text, credit, feature, button.isMultilineButton()));
            }
            if (feature.getSettings().size() > 0) {
                this.field_146292_n.add(new ButtonSettings((double)(x + boxWidth - 33), y + (double)boxHeight - 20.0, text, this.main, feature));
            }
            this.field_146292_n.add(new ButtonToggle(x + 40, y + (double)boxHeight - 18.0, this.main, feature));
        } else if (buttonType == EnumUtils.ButtonType.SOLID) {
            this.field_146292_n.add(new ButtonNormal((double)x, y, text, this.main, feature));
            if (feature == Feature.TEXT_STYLE || feature == Feature.CHROMA_MODE || feature == Feature.TURN_ALL_FEATURES_CHROMA) {
                this.field_146292_n.add(new ButtonSolid(x + 10, y + (double)boxHeight - 23.0, 120, 15, "", this.main, feature));
            } else if (feature == Feature.WARNING_TIME) {
                int solidButtonX = x + boxWidth / 2 - 17;
                this.field_146292_n.add(new ButtonModify(solidButtonX - 20, y + (double)boxHeight - 23.0, 15, 15, "+", this.main, Feature.ADD));
                this.field_146292_n.add(new ButtonSolid(solidButtonX, y + (double)boxHeight - 23.0, 35, 15, "", this.main, feature));
                this.field_146292_n.add(new ButtonModify(solidButtonX + 35 + 5, y + (double)boxHeight - 23.0, 15, 15, "-", this.main, Feature.SUBTRACT));
            }
        } else if (buttonType == EnumUtils.ButtonType.CHROMA_SLIDER) {
            this.field_146292_n.add(new ButtonNormal((double)x, y, text, this.main, feature));
            if (feature == Feature.CHROMA_SPEED) {
                this.field_146292_n.add(new NewButtonSlider(x + 35, y + (double)boxHeight - 23.0, 70, 15, this.main.getConfigValues().getChromaSpeed().floatValue(), 0.5f, 20.0f, 0.5f, value -> this.main.getConfigValues().getChromaSpeed().setValue((Number)value)));
            } else if (feature == Feature.CHROMA_SIZE) {
                this.field_146292_n.add(new NewButtonSlider(x + 35, y + (double)boxHeight - 23.0, 70, 15, this.main.getConfigValues().getChromaSize().floatValue(), 1.0f, 100.0f, 1.0f, value -> this.main.getConfigValues().getChromaSize().setValue((Number)value)));
            } else if (feature == Feature.CHROMA_BRIGHTNESS) {
                this.field_146292_n.add(new NewButtonSlider(x + 35, y + (double)boxHeight - 23.0, 70, 15, this.main.getConfigValues().getChromaBrightness().floatValue(), 0.0f, 1.0f, 0.01f, value -> this.main.getConfigValues().getChromaBrightness().setValue((Number)value)));
            } else if (feature == Feature.CHROMA_SATURATION) {
                this.field_146292_n.add(new NewButtonSlider(x + 35, y + (double)boxHeight - 23.0, 70, 15, this.main.getConfigValues().getChromaSaturation().floatValue(), 0.0f, 1.0f, 0.01f, value -> this.main.getConfigValues().getChromaSaturation().setValue((Number)value)));
            }
        }
        if (feature.isNew()) {
            this.field_146292_n.add(new ButtonNewTag(x + boxWidth - 15, (int)y + boxHeight - 10));
        }
        ++this.collumn;
        if (this.collumn > 3) {
            this.collumn = 1;
            ++this.row;
        }
        --this.displayCount;
    }

    private void addLanguageButton() {
        int halfWidth = this.field_146294_l / 2;
        int boxWidth = 140;
        int boxHeight = 50;
        int x = halfWidth + 90;
        double y = this.getRowHeight(this.displayCount / 3 + 1);
        this.field_146292_n.add(new ButtonNormal(x, y, boxWidth, boxHeight, Translations.getMessage("languageText", new Object[0]) + Feature.LANGUAGE.getMessage(new String[0]), this.main, Feature.LANGUAGE));
    }

    private void addEditLocationsButton() {
        int halfWidth = this.field_146294_l / 2;
        int boxWidth = 140;
        int boxHeight = 50;
        int x = halfWidth - 90 - boxWidth;
        double y = this.getRowHeight(this.displayCount / 3 + 1);
        this.field_146292_n.add(new ButtonNormal(x, y, boxWidth, boxHeight, Feature.EDIT_LOCATIONS.getMessage(new String[0]), this.main, Feature.EDIT_LOCATIONS));
    }

    private void addGeneralSettingsButton() {
        int halfWidth = this.field_146294_l / 2;
        int boxWidth = 140;
        int boxHeight = 15;
        int x = halfWidth + 90;
        double y = this.getRowHeight(1.0) - 25.0;
        this.field_146292_n.add(new ButtonNormal(x, y, boxWidth, boxHeight, Message.TAB_GENERAL_SETTINGS.getMessage(new String[0]), this.main, Feature.GENERAL_SETTINGS));
    }

    private void addFeaturedBanner() {
        if (this.main.getOnlineData().getBannerImageURL() != null) {
            int halfWidth = this.field_146294_l / 2;
            this.field_146292_n.add(new ButtonBanner(halfWidth - 170, 15.0));
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        if (this.featureSearchBar.func_146206_l()) {
            this.featureSearchBar.func_146201_a(typedChar, keyCode);
            searchString = this.featureSearchBar.func_146179_b();
            this.main.getUtils().setFadingIn(false);
            this.field_146292_n.clear();
            this.page = 1;
            this.func_73866_w_();
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.featureSearchBar.func_146192_a(mouseX, mouseY, mouseButton);
    }

    private double getRowHeight(double row) {
        return 95.0 + (row -= 1.0) * 60.0;
    }

    public void func_146281_b() {
        if (!this.cancelClose) {
            if (this.tab == EnumUtils.GuiTab.GENERAL_SETTINGS) {
                this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.MAIN, 1, EnumUtils.GuiTab.MAIN);
            }
            this.main.getConfigValues().saveConfig();
            Keyboard.enableRepeatEvents((boolean)false);
        }
    }

    public void func_73876_c() {
        super.func_73876_c();
        this.featureSearchBar.func_146178_a();
    }

    public void func_175273_b(Minecraft mcIn, int w, int h) {
        super.func_175273_b(mcIn, w, h);
        this.main.getUtils().setFadingIn(false);
    }
}

