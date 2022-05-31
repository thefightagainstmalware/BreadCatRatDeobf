/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.spookyevent;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.features.spookyevent.CandyType;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpookyEventManager {
    private static final Pattern CANDY_PATTERN = Pattern.compile("Your Candy: (?<greenCandy>\\d+) Green, (?<purpleCandy>\\d+) Purple \\((?<points>\\d+) pts\\.\\)");
    private static final Map<CandyType, Integer> dummyCandyCounts = new HashMap<CandyType, Integer>();
    private static final Map<CandyType, Integer> candyCounts;
    private static int points;

    public static void reset() {
        for (CandyType candyType : CandyType.values()) {
            candyCounts.put(candyType, 0);
        }
        points = 0;
    }

    public static boolean isActive() {
        return SpookyEventManager.getCandyCounts().get((Object)CandyType.GREEN) != 0 || SpookyEventManager.getCandyCounts().get((Object)CandyType.PURPLE) != 0;
    }

    public static void update(String strippedTabFooterString) {
        if (strippedTabFooterString == null) {
            SpookyEventManager.reset();
            return;
        }
        try {
            Matcher matcher = CANDY_PATTERN.matcher(strippedTabFooterString);
            if (matcher.find()) {
                candyCounts.put(CandyType.GREEN, Integer.valueOf(matcher.group("greenCandy")));
                candyCounts.put(CandyType.PURPLE, Integer.valueOf(matcher.group("purpleCandy")));
                points = Integer.parseInt(matcher.group("points"));
            }
        }
        catch (Exception ex) {
            SkyblockAddons.getLogger().error("An error occurred while parsing the spooky event event text in the tab list!", (Throwable)ex);
        }
    }

    public static void update(int green, int purple, int pts) {
        candyCounts.put(CandyType.GREEN, green);
        candyCounts.put(CandyType.PURPLE, purple);
        points = pts;
    }

    public static Map<CandyType, Integer> getDummyCandyCounts() {
        return dummyCandyCounts;
    }

    public static Map<CandyType, Integer> getCandyCounts() {
        return candyCounts;
    }

    public static int getPoints() {
        return points;
    }

    static {
        dummyCandyCounts.put(CandyType.GREEN, 12);
        dummyCandyCounts.put(CandyType.PURPLE, 34);
        candyCounts = new HashMap<CandyType, Integer>();
        SpookyEventManager.reset();
    }
}

