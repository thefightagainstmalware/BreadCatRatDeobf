/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core;

import codes.biscuit.skyblockaddons.utils.ColorCode;

public enum ItemRarity {
    COMMON("COMMON", ColorCode.WHITE),
    UNCOMMON("UNCOMMON", ColorCode.GREEN),
    RARE("RARE", ColorCode.BLUE),
    EPIC("EPIC", ColorCode.DARK_PURPLE),
    LEGENDARY("LEGENDARY", ColorCode.GOLD),
    MYTHIC("MYTHIC", ColorCode.LIGHT_PURPLE),
    SUPREME("SUPREME", ColorCode.DARK_RED),
    SPECIAL("SPECIAL", ColorCode.RED),
    VERY_SPECIAL("VERY SPECIAL", ColorCode.RED);

    private String loreName;
    private ColorCode colorCode;

    private ItemRarity(String loreName, ColorCode colorCode) {
        this.loreName = loreName;
        this.colorCode = colorCode;
    }

    public String getLoreName() {
        return this.loreName;
    }

    public ColorCode getColorCode() {
        return this.colorCode;
    }
}

