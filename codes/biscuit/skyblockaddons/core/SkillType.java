/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum SkillType {
    FARMING("Farming", Items.field_151013_M, false),
    MINING("Mining", Items.field_151046_w, false),
    COMBAT("Combat", Items.field_151040_l, false),
    FORAGING("Foraging", Item.func_150898_a((Block)Blocks.field_150345_g), false),
    FISHING("Fishing", (Item)Items.field_151112_aM, false),
    ENCHANTING("Enchanting", Item.func_150898_a((Block)Blocks.field_150381_bn), false),
    ALCHEMY("Alchemy", Items.field_151067_bt, false),
    CARPENTRY("Carpentry", Item.func_150898_a((Block)Blocks.field_150462_ai), true),
    RUNECRAFTING("Runecrafting", Items.field_151064_bs, true),
    TAMING("Taming", Items.field_151063_bx, false),
    DUNGEONEERING("Dungeoneering", Item.func_150898_a((Block)Blocks.field_150330_I), false),
    SOCIAL("Social", Items.field_151105_aU, true);

    private final String skillName;
    private final ItemStack item;
    private final boolean cosmetic;

    private SkillType(String skillName, Item item, boolean isCosmetic) {
        this.skillName = skillName;
        this.item = new ItemStack(item);
        this.cosmetic = isCosmetic;
    }

    public static SkillType getFromString(String text) {
        for (SkillType skillType : SkillType.values()) {
            if (skillType.skillName == null || !skillType.skillName.equals(text)) continue;
            return skillType;
        }
        return null;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public boolean isCosmetic() {
        return this.cosmetic;
    }
}

