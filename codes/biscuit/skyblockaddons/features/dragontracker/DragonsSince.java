/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.CaseFormat
 */
package codes.biscuit.skyblockaddons.features.dragontracker;

import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.core.Translations;
import com.google.common.base.CaseFormat;

public enum DragonsSince {
    SUPERIOR(ItemRarity.LEGENDARY),
    ASPECT_OF_THE_DRAGONS(ItemRarity.LEGENDARY),
    ENDER_DRAGON_PET(ItemRarity.LEGENDARY);

    private ItemRarity itemRarity;

    public String getDisplayName() {
        return Translations.getMessage("dragonTracker." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name()), new Object[0]);
    }

    private DragonsSince(ItemRarity itemRarity) {
        this.itemRarity = itemRarity;
    }

    public ItemRarity getItemRarity() {
        return this.itemRarity;
    }
}

