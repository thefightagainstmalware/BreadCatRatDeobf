/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.features;

import codes.biscuit.skyblockaddons.utils.ColorCode;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlayerArmorProgress {
    private final ItemStack itemStack;
    private String percent;
    private String defence;

    public SlayerArmorProgress(ItemStack itemStack) {
        this.itemStack = new ItemStack(itemStack.func_77973_b());
        this.percent = "55";
        this.defence = "\u00a7a40\u2748";
        this.setHelmetColor();
    }

    public SlayerArmorProgress(ItemStack itemStack, String percent, String defence) {
        this.itemStack = itemStack;
        this.percent = percent;
        this.defence = defence;
    }

    private void setHelmetColor() {
        if (this.itemStack.func_77973_b().equals(Items.field_151024_Q)) {
            ((ItemArmor)this.itemStack.func_77973_b()).func_82813_b(this.itemStack, ColorCode.BLACK.getColor());
        }
    }

    public void setDefence(String defence) {
        this.defence = defence;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public String getPercent() {
        return this.percent;
    }

    public String getDefence() {
        return this.defence;
    }
}

