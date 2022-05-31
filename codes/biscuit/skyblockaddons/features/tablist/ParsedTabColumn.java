/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.tablist;

import codes.biscuit.skyblockaddons.features.tablist.ParsedTabSection;
import java.util.LinkedList;
import java.util.List;

public class ParsedTabColumn {
    private String title;
    private List<String> lines = new LinkedList<String>();
    private List<ParsedTabSection> sections = new LinkedList<ParsedTabSection>();

    public ParsedTabColumn(String title) {
        this.title = title;
    }

    public void addLine(String line) {
        this.lines.add(line);
    }

    public void addSection(ParsedTabSection section) {
        this.sections.add(section);
    }

    public int size() {
        return this.lines.size() + 1;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public List<ParsedTabSection> getSections() {
        return this.sections;
    }

    public void setSections(List<ParsedTabSection> sections) {
        this.sections = sections;
    }
}

