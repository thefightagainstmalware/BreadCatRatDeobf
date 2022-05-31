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
 *  org.objectweb.asm.tree.MethodInsnNode
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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ItemTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.Item.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = methodNode.name;
            if (this.nameMatches(methodName, "showDurabilityBar")) {
                ListIterator iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                    if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 182) continue;
                    MethodInsnNode methodInsnNode = (MethodInsnNode)abstractNode;
                    if (!methodInsnNode.owner.equals(TransformerClass.ItemStack.getNameRaw()) || !TransformerMethod.isItemDamaged.matches(methodInsnNode)) continue;
                    methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/ItemHook", "isItemDamaged", "(" + TransformerClass.ItemStack.getName() + ")Z", false));
                    iterator.remove();
                    break;
                }
            }
            if (!this.nameMatches(methodName, "getDurabilityForDisplay")) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertDurabilityHook());
        }
    }

    private InsnList insertDurabilityHook() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 2));
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/ItemHook", "getDurabilityForDisplay", "(" + TransformerClass.ItemStack.getName() + "Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "getReturnValue", "()Ljava/lang/Object;", false));
        list.add((AbstractInsnNode)new TypeInsnNode(192, "java/lang/Double"));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "java/lang/Double", "doubleValue", "()D", false));
        list.add((AbstractInsnNode)new InsnNode(175));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }
}

