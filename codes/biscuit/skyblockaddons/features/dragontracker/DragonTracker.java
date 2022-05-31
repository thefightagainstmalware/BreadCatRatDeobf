/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package codes.biscuit.skyblockaddons.features.dragontracker;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.features.ItemDiff;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonTrackerData;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonType;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonsSince;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.skyblockdata.PetInfo;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragonTracker {
    private static final List<DragonType> dummyDragons = Lists.newArrayList((Object[])new DragonType[]{DragonType.PROTECTOR, DragonType.SUPERIOR, DragonType.WISE});
    private static final DragonTracker instance = new DragonTracker();
    private transient boolean contributedToCurrentDragon = false;
    private transient long lastDragonKilled = -1L;
    private transient int eyesToPlace = 0;
    private transient Map<Long, List<ItemDiff>> recentInventoryDifferences = new HashMap<Long, List<ItemDiff>>();

    public int getDragsSince(DragonsSince dragonsSince) {
        DragonTrackerData dragonTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getDragonTracker();
        return dragonTrackerData.getDragonsSince().getOrDefault((Object)dragonsSince, 0);
    }

    public List<DragonType> getRecentDragons() {
        return SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getDragonTracker().getRecentDragons();
    }

    public void dragonSpawned(String dragonTypeText) {
        if (this.eyesToPlace > 0) {
            this.contributedToCurrentDragon = true;
            DragonTrackerData dragonTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getDragonTracker();
            DragonType dragonType = DragonType.fromName(dragonTypeText);
            if (dragonType != null) {
                if (dragonTrackerData.getRecentDragons().size() == 3) {
                    dragonTrackerData.getRecentDragons().remove(0);
                }
                dragonTrackerData.getRecentDragons().add(dragonType);
            }
            for (DragonsSince dragonsSince : DragonsSince.values()) {
                dragonTrackerData.getDragonsSince().put(dragonsSince, dragonTrackerData.getDragonsSince().getOrDefault((Object)dragonsSince, 0) + 1);
            }
            if (dragonType == DragonType.SUPERIOR) {
                dragonTrackerData.getDragonsSince().put(DragonsSince.SUPERIOR, 0);
            }
            dragonTrackerData.setEyesPlaced(dragonTrackerData.getEyesPlaced() + this.eyesToPlace);
            this.eyesToPlace = 0;
            SkyblockAddons.getInstance().getPersistentValuesManager().saveValues();
        }
    }

    public void dragonKilled() {
        if (!this.contributedToCurrentDragon) {
            return;
        }
        this.lastDragonKilled = System.currentTimeMillis();
        this.contributedToCurrentDragon = false;
    }

    public void checkInventoryDifferenceForDrops(List<ItemDiff> newInventoryDifference) {
        this.recentInventoryDifferences.entrySet().removeIf(entry -> System.currentTimeMillis() - (Long)entry.getKey() > 1000L);
        this.recentInventoryDifferences.put(System.currentTimeMillis(), newInventoryDifference);
        if (this.lastDragonKilled == -1L || System.currentTimeMillis() - this.lastDragonKilled > 60000L) {
            return;
        }
        for (List<ItemDiff> inventoryDifference : this.recentInventoryDifferences.values()) {
            for (ItemDiff itemDifference : inventoryDifference) {
                String skyBlockItemID;
                if (itemDifference.getAmount() < 1) continue;
                DragonTrackerData dragonTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getDragonTracker();
                switch (skyBlockItemID = ItemUtils.getSkyblockItemID(itemDifference.getExtraAttributes())) {
                    case "ASPECT_OF_THE_DRAGON": {
                        dragonTrackerData.getDragonsSince().put(DragonsSince.ASPECT_OF_THE_DRAGONS, 0);
                        SkyblockAddons.getInstance().getPersistentValuesManager().saveValues();
                        break;
                    }
                    case "PET": {
                        PetInfo petInfo = ItemUtils.getPetInfo(itemDifference.getExtraAttributes());
                        if (petInfo == null || !"ENDER_DRAGON".equals(petInfo.getType())) break;
                        dragonTrackerData.getDragonsSince().put(DragonsSince.ENDER_DRAGON_PET, 0);
                        SkyblockAddons.getInstance().getPersistentValuesManager().saveValues();
                        break;
                    }
                }
            }
        }
        this.recentInventoryDifferences.clear();
    }

    public void reset() {
        this.eyesToPlace = 0;
        this.contributedToCurrentDragon = false;
        this.lastDragonKilled = -1L;
    }

    public void addEye() {
        ++this.eyesToPlace;
    }

    public void removeEye() {
        --this.eyesToPlace;
    }

    public static List<DragonType> getDummyDragons() {
        return dummyDragons;
    }

    public static DragonTracker getInstance() {
        return instance;
    }
}

