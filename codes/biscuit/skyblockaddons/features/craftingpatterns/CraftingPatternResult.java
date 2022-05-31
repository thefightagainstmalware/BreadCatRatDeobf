/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.features.craftingpatterns;

import codes.biscuit.skyblockaddons.features.ItemDiff;
import java.util.Map;
import net.minecraft.item.ItemStack;

public class CraftingPatternResult {
    private final boolean filled;
    private final boolean satisfied;
    private final int emptySpace;
    private final Map<String, ItemDiff> freeSpaceMap;

    CraftingPatternResult(boolean filled, boolean satisfied, int emptySpace, Map<String, ItemDiff> freeSpaceMap) {
        this.filled = filled;
        this.satisfied = satisfied;
        this.emptySpace = emptySpace;
        this.freeSpaceMap = freeSpaceMap;
    }

    public boolean isFilled() {
        return this.filled;
    }

    public boolean isSatisfied() {
        return this.satisfied;
    }

    public boolean fitsItem(ItemStack itemStack) {
        ItemDiff itemDiff = this.freeSpaceMap.getOrDefault(itemStack.func_82833_r(), null);
        if (itemDiff != null) {
            return itemStack.field_77994_a <= itemDiff.getAmount();
        }
        return itemStack.field_77994_a <= this.emptySpace;
    }
}

