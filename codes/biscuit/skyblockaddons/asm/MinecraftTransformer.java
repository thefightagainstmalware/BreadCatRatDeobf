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
 *  org.objectweb.asm.tree.TypeInsnNode
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
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class MinecraftTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.Minecraft.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.rightClickMouse.matches(methodNode)) {
                for (AbstractInsnNode abstractNode : methodNode.instructions) {
                    if (!(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 7) continue;
                    methodNode.instructions.insertBefore(abstractNode.getPrevious(), this.insertRightClickMouse());
                    break;
                }
            }
            if (TransformerMethod.runTick.matches(methodNode)) {
                for (AbstractInsnNode abstractNode : methodNode.instructions) {
                    if (!(abstractNode instanceof FieldInsnNode) || abstractNode.getOpcode() != 181 || !TransformerField.currentItem.matches((FieldInsnNode)abstractNode)) continue;
                    methodNode.instructions.insertBefore(abstractNode.getPrevious().getPrevious().getPrevious().getPrevious(), (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/MinecraftHook", "updatedCurrentItem", "()V", false));
                    break;
                }
            }
            if (TransformerMethod.clickMouse.matches(methodNode)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertOnClickMouse());
            }
            if (!TransformerMethod.sendClickBlockToController.matches(methodNode)) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertOnSendClickBlockToController());
        }
    }

    private InsnList insertRightClickMouse() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 6));
        list.add((AbstractInsnNode)new VarInsnNode(25, 6));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/MinecraftHook", "rightClickMouse", "(Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 6));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }

    private InsnList insertOnClickMouse() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 3));
        list.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/MinecraftHook", "onClickMouse", "(Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }

    private InsnList insertOnSendClickBlockToController() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 3));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/MinecraftHook", "onSendClickBlockToController", "(ZLcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }
}

