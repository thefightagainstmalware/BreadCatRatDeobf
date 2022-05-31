/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.core.dungeons;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum DungeonClass {
    HEALER((Item)Items.field_151068_bn, "Healer"),
    ARCHER((Item)Items.field_151031_f, "Archer"),
    TANK((Item)Items.field_151027_R, "Tank"),
    MAGE(Items.field_151072_bj, "Mage"),
    BERSERKER(Items.field_151040_l, "Berserk");

    private char firstLetter = this.name().charAt(0);
    private ItemStack item;
    private String chatDisplayName;

    private DungeonClass(Item item, String chatDisplayName) {
        this.item = new ItemStack(item);
        this.chatDisplayName = chatDisplayName;
    }

    public static DungeonClass fromFirstLetter(char firstLetter) {
        for (DungeonClass dungeonClass : DungeonClass.values()) {
            if (dungeonClass.firstLetter != firstLetter) continue;
            return dungeonClass;
        }
        return null;
    }

    public static DungeonClass fromDisplayName(String name) {
        for (DungeonClass dungeonClass : DungeonClass.values()) {
            if (!dungeonClass.getChatDisplayName().equals(name)) continue;
            return dungeonClass;
        }
        return null;
    }

    public char getFirstLetter() {
        return this.firstLetter;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public String getChatDisplayName() {
        return this.chatDisplayName;
    }
}

