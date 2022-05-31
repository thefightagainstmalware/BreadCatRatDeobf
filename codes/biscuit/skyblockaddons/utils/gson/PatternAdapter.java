/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.TypeAdapter
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonToken
 *  com.google.gson.stream.JsonWriter
 */
package codes.biscuit.skyblockaddons.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class PatternAdapter
extends TypeAdapter<Pattern> {
    public void write(JsonWriter out, Pattern value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.pattern());
    }

    public Pattern read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String patternString = in.nextString();
        return Pattern.compile(patternString);
    }
}

