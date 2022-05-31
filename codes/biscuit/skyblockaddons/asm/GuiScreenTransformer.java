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
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiScreenTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.GuiScreen.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        try {
            for (MethodNode methodNode : classNode.methods) {
                if (TransformerMethod.renderToolTip.matches(methodNode)) {
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.onRenderTooltip());
                }
                if (!TransformerMethod.handleComponentClick.matches(methodNode)) continue;
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertComponentClick());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private InsnList onRenderTooltip() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiScreenHook", "onRenderTooltip", "(" + TransformerClass.ItemStack.getName() + "II)Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }

    private InsnList insertComponentClick() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiScreenHook", "handleComponentClick", "(" + TransformerClass.IChatComponent.getName() + ")V", false));
        return list;
    }
}

