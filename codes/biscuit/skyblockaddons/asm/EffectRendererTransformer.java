/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EffectRendererTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.EffectRenderer.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.addEffect.matches(methodNode)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertOnAddParticle());
                continue;
            }
            if (!TransformerMethod.renderParticles.matches(methodNode)) continue;
            AbstractInsnNode last_depthFunc = null;
            for (AbstractInsnNode abstractNode : methodNode.instructions) {
                if (abstractNode instanceof MethodInsnNode && abstractNode.getOpcode() == 184 && ((MethodInsnNode)abstractNode).owner.equals(TransformerClass.GlStateManager.getNameRaw()) && ((MethodInsnNode)abstractNode).name.equals(TransformerMethod.depthMask.getName())) {
                    last_depthFunc = abstractNode;
                    continue;
                }
                if (last_depthFunc == null || !(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 177) continue;
                methodNode.instructions.insertBefore(last_depthFunc.getPrevious(), this.insertAfterRenderParticles());
            }
        }
    }

    private InsnList insertOnAddParticle() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/EffectRendererHook", "onAddParticle", "(" + TransformerClass.EntityFX.getName() + ")V", false));
        return list;
    }

    private InsnList insertAfterRenderParticles() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(23, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/EffectRendererHook", "renderParticleOverlays", "(F)V", false));
        return list;
    }
}

