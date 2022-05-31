/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.IResource
 *  net.minecraft.client.resources.IResourceManager
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.listeners;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.io.IOException;
import java.util.regex.Pattern;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

public class ResourceManagerReloadListener
implements IResourceManagerReloadListener {
    private static final ResourceLocation containerPreviewLocation = new ResourceLocation("skyblockaddons", "containerPreview.png");
    private static final Pattern furfSkyRebornName = Pattern.compile("FurfSky Reborn.*");

    public void func_110549_a(IResourceManager resourceManager) {
        boolean usingFSR = false;
        try {
            IResource containerPreviewTexture = resourceManager.func_110536_a(containerPreviewLocation);
            containerPreviewTexture.func_110527_b().close();
            if (furfSkyRebornName.matcher(TextUtils.stripColor(containerPreviewTexture.func_177240_d())).matches()) {
                usingFSR = true;
            }
        }
        catch (IOException e) {
            SkyblockAddons.getLogger().error("Failed to get containerPreview texture", (Throwable)e);
        }
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main != null) {
            main.getUtils().setUsingFSRcontainerPreviewTexture(usingFSR);
        }
    }
}

