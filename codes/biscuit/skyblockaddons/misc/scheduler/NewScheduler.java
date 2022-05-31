/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 */
package codes.biscuit.skyblockaddons.misc.scheduler;

import codes.biscuit.skyblockaddons.misc.scheduler.ScheduledTask;
import codes.biscuit.skyblockaddons.misc.scheduler.SkyblockRunnable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NewScheduler {
    private final List<ScheduledTask> queuedTasks = new ArrayList<ScheduledTask>();
    private final List<ScheduledTask> pendingTasks = new ArrayList<ScheduledTask>();
    private final Object anchor = new Object();
    private volatile long totalTicks = 0L;

    public synchronized long getTotalTicks() {
        return this.totalTicks;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SubscribeEvent
    public void ticker(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Iterator<ScheduledTask> iterator = this.anchor;
            synchronized (iterator) {
                ++this.totalTicks;
            }
            if (Minecraft.func_71410_x() != null) {
                this.pendingTasks.removeIf(ScheduledTask::isCanceled);
                this.pendingTasks.addAll(this.queuedTasks);
                this.queuedTasks.clear();
                try {
                    for (ScheduledTask scheduledTask : this.pendingTasks) {
                        if (this.getTotalTicks() < scheduledTask.getAddedTicks() + (long)scheduledTask.getDelay()) continue;
                        scheduledTask.start();
                        if (scheduledTask.isRepeating()) {
                            if (scheduledTask.isCanceled()) continue;
                            scheduledTask.setDelay(scheduledTask.getPeriod());
                            continue;
                        }
                        scheduledTask.cancel();
                    }
                }
                catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public synchronized void cancel(int id) {
        this.pendingTasks.forEach(scheduledTask -> {
            if (scheduledTask.getId() == id) {
                scheduledTask.cancel();
            }
        });
    }

    public void cancel(ScheduledTask task) {
        task.cancel();
    }

    public ScheduledTask repeat(SkyblockRunnable task) {
        return this.scheduleRepeatingTask(task, 0, 1);
    }

    public ScheduledTask repeatAsync(SkyblockRunnable task) {
        return this.runAsync(task, 0, 1);
    }

    public ScheduledTask runAsync(SkyblockRunnable task) {
        return this.runAsync(task, 0);
    }

    public ScheduledTask runAsync(SkyblockRunnable task, int delay) {
        return this.runAsync(task, delay, 0);
    }

    public ScheduledTask runAsync(SkyblockRunnable task, int delay, int period) {
        ScheduledTask scheduledTask = new ScheduledTask(task, delay, period, true);
        this.pendingTasks.add(scheduledTask);
        return scheduledTask;
    }

    public ScheduledTask scheduleTask(SkyblockRunnable task) {
        return this.scheduleDelayedTask(task, 0);
    }

    public ScheduledTask scheduleDelayedTask(SkyblockRunnable task, int delay) {
        return this.scheduleRepeatingTask(task, delay, 0);
    }

    public ScheduledTask scheduleRepeatingTask(SkyblockRunnable task, int delay, int period) {
        return this.scheduleRepeatingTask(task, delay, period, false);
    }

    public ScheduledTask scheduleRepeatingTask(SkyblockRunnable task, int delay, int period, boolean queued) {
        ScheduledTask scheduledTask = new ScheduledTask(task, delay, period, false);
        if (queued) {
            this.queuedTasks.add(scheduledTask);
        } else {
            this.pendingTasks.add(scheduledTask);
        }
        return scheduledTask;
    }

    public void schedule(ScheduledTask scheduledTask) {
        this.pendingTasks.add(scheduledTask);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }
}

