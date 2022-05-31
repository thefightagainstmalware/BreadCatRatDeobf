/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.features.itemdrops;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ItemDropChecker {
    private static final long DROP_CONFIRMATION_TIMEOUT = 3000L;
    private SkyblockAddons main = SkyblockAddons.getInstance();
    private ItemStack itemOfLastDropAttempt;
    private long timeOfLastDropAttempt;
    private int attemptsRequiredToConfirm;

    public boolean canDropItem(ItemStack item) {
        return this.canDropItem(item, false);
    }

    public boolean canDropItem(Slot slot) {
        if (slot != null && slot.func_75216_d()) {
            return this.canDropItem(slot.func_75211_c());
        }
        return true;
    }

    public boolean canDropItem(ItemStack item, boolean itemIsInHotbar) {
        return this.canDropItem(item, itemIsInHotbar, true);
    }

    public boolean canDropItem(ItemStack item, boolean itemIsInHotbar, boolean playAlert) {
        if (this.main.getUtils().isOnSkyblock()) {
            if (ItemUtils.getSkyblockItemID(item) == null) {
                return true;
            }
            if (ItemUtils.getRarity(item) == null) {
                return true;
            }
            String itemID = ItemUtils.getSkyblockItemID(item);
            ItemRarity itemRarity = ItemUtils.getRarity(item);
            List<String> blacklist = this.main.getOnlineData().getDropSettings().getDontDropTheseItems();
            List<String> whitelist = this.main.getOnlineData().getDropSettings().getAllowDroppingTheseItems();
            if (itemIsInHotbar) {
                if (itemRarity.compareTo(this.main.getOnlineData().getDropSettings().getMinimumHotbarRarity()) < 0 && !blacklist.contains(itemID)) {
                    return true;
                }
                if (whitelist.contains(itemID)) {
                    return true;
                }
                if (playAlert) {
                    this.playAlert();
                }
                return false;
            }
            if (itemRarity.compareTo(this.main.getOnlineData().getDropSettings().getMinimumInventoryRarity()) < 0 && !blacklist.contains(itemID)) {
                return true;
            }
            if (whitelist.contains(itemID)) {
                return true;
            }
            return this.dropConfirmed(item, 3, playAlert);
        }
        if (this.main.getConfigValues().isEnabled(Feature.DROP_CONFIRMATION) && this.main.getConfigValues().isEnabled(Feature.DOUBLE_DROP_IN_OTHER_GAMES)) {
            return this.dropConfirmed(item, 2, playAlert);
        }
        return true;
    }

    public boolean dropConfirmed(ItemStack item, int numberOfActions, boolean playAlert) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null!");
        }
        if (numberOfActions < 2) {
            throw new IllegalArgumentException("At least two attempts are required.");
        }
        if (this.itemOfLastDropAttempt == null) {
            this.itemOfLastDropAttempt = item;
            this.timeOfLastDropAttempt = Minecraft.func_71386_F();
            this.attemptsRequiredToConfirm = numberOfActions - 1;
            this.onDropConfirmationFail();
            return false;
        }
        if (Minecraft.func_71386_F() - this.timeOfLastDropAttempt > 3000L || !ItemStack.func_77989_b((ItemStack)item, (ItemStack)this.itemOfLastDropAttempt)) {
            this.resetDropConfirmation();
            return this.dropConfirmed(item, numberOfActions, playAlert);
        }
        if (this.attemptsRequiredToConfirm >= 1) {
            this.onDropConfirmationFail();
            return false;
        }
        this.resetDropConfirmation();
        return true;
    }

    public void onDropConfirmationFail() {
        ColorCode colorCode = this.main.getConfigValues().getRestrictedColor(Feature.DROP_CONFIRMATION);
        if (this.attemptsRequiredToConfirm >= 2) {
            String multipleAttemptsRequiredMessage = Message.MESSAGE_CLICK_MORE_TIMES.getMessage(Integer.toString(this.attemptsRequiredToConfirm));
            this.main.getUtils().sendMessage((Object)((Object)colorCode) + multipleAttemptsRequiredMessage);
        } else {
            String oneMoreAttemptRequiredMessage = Message.MESSAGE_CLICK_ONE_MORE_TIME.getMessage(new String[0]);
            this.main.getUtils().sendMessage((Object)((Object)colorCode) + oneMoreAttemptRequiredMessage);
        }
        this.playAlert();
        --this.attemptsRequiredToConfirm;
    }

    public void playAlert() {
        this.main.getUtils().playLoudSound("note.bass", 0.5);
    }

    public void resetDropConfirmation() {
        this.itemOfLastDropAttempt = null;
        this.timeOfLastDropAttempt = 0L;
        this.attemptsRequiredToConfirm = 0;
    }
}

