/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.InjectionHelper;
import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class WorldVertexBufferUploaderTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.WorldVertexBufferUploader.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (!InjectionHelper.matches(methodNode, TransformerMethod.draw)) continue;
            InjectionHelper.start().matchMethodHead().startCode().callStaticMethod("codes/biscuit/skyblockaddons/asm/hooks/WorldVertexBufferUploaderHook", "onRenderWorldRendererBuffer", "()Z").startIfEqual().reeturn().endIf().endCode().finish();
            return;
        }
    }
}

