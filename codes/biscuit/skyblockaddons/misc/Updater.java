/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.event.ClickEvent
 *  net.minecraft.event.ClickEvent$Action
 *  net.minecraft.event.HoverEvent
 *  net.minecraft.event.HoverEvent$Action
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraftforge.common.ForgeVersion$Status
 *  net.minecraftforge.fml.common.versioning.ComparableVersion
 *  org.apache.logging.log4j.Logger
 */
package codes.biscuit.skyblockaddons.misc;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.core.UpdateInfo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.versioning.ComparableVersion;
import org.apache.logging.log4j.Logger;

public class Updater {
    private static final Pattern VERSION_PATTERN = Pattern.compile("(?<major>[0-9])\\.(?<minor>[0-9])\\.(?<patch>[0-9]).*");
    private final SkyblockAddons main = SkyblockAddons.getInstance();
    private final Logger LOGGER = SkyblockAddons.getLogger();
    private ComparableVersion target = null;
    private String messageToRender;
    private String downloadLink;
    private String changelogLink;
    private String showcaseLink;
    private boolean hasUpdate = false;
    private boolean isPatch = false;
    private boolean sentUpdateMessage = false;

    public boolean hasSentUpdateMessage() {
        return this.sentUpdateMessage;
    }

    public boolean hasUpdate() {
        return this.hasUpdate;
    }

    public void checkForUpdate() {
        this.LOGGER.info("Checking to see if an update is available...");
        UpdateInfo updateInfo = this.main.getOnlineData().getUpdateInfo();
        this.sentUpdateMessage = false;
        this.main.getRenderListener().setUpdateMessageDisplayed(false);
        if (updateInfo == null) {
            this.LOGGER.error("Update check failed: Update info is null!");
            return;
        }
        ComparableVersion latestRelease = null;
        ComparableVersion latestBeta = null;
        ComparableVersion current = new ComparableVersion(SkyblockAddons.VERSION);
        boolean isCurrentBeta = this.isBetaVersion(current);
        boolean latestReleaseExists = updateInfo.getLatestRelease() != null && !updateInfo.getLatestRelease().equals("");
        boolean latestBetaExists = updateInfo.getLatestBeta() != null && !updateInfo.getLatestBeta().equals("");
        int releaseDiff = 0;
        int betaDiff = 0;
        if (latestReleaseExists) {
            latestRelease = new ComparableVersion(updateInfo.getLatestRelease());
            releaseDiff = latestRelease.compareTo(current);
        } else {
            if (!isCurrentBeta) {
                this.LOGGER.error("Update check failed: Current version is a release version and key `latestRelease` is null or empty.");
                return;
            }
            this.LOGGER.warn("Key `latestRelease` is null or empty, skipping!");
        }
        if (isCurrentBeta) {
            if (latestBetaExists) {
                latestBeta = new ComparableVersion(updateInfo.getLatestBeta());
                betaDiff = latestBeta.compareTo(current);
            } else {
                if (latestRelease == null) {
                    this.LOGGER.error("Update check failed: Keys `latestRelease` and `latestBeta` are null or empty.");
                    return;
                }
                this.LOGGER.warn("Key `latestBeta` is null or empty, skipping!");
            }
        }
        ForgeVersion.Status status = null;
        if (!isCurrentBeta) {
            if (releaseDiff == 0) {
                status = ForgeVersion.Status.UP_TO_DATE;
            } else if (releaseDiff < 0) {
                status = ForgeVersion.Status.AHEAD;
            } else {
                status = ForgeVersion.Status.OUTDATED;
                this.target = latestRelease;
            }
        } else {
            String currentVersionString = current.toString();
            if (latestReleaseExists) {
                ComparableVersion currentWithoutPrerelease = new ComparableVersion(currentVersionString.substring(0, currentVersionString.indexOf(45)));
                if (releaseDiff > 0 || latestRelease.compareTo(currentWithoutPrerelease) == 0) {
                    status = ForgeVersion.Status.OUTDATED;
                    this.target = latestRelease;
                } else if (!latestBetaExists && releaseDiff < 0) {
                    status = ForgeVersion.Status.AHEAD;
                } else if (releaseDiff == 0) {
                    this.LOGGER.warn("The current beta version (" + currentVersionString + ") matches the latest release version. There is probably something wrong with the online data.");
                    status = ForgeVersion.Status.UP_TO_DATE;
                }
            }
            if (status == null) {
                if (betaDiff == 0) {
                    status = ForgeVersion.Status.UP_TO_DATE;
                } else if (betaDiff < 0) {
                    status = ForgeVersion.Status.AHEAD;
                } else {
                    status = ForgeVersion.Status.BETA_OUTDATED;
                    this.target = latestBeta;
                }
            }
        }
        if (status == ForgeVersion.Status.OUTDATED || status == ForgeVersion.Status.BETA_OUTDATED) {
            this.hasUpdate = true;
            String currentVersion = current.toString();
            String targetVersion = this.target.toString();
            this.LOGGER.info("Found an update: " + targetVersion);
            if (status == ForgeVersion.Status.OUTDATED) {
                targetVersion = updateInfo.getLatestRelease();
                this.downloadLink = updateInfo.getReleaseDownload();
                this.changelogLink = updateInfo.getReleaseChangelog();
                this.showcaseLink = updateInfo.getReleaseShowcase();
            } else {
                targetVersion = updateInfo.getLatestBeta();
                this.downloadLink = updateInfo.getBetaDownload();
                this.changelogLink = updateInfo.getBetaChangelog();
                this.showcaseLink = updateInfo.getBetaShowcase();
            }
            try {
                Matcher currentMatcher = VERSION_PATTERN.matcher(currentVersion);
                Matcher targetMatcher = VERSION_PATTERN.matcher(targetVersion);
                this.isPatch = currentMatcher.matches() && targetMatcher.matches() && currentMatcher.group("major").equals(targetMatcher.group("major")) && currentMatcher.group("minor").equals(targetMatcher.group("minor")) && !isCurrentBeta;
            }
            catch (Exception ex) {
                SkyblockAddons.getLogger().warn("Couldn't parse update version numbers... This shouldn't affect too much.");
                SkyblockAddons.getLogger().catching((Throwable)ex);
            }
            this.messageToRender = this.isPatch ? Translations.getMessage("messages.updateChecker.notificationBox.patchAvailable", targetVersion) : (status == ForgeVersion.Status.BETA_OUTDATED ? Translations.getMessage("messages.updateChecker.notificationBox.betaAvailable", targetVersion) : Translations.getMessage("messages.updateChecker.notificationBox.majorAvailable", targetVersion));
        } else if (status == ForgeVersion.Status.AHEAD) {
            this.LOGGER.warn("The current version is newer than the latest version. Please tell an SBA developer to update the online data.");
        } else {
            this.LOGGER.info("Up to date!");
        }
    }

