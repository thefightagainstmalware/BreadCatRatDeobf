/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package codes.biscuit.skyblockaddons.misc.scheduler;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.listeners.PlayerListener;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.objects.IntPair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.mutable.MutableInt;

public class Scheduler {
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private long totalTicks = 0L;
    private final Map<Long, Set<Command>> queue = new HashMap<Long, Set<Command>>();
    private boolean delayingMagmaCall = false;

    public void schedule(CommandType commandType, int delaySeconds, Object ... data) {
        if (delaySeconds <= 0) {
            throw new IllegalArgumentException("Delay must be greater than zero!");
        }
        long ticks = this.totalTicks + (long)(delaySeconds * 20);
        Set<Command> commands = this.queue.get(ticks);
        if (commands != null) {
            for (Command command : commands) {
                if (command.getCommandType() != commandType) continue;
                command.addCount(data);
                return;
            }
            commands.add(new Command(commandType, data));
        } else {
            HashSet<Command> commandSet = new HashSet<Command>();
            commandSet.add(new Command(commandType, data));
            this.queue.put(ticks, commandSet);
        }
    }

    public void removeQueuedFullInventoryWarnings() {
        Iterator<Map.Entry<Long, Set<Command>>> queueIterator = this.queue.entrySet().iterator();
        LinkedList<Long> resetTitleFeatureTicks = new LinkedList<Long>();
        block0: while (queueIterator.hasNext()) {
            Map.Entry<Long, Set<Command>> entry = queueIterator.next();
            if (entry.getValue().removeIf(command -> CommandType.SHOW_FULL_INVENTORY_WARNING.equals((Object)((Command)command).commandType))) {
                resetTitleFeatureTicks.add(entry.getKey() + (long)(this.main.getConfigValues().getWarningSeconds() * 20));
            }
            if (!resetTitleFeatureTicks.contains(entry.getKey())) continue;
            Set<Command> commands = entry.getValue();
            Iterator<Command> commandIterator = commands.iterator();
            while (commandIterator.hasNext()) {
                Command command2 = commandIterator.next();
                if (!command2.commandType.equals((Object)CommandType.RESET_TITLE_FEATURE)) continue;
                commandIterator.remove();
                continue block0;
            }
        }
    }

    @SubscribeEvent
    public void ticker(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            ++this.totalTicks;
            Set<Command> commands = this.queue.get(this.totalTicks);
            if (commands != null) {
                for (Command command : commands) {
                    for (int times = 0; times < command.getCount().getValue(); ++times) {
                        command.getCommandType().execute(command, times + 1);
                    }
                }
                this.queue.remove(this.totalTicks);
            }
            if ((this.totalTicks % 12000L == 0L || this.delayingMagmaCall) && this.main.getPlayerListener().getMagmaAccuracy() != EnumUtils.MagmaTimerAccuracy.EXACTLY) {
                if (this.main.getUtils().isOnSkyblock()) {
                    this.delayingMagmaCall = false;
                    this.main.getUtils().fetchMagmaBossEstimate();
                } else if (!this.delayingMagmaCall) {
                    this.delayingMagmaCall = true;
                }
            }
        }
    }

    public static enum CommandType {
        RESET_MAGMA_PREDICTION,
        SUBTRACT_MAGMA_COUNT,
        SUBTRACT_BLAZE_COUNT,
        RESET_TITLE_FEATURE,
        RESET_SUBTITLE_FEATURE,
        ERASE_UPDATE_MESSAGE,
        DELETE_RECENT_CHUNK,
        SHOW_FULL_INVENTORY_WARNING,
        CHECK_FOR_UPDATE;


        public void execute(Command command, int count) {
            SkyblockAddons main = SkyblockAddons.getInstance();
            PlayerListener playerListener = main.getPlayerListener();
            Object[] commandData = command.getData(count);
            if (this == SUBTRACT_MAGMA_COUNT) {
                playerListener.setRecentMagmaCubes(playerListener.getRecentMagmaCubes() - 1);
            } else if (this == SUBTRACT_BLAZE_COUNT) {
                playerListener.setRecentBlazes(playerListener.getRecentBlazes() - 1);
            } else if (this == RESET_MAGMA_PREDICTION) {
                if (playerListener.getMagmaAccuracy() == EnumUtils.MagmaTimerAccuracy.SPAWNED_PREDICTION) {
                    playerListener.setMagmaAccuracy(EnumUtils.MagmaTimerAccuracy.ABOUT);
                    playerListener.setMagmaTime(7200);
                }
            } else if (this == DELETE_RECENT_CHUNK) {
                int x = (Integer)commandData[0];
                int z = (Integer)commandData[1];
                IntPair intPair = new IntPair(x, z);
                playerListener.getRecentlyLoadedChunks().remove(intPair);
            } else if (this == SHOW_FULL_INVENTORY_WARNING) {
                Minecraft mc = Minecraft.func_71410_x();
                if (mc.field_71441_e == null || mc.field_71439_g == null || !main.getUtils().isOnSkyblock()) {
                    return;
                }
                main.getInventoryUtils().showFullInventoryWarning();
                if (main.getConfigValues().isEnabled(Feature.REPEAT_FULL_INVENTORY_WARNING)) {
                    main.getScheduler().schedule(SHOW_FULL_INVENTORY_WARNING, 10, new Object[0]);
                    main.getScheduler().schedule(RESET_TITLE_FEATURE, 10 + main.getConfigValues().getWarningSeconds(), new Object[0]);
                }
            } else if (this == RESET_TITLE_FEATURE) {
                main.getRenderListener().setTitleFeature(null);
            } else if (this == RESET_SUBTITLE_FEATURE) {
                main.getRenderListener().setSubtitleFeature(null);
            } else if (this == ERASE_UPDATE_MESSAGE) {
                main.getRenderListener().setUpdateMessageDisplayed(true);
            } else if (this == CHECK_FOR_UPDATE) {
                main.getUpdater().checkForUpdate();
            }
        }
    }

    private class Command {
        private final CommandType commandType;
        private final MutableInt count = new MutableInt(1);
        private final Map<Integer, Object[]> countData = new HashMap<Integer, Object[]>();

        private Command(CommandType commandType, Object ... data) {
            this.commandType = commandType;
            if (data.length > 0) {
                this.countData.put(1, data);
            }
        }

        private void addCount(Object ... data) {
            this.count.increment();
            if (data.length > 0) {
                this.countData.put(this.count.getValue(), data);
            }
        }

        Object[] getData(int count) {
            return this.countData.get(count);
        }

        public CommandType getCommandType() {
            return this.commandType;
        }

        public MutableInt getCount() {
            return this.count;
        }

        public Map<Integer, Object[]> getCountData() {
            return this.countData;
        }
    }
}

