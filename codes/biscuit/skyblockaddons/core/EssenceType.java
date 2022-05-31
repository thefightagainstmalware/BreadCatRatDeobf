/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  org.apache.commons.lang3.text.WordUtils
 */
package codes.biscuit.skyblockaddons.core;

import java.util.Locale;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;

public enum EssenceType {
    WITHER,
    SPIDER,
    UNDEAD,
    DRAGON,
    GOLD,
    DIAMOND,
    ICE;

    private String niceName = WordUtils.capitalizeFully((String)this.name());
    private ResourceLocation resourceLocation = new ResourceLocation("skyblockaddons", "essences/" + this.name().toLowerCase(Locale.US) + ".png");

    public static EssenceType fromName(String name) {
        for (EssenceType essenceType : EssenceType.values()) {
            if (!essenceType.niceName.equals(name)) continue;
            return essenceType;
        }
        return null;
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
}

