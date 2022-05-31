/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.itemdrops;

import codes.biscuit.skyblockaddons.core.ItemRarity;
import java.util.List;

class ItemDropList {
    private ItemRarity minimumInventoryItemRarity;
    private ItemRarity minimumHotbarItemRarity;
    private List<String> dontDropTheseItems;
    private List<String> allowDroppingTheseItems;

    ItemDropList() {
    }

    public ItemRarity getMinimumInventoryItemRarity() {
        return this.minimumInventoryItemRarity;
    }

    public ItemRarity getMinimumHotbarItemRarity() {
        return this.minimumHotbarItemRarity;
    }

    public List<String> getDontDropTheseItems() {
        return this.dontDropTheseItems;
    }

    public List<String> getAllowDroppingTheseItems() {
        return this.allowDroppingTheseItems;
    }
}

