/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.FieldInsnNode
 */
package codes.biscuit.skyblockaddons.asm.utils;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerField;
import org.objectweb.asm.tree.FieldInsnNode;

public class ASMUtils {
    public static FieldInsnNode getField(TransformerField field, TransformerClass targetClass) {
        return new FieldInsnNode(180, targetClass.getNameRaw(), field.getName(), field.getType());
    }
}

