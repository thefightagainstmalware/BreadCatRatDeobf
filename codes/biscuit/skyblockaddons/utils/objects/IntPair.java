/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package codes.biscuit.skyblockaddons.utils.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.mutable.MutableInt;

public class IntPair {
    private MutableInt x;
    private MutableInt y;

    public IntPair(int x, int y) {
        this.x = new MutableInt(x);
        this.y = new MutableInt(y);
    }

    public int getX() {
        return this.x.getValue();
    }

    public int getY() {
        return this.y.getValue();
    }

    public void setY(int y) {
        this.y.setValue(y);
    }

    public void setX(int x) {
        this.x.setValue(x);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        IntPair chunkCoords = (IntPair)obj;
        return new EqualsBuilder().append(this.getX(), chunkCoords.getX()).append(this.getY(), chunkCoords.getY()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(83, 11).append(this.getX()).append(this.getY()).toHashCode();
    }

    public String toString() {
        return this.getX() + "|" + this.getY();
    }

    public IntPair cloneCoords() {
        return new IntPair(this.getX(), this.getY());
    }
}

