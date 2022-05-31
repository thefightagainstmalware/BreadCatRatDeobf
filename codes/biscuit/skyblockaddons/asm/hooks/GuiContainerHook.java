/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.utils.ReturnValue;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.InventoryType;
import codes.biscuit.skyblockaddons.features.backpacks.ContainerPreviewManager;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class GuiContainerHook {
    private static final ResourceLocation LOCK = new ResourceLocation("skyblockaddons", "lock.png");
    private static final int OVERLAY_RED = ColorCode.RED.getColor(127);
    private static final int OVERLAY_GREEN = ColorCode.GREEN.getColor(127);

    public static void keyTyped(int keyCode) {
        ContainerPreviewManager.onContainerKeyTyped(keyCode);
    }

    public static void drawBackpacks(GuiContainer guiContainer, int mouseX, int mouseY, FontRenderer fontRendererObj) {
        ContainerPreviewManager.drawContainerPreviews(guiContainer, mouseX, mouseY);
    }

    public static void setLastSlot() {
        SkyblockAddons.getInstance().getUtils().setLastHoveredSlot(-1);
    }

    public static void drawGradientRect(GuiContainer guiContainer, int left, int top, int right, int bottom, int startColor, int endColor, Slot theSlot) {
        if (ContainerPreviewManager.isFrozen()) {
            return;
        }
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (theSlot != null && theSlot.func_75216_d() && main.getConfigValues().isEnabled(Feature.DISABLE_EMPTY_GLASS_PANES) && main.getUtils().isEmptyGlassPane(theSlot.func_75211_c())) {
            return;
        }
        Container container = Minecraft.func_71410_x().field_71439_g.field_71070_bA;
        if (theSlot != null) {
            int slotNum = theSlot.field_75222_d + main.getInventoryUtils().getSlotDifference(container);
            main.getUtils().setLastHoveredSlot(slotNum);
            if (main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && main.getUtils().isOnSkyblock() && main.getConfigValues().getLockedSlots().contains(slotNum) && (slotNum >= 9 || container instanceof ContainerPlayer && slotNum >= 5)) {
                guiContainer.func_73733_a(left, top, right, bottom, OVERLAY_RED, OVERLAY_RED);
                return;
            }
        }
        guiContainer.func_73733_a(left, top, right, bottom, startColor, endColor);
    }

    public static void drawSlot(GuiContainer guiContainer, Slot slot) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        Minecraft mc = Minecraft.func_71410_x();
        Container container = mc.field_71439_g.field_71070_bA;
        if (slot != null && main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && main.getUtils().isOnSkyblock()) {
            int slotNum = slot.field_75222_d + main.getInventoryUtils().getSlotDifference(container);
            if (main.getConfigValues().getLockedSlots().contains(slotNum) && (slotNum >= 9 || container instanceof ContainerPlayer && slotNum >= 5)) {
                GlStateManager.func_179140_f();
                GlStateManager.func_179097_i();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)0.4f);
                GlStateManager.func_179147_l();
                mc.func_110434_K().func_110577_a(LOCK);
                mc.field_71456_v.func_73729_b(slot.field_75223_e, slot.field_75221_f, 0, 0, 16, 16);
                GlStateManager.func_179145_e();
                GlStateManager.func_179126_j();
            }
        }
    }

    public static void keyTyped(GuiContainer guiContainer, int keyCode, Slot theSlot, ReturnValue<?> returnValue) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        Minecraft mc = Minecraft.func_71410_x();
        if (main.getUtils().isOnSkyblock()) {
            if (main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && keyCode != 1 && keyCode != mc.field_71474_y.field_151445_Q.func_151463_i()) {
                int slot = main.getUtils().getLastHoveredSlot();
                boolean isHotkeying = false;
                if (mc.field_71439_g.field_71071_by.func_70445_o() == null && theSlot != null) {
                    for (int i = 0; i < 9; ++i) {
                        if (keyCode != mc.field_71474_y.field_151456_ac[i].func_151463_i()) continue;
                        slot = i + 36;
                        isHotkeying = true;
                    }
                }
                if (slot >= 9 || mc.field_71439_g.field_71070_bA instanceof ContainerPlayer && slot >= 5) {
                    if (main.getConfigValues().getLockedSlots().contains(slot)) {
                        if (main.getLockSlotKey().func_151463_i() == keyCode) {
                            main.getUtils().playLoudSound("random.orb", 1.0);
                            main.getConfigValues().getLockedSlots().remove(slot);
                            main.getConfigValues().saveConfig();
                        } else if (isHotkeying || mc.field_71474_y.field_74316_C.func_151463_i() == keyCode) {
                            returnValue.cancel();
                            main.getUtils().playLoudSound("note.bass", 0.5);
                            return;
                        }
                    } else if (main.getLockSlotKey().func_151463_i() == keyCode) {
                        main.getUtils().playLoudSound("random.orb", 0.1);
                        main.getConfigValues().getLockedSlots().add(slot);
                        main.getConfigValues().saveConfig();
                    }
                }
            }
            if (mc.field_71474_y.field_74316_C.func_151463_i() == keyCode && main.getConfigValues().isEnabled(Feature.STOP_DROPPING_SELLING_RARE_ITEMS) && !main.getUtils().isInDungeon() && !main.getUtils().getItemDropChecker().canDropItem(theSlot)) {
                returnValue.cancel();
            }
        }
    }

    public static boolean onHandleMouseClick(Slot slot, int slotId, int clickedButton, int clickType) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        return slot != null && slot.func_75216_d() && main.getConfigValues().isEnabled(Feature.DISABLE_EMPTY_GLASS_PANES) && main.getUtils().isEmptyGlassPane(slot.func_75211_c()) && main.getUtils().isOnSkyblock() && !main.getUtils().isInDungeon() && (main.getInventoryUtils().getInventoryType() != InventoryType.ULTRASEQUENCER || main.getUtils().isGlassPaneColor(slot.func_75211_c(), EnumDyeColor.BLACK));
    }
}

