/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.apache.commons.lang3.mutable.MutableFloat
 */
package codes.biscuit.skyblockaddons.utils.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.mutable.MutableFloat;

public class FloatPair {
    private MutableFloat x;
    private MutableFloat y;

    public FloatPair(float x, float y) {
        this.x = new MutableFloat(x);
        this.y = new MutableFloat(y);
    }

    public float getX() {
        return this.x.getValue().floatValue();
    }

    public float getY() {
        return this.y.getValue().floatValue();
    }

    public void setY(float y) {
        this.y.setValue(y);
    }

    public void setX(float x) {
        this.x.setValue(x);
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        FloatPair otherFloatPair = (FloatPair)other;
        return new EqualsBuilder().append(this.getX(), otherFloatPair.getX()).append(this.getY(), otherFloatPair.getY()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(83, 11).append(this.getX()).append(this.getY()).toHashCode();
    }

    public String toString() {
        return this.getX() + "|" + this.getY();
    }

    public FloatPair cloneCoords() {
        return new FloatPair(this.getX(), this.getY());
    }
}

