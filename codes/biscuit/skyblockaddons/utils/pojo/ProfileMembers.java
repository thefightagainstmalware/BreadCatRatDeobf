/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.utils.pojo;

import java.util.HashMap;

public class ProfileMembers {
    private HashMap<String, MemberData> members;

    public HashMap<String, MemberData> getMembers() {
        return this.members;
    }

    public static class PetMilestones {
        private int ore_mined;
        private int sea_creatures_killed;

        public int getOre_mined() {
            return this.ore_mined;
        }

        public int getSea_creatures_killed() {
            return this.sea_creatures_killed;
        }
    }

    public static class Stats {
        private PetMilestones pet_milestones;

        public PetMilestones getPet_milestones() {
            return this.pet_milestones;
        }
    }

    public static class SlayerData {
        private HashMap<Integer, Integer> kills_tier;

        public HashMap<Integer, Integer> getKills_tier() {
            return this.kills_tier;
        }
    }

    public static class Slayers {
        private SlayerData zombie;
        private SlayerData spider;
        private SlayerData wolf;
        private SlayerData enderman;

        public SlayerData getZombie() {
            return this.zombie;
        }

        public SlayerData getSpider() {
            return this.spider;
        }

        public SlayerData getWolf() {
            return this.wolf;
        }

        public SlayerData getEnderman() {
            return this.enderman;
        }
    }

    public static class MemberData {
        private Slayers slayer;
        private Stats stats;

        public Slayers getSlayer() {
            return this.slayer;
        }

        public Stats getStats() {
            return this.stats;
        }
    }
}

