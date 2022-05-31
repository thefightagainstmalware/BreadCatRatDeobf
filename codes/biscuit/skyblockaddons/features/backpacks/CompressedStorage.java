/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.backpacks;

import codes.biscuit.skyblockaddons.utils.gson.GsonInitializable;

public class CompressedStorage
implements GsonInitializable {
    private String storage = "[]";
    private transient byte[] transientStorage = new byte[0];

    public CompressedStorage() {
    }

    public CompressedStorage(byte[] compressedStorage) {
        this.transientStorage = compressedStorage;
        this.storage = this.convertByteArrayToString(compressedStorage);
    }

    public byte[] getStorage() {
        return this.transientStorage;
    }

    public void setStorage(byte[] storageBytes) {
        this.transientStorage = storageBytes;
        this.storage = this.convertByteArrayToString(storageBytes);
    }

    private String convertByteArrayToString(byte[] byteArray) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (byte b : byteArray) {
            builder.append(b).append(",");
        }
        if (builder.length() > 1) {
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        return builder.toString();
    }

    private byte[] convertStringToByteArray(String formattedString) {
        if (formattedString == null || formattedString.length() < 2) {
            return new byte[0];
        }
        String list = formattedString.substring(1, formattedString.length() - 1);
        String[] bytes = list.split(",");
        byte[] ret = new byte[bytes.length];
        for (int i = 0; i < bytes.length; ++i) {
            ret[i] = Byte.parseByte(bytes[i]);
        }
        return ret;
    }

    @Override
    public void gsonInit() {
        this.transientStorage = this.convertStringToByteArray(this.storage);
    }
}

