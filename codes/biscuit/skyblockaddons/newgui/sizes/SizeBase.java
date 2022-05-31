/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.newgui.sizes;

import codes.biscuit.skyblockaddons.SkyblockAddons;

public class SizeBase {
    private int lastPositionsUpdate;
    private int lastSizesUpdate;
    private boolean forceUpdate;
    protected float x;
    protected float y;
    protected float w;
    protected float h;

    public float getX() {
        if (this.positionsNeedUpdate()) {
            this.updatePositions();
        }
        return this.x;
    }

    public float getY() {
        if (this.positionsNeedUpdate()) {
            this.updatePositions();
        }
        return this.y;
    }

    public float getW() {
        if (this.sizesNeedUpdate()) {
            this.updateSizes();
        }
        return this.w;
    }

    public float getH() {
        if (this.sizesNeedUpdate()) {
            this.updateSizes();
        }
        return this.h;
    }

    public void setForceUpdate() {
        this.forceUpdate = true;
    }

    private boolean positionsNeedUpdate() {
        if (this.forceUpdate) {
            return true;
        }
        return SkyblockAddons.getInstance().getNewScheduler().getTotalTicks() != (long)this.lastPositionsUpdate;
    }

    private boolean sizesNeedUpdate() {
        if (this.forceUpdate) {
            return true;
        }
        return SkyblockAddons.getInstance().getNewScheduler().getTotalTicks() != (long)this.lastSizesUpdate;
    }

    public void updatePositions() {
    }

    public void updateSizes() {
    }
}

