/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shader;

public enum ElementType {
    FLOAT(4, 5126, false),
    UNSIGNED_BYTE(1, 5121, true);

    private int size;
    private int glType;
    private boolean normalize;

    private ElementType(int size, int glType, boolean normalize) {
        this.size = size;
        this.glType = glType;
        this.normalize = normalize;
    }

    public int getSize() {
        return this.size;
    }

    public int getGlType() {
        return this.glType;
    }

    public boolean isNormalize() {
        return this.normalize;
    }
}

