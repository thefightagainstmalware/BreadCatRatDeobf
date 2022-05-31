/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.FrameNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.IntInsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
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
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class FontRendererTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.FontRenderer.getTransformerName(), "club.sk1er.patcher.hooks.FontRendererHook"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        block0: for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.renderChar.matches(methodNode) || methodNode.name.equals("renderChar")) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertChangeTextColor());
                continue;
            }
            if (!TransformerMethod.renderStringAtPos.matches(methodNode)) continue;
            LabelNode elseIf22Start = new LabelNode();
            LabelNode nextElseIf = null;
            int italicStyleCount = 0;
            boolean findString = false;
            boolean insertedChroma = false;
            boolean findIfEq20 = false;
            for (AbstractInsnNode abstractNode : methodNode.instructions) {
                AbstractInsnNode nextNode;
                FieldInsnNode fieldInsnNode;
                if (!findString && abstractNode instanceof LdcInsnNode && abstractNode.getOpcode() == 18 && ((LdcInsnNode)abstractNode).cst instanceof String && ((LdcInsnNode)abstractNode).cst.equals("0123456789abcdefklmnor")) {
                    ((LdcInsnNode)abstractNode).cst = "0123456789abcdefklmnorz";
                    findString = true;
                    continue;
                }
                if (findString && abstractNode instanceof FieldInsnNode && (fieldInsnNode = (FieldInsnNode)abstractNode).getOpcode() == 181 && fieldInsnNode.owner.equals(TransformerClass.FontRenderer.getNameRaw()) && fieldInsnNode.name.equals(TransformerField.italicStyle.getName())) {
                    if (++italicStyleCount != 1 && italicStyleCount != 3) continue;
                    methodNode.instructions.insert(abstractNode, this.insertRestoreChromaState());
                    continue;
                }
                if (findString && !findIfEq20 && abstractNode instanceof VarInsnNode && abstractNode.getOpcode() == 21 && ((VarInsnNode)abstractNode).var == 5 && (nextNode = abstractNode.getNext()) instanceof IntInsnNode && nextNode.getOpcode() == 16 && ((IntInsnNode)nextNode).operand == 20 && (nextNode = nextNode.getNext()) instanceof JumpInsnNode && nextNode.getOpcode() == 160) {
                    nextElseIf = ((JumpInsnNode)nextNode).label;
                    ((JumpInsnNode)nextNode).label = elseIf22Start;
                    findIfEq20 = true;
                    continue;
                }
                if (findIfEq20 && !insertedChroma && abstractNode instanceof JumpInsnNode && abstractNode.getOpcode() == 167) {
                    methodNode.instructions.insert(abstractNode, this.checkChromaToggleOn(elseIf22Start, nextElseIf, ((JumpInsnNode)abstractNode).label));
                    insertedChroma = true;
                    continue;
                }
                if (!insertedChroma || !(abstractNode instanceof InsnNode) || abstractNode.getOpcode() != 177) continue;
                methodNode.instructions.insertBefore(abstractNode, this.insertEndOfString());
            }
            if (!insertedChroma) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.insertBeginRenderString());
            VarInsnNode thisAnchor = null;
            ListIterator iterator = methodNode.instructions.iterator();
            for (int i = 0; iterator.hasNext() && i < 20; ++i) {
                AbstractInsnNode abstractNode = (AbstractInsnNode)iterator.next();
                if (abstractNode instanceof FieldInsnNode && abstractNode.getOpcode() == 180 && ((FieldInsnNode)abstractNode).name.equals("patcherFontRenderer") && abstractNode.getPrevious() instanceof VarInsnNode) {
                    thisAnchor = (VarInsnNode)abstractNode.getPrevious();
                    continue;
                }
                if (thisAnchor == null || abstractNode.getOpcode() != 177) continue;
                LabelNode endif = new LabelNode();
                methodNode.instructions.insert(abstractNode, (AbstractInsnNode)endif);
                methodNode.instructions.insertBefore(abstractNode, this.insertEndOfString());
                methodNode.instructions.insertBefore((AbstractInsnNode)thisAnchor, this.patcherOverride(endif));
                continue block0;
            }
        }
    }

    private InsnList insertChangeTextColor() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/FontRendererHook", "changeTextColor", "()V", false));
        return list;
    }

    private InsnList insertBeginRenderString() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/FontRendererHook", "beginRenderString", "(Z)V", false));
        return list;
    }

    private InsnList patcherOverride(LabelNode endIf) {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/FontRendererHook", "shouldOverridePatcher", "(Ljava/lang/String;)Z", false));
        list.add((AbstractInsnNode)new JumpInsnNode(154, endIf));
        return list;
    }

    private InsnList insertRestoreChromaState() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/FontRendererHook", "restoreChromaState", "()V", false));
        return list;
    }

    private InsnList insertEndOfString() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/FontRendererHook", "endRenderString", "()V", false));
        return list;
    }

    private InsnList checkChromaToggleOn(LabelNode startIf, LabelNode elseIf, LabelNode endIf) {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)startIf);
        list.add((AbstractInsnNode)new FrameNode(3, 0, null, 0, null));
        list.add((AbstractInsnNode)new VarInsnNode(21, 5));
        list.add((AbstractInsnNode)new IntInsnNode(16, 22));
        list.add((AbstractInsnNode)new JumpInsnNode(160, elseIf));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.FontRenderer.getNameRaw(), TransformerMethod.resetStyles.getName(), TransformerMethod.resetStyles.getDescription(), false));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/FontRendererHook", "toggleChromaOn", "()V", false));
        list.add((AbstractInsnNode)new JumpInsnNode(167, endIf));
        return list;
    }
}

