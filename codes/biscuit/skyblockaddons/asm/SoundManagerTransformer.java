/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class SoundManagerTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.SoundManager.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.playSound.matches(methodNode)) continue;
            ListIterator iterator = methodNode.instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                if (!(abstractNode instanceof MethodInsnNode)) continue;
                MethodInsnNode methodInsnNode = (MethodInsnNode)abstractNode;
                if (!this.nameMatches(methodInsnNode.owner, TransformerClass.SoundManager.getNameRaw()) || !TransformerMethod.getNormalizedVolume.matches(methodInsnNode)) continue;
                methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/SoundManagerHook", "getNormalizedVolume", "(" + TransformerClass.SoundManager.getName() + TransformerClass.ISound.getName() + TransformerClass.SoundPoolEntry.getName() + TransformerClass.SoundCategory.getName() + ")F", false));
                iterator.remove();
                break block0;
            }
            break block0;
        }
    }
}

