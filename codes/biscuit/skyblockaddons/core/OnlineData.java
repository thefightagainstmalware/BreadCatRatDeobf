/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core;

import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.core.UpdateInfo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class OnlineData {
    private String bannerImageURL;
    private String bannerLink;
    private UpdateInfo updateInfo;
    private String languageJSONFormat;
    private HashMap<String, List<Integer>> disabledFeatures;
    private DropSettings dropSettings;
    private HashSet<Pattern> hypixelBrands;

    public String getBannerImageURL() {
        return this.bannerImageURL;
    }

    public String getBannerLink() {
        return this.bannerLink;
    }

    public UpdateInfo getUpdateInfo() {
        return this.updateInfo;
    }

    public String getLanguageJSONFormat() {
        return this.languageJSONFormat;
    }

    public HashMap<String, List<Integer>> getDisabledFeatures() {
        return this.disabledFeatures;
    }

    public DropSettings getDropSettings() {
        return this.dropSettings;
    }

    public HashSet<Pattern> getHypixelBrands() {
        return this.hypixelBrands;
    }

    public static class DropSettings {
        private ItemRarity minimumInventoryRarity;
        private ItemRarity minimumHotbarRarity;
        private List<String> dontDropTheseItems;
        private List<String> allowDroppingTheseItems;

        public ItemRarity getMinimumInventoryRarity() {
            return this.minimumInventoryRarity;
        }

        public ItemRarity getMinimumHotbarRarity() {
            return this.minimumHotbarRarity;
        }

        public List<String> getDontDropTheseItems() {
            return this.dontDropTheseItems;
        }

        public List<String> getAllowDroppingTheseItems() {
            return this.allowDroppingTheseItems;
        }
    }
}

