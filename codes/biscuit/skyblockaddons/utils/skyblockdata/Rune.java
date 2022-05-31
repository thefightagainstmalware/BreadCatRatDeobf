/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package codes.biscuit.skyblockaddons.utils.skyblockdata;

import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;

public class Rune {
    private String type;
    private int level;

    public Rune(NBTTagCompound runeData) {
        Iterator iterator = runeData.func_150296_c().iterator();
        while (iterator.hasNext()) {
            String runeType;
            this.type = runeType = (String)iterator.next();
            this.level = runeData.func_74762_e(runeType);
        }
    }

    public String getType() {
        return this.type;
    }

    public int getLevel() {
        return this.level;
    }
}

