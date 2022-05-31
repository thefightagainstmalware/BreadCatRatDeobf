/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.client.particle.EntityFishWakeFX
 *  net.minecraft.entity.projectile.EntityFishHook
 *  net.minecraft.util.MathHelper
 */
package codes.biscuit.skyblockaddons.features.fishParticles;

import codes.biscuit.skyblockaddons.features.fishParticles.FishParticleOverlay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFishWakeFX;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;

public class FishParticleManager {
    private static final double DIST_EXPECTED = 0.1;
    private static final double DIST_VARIATION = 0.005;
    private static final double ANGLE_EXPECTED = 12.0;
    private static final int TIME_VARIATION = 4;
    private static final double[] particleAngl = new double[64];
    private static final double[] particleDist = new double[64];
    private static final long[] particleMatrixRows = new long[64];
    private static final ArrayList<EntityFX>[] particleList = new ArrayList[64];
    private static final LinkedHashMap<Double, List<EntityFX>> particleHash = new LinkedHashMap(64);
    private static final long[] particleTime = new long[64];
    private static int idx = 0;
    private static boolean cacheEmpty = true;
    private static final FishParticleOverlay overlay = new FishParticleOverlay();

    public static void onFishWakeSpawn(EntityFishWakeFX fishWakeParticle) {
        EntityFishHook hook = Minecraft.func_71410_x().field_71439_g.field_71104_cf;
        double xCoord = fishWakeParticle.field_70165_t;
        double zCoord = fishWakeParticle.field_70161_v;
        double hash = 31.0 * (713.0 + xCoord) + zCoord;
        if (hook != null && !particleHash.containsKey(hash)) {
            double distToHook = Math.sqrt((xCoord - hook.field_70165_t) * (xCoord - hook.field_70165_t) + (zCoord - hook.field_70161_v) * (zCoord - hook.field_70161_v));
            if (distToHook > 8.0) {
                return;
            }
            FishParticleManager.particleDist[FishParticleManager.idx] = distToHook;
            FishParticleManager.particleAngl[FishParticleManager.idx] = MathHelper.func_181159_b((double)(xCoord - hook.field_70165_t), (double)(zCoord - hook.field_70161_v)) * 180.0 / Math.PI;
            FishParticleManager.particleTime[FishParticleManager.idx] = Minecraft.func_71410_x().field_71441_e.func_82737_E();
            ArrayList<EntityFishWakeFX> tmp = new ArrayList<EntityFishWakeFX>(Collections.singletonList(fishWakeParticle));
            FishParticleManager.particleList[FishParticleManager.idx] = tmp;
            particleHash.put(hash, tmp);
            if (particleHash.size() > 64) {
                Iterator<Map.Entry<Double, List<EntityFX>>> itr = particleHash.entrySet().iterator();
                while (particleHash.size() > 64) {
                    itr.next();
                    itr.remove();
                }
            }
            cacheEmpty = false;
            long particleRowTmp1 = 0L;
            long particleRowTmp2 = 0L;
            long idxMask = 1L << 63 - idx ^ 0xFFFFFFFFFFFFFFFFL;
            int i = 0;
            while (i < 64) {
                double anglDiff = Math.abs(particleAngl[i] - particleAngl[idx]) % 360.0;
                boolean anglMatch = (anglDiff > 180.0 ? 360.0 - anglDiff : anglDiff) < 12.0;
                double distDiff1 = Math.abs(particleDist[i] - particleDist[idx] - 0.1);
                double distDiff2 = Math.abs(particleDist[i] - particleDist[idx] - 0.2);
                boolean timeMatch = particleTime[idx] - particleTime[i] <= 4L;
                particleRowTmp1 |= (distDiff1 < 0.005 && anglMatch && timeMatch ? 1L : 0L) << 63 - i;
                particleRowTmp2 |= (distDiff2 < 0.005 && anglMatch && timeMatch ? 1L : 0L) << 63 - i;
                int n = i++;
                particleMatrixRows[n] = particleMatrixRows[n] & idxMask;
            }
            FishParticleManager.particleMatrixRows[FishParticleManager.idx] = particleRowTmp1 != 0L ? particleRowTmp1 : particleRowTmp2;
            FishParticleManager.calculateTrails();
            idx = idx < 63 ? idx + 1 : 0;
        } else if (hook != null) {
            particleHash.get(hash).add((EntityFX)fishWakeParticle);
        } else {
            FishParticleManager.clearParticleCache();
        }
    }

    private static void calculateTrails() {
        long[] pow2 = new long[64];
        long[] pow4 = new long[64];
        FishParticleManager.bitwiseMatrixSquare(pow2, particleMatrixRows);
        FishParticleManager.bitwiseMatrixSquare(pow4, pow2);
        long trailHeadTracker = 0L;
        boolean first = true;
        long currTick = Minecraft.func_71410_x().field_71441_e.func_82737_E();
        int i = idx;
        while (first || i != idx) {
            if (pow4[i] != 0L) {
                long mask = 1L << 63 - i;
                if ((trailHeadTracker & mask) == 0L && currTick - particleTime[i] < 5L) {
                    for (EntityFX entityFX : particleList[i]) {
                        overlay.addParticle(entityFX);
                    }
                }
                trailHeadTracker |= particleMatrixRows[i];
            }
            first = false;
            i = i == 0 ? 63 : i - 1;
        }
    }

    public static void clearParticleCache() {
        if (cacheEmpty) {
            return;
        }
        for (int i = 0; i < 64; ++i) {
            FishParticleManager.particleMatrixRows[i] = 0L;
            FishParticleManager.particleDist[i] = Double.MAX_VALUE;
            FishParticleManager.particleAngl[i] = Double.MAX_VALUE;
            FishParticleManager.particleList[i] = null;
            FishParticleManager.particleTime[i] = Long.MAX_VALUE;
        }
        particleHash.clear();
        idx = 0;
        overlay.clearParticles();
        cacheEmpty = true;
    }

    private static void bitwiseMatrixSquare(long[] result, long[] rows) {
        long[] cols = new long[64];
        for (int j = 0; j < 64; ++j) {
            cols[j] = 0L;
            long mask = 1L << 63 - j;
            for (int i = 0; i < 64; ++i) {
                int n = j;
                cols[n] = cols[n] | ((rows[i] & mask) != 0L ? 1L : 0L) << i;
            }
        }
        for (int i = 0; i < 64; ++i) {
            result[i] = 0L;
            for (int j = 0; j < 64; ++j) {
                int n = i;
                result[n] = result[n] | ((rows[i] & cols[j]) != 0L ? 1L : 0L) << 63 - j;
            }
        }
    }
}

