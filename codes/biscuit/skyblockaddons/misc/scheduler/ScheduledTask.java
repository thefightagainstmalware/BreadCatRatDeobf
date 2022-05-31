/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.misc.scheduler;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.misc.scheduler.SkyblockRunnable;

public class ScheduledTask {
    private static volatile int currentId = 1;
    private static final Object anchor = new Object();
    private final long addedTime = System.currentTimeMillis();
    private long addedTicks = SkyblockAddons.getInstance().getNewScheduler().getTotalTicks();
    private final int id;
    private int delay;
    private final int period;
    private final boolean async;
    private boolean running;
    private boolean canceled;
    private boolean repeating;
    private Runnable task;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ScheduledTask(int delay, int period, boolean async) {
        Object object = anchor;
        synchronized (object) {
            this.id = currentId++;
        }
        this.delay = delay;
        this.period = period;
        this.async = async;
        this.repeating = this.period > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ScheduledTask(SkyblockRunnable task, int delay, int period, boolean async) {
        Object object = anchor;
        synchronized (object) {
            this.id = currentId++;
        }
        this.delay = delay;
        this.period = period;
        this.async = async;
        this.repeating = this.period > 0;
        task.setThisTask(this);
        this.task = () -> {
            this.running = true;
            task.run();
            this.running = false;
        };
    }

    public final void cancel() {
        this.repeating = false;
        this.running = false;
        this.canceled = true;
    }

    public final long getAddedTime() {
        return this.addedTime;
    }

    public final long getAddedTicks() {
        return this.addedTicks;
    }

    public final int getId() {
        return this.id;
    }

    public final int getDelay() {
        return this.delay;
    }

    public final int getPeriod() {
        return this.period;
    }

    public boolean isAsync() {
        return this.async;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public boolean isRunning() {
        return this.running;
    }

    void setDelay(int delay) {
        this.addedTicks = SkyblockAddons.getInstance().getNewScheduler().getTotalTicks();
        this.delay = delay;
    }

    public void start() {
        if (this.isAsync()) {
            SkyblockAddons.runAsync(this.task);
        } else {
            this.task.run();
        }
    }

    public void setTask(SkyblockRunnable task) {
        this.task = () -> {
            this.running = true;
            task.run();
            this.running = false;
        };
    }

    public boolean isRepeating() {
        return this.repeating;
    }
}

