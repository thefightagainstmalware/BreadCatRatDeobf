/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.core.Location;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationUtils {
    private static final List<Location> dwarvenLocations = new ArrayList<Location>(Arrays.asList(Location.DWARVEN_MINES, Location.DWARVEN_VILLAGE, Location.GATES_TO_THE_MINES, Location.THE_LIFT, Location.THE_FORGE, Location.FORGE_BASIN, Location.LAVA_SPRINGS, Location.PALACE_BRIDGE, Location.ROYAL_PALACE, Location.ARISTOCRAT_PASSAGE, Location.HANGING_TERRACE, Location.CLIFFSIDE_VEINS, Location.RAMPARTS_QUARRY, Location.DIVANS_GATEWAY, Location.FAR_RESERVE, Location.GOBLIN_BURROWS, Location.UPPER_MINES, Location.ROYAL_MINES, Location.MINERS_GUILD, Location.GREAT_ICE_WALL, Location.THE_MIST, Location.CC_MINECARTS_CO, Location.GRAND_LIBRARY, Location.HANGING_COURT));

    public static boolean isInDwarvenMines(String locationName) {
        for (Location location : dwarvenLocations) {
            if (!location.getScoreboardName().equals(locationName)) continue;
            return true;
        }
        return false;
    }
}

