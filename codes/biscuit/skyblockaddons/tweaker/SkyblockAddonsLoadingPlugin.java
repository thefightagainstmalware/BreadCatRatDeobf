/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin$MCVersion
 */
package codes.biscuit.skyblockaddons.tweaker;

import codes.biscuit.skyblockaddons.tweaker.SkyblockAddonsDuplicateChecker;
import codes.biscuit.skyblockaddons.tweaker.SkyblockAddonsTransformer;
import java.util.List;
import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(value="1.8.9")
public class SkyblockAddonsLoadingPlugin
implements IFMLLoadingPlugin {
    static List<Object> coremodList;

    public String[] getASMTransformerClass() {
        return new String[]{SkyblockAddonsTransformer.class.getName()};
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return SkyblockAddonsDuplicateChecker.class.getName();
    }

    public void injectData(Map<String, Object> data) {
        coremodList = (List)data.get("coremodList");
    }

    public String getAccessTransformerClass() {
        return null;
    }
}

