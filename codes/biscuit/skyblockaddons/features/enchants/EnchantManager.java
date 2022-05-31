/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  org.lwjgl.input.Mouse
 */
package codes.biscuit.skyblockaddons.features.enchants;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.InventoryType;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.features.enchants.EnchantListLayout;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.RomanNumeralParser;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Mouse;

public class EnchantManager {
    private static final Pattern ENCHANTMENT_PATTERN = Pattern.compile("(?<enchant>[A-Za-z][A-Za-z -]+) (?<levelNumeral>[IVXLCDM]+)(?=, |$| [\\d,]+$)");
    private static final Pattern GREY_ENCHANT_PATTERN = Pattern.compile("^(Respiration|Aqua Affinity|Depth Strider|Efficiency).*");
    private static final String COMMA = ", ";
    private static Enchants enchants = new Enchants();
    private static final Cache loreCache = new Cache();

    /*
     * WARNING - void declaration
     */
    public static void parseEnchants(List<String> loreList, ItemStack item) {
        ArrayList<String> insertEnchants;
        int i;
        NBTTagCompound enchantNBT;
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(item);
        NBTTagCompound nBTTagCompound = enchantNBT = extraAttributes == null ? null : extraAttributes.func_74775_l("enchantments");
        if (enchantNBT == null && SkyblockAddons.getInstance().getInventoryUtils().getInventoryType() != InventoryType.SUPERPAIRS) {
            return;
        }
        if (loreCache.isCached(loreList)) {
            loreList.clear();
            loreList.addAll(loreCache.getCachedAfter());
            return;
        }
        loreCache.updateBefore(loreList);
        ConfigValues config = SkyblockAddons.getInstance().getConfigValues();
        FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
        int startEnchant = -1;
        int endEnchant = -1;
        int maxTooltipWidth = 0;
        int indexOfLastGreyEnchant = EnchantManager.accountForAndRemoveGreyEnchants(loreList, item);
        int n = i = indexOfLastGreyEnchant == -1 ? 0 : indexOfLastGreyEnchant + 1;
        while (i < loreList.size()) {
            String u = loreList.get(i);
            String s = TextUtils.stripColor(u);
            if (startEnchant == -1) {
                if (EnchantManager.containsEnchantment(enchantNBT, s)) {
                    startEnchant = i;
                }
            } else if (s.trim().length() == 0 && endEnchant == -1) {
                endEnchant = i - 1;
            }
            if (startEnchant == -1 || endEnchant != -1) {
                maxTooltipWidth = Math.max(fontRenderer.func_78256_a(loreList.get(i)), maxTooltipWidth);
            }
            ++i;
        }
        if (enchantNBT == null && endEnchant == -1) {
            endEnchant = startEnchant;
        }
        if (endEnchant == -1) {
            loreCache.updateAfter(loreList);
            return;
        }
        maxTooltipWidth = EnchantManager.correctTooltipWidth(maxTooltipWidth);
        boolean hasLore = false;
        TreeSet<FormattedEnchant> orderedEnchants = new TreeSet<FormattedEnchant>();
        FormattedEnchant lastEnchant = null;
        for (int i2 = startEnchant; i2 <= endEnchant; ++i2) {
            String unformattedLine = TextUtils.stripColor(loreList.get(i2));
            Matcher m = ENCHANTMENT_PATTERN.matcher(unformattedLine);
            boolean containsEnchant = false;
            while (m.find()) {
                Enchant enchant = enchants.getFromLore(m.group("enchant"));
                int level = RomanNumeralParser.parseNumeral(m.group("levelNumeral"));
                if (enchant == null) continue;
                String inputFormatEnchant = "null";
                if (config.isDisabled(Feature.ENCHANTMENTS_HIGHLIGHT)) {
                    inputFormatEnchant = EnchantManager.getInputEnchantFormat(loreList.get(i2), m.group());
                }
                if (!orderedEnchants.add(lastEnchant = new FormattedEnchant(enchant, level, inputFormatEnchant))) {
                    for (FormattedEnchant e : orderedEnchants) {
                        if (e.compareTo(lastEnchant) != 0) continue;
                        lastEnchant = e;
                        break;
                    }
                }
                containsEnchant = true;
            }
            if (containsEnchant || lastEnchant == null) continue;
            lastEnchant.addLore(loreList.get(i2));
            hasLore = true;
        }
        int numEnchants = orderedEnchants.size();
        for (FormattedEnchant enchant : orderedEnchants) {
            maxTooltipWidth = Math.max(enchant.getRenderLength(), maxTooltipWidth);
        }
        if (orderedEnchants.size() == 0) {
            loreCache.updateAfter(loreList);
            return;
        }
        loreList.subList(startEnchant, endEnchant + 1).clear();
        EnchantListLayout layout = config.getEnchantLayout();
        if (layout == EnchantListLayout.COMPRESS && numEnchants != 1) {
            insertEnchants = new ArrayList<String>();
            String comma = (Object)((Object)SkyblockAddons.getInstance().getConfigValues().getRestrictedColor(Feature.ENCHANTMENT_COMMA_COLOR)) + COMMA;
            int n2 = fontRenderer.func_78256_a(comma);
            int sum = 0;
            StringBuilder builder = new StringBuilder(maxTooltipWidth);
            for (FormattedEnchant enchant : orderedEnchants) {
                if (sum + enchant.getRenderLength() > maxTooltipWidth) {
                    builder.delete(builder.length() - comma.length(), builder.length());
                    insertEnchants.add(builder.toString());
                    builder = new StringBuilder(maxTooltipWidth);
                    sum = 0;
                }
                builder.append(enchant.getFormattedString()).append(comma);
                sum += enchant.getRenderLength() + n2;
            }
            if (builder.length() >= comma.length()) {
                builder.delete(builder.length() - comma.length(), builder.length());
                insertEnchants.add(builder.toString());
            }
        } else if (layout == EnchantListLayout.NORMAL && !hasLore) {
            insertEnchants = new ArrayList();
            String comma = (Object)((Object)SkyblockAddons.getInstance().getConfigValues().getRestrictedColor(Feature.ENCHANTMENT_COMMA_COLOR)) + COMMA;
            boolean bl = false;
            StringBuilder builder = new StringBuilder(maxTooltipWidth);
            for (FormattedEnchant enchant : orderedEnchants) {
                void var17_24;
                builder.append(enchant.getFormattedString());
                if (var17_24 % 2 == false) {
                    builder.append(comma);
                } else {
                    insertEnchants.add(builder.toString());
                    builder = new StringBuilder(maxTooltipWidth);
                }
                ++var17_24;
            }
            if (builder.length() >= comma.length()) {
                builder.delete(builder.length() - comma.length(), builder.length());
                insertEnchants.add(builder.toString());
            }
        } else if (config.isDisabled(Feature.HIDE_ENCHANT_DESCRIPTION)) {
            insertEnchants = new ArrayList((hasLore ? 3 : 1) * numEnchants);
            for (FormattedEnchant formattedEnchant : orderedEnchants) {
                insertEnchants.add(formattedEnchant.getFormattedString());
                insertEnchants.addAll(formattedEnchant.getLore());
            }
        } else {
            insertEnchants = new ArrayList(numEnchants);
            for (FormattedEnchant formattedEnchant : orderedEnchants) {
                insertEnchants.add(formattedEnchant.getFormattedString());
            }
        }
        loreList.addAll(startEnchant, insertEnchants);
        loreCache.updateAfter(loreList);
    }

