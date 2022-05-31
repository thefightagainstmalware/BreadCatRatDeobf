/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.dragontracker;

import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import java.util.Locale;

public enum DragonType {
    PROTECTOR(ColorCode.DARK_BLUE),
    OLD(ColorCode.GRAY),
    WISE(ColorCode.BLUE),
    UNSTABLE(ColorCode.BLACK),
    YOUNG(ColorCode.WHITE),
    STRONG(ColorCode.RED),
    SUPERIOR(ColorCode.GOLD);

    private ColorCode color;

    public String getDisplayName() {
        return Translations.getMessage("dragonTracker." + this.name().toLowerCase(Locale.US), new Object[0]);
    }

    public static DragonType fromName(String name) {
        for (DragonType dragonType : DragonType.values()) {
            if (!dragonType.name().equals(name.toUpperCase(Locale.US))) continue;
            return dragonType;
        }
        return null;
    }

    private DragonType(ColorCode color) {
        this.color = color;
    }

    public ColorCode getColor() {
        return this.color;
    }
}

