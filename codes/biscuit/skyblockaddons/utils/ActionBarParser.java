/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Attribute;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.SkillType;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class ActionBarParser {
    private static final Pattern COLLECTIONS_CHAT_PATTERN = Pattern.compile("\\+(?<gained>[0-9,.]+) (?<skillName>[A-Za-z]+) (?<progress>\\((((?<current>[0-9.,kM]+)\\/(?<total>[0-9.,kM]+))|((?<percent>[0-9.,]+)%))\\))");
    private static final Pattern SKILL_GAIN_PATTERN_S = Pattern.compile("\\+(?<gained>[0-9,.]+) (?<skillName>[A-Za-z]+) (?<progress>\\((((?<current>[0-9.,kM]+)\\/(?<total>[0-9.,kM]+))|((?<percent>[0-9.]+)%))\\))");
    private static final Pattern MANA_PATTERN_S = Pattern.compile("(?<num>[0-9,]+)/(?<den>[0-9,]+)\u270e(| Mana| (?<overflow>-?[0-9,]+)\u02ac)");
    private static final Pattern DEFENSE_PATTERN_S = Pattern.compile("(?<defense>[0-9]+)\u2748 Defense(?<other>( (?<align>\\|\\|\\|))?( {2}(?<tether>T[0-9]+!?))?.*)?");
    private static final Pattern HEALTH_PATTERN_S = Pattern.compile("(?<health>[0-9]+)/(?<maxHealth>[0-9]+)\u2764(?<wand>\\+(?<wandHeal>[0-9]+)[\u2586\u2585\u2584\u2583\u2582\u2581])?");
    private final SkyblockAddons main;
    private int tickers = -1;
    private int maxTickers = 0;
    private int lastSecondHealth = -1;
    private Integer healthUpdate;
    private long lastHealthUpdate;
    private float currentSkillXP;
    private int totalSkillXP;
    private float percent;
    private boolean healthLock;
    private String otherDefense;
    private final LinkedList<String> stringsToRemove = new LinkedList();

    public ActionBarParser() {
        this.main = SkyblockAddons.getInstance();
    }

    public String parseActionBar(String actionBar) {
        String[] splitMessage = actionBar.split(" {3,}");
        LinkedList<String> unusedSections = new LinkedList<String>();
        this.stringsToRemove.clear();
        this.main.getRenderListener().setPredictMana(true);
        this.main.getRenderListener().setPredictHealth(true);
        this.tickers = -1;
        if (actionBar.contains("\u2764") && !actionBar.contains("\u2748") && splitMessage.length == 2) {
            this.setAttribute(Attribute.DEFENCE, 0);
        }
        for (String section : splitMessage) {
            try {
                String sectionReturn = this.parseSection(section);
                if (sectionReturn != null) {
                    unusedSections.add(sectionReturn);
                    continue;
                }
                this.stringsToRemove.add(section);
            }
            catch (Exception ex) {
                unusedSections.add(section);
            }
        }
        return String.join((CharSequence)StringUtils.repeat((String)" ", (int)5), unusedSections);
    }

    private String parseSection(String section) {
        String stripColoring = TextUtils.stripColor(section);
        String convertMag = TextUtils.convertMagnitudes(stripColoring);
        if (section.contains("\u02ac")) {
            convertMag = convertMag.split(" ")[0];
        }
        String numbersOnly = TextUtils.getNumbersOnly(convertMag).trim();
        String[] splitStats = numbersOnly.split("/");
        if (section.contains("\u2764")) {
            return this.parseHealth(section);
        }
        if (section.contains("\u2748")) {
            return this.parseDefense(section);
        }
        if (section.contains("\u270e")) {
            return this.parseMana(section);
        }
        if (section.contains("(")) {
            return this.parseSkill(section);
        }
        if (section.contains("\u24c4") || section.contains("\u24e9")) {
            return this.parseTickers(section);
        }
        if (section.contains("Drill")) {
            return this.parseDrill(section, splitStats);
        }
        return section;
    }

    private String parseHealth(String healthSection) {
        boolean separateDisplay = this.main.getConfigValues().isEnabled(Feature.HEALTH_BAR) || this.main.getConfigValues().isEnabled(Feature.HEALTH_TEXT);
        String returnString = healthSection;
        String stripped = TextUtils.stripColor(healthSection);
        Matcher m = HEALTH_PATTERN_S.matcher(stripped);
        if (separateDisplay && m.matches()) {
            int newHealth = Integer.parseInt(m.group("health"));
            int maxHealth = Integer.parseInt(m.group("maxHealth"));
            if (m.group("wand") != null) {
                returnString = "";
                this.stringsToRemove.add(stripped.substring(0, m.start("wand")));
            } else {
                returnString = null;
            }
            this.healthLock = false;
            boolean postSetLock = this.main.getUtils().getAttributes().get((Object)Attribute.MAX_HEALTH).getValue() != maxHealth || (double)((float)Math.abs(this.main.getUtils().getAttributes().get((Object)Attribute.HEALTH).getValue() - newHealth) / (float)maxHealth) > 0.05;
            this.setAttribute(Attribute.HEALTH, newHealth);
            this.setAttribute(Attribute.MAX_HEALTH, maxHealth);
            this.healthLock = postSetLock;
        }
        return returnString;
    }

    private String parseMana(String manaSection) {
        Matcher m = MANA_PATTERN_S.matcher(TextUtils.stripColor(manaSection).trim());
        if (m.matches()) {
            this.setAttribute(Attribute.MANA, Integer.parseInt(m.group("num").replaceAll(",", "")));
            this.setAttribute(Attribute.MAX_MANA, Integer.parseInt(m.group("den").replaceAll(",", "")));
            int overflowMana = 0;
            if (m.group("overflow") != null) {
                overflowMana = Integer.parseInt(m.group("overflow").replaceAll(",", ""));
            }
            this.setAttribute(Attribute.OVERFLOW_MANA, overflowMana);
            this.main.getRenderListener().setPredictMana(false);
            if (this.main.getConfigValues().isEnabled(Feature.MANA_BAR) || this.main.getConfigValues().isEnabled(Feature.MANA_TEXT)) {
                return null;
            }
        }
        return manaSection;
    }

    private String parseDefense(String defenseSection) {
        String stripped = TextUtils.stripColor(defenseSection);
        Matcher m = DEFENSE_PATTERN_S.matcher(stripped);
        if (m.matches()) {
            int defense = Integer.parseInt(m.group("defense"));
            this.setAttribute(Attribute.DEFENCE, defense);
            this.otherDefense = TextUtils.getFormattedString(defenseSection, m.group("other").trim());
            if (this.main.getConfigValues().isEnabled(Feature.DEFENCE_TEXT) || this.main.getConfigValues().isEnabled(Feature.DEFENCE_PERCENTAGE)) {
                return null;
            }
        }
        return defenseSection;
    }

    private String parseSkill(String skillSection) {
        Matcher matcher = SKILL_GAIN_PATTERN_S.matcher(TextUtils.stripColor(skillSection));
        if (matcher.matches() && (this.main.getConfigValues().isEnabled(Feature.SKILL_DISPLAY) || this.main.getConfigValues().isEnabled(Feature.SKILL_PROGRESS_BAR))) {
            StringBuilder skillTextBuilder = new StringBuilder();
            if (this.main.getConfigValues().isEnabled(Feature.SHOW_SKILL_XP_GAINED)) {
                skillTextBuilder.append("+").append(matcher.group("gained"));
            }
            float gained = Float.parseFloat(matcher.group("gained").replaceAll(",", ""));
            SkillType skillType = SkillType.getFromString(matcher.group("skillName"));
            boolean skillPercent = matcher.group("percent") != null;
            boolean parseCurrAndTotal = true;
            if (skillPercent) {
                this.percent = Float.parseFloat(matcher.group("percent"));
                int skillLevel = this.main.getSkillXpManager().getSkillLevel(skillType);
                if (skillLevel != -1) {
                    this.totalSkillXP = this.main.getSkillXpManager().getSkillXpForNextLevel(skillType, skillLevel);
                    this.currentSkillXP = (float)this.totalSkillXP * this.percent / 100.0f;
                } else {
                    parseCurrAndTotal = false;
                }
            } else {
                this.currentSkillXP = Float.parseFloat(TextUtils.convertMagnitudes(matcher.group("current")).replaceAll(",", ""));
                this.totalSkillXP = Integer.parseInt(TextUtils.convertMagnitudes(matcher.group("total")).replaceAll(",", ""));
                this.percent = this.totalSkillXP == 0 ? 100.0f : 100.0f * this.currentSkillXP / (float)this.totalSkillXP;
            }
            this.percent = Math.min(100.0f, this.percent);
            if (!parseCurrAndTotal || this.main.getConfigValues().isEnabled(Feature.SHOW_SKILL_PERCENTAGE_INSTEAD_OF_XP)) {
                skillTextBuilder.append(" (").append(String.format("%.2f", Float.valueOf(this.percent))).append("%)");
            } else {
                skillTextBuilder.append(" (").append(TextUtils.formatDouble(this.currentSkillXP));
                if (this.totalSkillXP != 0) {
                    skillTextBuilder.append("/");
                    if (this.main.getConfigValues().isEnabled(Feature.ABBREVIATE_SKILL_XP_DENOMINATOR)) {
                        skillTextBuilder.append(TextUtils.abbreviate(this.totalSkillXP));
                    } else {
                        skillTextBuilder.append(TextUtils.formatDouble(this.totalSkillXP));
                    }
                }
                skillTextBuilder.append(")");
            }
            if (parseCurrAndTotal && this.main.getConfigValues().isEnabled(Feature.SKILL_ACTIONS_LEFT_UNTIL_NEXT_LEVEL) && this.percent != 100.0f) {
                skillTextBuilder.append(" - ").append(Translations.getMessage("messages.actionsLeft", (int)Math.ceil(((float)this.totalSkillXP - this.currentSkillXP) / gained)));
            }
            this.main.getRenderListener().setSkillText(skillTextBuilder.toString());
            this.main.getRenderListener().setSkill(skillType);
            this.main.getRenderListener().setSkillFadeOutTime(System.currentTimeMillis() + 4000L);
            if (this.main.getConfigValues().isEnabled(Feature.SKILL_DISPLAY)) {
                return null;
            }
        }
        return skillSection;
    }

    private String parseTickers(String tickerSection) {
        this.tickers = 0;
        this.maxTickers = 0;
        boolean hitUnusables = false;
        for (char character : tickerSection.toCharArray()) {
            if (!(hitUnusables || character != '7' && character != '2' && character != '6')) {
                hitUnusables = true;
                continue;
            }
            if (character != '\u24c4' && character != '\u24e9') continue;
            if (!hitUnusables) {
                ++this.tickers;
            }
            ++this.maxTickers;
        }
        if (this.main.getConfigValues().isEnabled(Feature.TICKER_CHARGES_DISPLAY)) {
            return null;
        }
        return tickerSection;
    }

    private String parseDrill(String drillSection, String[] splitStats) {
        int fuel = Math.max(0, Integer.parseInt(splitStats[0]));
        int maxFuel = Math.max(1, Integer.parseInt(splitStats[1]));
        this.setAttribute(Attribute.FUEL, fuel);
        this.setAttribute(Attribute.MAX_FUEL, maxFuel);
        if (this.main.getConfigValues().isEnabled(Feature.DRILL_FUEL_BAR) || this.main.getConfigValues().isEnabled(Feature.DRILL_FUEL_TEXT)) {
            return null;
        }
        return drillSection;
    }

    private void setAttribute(Attribute attribute, int value) {
        if (attribute == Attribute.HEALTH && this.healthLock) {
            return;
        }
        this.main.getUtils().getAttributes().get((Object)attribute).setValue(value);
    }

    public SkyblockAddons getMain() {
        return this.main;
    }

    public int getTickers() {
        return this.tickers;
    }

    public int getMaxTickers() {
        return this.maxTickers;
    }

    public int getLastSecondHealth() {
        return this.lastSecondHealth;
    }

    public Integer getHealthUpdate() {
        return this.healthUpdate;
    }

    public long getLastHealthUpdate() {
        return this.lastHealthUpdate;
    }

    public float getCurrentSkillXP() {
        return this.currentSkillXP;
    }

    public int getTotalSkillXP() {
        return this.totalSkillXP;
    }

    public float getPercent() {
        return this.percent;
    }

    public boolean isHealthLock() {
        return this.healthLock;
    }

    public String getOtherDefense() {
        return this.otherDefense;
    }

    public LinkedList<String> getStringsToRemove() {
        return this.stringsToRemove;
    }

    public void setLastSecondHealth(int lastSecondHealth) {
        this.lastSecondHealth = lastSecondHealth;
    }

    public void setHealthUpdate(Integer healthUpdate) {
        this.healthUpdate = healthUpdate;
    }

    public void setLastHealthUpdate(long lastHealthUpdate) {
        this.lastHealthUpdate = lastHealthUpdate;
    }
}

