/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package codes.biscuit.skyblockaddons.core;

import com.google.common.collect.Sets;
import java.util.HashSet;

public enum Location {
    ISLAND("Your Island"),
    GUEST_ISLAND("'s Island", "island"),
    AUCTION_HOUSE("Auction House"),
    BANK("Bank"),
    BAZAAR("Bazaar Alley"),
    CANVAS_ROOM("Canvas Room"),
    COAL_MINE("Coal Mine"),
    COLOSSEUM("Colosseum"),
    COLOSSEUM_ARENA("Colosseum Arena", "colosseum"),
    DUEL_ZONE("Duel Zone", "colosseum"),
    ELECTION_ROOM("Election Room"),
    FARM("Farm"),
    FASHION_SHOP("Fashion Shop"),
    FISHERMANS_HUT("Fisherman's Hut"),
    FLOWER_HOUSE("Flower House"),
    FOREST("Forest"),
    GRAVEYARD("Graveyard"),
    HIGH_LEVEL("High Level"),
    LIBRARY("Library"),
    MOUNTAIN("Mountain"),
    RUINS("Ruins"),
    TAVERN("Tavern"),
    VILLAGE("Village"),
    WILDERNESS("Wilderness"),
    WIZARD_TOWER("Wizard Tower"),
    CATACOMBS_ENTRANCE("Catacombs Entrance"),
    BIRCH_PARK("Birch Park"),
    SPRUCE_WOODS("Spruce Woods"),
    SAVANNA_WOODLAND("Savanna Woodland"),
    DARK_THICKET("Dark Thicket"),
    JUNGLE_ISLAND("Jungle Island"),
    HOWLING_CAVE("Howling Cave"),
    GOLD_MINE("Gold Mine"),
    DEEP_CAVERNS("Deep Caverns"),
    GUNPOWDER_MINES("Gunpowder Mines"),
    LAPIS_QUARRY("Lapis Quarry"),
    PIGMAN_DEN("Pigmen's Den"),
    SLIMEHILL("Slimehill"),
    DIAMOND_RESERVE("Diamond Reserve"),
    OBSIDIAN_SANCTUARY("Obsidian Sanctuary"),
    DWARVEN_MINES("Dwarven Mines"),
    DWARVEN_VILLAGE("Dwarven Village"),
    GATES_TO_THE_MINES("Gates to the Mines"),
    THE_LIFT("The Lift"),
    THE_FORGE("The Forge"),
    FORGE_BASIN("Forge Basin"),
    LAVA_SPRINGS("Lava Springs"),
    PALACE_BRIDGE("Palace Bridge"),
    ROYAL_PALACE("Royal Palace"),
    ARISTOCRAT_PASSAGE("Aristocrat Passage"),
    HANGING_TERRACE("Hanging Terrace"),
    CLIFFSIDE_VEINS("Cliffside Veins"),
    RAMPARTS_QUARRY("Rampart's Quarry"),
    DIVANS_GATEWAY("Divan's Gateway"),
    FAR_RESERVE("Far Reserve"),
    GOBLIN_BURROWS("Goblin Burrows"),
    UPPER_MINES("Upper Mines"),
    ROYAL_MINES("Royal Mines"),
    MINERS_GUILD("Miner's Guild"),
    GREAT_ICE_WALL("Great Ice Wall"),
    THE_MIST("The Mist"),
    CC_MINECARTS_CO("C&C Minecarts Co."),
    GRAND_LIBRARY("Grand Library"),
    HANGING_COURT("Hanging Court"),
    CRYSTAL_HOLLOWS("Crystal Hollows"),
    MAGMA_FIELDS("Magma Fields"),
    JUNGLE("Jungle"),
    MITHRIL_DEPOSITS("Mithril Deposits"),
    GOBLIN_HOLDOUT("Goblin Holdout"),
    PRECURSOR_REMNANT("Precursor Remnant"),
    FAIRY_GROTTO("Fairy Grotto"),
    THE_BARN("The Barn"),
    MUSHROOM_DESERT("Mushroom Desert"),
    DESERT_SETTLEMENT("Desert Settlement"),
    TREASURE_HUNTER_CAMP("Treasure Hunter Camp"),
    OASIS("Oasis"),
    MUSHROOM_GORGE("Mushroom Gorge"),
    GLOWING_MUSHROOM_CAVE("Glowing Mushroom Cave"),
    OVERGROWN_MUSHROOM_CAVE("Overgrown Mushroom Cave"),
    JAKES_HOUSE("Jake's House"),
    SHEPHERDS_KEEP("Shepherds Keep"),
    TRAPPERS_DEN("Trappers Den"),
    SPIDERS_DEN("Spider's Den"),
    BLAZING_FORTRESS("Blazing Fortress"),
    THE_END("The End"),
    DRAGONS_NEST("Dragon's Nest"),
    VOID_SEPULTURE("Void Sepulture"),
    JERRY_POND("Jerry Pond"),
    JERRYS_WORKSHOP("Jerry's Workshop"),
    THE_CATACOMBS("The Catacombs"),
    DUNGEON_HUB("Dungeon Hub"),
    NONE("None"),
    UNKNOWN("Unknown");

    private final String scoreboardName;
    private final String discordIconKey;

    private Location(String scoreboardName, String discordIconKey) {
        this.scoreboardName = scoreboardName;
        this.discordIconKey = discordIconKey;
    }

    private Location(String scoreboardName) {
        this.scoreboardName = scoreboardName;
        HashSet NO_DISCORD_RPC = Sets.newHashSet((Object[])new String[]{"ISLAND", "BAZAAR", "DEEP_CAVERNS", "GUNPOWDER_MINES", "LAPIS_QUARRY", "PIGMAN_DEN", "JERRYS_WORKSHOP", "JERRY_POND", "DWARVEN_MINES", "DWARVEN_VILLAGE", "GATES_TO_THE_MINES", "THE_LIFT", "THE_FORGE", "FORGE_BASIN", "LAVA_SPRINGS", "PALACE_BRIDGE", "ROYAL_PALACE", "ARISTOCRAT_PASSAGE", "HANGING_TERRACE", "CLIFFSIDE_VEINS", "RAMPARTS_QUARRY", "DIVANS_GATEWAY", "FAR_RESERVE", "GOBLIN_BURROWs", "UPPER_MINES", "MINERS_GUILD", "GREAT_ICE_WALL", "THE_MIST", "CC_MINECARTS_CO", "GRAND_LIBRARY", "HANGING_COURT", "ROYAL_MINES", "DESERT_SETTLEMENT", "TREASURE_HUNTER_CAMP", "OASIS", "MUSHROOM_GORGE", "GLOWING_MUSHROOM_CAVE", "OVERGROWN_MUSHROOM_CAVE", "JAKES_HOUSE", "SHEPHERDS_KEEP", "TRAPPERS_DEN"});
        this.discordIconKey = NO_DISCORD_RPC.contains(this.name()) ? "skyblock" : this.name().toLowerCase().replace("_", "-");
    }

    public String getScoreboardName() {
        return this.scoreboardName;
    }

    public String getDiscordIconKey() {
        return this.discordIconKey;
    }
}

