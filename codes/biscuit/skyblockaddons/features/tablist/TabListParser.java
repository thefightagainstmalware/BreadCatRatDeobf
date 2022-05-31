/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiPlayerTabOverlay
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package codes.biscuit.skyblockaddons.features.tablist;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.SkillType;
import codes.biscuit.skyblockaddons.features.spookyevent.SpookyEventManager;
import codes.biscuit.skyblockaddons.features.tablist.ParsedTabColumn;
import codes.biscuit.skyblockaddons.features.tablist.ParsedTabSection;
import codes.biscuit.skyblockaddons.features.tablist.RenderColumn;
import codes.biscuit.skyblockaddons.features.tablist.TabLine;
import codes.biscuit.skyblockaddons.features.tablist.TabStringType;
import codes.biscuit.skyblockaddons.features.tabtimers.TabEffectManager;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

public class TabListParser {
    private static final SkyblockAddons main = SkyblockAddons.getInstance();
    public static String HYPIXEL_ADVERTISEMENT_CONTAINS = "HYPIXEL.NET";
    private static final Pattern GOD_POTION_PATTERN = Pattern.compile("You have a God Potion active! (?<timer>\\d{0,2}:?\\d{1,2}:\\d{2})");
    private static final Pattern ACTIVE_EFFECTS_PATTERN = Pattern.compile("Active Effects(?:\u00a7.)*(?:\\n(?:\u00a7.)*\u00a77.+)*");
    private static final Pattern COOKIE_BUFF_PATTERN = Pattern.compile("Cookie Buff(?:\u00a7.)*(?:\\n(\u00a7.)*\u00a77.+)*");
    private static final Pattern UPGRADES_PATTERN = Pattern.compile("(?<firstPart>\u00a7e[A-Za-z ]+)(?<secondPart> \u00a7f[0-9dhms ]+)");
    private static final Pattern RAIN_TIME_PATTERN_S = Pattern.compile("Rain: (?<time>[0-9dhms ]+)");
    private static final Pattern CANDY_PATTERN_S = Pattern.compile("Your Candy: (?<green>[0-9,]+) Green, (?<purple>[0-9,]+) Purple \\((?<points>[0-9,]+) pts\\.\\)");
    private static final Pattern SKILL_LEVEL_S = Pattern.compile("Skills: (?<skill>[A-Za-z]+) (?<level>[0-9]+).*");
    private static List<RenderColumn> renderColumns;
    private static String parsedRainTime;

    public static void parse() {
        Minecraft mc = Minecraft.func_71410_x();
        if (!main.getUtils().isOnSkyblock() || !main.getConfigValues().isEnabled(Feature.COMPACT_TAB_LIST) && (!main.getConfigValues().isEnabled(Feature.BIRCH_PARK_RAINMAKER_TIMER) || main.getUtils().getLocation() != Location.BIRCH_PARK) && main.getConfigValues().isDisabled(Feature.CANDY_POINTS_COUNTER) && main.getConfigValues().isEnabled(Feature.SHOW_SKILL_PERCENTAGE_INSTEAD_OF_XP)) {
            renderColumns = null;
            return;
        }
        if (mc.field_71439_g == null || mc.field_71439_g.field_71174_a == null) {
            renderColumns = null;
            return;
        }
        NetHandlerPlayClient netHandler = mc.field_71439_g.field_71174_a;
        List<NetworkPlayerInfo> fullList = GuiPlayerTabOverlay.field_175252_a.sortedCopy((Iterable)netHandler.func_175106_d());
        if (fullList.size() < 80) {
            renderColumns = null;
            return;
        }
        fullList = fullList.subList(0, 80);
        List<ParsedTabColumn> columns = TabListParser.parseColumns(fullList);
        ParsedTabColumn footerAsColumn = TabListParser.parseFooterAsColumn();
        if (footerAsColumn != null) {
            columns.add(footerAsColumn);
        }
        TabListParser.parseSections(columns);
        renderColumns = new LinkedList<RenderColumn>();
        RenderColumn renderColumn = new RenderColumn();
        renderColumns.add(renderColumn);
        TabListParser.combineColumnsToRender(columns, renderColumn);
    }

    public static ParsedTabColumn getColumnFromName(List<ParsedTabColumn> columns, String name) {
        for (ParsedTabColumn parsedTabColumn : columns) {
            if (!name.equals(parsedTabColumn.getTitle())) continue;
            return parsedTabColumn;
        }
        return null;
    }

    private static List<ParsedTabColumn> parseColumns(List<NetworkPlayerInfo> fullList) {
        GuiPlayerTabOverlay tabList = Minecraft.func_71410_x().field_71456_v.func_175181_h();
        LinkedList<ParsedTabColumn> columns = new LinkedList<ParsedTabColumn>();
        for (int entry = 0; entry < fullList.size(); entry += 20) {
            String title = TextUtils.trimWhitespaceAndResets(tabList.func_175243_a(fullList.get(entry)));
            ParsedTabColumn column = TabListParser.getColumnFromName(columns, title);
            if (column == null) {
                column = new ParsedTabColumn(title);
                columns.add(column);
            }
            for (int columnEntry = entry + 1; columnEntry < fullList.size() && columnEntry < entry + 20; ++columnEntry) {
                column.addLine(tabList.func_175243_a(fullList.get(columnEntry)));
            }
        }
        return columns;
    }

