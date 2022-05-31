/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ReportedException
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.apache.commons.lang3.mutable.MutableFloat
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.apache.commons.lang3.text.WordUtils
 *  org.apache.logging.log4j.Logger
 */
package codes.biscuit.skyblockaddons.config;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Language;
import codes.biscuit.skyblockaddons.core.chroma.ManualChromaManager;
import codes.biscuit.skyblockaddons.features.discordrpc.DiscordStatus;
import codes.biscuit.skyblockaddons.features.enchants.EnchantListLayout;
import codes.biscuit.skyblockaddons.features.enchants.EnchantManager;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockColor;
import codes.biscuit.skyblockaddons.utils.objects.FloatPair;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.geom.Point2D;
import java.beans.Introspector;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.Logger;

public class ConfigValues {
    private static final int CONFIG_VERSION = 9;
    private static final float DEFAULT_GUI_SCALE = ConfigValues.normalizeValueNoStep(1.0f);
    private static final float GUI_SCALE_MINIMUM = 0.5f;
    private static final float GUI_SCALE_MAXIMUM = 5.0f;
    private static final ReentrantLock SAVE_LOCK = new ReentrantLock();
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private final Logger logger = SkyblockAddons.getLogger();
    private final Map<Feature, FloatPair> defaultCoordinates = new EnumMap<Feature, FloatPair>(Feature.class);
    private final Map<Feature, EnumUtils.AnchorPoint> defaultAnchorPoints = new EnumMap<Feature, EnumUtils.AnchorPoint>(Feature.class);
    private final Map<Feature, Float> defaultGuiScales = new EnumMap<Feature, Float>(Feature.class);
    private final Map<Feature, FloatPair> defaultBarSizes = new EnumMap<Feature, FloatPair>(Feature.class);
    private final File settingsConfigFile;
    private JsonObject loadedConfig = new JsonObject();
    private JsonObject languageConfig = new JsonObject();
    private final Set<Feature> disabledFeatures = EnumSet.noneOf(Feature.class);
    private final Map<Feature, Integer> colors = new HashMap<Feature, Integer>();
    private Map<Feature, Float> guiScales = new EnumMap<Feature, Float>(Feature.class);
    private final Map<Feature, FloatPair> barSizes = new EnumMap<Feature, FloatPair>(Feature.class);
    private final MutableInt warningSeconds = new MutableInt(4);
    private final Map<Feature, FloatPair> coordinates = new EnumMap<Feature, FloatPair>(Feature.class);
    private Map<Feature, EnumUtils.AnchorPoint> anchorPoints = new EnumMap<Feature, EnumUtils.AnchorPoint>(Feature.class);
    private final MutableObject<Language> language = new MutableObject((Object)Language.ENGLISH);
    private final MutableObject<EnumUtils.BackpackStyle> backpackStyle = new MutableObject((Object)EnumUtils.BackpackStyle.GUI);
    private final MutableObject<EnumUtils.PowerOrbDisplayStyle> powerOrbDisplayStyle = new MutableObject((Object)EnumUtils.PowerOrbDisplayStyle.COMPACT);
    private final MutableObject<EnumUtils.TextStyle> textStyle = new MutableObject((Object)EnumUtils.TextStyle.STYLE_ONE);
    private final Map<String, Set<Integer>> profileLockedSlots = new HashMap<String, Set<Integer>>();
    private final Set<Feature> chromaFeatures = EnumSet.noneOf(Feature.class);
    @Deprecated
    private final MutableFloat oldChromaSpeed = new MutableFloat(0.19354838f);
    private final MutableObject<EnumUtils.ChromaMode> chromaMode = new MutableObject((Object)EnumUtils.ChromaMode.FADE);
    private final MutableFloat chromaFadeWidth = new MutableFloat(0.22580644f);
    private final MutableObject<DiscordStatus> discordDetails = new MutableObject((Object)DiscordStatus.LOCATION);
    private final MutableObject<DiscordStatus> discordStatus = new MutableObject((Object)DiscordStatus.AUTO_STATUS);
    private final MutableObject<DiscordStatus> discordAutoDefault = new MutableObject((Object)DiscordStatus.NONE);
    private final List<String> discordCustomStatuses = new ArrayList<String>();
    private final MutableFloat mapZoom = new MutableFloat(0.18478261f);
    private final MutableFloat healingCircleOpacity = new MutableFloat((Number)0.4);
    private MutableFloat chromaSize = new MutableFloat(30.0f);
    private final MutableFloat chromaSpeed = new MutableFloat(6.0f);
    private final MutableFloat chromaSaturation = new MutableFloat(0.75f);
    private final MutableFloat chromaBrightness = new MutableFloat(0.9f);
    private final MutableObject<EnchantListLayout> enchantLayout = new MutableObject((Object)EnchantListLayout.NORMAL);

    public ConfigValues(File settingsConfigFile) {
        this.settingsConfigFile = settingsConfigFile;
    }

