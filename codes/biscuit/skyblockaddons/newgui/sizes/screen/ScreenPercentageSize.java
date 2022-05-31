/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package codes.biscuit.skyblockaddons.newgui.sizes.screen;

import codes.biscuit.skyblockaddons.newgui.sizes.SizeBase;
import net.minecraft.client.Minecraft;

public class ScreenPercentageSize
extends SizeBase {
    private float xPercentage;
    private float yPercentage;

    public ScreenPercentageSize(float xPercentage, float yPercentage) {
        this.xPercentage = xPercentage;
        this.yPercentage = yPercentage;
    }

    public ScreenPercentageSize(float percentage) {
        this(percentage, percentage);
    }

    @Override
    public void updatePositions() {
        this.y = (float)Minecraft.func_71410_x().field_71440_d * this.xPercentage;
        this.x = (float)Minecraft.func_71410_x().field_71443_c * this.yPercentage;
    }

    @Override
    public void updateSizes() {
        this.h = (float)Minecraft.func_71410_x().field_71440_d * this.xPercentage;
        this.w = (float)Minecraft.func_71410_x().field_71443_c * this.yPercentage;
    }
}

