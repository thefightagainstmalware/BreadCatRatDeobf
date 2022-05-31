/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBShaderObjects
 *  org.lwjgl.opengl.ARBVertexArrayObject
 *  org.lwjgl.opengl.ARBVertexBufferObject
 *  org.lwjgl.opengl.ARBVertexShader
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.GL15
 *  org.lwjgl.opengl.GL20
 *  org.lwjgl.opengl.GL30
 *  org.lwjgl.opengl.GLContext
 */
package codes.biscuit.skyblockaddons.shader;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public class ShaderHelper {
    public static final ShaderHelper instance = new ShaderHelper();
    private boolean shadersSupported;
    private boolean vbosSupported;
    private boolean vaosSupported;
    private boolean usingARBShaders;
    private boolean usingARBVbos;
    private boolean usingARBVaos;
    public int GL_LINK_STATUS;
    public int GL_ARRAY_BUFFER;
    public int GL_DYNAMIC_DRAW;
    public int GL_COMPILE_STATUS;
    public int GL_VERTEX_SHADER;
    public int GL_FRAGMENT_SHADER;

    public ShaderHelper() {
        this.checkCapabilities();
    }

    private void checkCapabilities() {
        StringBuilder infoBuilder = new StringBuilder();
        ContextCapabilities capabilities = GLContext.getCapabilities();
        boolean openGL33Supported = capabilities.OpenGL30;
        this.vaosSupported = openGL33Supported || capabilities.GL_ARB_vertex_array_object;
        infoBuilder.append("VAOs are ").append(this.vaosSupported ? "" : "not ").append("available. ");
        if (this.vaosSupported) {
            if (capabilities.OpenGL30) {
                infoBuilder.append("OpenGL 3.0 is supported. ");
                this.usingARBVaos = false;
            } else {
                infoBuilder.append("GL_ARB_vertex_array_object is supported. ");
                this.usingARBVaos = true;
            }
        } else {
            infoBuilder.append("OpenGL 3.0 is not supported and GL_ARB_vertex_array_object is not supported. ");
        }
        boolean openGL21Supported = capabilities.OpenGL20;
        this.shadersSupported = openGL21Supported || capabilities.GL_ARB_vertex_shader && capabilities.GL_ARB_fragment_shader && capabilities.GL_ARB_shader_objects;
        infoBuilder.append("Shaders are ").append(this.shadersSupported ? "" : "not ").append("available. ");
        if (this.shadersSupported) {
            if (capabilities.OpenGL20) {
                infoBuilder.append("OpenGL 2.0 is supported. ");
                this.usingARBShaders = false;
                this.GL_LINK_STATUS = 35714;
                this.GL_COMPILE_STATUS = 35713;
                this.GL_VERTEX_SHADER = 35633;
                this.GL_FRAGMENT_SHADER = 35632;
            } else {
                infoBuilder.append("ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported. ");
                this.usingARBShaders = true;
                this.GL_LINK_STATUS = 35714;
                this.GL_COMPILE_STATUS = 35713;
                this.GL_VERTEX_SHADER = 35633;
                this.GL_FRAGMENT_SHADER = 35632;
            }
        } else {
            infoBuilder.append("OpenGL 2.0 is not supported and ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are not supported. ");
        }
        this.usingARBVbos = !capabilities.OpenGL15 && capabilities.GL_ARB_vertex_buffer_object;
        this.vbosSupported = capabilities.OpenGL15 || this.usingARBVbos;
        infoBuilder.append("VBOs are ").append(this.vbosSupported ? "" : "not ").append("available. ");
        if (this.vbosSupported) {
            if (this.usingARBVbos) {
                infoBuilder.append("ARB_vertex_buffer_object is supported. ");
                this.GL_ARRAY_BUFFER = 34962;
                this.GL_DYNAMIC_DRAW = 35048;
            } else {
                infoBuilder.append("OpenGL 1.5 is supported. ");
                this.GL_ARRAY_BUFFER = 34962;
                this.GL_DYNAMIC_DRAW = 35048;
            }
        } else {
            infoBuilder.append("OpenGL 1.5 is not supported and ARB_vertex_buffer_object is not supported. ");
        }
        SkyblockAddons.getLogger().info(infoBuilder.toString());
    }

    public void glLinkProgram(int program) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glLinkProgramARB((int)program);
        } else {
            GL20.glLinkProgram((int)program);
        }
    }

    public String glGetProgramInfoLog(int program, int maxLength) {
        return this.usingARBShaders ? ARBShaderObjects.glGetInfoLogARB((int)program, (int)maxLength) : GL20.glGetProgramInfoLog((int)program, (int)maxLength);
    }

    public int glGetProgrami(int program, int pname) {
        return this.usingARBShaders ? ARBShaderObjects.glGetObjectParameteriARB((int)program, (int)pname) : GL20.glGetProgrami((int)program, (int)pname);
    }

    public void glUseProgram(int program) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glUseProgramObjectARB((int)program);
        } else {
            GL20.glUseProgram((int)program);
        }
    }

    public void glBindBuffer(int target, int buffer) {
        if (this.usingARBVbos) {
            ARBVertexBufferObject.glBindBufferARB((int)target, (int)buffer);
        } else {
            GL15.glBindBuffer((int)target, (int)buffer);
        }
    }

    public void glBufferData(int target, ByteBuffer data, int usage) {
        if (this.usingARBVbos) {
            ARBVertexBufferObject.glBufferDataARB((int)target, (ByteBuffer)data, (int)usage);
        } else {
            GL15.glBufferData((int)target, (ByteBuffer)data, (int)usage);
        }
    }

    public int glGenBuffers() {
        return this.usingARBVbos ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
    }

    public void glAttachShader(int program, int shaderIn) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glAttachObjectARB((int)program, (int)shaderIn);
        } else {
            GL20.glAttachShader((int)program, (int)shaderIn);
        }
    }

    public void glDeleteShader(int p_153180_0_) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glDeleteObjectARB((int)p_153180_0_);
        } else {
            GL20.glDeleteShader((int)p_153180_0_);
        }
    }

    public int glCreateShader(int type) {
        return this.usingARBShaders ? ARBShaderObjects.glCreateShaderObjectARB((int)type) : GL20.glCreateShader((int)type);
    }

    public void glShaderSource(int shaderIn, ByteBuffer string) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glShaderSourceARB((int)shaderIn, (ByteBuffer)string);
        } else {
            GL20.glShaderSource((int)shaderIn, (ByteBuffer)string);
        }
    }

    public void glCompileShader(int shaderIn) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glCompileShaderARB((int)shaderIn);
        } else {
            GL20.glCompileShader((int)shaderIn);
        }
    }

    public int glGetShaderi(int shaderIn, int pname) {
        return this.usingARBShaders ? ARBShaderObjects.glGetObjectParameteriARB((int)shaderIn, (int)pname) : GL20.glGetShaderi((int)shaderIn, (int)pname);
    }

    public String glGetShaderInfoLog(int shaderIn, int maxLength) {
        return this.usingARBShaders ? ARBShaderObjects.glGetInfoLogARB((int)shaderIn, (int)maxLength) : GL20.glGetShaderInfoLog((int)shaderIn, (int)maxLength);
    }

    public void glUniform1f(int location, float v0) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glUniform1fARB((int)location, (float)v0);
        } else {
            GL20.glUniform1f((int)location, (float)v0);
        }
    }

    public void glUniform3f(int location, float v0, float v1, float v2) {
        if (this.usingARBShaders) {
            ARBShaderObjects.glUniform3fARB((int)location, (float)v0, (float)v1, (float)v2);
        } else {
            GL20.glUniform3f((int)location, (float)v0, (float)v1, (float)v2);
        }
    }

    public void glEnableVertexAttribArray(int index) {
        if (this.usingARBShaders) {
            ARBVertexShader.glEnableVertexAttribArrayARB((int)index);
        } else {
            GL20.glEnableVertexAttribArray((int)index);
        }
    }

    public int glGetUniformLocation(int programObj, CharSequence name) {
        return this.usingARBShaders ? ARBShaderObjects.glGetUniformLocationARB((int)programObj, (CharSequence)name) : GL20.glGetUniformLocation((int)programObj, (CharSequence)name);
    }

    public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long buffer_buffer_offset) {
        if (this.usingARBShaders) {
            ARBVertexShader.glVertexAttribPointerARB((int)index, (int)size, (int)type, (boolean)normalized, (int)stride, (long)buffer_buffer_offset);
        } else {
            GL20.glVertexAttribPointer((int)index, (int)size, (int)type, (boolean)normalized, (int)stride, (long)buffer_buffer_offset);
        }
    }

    public int glGenVertexArrays() {
        return this.usingARBVaos ? ARBVertexArrayObject.glGenVertexArrays() : GL30.glGenVertexArrays();
    }

    public void glBindVertexArray(int array) {
        if (this.usingARBVaos) {
            ARBVertexArrayObject.glBindVertexArray((int)array);
        } else {
            GL30.glBindVertexArray((int)array);
        }
    }

    public static ShaderHelper getInstance() {
        return instance;
    }

    public boolean isShadersSupported() {
        return this.shadersSupported;
    }

    public boolean isVbosSupported() {
        return this.vbosSupported;
    }

    public boolean isVaosSupported() {
        return this.vaosSupported;
    }
}

