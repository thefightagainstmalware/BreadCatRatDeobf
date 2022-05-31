/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package codes.biscuit.skyblockaddons.features.tablist;

import codes.biscuit.skyblockaddons.features.tablist.TabStringType;
import net.minecraft.client.Minecraft;

public class TabLine {
    private TabStringType type;
    private String text;

    public TabLine(String text, TabStringType type) {
        this.type = type;
        this.text = text;
    }

    public int getWidth() {
        Minecraft mc = Minecraft.func_71410_x();
        int width = mc.field_71466_p.func_78256_a(this.text);
        if (this.type == TabStringType.PLAYER) {
            width += 10;
        }
        if (this.type == TabStringType.TEXT) {
            width += 4;
        }
        return width;
    }

    public TabStringType getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }
}

