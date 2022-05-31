/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.enchantedItemBlacklist;

import codes.biscuit.skyblockaddons.core.ItemRarity;
import java.util.List;

public class EnchantedItemLists {
    List<String> blacklistedIDs;
    List<String> whitelistedIDs;
    ItemRarity rarityLimit;

    public EnchantedItemLists(List<String> blacklistedIDs, List<String> whitelistedIDs, ItemRarity rarityLimit) {
        this.blacklistedIDs = blacklistedIDs;
        this.whitelistedIDs = whitelistedIDs;
        this.rarityLimit = rarityLimit;
    }

    public List<String> getBlacklistedIDs() {
        return this.blacklistedIDs;
    }

    public List<String> getWhitelistedIDs() {
        return this.whitelistedIDs;
    }

    public ItemRarity getRarityLimit() {
        return this.rarityLimit;
    }

    public void setBlacklistedIDs(List<String> blacklistedIDs) {
        this.blacklistedIDs = blacklistedIDs;
    }

    public void setWhitelistedIDs(List<String> whitelistedIDs) {
        this.whitelistedIDs = whitelistedIDs;
    }

    public void setRarityLimit(ItemRarity rarityLimit) {
        this.rarityLimit = rarityLimit;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EnchantedItemLists)) {
            return false;
        }
        EnchantedItemLists other = (EnchantedItemLists)o;
        if (!other.canEqual(this)) {
            return false;
        }
        List<String> this$blacklistedIDs = this.getBlacklistedIDs();
        List<String> other$blacklistedIDs = other.getBlacklistedIDs();
        if (this$blacklistedIDs == null ? other$blacklistedIDs != null : !((Object)this$blacklistedIDs).equals(other$blacklistedIDs)) {
            return false;
        }
        List<String> this$whitelistedIDs = this.getWhitelistedIDs();
        List<String> other$whitelistedIDs = other.getWhitelistedIDs();
        if (this$whitelistedIDs == null ? other$whitelistedIDs != null : !((Object)this$whitelistedIDs).equals(other$whitelistedIDs)) {
            return false;
        }
        ItemRarity this$rarityLimit = this.getRarityLimit();
        ItemRarity other$rarityLimit = other.getRarityLimit();
        return !(this$rarityLimit == null ? other$rarityLimit != null : !((Object)((Object)this$rarityLimit)).equals((Object)other$rarityLimit));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EnchantedItemLists;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<String> $blacklistedIDs = this.getBlacklistedIDs();
        result = result * 59 + ($blacklistedIDs == null ? 43 : ((Object)$blacklistedIDs).hashCode());
        List<String> $whitelistedIDs = this.getWhitelistedIDs();
        result = result * 59 + ($whitelistedIDs == null ? 43 : ((Object)$whitelistedIDs).hashCode());
        ItemRarity $rarityLimit = this.getRarityLimit();
        result = result * 59 + ($rarityLimit == null ? 43 : ((Object)((Object)$rarityLimit)).hashCode());
        return result;
    }

    public String toString() {
        return "EnchantedItemLists(blacklistedIDs=" + this.getBlacklistedIDs() + ", whitelistedIDs=" + this.getWhitelistedIDs() + ", rarityLimit=" + (Object)((Object)this.getRarityLimit()) + ")";
    }
}

