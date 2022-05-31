/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.features.healingcircle;

import codes.biscuit.skyblockaddons.features.healingcircle.HealingCircleParticle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HealingCircle {
    public static final float DIAMETER = 12.0f;
    public static final float RADIUS = 6.0f;
    private List<HealingCircleParticle> healingCircleParticles = new ArrayList<HealingCircleParticle>();
    private long creation = System.currentTimeMillis();
    private Point2D.Double cachedCenterPoint = null;
    private double totalX;
    private double totalZ;
    private int totalParticles;

    public HealingCircle(HealingCircleParticle healingCircleParticle) {
        this.addPoint(healingCircleParticle);
    }

    public void addPoint(HealingCircleParticle healingCircleParticle) {
        ++this.totalParticles;
        this.totalX += healingCircleParticle.getPoint().getX();
        this.totalZ += healingCircleParticle.getPoint().getY();
        this.healingCircleParticles.add(healingCircleParticle);
    }

    public double getAverageX() {
        return this.totalX / (double)this.totalParticles;
    }

    public double getAverageZ() {
        return this.totalZ / (double)this.totalParticles;
    }

    public double getParticlesPerSecond() {
        int particlesPerSecond = 0;
        long now = System.currentTimeMillis();
        for (HealingCircleParticle healingCircleParticle : this.healingCircleParticles) {
            if (now - healingCircleParticle.getCreation() >= 1000L) continue;
            ++particlesPerSecond;
        }
        return particlesPerSecond;
    }

    public Point2D.Double getCircleCenter() {
        if (this.cachedCenterPoint != null) {
            return this.cachedCenterPoint;
        }
        if (this.healingCircleParticles.size() < 3) {
            return new Point2D.Double(Double.NaN, Double.NaN);
        }
        Point2D.Double middlePoint = this.healingCircleParticles.get(0).getPoint();
        Point2D.Double firstPoint = null;
        for (HealingCircleParticle healingCircleParticle : this.healingCircleParticles) {
            Point2D.Double point = healingCircleParticle.getPoint();
            if (point == middlePoint || !(point.distance(middlePoint) > 2.0)) continue;
            firstPoint = point;
            break;
        }
        if (firstPoint == null) {
            return new Point2D.Double(Double.NaN, Double.NaN);
        }
        Point2D.Double secondPoint = null;
        for (HealingCircleParticle healingCircleParticle : this.healingCircleParticles) {
            double distanceToMiddle;
            Point2D.Double point = healingCircleParticle.getPoint();
            if (point == middlePoint || point == firstPoint || !((distanceToMiddle = point.distance(middlePoint)) > 2.0) || !(point.distance(firstPoint) > distanceToMiddle)) continue;
            secondPoint = point;
            break;
        }
        if (secondPoint == null) {
            return new Point2D.Double(Double.NaN, Double.NaN);
        }
        Point2D.Double double_ = new Point2D.Double((middlePoint.x + firstPoint.x) / 2.0, (middlePoint.y + firstPoint.y) / 2.0);
        Point2D.Double secondChordMidpoint = new Point2D.Double((middlePoint.x + secondPoint.x) / 2.0, (middlePoint.y + secondPoint.y) / 2.0);
        Point2D.Double firstChordFirst = HealingCircle.rotatePoint(middlePoint, double_, 90.0);
        Point2D.Double firstChordSecond = HealingCircle.rotatePoint(firstPoint, double_, 90.0);
        Point2D.Double secondChordFirst = HealingCircle.rotatePoint(middlePoint, secondChordMidpoint, 90.0);
        Point2D.Double secondChordSecond = HealingCircle.rotatePoint(secondPoint, secondChordMidpoint, 90.0);
        Point2D.Double center = HealingCircle.lineLineIntersection(firstChordFirst, firstChordSecond, secondChordFirst, secondChordSecond);
        this.checkIfCenterIsPerfect(center);
        return center;
    }

    public void checkIfCenterIsPerfect(Point2D.Double center) {
        if (this.totalParticles < 25) {
            return;
        }
        int perfectParticles = 0;
        for (HealingCircleParticle healingCircleParticle : this.healingCircleParticles) {
            Point2D.Double point = healingCircleParticle.getPoint();
            double distance = point.distance(center);
            if (!(distance > 5.5) || !(distance < 6.5)) continue;
            ++perfectParticles;
        }
        float percentagePerfect = (float)perfectParticles / (float)this.totalParticles;
        if ((double)percentagePerfect > 0.75) {
            this.cachedCenterPoint = center;
        }
    }

    private static Point2D.Double rotatePoint(Point2D.Double point, Point2D.Double center, double degrees) {
        double radians = Math.toRadians(degrees);
        double newX = center.getX() + (point.getX() - center.getX()) * Math.cos(radians) - (point.getY() - center.getY()) * Math.sin(radians);
        double newY = center.getY() + (point.getX() - center.getX()) * Math.sin(radians) + (point.getY() - center.getY()) * Math.cos(radians);
        return new Point2D.Double(newX, newY);
    }

    private static Point2D.Double lineLineIntersection(Point2D.Double a, Point2D.Double b, Point2D.Double c, Point2D.Double d) {
        double a1 = b.y - a.y;
        double b1 = a.x - b.x;
        double c1 = a1 * a.x + b1 * a.y;
        double a2 = d.y - c.y;
        double b2 = c.x - d.x;
        double c2 = a2 * c.x + b2 * c.y;
        double determinant = a1 * b2 - a2 * b1;
        if (determinant == 0.0) {
            return new Point2D.Double(Double.NaN, Double.NaN);
        }
        double x = (b2 * c1 - b1 * c2) / determinant;
        double y = (a1 * c2 - a2 * c1) / determinant;
        return new Point2D.Double(x, y);
    }

    public void removeOldParticles() {
        Iterator<HealingCircleParticle> healingCircleParticleIterator = this.healingCircleParticles.iterator();
        while (healingCircleParticleIterator.hasNext()) {
            HealingCircleParticle healingCircleParticle = healingCircleParticleIterator.next();
            if (System.currentTimeMillis() - healingCircleParticle.getCreation() <= 10000L) continue;
            this.totalX -= healingCircleParticle.getPoint().getX();
            this.totalZ -= healingCircleParticle.getPoint().getY();
            --this.totalParticles;
            healingCircleParticleIterator.remove();
        }
    }

    public boolean hasCachedCenterPoint() {
        return this.cachedCenterPoint != null;
    }

    public List<HealingCircleParticle> getHealingCircleParticles() {
        return this.healingCircleParticles;
    }

    public long getCreation() {
        return this.creation;
    }

    public Point2D.Double getCachedCenterPoint() {
        return this.cachedCenterPoint;
    }

    public double getTotalX() {
        return this.totalX;
    }

    public double getTotalZ() {
        return this.totalZ;
    }

    public int getTotalParticles() {
        return this.totalParticles;
    }

    public void setHealingCircleParticles(List<HealingCircleParticle> healingCircleParticles) {
        this.healingCircleParticles = healingCircleParticles;
    }

    public void setCreation(long creation) {
        this.creation = creation;
    }

    public void setCachedCenterPoint(Point2D.Double cachedCenterPoint) {
        this.cachedCenterPoint = cachedCenterPoint;
    }

    public void setTotalX(double totalX) {
        this.totalX = totalX;
    }

    public void setTotalZ(double totalZ) {
        this.totalZ = totalZ;
    }

    public void setTotalParticles(int totalParticles) {
        this.totalParticles = totalParticles;
    }
}

