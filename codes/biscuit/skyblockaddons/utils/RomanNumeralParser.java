/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.utils;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RomanNumeralParser {
    private static final Pattern NUMERAL_VALIDATION_PATTERN = Pattern.compile("^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    private static final Pattern NUMERAL_FINDING_PATTERN = Pattern.compile(" (?=[MDCLXVI])(M*(?:C[MD]|D?C{0,3})(?:X[CL]|L?X{0,3})(?:I[XV]|V?I{0,3}))(.?)");
    private static final TreeMap<Integer, String> INT_ROMAN_MAP = new TreeMap();

    public static String integerToRoman(int number) {
        int l = INT_ROMAN_MAP.floorKey(number);
        if (number == l) {
            return INT_ROMAN_MAP.get(number);
        }
        return INT_ROMAN_MAP.get(l) + RomanNumeralParser.integerToRoman(number - l);
    }

    public static String replaceNumeralsWithIntegers(String input) {
        StringBuffer result = new StringBuffer();
        Matcher matcher = NUMERAL_FINDING_PATTERN.matcher(input);
        while (matcher.find()) {
            int parsedInteger;
            Matcher wordPartMatcher = Pattern.compile("^[\\w-']").matcher(matcher.group(2));
            if (wordPartMatcher.matches() || matcher.group(1).equals("") || (parsedInteger = RomanNumeralParser.parseNumeral(matcher.group(1))) == 1 && !matcher.group(2).equals("\u00a7") && !matcher.group(2).equals("")) continue;
            matcher.appendReplacement(result, " " + parsedInteger + "$2");
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static boolean isNumeralValid(String romanNumeral) {
        return NUMERAL_VALIDATION_PATTERN.matcher(romanNumeral).matches();
    }

    public static int parseNumeral(String numeralString) {
        if (!RomanNumeralParser.isNumeralValid(numeralString)) {
            throw new IllegalArgumentException("\"" + numeralString + "\" is not a valid Roman numeral.");
        }
        int value = 0;
        char[] charArray = numeralString.toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            Numeral nextNumeral;
            int diff;
            char c = charArray[i];
            Numeral numeral = Numeral.getFromChar(c);
            if (i + 1 < charArray.length && (diff = (nextNumeral = Numeral.getFromChar(charArray[i + 1])).value - numeral.value) > 0) {
                value += diff;
                ++i;
                continue;
            }
            value += numeral.value;
        }
        return value;
    }

    static {
        INT_ROMAN_MAP.put(1000, "M");
        INT_ROMAN_MAP.put(900, "CM");
        INT_ROMAN_MAP.put(500, "D");
        INT_ROMAN_MAP.put(400, "CD");
        INT_ROMAN_MAP.put(100, "C");
        INT_ROMAN_MAP.put(90, "XC");
        INT_ROMAN_MAP.put(50, "L");
        INT_ROMAN_MAP.put(40, "XL");
        INT_ROMAN_MAP.put(10, "X");
        INT_ROMAN_MAP.put(9, "IX");
        INT_ROMAN_MAP.put(5, "V");
        INT_ROMAN_MAP.put(4, "IV");
        INT_ROMAN_MAP.put(1, "I");
    }

    private static enum Numeral {
        I(1),
        V(5),
        X(10),
        L(50),
        C(100),
        D(500),
        M(1000);

        private final int value;

        private Numeral(int value) {
            this.value = value;
        }

        private static Numeral getFromChar(char c) {
            try {
                return Numeral.valueOf(Character.toString(c));
            }
            catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Expected valid Roman numeral, received '" + c + "'.");
            }
        }
    }
}

