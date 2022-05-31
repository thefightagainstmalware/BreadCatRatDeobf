/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.healingcircle;

import java.awt.geom.Point2D;

public class HealingCircleParticle {
    private Point2D.Double point;
    private long creation = System.currentTimeMillis();

    public HealingCircleParticle(double x, double z) {
        this.point = new Point2D.Double(x, z);
    }

    public Point2D.Double getPoint() {
        return this.point;
    }

    public long getCreation() {
        return this.creation;
    }
}

