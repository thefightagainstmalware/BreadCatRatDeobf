/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package codes.biscuit.skyblockaddons.features.discordrpc;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Attribute;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.SkyblockDate;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSelect;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public enum DiscordStatus implements ButtonSelect.SelectItem
{
    NONE(Message.DISCORD_STATUS_NONE_TITLE, Message.DISCORD_STATUS_NONE_DESCRIPTION, () -> null),
    LOCATION(Message.DISCORD_STATUS_LOCATION_TITLE, Message.DISCORD_STATUS_LOCATION_DESCRIPTION, () -> SkyblockAddons.getInstance().getUtils().getLocation().getScoreboardName()),
    PURSE(Message.DISCORD_STATUS_PURSE_TITLE, Message.DISCORD_STATUS_PURSE_DESCRIPTION, () -> String.format("%s Coins", TextUtils.formatDouble(SkyblockAddons.getInstance().getUtils().getPurse()))),
    STATS(Message.DISCORD_STATUS_STATS_TITLE, Message.DISCORD_STATUS_STATS_DESCRIPTION, () -> {
        int health = SkyblockAddons.getInstance().getUtils().getAttributes().get((Object)Attribute.HEALTH).getValue();
        int defense = SkyblockAddons.getInstance().getUtils().getAttributes().get((Object)Attribute.DEFENCE).getValue();
        int mana = SkyblockAddons.getInstance().getUtils().getAttributes().get((Object)Attribute.MANA).getValue();
        return String.format("%d H - %d D - %d M", health, defense, mana);
    }),
    ZEALOTS(Message.DISCORD_STATUS_ZEALOTS_TITLE, Message.DISCORD_STATUS_ZEALOTS_DESCRIPTION, () -> String.format("%d Zealots killed", SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getKills())),
    ITEM(Message.DISCORD_STATUS_ITEM_TITLE, Message.DISCORD_STATUS_ITEM_DESCRIPTION, () -> {
        EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        if (player != null && player.func_70694_bm() != null) {
            return String.format("Holding %s", TextUtils.stripColor(player.func_70694_bm().func_82833_r()));
        }
        return "No item in hand";
    }),
    TIME(Message.DISCORD_STATUS_TIME_TITLE, Message.DISCORD_STATUS_TIME_DESCRIPTION, () -> {
        SkyblockDate date = SkyblockAddons.getInstance().getUtils().getCurrentDate();
        return date != null ? date.toString() : "";
    }),
    PROFILE(Message.DISCORD_STATUS_PROFILE_TITLE, Message.DISCORD_STATUS_PROFILE_DESCRIPTION, () -> {
        String profile = SkyblockAddons.getInstance().getUtils().getProfileName();
        return String.format("Profile: %s", profile == null ? "None" : profile);
    }),
    CUSTOM(Message.DISCORD_STATUS_CUSTOM, Message.DISCORD_STATUS_CUSTOM_DESCRIPTION, () -> {
        SkyblockAddons main = SkyblockAddons.getInstance();
        String text = main.getConfigValues().getCustomStatus(main.getDiscordRPCManager().getCurrentEntry());
        return text.substring(0, Math.min(text.length(), 100));
    }),
    AUTO_STATUS(Message.DISCORD_STATUS_AUTO, Message.DISCORD_STATUS_AUTO_DESCRIPTION, () -> {
        SkyblockAddons main = SkyblockAddons.getInstance();
        Location location = main.getUtils().getLocation();
        if (location == Location.THE_END || location == Location.DRAGONS_NEST) {
            return DiscordStatus.ZEALOTS.displayMessageSupplier.get();
        }
        EnumUtils.SlayerQuest slayerQuest = main.getUtils().getSlayerQuest();
        if (slayerQuest != null) {
            if (slayerQuest == EnumUtils.SlayerQuest.REVENANT_HORROR) {
                return DiscordStatus.valueOf((String)"REVENANT").displayMessageSupplier.get();
            }
            if (slayerQuest == EnumUtils.SlayerQuest.SVEN_PACKMASTER) {
                return DiscordStatus.valueOf((String)"SVEN").displayMessageSupplier.get();
            }
            if (slayerQuest == EnumUtils.SlayerQuest.TARANTULA_BROODFATHER) {
                return DiscordStatus.valueOf((String)"TARANTULA").displayMessageSupplier.get();
            }
        }
        if ("AUTO_STATUS".equals(main.getConfigValues().getDiscordAutoDefault().name())) {
            main.getConfigValues().setDiscordAutoDefault(NONE);
        }
        return main.getConfigValues().getDiscordAutoDefault().displayMessageSupplier.get();
    }),
    REVENANT(Message.DISCORD_STATUS_REVENANT, Message.DISCORD_STATUS_REVENANT_DESCRIPTION, () -> {
        SkyblockAddons main = SkyblockAddons.getInstance();
        boolean bossAlive = main.getUtils().isSlayerBossAlive();
        if (bossAlive) {
            return "Slaying a Revenant Horror " + main.getUtils().getSlayerQuestLevel() + " boss.";
        }
        return "Doing a Revenant Horror " + main.getUtils().getSlayerQuestLevel() + " quest.";
    }),
    SVEN(Message.DISCORD_STATUS_SVEN, Message.DISCORD_STATUS_SVEN_DESCRIPTION, () -> {
        SkyblockAddons main = SkyblockAddons.getInstance();
        boolean bossAlive = main.getUtils().isSlayerBossAlive();
        if (bossAlive) {
            return "Slaying a Sven Packmaster " + main.getUtils().getSlayerQuestLevel() + " boss.";
        }
        return "Doing a Sven Packmaster " + main.getUtils().getSlayerQuestLevel() + " quest.";
    }),
    TARANTULA(Message.DISCORD_STATUS_TARANTULA, Message.DISCORD_STATUS_TARANTULA_DESCRIPTION, () -> {
        SkyblockAddons main = SkyblockAddons.getInstance();
        boolean bossAlive = main.getUtils().isSlayerBossAlive();
        if (bossAlive) {
            return "Slaying a Tarantula Broodfather  " + main.getUtils().getSlayerQuestLevel() + " boss.";
        }
        return "Doing a Tarantula Broodfather " + main.getUtils().getSlayerQuestLevel() + " quest.";
    });

    private final Message title;
    private final Message description;
    private final Supplier<String> displayMessageSupplier;

    private DiscordStatus(Message title, Message description, Supplier<String> displayMessageSupplier) {
        this.title = title;
        this.description = description;
        this.displayMessageSupplier = displayMessageSupplier;
    }

    public String getDisplayString(EnumUtils.DiscordStatusEntry currentEntry) {
        SkyblockAddons.getInstance().getDiscordRPCManager().setCurrentEntry(currentEntry);
        return this.displayMessageSupplier.get();
    }

    @Override
    public String getName() {
        return this.title.getMessage(new String[0]);
    }

    @Override
    public String getDescription() {
        return this.description.getMessage(new String[0]);
    }
}

