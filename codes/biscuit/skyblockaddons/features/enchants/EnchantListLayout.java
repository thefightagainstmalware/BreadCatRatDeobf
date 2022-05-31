/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.enchants;

import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSelect;

public enum EnchantListLayout implements ButtonSelect.SelectItem
{
    NORMAL("enchantLayout.titleNormal", "enchantLayout.descriptionNormal"),
    COMPRESS("enchantLayout.titleCompress", "enchantLayout.descriptionCompress"),
    EXPAND("enchantLayout.titleExpand", "enchantLayout.descriptionExpand");

    private final String title;
    private final String description;

    private EnchantListLayout(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String getName() {
        return Translations.getMessage(this.title, new Object[0]);
    }

    @Override
    public String getDescription() {
        return Translations.getMessage(this.description, new Object[0]);
    }
}

