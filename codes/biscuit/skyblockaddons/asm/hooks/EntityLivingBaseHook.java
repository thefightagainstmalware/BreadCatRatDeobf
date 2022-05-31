/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.AxisAlignedBB
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.misc.scheduler.SkyblockRunnable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;

public class EntityLivingBaseHook {
    private static Set<Long> nightVisionEffectsToRemove = new HashSet<Long>();

    public static void onResetHurtTime(EntityLivingBase entity) {
        if (entity == Minecraft.func_71410_x().field_71439_g) {
            SkyblockAddons main = SkyblockAddons.getInstance();
            if (!main.getUtils().isOnSkyblock() || !main.getConfigValues().isEnabled(Feature.COMBAT_TIMER_DISPLAY)) {
                return;
            }
            Minecraft mc = Minecraft.func_71410_x();
            List nearEntities = mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(mc.field_71439_g.field_70165_t - 3.0, mc.field_71439_g.field_70163_u - 2.0, mc.field_71439_g.field_70161_v - 3.0, mc.field_71439_g.field_70165_t + 3.0, mc.field_71439_g.field_70163_u + 2.0, mc.field_71439_g.field_70161_v + 3.0));
            boolean foundPossibleAttacker = false;
            for (Entity nearEntity : nearEntities) {
                if (!(nearEntity instanceof EntityMob) && !(nearEntity instanceof EntityWolf) && !(nearEntity instanceof IProjectile)) continue;
                foundPossibleAttacker = true;
                break;
            }
            if (foundPossibleAttacker) {
                SkyblockAddons.getInstance().getUtils().setLastDamaged(System.currentTimeMillis());
            }
        }
    }

    public static boolean onRemovePotionEffect(final EntityLivingBase entityLivingBase, final int potionID) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (potionID == 16 && entityLivingBase == Minecraft.func_71410_x().field_71439_g && main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.AVOID_BLINKING_NIGHT_VISION)) {
            final long now = System.currentTimeMillis();
            nightVisionEffectsToRemove.add(now);
            main.getNewScheduler().scheduleDelayedTask(new SkyblockRunnable(){

                @Override
                public void run() {
                    if (nightVisionEffectsToRemove.remove(now)) {
                        entityLivingBase.func_82170_o(potionID);
                    }
                }
            }, 2);
            return true;
        }
        return false;
    }

    public static void onAddPotionEffect(EntityLivingBase entityLivingBase, PotionEffect potionEffect) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (potionEffect.func_76456_a() == 16 && potionEffect.func_76459_b() == 300 && entityLivingBase == Minecraft.func_71410_x().field_71439_g && main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.AVOID_BLINKING_NIGHT_VISION)) {
            nightVisionEffectsToRemove.clear();
        }
    }
}

