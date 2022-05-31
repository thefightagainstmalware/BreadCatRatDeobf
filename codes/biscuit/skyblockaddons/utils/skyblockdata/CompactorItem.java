/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagList
 */
package codes.biscuit.skyblockaddons.utils.skyblockdata;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.gson.GsonInitializable;
import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

public class CompactorItem
implements GsonInitializable {
    private String itemId;
    private String displayName;
    private boolean enchanted;
    private String skullId;
    private String texture;
    private transient ItemStack itemStack;

    public CompactorItem() {
    }

    public CompactorItem(String theItemId, String theDisplayName, boolean isEnchanted, String theSkullId, String theTexture) {
        this.itemId = theItemId;
        this.displayName = theDisplayName;
        this.enchanted = isEnchanted;
        this.skullId = theSkullId;
        this.texture = theTexture;
        this.makeItemStack();
    }

    public CompactorItem(String theItemId, String theDisplayName, boolean isEnchanted) {
        this(theItemId, theDisplayName, isEnchanted, null, null);
    }

    @Override
    public void gsonInit() {
        this.makeItemStack();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Field f : CompactorItem.class.getDeclaredFields()) {
            builder.append(f.getName()).append(": ").append(f.get(this)).append(", ");
        }
        builder.append("}");
        return builder.toString();
    }

    private void makeItemStack() {
        try {
            if (this.itemId != null) {
                if (this.itemId.equals("skull")) {
                    this.itemStack = ItemUtils.createSkullItemStack(this.displayName, "", this.skullId, this.texture);
                } else {
                    String[] minecraftIdArray = this.itemId.split(":", 2);
                    int meta = minecraftIdArray.length == 2 ? Integer.parseInt(minecraftIdArray[1]) : 0;
                    Item item = Item.func_111206_d((String)minecraftIdArray[0]);
                    if (item != null) {
                        ItemStack itemStack = this.itemStack = minecraftIdArray.length == 1 ? new ItemStack(item) : new ItemStack(item, 1, meta);
                        if (this.enchanted) {
                            this.itemStack.func_77983_a("ench", (NBTBase)new NBTTagList());
                        }
                    }
                }
                if (this.itemStack != null) {
                    this.itemStack.func_151001_c(this.displayName);
                }
            }
        }
        catch (Exception ex) {
            this.itemStack = ItemUtils.createItemStack(Item.func_150898_a((Block)Blocks.field_150348_b), this.displayName != null ? this.displayName : "", this.itemId != null ? this.itemId : "", false);
            SkyblockAddons.getLogger().error("An error occurred while making an itemname with ID " + this.itemId + " and name " + this.displayName + ".", (Throwable)ex);
        }
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}

