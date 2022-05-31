/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package codes.biscuit.skyblockaddons.features;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.misc.scheduler.Scheduler;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FetchurManager {
    private static final long MILISECONDS_IN_A_DAY = 86400000L;
    private static final TimeZone fetchurZone = TimeZone.getTimeZone("America/New_York");
    private static final Calendar fetchurCalendar = new GregorianCalendar(TimeZone.getTimeZone("America/New_York"));
    private final String fetchurTaskCompletedPhrase = "thanks thats probably what i needed";
    private final String fetchurAlreadyDidTaskPhrase = "come back another time, maybe tmrw";
    private static final FetchurItem[] items = new FetchurItem[]{new FetchurItem(new ItemStack(Blocks.field_150325_L, 50, 14), "Red Wool"), new FetchurItem(new ItemStack((Block)Blocks.field_150399_cn, 20, 4), "Yellow Stained Glass"), new FetchurItem(new ItemStack(Items.field_151111_aL, 1), "Compass"), new FetchurItem(new ItemStack(Items.field_179563_cD, 20), "Mithril"), new FetchurItem(new ItemStack(Items.field_151152_bP, 1), "Firework Rocket"), new FetchurItem(ItemUtils.createSkullItemStack("\u00a7fCheap Coffee", null, "2fd02c32-6d35-3a1a-958b-e8c5a657c7d4", "194221a0de936bac5ce895f2acad19c64795c18ce5555b971594205bd3ec"), "Cheap Coffee"), new FetchurItem(new ItemStack(Items.field_179570_aq, 1), "Wooden Door"), new FetchurItem(new ItemStack(Items.field_179556_br, 3), "Rabbit's Feet"), new FetchurItem(new ItemStack(Blocks.field_150335_W, 1), "Superboom TNT"), new FetchurItem(new ItemStack(Blocks.field_150423_aK, 1), "Pumpkin"), new FetchurItem(new ItemStack(Items.field_151033_d, 1), "Flint and Steel"), new FetchurItem(new ItemStack(Blocks.field_150449_bY, 50), "Nether Quartz Ore"), new FetchurItem(new ItemStack(Items.field_151079_bi, 16), "Ender Pearl")};
    private static final FetchurManager instance = new FetchurManager();
    private FetchurItem currentItemSaved = null;

    public FetchurItem getCurrentFetchurItem() {
        int dayIdx = this.getFetchurDayOfMonth(System.currentTimeMillis()) - 1;
        return items[dayIdx % items.length];
    }

    public boolean hasFetchedToday() {
        long lastTimeFetched = SkyblockAddons.getInstance().getPersistentValuesManager().getPersistentValues().getLastTimeFetchur();
        long currTime = System.currentTimeMillis();
        return currTime - lastTimeFetched < 86400000L && this.getFetchurDayOfMonth(lastTimeFetched) == this.getFetchurDayOfMonth(currTime);
    }

    private int getFetchurDayOfMonth(long currTimeMilis) {
        fetchurCalendar.setTimeInMillis(currTimeMilis);
        return fetchurCalendar.get(5);
    }

    public void recalculateFetchurItem() {
        FetchurItem item = this.getCurrentFetchurItem();
        if (!item.equals(this.currentItemSaved)) {
            this.currentItemSaved = item;
            SkyblockAddons main = SkyblockAddons.getInstance();
            if (main.getConfigValues().isEnabled(Feature.WARN_WHEN_FETCHUR_CHANGES)) {
                main.getUtils().playLoudSound("random.orb", 0.5);
                main.getRenderListener().setTitleFeature(Feature.WARN_WHEN_FETCHUR_CHANGES);
                main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, main.getConfigValues().getWarningSeconds(), new Object[0]);
            }
        }
    }

    public void saveLastTimeFetched() {
        SkyblockAddons main = SkyblockAddons.getInstance();
        main.getPersistentValuesManager().getPersistentValues().setLastTimeFetchur(System.currentTimeMillis());
        main.getPersistentValuesManager().saveValues();
    }

    public void postPersistentConfigLoad() {
        if (this.hasFetchedToday()) {
            this.currentItemSaved = this.getCurrentFetchurItem();
        }
    }

    public static TimeZone getFetchurZone() {
        return fetchurZone;
    }

    public String getFetchurTaskCompletedPhrase() {
        return this.fetchurTaskCompletedPhrase;
    }

    public String getFetchurAlreadyDidTaskPhrase() {
        return this.fetchurAlreadyDidTaskPhrase;
    }

    public static FetchurManager getInstance() {
        return instance;
    }

    public FetchurItem getCurrentItemSaved() {
        return this.currentItemSaved;
    }

    public void setCurrentItemSaved(FetchurItem currentItemSaved) {
        this.currentItemSaved = currentItemSaved;
    }

    public static class FetchurItem {
        private final ItemStack itemStack;
        private final String itemText;

        public FetchurItem(ItemStack itemStack, String itemText) {
            this.itemStack = itemStack;
            this.itemText = itemText;
        }

        public boolean equals(Object anotherObject) {
            if (!(anotherObject instanceof FetchurItem)) {
                return false;
            }
            FetchurItem another = (FetchurItem)anotherObject;
            return another.getItemText().equals(this.getItemText()) && another.getItemStack().equals(this.getItemStack());
        }

        public ItemStack getItemStack() {
            return this.itemStack;
        }

        public String getItemText() {
            return this.itemText;
        }
    }
}