    public void sendUpdateMessage() {
        if (this.sentUpdateMessage) {
            return;
        }
        String targetVersion = this.target.toString();
        this.main.getUtils().sendMessage("\u00a77\u00a7m----------------\u00a77[ \u00a7b\u00a7lSkyblockAddons \u00a77]\u00a77\u00a7m----------------", false);
        ChatComponentText newUpdate = new ChatComponentText("\u00a7b" + Translations.getMessage("messages.updateChecker.newUpdateAvailable", targetVersion) + "\n");
        ChatComponentText viewChangelog = new ChatComponentText("\u00a7b" + Translations.getMessage("messages.updateChecker.wantToViewPatchNotes", new Object[0]) + "\n");
        ChatComponentText joinDiscord = new ChatComponentText("\u00a7b" + Translations.getMessage("messages.updateChecker.joinDiscord", new Object[0]) + "\n");
        newUpdate.func_150257_a((IChatComponent)viewChangelog).func_150257_a((IChatComponent)joinDiscord);
        this.main.getUtils().sendMessage(newUpdate, false);
        ChatComponentText showcaseButton = null;
        if (this.showcaseLink != null && !this.showcaseLink.equals("")) {
            showcaseButton = new ChatComponentText("\u00a7b\u00a7l[" + Translations.getMessage("messages.updateChecker.watchShowcase", targetVersion) + "]");
            showcaseButton.func_150255_a(showcaseButton.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, this.showcaseLink)).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText("\u00a77" + Translations.getMessage("messages.clickToOpenLink", new Object[0])))));
            showcaseButton.func_150257_a((IChatComponent)new ChatComponentText(" "));
        }
        ChatComponentText downloadButton = new ChatComponentText("\u00a7b\u00a7l[" + Translations.getMessage("messages.updateChecker.downloadButton", targetVersion) + "]");
        if (this.downloadLink != null && !this.downloadLink.equals("")) {
            downloadButton.func_150255_a(downloadButton.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, this.downloadLink)).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText("\u00a77" + Translations.getMessage("messages.clickToOpenLink", new Object[0])))));
        } else {
            downloadButton.func_150255_a(downloadButton.func_150256_b().func_150225_c(Boolean.valueOf(true)).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText("\u00a77" + Translations.getMessage("messages.updateChecker.noDownloadAvailable", new Object[0])))));
        }
        downloadButton.func_150257_a((IChatComponent)new ChatComponentText(" "));
        if (showcaseButton != null) {
            showcaseButton.func_150257_a((IChatComponent)downloadButton);
        }
        ChatComponentText openModsFolderButton = new ChatComponentText("\u00a7e\u00a7l[" + Translations.getMessage("messages.updateChecker.openModFolderButton", new Object[0]) + "]");
        openModsFolderButton.func_150255_a(openModsFolderButton.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sba folder")).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText("\u00a77" + Translations.getMessage("messages.clickToOpenFolder", new Object[0])))));
        downloadButton.func_150257_a((IChatComponent)openModsFolderButton);
        if (this.changelogLink != null && !this.changelogLink.equals("")) {
            ChatComponentText changelogButton = new ChatComponentText(" \u00a79\u00a7l[" + Translations.getMessage("messages.updateChecker.joinDiscordButton", new Object[0]) + "]");
            changelogButton.func_150255_a(changelogButton.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/zWyr3f5GXz")).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText("\u00a77" + Translations.getMessage("messages.clickToOpenLink", new Object[0])))));
            downloadButton.func_150257_a((IChatComponent)changelogButton);
        }
        if (showcaseButton != null) {
            this.main.getUtils().sendMessage(showcaseButton, false);
        } else {
            this.main.getUtils().sendMessage(downloadButton, false);
        }
        this.main.getUtils().sendMessage("\u00a77\u00a7m--------------------------------------------------", false);
        this.sentUpdateMessage = true;
    }

    private boolean isBetaVersion(ComparableVersion version) {
        return version.toString().contains("b");
    }

    public String getMessageToRender() {
        return this.messageToRender;
    }
}

