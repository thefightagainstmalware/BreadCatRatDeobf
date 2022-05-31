/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.crash.CrashReportCategory
 *  net.minecraft.util.ReportedException
 *  org.apache.http.client.ResponseHandler
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.logging.log4j.Logger
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Language;
import codes.biscuit.skyblockaddons.core.OnlineData;
import codes.biscuit.skyblockaddons.core.seacreatures.SeaCreatureManager;
import codes.biscuit.skyblockaddons.features.SkillXpManager;
import codes.biscuit.skyblockaddons.features.cooldowns.CooldownManager;
import codes.biscuit.skyblockaddons.features.enchantedItemBlacklist.EnchantedItemLists;
import codes.biscuit.skyblockaddons.features.enchantedItemBlacklist.EnchantedItemPlacementBlocker;
import codes.biscuit.skyblockaddons.features.enchants.EnchantManager;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import codes.biscuit.skyblockaddons.utils.skyblockdata.CompactorItem;
import codes.biscuit.skyblockaddons.utils.skyblockdata.ContainerData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.Logger;

public class DataUtils {
    private static final Gson gson = SkyblockAddons.getGson();
    private static final Logger logger = SkyblockAddons.getLogger();
    private static final SkyblockAddons main = SkyblockAddons.getInstance();
    private static final String NO_DATA_RECEIVED_ERROR = "No data received for get request to \"%s\"";
    private static String path;

    public static void readLocalAndFetchOnline() {
        DataUtils.readLocalFileData();
        DataUtils.fetchFromOnline();
    }

