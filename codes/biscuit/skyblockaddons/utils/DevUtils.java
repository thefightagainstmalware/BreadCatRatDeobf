/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.resources.SimpleReloadableResourceManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagByteArray
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagIntArray
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.scoreboard.Score
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.StringUtils
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.WorldType
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DataUtils;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class DevUtils {
    private static final Minecraft mc = Minecraft.func_71410_x();
    private static final SkyblockAddons main = SkyblockAddons.getInstance();
    private static final Logger logger = SkyblockAddons.getLogger();
    public static final Pattern SIDEBAR_PLAYER_NAME_PATTERN = Pattern.compile("[\ud83d\udd2b\ud83c\udf6b\ud83d\udca3\ud83d\udc7d\ud83d\udd2e\ud83d\udc0d\ud83d\udc7e\ud83c\udf20\ud83c\udf6d\u26bd\ud83c\udfc0\ud83d\udc79\ud83c\udf81\ud83c\udf89\ud83c\udf82]+");
    public static final List<String> ALL_ENTITY_NAMES = EntityList.func_180124_b();
    public static final int DEFAULT_ENTITY_COPY_RADIUS = 3;
    private static final List<Class<? extends Entity>> DEFAULT_ENTITY_NAMES = Collections.singletonList(EntityLivingBase.class);
    private static final boolean DEFAULT_SIDEBAR_FORMATTED = false;
    private static boolean loggingActionBarMessages = false;
    private static CopyMode copyMode = CopyMode.ENTITY;
    private static List<Class<? extends Entity>> entityNames = DEFAULT_ENTITY_NAMES;
    private static int entityCopyRadius = 3;
    private static boolean sidebarFormatted = false;

    public static void setSidebarFormatted(boolean formatted) {
        sidebarFormatted = formatted;
    }

    public static void resetSidebarFormattedToDefault() {
        sidebarFormatted = false;
    }

    public static void copyScoreboardSideBar() {
        DevUtils.copyScoreboardSidebar(sidebarFormatted);
    }

    private static void copyScoreboardSidebar(boolean stripControlCodes) {
        Scoreboard scoreboard = DevUtils.mc.field_71441_e.func_96441_U();
        if (scoreboard == null) {
            main.getUtils().sendErrorMessage("Nothing is being displayed in the sidebar!");
            return;
        }
        ScoreObjective sideBarObjective = scoreboard.func_96539_a(1);
        if (sideBarObjective == null) {
            main.getUtils().sendErrorMessage("Nothing is being displayed in the sidebar!");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String objectiveName = sideBarObjective.func_96678_d();
        List scores = (List)scoreboard.func_96534_i(sideBarObjective);
        if (scores == null || scores.isEmpty()) {
            main.getUtils().sendErrorMessage("No scores were found!");
            return;
        }
        if (stripControlCodes) {
            objectiveName = StringUtils.func_76338_a((String)objectiveName);
        }
        scores = scores.stream().filter(input -> input.func_96653_e() != null && !input.func_96653_e().startsWith("#")).skip(Math.max(scores.size() - 15, 0)).collect(Collectors.toList());
        Collections.reverse(scores);
        stringBuilder.append(objectiveName).append("\n");
        for (Score score : scores) {
            ScorePlayerTeam scoreplayerteam = scoreboard.func_96509_i(score.func_96653_e());
            String playerName = ScorePlayerTeam.func_96667_a((Team)scoreplayerteam, (String)score.func_96653_e());
            playerName = SIDEBAR_PLAYER_NAME_PATTERN.matcher(playerName).replaceAll("");
            if (stripControlCodes) {
                playerName = StringUtils.func_76338_a((String)playerName);
            }
            int points = score.func_96652_c();
            stringBuilder.append(playerName).append("[").append(points).append("]").append("\n");
        }
        DevUtils.copyStringToClipboard(stringBuilder.toString(), (Object)((Object)ColorCode.GREEN) + "Sidebar copied to clipboard!");
    }

    private static void copyEntityData(List<Class<? extends Entity>> includedEntityClasses, int copyRadius) {
        EntityPlayerSP player = DevUtils.mc.field_71439_g;
        LinkedList loadedEntitiesCopy = new LinkedList(DevUtils.mc.field_71441_e.field_72996_f);
        StringBuilder stringBuilder = new StringBuilder();
        ListIterator loadedEntitiesCopyIterator = loadedEntitiesCopy.listIterator();
        while (loadedEntitiesCopyIterator.hasNext()) {
            Entity entity = (Entity)loadedEntitiesCopyIterator.next();
            NBTTagCompound entityData = new NBTTagCompound();
            boolean isPartOfIncludedClasses = false;
            if (entity.func_70032_d((Entity)player) > (float)copyRadius) continue;
            for (Class<? extends Entity> entityClass : includedEntityClasses) {
                if (!entityClass.isAssignableFrom(entity.getClass())) continue;
                isPartOfIncludedClasses = true;
            }
            if (!isPartOfIncludedClasses) continue;
            entity.func_70109_d(entityData);
            if (stringBuilder.length() > 0) {
                stringBuilder.append(System.lineSeparator()).append(System.lineSeparator());
            }
            stringBuilder.append("Class: ").append(entity.getClass().getSimpleName()).append(System.lineSeparator());
            if (entity.func_145818_k_() || EntityPlayer.class.isAssignableFrom(entity.getClass())) {
                stringBuilder.append("Name: ").append(entity.func_70005_c_()).append(System.lineSeparator());
            }
            stringBuilder.append("NBT Data:").append(System.lineSeparator());
            stringBuilder.append(DevUtils.prettyPrintNBT((NBTBase)entityData));
        }
        if (stringBuilder.length() > 0) {
            DevUtils.copyStringToClipboard(stringBuilder.toString(), (Object)((Object)ColorCode.GREEN) + "Entity data was copied to clipboard!");
        } else {
            main.getUtils().sendErrorMessage("No entities matching the given parameters were found.");
        }
    }

    public static void setEntityNamesFromString(String includedEntityNames) {
        List<Class<? extends Entity>> entityClasses = DevUtils.getEntityClassListFromString(includedEntityNames);
        if (entityClasses == null || entityClasses.isEmpty()) {
            main.getUtils().sendErrorMessage("The entity class list is not valid or is empty! Falling back to default.");
            DevUtils.resetEntityNamesToDefault();
        } else {
            entityNames = entityClasses;
        }
    }

    public static void setEntityCopyRadius(int copyRadius) {
        if (copyRadius <= 0) {
            main.getUtils().sendErrorMessage("Radius cannot be negative! Falling back to 3.");
            DevUtils.resetEntityCopyRadiusToDefault();
        } else {
            entityCopyRadius = copyRadius;
        }
    }

    public static void resetEntityNamesToDefault() {
        entityNames = DEFAULT_ENTITY_NAMES;
    }

    public static void resetEntityCopyRadiusToDefault() {
        entityCopyRadius = 3;
    }

    public static void copyEntityData() {
        DevUtils.copyEntityData(entityNames, entityCopyRadius);
    }

    private static List<Class<? extends Entity>> getEntityClassListFromString(String text) {
        String[] entityNamesArray;
        Matcher listMatcher = Pattern.compile("(^[A-Z_]+)(?:,[A-Z_]+)*$", 2).matcher(text);
        if (!listMatcher.matches()) {
            return null;
        }
        ArrayList<Class<? extends Entity>> entityClasses = new ArrayList<Class<? extends Entity>>();
        for (String entityName : entityNamesArray = text.split(",")) {
            if (EntityList.func_180125_b((String)entityName)) {
                int entityId = EntityList.func_180122_a((String)entityName);
                if (entityId != 90 || entityName.equals("Pig")) {
                    entityClasses.add(EntityList.func_90035_a((int)entityId));
                    continue;
                }
                if (!entityName.equals("Player")) continue;
                entityClasses.add(EntityPlayerSP.class);
                entityClasses.add(EntityOtherPlayerMP.class);
                continue;
            }
            if (entityName.equals("PlayerSP")) {
                entityClasses.add(EntityPlayerSP.class);
                continue;
            }
            if (entityName.equals("PlayerMP") | entityName.equals("OtherPlayerMP")) {
                entityClasses.add(EntityOtherPlayerMP.class);
                continue;
            }
            main.getUtils().sendErrorMessage("The entity name \"" + entityName + "\" is invalid. Skipping!");
        }
        return entityClasses;
    }

    public static void copyData() {
        if (copyMode == CopyMode.ENTITY) {
            DevUtils.copyEntityData();
        } else if (copyMode == CopyMode.BLOCK) {
            DevUtils.copyBlockData();
        } else if (copyMode == CopyMode.SIDEBAR) {
            DevUtils.copyScoreboardSideBar();
        } else if (copyMode == CopyMode.TAB_LIST) {
            DevUtils.copyTabListHeaderAndFooter();
        }
    }

    public static void copyNBTTagToClipboard(NBTBase nbtTag, String message) {
        if (nbtTag == null) {
            main.getUtils().sendErrorMessage("This item has no NBT data!");
            return;
        }
        DevUtils.writeToClipboard(DevUtils.prettyPrintNBT(nbtTag), message);
    }

    public static void copyTabListHeaderAndFooter() {
        IChatComponent tabHeader = DevUtils.mc.field_71456_v.func_175181_h().field_175256_i;
        IChatComponent tabFooter = DevUtils.mc.field_71456_v.func_175181_h().field_175255_h;
        if (tabHeader == null && tabFooter == null) {
            main.getUtils().sendErrorMessage("There is no header or footer!");
            return;
        }
        StringBuilder output = new StringBuilder();
        if (tabHeader != null) {
            output.append("Header:").append("\n");
            output.append(tabHeader.func_150254_d());
            output.append("\n\n");
        }
        if (tabHeader != null) {
            output.append("Footer:").append("\n");
            output.append(tabFooter.func_150254_d());
        }
        DevUtils.copyStringToClipboard(output.toString(), (Object)((Object)ColorCode.GREEN) + "Successfully copied the tab list header and footer!");
    }

    public static void copyStringToClipboard(String string, String successMessage) {
        DevUtils.writeToClipboard(string, successMessage);
    }

    public static String getServerBrand() {
        Pattern SERVER_BRAND_PATTERN = Pattern.compile("(.+) <- (?:.+)");
        if (!mc.func_71356_B()) {
            Matcher matcher = SERVER_BRAND_PATTERN.matcher(DevUtils.mc.field_71439_g.func_142021_k());
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        }
        return null;
    }

    public static void copyBlockData() {
        if (DevUtils.mc.field_71476_x == null || DevUtils.mc.field_71476_x.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK || DevUtils.mc.field_71476_x.func_178782_a() == null) {
            main.getUtils().sendErrorMessage("You are not looking at a block!");
            return;
        }
        BlockPos blockPos = DevUtils.mc.field_71476_x.func_178782_a();
        IBlockState blockState = DevUtils.mc.field_71441_e.func_180495_p(blockPos);
        if (DevUtils.mc.field_71441_e.func_175624_G() != WorldType.field_180272_g) {
            blockState = blockState.func_177230_c().func_176221_a(blockState, (IBlockAccess)DevUtils.mc.field_71441_e, blockPos);
        }
        TileEntity tileEntity = DevUtils.mc.field_71441_e.func_175625_s(blockPos);
        NBTTagCompound nbt = new NBTTagCompound();
        if (tileEntity != null) {
            NBTTagCompound nbtTileEntity = new NBTTagCompound();
            tileEntity.func_145841_b(nbtTileEntity);
            nbt.func_74782_a("tileEntity", (NBTBase)nbtTileEntity);
        } else {
            nbt.func_74768_a("x", blockPos.func_177958_n());
            nbt.func_74768_a("y", blockPos.func_177956_o());
            nbt.func_74768_a("z", blockPos.func_177952_p());
        }
        nbt.func_74778_a("type", ((ResourceLocation)Block.field_149771_c.func_177774_c((Object)blockState.func_177230_c())).toString());
        blockState.func_177228_b().forEach((key, value) -> nbt.func_74778_a(key.func_177701_a(), value.toString()));
        DevUtils.writeToClipboard(DevUtils.prettyPrintNBT((NBTBase)nbt), (Object)((Object)ColorCode.GREEN) + "Successfully copied the block data!");
    }

    public static String prettyPrintNBT(NBTBase nbt) {
        String INDENT = "    ";
        byte tagID = nbt.func_74732_a();
        StringBuilder stringBuilder = new StringBuilder();
        if (tagID == 0) {
            stringBuilder.append('}');
        } else if (tagID == 7 || tagID == 11) {
            stringBuilder.append('[');
            if (tagID == 7) {
                NBTTagByteArray nbtByteArray = (NBTTagByteArray)nbt;
                byte[] bytes = nbtByteArray.func_150292_c();
                for (int i = 0; i < bytes.length; ++i) {
                    stringBuilder.append(bytes[i]);
                    if (i >= bytes.length - 1) continue;
                    stringBuilder.append(", ");
                }
            } else {
                NBTTagIntArray nbtIntArray = (NBTTagIntArray)nbt;
                int[] ints = nbtIntArray.func_150302_c();
                for (int i = 0; i < ints.length; ++i) {
                    stringBuilder.append(ints[i]);
                    if (i >= ints.length - 1) continue;
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(']');
        } else if (tagID == 9) {
            NBTTagList nbtTagList = (NBTTagList)nbt;
            stringBuilder.append('[');
            for (int i = 0; i < nbtTagList.func_74745_c(); ++i) {
                NBTBase currentListElement = nbtTagList.func_179238_g(i);
                stringBuilder.append(DevUtils.prettyPrintNBT(currentListElement));
                if (i >= nbtTagList.func_74745_c() - 1) continue;
                stringBuilder.append(", ");
            }
            stringBuilder.append(']');
        } else if (tagID == 10) {
            NBTTagCompound nbtTagCompound = (NBTTagCompound)nbt;
            stringBuilder.append('{');
            if (!nbtTagCompound.func_82582_d()) {
                Iterator iterator = nbtTagCompound.func_150296_c().iterator();
                stringBuilder.append(System.lineSeparator());
                while (iterator.hasNext()) {
                    String key = (String)iterator.next();
                    NBTBase currentCompoundTagElement = nbtTagCompound.func_74781_a(key);
                    stringBuilder.append(key).append(": ").append(DevUtils.prettyPrintNBT(currentCompoundTagElement));
                    if (key.contains("backpack_data") && currentCompoundTagElement instanceof NBTTagByteArray) {
                        try {
                            NBTTagCompound backpackData = CompressedStreamTools.func_74796_a((InputStream)new ByteArrayInputStream(((NBTTagByteArray)currentCompoundTagElement).func_150292_c()));
                            stringBuilder.append(",").append(System.lineSeparator());
                            stringBuilder.append(key).append("(decoded): ").append(DevUtils.prettyPrintNBT((NBTBase)backpackData));
                        }
                        catch (IOException e) {
                            logger.error("Couldn't decompress backpack data into NBT, skipping!", (Throwable)e);
                        }
                    }
                    if (!iterator.hasNext()) continue;
                    stringBuilder.append(",").append(System.lineSeparator());
                }
                String indentedString = stringBuilder.toString().replaceAll(System.lineSeparator(), System.lineSeparator() + "    ");
                stringBuilder = new StringBuilder(indentedString);
            }
            stringBuilder.append(System.lineSeparator()).append('}');
        } else {
            stringBuilder.append(nbt.toString());
        }
        return stringBuilder.toString();
    }

    public static void reloadAll() {
        DevUtils.reloadConfig();
        DevUtils.reloadResources();
    }

    public static void reloadConfig() {
        logger.info("Reloading settings...");
        main.getConfigValues().loadValues();
        logger.info("Settings reloaded");
    }

    public static void reloadResources() {
        logger.info("Reloading resources...");
        DataUtils.readLocalAndFetchOnline();
        main.getPersistentValuesManager().loadValues();
        ((SimpleReloadableResourceManager)mc.func_110442_L()).func_110545_a(FMLClientHandler.instance().getResourcePackFor("skyblockaddons"));
        try {
            Method notifyReloadListenersMethod = SimpleReloadableResourceManager.class.getDeclaredMethod("notifyReloadListeners", new Class[0]);
            notifyReloadListenersMethod.setAccessible(true);
            notifyReloadListenersMethod.invoke(mc.func_110442_L(), new Object[0]);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            logger.error("An error occurred while reloading the mod's resources.", (Throwable)e);
        }
        logger.info("Resources reloaded");
    }

    private static void writeToClipboard(String text, String successMessage) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection output = new StringSelection(text);
        try {
            clipboard.setContents(output, output);
            main.getUtils().sendMessage(successMessage);
        }
        catch (IllegalStateException exception) {
            main.getUtils().sendErrorMessage("Clipboard not available!");
        }
    }

    public static void setCopyMode(CopyMode copyMode) {
        DevUtils.copyMode = copyMode;
        main.getUtils().sendMessage((Object)((Object)ColorCode.YELLOW) + Translations.getMessage("messages.copyModeSet", new Object[]{copyMode, Keyboard.getKeyName((int)main.getDeveloperCopyNBTKey().getKeyCode())}));
    }

    public static boolean isLoggingActionBarMessages() {
        return loggingActionBarMessages;
    }

    public static void setLoggingActionBarMessages(boolean loggingActionBarMessages) {
        DevUtils.loggingActionBarMessages = loggingActionBarMessages;
    }

    static {
        ALL_ENTITY_NAMES.add("PlayerSP");
        ALL_ENTITY_NAMES.add("PlayerMP");
        ALL_ENTITY_NAMES.add("OtherPlayerMP");
    }

    public static enum CopyMode {
        ENTITY,
        BLOCK,
        TAB_LIST,
        SIDEBAR;

    }
}

