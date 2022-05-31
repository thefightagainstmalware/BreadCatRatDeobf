/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.core.Feature;
import net.minecraft.client.gui.GuiButton;

public class ButtonFeature
extends GuiButton {
    public Feature feature;

    ButtonFeature(int buttonId, int x, int y, String buttonText, Feature feature) {
        super(buttonId, x, y, buttonText);
        this.feature = feature;
    }

    public Feature getFeature() {
        return this.feature;
    }
}

