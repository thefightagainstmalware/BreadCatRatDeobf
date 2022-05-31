/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RendererLivingEntityTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.RendererLivingEntity.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.rotateCorpse.matches(methodNode)) {
                ListIterator iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                    if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 182) continue;
                    MethodInsnNode methodInsnNode = (MethodInsnNode)abstractNode;
                    if (methodInsnNode.owner.equals("java/lang/String") && methodInsnNode.name.equals("equals")) {
                        methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RendererLivingEntityHook", "equals", "(Ljava/lang/String;Ljava/lang/Object;)Z", false));
                        iterator.remove();
                        continue;
                    }
                    if (!methodInsnNode.owner.equals(TransformerClass.EntityPlayer.getNameRaw()) || !TransformerMethod.isWearing.matches(methodInsnNode)) continue;
                    methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RendererLivingEntityHook", "isWearing", "(" + TransformerClass.EntityPlayer.getName() + TransformerClass.EnumPlayerModelParts.getName() + ")Z", false));
                    iterator.remove();
                    continue block0;
                }
                continue;
            }
            if (!TransformerMethod.setScoreTeamColor.matches(methodNode)) continue;
            for (AbstractInsnNode abstractNode : methodNode.instructions) {
                if (!(abstractNode instanceof VarInsnNode) || abstractNode.getOpcode() != 21) continue;
                methodNode.instructions.insertBefore(abstractNode, this.setOutlineColor());
                continue block0;
            }
        }
    }

    private InsnList setOutlineColor() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RendererLivingEntityHook", "setOutlineColor", "(" + TransformerClass.EntityLivingBase.getName() + "I)I", false));
        list.add((AbstractInsnNode)new VarInsnNode(54, 2));
        return list;
    }
}

