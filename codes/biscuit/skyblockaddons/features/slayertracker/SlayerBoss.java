/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package codes.biscuit.skyblockaddons.features.slayertracker;

import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerDrop;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;

public enum SlayerBoss {
    REVENANT("Zombie", SlayerDrop.REVENANT_FLESH, SlayerDrop.FOUL_FLESH, SlayerDrop.PESTILENCE_RUNE, SlayerDrop.UNDEAD_CATALYST, SlayerDrop.SMITE_SIX, SlayerDrop.BEHEADED_HORROR, SlayerDrop.REVENANT_CATALYST, SlayerDrop.SNAKE_RUNE, SlayerDrop.SCYTHE_BLADE, SlayerDrop.SHARD_OF_SHREDDED, SlayerDrop.WARDEN_HEART),
    TARANTULA("Spider", SlayerDrop.TARANTULA_WEB, SlayerDrop.TOXIC_ARROW_POISON, SlayerDrop.SPIDER_CATALYST, SlayerDrop.BANE_OF_ARTHROPODS_SIX, SlayerDrop.BITE_RUNE, SlayerDrop.FLY_SWATTER, SlayerDrop.TARANTULA_TALISMAN, SlayerDrop.DIGESTED_MOSQUITO),
    SVEN("Wolf", SlayerDrop.WOLF_TOOTH, SlayerDrop.HAMSTER_WHEEL, SlayerDrop.SPIRIT_RUNE, SlayerDrop.CRITICAL_SIX, SlayerDrop.GRIZZLY_BAIT, SlayerDrop.RED_CLAW_EGG, SlayerDrop.OVERFLUX_CAPACITOR, SlayerDrop.COUTURE_RUNE),
    VOIDGLOOM("Enderman", SlayerDrop.NULL_SPHERE, SlayerDrop.TWILIGHT_ARROW_POISON, SlayerDrop.ENDERSNAKE_RUNE, SlayerDrop.SUMMONING_EYE, SlayerDrop.MANA_STEAL_ONE, SlayerDrop.TRANSMISSION_TUNER, SlayerDrop.NULL_ATOM, SlayerDrop.POCKET_ESPRESSO_MACHINE, SlayerDrop.SMARTY_PANTS_ONE, SlayerDrop.END_RUNE, SlayerDrop.HANDY_BLOOD_CHALICE, SlayerDrop.SINFUL_DICE, SlayerDrop.EXCEEDINGLY_RARE_ENDER_ARTIFACT_UPGRADER, SlayerDrop.VOID_CONQUEROR_ENDERMAN_SKIN, SlayerDrop.ETHERWARP_MERGER, SlayerDrop.JUDGEMENT_CORE, SlayerDrop.ENCHANT_RUNE, SlayerDrop.ENDER_SLAYER_SEVEN);

    private final List<SlayerDrop> drops;
    private final String mobType;

    private SlayerBoss(String mobType, SlayerDrop ... drops) {
        this.mobType = mobType;
        this.drops = Lists.newArrayList((Object[])drops);
    }

    public static SlayerBoss getFromMobType(String mobType) {
        for (SlayerBoss slayerBoss : SlayerBoss.values()) {
            if (!slayerBoss.mobType.equalsIgnoreCase(mobType)) continue;
            return slayerBoss;
        }
        return null;
    }

    public String getDisplayName() {
        return Translations.getMessage("slayerTracker." + this.name().toLowerCase(Locale.US), new Object[0]);
    }

    public List<SlayerDrop> getDrops() {
        return this.drops;
    }

    public String getMobType() {
        return this.mobType;
    }
}

