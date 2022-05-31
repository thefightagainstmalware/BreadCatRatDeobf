/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shader;

public class UniformType<T> {
    public static final UniformType<Float> FLOAT = new UniformType(1);
    public static final UniformType<Float[]> VEC3 = new UniformType(3);
    private int amount;

    public UniformType(int amount) {
        this.amount = amount;
    }
}

