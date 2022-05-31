/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.utils.ColorUtils;
import java.awt.Color;
import org.apache.commons.lang3.StringUtils;

public enum ColorCode {
    BLACK('0', 0),
    DARK_BLUE('1', 170),
    DARK_GREEN('2', 43520),
    DARK_AQUA('3', 43690),
    DARK_RED('4', 0xAA0000),
    DARK_PURPLE('5', 0xAA00AA),
    GOLD('6', 0xFFAA00),
    GRAY('7', 0xAAAAAA),
    DARK_GRAY('8', 0x555555),
    BLUE('9', 0x5555FF),
    GREEN('a', 0x55FF55),
    AQUA('b', 0x55FFFF),
    RED('c', 0xFF5555),
    LIGHT_PURPLE('d', 0xFF55FF),
    YELLOW('e', 0xFFFF55),
    WHITE('f', 0xFFFFFF),
    MAGIC('k', true, "obfuscated"),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true, "underlined"),
    ITALIC('o', true),
    RESET('r'),
    CHROMA('z', 0xFFFFFE);

    public static final char COLOR_CHAR = '\u00a7';
    private char code;
    private boolean isFormat;
    private String jsonName;
    private String toString;
    private int color;

    private ColorCode(char code) {
        this(code, -1);
    }

    private ColorCode(char code, int rgb) {
        this(code, false, rgb);
    }

    private ColorCode(char code, boolean isFormat) {
        this(code, isFormat, -1);
    }

    private ColorCode(char code, boolean isFormat, int rgb) {
        this(code, isFormat, null, rgb);
    }

    private ColorCode(char code, boolean isFormat, String jsonName) {
        this(code, isFormat, jsonName, -1);
    }

    private ColorCode(char code, boolean isFormat, String jsonName, int rgb) {
        this.code = code;
        this.isFormat = isFormat;
        this.jsonName = jsonName;
        this.toString = new String(new char[]{'\u00a7', code});
        this.color = 0xFF000000 | rgb & 0xFFFFFF;
    }

    public static ColorCode getByChar(char code) {
        for (ColorCode color : ColorCode.values()) {
            if (color.code != code) continue;
            return color;
        }
        return null;
    }

    public char getCode() {
        return this.code;
    }

    public Color getColorObject() {
        return new Color(this.color);
    }

    public int getColor(int alpha) {
        return ColorUtils.setColorAlpha(this.color, alpha);
    }

    public String getJsonName() {
        return StringUtils.isEmpty((CharSequence)this.jsonName) ? this.name().toLowerCase() : this.jsonName;
    }

    public int getColor() {
        return this.color;
    }

    public boolean isColor() {
        return !this.isFormat() && this != RESET;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public ColorCode getNextFormat() {
        return this.getNextFormat(this.ordinal());
    }

    private ColorCode getNextFormat(int ordinal) {
        int nextColor = ordinal + 1;
        ColorCode[] values = ColorCode.values();
        if (nextColor > values.length - 1) {
            return values[0];
        }
        if (!values[nextColor].isColor()) {
            return this.getNextFormat(nextColor);
        }
        return values[nextColor];
    }

    public String toString() {
        return this.toString;
    }
}

