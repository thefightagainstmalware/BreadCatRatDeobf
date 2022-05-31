/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.Translations;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public class EnumUtils {

    public static enum SlayerQuest {
        REVENANT_HORROR("Revenant Horror"),
        TARANTULA_BROODFATHER("Tarantula Broodfather"),
        SVEN_PACKMASTER("Sven Packmaster"),
        VOIDGLOOM_SERAPH("Voidgloom Seraph");

        private final String scoreboardName;

        private SlayerQuest(String scoreboardName) {
            this.scoreboardName = scoreboardName;
        }

        public static SlayerQuest fromName(String scoreboardName) {
            for (SlayerQuest slayerQuest : SlayerQuest.values()) {
                if (!slayerQuest.scoreboardName.equals(scoreboardName)) continue;
                return slayerQuest;
            }
            return null;
        }
    }

    public static enum DiscordStatusEntry {
        DETAILS(0),
        STATE(1);

        private final int id;

        private DiscordStatusEntry(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static enum ChromaMode {
        ALL_SAME_COLOR(Message.CHROMA_MODE_ALL_THE_SAME),
        FADE(Message.CHROME_MODE_FADE);

        private final Message message;

        private ChromaMode(Message message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message.getMessage(new String[0]);
        }

        public ChromaMode getNextType() {
            int nextType = this.ordinal() + 1;
            if (nextType > ChromaMode.values().length - 1) {
                nextType = 0;
            }
            return ChromaMode.values()[nextType];
        }
    }

    public static enum GUIType {
        MAIN,
        EDIT_LOCATIONS,
        SETTINGS,
        WARP;

    }

    public static enum Social {
        YOUTUBE("youtube", "https://www.youtube.com/channel/UCYmE9-052frn0wQwqa6i8_Q"),
        DISCORD("discord", "https://biscuit.codes/discord"),
        GITHUB("github", "https://github.com/BiscuitDevelopment/SkyblockAddons"),
        PATREON("patreon", "https://www.patreon.com/biscuitdev");

        private final ResourceLocation resourceLocation;
        private URI url;

        private Social(String resourcePath, String url) {
            this.resourceLocation = new ResourceLocation("skyblockaddons", "gui/" + resourcePath + ".png");
            try {
                this.url = new URI(url);
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        public ResourceLocation getResourceLocation() {
            return this.resourceLocation;
        }

        public URI getUrl() {
            return this.url;
        }
    }

    public static enum DrawType {
        SKELETON_BAR,
        BAR,
        TEXT,
        PICKUP_LOG,
        DEFENCE_ICON,
        REVENANT_PROGRESS,
        POWER_ORB_DISPLAY,
        TICKER,
        BAIT_LIST_DISPLAY,
        TAB_EFFECT_TIMERS,
        DUNGEONS_MAP,
        SLAYER_TRACKERS,
        DRAGON_STATS_TRACKER,
        PROXIMITY_INDICATOR;

    }

    public static enum FeatureCredit {
        INVENTIVE_TALENT("InventiveTalent", "inventivetalent.org", Feature.MAGMA_BOSS_TIMER),
        ORCHID_ALLOY("orchidalloy", "github.com/orchidalloy", Feature.SUMMONING_EYE_ALERT, Feature.FISHING_SOUND_INDICATOR, Feature.ENCHANTMENT_LORE_PARSING),
        HIGH_CRIT("HighCrit", "github.com/HighCrit", Feature.PREVENT_MOVEMENT_ON_DEATH),
        MOULBERRY("Moulberry", "github.com/Moulberry", Feature.DONT_RESET_CURSOR_INVENTORY),
        TOMOCRAFTER("tomocrafter", "github.com/tomocrafter", Feature.AVOID_BLINKING_NIGHT_VISION, Feature.SLAYER_INDICATOR, Feature.NO_ARROWS_LEFT_ALERT, Feature.BOSS_APPROACH_ALERT),
        DAPIGGUY("DaPigGuy", "github.com/DaPigGuy", Feature.MINION_DISABLE_LOCATION_WARNING),
        COMNIEMEER("comniemeer", "github.com/comniemeer", Feature.JUNGLE_AXE_COOLDOWN),
        KEAGEL("Keagel", "github.com/Keagel", Feature.DISABLE_MAGICAL_SOUP_MESSAGES),
        SUPERHIZE("SuperHiZe", "github.com/superhize", Feature.SPECIAL_ZEALOT_ALERT),
        DIDI_SKYWALKER("DidiSkywalker", "twitter.com/didiskywalker", Feature.ITEM_PICKUP_LOG, Feature.HEALTH_UPDATES, Feature.REPLACE_ROMAN_NUMERALS_WITH_NUMBERS, Feature.POWER_ORB_STATUS_DISPLAY),
        P0KE("P0ke", "p0ke.dev", Feature.ZEALOT_COUNTER),
        BERISAN("Berisan", "github.com/Berisan", Feature.TAB_EFFECT_TIMERS),
        MYNAMEISJEFF("MyNameIsJeff", "github.com/My-Name-Is-Jeff", Feature.SHOW_BROKEN_FRAGMENTS),
        DJTHEREDSTONER("DJtheRedstoner", "github.com/DJtheRedstoner", Feature.LEGENDARY_SEA_CREATURE_WARNING, Feature.HIDE_SVEN_PUP_NAMETAGS),
        CHARZARD("Charzard4261", "github.com/Charzard4261", Feature.DISABLE_TELEPORT_PAD_MESSAGES, Feature.BAIT_LIST, Feature.SHOW_BASE_STAT_BOOST_PERCENTAGE, Feature.SHOW_ITEM_DUNGEON_FLOOR, Feature.SHOW_BASE_STAT_BOOST_PERCENTAGE, Feature.SHOW_RARITY_UPGRADED, Feature.REVENANT_SLAYER_TRACKER, Feature.TARANTULA_SLAYER_TRACKER, Feature.SVEN_SLAYER_TRACKER, Feature.DRAGON_STATS_TRACKER, Feature.SHOW_PERSONAL_COMPACTOR_PREVIEW, Feature.SHOW_STACKING_ENCHANT_PROGRESS, Feature.STOP_BONZO_STAFF_SOUNDS, Feature.DISABLE_MORT_MESSAGES, Feature.DISABLE_BOSS_MESSAGES),
        IHDEVELOPER("iHDeveloper", "github.com/iHDeveloper", Feature.SHOW_DUNGEON_MILESTONE, Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY, Feature.SHOW_DUNGEON_TEAMMATE_NAME_OVERLAY, Feature.DUNGEONS_SECRETS_DISPLAY, Feature.SHOW_SALVAGE_ESSENCES_COUNTER, Feature.SHOW_SWORD_KILLS, Feature.ENCHANTMENTS_HIGHLIGHT),
        TIRELESS_TRAVELER("TirelessTraveler", "github.com/ILikePlayingGames", Feature.DUNGEON_DEATH_COUNTER),
        KAASBROODJU("kaasbroodju", "github.com/kaasbroodju", Feature.SKILL_PROGRESS_BAR, Feature.SHOW_SKILL_PERCENTAGE_INSTEAD_OF_XP, Feature.SHOW_SKILL_XP_GAINED),
        PHOUBE("Phoube", "github.com/Phoube", Feature.HIDE_OTHER_PLAYERS_PRESENTS, Feature.SHOW_EXPERIMENTATION_TABLE_TOOLTIPS, Feature.DRILL_FUEL_BAR, Feature.DRILL_FUEL_TEXT, Feature.FISHING_PARTICLE_OVERLAY, Feature.COOLDOWN_PREDICTION, Feature.BIGGER_WAKE, Feature.TREVOR_HIGHLIGHT_TRACKED_ENTITY, Feature.TREVOR_SHOW_QUEST_COOLDOWN),
        PEDRO9558("Pedro9558", "github.com/Pedro9558", Feature.TREVOR_TRACKED_ENTITY_PROXIMITY_INDICATOR, Feature.TREVOR_THE_TRAPPER_FEATURES, Feature.FETCHUR_TODAY, Feature.STOP_RAT_SOUNDS);

        private final Set<Feature> features;
        private final String author;
        private final String url;

        private FeatureCredit(String author, String url, Feature ... features) {
            this.features = EnumSet.of(features[0], features);
            this.author = author;
            this.url = url;
        }

        public static FeatureCredit fromFeature(Feature feature) {
            for (FeatureCredit credit : FeatureCredit.values()) {
                if (!credit.features.contains((Object)feature)) continue;
                return credit;
            }
            return null;
        }

        public String getAuthor() {
            return "Contrib. " + this.author;
        }

        public String getUrl() {
            return "https://" + this.url;
        }
    }

    public static enum FeatureSetting {
        COLOR(Message.SETTING_CHANGE_COLOR, -1),
        GUI_SCALE(Message.SETTING_GUI_SCALE, -1),
        GUI_SCALE_X("settings.guiScaleX", -1),
        GUI_SCALE_Y("settings.guiScaleY", -1),
        ENABLED_IN_OTHER_GAMES(Message.SETTING_SHOW_IN_OTHER_GAMES, -1),
        REPEATING(Message.SETTING_REPEATING, -1),
        TEXT_MODE(Message.SETTING_TEXT_MODE, -1),
        DRAGONS_NEST_ONLY(Message.SETTING_DRAGONS_NEST_ONLY, -1),
        USE_VANILLA_TEXTURE(Message.SETTING_USE_VANILLA_TEXTURE, 17),
        BACKPACK_STYLE(Message.SETTING_BACKPACK_STYLE, -1),
        SHOW_ONLY_WHEN_HOLDING_SHIFT(Message.SETTING_SHOW_ONLY_WHEN_HOLDING_SHIFT, 18),
        MAKE_INVENTORY_COLORED(Message.SETTING_MAKE_BACKPACK_INVENTORIES_COLORED, 43),
        POWER_ORB_DISPLAY_STYLE(Message.SETTING_POWER_ORB_DISPLAY_STYLE, -1),
        CHANGE_BAR_COLOR_WITH_POTIONS(Message.SETTING_CHANGE_BAR_COLOR_WITH_POTIONS, 46),
        ENABLE_MESSAGE_WHEN_ACTION_PREVENTED(Message.SETTING_ENABLE_MESSAGE_WHEN_ACTION_PREVENTED, -1),
        HIDE_NIGHT_VISION_EFFECT(Message.SETTING_HIDE_NIGHT_VISION_EFFECT_TIMER, 70),
        ENABLE_CAKE_BAG_PREVIEW(Message.SETTING_SHOW_CAKE_BAG_PREVIEW, 71),
        SORT_TAB_EFFECT_TIMERS(Message.SETTING_SORT_TAB_EFFECT_TIMERS, 74),
        ROTATE_MAP(Message.SETTING_ROTATE_MAP, 100),
        CENTER_ROTATION_ON_PLAYER(Message.SETTING_CENTER_ROTATION_ON_PLAYER, 101),
        MAP_ZOOM(Message.SETTING_MAP_ZOOM, -1),
        COLOUR_BY_RARITY(Message.SETTING_COLOR_BY_RARITY, -1),
        SHOW_PLAYER_HEADS_ON_MAP(Message.SETTING_SHOW_PLAYER_HEAD_ON_MAP, 106),
        SHOW_GLOWING_ITEMS_ON_ISLAND(Message.SETTING_SHOW_GLOWING_ITEMS_ON_ISLAND, 109),
        SKILL_ACTIONS_LEFT_UNTIL_NEXT_LEVEL(Message.SETTING_SKILL_ACTIONS_LEFT_UNTIL_NEXT_LEVEL, 115),
        HIDE_WHEN_NOT_IN_CRYPTS("settings.hideWhenNotDoingQuest", 133),
        HIDE_WHEN_NOT_IN_SPIDERS_DEN("settings.hideWhenNotDoingQuest", 134),
        HIDE_WHEN_NOT_IN_CASTLE("settings.hideWhenNotDoingQuest", 135),
        ENABLE_PERSONAL_COMPACTOR_PREVIEW(Message.SETTING_SHOW_PERSONAL_COMPACTOR_PREVIEW, 137),
        SHOW_SKILL_PERCENTAGE_INSTEAD_OF_XP("settings.showSkillPercentageInstead", 144),
        SHOW_SKILL_XP_GAINED("settings.showSkillXPGained", 145),
        SHOW_SALVAGE_ESSENCES_COUNTER("settings.showSalvageEssencesCounter", 146),
        HEALING_CIRCLE_OPACITY("settings.healingCircleOpacity", 156),
        COOLDOWN_PREDICTION("settings.cooldownPrediction", 164),
        PERFECT_ENCHANT_COLOR("enchants.superTier", 165),
        GREAT_ENCHANT_COLOR("enchants.highTier", 166),
        GOOD_ENCHANT_COLOR("enchants.midTier", 167),
        POOR_ENCHANT_COLOR("enchants.lowTier", 168),
        COMMA_ENCHANT_COLOR("enchants.commas", 171),
        LEVEL_100_LEG_MONKEY("settings.legendaryMonkeyLevel100", 169),
        BIGGER_WAKE("settings.biggerWake", 170),
        HIGHLIGHT_ENCHANTMENTS("settings.highlightSpecialEnchantments", 153),
        HIDE_ENCHANTMENT_LORE("settings.hideEnchantDescription", 176),
        HIDE_GREY_ENCHANTS(Message.SETTING_HIDE_GREY_ENCHANTS, 87),
        ENCHANT_LAYOUT("enchantLayout.title", 0),
        TREVOR_TRACKED_ENTITY_PROXIMITY_INDICATOR("settings.trevorTheTrapper.trackedEntityProximityIndicator", 173),
        TREVOR_HIGHLIGHT_TRACKED_ENTITY("settings.trevorTheTrapper.highlightTrackedEntity", 174),
        TREVOR_SHOW_QUEST_COOLDOWN("settings.trevorTheTrapper.showQuestCooldown", 175),
        SHOW_FETCHUR_ONLY_IN_DWARVENS(Message.SETTING_SHOW_FETCHUR_IN_DWARVEN_ONLY, 179),
        SHOW_FETCHUR_ITEM_NAME(Message.SETTING_SHOW_FETCHUR_ITEM_NAME, 180),
        SHOW_FETCHUR_INVENTORY_OPEN_ONLY(Message.SETTING_SHOW_FETCHUR_INVENTORY_OPEN_ONLY, 181),
        WARN_WHEN_FETCHUR_CHANGES(Message.SETTING_WARN_WHEN_FETCHUR_CHANGES, 182),
        STOP_ONLY_RAT_SQUEAK(Message.SETTING_STOP_ONLY_RAT_SQUEAK, 184),
        SHOW_ENDER_CHEST_PREVIEW("settings.showEnderChestPreview", 185),
        HIDE_WHEN_NOT_IN_END("settings.hideWhenNotDoingQuest", 187),
        HEALTH_PREDICTION("settings.vanillaHealthPrediction", 194),
        DUNGEON_PLAYER_GLOW("settings.outlineDungeonTeammates", 103),
        ITEM_GLOW("settings.glowingDroppedItems", 109),
        ABBREVIATE_SKILL_XP_DENOMINATOR("settings.abbreviateSkillXpDenominator", 198),
        OTHER_DEFENCE_STATS("settings.otherDefenseStats", 199),
        DISCORD_RP_STATE((Message)null, 0),
        DISCORD_RP_DETAILS((Message)null, 0);

        private Message message;
        private final int featureEquivalent;
        private String messagePath;

        private FeatureSetting(Message message, int featureEquivalent) {
            this.message = message;
            this.featureEquivalent = featureEquivalent;
        }

        private FeatureSetting(String messagePath, int featureEquivalent) {
            this.messagePath = messagePath;
            this.featureEquivalent = featureEquivalent;
        }

        public Feature getFeatureEquivalent() {
            if (this.featureEquivalent == -1) {
                return null;
            }
            for (Feature feature : Feature.values()) {
                if (feature.getId() != this.featureEquivalent) continue;
                return feature;
            }
            return null;
        }

        public String getMessage(String ... variables) {
            if (this.messagePath != null) {
                return Translations.getMessage(this.messagePath, variables);
            }
            return this.message.getMessage(variables);
        }
    }

    public static enum GuiTab {
        MAIN,
        GENERAL_SETTINGS;

    }

    public static enum MagmaEvent {
        MAGMA_WAVE("magma"),
        BLAZE_WAVE("blaze"),
        BOSS_SPAWN("spawn"),
        BOSS_DEATH("death"),
        PING("ping");

        private final String inventiveTalentEvent;

        private MagmaEvent(String inventiveTalentEvent) {
            this.inventiveTalentEvent = inventiveTalentEvent;
        }

        public String getInventiveTalentEvent() {
            return this.inventiveTalentEvent;
        }
    }

    public static enum MagmaTimerAccuracy {
        NO_DATA("N/A"),
        SPAWNED("NOW"),
        SPAWNED_PREDICTION("NOW"),
        EXACTLY(""),
        ABOUT("");

        private final String symbol;

        private MagmaTimerAccuracy(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return this.symbol;
        }
    }

    public static enum TextStyle {
        STYLE_ONE(Message.TEXT_STYLE_ONE),
        STYLE_TWO(Message.TEXT_STYLE_TWO);

        private final Message message;

        private TextStyle(Message message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message.getMessage(new String[0]);
        }

        public TextStyle getNextType() {
            int nextType = this.ordinal() + 1;
            if (nextType > TextStyle.values().length - 1) {
                nextType = 0;
            }
            return TextStyle.values()[nextType];
        }
    }

    public static enum PowerOrbDisplayStyle {
        DETAILED(Message.POWER_ORB_DISPLAY_STYLE_DETAILED),
        COMPACT(Message.POWER_ORB_DISPLAY_STYLE_COMPACT);

        private final Message message;

        private PowerOrbDisplayStyle(Message message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message.getMessage(new String[0]);
        }

        public PowerOrbDisplayStyle getNextType() {
            int nextType = this.ordinal() + 1;
            if (nextType > PowerOrbDisplayStyle.values().length - 1) {
                nextType = 0;
            }
            return PowerOrbDisplayStyle.values()[nextType];
        }
    }

    public static enum BackpackStyle {
        GUI(Message.BACKPACK_STYLE_REGULAR),
        BOX(Message.BACKPACK_STYLE_COMPACT);

        private final Message message;

        private BackpackStyle(Message message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message.getMessage(new String[0]);
        }

        public BackpackStyle getNextType() {
            int nextType = this.ordinal() + 1;
            if (nextType > BackpackStyle.values().length - 1) {
                nextType = 0;
            }
            return BackpackStyle.values()[nextType];
        }
    }

    public static enum ButtonType {
        TOGGLE,
        SOLID,
        CHROMA_SLIDER;

    }

    public static enum AnchorPoint {
        TOP_LEFT(0),
        TOP_RIGHT(1),
        BOTTOM_LEFT(2),
        BOTTOM_RIGHT(3),
        BOTTOM_MIDDLE(4);

        private final int id;

        private AnchorPoint(int id) {
            this.id = id;
        }

        public static AnchorPoint fromId(int id) {
            for (AnchorPoint feature : AnchorPoint.values()) {
                if (feature.getId() != id) continue;
                return feature;
            }
            return null;
        }

        public int getX(int maxX) {
            int x = 0;
            switch (this) {
                case TOP_RIGHT: 
                case BOTTOM_RIGHT: {
                    x = maxX;
                    break;
                }
                case BOTTOM_MIDDLE: {
                    x = maxX / 2;
                }
            }
            return x;
        }

        public int getY(int maxY) {
            int y = 0;
            switch (this) {
                case BOTTOM_RIGHT: 
                case BOTTOM_MIDDLE: 
                case BOTTOM_LEFT: {
                    y = maxY;
                }
            }
            return y;
        }

        public int getId() {
            return this.id;
        }
    }
}