    public static void readLocalFileData() {
        Throwable throwable;
        InputStreamReader inputStreamReader2;
        Throwable throwable2;
        InputStream inputStream;
        path = "/data.json";
        try {
            inputStream = DataUtils.class.getResourceAsStream(path);
            throwable2 = null;
            try {
                inputStreamReader2 = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                throwable = null;
                try {
                    main.setOnlineData((OnlineData)gson.fromJson((Reader)inputStreamReader2, OnlineData.class));
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
                finally {
                    if (inputStreamReader2 != null) {
                        if (throwable != null) {
                            try {
                                inputStreamReader2.close();
                            }
                            catch (Throwable throwable4) {
                                throwable.addSuppressed(throwable4);
                            }
                        } else {
                            inputStreamReader2.close();
                        }
                    }
                }
            }
            catch (Throwable inputStreamReader2) {
                throwable2 = inputStreamReader2;
                throw inputStreamReader2;
            }
            finally {
                if (inputStream != null) {
                    if (throwable2 != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable inputStreamReader2) {
                            throwable2.addSuppressed(inputStreamReader2);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the local data file");
        }
        DataUtils.loadLocalizedStrings(false);
        path = "/enchantedItemLists.json";
        try {
            inputStream = DataUtils.class.getResourceAsStream(path);
            throwable2 = null;
            try {
                inputStreamReader2 = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                throwable = null;
                try {
                    EnchantedItemPlacementBlocker.setItemLists((EnchantedItemLists)gson.fromJson((Reader)inputStreamReader2, EnchantedItemLists.class));
                }
                catch (Throwable throwable5) {
                    throwable = throwable5;
                    throw throwable5;
                }
                finally {
                    if (inputStreamReader2 != null) {
                        if (throwable != null) {
                            try {
                                inputStreamReader2.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                        } else {
                            inputStreamReader2.close();
                        }
                    }
                }
            }
            catch (Throwable inputStreamReader3) {
                throwable2 = inputStreamReader3;
                throw inputStreamReader3;
            }
            finally {
                if (inputStream != null) {
                    if (throwable2 != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable inputStreamReader3) {
                            throwable2.addSuppressed(inputStreamReader3);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the local enchanted item lists");
        }
        path = "/containers.json";
        try {
            inputStream = DataUtils.class.getResourceAsStream(path);
            throwable2 = null;
            try {
                inputStreamReader2 = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                throwable = null;
                try {
                    ItemUtils.setContainers((Map)gson.fromJson((Reader)inputStreamReader2, new TypeToken<HashMap<String, ContainerData>>(){}.getType()));
                }
                catch (Throwable throwable7) {
                    throwable = throwable7;
                    throw throwable7;
                }
                finally {
                    if (inputStreamReader2 != null) {
                        if (throwable != null) {
                            try {
                                inputStreamReader2.close();
                            }
                            catch (Throwable throwable8) {
                                throwable.addSuppressed(throwable8);
                            }
                        } else {
                            inputStreamReader2.close();
                        }
                    }
                }
            }
            catch (Throwable inputStreamReader4) {
                throwable2 = inputStreamReader4;
                throw inputStreamReader4;
            }
            finally {
                if (inputStream != null) {
                    if (throwable2 != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable inputStreamReader4) {
                            throwable2.addSuppressed(inputStreamReader4);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the containers map");
        }
        path = "/compactorItems.json";
        try {
            inputStream = DataUtils.class.getResourceAsStream(path);
            throwable2 = null;
            try {
                inputStreamReader2 = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                throwable = null;
                try {
                    ItemUtils.setCompactorItems((Map)gson.fromJson((Reader)inputStreamReader2, new TypeToken<HashMap<String, CompactorItem>>(){}.getType()));
                }
                catch (Throwable throwable9) {
                    throwable = throwable9;
                    throw throwable9;
                }
                finally {
                    if (inputStreamReader2 != null) {
                        if (throwable != null) {
                            try {
                                inputStreamReader2.close();
                            }
                            catch (Throwable throwable10) {
                                throwable.addSuppressed(throwable10);
                            }
                        } else {
                            inputStreamReader2.close();
                        }
                    }
                }
            }
            catch (Throwable inputStreamReader5) {
                throwable2 = inputStreamReader5;
                throw inputStreamReader5;
            }
            finally {
                if (inputStream != null) {
                    if (throwable2 != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable inputStreamReader5) {
                            throwable2.addSuppressed(inputStreamReader5);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the compactor items map");
        }
        path = "/enchants.json";
        try {
            inputStream = DataUtils.class.getResourceAsStream(path);
            throwable2 = null;
            try {
                inputStreamReader2 = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                throwable = null;
                try {
                    EnchantManager.setEnchants((EnchantManager.Enchants)gson.fromJson((Reader)inputStreamReader2, new TypeToken<EnchantManager.Enchants>(){}.getType()));
                }
                catch (Throwable throwable11) {
                    throwable = throwable11;
                    throw throwable11;
                }
                finally {
                    if (inputStreamReader2 != null) {
                        if (throwable != null) {
                            try {
                                inputStreamReader2.close();
                            }
                            catch (Throwable throwable12) {
                                throwable.addSuppressed(throwable12);
                            }
                        } else {
                            inputStreamReader2.close();
                        }
                    }
                }
            }
            catch (Throwable inputStreamReader6) {
                throwable2 = inputStreamReader6;
                throw inputStreamReader6;
            }
            finally {
                if (inputStream != null) {
                    if (throwable2 != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable inputStreamReader6) {
                            throwable2.addSuppressed(inputStreamReader6);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the enchantments file");
        }
        path = "/cooldowns.json";
        try {
            inputStream = DataUtils.class.getResourceAsStream(path);
            throwable2 = null;
            try {
                inputStreamReader2 = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                throwable = null;
                try {
                    CooldownManager.setItemCooldowns((Map)gson.fromJson((Reader)inputStreamReader2, new TypeToken<HashMap<String, Integer>>(){}.getType()));
                }
                catch (Throwable throwable13) {
                    throwable = throwable13;
                    throw throwable13;
                }
                finally {
                    if (inputStreamReader2 != null) {
                        if (throwable != null) {
                            try {
                                inputStreamReader2.close();
                            }
                            catch (Throwable throwable14) {
                                throwable.addSuppressed(throwable14);
                            }
                        } else {
                            inputStreamReader2.close();
                        }
                    }
                }
            }
            catch (Throwable inputStreamReader7) {
                throwable2 = inputStreamReader7;
                throw inputStreamReader7;
            }
            finally {
                if (inputStream != null) {
                    if (throwable2 != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable inputStreamReader7) {
                            throwable2.addSuppressed(inputStreamReader7);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the cooldown data file");
        }
        path = "/skillXp.json";
        try {
            inputStream = DataUtils.class.getResourceAsStream(path);
            throwable2 = null;
            try {
                inputStreamReader2 = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                throwable = null;
                try {
                    main.getSkillXpManager().initialize((SkillXpManager.JsonInput)gson.fromJson((Reader)inputStreamReader2, SkillXpManager.JsonInput.class));
                }
                catch (Throwable throwable15) {
                    throwable = throwable15;
                    throw throwable15;
                }
                finally {
                    if (inputStreamReader2 != null) {
                        if (throwable != null) {
                            try {
                                inputStreamReader2.close();
                            }
                            catch (Throwable throwable16) {
                                throwable.addSuppressed(throwable16);
                            }
                        } else {
                            inputStreamReader2.close();
                        }
                    }
                }
            }
            catch (Throwable throwable17) {
                throwable2 = throwable17;
                throw throwable17;
            }
            finally {
                if (inputStream != null) {
                    if (throwable2 != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable18) {
                            throwable2.addSuppressed(throwable18);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the skill xp data file");
        }
    }

    private static void fetchFromOnline() {
        block21: {
            try (CloseableHttpClient httpClient = HttpClientBuilder.create().setUserAgent(Utils.USER_AGENT).build();){
                logger.info("Trying to fetch online data from the server...");
                String requestUri = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons/development/src/main/resources/data.json";
                OnlineData receivedOnlineData = (OnlineData)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(OnlineData.class));
                if (receivedOnlineData == null) {
                    throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
                }
                logger.info("Successfully fetched online data!");
                main.setOnlineData(receivedOnlineData);
                main.getUpdater().checkForUpdate();
                Language language = main.getConfigValues().getLanguage();
                DataUtils.overwriteCommonJsonMembers(main.getConfigValues().getLanguageConfig(), DataUtils.getOnlineLocalizedStrings(httpClient, language));
                logger.info("Trying to fetch enchanted item lists from the server...");
                requestUri = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons-Data/main/skyblockaddons/enchantedItemLists.json";
                EnchantedItemLists receivedBlacklist = (EnchantedItemLists)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(EnchantedItemLists.class));
                if (receivedBlacklist == null) {
                    throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
                }
                logger.info("Successfully fetched enchanted item lists!");
                EnchantedItemPlacementBlocker.setItemLists(receivedBlacklist);
                logger.info("Trying to fetch containers from the server...");
                requestUri = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons-Data/main/skyblock/containers.json";
                Map receivedContainers = (Map)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(new TypeToken<HashMap<String, ContainerData>>(){}.getType()));
                if (receivedContainers == null) {
                    throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
                }
                logger.info("Successfully fetched containers!");
                ItemUtils.setContainers(receivedContainers);
                logger.info("Trying to fetch compactor items from the server...");
                requestUri = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons-Data/main/skyblock/compactorItems.json";
                Map receivedCompactorItems = (Map)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(new TypeToken<HashMap<String, CompactorItem>>(){}.getType()));
                if (receivedCompactorItems == null) {
                    throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
                }
                logger.info("Successfully fetched compactor items!");
                ItemUtils.setCompactorItems(receivedCompactorItems);
                SeaCreatureManager.getInstance().pullSeaCreatures();
                logger.info("Trying to fetch item enchantments from the server...");
                requestUri = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons-Data/main/skyblock/enchants.json";
                EnchantManager.Enchants receivedEnchants = (EnchantManager.Enchants)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(new TypeToken<EnchantManager.Enchants>(){}.getType()));
                if (receivedEnchants == null) {
                    throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
                }
                logger.info("Successfully fetched item enchantments!");
                EnchantManager.setEnchants(receivedEnchants);
                logger.info("Trying to fetch cooldowns from the server...");
                requestUri = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons-Data/main/skyblock/cooldowns.json";
                Map receivedCooldowns = (Map)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(new TypeToken<HashMap<String, Integer>>(){}.getType()));
                if (receivedCooldowns == null) {
                    throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
                }
                logger.info("Successfully fetched cooldowns!");
                CooldownManager.setItemCooldowns(receivedCooldowns);
                logger.info("Trying to fetch skill xp from the server...");
                requestUri = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockAddons-Data/main/skyblock/skillXp.json";
                SkillXpManager.JsonInput receivedSkillXp = (SkillXpManager.JsonInput)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(SkillXpManager.JsonInput.class));
                if (receivedSkillXp != null) {
                    logger.info("Successfully fetched skill xp!");
                    main.getSkillXpManager().initialize(receivedSkillXp);
                    break block21;
                }
                throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
            }
            catch (JsonSyntaxException | IOException | NullPointerException e) {
                logger.error("There was an error fetching data from the server. The bundled version of the file will be used instead.");
                logger.catching(e);
            }
        }
    }

    public static void loadLocalizedStrings(Language language, boolean loadOnlineStrings) {
        path = "lang/" + language.getPath() + ".json";
        try (InputStream inputStream = DataUtils.class.getClassLoader().getResourceAsStream(path);
             InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);){
            main.getConfigValues().setLanguageConfig((JsonObject)gson.fromJson((Reader)inputStreamReader, JsonObject.class));
        }
        catch (Exception ex) {
            DataUtils.handleFileReadException(ex, "Failed to read the local localized strings");
        }
        if (loadOnlineStrings) {
            DataUtils.loadOnlineLocalizedStrings(language);
        }
    }

    public static void loadOnlineLocalizedStrings(Language language) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setUserAgent(Utils.USER_AGENT).build();){
            JsonObject localLanguageConfig = main.getConfigValues().getLanguageConfig();
            JsonObject onlineLanguageConfig = DataUtils.getOnlineLocalizedStrings(httpClient, language);
            if (localLanguageConfig != null) {
                DataUtils.overwriteCommonJsonMembers(localLanguageConfig, onlineLanguageConfig);
            } else {
                logger.warn("Local language configuration was null, it will be replaced with the online version.");
                main.getConfigValues().setLanguageConfig(onlineLanguageConfig);
            }
        }
        catch (JsonSyntaxException | IOException e) {
            logger.error("There was an error fetching data from the server. The bundled version of the file will be used instead.");
            logger.catching(e);
        }
    }

    public static void loadLocalizedStrings(boolean loadOnlineStrings) {
        DataUtils.loadLocalizedStrings(main.getConfigValues().getLanguage(), loadOnlineStrings);
    }

    private static <T> ResponseHandler<T> createResponseHandler(Type T) {
        return response -> {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        };
    }

    private static <T> ResponseHandler<T> createLegacyResponseHandler(Type T) {
        return response -> {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        };
    }

    private static JsonObject getOnlineLocalizedStrings(CloseableHttpClient httpClient, Language language) throws IOException {
        logger.info("Trying to fetch localized strings from the server for " + language.name() + "...");
        String requestUri = String.format(main.getOnlineData().getLanguageJSONFormat(), language.getPath());
        JsonObject receivedLocalizedStrings = (JsonObject)httpClient.execute((HttpUriRequest)new HttpGet(requestUri), DataUtils.createLegacyResponseHandler(JsonObject.class));
        if (receivedLocalizedStrings != null) {
            logger.info("Successfully fetched localized strings!");
            return receivedLocalizedStrings;
        }
        throw new NullPointerException(String.format(NO_DATA_RECEIVED_ERROR, requestUri));
    }

    private static void handleFileReadException(Throwable exception, String description) {
        CrashReport crashReport = CrashReport.func_85055_a((Throwable)exception, (String)description);
        CrashReportCategory category = crashReport.func_85058_a("File being read");
        category.func_71507_a("File Path", (Object)path);
        Throwable[] suppressedExceptions = exception.getSuppressed();
        if (suppressedExceptions.length > 0) {
            for (int i = 0; i < suppressedExceptions.length; ++i) {
                category.func_71499_a("Suppressed Exception " + i, suppressedExceptions[i]);
            }
        }
        throw new ReportedException(crashReport);
    }

    private static void overwriteCommonJsonMembers(JsonObject baseObject, JsonObject otherObject) {
        for (Map.Entry entry : otherObject.entrySet()) {
            String memberName = (String)entry.getKey();
            JsonElement otherElement = (JsonElement)entry.getValue();
            if (otherElement.isJsonObject()) {
                JsonObject baseElementObject;
                if (baseObject.has(memberName) && baseObject.get(memberName).isJsonObject()) {
                    baseElementObject = baseObject.getAsJsonObject(memberName);
                    DataUtils.overwriteCommonJsonMembers(baseElementObject, otherElement.getAsJsonObject());
                    continue;
                }
                baseElementObject = new JsonObject();
                baseObject.add(memberName, (JsonElement)baseElementObject);
                DataUtils.overwriteCommonJsonMembers(baseElementObject, otherElement.getAsJsonObject());
                continue;
            }
            if (!otherElement.isJsonPrimitive() || !otherElement.getAsJsonPrimitive().isString()) continue;
            baseObject.add(memberName, otherElement);
        }
    }
}

