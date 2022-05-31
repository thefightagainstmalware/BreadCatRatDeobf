/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.TypeAdapterFactory
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.client.resources.SimpleReloadableResourceManager
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.command.ICommand
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLModDisabledEvent
 *  net.minecraftforge.fml.common.event.FMLPostInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.message.MessageFactory
 */
package codes.biscuit.skyblockaddons;

import codes.biscuit.skyblockaddons.commands.SkyblockAddonsCommand;
import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.config.PersistentValuesManager;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.OnlineData;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonManager;
import codes.biscuit.skyblockaddons.features.EntityOutlines.EntityOutlineRenderer;
import codes.biscuit.skyblockaddons.features.EntityOutlines.FeatureDungeonTeammateOutlines;
import codes.biscuit.skyblockaddons.features.EntityOutlines.FeatureItemOutlines;
import codes.biscuit.skyblockaddons.features.EntityOutlines.FeatureTrackerQuest;
import codes.biscuit.skyblockaddons.features.SkillXpManager;
import codes.biscuit.skyblockaddons.features.discordrpc.DiscordRPCManager;
import codes.biscuit.skyblockaddons.gui.IslandWarpGui;
import codes.biscuit.skyblockaddons.gui.SkyblockAddonsGui;
import codes.biscuit.skyblockaddons.listeners.GuiScreenListener;
import codes.biscuit.skyblockaddons.listeners.NetworkListener;
import codes.biscuit.skyblockaddons.listeners.PlayerListener;
import codes.biscuit.skyblockaddons.listeners.RenderListener;
import codes.biscuit.skyblockaddons.listeners.ResourceManagerReloadListener;
import codes.biscuit.skyblockaddons.misc.SkyblockKeyBinding;
import codes.biscuit.skyblockaddons.misc.Updater;
import codes.biscuit.skyblockaddons.misc.scheduler.NewScheduler;
import codes.biscuit.skyblockaddons.misc.scheduler.Scheduler;
import codes.biscuit.skyblockaddons.misc.scheduler.SkyblockRunnable;
import codes.biscuit.skyblockaddons.newgui.GuiManager;
import codes.biscuit.skyblockaddons.tweaker.SkyblockAddonsTransformer;
import codes.biscuit.skyblockaddons.utils.DataUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.InventoryUtils;
import codes.biscuit.skyblockaddons.utils.SkyblockAddonsMessageFactory;
import codes.biscuit.skyblockaddons.utils.Utils;
import codes.biscuit.skyblockaddons.utils.gson.GsonInitializableTypeAdapter;
import codes.biscuit.skyblockaddons.utils.gson.PatternAdapter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFactory;

@Mod(modid="skyblockaddons", name="SkyblockAddons", version="1.6.1", clientSideOnly=true, acceptedMinecraftVersions="[1.8.9]")
public class SkyblockAddons {
    public static final String MOD_ID = "skyblockaddons";
    public static final String MOD_NAME = "SkyblockAddons";
    public static String VERSION = "1.6.1";
    private static SkyblockAddons instance;
    private static boolean fullyInitialized;
    private static final Gson GSON;
    private static final Executor THREAD_EXECUTOR;
    private ConfigValues configValues;
    private PersistentValuesManager persistentValuesManager;
    private final PlayerListener playerListener;
    private final GuiScreenListener guiScreenListener;
    private final RenderListener renderListener;
    private final ResourceManagerReloadListener resourceManagerReloadListener;
    private final InventoryUtils inventoryUtils;
    private final Utils utils;
    private final Updater updater;
    private OnlineData onlineData;
    private final DiscordRPCManager discordRPCManager;
    private final Scheduler scheduler;
    private final NewScheduler newScheduler;
    private final DungeonManager dungeonManager;
    private final GuiManager guiManager;
    private final SkillXpManager skillXpManager;
    private boolean usingLabymod;
    private boolean usingOofModv1;
    private boolean usingPatcher;
    private boolean devMode;
    private final List<SkyblockKeyBinding> keyBindings = new LinkedList<SkyblockKeyBinding>();
    private final Set<Integer> registeredFeatureIDs = new HashSet<Integer>();

