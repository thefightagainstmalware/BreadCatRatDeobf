/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerField;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityLivingBaseTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.EntityLivingBase.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.handleStatusUpdate.matches(methodNode)) {
                for (AbstractInsnNode abstractNode : methodNode.instructions) {
                    if (!(abstractNode instanceof FieldInsnNode) || abstractNode.getOpcode() != 181) continue;
                    FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractNode;
                    if (!fieldInsnNode.owner.equals(TransformerClass.EntityLivingBase.getNameRaw()) || !TransformerField.hurtTime.matches(fieldInsnNode)) continue;
                    methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/EntityLivingBaseHook", "onResetHurtTime", "(" + TransformerClass.EntityLivingBase.getName() + ")V", false));
                    methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                }
                continue;
            }
            if (TransformerMethod.removePotionEffectClient.matches(methodNode)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.onRemovePotionEffect());
                continue;
            }
            if (!TransformerMethod.addPotionEffect.matches(methodNode)) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.onAddPotionEffect());
        }
    }

    private InsnList onRemovePotionEffect() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/EntityLivingBaseHook", "onRemovePotionEffect", "(" + TransformerClass.EntityLivingBase.getName() + "I)Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }

    private InsnList onAddPotionEffect() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/EntityLivingBaseHook", "onAddPotionEffect", "(" + TransformerClass.EntityLivingBase.getName() + TransformerClass.PotionEffect.getName() + ")V", false));
        return list;
    }
}

