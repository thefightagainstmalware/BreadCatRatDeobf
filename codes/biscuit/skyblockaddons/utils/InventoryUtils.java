/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.Multimap
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerBeacon
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.ContainerFurnace
 *  net.minecraft.inventory.ContainerHopper
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.InventoryType;
import codes.biscuit.skyblockaddons.features.ItemDiff;
import codes.biscuit.skyblockaddons.features.SlayerArmorProgress;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonTracker;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerTracker;
import codes.biscuit.skyblockaddons.misc.scheduler.Scheduler;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.Pair;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryUtils {
    private static final int SKYBLOCK_MENU_SLOT = 8;
    private static final String SKELETON_HELMET_ID = "SKELETON_HELMET";
    private static final String TOXIC_ARROW_POISON_ID = "TOXIC_ARROW_POISON";
    public static final String MADDOX_BATPHONE_ID = "AATROX_BATPHONE";
    public static final String JUNGLE_AXE_ID = "JUNGLE_AXE";
    public static final String TREECAPITATOR_ID = "TREECAPITATOR_AXE";
    public static final String CHICKEN_HEAD_ID = "CHICKEN_HEAD";
    public static final HashSet<String> BAT_PERSON_SET_IDS = new HashSet<String>(Arrays.asList("BAT_PERSON_BOOTS", "BAT_PERSON_LEGGINGS", "BAT_PERSON_CHESTPLATE", "BAT_PERSON_HELMET"));
    public static final String GRAPPLING_HOOK_ID = "GRAPPLING_HOOK";
    private static final Pattern REVENANT_UPGRADE_PATTERN = Pattern.compile("Next Upgrade: \\+([0-9]+\u2748) \\(([0-9,]+)/([0-9,]+)\\)");
    private List<ItemStack> previousInventory;
    private final Multimap<String, ItemDiff> itemPickupLog = ArrayListMultimap.create();
    private boolean inventoryWarningShown;
    private boolean wearingSkeletonHelmet;
    private boolean usingToxicArrowPoison;
    private final SlayerArmorProgress[] slayerArmorProgresses = new SlayerArmorProgress[4];
    private InventoryType inventoryType;
    String inventoryKey;
    private int inventoryPageNum;
    private String inventorySubtype;
    private final SkyblockAddons main = SkyblockAddons.getInstance();

    private List<ItemStack> copyInventory(ItemStack[] inventory) {
        ArrayList<ItemStack> copy = new ArrayList<ItemStack>(inventory.length);
        for (ItemStack item : inventory) {
            if (item != null) {
                copy.add(ItemStack.func_77944_b((ItemStack)item));
                continue;
            }
            copy.add(null);
        }
        return copy;
    }

    public void getInventoryDifference(ItemStack[] currentInventory) {
        List<ItemStack> newInventory = this.copyInventory(currentInventory);
        HashMap<String, Pair<Integer, NBTTagCompound>> previousInventoryMap = new HashMap<String, Pair<Integer, NBTTagCompound>>();
        HashMap<String, Pair<Integer, NBTTagCompound>> newInventoryMap = new HashMap<String, Pair<Integer, NBTTagCompound>>();
        if (this.previousInventory != null) {
            for (int i = 0; i < newInventory.size(); ++i) {
                NBTTagCompound extraAttributes;
                int amount;
                if (i == 8) continue;
                ItemStack previousItem = this.previousInventory.get(i);
                ItemStack newItem = newInventory.get(i);
                if (previousItem != null) {
                    amount = previousInventoryMap.containsKey(previousItem.func_82833_r()) ? (Integer)((Pair)previousInventoryMap.get(previousItem.func_82833_r())).getKey() + previousItem.field_77994_a : previousItem.field_77994_a;
                    extraAttributes = ItemUtils.getExtraAttributes(previousItem);
                    if (extraAttributes != null) {
                        extraAttributes = (NBTTagCompound)extraAttributes.func_74737_b();
                    }
                    previousInventoryMap.put(previousItem.func_82833_r(), new Pair<Integer, NBTTagCompound>(amount, extraAttributes));
                }
                if (newItem == null) continue;
                if (newItem.func_82833_r().contains(" " + (Object)((Object)ColorCode.DARK_GRAY) + "x")) {
                    String newName = newItem.func_82833_r().substring(0, newItem.func_82833_r().lastIndexOf(" "));
                    newItem.func_151001_c(newName);
                }
                amount = newInventoryMap.containsKey(newItem.func_82833_r()) ? (Integer)((Pair)newInventoryMap.get(newItem.func_82833_r())).getKey() + newItem.field_77994_a : newItem.field_77994_a;
                extraAttributes = ItemUtils.getExtraAttributes(newItem);
                if (extraAttributes != null) {
                    extraAttributes = (NBTTagCompound)extraAttributes.func_74737_b();
                }
                newInventoryMap.put(newItem.func_82833_r(), new Pair<Integer, NBTTagCompound>(amount, extraAttributes));
            }
            LinkedList<ItemDiff> inventoryDifference = new LinkedList<ItemDiff>();
            HashSet keySet = new HashSet(previousInventoryMap.keySet());
            keySet.addAll(newInventoryMap.keySet());
            keySet.forEach(key -> {
                int diff;
                int previousAmount = 0;
                if (previousInventoryMap.containsKey(key)) {
                    previousAmount = (Integer)((Pair)previousInventoryMap.get(key)).getKey();
                }
                int newAmount = 0;
                if (newInventoryMap.containsKey(key)) {
                    newAmount = (Integer)((Pair)newInventoryMap.get(key)).getKey();
                }
                if ((diff = newAmount - previousAmount) != 0) {
                    inventoryDifference.add(new ItemDiff((String)key, diff, (NBTTagCompound)((Pair)newInventoryMap.getOrDefault(key, (Pair<Integer, NBTTagCompound>)previousInventoryMap.get(key))).getValue()));
                }
            });
            DragonTracker.getInstance().checkInventoryDifferenceForDrops(inventoryDifference);
            SlayerTracker.getInstance().checkInventoryDifferenceForDrops(inventoryDifference);
            if (this.main.getConfigValues().isEnabled(Feature.ITEM_PICKUP_LOG)) {
                for (ItemDiff diff : inventoryDifference) {
                    Collection itemDiffs = this.itemPickupLog.get((Object)diff.getDisplayName());
                    if (itemDiffs.size() <= 0) {
                        this.itemPickupLog.put((Object)diff.getDisplayName(), (Object)diff);
                        continue;
                    }
                    boolean added = false;
                    for (ItemDiff loopDiff : itemDiffs) {
                        if ((diff.getAmount() >= 0 || loopDiff.getAmount() >= 0) && (diff.getAmount() <= 0 || loopDiff.getAmount() <= 0)) continue;
                        loopDiff.add(diff.getAmount());
                        added = true;
                    }
                    if (added) continue;
                    this.itemPickupLog.put((Object)diff.getDisplayName(), (Object)diff);
                }
            }
        }
        this.previousInventory = newInventory;
    }

    public void resetPreviousInventory() {
        this.previousInventory = null;
    }

    public void cleanUpPickupLog() {
        this.itemPickupLog.entries().removeIf(entry -> ((ItemDiff)entry.getValue()).getLifetime() > 5000L);
    }

    public void checkIfInventoryIsFull(Minecraft mc, EntityPlayerSP p) {
        if (this.main.getUtils().isOnSkyblock() && this.main.getConfigValues().isEnabled(Feature.FULL_INVENTORY_WARNING)) {
            for (int i = 0; i < p.field_71071_by.field_70462_a.length; ++i) {
                if (p.field_71071_by.field_70462_a[i] != null || i == 8) continue;
                if (this.inventoryWarningShown) {
                    this.main.getScheduler().removeQueuedFullInventoryWarnings();
                }
                this.inventoryWarningShown = false;
                return;
            }
            if (mc.field_71462_r == null && this.main.getPlayerListener().didntRecentlyJoinWorld() && !this.inventoryWarningShown) {
                this.showFullInventoryWarning();
                this.main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                if (this.main.getConfigValues().isEnabled(Feature.REPEAT_FULL_INVENTORY_WARNING)) {
                    this.main.getScheduler().schedule(Scheduler.CommandType.SHOW_FULL_INVENTORY_WARNING, 10, new Object[0]);
                    this.main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, 10 + this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                }
                this.inventoryWarningShown = true;
            }
        }
    }

    public void showFullInventoryWarning() {
        this.main.getUtils().playLoudSound("random.orb", 0.5);
        this.main.getRenderListener().setTitleFeature(Feature.FULL_INVENTORY_WARNING);
    }

    public void checkIfWearingSkeletonHelmet(EntityPlayerSP p) {
        if (this.main.getConfigValues().isEnabled(Feature.SKELETON_BAR)) {
            ItemStack item = p.func_71124_b(4);
            if (item != null && SKELETON_HELMET_ID.equals(ItemUtils.getSkyblockItemID(item))) {
                this.wearingSkeletonHelmet = true;
                return;
            }
            this.wearingSkeletonHelmet = false;
        }
    }

    public void checkIfUsingToxicArrowPoison(EntityPlayerSP p) {
        if (this.main.getConfigValues().isEnabled(Feature.TURN_BOW_GREEN_WHEN_USING_TOXIC_ARROW_POISON)) {
            for (ItemStack item : p.field_71071_by.field_70462_a) {
                if (item == null || !TOXIC_ARROW_POISON_ID.equals(ItemUtils.getSkyblockItemID(item))) continue;
                this.usingToxicArrowPoison = true;
                return;
            }
            this.usingToxicArrowPoison = false;
        }
    }

    public int getSlotDifference(Container container) {
        if (container instanceof ContainerChest) {
            return 9 - ((ContainerChest)container).func_85151_d().func_70302_i_();
        }
        if (container instanceof ContainerHopper) {
            return 4;
        }
        if (container instanceof ContainerFurnace) {
            return 6;
        }
        if (container instanceof ContainerBeacon) {
            return 8;
        }
        return 0;
    }

    public void checkIfWearingSlayerArmor(EntityPlayerSP p) {
        if (this.main.getConfigValues().isEnabled(Feature.SLAYER_INDICATOR)) {
            for (int i = 3; i >= 0; --i) {
                String itemID;
                ItemStack itemStack = p.field_71071_by.field_70460_b[i];
                String string = itemID = itemStack != null ? ItemUtils.getSkyblockItemID(itemStack) : null;
                if (itemID != null && (itemID.startsWith("REVENANT") || itemID.startsWith("TARANTULA") || itemID.startsWith("FINAL_DESTINATION") || itemID.startsWith("REAPER"))) {
                    String percent = null;
                    String defence = null;
                    List<String> lore = ItemUtils.getItemLore(itemStack);
                    for (String loreLine : lore) {
                        Matcher matcher = REVENANT_UPGRADE_PATTERN.matcher(TextUtils.stripColor(loreLine));
                        if (!matcher.matches()) continue;
                        try {
                            float percentage = Float.parseFloat(matcher.group(2).replace(",", "")) / (float)Integer.parseInt(matcher.group(3).replace(",", "")) * 100.0f;
                            BigDecimal bigDecimal = new BigDecimal(percentage).setScale(0, 4);
                            percent = bigDecimal.toString();
                            defence = (Object)((Object)ColorCode.GREEN) + matcher.group(1);
                            break;
                        }
                        catch (NumberFormatException numberFormatException) {
                        }
                    }
                    if (percent == null || defence == null) continue;
                    SlayerArmorProgress currentProgress = this.slayerArmorProgresses[i];
                    if (currentProgress == null || itemStack != currentProgress.getItemStack()) {
                        this.slayerArmorProgresses[i] = new SlayerArmorProgress(itemStack, percent, defence);
                        continue;
                    }
                    currentProgress.setPercent(percent);
                    currentProgress.setDefence(defence);
                    continue;
                }
                this.slayerArmorProgresses[i] = null;
            }
        }
    }

    public static boolean isWearingFullSet(EntityPlayer player, Set<String> armorSetIds) {
        boolean flag = true;
        ItemStack[] armorInventory = player.field_71071_by.field_70460_b;
        for (int i = 0; i < 4; ++i) {
            String itemID = ItemUtils.getSkyblockItemID(armorInventory[i]);
            if (itemID != null && armorSetIds.contains(itemID)) continue;
            flag = false;
            break;
        }
        return flag;
    }

    public Collection<ItemDiff> getItemPickupLog() {
        return this.itemPickupLog.values();
    }

    public InventoryType updateInventoryType() {
        GuiScreen currentScreen = Minecraft.func_71410_x().field_71462_r;
        if (!(currentScreen instanceof GuiChest)) {
            this.inventoryType = null;
            return null;
        }
        IInventory inventory = ((GuiChest)currentScreen).field_147015_w;
        if (inventory.func_145748_c_() == null) {
            this.inventoryType = null;
            return null;
        }
        String chestName = TextUtils.stripColor(inventory.func_145748_c_().func_150260_c());
        this.inventoryType = null;
        for (InventoryType inventoryTypeItr : InventoryType.values()) {
            Matcher m = inventoryTypeItr.getInventoryPattern().matcher(chestName);
            if (!m.matches()) continue;
            if (m.groupCount() > 0) {
                try {
                    this.inventoryPageNum = Integer.parseInt(m.group("page"));
                }
                catch (Exception e) {
                    this.inventoryPageNum = 0;
                }
                try {
                    this.inventorySubtype = m.group("type");
                }
                catch (Exception e) {
                    this.inventorySubtype = null;
                }
            } else {
                this.inventoryPageNum = 0;
                this.inventorySubtype = null;
            }
            if (inventoryTypeItr == InventoryType.BASIC_REFORGING || inventoryTypeItr == InventoryType.BASIC_ACCESSORY_BAG_REFORGING) {
                this.inventoryType = this.getReforgeInventoryType(inventoryTypeItr, inventory);
                break;
            }
            this.inventoryType = inventoryTypeItr;
            break;
        }
        this.inventoryKey = this.getInventoryKey(this.inventoryType, this.inventoryPageNum);
        return this.inventoryType;
    }

    private String getInventoryKey(InventoryType inventoryType, int inventoryPageNum) {
        if (inventoryType == null) {
            return null;
        }
        return inventoryType.getInventoryName() + inventoryPageNum;
    }

    private InventoryType getReforgeInventoryType(InventoryType baseType, IInventory inventory) {
        ItemStack barrier = inventory.func_70301_a(13);
        ItemStack glassPane = inventory.func_70301_a(14);
        if (barrier != null && barrier.func_77973_b() == Item.func_150898_a((Block)Blocks.field_180401_cv) || glassPane != null && glassPane.func_82837_s() && TextUtils.stripColor(glassPane.func_82833_r()).equals("Reforge Stone")) {
            return baseType == InventoryType.BASIC_REFORGING ? InventoryType.ADVANCED_REFORGING : InventoryType.ADVANCED_ACCESSORY_BAG_REFORGING;
        }
        return baseType == InventoryType.BASIC_REFORGING ? InventoryType.BASIC_REFORGING : InventoryType.BASIC_ACCESSORY_BAG_REFORGING;
    }

    public void setInventoryWarningShown(boolean inventoryWarningShown) {
        this.inventoryWarningShown = inventoryWarningShown;
    }

    public boolean isWearingSkeletonHelmet() {
        return this.wearingSkeletonHelmet;
    }

    public boolean isUsingToxicArrowPoison() {
        return this.usingToxicArrowPoison;
    }

    public SlayerArmorProgress[] getSlayerArmorProgresses() {
        return this.slayerArmorProgresses;
    }

    public InventoryType getInventoryType() {
        return this.inventoryType;
    }

    public String getInventoryKey() {
        return this.inventoryKey;
    }

    public int getInventoryPageNum() {
        return this.inventoryPageNum;
    }

    public String getInventorySubtype() {
        return this.inventorySubtype;
    }
}

