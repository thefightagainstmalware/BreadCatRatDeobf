/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shader;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.shader.Shader;
import codes.biscuit.skyblockaddons.shader.ShaderHelper;
import java.util.HashMap;
import java.util.Map;

public class ShaderManager {
    public static final ShaderManager instance = new ShaderManager();
    private Map<Class<? extends Shader>, Shader> shaders = new HashMap<Class<? extends Shader>, Shader>();
    private Class<? extends Shader> activeShaderType;
    private Shader activeShader;

    public <T extends Shader> T enableShader(Class<T> shaderClass) {
        Shader shader;
        if (this.activeShaderType == shaderClass) {
            return (T)this.activeShader;
        }
        if (this.activeShader != null) {
            this.disableShader();
        }
        if ((shader = this.shaders.get(shaderClass)) == null) {
            shader = this.newInstance(shaderClass);
            this.shaders.put(shaderClass, shader);
        }
        if (shader == null) {
            return null;
        }
        this.activeShaderType = shaderClass;
        this.activeShader = shader;
        this.activeShader.enable();
        this.activeShader.updateUniforms();
        return (T)shader;
    }

    private <T extends Shader> T newInstance(Class<T> shaderClass) {
        try {
            return (T)((Shader)shaderClass.getConstructor(new Class[0]).newInstance(new Object[0]));
        }
        catch (Exception ex) {
            SkyblockAddons.getLogger().error("An error occurred while creating a shader!", (Throwable)ex);
            return null;
        }
    }

    public void disableShader() {
        if (this.activeShader == null) {
            return;
        }
        this.activeShader.disable();
        this.activeShaderType = null;
        this.activeShader = null;
    }

    public boolean isShaderEnabled() {
        return this.activeShader != null;
    }

    public boolean onRenderWorldRendererBuffer() {
        return this.isShaderEnabled() && !this.activeShader.isUsingFixedPipeline();
    }

    public boolean areShadersSupported() {
        return ShaderHelper.getInstance().isShadersSupported();
    }

    public Map<Class<? extends Shader>, Shader> getShaders() {
        return this.shaders;
    }

    public Class<? extends Shader> getActiveShaderType() {
        return this.activeShaderType;
    }

    public Shader getActiveShader() {
        return this.activeShader;
    }

    public static ShaderManager getInstance() {
        return instance;
    }
}

