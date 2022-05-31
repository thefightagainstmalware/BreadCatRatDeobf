/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.passive.EntityChicken
 *  net.minecraft.entity.passive.EntityCow
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraft.entity.passive.EntityRabbit
 *  net.minecraft.entity.passive.EntitySheep
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 */
package codes.biscuit.skyblockaddons.features.EntityOutlines;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.events.RenderEntityOutlineEvent;
import codes.biscuit.skyblockaddons.features.cooldowns.CooldownManager;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonLocation;
import codes.biscuit.skyblockaddons.listeners.RenderListener;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DrawUtils;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FeatureTrackerQuest {
    private static final EnumSet<Location> mushroomIslandLocations = EnumSet.of(Location.MUSHROOM_DESERT, new Location[]{Location.TRAPPERS_DEN, Location.DESERT_SETTLEMENT, Location.OASIS, Location.GLOWING_MUSHROOM_CAVE, Location.MUSHROOM_GORGE, Location.SHEPHERDS_KEEP, Location.OVERGROWN_MUSHROOM_CAVE, Location.JAKES_HOUSE, Location.TREASURE_HUNTER_CAMP});
    private static final Pattern TRACKED_ANIMAL_NAME_PATTERN = Pattern.compile("\\[Lv[0-9]+] (?<rarity>[a-zA-Z]+) (?<animal>[a-zA-Z]+) .*\u2764");
    private static final Pattern TREVOR_FIND_ANIMAL_PATTERN = Pattern.compile("\\[NPC] Trevor The Trapper: You can find your [A-Z]+ animal near the [a-zA-Z ]+.");
    private static final Pattern ANIMAL_DIED_PATTERN = Pattern.compile("Your mob died randomly, you are rewarded [0-9]+ pelts?.");
    private static final Pattern ANIMAL_KILLED_PATTERN = Pattern.compile("Killing the animal rewarded you [0-9]+ pelts?.");
    private static final ResourceLocation TICKER_SYMBOL = new ResourceLocation("skyblockaddons", "tracker.png");
    private static boolean isTrackingAnimal = false;
    private static TrackedEntity entityToOutline = null;

    public static void drawTrackerLocationIndicator(Minecraft mc, float scale, ButtonLocation buttonLocation) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (buttonLocation != null || isTrackingAnimal) {
            RenderListener listener = main.getRenderListener();
            float x = main.getConfigValues().getActualX(Feature.TREVOR_TRACKED_ENTITY_PROXIMITY_INDICATOR);
            float y = main.getConfigValues().getActualY(Feature.TREVOR_TRACKED_ENTITY_PROXIMITY_INDICATOR);
            int height = 9;
            int width = 42;
            x = listener.transformXY(x, width, scale);
            y = listener.transformXY(y, height, scale);
            if (buttonLocation != null) {
                buttonLocation.checkHoveredAndDrawBox(x, x + (float)width, y, y + (float)height, scale);
            }
            main.getUtils().enableStandardGLOptions();
            int maxTickers = 4;
            int fullTickers = buttonLocation != null ? 3 : (entityToOutline == null ? FeatureTrackerQuest.getFlashingTickers() : (entityToOutline.getDistanceToPlayer() < 16.0 ? 4 : (entityToOutline.getDistanceToPlayer() < 32.0 ? 3 : (entityToOutline.getDistanceToPlayer() < 48.0 ? 2 : (entityToOutline.getDistanceToPlayer() < 64.0 ? 1 : FeatureTrackerQuest.getFlashingTickers())))));
            for (int tickers = 0; tickers < maxTickers; ++tickers) {
                mc.func_110434_K().func_110577_a(TICKER_SYMBOL);
                GlStateManager.func_179141_d();
                if (tickers < fullTickers) {
                    DrawUtils.drawModalRectWithCustomSizedTexture(x + (float)(tickers * 11), y, 0.0f, 0.0f, 9.0f, 9.0f, 18.0f, 9.0f, false);
                    continue;
                }
                DrawUtils.drawModalRectWithCustomSizedTexture(x + (float)(tickers * 11), y, 9.0f, 0.0f, 9.0f, 9.0f, 18.0f, 9.0f, false);
            }
            main.getUtils().restoreGLOptions();
        }
    }

    private static int getFlashingTickers() {
        if (CooldownManager.getRemainingCooldown("TREVOR_THE_TRAPPER_HUNT") % 2000L < 1000L) {
            return 0;
        }
        return 1;
    }

    @SubscribeEvent
    public void onEntityOutline(RenderEntityOutlineEvent e) {
        if (e.getType() == RenderEntityOutlineEvent.Type.NO_XRAY && SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.TREVOR_THE_TRAPPER_FEATURES) && SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.TREVOR_HIGHLIGHT_TRACKED_ENTITY) && isTrackingAnimal && entityToOutline != null && entityToOutline.getAnimal() != null && !Minecraft.func_71410_x().field_71439_g.func_70644_a(Potion.field_76440_q)) {
            e.queueEntityToOutline(entityToOutline.getAnimal(), entityToOutline.getRarity().getColorInt());
        }
    }

    @SubscribeEvent
    public void onEntityEvent(LivingEvent.LivingUpdateEvent e) {
        Matcher m;
        SkyblockAddons main = SkyblockAddons.getInstance();
        Entity entity = e.entity;
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.TREVOR_THE_TRAPPER_FEATURES) && (main.getConfigValues().isEnabled(Feature.TREVOR_TRACKED_ENTITY_PROXIMITY_INDICATOR) || main.getConfigValues().isEnabled(Feature.TREVOR_HIGHLIGHT_TRACKED_ENTITY)) && mushroomIslandLocations.contains((Object)main.getUtils().getLocation()) && entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.field_70173_aa > 30 && (m = TRACKED_ANIMAL_NAME_PATTERN.matcher(TextUtils.stripColor(entity.func_95999_t()))).matches()) {
            TrackerRarity rarity = TrackerRarity.getFromString(m.group("rarity"));
            TrackerType animal = TrackerType.getFromString(m.group("animal"));
            if (rarity != null && animal != null) {
                try {
                    TrackedEntity trackedEntity = new TrackedEntity((EntityArmorStand)entity, animal, rarity);
                    trackedEntity.attachAnimal(Minecraft.func_71410_x().field_71441_e.func_72872_a(animal.getClazz(), new AxisAlignedBB(entity.field_70165_t - 2.0, entity.field_70163_u - 2.0, entity.field_70161_v - 2.0, entity.field_70165_t + 2.0, entity.field_70163_u + 2.0, entity.field_70161_v + 2.0)));
                    entityToOutline = trackedEntity;
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
        }
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.TREVOR_THE_TRAPPER_FEATURES) && e.type != 2 && SkyblockAddons.getInstance().getUtils().isOnSkyblock()) {
            String stripped = TextUtils.stripColor(e.message.func_150254_d());
            if (TREVOR_FIND_ANIMAL_PATTERN.matcher(stripped).matches()) {
                isTrackingAnimal = true;
                CooldownManager.put("TREVOR_THE_TRAPPER_HUNT", 600000L);
                CooldownManager.put("TREVOR_THE_TRAPPER_RETURN", 60000L);
            } else if (ANIMAL_DIED_PATTERN.matcher(stripped).matches() || ANIMAL_KILLED_PATTERN.matcher(stripped).matches()) {
                CooldownManager.remove("TREVOR_THE_TRAPPER_HUNT");
                this.onQuestEnded();
            }
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onNameTagRender(RenderLivingEvent.Specials.Pre<EntityLivingBase> e) {
        String s;
        Pattern p;
        EntityLivingBase entity = e.entity;
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.TREVOR_THE_TRAPPER_FEATURES) && !e.isCanceled() && SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.TREVOR_SHOW_QUEST_COOLDOWN) && CooldownManager.isOnCooldown("TREVOR_THE_TRAPPER_RETURN") && (p = Pattern.compile("Trevor The Trapper")).matcher(s = TextUtils.stripColor(entity.func_95999_t())).matches()) {
            String str = Translations.getMessage("messages.worldRenderedCooldownTime", CooldownManager.getRemainingCooldown("TREVOR_THE_TRAPPER_RETURN") / 1000L);
            DrawUtils.drawTextInWorld(str, e.x, e.y + (double)entity.field_70131_O + 0.75, e.z);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.TREVOR_THE_TRAPPER_FEATURES) && e.phase == TickEvent.Phase.START && Minecraft.func_71410_x().field_71439_g != null) {
            if (isTrackingAnimal && CooldownManager.getRemainingCooldown("TREVOR_THE_TRAPPER_HUNT") == 0L) {
                this.onQuestEnded();
            } else if (entityToOutline != null) {
                entityToOutline.cacheDistanceToPlayer();
            }
        }
    }

    private void onQuestEnded() {
        isTrackingAnimal = false;
        entityToOutline = null;
    }

    private static class TrackedEntity {
        private final EntityArmorStand armorStand;
        private final TrackerType type;
        private final TrackerRarity rarity;
        private Entity animal;
        private double distanceToPlayer;

        public TrackedEntity(EntityArmorStand theArmorStand, TrackerType trackerType, TrackerRarity trackerRarity) {
            this.armorStand = theArmorStand;
            this.type = trackerType;
            this.rarity = trackerRarity;
            this.cacheDistanceToPlayer();
        }

        public void attachAnimal(List<Entity> animalList) {
            if (animalList.size() == 0) {
                this.animal = null;
            }
            double minDist = Double.MAX_VALUE;
            for (Entity e : animalList) {
                double horizDist = (e.field_70165_t - this.armorStand.field_70165_t) * (e.field_70165_t - this.armorStand.field_70165_t) + (e.field_70161_v - this.armorStand.field_70161_v) * (e.field_70161_v - this.armorStand.field_70161_v);
                if (!(horizDist < minDist) || !(Math.abs(e.field_70163_u - this.armorStand.field_70163_u) < 2.0)) continue;
                minDist = horizDist;
                this.animal = e;
            }
        }

        public void cacheDistanceToPlayer() {
            this.distanceToPlayer = this.animal != null ? (double)Minecraft.func_71410_x().field_71439_g.func_70032_d(this.animal) : (double)Minecraft.func_71410_x().field_71439_g.func_70032_d((Entity)this.armorStand);
        }

        public EntityArmorStand getArmorStand() {
            return this.armorStand;
        }

        public TrackerType getType() {
            return this.type;
        }

        public TrackerRarity getRarity() {
            return this.rarity;
        }

        public Entity getAnimal() {
            return this.animal;
        }

        public double getDistanceToPlayer() {
            return this.distanceToPlayer;
        }
    }

    private static enum TrackerRarity {
        TRACKABLE("Trackable", ColorCode.WHITE),
        UNTRACKABLE("Untrackable", ColorCode.DARK_GREEN),
        UNDETECTED("Undetected", ColorCode.DARK_BLUE),
        ENDANGERED("Endangered", ColorCode.DARK_PURPLE),
        ELUSIVE("Elusive", ColorCode.GOLD);

        private final String nameTagName;
        private final ColorCode colorCode;
        private final int colorInt;

        private TrackerRarity(String nameTag, ColorCode color) {
            this.nameTagName = nameTag;
            this.colorCode = color;
            this.colorInt = color.getColor();
        }

        public static TrackerRarity getFromString(String s) {
            for (TrackerRarity type : TrackerRarity.values()) {
                if (!type.nameTagName.equals(s)) continue;
                return type;
            }
            return null;
        }

        public String getNameTagName() {
            return this.nameTagName;
        }

        public ColorCode getColorCode() {
            return this.colorCode;
        }

        public int getColorInt() {
            return this.colorInt;
        }
    }

    private static enum TrackerType {
        COW("Cow", EntityCow.class),
        PIG("Pig", EntityPig.class),
        SHEEP("Sheep", EntitySheep.class),
        RABBIT("Rabbit", EntityRabbit.class),
        CHICKEN("Chicken", EntityChicken.class);

        private final String name;
        private final Class<? extends Entity> clazz;

        private TrackerType(String entityName, Class<? extends Entity> entityClass) {
            this.name = entityName;
            this.clazz = entityClass;
        }

        public static TrackerType getFromString(String s) {
            for (TrackerType type : TrackerType.values()) {
                if (!type.name.equals(s)) continue;
                return type;
            }
            return null;
        }

        public String getName() {
            return this.name;
        }

        public Class<? extends Entity> getClazz() {
            return this.clazz;
        }
    }
}

