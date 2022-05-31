/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagByteArray
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagString
 *  org.apache.commons.lang3.text.WordUtils
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackColor;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import codes.biscuit.skyblockaddons.utils.skyblockdata.CompactorItem;
import codes.biscuit.skyblockaddons.utils.skyblockdata.ContainerData;
import codes.biscuit.skyblockaddons.utils.skyblockdata.PetInfo;
import codes.biscuit.skyblockaddons.utils.skyblockdata.Rune;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.apache.commons.lang3.text.WordUtils;

public class ItemUtils {
    public static final int NBT_INTEGER = 3;
    public static final int NBT_STRING = 8;
    public static final int NBT_LIST = 9;
    public static final int NBT_COMPOUND = 10;
    private static Map<String, CompactorItem> compactorItems;
    private static Map<String, ContainerData> containers;
    private static final Pattern RARITY_PATTERN;

    public static ItemRarity getRarity(ItemStack item) {
        if (item == null || !item.func_77942_o()) {
            return null;
        }
        NBTTagCompound display = item.func_179543_a("display", false);
        if (display == null || !display.func_74764_b("Lore")) {
            return null;
        }
        NBTTagList lore = display.func_150295_c("Lore", 8);
        for (int i = 0; i < lore.func_74745_c(); ++i) {
            String currentLine = lore.func_150307_f(i);
            Matcher rarityMatcher = RARITY_PATTERN.matcher(currentLine);
            if (!rarityMatcher.find()) continue;
            String rarity = rarityMatcher.group("rarity");
            for (ItemRarity itemRarity : ItemRarity.values()) {
                if (!rarity.startsWith(itemRarity.getLoreName())) continue;
                return itemRarity;
            }
        }
        return null;
    }

    public static ItemStack getPersonalCompactorItemStack(String personalCompactorSkyblockID) {
        CompactorItem compactorItem = compactorItems.get(personalCompactorSkyblockID);
        return compactorItem != null ? compactorItem.getItemStack() : ItemUtils.createSkullItemStack("\u00a77Unknown (" + personalCompactorSkyblockID + ")", Collections.singletonList("\u00a76also biscut was here hi!!"), personalCompactorSkyblockID, "724c64a2-fc8b-4842-852b-6b4c2c6ef241", "e0180f4aeb6929f133c9ff10476ab496f74c46cf8b3be6809798a974929ccca3");
    }

    public static ContainerData getContainerData(String skyblockID) {
        return containers.get(skyblockID);
    }

    public static String getSkyblockItemID(ItemStack item) {
        if (item == null) {
            return null;
        }
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(item);
        if (extraAttributes == null) {
            return null;
        }
        if (!extraAttributes.func_150297_b("id", 8)) {
            return null;
        }
        return extraAttributes.func_74779_i("id");
    }

    public static NBTTagCompound getExtraAttributes(ItemStack item) {
        if (item == null || !item.func_77942_o()) {
            return null;
        }
        return item.func_179543_a("ExtraAttributes", false);
    }

    public static NBTTagCompound getEnchantments(ItemStack item) {
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(item);
        return extraAttributes == null ? null : extraAttributes.func_74775_l("enchantments");
    }

    public static String getReforge(ItemStack item) {
        NBTTagCompound extraAttributes;
        if (item.func_77942_o() && (extraAttributes = item.func_77978_p()).func_74764_b("ExtraAttributes") && (extraAttributes = extraAttributes.func_74775_l("ExtraAttributes")).func_74764_b("modifier")) {
            String reforge = WordUtils.capitalizeFully((String)extraAttributes.func_74779_i("modifier"));
            reforge = reforge.replace("_sword", "");
            reforge = reforge.replace("_bow", "");
            return reforge;
        }
        return null;
    }

    public static boolean isMaterialForRecipe(ItemStack itemStack) {
        List<String> lore = ItemUtils.getItemLore(itemStack);
        for (String loreLine : lore) {
            if (!"Right-click to view recipes!".equals(TextUtils.stripColor(loreLine))) continue;
            return true;
        }
        return false;
    }

    public static boolean isMiningTool(ItemStack itemStack) {
        return itemStack.func_77973_b() instanceof ItemPickaxe || ItemUtils.isDrill(itemStack);
    }

    public static boolean isDrill(ItemStack itemStack) {
        String id = ItemUtils.getSkyblockItemID(itemStack);
        return id != null && (id.startsWith("MITHRIL_DRILL_") || id.startsWith("TITANIUM_DRILL_"));
    }

