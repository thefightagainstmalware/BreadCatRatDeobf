/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core;

import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkyblockDate {
    private static final Pattern DATE_PATTERN = Pattern.compile("(?<month>[\\w ]+) (?<day>\\d{1,2})(th|st|nd|rd)");
    private static final Pattern TIME_PATTERN = Pattern.compile("(?<hour>\\d{1,2}):(?<minute>\\d\\d)(?<period>am|pm)");
    private SkyblockMonth month;
    private int day;
    private int hour;
    private int minute;
    private String period;

    public static SkyblockDate parse(String dateString, String timeString) {
        if (dateString == null || timeString == null) {
            return null;
        }
        Matcher dateMatcher = DATE_PATTERN.matcher(dateString.trim());
        Matcher timeMatcher = TIME_PATTERN.matcher(timeString.trim());
        int day = 1;
        int hour = 0;
        int minute = 0;
        String month = SkyblockMonth.EARLY_SPRING.scoreboardString;
        String period = "am";
        if (dateMatcher.find()) {
            month = dateMatcher.group("month");
            day = Integer.parseInt(dateMatcher.group("day"));
        }
        if (timeMatcher.find()) {
            hour = Integer.parseInt(timeMatcher.group("hour"));
            minute = Integer.parseInt(timeMatcher.group("minute"));
            period = timeMatcher.group("period");
        }
        return new SkyblockDate(SkyblockMonth.fromName(month), day, hour, minute, period);
    }

    public SkyblockDate(SkyblockMonth month, int day, int hour, int minute, String period) {
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.period = period;
    }

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String monthName = this.month != null ? this.month.scoreboardString : null;
        return String.format("%s %s, %d:%s%s", monthName, this.day + TextUtils.getOrdinalSuffix(this.day), this.hour, decimalFormat.format(this.minute), this.period);
    }

    public static enum SkyblockMonth {
        EARLY_WINTER("Early Winter"),
        WINTER("Winter"),
        LATE_WINTER("Late Winter"),
        EARLY_SPRING("Early Spring"),
        SPRING("Spring"),
        LATE_SPRING("Late Spring"),
        EARLY_SUMMER("Early Summer"),
        SUMMER("Summer"),
        LATE_SUMMER("Late Summer"),
        EARLY_AUTUMN("Early Autumn"),
        AUTUMN("Autumn"),
        LATE_AUTUMN("Late Autumn");

        final String scoreboardString;

        private SkyblockMonth(String scoreboardString) {
            this.scoreboardString = scoreboardString;
        }

        public static SkyblockMonth fromName(String scoreboardName) {
            for (SkyblockMonth skyblockMonth : SkyblockMonth.values()) {
                if (!skyblockMonth.scoreboardString.equals(scoreboardName)) continue;
                return skyblockMonth;
            }
            return null;
        }
    }
}

