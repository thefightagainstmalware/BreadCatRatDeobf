/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.features.cooldowns;

import codes.biscuit.skyblockaddons.features.cooldowns.CooldownEntry;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.item.ItemStack;

public class CooldownManager {
    private static Map<String, Integer> itemCooldowns = new HashMap<String, Integer>();
    private static final Pattern ITEM_COOLDOWN_PATTERN = Pattern.compile("Cooldown: ([0-9]+)s");
    private static final Pattern ALTERNATE_COOLDOWN_PATTERN = Pattern.compile("([0-9]+) Second Cooldown");
    private static final Map<String, CooldownEntry> cooldowns = new HashMap<String, CooldownEntry>();

    private static CooldownEntry get(ItemStack item) {
        return CooldownManager.get(ItemUtils.getSkyblockItemID(item));
    }

    private static CooldownEntry get(String itemId) {
        return cooldowns.getOrDefault(itemId, CooldownEntry.NULL_ENTRY);
    }

    public static int getItemCooldown(ItemStack item) {
        return itemCooldowns.getOrDefault(ItemUtils.getSkyblockItemID(item), 0);
    }

    public static int getItemCooldown(String itemId) {
        return itemCooldowns.getOrDefault(itemId, 0);
    }

    public static void put(ItemStack item) {
        String itemId = ItemUtils.getSkyblockItemID(item);
        if (itemId == null) {
            return;
        }
        int cooldown = itemCooldowns.getOrDefault(itemId, 0);
        if (cooldown > 0) {
            CooldownManager.put(itemId, (long)cooldown);
        }
    }

    public static void put(String itemId) {
        if (itemId == null) {
            return;
        }
        int cooldown = itemCooldowns.getOrDefault(itemId, 0);
        if (cooldown > 0) {
            CooldownManager.put(itemId, (long)cooldown);
        }
    }

    public static void put(ItemStack item, long cooldown) {
        String itemId = ItemUtils.getSkyblockItemID(item);
        if (itemId != null && cooldown > 0L) {
            CooldownManager.put(itemId, cooldown);
        }
    }

    public static void put(String itemId, long cooldown) {
        if (cooldown < 0L) {
            throw new IllegalArgumentException("Cooldown must be positive and not 0");
        }
        if (!cooldowns.containsKey(itemId) || !cooldowns.get(itemId).isOnCooldown()) {
            CooldownEntry cooldownEntry = new CooldownEntry(cooldown);
            cooldowns.put(itemId, cooldownEntry);
        }
    }

    public static void remove(String itemId) {
        cooldowns.put(itemId, CooldownEntry.NULL_ENTRY);
    }

    public static boolean isOnCooldown(ItemStack item) {
        return CooldownManager.get(item).isOnCooldown();
    }

    public static boolean isOnCooldown(String itemId) {
        return CooldownManager.get(itemId).isOnCooldown();
    }

    public static long getRemainingCooldown(ItemStack item) {
        return CooldownManager.get(item).getRemainingCooldown();
    }

    public static long getRemainingCooldown(String itemId) {
        return CooldownManager.get(itemId).getRemainingCooldown();
    }

    public static double getRemainingCooldownPercent(ItemStack item) {
        return CooldownManager.get(item).getRemainingCooldownPercent();
    }

    public static double getRemainingCooldownPercent(String itemId) {
        return CooldownManager.get(itemId).getRemainingCooldownPercent();
    }

    private static int getLoreCooldown(ItemStack itemStack) {
        for (String loreLine : ItemUtils.getItemLore(itemStack)) {
            String strippedLoreLine = TextUtils.stripColor(loreLine);
            Matcher matcher = ITEM_COOLDOWN_PATTERN.matcher(strippedLoreLine);
            if (matcher.matches()) {
                try {
                    return Integer.parseInt(matcher.group(1));
                }
                catch (NumberFormatException numberFormatException) {
                    continue;
                }
            }
            matcher = ALTERNATE_COOLDOWN_PATTERN.matcher(strippedLoreLine);
            if (!matcher.matches()) continue;
            try {
                return Integer.parseInt(matcher.group(1));
            }
            catch (NumberFormatException numberFormatException) {
            }
        }
        return -1;
    }

    public static void setItemCooldowns(Map<String, Integer> itemCooldowns) {
        CooldownManager.itemCooldowns = itemCooldowns;
    }
}

