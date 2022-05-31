/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shader;

import codes.biscuit.skyblockaddons.shader.ElementType;

public enum VertexFormatElement {
    POSITION(3, ElementType.FLOAT),
    TEX(2, ElementType.FLOAT),
    COLOR(4, ElementType.UNSIGNED_BYTE);

    private int count;
    private ElementType elementType;

    public int getTotalSize() {
        return this.count * this.elementType.getSize();
    }

    private VertexFormatElement(int count, ElementType elementType) {
        this.count = count;
        this.elementType = elementType;
    }

    public int getCount() {
        return this.count;
    }

    public ElementType getElementType() {
        return this.elementType;
    }
}

