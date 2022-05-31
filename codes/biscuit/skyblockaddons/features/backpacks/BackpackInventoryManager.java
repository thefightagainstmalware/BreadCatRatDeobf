/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.backpacks;

import codes.biscuit.skyblockaddons.features.backpacks.BackpackColor;

public class BackpackInventoryManager {
    private static BackpackColor backpackColor;

    public static BackpackColor getBackpackColor() {
        return backpackColor;
    }

    public static void setBackpackColor(BackpackColor backpackColor) {
        BackpackInventoryManager.backpackColor = backpackColor;
    }
}

