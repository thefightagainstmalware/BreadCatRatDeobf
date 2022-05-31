/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.tablist;

import codes.biscuit.skyblockaddons.features.tablist.TabLine;
import java.util.LinkedList;
import java.util.List;

public class RenderColumn {
    private List<TabLine> lines = new LinkedList<TabLine>();

    public int size() {
        return this.lines.size();
    }

    public void addLine(TabLine line) {
        this.lines.add(line);
    }

    public int getMaxWidth() {
        int maxWidth = 0;
        for (TabLine tabLine : this.lines) {
            maxWidth = Math.max(maxWidth, tabLine.getWidth());
        }
        return maxWidth;
    }

    public List<TabLine> getLines() {
        return this.lines;
    }
}

