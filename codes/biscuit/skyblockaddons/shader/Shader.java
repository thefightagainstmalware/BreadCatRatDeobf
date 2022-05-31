/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.shader.ShaderLinkHelper
 *  org.lwjgl.opengl.OpenGLException
 */
package codes.biscuit.skyblockaddons.shader;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.shader.ShaderHelper;
import codes.biscuit.skyblockaddons.shader.ShaderLoader;
import codes.biscuit.skyblockaddons.shader.Uniform;
import codes.biscuit.skyblockaddons.shader.UniformType;
import codes.biscuit.skyblockaddons.shader.VertexFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.shader.ShaderLinkHelper;
import org.lwjgl.opengl.OpenGLException;

public abstract class Shader {
    protected static final SkyblockAddons main = SkyblockAddons.getInstance();
    private String vertex;
    private String fragment;
    private VertexFormat vertexFormat;
    protected int program;
    private List<Uniform<?>> uniforms = new ArrayList();

    public Shader(String vertex, String fragment) throws Exception {
        this(vertex, fragment, null);
    }

    private Shader(String vertex, String fragment, VertexFormat vertexFormat) throws Exception {
        this.vertex = vertex;
        this.fragment = fragment;
        this.vertexFormat = vertexFormat;
        this.init();
    }

    private void init() throws Exception {
        this.program = ShaderLinkHelper.func_148074_b().func_148078_c();
        if (this.vertex != null) {
            ShaderLoader vertexShaderLoader = ShaderLoader.load(ShaderLoader.ShaderType.VERTEX, this.vertex);
            vertexShaderLoader.attachShader(this);
        }
        if (this.fragment != null) {
            ShaderLoader fragmentShaderLoader = ShaderLoader.load(ShaderLoader.ShaderType.FRAGMENT, this.fragment);
            fragmentShaderLoader.attachShader(this);
        }
        ShaderHelper.getInstance().glLinkProgram(this.program);
        int linkStatus = ShaderHelper.getInstance().glGetProgrami(this.program, ShaderHelper.getInstance().GL_LINK_STATUS);
        if (linkStatus == 0) {
            throw new OpenGLException("Error encountered when linking program containing VS " + this.vertex + " and FS " + this.fragment + ": " + ShaderHelper.getInstance().glGetProgramInfoLog(this.program, 32768));
        }
        this.registerUniforms();
    }

    protected void registerUniforms() {
    }

    public void updateUniforms() {
        for (Uniform<?> uniform : this.uniforms) {
            uniform.update();
        }
    }

    public void enable() {
        ShaderHelper.getInstance().glUseProgram(this.program);
    }

    public void disable() {
        ShaderHelper.getInstance().glUseProgram(0);
    }

    public boolean isUsingFixedPipeline() {
        return this.vertexFormat == null;
    }

    public <T> void registerUniform(UniformType<T> uniformType, String name, Supplier<T> uniformValuesSupplier) {
        this.uniforms.add(new Uniform<T>(this, uniformType, name, uniformValuesSupplier));
    }

    public int getProgram() {
        return this.program;
    }
}

