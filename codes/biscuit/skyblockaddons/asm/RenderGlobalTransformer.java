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

public class RenderGlobalTransformer
implements ITransformer {
    private LabelNode existingLabel = null;
    private final LabelNode newLabel = new LabelNode();

    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.RenderGlobal.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.renderEntities.matches(methodNode)) {
                for (AbstractInsnNode abstractNode : methodNode.instructions) {
                    MethodInsnNode methodInsnNode;
                    if (abstractNode instanceof MethodInsnNode && abstractNode.getOpcode() == 182 && TransformerMethod.isRenderEntityOutlines.matches(methodInsnNode = (MethodInsnNode)abstractNode) && abstractNode.getNext() instanceof JumpInsnNode && abstractNode.getNext().getOpcode() == 153) {
                        JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractNode.getNext();
                        this.existingLabel = jumpInsnNode.label;
                        methodNode.instructions.insertBefore(abstractNode.getPrevious(), this.shouldRenderEntityOutlinesExtraCondition(this.newLabel));
                    }
                    if (this.newLabel == null || !(abstractNode instanceof LabelNode) || abstractNode != this.existingLabel) continue;
                    methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)this.newLabel);
                }
                continue;
            }
            if (TransformerMethod.isRenderEntityOutlines.matches(methodNode)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.loadInFrameBuffers());
                continue;
            }
            if (TransformerMethod.renderEntityOutlineFramebuffer.matches(methodNode)) {
                for (AbstractInsnNode abstractNode : methodNode.instructions) {
                    if (!(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 177) continue;
                    methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RenderGlobalHook", "afterFramebufferDraw", "()V", false));
                    continue block0;
                }
                continue;
            }
            if (!TransformerMethod.sendBlockBreakProgress.matches(methodNode)) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertOnAddBlockBreakParticle());
        }
    }

    private InsnList insertOnAddBlockBreakParticle() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RenderGlobalHook", "onAddBlockBreakParticle", "(I" + TransformerClass.BlockPos.getName() + "I)V", false));
        return list;
    }

    private InsnList shouldRenderEntityOutlinesExtraCondition(LabelNode labelNode) {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list.add((AbstractInsnNode)new VarInsnNode(23, 3));
        list.add((AbstractInsnNode)new VarInsnNode(24, 5));
        list.add((AbstractInsnNode)new VarInsnNode(24, 7));
        list.add((AbstractInsnNode)new VarInsnNode(24, 9));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RenderGlobalHook", "blockRenderingSkyblockItemOutlines", "(" + TransformerClass.ICamera.getName() + "FDDD)Z", false));
        list.add((AbstractInsnNode)new JumpInsnNode(153, labelNode));
        return list;
    }

    private InsnList loadInFrameBuffers() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/RenderGlobalHook", "shouldRenderSkyblockItemOutlines", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(4));
        list.add((AbstractInsnNode)new InsnNode(172));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }
}

