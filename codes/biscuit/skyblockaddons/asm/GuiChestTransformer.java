/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FrameNode
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
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiChestTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.GuiChest.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        MethodNode updateScreen = TransformerMethod.updateScreen.createMethodNode();
        updateScreen.instructions.add(this.updateScreen());
        classNode.methods.add(updateScreen);
        MethodNode onGuiClosed = TransformerMethod.onGuiClosed.createMethodNode();
        onGuiClosed.instructions.add(this.onGuiClosed());
        classNode.methods.add(onGuiClosed);
        MethodNode drawScreen = TransformerMethod.drawScreen.createMethodNode();
        drawScreen.instructions.add(this.drawScreen());
        classNode.methods.add(drawScreen);
        MethodNode initGui = TransformerMethod.initGui.createMethodNode();
        initGui.instructions.add(this.initGui());
        classNode.methods.add(initGui);
        MethodNode keyTyped = TransformerMethod.keyTyped.createMethodNode();
        keyTyped.instructions.add(this.keyTyped());
        classNode.methods.add(keyTyped);
        MethodNode handleMouseClick = TransformerMethod.handleMouseClick.createMethodNode();
        handleMouseClick.instructions.add(this.handleMouseClick());
        classNode.methods.add(handleMouseClick);
        MethodNode mouseClicked = TransformerMethod.mouseClicked.createMethodNode();
        mouseClicked.instructions.add(this.mouseClicked());
        classNode.methods.add(mouseClicked);
        MethodNode mouseReleased = TransformerMethod.mouseReleased.createMethodNode();
        mouseReleased.instructions.add(this.mouseReleased());
        classNode.methods.add(mouseReleased);
        MethodNode mouseClickMove = TransformerMethod.mouseClickMove.createMethodNode();
        mouseClickMove.instructions.add(this.mouseClickMove());
        classNode.methods.add(mouseClickMove);
        block0: for (MethodNode methodNode : classNode.methods) {
            MethodInsnNode methodInsnNode;
            AbstractInsnNode abstractNode;
            ListIterator iterator;
            if (TransformerMethod.drawGuiContainerBackgroundLayer.matches(methodNode)) {
                iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    abstractNode = (AbstractInsnNode)iterator.next();
                    if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 184) continue;
                    methodInsnNode = (MethodInsnNode)abstractNode;
                    if (!methodInsnNode.owner.equals(TransformerClass.GlStateManager.getNameRaw()) || !methodInsnNode.name.equals(TransformerMethod.color.getName())) continue;
                    methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)TransformerField.lowerChestInventory.getField(TransformerClass.GuiChest));
                    methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "color", "(FFFF" + TransformerClass.IInventory.getName() + ")V", false));
                    iterator.remove();
                    continue block0;
                }
                continue;
            }
            if (!TransformerMethod.drawGuiContainerForegroundLayer.matches(methodNode)) continue;
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), this.onRenderChestForegroundLayer());
            iterator = methodNode.instructions.iterator();
            while (iterator.hasNext()) {
                abstractNode = (AbstractInsnNode)iterator.next();
                if (!(abstractNode instanceof MethodInsnNode) || abstractNode.getOpcode() != 182) continue;
                methodInsnNode = (MethodInsnNode)abstractNode;
                if (!methodInsnNode.owner.equals(TransformerClass.FontRenderer.getNameRaw()) || !methodInsnNode.name.equals(TransformerMethod.drawString.getName())) continue;
                methodNode.instructions.insertBefore(abstractNode, (AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "drawString", "(" + TransformerClass.FontRenderer.getName() + "Ljava/lang/String;III)I", false));
                iterator.remove();
            }
        }
    }

    private InsnList onRenderChestForegroundLayer() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "onRenderChestForegroundLayer", "(" + TransformerClass.GuiChest.getName() + ")V", false));
        return list;
    }

    private InsnList updateScreen() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "updateScreen", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.updateScreen.getName(), "()V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList onGuiClosed() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "onGuiClosed", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.onGuiClosed.getName(), "()V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList drawScreen() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 4));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(25, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "drawScreenIslands", "(IILcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        list.add((AbstractInsnNode)new FrameNode(1, 1, new Object[]{"codes/biscuit/skyblockaddons/asm/utils/ReturnValue"}, 0, null));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(23, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.drawScreen.getName(), "(IIF)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.guiLeft.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.guiTop.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "drawScreen", "(II)V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList initGui() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.initGui.getName(), "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.lowerChestInventory.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.guiLeft.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.guiTop.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.fontRendererObj.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "initGui", "(" + TransformerClass.IInventory.getName() + "II" + TransformerClass.FontRenderer.getName() + ")V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList keyTyped() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "keyTyped", "(CI)Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.keyTyped.getName(), "(CI)V", false));
        list.add((AbstractInsnNode)notCancelled);
        list.add((AbstractInsnNode)new FrameNode(3, 0, null, 0, null));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList handleMouseClick() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 5));
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.inventorySlots.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)TransformerField.lowerChestInventory.getField(TransformerClass.GuiChest));
        list.add((AbstractInsnNode)new VarInsnNode(25, 5));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "handleMouseClick", "(" + TransformerClass.Slot.getName() + TransformerClass.Container.getName() + TransformerClass.IInventory.getName() + "Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 5));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        list.add((AbstractInsnNode)new FrameNode(1, 1, new Object[]{"codes/biscuit/skyblockaddons/asm/utils/ReturnValue"}, 0, null));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new VarInsnNode(21, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.handleMouseClick.getName(), "(" + TransformerClass.Slot.getName() + "III)V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList mouseClicked() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 4));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new VarInsnNode(25, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "mouseClicked", "(IIILcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        list.add((AbstractInsnNode)new FrameNode(1, 1, new Object[]{"codes/biscuit/skyblockaddons/asm/utils/ReturnValue"}, 0, null));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.mouseClicked.getName(), "(III)V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList mouseReleased() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 4));
        list.add((AbstractInsnNode)new VarInsnNode(25, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "mouseReleased", "(Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        list.add((AbstractInsnNode)new FrameNode(1, 1, new Object[]{"codes/biscuit/skyblockaddons/asm/utils/ReturnValue"}, 0, null));
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.mouseReleased.getName(), "(III)V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }

    private InsnList mouseClickMove() {
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue"));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add((AbstractInsnNode)new VarInsnNode(58, 6));
        list.add((AbstractInsnNode)new VarInsnNode(25, 6));
        list.add((AbstractInsnNode)new MethodInsnNode(184, "codes/biscuit/skyblockaddons/asm/hooks/GuiChestHook", "mouseClickMove", "(Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V", false));
        list.add((AbstractInsnNode)new VarInsnNode(25, 6));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z", false));
        LabelNode notCancelled = new LabelNode();
        list.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        list.add((AbstractInsnNode)new InsnNode(177));
        list.add((AbstractInsnNode)notCancelled);
        list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new VarInsnNode(21, 1));
        list.add((AbstractInsnNode)new VarInsnNode(21, 2));
        list.add((AbstractInsnNode)new VarInsnNode(21, 3));
        list.add((AbstractInsnNode)new VarInsnNode(22, 4));
        list.add((AbstractInsnNode)new MethodInsnNode(183, TransformerClass.GuiContainer.getNameRaw(), TransformerMethod.mouseClickMove.getName(), "(IIIJ)V", false));
        list.add((AbstractInsnNode)new InsnNode(177));
        return list;
    }
}

