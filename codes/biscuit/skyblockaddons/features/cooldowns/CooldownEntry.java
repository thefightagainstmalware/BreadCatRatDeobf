/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.cooldowns;

public class CooldownEntry {
    static final CooldownEntry NULL_ENTRY = new CooldownEntry(0L);
    private long cooldown;
    private long lastUse;

    CooldownEntry(long cooldown) {
        this.cooldown = cooldown;
        this.lastUse = System.currentTimeMillis();
    }

    boolean isOnCooldown() {
        return System.currentTimeMillis() < this.lastUse + this.cooldown;
    }

    long getRemainingCooldown() {
        long diff = this.lastUse + this.cooldown - System.currentTimeMillis();
        return diff <= 0L ? 0L : diff;
    }

    double getRemainingCooldownPercent() {
        return this.isOnCooldown() ? (double)this.getRemainingCooldown() / (double)this.cooldown : 0.0;
    }
}

