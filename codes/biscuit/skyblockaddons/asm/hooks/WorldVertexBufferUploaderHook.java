/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.shader.ShaderManager;
import net.minecraft.client.renderer.Tessellator;

public class WorldVertexBufferUploaderHook {
    public static boolean onRenderWorldRendererBuffer() {
        boolean canceled = ShaderManager.getInstance().onRenderWorldRendererBuffer();
        if (canceled) {
            Tessellator.func_178181_a().func_178180_c().func_178965_a();
        }
        return canceled;
    }
}

