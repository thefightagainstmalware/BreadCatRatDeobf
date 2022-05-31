/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.tablist;

import codes.biscuit.skyblockaddons.features.tablist.ParsedTabColumn;
import java.util.LinkedList;
import java.util.List;

public class ParsedTabSection {
    private ParsedTabColumn column;
    private List<String> lines = new LinkedList<String>();

    public ParsedTabSection(ParsedTabColumn column) {
        this.column = column;
    }

    public void addLine(String line) {
        this.lines.add(line);
    }

    public int size() {
        return this.lines.size();
    }

    public ParsedTabColumn getColumn() {
        return this.column;
    }

    public List<String> getLines() {
        return this.lines;
    }
}

