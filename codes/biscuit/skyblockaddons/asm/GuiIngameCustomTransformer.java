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
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiIngameCustomTransformer
implements ITransformer {
    private boolean foundHealthBlock;
    private boolean foundFoodBlock;
    private boolean doneHealth;
    private boolean doneFood;
    private boolean doneArmor;
    private boolean doneMountHealth;

    @Override
    public String[] getClassName() {
        return new String[]{"net.labymod.core_implementation.mc18.gui.GuiIngameCustom"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (!methodNode.name.equals("renderPlayerStatsNew")) continue;
            for (AbstractInsnNode abstractNode : methodNode.instructions) {
                if (abstractNode instanceof LdcInsnNode) {
                    LdcInsnNode ldcInsnNode = (LdcInsnNode)abstractNode;
                    if (!this.doneArmor && "armor".equals(ldcInsnNode.cst)) {
                        methodNode.instructions.insert(ldcInsnNode.getNext(), this.insertCancelArmorRendering());
                        this.doneArmor = true;
                    } else if ("health".equals(ldcInsnNode.cst)) {
                        this.foundHealthBlock = true;
                    } else if ("food".equals(ldcInsnNode.cst)) {
                        this.foundFoodBlock = true;
                    }
                }
                if (abstractNode instanceof JumpInsnNode) {
                    JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractNode;
                    if (!this.doneHealth && this.foundHealthBlock && abstractNode.getOpcode() == 155) {
                        this.doneHealth = true;
                        methodNode.instructions.insert(abstractNode, this.insertCancelHealthRendering(jumpInsnNode.label));
                    }
                    if (!this.doneFood && abstractNode.getOpcode() == 199) {
                        this.doneFood = true;
                        methodNode.instructions.insert(abstractNode, this.insertCancelFoodRendering(jumpInsnNode.label));
                    }
                }
                if (this.doneMountHealth || !this.foundFoodBlock || !(abstractNode instanceof TypeInsnNode) || abstractNode.getOpcode() != 193) continue;
                TypeInsnNode typeInsnNode = (TypeInsnNode)abstractNode;
                if (!typeInsnNode.desc.equals(TransformerClass.EntityLivingBase.getNameRaw()) || typeInsnNode.getNext().getOpcode() != 153 || !(typeInsnNode.getNext() instanceof JumpInsnNode)) continue;
                JumpInsnNode jumpInsnNode = (JumpInsnNode)typeInsnNode.getNext();
                this.doneMountHealth = true;
                methodNode.instructions.insert((AbstractInsnNode)jumpInsnNode, this.insertCancelMountHealthRendering(jumpInsnNode.label));
            }
        }
    }

    private InsnList insertCancelArmorRendering() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiIngameCustomHook", "shouldRenderArmor", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(154, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(3));
        list.add((AbstractInsnNode)new VarInsnNode(54, 22));
        list.add((AbstractInsnNode)notCancelled);
        return list;
    }

    private InsnList insertCancelHealthRendering(LabelNode label) {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiIngameCustomHook", "shouldRenderHealth", "()Z", false));
        list.add((AbstractInsnNode)new JumpInsnNode(153, label));
        return list;
    }

    private InsnList insertCancelFoodRendering(LabelNode label) {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiIngameCustomHook", "shouldRenderFood", "()Z", false));
        list.add((AbstractInsnNode)new JumpInsnNode(153, label));
        return list;
    }

    private InsnList insertCancelMountHealthRendering(LabelNode label) {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiIngameCustomHook", "shouldRenderMountHealth", "()Z", false));
        list.add((AbstractInsnNode)new JumpInsnNode(153, label));
        return list;
    }
}

