/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.newgui.elements;

import codes.biscuit.skyblockaddons.newgui.GuiElement;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import net.minecraft.client.renderer.GlStateManager;

public class TextElement
extends GuiElement<TextElement> {
    private final String text;
    private float scale = 1.0f;

    @Override
    public void render() {
        if (this.scale != 1.0f) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a((float)this.scale, (float)this.scale, (float)this.scale);
        }
        DrawUtils.drawText(this.text, this.getX(), this.getY(), -1);
        if (this.scale != 1.0f) {
            GlStateManager.func_179121_F();
        }
    }

    public TextElement(String text) {
        this.text = text;
    }
}

