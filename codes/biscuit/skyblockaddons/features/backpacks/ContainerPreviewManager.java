/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagByteArray
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.features.backpacks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.InventoryType;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackColor;
import codes.biscuit.skyblockaddons.features.backpacks.CompressedStorage;
import codes.biscuit.skyblockaddons.features.backpacks.ContainerPreview;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import codes.biscuit.skyblockaddons.utils.skyblockdata.ContainerData;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class ContainerPreviewManager {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("skyblockaddons", "containerPreview.png");
    private static final Pattern BACKPACK_STORAGE_PATTERN = Pattern.compile("Backpack Slot (?<slot>[0-9]+)");
    private static final Pattern ENDERCHEST_STORAGE_PATTERN = Pattern.compile("Ender Chest Page (?<page>[0-9]+)");
    private static ContainerPreview currentContainerPreview;
    private static boolean frozen;
    private static long lastToggleFreezeTime;
    private static boolean drawingFrozenItemTooltip;

    public static ContainerPreview getFromItem(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(stack);
        String skyblockID = ItemUtils.getSkyblockItemID(extraAttributes);
        ContainerData containerData = ItemUtils.getContainerData(skyblockID);
        if (containerData != null) {
            int containerSize = containerData.getSize();
            ArrayList<ItemStack> items = null;
            String compressedDataTag = containerData.getCompressedDataTag();
            List<String> itemStackDataTags = containerData.getItemStackDataTags();
            if (compressedDataTag != null) {
                if (extraAttributes.func_150297_b(compressedDataTag, 7)) {
                    byte[] bytes = extraAttributes.func_74770_j(compressedDataTag);
                    items = ContainerPreviewManager.decompressItems(bytes);
                }
            } else if (itemStackDataTags != null) {
                items = new ArrayList(containerSize);
                Iterator<String> itemStackDataTagsIterator = itemStackDataTags.iterator();
                for (int itemNumber = 0; itemNumber < containerSize && itemStackDataTagsIterator.hasNext(); ++itemNumber) {
                    String key = itemStackDataTagsIterator.next();
                    if (!extraAttributes.func_74764_b(key)) continue;
                    items.add(ItemUtils.getPersonalCompactorItemStack(extraAttributes.func_74779_i(key)));
                }
            }
            if (items == null) {
                return null;
            }
            BackpackColor color = ItemUtils.getBackpackColor(stack);
            String name = containerData.isPersonalCompactor() ? null : TextUtils.stripColor(stack.func_82833_r());
            return new ContainerPreview(items, name, color, containerData.getNumRows(), containerData.getNumCols());
        }
        return null;
    }

    private static List<ItemStack> decompressItems(byte[] bytes) {
        ArrayList<ItemStack> items = null;
        try {
            NBTTagCompound decompressedData = CompressedStreamTools.func_74796_a((InputStream)new ByteArrayInputStream(bytes));
            NBTTagList list = decompressedData.func_150295_c("i", 10);
            if (list.func_82582_d()) {
                throw new Exception("Decompressed container list has no item tags");
            }
            int size = Math.min(list.func_74745_c(), 54);
            items = new ArrayList<ItemStack>(size);
            for (int i = 0; i < size; ++i) {
                NBTTagCompound item = list.func_150305_b(i);
                short itemID = item.func_74765_d("id");
                if (itemID == 142) {
                    item.func_74777_a("id", (short)392);
                } else if (itemID == 141) {
                    item.func_74777_a("id", (short)391);
                }
                items.add(ItemStack.func_77949_a((NBTTagCompound)item));
            }
        }
        catch (Exception ex) {
            SkyblockAddons.getLogger().error("There was an error decompressing container data.");
            SkyblockAddons.getLogger().catching((Throwable)ex);
        }
        return items;
    }

    public static void drawContainerPreviews(GuiContainer guiContainer, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.func_71410_x();
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (currentContainerPreview != null) {
            int x = currentContainerPreview.getX();
            int y = currentContainerPreview.getY();
            List<ItemStack> items = currentContainerPreview.getItems();
            int length = items.size();
            int rows = currentContainerPreview.getNumRows();
            int cols = currentContainerPreview.getNumCols();
            int screenHeight = guiContainer.field_146295_m;
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ItemStack tooltipItem = null;
            if (main.getConfigValues().getBackpackStyle() == EnumUtils.BackpackStyle.GUI) {
                BackpackColor color;
                mc.func_110434_K().func_110577_a(CHEST_GUI_TEXTURE);
                GlStateManager.func_179140_f();
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)300.0f);
                int textColor = 0x404040;
                if (main.getConfigValues().isEnabled(Feature.MAKE_BACKPACK_INVENTORIES_COLORED) && (color = currentContainerPreview.getBackpackColor()) != null) {
                    GlStateManager.func_179131_c((float)color.getR(), (float)color.getG(), (float)color.getB(), (float)1.0f);
                    textColor = color.getInventoryTextColor();
                }
                int textureBorder = 7;
                int textureTopBorder = 17;
                int textureItemSquare = 18;
                int topBorder = currentContainerPreview.getName() == null ? 7 : 17;
                int totalWidth = cols * 18 + 14;
                int totalHeight = rows * 18 + topBorder + 7;
                int squaresEndWidth = totalWidth - 7;
                int squaresEndHeight = totalHeight - 7;
                if (x + totalWidth > guiContainer.field_146294_l) {
                    x -= totalWidth;
                }
                if (y + totalHeight > screenHeight) {
                    y = screenHeight - totalHeight;
                }
                if (currentContainerPreview.getName() == null) {
                    guiContainer.func_73729_b(x, y, 0, 0, squaresEndWidth, topBorder);
                    guiContainer.func_73729_b(x, y + topBorder, 0, 17, squaresEndWidth, squaresEndHeight - topBorder);
                } else {
                    guiContainer.func_73729_b(x, y, 0, 0, squaresEndWidth, squaresEndHeight);
                }
                guiContainer.func_73729_b(x + squaresEndWidth, y, 169, 0, 7, squaresEndHeight);
                guiContainer.func_73729_b(x, y + squaresEndHeight, 0, 125, squaresEndWidth, 7);
                guiContainer.func_73729_b(x + squaresEndWidth, y + squaresEndHeight, 169, 125, 7, 7);
                if (currentContainerPreview.getName() != null) {
                    String name = currentContainerPreview.getName();
                    if (main.getUtils().isUsingFSRcontainerPreviewTexture()) {
                        name = (Object)((Object)ColorCode.GOLD) + TextUtils.stripColor(name);
                    }
                    mc.field_71466_p.func_78276_b(name, x + 8, y + 6, textColor);
                }
                GlStateManager.func_179121_F();
                GlStateManager.func_179145_e();
                RenderHelper.func_74520_c();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.func_179091_B();
                int itemStartX = x + 7 + 1;
                int itemStartY = y + topBorder + 1;
                for (int i = 0; i < length; ++i) {
                    ItemStack item = items.get(i);
                    if (item == null) continue;
                    int itemX = itemStartX + i % cols * 18;
                    int itemY = itemStartY + i / cols * 18;
                    RenderItem renderItem = mc.func_175599_af();
                    guiContainer.field_73735_i = 200.0f;
                    renderItem.field_77023_b = 200.0f;
                    renderItem.func_180450_b(item, itemX, itemY);
                    renderItem.func_180453_a(mc.field_71466_p, item, itemX, itemY, null);
                    guiContainer.field_73735_i = 0.0f;
                    renderItem.field_77023_b = 0.0f;
                    if (!frozen || mouseX <= itemX || mouseX >= itemX + 16 || mouseY <= itemY || mouseY >= itemY + 16) continue;
                    tooltipItem = item;
                }
            } else {
                int totalHeight;
                int totalWidth = 16 * cols + 3;
                if (x + totalWidth > guiContainer.field_146294_l) {
                    x -= totalWidth;
                }
                if (y + (totalHeight = 16 * rows + 3) > screenHeight) {
                    y = screenHeight - totalHeight;
                }
                GlStateManager.func_179140_f();
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)300.0f);
                Gui.func_73734_a((int)x, (int)y, (int)(x + totalWidth), (int)(y + totalHeight), (int)ColorCode.DARK_GRAY.getColor(250));
                GlStateManager.func_179121_F();
                GlStateManager.func_179145_e();
                RenderHelper.func_74520_c();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.func_179091_B();
                for (int i = 0; i < length; ++i) {
                    ItemStack item = items.get(i);
                    if (item == null) continue;
                    int itemX = x + i % cols * 16;
                    int itemY = y + i / cols * 16;
                    RenderItem renderItem = mc.func_175599_af();
                    guiContainer.field_73735_i = 200.0f;
                    renderItem.field_77023_b = 200.0f;
                    renderItem.func_180450_b(item, itemX, itemY);
                    renderItem.func_180453_a(mc.field_71466_p, item, itemX, itemY, null);
                    guiContainer.field_73735_i = 0.0f;
                    renderItem.field_77023_b = 0.0f;
                    if (!frozen || mouseX <= itemX || mouseX >= itemX + 16 || mouseY <= itemY || mouseY >= itemY + 16) continue;
                    tooltipItem = item;
                }
            }
            if (tooltipItem != null) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)302.0f);
                drawingFrozenItemTooltip = true;
                guiContainer.func_146283_a(tooltipItem.func_82840_a((EntityPlayer)mc.field_71439_g, mc.field_71474_y.field_82882_x), mouseX, mouseY);
                drawingFrozenItemTooltip = false;
                GlStateManager.func_179121_F();
            }
            if (!frozen) {
                currentContainerPreview = null;
            }
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
            RenderHelper.func_74519_b();
        }
    }

    public static ContainerPreview getFromStorageBackpack(ItemStack stack, List<ItemStack> items) {
        if (items == null) {
            return null;
        }
        BackpackColor color = ItemUtils.getBackpackColor(stack);
        String skyblockID = TextUtils.stripColor(ItemUtils.getItemLore(stack).get(0)).toUpperCase().replaceAll(" ", "_").trim();
        ContainerData containerData = ItemUtils.getContainerData(skyblockID);
        int rows = 6;
        int cols = 9;
        if (containerData != null) {
            rows = Math.min(containerData.getNumRows(), 5);
            cols = containerData.getNumCols();
        } else if (TextUtils.stripColor(stack.func_82833_r()).toUpperCase().startsWith("ENDER CHEST")) {
            System.out.println(items.size());
            rows = Math.min(5, (int)Math.ceil((float)items.size() / 9.0f));
        }
        return new ContainerPreview(items, TextUtils.stripColor(stack.func_82833_r()), color, rows, cols);
    }

    private static boolean isFreezeKeyDown() {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getFreezeBackpackKey().func_151470_d()) {
            return true;
        }
        try {
            if (Keyboard.isKeyDown((int)main.getFreezeBackpackKey().func_151463_i())) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static void onContainerKeyTyped(int keyCode) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock()) {
            if (keyCode == 1 || keyCode == Minecraft.func_71410_x().field_71474_y.field_151445_Q.func_151463_i()) {
                frozen = false;
                currentContainerPreview = null;
            }
            if (keyCode == main.getFreezeBackpackKey().func_151463_i() && frozen && System.currentTimeMillis() - lastToggleFreezeTime > 500L) {
                lastToggleFreezeTime = System.currentTimeMillis();
                frozen = false;
            }
        }
    }

    public static boolean onRenderTooltip(ItemStack itemStack, int x, int y) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (frozen && !drawingFrozenItemTooltip) {
            return true;
        }
        if (main.getConfigValues().isEnabled(Feature.SHOW_BACKPACK_PREVIEW)) {
            if (main.getConfigValues().isEnabled(Feature.SHOW_BACKPACK_HOLDING_SHIFT) && !GuiScreen.func_146272_n()) {
                return false;
            }
            if (ItemUtils.isMenuItem(itemStack)) {
                return false;
            }
            ContainerPreview containerPreview = null;
            if (main.getInventoryUtils().getInventoryType() == InventoryType.STORAGE) {
                Map<String, CompressedStorage> cache;
                int pageNum;
                String strippedName = TextUtils.stripColor(itemStack.func_82833_r());
                String storageKey = null;
                Matcher m = BACKPACK_STORAGE_PATTERN.matcher(strippedName);
                if (m.matches()) {
                    pageNum = Integer.parseInt(m.group("slot"));
                    storageKey = InventoryType.STORAGE_BACKPACK.getInventoryName() + pageNum;
                } else if (main.getConfigValues().isEnabled(Feature.SHOW_ENDER_CHEST_PREVIEW) && (m = ENDERCHEST_STORAGE_PATTERN.matcher(strippedName)).matches()) {
                    pageNum = Integer.parseInt(m.group("page"));
                    storageKey = InventoryType.ENDER_CHEST.getInventoryName() + pageNum;
                }
                if (storageKey != null && (cache = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getStorageCache()).get(storageKey) != null) {
                    byte[] bytes = cache.get(storageKey).getStorage();
                    List<ItemStack> items = ContainerPreviewManager.decompressItems(bytes);
                    items = items.subList(9, items.size());
                    containerPreview = ContainerPreviewManager.getFromStorageBackpack(itemStack, items);
                }
            }
            if (containerPreview == null) {
                String chestName;
                IInventory chestInventory;
                NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(itemStack);
                ContainerData containerData = ItemUtils.getContainerData(ItemUtils.getSkyblockItemID(extraAttributes));
                if (containerData == null || containerData.isCakeBag() && main.getConfigValues().isDisabled(Feature.CAKE_BAG_PREVIEW) || containerData.isPersonalCompactor() && main.getConfigValues().isDisabled(Feature.SHOW_PERSONAL_COMPACTOR_PREVIEW)) {
                    return false;
                }
                Container playerContainer = Minecraft.func_71410_x().field_71439_g.field_71070_bA;
                if (playerContainer instanceof ContainerChest && (chestInventory = ((ContainerChest)playerContainer).func_85151_d()).func_145818_k_() && ((chestName = chestInventory.func_145748_c_().func_150260_c()).contains("Auction") || "Your Bids".equals(chestName))) {
                    for (int slotNumber = 0; slotNumber < chestInventory.func_70302_i_(); ++slotNumber) {
                        if (chestInventory.func_70301_a(slotNumber) != itemStack) continue;
                        return false;
                    }
                }
                containerPreview = ContainerPreviewManager.getFromItem(itemStack);
            }
            if (containerPreview != null) {
                containerPreview.setX(x);
                containerPreview.setY(y);
                if (ContainerPreviewManager.isFreezeKeyDown() && System.currentTimeMillis() - lastToggleFreezeTime > 500L) {
                    lastToggleFreezeTime = System.currentTimeMillis();
                    frozen = !frozen;
                    currentContainerPreview = containerPreview;
                }
                if (!frozen) {
                    currentContainerPreview = containerPreview;
                }
                return true;
            }
        }
        return frozen;
    }

    public static NBTTagByteArray getCompressedInventoryContents(IInventory inventory) {
        if (inventory == null) {
            return null;
        }
        ItemStack[] list = new ItemStack[inventory.func_70302_i_()];
        for (int slotNumber = 0; slotNumber < inventory.func_70302_i_(); ++slotNumber) {
            list[slotNumber] = inventory.func_70301_a(slotNumber);
        }
        return ItemUtils.getCompressedNBT(list);
    }

    public static void saveStorageContainerInventory(String storageKey) {
        Map<String, CompressedStorage> cache = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getStorageCache();
        CompressedStorage cachedContainer = cache.get(storageKey);
        byte[] previousCache = cachedContainer == null ? null : cachedContainer.getStorage();
        ContainerChest chest = (ContainerChest)Minecraft.func_71410_x().field_71439_g.field_71070_bA;
        IInventory chestInventory = chest.func_85151_d();
        byte[] bytes = ContainerPreviewManager.getCompressedInventoryContents(chestInventory).func_150292_c();
        boolean dirty = previousCache == null || previousCache.length != bytes.length;
        for (int i = 0; i < bytes.length; ++i) {
            if (dirty || bytes[i] == previousCache[i]) continue;
            dirty = true;
            break;
        }
        if (dirty) {
            if (cachedContainer == null) {
                cache.put(storageKey, new CompressedStorage(bytes));
            } else {
                cachedContainer.setStorage(bytes);
            }
            SkyblockAddons.getInstance().getPersistentValuesManager().saveValues();
            System.out.println("Caching container " + storageKey);
        }
    }

    public static boolean isFrozen() {
        return frozen;
    }
}

