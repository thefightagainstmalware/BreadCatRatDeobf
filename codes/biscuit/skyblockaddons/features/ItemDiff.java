/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package codes.biscuit.skyblockaddons.features;

import net.minecraft.nbt.NBTTagCompound;

public class ItemDiff {
    public static final long LIFESPAN = 5000L;
    private final String displayName;
    private final NBTTagCompound extraAttributes;
    private int amount;
    private long timestamp;

    public ItemDiff(String displayName, int amount) {
        this(displayName, amount, null);
    }

    public ItemDiff(String displayName, int amount, NBTTagCompound extraAttributes) {
        this.displayName = displayName;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
        this.extraAttributes = extraAttributes;
    }

    public void add(int amount) {
        this.amount += amount;
        this.timestamp = this.amount == 0 ? (this.timestamp -= 5000L) : System.currentTimeMillis();
    }

    public long getLifetime() {
        return System.currentTimeMillis() - this.timestamp;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public int getAmount() {
        return this.amount;
    }

    public NBTTagCompound getExtraAttributes() {
        return this.extraAttributes;
    }
}

