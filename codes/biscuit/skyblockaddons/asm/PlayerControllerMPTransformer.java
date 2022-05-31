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

import codes.biscuit.skyblockaddons.asm.utils.InjectionHelper;
import codes.biscuit.skyblockaddons.asm.utils.InstructionBuilder;
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
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PlayerControllerMPTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.PlayerControllerMP.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (InjectionHelper.matches(methodNode, TransformerMethod.onPlayerDestroyBlock)) {
                InjectionHelper.start().matchingOwner(TransformerClass.World).matchingMethod(TransformerMethod.playAuxSFX).endCondition().injectCodeBefore().load(InstructionBuilder.VariableType.OBJECT, 1).callStaticMethod("codes/biscuit/skyblockaddons/asm/hooks/PlayerControllerMPHook", "onPlayerDestroyBlock", "(" + TransformerClass.BlockPos.getName() + ")V").endCode().finish();
                continue;
            }
            if (TransformerMethod.windowClick.matches(methodNode)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertOnWindowClick());
                continue;
            }
            if (!TransformerMethod.resetBlockRemoving.matches(methodNode)) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/PlayerControllerMPHook", "onResetBlockRemoving", "()V", false));
        }
    }

    private InsnList insertOnWindowClick() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 8));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new VarInsnNode(21, 4));
        list.add((AbstractInsnNode)new VarInsnNode(25, 5));
        list.add((AbstractInsnNode)new VarInsnNode(25, 8));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/PlayerControllerMPHook", "onWindowClick", "(III" + TransformerClass.EntityPlayer.getName() + "Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 8));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(1));
        list.add((AbstractInsnNode)new InsnNode(176));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }
}

