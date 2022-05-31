/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector3d
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityCaveSpider
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.monster.EntityZombie
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  net.minecraftforge.client.GuiIngameForge
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Post
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Pre
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.client.GuiNotification
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.opengl.GL11
 */
package codes.biscuit.skyblockaddons.listeners;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.hooks.FontRendererHook;
import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.core.Attribute;
import codes.biscuit.skyblockaddons.core.EssenceType;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.SkillType;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonClass;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonMilestone;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonPlayer;
import codes.biscuit.skyblockaddons.features.BaitManager;
import codes.biscuit.skyblockaddons.features.EndstoneProtectorManager;
import codes.biscuit.skyblockaddons.features.FetchurManager;
import codes.biscuit.skyblockaddons.features.ItemDiff;
import codes.biscuit.skyblockaddons.features.SlayerArmorProgress;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonTracker;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonType;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonsSince;
import codes.biscuit.skyblockaddons.features.healingcircle.HealingCircleManager;
import codes.biscuit.skyblockaddons.features.powerorbs.PowerOrb;
import codes.biscuit.skyblockaddons.features.powerorbs.PowerOrbManager;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerBoss;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerDrop;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerTracker;
import codes.biscuit.skyblockaddons.features.spookyevent.CandyType;
import codes.biscuit.skyblockaddons.features.spookyevent.SpookyEventManager;
import codes.biscuit.skyblockaddons.features.tablist.TabListParser;
import codes.biscuit.skyblockaddons.features.tablist.TabListRenderer;
import codes.biscuit.skyblockaddons.features.tabtimers.TabEffect;
import codes.biscuit.skyblockaddons.features.tabtimers.TabEffectManager;
import codes.biscuit.skyblockaddons.gui.EnchantmentSettingsGui;
import codes.biscuit.skyblockaddons.gui.IslandWarpGui;
import codes.biscuit.skyblockaddons.gui.LocationEditGui;
import codes.biscuit.skyblockaddons.gui.SettingsGui;
import codes.biscuit.skyblockaddons.gui.SkyblockAddonsGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonLocation;
import codes.biscuit.skyblockaddons.misc.Updater;
import codes.biscuit.skyblockaddons.misc.scheduler.Scheduler;
import codes.biscuit.skyblockaddons.shader.ShaderManager;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaScreenTexturedShader;
import codes.biscuit.skyblockaddons.utils.ActionBarParser;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.LocationUtils;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import javax.vecmath.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.client.GuiNotification;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class RenderListener {
    private static final ItemStack BONE_ITEM = new ItemStack(Items.field_151103_aS);
    private static final ResourceLocation BARS = new ResourceLocation("skyblockaddons", "barsV2.png");
    private static final ResourceLocation DEFENCE_VANILLA = new ResourceLocation("skyblockaddons", "defence.png");
    private static final ResourceLocation IMPERIAL_BARS_FIX = new ResourceLocation("skyblockaddons", "imperialbarsfix.png");
    private static final ResourceLocation TICKER_SYMBOL = new ResourceLocation("skyblockaddons", "ticker.png");
    private static final ResourceLocation ENDERMAN_ICON = new ResourceLocation("skyblockaddons", "icons/enderman.png");
    private static final ResourceLocation ENDERMAN_GROUP_ICON = new ResourceLocation("skyblockaddons", "icons/endermangroup.png");
    private static final ResourceLocation MAGMA_BOSS_ICON = new ResourceLocation("skyblockaddons", "icons/magmaboss.png");
    private static final ResourceLocation SIRIUS_ICON = new ResourceLocation("skyblockaddons", "icons/sirius.png");
    private static final ResourceLocation SUMMONING_EYE_ICON = new ResourceLocation("skyblockaddons", "icons/summoningeye.png");
    private static final ResourceLocation ZEALOTS_PER_EYE_ICON = new ResourceLocation("skyblockaddons", "icons/zealotspereye.png");
    private static final ResourceLocation SLASH_ICON = new ResourceLocation("skyblockaddons", "icons/slash.png");
    private static final ResourceLocation IRON_GOLEM_ICON = new ResourceLocation("skyblockaddons", "icons/irongolem.png");
    private static final ResourceLocation CRITICAL = new ResourceLocation("skyblockaddons", "critical.png");
    private static final ItemStack WATER_BUCKET = new ItemStack(Items.field_151131_as);
    private static final ItemStack IRON_SWORD = new ItemStack(Items.field_151040_l);
    private static final ItemStack WARP_SKULL = ItemUtils.createSkullItemStack("\u00a7bFast Travel", null, "9ae837fc-19da-3841-af06-7db55d51c815", "c9c8881e42915a9d29bb61a16fb26d059913204d265df5b439b3d792acd56");
    private static final ItemStack SKYBLOCK_MENU = ItemUtils.createItemStack(Items.field_151156_bN, "\u00a7aSkyBlock Menu \u00a77(Right Click)", "SKYBLOCK_MENU", false);
    private static final ItemStack PET_ROCK = ItemUtils.createSkullItemStack("\u00a7f\u00a7f\u00a77[Lvl 100] \u00a76Rock", null, "1ed7c993-8190-3055-a48c-f70f71b17284", "cb2b5d48e57577563aca31735519cb622219bc058b1f34648b67b8e71bc0fa");
    private static final ItemStack DOLPHIN_PET = ItemUtils.createSkullItemStack("\u00a7f\u00a7f\u00a77[Lvl 100] \u00a76Dolphin", null, "48f53ffe-a3f0-3280-aac0-11cc0d6121f4", "cefe7d803a45aa2af1993df2544a28df849a762663719bfefc58bf389ab7f5");
    private static final ItemStack CHEST = new ItemStack(Item.func_150898_a((Block)Blocks.field_150486_ae));
    private static final ItemStack SKULL = ItemUtils.createSkullItemStack("Skull", null, "c659cdd4-e436-4977-a6a7-d5518ebecfbb", "1ae3855f952cd4a03c148a946e3f812a5955ad35cbcb52627ea4acd47d3081");
    private static final ItemStack GREEN_CANDY = ItemUtils.createSkullItemStack("Green Candy", "GREEN_CANDY", "0961dbb3-2167-3f75-92e4-ec8eb4f57e55", "ce0622d01cfdae386cc7dd83427674b422f46d0a57e67a20607e6ca4b9af3b01");
    private static final ItemStack PURPLE_CANDY = ItemUtils.createSkullItemStack("Purple Candy", "PURPLE_CANDY", "5b0e6bf0-6312-3476-b5f8-dbc9a8849a1f", "95d7aee4e97ad84095f55405ee1305d1fc8554c309edb12a1db863cde9c1ec80");
    private static final SlayerArmorProgress[] DUMMY_PROGRESSES = new SlayerArmorProgress[]{new SlayerArmorProgress(new ItemStack((Item)Items.field_151175_af)), new SlayerArmorProgress(new ItemStack((Item)Items.field_151022_W)), new SlayerArmorProgress(new ItemStack((Item)Items.field_151163_ad)), new SlayerArmorProgress(new ItemStack((Item)Items.field_151024_Q))};
    private static EntityArmorStand radiantDummyArmorStand;
    private static EntityZombie revenant;
    private static EntitySpider tarantula;
    private static EntityCaveSpider caveSpider;
    private static EntityWolf sven;
    private static EntityEnderman enderman;
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private boolean predictHealth;
    private boolean predictMana;
    private boolean updateMessageDisplayed;
    private Feature subtitleFeature;
    private Feature titleFeature;
    private int arrowsLeft;
    private String cannotReachMobName;
    private long skillFadeOutTime = -1L;
    private SkillType skill;
    private String skillText;
    private EnumUtils.GUIType guiToOpen;
    private int guiPageToOpen = 1;
    private EnumUtils.GuiTab guiTabToOpen = EnumUtils.GuiTab.MAIN;
    private Feature guiFeatureToOpen;
    private static final List<ItemDiff> DUMMY_PICKUP_LOG;

    @SubscribeEvent
    public void onRenderRegular(RenderGameOverlayEvent.Post e) {
        EntityPlayerSP p;
        Minecraft mc = Minecraft.func_71410_x();
        if (mc != null && (p = mc.field_71439_g) != null && this.main.getConfigValues().isEnabled(Feature.HEALTH_PREDICTION)) {
            int newHealth = this.getAttribute(Attribute.HEALTH) > this.getAttribute(Attribute.MAX_HEALTH) ? this.getAttribute(Attribute.HEALTH) : Math.round((float)this.getAttribute(Attribute.MAX_HEALTH) * (p.func_110143_aJ() / p.func_110138_aP()));
            this.main.getUtils().getAttributes().get((Object)Attribute.HEALTH).setValue(newHealth);
        }
        if (!(this.main.isUsingLabymod() && !(Minecraft.func_71410_x().field_71456_v instanceof GuiIngameForge) || e.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && e.type != RenderGameOverlayEvent.ElementType.JUMPBAR)) {
            if (this.main.getUtils().isOnSkyblock()) {
                this.renderOverlays();
                this.renderWarnings(e.resolution);
            } else {
                this.renderTimersOnly();
            }
            this.drawUpdateMessage();
        }
    }

    @SubscribeEvent
    public void onRenderLabyMod(RenderGameOverlayEvent e) {
        if (e.type == null && this.main.isUsingLabymod()) {
            if (this.main.getUtils().isOnSkyblock()) {
                this.renderOverlays();
                this.renderWarnings(e.resolution);
            } else {
                this.renderTimersOnly();
            }
            this.drawUpdateMessage();
        }
    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Specials.Pre<EntityLivingBase> e) {
        EntityLivingBase entity = e.entity;
        if (entity.func_145818_k_()) {
            if (this.main.getConfigValues().isEnabled(Feature.MINION_DISABLE_LOCATION_WARNING)) {
                if (entity.func_95999_t().startsWith("\u00a7cThis location isn't perfect! :(")) {
                    e.setCanceled(true);
                }
                if (entity.func_95999_t().startsWith("\u00a7c/!\\")) {
                    for (Entity listEntity : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
                        if (!listEntity.func_145818_k_() || !listEntity.func_95999_t().startsWith("\u00a7cThis location isn't perfect! :(") || listEntity.field_70165_t != entity.field_70165_t || listEntity.field_70161_v != entity.field_70161_v || listEntity.field_70163_u + 0.375 != entity.field_70163_u) continue;
                        e.setCanceled(true);
                        break;
                    }
                }
            }
            if (this.main.getConfigValues().isEnabled(Feature.HIDE_SVEN_PUP_NAMETAGS) && entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.func_95999_t().contains("Sven Pup")) {
                e.setCanceled(true);
            }
        }
    }

    private void renderTimersOnly() {
        Minecraft mc = Minecraft.func_71410_x();
        if (!(mc.field_71462_r instanceof LocationEditGui) && !(mc.field_71462_r instanceof GuiNotification)) {
            float scale;
            GlStateManager.func_179084_k();
            if (this.main.getConfigValues().isEnabled(Feature.MAGMA_BOSS_TIMER) && this.main.getConfigValues().isEnabled(Feature.SHOW_MAGMA_TIMER_IN_OTHER_GAMES) && this.main.getPlayerListener().getMagmaAccuracy() != EnumUtils.MagmaTimerAccuracy.NO_DATA) {
                scale = this.main.getConfigValues().getGuiScale(Feature.MAGMA_BOSS_TIMER);
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
                this.drawText(Feature.MAGMA_BOSS_TIMER, scale, mc, null);
                GlStateManager.func_179121_F();
            }
            if (this.main.getConfigValues().isEnabled(Feature.DARK_AUCTION_TIMER) && this.main.getConfigValues().isEnabled(Feature.SHOW_DARK_AUCTION_TIMER_IN_OTHER_GAMES)) {
                scale = this.main.getConfigValues().getGuiScale(Feature.DARK_AUCTION_TIMER);
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
                this.drawText(Feature.DARK_AUCTION_TIMER, scale, mc, null);
                GlStateManager.func_179121_F();
            }
        }
    }

    private void renderWarnings(ScaledResolution scaledResolution) {
        float scale;
        int stringWidth;
        String text;
        Message message;
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71441_e == null || mc.field_71439_g == null || !this.main.getUtils().isOnSkyblock()) {
            return;
        }
        int scaledWidth = scaledResolution.func_78326_a();
        int scaledHeight = scaledResolution.func_78328_b();
        if (this.titleFeature != null) {
            message = null;
            switch (this.titleFeature) {
                case MAGMA_WARNING: {
                    message = Message.MESSAGE_MAGMA_BOSS_WARNING;
                    break;
                }
                case FULL_INVENTORY_WARNING: {
                    message = Message.MESSAGE_FULL_INVENTORY;
                    break;
                }
                case SUMMONING_EYE_ALERT: {
                    message = Message.MESSAGE_SUMMONING_EYE_FOUND;
                    break;
                }
                case SPECIAL_ZEALOT_ALERT: {
                    message = Message.MESSAGE_SPECIAL_ZEALOT_FOUND;
                    break;
                }
                case LEGENDARY_SEA_CREATURE_WARNING: {
                    message = Message.MESSAGE_LEGENDARY_SEA_CREATURE_WARNING;
                    break;
                }
                case BOSS_APPROACH_ALERT: {
                    message = Message.MESSAGE_BOSS_APPROACH_ALERT;
                    break;
                }
                case WARN_WHEN_FETCHUR_CHANGES: {
                    message = Message.MESSAGE_FETCHUR_WARNING;
                }
            }
            if (message != null) {
                text = message.getMessage(new String[0]);
                stringWidth = mc.field_71466_p.func_78256_a(text);
                if ((float)stringWidth * (scale = 4.0f) > (float)scaledWidth * 0.9f) {
                    scale = (float)scaledWidth * 0.9f / (float)stringWidth;
                }
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)(scaledWidth / 2), (float)(scaledHeight / 2), (float)0.0f);
                GlStateManager.func_179147_l();
                GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)scale);
                FontRendererHook.setupFeatureFont(this.titleFeature);
                DrawUtils.drawText(text, -mc.field_71466_p.func_78256_a(text) / 2, -20.0f, this.main.getConfigValues().getColor(this.titleFeature));
                FontRendererHook.endFeatureFont();
                GlStateManager.func_179121_F();
                GlStateManager.func_179121_F();
            }
        }
        if (this.subtitleFeature != null) {
            message = null;
            switch (this.subtitleFeature) {
                case MINION_STOP_WARNING: {
                    message = Message.MESSAGE_MINION_CANNOT_REACH;
                    break;
                }
                case MINION_FULL_WARNING: {
                    message = Message.MESSAGE_MINION_IS_FULL;
                    break;
                }
                case NO_ARROWS_LEFT_ALERT: {
                    message = Message.MESSAGE_NO_ARROWS_LEFT;
                }
            }
            if (message != null) {
                text = message == Message.MESSAGE_MINION_CANNOT_REACH ? message.getMessage(this.cannotReachMobName) : (message == Message.MESSAGE_NO_ARROWS_LEFT && this.arrowsLeft != -1 ? Message.MESSAGE_ONLY_FEW_ARROWS_LEFT.getMessage(Integer.toString(this.arrowsLeft)) : message.getMessage(new String[0]));
                stringWidth = mc.field_71466_p.func_78256_a(text);
                if ((float)stringWidth * (scale = 2.0f) > (float)scaledWidth * 0.9f) {
                    scale = (float)scaledWidth * 0.9f / (float)stringWidth;
                }
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)(scaledWidth / 2), (float)(scaledHeight / 2), (float)0.0f);
                GlStateManager.func_179147_l();
                GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)scale);
                FontRendererHook.setupFeatureFont(this.subtitleFeature);
                DrawUtils.drawText(text, (float)(-mc.field_71466_p.func_78256_a(text)) / 2.0f, -23.0f, this.main.getConfigValues().getColor(this.subtitleFeature));
                FontRendererHook.endFeatureFont();
                GlStateManager.func_179121_F();
                GlStateManager.func_179121_F();
            }
        }
    }

    private void renderOverlays() {
        Minecraft mc = Minecraft.func_71410_x();
        if (!(mc.field_71462_r instanceof LocationEditGui) && !(mc.field_71462_r instanceof GuiNotification)) {
            GlStateManager.func_179084_k();
            for (Feature feature : Feature.getGuiFeatures()) {
                if (!this.main.getConfigValues().isEnabled(feature) || feature == Feature.SKELETON_BAR && !this.main.getInventoryUtils().isWearingSkeletonHelmet() || feature == Feature.HEALTH_UPDATES && this.main.getPlayerListener().getHealthUpdate() == null) continue;
                float scale = this.main.getConfigValues().getGuiScale(feature);
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
                feature.draw(scale, mc, null);
                GlStateManager.func_179121_F();
            }
        }
    }

    public void drawBar(Feature feature, float scale, Minecraft mc, ButtonLocation buttonLocation) {
        int remainingTime;
        float fill;
        if (feature == Feature.MANA_BAR) {
            fill = (float)this.getAttribute(Attribute.MANA) / (float)this.getAttribute(Attribute.MAX_MANA);
        } else if (feature == Feature.DRILL_FUEL_BAR) {
            fill = (float)this.getAttribute(Attribute.FUEL) / (float)this.getAttribute(Attribute.MAX_FUEL);
        } else if (feature == Feature.SKILL_PROGRESS_BAR) {
            ActionBarParser parser = this.main.getPlayerListener().getActionBarParser();
            if (buttonLocation == null) {
                if (parser.getPercent() == 0.0f || parser.getPercent() == 100.0f) {
                    return;
                }
                fill = parser.getPercent() / 100.0f;
            } else {
                fill = 0.4f;
            }
        } else {
            fill = (float)this.getAttribute(Attribute.HEALTH) / (float)this.getAttribute(Attribute.MAX_HEALTH);
        }
        if (fill > 1.0f) {
            fill = 1.0f;
        }
        float x = this.main.getConfigValues().getActualX(feature);
        float y = this.main.getConfigValues().getActualY(feature);
        float scaleX = this.main.getConfigValues().getSizesX(feature);
        float scaleY = this.main.getConfigValues().getSizesY(feature);
        GlStateManager.func_179152_a((float)scaleX, (float)scaleY, (float)1.0f);
        x = this.transformXY(x, 71, scale * scaleX);
        y = this.transformXY(y, 5, scale * scaleY);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + 71.0f, y, y + 5.0f, scale, scaleX, scaleY);
        }
        SkyblockColor color = ColorUtils.getDummySkyblockColor(this.main.getConfigValues().getColor(feature), this.main.getConfigValues().getChromaFeatures().contains((Object)feature));
        if (feature == Feature.SKILL_PROGRESS_BAR && buttonLocation == null && (remainingTime = (int)(this.skillFadeOutTime - System.currentTimeMillis())) < 0) {
            if (remainingTime < -2000) {
                return;
            }
            int textAlpha = Math.round(255.0f - (float)(-remainingTime) / 2000.0f * 255.0f);
            color = ColorUtils.getDummySkyblockColor(this.main.getConfigValues().getColor(feature, textAlpha), this.main.getConfigValues().getChromaFeatures().contains((Object)feature));
        }
        if (feature == Feature.DRILL_FUEL_BAR && buttonLocation == null && !ItemUtils.isDrill(mc.field_71439_g.func_70694_bm())) {
            return;
        }
        if (feature == Feature.HEALTH_BAR && this.main.getConfigValues().isEnabled(Feature.CHANGE_BAR_COLOR_FOR_POTIONS)) {
            if (mc.field_71439_g.func_82165_m(19)) {
                color = ColorUtils.getDummySkyblockColor(ColorCode.DARK_GREEN.getColor(), this.main.getConfigValues().getChromaFeatures().contains((Object)feature));
            } else if (mc.field_71439_g.func_82165_m(20)) {
                color = ColorUtils.getDummySkyblockColor(ColorCode.DARK_GRAY.getColor(), this.main.getConfigValues().getChromaFeatures().contains((Object)feature));
            }
        }
        this.main.getUtils().enableStandardGLOptions();
        this.drawMultiLayeredBar(mc, color, x, y, fill);
        this.main.getUtils().restoreGLOptions();
    }

    private void drawMultiLayeredBar(Minecraft mc, SkyblockColor color, float x, float y, float fill) {
        int barHeight = 5;
        int barWidth = 71;
        float barFill = (float)barWidth * fill;
        mc.func_110434_K().func_110577_a(BARS);
        if (color.getColor() == ColorCode.BLACK.getColor()) {
            GlStateManager.func_179131_c((float)0.25f, (float)0.25f, (float)0.25f, (float)((float)ColorUtils.getAlpha(color.getColor()) / 255.0f));
        } else {
            ColorUtils.bindColor(color.getColor(), 0.9f);
        }
        if (color.drawMulticolorUsingShader()) {
            GlStateManager.func_179124_c((float)0.5f, (float)0.5f, (float)0.5f);
            ShaderManager.getInstance().enableShader(ChromaScreenTexturedShader.class);
        }
        DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 1.0f, 1.0f, barWidth, barHeight, 80.0f, 50.0f);
        if (color.drawMulticolorUsingShader()) {
            ColorUtils.bindWhite();
            ShaderManager.getInstance().enableShader(ChromaScreenTexturedShader.class);
        }
        if (fill != 0.0f) {
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 1.0f, 7.0f, barFill, barHeight, 80.0f, 50.0f);
        }
        if (color.drawMulticolorUsingShader()) {
            ShaderManager.getInstance().disableShader();
        }
        ColorUtils.bindWhite();
        if (fill > 0.0f && fill < 1.0f) {
            float padding = 0.01f;
            float oneSide = 2.0f - padding;
            float startX = Math.max(0.0f, barFill - oneSide);
            float startTexX = Math.max(padding, oneSide - barFill);
            float endTexX = Math.min(2.0f * oneSide - startTexX, (float)barWidth - barFill + oneSide);
            DrawUtils.drawModalRectWithCustomSizedTexture(x + startX, y, 1.0f + startTexX, 24.0f, endTexX, barHeight, 80.0f, 50.0f);
        }
        DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 1.0f, 13.0f, barWidth, barHeight, 80.0f, 50.0f);
    }

    private void drawUpdateMessage() {
        Updater updater2 = this.main.getUpdater();
        String message = updater2.getMessageToRender();
        if (updater2.hasUpdate() && message != null && !this.updateMessageDisplayed) {
            Minecraft mc = Minecraft.func_71410_x();
            String[] textList = this.main.getUtils().wrapSplitText(message, 36);
            int halfWidth = new ScaledResolution(mc).func_78326_a() / 2;
            Gui.func_73734_a((int)(halfWidth - 110), (int)20, (int)(halfWidth + 110), (int)(53 + textList.length * 10), (int)this.main.getUtils().getDefaultBlue(140));
            String title = "SkyblockAddons";
            GlStateManager.func_179094_E();
            float scale = 1.5f;
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
            DrawUtils.drawCenteredText(title, (int)((float)halfWidth / scale), (int)(30.0f / scale), ColorCode.WHITE.getColor());
            GlStateManager.func_179121_F();
            int y = 45;
            for (String line : textList) {
                DrawUtils.drawCenteredText(line, halfWidth, y, ColorCode.WHITE.getColor());
                y += 10;
            }
            this.main.getScheduler().schedule(Scheduler.CommandType.ERASE_UPDATE_MESSAGE, 10, new Object[0]);
            if (!this.main.getUpdater().hasSentUpdateMessage()) {
                this.main.getUpdater().sendUpdateMessage();
            }
        }
    }

    public void drawSkeletonBar(Minecraft mc, float scale, ButtonLocation buttonLocation) {
        float x = this.main.getConfigValues().getActualX(Feature.SKELETON_BAR);
        float y = this.main.getConfigValues().getActualY(Feature.SKELETON_BAR);
        int bones = 0;
        if (!(mc.field_71462_r instanceof LocationEditGui)) {
            for (Entity listEntity : mc.field_71441_e.field_72996_f) {
                if (!(listEntity instanceof EntityItem) || !(listEntity.field_70154_o instanceof EntityArmorStand) || !listEntity.field_70154_o.func_82150_aj() || !(listEntity.func_70032_d((Entity)mc.field_71439_g) <= 8.0f)) continue;
                ++bones;
            }
        } else {
            bones = 3;
        }
        if (bones > 3) {
            bones = 3;
        }
        int height = 16;
        int width = 48;
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        this.main.getUtils().enableStandardGLOptions();
        for (int boneCounter = 0; boneCounter < bones; ++boneCounter) {
            this.renderItem(BONE_ITEM, x + (float)(boneCounter * 16), y);
        }
        this.main.getUtils().restoreGLOptions();
    }

    public void drawScorpionFoilTicker(Minecraft mc, float scale, ButtonLocation buttonLocation) {
        if (buttonLocation != null || this.main.getPlayerListener().getTickers() != -1) {
            float x = this.main.getConfigValues().getActualX(Feature.TICKER_CHARGES_DISPLAY);
            float y = this.main.getConfigValues().getActualY(Feature.TICKER_CHARGES_DISPLAY);
            int height = 9;
            int width = 42;
            x = this.transformXY(x, width, scale);
            y = this.transformXY(y, height, scale);
            if (buttonLocation != null) {
                buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            }
            this.main.getUtils().enableStandardGLOptions();
            int maxTickers = buttonLocation == null ? this.main.getPlayerListener().getMaxTickers() : 4;
            for (int tickers = 0; tickers < maxTickers; ++tickers) {
                mc.func_110434_K().func_110577_a(TICKER_SYMBOL);
                GlStateManager.func_179141_d();
                if (tickers < (buttonLocation == null ? this.main.getPlayerListener().getTickers() : 3)) {
                    DrawUtils.drawModalRectWithCustomSizedTexture(x + (float)(tickers * 11), y, 0.0f, 0.0f, 9.0f, 9.0f, 18.0f, 9.0f, false);
                    continue;
                }
                DrawUtils.drawModalRectWithCustomSizedTexture(x + (float)(tickers * 11), y, 9.0f, 0.0f, 9.0f, 9.0f, 18.0f, 9.0f, false);
            }
            this.main.getUtils().restoreGLOptions();
        }
    }

    public void drawIcon(float scale, Minecraft mc, ButtonLocation buttonLocation) {
        if (this.main.getConfigValues().isDisabled(Feature.USE_VANILLA_TEXTURE_DEFENCE)) {
            mc.func_110434_K().func_110577_a(Gui.field_110324_m);
        } else {
            mc.func_110434_K().func_110577_a(DEFENCE_VANILLA);
        }
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int height = 9;
        int width = 9;
        float x = this.main.getConfigValues().getActualX(Feature.DEFENCE_ICON);
        float y = this.main.getConfigValues().getActualY(Feature.DEFENCE_ICON);
        x = this.main.getRenderListener().transformXY(x, width, scale);
        y = this.main.getRenderListener().transformXY(y, height, scale);
        this.main.getUtils().enableStandardGLOptions();
        if (buttonLocation == null) {
            mc.field_71456_v.func_175174_a(x, y, 34, 9, width, height);
        } else {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            buttonLocation.func_175174_a(x, y, 34, 9, width, height);
        }
        this.main.getUtils().restoreGLOptions();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void drawText(Feature feature, float scale, Minecraft mc, ButtonLocation buttonLocation) {
        int purple;
        int green;
        String text;
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int color = this.main.getConfigValues().getColor(feature);
        if (feature == Feature.MANA_TEXT) {
            text = this.getAttribute(Attribute.MANA) + "/" + this.getAttribute(Attribute.MAX_MANA);
        } else if (feature == Feature.OVERFLOW_MANA) {
            if (this.getAttribute(Attribute.OVERFLOW_MANA) == 0 && buttonLocation == null) return;
            text = this.getAttribute(Attribute.OVERFLOW_MANA) + "\u02ac";
        } else if (feature == Feature.HEALTH_TEXT) {
            text = this.getAttribute(Attribute.HEALTH) + "/" + this.getAttribute(Attribute.MAX_HEALTH);
        } else if (feature == Feature.DEFENCE_TEXT) {
            text = String.valueOf(this.getAttribute(Attribute.DEFENCE));
        } else if (feature == Feature.OTHER_DEFENCE_STATS) {
            text = this.main.getPlayerListener().getActionBarParser().getOtherDefense();
            if (buttonLocation != null && (text == null || text.length() == 0)) {
                text = "|||  T3!";
            }
            if (text == null || text.length() == 0) {
                return;
            }
        } else if (feature == Feature.EFFECTIVE_HEALTH_TEXT) {
            text = String.valueOf(Math.round((float)this.getAttribute(Attribute.HEALTH) * (1.0f + (float)this.getAttribute(Attribute.DEFENCE) / 100.0f)));
        } else if (feature == Feature.DRILL_FUEL_TEXT) {
            if (!ItemUtils.isDrill(mc.field_71439_g.func_70694_bm())) {
                return;
            }
            text = (this.getAttribute(Attribute.FUEL) + "/" + this.getAttribute(Attribute.MAX_FUEL)).replaceAll("000$", "k");
        } else if (feature == Feature.DEFENCE_PERCENTAGE) {
            double doubleDefence = this.getAttribute(Attribute.DEFENCE);
            double percentage = doubleDefence / 100.0 / (doubleDefence / 100.0 + 1.0) * 100.0;
            BigDecimal bigDecimal = new BigDecimal(percentage).setScale(1, 4);
            text = bigDecimal.toString() + "%";
        } else if (feature == Feature.SPEED_PERCENTAGE) {
            String walkSpeed = String.valueOf(Minecraft.func_71410_x().field_71439_g.field_71075_bZ.func_75094_b() * 1000.0f);
            text = walkSpeed.substring(0, Math.min(walkSpeed.length(), 3));
            if (text.endsWith(".")) {
                text = text.substring(0, text.indexOf(46));
            }
            text = text + "%";
        } else if (feature == Feature.HEALTH_UPDATES) {
            Integer healthUpdate = this.main.getPlayerListener().getHealthUpdate();
            if (buttonLocation == null) {
                if (healthUpdate == null) return;
                color = healthUpdate > 0 ? ColorCode.GREEN.getColor() : ColorCode.RED.getColor();
                text = (healthUpdate > 0 ? "+" : "-") + Math.abs(healthUpdate);
            } else {
                text = "+123";
                color = ColorCode.GREEN.getColor();
            }
        } else if (feature == Feature.DARK_AUCTION_TIMER) {
            Calendar nextDarkAuction = Calendar.getInstance(TimeZone.getTimeZone("EST"));
            if (nextDarkAuction.get(12) >= 55) {
                nextDarkAuction.add(11, 1);
            }
            nextDarkAuction.set(12, 55);
            nextDarkAuction.set(13, 0);
            int difference = (int)(nextDarkAuction.getTimeInMillis() - System.currentTimeMillis());
            int minutes = difference / 60000;
            int seconds = (int)Math.round((double)(difference % 60000) / 1000.0);
            StringBuilder timestamp = new StringBuilder();
            if (minutes < 10) {
                timestamp.append("0");
            }
            timestamp.append(minutes).append(":");
            if (seconds < 10) {
                timestamp.append("0");
            }
            timestamp.append(seconds);
            text = timestamp.toString();
        } else if (feature == Feature.MAGMA_BOSS_TIMER) {
            StringBuilder magmaBuilder = new StringBuilder();
            magmaBuilder.append(this.main.getPlayerListener().getMagmaAccuracy().getSymbol());
            EnumUtils.MagmaTimerAccuracy ma = this.main.getPlayerListener().getMagmaAccuracy();
            if (ma == EnumUtils.MagmaTimerAccuracy.ABOUT || ma == EnumUtils.MagmaTimerAccuracy.EXACTLY) {
                if (buttonLocation == null) {
                    int totalSeconds = this.main.getPlayerListener().getMagmaTime();
                    if (totalSeconds < 0) {
                        totalSeconds = 0;
                    }
                    int hours = totalSeconds / 3600;
                    int minutes = totalSeconds / 60 % 60;
                    int seconds = totalSeconds % 60;
                    if (Math.abs(hours) >= 10) {
                        hours = 10;
                    }
                    magmaBuilder.append(hours).append(":");
                    if (minutes < 10) {
                        magmaBuilder.append("0");
                    }
                    magmaBuilder.append(minutes).append(":");
                    if (seconds < 10) {
                        magmaBuilder.append("0");
                    }
                    magmaBuilder.append(seconds);
                } else {
                    magmaBuilder.append("1:23:45");
                }
            }
            text = magmaBuilder.toString();
        } else if (feature == Feature.SKILL_DISPLAY) {
            int remainingTime;
            if (buttonLocation == null) {
                text = this.skillText;
                if (text == null) {
                    return;
                }
            } else {
                StringBuilder previewBuilder = new StringBuilder();
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_SKILL_XP_GAINED)) {
                    previewBuilder.append("+123 ");
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_SKILL_PERCENTAGE_INSTEAD_OF_XP)) {
                    previewBuilder.append("40% ");
                } else {
                    previewBuilder.append("(2000/5000) ");
                }
                if (this.main.getConfigValues().isEnabled(Feature.SKILL_ACTIONS_LEFT_UNTIL_NEXT_LEVEL)) {
                    previewBuilder.append(" - ").append(Translations.getMessage("messages.actionsLeft", 3000)).append(" ");
                }
                previewBuilder.setLength(previewBuilder.length() - 1);
                text = previewBuilder.toString();
            }
            if (buttonLocation == null && (remainingTime = (int)(this.skillFadeOutTime - System.currentTimeMillis())) < 0) {
                if (remainingTime < -2000) {
                    return;
                }
                int textAlpha = Math.round(255.0f - (float)(-remainingTime) / 2000.0f * 255.0f);
                color = this.main.getConfigValues().getColor(feature, textAlpha);
            }
        } else if (feature == Feature.ZEALOT_COUNTER) {
            if (this.main.getConfigValues().isEnabled(Feature.ZEALOT_COUNTER_NEST_ONLY) && this.main.getUtils().getLocation() != Location.DRAGONS_NEST && buttonLocation == null) {
                return;
            }
            text = String.valueOf(this.main.getPersistentValuesManager().getPersistentValues().getKills());
        } else if (feature == Feature.SHOW_TOTAL_ZEALOT_COUNT) {
            if (this.main.getConfigValues().isEnabled(Feature.SHOW_TOTAL_ZEALOT_COUNT_NEST_ONLY) && this.main.getUtils().getLocation() != Location.DRAGONS_NEST && buttonLocation == null) {
                return;
            }
            text = this.main.getPersistentValuesManager().getPersistentValues().getTotalKills() <= 0 ? String.valueOf(this.main.getPersistentValuesManager().getPersistentValues().getKills()) : String.valueOf(this.main.getPersistentValuesManager().getPersistentValues().getTotalKills() + this.main.getPersistentValuesManager().getPersistentValues().getKills());
        } else if (feature == Feature.SHOW_SUMMONING_EYE_COUNT) {
            if (this.main.getConfigValues().isEnabled(Feature.SHOW_SUMMONING_EYE_COUNT_NEST_ONLY) && this.main.getUtils().getLocation() != Location.DRAGONS_NEST && buttonLocation == null) {
                return;
            }
            text = String.valueOf(this.main.getPersistentValuesManager().getPersistentValues().getSummoningEyeCount());
        } else if (feature == Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE) {
            if (this.main.getConfigValues().isEnabled(Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE_NEST_ONLY) && this.main.getUtils().getLocation() != Location.DRAGONS_NEST && buttonLocation == null) {
                return;
            }
            int summoningEyeCount = this.main.getPersistentValuesManager().getPersistentValues().getSummoningEyeCount();
            text = summoningEyeCount > 0 ? String.valueOf(Math.round((double)this.main.getPersistentValuesManager().getPersistentValues().getTotalKills() / (double)this.main.getPersistentValuesManager().getPersistentValues().getSummoningEyeCount())) : "0";
        } else if (feature == Feature.BIRCH_PARK_RAINMAKER_TIMER) {
            long rainmakerTime = this.main.getPlayerListener().getRainmakerTimeEnd();
            if ((this.main.getUtils().getLocation() != Location.BIRCH_PARK || rainmakerTime == -1L) && buttonLocation == null) {
                return;
            }
            int totalSeconds = (int)(rainmakerTime - System.currentTimeMillis()) / 1000;
            if (TabListParser.getParsedRainTime() != null) {
                text = TabListParser.getParsedRainTime();
            } else if (rainmakerTime != -1L && totalSeconds > 0) {
                StringBuilder timerBuilder = new StringBuilder();
                int hours = totalSeconds / 3600;
                int minutes = totalSeconds / 60 % 60;
                int seconds = totalSeconds % 60;
                if (hours > 0) {
                    timerBuilder.append(hours).append(":");
                }
                if (minutes < 10 && hours > 0) {
                    timerBuilder.append("0");
                }
                timerBuilder.append(minutes).append(":");
                if (seconds < 10) {
                    timerBuilder.append("0");
                }
                timerBuilder.append(seconds);
                text = timerBuilder.toString();
            } else {
                if (buttonLocation == null) {
                    return;
                }
                text = "1:23";
            }
        } else if (feature == Feature.COMBAT_TIMER_DISPLAY) {
            long lastDamaged = this.main.getUtils().getLastDamaged() + 5000L;
            int combatSeconds = (int)Math.ceil((double)(lastDamaged - System.currentTimeMillis()) / 1000.0);
            if (combatSeconds <= 0 && buttonLocation == null) {
                return;
            }
            text = "IN COMBAT";
        } else if (feature == Feature.ENDSTONE_PROTECTOR_DISPLAY) {
            if ((this.main.getUtils().getLocation() != Location.THE_END && this.main.getUtils().getLocation() != Location.DRAGONS_NEST || EndstoneProtectorManager.getMinibossStage() == null || !EndstoneProtectorManager.isCanDetectSkull()) && buttonLocation == null) {
                return;
            }
            EndstoneProtectorManager.Stage stage = EndstoneProtectorManager.getMinibossStage();
            if (buttonLocation != null && stage == null) {
                stage = EndstoneProtectorManager.Stage.STAGE_3;
            }
            int stageNum = Math.min(stage.ordinal(), 5);
            text = Message.MESSAGE_STAGE.getMessage(String.valueOf(stageNum));
        } else if (feature == Feature.SHOW_DUNGEON_MILESTONE) {
            if (buttonLocation == null && !this.main.getUtils().isInDungeon()) {
                return;
            }
            DungeonMilestone dungeonMilestone = this.main.getDungeonManager().getDungeonMilestone();
            if (dungeonMilestone == null) {
                if (buttonLocation == null) return;
                dungeonMilestone = new DungeonMilestone(DungeonClass.HEALER);
            }
            text = "Milestone " + dungeonMilestone.getLevel();
        } else if (feature == Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY) {
            if (buttonLocation == null && !this.main.getUtils().isInDungeon()) {
                return;
            }
            text = "";
        } else if (feature == Feature.DUNGEON_DEATH_COUNTER) {
            int deaths = this.main.getDungeonManager().getDeathCount();
            if (buttonLocation == null && !this.main.getUtils().isInDungeon()) {
                return;
            }
            text = Integer.toString(deaths);
        } else if (feature == Feature.ROCK_PET_TRACKER) {
            text = String.valueOf(this.main.getPersistentValuesManager().getPersistentValues().getOresMined());
        } else if (feature == Feature.DOLPHIN_PET_TRACKER) {
            text = String.valueOf(this.main.getPersistentValuesManager().getPersistentValues().getSeaCreaturesKilled());
        } else if (feature == Feature.DUNGEONS_SECRETS_DISPLAY) {
            if (buttonLocation == null && !this.main.getUtils().isInDungeon()) {
                return;
            }
            text = "Secrets";
        } else if (feature == Feature.CANDY_POINTS_COUNTER) {
            if (buttonLocation == null && !SpookyEventManager.isActive()) {
                return;
            }
            text = "Test";
        } else {
            if (feature != Feature.FETCHUR_TODAY) return;
            FetchurManager.FetchurItem fetchurItem = FetchurManager.getInstance().getCurrentFetchurItem();
            text = !FetchurManager.getInstance().hasFetchedToday() || buttonLocation != null ? (this.main.getConfigValues().isEnabled(Feature.SHOW_FETCHUR_ITEM_NAME) ? Message.MESSAGE_FETCHUR_TODAY.getMessage(fetchurItem.getItemStack().field_77994_a + "x " + fetchurItem.getItemText()) : Message.MESSAGE_FETCHUR_TODAY.getMessage("")) : "";
        }
        float x = this.main.getConfigValues().getActualX(feature);
        float y = this.main.getConfigValues().getActualY(feature);
        int height = 7;
        int width = mc.field_71466_p.func_78256_a(text);
        if (feature == Feature.ZEALOT_COUNTER || feature == Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE) {
            width = mc.field_71466_p.func_78256_a("500");
        } else if (feature == Feature.SHOW_TOTAL_ZEALOT_COUNT) {
            width = mc.field_71466_p.func_78256_a("30000");
        } else if (feature == Feature.SHOW_SUMMONING_EYE_COUNT) {
            width = mc.field_71466_p.func_78256_a("100");
        }
        if (feature == Feature.MAGMA_BOSS_TIMER || feature == Feature.DARK_AUCTION_TIMER || feature == Feature.ZEALOT_COUNTER || feature == Feature.SKILL_DISPLAY || feature == Feature.SHOW_TOTAL_ZEALOT_COUNT || feature == Feature.SHOW_SUMMONING_EYE_COUNT || feature == Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE || feature == Feature.BIRCH_PARK_RAINMAKER_TIMER || feature == Feature.COMBAT_TIMER_DISPLAY || feature == Feature.ENDSTONE_PROTECTOR_DISPLAY || feature == Feature.DUNGEON_DEATH_COUNTER || feature == Feature.DOLPHIN_PET_TRACKER || feature == Feature.ROCK_PET_TRACKER) {
            width += 18;
            height += 9;
        }
        if (feature == Feature.ENDSTONE_PROTECTOR_DISPLAY) {
            width += 20 + mc.field_71466_p.func_78256_a(String.valueOf(EndstoneProtectorManager.getZealotCount()));
        }
        if (feature == Feature.COMBAT_TIMER_DISPLAY) {
            height += 15;
        }
        if (feature == Feature.SHOW_DUNGEON_MILESTONE || feature == Feature.DUNGEONS_SECRETS_DISPLAY) {
            width += 18;
            height += 10;
        }
        if (feature == Feature.FETCHUR_TODAY && this.main.getConfigValues().isDisabled(Feature.SHOW_FETCHUR_ITEM_NAME)) {
            width += 18;
            height += 9;
        }
        if (feature == Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY) {
            int maxNumberWidth = mc.field_71466_p.func_78256_a("99");
            width = 20 + maxNumberWidth + 5 + 18 + 2 + maxNumberWidth;
            height = 18 * (int)Math.ceil((float)EssenceType.values().length / 2.0f);
        }
        if (feature == Feature.CANDY_POINTS_COUNTER) {
            width = 0;
            Map<CandyType, Integer> candyCounts = SpookyEventManager.getCandyCounts();
            if (!SpookyEventManager.isActive()) {
                if (buttonLocation == null) {
                    return;
                }
                candyCounts = SpookyEventManager.getDummyCandyCounts();
            }
            green = candyCounts.get((Object)CandyType.GREEN);
            purple = candyCounts.get((Object)CandyType.PURPLE);
            if (buttonLocation != null || green > 0) {
                width += 17 + mc.field_71466_p.func_78256_a(String.valueOf(green));
            }
            if (buttonLocation != null || purple > 0) {
                if (green > 0) {
                    ++width;
                }
                width += 17 + mc.field_71466_p.func_78256_a(String.valueOf(purple)) + 1;
            }
            height = 24;
        }
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        this.main.getUtils().enableStandardGLOptions();
        if (feature == Feature.DARK_AUCTION_TIMER) {
            mc.func_110434_K().func_110577_a(SIRIUS_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.MAGMA_BOSS_TIMER) {
            mc.func_110434_K().func_110577_a(MAGMA_BOSS_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.ZEALOT_COUNTER) {
            mc.func_110434_K().func_110577_a(ENDERMAN_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.SHOW_TOTAL_ZEALOT_COUNT) {
            mc.func_110434_K().func_110577_a(ENDERMAN_GROUP_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.SHOW_SUMMONING_EYE_COUNT) {
            mc.func_110434_K().func_110577_a(SUMMONING_EYE_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE) {
            mc.func_110434_K().func_110577_a(ZEALOTS_PER_EYE_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            mc.func_110434_K().func_110577_a(SLASH_ICON);
            ColorUtils.bindColor(color);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f, true);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.SKILL_DISPLAY && (this.skill != null && this.skill.getItem() != null || buttonLocation != null)) {
            this.renderItem(buttonLocation == null ? this.skill.getItem() : SkillType.FARMING.getItem(), x, y);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.BIRCH_PARK_RAINMAKER_TIMER) {
            this.renderItem(WATER_BUCKET, x, y);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.COMBAT_TIMER_DISPLAY) {
            long lastDamaged = this.main.getUtils().getLastDamaged() + 5000L;
            int combatSeconds = (int)Math.ceil((double)(lastDamaged - System.currentTimeMillis()) / 1000.0);
            if (buttonLocation != null) {
                combatSeconds = 5;
            }
            this.renderItem(IRON_SWORD, x, y);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
            y += 20.0f;
            String warpTimeRemaining = combatSeconds + "s";
            String menuTimeRemaining = combatSeconds - 2 + "s";
            if (combatSeconds <= 2) {
                menuTimeRemaining = "\u2714";
            }
            int menuTimeRemainingWidth = mc.field_71466_p.func_78256_a(menuTimeRemaining);
            int spacerBetweenBothItems = 4;
            int spacerBetweenItemsAndText = 2;
            this.renderItem(SKYBLOCK_MENU, x + (float)width / 2.0f - 16.0f - (float)menuTimeRemainingWidth - (float)spacerBetweenItemsAndText - (float)spacerBetweenBothItems / 2.0f, y - 5.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(menuTimeRemaining, x + (float)width / 2.0f - (float)menuTimeRemainingWidth - (float)spacerBetweenBothItems / 2.0f, y, color);
            FontRendererHook.endFeatureFont();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.renderItem(WARP_SKULL, x + (float)width / 2.0f + (float)spacerBetweenBothItems / 2.0f, y - 5.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(warpTimeRemaining, x + (float)width / 2.0f + (float)spacerBetweenBothItems / 2.0f + 13.0f + (float)spacerBetweenItemsAndText, y, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.ENDSTONE_PROTECTOR_DISPLAY) {
            mc.func_110434_K().func_110577_a(IRON_GOLEM_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            mc.func_110434_K().func_110577_a(ENDERMAN_GROUP_ICON);
            DrawUtils.drawModalRectWithCustomSizedTexture(x += (float)(18 + mc.field_71466_p.func_78256_a(text) + 2), y, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            int count = EndstoneProtectorManager.getZealotCount();
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(String.valueOf(count), x + 16.0f + 2.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.SHOW_DUNGEON_MILESTONE) {
            DungeonMilestone dungeonMilestone = this.main.getDungeonManager().getDungeonMilestone();
            if (buttonLocation != null) {
                dungeonMilestone = new DungeonMilestone(DungeonClass.HEALER);
            }
            this.renderItem(dungeonMilestone.getDungeonClass().getItem(), x, y);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y, color);
            double amount = Double.parseDouble(dungeonMilestone.getValue());
            DecimalFormat formatter = new DecimalFormat("#,###");
            DrawUtils.drawText(formatter.format(amount), x + 18.0f + (float)mc.field_71466_p.func_78256_a(text) / 2.0f - (float)mc.field_71466_p.func_78256_a(formatter.format(amount)) / 2.0f, y + 9.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY) {
            this.drawCollectedEssences(x, y, buttonLocation != null, true);
        } else if (feature == Feature.DUNGEON_DEATH_COUNTER) {
            this.renderItem(SKULL, x, y);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.ROCK_PET_TRACKER) {
            this.renderItem(PET_ROCK, x, y);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.DOLPHIN_PET_TRACKER) {
            this.renderItem(DOLPHIN_PET, x, y);
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x + 18.0f, y + 4.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.DUNGEONS_SECRETS_DISPLAY) {
            int secrets = this.main.getDungeonManager().getSecrets();
            int maxSecrets = this.main.getDungeonManager().getMaxSecrets();
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText("\u00a7n" + text, x + 16.0f + 2.0f, y, color);
            FontRendererHook.endFeatureFont();
            if (secrets == -1 && buttonLocation != null) {
                secrets = 5;
                maxSecrets = 10;
            }
            if (secrets == -1 | maxSecrets == 0) {
                FontRendererHook.setupFeatureFont(feature);
                String none = Translations.getMessage("messages.none", new Object[0]);
                DrawUtils.drawText(none, x + 16.0f + 2.0f + (float)mc.field_71466_p.func_78256_a(text) / 2.0f - (float)mc.field_71466_p.func_78256_a(none) / 2.0f, y + 9.0f, color);
                FontRendererHook.endFeatureFont();
            } else {
                float g;
                float r;
                float percent;
                if (secrets > maxSecrets) {
                    maxSecrets = secrets;
                }
                if ((percent = (float)secrets / (float)maxSecrets) < 0.0f) {
                    percent = 0.0f;
                } else if (percent > 1.0f) {
                    percent = 1.0f;
                }
                if ((double)percent <= 0.5) {
                    r = 1.0f;
                    g = percent * 2.0f * 0.66f + 0.33f;
                } else {
                    r = (1.0f - percent) * 0.66f + 0.33f;
                    g = 1.0f;
                }
                int secretsColor = new Color(Math.min(1.0f, r), g, 0.33f).getRGB();
                float secretsWidth = mc.field_71466_p.func_78256_a(String.valueOf(secrets));
                float slashWidth = mc.field_71466_p.func_78256_a("/");
                float maxSecretsWidth = mc.field_71466_p.func_78256_a(String.valueOf(maxSecrets));
                float totalWidth = secretsWidth + slashWidth + maxSecretsWidth;
                FontRendererHook.setupFeatureFont(feature);
                DrawUtils.drawText("/", x + 16.0f + 2.0f + (float)mc.field_71466_p.func_78256_a(text) / 2.0f - totalWidth / 2.0f + secretsWidth, y + 9.0f, color);
                FontRendererHook.endFeatureFont();
                DrawUtils.drawText(String.valueOf(secrets), x + 16.0f + 2.0f + (float)mc.field_71466_p.func_78256_a(text) / 2.0f - totalWidth / 2.0f, y + 9.0f, secretsColor);
                DrawUtils.drawText(String.valueOf(maxSecrets), x + 16.0f + 2.0f + (float)mc.field_71466_p.func_78256_a(text) / 2.0f - totalWidth / 2.0f + secretsWidth + slashWidth, y + 9.0f, secretsColor);
            }
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.renderItem(CHEST, x, y);
        } else if (feature == Feature.CANDY_POINTS_COUNTER) {
            Map<CandyType, Integer> candyCounts = SpookyEventManager.getCandyCounts();
            if (!SpookyEventManager.isActive()) {
                candyCounts = SpookyEventManager.getDummyCandyCounts();
            }
            green = candyCounts.get((Object)CandyType.GREEN);
            purple = candyCounts.get((Object)CandyType.PURPLE);
            int points = SpookyEventManager.getPoints();
            if (!SpookyEventManager.isActive()) {
                points = 5678;
            }
            float currentX = x;
            if (buttonLocation != null || green > 0) {
                this.renderItem(GREEN_CANDY, currentX, y);
                FontRendererHook.setupFeatureFont(feature);
                DrawUtils.drawText(String.valueOf(green), currentX += 17.0f, y + 4.0f, color);
                FontRendererHook.endFeatureFont();
            }
            if (buttonLocation != null || purple > 0) {
                if (buttonLocation != null || green > 0) {
                    currentX += (float)(mc.field_71466_p.func_78256_a(String.valueOf(green)) + 1);
                }
                this.renderItem(PURPLE_CANDY, currentX, y);
                FontRendererHook.setupFeatureFont(feature);
                DrawUtils.drawText(String.valueOf(purple), currentX += 17.0f, y + 4.0f, color);
                FontRendererHook.endFeatureFont();
            }
            FontRendererHook.setupFeatureFont(feature);
            text = points + " Points";
            DrawUtils.drawText(text, x + (float)width / 2.0f - (float)mc.field_71466_p.func_78256_a(text) / 2.0f, y + 16.0f, color);
            FontRendererHook.endFeatureFont();
        } else if (feature == Feature.FETCHUR_TODAY) {
            boolean showDwarven = this.main.getConfigValues().isDisabled(Feature.SHOW_FETCHUR_ONLY_IN_DWARVENS) || LocationUtils.isInDwarvenMines(this.main.getUtils().getLocation().getScoreboardName());
            boolean showInventory = this.main.getConfigValues().isDisabled(Feature.SHOW_FETCHUR_INVENTORY_OPEN_ONLY) || Minecraft.func_71410_x().field_71462_r != null;
            FetchurManager.FetchurItem fetchurItem = FetchurManager.getInstance().getCurrentFetchurItem();
            if (fetchurItem != null && (buttonLocation != null || !FetchurManager.getInstance().hasFetchedToday() && showDwarven && showInventory)) {
                FontRendererHook.setupFeatureFont(feature);
                if (this.main.getConfigValues().isDisabled(Feature.SHOW_FETCHUR_ITEM_NAME)) {
                    DrawUtils.drawText(text, x + 1.0f, y + 4.0f, color);
                    float offsetX = Minecraft.func_71410_x().field_71466_p.func_78256_a(text);
                    this.renderItemAndOverlay(fetchurItem.getItemStack(), String.valueOf(fetchurItem.getItemStack().field_77994_a), x + offsetX, y);
                } else {
                    DrawUtils.drawText(text, x, y, color);
                }
                FontRendererHook.endFeatureFont();
            }
        } else {
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(text, x, y, color);
            FontRendererHook.endFeatureFont();
        }
        this.main.getUtils().restoreGLOptions();
    }

    public void drawCollectedEssences(float x, float y, boolean usePlaceholders, boolean hideZeroes) {
        Minecraft mc = Minecraft.func_71410_x();
        Map<EssenceType, Integer> collectedEssences = this.main.getDungeonManager().getCollectedEssences();
        float currentX = x;
        int maxNumberWidth = mc.field_71466_p.func_78256_a("99");
        int color = this.main.getConfigValues().getColor(Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY);
        int count = 0;
        for (EssenceType essenceType : EssenceType.values()) {
            int value = collectedEssences.getOrDefault((Object)essenceType, 0);
            if (usePlaceholders) {
                value = 99;
            } else if (value <= 0 && hideZeroes) continue;
            int column = count % 2;
            int row = count / 2;
            if (column == 0) {
                currentX = x;
            } else if (column == 1) {
                currentX = x + 18.0f + 2.0f + (float)maxNumberWidth + 5.0f;
            }
            float currentY = y + (float)(row * 18);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            mc.func_110434_K().func_110577_a(essenceType.getResourceLocation());
            DrawUtils.drawModalRectWithCustomSizedTexture(currentX, currentY, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f);
            FontRendererHook.setupFeatureFont(Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY);
            DrawUtils.drawText(String.valueOf(value), currentX + 18.0f + 2.0f, currentY + 5.0f, color);
            FontRendererHook.endFeatureFont();
            ++count;
        }
    }

    public void drawBaitList(Minecraft mc, float scale, ButtonLocation buttonLocation) {
        if (!BaitManager.getInstance().isHoldingRod() && buttonLocation == null) {
            return;
        }
        Map<BaitManager.BaitType, Integer> baits = BaitManager.getInstance().getBaitsInInventory();
        if (buttonLocation != null) {
            baits = BaitManager.DUMMY_BAITS;
        }
        int longestLineWidth = 0;
        for (Map.Entry<BaitManager.BaitType, Integer> entry : baits.entrySet()) {
            longestLineWidth = Math.max(longestLineWidth, Minecraft.func_71410_x().field_71466_p.func_78256_a(String.valueOf(entry.getValue())));
        }
        float x = this.main.getConfigValues().getActualX(Feature.BAIT_LIST);
        float y = this.main.getConfigValues().getActualY(Feature.BAIT_LIST);
        int spacing = 1;
        int iconSize = 16;
        int width = iconSize + spacing + longestLineWidth;
        int height = iconSize * baits.size();
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
        }
        this.main.getUtils().enableStandardGLOptions();
        for (Map.Entry<BaitManager.BaitType, Integer> entry : baits.entrySet()) {
            if (entry.getValue() == 0) continue;
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.renderItem(entry.getKey().getItemStack(), x, y);
            int color = this.main.getConfigValues().getColor(Feature.BAIT_LIST);
            FontRendererHook.setupFeatureFont(Feature.BAIT_LIST);
            DrawUtils.drawText(String.valueOf(entry.getValue()), x + (float)iconSize + (float)spacing, y + (float)iconSize / 2.0f - 4.0f, color);
            FontRendererHook.endFeatureFont();
            y += (float)iconSize;
        }
        this.main.getUtils().restoreGLOptions();
    }

    public void drawSlayerTrackers(Feature feature, Minecraft mc, float scale, ButtonLocation buttonLocation) {
        SlayerBoss slayerBoss;
        boolean textMode;
        boolean colorByRarity;
        EnumUtils.SlayerQuest quest = this.main.getUtils().getSlayerQuest();
        Location location = this.main.getUtils().getLocation();
        ConfigValues config = this.main.getConfigValues();
        if (feature == Feature.REVENANT_SLAYER_TRACKER) {
            if (buttonLocation == null && config.isEnabled(Feature.HIDE_WHEN_NOT_IN_CRYPTS) && (quest != EnumUtils.SlayerQuest.REVENANT_HORROR || location != Location.GRAVEYARD && location != Location.COAL_MINE)) {
                return;
            }
            colorByRarity = config.isEnabled(Feature.REVENANT_COLOR_BY_RARITY);
            textMode = config.isEnabled(Feature.REVENANT_TEXT_MODE);
            slayerBoss = SlayerBoss.REVENANT;
        } else if (feature == Feature.TARANTULA_SLAYER_TRACKER) {
            if (buttonLocation == null && config.isEnabled(Feature.HIDE_WHEN_NOT_IN_SPIDERS_DEN) && (quest != EnumUtils.SlayerQuest.TARANTULA_BROODFATHER || location != Location.SPIDERS_DEN)) {
                return;
            }
            colorByRarity = config.isEnabled(Feature.TARANTULA_COLOR_BY_RARITY);
            textMode = config.isEnabled(Feature.TARANTULA_TEXT_MODE);
            slayerBoss = SlayerBoss.TARANTULA;
        } else if (feature == Feature.SVEN_SLAYER_TRACKER) {
            if (buttonLocation == null && config.isEnabled(Feature.HIDE_WHEN_NOT_IN_CASTLE) && (quest != EnumUtils.SlayerQuest.SVEN_PACKMASTER || location != Location.RUINS && location != Location.HOWLING_CAVE)) {
                return;
            }
            colorByRarity = config.isEnabled(Feature.SVEN_COLOR_BY_RARITY);
            textMode = config.isEnabled(Feature.SVEN_TEXT_MODE);
            slayerBoss = SlayerBoss.SVEN;
        } else if (feature == Feature.VOIDGLOOM_SLAYER_TRACKER) {
            if (buttonLocation == null && config.isEnabled(Feature.HIDE_WHEN_NOT_IN_END) && (quest != EnumUtils.SlayerQuest.VOIDGLOOM_SERAPH || location != Location.THE_END && location != Location.DRAGONS_NEST && location != Location.VOID_SEPULTURE)) {
                return;
            }
            colorByRarity = config.isEnabled(Feature.ENDERMAN_COLOR_BY_RARITY);
            textMode = config.isEnabled(Feature.ENDERMAN_TEXT_MODE);
            slayerBoss = SlayerBoss.VOIDGLOOM;
        } else {
            return;
        }
        float x = config.getActualX(feature);
        float y = config.getActualY(feature);
        int color = config.getColor(feature);
        if (textMode) {
            int lineHeight = 8;
            int spacer = 3;
            int lines = 0;
            int spacers = 0;
            int longestLineWidth = mc.field_71466_p.func_78256_a(slayerBoss.getDisplayName());
            ++lines;
            ++spacers;
            int longestSlayerDropLineWidth = mc.field_71466_p.func_78256_a(Translations.getMessage("slayerTracker.bossesKilled", new Object[0]));
            int longestCount = mc.field_71466_p.func_78256_a(String.valueOf(SlayerTracker.getInstance().getSlayerKills(slayerBoss)));
            ++lines;
            ++spacers;
            for (SlayerDrop drop : slayerBoss.getDrops()) {
                longestSlayerDropLineWidth = Math.max(longestSlayerDropLineWidth, mc.field_71466_p.func_78256_a(drop.getDisplayName()));
                longestCount = Math.max(longestCount, mc.field_71466_p.func_78256_a(String.valueOf(SlayerTracker.getInstance().getDropCount(drop))));
                ++lines;
            }
            int width = Math.max(longestLineWidth, longestSlayerDropLineWidth + 8 + longestCount);
            int height = lines * 8 + spacer * spacers;
            x = this.transformXY(x, width, scale);
            y = this.transformXY(y, height, scale);
            if (buttonLocation != null) {
                buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            }
            FontRendererHook.setupFeatureFont(feature);
            DrawUtils.drawText(slayerBoss.getDisplayName(), x, y, color);
            DrawUtils.drawText(Translations.getMessage("slayerTracker.bossesKilled", new Object[0]), x, y += (float)(lineHeight + spacer), color);
            String text = String.valueOf(SlayerTracker.getInstance().getSlayerKills(slayerBoss));
            DrawUtils.drawText(text, x + (float)width - (float)mc.field_71466_p.func_78256_a(text), y, color);
            y += (float)(lineHeight + spacer);
            FontRendererHook.endFeatureFont();
            for (SlayerDrop slayerDrop : slayerBoss.getDrops()) {
                int currentColor = color;
                if (colorByRarity) {
                    currentColor = slayerDrop.getRarity().getColorCode().getColor();
                } else {
                    FontRendererHook.setupFeatureFont(feature);
                }
                DrawUtils.drawText(slayerDrop.getDisplayName(), x, y, currentColor);
                if (!colorByRarity) {
                    FontRendererHook.endFeatureFont();
                }
                FontRendererHook.setupFeatureFont(feature);
                text = String.valueOf(SlayerTracker.getInstance().getDropCount(slayerDrop));
                DrawUtils.drawText(text, x + (float)width - (float)mc.field_71466_p.func_78256_a(text), y, currentColor);
                FontRendererHook.endFeatureFont();
                y += (float)lineHeight;
            }
        } else {
            int width;
            int textCenterX;
            int entityRenderY;
            if (feature == Feature.REVENANT_SLAYER_TRACKER) {
                entityRenderY = 30;
                textCenterX = 15;
            } else if (feature == Feature.TARANTULA_SLAYER_TRACKER) {
                entityRenderY = 36;
                textCenterX = 28;
            } else if (feature == Feature.VOIDGLOOM_SLAYER_TRACKER) {
                entityRenderY = 25;
                textCenterX = 20;
            } else {
                entityRenderY = 36;
                textCenterX = 15;
            }
            int iconWidth = 16;
            int entityWidth = textCenterX * 2;
            int entityIconSpacingHorizontal = 2;
            int iconTextOffset = -2;
            int row = 0;
            int column = 0;
            int maxItemsPerRow = (int)Math.ceil((double)slayerBoss.getDrops().size() / 3.0);
            int[] maxTextWidths = new int[maxItemsPerRow];
            for (SlayerDrop slayerDrop : slayerBoss.getDrops()) {
                width = mc.field_71466_p.func_78256_a(TextUtils.abbreviate(SlayerTracker.getInstance().getDropCount(slayerDrop)));
                maxTextWidths[column] = Math.max(maxTextWidths[column], width);
                if (++column != maxItemsPerRow) continue;
                column = 0;
                ++row;
            }
            int totalColumnWidth = 0;
            for (int i : maxTextWidths) {
                totalColumnWidth += i;
            }
            int n = 4;
            width = entityWidth + entityIconSpacingHorizontal + maxItemsPerRow * iconWidth + totalColumnWidth + iconTextOffset;
            int height = (iconWidth + n) * 3 - n;
            x = this.transformXY(x, width, scale);
            y = this.transformXY(y, height, scale);
            if (buttonLocation != null) {
                buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            }
            if (feature == Feature.REVENANT_SLAYER_TRACKER) {
                if (revenant == null) {
                    revenant = new EntityZombie((World)Utils.getDummyWorld());
                    RenderListener.revenant.func_70035_c()[0] = ItemUtils.createItemStack(Items.field_151012_L, true);
                    RenderListener.revenant.func_70035_c()[1] = ItemUtils.createItemStack((Item)Items.field_151175_af, false);
                    RenderListener.revenant.func_70035_c()[2] = ItemUtils.createItemStack((Item)Items.field_151173_ae, true);
                    RenderListener.revenant.func_70035_c()[3] = ItemUtils.createItemStack((Item)Items.field_151163_ad, true);
                    RenderListener.revenant.func_70035_c()[4] = ItemUtils.createSkullItemStack(null, null, "45012ee3-29fd-42ed-908b-648c731c7457", "1fc0184473fe882d2895ce7cbc8197bd40ff70bf10d3745de97b6c2a9c5fc78f");
                }
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                RenderListener.revenant.field_70173_aa = (int)this.main.getNewScheduler().getTotalTicks();
                this.drawEntity((EntityLivingBase)revenant, x + 15.0f, y + 53.0f, -15.0f);
            } else if (feature == Feature.TARANTULA_SLAYER_TRACKER) {
                if (tarantula == null) {
                    tarantula = new EntitySpider((World)Utils.getDummyWorld());
                    caveSpider = new EntityCaveSpider((World)Utils.getDummyWorld());
                    RenderListener.tarantula.field_70153_n = caveSpider;
                    RenderListener.caveSpider.field_70154_o = tarantula;
                }
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.drawEntity((EntityLivingBase)tarantula, x + 28.0f, y + 38.0f, -30.0f);
                this.drawEntity((EntityLivingBase)caveSpider, x + 25.0f, y + 23.0f, -30.0f);
            } else if (feature == Feature.VOIDGLOOM_SLAYER_TRACKER) {
                if (enderman == null) {
                    enderman = new EntityEnderman((World)Utils.getDummyWorld());
                    enderman.func_175490_a(Blocks.field_150461_bJ.func_176194_O().func_177621_b());
                }
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                RenderListener.enderman.field_70173_aa = (int)this.main.getNewScheduler().getTotalTicks();
                GlStateManager.func_179139_a((double)0.7, (double)0.7, (double)1.0);
                this.drawEntity((EntityLivingBase)enderman, (x + 15.0f) / 0.7f, (y + 51.0f) / 0.7f, -30.0f);
                GlStateManager.func_179139_a((double)1.4285714285714286, (double)1.4285714285714286, (double)1.0);
            } else {
                if (sven == null) {
                    sven = new EntityWolf((World)Utils.getDummyWorld());
                    sven.func_70916_h(true);
                }
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.drawEntity((EntityLivingBase)sven, x + 17.0f, y + 38.0f, -35.0f);
            }
            GlStateManager.func_179097_i();
            FontRendererHook.setupFeatureFont(feature);
            String text = TextUtils.abbreviate(SlayerTracker.getInstance().getSlayerKills(slayerBoss)) + " Kills";
            DrawUtils.drawText(text, x + (float)textCenterX - (float)mc.field_71466_p.func_78256_a(text) / 2.0f, y + (float)entityRenderY, color);
            FontRendererHook.endFeatureFont();
            row = 0;
            column = 0;
            float currentX = x + (float)entityIconSpacingHorizontal + (float)entityWidth;
            for (SlayerDrop slayerDrop : slayerBoss.getDrops()) {
                if (column > 0) {
                    currentX += (float)(iconWidth + maxTextWidths[column - 1]);
                }
                float currentY = y + (float)(row * (iconWidth + n));
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.renderItem(slayerDrop.getItemStack(), currentX, currentY);
                GlStateManager.func_179097_i();
                int currentColor = color;
                if (colorByRarity) {
                    currentColor = slayerDrop.getRarity().getColorCode().getColor();
                } else {
                    FontRendererHook.setupFeatureFont(feature);
                }
                DrawUtils.drawText(TextUtils.abbreviate(SlayerTracker.getInstance().getDropCount(slayerDrop)), currentX + (float)iconWidth + (float)iconTextOffset, currentY + 8.0f, currentColor);
                if (!colorByRarity) {
                    FontRendererHook.endFeatureFont();
                }
                if (++column != maxItemsPerRow) continue;
                currentX = x + (float)entityIconSpacingHorizontal + (float)entityWidth;
                column = 0;
                ++row;
            }
            GlStateManager.func_179126_j();
        }
    }

    public void drawDragonTrackers(Minecraft mc, float scale, ButtonLocation buttonLocation) {
        int height;
        int width;
        if (this.main.getConfigValues().isEnabled(Feature.DRAGON_STATS_TRACKER_NEST_ONLY) && this.main.getUtils().getLocation() != Location.DRAGONS_NEST && buttonLocation == null) {
            return;
        }
        List<DragonType> recentDragons = DragonTracker.getInstance().getRecentDragons();
        if (recentDragons.isEmpty() && buttonLocation != null) {
            recentDragons = DragonTracker.getDummyDragons();
        }
        boolean colorByRarity = this.main.getConfigValues().isEnabled(Feature.DRAGON_STATS_TRACKER_COLOR_BY_RARITY);
        boolean textMode = this.main.getConfigValues().isEnabled(Feature.DRAGON_STATS_TRACKER_TEXT_MODE);
        int spacerHeight = 3;
        String never = Translations.getMessage("dragonTracker.never", new Object[0]);
        if (textMode) {
            int lines = 0;
            int spacers = 0;
            int longestLineWidth = mc.field_71466_p.func_78256_a(Translations.getMessage("dragonTracker.recentDragons", new Object[0]));
            ++lines;
            ++spacers;
            ++spacers;
            longestLineWidth = Math.max(longestLineWidth, mc.field_71466_p.func_78256_a(Translations.getMessage("dragonTracker.dragonsSince", new Object[0])));
            ++lines;
            ++spacers;
            for (DragonType dragon : recentDragons) {
                longestLineWidth = Math.max(longestLineWidth, mc.field_71466_p.func_78256_a(dragon.getDisplayName()));
                ++lines;
            }
            int longestCount = 0;
            int longestDragonsSinceLineWidth = 0;
            for (DragonsSince dragonsSince : DragonsSince.values()) {
                longestDragonsSinceLineWidth = Math.max(longestDragonsSinceLineWidth, mc.field_71466_p.func_78256_a(dragonsSince.getDisplayName()));
                int dragonsSinceValue = DragonTracker.getInstance().getDragsSince(dragonsSince);
                longestCount = Math.max(longestCount, mc.field_71466_p.func_78256_a(dragonsSinceValue == 0 ? never : String.valueOf(dragonsSinceValue)));
                ++lines;
            }
            width = Math.max(longestLineWidth, longestDragonsSinceLineWidth + 8 + longestCount);
            height = lines * 8 + spacerHeight * spacers;
        } else {
            width = 100;
            height = 100;
        }
        float x = this.main.getConfigValues().getActualX(Feature.DRAGON_STATS_TRACKER);
        float y = this.main.getConfigValues().getActualY(Feature.DRAGON_STATS_TRACKER);
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
        }
        int color = this.main.getConfigValues().getColor(Feature.DRAGON_STATS_TRACKER);
        if (textMode) {
            FontRendererHook.setupFeatureFont(Feature.DRAGON_STATS_TRACKER);
            DrawUtils.drawText(Translations.getMessage("dragonTracker.recentDragons", new Object[0]), x, y, color);
            y += (float)(8 + spacerHeight);
            FontRendererHook.endFeatureFont();
            for (DragonType dragon : recentDragons) {
                int currentColor = color;
                if (colorByRarity) {
                    currentColor = dragon.getColor().getColor();
                } else {
                    FontRendererHook.setupFeatureFont(Feature.DRAGON_STATS_TRACKER);
                }
                DrawUtils.drawText(dragon.getDisplayName(), x, y, currentColor);
                if (!colorByRarity) {
                    FontRendererHook.endFeatureFont();
                }
                y += 8.0f;
            }
            FontRendererHook.setupFeatureFont(Feature.DRAGON_STATS_TRACKER);
            color = this.main.getConfigValues().getColor(Feature.DRAGON_STATS_TRACKER);
            DrawUtils.drawText(Translations.getMessage("dragonTracker.dragonsSince", new Object[0]), x, y += (float)spacerHeight, color);
            y += (float)(8 + spacerHeight);
            FontRendererHook.endFeatureFont();
            for (DragonsSince dragonsSince : DragonsSince.values()) {
                GlStateManager.func_179097_i();
                GlStateManager.func_179147_l();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.func_179084_k();
                GlStateManager.func_179126_j();
                int currentColor = color;
                if (colorByRarity) {
                    currentColor = dragonsSince.getItemRarity().getColorCode().getColor();
                } else {
                    FontRendererHook.setupFeatureFont(Feature.DRAGON_STATS_TRACKER);
                }
                DrawUtils.drawText(dragonsSince.getDisplayName(), x, y, currentColor);
                if (!colorByRarity) {
                    FontRendererHook.endFeatureFont();
                }
                FontRendererHook.setupFeatureFont(Feature.DRAGON_STATS_TRACKER);
                int dragonsSinceValue = DragonTracker.getInstance().getDragsSince(dragonsSince);
                String text = dragonsSinceValue == 0 ? never : String.valueOf(dragonsSinceValue);
                DrawUtils.drawText(text, x + (float)width - (float)mc.field_71466_p.func_78256_a(text), y, color);
                y += 8.0f;
                FontRendererHook.endFeatureFont();
            }
        }
    }

    public void drawRevenantIndicator(float scale, Minecraft mc, ButtonLocation buttonLocation) {
        float x = this.main.getConfigValues().getActualX(Feature.SLAYER_INDICATOR);
        float y = this.main.getConfigValues().getActualY(Feature.SLAYER_INDICATOR);
        int longest = -1;
        SlayerArmorProgress[] progresses = this.main.getInventoryUtils().getSlayerArmorProgresses();
        if (buttonLocation != null) {
            progresses = DUMMY_PROGRESSES;
        }
        for (SlayerArmorProgress progress : progresses) {
            int textWidth;
            if (progress == null || (textWidth = mc.field_71466_p.func_78256_a(progress.getPercent() + "% (" + progress.getDefence() + ")")) <= longest) continue;
            longest = textWidth;
        }
        if (longest == -1) {
            return;
        }
        int height = 60;
        int width = 18 + longest;
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        this.main.getUtils().enableStandardGLOptions();
        EnumUtils.AnchorPoint anchorPoint = this.main.getConfigValues().getAnchorPoint(Feature.SLAYER_INDICATOR);
        boolean downwards = anchorPoint == EnumUtils.AnchorPoint.TOP_LEFT || anchorPoint == EnumUtils.AnchorPoint.TOP_RIGHT;
        int color = this.main.getConfigValues().getColor(Feature.SLAYER_INDICATOR);
        int drawnCount = 0;
        for (int armorPiece = 3; armorPiece >= 0; --armorPiece) {
            SlayerArmorProgress progress = progresses[downwards ? armorPiece : 3 - armorPiece];
            if (progress == null) continue;
            float fixedY = downwards ? y + (float)(drawnCount * 15) : y + 45.0f - (float)(drawnCount * 15);
            this.renderItem(progress.getItemStack(), x, fixedY);
            float currentX = x + 19.0f;
            FontRendererHook.setupFeatureFont(Feature.SLAYER_INDICATOR);
            DrawUtils.drawText(progress.getPercent() + "% (", currentX, fixedY + 5.0f, color);
            FontRendererHook.endFeatureFont();
            DrawUtils.drawText(progress.getDefence(), currentX += (float)mc.field_71466_p.func_78256_a(progress.getPercent() + "% ("), fixedY + 5.0f, -1);
            FontRendererHook.setupFeatureFont(Feature.SLAYER_INDICATOR);
            DrawUtils.drawText(")", currentX += (float)mc.field_71466_p.func_78256_a(progress.getDefence()), fixedY + 5.0f, color);
            FontRendererHook.endFeatureFont();
            ++drawnCount;
        }
        this.main.getUtils().restoreGLOptions();
    }

    public void drawPotionEffectTimers(float scale, ButtonLocation buttonLocation) {
        String duration;
        String effect;
        EnumUtils.AnchorPoint anchorPoint;
        float x = this.main.getConfigValues().getActualX(Feature.TAB_EFFECT_TIMERS);
        float y = this.main.getConfigValues().getActualY(Feature.TAB_EFFECT_TIMERS);
        TabEffectManager tabEffect = TabEffectManager.getInstance();
        List<TabEffect> potionTimers = tabEffect.getPotionTimers();
        List<TabEffect> powerupTimers = tabEffect.getPowerupTimers();
        if (buttonLocation == null) {
            if (potionTimers.isEmpty() && powerupTimers.isEmpty() && TabEffectManager.getInstance().getEffectCount() == 0) {
                return;
            }
        } else {
            potionTimers = TabEffectManager.getDummyPotionTimers();
            powerupTimers = TabEffectManager.getDummyPowerupTimers();
        }
        boolean topDown = (anchorPoint = this.main.getConfigValues().getAnchorPoint(Feature.TAB_EFFECT_TIMERS)) == EnumUtils.AnchorPoint.TOP_LEFT || anchorPoint == EnumUtils.AnchorPoint.TOP_RIGHT;
        int totalEffects = TabEffectManager.getDummyPotionTimers().size() + TabEffectManager.getDummyPowerupTimers().size() + 1;
        int spacer = !TabEffectManager.getDummyPotionTimers().isEmpty() && !TabEffectManager.getDummyPowerupTimers().isEmpty() ? 3 : 0;
        int lineHeight = 9;
        int height = totalEffects * lineHeight + spacer - 1;
        int width = 156;
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        this.main.getUtils().enableStandardGLOptions();
        boolean alignRight = anchorPoint == EnumUtils.AnchorPoint.TOP_RIGHT || anchorPoint == EnumUtils.AnchorPoint.BOTTOM_RIGHT;
        int color = this.main.getConfigValues().getColor(Feature.TAB_EFFECT_TIMERS);
        Minecraft mc = Minecraft.func_71410_x();
        FontRendererHook.setupFeatureFont(Feature.TAB_EFFECT_TIMERS);
        int effectCount = TabEffectManager.getInstance().getEffectCount();
        String text = effectCount == 1 ? Message.MESSAGE_ONE_EFFECT_ACTIVE.getMessage(new String[0]) : Message.MESSAGE_EFFECTS_ACTIVE.getMessage(String.valueOf(effectCount));
        float lineY = topDown ? y : y + (float)height - 8.0f;
        if (alignRight) {
            DrawUtils.drawText(text, x + (float)width - (float)mc.field_71466_p.func_78256_a(text), lineY, color);
        } else {
            DrawUtils.drawText(text, x, lineY, color);
        }
        FontRendererHook.endFeatureFont();
        int drawnCount = 1;
        for (TabEffect potion : potionTimers) {
            lineY = topDown ? y + (float)(drawnCount * lineHeight) : y + (float)height - (float)(drawnCount * lineHeight) - 8.0f;
            effect = potion.getEffect();
            duration = potion.getDurationForDisplay();
            if (alignRight) {
                FontRendererHook.setupFeatureFont(Feature.TAB_EFFECT_TIMERS);
                DrawUtils.drawText(duration + " ", x + (float)width - (float)mc.field_71466_p.func_78256_a(duration + " ") - (float)mc.field_71466_p.func_78256_a(effect.trim()), lineY, color);
                FontRendererHook.endFeatureFont();
                DrawUtils.drawText(effect.trim(), x + (float)width - (float)mc.field_71466_p.func_78256_a(effect.trim()), lineY, color);
            } else {
                DrawUtils.drawText(effect, x, lineY, color);
                FontRendererHook.setupFeatureFont(Feature.TAB_EFFECT_TIMERS);
                DrawUtils.drawText(duration, x + (float)mc.field_71466_p.func_78256_a(effect), lineY, color);
                FontRendererHook.endFeatureFont();
            }
            ++drawnCount;
        }
        for (TabEffect powerUp : powerupTimers) {
            lineY = topDown ? y + (float)spacer + (float)(drawnCount * lineHeight) : y + (float)height - (float)(drawnCount * lineHeight) - (float)spacer - 8.0f;
            effect = powerUp.getEffect();
            duration = powerUp.getDurationForDisplay();
            if (alignRight) {
                FontRendererHook.setupFeatureFont(Feature.TAB_EFFECT_TIMERS);
                DrawUtils.drawText(duration + " ", x + (float)width - (float)mc.field_71466_p.func_78256_a(duration + " ") - (float)mc.field_71466_p.func_78256_a(effect.trim()), lineY, color);
                FontRendererHook.endFeatureFont();
                DrawUtils.drawText(effect, x + (float)width - (float)mc.field_71466_p.func_78256_a(effect.trim()), lineY, color);
            } else {
                DrawUtils.drawText(effect, x, lineY, color);
                FontRendererHook.setupFeatureFont(Feature.TAB_EFFECT_TIMERS);
                DrawUtils.drawText(duration, x + (float)mc.field_71466_p.func_78256_a(effect), lineY, color);
                FontRendererHook.endFeatureFont();
            }
            ++drawnCount;
        }
        this.main.getUtils().restoreGLOptions();
    }

    private void renderItem(ItemStack item, float x, float y) {
        GlStateManager.func_179091_B();
        RenderHelper.func_74520_c();
        GlStateManager.func_179126_j();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)x, (float)y, (float)0.0f);
        Minecraft.func_71410_x().func_175599_af().func_175042_a(item, 0, 0);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
    }

    private void renderItemAndOverlay(ItemStack item, String name, float x, float y) {
        GlStateManager.func_179091_B();
        RenderHelper.func_74520_c();
        GlStateManager.func_179126_j();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)x, (float)y, (float)0.0f);
        Minecraft.func_71410_x().func_175599_af().func_175042_a(item, 0, 0);
        Minecraft.func_71410_x().func_175599_af().func_180453_a(Minecraft.func_71410_x().field_71466_p, item, 0, 0, name);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
    }

    public void drawItemPickupLog(float scale, ButtonLocation buttonLocation) {
        float x = this.main.getConfigValues().getActualX(Feature.ITEM_PICKUP_LOG);
        float y = this.main.getConfigValues().getActualY(Feature.ITEM_PICKUP_LOG);
        EnumUtils.AnchorPoint anchorPoint = this.main.getConfigValues().getAnchorPoint(Feature.ITEM_PICKUP_LOG);
        boolean downwards = anchorPoint == EnumUtils.AnchorPoint.TOP_RIGHT || anchorPoint == EnumUtils.AnchorPoint.TOP_LEFT;
        int lineHeight = 9;
        int height = lineHeight * 3 - 1;
        int width = Minecraft.func_71410_x().field_71466_p.func_78256_a("+ 1x Forceful Ember Chestplate");
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        this.main.getUtils().enableStandardGLOptions();
        int i = 0;
        Collection<ItemDiff> log = this.main.getInventoryUtils().getItemPickupLog();
        if (buttonLocation != null) {
            log = DUMMY_PICKUP_LOG;
        }
        for (ItemDiff itemDiff : log) {
            String text = String.format("%s %sx \u00a7r%s", itemDiff.getAmount() > 0 ? "\u00a7a+" : "\u00a7c-", Math.abs(itemDiff.getAmount()), itemDiff.getDisplayName());
            float stringY = y + (float)(i * lineHeight);
            if (!downwards) {
                stringY = y + (float)height - (float)(i * lineHeight) - 8.0f;
            }
            DrawUtils.drawText(text, x, stringY, -1);
            ++i;
        }
        this.main.getUtils().restoreGLOptions();
    }

    public void drawPowerOrbStatus(Minecraft mc, float scale, ButtonLocation buttonLocation) {
        PowerOrbManager.PowerOrbEntry activePowerOrb = PowerOrbManager.getInstance().getActivePowerOrb();
        if (buttonLocation != null) {
            activePowerOrb = PowerOrbManager.DUMMY_POWER_ORB_ENTRY;
        }
        if (activePowerOrb != null) {
            PowerOrb powerOrb = activePowerOrb.getPowerOrb();
            int seconds = activePowerOrb.getSeconds();
            EnumUtils.PowerOrbDisplayStyle displayStyle = this.main.getConfigValues().getPowerOrbDisplayStyle();
            if (displayStyle == EnumUtils.PowerOrbDisplayStyle.DETAILED) {
                this.drawDetailedPowerOrbStatus(mc, scale, buttonLocation, powerOrb, seconds);
            } else {
                this.drawCompactPowerOrbStatus(mc, scale, buttonLocation, powerOrb, seconds);
            }
        }
    }

    private void drawCompactPowerOrbStatus(Minecraft mc, float scale, ButtonLocation buttonLocation, PowerOrb powerOrb, int seconds) {
        float x = this.main.getConfigValues().getActualX(Feature.POWER_ORB_STATUS_DISPLAY);
        float y = this.main.getConfigValues().getActualY(Feature.POWER_ORB_STATUS_DISPLAY);
        String secondsString = String.format("\u00a7e%ss", seconds);
        int spacing = 1;
        int iconSize = mc.field_71466_p.field_78288_b * 3;
        int width = iconSize + spacing + mc.field_71466_p.func_78256_a(secondsString);
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, iconSize, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)iconSize, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        EntityArmorStand entity = null;
        if (PowerOrbManager.getInstance().getActivePowerOrb() != null && PowerOrbManager.getInstance().getActivePowerOrb().getUuid() != null) {
            entity = Utils.getEntityByUUID(PowerOrbManager.getInstance().getActivePowerOrb().getUuid());
        }
        if (entity == null && buttonLocation != null) {
            entity = this.getRadiantDummyArmorStand();
        }
        this.main.getUtils().enableStandardGLOptions();
        if (entity instanceof EntityArmorStand) {
            this.drawPowerOrbArmorStand(entity, x + 1.0f, y + 4.0f);
        } else {
            mc.func_110434_K().func_110577_a(powerOrb.getResourceLocation());
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, iconSize, iconSize, iconSize, iconSize);
        }
        DrawUtils.drawText(secondsString, x + (float)spacing + (float)iconSize, y + (float)iconSize / 2.0f - 4.0f, ColorCode.WHITE.getColor(255));
        this.main.getUtils().restoreGLOptions();
    }

    private void drawDetailedPowerOrbStatus(Minecraft mc, float scale, ButtonLocation buttonLocation, PowerOrb powerOrb, int seconds) {
        float x = this.main.getConfigValues().getActualX(Feature.POWER_ORB_STATUS_DISPLAY);
        float y = this.main.getConfigValues().getActualY(Feature.POWER_ORB_STATUS_DISPLAY);
        int maxHealth = this.main.getUtils().getAttributes().get((Object)Attribute.MAX_HEALTH).getValue();
        double healthRegen = (double)maxHealth * powerOrb.getHealthRegen();
        if (this.main.getUtils().getSlayerQuest() == EnumUtils.SlayerQuest.TARANTULA_BROODFATHER && this.main.getUtils().getSlayerQuestLevel() >= 2) {
            healthRegen *= 0.5;
        }
        double healIncrease = powerOrb.getHealIncrease() * 100.0;
        LinkedList<String> display = new LinkedList<String>();
        display.add(String.format("\u00a7c+%s \u2764/s", TextUtils.formatDouble(healthRegen)));
        if (powerOrb.getManaRegen() > 0.0) {
            int maxMana = this.main.getUtils().getAttributes().get((Object)Attribute.MAX_MANA).getValue();
            double manaRegen = Math.floorDiv(maxMana, 50);
            manaRegen += manaRegen * powerOrb.getManaRegen();
            display.add(String.format("\u00a7b+%s \u270e/s", TextUtils.formatDouble(manaRegen)));
        }
        if (powerOrb.getStrength() > 0) {
            display.add(String.format("\u00a74+%d \u2741", powerOrb.getStrength()));
        }
        if (healIncrease > 0.0) {
            display.add(String.format("\u00a72+%s%% Healing", TextUtils.formatDouble(healIncrease)));
        }
        Optional<String> longestLine = display.stream().max(Comparator.comparingInt(String::length));
        int spacingBetweenLines = 1;
        int iconSize = mc.field_71466_p.field_78288_b * 3;
        int iconAndSecondsHeight = iconSize + mc.field_71466_p.field_78288_b;
        int effectsHeight = (mc.field_71466_p.field_78288_b + spacingBetweenLines) * display.size();
        int width = iconSize + 2 + longestLine.map(arg_0 -> ((FontRenderer)mc.field_71466_p).func_78256_a(arg_0)).orElseGet(() -> mc.field_71466_p.func_78256_a((String)display.get(0)));
        int height = Math.max(effectsHeight, iconAndSecondsHeight);
        x = this.transformXY(x, width, scale);
        y = this.transformXY(y, height, scale);
        if (buttonLocation != null) {
            buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        EntityArmorStand entity = null;
        if (PowerOrbManager.getInstance().getActivePowerOrb() != null && PowerOrbManager.getInstance().getActivePowerOrb().getUuid() != null) {
            entity = Utils.getEntityByUUID(PowerOrbManager.getInstance().getActivePowerOrb().getUuid());
        }
        if (entity == null && buttonLocation != null) {
            entity = this.getRadiantDummyArmorStand();
        }
        this.main.getUtils().enableStandardGLOptions();
        if (entity instanceof EntityArmorStand) {
            this.drawPowerOrbArmorStand(entity, x + 1.0f, y + 4.0f);
        } else {
            mc.func_110434_K().func_110577_a(powerOrb.getResourceLocation());
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, iconSize, iconSize, iconSize, iconSize);
        }
        String secondsString = String.format("\u00a7e%ss", seconds);
        DrawUtils.drawText(secondsString, Math.round(x + (float)iconSize / 2.0f - (float)mc.field_71466_p.func_78256_a(secondsString) / 2.0f), y + (float)iconSize, ColorCode.WHITE.getColor(255));
        float startY = Math.round(y + (float)iconAndSecondsHeight / 2.0f - (float)effectsHeight / 2.0f);
        for (int i = 0; i < display.size(); ++i) {
            DrawUtils.drawText((String)display.get(i), x + (float)iconSize + 2.0f, startY + (float)(i * (mc.field_71466_p.field_78288_b + spacingBetweenLines)), ColorCode.WHITE.getColor(255));
        }
        this.main.getUtils().restoreGLOptions();
    }

    private int getAttribute(Attribute attribute) {
        return this.main.getUtils().getAttributes().get((Object)attribute).getValue();
    }

    @SubscribeEvent
    public void onRenderRemoveBars(RenderGameOverlayEvent.Pre e) {
        if (this.main.getUtils().isOnSkyblock() && this.main.getConfigValues().isEnabled(Feature.COMPACT_TAB_LIST) && e.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST && TabListParser.getRenderColumns() != null) {
            e.setCanceled(true);
            TabListRenderer.render();
        }
        if (e.type == RenderGameOverlayEvent.ElementType.ALL) {
            if (this.main.getUtils().isOnSkyblock()) {
                if (this.main.getConfigValues().isEnabled(Feature.HIDE_FOOD_ARMOR_BAR)) {
                    GuiIngameForge.renderFood = false;
                    GuiIngameForge.renderArmor = false;
                }
                if (this.main.getConfigValues().isEnabled(Feature.HIDE_HEALTH_BAR)) {
                    GuiIngameForge.renderHealth = false;
                }
                if (this.main.getConfigValues().isEnabled(Feature.HIDE_PET_HEALTH_BAR)) {
                    GuiIngameForge.renderHealthMount = false;
                }
            } else {
                if (this.main.getConfigValues().isEnabled(Feature.HIDE_HEALTH_BAR)) {
                    GuiIngameForge.renderHealth = true;
                }
                if (this.main.getConfigValues().isEnabled(Feature.HIDE_FOOD_ARMOR_BAR)) {
                    GuiIngameForge.renderArmor = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if (this.guiToOpen == EnumUtils.GUIType.MAIN) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new SkyblockAddonsGui(this.guiPageToOpen, this.guiTabToOpen));
        } else if (this.guiToOpen == EnumUtils.GUIType.EDIT_LOCATIONS) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new LocationEditGui(this.guiPageToOpen, this.guiTabToOpen));
        } else if (this.guiToOpen == EnumUtils.GUIType.SETTINGS) {
            if (this.guiFeatureToOpen == Feature.ENCHANTMENT_LORE_PARSING) {
                Minecraft.func_71410_x().func_147108_a((GuiScreen)new EnchantmentSettingsGui(this.guiFeatureToOpen, 1, this.guiPageToOpen, this.guiTabToOpen, this.guiFeatureToOpen.getSettings()));
            } else {
                Minecraft.func_71410_x().func_147108_a((GuiScreen)new SettingsGui(this.guiFeatureToOpen, 1, this.guiPageToOpen, this.guiTabToOpen, this.guiFeatureToOpen.getSettings()));
            }
        } else if (this.guiToOpen == EnumUtils.GUIType.WARP) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new IslandWarpGui());
        }
        this.guiToOpen = null;
    }

    public void setGuiToOpen(EnumUtils.GUIType guiToOpen) {
        this.guiToOpen = guiToOpen;
    }

    public void setGuiToOpen(EnumUtils.GUIType guiToOpen, int page, EnumUtils.GuiTab tab) {
        this.guiToOpen = guiToOpen;
        this.guiPageToOpen = page;
        this.guiTabToOpen = tab;
    }

    public void setGuiToOpen(EnumUtils.GUIType guiToOpen, int page, EnumUtils.GuiTab tab, Feature feature) {
        this.setGuiToOpen(guiToOpen, page, tab);
        this.guiFeatureToOpen = feature;
    }

    public void setSubtitleFeature(Feature subtitleFeature) {
        this.subtitleFeature = subtitleFeature;
    }

    public float transformXY(float xy, int widthHeight, float scale) {
        float minecraftScale = new ScaledResolution(Minecraft.func_71410_x()).func_78325_e();
        xy -= (float)widthHeight / 2.0f * scale;
        xy = (float)Math.round(xy * minecraftScale) / minecraftScale;
        return xy / scale;
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent e) {
        Minecraft mc = Minecraft.func_71410_x();
        float partialTicks = e.partialTicks;
        HealingCircleManager.renderHealingCircleOverlays(partialTicks);
        if (this.main.getUtils().isOnSkyblock() && this.main.getUtils().isInDungeon() && (this.main.getConfigValues().isEnabled(Feature.SHOW_CRITICAL_DUNGEONS_TEAMMATES) || this.main.getConfigValues().isEnabled(Feature.SHOW_DUNGEON_TEAMMATE_NAME_OVERLAY))) {
            Entity renderViewEntity = mc.func_175606_aa();
            Vector3d viewPosition = Utils.getPlayerViewPosition();
            int iconSize = 25;
            for (EntityPlayer entity : mc.field_71441_e.field_73010_i) {
                if (renderViewEntity == entity || !this.main.getDungeonManager().getTeammates().containsKey(entity.func_70005_c_())) continue;
                DungeonPlayer dungeonPlayer = this.main.getDungeonManager().getTeammates().get(entity.func_70005_c_());
                double x = MathUtils.interpolateX((Entity)entity, partialTicks);
                double y = MathUtils.interpolateY((Entity)entity, partialTicks);
                double z = MathUtils.interpolateZ((Entity)entity, partialTicks);
                x -= viewPosition.x;
                y -= viewPosition.y;
                z -= viewPosition.z;
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_DUNGEON_TEAMMATE_NAME_OVERLAY)) {
                    y += (double)0.35f;
                }
                if (entity.func_70093_af()) {
                    y -= (double)0.65f;
                }
                double distanceScale = Math.max(1.0, renderViewEntity.func_174791_d().func_72438_d(entity.func_174791_d()) / 10.0);
                y = this.main.getConfigValues().isEnabled(Feature.MAKE_DUNGEON_TEAMMATES_GLOW) ? (y += (double)(entity.field_70131_O + 0.75f) + (double)iconSize * distanceScale / 40.0) : (y += (double)(entity.field_70131_O / 2.0f + 0.25f));
                float f = 1.6f;
                float f1 = 0.016666668f * f;
                GlStateManager.func_179094_E();
                GlStateManager.func_179137_b((double)x, (double)y, (double)z);
                GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.func_179114_b((float)(-mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.func_179114_b((float)mc.func_175598_ae().field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.func_179152_a((float)(-f1), (float)(-f1), (float)f1);
                GlStateManager.func_179139_a((double)distanceScale, (double)distanceScale, (double)distanceScale);
                GlStateManager.func_179140_f();
                GlStateManager.func_179132_a((boolean)false);
                GlStateManager.func_179097_i();
                GlStateManager.func_179147_l();
                GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
                GlStateManager.func_179098_w();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.func_179141_d();
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_CRITICAL_DUNGEONS_TEAMMATES) && !dungeonPlayer.isGhost() && (dungeonPlayer.isCritical() || dungeonPlayer.isLow())) {
                    Tessellator tessellator = Tessellator.func_178181_a();
                    WorldRenderer worldrenderer = tessellator.func_178180_c();
                    mc.func_110434_K().func_110577_a(CRITICAL);
                    worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                    worldrenderer.func_181662_b((double)((float)(-iconSize) / 2.0f), (double)((float)(-iconSize) / 2.0f), 0.0).func_181673_a(0.0, 0.0).func_181675_d();
                    worldrenderer.func_181662_b((double)((float)(-iconSize) / 2.0f), (double)((float)iconSize / 2.0f), 0.0).func_181673_a(0.0, 1.0).func_181675_d();
                    worldrenderer.func_181662_b((double)((float)iconSize / 2.0f), (double)((float)iconSize / 2.0f), 0.0).func_181673_a(1.0, 1.0).func_181675_d();
                    worldrenderer.func_181662_b((double)((float)iconSize / 2.0f), (double)((float)(-iconSize) / 2.0f), 0.0).func_181673_a(1.0, 0.0).func_181675_d();
                    tessellator.func_78381_a();
                    String text = "";
                    if (dungeonPlayer.isLow()) {
                        text = "LOW";
                    } else if (dungeonPlayer.isCritical()) {
                        text = "CRITICAL";
                    }
                    mc.field_71466_p.func_175065_a(text, (float)(-mc.field_71466_p.func_78256_a(text)) / 2.0f, (float)iconSize / 2.0f + 2.0f, -1, true);
                }
                if (!dungeonPlayer.isGhost() && this.main.getConfigValues().isEnabled(Feature.SHOW_DUNGEON_TEAMMATE_NAME_OVERLAY)) {
                    String nameOverlay = (Object)((Object)ColorCode.YELLOW) + "[" + dungeonPlayer.getDungeonClass().getFirstLetter() + "] " + (Object)((Object)ColorCode.GREEN) + entity.func_70005_c_();
                    mc.field_71466_p.func_175065_a(nameOverlay, (float)(-mc.field_71466_p.func_78256_a(nameOverlay)) / 2.0f, (float)iconSize / 2.0f + 13.0f, -1, true);
                }
                GlStateManager.func_179126_j();
                GlStateManager.func_179132_a((boolean)true);
                GlStateManager.func_179145_e();
                GlStateManager.func_179084_k();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.func_179121_F();
            }
        }
    }

    private void drawPowerOrbArmorStand(EntityArmorStand powerOrbArmorStand, float x, float y) {
        float yaw;
        float prevRenderYawOffset = powerOrbArmorStand.field_70761_aq;
        float prevPrevRenderYawOffset = powerOrbArmorStand.field_70760_ar;
        GlStateManager.func_179094_E();
        GlStateManager.func_179126_j();
        GlStateManager.func_179142_g();
        GlStateManager.func_179109_b((float)(x + 12.5f), (float)(y + 50.0f), (float)50.0f);
        GlStateManager.func_179152_a((float)-25.0f, (float)25.0f, (float)25.0f);
        GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)22.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
        rendermanager.func_178631_a(180.0f);
        boolean shadowsEnabled = rendermanager.func_178627_a();
        rendermanager.func_178633_a(false);
        powerOrbArmorStand.func_82142_c(true);
        powerOrbArmorStand.field_70761_aq = yaw = (float)(System.currentTimeMillis() % 1750L) / 1750.0f * 360.0f;
        powerOrbArmorStand.field_70760_ar = yaw;
        rendermanager.func_147940_a((Entity)powerOrbArmorStand, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        rendermanager.func_178633_a(shadowsEnabled);
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
        GlStateManager.func_179121_F();
        powerOrbArmorStand.field_70761_aq = prevRenderYawOffset;
        powerOrbArmorStand.field_70760_ar = prevPrevRenderYawOffset;
    }

    private void drawEntity(EntityLivingBase entity, float x, float y, float yaw) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179126_j();
        GlStateManager.func_179109_b((float)x, (float)y, (float)50.0f);
        GlStateManager.func_179152_a((float)-25.0f, (float)25.0f, (float)25.0f);
        GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)15.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        RenderHelper.func_74520_c();
        entity.field_70761_aq = yaw;
        entity.field_70760_ar = yaw;
        entity.field_70759_as = yaw;
        entity.field_70758_at = yaw;
        RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
        rendermanager.func_178631_a(180.0f);
        boolean shadowsEnabled = rendermanager.func_178627_a();
        rendermanager.func_178633_a(false);
        rendermanager.func_147940_a((Entity)entity, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        rendermanager.func_178633_a(shadowsEnabled);
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
        GlStateManager.func_179121_F();
    }

    public EntityArmorStand getRadiantDummyArmorStand() {
        if (radiantDummyArmorStand != null) {
            return radiantDummyArmorStand;
        }
        radiantDummyArmorStand = new EntityArmorStand((World)Utils.getDummyWorld());
        ItemStack orbItemStack = ItemUtils.createSkullItemStack(null, null, "3ae3572b-2679-40b4-ba50-14dd58cbbbf7", "7ab4c4d6ee69bc24bba2b8faf67b9f704a06b01aa93f3efa6aef7a9696c4feef");
        radiantDummyArmorStand.func_70062_b(4, orbItemStack);
        return radiantDummyArmorStand;
    }

    public boolean isPredictHealth() {
        return this.predictHealth;
    }

    public void setPredictHealth(boolean predictHealth) {
        this.predictHealth = predictHealth;
    }

    public boolean isPredictMana() {
        return this.predictMana;
    }

    public void setPredictMana(boolean predictMana) {
        this.predictMana = predictMana;
    }

    public void setUpdateMessageDisplayed(boolean updateMessageDisplayed) {
        this.updateMessageDisplayed = updateMessageDisplayed;
    }

    public Feature getTitleFeature() {
        return this.titleFeature;
    }

    public void setTitleFeature(Feature titleFeature) {
        this.titleFeature = titleFeature;
    }

    public void setArrowsLeft(int arrowsLeft) {
        this.arrowsLeft = arrowsLeft;
    }

    public void setCannotReachMobName(String cannotReachMobName) {
        this.cannotReachMobName = cannotReachMobName;
    }

    public void setSkillFadeOutTime(long skillFadeOutTime) {
        this.skillFadeOutTime = skillFadeOutTime;
    }

    public void setSkill(SkillType skill) {
        this.skill = skill;
    }

    public void setSkillText(String skillText) {
        this.skillText = skillText;
    }

    static {
        DUMMY_PICKUP_LOG = new ArrayList<ItemDiff>(Arrays.asList(new ItemDiff((Object)((Object)ColorCode.DARK_PURPLE) + "Forceful Ember Chestplate", 1), new ItemDiff("Boat", -1), new ItemDiff((Object)((Object)ColorCode.BLUE) + "Aspect of the End", 1)));
    }
}

