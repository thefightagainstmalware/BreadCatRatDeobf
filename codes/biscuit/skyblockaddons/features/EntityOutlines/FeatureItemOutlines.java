/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package codes.biscuit.skyblockaddons.features.EntityOutlines;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.events.RenderEntityOutlineEvent;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureItemOutlines {
    private static final HashSet<Location> SHOWCASE_ITEM_LOCATIONS = new HashSet<Location>(Arrays.asList(Location.VILLAGE, Location.AUCTION_HOUSE, Location.BANK, Location.BAZAAR, Location.COAL_MINE, Location.LIBRARY, Location.JERRYS_WORKSHOP, Location.THE_END));
    private static Location location;
    private static ConfigValues config;
    private static final Function<Entity, Integer> OUTLINE_COLOR;

    private static boolean GLOBAL_TEST() {
        return config.isEnabled(Feature.MAKE_DROPPED_ITEMS_GLOW) && (config.isEnabled(Feature.SHOW_GLOWING_ITEMS_ON_ISLAND) || location != Location.ISLAND);
    }

    private static boolean isShopShowcaseItem(EntityItem entityItem) {
        for (EntityArmorStand entityArmorStand : entityItem.field_70170_p.func_72872_a(EntityArmorStand.class, entityItem.func_174813_aQ())) {
            if (!entityArmorStand.func_82150_aj() || entityArmorStand.func_71124_b(4) == null || entityArmorStand.func_71124_b(4).func_77973_b() != Item.func_150898_a((Block)Blocks.field_150359_w)) continue;
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public void onRenderEntityOutlines(RenderEntityOutlineEvent e) {
        location = SkyblockAddons.getInstance().getUtils().getLocation();
        config = SkyblockAddons.getInstance().getConfigValues();
        if (e.getType() == RenderEntityOutlineEvent.Type.XRAY && FeatureItemOutlines.GLOBAL_TEST()) {
            e.queueEntitiesToOutline(OUTLINE_COLOR);
        }
    }

    static {
        OUTLINE_COLOR = e -> {
            if (!(!(e instanceof EntityItem) || SHOWCASE_ITEM_LOCATIONS.contains((Object)location) && FeatureItemOutlines.isShopShowcaseItem((EntityItem)e))) {
                ItemRarity itemRarity = ItemUtils.getRarity(((EntityItem)e).func_92059_d());
                if (itemRarity != null) {
                    return itemRarity.getColorCode().getColor();
                }
                return null;
            }
            return null;
        };
    }
}

