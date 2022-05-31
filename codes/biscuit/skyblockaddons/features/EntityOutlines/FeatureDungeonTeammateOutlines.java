/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Team$EnumVisible
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package codes.biscuit.skyblockaddons.features.EntityOutlines;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.events.RenderEntityOutlineEvent;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureDungeonTeammateOutlines {
    private static final Function<Entity, Integer> OUTLINE_COLOR = e -> {
        if (e instanceof EntityOtherPlayerMP) {
            String formattedName;
            ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)((EntityPlayer)e).func_96124_cp();
            if (scoreplayerteam != null && scoreplayerteam.func_178770_i() != Team.EnumVisible.NEVER && (formattedName = FontRenderer.func_78282_e((String)scoreplayerteam.func_96668_e())).length() >= 2) {
                return Minecraft.func_71410_x().field_71466_p.func_175064_b(formattedName.charAt(1));
            }
            return null;
        }
        return null;
    };

    private static boolean GLOBAL_TEST() {
        return SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.MAKE_DUNGEON_TEAMMATES_GLOW) && SkyblockAddons.getInstance().getUtils().isInDungeon();
    }

    @SubscribeEvent
    public void onRenderEntityOutlines(RenderEntityOutlineEvent e) {
        if (e.getType() == RenderEntityOutlineEvent.Type.XRAY && FeatureDungeonTeammateOutlines.GLOBAL_TEST()) {
            e.queueEntitiesToOutline(OUTLINE_COLOR);
        }
    }
}

