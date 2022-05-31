/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package codes.biscuit.skyblockaddons.shader.chroma;

import codes.biscuit.skyblockaddons.shader.Shader;
import codes.biscuit.skyblockaddons.shader.UniformType;
import codes.biscuit.skyblockaddons.utils.Utils;
import net.minecraft.client.Minecraft;

public abstract class ChromaShader
extends Shader {
    public ChromaShader(String shaderName) throws Exception {
        super(shaderName, shaderName);
    }

    @Override
    protected void registerUniforms() {
        this.registerUniform(UniformType.FLOAT, "chromaSize", () -> Float.valueOf(main.getConfigValues().getChromaSize().floatValue() * ((float)Minecraft.func_71410_x().field_71443_c / 100.0f)));
        this.registerUniform(UniformType.FLOAT, "timeOffset", () -> {
            float ticks = (float)main.getNewScheduler().getTotalTicks() + Utils.getPartialTicks();
            float chromaSpeed = main.getConfigValues().getChromaSpeed().floatValue() / 360.0f;
            return Float.valueOf(ticks * chromaSpeed);
        });
        this.registerUniform(UniformType.FLOAT, "saturation", () -> Float.valueOf(main.getConfigValues().getChromaSaturation().floatValue()));
    }
}

