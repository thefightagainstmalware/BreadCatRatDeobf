/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package codes.biscuit.skyblockaddons.features.slayertracker;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.features.ItemDiff;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerBoss;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerDrop;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerTrackerData;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.skyblockdata.Rune;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;

public class SlayerTracker {
    private static final SlayerTracker instance = new SlayerTracker();
    private final transient Map<Long, List<ItemDiff>> recentInventoryDifferences = new HashMap<Long, List<ItemDiff>>();
    private transient long lastSlayerCompleted = -1L;

    public int getSlayerKills(SlayerBoss slayerBoss) {
        SlayerTrackerData slayerTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getSlayerTracker();
        return slayerTrackerData.getSlayerKills().getOrDefault((Object)slayerBoss, 0);
    }

    public int getDropCount(SlayerDrop slayerDrop) {
        SlayerTrackerData slayerTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getSlayerTracker();
        return slayerTrackerData.getSlayerDropCounts().getOrDefault((Object)slayerDrop, 0);
    }

    public void completedSlayer(String slayerTypeText) {
        SlayerBoss slayerBoss = SlayerBoss.getFromMobType(slayerTypeText);
        if (slayerBoss != null) {
            SlayerTrackerData slayerTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getSlayerTracker();
            slayerTrackerData.getSlayerKills().put(slayerBoss, slayerTrackerData.getSlayerKills().getOrDefault((Object)slayerBoss, 0) + 1);
            slayerTrackerData.setLastKilledBoss(slayerBoss);
            this.lastSlayerCompleted = System.currentTimeMillis();
            SkyblockAddons.getInstance().getPersistentValuesManager().saveValues();
        }
    }

    public void checkInventoryDifferenceForDrops(List<ItemDiff> newInventoryDifference) {
        this.recentInventoryDifferences.entrySet().removeIf(entry -> System.currentTimeMillis() - (Long)entry.getKey() > 1000L);
        this.recentInventoryDifferences.put(System.currentTimeMillis(), newInventoryDifference);
        SlayerTrackerData slayerTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getSlayerTracker();
        if (slayerTrackerData.getLastKilledBoss() == null || this.lastSlayerCompleted == -1L || System.currentTimeMillis() - this.lastSlayerCompleted > 30000L) {
            return;
        }
        for (List<ItemDiff> inventoryDifference : this.recentInventoryDifferences.values()) {
            for (ItemDiff itemDifference : inventoryDifference) {
                if (itemDifference.getAmount() < 1) continue;
                for (SlayerDrop drop : slayerTrackerData.getLastKilledBoss().getDrops()) {
                    if (!drop.getSkyblockID().equals(ItemUtils.getSkyblockItemID(itemDifference.getExtraAttributes()))) continue;
                    Rune rune = ItemUtils.getRuneData(itemDifference.getExtraAttributes());
                    if (drop.getRuneID() != null && (rune == null || rune.getType() == null || !rune.getType().equals(drop.getRuneID()))) continue;
                    if (drop.getSkyblockID().equals("ENCHANTED_BOOK")) {
                        boolean match = true;
                        NBTTagCompound diffTag = itemDifference.getExtraAttributes().func_74775_l("enchantments");
                        NBTTagCompound dropTag = ItemUtils.getEnchantments(drop.getItemStack());
                        if (diffTag != null && dropTag != null && diffTag.func_150296_c().size() == dropTag.func_150296_c().size()) {
                            for (String key : diffTag.func_150296_c()) {
                                if (dropTag.func_150297_b(key, 3) && dropTag.func_74762_e(key) == diffTag.func_74762_e(key)) continue;
                                match = false;
                                break;
                            }
                        } else {
                            match = false;
                        }
                        if (!match) continue;
                    }
                    slayerTrackerData.getSlayerDropCounts().put(drop, slayerTrackerData.getSlayerDropCounts().getOrDefault((Object)drop, 0) + itemDifference.getAmount());
                }
            }
        }
        this.recentInventoryDifferences.clear();
    }

    public void setStatManually(String[] args) {
        SlayerDrop slayerDrop;
        SlayerBoss slayerBoss = SlayerBoss.getFromMobType(args[1]);
        if (slayerBoss == null) {
            throw new IllegalArgumentException(Translations.getMessage("commandUsage.sba.slayer.invalidBoss", args[1]));
        }
        SlayerTrackerData slayerTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getSlayerTracker();
        if (args[2].equalsIgnoreCase("kills")) {
            int count = Integer.parseInt(args[3]);
            slayerTrackerData.getSlayerKills().put(slayerBoss, count);
            SkyblockAddons.getInstance().getUtils().sendMessage(Translations.getMessage("commandUsage.sba.slayer.killsSet", args[1], args[3]));
            SkyblockAddons.getInstance().getPersistentValuesManager().saveValues();
            return;
        }
        try {
            slayerDrop = SlayerDrop.valueOf(args[2].toUpperCase());
        }
        catch (IllegalArgumentException ex) {
            slayerDrop = null;
        }
        if (slayerDrop != null) {
            int count = Integer.parseInt(args[3]);
            slayerTrackerData.getSlayerDropCounts().put(slayerDrop, count);
            SkyblockAddons.getInstance().getUtils().sendMessage(Translations.getMessage("commandUsage.sba.slayer.statSet", args[2], args[1], args[3]));
            SkyblockAddons.getInstance().getPersistentValuesManager().saveValues();
            return;
        }
        throw new IllegalArgumentException(Translations.getMessage("commandUsage.sba.slayer.invalidStat", args[1]));
    }

    public void setKillCount(SlayerBoss slayerBoss, int kills) {
        SlayerTrackerData slayerTrackerData = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getSlayerTracker();
        slayerTrackerData.getSlayerKills().put(slayerBoss, kills);
    }

    public static SlayerTracker getInstance() {
        return instance;
    }
}

