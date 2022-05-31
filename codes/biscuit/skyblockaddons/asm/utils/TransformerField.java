/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.FieldInsnNode
 */
package codes.biscuit.skyblockaddons.asm.utils;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.tweaker.SkyblockAddonsTransformer;
import org.objectweb.asm.tree.FieldInsnNode;

public enum TransformerField {
    width("width", "field_146294_l", "l", "I"),
    height("height", "field_146295_m", "m", "I"),
    buttonList("buttonList", "field_146292_n", "n", "Ljava/util/List;"),
    id("id", "field_146127_k", "k", "I"),
    lowerChestInventory("lowerChestInventory", "field_147015_w", "w", TransformerClass.IInventory.getName()),
    guiLeft("guiLeft", "field_147003_i", "i", "I"),
    guiTop("guiTop", "field_147009_r", "r", "I"),
    fontRendererObj("fontRendererObj", "field_146289_q", "q", TransformerClass.FontRenderer.getName()),
    inventorySlots("inventorySlots", "field_147002_h", "h", TransformerClass.Container.getName()),
    theSlot("theSlot", "field_147006_u", "u", TransformerClass.Slot.getName()),
    xSize("xSize", "field_146999_f", "f", "I"),
    ySize("ySize", "field_147000_g", "g", "I"),
    mcResourceManager("mcResourceManager", "field_110451_am", "ay", TransformerClass.IReloadableResourceManager.getName()),
    currentScreen("currentScreen", "field_71462_r", "m", TransformerClass.GuiScreen.getName()),
    red("red", "field_78291_n", "m", "F"),
    green("green", "field_179186_b", "b", "F"),
    blue("blue", "field_78292_o", "n", "F"),
    alpha("alpha", "field_78305_q", "p", "F"),
    italicStyle("italicStyle", "field_78301_u", "t", "Z"),
    hurtTime("hurtTime", "field_70737_aN ", "au", "I"),
    inventory("inventory", "field_71071_by", "bi", TransformerClass.InventoryPlayer.getName()),
    currentItem("currentItem", "field_70461_c", "c", "I"),
    armorInventory("armorInventory", "field_70460_b", "b", "[" + TransformerClass.ItemStack.getName()),
    renderEndNanoTime("renderEndNanoTime", "field_78510_Z", "F", "J");

    private String name;
    private String type;

    private TransformerField(String deobfName, String seargeName, String notchName18, String type) {
        this.type = type;
        this.name = SkyblockAddonsTransformer.isDeobfuscated() ? deobfName : (SkyblockAddonsTransformer.isUsingNotchMappings() ? notchName18 : seargeName);
    }

    public FieldInsnNode getField(TransformerClass currentClass) {
        return new FieldInsnNode(180, currentClass.getNameRaw(), this.name, this.type);
    }

    public FieldInsnNode putField(TransformerClass currentClass) {
        return new FieldInsnNode(181, currentClass.getNameRaw(), this.name, this.type);
    }

    public boolean matches(FieldInsnNode fieldInsnNode) {
        return this.name.equals(fieldInsnNode.name) && this.type.equals(fieldInsnNode.desc);
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
}

