/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.tabtimers;

import codes.biscuit.skyblockaddons.utils.TextUtils;

public class TabEffect
implements Comparable<TabEffect> {
    private String duration;
    private String effect;
    private int durationSeconds;

    public TabEffect(String effect, String duration) {
        this.effect = effect;
        this.duration = duration;
        String[] s = duration.split(":");
        this.durationSeconds = 0;
        for (int i = s.length; i > 0; --i) {
            this.durationSeconds = (int)((double)this.durationSeconds + (double)Integer.parseInt(s[i - 1]) * Math.pow(60.0, s.length - i));
        }
    }

    public String getDurationForDisplay() {
        return "\u00a7r" + this.duration;
    }

    @Override
    public int compareTo(TabEffect o) {
        int difference = o.getDurationSeconds() - this.getDurationSeconds();
        if (Math.abs(difference) <= 1) {
            return TextUtils.stripColor(o.getEffect()).compareTo(TextUtils.stripColor(this.getEffect()));
        }
        return difference;
    }

    public String getEffect() {
        return this.effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getDurationSeconds() {
        return this.durationSeconds;
    }
}

