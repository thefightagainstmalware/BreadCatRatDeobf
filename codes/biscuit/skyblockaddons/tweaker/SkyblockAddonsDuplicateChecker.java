/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.FMLRelaunchLog
 *  net.minecraftforge.fml.relauncher.IFMLCallHook
 *  org.apache.logging.log4j.Level
 */
package codes.biscuit.skyblockaddons.tweaker;

import codes.biscuit.skyblockaddons.tweaker.SkyblockAddonsLoadingPlugin;
import codes.biscuit.skyblockaddons.tweaker.SkyblockAddonsTransformer;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import org.apache.logging.log4j.Level;

public class SkyblockAddonsDuplicateChecker
implements IFMLCallHook {
    public Void call() {
        this.logDebug("Searching for duplicate SkyblockAddons installations...");
        try {
            List<Object> coremodList = SkyblockAddonsLoadingPlugin.coremodList;
            Field nameField = coremodList.get(0).getClass().getField("name");
            boolean coreFound = false;
            nameField.setAccessible(true);
            for (Object coreMod : coremodList) {
                String name = (String)nameField.get(coreMod);
                if (!name.equals(SkyblockAddonsLoadingPlugin.class.getSimpleName())) continue;
                if (!coreFound) {
                    coreFound = true;
                    continue;
                }
                throw new RuntimeException("Launch failed because a duplicate installation of SkyblockAddons was found. Please remove it and restart Minecraft!");
            }
            nameField.setAccessible(false);
            this.logDebug("No duplicate installations were found");
        }
        catch (ReflectiveOperationException ex) {
            this.log(Level.ERROR, ex, "An error occurred while checking for duplicate SkyblockAddons installations!");
        }
        return null;
    }

    private void log(Level level, Throwable throwable, String message) {
        String loggerName = "SkyblockAddons/" + this.getClass().getSimpleName();
        if (throwable != null) {
            FMLRelaunchLog.log((String)loggerName, (Level)level, (Throwable)throwable, (String)((SkyblockAddonsTransformer.isDeobfuscated() ? "" : "[" + loggerName + "] ") + message), (Object[])new Object[0]);
        } else {
            FMLRelaunchLog.log((String)loggerName, (Level)level, (String)((SkyblockAddonsTransformer.isDeobfuscated() ? "" : "[" + loggerName + "] ") + message), (Object[])new Object[0]);
        }
    }

    private void logDebug(String message) {
        this.log(Level.DEBUG, null, message);
    }

    public void injectData(Map<String, Object> data) {
    }
}

