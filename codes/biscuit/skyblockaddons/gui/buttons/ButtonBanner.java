/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.Utils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

public class ButtonBanner
extends GuiButton {
    private SkyblockAddons main = SkyblockAddons.getInstance();
    private static ResourceLocation banner;
    private static BufferedImage bannerImage;
    private static boolean grabbedBanner;
    private long timeOpened = System.currentTimeMillis();
    private static final int WIDTH = 130;

    public ButtonBanner(double x, double y) {
        super(0, (int)x, (int)y, "");
        if (!grabbedBanner) {
            grabbedBanner = true;
            bannerImage = null;
            banner = null;
            SkyblockAddons.runAsync(() -> {
                try {
                    URL url = new URL(this.main.getOnlineData().getBannerImageURL());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setReadTimeout(5000);
                    connection.addRequestProperty("User-Agent", Utils.USER_AGENT);
                    bannerImage = TextureUtil.func_177053_a((InputStream)connection.getInputStream());
                    connection.disconnect();
                    this.field_146120_f = bannerImage.getWidth();
                    this.field_146121_g = bannerImage.getHeight();
                }
                catch (IOException ex) {
                    SkyblockAddons.getLogger().info("Couldn't grab main menu banner image from URL, falling back to local banner.");
                }
            });
        }
        this.field_146128_h -= 65;
        if (bannerImage != null) {
            this.field_146120_f = bannerImage.getWidth();
            this.field_146121_g = bannerImage.getHeight();
        }
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (bannerImage != null && banner == null) {
            banner = Minecraft.func_71410_x().func_110434_K().func_110578_a("banner", new DynamicTexture(bannerImage));
        }
        if (banner != null) {
            int fadeMilis;
            long timeSinceOpen;
            float alphaMultiplier = 1.0f;
            if (this.main.getUtils().isFadingIn() && (timeSinceOpen = System.currentTimeMillis() - this.timeOpened) <= (long)(fadeMilis = 500)) {
                alphaMultiplier = (float)timeSinceOpen / (float)fadeMilis;
            }
            float scale = 130.0f / (float)bannerImage.getWidth();
            this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + 130 && (float)mouseY < (float)this.field_146129_i + (float)bannerImage.getHeight() * scale;
            GlStateManager.func_179147_l();
            if (this.field_146123_n) {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 1.0f));
            } else {
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)(alphaMultiplier * 0.8f));
            }
            mc.func_110434_K().func_110577_a(banner);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
            ButtonBanner.func_146110_a((int)Math.round((float)this.field_146128_h / scale), (int)Math.round((float)this.field_146129_i / scale), (float)0.0f, (float)0.0f, (int)this.field_146120_f, (int)this.field_146121_g, (float)this.field_146120_f, (float)this.field_146121_g);
            GlStateManager.func_179121_F();
        }
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        return this.field_146123_n;
    }
}

