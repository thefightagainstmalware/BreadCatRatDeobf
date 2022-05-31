/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonObject
 *  javax.vecmath.Vector3d
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.WorldSettings
 *  net.minecraft.world.WorldSettings$GameType
 *  net.minecraft.world.WorldType
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.ModContainer
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.apache.commons.lang3.text.WordUtils
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.vector.Matrix4f
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Attribute;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.SkyblockDate;
import codes.biscuit.skyblockaddons.events.SkyblockJoinedEvent;
import codes.biscuit.skyblockaddons.events.SkyblockLeftEvent;
import codes.biscuit.skyblockaddons.features.itemdrops.ItemDropChecker;
import codes.biscuit.skyblockaddons.misc.scheduler.Scheduler;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import codes.biscuit.skyblockaddons.utils.RomanNumeralParser;
import codes.biscuit.skyblockaddons.utils.ScoreboardManager;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.vecmath.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class Utils {
    public static final String MESSAGE_PREFIX = (Object)((Object)ColorCode.GRAY) + "[" + (Object)((Object)ColorCode.AQUA) + "SkyblockAddons" + (Object)((Object)ColorCode.GRAY) + "] ";
    private static final Pattern SERVER_REGEX = Pattern.compile("(?<serverType>[Mm])(?<serverCode>[0-9]+[A-Z])$");
    private static final Pattern PURSE_REGEX = Pattern.compile("(?:Purse|Piggy): (?<coins>[0-9.]*)(?: .*)?");
    private static final Pattern SLAYER_TYPE_REGEX = Pattern.compile("(?<type>Tarantula Broodfather|Revenant Horror|Sven Packmaster|Voidgloom Seraph) (?<level>[IV]+)");
    private static final Pattern SLAYER_PROGRESS_REGEX = Pattern.compile("(?<progress>[0-9.k]*)/(?<total>[0-9.k]*) (?:Kills|Combat XP)$");
    private static final Set<String> SKYBLOCK_IN_ALL_LANGUAGES = Sets.newHashSet((Object[])new String[]{"SKYBLOCK", "\u7a7a\u5c9b\u751f\u5b58", "\u7a7a\u5cf6\u751f\u5b58"});
    private static final WorldClient DUMMY_WORLD = new WorldClient(null, new WorldSettings(0L, WorldSettings.GameType.SURVIVAL, false, false, WorldType.field_77137_b), 0, null, null);
    public static final String USER_AGENT = "SkyblockAddons/" + SkyblockAddons.VERSION;
    public static boolean blockNextClick;
    private Map<Attribute, MutableInt> attributes = new EnumMap<Attribute, MutableInt>(Attribute.class);
    private final ItemDropChecker itemDropChecker = new ItemDropChecker();
    private List<String> reforgeMatches = new LinkedList<String>();
    private List<String> reforgeExclusions = new LinkedList<String>();
    private boolean onSkyblock;
    private Location location = Location.UNKNOWN;
    private String profileName = "Unknown";
    private boolean playingSound;
    private String serverID = "";
    private int lastHoveredSlot = -1;
    private boolean usingFSRcontainerPreviewTexture = false;
    private SkyblockDate currentDate = new SkyblockDate(SkyblockDate.SkyblockMonth.EARLY_WINTER, 1, 1, 1, "am");
    private double purse = 0.0;
    private int jerryWave = -1;
    private boolean alpha;
    private boolean inDungeon;
    private boolean fadingIn;
    private long lastDamaged = -1L;
    private EnumUtils.SlayerQuest slayerQuest;
    private int slayerQuestLevel = 1;
    private boolean slayerBossAlive;
    private SkyblockAddons main = SkyblockAddons.getInstance();
    private Logger logger = SkyblockAddons.getLogger();
    private boolean triggeredSlayerWarning = false;
    private float lastCompletion;
    private boolean depthEnabled;
    private boolean blendEnabled;
    private boolean alphaEnabled;
    private int blendFunctionSrcFactor;
    private int blendFunctionDstFactor;
    private static final Vector3d interpolatedPlayerPosition;
    private static long lastTick;
    private static float lastPartialTicks;

    public Utils() {
        this.addDefaultStats();
    }

    private void addDefaultStats() {
        for (Attribute attribute : Attribute.values()) {
            this.attributes.put(attribute, new MutableInt(attribute.getDefaultValue()));
        }
    }

    public void sendMessage(String text, boolean prefix) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent(1, (IChatComponent)new ChatComponentText((prefix ? MESSAGE_PREFIX : "") + text));
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            Minecraft.func_71410_x().field_71439_g.func_145747_a(event.message);
        }
    }

    public void sendMessage(String text) {
        this.sendMessage(text, true);
    }

    public void sendMessage(ChatComponentText text, boolean prefix) {
        if (prefix) {
            ChatComponentText newText = new ChatComponentText(MESSAGE_PREFIX);
            newText.func_150257_a((IChatComponent)text);
            text = newText;
        }
        ClientChatReceivedEvent event = new ClientChatReceivedEvent(1, (IChatComponent)text);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            Minecraft.func_71410_x().field_71439_g.func_145747_a(event.message);
        }
    }

    public void sendErrorMessage(String errorText) {
        this.sendMessage((Object)((Object)ColorCode.RED) + "Error: " + errorText);
    }

    public boolean isOnHypixel() {
        EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        if (player == null) {
            return false;
        }
        String brand = player.func_142021_k();
        if (brand != null) {
            for (Pattern p : this.main.getOnlineData().getHypixelBrands()) {
                if (!p.matcher(brand).matches()) continue;
                return true;
            }
        }
        return false;
    }

    public void parseSidebar() {
        boolean foundScoreboard = false;
        boolean foundLocation = false;
        boolean foundJerryWave = false;
        boolean foundAlphaIP = false;
        boolean foundInDungeon = false;
        boolean foundSlayerQuest = false;
        boolean foundBossAlive = false;
        boolean foundSkyblockTitle = false;
        if (this.isOnHypixel() && ScoreboardManager.hasScoreboard()) {
            foundScoreboard = true;
            String strippedScoreboardTitle = ScoreboardManager.getStrippedScoreboardTitle();
            for (String skyblock : SKYBLOCK_IN_ALL_LANGUAGES) {
                if (!strippedScoreboardTitle.startsWith(skyblock)) continue;
                foundSkyblockTitle = true;
                break;
            }
            if (foundSkyblockTitle && !this.isOnSkyblock()) {
                MinecraftForge.EVENT_BUS.post((Event)new SkyblockJoinedEvent());
            }
            String timeString = null;
            String dateString = null;
            for (int lineNumber = 0; lineNumber < ScoreboardManager.getNumberOfLines(); ++lineNumber) {
                Matcher matcher;
                String scoreboardLine = ScoreboardManager.getScoreboardLines().get(lineNumber);
                String strippedScoreboardLine = ScoreboardManager.getStrippedScoreboardLines().get(lineNumber);
                if (strippedScoreboardLine.endsWith("am") || strippedScoreboardLine.endsWith("pm")) {
                    timeString = strippedScoreboardLine;
                }
                if (strippedScoreboardLine.endsWith("st") || strippedScoreboardLine.endsWith("nd") || strippedScoreboardLine.endsWith("rd") || strippedScoreboardLine.endsWith("th")) {
                    dateString = strippedScoreboardLine;
                }
                if ((matcher = PURSE_REGEX.matcher(strippedScoreboardLine)).matches()) {
                    try {
                        double oldCoins = this.purse;
                        this.purse = Double.parseDouble(matcher.group("coins"));
                        if (oldCoins != this.purse) {
                            this.onCoinsChange(this.purse - oldCoins);
                        }
                    }
                    catch (NumberFormatException ignored) {
                        this.purse = 0.0;
                    }
                }
                if ((matcher = SERVER_REGEX.matcher(strippedScoreboardLine)).find()) {
                    String serverType = matcher.group("serverType");
                    if (serverType.equals("m")) {
                        this.serverID = "mini" + matcher.group("serverCode");
                    } else if (serverType.equals("M")) {
                        this.serverID = "mega" + matcher.group("serverCode");
                    }
                }
                if (strippedScoreboardLine.endsWith("Combat XP") || strippedScoreboardLine.endsWith("Kills")) {
                    this.parseSlayerProgress(strippedScoreboardLine);
                }
                if (!foundLocation) {
                    if (strippedScoreboardLine.contains(Location.THE_CATACOMBS.getScoreboardName())) {
                        this.location = Location.THE_CATACOMBS;
                        foundLocation = true;
                    } else {
                        for (Location loopLocation : Location.values()) {
                            if (!strippedScoreboardLine.endsWith(loopLocation.getScoreboardName())) continue;
                            if (loopLocation == Location.BLAZING_FORTRESS && this.location != Location.BLAZING_FORTRESS) {
                                this.sendInventiveTalentPingRequest(EnumUtils.MagmaEvent.PING);
                                this.fetchMagmaBossEstimate();
                            }
                            if (loopLocation == Location.VILLAGE && strippedScoreboardLine.contains("Dwarven")) continue;
                            this.location = loopLocation;
                            foundLocation = true;
                            break;
                        }
                    }
                }
                if (!foundJerryWave && (this.location == Location.JERRYS_WORKSHOP || this.location == Location.JERRY_POND) && strippedScoreboardLine.startsWith("Wave")) {
                    int newJerryWave;
                    foundJerryWave = true;
                    try {
                        newJerryWave = Integer.parseInt(TextUtils.keepIntegerCharactersOnly(strippedScoreboardLine));
                    }
                    catch (NumberFormatException ignored) {
                        newJerryWave = 0;
                    }
                    if (this.jerryWave != newJerryWave) {
                        this.jerryWave = newJerryWave;
                    }
                }
                if (!foundAlphaIP && strippedScoreboardLine.contains("alpha.hypixel.net")) {
                    foundAlphaIP = true;
                    this.alpha = true;
                    this.profileName = "Alpha";
                }
                if (!foundInDungeon && strippedScoreboardLine.contains("Dungeon Cleared: ")) {
                    foundInDungeon = true;
                    this.inDungeon = true;
                    String lastServer = this.main.getDungeonManager().getLastServerId();
                    if (lastServer != null && !lastServer.equals(this.serverID)) {
                        this.main.getDungeonManager().reset();
                    }
                    this.main.getDungeonManager().setLastServerId(this.serverID);
                }
                if ((matcher = SLAYER_TYPE_REGEX.matcher(strippedScoreboardLine)).matches()) {
                    String type = matcher.group("type");
                    String levelRomanNumeral = matcher.group("level");
                    EnumUtils.SlayerQuest detectedSlayerQuest = EnumUtils.SlayerQuest.fromName(type);
                    if (detectedSlayerQuest != null) {
                        try {
                            int level = RomanNumeralParser.parseNumeral(levelRomanNumeral);
                            this.slayerQuest = detectedSlayerQuest;
                            this.slayerQuestLevel = level;
                            foundSlayerQuest = true;
                        }
                        catch (IllegalArgumentException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (strippedScoreboardLine.equals("Slay the boss!")) {
                    foundBossAlive = true;
                    this.slayerBossAlive = true;
                }
                if (!this.inDungeon) continue;
                try {
                    this.main.getDungeonManager().updateDungeonPlayer(scoreboardLine);
                    continue;
                }
                catch (NumberFormatException ex) {
                    this.logger.error("Failed to update a dungeon player from the line " + scoreboardLine + ".", (Throwable)ex);
                }
            }
            this.currentDate = SkyblockDate.parse(dateString, timeString);
        }
        if (!foundLocation) {
            this.location = Location.UNKNOWN;
        }
        if (!foundJerryWave) {
            this.jerryWave = -1;
        }
        if (!foundAlphaIP) {
            this.alpha = false;
        }
        if (!foundInDungeon) {
            this.inDungeon = false;
        }
        if (!foundSlayerQuest) {
            this.slayerQuestLevel = 1;
            this.slayerQuest = null;
        }
        if (!foundBossAlive) {
            this.slayerBossAlive = false;
        }
        if (!foundSkyblockTitle && this.isOnSkyblock() && (foundScoreboard || System.currentTimeMillis() - ScoreboardManager.getLastFoundScoreboard() > 10000L)) {
            MinecraftForge.EVENT_BUS.post((Event)new SkyblockLeftEvent());
        }
    }

    private void parseSlayerProgress(String line) {
        if (!this.main.getConfigValues().isEnabled(Feature.BOSS_APPROACH_ALERT)) {
            return;
        }
        Matcher matcher = SLAYER_PROGRESS_REGEX.matcher(line);
        if (matcher.find()) {
            float completion;
            String progressString = matcher.group("progress");
            String totalString = matcher.group("total");
            float progress = Float.parseFloat(TextUtils.keepFloatCharactersOnly(progressString));
            float total = Float.parseFloat(TextUtils.keepFloatCharactersOnly(totalString));
            if (progressString.contains("k")) {
                progress *= 1000.0f;
            }
            if (totalString.contains("k")) {
                total *= 1000.0f;
            }
            if ((double)(completion = progress / total) > 0.85) {
                if (!this.triggeredSlayerWarning || this.main.getConfigValues().isEnabled(Feature.REPEAT_SLAYER_BOSS_WARNING) && completion != this.lastCompletion) {
                    this.triggeredSlayerWarning = true;
                    this.main.getUtils().playLoudSound("random.orb", 0.5);
                    this.main.getRenderListener().setTitleFeature(Feature.BOSS_APPROACH_ALERT);
                    this.main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                }
            } else {
                this.triggeredSlayerWarning = false;
            }
            this.lastCompletion = completion;
        }
    }

    private void onCoinsChange(double coinsChange) {
    }

    public int getDefaultColor(float alphaFloat) {
        int alpha = (int)alphaFloat;
        return new Color(150, 236, 255, alpha).getRGB();
    }

    public void playLoudSound(String sound, double pitch) {
        this.playingSound = true;
        Minecraft.func_71410_x().field_71439_g.func_85030_a(sound, 1.0f, (float)pitch);
        this.playingSound = false;
    }

    public void playSound(String sound, double pitch) {
        Minecraft.func_71410_x().field_71439_g.func_85030_a(sound, 1.0f, (float)pitch);
    }

    public void playSound(String sound, double volume, double pitch) {
        Minecraft.func_71410_x().field_71439_g.func_85030_a(sound, (float)volume, (float)pitch);
    }

    public boolean enchantReforgeMatches(String reforge) {
        reforge = reforge.trim().toLowerCase(Locale.US);
        for (String desiredReforge : this.reforgeMatches) {
            if (!StringUtils.isNotEmpty((CharSequence)(desiredReforge = desiredReforge.trim().toLowerCase(Locale.US))) || !reforge.contains(desiredReforge)) continue;
            boolean foundExclusion = false;
            for (String excludedReforge : this.reforgeExclusions) {
                if (!StringUtils.isNotEmpty((CharSequence)(excludedReforge = excludedReforge.trim().toLowerCase(Locale.US))) || !reforge.contains(excludedReforge)) continue;
                foundExclusion = true;
                break;
            }
            if (foundExclusion) continue;
            return true;
        }
        return false;
    }

    public void fetchMagmaBossEstimate() {
        SkyblockAddons.runAsync(() -> {
            block5: {
                boolean magmaTimerEnabled = this.main.getConfigValues().isEnabled(Feature.MAGMA_BOSS_TIMER);
                if (!magmaTimerEnabled) {
                    this.logger.info("Getting magma boss spawn estimate from server...");
                }
                try {
                    URL url = new URL("https://hypixel-api.inventivetalent.org/api/skyblock/bosstimer/magma/estimatedSpawn");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", USER_AGENT);
                    if (!magmaTimerEnabled) {
                        this.logger.info("Got response code " + connection.getResponseCode());
                    }
                    JsonObject responseJson = (JsonObject)SkyblockAddons.getGson().fromJson((Reader)new InputStreamReader(connection.getInputStream()), JsonObject.class);
                    connection.disconnect();
                    long estimate = responseJson.get("estimate").getAsLong();
                    long currentTime = responseJson.get("queryTime").getAsLong();
                    int magmaSpawnTime = (int)((estimate - currentTime) / 1000L);
                    if (!magmaTimerEnabled) {
                        this.logger.info("Query time was " + currentTime + ", server time estimate is " + estimate + ". Updating magma boss spawn to be in " + magmaSpawnTime + " seconds.");
                    }
                    this.main.getPlayerListener().setMagmaTime(magmaSpawnTime);
                    this.main.getPlayerListener().setMagmaAccuracy(EnumUtils.MagmaTimerAccuracy.ABOUT);
                }
                catch (IOException ex) {
                    if (magmaTimerEnabled) break block5;
                    this.logger.warn("Failed to get magma boss spawn estimate from server");
                }
            }
        });
    }

    public void sendInventiveTalentPingRequest(EnumUtils.MagmaEvent event) {
        SkyblockAddons.runAsync(() -> {
            block17: {
                boolean magmaTimerEnabled = this.main.getConfigValues().isEnabled(Feature.MAGMA_BOSS_TIMER);
                if (!magmaTimerEnabled) {
                    this.logger.info("Posting event " + event.getInventiveTalentEvent() + " to InventiveTalent API");
                }
                try {
                    String urlString = "https://hypixel-api.inventivetalent.org/api/skyblock/bosstimer/magma/addEvent";
                    if (event == EnumUtils.MagmaEvent.PING) {
                        urlString = "https://hypixel-api.inventivetalent.org/api/skyblock/bosstimer/magma/ping";
                    }
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("User-Agent", USER_AGENT);
                    Minecraft mc = Minecraft.func_71410_x();
                    if (mc == null || mc.field_71439_g == null) break block17;
                    String postString = event == EnumUtils.MagmaEvent.PING ? "isModRequest=true&minecraftUser=" + mc.field_71439_g.func_70005_c_() + "&lastFocused=" + System.currentTimeMillis() / 1000L + "&serverId=" + this.serverID : "type=" + event.getInventiveTalentEvent() + "&isModRequest=true&minecraftUser=" + mc.field_71439_g.func_70005_c_() + "&serverId=" + this.serverID;
                    connection.setDoOutput(true);
                    try (DataOutputStream out = new DataOutputStream(connection.getOutputStream());){
                        out.writeBytes(postString);
                        out.flush();
                    }
                    if (!magmaTimerEnabled) {
                        this.logger.info("Got response code " + connection.getResponseCode());
                    }
                    connection.disconnect();
                }
                catch (IOException ex) {
                    if (magmaTimerEnabled) break block17;
                    this.logger.warn("Failed to post event to server");
                }
            }
        });
    }

    public File getSBAFolder() {
        return Loader.instance().activeModContainer().getSource().getParentFile();
    }

    public boolean isHalloween() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) == 9 && calendar.get(5) == 31;
    }

    public int getDefaultBlue(int alpha) {
        return new Color(160, 225, 229, alpha).getRGB();
    }

    public float normalizeValueNoStep(float value, float min, float max) {
        return MathHelper.func_76131_a((float)((this.snapNearDefaultValue(value) - min) / (max - min)), (float)0.0f, (float)1.0f);
    }

    public float snapNearDefaultValue(float value) {
        if (value != 1.0f && (double)value > 0.95 && (double)value < 1.05) {
            return 1.0f;
        }
        return value;
    }

    public static String roundForString(float value, int decimalPlaces) {
        return String.format("%." + decimalPlaces + "f", Float.valueOf(value));
    }

    public String[] wrapSplitText(String text, int wrapLength) {
        return WordUtils.wrap((String)text, (int)wrapLength).replace("\r", "").split(Pattern.quote("\n"));
    }

    public boolean itemIsInHotbar(ItemStack itemStack) {
        ItemStack[] inventory = Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a;
        for (int slot = 0; slot < 9; ++slot) {
            if (inventory[slot] != itemStack) continue;
            return true;
        }
        return false;
    }

    public boolean isAxe(Item item) {
        return item instanceof ItemAxe;
    }

    public void enableStandardGLOptions() {
        this.depthEnabled = GL11.glIsEnabled((int)2929);
        this.blendEnabled = GL11.glIsEnabled((int)3042);
        this.alphaEnabled = GL11.glIsEnabled((int)3008);
        this.blendFunctionSrcFactor = GL11.glGetInteger((int)3041);
        this.blendFunctionDstFactor = GL11.glGetInteger((int)3040);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179141_d();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public void restoreGLOptions() {
        if (this.depthEnabled) {
            GlStateManager.func_179126_j();
        }
        if (!this.alphaEnabled) {
            GlStateManager.func_179118_c();
        }
        if (!this.blendEnabled) {
            GlStateManager.func_179084_k();
        }
        GlStateManager.func_179112_b((int)this.blendFunctionSrcFactor, (int)this.blendFunctionDstFactor);
    }

    public boolean isModLoaded(String modId) {
        return this.isModLoaded(modId, null);
    }

    public boolean isModLoaded(String modId, String version) {
        boolean isLoaded = Loader.isModLoaded((String)modId);
        if (isLoaded && version != null) {
            for (ModContainer modContainer : Loader.instance().getModList()) {
                if (!modContainer.getModId().equals(modId) || !modContainer.getVersion().equals(version)) continue;
                return true;
            }
            return false;
        }
        return isLoaded;
    }

    public static WorldClient getDummyWorld() {
        return DUMMY_WORLD;
    }

    public float[] getCurrentGLTransformations() {
        FloatBuffer buf = BufferUtils.createFloatBuffer((int)16);
        GL11.glGetFloat((int)2982, (FloatBuffer)buf);
        buf.rewind();
        Matrix4f mat = new Matrix4f();
        mat.load(buf);
        float x = mat.m30;
        float y = mat.m31;
        float z = mat.m32;
        float scale = (float)Math.sqrt(mat.m00 * mat.m00 + mat.m01 * mat.m01 + mat.m02 * mat.m02);
        return new float[]{x, y, z, scale};
    }

    public static EntityPlayer getPlayerFromName(String name) {
        return Minecraft.func_71410_x().field_71441_e.func_72924_a(name);
    }

    public boolean isEmptyGlassPane(ItemStack itemStack) {
        return itemStack != null && (itemStack.func_77973_b() == Item.func_150898_a((Block)Blocks.field_150397_co) || itemStack.func_77973_b() == Item.func_150898_a((Block)Blocks.field_150410_aZ)) && itemStack.func_82837_s() && TextUtils.stripColor(itemStack.func_82833_r().trim()).isEmpty();
    }

    public boolean isGlassPaneColor(ItemStack itemStack, EnumDyeColor color) {
        return itemStack != null && itemStack.func_77960_j() == color.func_176765_a();
    }

    public static float getPartialTicks() {
        return Minecraft.func_71410_x().field_71428_T.field_74281_c;
    }

    public static long getCurrentTick() {
        return SkyblockAddons.getInstance().getNewScheduler().getTotalTicks();
    }

    public static Vector3d getPlayerViewPosition() {
        long currentTick = Utils.getCurrentTick();
        float currentPartialTicks = Utils.getPartialTicks();
        if (currentTick != lastTick || currentPartialTicks != lastPartialTicks) {
            Entity renderViewEntity = Minecraft.func_71410_x().func_175606_aa();
            Utils.interpolatedPlayerPosition.x = MathUtils.interpolateX(renderViewEntity, currentPartialTicks);
            Utils.interpolatedPlayerPosition.y = MathUtils.interpolateY(renderViewEntity, currentPartialTicks);
            Utils.interpolatedPlayerPosition.z = MathUtils.interpolateZ(renderViewEntity, currentPartialTicks);
            lastTick = currentTick;
            lastPartialTicks = currentPartialTicks;
        }
        return interpolatedPlayerPosition;
    }

    public static byte[] toByteArray(BufferedInputStream inputStream) throws IOException {
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray((InputStream)inputStream);
        }
        finally {
            inputStream.close();
        }
        return bytes;
    }

    public static Entity getEntityByUUID(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        for (Entity entity : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
            if (!entity.func_110124_au().equals(uuid)) continue;
            return entity;
        }
        return null;
    }

    public static int getBlockMetaId(Block block, int meta) {
        return Block.func_176210_f((IBlockState)block.func_176203_a(meta));
    }

    public Map<Attribute, MutableInt> getAttributes() {
        return this.attributes;
    }

    public ItemDropChecker getItemDropChecker() {
        return this.itemDropChecker;
    }

    public List<String> getReforgeMatches() {
        return this.reforgeMatches;
    }

    public List<String> getReforgeExclusions() {
        return this.reforgeExclusions;
    }

    public boolean isOnSkyblock() {
        return this.onSkyblock;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public boolean isPlayingSound() {
        return this.playingSound;
    }

    public String getServerID() {
        return this.serverID;
    }

    public int getLastHoveredSlot() {
        return this.lastHoveredSlot;
    }

    public boolean isUsingFSRcontainerPreviewTexture() {
        return this.usingFSRcontainerPreviewTexture;
    }

    public SkyblockDate getCurrentDate() {
        return this.currentDate;
    }

    public double getPurse() {
        return this.purse;
    }

    public int getJerryWave() {
        return this.jerryWave;
    }

    public boolean isAlpha() {
        return this.alpha;
    }

    public boolean isInDungeon() {
        return this.inDungeon;
    }

    public boolean isFadingIn() {
        return this.fadingIn;
    }

    public long getLastDamaged() {
        return this.lastDamaged;
    }

    public EnumUtils.SlayerQuest getSlayerQuest() {
        return this.slayerQuest;
    }

    public int getSlayerQuestLevel() {
        return this.slayerQuestLevel;
    }

    public boolean isSlayerBossAlive() {
        return this.slayerBossAlive;
    }

    public SkyblockAddons getMain() {
        return this.main;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public boolean isTriggeredSlayerWarning() {
        return this.triggeredSlayerWarning;
    }

    public float getLastCompletion() {
        return this.lastCompletion;
    }

    public boolean isDepthEnabled() {
        return this.depthEnabled;
    }

    public boolean isBlendEnabled() {
        return this.blendEnabled;
    }

    public boolean isAlphaEnabled() {
        return this.alphaEnabled;
    }

    public int getBlendFunctionSrcFactor() {
        return this.blendFunctionSrcFactor;
    }

    public int getBlendFunctionDstFactor() {
        return this.blendFunctionDstFactor;
    }

    public void setAttributes(Map<Attribute, MutableInt> attributes) {
        this.attributes = attributes;
    }

    public void setReforgeMatches(List<String> reforgeMatches) {
        this.reforgeMatches = reforgeMatches;
    }

    public void setReforgeExclusions(List<String> reforgeExclusions) {
        this.reforgeExclusions = reforgeExclusions;
    }

    public void setOnSkyblock(boolean onSkyblock) {
        this.onSkyblock = onSkyblock;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setPlayingSound(boolean playingSound) {
        this.playingSound = playingSound;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public void setLastHoveredSlot(int lastHoveredSlot) {
        this.lastHoveredSlot = lastHoveredSlot;
    }

    public void setUsingFSRcontainerPreviewTexture(boolean usingFSRcontainerPreviewTexture) {
        this.usingFSRcontainerPreviewTexture = usingFSRcontainerPreviewTexture;
    }

    public void setCurrentDate(SkyblockDate currentDate) {
        this.currentDate = currentDate;
    }

    public void setPurse(double purse) {
        this.purse = purse;
    }

    public void setJerryWave(int jerryWave) {
        this.jerryWave = jerryWave;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    public void setInDungeon(boolean inDungeon) {
        this.inDungeon = inDungeon;
    }

    public void setFadingIn(boolean fadingIn) {
        this.fadingIn = fadingIn;
    }

    public void setLastDamaged(long lastDamaged) {
        this.lastDamaged = lastDamaged;
    }

    public void setSlayerQuest(EnumUtils.SlayerQuest slayerQuest) {
        this.slayerQuest = slayerQuest;
    }

    public void setSlayerQuestLevel(int slayerQuestLevel) {
        this.slayerQuestLevel = slayerQuestLevel;
    }

    public void setSlayerBossAlive(boolean slayerBossAlive) {
        this.slayerBossAlive = slayerBossAlive;
    }

    public void setMain(SkyblockAddons main) {
        this.main = main;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setTriggeredSlayerWarning(boolean triggeredSlayerWarning) {
        this.triggeredSlayerWarning = triggeredSlayerWarning;
    }

    public void setLastCompletion(float lastCompletion) {
        this.lastCompletion = lastCompletion;
    }

    public void setDepthEnabled(boolean depthEnabled) {
        this.depthEnabled = depthEnabled;
    }

    public void setBlendEnabled(boolean blendEnabled) {
        this.blendEnabled = blendEnabled;
    }

    public void setAlphaEnabled(boolean alphaEnabled) {
        this.alphaEnabled = alphaEnabled;
    }

    public void setBlendFunctionSrcFactor(int blendFunctionSrcFactor) {
        this.blendFunctionSrcFactor = blendFunctionSrcFactor;
    }

    public void setBlendFunctionDstFactor(int blendFunctionDstFactor) {
        this.blendFunctionDstFactor = blendFunctionDstFactor;
    }

    public Location getLocation() {
        return this.location;
    }

    static {
        interpolatedPlayerPosition = new Vector3d();
    }
}

