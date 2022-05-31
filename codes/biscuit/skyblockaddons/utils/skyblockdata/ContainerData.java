/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.utils.skyblockdata;

import java.util.List;

public class ContainerData {
    private ContainerType type;
    private int size;
    private String compressedItemStacksTag;
    private List<String> itemStackDataTags;
    private String colorTag;
    private int[] dimensions = new int[]{6, 9};

    public boolean isBackpack() {
        return this.type == ContainerType.BACKPACK;
    }

    public boolean isCakeBag() {
        return this.type == ContainerType.NEW_YEARS_CAKE;
    }

    public boolean isPersonalCompactor() {
        return this.type == ContainerType.PERSONAL_COMPACTOR;
    }

    public boolean isBuildersWand() {
        return this.type == ContainerType.BUILDERS_WAND;
    }

    public int getSize() {
        return Math.min(this.size, 54);
    }

    public int getNumRows() {
        return this.dimensions.length == 2 ? Math.min(this.dimensions[0], 6) : 6;
    }

    public int getNumCols() {
        return this.dimensions.length == 2 ? Math.min(this.dimensions[1], 9) : 9;
    }

    public String getCompressedDataTag() {
        return this.compressedItemStacksTag;
    }

    public List<String> getItemStackDataTags() {
        return this.itemStackDataTags;
    }

    public String getColorTag() {
        return this.colorTag;
    }

    private static enum ContainerType {
        BACKPACK,
        NEW_YEARS_CAKE,
        PERSONAL_COMPACTOR,
        BUILDERS_WAND;

    }
}

