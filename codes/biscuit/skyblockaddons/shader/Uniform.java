/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shader;

import codes.biscuit.skyblockaddons.shader.Shader;
import codes.biscuit.skyblockaddons.shader.ShaderHelper;
import codes.biscuit.skyblockaddons.shader.UniformType;
import java.util.Objects;
import java.util.function.Supplier;

public class Uniform<T> {
    private final UniformType<T> uniformType;
    private final Supplier<T> uniformValuesSupplier;
    private final String name;
    private int uniformID;
    private T previousUniformValue;

    public Uniform(Shader shader, UniformType<T> uniformType, String name, Supplier<T> uniformValuesSupplier) {
        this.uniformType = uniformType;
        this.uniformValuesSupplier = uniformValuesSupplier;
        this.name = name;
        this.init(shader, name);
    }

    private void init(Shader shader, String name) {
        this.uniformID = ShaderHelper.getInstance().glGetUniformLocation(shader.getProgram(), name);
    }

    public void update() {
        T newUniformValue = this.uniformValuesSupplier.get();
        if (!Objects.deepEquals(this.previousUniformValue, newUniformValue)) {
            if (this.uniformType == UniformType.FLOAT) {
                ShaderHelper.getInstance().glUniform1f(this.uniformID, ((Float)newUniformValue).floatValue());
            } else if (this.uniformType == UniformType.VEC3) {
                Float[] values = (Float[])newUniformValue;
                ShaderHelper.getInstance().glUniform3f(this.uniformID, values[0].floatValue(), values[1].floatValue(), values[2].floatValue());
            }
            this.previousUniformValue = newUniformValue;
        }
    }
}

