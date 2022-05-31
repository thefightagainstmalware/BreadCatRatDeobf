/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core;

import java.util.regex.Pattern;

public enum InventoryType {
    ENCHANTMENT_TABLE("Enchant Item", "Enchant Item"),
    BASIC_REFORGING("Reforge Item", "Reforge Item"),
    ADVANCED_REFORGING("Reforge Item", "Reforge Item"),
    BASIC_ACCESSORY_BAG_REFORGING("Reforge Accessory Bag", "Reforge Accessory Bag"),
    ADVANCED_ACCESSORY_BAG_REFORGING("Reforge Accessory Bag", "Reforge Accessory Bag"),
    BAKER("Baker", "Baker"),
    CRAFTING_TABLE("Craft Item", "Craft Item"),
    SALVAGING("Salvage Dungeon Item", "Salvage Dungeon Item"),
    ULTRASEQUENCER("Ultrasequencer", "Ultrasequencer \\((?<type>[a-zA-Z]+)\\)"),
    CHRONOMATRON("Chronomatron", "Chronomatron \\((?<type>[a-zA-Z]+)\\)"),
    SUPERPAIRS("Superpairs", "Superpairs \\((?<type>[a-zA-Z]+)\\)"),
    STORAGE("Storage", "Storage"),
    STORAGE_BACKPACK("BackpackStorage", "(?<type>[a-zA-Z]+) Backpack \\((?<page>[0-9]+)/[0-9]+\\)"),
    SKILL_TYPE_MENU("Skill Type Menu", "(?<type>[a-zA-Z]+) Skill"),
    ENDER_CHEST("EnderChest", "Ender Chest \\((?<page>[0-9]+)/[0-9]+\\)");

    private final String inventoryName;
    private final Pattern inventoryPattern;

    private InventoryType(String inventoryName, String regex) {
        this.inventoryName = inventoryName;
        this.inventoryPattern = Pattern.compile(regex);
    }

    public String getInventoryName() {
        return this.inventoryName;
    }

    public Pattern getInventoryPattern() {
        return this.inventoryPattern;
    }
}

