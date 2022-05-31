/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 */
package codes.biscuit.skyblockaddons.core.seacreatures;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.core.seacreatures.SeaCreature;
import codes.biscuit.skyblockaddons.utils.ConnectUtils;
import codes.biscuit.skyblockaddons.utils.DataUtils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SeaCreatureManager {
    private static final SeaCreatureManager INSTANCE = new SeaCreatureManager();
    private Map<String, SeaCreature> seaCreatures = new LinkedHashMap<String, SeaCreature>();
    private final Set<String> allSeaCreatureSpawnMessages = new HashSet<String>();
    private final Set<String> legendarySeaCreatureSpawnMessages = new HashSet<String>();

    public void pullSeaCreatures() {
        InputStream localStream = DataUtils.class.getResourceAsStream("/seaCreatures.json");
        try (JsonReader jsonReader = new JsonReader((Reader)new BufferedReader(new InputStreamReader(localStream, StandardCharsets.UTF_8)));){
            this.seaCreatures = (Map)SkyblockAddons.getGson().fromJson(jsonReader, new TypeToken<Map<String, SeaCreature>>(){}.getType());
            for (SeaCreature sc : this.seaCreatures.values()) {
                this.allSeaCreatureSpawnMessages.add(sc.getSpawnMessage());
                if (sc.getRarity() != ItemRarity.LEGENDARY) continue;
                this.legendarySeaCreatureSpawnMessages.add(sc.getSpawnMessage());
            }
        }
        catch (Exception ex) {
            SkyblockAddons.getLogger().error("An error occurred while reading local sea creatures!");
        }
        ConnectUtils.get("https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons-Data/main/skyblock/seaCreatures.json", onlineStream -> {
            try (JsonReader jsonReader = new JsonReader((Reader)new BufferedReader(new InputStreamReader((InputStream)onlineStream, StandardCharsets.UTF_8)));){
                this.seaCreatures = (Map)SkyblockAddons.getGson().fromJson(jsonReader, new TypeToken<Map<String, SeaCreature>>(){}.getType());
                this.allSeaCreatureSpawnMessages.clear();
                this.legendarySeaCreatureSpawnMessages.clear();
                for (SeaCreature sc : this.seaCreatures.values()) {
                    this.allSeaCreatureSpawnMessages.add(sc.getSpawnMessage());
                    if (sc.getRarity() != ItemRarity.LEGENDARY) continue;
                    this.legendarySeaCreatureSpawnMessages.add(sc.getSpawnMessage());
                }
                SkyblockAddons.getLogger().info("Successfully fetched sea creatures!");
            }
            catch (Exception ex) {
                SkyblockAddons.getLogger().error("An error occurred while pulling online sea creatures!", (Throwable)ex);
            }
        });
    }

    public static SeaCreatureManager getInstance() {
        return INSTANCE;
    }

    public Set<String> getAllSeaCreatureSpawnMessages() {
        return this.allSeaCreatureSpawnMessages;
    }

    public Set<String> getLegendarySeaCreatureSpawnMessages() {
        return this.legendarySeaCreatureSpawnMessages;
    }
}

