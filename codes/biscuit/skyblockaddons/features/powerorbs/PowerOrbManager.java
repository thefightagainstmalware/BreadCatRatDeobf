/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.world.World
 */
package codes.biscuit.skyblockaddons.features.powerorbs;

import codes.biscuit.skyblockaddons.features.powerorbs.PowerOrb;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class PowerOrbManager {
    private static final Pattern POWER_ORB_PATTERN = Pattern.compile("[A-Za-z ]* (?<seconds>[0-9]*)s");
    private static final PowerOrbManager instance = new PowerOrbManager();
    public static final PowerOrbEntry DUMMY_POWER_ORB_ENTRY = new PowerOrbEntry(PowerOrb.RADIANT, 20, null);
    private Map<PowerOrb, PowerOrbEntry> powerOrbEntryMap = new HashMap<PowerOrb, PowerOrbEntry>();

    private void put(PowerOrb powerOrb, int seconds, UUID uuid) {
        this.powerOrbEntryMap.put(powerOrb, new PowerOrbEntry(powerOrb, seconds, uuid));
    }

    public PowerOrbEntry getActivePowerOrb() {
        Optional max = this.powerOrbEntryMap.entrySet().stream().filter(powerOrbEntryEntry -> ((PowerOrbEntry)powerOrbEntryEntry.getValue()).timestamp + 100L > System.currentTimeMillis()).max(Map.Entry.comparingByKey());
        return max.map(Map.Entry::getValue).orElse(null);
    }

    public void detectPowerOrb(Entity entity) {
        Matcher matcher;
        String customNameTag = entity.func_95999_t();
        PowerOrb powerOrb = PowerOrb.getByDisplayname(customNameTag);
        if (powerOrb != null && powerOrb.isInRadius(entity.func_70068_e((Entity)Minecraft.func_71410_x().field_71439_g)) && (matcher = POWER_ORB_PATTERN.matcher(TextUtils.stripColor(customNameTag))).matches()) {
            int seconds;
            try {
                seconds = Integer.parseInt(matcher.group("seconds"));
            }
            catch (NumberFormatException ex) {
                return;
            }
            List surroundingArmorStands = Minecraft.func_71410_x().field_71441_e.func_72872_a(EntityArmorStand.class, new AxisAlignedBB(entity.field_70165_t - 0.1, entity.field_70163_u - 3.0, entity.field_70161_v - 0.1, entity.field_70165_t + 0.1, entity.field_70163_u, entity.field_70161_v + 0.1));
            if (!surroundingArmorStands.isEmpty()) {
                EntityArmorStand orbArmorStand = null;
                for (EntityArmorStand surroundingArmorStand : surroundingArmorStands) {
                    ItemStack helmet = surroundingArmorStand.func_82169_q(3);
                    if (helmet == null) continue;
                    orbArmorStand = surroundingArmorStand;
                }
                this.put(powerOrb, seconds, orbArmorStand == null ? null : orbArmorStand.func_110124_au());
            }
        }
    }

    public EntityArmorStand createVirtualArmorStand(EntityArmorStand armorStandToClone) {
        EntityArmorStand virtualArmorStand = new EntityArmorStand((World)Utils.getDummyWorld());
        virtualArmorStand.func_70062_b(4, armorStandToClone.func_71124_b(4));
        return virtualArmorStand;
    }

    public static PowerOrbManager getInstance() {
        return instance;
    }

    public static class PowerOrbEntry {
        private final PowerOrb powerOrb;
        private final int seconds;
        private final long timestamp = System.currentTimeMillis();
        private final UUID uuid;

        public PowerOrb getPowerOrb() {
            return this.powerOrb;
        }

        public int getSeconds() {
            return this.seconds;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public UUID getUuid() {
            return this.uuid;
        }

        public PowerOrbEntry(PowerOrb powerOrb, int seconds, UUID uuid) {
            this.powerOrb = powerOrb;
            this.seconds = seconds;
            this.uuid = uuid;
        }
    }
}

