/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 */
package codes.biscuit.skyblockaddons.features.healingcircle;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.features.healingcircle.HealingCircle;
import codes.biscuit.skyblockaddons.features.healingcircle.HealingCircleParticle;
import codes.biscuit.skyblockaddons.utils.ColorUtils;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import com.google.common.collect.Sets;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class HealingCircleManager {
    private static SkyblockAddons main = SkyblockAddons.getInstance();
    private static Set<HealingCircle> healingCircles = Sets.newConcurrentHashSet();

    public static void addHealingCircleParticle(HealingCircleParticle healingCircleParticle) {
        HealingCircle nearbyHealingCircle = null;
        for (HealingCircle healingCircle : healingCircles) {
            if (healingCircle.hasCachedCenterPoint()) {
                Point2D.Double circleCenter = healingCircle.getCircleCenter();
                if (!(healingCircleParticle.getPoint().distance(circleCenter.getX(), circleCenter.getY()) < 7.0)) continue;
                nearbyHealingCircle = healingCircle;
                break;
            }
            if (!(healingCircleParticle.getPoint().distance(healingCircle.getAverageX(), healingCircle.getAverageZ()) < 14.0)) continue;
            nearbyHealingCircle = healingCircle;
            break;
        }
        if (nearbyHealingCircle != null) {
            nearbyHealingCircle.addPoint(healingCircleParticle);
        } else {
            healingCircles.add(new HealingCircle(healingCircleParticle));
        }
    }

    public static void renderHealingCircleOverlays(float partialTicks) {
        if (main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.SHOW_HEALING_CIRCLE_WALL)) {
            Iterator<HealingCircle> healingCircleIterator = healingCircles.iterator();
            while (healingCircleIterator.hasNext()) {
                HealingCircle healingCircle = healingCircleIterator.next();
                healingCircle.removeOldParticles();
                if (System.currentTimeMillis() - healingCircle.getCreation() > 1000L && healingCircle.getParticlesPerSecond() < 10.0) {
                    healingCircleIterator.remove();
                    continue;
                }
                Point2D.Double circleCenter = healingCircle.getCircleCenter();
                if (circleCenter == null || Double.isNaN(circleCenter.getX()) || Double.isNaN(circleCenter.getY())) continue;
                GlStateManager.func_179094_E();
                GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.func_179140_f();
                GlStateManager.func_179132_a((boolean)false);
                GlStateManager.func_179126_j();
                GlStateManager.func_179147_l();
                GlStateManager.func_179143_c((int)515);
                GlStateManager.func_179129_p();
                GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
                GlStateManager.func_179141_d();
                GlStateManager.func_179090_x();
                boolean chroma = main.getConfigValues().getChromaFeatures().contains((Object)Feature.SHOW_HEALING_CIRCLE_WALL);
                int color = main.getConfigValues().getColor(Feature.SHOW_HEALING_CIRCLE_WALL, ColorUtils.getAlphaIntFromFloat(MathUtils.clamp(main.getConfigValues().getHealingCircleOpacity().floatValue(), 0.0f, 1.0f)));
                DrawUtils.drawCylinder(circleCenter.getX(), 0.0, circleCenter.getY(), 6.0f, 255.0f, ColorUtils.getDummySkyblockColor(color, chroma));
                GlStateManager.func_179089_o();
                GlStateManager.func_179098_w();
                GlStateManager.func_179126_j();
                GlStateManager.func_179132_a((boolean)true);
                GlStateManager.func_179145_e();
                GlStateManager.func_179084_k();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.func_179121_F();
            }
        }
    }

    public static Set<HealingCircle> getHealingCircles() {
        return healingCircles;
    }
}

