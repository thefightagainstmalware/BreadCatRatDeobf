/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.misc.scheduler;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.misc.scheduler.ScheduledTask;

public abstract class SkyblockRunnable
implements Runnable {
    private ScheduledTask thisTask;

    public void cancel() {
        SkyblockAddons.getInstance().getNewScheduler().cancel(this.thisTask);
    }

    public void setThisTask(ScheduledTask thisTask) {
        this.thisTask = thisTask;
    }
}

