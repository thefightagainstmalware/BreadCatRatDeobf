/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector3d
 */
package codes.biscuit.skyblockaddons.shader.chroma;

import codes.biscuit.skyblockaddons.shader.UniformType;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaShader;
import codes.biscuit.skyblockaddons.utils.Utils;
import javax.vecmath.Vector3d;

public class Chroma3DShader
extends ChromaShader {
    private float alpha = 1.0f;

    public Chroma3DShader() throws Exception {
        super("chroma_3d");
    }

    @Override
    protected void registerUniforms() {
        super.registerUniforms();
        this.registerUniform(UniformType.VEC3, "playerWorldPosition", () -> {
            Vector3d viewPosition = Utils.getPlayerViewPosition();
            return new Float[]{Float.valueOf((float)viewPosition.x), Float.valueOf((float)viewPosition.y), Float.valueOf((float)viewPosition.z)};
        });
        this.registerUniform(UniformType.FLOAT, "alpha", () -> Float.valueOf(this.alpha));
        this.registerUniform(UniformType.FLOAT, "brightness", () -> Float.valueOf(main.getConfigValues().getChromaBrightness().floatValue()));
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}