    public static String getSkyblockItemID(NBTTagCompound extraAttributes) {
        if (extraAttributes == null) {
            return null;
        }
        String itemId = extraAttributes.func_74779_i("id");
        if (itemId.equals("")) {
            return null;
        }
        return itemId;
    }

    public static boolean isBackpack(ItemStack stack) {
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(stack);
        ContainerData containerData = containers.get(ItemUtils.getSkyblockItemID(extraAttributes));
        return containerData != null && containerData.isBackpack();
    }

    public static boolean isBuildersWand(ItemStack stack) {
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(stack);
        ContainerData containerData = containers.get(ItemUtils.getSkyblockItemID(extraAttributes));
        return containerData != null && containerData.isBuildersWand();
    }

    public static BackpackColor getBackpackColor(ItemStack stack) {
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(stack);
        ContainerData containerData = containers.get(ItemUtils.getSkyblockItemID(extraAttributes));
        if (containerData != null) {
            try {
                return BackpackColor.valueOf(extraAttributes.func_74779_i(containerData.getColorTag()));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return BackpackColor.WHITE;
            }
        }
        if (extraAttributes != null && extraAttributes.func_74764_b("backpack_color")) {
            try {
                return BackpackColor.valueOf(extraAttributes.func_74779_i("backpack_color"));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return BackpackColor.WHITE;
            }
        }
        return null;
    }

    public static String getReforge(NBTTagCompound extraAttributes) {
        if (extraAttributes != null && extraAttributes.func_150297_b("modifier", 8)) {
            return extraAttributes.func_74779_i("modifier");
        }
        return null;
    }

    public static Rune getRuneData(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {
            if (!extraAttributes.func_74764_b("runes")) {
                return null;
            }
            return new Rune(extraAttributes.func_74775_l("runes"));
        }
        return null;
    }

    public static PetInfo getPetInfo(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {
            String itemId = extraAttributes.func_74779_i("id");
            if (!itemId.equals("PET") || !extraAttributes.func_74764_b("petInfo")) {
                return null;
            }
            return (PetInfo)SkyblockAddons.getGson().fromJson(extraAttributes.func_74779_i("petInfo"), PetInfo.class);
        }
        return null;
    }

    public static List<String> getItemLore(ItemStack itemStack) {
        NBTTagCompound display;
        if (itemStack.func_77942_o() && itemStack.func_77978_p().func_150297_b("display", 10) && (display = itemStack.func_77978_p().func_74775_l("display")).func_150297_b("Lore", 9)) {
            NBTTagList lore = display.func_150295_c("Lore", 8);
            ArrayList<String> loreAsList = new ArrayList<String>();
            for (int lineNumber = 0; lineNumber < lore.func_74745_c(); ++lineNumber) {
                loreAsList.add(lore.func_150307_f(lineNumber));
            }
            return Collections.unmodifiableList(loreAsList);
        }
        return Collections.emptyList();
    }

    public static void setItemLore(ItemStack itemStack, List<String> lore) {
        NBTTagCompound display = itemStack.func_179543_a("display", true);
        NBTTagList loreTagList = new NBTTagList();
        for (String loreLine : lore) {
            loreTagList.func_74742_a((NBTBase)new NBTTagString(loreLine));
        }
        display.func_74782_a("Lore", (NBTBase)loreTagList);
    }

    public static boolean isMenuItem(ItemStack itemStack) {
        if (itemStack == null) {
            throw new NullPointerException("Item stack cannot be null!");
        }
        NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(itemStack);
        if (extraAttributes != null) {
            return !extraAttributes.func_74764_b("uuid");
        }
        return false;
    }

    public static ItemStack createItemStack(Item item, boolean enchanted) {
        return ItemUtils.createItemStack(item, 0, null, null, enchanted);
    }

    public static ItemStack createItemStack(Item item, String name, String skyblockID, boolean enchanted) {
        return ItemUtils.createItemStack(item, 0, name, skyblockID, enchanted);
    }

    public static ItemStack createItemStack(Item item, int meta, String name, String skyblockID, boolean enchanted) {
        ItemStack stack = new ItemStack(item, 1, meta);
        if (name != null) {
            stack.func_151001_c(name);
        }
        if (enchanted) {
            stack.func_77966_a(Enchantment.field_180310_c, 0);
        }
        if (skyblockID != null) {
            ItemUtils.setItemStackSkyblockID(stack, skyblockID);
        }
        return stack;
    }

