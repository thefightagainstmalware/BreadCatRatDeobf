/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;

public class EntityRendererHook {
    public static void onGetNightVisionBrightness(ReturnValue<Float> returnValue) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isEnabled(Feature.AVOID_BLINKING_NIGHT_VISION)) {
            returnValue.cancel(Float.valueOf(1.0f));
        }
    }

    public static void onRenderScreenPre() {
        SkyblockAddons.getInstance().getGuiManager().render();
    }
}

