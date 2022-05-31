/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 */
package codes.biscuit.skyblockaddons.tweaker.transformer;

import org.objectweb.asm.tree.ClassNode;

public interface ITransformer {
    public String[] getClassName();

    public void transform(ClassNode var1, String var2);

    default public boolean nameMatches(String method, String ... names) {
        for (String name : names) {
            if (!method.equals(name)) continue;
            return true;
        }
        return false;
    }
}

