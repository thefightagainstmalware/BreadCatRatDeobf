/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  org.apache.commons.lang3.StringUtils
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.InventoryType;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.npc.NPCUtils;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackColor;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackInventoryManager;
import codes.biscuit.skyblockaddons.features.backpacks.ContainerPreviewManager;
import codes.biscuit.skyblockaddons.gui.IslandWarpGui;
import codes.biscuit.skyblockaddons.gui.elements.CraftingPatternSelection;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class GuiChestHook {
    private static GuiTextField textFieldMatch = null;
    private static GuiTextField textFieldExclusions = null;
    private static String lastAccessoryBagReforge = null;
    private static CraftingPatternSelection craftingPatternSelection = null;
    private static final Pattern warpPattern = Pattern.compile("(?:\u00a75\u00a7o)?\u00a78/warp ([a-z_]*)");
    private static final Pattern unlockedPattern = Pattern.compile("(?:\u00a75\u00a7o)?\u00a7eClick to warp!");
    private static final Pattern notUnlockedPattern = Pattern.compile("(?:\u00a75\u00a7o)?\u00a7cWarp not unlocked!");
    private static final Pattern inCombatPattern = Pattern.compile("(?:\u00a75\u00a7o)?\u00a7cYou're in combat!");
    private static final Pattern youAreHerePattern = Pattern.compile("(?:\u00a75\u00a7o)?\u00a7aYou are here!");
    private static IslandWarpGui islandWarpGui = null;

    public static void updateScreen() {
        if (textFieldMatch != null && textFieldExclusions != null) {
            textFieldMatch.func_146178_a();
            textFieldExclusions.func_146178_a();
        }
    }

    public static void onGuiClosed() {
        SkyblockAddons.getInstance().getInventoryUtils().updateInventoryType();
        Keyboard.enableRepeatEvents((boolean)false);
        islandWarpGui = null;
        BackpackInventoryManager.setBackpackColor(null);
    }

    public static void drawScreenIslands(int mouseX, int mouseY, ReturnValue<?> returnValue) {
        Minecraft mc = Minecraft.func_71410_x();
        Container playerContainer = mc.field_71439_g.field_71070_bA;
        if (playerContainer instanceof ContainerChest && SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.FANCY_WARP_MENU)) {
            IInventory chestInventory = ((ContainerChest)playerContainer).func_85151_d();
            if (chestInventory.func_145818_k_()) {
                String chestName = chestInventory.func_145748_c_().func_150260_c();
                if (chestName.equals("Fast Travel")) {
                    EnumMap<IslandWarpGui.Marker, IslandWarpGui.UnlockedStatus> markers = new EnumMap<IslandWarpGui.Marker, IslandWarpGui.UnlockedStatus>(IslandWarpGui.Marker.class);
                    for (int slot = 0; slot < chestInventory.func_70302_i_(); ++slot) {
                        ItemStack itemStack = chestInventory.func_70301_a(slot);
                        if (itemStack == null || Items.field_151144_bL != itemStack.func_77973_b() && Items.field_151121_aF != itemStack.func_77973_b()) continue;
                        List<String> lore = ItemUtils.getItemLore(itemStack);
                        IslandWarpGui.Marker marker = null;
                        IslandWarpGui.UnlockedStatus status = IslandWarpGui.UnlockedStatus.UNKNOWN;
                        for (String loreLine : lore) {
                            Matcher matcher = warpPattern.matcher(loreLine);
                            if (matcher.matches()) {
                                marker = IslandWarpGui.Marker.fromWarpName(matcher.group(1));
                            }
                            if ((matcher = unlockedPattern.matcher(loreLine)).matches() || youAreHerePattern.matcher(loreLine).matches()) {
                                status = IslandWarpGui.UnlockedStatus.UNLOCKED;
                                break;
                            }
                            matcher = notUnlockedPattern.matcher(loreLine);
                            if (matcher.matches()) {
                                status = IslandWarpGui.UnlockedStatus.NOT_UNLOCKED;
                                break;
                            }
                            matcher = inCombatPattern.matcher(loreLine);
                            if (!matcher.matches()) continue;
                            status = IslandWarpGui.UnlockedStatus.IN_COMBAT;
                            break;
                        }
                        if (marker == null) continue;
                        markers.put(marker, status);
                    }
                    for (IslandWarpGui.Marker marker : IslandWarpGui.Marker.values()) {
                        if (markers.containsKey((Object)marker)) continue;
                        markers.put(marker, IslandWarpGui.UnlockedStatus.UNKNOWN);
                    }
                    markers.put(IslandWarpGui.Marker.DWARVEN_FORGE, markers.getOrDefault((Object)IslandWarpGui.Marker.DEEP_CAVERNS, IslandWarpGui.UnlockedStatus.UNKNOWN));
                    markers.put(IslandWarpGui.Marker.DUNGEON_HUB_ISLAND, markers.getOrDefault((Object)IslandWarpGui.Marker.DUNGEON_HUB, IslandWarpGui.UnlockedStatus.UNKNOWN));
                    if (islandWarpGui == null || !islandWarpGui.getMarkers().equals(markers)) {
                        islandWarpGui = new IslandWarpGui(markers);
                        ScaledResolution scaledresolution = new ScaledResolution(mc);
                        int i = scaledresolution.func_78326_a();
                        int j = scaledresolution.func_78328_b();
                        islandWarpGui.func_146280_a(mc, i, j);
                    }
                    try {
                        islandWarpGui.func_73863_a(mouseX, mouseY, 0.0f);
                    }
                    catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                    returnValue.cancel();
                } else {
                    islandWarpGui = null;
                }
            } else {
                islandWarpGui = null;
            }
        } else {
            islandWarpGui = null;
        }
    }

    public static void drawScreen(int guiLeft, int guiTop) {
        InventoryType inventoryType = SkyblockAddons.getInstance().getInventoryUtils().updateInventoryType();
        if (inventoryType == InventoryType.SALVAGING) {
            int ySize = 222;
            float x = guiLeft - 69 - 5;
            float y = (float)guiTop + (float)ySize / 2.0f - 36.0f;
            SkyblockAddons.getInstance().getRenderListener().drawCollectedEssences(x, y, false, false);
        } else if (inventoryType == InventoryType.STORAGE_BACKPACK) {
            ContainerPreviewManager.saveStorageContainerInventory(SkyblockAddons.getInstance().getInventoryUtils().getInventoryKey());
        } else if (inventoryType == InventoryType.ENDER_CHEST) {
            ContainerPreviewManager.saveStorageContainerInventory(SkyblockAddons.getInstance().getInventoryUtils().getInventoryKey());
        }
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.REFORGE_FILTER) && textFieldMatch != null && (inventoryType == InventoryType.BASIC_REFORGING || inventoryType == InventoryType.BASIC_ACCESSORY_BAG_REFORGING)) {
            Minecraft mc = Minecraft.func_71410_x();
            SkyblockAddons main = SkyblockAddons.getInstance();
            String typeToMatch = Message.MESSAGE_REFORGES.getMessage(new String[0]);
            int defaultBlue = main.getUtils().getDefaultBlue(255);
            float scale = 0.75f;
            int x = guiLeft - 160;
            if (x < 0) {
                x = 20;
            }
            String inclusionExample = Message.MESSAGE_REFORGE_INCLUSION_EXAMPLE.getMessage(new String[0]);
            String exclusionExample = Message.MESSAGE_REFORGE_EXCLUSION_EXAMPLE.getMessage(new String[0]);
            GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)1.0f);
            mc.field_71466_p.func_78276_b(Message.MESSAGE_TYPE_ENCHANTMENTS.getMessage(typeToMatch), Math.round((float)x / scale), Math.round((float)(guiTop + 40) / scale), defaultBlue);
            int width = mc.field_71466_p.func_78256_a(Message.MESSAGE_SEPARATE_ENCHANTMENTS.getMessage(new String[0]));
            if (width > guiLeft - x) {
                mc.field_71466_p.func_78279_b(Message.MESSAGE_SEPARATE_ENCHANTMENTS.getMessage(new String[0]), Math.round((float)x / scale), Math.round((float)(guiTop + 50) / scale), GuiChestHook.textFieldMatch.field_146218_h - x, defaultBlue);
            } else {
                mc.field_71466_p.func_78276_b(Message.MESSAGE_SEPARATE_ENCHANTMENTS.getMessage(new String[0]), Math.round((float)x / scale), Math.round((float)(guiTop + 50) / scale), defaultBlue);
            }
            mc.field_71466_p.func_78276_b(Message.MESSAGE_ENCHANTS_TO_MATCH.getMessage(typeToMatch), Math.round((float)x / scale), Math.round((float)(guiTop + 70) / scale), defaultBlue);
            mc.field_71466_p.func_78276_b(Message.MESSAGE_ENCHANTS_TO_EXCLUDE.getMessage(typeToMatch), Math.round((float)x / scale), Math.round((float)(guiTop + 110) / scale), defaultBlue);
            GlStateManager.func_179121_F();
            textFieldMatch.func_146194_f();
            if (StringUtils.isEmpty((CharSequence)textFieldMatch.func_146179_b())) {
                mc.field_71466_p.func_78276_b(inclusionExample, x + 4, guiTop + 86, ColorCode.DARK_GRAY.getColor());
            }
            textFieldExclusions.func_146194_f();
            if (StringUtils.isEmpty((CharSequence)textFieldExclusions.func_146179_b())) {
                mc.field_71466_p.func_78276_b(exclusionExample, x + 4, guiTop + 126, ColorCode.DARK_GRAY.getColor());
            }
        }
    }

    public static void initGui(IInventory lowerChestInventory, int guiLeft, int guiTop, FontRenderer fontRendererObj) {
        if (!SkyblockAddons.getInstance().getUtils().isOnSkyblock()) {
            return;
        }
        InventoryType inventoryType = SkyblockAddons.getInstance().getInventoryUtils().updateInventoryType();
        if (inventoryType != null) {
            if (inventoryType == InventoryType.CRAFTING_TABLE) {
                return;
            }
            if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.REFORGE_FILTER) && inventoryType == InventoryType.BASIC_REFORGING || inventoryType == InventoryType.BASIC_ACCESSORY_BAG_REFORGING) {
                int xPos = guiLeft - 160;
                if (xPos < 0) {
                    xPos = 20;
                }
                int yPos = guiTop + 80;
                textFieldMatch = new GuiTextField(2, fontRendererObj, xPos, yPos, 120, 20);
                textFieldMatch.func_146203_f(500);
                List<String> lockedReforges = SkyblockAddons.getInstance().getUtils().getReforgeMatches();
                StringBuilder reforgeBuilder = new StringBuilder();
                int i = 1;
                for (String reforge : lockedReforges) {
                    reforgeBuilder.append(reforge);
                    if (i < lockedReforges.size()) {
                        reforgeBuilder.append(",");
                    }
                    ++i;
                }
                String text = reforgeBuilder.toString();
                if (text.length() > 0) {
                    textFieldMatch.func_146180_a(text);
                }
                textFieldExclusions = new GuiTextField(2, fontRendererObj, xPos, yPos += 40, 120, 20);
                textFieldExclusions.func_146203_f(500);
                lockedReforges = SkyblockAddons.getInstance().getUtils().getReforgeExclusions();
                reforgeBuilder = new StringBuilder();
                i = 1;
                for (String enchantment : lockedReforges) {
                    reforgeBuilder.append(enchantment);
                    if (i < lockedReforges.size()) {
                        reforgeBuilder.append(",");
                    }
                    ++i;
                }
                text = reforgeBuilder.toString();
                if (text.length() > 0) {
                    textFieldExclusions.func_146180_a(text);
                }
                Keyboard.enableRepeatEvents((boolean)true);
            }
        }
    }

    public static boolean keyTyped(char typedChar, int keyCode) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.REFORGE_FILTER)) {
            InventoryType inventoryType = main.getInventoryUtils().getInventoryType();
            if (inventoryType == InventoryType.BASIC_REFORGING || inventoryType == InventoryType.BASIC_ACCESSORY_BAG_REFORGING) {
                if (keyCode != Minecraft.func_71410_x().field_71474_y.field_151445_Q.func_151463_i() || !textFieldMatch.func_146206_l() && !textFieldExclusions.func_146206_l()) {
                    GuiChestHook.processTextFields(typedChar, keyCode);
                    return true;
                }
            } else {
                return true;
            }
            GuiChestHook.processTextFields(typedChar, keyCode);
            return false;
        }
        return true;
    }

    private static void processTextFields(char typedChar, int keyCode) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isEnabled(Feature.REFORGE_FILTER) && textFieldMatch != null) {
            textFieldMatch.func_146201_a(typedChar, keyCode);
            textFieldExclusions.func_146201_a(typedChar, keyCode);
            LinkedList<String> reforges = new LinkedList<String>(Arrays.asList(textFieldMatch.func_146179_b().split(",")));
            main.getUtils().setReforgeMatches(reforges);
            reforges = new LinkedList<String>(Arrays.asList(textFieldExclusions.func_146179_b().split(",")));
            main.getUtils().setReforgeExclusions(reforges);
        }
    }

    public static void handleMouseClick(Slot slotIn, Container slots, IInventory lowerChestInventory, ReturnValue<?> returnValue) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock()) {
            if (main.getConfigValues().isEnabled(Feature.REFORGE_FILTER) && !main.getUtils().getReforgeMatches().isEmpty() && slotIn != null && !slotIn.field_75224_c.equals(Minecraft.func_71410_x().field_71439_g.field_71071_by) && slotIn.func_75216_d()) {
                Slot itemSlot;
                InventoryType inventoryType = main.getInventoryUtils().getInventoryType();
                if (slotIn.getSlotIndex() == 22 && (inventoryType == InventoryType.BASIC_REFORGING || inventoryType == InventoryType.BASIC_ACCESSORY_BAG_REFORGING) && (itemSlot = slots.func_75139_a(13)) != null && itemSlot.func_75216_d()) {
                    if (inventoryType == InventoryType.BASIC_REFORGING) {
                        String reforge;
                        ItemStack item = itemSlot.func_75211_c();
                        if (item.func_82837_s() && (reforge = ItemUtils.getReforge(item)) != null && main.getUtils().enchantReforgeMatches(reforge)) {
                            main.getUtils().playLoudSound("random.orb", 0.1);
                            returnValue.cancel();
                        }
                    } else if (lastAccessoryBagReforge != null && main.getUtils().enchantReforgeMatches(lastAccessoryBagReforge)) {
                        main.getUtils().playLoudSound("random.orb", 0.1);
                        returnValue.cancel();
                    }
                }
            }
            if (main.getConfigValues().isEnabled(Feature.STOP_DROPPING_SELLING_RARE_ITEMS) && !main.getUtils().isInDungeon() && NPCUtils.isSellMerchant(lowerChestInventory) && slotIn != null && slotIn.field_75224_c instanceof InventoryPlayer && !main.getUtils().getItemDropChecker().canDropItem(slotIn)) {
                returnValue.cancel();
            }
        }
    }

    public static void mouseClicked(int mouseX, int mouseY, int mouseButton, ReturnValue<?> returnValue) throws IOException {
        if (islandWarpGui != null) {
            islandWarpGui.func_73864_a(mouseX, mouseY, mouseButton);
            returnValue.cancel();
            return;
        }
        if (textFieldMatch != null) {
            textFieldMatch.func_146192_a(mouseX, mouseY, mouseButton);
            textFieldExclusions.func_146192_a(mouseX, mouseY, mouseButton);
        }
        if (craftingPatternSelection != null && SkyblockAddons.getInstance().getInventoryUtils().getInventoryType() == InventoryType.CRAFTING_TABLE) {
            craftingPatternSelection.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public static void color(float colorRed, float colorGreen, float colorBlue, float colorAlpha, IInventory lowerChestInventory) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (!main.getUtils().isOnSkyblock()) {
            return;
        }
        Minecraft mc = Minecraft.func_71410_x();
        if (main.getConfigValues().isEnabled(Feature.SHOW_BACKPACK_PREVIEW) && main.getConfigValues().isEnabled(Feature.MAKE_BACKPACK_INVENTORIES_COLORED) && lowerChestInventory.func_145818_k_()) {
            if (lowerChestInventory.func_145748_c_().func_150260_c().contains("Backpack")) {
                if (BackpackInventoryManager.getBackpackColor() != null) {
                    BackpackColor color = BackpackInventoryManager.getBackpackColor();
                    GlStateManager.func_179131_c((float)color.getR(), (float)color.getG(), (float)color.getB(), (float)1.0f);
                    return;
                }
            } else if (lowerChestInventory.func_145748_c_().func_150260_c().contains("Bank")) {
                ItemStack item = mc.field_71439_g.func_70694_bm();
                if (item != null && item.func_82837_s() && item.func_82833_r().contains("Piggy Bank")) {
                    BackpackColor color = BackpackColor.PINK;
                    GlStateManager.func_179131_c((float)color.getR(), (float)color.getG(), (float)color.getB(), (float)1.0f);
                }
                return;
            }
        }
        GlStateManager.func_179131_c((float)colorRed, (float)colorGreen, (float)colorBlue, (float)colorAlpha);
    }

    public static int drawString(FontRenderer fontRenderer, String text, int x, int y, int color) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && main.getConfigValues().isEnabled(Feature.SHOW_BACKPACK_PREVIEW) && main.getConfigValues().isEnabled(Feature.MAKE_BACKPACK_INVENTORIES_COLORED) && BackpackInventoryManager.getBackpackColor() != null) {
            return fontRenderer.func_78276_b(text, x, y, BackpackInventoryManager.getBackpackColor().getInventoryTextColor());
        }
        return fontRenderer.func_78276_b(text, x, y, color);
    }

    public static void mouseReleased(ReturnValue<?> returnValue) {
        if (islandWarpGui != null) {
            returnValue.cancel();
        }
    }

    public static void mouseClickMove(ReturnValue<?> returnValue) {
        if (islandWarpGui != null) {
            returnValue.cancel();
        }
    }

    public static void onRenderChestForegroundLayer(GuiChest guiChest) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isEnabled(Feature.SHOW_REFORGE_OVERLAY)) {
            ItemStack item;
            Slot slot;
            Minecraft mc = Minecraft.func_71410_x();
            if (guiChest.field_147002_h.field_75151_b.size() > 13 && (slot = (Slot)guiChest.field_147002_h.field_75151_b.get(13)) != null && (item = slot.func_75211_c()) != null) {
                String reforge = null;
                if (main.getInventoryUtils().getInventoryType() == InventoryType.BASIC_REFORGING) {
                    reforge = ItemUtils.getReforge(item);
                } else if (main.getInventoryUtils().getInventoryType() == InventoryType.BASIC_ACCESSORY_BAG_REFORGING) {
                    reforge = GuiChestHook.getLastAccessoryBagReforge();
                }
                if (reforge != null) {
                    int color = ColorCode.YELLOW.getColor();
                    if (main.getConfigValues().isEnabled(Feature.REFORGE_FILTER) && !main.getUtils().getReforgeMatches().isEmpty() && main.getUtils().enchantReforgeMatches(reforge)) {
                        color = ColorCode.RED.getColor();
                    }
                    int x = slot.field_75223_e;
                    int y = slot.field_75221_f;
                    int stringWidth = mc.field_71466_p.func_78256_a(reforge);
                    float renderX = (float)(x - 28) - (float)stringWidth / 2.0f;
                    int renderY = y + 22;
                    GlStateManager.func_179097_i();
                    GuiChestHook.drawTooltipBackground(renderX, renderY, stringWidth, 8.0f);
                    mc.field_71466_p.func_175065_a(reforge, renderX, (float)renderY, color, true);
                    GlStateManager.func_179126_j();
                }
            }
        }
    }

    private static void drawTooltipBackground(float x, float y, float width, float height) {
        int l = -267386864;
        DrawUtils.drawRectAbsolute(x - 3.0f, y - 4.0f, x + width + 3.0f, y - 3.0f, l);
        DrawUtils.drawRectAbsolute(x - 3.0f, y + height + 3.0f, x + width + 3.0f, y + height + 4.0f, l);
        DrawUtils.drawRectAbsolute(x - 3.0f, y - 3.0f, x + width + 3.0f, y + height + 3.0f, l);
        DrawUtils.drawRectAbsolute(x - 4.0f, y - 3.0f, x - 3.0f, y + height + 3.0f, l);
        DrawUtils.drawRectAbsolute(x + width + 3.0f, y - 3.0f, x + width + 4.0f, y + height + 3.0f, l);
        int borderColor = 0x505000FF;
        DrawUtils.drawRectAbsolute(x - 3.0f, y - 3.0f + 1.0f, x - 3.0f + 1.0f, y + height + 3.0f - 1.0f, borderColor);
        DrawUtils.drawRectAbsolute(x + width + 2.0f, y - 3.0f + 1.0f, x + width + 3.0f, y + height + 3.0f - 1.0f, borderColor);
        DrawUtils.drawRectAbsolute(x - 3.0f, y - 3.0f, x + width + 3.0f, y - 3.0f + 1.0f, borderColor);
        DrawUtils.drawRectAbsolute(x - 3.0f, y + height + 2.0f, x + width + 3.0f, y + height + 3.0f, borderColor);
    }

    public static void setLastAccessoryBagReforge(String lastAccessoryBagReforge) {
        GuiChestHook.lastAccessoryBagReforge = lastAccessoryBagReforge;
    }

    public static String getLastAccessoryBagReforge() {
        return lastAccessoryBagReforge;
    }
}

