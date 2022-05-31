/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class InventoryPlayerTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.InventoryPlayer.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.changeCurrentItem.matches(methodNode)) continue;
            methodNode.instructions.insert((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/MinecraftHook", "updatedCurrentItem", "()V", false));
        }
    }
}