    public static int insertStackingEnchantProgress(List<String> loreList, NBTTagCompound extraAttributes, int insertAt) {
        if (extraAttributes == null || SkyblockAddons.getInstance().getConfigValues().isDisabled(Feature.SHOW_STACKING_ENCHANT_PROGRESS)) {
            return insertAt;
        }
        for (Enchant.Stacking enchant : EnchantManager.enchants.STACKING.values()) {
            if (!extraAttributes.func_150297_b(enchant.nbtNum, 99)) continue;
            int stackedEnchantNum = extraAttributes.func_74762_e(enchant.nbtNum);
            Integer nextLevel = enchant.stackLevel.higher(stackedEnchantNum);
            String statLabel = Translations.getMessage("enchants." + enchant.statLabel, new Object[0]);
            ColorCode colorCode = SkyblockAddons.getInstance().getConfigValues().getRestrictedColor(Feature.SHOW_STACKING_ENCHANT_PROGRESS);
            StringBuilder b = new StringBuilder();
            b.append("\u00a77").append(statLabel).append(": ").append((Object)colorCode);
            if (nextLevel == null) {
                b.append(TextUtils.abbreviate(stackedEnchantNum)).append(" \u00a77(").append(Translations.getMessage("enchants.maxed", new Object[0])).append(")");
            } else {
                b.append(stackedEnchantNum).append(" \u00a77/ ").append(TextUtils.abbreviate(nextLevel));
            }
            loreList.add(insertAt++, b.toString());
        }
        return insertAt;
    }

