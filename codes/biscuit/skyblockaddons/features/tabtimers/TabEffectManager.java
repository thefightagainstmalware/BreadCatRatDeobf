/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.tabtimers;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.features.tabtimers.TabEffect;
import codes.biscuit.skyblockaddons.utils.RomanNumeralParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabEffectManager {
    private static final TabEffectManager instance = new TabEffectManager();
    private static final Pattern EFFECT_PATTERN = Pattern.compile("(?:(?<potion>\u00a7r\u00a7[a-f0-9][a-zA-Z ]+ (?:I[XV]|V?I{0,3})\u00a7r )|(?<powerup>\u00a7r\u00a7[a-f0-9][a-zA-Z ]+ ))\u00a7r\u00a7f(?<timer>\\d{0,2}:?\\d{1,2}:\\d{2})");
    private static final Pattern EFFECT_COUNT_PATTERN = Pattern.compile("You have (?<effectCount>[0-9]+) active effects\\.");
    private static final Pattern GOD_POTION_PATTERN = Pattern.compile("You have a God Potion active! (?<timer>\\d{0,2}:?\\d{1,2}:\\d{2})");
    private final List<TabEffect> potionTimers = new ArrayList<TabEffect>();
    private final List<TabEffect> powerupTimers = new ArrayList<TabEffect>();
    private int effectCount;
    private static final List<TabEffect> dummyPotionTimers = Arrays.asList(new TabEffect("\u00a7r\u00a7ePotion Effect II ", "12:34"), new TabEffect("\u00a7r\u00a7aEnchanting XP Boost III ", "1:23:45"));
    private static final List<TabEffect> dummyPowerupTimers = Collections.singletonList(new TabEffect("\u00a7r\u00a7bHoming Snowballs ", "1:39"));

    public void putPotionEffect(String potionEffect, String timer) {
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.HIDE_NIGHT_VISION_EFFECT_TIMER) && potionEffect.startsWith("\u00a7r\u00a75Night Vision")) {
            return;
        }
        this.putEffect(new TabEffect(potionEffect, timer), this.potionTimers);
    }

    public void putPowerup(String powerup, String timer) {
        this.putEffect(new TabEffect(powerup, timer), this.powerupTimers);
    }

    private void putEffect(TabEffect effect, List<TabEffect> list) {
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.REPLACE_ROMAN_NUMERALS_WITH_NUMBERS)) {
            effect.setEffect(RomanNumeralParser.replaceNumeralsWithIntegers(effect.getEffect()));
        }
        list.add(effect);
    }

    public void update(String tabFooterString, String strippedTabFooterString) {
        this.potionTimers.clear();
        this.powerupTimers.clear();
        if (tabFooterString == null) {
            return;
        }
        Matcher matcher = EFFECT_PATTERN.matcher(tabFooterString);
        while (matcher.find()) {
            String effectString = matcher.group("potion");
            if (effectString != null) {
                this.putPotionEffect(effectString, matcher.group("timer"));
                continue;
            }
            effectString = matcher.group("powerup");
            if (effectString == null) continue;
            this.putPowerup(effectString, matcher.group("timer"));
        }
        matcher = EFFECT_COUNT_PATTERN.matcher(strippedTabFooterString);
        if (matcher.find()) {
            this.effectCount = Integer.parseInt(matcher.group("effectCount"));
        } else {
            matcher = GOD_POTION_PATTERN.matcher(strippedTabFooterString);
            if (matcher.find()) {
                this.putPotionEffect("\u00a7cGod Potion\u00a7r ", matcher.group("timer"));
                this.effectCount = 32;
            } else {
                this.effectCount = 0;
            }
        }
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.SORT_TAB_EFFECT_TIMERS)) {
            Collections.sort(this.potionTimers);
            Collections.sort(this.powerupTimers);
        }
    }

    public static TabEffectManager getInstance() {
        return instance;
    }

    public List<TabEffect> getPotionTimers() {
        return this.potionTimers;
    }

    public List<TabEffect> getPowerupTimers() {
        return this.powerupTimers;
    }

    public int getEffectCount() {
        return this.effectCount;
    }

    public static List<TabEffect> getDummyPotionTimers() {
        return dummyPotionTimers;
    }

    public static List<TabEffect> getDummyPowerupTimers() {
        return dummyPowerupTimers;
    }
}

