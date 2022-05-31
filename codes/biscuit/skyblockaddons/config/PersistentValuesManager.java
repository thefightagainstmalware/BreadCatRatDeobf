/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.config;

import codes.biscuit.hypixellocalizationlib.HypixelLanguage;
import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.features.FetchurManager;
import codes.biscuit.skyblockaddons.features.backpacks.CompressedStorage;
import codes.biscuit.skyblockaddons.features.craftingpatterns.CraftingPattern;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonTrackerData;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerTrackerData;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class PersistentValuesManager {
    private static final ReentrantLock SAVE_LOCK = new ReentrantLock();
    private final File persistentValuesFile;
    private PersistentValues persistentValues = new PersistentValues();

    public PersistentValuesManager(File configDir) {
        this.persistentValuesFile = new File(configDir.getAbsolutePath() + "/skyblockaddons_persistent.cfg");
    }

    public void loadValues() {
        if (this.persistentValuesFile.exists()) {
            try (FileReader reader = new FileReader(this.persistentValuesFile);){
                this.persistentValues = (PersistentValues)SkyblockAddons.getGson().fromJson((Reader)reader, PersistentValues.class);
            }
            catch (Exception ex) {
                SkyblockAddons.getLogger().error("There was an error while trying to load persistent values.");
                SkyblockAddons.getLogger().catching((Throwable)ex);
                this.saveValues();
            }
        } else {
            this.saveValues();
        }
        FetchurManager.getInstance().postPersistentConfigLoad();
    }

    public void saveValues() {
        SkyblockAddons.runAsync(() -> {
            if (!SAVE_LOCK.tryLock()) {
                return;
            }
            try {
                this.persistentValuesFile.createNewFile();
                try (FileWriter writer = new FileWriter(this.persistentValuesFile);){
                    SkyblockAddons.getGson().toJson((Object)this.persistentValues, (Appendable)writer);
                }
            }
            catch (Exception ex) {
                SkyblockAddons.getLogger().error("An error occurred while attempting to save persistent values!");
                SkyblockAddons.getLogger().catching((Throwable)ex);
            }
            SAVE_LOCK.unlock();
        });
    }

    public void addEyeResetKills() {
        this.persistentValues.summoningEyeCount++;
        PersistentValues persistentValues = this.persistentValues;
        persistentValues.totalKills = persistentValues.totalKills + this.persistentValues.kills;
        this.persistentValues.kills = -1;
        this.saveValues();
    }

    public void setPersistentValues(PersistentValues persistentValues) {
        this.persistentValues = persistentValues;
    }

    public File getPersistentValuesFile() {
        return this.persistentValuesFile;
    }

    public PersistentValues getPersistentValues() {
        return this.persistentValues;
    }

    public static class PersistentValues {
        private int kills = 0;
        private int totalKills = 0;
        private int summoningEyeCount = 0;
        private SlayerTrackerData slayerTracker = new SlayerTrackerData();
        private DragonTrackerData dragonTracker = new DragonTrackerData();
        private Map<String, CompressedStorage> storageCache = new HashMap<String, CompressedStorage>();
        private boolean blockCraftingIncompletePatterns = true;
        private CraftingPattern selectedCraftingPattern = CraftingPattern.FREE;
        private int oresMined = 0;
        private int seaCreaturesKilled = 0;
        private long lastTimeFetchur;
        private HypixelLanguage hypixelLanguage = HypixelLanguage.ENGLISH;

        public int getKills() {
            return this.kills;
        }

        public int getTotalKills() {
            return this.totalKills;
        }

        public int getSummoningEyeCount() {
            return this.summoningEyeCount;
        }

        public SlayerTrackerData getSlayerTracker() {
            return this.slayerTracker;
        }

        public DragonTrackerData getDragonTracker() {
            return this.dragonTracker;
        }

        public Map<String, CompressedStorage> getStorageCache() {
            return this.storageCache;
        }

        public boolean isBlockCraftingIncompletePatterns() {
            return this.blockCraftingIncompletePatterns;
        }

        public CraftingPattern getSelectedCraftingPattern() {
            return this.selectedCraftingPattern;
        }

        public int getOresMined() {
            return this.oresMined;
        }

        public int getSeaCreaturesKilled() {
            return this.seaCreaturesKilled;
        }

        public long getLastTimeFetchur() {
            return this.lastTimeFetchur;
        }

        public HypixelLanguage getHypixelLanguage() {
            return this.hypixelLanguage;
        }

        public void setKills(int kills) {
            this.kills = kills;
        }

        public void setTotalKills(int totalKills) {
            this.totalKills = totalKills;
        }

        public void setSummoningEyeCount(int summoningEyeCount) {
            this.summoningEyeCount = summoningEyeCount;
        }

        public void setSlayerTracker(SlayerTrackerData slayerTracker) {
            this.slayerTracker = slayerTracker;
        }

        public void setDragonTracker(DragonTrackerData dragonTracker) {
            this.dragonTracker = dragonTracker;
        }

        public void setStorageCache(Map<String, CompressedStorage> storageCache) {
            this.storageCache = storageCache;
        }

        public void setBlockCraftingIncompletePatterns(boolean blockCraftingIncompletePatterns) {
            this.blockCraftingIncompletePatterns = blockCraftingIncompletePatterns;
        }

        public void setSelectedCraftingPattern(CraftingPattern selectedCraftingPattern) {
            this.selectedCraftingPattern = selectedCraftingPattern;
        }

        public void setOresMined(int oresMined) {
            this.oresMined = oresMined;
        }

        public void setSeaCreaturesKilled(int seaCreaturesKilled) {
            this.seaCreaturesKilled = seaCreaturesKilled;
        }

        public void setLastTimeFetchur(long lastTimeFetchur) {
            this.lastTimeFetchur = lastTimeFetchur;
        }

        public void setHypixelLanguage(HypixelLanguage hypixelLanguage) {
            this.hypixelLanguage = hypixelLanguage;
        }
    }
}