    public SkyblockAddons() {
        instance = this;
        this.playerListener = new PlayerListener();
        this.guiScreenListener = new GuiScreenListener();
        this.renderListener = new RenderListener();
        this.resourceManagerReloadListener = new ResourceManagerReloadListener();
        this.inventoryUtils = new InventoryUtils();
        this.utils = new Utils();
        this.updater = new Updater();
        this.scheduler = new Scheduler();
        this.newScheduler = new NewScheduler();
        this.dungeonManager = new DungeonManager();
        this.discordRPCManager = new DiscordRPCManager();
        this.guiManager = new GuiManager();
        this.skillXpManager = new SkillXpManager();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        this.configValues = new ConfigValues(e.getSuggestedConfigurationFile());
        this.persistentValuesManager = new PersistentValuesManager(e.getModConfigurationDirectory());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register((Object)new NetworkListener());
        MinecraftForge.EVENT_BUS.register((Object)this.playerListener);
        MinecraftForge.EVENT_BUS.register((Object)this.guiScreenListener);
        MinecraftForge.EVENT_BUS.register((Object)this.renderListener);
        MinecraftForge.EVENT_BUS.register((Object)this.scheduler);
        MinecraftForge.EVENT_BUS.register((Object)this.newScheduler);
        MinecraftForge.EVENT_BUS.register((Object)new FeatureItemOutlines());
        MinecraftForge.EVENT_BUS.register((Object)new FeatureDungeonTeammateOutlines());
        MinecraftForge.EVENT_BUS.register((Object)new EntityOutlineRenderer());
        MinecraftForge.EVENT_BUS.register((Object)new FeatureTrackerQuest());
        ((SimpleReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a((IResourceManagerReloadListener)this.resourceManagerReloadListener);
        ClientCommandHandler.instance.func_71560_a((ICommand)new SkyblockAddonsCommand());
        Collections.addAll(this.keyBindings, new SkyblockKeyBinding("open_settings", 0, Message.SETTING_SETTINGS), new SkyblockKeyBinding("edit_gui", 0, Message.SETTING_EDIT_LOCATIONS), new SkyblockKeyBinding("lock_slot", 38, Message.SETTING_LOCK_SLOT), new SkyblockKeyBinding("freeze_backpack", 33, Message.SETTING_FREEZE_BACKPACK_PREVIEW), new SkyblockKeyBinding("copy_NBT", 157, Message.KEY_DEVELOPER_COPY_NBT));
        this.registerKeyBindings(this.keyBindings);
        this.getDeveloperCopyNBTKey().deRegister();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        this.configValues.loadValues();
        DataUtils.readLocalAndFetchOnline();
        this.persistentValuesManager.loadValues();
        this.setKeyBindingDescriptions();
        this.usingLabymod = this.utils.isModLoaded("labymod");
        this.usingOofModv1 = this.utils.isModLoaded("refractionoof", "1.0");
        this.usingPatcher = this.utils.isModLoaded("patcher");
        this.scheduleMagmaBossCheck();
        for (Feature feature : Feature.values()) {
            if (feature.isGuiFeature()) {
                feature.getSettings().add(EnumUtils.FeatureSetting.GUI_SCALE);
            }
            if (feature.isColorFeature()) {
                feature.getSettings().add(EnumUtils.FeatureSetting.COLOR);
            }
            if (feature.getGuiFeatureData() == null || feature.getGuiFeatureData().getDrawType() != EnumUtils.DrawType.BAR) continue;
            feature.getSettings().add(EnumUtils.FeatureSetting.GUI_SCALE_X);
            feature.getSettings().add(EnumUtils.FeatureSetting.GUI_SCALE_Y);
        }
        if (this.configValues.isEnabled(Feature.FANCY_WARP_MENU)) {
            for (Enum enum_ : IslandWarpGui.Island.values()) {
                Minecraft.func_71410_x().func_110434_K().func_110577_a(((IslandWarpGui.Island)enum_).getResourceLocation());
            }
        }
        Minecraft.func_71410_x().func_110434_K().func_110577_a(SkyblockAddonsGui.LOGO);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(SkyblockAddonsGui.LOGO_GLOW);
        fullyInitialized = true;
    }

    @Mod.EventHandler
    public void stop(FMLModDisabledEvent e) {
        this.discordRPCManager.stop();
    }

    private void scheduleMagmaBossCheck() {
        this.newScheduler.scheduleRepeatingTask(new SkyblockRunnable(){

            @Override
            public void run() {
                if (Minecraft.func_71410_x() != null && Minecraft.func_71410_x().field_71439_g != null) {
                    SkyblockAddons.this.utils.fetchMagmaBossEstimate();
                    this.cancel();
                }
            }
        }, 100, 100);
    }

    public KeyBinding getOpenSettingsKey() {
        return this.keyBindings.get(0).getKeyBinding();
    }

    public KeyBinding getOpenEditLocationsKey() {
        return this.keyBindings.get(1).getKeyBinding();
    }

    public KeyBinding getLockSlotKey() {
        return this.keyBindings.get(2).getKeyBinding();
    }

    public KeyBinding getFreezeBackpackKey() {
        return this.keyBindings.get(3).getKeyBinding();
    }

    public SkyblockKeyBinding getDeveloperCopyNBTKey() {
        return this.keyBindings.get(4);
    }

    public void registerKeyBindings(List<SkyblockKeyBinding> keyBindings) {
        for (SkyblockKeyBinding keybinding : keyBindings) {
            keybinding.register();
        }
    }

    public void setKeyBindingDescriptions() {
        for (SkyblockKeyBinding skyblockKeyBinding : this.keyBindings) {
            skyblockKeyBinding.getKeyBinding().field_74515_c = skyblockKeyBinding.getMessage().getMessage(new String[0]);
        }
    }

    public static Gson getGson() {
        return GSON;
    }

    public static Logger getLogger() {
        String fullClassName = new Throwable().getStackTrace()[1].getClassName();
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(46) + 1);
        String loggerName = "SkyblockAddons/" + simpleClassName;
        if (SkyblockAddonsTransformer.isDeobfuscated()) {
            return LogManager.getLogger((String)loggerName);
        }
        return LogManager.getLogger((String)loggerName, (MessageFactory)new SkyblockAddonsMessageFactory(loggerName));
    }

    public static void runAsync(Runnable runnable) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String fullClassName = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(46) + 1);
        THREAD_EXECUTOR.execute(() -> {
            SkyblockAddons.getLogger().info("Started asynchronous task from " + simpleClassName + "#" + methodName + ".");
            runnable.run();
            SkyblockAddons.getLogger().info("Asynchronous task from " + simpleClassName + "#" + methodName + " has finished.");
        });
    }

    public ConfigValues getConfigValues() {
        return this.configValues;
    }

    public PersistentValuesManager getPersistentValuesManager() {
        return this.persistentValuesManager;
    }

    public PlayerListener getPlayerListener() {
        return this.playerListener;
    }

    public GuiScreenListener getGuiScreenListener() {
        return this.guiScreenListener;
    }

    public RenderListener getRenderListener() {
        return this.renderListener;
    }

    public ResourceManagerReloadListener getResourceManagerReloadListener() {
        return this.resourceManagerReloadListener;
    }

    public InventoryUtils getInventoryUtils() {
        return this.inventoryUtils;
    }

    public Utils getUtils() {
        return this.utils;
    }

    public Updater getUpdater() {
        return this.updater;
    }

    public OnlineData getOnlineData() {
        return this.onlineData;
    }

    public DiscordRPCManager getDiscordRPCManager() {
        return this.discordRPCManager;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public NewScheduler getNewScheduler() {
        return this.newScheduler;
    }

    public DungeonManager getDungeonManager() {
        return this.dungeonManager;
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }

    public SkillXpManager getSkillXpManager() {
        return this.skillXpManager;
    }

    public boolean isUsingLabymod() {
        return this.usingLabymod;
    }

    public boolean isUsingOofModv1() {
        return this.usingOofModv1;
    }

    public boolean isUsingPatcher() {
        return this.usingPatcher;
    }

    public boolean isDevMode() {
        return this.devMode;
    }

    public List<SkyblockKeyBinding> getKeyBindings() {
        return this.keyBindings;
    }

    public static SkyblockAddons getInstance() {
        return instance;
    }

    public static boolean isFullyInitialized() {
        return fullyInitialized;
    }

    public void setOnlineData(OnlineData onlineData) {
        this.onlineData = onlineData;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public Set<Integer> getRegisteredFeatureIDs() {
        return this.registeredFeatureIDs;
    }

    static {
        GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(EnumMap.class, type -> {
            Type[] types = ((ParameterizedType)type).getActualTypeArguments();
            return new EnumMap((Class)types[0]);
        }).registerTypeAdapterFactory((TypeAdapterFactory)new GsonInitializableTypeAdapter()).registerTypeAdapter(Pattern.class, (Object)new PatternAdapter()).create();
        THREAD_EXECUTOR = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("SkyblockAddons - #%d").build());
        if (VERSION.contains("@")) {
            VERSION = "1.6.0";
        }
    }
}