    public static boolean containsEnchantment(NBTTagCompound enchantNBT, String s) {
        Matcher m = ENCHANTMENT_PATTERN.matcher(s);
        while (m.find()) {
            Enchant enchant = enchants.getFromLore(m.group("enchant"));
            if (enchantNBT != null && !enchantNBT.func_74764_b(enchant.nbtName)) continue;
            return true;
        }
        return false;
    }

    private static String getInputEnchantFormat(String formattedEnchants, String unformattedEnchant) {
        if (unformattedEnchant.length() == 0) {
            return "";
        }
        String styles = "kKlLmMnNoO";
        StringBuilder preEnchantFormat = new StringBuilder();
        StringBuilder formattedEnchant = new StringBuilder();
        int i = -2;
        int len = formattedEnchants.length();
        int unformattedEnchantIdx = 0;
        int k = 0;
        while (true) {
            if ((i = formattedEnchants.indexOf(167, i + 2)) == -1) {
                while (k < len) {
                    if (formattedEnchants.charAt(k) == unformattedEnchant.charAt(unformattedEnchantIdx)) {
                        formattedEnchant.append(formattedEnchants.charAt(k));
                        if (++unformattedEnchantIdx == unformattedEnchant.length()) {
                            return preEnchantFormat.append((CharSequence)formattedEnchant).toString();
                        }
                    } else {
                        unformattedEnchantIdx = 0;
                        preEnchantFormat = new StringBuilder(EnchantManager.mergeFormats(preEnchantFormat.toString(), formattedEnchant.toString()));
                        formattedEnchant = new StringBuilder();
                    }
                    ++k;
                }
                return null;
            }
            while (k < i) {
                if (formattedEnchants.charAt(k) == unformattedEnchant.charAt(unformattedEnchantIdx)) {
                    formattedEnchant.append(formattedEnchants.charAt(k));
                    if (++unformattedEnchantIdx == unformattedEnchant.length()) {
                        return preEnchantFormat.append((CharSequence)formattedEnchant).toString();
                    }
                } else {
                    unformattedEnchantIdx = 0;
                    preEnchantFormat = new StringBuilder(EnchantManager.mergeFormats(preEnchantFormat.toString(), formattedEnchant.toString()));
                    formattedEnchant = new StringBuilder();
                }
                ++k;
            }
            if (i + 1 >= len) continue;
            char formatChar = formattedEnchants.charAt(i + 1);
            if (unformattedEnchantIdx == 0) {
                if (styles.indexOf(formatChar) == -1) {
                    preEnchantFormat = new StringBuilder();
                }
                preEnchantFormat.append("\u00a7").append(formatChar);
            } else {
                formattedEnchant.append("\u00a7").append(formatChar);
            }
            k = i + 2;
        }
    }

    private static String mergeFormats(String firstFormat, String secondFormat) {
        if (secondFormat == null || secondFormat.length() == 0) {
            return firstFormat;
        }
        String styles = "kKlLmMnNoO";
        StringBuilder builder = new StringBuilder(firstFormat);
        int i = -2;
        while ((i = secondFormat.indexOf(167, i + 2)) != -1) {
            if (i + 1 >= secondFormat.length()) continue;
            char c = secondFormat.charAt(i + 1);
            if (styles.indexOf(c) == -1) {
                builder = new StringBuilder();
            }
            builder.append("\u00a7").append(c);
        }
        return builder.toString();
    }

