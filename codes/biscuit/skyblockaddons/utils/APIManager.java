/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.client.Minecraft
 *  org.apache.logging.log4j.Logger
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerBoss;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerTracker;
import codes.biscuit.skyblockaddons.utils.Utils;
import codes.biscuit.skyblockaddons.utils.pojo.PlayerData;
import codes.biscuit.skyblockaddons.utils.pojo.Profile;
import codes.biscuit.skyblockaddons.utils.pojo.ProfileMembers;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;

public class APIManager {
    private static final APIManager INSTANCE = new APIManager();
    private static final String BASE_URL = "https://api.slothpixel.me/api/";
    private static final String PLAYER = "https://api.slothpixel.me/api/players/%s";
    private static final String SKYBLOCK_PROFILES = "https://api.slothpixel.me/api/skyblock/profiles/%s";
    private static final String SKYBLOCK_PROFILE = "https://api.slothpixel.me/api/skyblock/profile/%s/%s";
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private final Logger logger = SkyblockAddons.getLogger();
    private boolean firstSwitch = true;

    public void onProfileSwitch(String profileName) {
        if (profileName != null) {
            String uuid = Minecraft.func_71410_x().field_71439_g.func_110124_au().toString().replace("-", "");
            SkyblockAddons.runAsync(() -> {
                if (this.firstSwitch) {
                    this.pullPlayer(uuid);
                    this.firstSwitch = false;
                }
                this.pullProfiles(uuid, profileName);
            });
        }
    }

    public void pullPlayer(String uuid) {
        this.logger.info("Grabbing player API data for UUID " + uuid + "...");
        try {
            URL url = new URL(String.format(PLAYER, uuid));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", Utils.USER_AGENT);
            this.logger.info("Got response code " + connection.getResponseCode());
            PlayerData playerData = (PlayerData)SkyblockAddons.getGson().fromJson((Reader)new InputStreamReader(connection.getInputStream()), PlayerData.class);
            connection.disconnect();
            if (playerData != null && playerData.getLanguage() != null) {
                this.main.getPersistentValuesManager().getPersistentValues().setHypixelLanguage(playerData.getLanguage());
            }
        }
        catch (Exception ex) {
            this.logger.warn("Failed to grab player's profiles API data!");
            this.logger.catching((Throwable)ex);
        }
    }

    public void pullProfiles(String uuid, String profileName) {
        this.logger.info("Grabbing player's profiles API data for UUID " + uuid + " & profile name " + profileName + "...");
        try {
            URL url = new URL(String.format(SKYBLOCK_PROFILES, uuid));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", Utils.USER_AGENT);
            this.logger.info("Got response code " + connection.getResponseCode());
            Map profiles = (Map)SkyblockAddons.getGson().fromJson((Reader)new InputStreamReader(connection.getInputStream()), new TypeToken<HashMap<String, Profile>>(){}.getType());
            connection.disconnect();
            for (Map.Entry entry : profiles.entrySet()) {
                String profileID = (String)entry.getKey();
                Profile profile = (Profile)entry.getValue();
                if (!profileName.equals(profile.getCute_name())) continue;
                this.logger.info("Found profile matching " + profileName + " with ID " + profileID + "! Pulling profile data...");
                this.pullProfileData(uuid, profileID);
                return;
            }
            this.logger.info("Did not find profile matching " + profileName + "!");
        }
        catch (Exception ex) {
            this.logger.warn("Failed to grab player's profiles API data!");
            this.logger.catching((Throwable)ex);
        }
    }

    public void pullProfileData(String uuid, String profileID) {
        this.logger.info("Grabbing profile API data for UUID " + uuid + " & profile ID " + profileID + "...");
        try {
            URL url = new URL(String.format(SKYBLOCK_PROFILE, uuid, profileID));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", Utils.USER_AGENT);
            this.logger.info("Got response code " + connection.getResponseCode());
            ProfileMembers profileMembers = (ProfileMembers)SkyblockAddons.getGson().fromJson((Reader)new InputStreamReader(connection.getInputStream()), ProfileMembers.class);
            connection.disconnect();
            if (profileMembers.getMembers().containsKey(uuid)) {
                ProfileMembers.PetMilestones petMilestones;
                ProfileMembers.Stats stats;
                ProfileMembers.MemberData memberData = profileMembers.getMembers().get(uuid);
                ProfileMembers.Slayers slayers = memberData.getSlayer();
                if (slayers != null) {
                    int total;
                    ProfileMembers.SlayerData zombie = memberData.getSlayer().getZombie();
                    ProfileMembers.SlayerData spider = memberData.getSlayer().getSpider();
                    ProfileMembers.SlayerData wolf = memberData.getSlayer().getWolf();
                    ProfileMembers.SlayerData enderman = memberData.getSlayer().getEnderman();
                    if (zombie != null && zombie.getKills_tier() != null) {
                        total = 0;
                        for (Integer kills : zombie.getKills_tier().values()) {
                            total += kills.intValue();
                        }
                        SlayerTracker.getInstance().setKillCount(SlayerBoss.REVENANT, total);
                    }
                    if (spider != null && spider.getKills_tier() != null) {
                        total = 0;
                        for (Integer kills : spider.getKills_tier().values()) {
                            total += kills.intValue();
                        }
                        SlayerTracker.getInstance().setKillCount(SlayerBoss.TARANTULA, total);
                    }
                    if (wolf != null && wolf.getKills_tier() != null) {
                        total = 0;
                        for (Integer kills : wolf.getKills_tier().values()) {
                            total += kills.intValue();
                        }
                        SlayerTracker.getInstance().setKillCount(SlayerBoss.SVEN, total);
                    }
                    if (enderman != null && enderman.getKills_tier() != null) {
                        total = 0;
                        for (Integer kills : enderman.getKills_tier().values()) {
                            total += kills.intValue();
                        }
                        SlayerTracker.getInstance().setKillCount(SlayerBoss.VOIDGLOOM, total);
                    }
                }
                if ((stats = memberData.getStats()) != null && (petMilestones = stats.getPet_milestones()) != null) {
                    this.main.getPersistentValuesManager().getPersistentValues().setOresMined(petMilestones.getOre_mined());
                    this.main.getPersistentValuesManager().getPersistentValues().setSeaCreaturesKilled(petMilestones.getSea_creatures_killed());
                }
                this.main.getPersistentValuesManager().saveValues();
            }
        }
        catch (Exception ex) {
            this.logger.warn("Failed to grab profile API data!");
            this.logger.catching((Throwable)ex);
        }
    }

    public static APIManager getInstance() {
        return INSTANCE;
    }
}

