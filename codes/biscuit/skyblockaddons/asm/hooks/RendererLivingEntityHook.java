/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EnumPlayerModelParts
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonPlayer;
import codes.biscuit.skyblockaddons.features.EntityOutlines.EntityOutlineRenderer;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class RendererLivingEntityHook {
    private static final Set<String> coolPeople = Sets.newHashSet((Object[])new String[]{"Dinnerbone", "Biscut", "Pinpointed", "Berded", "Potat_owo", "Pnda__", "StopUsingSBE"});
    private static boolean isCoolPerson;

    public static boolean equals(String string, Object otherString) {
        isCoolPerson = coolPeople.contains(string);
        return isCoolPerson;
    }

    public static boolean isWearing(EntityPlayer entityPlayer, EnumPlayerModelParts p_175148_1_) {
        return !isCoolPerson && entityPlayer.func_175148_a(p_175148_1_) || isCoolPerson && !entityPlayer.func_175148_a(p_175148_1_);
    }

    public static int setOutlineColor(EntityLivingBase entity, int originalColor) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isEnabled(Feature.SHOW_CRITICAL_DUNGEONS_TEAMMATES) && main.getUtils().isInDungeon() && main.getDungeonManager().getTeammates().containsKey(entity.func_70005_c_())) {
            DungeonPlayer dungeonPlayer = main.getDungeonManager().getTeammates().get(entity.func_70005_c_());
            if (dungeonPlayer.isCritical()) {
                return Minecraft.func_71410_x().field_71466_p.func_175064_b('c');
            }
            if (dungeonPlayer.isLow()) {
                return Minecraft.func_71410_x().field_71466_p.func_175064_b('e');
            }
        } else {
            Integer i = EntityOutlineRenderer.getCustomOutlineColor(entity);
            if (i != null) {
                return i;
            }
        }
        return originalColor;
    }
}

