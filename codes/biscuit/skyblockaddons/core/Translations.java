/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.ibm.icu.text.ArabicShaping
 *  com.ibm.icu.text.ArabicShapingException
 *  com.ibm.icu.text.Bidi
 *  net.minecraft.client.Minecraft
 */
package codes.biscuit.skyblockaddons.core;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Language;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;

public class Translations {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("%[A-Za-z-]+%");

    public static String getMessage(String path, Object ... variables) {
        String text = "";
        try {
            SkyblockAddons main = SkyblockAddons.getInstance();
            String[] pathSplit = path.split(Pattern.quote("."));
            JsonObject jsonObject = main.getConfigValues().getLanguageConfig();
            for (String pathPart : pathSplit) {
                if (pathPart.equals("")) continue;
                JsonElement jsonElement = jsonObject.get(pathPart);
                if (jsonElement.isJsonObject()) {
                    jsonObject = jsonObject.getAsJsonObject(pathPart);
                    continue;
                }
                text = jsonObject.get(path.substring(path.lastIndexOf(pathPart))).getAsString();
                break;
            }
            Matcher matcher = VARIABLE_PATTERN.matcher(text);
            ArrayDeque<Object> variablesDeque = new ArrayDeque<Object>(Arrays.asList(variables));
            while (matcher.find() && !variablesDeque.isEmpty()) {
                text = matcher.replaceFirst(Matcher.quoteReplacement(variablesDeque.pollFirst().toString()));
                matcher = VARIABLE_PATTERN.matcher(text);
            }
            if (!(main.getConfigValues().getLanguage() != Language.HEBREW && main.getConfigValues().getLanguage() != Language.ARABIC || Minecraft.func_71410_x().field_71466_p.func_78260_a())) {
                text = Translations.bidiReorder(text);
            }
        }
        catch (Exception ex) {
            SkyblockAddons.getLogger().error("The string with the path \"{}\" wasn't found.", new Object[]{path, ex});
            text = path;
        }
        return text;
    }

    private static String bidiReorder(String text) {
        try {
            Bidi bidi = new Bidi(new ArabicShaping(8).shape(text), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        }
        catch (ArabicShapingException var3) {
            return text;
        }
    }
}

