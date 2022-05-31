/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.IChatComponent
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import net.minecraft.util.IChatComponent;

public class GuiNewChatHook {
    public static String getUnformattedText(IChatComponent iChatComponent) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main != null && main.isDevMode()) {
            return iChatComponent.func_150254_d();
        }
        return iChatComponent.func_150260_c();
    }
}

