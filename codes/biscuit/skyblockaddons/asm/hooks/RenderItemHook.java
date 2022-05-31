/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.resources.model.IBakedModel
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderItemHook {
    private static final ResourceLocation BLANK = new ResourceLocation("skyblockaddons", "blank.png");

    public static void renderToxicArrowPoisonEffect(IBakedModel model, ItemStack stack) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.TURN_BOW_GREEN_WHEN_USING_TOXIC_ARROW_POISON) && main.getInventoryUtils().isUsingToxicArrowPoison() && Items.field_151031_f.equals(stack.func_77973_b()) && main.getUtils().itemIsInHotbar(stack)) {
            TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
            GlStateManager.func_179132_a((boolean)false);
            GlStateManager.func_179143_c((int)514);
            GlStateManager.func_179140_f();
            GlStateManager.func_179112_b((int)768, (int)1);
            textureManager.func_110577_a(BLANK);
            GlStateManager.func_179128_n((int)5890);
            GlStateManager.func_179094_E();
            Minecraft.func_71410_x().func_175599_af().func_175035_a(model, 538753601);
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n((int)5888);
            GlStateManager.func_179112_b((int)770, (int)771);
            GlStateManager.func_179145_e();
            GlStateManager.func_179143_c((int)515);
            GlStateManager.func_179132_a((boolean)true);
            textureManager.func_110577_a(TextureMap.field_110575_b);
        }
    }
}

