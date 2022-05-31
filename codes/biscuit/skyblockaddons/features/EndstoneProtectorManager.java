/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.EntityIronGolem
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraft.world.chunk.Chunk
 */
package codes.biscuit.skyblockaddons.features;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class EndstoneProtectorManager {
    private static boolean canDetectSkull = false;
    private static Stage minibossStage = null;
    private static int zealotCount = 0;
    private static long lastWaveStart = -1L;

    public static void checkGolemStatus() {
        Minecraft mc = Minecraft.func_71410_x();
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (mc.field_71441_e != null && (main.getUtils().getLocation() == Location.THE_END || main.getUtils().getLocation() == Location.DRAGONS_NEST) && main.getConfigValues().isEnabled(Feature.ENDSTONE_PROTECTOR_DISPLAY)) {
            WorldClient world = mc.field_71441_e;
            Chunk chunk = world.func_175726_f(new BlockPos(-689, 5, -273));
            if (chunk == null || !chunk.func_177410_o()) {
                canDetectSkull = false;
                return;
            }
            Stage newStage = Stage.detectStage();
            for (Entity entity : world.field_72996_f) {
                if (!(entity instanceof EntityIronGolem)) continue;
                newStage = Stage.GOLEM_ALIVE;
                break;
            }
            canDetectSkull = true;
            if (minibossStage != newStage) {
                int timeTaken = (int)(System.currentTimeMillis() - lastWaveStart);
                String previousStage = minibossStage == null ? "null" : minibossStage.name();
                String zealotsKilled = "N/A";
                if (minibossStage != null) {
                    zealotsKilled = String.valueOf(zealotCount);
                }
                int totalSeconds = timeTaken / 1000;
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;
                SkyblockAddons.getLogger().info("Endstone protector stage updated from " + previousStage + " to " + newStage.name() + ". Your zealot kill count was " + zealotsKilled + ". This took " + minutes + "m " + seconds + "s.");
                if (minibossStage == Stage.GOLEM_ALIVE && newStage == Stage.NO_HEAD) {
                    zealotCount = 0;
                }
                minibossStage = newStage;
                lastWaveStart = System.currentTimeMillis();
            }
        } else {
            canDetectSkull = false;
        }
    }

    public static void onKill() {
        ++zealotCount;
    }

    public static void reset() {
        minibossStage = null;
        zealotCount = 0;
        canDetectSkull = false;
    }

    public static boolean isCanDetectSkull() {
        return canDetectSkull;
    }

    public static Stage getMinibossStage() {
        return minibossStage;
    }

    public static int getZealotCount() {
        return zealotCount;
    }

    public static enum Stage {
        NO_HEAD(-1),
        STAGE_1(0),
        STAGE_2(1),
        STAGE_3(2),
        STAGE_4(3),
        STAGE_5(4),
        GOLEM_ALIVE(-1);

        private int blocksUp;
        private static Stage lastStage;
        private static BlockPos lastPos;
        private static final ExecutorService EXECUTOR;

        private Stage(int blocksUp) {
            this.blocksUp = blocksUp;
        }

        public static Stage detectStage() {
            EXECUTOR.submit(() -> {
                try {
                    WorldClient world = Minecraft.func_71410_x().field_71441_e;
                    if (lastStage != null && lastPos != null && Blocks.field_150465_bP == world.func_180495_p(lastPos).func_177230_c()) {
                        return;
                    }
                    for (Stage stage : Stage.values()) {
                        if (stage.blocksUp == -1) continue;
                        for (int x = -749; x < -602; ++x) {
                            for (int z = -353; z < -202; ++z) {
                                BlockPos blockPos = new BlockPos(x, 5 + stage.blocksUp, z);
                                if (!Blocks.field_150465_bP.equals(world.func_180495_p(blockPos).func_177230_c())) continue;
                                lastStage = stage;
                                lastPos = blockPos;
                                return;
                            }
                        }
                    }
                    lastStage = NO_HEAD;
                    lastPos = null;
                }
                catch (Throwable ex) {
                    ex.printStackTrace();
                }
            });
            return lastStage;
        }

        static {
            lastStage = null;
            lastPos = null;
            EXECUTOR = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("SkyblockAddons - Endstone Protector #%d").build());
        }
    }
}

