/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonToggleNew;
import codes.biscuit.skyblockaddons.gui.buttons.IslandButton;
import codes.biscuit.skyblockaddons.gui.buttons.IslandMarkerButton;
import codes.biscuit.skyblockaddons.misc.scheduler.SkyblockRunnable;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class IslandWarpGui
extends GuiScreen {
    private static Marker doubleWarpMarker;
    private static int TOTAL_WIDTH;
    private static int TOTAL_HEIGHT;
    public static float SHIFT_LEFT;
    public static float SHIFT_TOP;
    private final Map<Marker, UnlockedStatus> markers;
    private Marker selectedMarker;
    private boolean guiIsActualWarpMenu = false;
    private boolean foundAdvancedWarpToggle = false;
    public static float ISLAND_SCALE;
    public static float IMAGE_SCALED_DOWN_FACTOR;

    public IslandWarpGui() {
        EnumMap<Marker, UnlockedStatus> markers = new EnumMap<Marker, UnlockedStatus>(Marker.class);
        for (Marker marker : Marker.values()) {
            markers.put(marker, UnlockedStatus.UNLOCKED);
        }
        this.markers = markers;
    }

    public IslandWarpGui(Map<Marker, UnlockedStatus> markers) {
        this.markers = markers;
        this.guiIsActualWarpMenu = true;
    }

    public void func_73866_w_() {
        SkyblockAddons main = SkyblockAddons.getInstance();
        EnumMap<Island, UnlockedStatus> islands = new EnumMap<Island, UnlockedStatus>(Island.class);
        for (Map.Entry<Marker, UnlockedStatus> entry : this.markers.entrySet()) {
            Island island = entry.getKey().getIsland();
            UnlockedStatus currentStatus = (UnlockedStatus)((Object)islands.get((Object)island));
            UnlockedStatus newStatus = entry.getValue();
            if (currentStatus != null && newStatus.ordinal() <= currentStatus.ordinal()) continue;
            islands.put(island, newStatus);
        }
        for (Map.Entry<Marker, UnlockedStatus> entry : islands.entrySet()) {
            this.field_146292_n.add(new IslandButton((Island)((Object)entry.getKey()), entry.getValue(), this.markers));
        }
        int screenWidth = this.field_146297_k.field_71443_c;
        int n = this.field_146297_k.field_71440_d;
        float scale = ISLAND_SCALE = 6.481481E-4f * (float)n;
        float totalWidth = (float)TOTAL_WIDTH * scale;
        float totalHeight = (float)TOTAL_HEIGHT * scale;
        SHIFT_LEFT = ((float)screenWidth / 2.0f - totalWidth / 2.0f) / scale;
        SHIFT_TOP = ((float)n / 2.0f - totalHeight / 2.0f) / scale;
        int x = Math.round((float)screenWidth / ISLAND_SCALE - SHIFT_LEFT - 475.0f);
        int y = Math.round((float)n / ISLAND_SCALE - SHIFT_TOP);
        if (this.guiIsActualWarpMenu) {
            this.field_146292_n.add(new ButtonToggleNew(x, y - 30 - 180, 50, () -> {
                GuiScreen guiScreen = Minecraft.func_71410_x().field_71462_r;
                if (guiScreen instanceof GuiChest) {
                    ItemStack toggleAdvancedModeItem;
                    GuiChest gui = (GuiChest)guiScreen;
                    Slot toggleAdvancedModeSlot = gui.field_147002_h.func_75139_a(51);
                    if (toggleAdvancedModeSlot != null && toggleAdvancedModeSlot.func_75216_d() && Items.field_151100_aR == (toggleAdvancedModeItem = toggleAdvancedModeSlot.func_75211_c()).func_77973_b()) {
                        int damage = toggleAdvancedModeItem.func_77952_i();
                        if (damage == 10) {
                            this.foundAdvancedWarpToggle = true;
                            return true;
                        }
                        if (damage == 8) {
                            this.foundAdvancedWarpToggle = true;
                            return false;
                        }
                    }
                }
                return false;
            }, () -> {
                if (!this.foundAdvancedWarpToggle) {
                    return;
                }
                GuiScreen guiScreen = Minecraft.func_71410_x().field_71462_r;
                if (guiScreen instanceof GuiChest) {
                    GuiChest gui = (GuiChest)guiScreen;
                    this.field_146297_k.field_71442_b.func_78753_a(gui.field_147002_h.field_75152_c, 51, 0, 0, (EntityPlayer)this.field_146297_k.field_71439_g);
                }
            }));
            this.field_146292_n.add(new ButtonToggleNew(x, y - 30 - 120, 50, () -> main.getConfigValues().isEnabled(Feature.FANCY_WARP_MENU), () -> {
                if (main.getConfigValues().getDisabledFeatures().contains((Object)Feature.FANCY_WARP_MENU)) {
                    main.getConfigValues().getDisabledFeatures().remove((Object)Feature.FANCY_WARP_MENU);
                } else {
                    main.getConfigValues().getDisabledFeatures().add(Feature.FANCY_WARP_MENU);
                }
            }));
        }
        this.field_146292_n.add(new ButtonToggleNew(x, y - 30 - 60, 50, () -> main.getConfigValues().isEnabled(Feature.DOUBLE_WARP), () -> {
            if (main.getConfigValues().getDisabledFeatures().contains((Object)Feature.DOUBLE_WARP)) {
                main.getConfigValues().getDisabledFeatures().remove((Object)Feature.DOUBLE_WARP);
            } else {
                main.getConfigValues().getDisabledFeatures().add(Feature.DOUBLE_WARP);
            }
        }));
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.field_146297_k);
        int guiScale = sr.func_78325_e();
        int startColor = new Color(0, 0, 0, Math.round(85.0f)).getRGB();
        int endColor = new Color(0, 0, 0, Math.round(127.5f)).getRGB();
        this.func_73733_a(0, 0, sr.func_78326_a(), sr.func_78328_b(), startColor, endColor);
        this.func_73732_a(this.field_146297_k.field_71466_p, Translations.getMessage("warpMenu.click", new Object[0]), sr.func_78326_a() / 2, 10, -1);
        this.func_73732_a(this.field_146297_k.field_71466_p, Translations.getMessage("warpMenu.mustUnlock", new Object[0]), sr.func_78326_a() / 2, 20, -1);
        GlStateManager.func_179094_E();
        float scale = ISLAND_SCALE = 6.481481E-4f * (float)this.field_146297_k.field_71440_d;
        GlStateManager.func_179152_a((float)(1.0f / (float)guiScale), (float)(1.0f / (float)guiScale), (float)1.0f);
        GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
        float totalWidth = (float)TOTAL_WIDTH * scale;
        float totalHeight = (float)TOTAL_HEIGHT * scale;
        SHIFT_LEFT = ((float)this.field_146297_k.field_71443_c / 2.0f - totalWidth / 2.0f) / scale;
        SHIFT_TOP = ((float)this.field_146297_k.field_71440_d / 2.0f - totalHeight / 2.0f) / scale;
        GlStateManager.func_179109_b((float)SHIFT_LEFT, (float)SHIFT_TOP, (float)0.0f);
        GlStateManager.func_179141_d();
        GlStateManager.func_179147_l();
        IslandButton lastHoveredButton = null;
        for (GuiButton button : this.field_146292_n) {
            if (!(button instanceof IslandButton)) continue;
            IslandButton islandButton = (IslandButton)button;
            islandButton.drawButton(this.field_146297_k, mouseX, mouseY, false);
            if (!islandButton.isHovering()) continue;
            if (lastHoveredButton != null) {
                lastHoveredButton.setDisableHover(true);
            }
            lastHoveredButton = islandButton;
        }
        for (GuiButton guiButton : this.field_146292_n) {
            guiButton.func_146112_a(this.field_146297_k, mouseX, mouseY);
        }
        int x = Math.round((float)this.field_146297_k.field_71443_c / ISLAND_SCALE - SHIFT_LEFT - 500.0f);
        int y = Math.round((float)this.field_146297_k.field_71440_d / ISLAND_SCALE - SHIFT_TOP);
        GlStateManager.func_179094_E();
        float textScale = 3.0f;
        GlStateManager.func_179152_a((float)textScale, (float)textScale, (float)1.0f);
        if (this.guiIsActualWarpMenu) {
            this.field_146297_k.field_71466_p.func_175063_a(Feature.WARP_ADVANCED_MODE.getMessage(new String[0]), (float)x / textScale + 50.0f, (float)(y - 30 - 180) / textScale + 5.0f, -1);
            this.field_146297_k.field_71466_p.func_175063_a(Feature.FANCY_WARP_MENU.getMessage(new String[0]), (float)x / textScale + 50.0f, (float)(y - 30 - 120) / textScale + 5.0f, -1);
        }
        this.field_146297_k.field_71466_p.func_175063_a(Feature.DOUBLE_WARP.getMessage(new String[0]), (float)x / textScale + 50.0f, (float)(y - 30 - 60) / textScale + 5.0f, -1);
        GlStateManager.func_179121_F();
        GlStateManager.func_179121_F();
        this.detectClosestMarker(mouseX, mouseY);
    }

    public void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && this.selectedMarker != null) {
            Minecraft.func_71410_x().func_147108_a(null);
            if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.DOUBLE_WARP)) {
                doubleWarpMarker = this.selectedMarker;
                SkyblockAddons.getInstance().getNewScheduler().scheduleDelayedTask(new SkyblockRunnable(){

                    @Override
                    public void run() {
                        if (doubleWarpMarker != null) {
                            doubleWarpMarker = null;
                        }
                    }
                }, 20);
            }
            if (this.selectedMarker != Marker.DWARVEN_FORGE) {
                Minecraft.func_71410_x().field_71439_g.func_71165_d("/warp " + this.selectedMarker.getWarpName());
            } else {
                Minecraft.func_71410_x().field_71439_g.func_71165_d("/warp" + this.selectedMarker.getWarpName());
            }
        }
        int minecraftScale = new ScaledResolution(this.field_146297_k).func_78325_e();
        float islandGuiScale = ISLAND_SCALE;
        mouseX *= minecraftScale;
        mouseY *= minecraftScale;
        mouseX = (int)((float)mouseX / islandGuiScale);
        mouseY = (int)((float)mouseY / islandGuiScale);
        mouseX = (int)((float)mouseX - SHIFT_LEFT);
        mouseY = (int)((float)mouseY - SHIFT_TOP);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void detectClosestMarker(int mouseX, int mouseY) {
        int minecraftScale = new ScaledResolution(this.field_146297_k).func_78325_e();
        float islandGuiScale = ISLAND_SCALE;
        mouseX *= minecraftScale;
        mouseY *= minecraftScale;
        mouseX = (int)((float)mouseX / islandGuiScale);
        mouseY = (int)((float)mouseY / islandGuiScale);
        mouseX = (int)((float)mouseX - SHIFT_LEFT);
        mouseY = (int)((float)mouseY - SHIFT_TOP);
        Marker hoveredMarker = null;
        double markerDistance = 91.0;
        for (GuiButton button : this.field_146292_n) {
            if (!(button instanceof IslandButton)) continue;
            IslandButton islandButton = (IslandButton)button;
            for (IslandMarkerButton marker : islandButton.getMarkerButtons()) {
                double distance = marker.getDistance(mouseX, mouseY);
                if (distance == -1.0 || !(distance < markerDistance)) continue;
                hoveredMarker = marker.getMarker();
                markerDistance = distance;
            }
        }
        this.selectedMarker = hoveredMarker;
    }

    public static Marker getDoubleWarpMarker() {
        return doubleWarpMarker;
    }

    public static void setDoubleWarpMarker(Marker doubleWarpMarker) {
        IslandWarpGui.doubleWarpMarker = doubleWarpMarker;
    }

    public Map<Marker, UnlockedStatus> getMarkers() {
        return this.markers;
    }

    static {
        IMAGE_SCALED_DOWN_FACTOR = 0.75f;
    }

    public static enum UnlockedStatus {
        UNKNOWN(Translations.getMessage("warpMenu.unknown", new Object[0])),
        NOT_UNLOCKED(Translations.getMessage("warpMenu.notUnlocked", new Object[0])),
        IN_COMBAT(Translations.getMessage("warpMenu.inCombat", new Object[0])),
        UNLOCKED(null);

        private final String message;

        private UnlockedStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public static enum Marker {
        PRIVATE_ISLAND("home", Translations.getMessage("warpMenu.home", new Object[0]), Island.PRIVATE_ISLAND, true, 72, 90),
        HUB("hub", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.HUB, true, 600, 200),
        CASTLE("castle", "Castle", Island.HUB, 130, 80),
        DARK_AUCTION("da", "Sirius Shack", Island.HUB, 385, 415),
        CRYPT("crypt", "Crypts", Island.HUB, 550, 100),
        DUNGEON_HUB("dungeon_hub", "Dungeon Hub", Island.HUB, false, 400, 175),
        SPIDERS_DEN("spider", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.SPIDERS_DEN, true, 345, 240),
        SPIDERS_DEN_NEST("nest", "Top of Nest", Island.SPIDERS_DEN, 450, 30),
        THE_PARK("park", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.THE_PARK, true, 263, 308),
        HOWLING_CAVE("howl", "Howling Cave", Island.THE_PARK, 254, 202),
        THE_PARK_JUNGLE("jungle", "Jungle", Island.THE_PARK, 194, 82),
        THE_END("end", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.THE_END, true, 440, 291),
        DRAGONS_NEST("drag", "Dragon's Nest", Island.THE_END, 260, 248),
        VOID_SEPULTURE("void", "Void Sepulture", Island.THE_END, true, 370, 227),
        BLAZING_FORTRESS("nether", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.BLAZING_FORTRESS, true, 80, 350),
        BLAZING_FORTRESS_MAGMA("magma", "Magma Cube Arena", Island.BLAZING_FORTRESS, 350, 200),
        THE_BARN("barn", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.THE_BARN, true, 140, 150),
        MUSHROOM_DESERT("desert", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.MUSHROOM_DESERT, true, 210, 295),
        GOLD_MINE("gold", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.GOLD_MINE, true, 86, 259),
        DEEP_CAVERNS("deep", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.DEEP_CAVERNS, true, 97, 213),
        DWARVEN_MINES("mines", "Dwarven Mines", Island.DEEP_CAVERNS, false, 150, 320),
        DWARVEN_FORGE("forge", "Forge", Island.DEEP_CAVERNS, false, 220, 350),
        DUNGEON_HUB_ISLAND("dungeon_hub", Translations.getMessage("warpMenu.spawn", new Object[0]), Island.DUNGEON_HUB, false, 35, 80);

        private final String warpName;
        private final String label;
        private final Island island;
        private final boolean advanced;
        private final int x;
        private final int y;

        private Marker(String warpName, String label, Island island, int x, int y) {
            this(warpName, label, island, false, x, y);
        }

        private Marker(String warpName, String label, Island island, boolean advanced, int x, int y) {
            this.warpName = warpName;
            this.label = label;
            this.island = island;
            this.x = x;
            this.y = y;
            this.advanced = advanced;
        }

        public static Marker fromWarpName(String warpName) {
            for (Marker marker : Marker.values()) {
                if (!marker.warpName.equals(warpName)) continue;
                return marker;
            }
            return null;
        }

        public String getWarpName() {
            return this.warpName;
        }

        public String getLabel() {
            return this.label;
        }

        public Island getIsland() {
            return this.island;
        }

        public boolean isAdvanced() {
            return this.advanced;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    public static enum Island {
        THE_END("The End", 290, -10),
        BLAZING_FORTRESS("Blazing Fortress", 900, -50),
        THE_PARK("The Park", 103, 370),
        SPIDERS_DEN("Spider's Den", 500, 420),
        DEEP_CAVERNS("Deep Caverns", 1400, 200),
        GOLD_MINE("Gold Mine", 1130, 475),
        MUSHROOM_DESERT("Mushroom Desert", 1470, 475),
        THE_BARN("The Barn", 1125, 800),
        HUB("Hub", 300, 724),
        PRIVATE_ISLAND("Private Island", 275, 1122),
        DUNGEON_HUB("Dungeon Hub", 1500, 1050);

        private final String label;
        private final int x;
        private final int y;
        private int w;
        private int h;
        private final ResourceLocation resourceLocation;
        private BufferedImage bufferedImage;

        private Island(String label, int x, int y) {
            this.label = label;
            this.x = x;
            this.y = y;
            this.resourceLocation = new ResourceLocation("skyblockaddons", "islands/" + this.name().toLowerCase(Locale.US).replace("_", "") + ".png");
            try {
                this.bufferedImage = TextureUtil.func_177053_a((InputStream)Minecraft.func_71410_x().func_110442_L().func_110536_a(this.resourceLocation).func_110527_b());
                this.w = this.bufferedImage.getWidth();
                this.h = this.bufferedImage.getHeight();
                if (label.equals("The End")) {
                    IMAGE_SCALED_DOWN_FACTOR = (float)this.w / 573.0f;
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            this.w = (int)((float)this.w / IMAGE_SCALED_DOWN_FACTOR);
            this.h = (int)((float)this.h / IMAGE_SCALED_DOWN_FACTOR);
            if (this.y + this.h > TOTAL_HEIGHT) {
                TOTAL_HEIGHT = this.y + this.h;
            }
            if (this.x + this.w > TOTAL_WIDTH) {
                TOTAL_WIDTH = this.x + this.w;
            }
        }

        public String getLabel() {
            return this.label;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getW() {
            return this.w;
        }

        public int getH() {
            return this.h;
        }

        public ResourceLocation getResourceLocation() {
            return this.resourceLocation;
        }

        public BufferedImage getBufferedImage() {
            return this.bufferedImage;
        }
    }
}

