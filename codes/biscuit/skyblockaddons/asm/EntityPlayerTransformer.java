/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.ASMUtils;
import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerField;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityPlayerTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.EntityPlayer.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.setCurrentItemOrArmor.matches(methodNode)) continue;
            methodNode.instructions = this.setCurrentItemOrArmor();
        }
    }

    private InsnList setCurrentItemOrArmor() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)ASMUtils.getField(TransformerField.inventory, TransformerClass.EntityPlayer));
        list.add((AbstractInsnNode)ASMUtils.getField(TransformerField.armorInventory, TransformerClass.InventoryPlayer));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list.add((AbstractInsnNode)new InsnNode(83));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }
}

