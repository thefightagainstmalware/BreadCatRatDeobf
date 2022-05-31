/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.core.chroma;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.shader.ShaderManager;
import codes.biscuit.skyblockaddons.shader.chroma.Chroma3DShader;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaScreenShader;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaScreenTexturedShader;
import codes.biscuit.skyblockaddons.shader.chroma.ChromaShader;

public class MulticolorShaderManager {
    private static MulticolorState currentState = new MulticolorState();

    public static void begin(boolean isTextured, boolean ignoreTexture, boolean is3D) {
        currentState.disable();
        currentState = new ShaderChromaState(isTextured, ignoreTexture, is3D);
        currentState.setup();
    }

    public static void end() {
        currentState.disable();
    }

    public static boolean shouldUseChromaShaders() {
        return ShaderManager.getInstance().areShadersSupported() && SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.USE_NEW_CHROMA_EFFECT);
    }

    private static class ShaderChromaState
    extends MulticolorState {
        Class<? extends ChromaShader> shaderType;

        public ShaderChromaState(boolean isTextured, boolean shouldIgnoreTexture, boolean shouldRender3D) {
            super(isTextured, shouldIgnoreTexture, shouldRender3D);
            this.shaderType = isTextured ? (shouldRender3D ? ChromaScreenTexturedShader.class : ChromaScreenTexturedShader.class) : (shouldRender3D ? Chroma3DShader.class : ChromaScreenShader.class);
        }

        @Override
        public void setup() {
            if (!this.chromaEnabled) {
                this.chromaEnabled = true;
                ShaderManager.getInstance().enableShader(this.shaderType);
            }
        }

        @Override
        public void disable() {
            if (this.chromaEnabled) {
                this.chromaEnabled = false;
                ShaderManager.getInstance().disableShader();
            }
        }
    }

    private static class MulticolorState {
        boolean chromaEnabled;
        boolean textured;
        boolean ignoreTexture;
        boolean render3D;

        public MulticolorState() {
            this.chromaEnabled = false;
        }

        public MulticolorState(boolean isTextured, boolean shouldIgnoreTexture, boolean shouldRender3D) {
            this.textured = isTextured;
            this.ignoreTexture = shouldIgnoreTexture;
            this.render3D = shouldRender3D;
        }

        public void setup() {
        }

        public void disable() {
        }
    }
}