    private static int accountForAndRemoveGreyEnchants(List<String> tooltip, ItemStack item) {
        if (item.func_77986_q() == null || item.func_77986_q().func_74745_c() == 0) {
            return -1;
        }
        int lastGreyEnchant = -1;
        boolean removeGreyEnchants = SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.HIDE_GREY_ENCHANTS);
        int i = 1;
        for (int total = 0; total < 1 + item.func_77986_q().func_74745_c() && i < tooltip.size(); ++total) {
            String line = tooltip.get(i);
            if (GREY_ENCHANT_PATTERN.matcher(line).matches()) {
                lastGreyEnchant = i;
                if (!removeGreyEnchants) continue;
                tooltip.remove(i);
                continue;
            }
            ++i;
        }
        return removeGreyEnchants ? -1 : lastGreyEnchant;
    }

    private static int correctTooltipWidth(int maxTooltipWidth) {
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
        int mouseX = Mouse.getX() * scaledresolution.func_78326_a() / Minecraft.func_71410_x().field_71443_c;
        int tooltipX = mouseX + 12;
        if (tooltipX + maxTooltipWidth + 4 > scaledresolution.func_78326_a() && (tooltipX = mouseX - 16 - maxTooltipWidth) < 4) {
            maxTooltipWidth = mouseX > scaledresolution.func_78326_a() / 2 ? mouseX - 12 - 8 : scaledresolution.func_78326_a() - 16 - mouseX;
        }
        if (scaledresolution.func_78326_a() > 0 && maxTooltipWidth > scaledresolution.func_78326_a()) {
            maxTooltipWidth = scaledresolution.func_78326_a();
        }
        return maxTooltipWidth;
    }

    public static void markCacheDirty() {
        EnchantManager.loreCache.configChanged = true;
    }

    public static void setEnchants(Enchants enchants) {
        EnchantManager.enchants = enchants;
    }

    static class FormattedEnchant
    implements Comparable<FormattedEnchant> {
        Enchant enchant;
        int level;
        List<String> loreDescription;
        String inputFormattedString;

        public FormattedEnchant(Enchant theEnchant, int theLevel, String theFormattedEnchant) {
            this.enchant = theEnchant;
            this.level = theLevel;
            this.inputFormattedString = theFormattedEnchant;
            this.loreDescription = new ArrayList<String>();
        }

        public void addLore(String lineOfEnchantLore) {
            this.loreDescription.add(lineOfEnchantLore);
        }

        public List<String> getLore() {
            return this.loreDescription;
        }

        @Override
        public int compareTo(FormattedEnchant o) {
            return this.enchant.compareTo(o.enchant);
        }

        public int getRenderLength() {
            return Minecraft.func_71410_x().field_71466_p.func_78256_a(this.getFormattedString());
        }

        public String getFormattedString() {
            ConfigValues config = SkyblockAddons.getInstance().getConfigValues();
            StringBuilder b = new StringBuilder();
            if (!config.isEnabled(Feature.ENCHANTMENTS_HIGHLIGHT)) {
                return this.inputFormattedString;
            }
            b.append(this.enchant.getFormattedName(this.level));
            b.append(" ");
            if (config.isEnabled(Feature.REPLACE_ROMAN_NUMERALS_WITH_NUMBERS)) {
                b.append(this.level);
            } else {
                b.append(RomanNumeralParser.integerToRoman(this.level));
            }
            return b.toString();
        }
    }

    static class Cache {
        List<String> cachedAfter = new ArrayList<String>();
        boolean configChanged;
        private List<String> cachedBefore = new ArrayList<String>();

        public void updateBefore(List<String> loreBeforeModifications) {
            this.cachedBefore = new ArrayList<String>(loreBeforeModifications);
        }

        public void updateAfter(List<String> loreAfterModifications) {
            this.cachedAfter = new ArrayList<String>(loreAfterModifications);
            this.configChanged = false;
        }

        public boolean isCached(List<String> loreBeforeModifications) {
            if (this.configChanged || loreBeforeModifications.size() != this.cachedBefore.size()) {
                return false;
            }
            for (int i = 0; i < loreBeforeModifications.size(); ++i) {
                if (loreBeforeModifications.get(i).equals(this.cachedBefore.get(i))) continue;
                return false;
            }
            return true;
        }

        public List<String> getCachedAfter() {
            return this.cachedAfter;
        }

        public List<String> getCachedBefore() {
            return this.cachedBefore;
        }
    }

    static class Enchant
    implements Comparable<Enchant> {
        String nbtName;
        String loreName;
        int goodLevel;
        int maxLevel;

        Enchant() {
        }

        public boolean isNormal() {
            return this instanceof Normal;
        }

        public boolean isUltimate() {
            return this instanceof Ultimate;
        }

        public boolean isStacking() {
            return this instanceof Stacking;
        }

        public String getFormattedName(int level) {
            return this.getFormat(level) + this.loreName;
        }

        public String getUnformattedName() {
            return this.loreName;
        }

        public String getFormat(int level) {
            ConfigValues config = SkyblockAddons.getInstance().getConfigValues();
            if (level >= this.maxLevel) {
                return config.getRestrictedColor(Feature.ENCHANTMENT_PERFECT_COLOR).toString();
            }
            if (level > this.goodLevel) {
                return config.getRestrictedColor(Feature.ENCHANTMENT_GREAT_COLOR).toString();
            }
            if (level == this.goodLevel) {
                return config.getRestrictedColor(Feature.ENCHANTMENT_GOOD_COLOR).toString();
            }
            return config.getRestrictedColor(Feature.ENCHANTMENT_POOR_COLOR).toString();
        }

        public String toString() {
            return this.nbtName + " " + this.goodLevel + " " + this.maxLevel + "\n";
        }

        @Override
        public int compareTo(Enchant o) {
            if (this.isUltimate() == o.isUltimate()) {
                if (this.isStacking() == o.isStacking()) {
                    return this.loreName.compareTo(o.loreName);
                }
                return this.isStacking() ? -1 : 1;
            }
            return this.isUltimate() ? -1 : 1;
        }

        static class Dummy
        extends Enchant {
            public Dummy(String name) {
                this.loreName = name;
                this.nbtName = name.toLowerCase().replaceAll(" ", "_");
            }

            @Override
            public String getFormat(int level) {
                return ColorCode.DARK_RED.toString();
            }
        }

        static class Stacking
        extends Enchant {
            String nbtNum;
            String statLabel;
            TreeSet<Integer> stackLevel;

            Stacking() {
            }

            @Override
            public String toString() {
                return this.nbtNum + " " + this.stackLevel.toString() + " " + super.toString();
            }
        }

        static class Ultimate
        extends Enchant {
            Ultimate() {
            }

            @Override
            public String getFormat(int level) {
                return "\u00a7d\u00a7l";
            }
        }

        static class Normal
        extends Enchant {
            Normal() {
            }
        }
    }

    public static class Enchants {
        HashMap<String, Enchant.Normal> NORMAL = new HashMap();
        HashMap<String, Enchant.Ultimate> ULTIMATE = new HashMap();
        HashMap<String, Enchant.Stacking> STACKING = new HashMap();

        public Enchant getFromLore(String loreName) {
            Enchant enchant = this.NORMAL.get(loreName = loreName.toLowerCase(Locale.US));
            if (enchant == null) {
                enchant = this.ULTIMATE.get(loreName);
            }
            if (enchant == null) {
                enchant = this.STACKING.get(loreName);
            }
            if (enchant == null) {
                enchant = new Enchant.Dummy(loreName);
            }
            return enchant;
        }

        public String toString() {
            return "NORMAL:\n" + this.NORMAL.toString() + "\nULTIMATE:\n" + this.ULTIMATE.toString() + "\nSTACKING:\n" + this.STACKING.toString();
        }
    }
}

