/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.tablist;

import codes.biscuit.skyblockaddons.utils.TextUtils;

public enum TabStringType {
    TITLE,
    SUB_TITLE,
    TEXT,
    PLAYER;


    public static TabStringType fromLine(String line) {
        String strippedLine = TextUtils.stripColor(line);
        if (strippedLine.startsWith(" ")) {
            return TEXT;
        }
        if (!line.contains("\u00a7l") && TextUtils.isUsername(strippedLine)) {
            return PLAYER;
        }
        return SUB_TITLE;
    }
}

