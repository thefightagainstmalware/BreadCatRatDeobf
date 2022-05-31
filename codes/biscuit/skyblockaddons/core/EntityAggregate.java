/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 */
package codes.biscuit.skyblockaddons.core;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;

public class EntityAggregate {
    private List<UUID> entities;

    public EntityAggregate(UUID ... entities) {
        this.entities = Lists.newArrayList((Object[])entities);
    }

    public boolean isDead() {
        WorldClient world = Minecraft.func_71410_x().field_71441_e;
        if (world == null) {
            return true;
        }
        for (UUID uuid : this.entities) {
            Entity entity = this.getEntityFromUUID(uuid);
            if (entity == null || entity.field_70128_L) continue;
            return false;
        }
        return true;
    }

    private Entity getEntityFromUUID(UUID uuid) {
        for (Entity entity : Minecraft.func_71410_x().field_71441_e.func_72910_y()) {
            if (!entity.func_110124_au().equals(uuid)) continue;
            return entity;
        }
        return null;
    }

    public List<UUID> getEntities() {
        return this.entities;
    }
}

