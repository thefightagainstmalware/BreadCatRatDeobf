/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shader;

import codes.biscuit.skyblockaddons.shader.VertexFormatElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum VertexFormat {
    POSITION(VertexFormatElement.POSITION),
    POSITION_COLOR(VertexFormatElement.POSITION, VertexFormatElement.COLOR);

    private List<VertexFormatElement> vertexFormatElements;

    private VertexFormat(VertexFormatElement ... vertexFormatElements) {
        this.vertexFormatElements = Collections.unmodifiableList(Arrays.asList(vertexFormatElements));
    }

    public List<VertexFormatElement> getVertexFormatElements() {
        return this.vertexFormatElements;
    }
}

