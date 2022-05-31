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

public class GuiNewChatTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.GuiNewChat.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (!TransformerMethod.printChatMessageWithOptionalDeletion.matches(methodNode)) continue;
            ListIterator iterator = methodNode.instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 185) continue;
                MethodInsnNode methodInsnNode = (MethodInsnNode)abstractNode;
                if (!methodInsnNode.owner.equals(TransformerClass.IChatComponent.getNameRaw()) || !TransformerMethod.getUnformattedText.matches(methodInsnNode)) continue;
                methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiNewChatHook", "getUnformattedText", "(" + TransformerClass.IChatComponent.getName() + ")Ljava/lang/String;", false));
                iterator.remove();
                continue block0;
            }
        }
    }
}