    public static ParsedTabColumn parseFooterAsColumn() {
        GuiPlayerTabOverlay tabList = Minecraft.func_71410_x().field_71456_v.func_175181_h();
        if (tabList.field_175255_h == null) {
            return null;
        }
        ParsedTabColumn column = new ParsedTabColumn("\u00a72\u00a7lOther");
        String footer = tabList.field_175255_h.func_150254_d();
        Matcher m = GOD_POTION_PATTERN.matcher(tabList.field_175255_h.func_150260_c());
        footer = m.find() ? ACTIVE_EFFECTS_PATTERN.matcher(footer).replaceAll("Active Effects: \u00a7r\u00a7e" + TabEffectManager.getInstance().getEffectCount() + "\n\u00a7cGod Potion\u00a7r: " + m.group("timer")) : ACTIVE_EFFECTS_PATTERN.matcher(footer).replaceAll("Active Effects: \u00a7r\u00a7e" + TabEffectManager.getInstance().getEffectCount());
        Matcher matcher = COOKIE_BUFF_PATTERN.matcher(footer);
        if (matcher.find() && matcher.group().contains("Not active!")) {
            footer = matcher.replaceAll("Cookie Buff \n\u00a7r\u00a77Not Active");
        }
        for (String line : new ArrayList<String>(Arrays.asList(footer.split("\n")))) {
            if (line.contains(HYPIXEL_ADVERTISEMENT_CONTAINS)) continue;
            matcher = UPGRADES_PATTERN.matcher(TextUtils.stripResets(line));
            if (matcher.matches()) {
                String firstPart = TextUtils.trimWhitespaceAndResets(matcher.group("firstPart"));
                if (!firstPart.contains("\u00a7l")) {
                    firstPart = " " + firstPart;
                }
                column.addLine(firstPart);
                line = matcher.group("secondPart");
            }
            if (!(line = TextUtils.trimWhitespaceAndResets(line)).contains("\u00a7l")) {
                line = " " + line;
            }
            column.addLine(line);
        }
        return column;
    }

    public static void parseSections(List<ParsedTabColumn> columns) {
        parsedRainTime = null;
        boolean foundSpooky = false;
        boolean parsedSkill = false;
        for (ParsedTabColumn column : columns) {
            ParsedTabSection currentSection = null;
            for (String line : column.getLines()) {
                Matcher m;
                if (TextUtils.trimWhitespaceAndResets(line).isEmpty()) {
                    currentSection = null;
                    continue;
                }
                String stripped = TextUtils.stripColor(line).trim();
                if (parsedRainTime == null && (m = RAIN_TIME_PATTERN_S.matcher(stripped)).matches()) {
                    parsedRainTime = m.group("time");
                }
                if (!foundSpooky && (m = CANDY_PATTERN_S.matcher(stripped)).matches()) {
                    SpookyEventManager.update(Integer.parseInt(m.group("green").replaceAll(",", "")), Integer.parseInt(m.group("purple").replaceAll(",", "")), Integer.parseInt(m.group("points").replaceAll(",", "")));
                    foundSpooky = true;
                }
                if (!parsedSkill && (m = SKILL_LEVEL_S.matcher(stripped)).matches()) {
                    SkillType skillType = SkillType.getFromString(m.group("skill"));
                    int level = Integer.parseInt(m.group("level"));
                    main.getSkillXpManager().setSkillLevel(skillType, level);
                    parsedSkill = true;
                }
                if (currentSection == null) {
                    currentSection = new ParsedTabSection(column);
                    column.addSection(currentSection);
                }
                currentSection.addLine(line);
            }
        }
        if (!foundSpooky) {
            SpookyEventManager.reset();
        }
    }

    public static void combineColumnsToRender(List<ParsedTabColumn> columns, RenderColumn initialColumn) {
        String lastTitle = null;
        for (ParsedTabColumn column : columns) {
            for (ParsedTabSection section : column.getSections()) {
                int sectionSize = section.size();
                boolean needsTitle = false;
                if (lastTitle != section.getColumn().getTitle()) {
                    needsTitle = true;
                    ++sectionSize;
                }
                int currentCount = initialColumn.size();
                if (sectionSize >= 11) {
                    if (currentCount >= 22) {
                        initialColumn = new RenderColumn();
                        renderColumns.add(initialColumn);
                        currentCount = 1;
                    } else if (initialColumn.size() > 0) {
                        initialColumn.addLine(new TabLine("", TabStringType.TEXT));
                    }
                    if (needsTitle) {
                        lastTitle = section.getColumn().getTitle();
                        initialColumn.addLine(new TabLine(lastTitle, TabStringType.TITLE));
                        ++currentCount;
                    }
                    for (String line : section.getLines()) {
                        if (currentCount >= 22) {
                            initialColumn = new RenderColumn();
                            renderColumns.add(initialColumn);
                            currentCount = 1;
                        }
                        initialColumn.addLine(new TabLine(line, TabStringType.fromLine(line)));
                        ++currentCount;
                    }
                    continue;
                }
                if (currentCount + sectionSize > 22) {
                    initialColumn = new RenderColumn();
                    renderColumns.add(initialColumn);
                } else if (initialColumn.size() > 0) {
                    initialColumn.addLine(new TabLine("", TabStringType.TEXT));
                }
                if (needsTitle) {
                    lastTitle = section.getColumn().getTitle();
                    initialColumn.addLine(new TabLine(lastTitle, TabStringType.TITLE));
                }
                for (String line : section.getLines()) {
                    initialColumn.addLine(new TabLine(line, TabStringType.fromLine(line)));
                }
            }
        }
    }

    public static List<RenderColumn> getRenderColumns() {
        return renderColumns;
    }

    public static String getParsedRainTime() {
        return parsedRainTime;
    }

    static {
        parsedRainTime = null;
    }
}