    public void loadValues() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("default.json");
             InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);){
            JsonObject defaultValues = (JsonObject)SkyblockAddons.getGson().fromJson((Reader)inputStreamReader, JsonObject.class);
            this.deserializeFeatureFloatCoordsMapFromID(defaultValues, this.defaultCoordinates, "coordinates");
            this.deserializeEnumEnumMapFromIDS(defaultValues, this.defaultAnchorPoints, "anchorPoints", Feature.class, EnumUtils.AnchorPoint.class);
            this.deserializeEnumNumberMapFromID(defaultValues, this.defaultGuiScales, "guiScales", Feature.class, Float.TYPE);
            this.deserializeFeatureIntCoordsMapFromID(defaultValues, this.defaultBarSizes, "barSizes");
        }
        catch (Exception ex) {
            CrashReport crashReport = CrashReport.func_85055_a((Throwable)ex, (String)"Reading default settings file");
            throw new ReportedException(crashReport);
        }
        if (this.settingsConfigFile.exists()) {
            Feature feature;
            try (FileReader reader = new FileReader(this.settingsConfigFile);){
                JsonElement jsonElement = new JsonParser().parse((Reader)reader);
                if (jsonElement == null || jsonElement.isJsonNull()) {
                    throw new JsonParseException("File is null!");
                }
                this.loadedConfig = jsonElement.getAsJsonObject();
            }
            catch (JsonParseException | IOException | IllegalStateException ex) {
                this.logger.error("There was an error loading the config. Resetting all settings to default.");
                this.logger.catching(ex);
                this.addDefaultsAndSave();
                return;
            }
            int configVersion = this.loadedConfig.has("configVersion") ? this.loadedConfig.get("configVersion").getAsInt() : 9;
            this.deserializeFeatureSetFromID(this.disabledFeatures, "disabledFeatures");
            this.deserializeStringIntSetMap(this.profileLockedSlots, "profileLockedSlots");
            this.deserializeNumber((Mutable<Number>)this.warningSeconds, "warningSeconds", Integer.TYPE);
            try {
                String languageKey;
                Language language;
                if (this.loadedConfig.has("language") && (language = Language.getFromPath(languageKey = this.loadedConfig.get("language").getAsString())) != null) {
                    this.setLanguage(language);
                }
            }
            catch (Exception ex) {
                this.logger.error("Failed to deserialize path: language");
                this.logger.catching((Throwable)ex);
            }
            this.deserializeEnumValueFromOrdinal(this.backpackStyle, "backpackStyle");
            this.deserializeEnumValueFromOrdinal(this.powerOrbDisplayStyle, "powerOrbStyle");
            this.deserializeEnumEnumMapFromIDS(this.anchorPoints, "anchorPoints", Feature.class, EnumUtils.AnchorPoint.class);
            this.deserializeEnumNumberMapFromID(this.guiScales, "guiScales", Feature.class, Float.TYPE);
            try {
                for (Feature feature2 : Feature.getGuiFeatures()) {
                    String property = Introspector.decapitalize(WordUtils.capitalizeFully((String)feature2.toString().replace("_", " "))).replace(" ", "");
                    String x = property + "X";
                    String y = property + "Y";
                    if (!this.loadedConfig.has(x)) continue;
                    this.coordinates.put(feature2, new FloatPair(this.loadedConfig.get(x).getAsFloat(), this.loadedConfig.get(y).getAsFloat()));
                }
            }
            catch (Exception ex) {
                this.logger.error("Failed to deserialize path: coordinates (legacy)");
                this.logger.catching((Throwable)ex);
            }
            if (this.loadedConfig.has("coordinates")) {
                this.deserializeFeatureFloatCoordsMapFromID(this.coordinates, "coordinates");
            } else {
                this.deserializeFeatureFloatCoordsMapFromID(this.coordinates, "guiPositions");
            }
            this.deserializeFeatureIntCoordsMapFromID(this.barSizes, "barSizes");
            if (this.loadedConfig.has("featureColors")) {
                try {
                    for (Map.Entry entry : this.loadedConfig.getAsJsonObject("featureColors").entrySet()) {
                        ColorCode colorCode;
                        feature = Feature.fromId(Integer.parseInt((String)entry.getKey()));
                        if (feature == null || !(colorCode = ColorCode.values()[((JsonElement)entry.getValue()).getAsInt()]).isColor() || colorCode == ColorCode.RED) continue;
                        this.colors.put(feature, colorCode.getColor());
                    }
                }
                catch (Exception ex) {
                    this.logger.error("Failed to deserialize path: featureColors");
                    this.logger.catching((Throwable)ex);
                }
            } else {
                this.deserializeEnumNumberMapFromID(this.colors, "colors", Feature.class, Integer.TYPE);
            }
            this.deserializeEnumValueFromOrdinal(this.textStyle, "textStyle");
            this.deserializeFeatureSetFromID(this.chromaFeatures, "chromaFeatures");
            if (configVersion <= 8) {
                this.deserializeNumber((Mutable<Number>)this.oldChromaSpeed, "chromaSpeed", Float.TYPE);
                this.chromaSpeed.setValue(MathUtils.denormalizeSliderValue(this.oldChromaSpeed.floatValue(), 0.1f, 10.0f, 0.5f));
            } else {
                this.deserializeNumber((Mutable<Number>)this.chromaSpeed, "chromaSpeed", Float.TYPE);
            }
            this.deserializeNumber((Mutable<Number>)this.chromaSize, "chromaSize", Float.TYPE);
            this.deserializeEnumValueFromOrdinal(this.chromaMode, "chromaMode");
            this.deserializeNumber((Mutable<Number>)this.chromaFadeWidth, "chromaFadeWidth", Float.TYPE);
            this.deserializeEnumValueFromOrdinal(this.discordStatus, "discordStatus");
            this.deserializeEnumValueFromOrdinal(this.discordDetails, "discordDetails");
            this.deserializeEnumValueFromOrdinal(this.discordAutoDefault, "discordAutoDefault");
            this.deserializeStringCollection(this.discordCustomStatuses, "discordCustomStatuses");
            this.deserializeEnumValueFromOrdinal(this.enchantLayout, "enchantLayout");
            this.deserializeNumber((Mutable<Number>)this.mapZoom, "mapZoom", Float.TYPE);
            this.deserializeNumber((Mutable<Number>)this.chromaSaturation, "chromaSaturation", Float.TYPE);
            this.deserializeNumber((Mutable<Number>)this.chromaBrightness, "chromaBrightness", Float.TYPE);
            if (configVersion <= 5) {
                this.disabledFeatures.add(Feature.REPLACE_ROMAN_NUMERALS_WITH_NUMBERS);
            } else if (configVersion <= 6) {
                this.putDefaultBarSizes();
                for (Map.Entry entry : this.coordinates.entrySet()) {
                    if (this.getAnchorPoint((Feature)((Object)entry.getKey())) != EnumUtils.AnchorPoint.BOTTOM_MIDDLE) continue;
                    FloatPair coords = (FloatPair)entry.getValue();
                    coords.setX(coords.getX() - 91.0f);
                    coords.setY(coords.getY() - 39.0f);
                }
            } else if (configVersion <= 7) {
                for (Map.Entry entry : this.coordinates.entrySet()) {
                    feature = (Feature)((Object)entry.getKey());
                    FloatPair coords = (FloatPair)entry.getValue();
                    if (feature == Feature.MAGMA_BOSS_TIMER || feature == Feature.DARK_AUCTION_TIMER || feature == Feature.ZEALOT_COUNTER || feature == Feature.SKILL_DISPLAY || feature == Feature.SHOW_TOTAL_ZEALOT_COUNT || feature == Feature.SHOW_SUMMONING_EYE_COUNT || feature == Feature.SHOW_AVERAGE_ZEALOTS_PER_EYE || feature == Feature.BIRCH_PARK_RAINMAKER_TIMER || feature == Feature.COMBAT_TIMER_DISPLAY || feature == Feature.ENDSTONE_PROTECTOR_DISPLAY) {
                        coords.setY(coords.getY() + 1.0f);
                        coords.setX(coords.getX() - 9.0f);
                        coords.setY(coords.getY() - 4.5f);
                        if (feature == Feature.COMBAT_TIMER_DISPLAY) {
                            coords.setY(coords.getY() + 7.5f);
                        }
                    }
                    if (feature.getGuiFeatureData() == null || feature.getGuiFeatureData().getDrawType() != EnumUtils.DrawType.BAR) continue;
                    coords.setY(coords.getY() + 1.0f);
                }
            }
            int lastFeatureID = this.loadedConfig.has("lastFeatureID") ? this.loadedConfig.get("lastFeatureID").getAsInt() : Feature.SKYBLOCK_ADDONS_BUTTON_IN_PAUSE_MENU.getId();
            for (Feature feature3 : Feature.values()) {
                if (feature3.getId() <= lastFeatureID || !feature3.isDefaultDisabled() || !this.featureCoordinatesAreDefault(feature3)) continue;
                this.getDisabledFeatures().add(feature3);
            }
        } else {
            this.addDefaultsAndSave();
        }
    }

    private void addDefaultsAndSave() {
        Minecraft mc = Minecraft.func_71410_x();
        if (mc != null && mc.func_135016_M() != null && mc.func_135016_M().func_135041_c().func_135034_a() != null) {
            String minecraftLanguage = Minecraft.func_71410_x().func_135016_M().func_135041_c().func_135034_a().toLowerCase(Locale.US);
            Language configLanguage = Language.getFromPath(minecraftLanguage);
            if (configLanguage != null) {
                this.language.setValue((Object)configLanguage);
            } else {
                String languageCode = minecraftLanguage.split("_")[0];
                for (Language loopLanguage : Language.values()) {
                    String loopLanguageCode = loopLanguage.getPath().split("_")[0];
                    if (!loopLanguageCode.equals(languageCode)) continue;
                    this.language.setValue((Object)loopLanguage);
                    break;
                }
            }
        }
        for (Feature feature : Feature.values()) {
            ColorCode color = feature.getDefaultColor();
            if (color != null) {
                this.colors.put(feature, color.getColor());
            }
            if (!feature.isDefaultDisabled()) continue;
            this.disabledFeatures.add(feature);
        }
        this.setAllCoordinatesToDefault();
        this.putDefaultBarSizes();
        this.saveConfig();
    }

    public void saveConfig() {
        EnchantManager.markCacheDirty();
        SkyblockAddons.runAsync(() -> {
            if (!SAVE_LOCK.tryLock()) {
                return;
            }
            try {
                int n;
                this.settingsConfigFile.createNewFile();
                JsonObject saveConfig = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                for (Feature feature : this.disabledFeatures) {
                    jsonArray.add(new GsonBuilder().create().toJsonTree((Object)feature.getId()));
                }
                saveConfig.add("disabledFeatures", (JsonElement)jsonArray);
                JsonObject profileSlotsObject = new JsonObject();
                for (Map.Entry<String, Set<Integer>> entry : this.profileLockedSlots.entrySet()) {
                    JsonArray jsonArray2 = new JsonArray();
                    for (int slot : entry.getValue()) {
                        jsonArray2.add(new GsonBuilder().create().toJsonTree((Object)slot));
                    }
                    profileSlotsObject.add(entry.getKey(), (JsonElement)jsonArray2);
                }
                saveConfig.add("profileLockedSlots", (JsonElement)profileSlotsObject);
                JsonObject jsonObject = new JsonObject();
                for (Feature feature : Feature.getGuiFeatures()) {
                    jsonObject.addProperty(String.valueOf(feature.getId()), (Number)this.getAnchorPoint(feature).getId());
                }
                saveConfig.add("anchorPoints", (JsonElement)jsonObject);
                JsonObject jsonObject2 = new JsonObject();
                for (Feature feature : this.guiScales.keySet()) {
                    jsonObject2.addProperty(String.valueOf(feature.getId()), (Number)this.guiScales.get((Object)feature));
                }
                saveConfig.add("guiScales", (JsonElement)jsonObject2);
                JsonObject jsonObject3 = new JsonObject();
                for (Feature feature : this.colors.keySet()) {
                    int featureColor = this.colors.get((Object)feature);
                    if (featureColor == ColorCode.RED.getColor()) continue;
                    jsonObject3.addProperty(String.valueOf(feature.getId()), (Number)this.colors.get((Object)feature));
                }
                saveConfig.add("colors", (JsonElement)jsonObject3);
                JsonObject jsonObject4 = new JsonObject();
                for (Feature feature : this.coordinates.keySet()) {
                    JsonArray jsonArray3 = new JsonArray();
                    jsonArray3.add(new GsonBuilder().create().toJsonTree((Object)Math.round(this.coordinates.get((Object)feature).getX())));
                    jsonArray3.add(new GsonBuilder().create().toJsonTree((Object)Math.round(this.coordinates.get((Object)feature).getY())));
                    jsonObject4.add(String.valueOf(feature.getId()), (JsonElement)jsonArray3);
                }
                saveConfig.add("guiPositions", (JsonElement)jsonObject4);
                JsonObject jsonObject5 = new JsonObject();
                for (Feature feature : this.coordinates.keySet()) {
                    JsonArray jsonArray4 = new JsonArray();
                    jsonArray4.add(new GsonBuilder().create().toJsonTree((Object)Float.valueOf(this.coordinates.get((Object)feature).getX())));
                    jsonArray4.add(new GsonBuilder().create().toJsonTree((Object)Float.valueOf(this.coordinates.get((Object)feature).getY())));
                    jsonObject5.add(String.valueOf(feature.getId()), (JsonElement)jsonArray4);
                }
                saveConfig.add("coordinates", (JsonElement)jsonObject5);
                JsonObject barSizesObject = new JsonObject();
                for (Feature feature : this.barSizes.keySet()) {
                    JsonArray jsonArray5 = new JsonArray();
                    jsonArray5.add(new GsonBuilder().create().toJsonTree((Object)Float.valueOf(this.barSizes.get((Object)feature).getX())));
                    jsonArray5.add(new GsonBuilder().create().toJsonTree((Object)Float.valueOf(this.barSizes.get((Object)feature).getY())));
                    barSizesObject.add(String.valueOf(feature.getId()), (JsonElement)jsonArray5);
                }
                saveConfig.add("barSizes", (JsonElement)barSizesObject);
                saveConfig.addProperty("warningSeconds", (Number)this.warningSeconds);
                saveConfig.addProperty("textStyle", (Number)((EnumUtils.TextStyle)((Object)((Object)this.textStyle.getValue()))).ordinal());
                saveConfig.addProperty("language", ((Language)((Object)((Object)this.language.getValue()))).getPath());
                saveConfig.addProperty("backpackStyle", (Number)((EnumUtils.BackpackStyle)((Object)((Object)this.backpackStyle.getValue()))).ordinal());
                saveConfig.addProperty("powerOrbStyle", (Number)((EnumUtils.PowerOrbDisplayStyle)((Object)((Object)this.powerOrbDisplayStyle.getValue()))).ordinal());
                JsonArray chromaFeaturesArray = new JsonArray();
                for (Feature feature : this.chromaFeatures) {
                    chromaFeaturesArray.add(new GsonBuilder().create().toJsonTree((Object)feature.getId()));
                }
                saveConfig.add("chromaFeatures", (JsonElement)chromaFeaturesArray);
                saveConfig.addProperty("chromaSpeed", (Number)this.chromaSpeed);
                saveConfig.addProperty("chromaMode", (Number)((EnumUtils.ChromaMode)((Object)((Object)this.chromaMode.getValue()))).ordinal());
                saveConfig.addProperty("chromaSize", (Number)this.chromaSize);
                saveConfig.addProperty("discordStatus", (Number)((DiscordStatus)this.discordStatus.getValue()).ordinal());
                saveConfig.addProperty("discordDetails", (Number)((DiscordStatus)this.discordDetails.getValue()).ordinal());
                saveConfig.addProperty("discordAutoDefault", (Number)((DiscordStatus)this.discordAutoDefault.getValue()).ordinal());
                saveConfig.addProperty("enchantLayout", (Number)((EnchantListLayout)this.enchantLayout.getValue()).ordinal());
                JsonArray jsonArray6 = new JsonArray();
                for (String string : this.discordCustomStatuses) {
                    jsonArray6.add(new GsonBuilder().create().toJsonTree((Object)string));
                }
                saveConfig.add("discordCustomStatuses", (JsonElement)jsonArray6);
                saveConfig.addProperty("mapZoom", (Number)this.mapZoom);
                saveConfig.addProperty("chromaSaturation", (Number)this.chromaSaturation);
                saveConfig.addProperty("chromaBrightness", (Number)this.chromaBrightness);
                saveConfig.addProperty("configVersion", (Number)9);
                boolean bl = false;
                for (Feature feature : Feature.values()) {
                    if (feature.getId() <= n) continue;
                    n = feature.getId();
                }
                saveConfig.addProperty("lastFeatureID", (Number)n);
                try (FileWriter fileWriter = new FileWriter(this.settingsConfigFile);){
                    SkyblockAddons.getGson().toJson((JsonElement)saveConfig, (Appendable)fileWriter);
                }
            }
            catch (Exception ex) {
                this.logger.error("An error occurred while attempting to save the config!");
                this.logger.catching((Throwable)ex);
            }
            SAVE_LOCK.unlock();
        });
    }

    private void deserializeFeatureSetFromID(Collection<Feature> collection, String path) {
        try {
            if (this.loadedConfig.has(path)) {
                for (JsonElement element : this.loadedConfig.getAsJsonArray(path)) {
                    Feature feature = Feature.fromId(element.getAsInt());
                    if (feature == null) continue;
                    collection.add(feature);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private void deserializeStringCollection(Collection<String> collection, String path) {
        try {
            if (this.loadedConfig.has(path)) {
                for (JsonElement element : this.loadedConfig.getAsJsonArray(path)) {
                    String string = element.getAsString();
                    if (string == null) continue;
                    collection.add(string);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private void deserializeStringIntSetMap(Map<String, Set<Integer>> map, String path) {
        try {
            if (this.loadedConfig.has(path)) {
                JsonObject profileSlotsObject = this.loadedConfig.getAsJsonObject(path);
                for (Map.Entry entry : profileSlotsObject.entrySet()) {
                    HashSet<Integer> slots = new HashSet<Integer>();
                    for (JsonElement element : ((JsonElement)entry.getValue()).getAsJsonArray()) {
                        slots.add(element.getAsInt());
                    }
                    map.put((String)entry.getKey(), (Set<Integer>)slots);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private <E extends Enum<?>, F extends Enum<?>> void deserializeEnumEnumMapFromIDS(Map<E, F> map, String path, Class<E> keyClass, Class<F> valueClass) {
        this.deserializeEnumEnumMapFromIDS(this.loadedConfig, map, path, keyClass, valueClass);
    }

    private <E extends Enum<?>, F extends Enum<?>> void deserializeEnumEnumMapFromIDS(JsonObject jsonObject, Map<E, F> map, String path, Class<E> keyClass, Class<F> valueClass) {
        try {
            if (jsonObject.has(path)) {
                for (Map.Entry element : jsonObject.getAsJsonObject(path).entrySet()) {
                    Method fromId = keyClass.getDeclaredMethod("fromId", Integer.TYPE);
                    Enum key = (Enum)fromId.invoke(null, Integer.parseInt((String)element.getKey()));
                    fromId = valueClass.getDeclaredMethod("fromId", Integer.TYPE);
                    Enum value = (Enum)fromId.invoke(null, ((JsonElement)element.getValue()).getAsInt());
                    if (key == null || value == null) continue;
                    map.put(key, value);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private <E extends Enum<?>, N extends Number> void deserializeEnumNumberMapFromID(Map<E, N> map, String path, Class<E> keyClass, Class<N> numberClass) {
        this.deserializeEnumNumberMapFromID(this.loadedConfig, map, path, keyClass, numberClass);
    }

    private <E extends Enum<?>, N extends Number> void deserializeEnumNumberMapFromID(JsonObject jsonObject, Map<E, N> map, String path, Class<E> keyClass, Class<N> numberClass) {
        try {
            if (jsonObject.has(path)) {
                for (Map.Entry element : jsonObject.getAsJsonObject(path).entrySet()) {
                    Method fromId = keyClass.getDeclaredMethod("fromId", Integer.TYPE);
                    Enum key = (Enum)fromId.invoke(null, Integer.parseInt((String)element.getKey()));
                    if (key == null) continue;
                    map.put(key, this.getNumber((JsonElement)element.getValue(), numberClass));
                }
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private <N extends Number> void deserializeNumber(Mutable<Number> number, String path, Class<N> numberClass) {
        try {
            if (this.loadedConfig.has(path)) {
                number.setValue((Object)this.getNumber(this.loadedConfig.get(path), numberClass));
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private Number getNumber(JsonElement jsonElement, Class<? extends Number> numberClass) {
        if (numberClass == Byte.TYPE) {
            return jsonElement.getAsByte();
        }
        if (numberClass == Short.TYPE) {
            return jsonElement.getAsShort();
        }
        if (numberClass == Integer.TYPE) {
            return jsonElement.getAsInt();
        }
        if (numberClass == Long.TYPE) {
            return jsonElement.getAsLong();
        }
        if (numberClass == Float.TYPE) {
            return Float.valueOf(jsonElement.getAsFloat());
        }
        if (numberClass == Double.TYPE) {
            return jsonElement.getAsDouble();
        }
        return null;
    }

    private <E extends Enum<?>> void deserializeEnumValueFromOrdinal(MutableObject<E> value, String path) {
        try {
            Enum enumValue;
            int ordinal;
            Class enumClass = ((Enum)value.getValue()).getDeclaringClass();
            Method method = enumClass.getDeclaredMethod("values", new Class[0]);
            Object valuesObject = method.invoke(null, new Object[0]);
            Enum[] values = (Enum[])valuesObject;
            if (this.loadedConfig.has(path) && values.length > (ordinal = this.loadedConfig.get(path).getAsInt()) && (enumValue = values[ordinal]) != null) {
                value.setValue((Object)values[ordinal]);
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private void deserializeFeatureFloatCoordsMapFromID(Map<Feature, FloatPair> map, String path) {
        this.deserializeFeatureFloatCoordsMapFromID(this.loadedConfig, map, path);
    }

    private void deserializeFeatureFloatCoordsMapFromID(JsonObject jsonObject, Map<Feature, FloatPair> map, String path) {
        try {
            if (jsonObject.has(path)) {
                for (Map.Entry element : jsonObject.getAsJsonObject(path).entrySet()) {
                    Feature feature = Feature.fromId(Integer.parseInt((String)element.getKey()));
                    if (feature == null) continue;
                    JsonArray coords = ((JsonElement)element.getValue()).getAsJsonArray();
                    map.put(feature, new FloatPair(coords.get(0).getAsFloat(), coords.get(1).getAsFloat()));
                }
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    private void deserializeFeatureIntCoordsMapFromID(Map<Feature, FloatPair> map, String path) {
        this.deserializeFeatureIntCoordsMapFromID(this.loadedConfig, map, path);
    }

    private void deserializeFeatureIntCoordsMapFromID(JsonObject jsonObject, Map<Feature, FloatPair> map, String path) {
        try {
            if (jsonObject.has(path)) {
                for (Map.Entry element : jsonObject.getAsJsonObject(path).entrySet()) {
                    Feature feature = Feature.fromId(Integer.parseInt((String)element.getKey()));
                    if (feature == null) continue;
                    JsonArray coords = ((JsonElement)element.getValue()).getAsJsonArray();
                    map.put(feature, new FloatPair(coords.get(0).getAsFloat(), coords.get(1).getAsFloat()));
                }
            }
        }
        catch (Exception ex) {
            this.logger.error("Failed to deserialize path: " + path);
            this.logger.catching((Throwable)ex);
        }
    }

    public void setAllCoordinatesToDefault() {
        this.coordinates.clear();
        for (Map.Entry<Feature, FloatPair> entry : this.defaultCoordinates.entrySet()) {
            this.coordinates.put(entry.getKey(), entry.getValue().cloneCoords());
        }
        this.anchorPoints = new HashMap<Feature, EnumUtils.AnchorPoint>(this.defaultAnchorPoints);
        this.guiScales = new HashMap<Feature, Float>(this.defaultGuiScales);
    }

    private void putDefaultCoordinates(Feature feature) {
        FloatPair coords = this.defaultCoordinates.get((Object)feature);
        if (coords != null) {
            this.coordinates.put(feature, coords);
        }
    }

    public void putDefaultBarSizes() {
        this.barSizes.clear();
        for (Map.Entry<Feature, FloatPair> entry : this.defaultBarSizes.entrySet()) {
            this.barSizes.put(entry.getKey(), entry.getValue().cloneCoords());
        }
    }

    public static float normalizeValueNoStep(float value) {
        return MathHelper.func_76131_a((float)((ConfigValues.snapNearDefaultValue(value) - 0.5f) / 4.5f), (float)0.0f, (float)1.0f);
    }

    public static float denormalizeScale(float value) {
        return ConfigValues.snapNearDefaultValue(0.5f + 4.5f * MathHelper.func_76131_a((float)value, (float)0.0f, (float)1.0f));
    }

    public static float snapNearDefaultValue(float value) {
        if (value != 1.0f && (double)value > 0.95 && (double)value < 1.05) {
            return 1.0f;
        }
        return value;
    }

    public boolean isRemoteDisabled(Feature feature) {
        String version;
        if (feature == null) {
            return false;
        }
        HashMap<String, List<Integer>> disabledFeatures = this.main.getOnlineData().getDisabledFeatures();
        if (disabledFeatures.containsKey("all")) {
            if (disabledFeatures.get("all") != null) {
                if (disabledFeatures.get("all").contains(feature.getId())) {
                    return true;
                }
            } else {
                this.logger.error("\"all\" key in disabled features map has value of null. Please fix online data.");
            }
        }
        if ((version = SkyblockAddons.VERSION).contains("-")) {
            version = version.split("-")[0];
        }
        if (disabledFeatures.containsKey(version)) {
            if (disabledFeatures.get(version) != null) {
                return disabledFeatures.get(version).contains(feature.getId());
            }
            this.logger.error("\"" + version + "\" key in disabled features map has value of null. Please fix online data.");
        }
        return false;
    }

    public boolean isDisabled(Feature feature) {
        return this.disabledFeatures.contains((Object)feature) || this.isRemoteDisabled(feature);
    }

    public boolean isEnabled(Feature feature) {
        return !this.isDisabled(feature);
    }

    public int getColor(Feature feature) {
        return this.getColor(feature, 255);
    }

    public int getColor(Feature feature, int alpha) {
        if (this.chromaFeatures.contains((Object)feature)) {
            return ManualChromaManager.getChromaColor(0.0f, 0.0f, alpha);
        }
        if (this.colors.containsKey((Object)feature)) {
            return ColorUtils.setColorAlpha((int)this.colors.get((Object)feature), alpha);
        }
        ColorCode defaultColor = feature.getDefaultColor();
        return ColorUtils.setColorAlpha(defaultColor != null ? defaultColor.getColor() : ColorCode.RED.getColor(), alpha);
    }

    public SkyblockColor getSkyblockColor(Feature feature) {
        SkyblockColor color = ColorUtils.getDummySkyblockColor(this.getColor(feature), this.chromaFeatures.contains((Object)feature));
        if (color.drawMulticolorUsingShader()) {
            color.setColor(-1);
        }
        return color;
    }

    public ColorCode getRestrictedColor(Feature feature) {
        Integer featureColor = this.colors.get((Object)feature);
        if (featureColor != null) {
            for (ColorCode colorCode : ColorCode.values()) {
                if (!colorCode.isColor() || colorCode.getColor() != featureColor.intValue()) continue;
                return colorCode;
            }
        }
        return feature.getDefaultColor();
    }

    private boolean featureCoordinatesAreDefault(Feature feature) {
        if (!this.defaultCoordinates.containsKey((Object)feature)) {
            return true;
        }
        if (!this.coordinates.containsKey((Object)feature)) {
            return true;
        }
        return this.coordinates.get((Object)feature).equals(this.defaultCoordinates.get((Object)feature));
    }

    public void setColor(Feature feature, int color) {
        this.colors.put(feature, color);
    }

    public float getActualX(Feature feature) {
        int maxX = new ScaledResolution(Minecraft.func_71410_x()).func_78326_a();
        return (float)this.getAnchorPoint(feature).getX(maxX) + this.getRelativeCoords(feature).getX();
    }

    public float getActualY(Feature feature) {
        int maxY = new ScaledResolution(Minecraft.func_71410_x()).func_78328_b();
        return (float)this.getAnchorPoint(feature).getY(maxY) + this.getRelativeCoords(feature).getY();
    }

    public FloatPair getSizes(Feature feature) {
        return this.barSizes.getOrDefault((Object)feature, this.defaultBarSizes.containsKey((Object)feature) ? this.defaultBarSizes.get((Object)feature).cloneCoords() : new FloatPair(1.0f, 1.0f));
    }

    public float getSizesX(Feature feature) {
        return Math.min(Math.max(this.getSizes(feature).getX(), 0.25f), 1.0f);
    }

    public float getSizesY(Feature feature) {
        return Math.min(Math.max(this.getSizes(feature).getY(), 0.25f), 1.0f);
    }

    public void setScaleX(Feature feature, float x) {
        FloatPair coords = this.getSizes(feature);
        coords.setX(x);
    }

    public void setScaleY(Feature feature, float y) {
        FloatPair coords = this.getSizes(feature);
        coords.setY(y);
    }

    public FloatPair getRelativeCoords(Feature feature) {
        if (this.coordinates.containsKey((Object)feature)) {
            return this.coordinates.get((Object)feature);
        }
        this.putDefaultCoordinates(feature);
        if (this.coordinates.containsKey((Object)feature)) {
            return this.coordinates.get((Object)feature);
        }
        return new FloatPair(0.0f, 0.0f);
    }

    public void setCoords(Feature feature, float x, float y) {
        if (this.coordinates.containsKey((Object)feature)) {
            this.coordinates.get((Object)feature).setX(x);
            this.coordinates.get((Object)feature).setY(y);
        } else {
            this.coordinates.put(feature, new FloatPair(x, y));
        }
    }

    public EnumUtils.AnchorPoint getClosestAnchorPoint(float x, float y) {
        ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
        int maxX = sr.func_78326_a();
        int maxY = sr.func_78328_b();
        double shortestDistance = -1.0;
        EnumUtils.AnchorPoint closestAnchorPoint = EnumUtils.AnchorPoint.BOTTOM_MIDDLE;
        for (EnumUtils.AnchorPoint point : EnumUtils.AnchorPoint.values()) {
            double distance = Point2D.distance(x, y, point.getX(maxX), point.getY(maxY));
            if (shortestDistance != -1.0 && !(distance < shortestDistance)) continue;
            closestAnchorPoint = point;
            shortestDistance = distance;
        }
        return closestAnchorPoint;
    }

    public void setClosestAnchorPoint(Feature feature) {
        float x1 = this.getActualX(feature);
        float y1 = this.getActualY(feature);
        ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
        int maxX = sr.func_78326_a();
        int maxY = sr.func_78328_b();
        double shortestDistance = -1.0;
        EnumUtils.AnchorPoint closestAnchorPoint = EnumUtils.AnchorPoint.BOTTOM_MIDDLE;
        for (EnumUtils.AnchorPoint point : EnumUtils.AnchorPoint.values()) {
            double distance = Point2D.distance(x1, y1, point.getX(maxX), point.getY(maxY));
            if (shortestDistance != -1.0 && !(distance < shortestDistance)) continue;
            closestAnchorPoint = point;
            shortestDistance = distance;
        }
        if (this.getAnchorPoint(feature) == closestAnchorPoint) {
            return;
        }
        float targetX = this.getActualX(feature);
        float targetY = this.getActualY(feature);
        float x = targetX - (float)closestAnchorPoint.getX(maxX);
        float y = targetY - (float)closestAnchorPoint.getY(maxY);
        this.anchorPoints.put(feature, closestAnchorPoint);
        this.setCoords(feature, x, y);
    }

    public EnumUtils.AnchorPoint getAnchorPoint(Feature feature) {
        return this.anchorPoints.getOrDefault((Object)feature, this.defaultAnchorPoints.getOrDefault((Object)feature, EnumUtils.AnchorPoint.BOTTOM_MIDDLE));
    }

    public Set<Integer> getLockedSlots() {
        String profile = this.main.getUtils().getProfileName();
        if (!this.profileLockedSlots.containsKey(profile)) {
            this.profileLockedSlots.put(profile, new HashSet());
        }
        return this.profileLockedSlots.get(profile);
    }

    public void setGuiScale(Feature feature, float scale) {
        this.guiScales.put(feature, Float.valueOf(scale));
    }

    public float getGuiScale(Feature feature) {
        return this.getGuiScale(feature, true);
    }

    public float getGuiScale(Feature feature, boolean denormalized) {
        float value = DEFAULT_GUI_SCALE;
        if (this.guiScales.containsKey((Object)feature)) {
            value = this.guiScales.get((Object)feature).floatValue();
        }
        if (denormalized) {
            value = ConfigValues.denormalizeScale(value);
        }
        return value;
    }

    public void setChroma(Feature feature, boolean enabled) {
        if (enabled) {
            this.chromaFeatures.add(feature);
        } else {
            this.chromaFeatures.remove((Object)feature);
        }
    }

    public int getWarningSeconds() {
        return this.warningSeconds.getValue();
    }

    public void setWarningSeconds(int warningSeconds) {
        this.warningSeconds.setValue(warningSeconds);
    }

    public Language getLanguage() {
        return (Language)((Object)this.language.getValue());
    }

    public void setLanguage(Language language) {
        this.language.setValue((Object)language);
    }

    public EnumUtils.BackpackStyle getBackpackStyle() {
        return (EnumUtils.BackpackStyle)((Object)this.backpackStyle.getValue());
    }

    public void setBackpackStyle(EnumUtils.BackpackStyle backpackStyle) {
        this.backpackStyle.setValue((Object)backpackStyle);
    }

    public EnumUtils.PowerOrbDisplayStyle getPowerOrbDisplayStyle() {
        return (EnumUtils.PowerOrbDisplayStyle)((Object)this.powerOrbDisplayStyle.getValue());
    }

    public void setPowerOrbDisplayStyle(EnumUtils.PowerOrbDisplayStyle powerOrbDisplayStyle) {
        this.powerOrbDisplayStyle.setValue((Object)powerOrbDisplayStyle);
    }

    public EnumUtils.TextStyle getTextStyle() {
        return (EnumUtils.TextStyle)((Object)this.textStyle.getValue());
    }

    public void setTextStyle(EnumUtils.TextStyle textStyle) {
        this.textStyle.setValue((Object)textStyle);
    }

    public EnumUtils.ChromaMode getChromaMode() {
        return (EnumUtils.ChromaMode)((Object)this.chromaMode.getValue());
    }

    public void setChromaMode(EnumUtils.ChromaMode chromaMode) {
        this.chromaMode.setValue((Object)chromaMode);
    }

    public void setChromaFadeWidth(float chromaFadeWidth) {
        this.chromaFadeWidth.setValue(chromaFadeWidth);
    }

    public float getChromaFadeWidth() {
        return this.chromaFadeWidth.getValue().floatValue();
    }

    public void setDiscordDetails(DiscordStatus discordDetails) {
        this.discordDetails.setValue((Object)discordDetails);
    }

    public void setDiscordStatus(DiscordStatus discordStatus) {
        this.discordStatus.setValue((Object)discordStatus);
    }

    public DiscordStatus getDiscordStatus() {
        return this.discordStatus != null ? (DiscordStatus)this.discordStatus.getValue() : DiscordStatus.NONE;
    }

    public DiscordStatus getDiscordDetails() {
        return this.discordDetails != null ? (DiscordStatus)this.discordDetails.getValue() : DiscordStatus.NONE;
    }

    public DiscordStatus getDiscordAutoDefault() {
        return this.discordAutoDefault != null ? (DiscordStatus)this.discordAutoDefault.getValue() : DiscordStatus.NONE;
    }

    public void setDiscordAutoDefault(DiscordStatus discordAutoDefault) {
        this.discordAutoDefault.setValue((Object)discordAutoDefault);
    }

    public String getCustomStatus(EnumUtils.DiscordStatusEntry statusEntry) {
        while (this.main.getConfigValues().getDiscordCustomStatuses().size() < 2) {
            this.main.getConfigValues().getDiscordCustomStatuses().add("");
        }
        return this.discordCustomStatuses.get(statusEntry.getId());
    }

    public String setCustomStatus(EnumUtils.DiscordStatusEntry statusEntry, String text) {
        while (this.main.getConfigValues().getDiscordCustomStatuses().size() < 2) {
            this.main.getConfigValues().getDiscordCustomStatuses().add("");
        }
        return this.discordCustomStatuses.set(statusEntry.getId(), text);
    }

    public EnchantListLayout getEnchantLayout() {
        return this.enchantLayout != null ? (EnchantListLayout)this.enchantLayout.getValue() : EnchantListLayout.NORMAL;
    }

    public void setEnchantLayout(EnchantListLayout enchantLayout) {
        this.enchantLayout.setValue((Object)enchantLayout);
    }

    public JsonObject getLanguageConfig() {
        return this.languageConfig;
    }

    public void setLanguageConfig(JsonObject languageConfig) {
        this.languageConfig = languageConfig;
    }

    public Set<Feature> getDisabledFeatures() {
        return this.disabledFeatures;
    }

    public Set<Feature> getChromaFeatures() {
        return this.chromaFeatures;
    }

    public List<String> getDiscordCustomStatuses() {
        return this.discordCustomStatuses;
    }

    public MutableFloat getMapZoom() {
        return this.mapZoom;
    }

    public MutableFloat getHealingCircleOpacity() {
        return this.healingCircleOpacity;
    }

    public void setChromaSize(MutableFloat chromaSize) {
        this.chromaSize = chromaSize;
    }

    public MutableFloat getChromaSize() {
        return this.chromaSize;
    }

    public MutableFloat getChromaSpeed() {
        return this.chromaSpeed;
    }

    public MutableFloat getChromaSaturation() {
        return this.chromaSaturation;
    }

    public MutableFloat getChromaBrightness() {
        return this.chromaBrightness;
    }
}

