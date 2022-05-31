/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package codes.biscuit.skyblockaddons.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderEntityOutlineEvent
extends Event {
    private final Type type;
    private HashMap<Entity, Integer> entitiesToOutline = null;
    private HashSet<Entity> entitiesToChooseFrom;

    public RenderEntityOutlineEvent(Type theType, HashSet<Entity> potentialEntities) {
        this.type = theType;
        this.entitiesToChooseFrom = potentialEntities;
        if (potentialEntities != null) {
            this.entitiesToOutline = new HashMap(potentialEntities.size());
        }
    }

    public void queueEntitiesToOutline(Function<Entity, Integer> outlineColor) {
        if (outlineColor == null) {
            return;
        }
        if (this.entitiesToChooseFrom == null) {
            this.computeAndCacheEntitiesToChooseFrom();
        }
        Iterator<Entity> itr = this.entitiesToChooseFrom.iterator();
        while (itr.hasNext()) {
            Entity e = itr.next();
            Integer i = outlineColor.apply(e);
            if (i == null) continue;
            this.entitiesToOutline.put(e, i);
            itr.remove();
        }
    }

    public void queueEntityToOutline(Entity entity, int outlineColor) {
        if (entity == null) {
            return;
        }
        if (this.entitiesToChooseFrom == null) {
            this.computeAndCacheEntitiesToChooseFrom();
        }
        if (!this.entitiesToChooseFrom.contains(entity)) {
            return;
        }
        this.entitiesToOutline.put(entity, outlineColor);
        this.entitiesToChooseFrom.remove(entity);
    }

    private void computeAndCacheEntitiesToChooseFrom() {
        List entities = Minecraft.func_71410_x().field_71441_e.func_72910_y();
        this.entitiesToChooseFrom = new HashSet(entities.size());
        entities.forEach(e -> {
            if (!(e == null || e instanceof EntityArmorStand && e.func_82150_aj() || e instanceof EntityItemFrame)) {
                this.entitiesToChooseFrom.add((Entity)e);
            }
        });
        this.entitiesToOutline = new HashMap(this.entitiesToChooseFrom.size());
    }

    public Type getType() {
        return this.type;
    }

    public HashMap<Entity, Integer> getEntitiesToOutline() {
        return this.entitiesToOutline;
    }

    public HashSet<Entity> getEntitiesToChooseFrom() {
        return this.entitiesToChooseFrom;
    }

    public static class EntityAndOutlineColor {
        private final Entity entity;
        private final int color;

        public EntityAndOutlineColor(Entity theEntity, int theColor) {
            this.entity = theEntity;
            this.color = theColor;
        }

        public int hashCode() {
            return this.entity.hashCode();
        }

        public Entity getEntity() {
            return this.entity;
        }

        public int getColor() {
            return this.color;
        }
    }

    public static enum Type {
        XRAY,
        NO_XRAY;

    }
}

