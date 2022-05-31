/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.CaseFormat
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.command.NumberInvalidException
 *  net.minecraft.command.WrongUsageException
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.ChatComponentTranslation
 *  net.minecraft.util.EnumChatFormatting
 */
package codes.biscuit.skyblockaddons.commands;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Message;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerBoss;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerDrop;
import codes.biscuit.skyblockaddons.features.slayertracker.SlayerTracker;
import codes.biscuit.skyblockaddons.misc.SkyblockKeyBinding;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.DevUtils;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.Utils;
import com.google.common.base.CaseFormat;
import java.awt.Desktop;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class SkyblockAddonsCommand
extends CommandBase {
    private static final String HEADER = "\u00a77\u00a7m----------------\u00a77[ \u00a7b\u00a7lSkyblockAddons \u00a77]\u00a77\u00a7m----------------";
    private static final String FOOTER = "\u00a77\u00a7m-----------------------------------------------------";
    private static final String[] SUBCOMMANDS = new String[]{"help", "edit", "folder", "set", "slayer", "dev", "brand", "copyBlock", "copyEntity", "copySidebar", "copyTabList", "pd", "reload", "reloadConfig", "reloadRes", "toggleActionBarLogging"};
    private final SkyblockAddons main = SkyblockAddons.getInstance();

    public String func_71517_b() {
        return "skyblockaddons";
    }

    public int func_82362_a() {
        return 0;
    }

    public List<String> func_71514_a() {
        return Collections.singletonList("sba");
    }

    public String func_71518_a(ICommandSender sender) {
        String usage = "\u00a77\u00a7m----------------\u00a77[ \u00a7b\u00a7lSkyblockAddons \u00a77]\u00a77\u00a7m----------------\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.BASE) + " \u00a77- " + Translations.getMessage("commandUsage.sba.base.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.HELP) + " \u00a77- " + Translations.getMessage("commandUsage.sba.help.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.EDIT) + " \u00a77- " + Translations.getMessage("commandUsage.sba.edit.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.SET) + " \u00a77- " + Translations.getMessage("commandUsage.sba.set.zealotCounter.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.FOLDER) + " \u00a77- " + Translations.getMessage("commandUsage.sba.folder.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.SLAYER) + " \u00a77- " + Translations.getMessage("commandUsage.sba.slayer.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.DEV) + " \u00a77- " + Translations.getMessage("commandUsage.sba.dev.help", new Object[0]);
        if (this.main.isDevMode()) {
            usage = usage + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.BRAND) + " \u00a77- " + Translations.getMessage("commandUsage.sba.brand.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.COPY_BLOCK) + " \u00a77- " + Translations.getMessage("commandUsage.sba.copyBlock.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.COPY_ENTITY) + " \u00a77- " + Translations.getMessage("commandUsage.sba.copyEntity.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.COPY_SIDEBAR) + " \u00a77- " + Translations.getMessage("commandUsage.sba.copySidebar.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.COPY_TAB_LIST) + " \u00a77- " + Translations.getMessage("commandUsage.sba.copyTabList.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.PD) + " \u00a77- " + Translations.getMessage("commandUsage.sba.printDeaths.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.RELOAD) + " \u00a77- " + Translations.getMessage("commandUsage.sba.reload.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.RELOAD_CONFIG) + " \u00a77- " + Translations.getMessage("commandUsage.sba.reloadConfig.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.RELOAD_RES) + " \u00a77- " + Translations.getMessage("commandUsage.sba.reloadRes.help", new Object[0]) + "\n\u00a7b\u25cf " + (Object)((Object)CommandSyntax.TOGGLE_ACTION_BAR_LOGGING) + " \u00a77- " + Translations.getMessage("commandUsage.sba.toggleActionBarLogging.help", new Object[0]);
        }
        usage = usage + "\n" + FOOTER;
        return usage;
    }

    public String getSubCommandUsage(String subCommand) {
        for (String validSubCommand : SUBCOMMANDS) {
            if (!subCommand.equalsIgnoreCase(validSubCommand)) continue;
            subCommand = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, validSubCommand);
        }
        return "\u00a77\u00a7m----------------\u00a77[ \u00a7b\u00a7lSkyblockAddons \u00a77]\u00a77\u00a7m----------------\n" + (Object)((Object)SubCommandUsage.valueOf(subCommand)) + "\n" + FOOTER;
    }

    public List<String> func_180525_a(ICommandSender sender, String[] args, BlockPos pos) {
        SlayerBoss slayerBoss;
        if (args.length == 1) {
            return this.getSubCommandTabCompletionOptions(args);
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                return this.getSubCommandTabCompletionOptions(args);
            }
            if (args[0].equalsIgnoreCase("set")) {
                return SkyblockAddonsCommand.func_71530_a((String[])args, (String[])new String[]{"total", "zealots", "eyes"});
            }
            if (args[0].equalsIgnoreCase("slayer")) {
                String[] slayers = new String[SlayerBoss.values().length];
                for (int i = 0; i < SlayerBoss.values().length; ++i) {
                    slayers[i] = SlayerBoss.values()[i].getMobType().toLowerCase(Locale.US);
                }
                return SkyblockAddonsCommand.func_71530_a((String[])args, (String[])slayers);
            }
            if (this.main.isDevMode()) {
                if (args[0].equalsIgnoreCase("copyEntity")) {
                    return SkyblockAddonsCommand.func_175762_a((String[])args, DevUtils.ALL_ENTITY_NAMES);
                }
                if (args[0].equalsIgnoreCase("copySidebar")) {
                    return SkyblockAddonsCommand.func_71530_a((String[])args, (String[])new String[]{"formatted"});
                }
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("slayer") && (slayerBoss = SlayerBoss.getFromMobType(args[1])) != null) {
            String[] drops = new String[slayerBoss.getDrops().size() + 1];
            drops[0] = "kills";
            int i = 1;
            for (SlayerDrop slayerDrop : slayerBoss.getDrops()) {
                drops[i] = slayerDrop.name().toLowerCase(Locale.US);
                ++i;
            }
            return SkyblockAddonsCommand.func_71530_a((String[])args, (String[])drops);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
        try {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    if (args.length == 2) {
                        try {
                            this.main.getUtils().sendMessage(this.getSubCommandUsage(args[1]), false);
                            return;
                        }
                        catch (IllegalArgumentException e) {
                            throw new CommandException(Translations.getMessage("commandUsage.sba.errors.wrongUsage.subCommandNotFound", args[1]), new Object[0]);
                        }
                    }
                    this.main.getUtils().sendMessage(this.func_71518_a(sender), false);
                    return;
                }
                if (args[0].equalsIgnoreCase("edit")) {
                    this.main.getUtils().setFadingIn(false);
                    this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.EDIT_LOCATIONS, 0, null);
                    return;
                }
                if (args[0].equalsIgnoreCase("dev") || args[0].equalsIgnoreCase("nbt")) {
                    SkyblockKeyBinding devModeKeyBinding = this.main.getDeveloperCopyNBTKey();
                    this.main.setDevMode(!this.main.isDevMode());
                    if (this.main.isDevMode()) {
                        devModeKeyBinding.register();
                        this.main.getUtils().sendMessage((Object)((Object)ColorCode.GREEN) + Translations.getMessage("commandUsage.sba.dev.enabled", GameSettings.func_74298_c((int)devModeKeyBinding.getKeyCode())));
                        return;
                    } else {
                        devModeKeyBinding.deRegister();
                        this.main.getUtils().sendMessage((Object)((Object)ColorCode.RED) + Translations.getMessage("commandUsage.sba.dev.disabled", new Object[0]));
                    }
                    return;
                }
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length < 3) {
                        throw new WrongUsageException(Translations.getMessage("commandUsage.sba.errors.wrongUsage.generic", new Object[0]), new Object[0]);
                    }
                    int number = SkyblockAddonsCommand.func_175755_a((String)args[2]);
                    if (args[1].equalsIgnoreCase("totalZealots") || args[1].equalsIgnoreCase("total")) {
                        this.main.getPersistentValuesManager().getPersistentValues().setTotalKills(number);
                        this.main.getPersistentValuesManager().saveValues();
                        this.main.getUtils().sendMessage(Translations.getMessage("commandUsage.sba.set.zealotCounter.totalZealotsSet", Integer.toString(number)));
                        return;
                    } else if (args[1].equalsIgnoreCase("zealots")) {
                        this.main.getPersistentValuesManager().getPersistentValues().setKills(number);
                        this.main.getPersistentValuesManager().saveValues();
                        this.main.getUtils().sendMessage(Translations.getMessage("commandUsage.sba.set.zealotCounter.zealotsSet", Integer.toString(number)));
                        return;
                    } else {
                        if (!args[1].equalsIgnoreCase("eyes")) throw new WrongUsageException(Translations.getMessage("sba.set.zealotCounter.wrongUsage", "'zealots', 'totalZealots/total', 'eyes'"), new Object[0]);
                        this.main.getPersistentValuesManager().getPersistentValues().setSummoningEyeCount(number);
                        this.main.getPersistentValuesManager().saveValues();
                        this.main.getUtils().sendMessage(Translations.getMessage("commandUsage.sba.set.zealotCounter.eyesSet", Integer.toString(number)));
                    }
                    return;
                }
                if (args[0].equalsIgnoreCase("folder")) {
                    try {
                        Desktop.getDesktop().open(this.main.getUtils().getSBAFolder());
                        return;
                    }
                    catch (IOException e) {
                        SkyblockAddons.getLogger().error("An error occurred trying to open the mods folder.", (Throwable)e);
                        throw new CommandException(Translations.getMessage("commandUsage.sba.folder.error", new Object[0]), new Object[]{e.getMessage()});
                    }
                }
                if (args[0].equalsIgnoreCase("warp")) {
                    this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.WARP);
                    return;
                }
                if (args[0].equalsIgnoreCase("slayer")) {
                    if (args.length == 1) {
                        StringBuilder bosses = new StringBuilder();
                        for (int i = 0; i < SlayerBoss.values().length; ++i) {
                            SlayerBoss slayerBoss = SlayerBoss.values()[i];
                            bosses.append("'").append(slayerBoss.getMobType().toLowerCase(Locale.US)).append("'");
                            if (i + 1 >= SlayerBoss.values().length) continue;
                            bosses.append(", ");
                        }
                        throw new WrongUsageException(Translations.getMessage("commandUsage.sba.slayer.bossRequired", bosses.toString()), new Object[0]);
                    }
                    if (args.length == 2) {
                        throw new WrongUsageException(Translations.getMessage("commandUsage.sba.slayer.statRequired", new Object[0]), new Object[0]);
                    }
                    if (args.length == 3) {
                        throw new WrongUsageException(Translations.getMessage("commandUsage.sba.slayer.numberRequired", new Object[0]), new Object[0]);
                    }
                    if (args.length != 4) return;
                    try {
                        SlayerTracker.getInstance().setStatManually(args);
                        return;
                    }
                    catch (NumberFormatException e) {
                        throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{args[3]});
                    }
                    catch (IllegalArgumentException e) {
                        throw new WrongUsageException(e.getMessage(), new Object[0]);
                    }
                }
                if (!this.main.isDevMode()) throw new WrongUsageException(Translations.getMessage("commandUsage.sba.errors.wrongUsage.subCommandNotFound", args[0]), new Object[0]);
                if (args[0].equalsIgnoreCase("brand")) {
                    String serverBrand = DevUtils.getServerBrand();
                    if (serverBrand == null) throw new CommandException(Message.COMMAND_USAGE_SBA_BRAND_NOT_FOUND.getMessage(new String[0]), new Object[0]);
                    this.main.getUtils().sendMessage(Message.COMMAND_USAGE_SBA_BRAND_BRAND_OUTPUT.getMessage(serverBrand));
                    return;
                }
                if (args[0].equalsIgnoreCase("copyBlock")) {
                    DevUtils.setCopyMode(DevUtils.CopyMode.BLOCK);
                    DevUtils.copyData();
                    return;
                }
                if (args[0].equalsIgnoreCase("copyEntity")) {
                    try {
                        if (args.length >= 3) {
                            DevUtils.setEntityNamesFromString(args[1]);
                            DevUtils.setEntityCopyRadius(SkyblockAddonsCommand.func_175755_a((String)args[2]));
                        } else if (args.length >= 2) {
                            DevUtils.setEntityNamesFromString(args[1]);
                            DevUtils.resetEntityCopyRadiusToDefault();
                        } else {
                            DevUtils.resetEntityNamesToDefault();
                            DevUtils.resetEntityCopyRadiusToDefault();
                        }
                        DevUtils.setCopyMode(DevUtils.CopyMode.ENTITY);
                        DevUtils.copyData();
                        return;
                    }
                    catch (IllegalArgumentException e) {
                        throw new WrongUsageException(e.getMessage(), new Object[0]);
                    }
                }
                if (args[0].equalsIgnoreCase("copySidebar")) {
                    try {
                        if (args.length >= 2) {
                            DevUtils.setSidebarFormatted(SkyblockAddonsCommand.func_180527_d((String)args[1]));
                        }
                        DevUtils.setCopyMode(DevUtils.CopyMode.SIDEBAR);
                        DevUtils.copyData();
                        return;
                    }
                    catch (NullPointerException e) {
                        throw new WrongUsageException(Translations.getMessage("commandUsage.sba.errors.wrongUsage.generic", new Object[0]), new Object[0]);
                    }
                }
                if (args[0].equalsIgnoreCase("copyTabList")) {
                    DevUtils.setCopyMode(DevUtils.CopyMode.TAB_LIST);
                    DevUtils.copyData();
                    return;
                } else if (args[0].equalsIgnoreCase("pd")) {
                    this.main.getUtils().sendMessage(EnumChatFormatting.BOLD + "Death Counts: ");
                    this.main.getUtils().sendMessage(EnumChatFormatting.WHITE + "Deaths: " + EnumChatFormatting.GOLD + this.main.getDungeonManager().getDeaths());
                    this.main.getUtils().sendMessage(EnumChatFormatting.WHITE + "Alt Deaths: " + EnumChatFormatting.GOLD + this.main.getDungeonManager().getAlternateDeaths());
                    this.main.getUtils().sendMessage(EnumChatFormatting.WHITE + "Tab Deaths: " + EnumChatFormatting.GOLD + this.main.getDungeonManager().getPlayerListInfoDeaths());
                    return;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    DevUtils.reloadAll();
                    return;
                } else if (args[0].equalsIgnoreCase("reloadConfig")) {
                    DevUtils.reloadConfig();
                    return;
                } else if (args[0].equalsIgnoreCase("reloadRes")) {
                    DevUtils.reloadResources();
                    return;
                } else {
                    if (!args[0].equalsIgnoreCase("toggleActionBarLogging")) throw new WrongUsageException(Translations.getMessage("commandUsage.sba.errors.wrongUsage.subCommandNotFound", args[0]), new Object[0]);
                    DevUtils.setLoggingActionBarMessages(!DevUtils.isLoggingActionBarMessages());
                    if (DevUtils.isLoggingActionBarMessages()) {
                        this.main.getUtils().sendMessage((Object)((Object)ColorCode.GREEN) + Translations.getMessage("commandUsage.sba.toggleActionBarLogging.enabled", new Object[0]));
                        return;
                    } else {
                        this.main.getUtils().sendMessage((Object)((Object)ColorCode.RED) + Translations.getMessage("commandUsage.sba.toggleActionBarLogging.disabled", new Object[0]));
                    }
                }
                return;
            }
            this.main.getUtils().setFadingIn(true);
            this.main.getRenderListener().setGuiToOpen(EnumUtils.GUIType.MAIN, 1, EnumUtils.GuiTab.MAIN);
            return;
        }
        catch (CommandException e) {
            ChatComponentTranslation errorMessage = new ChatComponentTranslation(e.getMessage(), e.func_74844_a());
            errorMessage.func_150256_b().func_150238_a(EnumChatFormatting.RED);
            throw new CommandException(Utils.MESSAGE_PREFIX + errorMessage.func_150254_d(), new Object[0]);
        }
    }

    private List<String> getSubCommandTabCompletionOptions(String[] args) {
        if (this.main.isDevMode()) {
            return SkyblockAddonsCommand.func_71530_a((String[])args, (String[])SUBCOMMANDS);
        }
        return SkyblockAddonsCommand.func_71530_a((String[])args, (String[])Arrays.copyOf(SUBCOMMANDS, 5));
    }

    private static enum SubCommandUsage {
        HELP(CommandSyntax.HELP, "commandUsage.sba.help.help", Collections.singletonList(CommandOption.COMMAND)),
        EDIT(CommandSyntax.EDIT, "commandUsage.sba.edit.help", null),
        SET(CommandSyntax.SET, "commandUsage.sba.set.zealotCounter.detailedHelp.description", Arrays.asList(CommandOption.ZEALOTS, CommandOption.EYES, CommandOption.TOTAL_ZEALOTS)),
        FOLDER(CommandSyntax.FOLDER, "commandUsage.sba.folder.help", null),
        DEV(CommandSyntax.DEV, "commandUsage.sba.dev.detailedHelp.description", null),
        BRAND(CommandSyntax.BRAND, "commandUsage.sba.brand.help", null),
        COPY_ENTITY(CommandSyntax.COPY_ENTITY, "commandUsage.sba.copyEntity.detailedHelp.description", Arrays.asList(CommandOption.ENTITY_NAMES, CommandOption.RADIUS)),
        COPY_SIDEBAR(CommandSyntax.COPY_SIDEBAR, "commandUsage.sba.copySidebar.detailedHelp.description", Collections.singletonList(CommandOption.FORMATTED)),
        COPY_TAB_LIST(CommandSyntax.COPY_TAB_LIST, "commandUsage.sba.copyTabList.detailedHelp.description", null),
        TOGGLE_ACTION_BAR_LOGGING(CommandSyntax.TOGGLE_ACTION_BAR_LOGGING, "commandUsage.sba.toggleActionBarLogging.help", null),
        SLAYER(CommandSyntax.SLAYER, "commandUsage.sba.slayer.detailedHelp.description", Arrays.asList(CommandOption.SLAYER_BOSS, CommandOption.SLAYER_STAT, CommandOption.SLAYER_NUMBER)),
        COPY_BLOCK(CommandSyntax.COPY_BLOCK, "commandUsage.sba.copyBlock.help", null),
        RELOAD(CommandSyntax.RELOAD, "commandUsage.sba.reload.help", null),
        RELOAD_CONFIG(CommandSyntax.RELOAD_CONFIG, "commandUsage.sba.reloadConfig.help", null),
        RELOAD_RES(CommandSyntax.RELOAD_RES, "commandUsage.sba.reloadRes.help", null),
        PD(CommandSyntax.PD, "commandUsage.sba.printDeaths.help", null);

        private final CommandSyntax syntax;
        private final String descriptionTranslationKey;
        private final List<CommandOption> options;

        private SubCommandUsage(CommandSyntax syntax, String descriptionTranslationKey, List<CommandOption> options) {
            this.syntax = syntax;
            this.descriptionTranslationKey = descriptionTranslationKey;
            this.options = options;
        }

        public String toString() {
            StringBuilder usageBuilder = new StringBuilder("Usage: \u00a7b" + (Object)((Object)this.syntax) + "\u00a7r\n\n\u00a7lDescription:\n\u00a77" + Translations.getMessage(this.descriptionTranslationKey, new Object[0]));
            if (this.options != null) {
                ListIterator<CommandOption> optionListIterator = this.options.listIterator();
                usageBuilder.append("\n").append("\n\u00a7lOptions:");
                while (optionListIterator.hasNext()) {
                    usageBuilder.append("\n");
                    usageBuilder.append((Object)optionListIterator.next());
                }
            }
            return usageBuilder.toString();
        }
    }

    private static enum CommandSyntax {
        BASE("/sba"),
        HELP("/sba help [command]"),
        EDIT("/sba edit"),
        SET("/sba set <zealots|eyes|totalZealots \u00a7eor\u00a7b total> <number>"),
        FOLDER("/sba folder"),
        DEV("/sba dev"),
        BRAND("/sba brand"),
        COPY_ENTITY("/sba copyEntity [entityNames] [radius: integer]"),
        COPY_SIDEBAR("/sba copySidebar [formatted: boolean]"),
        COPY_TAB_LIST("/sba copyTabList"),
        TOGGLE_ACTION_BAR_LOGGING("/sba toggleActionBarLogging"),
        SLAYER("/sba slayer <boss> <stat> <number>"),
        COPY_BLOCK("/sba copyBlock"),
        RELOAD("/sba reload"),
        RELOAD_CONFIG("/sba reloadConfig"),
        RELOAD_RES("/sba reloadRes"),
        PD("/sba pd");

        private final String syntax;

        private CommandSyntax(String syntax) {
            this.syntax = syntax;
        }

        public String toString() {
            return this.syntax;
        }

        public String getSyntax() {
            return this.syntax;
        }
    }

    private static enum CommandOption {
        COMMAND("Command", "commandUsage.sba.help.detailedHelp.options.command"),
        ZEALOTS("Zealots", "commandUsage.sba.set.zealotCounter.detailedHelp.options.zealots"),
        EYES("Eyes", "commandUsage.sba.set.zealotCounter.detailedHelp.options.eyes"),
        TOTAL_ZEALOTS("TotalZealots|Total", "commandUsage.sba.set.zealotCounter.detailedHelp.options.totalZealots"),
        FORMATTED("Formatted", "commandUsage.sba.copySidebar.detailedHelp.options.formatted"),
        ENTITY_NAMES("EntityNames", "commandUsage.sba.copyEntity.detailedHelp.options.entityNames"),
        RADIUS("Radius", "commandUsage.sba.copyEntity.detailedHelp.options.radius"),
        SLAYER_BOSS("Boss", "commandUsage.sba.slayer.detailedHelp.options.boss"),
        SLAYER_NUMBER("Number", "commandUsage.sba.slayer.detailedHelp.options.number"),
        SLAYER_STAT("Stat", "commandUsage.sba.slayer.detailedHelp.options.stat");

        private final String name;
        private final String descriptionTranslationKey;

        private CommandOption(String name, String descriptionTranslationKey) {
            this.name = name;
            this.descriptionTranslationKey = descriptionTranslationKey;
        }

        public String toString() {
            return "\u00a7b\u25cf " + this.name + " \u00a77- " + Translations.getMessage(this.descriptionTranslationKey, new Object[0]);
        }

        public String getName() {
            return this.name;
        }
    }
}

