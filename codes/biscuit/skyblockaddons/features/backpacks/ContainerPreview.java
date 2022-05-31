/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.features.backpacks;

import codes.biscuit.skyblockaddons.features.backpacks.BackpackColor;
import java.util.List;
import net.minecraft.item.ItemStack;

public class ContainerPreview {
    private int x;
    private int y;
    private final int numRows;
    private final int numCols;
    private final List<ItemStack> items;
    private final String name;
    private final BackpackColor backpackColor;

    public ContainerPreview(List<ItemStack> items, String name, BackpackColor backpackColor, int rows, int cols) {
        this.items = items;
        this.name = name;
        this.backpackColor = backpackColor;
        this.numRows = Math.min(rows, 6);
        this.numCols = Math.min(cols, 9);
    }

    public ContainerPreview(List<ItemStack> items, String backpackName, BackpackColor backpackColor, int rows, int cols, int x, int y) {
        this(items, backpackName, backpackColor, rows, cols);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public String getName() {
        return this.name;
    }

    public BackpackColor getBackpackColor() {
        return this.backpackColor;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