    public static ItemStack createEnchantedBook(String name, String skyblockID, String enchantName, int enchantLevel) {
        ItemStack stack = ItemUtils.createItemStack((Item)Items.field_151134_bR, name, skyblockID, false);
        NBTTagCompound enchantments = new NBTTagCompound();
        enchantments.func_74768_a(enchantName, enchantLevel);
        NBTTagCompound extraAttributes = stack.func_77978_p().func_74775_l("ExtraAttributes");
        extraAttributes.func_74782_a("enchantments", (NBTBase)enchantments);
        return stack;
    }

    public static ItemStack createSkullItemStack(String name, String skyblockID, String skullID, String textureURL) {
        ItemStack stack = new ItemStack(Items.field_151144_bL, 1, 3);
        NBTTagCompound texture = new NBTTagCompound();
        texture.func_74778_a("Value", TextUtils.encodeSkinTextureURL(textureURL));
        NBTTagList textures = new NBTTagList();
        textures.func_74742_a((NBTBase)texture);
        NBTTagCompound properties = new NBTTagCompound();
        properties.func_74782_a("textures", (NBTBase)textures);
        NBTTagCompound skullOwner = new NBTTagCompound();
        skullOwner.func_74782_a("Properties", (NBTBase)properties);
        skullOwner.func_74778_a("Id", skullID);
        stack.func_77983_a("SkullOwner", (NBTBase)skullOwner);
        if (name != null) {
            stack.func_151001_c(name);
        }
        if (skyblockID != null) {
            ItemUtils.setItemStackSkyblockID(stack, skyblockID);
        }
        return stack;
    }

    public static ItemStack createSkullItemStack(String name, List<String> lore, String skyblockID, String skullID, String textureURL) {
        ItemStack stack = new ItemStack(Items.field_151144_bL, 1, 3);
        NBTTagCompound texture = new NBTTagCompound();
        texture.func_74778_a("Value", TextUtils.encodeSkinTextureURL(textureURL));
        NBTTagList textures = new NBTTagList();
        textures.func_74742_a((NBTBase)texture);
        NBTTagCompound properties = new NBTTagCompound();
        properties.func_74782_a("textures", (NBTBase)textures);
        NBTTagCompound skullOwner = new NBTTagCompound();
        skullOwner.func_74782_a("Properties", (NBTBase)properties);
        skullOwner.func_74778_a("Id", skullID);
        stack.func_77983_a("SkullOwner", (NBTBase)skullOwner);
        if (name != null) {
            stack.func_151001_c(name);
            ItemUtils.setItemLore(stack, lore);
        }
        if (skyblockID != null) {
            ItemUtils.setItemStackSkyblockID(stack, skyblockID);
        }
        return stack;
    }

    public static void setItemStackSkyblockID(ItemStack itemStack, String skyblockID) {
        NBTTagCompound extraAttributes = new NBTTagCompound();
        extraAttributes.func_74778_a("id", skyblockID);
        itemStack.func_77983_a("ExtraAttributes", (NBTBase)extraAttributes);
    }

    public static String getSkullOwnerID(ItemStack skull) {
        if (skull == null || !skull.func_77942_o()) {
            return null;
        }
        NBTTagCompound nbt = skull.func_77978_p();
        if (nbt.func_150297_b("SkullOwner", 10) && (nbt = nbt.func_74775_l("SkullOwner")).func_150297_b("Id", 8)) {
            return nbt.func_74779_i("Id");
        }
        return null;
    }

    public static NBTTagByteArray getCompressedNBT(ItemStack[] items) {
        if (items == null) {
            return null;
        }
        NBTTagList list = new NBTTagList();
        for (ItemStack item : items) {
            if (item == null) {
                list.func_74742_a((NBTBase)new ItemStack((Item)null).serializeNBT());
                continue;
            }
            list.func_74742_a((NBTBase)item.serializeNBT());
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.func_74782_a("i", (NBTBase)list);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            CompressedStreamTools.func_74799_a((NBTTagCompound)nbt, (OutputStream)stream);
        }
        catch (IOException e) {
            return null;
        }
        return new NBTTagByteArray(stream.toByteArray());
    }

    public static void setCompactorItems(Map<String, CompactorItem> compactorItems) {
        ItemUtils.compactorItems = compactorItems;
    }

    public static void setContainers(Map<String, ContainerData> containers) {
        ItemUtils.containers = containers;
    }

    static {
        RARITY_PATTERN = Pattern.compile("(\u00a7[0-9a-f]\u00a7l\u00a7ka\u00a7r )?([\u00a70-9a-fk-or]+)(?<rarity>[A-Z]+)");
    }
}

