/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.IntInsnNode
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
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiIngameMenuTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.GuiIngameMenu.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.actionPerformed.matches(methodNode)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertOnButtonClick());
                for (AbstractInsnNode abstractNode : methodNode.instructions) {
                    if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 182) continue;
                    MethodInsnNode methodInsnNode = (MethodInsnNode)abstractNode;
                    if (!methodInsnNode.owner.equals(TransformerClass.Minecraft.getNameRaw()) || !TransformerMethod.isIntegratedServerRunning.matches(methodInsnNode)) continue;
                    methodNode.instructions.insertBefore(abstractNode.getPrevious().getPrevious(), (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiDisconnectedHook", "onDisconnect", "()V", false));
                }
                continue;
            }
            if (!TransformerMethod.initGui.matches(methodNode)) continue;
            for (AbstractInsnNode abstractNode : methodNode.instructions) {
                if (!(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 177) continue;
                methodNode.instructions.insertBefore(abstractNode, this.insertAddMenuButtons());
            }
        }
    }

    private InsnList insertAddMenuButtons() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.buttonList.getField(TransformerClass.GuiIngameMenu));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.width.getField(TransformerClass.GuiIngameMenu));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.height.getField(TransformerClass.GuiIngameMenu));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiIngameMenuHook", "addMenuButtons", "(Ljava/util/List;II)V", false));
        return list;
    }

    private InsnList insertOnButtonClick() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)TransformerField.id.getField(TransformerClass.GuiButton));
        list.add((AbstractInsnNode)new IntInsnNode(16, 53));
        LabelNode labelNode = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(160, labelNode));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiIngameMenuHook", "onButtonClick", "()V", false));
        list.add((AbstractInsnNode)labelNode);
        return list;
    }
}

