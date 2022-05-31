/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import java.util.ListIterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ItemArmorDispenserBehaviorTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.ItemArmor.getTransformerName() + "$1"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.dispenseStack.matches(methodNode)) continue;
            ListIterator iterator = methodNode.instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode nextAbstractNode;
                AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                if (!(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 3 || !iterator.hasNext() || !((nextAbstractNode = (AbstractInsnNode)iterator.next()) instanceof VarInsnNode) || nextAbstractNode.getOpcode() != 54) continue;
                methodNode.instructions.insertBefore(abstractNode, this.revertVanillaBehavior());
                methodNode.instructions.remove(abstractNode);
                methodNode.instructions.remove(nextAbstractNode);
                continue block0;
            }
        }
    }

    private InsnList revertVanillaBehavior() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 9));
        list.add((AbstractInsnNode)new TypeInsnNode(193, TransformerClass.EntityPlayer.getNameRaw()));
        LabelNode notEqual = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notEqual));
        list.add((AbstractInsnNode)new InsnNode(4));
        LabelNode afterCondition = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(167, afterCondition));
        list.add((AbstractInsnNode)notEqual);
        list.add((AbstractInsnNode)new InsnNode(3));
        list.add((AbstractInsnNode)afterCondition);
        list.add((AbstractInsnNode)new VarInsnNode(54, 10));
        return list;
    }
}

