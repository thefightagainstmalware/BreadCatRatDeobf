/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 */
package codes.biscuit.skyblockaddons.gui.elements;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.config.PersistentValuesManager;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.features.craftingpatterns.CraftingPattern;
import codes.biscuit.skyblockaddons.gui.elements.CheckBox;
import codes.biscuit.skyblockaddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class CraftingPatternSelection {
    public static final int ICON_SIZE = 32;
    private static final int MARGIN = 2;
    private final Minecraft mc;
    private final int x;
    private final int y;
    private final CheckBox blockIncompleteCheckBox;

    public CraftingPatternSelection(Minecraft mc, int x, int y) {
        this.mc = mc;
        this.x = x;
        this.y = y;
        int checkBoxY = y - 2 - 8;
        String checkBoxText = Message.MESSAGE_BLOCK_INCOMPLETE_PATTERNS.getMessage(new String[0]);
        PersistentValuesManager persistentValuesManager = SkyblockAddons.getInstance().getPersistentValuesManager();
        this.blockIncompleteCheckBox = new CheckBox(mc, x, checkBoxY, 8, checkBoxText, persistentValuesManager.getPersistentValues().isBlockCraftingIncompletePatterns());
        this.blockIncompleteCheckBox.setOnToggleListener(value -> {
            persistentValuesManager.getPersistentValues().setBlockCraftingIncompletePatterns(value);
            persistentValuesManager.saveValues();
        });
    }

    public void draw() {
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        Minecraft.func_71410_x().func_110434_K().func_110577_a(CraftingPattern.ICONS);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        for (CraftingPattern craftingPattern : CraftingPattern.values()) {
            int offset = this.getYOffsetByIndex(craftingPattern.index);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.mc.field_71456_v.func_73729_b(this.x, this.y + offset, 0, offset, 32, 32);
            if (craftingPattern == SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getSelectedCraftingPattern()) continue;
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
            this.mc.field_71456_v.func_73729_b(this.x, this.y + offset, 33, 0, 32, 32);
        }
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
        this.blockIncompleteCheckBox.draw();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.blockIncompleteCheckBox.onMouseClick(mouseX, mouseY, mouseButton);
        if (mouseButton != 0 || mouseX < this.x || mouseX > this.x + 32 || mouseY < this.y || mouseY > this.y + CraftingPattern.values().length * 34) {
            return;
        }
        PersistentValuesManager persistentValuesManager = SkyblockAddons.getInstance().getPersistentValuesManager();
        for (CraftingPattern craftingPattern : CraftingPattern.values()) {
            int offset = this.getYOffsetByIndex(craftingPattern.index);
            if (mouseY <= this.y + offset || mouseY >= this.y + offset + 32 || persistentValuesManager.getPersistentValues().getSelectedCraftingPattern() == craftingPattern) continue;
            SkyblockAddons.getInstance().getUtils().playLoudSound("gui.button.press", 1.0);
            persistentValuesManager.getPersistentValues().setSelectedCraftingPattern(craftingPattern);
            persistentValuesManager.saveValues();
        }
        Utils.blockNextClick = true;
    }

    private int getYOffsetByIndex(int index) {
        return index * 34;
    }
}

