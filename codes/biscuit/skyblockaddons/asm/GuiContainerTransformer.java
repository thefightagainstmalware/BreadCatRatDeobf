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
import codes.biscuit.skyblockaddons.asm.utils.TransformerField;
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

public class GuiContainerTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.GuiContainer.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            MethodInsnNode methodInsnNode;
            if (TransformerMethod.drawScreen.matches(methodNode)) {
                ListIterator iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                    if (abstractNode instanceof VarInsnNode && abstractNode.getOpcode() == 54) {
                        VarInsnNode varInsnNode = (VarInsnNode)abstractNode;
                        if (varInsnNode.var != 7) continue;
                        methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiContainerHook", "setLastSlot", "()V", false));
                        continue;
                    }
                    if (abstractNode instanceof MethodInsnNode && abstractNode.getOpcode() == 182) {
                        methodInsnNode = (MethodInsnNode)abstractNode;
                        if (!methodInsnNode.owner.equals(TransformerClass.GuiContainer.getNameRaw()) || !TransformerMethod.drawGradientRect.matches(methodInsnNode)) continue;
                        methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                        methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)TransformerField.theSlot.getField(TransformerClass.GuiContainer));
                        methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiContainerHook", "drawGradientRect", "(" + TransformerClass.GuiContainer.getName() + "IIIIII" + TransformerClass.Slot.getName() + ")V", false));
                        iterator.remove();
                        continue;
                    }
                    if (abstractNode instanceof MethodInsnNode && abstractNode.getOpcode() == 183) {
                        methodInsnNode = (MethodInsnNode)abstractNode;
                        if (!methodInsnNode.owner.equals(TransformerClass.GuiContainer.getNameRaw()) || !TransformerMethod.drawSlot.matches(methodInsnNode)) continue;
                        methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiContainerHook", "drawSlot", "(" + TransformerClass.GuiContainer.getName() + TransformerClass.Slot.getName() + ")V", false));
                        methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new VarInsnNode(25, 9));
                        methodNode.instructions.insert(abstractNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                        continue;
                    }
                    if (!(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 177) continue;
                    methodNode.instructions.insertBefore(abstractNode, this.insertDrawBackpacks());
                }
                continue;
            }
            if (TransformerMethod.keyTyped.matches(methodNode)) {
                for (AbstractInsnNode abstractNode : methodNode.instructions) {
                    if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 182) continue;
                    methodInsnNode = (MethodInsnNode)abstractNode;
                    if (!methodInsnNode.owner.equals(TransformerClass.GuiContainer.getNameRaw()) || !TransformerMethod.checkHotbarKeys.matches(methodInsnNode)) continue;
                    methodNode.instructions.insertBefore(abstractNode.getPrevious().getPrevious(), this.insertKeyTyped());
                }
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertKeyTypedTwo());
                continue;
            }
            if (!TransformerMethod.handleMouseClick.matches(methodNode)) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.onHandleMouseClick());
        }
    }

    private InsnList insertKeyTypedTwo() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiContainerHook", "keyTyped", "(I)V", false));
        return list;
    }

    private InsnList insertDrawBackpacks() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.fontRendererObj.getField(TransformerClass.GuiContainer));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiContainerHook", "drawBackpacks", "(" + TransformerClass.GuiContainer.getName() + "II" + TransformerClass.FontRenderer.getName() + ")V", false));
        return list;
    }

    private InsnList insertKeyTyped() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 3));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.theSlot.getField(TransformerClass.GuiContainer));
        list.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiContainerHook", "keyTyped", "(" + TransformerClass.GuiContainer.getName() + "I" + TransformerClass.Slot.getName() + "Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }

    private InsnList onHandleMouseClick() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new VarInsnNode(21, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiContainerHook", "onHandleMouseClick", "(" + TransformerClass.Slot.getName() + "III)Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }
}

