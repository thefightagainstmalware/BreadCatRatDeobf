/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockPrismarine$EnumType
 *  net.minecraft.block.BlockStone$EnumType
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.PositionedSoundRecord
 *  net.minecraft.client.audio.SoundCategory
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.EntityBlaze
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityMagmaCube
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.entity.projectile.EntityFishHook
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.client.event.sound.PlaySoundEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.EntityEvent$EnteringChunk
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.event.entity.living.EnderTeleportEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$Action
 *  net.minecraftforge.event.world.ChunkEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.listeners;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.asm.hooks.GuiChestHook;
import codes.biscuit.skyblockaddons.config.PersistentValuesManager;
import codes.biscuit.skyblockaddons.core.Attribute;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.InventoryType;
import codes.biscuit.skyblockaddons.core.ItemRarity;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.SkillType;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonMilestone;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonPlayer;
import codes.biscuit.skyblockaddons.core.npc.NPCUtils;
import codes.biscuit.skyblockaddons.core.seacreatures.SeaCreatureManager;
import codes.biscuit.skyblockaddons.events.DungeonPlayerReviveEvent;
import codes.biscuit.skyblockaddons.events.SkyblockBlockBreakEvent;
import codes.biscuit.skyblockaddons.events.SkyblockPlayerDeathEvent;
import codes.biscuit.skyblockaddons.features.BaitManager;
import codes.biscuit.skyblockaddons.features.EndstoneProtectorManager;
import codes.biscuit.skyblockaddons.features.FetchurManager;
import codes.biscuit.skyblockaddons.features.JerryPresent;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackColor;
import codes.biscuit.skyblockaddons.features.backpacks.BackpackInventoryManager;
import codes.biscuit.skyblockaddons.features.cooldowns.CooldownManager;
import codes.biscuit.skyblockaddons.features.dragontracker.DragonTracker;
import codes.biscuit.skyblockaddons.features.enchants.EnchantManager;
import codes.biscuit.skyblockaddons.features.fishParticles.FishParticleManager;
import codes.biscuit.skyblockaddons.features.powerorbs.PowerOrbManager;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerTracker;
import codes.biscuit.skyblockaddons.features.tablist.TabListParser;
import codes.biscuit.skyblockaddons.features.tabtimers.TabEffectManager;
import codes.biscuit.skyblockaddons.gui.IslandWarpGui;
import codes.biscuit.skyblockaddons.misc.scheduler.Scheduler;
import codes.biscuit.skyblockaddons.misc.scheduler.SkyblockRunnable;
import codes.biscuit.skyblockaddons.utils.APIManager;
import codes.biscuit.skyblockaddons.utils.ActionBarParser;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DevUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.InventoryUtils;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.MathUtils;
import codes.biscuit.skyblockaddons.utils.RomanNumeralParser;
import codes.biscuit.skyblockaddons.utils.ScoreboardManager;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import codes.biscuit.skyblockaddons.utils.objects.IntPair;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class PlayerListener {
    private static final Pattern NO_ARROWS_LEFT_PATTERN = Pattern.compile("(?:\u00a7r)?\u00a7cYou don't have any more Arrows left in your Quiver!\u00a7r");
    private static final Pattern ONLY_HAVE_ARROWS_LEFT_PATTERN = Pattern.compile("(?:\u00a7r)?\u00a7cYou only have (?<arrows>[0-9]+) Arrows left in your Quiver!\u00a7r");
    private static final String ENCHANT_LINE_STARTS_WITH = "\u00a75\u00a7o\u00a79";
    private static final Pattern ABILITY_CHAT_PATTERN = Pattern.compile("\u00a7r\u00a7aUsed \u00a7r\u00a76[A-Za-z ]+\u00a7r\u00a7a! \u00a7r\u00a7b\\([0-9]+ Mana\\)\u00a7r");
    private static final Pattern PROFILE_CHAT_PATTERN = Pattern.compile("You are playing on profile: ([A-Za-z]+).*");
    private static final Pattern SWITCH_PROFILE_CHAT_PATTERN = Pattern.compile("Your profile was changed to: ([A-Za-z]+).*");
    private static final Pattern MINION_CANT_REACH_PATTERN = Pattern.compile("\u00a7cI can't reach any (?<mobName>[A-Za-z]*)s");
    private static final Pattern DRAGON_KILLED_PATTERN = Pattern.compile(" *[A-Z]* DRAGON DOWN!");
    private static final Pattern DRAGON_SPAWNED_PATTERN = Pattern.compile("\u262c The (?<dragonType>[A-Za-z ]+) Dragon has spawned!");
    private static final Pattern SLAYER_COMPLETED_PATTERN = Pattern.compile(" {3}\u00bb Talk to Maddox to claim your (?<slayerType>[A-Za-z]+) Slayer XP!");
    private static final Pattern SLAYER_COMPLETED_PATTERN_AUTO1 = Pattern.compile(" *(?<slayerType>[A-Za-z]+) Slayer LVL \\d+ - Next LVL in [\\d,]+ XP!");
    private static final Pattern SLAYER_COMPLETED_PATTERN_AUTO2 = Pattern.compile(" *SLAYER QUEST STARTED!");
    private static final Pattern DEATH_MESSAGE_PATTERN = Pattern.compile(" \u2620 (?<username>\\w+) (?<causeOfDeath>.+)\\.");
    private static final Pattern REVIVE_MESSAGE_PATTERN = Pattern.compile(" \u2763 (?<revivedPlayer>\\w+) was revived(?: by (?<reviver>\\w+))*!");
    private static final Pattern ACCESSORY_BAG_REFORGE_PATTERN = Pattern.compile("You applied the (?<reforge>\\w+) reforge to \\d+ accessories in your Accessory Bag!");
    private static final Pattern NEXT_TIER_PET_PROGRESS = Pattern.compile("Next tier: (?<total>[0-9,]+)/.*");
    private static final Pattern MAXED_TIER_PET_PROGRESS = Pattern.compile(".*: (?<total>[0-9,]+)");
    private static final AxisAlignedBB MAGMA_BOSS_SPAWN_AREA = new AxisAlignedBB(-244.0, 0.0, -566.0, -379.0, 255.0, -635.0);
    private static final Set<String> SOUP_RANDOM_MESSAGES = new HashSet<String>(Arrays.asList("I feel like I can fly!", "What was in that soup?", "Hmm\u2026 tasty!", "Hmm... tasty!", "You can now fly for 2 minutes.", "Your flight has been extended for 2 extra minutes.", "You can now fly for 200 minutes.", "Your flight has been extended for 200 extra minutes."));
    private static final Set<String> BONZO_STAFF_SOUNDS = new HashSet<String>(Arrays.asList("fireworks.blast", "fireworks.blast_far", "fireworks.twinkle", "fireworks.twinkle_far", "mob.ghast.moan"));
    private static final Set<PositionedSoundRecord> RAT_SOUNDS = new HashSet<PositionedSoundRecord>(Arrays.asList(new PositionedSoundRecord(new ResourceLocation("minecraft", "mob.bat.idle"), 1.0f, 1.1904762f, 0.0f, 0.0f, 0.0f), new PositionedSoundRecord(new ResourceLocation("minecraft", "mob.chicken.step"), 0.15f, 1.0f, 0.0f, 0.0f, 0.0f)));
    private static final Set<Integer> ORES = Sets.newHashSet((Object[])new Integer[]{Block.func_149682_b((Block)Blocks.field_150365_q), Block.func_149682_b((Block)Blocks.field_150366_p), Block.func_149682_b((Block)Blocks.field_150352_o), Block.func_149682_b((Block)Blocks.field_150450_ax), Block.func_149682_b((Block)Blocks.field_150412_bA), Block.func_149682_b((Block)Blocks.field_150369_x), Block.func_149682_b((Block)Blocks.field_150482_ag), Block.func_149682_b((Block)Blocks.field_150439_ay), Block.func_149682_b((Block)Blocks.field_150343_Z), Block.func_149682_b((Block)Blocks.field_150484_ah), Utils.getBlockMetaId(Blocks.field_150348_b, BlockStone.EnumType.DIORITE_SMOOTH.func_176642_a()), Utils.getBlockMetaId(Blocks.field_150406_ce, EnumDyeColor.CYAN.func_176765_a()), Utils.getBlockMetaId(Blocks.field_180397_cI, BlockPrismarine.EnumType.ROUGH.func_176807_a()), Utils.getBlockMetaId(Blocks.field_180397_cI, BlockPrismarine.EnumType.DARK.func_176807_a()), Utils.getBlockMetaId(Blocks.field_180397_cI, BlockPrismarine.EnumType.BRICKS.func_176807_a()), Utils.getBlockMetaId(Blocks.field_150325_L, EnumDyeColor.LIGHT_BLUE.func_176765_a()), Utils.getBlockMetaId(Blocks.field_150325_L, EnumDyeColor.GRAY.func_176765_a())});
    private long lastWorldJoin = -1L;
    private long lastBoss = -1L;
    private int magmaTick = 1;
    private int timerTick = 1;
    private long lastMinionSound = -1L;
    private long lastBossSpawnPost = -1L;
    private long lastBossDeathPost = -1L;
    private long lastMagmaWavePost = -1L;
    private long lastBlazeWavePost = -1L;
    private Class<?> lastOpenedInventory;
    private long lastClosedInv = -1L;
    private long lastFishingAlert = 0L;
    private long lastBobberEnteredWater = Long.MAX_VALUE;
    private long lastSkyblockServerJoinAttempt = 0L;
    private long lastDeath = 0L;
    private long lastRevive = 0L;
    private long lastMaddoxLevelTime;
    private String lastMaddoxSlayerType;
    private long rainmakerTimeEnd = -1L;
    private boolean oldBobberIsInWater;
    private double oldBobberPosY = 0.0;
    private final Set<UUID> countedEndermen = new HashSet<UUID>();
    private final TreeMap<Long, Set<Vec3>> recentlyKilledZealots = new TreeMap();
    private final Set<IntPair> recentlyLoadedChunks = new HashSet<IntPair>();
    private EnumUtils.MagmaTimerAccuracy magmaAccuracy = EnumUtils.MagmaTimerAccuracy.NO_DATA;
    private int magmaTime = 0;
    private int recentMagmaCubes = 0;
    private int recentBlazes = 0;
    private final TreeMap<Long, Vec3> explosiveBowExplosions = new TreeMap();
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private final ActionBarParser actionBarParser = new ActionBarParser();

    @SubscribeEvent
    public void onWorldJoin(EntityJoinWorldEvent e) {
        Entity entity = e.entity;
        if (entity == Minecraft.func_71410_x().field_71439_g) {
            this.lastWorldJoin = Minecraft.func_71386_F();
            this.lastBoss = -1L;
            this.magmaTick = 1;
            this.timerTick = 1;
            this.main.getInventoryUtils().resetPreviousInventory();
            this.recentlyLoadedChunks.clear();
            this.countedEndermen.clear();
            EndstoneProtectorManager.reset();
            IslandWarpGui.Marker doubleWarpMarker = IslandWarpGui.getDoubleWarpMarker();
            if (doubleWarpMarker != null) {
                IslandWarpGui.setDoubleWarpMarker(null);
                Minecraft.func_71410_x().field_71439_g.func_71165_d("/warp " + doubleWarpMarker.getWarpName());
            }
            NPCUtils.getNpcLocations().clear();
            JerryPresent.getJerryPresents().clear();
            FishParticleManager.clearParticleCache();
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load e) {
        if (this.main.getUtils().isOnSkyblock()) {
            int x = e.getChunk().field_76635_g;
            int z = e.getChunk().field_76647_h;
            IntPair coords = new IntPair(x, z);
            this.recentlyLoadedChunks.add(coords);
            this.main.getScheduler().schedule(Scheduler.CommandType.DELETE_RECENT_CHUNK, 20, x, z);
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGH)
    public void onChatReceive(ClientChatReceivedEvent e) {
        String formattedText = e.message.func_150254_d();
        String unformattedText = e.message.func_150260_c();
        String strippedText = TextUtils.stripColor(formattedText);
        if (formattedText.startsWith("\u00a77Sending to server ")) {
            this.lastSkyblockServerJoinAttempt = Minecraft.func_71386_F();
            DragonTracker.getInstance().reset();
            return;
        }
        if (this.main.getUtils().isOnSkyblock()) {
            if (e.type == 2) {
                if (this.main.isDevMode() && DevUtils.isLoggingActionBarMessages()) {
                    SkyblockAddons.getLogger().info("[ACTION BAR] " + unformattedText);
                }
                String restMessage = this.actionBarParser.parseActionBar(unformattedText);
                if (this.main.isUsingOofModv1() && restMessage.trim().length() == 0) {
                    e.setCanceled(true);
                    return;
                }
                if (this.main.getUtils().isInDungeon()) {
                    if (this.main.getConfigValues().isEnabled(Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY)) {
                        this.main.getDungeonManager().addEssence(restMessage);
                    }
                    if (this.main.getConfigValues().isEnabled(Feature.DUNGEONS_SECRETS_DISPLAY)) {
                        this.main.getDungeonManager().addSecrets(restMessage);
                    }
                }
            } else {
                Matcher matcher;
                if (this.main.getRenderListener().isPredictMana() && unformattedText.startsWith("Used ") && unformattedText.endsWith("Mana)")) {
                    int manaLost = Integer.parseInt(unformattedText.split(Pattern.quote("! ("))[1].split(Pattern.quote(" Mana)"))[0]);
                    this.changeMana(-manaLost);
                } else {
                    matcher = DEATH_MESSAGE_PATTERN.matcher(unformattedText);
                    if (matcher.matches()) {
                        String causeOfDeath = matcher.group("causeOfDeath");
                        if (!causeOfDeath.equals("reconnected")) {
                            String username = matcher.group("username");
                            Object deadPlayer = username.equals("You") ? Minecraft.func_71410_x().field_71439_g : Minecraft.func_71410_x().field_71441_e.func_72924_a(username);
                            MinecraftForge.EVENT_BUS.post((Event)new SkyblockPlayerDeathEvent((EntityPlayer)deadPlayer, username, causeOfDeath));
                        }
                    } else if (this.main.getConfigValues().isEnabled(Feature.SUMMONING_EYE_ALERT) && formattedText.equals("\u00a7r\u00a76\u00a7lRARE DROP! \u00a7r\u00a75Summoning Eye\u00a7r")) {
                        this.main.getUtils().playLoudSound("random.orb", 0.5);
                        this.main.getRenderListener().setTitleFeature(Feature.SUMMONING_EYE_ALERT);
                        this.main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                    } else if (formattedText.equals("\u00a7r\u00a7aA special \u00a7r\u00a75Zealot \u00a7r\u00a7ahas spawned nearby!\u00a7r")) {
                        if (this.main.getConfigValues().isEnabled(Feature.SPECIAL_ZEALOT_ALERT)) {
                            this.main.getUtils().playLoudSound("random.orb", 0.5);
                            this.main.getRenderListener().setTitleFeature(Feature.SUMMONING_EYE_ALERT);
                            this.main.getRenderListener().setTitleFeature(Feature.SPECIAL_ZEALOT_ALERT);
                            this.main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                        }
                        if (this.main.getConfigValues().isEnabled(Feature.ZEALOT_COUNTER)) {
                            e.message = new ChatComponentText(formattedText + (Object)((Object)ColorCode.GRAY) + " (" + this.main.getPersistentValuesManager().getPersistentValues().getKills() + ")");
                        }
                        this.main.getPersistentValuesManager().addEyeResetKills();
                    } else if (this.main.getConfigValues().isEnabled(Feature.LEGENDARY_SEA_CREATURE_WARNING) && SeaCreatureManager.getInstance().getAllSeaCreatureSpawnMessages().contains(unformattedText)) {
                        this.main.getPersistentValuesManager().getPersistentValues().setSeaCreaturesKilled(this.main.getPersistentValuesManager().getPersistentValues().getSeaCreaturesKilled() + 1);
                        if (SeaCreatureManager.getInstance().getLegendarySeaCreatureSpawnMessages().contains(unformattedText)) {
                            this.main.getUtils().playLoudSound("random.orb", 0.5);
                            this.main.getRenderListener().setTitleFeature(Feature.LEGENDARY_SEA_CREATURE_WARNING);
                            this.main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                        }
                    } else if (this.main.getConfigValues().isEnabled(Feature.DISABLE_MAGICAL_SOUP_MESSAGES) && SOUP_RANDOM_MESSAGES.contains(unformattedText)) {
                        e.setCanceled(true);
                    } else if (this.main.getConfigValues().isEnabled(Feature.DISABLE_TELEPORT_PAD_MESSAGES) && (formattedText.startsWith("\u00a7r\u00a7aWarped from ") || formattedText.equals("\u00a7r\u00a7cThis Teleport Pad does not have a destination set!\u00a7r"))) {
                        e.setCanceled(true);
                    } else if (this.main.getConfigValues().isEnabled(Feature.DISABLE_MORT_MESSAGES) && strippedText.startsWith("[NPC] Mort:")) {
                        e.setCanceled(true);
                    } else if (this.main.getConfigValues().isEnabled(Feature.DISABLE_BOSS_MESSAGES) && strippedText.startsWith("[BOSS] ")) {
                        e.setCanceled(true);
                    } else {
                        matcher = SLAYER_COMPLETED_PATTERN.matcher(strippedText);
                        if (matcher.matches()) {
                            SlayerTracker.getInstance().completedSlayer(matcher.group("slayerType"));
                        } else {
                            matcher = SLAYER_COMPLETED_PATTERN_AUTO1.matcher(strippedText);
                            if (matcher.matches()) {
                                this.lastMaddoxLevelTime = System.currentTimeMillis();
                                this.lastMaddoxSlayerType = matcher.group("slayerType");
                            } else if (SLAYER_COMPLETED_PATTERN_AUTO2.matcher(strippedText).matches() && System.currentTimeMillis() - this.lastMaddoxLevelTime < 100L) {
                                SlayerTracker.getInstance().completedSlayer(this.lastMaddoxSlayerType);
                            } else if (strippedText.startsWith("\u262c You placed a Summoning Eye!")) {
                                DragonTracker.getInstance().addEye();
                            } else if (strippedText.equals("You recovered a Summoning Eye!")) {
                                DragonTracker.getInstance().removeEye();
                            } else {
                                FetchurManager fetchur;
                                matcher = DRAGON_SPAWNED_PATTERN.matcher(strippedText);
                                if (matcher.matches()) {
                                    DragonTracker.getInstance().dragonSpawned(matcher.group("dragonType"));
                                } else if (DRAGON_KILLED_PATTERN.matcher(strippedText).matches()) {
                                    DragonTracker.getInstance().dragonKilled();
                                } else if (unformattedText.equals("You laid an egg!")) {
                                    CooldownManager.put("CHICKEN_HEAD");
                                } else if (formattedText.startsWith("\u00a7r\u00a7eYou added a minute of rain!")) {
                                    this.rainmakerTimeEnd = this.rainmakerTimeEnd == -1L || this.rainmakerTimeEnd < System.currentTimeMillis() ? System.currentTimeMillis() + 60000L : (this.rainmakerTimeEnd += 60000L);
                                } else if (this.main.getConfigValues().isEnabled(Feature.SHOW_REFORGE_OVERLAY) && (matcher = ACCESSORY_BAG_REFORGE_PATTERN.matcher(unformattedText)).matches()) {
                                    GuiChestHook.setLastAccessoryBagReforge(matcher.group("reforge"));
                                } else if (formattedText.startsWith("\u00a7e[NPC] Fetchur\u00a7f:") && (unformattedText.contains((fetchur = FetchurManager.getInstance()).getFetchurTaskCompletedPhrase()) || !fetchur.hasFetchedToday() && unformattedText.contains(fetchur.getFetchurAlreadyDidTaskPhrase()))) {
                                    FetchurManager.getInstance().saveLastTimeFetched();
                                }
                            }
                        }
                    }
                }
                if (this.main.getConfigValues().isEnabled(Feature.NO_ARROWS_LEFT_ALERT)) {
                    if (NO_ARROWS_LEFT_PATTERN.matcher(formattedText).matches()) {
                        this.main.getUtils().playLoudSound("random.orb", 0.5);
                        this.main.getRenderListener().setSubtitleFeature(Feature.NO_ARROWS_LEFT_ALERT);
                        this.main.getRenderListener().setArrowsLeft(-1);
                        this.main.getScheduler().schedule(Scheduler.CommandType.RESET_SUBTITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                    } else {
                        matcher = ONLY_HAVE_ARROWS_LEFT_PATTERN.matcher(formattedText);
                        if (matcher.matches()) {
                            int arrowsLeft = Integer.parseInt(matcher.group("arrows"));
                            this.main.getUtils().playLoudSound("random.orb", 0.5);
                            this.main.getRenderListener().setSubtitleFeature(Feature.NO_ARROWS_LEFT_ALERT);
                            this.main.getRenderListener().setArrowsLeft(arrowsLeft);
                            this.main.getScheduler().schedule(Scheduler.CommandType.RESET_SUBTITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                        }
                    }
                }
                if (this.main.getInventoryUtils().getInventoryType() == InventoryType.SALVAGING && this.main.getConfigValues().isEnabled(Feature.SHOW_SALVAGE_ESSENCES_COUNTER)) {
                    this.main.getDungeonManager().addSalvagedEssences(unformattedText);
                }
                if (this.main.getUtils().isInDungeon()) {
                    DungeonMilestone dungeonMilestone;
                    Matcher reviveMessageMatcher = REVIVE_MESSAGE_PATTERN.matcher(unformattedText);
                    if (reviveMessageMatcher.matches()) {
                        List players = Minecraft.func_71410_x().field_71441_e.field_73010_i;
                        String revivedPlayerName = reviveMessageMatcher.group("revivedPlayer");
                        String reviverName = reviveMessageMatcher.group("reviver");
                        EntityPlayer revivedPlayer = null;
                        EntityPlayer revivingPlayer = null;
                        for (EntityPlayer player : players) {
                            if (revivedPlayer != null && revivingPlayer != null) break;
                            if (player.func_70005_c_().equals(revivedPlayerName)) {
                                revivedPlayer = player;
                                this.lastRevive = Minecraft.func_71386_F();
                            }
                            if (reviverName == null || !player.func_70005_c_().equals(reviverName)) continue;
                            revivingPlayer = player;
                        }
                        MinecraftForge.EVENT_BUS.post((Event)new DungeonPlayerReviveEvent(revivedPlayer, revivingPlayer));
                    }
                    if (this.main.getConfigValues().isEnabled(Feature.SHOW_DUNGEON_MILESTONE) && (dungeonMilestone = this.main.getDungeonManager().parseMilestone(formattedText)) != null) {
                        this.main.getDungeonManager().setDungeonMilestone(dungeonMilestone);
                    }
                    if (this.main.getConfigValues().isEnabled(Feature.DUNGEONS_COLLECTED_ESSENCES_DISPLAY)) {
                        this.main.getDungeonManager().addBonusEssence(formattedText);
                    }
                }
                if (ABILITY_CHAT_PATTERN.matcher(formattedText).matches()) {
                    CooldownManager.put(Minecraft.func_71410_x().field_71439_g.func_70694_bm());
                } else {
                    matcher = PROFILE_CHAT_PATTERN.matcher(strippedText);
                    if (matcher.matches()) {
                        String profile = matcher.group(1);
                        if (!profile.equals(this.main.getUtils().getProfileName())) {
                            APIManager.getInstance().onProfileSwitch(profile);
                        }
                        this.main.getUtils().setProfileName(profile);
                    } else {
                        matcher = SWITCH_PROFILE_CHAT_PATTERN.matcher(strippedText);
                        if (matcher.matches()) {
                            String profile = matcher.group(1);
                            if (!profile.equals(this.main.getUtils().getProfileName())) {
                                APIManager.getInstance().onProfileSwitch(profile);
                            }
                            this.main.getUtils().setProfileName(profile);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority=EventPriority.LOW)
    public void onChatReceiveLast(ClientChatReceivedEvent e) {
        if (e.type == 2 && !e.isCanceled()) {
            Iterator itr = this.actionBarParser.getStringsToRemove().iterator();
            String message = e.message.func_150260_c();
            while (itr.hasNext()) {
                message = message.replaceAll(" *" + Pattern.quote((String)itr.next()), "");
            }
            message = message.trim();
            e.message = new ChatComponentText(message);
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent e) {
        Minecraft mc = Minecraft.func_71410_x();
        ItemStack heldItem = e.entityPlayer.func_70694_bm();
        if (this.main.getUtils().isOnSkyblock() && heldItem != null) {
            if (heldItem.func_77973_b() == Items.field_151144_bL) {
                BackpackColor color = ItemUtils.getBackpackColor(heldItem);
                if (color != null) {
                    BackpackInventoryManager.setBackpackColor(color);
                }
            } else if (heldItem.func_77973_b().equals(Items.field_151112_aM) && (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)) {
                String itemId;
                if (this.main.getConfigValues().isEnabled(Feature.FISHING_SOUND_INDICATOR)) {
                    this.oldBobberIsInWater = false;
                    this.lastBobberEnteredWater = Long.MAX_VALUE;
                    this.oldBobberPosY = 0.0;
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_ITEM_COOLDOWNS) && (itemId = ItemUtils.getSkyblockItemID(heldItem)).equals("GRAPPLING_HOOK") && mc.field_71439_g.field_71104_cf != null) {
                    boolean wearingFullBatPerson = InventoryUtils.isWearingFullSet((EntityPlayer)mc.field_71439_g, InventoryUtils.BAT_PERSON_SET_IDS);
                    int cooldownTime = wearingFullBatPerson ? 0 : CooldownManager.getItemCooldown(itemId);
                    CooldownManager.put(itemId, (long)cooldownTime);
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.func_71410_x();
            ++this.timerTick;
            if (mc != null) {
                EntityPlayerSP p;
                ScoreboardManager.tick();
                if (this.actionBarParser.getHealthUpdate() != null && System.currentTimeMillis() - this.actionBarParser.getLastHealthUpdate() > 3000L) {
                    this.actionBarParser.setHealthUpdate(null);
                }
                if ((p = mc.field_71439_g) != null && this.main.getConfigValues().isEnabled(Feature.HEALTH_PREDICTION)) {
                    int newHealth = this.getAttribute(Attribute.HEALTH) > this.getAttribute(Attribute.MAX_HEALTH) ? this.getAttribute(Attribute.HEALTH) : Math.round((float)this.getAttribute(Attribute.MAX_HEALTH) * (p.func_110143_aJ() / p.func_110138_aP()));
                    this.setAttribute(Attribute.HEALTH, newHealth);
                }
                if (this.shouldTriggerFishingIndicator()) {
                    this.main.getUtils().playLoudSound("random.successful_hit", 0.8);
                }
                if (this.timerTick == 20) {
                    if (this.main.getRenderListener().isPredictMana() && this.getAttribute(Attribute.MANA) < this.getAttribute(Attribute.MAX_MANA)) {
                        this.setAttribute(Attribute.MANA, Math.min(this.getAttribute(Attribute.MANA) + this.getAttribute(Attribute.MAX_MANA) / 50, this.getAttribute(Attribute.MAX_MANA)));
                    }
                    this.parseTabList();
                    if (this.main.getConfigValues().isEnabled(Feature.DUNGEON_DEATH_COUNTER) && this.main.getUtils().isInDungeon() && this.main.getDungeonManager().isPlayerListInfoEnabled()) {
                        this.main.getDungeonManager().updateDeathsFromPlayerListInfo();
                    }
                } else if (this.timerTick % 5 == 0) {
                    EntityPlayerSP player = mc.field_71439_g;
                    if (player != null) {
                        EndstoneProtectorManager.checkGolemStatus();
                        TabListParser.parse();
                        this.main.getUtils().parseSidebar();
                        this.main.getInventoryUtils().checkIfInventoryIsFull(mc, player);
                        if (this.main.getUtils().isOnSkyblock()) {
                            SkillType skill;
                            this.main.getInventoryUtils().checkIfWearingSkeletonHelmet(player);
                            this.main.getInventoryUtils().checkIfUsingToxicArrowPoison(player);
                            this.main.getInventoryUtils().checkIfWearingSlayerArmor(player);
                            if (this.main.getConfigValues().isEnabled(Feature.FETCHUR_TODAY)) {
                                FetchurManager.getInstance().recalculateFetchurItem();
                            }
                            if (this.main.getInventoryUtils().getInventoryType() == InventoryType.SKILL_TYPE_MENU && ((skill = SkillType.getFromString(this.main.getInventoryUtils().getInventorySubtype())) == SkillType.MINING || skill == SkillType.FISHING)) {
                                try {
                                    IInventory cc = ((ContainerChest)((GuiChest)Minecraft.func_71410_x().field_71462_r).field_147002_h).func_85151_d();
                                    List<String> lore = ItemUtils.getItemLore(cc.func_70301_a(51));
                                    String milestoneProgress = TextUtils.stripColor(lore.get(lore.size() - 1));
                                    Matcher m = NEXT_TIER_PET_PROGRESS.matcher(milestoneProgress);
                                    int total = -1;
                                    if (m.matches()) {
                                        total = Integer.parseInt(m.group("total").replaceAll(",", ""));
                                    } else {
                                        m = MAXED_TIER_PET_PROGRESS.matcher(milestoneProgress);
                                        if (m.matches()) {
                                            total = Integer.parseInt(m.group("total").replaceAll(",", ""));
                                        }
                                    }
                                    if (total > 0) {
                                        int original;
                                        PersistentValuesManager.PersistentValues persistentValues = this.main.getPersistentValuesManager().getPersistentValues();
                                        if (skill == SkillType.FISHING) {
                                            original = persistentValues.getSeaCreaturesKilled();
                                            this.main.getPersistentValuesManager().getPersistentValues().setSeaCreaturesKilled(total);
                                        } else {
                                            original = persistentValues.getOresMined();
                                            this.main.getPersistentValuesManager().getPersistentValues().setOresMined(total);
                                        }
                                        if (original != total) {
                                            this.main.getPersistentValuesManager().saveValues();
                                        }
                                    }
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                            }
                        }
                        if (mc.field_71462_r == null && this.main.getPlayerListener().didntRecentlyJoinWorld() && (!this.main.getUtils().isInDungeon() || Minecraft.func_71386_F() - this.lastDeath > 1000L && Minecraft.func_71386_F() - this.lastRevive > 1000L)) {
                            this.main.getInventoryUtils().getInventoryDifference(player.field_71071_by.field_70462_a);
                        }
                        if (this.main.getConfigValues().isEnabled(Feature.BAIT_LIST) && BaitManager.getInstance().isHoldingRod()) {
                            BaitManager.getInstance().refreshBaits();
                        }
                    }
                    this.main.getInventoryUtils().cleanUpPickupLog();
                } else if (this.timerTick > 20) {
                    this.timerTick = 1;
                }
            }
        }
    }

    public void parseTabList() {
        IChatComponent tabFooterChatComponent = Minecraft.func_71410_x().field_71456_v.func_175181_h().field_175255_h;
        String tabFooterString = null;
        String strippedTabFooterString = null;
        if (tabFooterChatComponent != null) {
            tabFooterString = tabFooterChatComponent.func_150254_d();
            strippedTabFooterString = TextUtils.stripColor(tabFooterString);
        }
        if (this.main.getUtils().isOnSkyblock() && this.main.getConfigValues().isEnabled(Feature.TAB_EFFECT_TIMERS)) {
            TabEffectManager.getInstance().update(tabFooterString, strippedTabFooterString);
        }
    }

    @SubscribeEvent
    public void onEntityEvent(LivingEvent.LivingUpdateEvent e) {
        if (!this.main.getUtils().isOnSkyblock()) {
            return;
        }
        Entity entity = e.entity;
        if (entity.field_70173_aa < 5) {
            JerryPresent present;
            if (this.main.getConfigValues().isEnabled(Feature.HIDE_OTHER_PLAYERS_PRESENTS) && !JerryPresent.getJerryPresents().containsKey(entity.func_110124_au()) && (present = JerryPresent.getJerryPresent(entity)) != null) {
                JerryPresent.getJerryPresents().put(entity.func_110124_au(), present);
                return;
            }
            if (entity instanceof EntityOtherPlayerMP && this.main.getConfigValues().isEnabled(Feature.HIDE_PLAYERS_NEAR_NPCS)) {
                float health = ((EntityOtherPlayerMP)entity).func_110143_aJ();
                if (NPCUtils.getNpcLocations().containsKey(entity.func_110124_au())) {
                    if (health != 20.0f) {
                        NPCUtils.getNpcLocations().remove(entity.func_110124_au());
                        return;
                    }
                } else if (NPCUtils.isNPC(entity)) {
                    NPCUtils.getNpcLocations().put(entity.func_110124_au(), entity.func_174791_d());
                    return;
                }
            }
        }
        if (entity instanceof EntityArmorStand && entity.func_145818_k_()) {
            PowerOrbManager.getInstance().detectPowerOrb(entity);
            if (this.main.getUtils().getLocation() == Location.ISLAND) {
                long now;
                Matcher matcher;
                int cooldown = this.main.getConfigValues().getWarningSeconds() * 1000 + 5000;
                if (this.main.getConfigValues().isEnabled(Feature.MINION_FULL_WARNING) && entity.func_95999_t().equals("\u00a7cMy storage is full! :(")) {
                    long now2 = System.currentTimeMillis();
                    if (now2 - this.lastMinionSound > (long)cooldown) {
                        this.lastMinionSound = now2;
                        this.main.getUtils().playLoudSound("random.pop", 1.0);
                        this.main.getRenderListener().setSubtitleFeature(Feature.MINION_FULL_WARNING);
                        this.main.getScheduler().schedule(Scheduler.CommandType.RESET_SUBTITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                    }
                } else if (this.main.getConfigValues().isEnabled(Feature.MINION_STOP_WARNING) && (matcher = MINION_CANT_REACH_PATTERN.matcher(entity.func_95999_t())).matches() && (now = System.currentTimeMillis()) - this.lastMinionSound > (long)cooldown) {
                    this.lastMinionSound = now;
                    this.main.getUtils().playLoudSound("random.orb", 1.0);
                    String mobName = matcher.group("mobName");
                    this.main.getRenderListener().setCannotReachMobName(mobName);
                    this.main.getRenderListener().setSubtitleFeature(Feature.MINION_STOP_WARNING);
                    this.main.getScheduler().schedule(Scheduler.CommandType.RESET_SUBTITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (e.target instanceof EntityEnderman && this.isZealot(e.target)) {
            this.countedEndermen.add(e.target.func_110124_au());
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent e) {
        if (e.entity instanceof EntityEnderman) {
            if (this.countedEndermen.remove(e.entity.func_110124_au())) {
                this.main.getPersistentValuesManager().getPersistentValues().setKills(this.main.getPersistentValuesManager().getPersistentValues().getKills() + 1);
                this.main.getPersistentValuesManager().saveValues();
                EndstoneProtectorManager.onKill();
            } else if (this.main.getUtils().isOnSkyblock() && this.main.getConfigValues().isEnabled(Feature.ZEALOT_COUNTER_EXPLOSIVE_BOW_SUPPORT) && this.isZealot(e.entity)) {
                Vec3 deathLocation;
                long now = System.currentTimeMillis();
                if (this.recentlyKilledZealots.containsKey(now)) {
                    this.recentlyKilledZealots.get(now).add(e.entity.func_174791_d());
                } else {
                    this.recentlyKilledZealots.put(now, Sets.newHashSet((Object[])new Vec3[]{e.entity.func_174791_d()}));
                }
                this.explosiveBowExplosions.keySet().removeIf(explosionTime -> now - explosionTime > 150L);
                Map.Entry<Long, Vec3> latestExplosion = this.explosiveBowExplosions.lastEntry();
                if (latestExplosion == null) {
                    return;
                }
                Vec3 explosionLocation = latestExplosion.getValue();
                if (explosionLocation.func_72438_d(deathLocation = e.entity.func_174791_d()) < 4.6) {
                    this.main.getPersistentValuesManager().getPersistentValues().setKills(this.main.getPersistentValuesManager().getPersistentValues().getKills() + 1);
                    this.main.getPersistentValuesManager().saveValues();
                    EndstoneProtectorManager.onKill();
                }
            }
        }
        NPCUtils.getNpcLocations().remove(e.entity.func_110124_au());
    }

    public boolean isZealot(Entity enderman) {
        List stands = Minecraft.func_71410_x().field_71441_e.func_72872_a(EntityArmorStand.class, new AxisAlignedBB(enderman.field_70165_t - 1.0, enderman.field_70163_u, enderman.field_70161_v - 1.0, enderman.field_70165_t + 1.0, enderman.field_70163_u + 5.0, enderman.field_70161_v + 1.0));
        if (stands.isEmpty()) {
            return false;
        }
        EntityArmorStand armorStand = (EntityArmorStand)stands.get(0);
        return armorStand.func_145818_k_() && armorStand.func_95999_t().contains("Zealot");
    }

    @SubscribeEvent
    public void onClientTickMagma(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.func_71410_x();
            if (this.main.getUtils().isOnSkyblock() && this.main.getConfigValues().isEnabled(Feature.MAGMA_WARNING) && this.main.getUtils().getLocation() == Location.BLAZING_FORTRESS && mc != null && mc.field_71441_e != null) {
                if (this.magmaTick % 5 == 0) {
                    boolean foundBoss = false;
                    long currentTime = System.currentTimeMillis();
                    for (Entity entity : mc.field_71441_e.field_72996_f) {
                        EntitySlime magma;
                        if (!(entity instanceof EntityMagmaCube) || (magma = (EntitySlime)entity).func_70809_q() <= 10) continue;
                        foundBoss = true;
                        if (this.lastBoss == -1L || System.currentTimeMillis() - this.lastBoss > 1800000L) {
                            this.lastBoss = System.currentTimeMillis();
                            this.main.getRenderListener().setTitleFeature(Feature.MAGMA_WARNING);
                            this.magmaTick = 16;
                            this.main.getScheduler().schedule(Scheduler.CommandType.RESET_TITLE_FEATURE, this.main.getConfigValues().getWarningSeconds(), new Object[0]);
                        }
                        this.magmaAccuracy = EnumUtils.MagmaTimerAccuracy.SPAWNED;
                        if (currentTime - this.lastBossSpawnPost <= 300000L) continue;
                        this.lastBossSpawnPost = currentTime;
                        this.main.getUtils().sendInventiveTalentPingRequest(EnumUtils.MagmaEvent.BOSS_SPAWN);
                    }
                    if (!foundBoss && this.main.getRenderListener().getTitleFeature() == Feature.MAGMA_WARNING) {
                        this.main.getRenderListener().setTitleFeature(null);
                    }
                    if (!foundBoss && this.magmaAccuracy == EnumUtils.MagmaTimerAccuracy.SPAWNED) {
                        this.magmaAccuracy = EnumUtils.MagmaTimerAccuracy.ABOUT;
                        this.magmaTime = 7200;
                        if (currentTime - this.lastBossDeathPost > 300000L) {
                            this.lastBossDeathPost = currentTime;
                            this.main.getUtils().sendInventiveTalentPingRequest(EnumUtils.MagmaEvent.BOSS_DEATH);
                        }
                    }
                }
                if (this.main.getRenderListener().getTitleFeature() == Feature.MAGMA_WARNING && this.magmaTick % 4 == 0) {
                    this.main.getUtils().playLoudSound("random.orb", 0.5);
                }
            }
            ++this.magmaTick;
            if (this.magmaTick > 20) {
                if ((this.magmaAccuracy == EnumUtils.MagmaTimerAccuracy.EXACTLY || this.magmaAccuracy == EnumUtils.MagmaTimerAccuracy.ABOUT) && this.magmaTime == 0) {
                    this.magmaAccuracy = EnumUtils.MagmaTimerAccuracy.SPAWNED_PREDICTION;
                    this.main.getScheduler().schedule(Scheduler.CommandType.RESET_MAGMA_PREDICTION, 20, new Object[0]);
                }
                --this.magmaTime;
                this.magmaTick = 1;
            }
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(EntityEvent.EnteringChunk e) {
        Entity entity = e.entity;
        if (this.main.getUtils().isOnSkyblock() && this.main.getConfigValues().isEnabled(Feature.ZEALOT_COUNTER_EXPLOSIVE_BOW_SUPPORT) && entity instanceof EntityArrow) {
            AxisAlignedBB playerRadius;
            final EntityArrow arrow = (EntityArrow)entity;
            EntityPlayerSP p = Minecraft.func_71410_x().field_71439_g;
            ItemStack heldItem = p.func_70694_bm();
            if (heldItem != null && "EXPLOSIVE_BOW".equals(ItemUtils.getSkyblockItemID(heldItem)) && (playerRadius = new AxisAlignedBB(p.field_70165_t - 3.0, p.field_70163_u - 3.0, p.field_70161_v - 3.0, p.field_70165_t + 3.0, p.field_70163_u + 3.0, p.field_70161_v + 3.0)).func_72318_a(arrow.func_174791_d())) {
                this.main.getNewScheduler().scheduleRepeatingTask(new SkyblockRunnable(){

                    @Override
                    public void run() {
                        if (arrow.field_70128_L || arrow.field_70132_H || arrow.field_70254_i) {
                            this.cancel();
                            Vec3 explosionLocation = new Vec3(arrow.field_70165_t, arrow.field_70163_u, arrow.field_70161_v);
                            PlayerListener.this.explosiveBowExplosions.put(System.currentTimeMillis(), explosionLocation);
                            PlayerListener.this.recentlyKilledZealots.keySet().removeIf(killedTime -> System.currentTimeMillis() - killedTime > 150L);
                            HashSet filteredRecentlyKilledZealots = new HashSet();
                            for (Map.Entry recentlyKilledZealotEntry : PlayerListener.this.recentlyKilledZealots.entrySet()) {
                                filteredRecentlyKilledZealots.addAll((Collection)recentlyKilledZealotEntry.getValue());
                            }
                            if (filteredRecentlyKilledZealots.isEmpty()) {
                                return;
                            }
                            for (Vec3 zealotDeathLocation : filteredRecentlyKilledZealots) {
                                double distance = explosionLocation.func_72438_d(zealotDeathLocation);
                                if (!(distance < 4.6)) continue;
                                PlayerListener.this.main.getPersistentValuesManager().getPersistentValues().setKills(PlayerListener.this.main.getPersistentValuesManager().getPersistentValues().getKills() + 1);
                                PlayerListener.this.main.getPersistentValuesManager().saveValues();
                                EndstoneProtectorManager.onKill();
                            }
                        }
                    }
                }, 0, 1);
            }
        }
        if (this.main.getUtils().getLocation() == Location.BLAZING_FORTRESS && MAGMA_BOSS_SPAWN_AREA.func_72318_a(new Vec3(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v))) {
            long currentTime = System.currentTimeMillis();
            if (e.entity instanceof EntityMagmaCube) {
                if (!this.recentlyLoadedChunks.contains(new IntPair(e.newChunkX, e.newChunkZ)) && entity.field_70173_aa == 0) {
                    ++this.recentMagmaCubes;
                    this.main.getScheduler().schedule(Scheduler.CommandType.SUBTRACT_MAGMA_COUNT, 4, new Object[0]);
                    if (this.recentMagmaCubes >= 17) {
                        this.magmaTime = 600;
                        this.magmaAccuracy = EnumUtils.MagmaTimerAccuracy.EXACTLY;
                        if (currentTime - this.lastMagmaWavePost > 300000L) {
                            this.lastMagmaWavePost = currentTime;
                            this.main.getUtils().sendInventiveTalentPingRequest(EnumUtils.MagmaEvent.MAGMA_WAVE);
                        }
                    }
                }
            } else if (e.entity instanceof EntityBlaze && !this.recentlyLoadedChunks.contains(new IntPair(e.newChunkX, e.newChunkZ)) && entity.field_70173_aa == 0) {
                ++this.recentBlazes;
                this.main.getScheduler().schedule(Scheduler.CommandType.SUBTRACT_BLAZE_COUNT, 4, new Object[0]);
                if (this.recentBlazes >= 10) {
                    this.magmaTime = 1200;
                    this.magmaAccuracy = EnumUtils.MagmaTimerAccuracy.EXACTLY;
                    if (currentTime - this.lastBlazeWavePost > 300000L) {
                        this.lastBlazeWavePost = currentTime;
                        this.main.getUtils().sendInventiveTalentPingRequest(EnumUtils.MagmaEvent.BLAZE_WAVE);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent e) {
        if (this.main.getUtils().isOnSkyblock() && this.main.getConfigValues().isEnabled(Feature.DISABLE_ENDERMAN_TELEPORTATION_EFFECT)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(priority=EventPriority.NORMAL)
    public void onItemTooltipFirst(ItemTooltipEvent e) {
        ItemStack hoveredItem = e.itemStack;
        if (e.toolTip != null && this.main.getUtils().isOnSkyblock()) {
            String itemId;
            int insertAt = e.toolTip.size();
            --insertAt;
            if (e.showAdvancedItemTooltips) {
                insertAt -= 2;
                if (e.itemStack.func_77951_h()) {
                    --insertAt;
                }
            }
            insertAt = Math.max(0, insertAt);
            NBTTagCompound extraAttributes = ItemUtils.getExtraAttributes(hoveredItem);
            if (extraAttributes != null) {
                ColorCode colorCode;
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_ITEM_ANVIL_USES) && extraAttributes.func_150297_b("anvil_uses", 3)) {
                    int anvilUses = extraAttributes.func_74762_e("anvil_uses");
                    if (extraAttributes.func_150297_b("hot_potato_count", 3)) {
                        anvilUses -= extraAttributes.func_74762_e("hot_potato_count");
                    }
                    if (anvilUses > 0) {
                        e.toolTip.add(insertAt++, Message.MESSAGE_ANVIL_USES.getMessage(String.valueOf(anvilUses)));
                    }
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_BROKEN_FRAGMENTS) && hoveredItem.func_82833_r().contains("Dragon Fragment") && extraAttributes.func_74764_b("bossId") && extraAttributes.func_74764_b("spawnedFor")) {
                    e.toolTip.add(insertAt++, "\u00a7c\u00a7lBROKEN FRAGMENT");
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_BASE_STAT_BOOST_PERCENTAGE) && extraAttributes.func_150297_b("baseStatBoostPercentage", 3)) {
                    int baseStatBoost = extraAttributes.func_74762_e("baseStatBoostPercentage");
                    colorCode = this.main.getConfigValues().getRestrictedColor(Feature.SHOW_BASE_STAT_BOOST_PERCENTAGE);
                    if (this.main.getConfigValues().isEnabled(Feature.BASE_STAT_BOOST_COLOR_BY_RARITY)) {
                        int rarityIndex = baseStatBoost / 10;
                        if (rarityIndex < 0) {
                            rarityIndex = 0;
                        }
                        if (rarityIndex >= ItemRarity.values().length) {
                            rarityIndex = ItemRarity.values().length - 1;
                        }
                        colorCode = ItemRarity.values()[rarityIndex].getColorCode();
                    }
                    e.toolTip.add(insertAt++, "\u00a77Base Stat Boost: " + (Object)((Object)colorCode) + "+" + baseStatBoost + "%");
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_STACKING_ENCHANT_PROGRESS)) {
                    insertAt = EnchantManager.insertStackingEnchantProgress(e.toolTip, extraAttributes, insertAt);
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_SWORD_KILLS) && extraAttributes.func_150297_b("sword_kills", 3)) {
                    ColorCode colorCode2 = this.main.getConfigValues().getRestrictedColor(Feature.SHOW_SWORD_KILLS);
                    e.toolTip.add(insertAt++, "\u00a77Sword Kills: " + (Object)((Object)colorCode2) + extraAttributes.func_74762_e("sword_kills"));
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_ITEM_DUNGEON_FLOOR) && extraAttributes.func_150297_b("item_tier", 3)) {
                    int floor = extraAttributes.func_74762_e("item_tier");
                    colorCode = this.main.getConfigValues().getRestrictedColor(Feature.SHOW_ITEM_DUNGEON_FLOOR);
                    e.toolTip.add(insertAt++, "\u00a77Obtained on Floor: " + (Object)((Object)colorCode) + (floor == 0 ? "Entrance" : Integer.valueOf(floor)));
                }
                if (this.main.getConfigValues().isEnabled(Feature.SHOW_RARITY_UPGRADED) && extraAttributes.func_150297_b("rarity_upgrades", 3)) {
                    e.toolTip.add(insertAt, (Object)((Object)this.main.getConfigValues().getRestrictedColor(Feature.SHOW_RARITY_UPGRADED)) + "\u00a7lRARITY UPGRADED");
                }
            }
            if (this.main.isDevMode() && e.showAdvancedItemTooltips && (itemId = ItemUtils.getSkyblockItemID(e.itemStack)) != null) {
                insertAt = e.toolTip.size() - 1;
                e.toolTip.add(insertAt, EnumChatFormatting.DARK_GRAY + "skyblock:" + itemId);
            }
        }
    }

    @SubscribeEvent(priority=EventPriority.LOW)
    public void onItemTooltipLast(ItemTooltipEvent e) {
        ItemStack hoveredItem = e.itemStack;
        if (e.toolTip != null && this.main.getUtils().isOnSkyblock()) {
            if (this.main.getConfigValues().isEnabled(Feature.ENCHANTMENT_LORE_PARSING)) {
                EnchantManager.parseEnchants(e.toolTip, hoveredItem);
            }
            if (this.main.getConfigValues().isEnabled(Feature.REPLACE_ROMAN_NUMERALS_WITH_NUMBERS)) {
                for (int i = 0; i < e.toolTip.size(); ++i) {
                    e.toolTip.set(i, RomanNumeralParser.replaceNumeralsWithIntegers((String)e.toolTip.get(i)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (e.gui == null && GuiChest.class.equals(this.lastOpenedInventory)) {
            this.lastClosedInv = System.currentTimeMillis();
            this.lastOpenedInventory = null;
        }
        if (e.gui != null) {
            this.lastOpenedInventory = e.gui.getClass();
            if (e.gui instanceof GuiChest) {
                Minecraft mc = Minecraft.func_71410_x();
                IInventory chestInventory = ((GuiChest)e.gui).field_147015_w;
                if (chestInventory.func_145818_k_() && chestInventory.func_145748_c_().func_150260_c().contains("Backpack")) {
                    if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
                        mc.field_71439_g.func_85030_a("mob.horse.armor", 0.5f, 1.0f);
                    } else {
                        mc.field_71439_g.func_85030_a("mob.horse.leather", 0.5f, 1.0f);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (this.main.getOpenSettingsKey().func_151468_f()) {
            this.main.getUtils().setFadingIn(true);
            this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.MAIN, 1, EnumUtils.GuiTab.MAIN);
        } else if (this.main.getOpenEditLocationsKey().func_151468_f()) {
            this.main.getUtils().setFadingIn(false);
            this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.EDIT_LOCATIONS, 0, null);
        } else if (this.main.isDevMode() && this.main.getDeveloperCopyNBTKey().isPressed()) {
            DevUtils.copyData();
        }
        if (this.main.getConfigValues().isEnabled(Feature.DUNGEONS_MAP_DISPLAY) && this.main.getUtils().isInDungeon()) {
            if (Keyboard.isKeyDown((int)12) && Keyboard.getEventKeyState()) {
                float zoomScaleFactor = MathUtils.denormalizeSliderValue(this.main.getConfigValues().getMapZoom().getValue().floatValue(), 0.5f, 5.0f, 0.1f);
                this.main.getConfigValues().getMapZoom().setValue(this.main.getUtils().normalizeValueNoStep(zoomScaleFactor - 0.5f, 0.5f, 5.0f));
            } else if (Keyboard.isKeyDown((int)13) && Keyboard.getEventKeyState()) {
                float zoomScaleFactor = MathUtils.denormalizeSliderValue(this.main.getConfigValues().getMapZoom().getValue().floatValue(), 0.5f, 5.0f, 0.1f);
                this.main.getConfigValues().getMapZoom().setValue(this.main.getUtils().normalizeValueNoStep(zoomScaleFactor + 0.5f, 0.5f, 5.0f));
            }
        }
    }

    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent event) {
        if (!this.main.getUtils().isOnSkyblock()) {
            return;
        }
        if (this.main.getConfigValues().isEnabled(Feature.STOP_RAT_SOUNDS) && event.sound instanceof PositionedSoundRecord && event.category == SoundCategory.ANIMALS) {
            PositionedSoundRecord eventSound = (PositionedSoundRecord)event.sound;
            for (PositionedSoundRecord sound : RAT_SOUNDS) {
                if (!eventSound.func_147650_b().equals((Object)sound.func_147650_b()) || eventSound.func_147655_f() != sound.func_147655_f() || eventSound.func_147653_e() != sound.func_147653_e()) continue;
                System.out.println(eventSound.func_147650_b());
                if (!this.main.getConfigValues().isDisabled(Feature.STOP_ONLY_RAT_SQUEAK) && !eventSound.func_147650_b().toString().endsWith("mob.bat.idle")) continue;
                event.result = null;
            }
        }
        if (this.main.getConfigValues().isEnabled(Feature.STOP_BONZO_STAFF_SOUNDS) && BONZO_STAFF_SOUNDS.contains(event.name)) {
            event.result = null;
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(SkyblockPlayerDeathEvent e) {
        EntityPlayerSP thisPlayer = Minecraft.func_71410_x().field_71439_g;
        if (this.main.getConfigValues().isEnabled(Feature.PREVENT_MOVEMENT_ON_DEATH) && e.entityPlayer == thisPlayer) {
            KeyBinding.func_74506_a();
        }
        if (this.main.getConfigValues().isEnabled(Feature.ITEM_PICKUP_LOG) && e.entityPlayer == thisPlayer && this.main.getUtils().isInDungeon()) {
            this.lastDeath = Minecraft.func_71386_F();
            this.main.getInventoryUtils().resetPreviousInventory();
        }
        if (this.main.getConfigValues().isEnabled(Feature.DUNGEON_DEATH_COUNTER) && this.main.getUtils().isInDungeon()) {
            DungeonPlayer dungeonPlayer = this.main.getDungeonManager().getDungeonPlayerByName(e.username);
            if (dungeonPlayer != null) {
                if (e.cause.contains("disconnected") && dungeonPlayer.isGhost()) {
                    return;
                }
                this.main.getDungeonManager().addDeath();
            } else if (e.entity == thisPlayer) {
                if (e.cause.contains("disconnected") && thisPlayer.field_71075_bZ.field_75101_c) {
                    return;
                }
                this.main.getDungeonManager().addDeath();
            } else {
                SkyblockAddons.getLogger().warn("Could not record death for " + e.username + ". This dungeon player isn't in the registry.");
            }
        }
    }

    @SubscribeEvent
    public void onDungeonPlayerRevive(DungeonPlayerReviveEvent e) {
        if (e.revivedPlayer == Minecraft.func_71410_x().field_71439_g) {
            this.lastRevive = Minecraft.func_71386_F();
        }
        if (this.main.getConfigValues().isEnabled(Feature.ITEM_PICKUP_LOG)) {
            this.main.getInventoryUtils().resetPreviousInventory();
        }
    }

    @SubscribeEvent
    public void onBlockBreak(SkyblockBlockBreakEvent e) {
        String itemId;
        IBlockState blockState = Minecraft.func_71410_x().field_71441_e.func_180495_p(e.blockPos);
        if (ORES.contains(Block.func_176210_f((IBlockState)blockState))) {
            boolean shouldIncrement = true;
            if (this.main.getUtils().getLocation() == Location.ISLAND && blockState.func_177230_c() == Blocks.field_150484_ah) {
                shouldIncrement = false;
            }
            if (shouldIncrement) {
                this.main.getPersistentValuesManager().getPersistentValues().setOresMined(this.main.getPersistentValuesManager().getPersistentValues().getOresMined() + 1);
            }
        }
        if (this.main.getConfigValues().isEnabled(Feature.SHOW_ITEM_COOLDOWNS) && (itemId = ItemUtils.getSkyblockItemID(Minecraft.func_71410_x().field_71439_g.func_70694_bm())) != null) {
            Block block = blockState.func_177230_c();
            if ((itemId.equals("JUNGLE_AXE") || itemId.equals("TREECAPITATOR_AXE")) && (block.equals(Blocks.field_150364_r) || block.equals(Blocks.field_150363_s))) {
                float multiplier = this.main.getConfigValues().isEnabled(Feature.LEG_MONKEY_LEVEL_100) ? 0.6f : 1.0f;
                long cooldownTime = (long)((float)CooldownManager.getItemCooldown(itemId) * multiplier);
                CooldownManager.put(itemId, Math.max(cooldownTime -= this.main.getConfigValues().isEnabled(Feature.COOLDOWN_PREDICTION) ? e.timeToBreak - 50L : 0L, 400L));
            }
        }
    }

    public boolean aboutToJoinSkyblockServer() {
        return Minecraft.func_71386_F() - this.lastSkyblockServerJoinAttempt < 6000L;
    }

    public boolean didntRecentlyJoinWorld() {
        return Minecraft.func_71386_F() - this.lastWorldJoin > 3000L;
    }

    public int getMaxTickers() {
        return this.actionBarParser.getMaxTickers();
    }

    public int getTickers() {
        return this.actionBarParser.getTickers();
    }

    public void updateLastSecondHealth() {
        int health = this.getAttribute(Attribute.HEALTH);
        if (this.main.getConfigValues().isEnabled(Feature.HEALTH_UPDATES) && this.actionBarParser.getLastSecondHealth() != health) {
            this.actionBarParser.setHealthUpdate(health - this.actionBarParser.getLastSecondHealth());
            this.actionBarParser.setLastHealthUpdate(System.currentTimeMillis());
        }
        this.actionBarParser.setLastSecondHealth(health);
    }

    public boolean shouldResetMouse() {
        return System.currentTimeMillis() - this.lastClosedInv > 100L;
    }

    Integer getHealthUpdate() {
        return this.actionBarParser.getHealthUpdate();
    }

    private void changeMana(int change) {
        this.setAttribute(Attribute.MANA, this.getAttribute(Attribute.MANA) + change);
    }

    private int getAttribute(Attribute attribute) {
        return this.main.getUtils().getAttributes().get((Object)attribute).getValue();
    }

    private void setAttribute(Attribute attribute, int value) {
        this.main.getUtils().getAttributes().get((Object)attribute).setValue(value);
    }

    private boolean shouldTriggerFishingIndicator() {
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71439_g != null && mc.field_71439_g.field_71104_cf != null && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b().equals(Items.field_151112_aM) && this.main.getConfigValues().isEnabled(Feature.FISHING_SOUND_INDICATOR)) {
            EntityFishHook bobber = mc.field_71439_g.field_71104_cf;
            long currentTime = System.currentTimeMillis();
            if (bobber.func_70090_H() && !this.oldBobberIsInWater) {
                this.lastBobberEnteredWater = currentTime;
            }
            this.oldBobberIsInWater = bobber.func_70090_H();
            if (bobber.func_70090_H() && Math.abs(bobber.field_70159_w) < 0.01 && Math.abs(bobber.field_70179_y) < 0.01 && currentTime - this.lastFishingAlert > 1000L && currentTime - this.lastBobberEnteredWater > 1500L) {
                double movement = bobber.field_70163_u - this.oldBobberPosY;
                this.oldBobberPosY = bobber.field_70163_u;
                if (movement < -0.04) {
                    this.lastFishingAlert = currentTime;
                    return true;
                }
            }
        }
        return false;
    }

    public ActionBarParser getActionBarParser() {
        return this.actionBarParser;
    }

    public long getRainmakerTimeEnd() {
        return this.rainmakerTimeEnd;
    }

    public Set<UUID> getCountedEndermen() {
        return this.countedEndermen;
    }

    public TreeMap<Long, Set<Vec3>> getRecentlyKilledZealots() {
        return this.recentlyKilledZealots;
    }

    public Set<IntPair> getRecentlyLoadedChunks() {
        return this.recentlyLoadedChunks;
    }

    public EnumUtils.MagmaTimerAccuracy getMagmaAccuracy() {
        return this.magmaAccuracy;
    }

    public void setMagmaAccuracy(EnumUtils.MagmaTimerAccuracy magmaAccuracy) {
        this.magmaAccuracy = magmaAccuracy;
    }

    public int getMagmaTime() {
        return this.magmaTime;
    }

    public void setMagmaTime(int magmaTime) {
        this.magmaTime = magmaTime;
    }

    public int getRecentMagmaCubes() {
        return this.recentMagmaCubes;
    }

    public void setRecentMagmaCubes(int recentMagmaCubes) {
        this.recentMagmaCubes = recentMagmaCubes;
    }

    public int getRecentBlazes() {
        return this.recentBlazes;
    }

    public void setRecentBlazes(int recentBlazes) {
        this.recentBlazes = recentBlazes;
    }

    public TreeMap<Long, Vec3> getExplosiveBowExplosions() {
        return this.explosiveBowExplosions;
    }
}

