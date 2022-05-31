/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  net.minecraft.client.Minecraft
 *  net.minecraft.scoreboard.Score
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.utils.TextUtils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

public class ScoreboardManager {
    public static final Pattern SIDEBAR_EMOJI_PATTERN = Pattern.compile("[\ud83d\udd2b\ud83c\udf6b\ud83d\udca3\ud83d\udc7d\ud83d\udd2e\ud83d\udc0d\ud83d\udc7e\ud83c\udf20\ud83c\udf6d\u26bd\ud83c\udfc0\ud83d\udc79\ud83c\udf81\ud83c\udf89\ud83c\udf82]+");
    private static String scoreboardTitle;
    private static String strippedScoreboardTitle;
    private static List<String> scoreboardLines;
    private static List<String> strippedScoreboardLines;
    private static long lastFoundScoreboard;

    public static void tick() {
        Minecraft mc = Minecraft.func_71410_x();
        if (mc == null || mc.field_71441_e == null || mc.func_71356_B()) {
            ScoreboardManager.clear();
            return;
        }
        Scoreboard scoreboard = mc.field_71441_e.func_96441_U();
        ScoreObjective sidebarObjective = scoreboard.func_96539_a(1);
        if (sidebarObjective == null) {
            ScoreboardManager.clear();
            return;
        }
        lastFoundScoreboard = System.currentTimeMillis();
        scoreboardTitle = sidebarObjective.func_96678_d();
        strippedScoreboardTitle = TextUtils.stripColor(scoreboardTitle);
        ArrayList scores = scoreboard.func_96534_i(sidebarObjective);
        ArrayList filteredScores = scores.stream().filter(p_apply_1_ -> p_apply_1_.func_96653_e() != null && !p_apply_1_.func_96653_e().startsWith("#")).collect(Collectors.toList());
        scores = filteredScores.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip(filteredScores, (int)(scores.size() - 15))) : filteredScores;
        Collections.reverse(filteredScores);
        scoreboardLines = new ArrayList<String>();
        strippedScoreboardLines = new ArrayList<String>();
        for (Score line : scores) {
            ScorePlayerTeam team = scoreboard.func_96509_i(line.func_96653_e());
            String scoreboardLine = ScorePlayerTeam.func_96667_a((Team)team, (String)line.func_96653_e()).trim();
            String cleansedScoreboardLine = SIDEBAR_EMOJI_PATTERN.matcher(scoreboardLine).replaceAll("");
            String strippedCleansedScoreboardLine = TextUtils.stripColor(cleansedScoreboardLine);
            scoreboardLines.add(cleansedScoreboardLine);
            strippedScoreboardLines.add(strippedCleansedScoreboardLine);
        }
    }

    private static void clear() {
        strippedScoreboardTitle = null;
        scoreboardTitle = null;
        strippedScoreboardLines = null;
        scoreboardLines = null;
    }

    public static boolean hasScoreboard() {
        return scoreboardTitle != null;
    }

    public static int getNumberOfLines() {
        return scoreboardLines.size();
    }

    public static String getScoreboardTitle() {
        return scoreboardTitle;
    }

    public static String getStrippedScoreboardTitle() {
        return strippedScoreboardTitle;
    }

    public static List<String> getScoreboardLines() {
        return scoreboardLines;
    }

    public static List<String> getStrippedScoreboardLines() {
        return strippedScoreboardLines;
    }

    public static long getLastFoundScoreboard() {
        return lastFoundScoreboard;
    }

    static {
        lastFoundScoreboard = -1L;
    }
}

