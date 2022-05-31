/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RenderItemTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.RenderItem.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.renderItem.matches(methodNode)) continue;
            for (AbstractInsnNode abstractNode : methodNode.instructions) {
                MethodInsnNode methodInsnNode;
                if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 183 && abstractNode.getOpcode() != 182 || !TransformerMethod.renderModel_IBakedModel_ItemStack.matches(methodInsnNode = (MethodInsnNode)abstractNode)) continue;
                methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RenderItemHook", "renderToxicArrowPoisonEffect", "(" + TransformerClass.IBakedModel.getName() + TransformerClass.ItemStack.getName() + ")V", false));
                methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new VarInsnNode(25, 1));
                methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new VarInsnNode(25, 2));
                continue block0;
            }
        }
    }
}

