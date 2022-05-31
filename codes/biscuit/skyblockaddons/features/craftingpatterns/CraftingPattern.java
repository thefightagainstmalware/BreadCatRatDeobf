/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.features.craftingpatterns;

import codes.biscuit.skyblockaddons.features.ItemDiff;
import codes.biscuit.skyblockaddons.features.craftingpatterns.CraftingPatternResult;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public enum CraftingPattern {
    FREE(0, null),
    THREE(1, new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0}),
    FIVE(2, new int[]{1, 1, 1, 1, 1, 0, 0, 0, 0}),
    SIX(3, new int[]{1, 1, 1, 1, 1, 1, 0, 0, 0});

    public static final ResourceLocation ICONS;
    public static final String CRAFTING_TABLE_DISPLAYNAME = "Craft Item";
    public static final int CRAFTING_RESULT_INDEX = 23;
    public static final List<Integer> CRAFTING_GRID_SLOTS;
    public final int index;
    public final int[] pattern;

    private CraftingPattern(int index, int[] pattern) {
        this.index = index;
        this.pattern = pattern;
    }

    public boolean isSlotInPattern(int slot) {
        return slot >= 0 && slot <= 8 && this.pattern[slot] == 1;
    }

    public CraftingPatternResult checkAgainstGrid(ItemStack[] grid) {
        if (grid == null || grid.length < 9) {
            throw new IllegalArgumentException("grid cannot be null or smaller than 9.");
        }
        boolean filled = true;
        boolean satisfied = true;
        int emptySpace = 0;
        HashMap<String, ItemDiff> freeSpaceMap = new HashMap<String, ItemDiff>();
        for (int i = 0; i < this.pattern.length; ++i) {
            boolean hasStack;
            ItemStack itemStack = grid[i];
            boolean bl = hasStack = itemStack != null;
            if (this.isSlotInPattern(i) && !hasStack) {
                filled = false;
                satisfied = false;
            } else if (!this.isSlotInPattern(i) && hasStack) {
                satisfied = false;
            }
            if (!this.isSlotInPattern(i)) continue;
            if (hasStack) {
                if (!freeSpaceMap.containsKey(itemStack.func_82833_r())) {
                    freeSpaceMap.put(itemStack.func_82833_r(), new ItemDiff(itemStack.func_82833_r(), 0));
                }
                ItemDiff diff = (ItemDiff)freeSpaceMap.get(itemStack.func_82833_r());
                diff.add(itemStack.func_77976_d() - itemStack.field_77994_a);
                continue;
            }
            emptySpace += 64;
        }
        return new CraftingPatternResult(filled, satisfied, emptySpace, freeSpaceMap);
    }

    public static int slotToCraftingGridIndex(int slotIndex) {
        return CRAFTING_GRID_SLOTS.indexOf(slotIndex);
    }

    public static int craftingGridIndexToSlot(int index) {
        if (index < 0 || index > 8) {
            throw new IndexOutOfBoundsException("Crafting Grid index must be between 0 and 8");
        }
        return CRAFTING_GRID_SLOTS.get(index);
    }

    static {
        ICONS = new ResourceLocation("skyblockaddons", "craftingpatterns.png");
        CRAFTING_GRID_SLOTS = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    }
}

