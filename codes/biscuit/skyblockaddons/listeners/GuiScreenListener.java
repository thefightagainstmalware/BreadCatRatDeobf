/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.inventory.Slot
 *  net.minecraft.nbt.NBTBase
 *  net.minecraftforge.client.event.GuiScreenEvent$KeyboardInputEvent$Pre
 *  net.minecraftforge.client.event.GuiScreenEvent$MouseInputEvent$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package codes.biscuit.skyblockaddons.listeners;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DevUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiScreenListener {
    private final SkyblockAddons main = SkyblockAddons.getInstance();

    @SubscribeEvent
    public void onKeyInput(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        Slot currentSlot;
        GuiScreen currentScreen;
        int eventKey = Keyboard.getEventKey();
        if (this.main.isDevMode() && eventKey == this.main.getDeveloperCopyNBTKey().getKeyCode() && Keyboard.getEventKeyState() && GuiContainer.class.isAssignableFrom((currentScreen = event.gui).getClass()) && (currentSlot = ((GuiContainer)currentScreen).getSlotUnderMouse()) != null && currentSlot.func_75216_d()) {
            DevUtils.copyNBTTagToClipboard((NBTBase)currentSlot.func_75211_c().serializeNBT(), (Object)((Object)ColorCode.GREEN) + "Item data was copied to clipboard!");
        }
    }

    @SubscribeEvent
    public void onMouseClick(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (!this.main.getUtils().isOnSkyblock()) {
            return;
        }
        int eventButton = Mouse.getEventButton();
        if (!Mouse.getEventButtonState()) {
            return;
        }
        if (this.main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) && event.gui instanceof GuiContainer) {
            GuiContainer guiContainer = (GuiContainer)event.gui;
            if (eventButton >= 0) {
                for (int i = 0; i < 9; ++i) {
                    if (eventButton - 100 != Minecraft.func_71410_x().field_71474_y.field_151456_ac[i].func_151463_i()) continue;
                    Slot slot = guiContainer.getSlotUnderMouse();
                    Slot hotbarSlot = guiContainer.field_147002_h.func_75139_a(guiContainer.field_147002_h.field_75151_b.size() - (9 - i));
                    if (slot == null || hotbarSlot == null) {
                        return;
                    }
                    if (!this.main.getConfigValues().getLockedSlots().contains(i + 36)) continue;
                    if (!slot.func_75216_d() && !hotbarSlot.func_75216_d()) {
                        return;
                    }
                    this.main.getUtils().playLoudSound("note.bass", 0.5);
                    this.main.getUtils().sendMessage((Object)((Object)this.main.getConfigValues().getRestrictedColor(Feature.DROP_CONFIRMATION)) + Message.MESSAGE_SLOT_LOCKED.getMessage(new String[0]));
                    event.setCanceled(true);
                }
            }
        }
    }
}

