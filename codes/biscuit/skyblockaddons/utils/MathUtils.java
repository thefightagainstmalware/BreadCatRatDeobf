/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.MathHelper
 */
package codes.biscuit.skyblockaddons.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MathUtils {
    public static boolean isInside(int x, int y, int minX, int minY, int maxX, int maxY) {
        return x >= minX && x <= maxX && y > minY && y < maxY;
    }

    public static boolean isInside(float x, float y, float minX, float minY, float maxX, float maxY) {
        return x >= minX && x <= maxX && y > minY && y < maxY;
    }

    public static float normalizeSliderValue(float value, float min, float max, float step) {
        return MathUtils.clamp((MathUtils.snapToStep(value, step) - min) / (max - min), 0.0f, 1.0f);
    }

    public static float denormalizeSliderValue(float value, float min, float max, float step) {
        return MathUtils.clamp(MathUtils.snapToStep(min + (max - min) * MathUtils.clamp(value, 0.0f, 1.0f), step), min, max);
    }

    private static float snapToStep(float value, float step) {
        return step * (float)Math.round(value / step);
    }

    public static float clamp(float value, float min, float max) {
        return value < min ? min : (value > max ? max : value);
    }

    public static double interpolateX(Entity entity, float partialTicks) {
        return MathUtils.interpolate(entity.field_70169_q, entity.field_70165_t, partialTicks);
    }

    public static double interpolateY(Entity entity, float partialTicks) {
        return MathUtils.interpolate(entity.field_70167_r, entity.field_70163_u, partialTicks);
    }

    public static double interpolateZ(Entity entity, float partialTicks) {
        return MathUtils.interpolate(entity.field_70166_s, entity.field_70161_v, partialTicks);
    }

    public static double interpolate(double first, double second, float partialTicks) {
        return first + (second - first) * (double)partialTicks;
    }

    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return MathUtils.distance((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2);
    }

    public static double distance(float x1, float y1, float z1, float x2, float y2, float z2) {
        float deltaX = x1 - x2;
        float deltaY = y1 - y2;
        float deltaZ = z1 - z2;
        return MathHelper.func_76129_c((float)(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return MathUtils.distance((float)x1, (float)y1, (float)x2, (float)y2);
    }

    public static double distance(float x1, float y1, float x2, float y2) {
        float deltaX = x1 - x2;
        float deltaY = y1 - y2;
        return MathHelper.func_76129_c((float)(deltaX * deltaX + deltaY * deltaY));
    }
}

