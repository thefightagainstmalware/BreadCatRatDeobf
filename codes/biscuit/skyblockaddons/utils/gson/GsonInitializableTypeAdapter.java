/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.TypeAdapter
 *  com.google.gson.TypeAdapterFactory
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonWriter
 */
package codes.biscuit.skyblockaddons.utils.gson;

import codes.biscuit.skyblockaddons.utils.gson.GsonInitializable;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class GsonInitializableTypeAdapter
implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter delegate = gson.getDelegateAdapter((TypeAdapterFactory)this, type);
        return new TypeAdapter<T>(){

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {
                Object object = delegate.read(in);
                if (object instanceof GsonInitializable) {
                    ((GsonInitializable)object).gsonInit();
                }
                return object;
            }
        };
    }
}

