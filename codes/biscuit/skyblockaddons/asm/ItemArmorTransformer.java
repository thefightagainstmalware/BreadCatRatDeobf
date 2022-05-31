/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import java.util.ListIterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ItemArmorTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.ItemArmor.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.onItemRightClick.matches(methodNode)) continue;
            ListIterator iterator = methodNode.instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode nextAbstractNode;
                AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                if (!(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 4 || !iterator.hasNext() || !((nextAbstractNode = (AbstractInsnNode)iterator.next()) instanceof InsnNode) || nextAbstractNode.getOpcode() != 96) continue;
                methodNode.instructions.remove(abstractNode);
                methodNode.instructions.remove(nextAbstractNode);
                continue block0;
            }
        }
    }
}

