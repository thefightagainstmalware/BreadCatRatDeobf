/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
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

public class TileEntityEnderChestRendererTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.TileEntityEnderChestRenderer.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.renderTileEntityAt.matches(methodNode)) continue;
            int bindTextureCount = 0;
            ListIterator iterator = methodNode.instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 182) continue;
                MethodInsnNode methodInsnNode = (MethodInsnNode)abstractNode;
                if (methodInsnNode.owner.equals(TransformerClass.TileEntityEnderChestRenderer.getNameRaw()) && TransformerMethod.bindTexture.matches(methodInsnNode)) {
                    if (bindTextureCount == 1) {
                        methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/TileEntityEnderChestRendererHook", "bindTexture", "(" + TransformerClass.TileEntityEnderChestRenderer.getName() + TransformerClass.ResourceLocation.getName() + ")V", false));
                        iterator.remove();
                    }
                    ++bindTextureCount;
                    continue;
                }
                if (!methodInsnNode.owner.equals(TransformerClass.ModelChest.getNameRaw()) || !TransformerMethod.renderAll.matches(methodInsnNode)) continue;
                methodNode.instructions.insertBefore(methodNode.instructions.get(methodNode.instructions.indexOf(abstractNode) - 2), this.insertChangeEnderchestColor());
            }
            break block0;
        }
    }

    private InsnList insertChangeEnderchestColor() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/TileEntityEnderChestRendererHook", "setEnderchestColor", "()V", false));
        return list;
    }
}

