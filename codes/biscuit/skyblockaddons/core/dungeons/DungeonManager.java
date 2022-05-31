/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package codes.biscuit.skyblockaddons.core.dungeons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.EssenceType;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonClass;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonMilestone;
import codes.biscuit.skyblockaddons.core.dungeons.DungeonPlayer;
import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

public class DungeonManager {
    private static final Pattern PATTERN_MILESTONE = Pattern.compile("^.+?(Healer|Tank|Mage|Archer|Berserk) Milestone .+?([\u2776-\u277f]).+?\u00a7r\u00a7.(\\d+)\u00a7.\u00a77 .+?");
    private static final Pattern PATTERN_COLLECTED_ESSENCES = Pattern.compile("\u00a7.+?(\\d+) (Wither|Spider|Undead|Dragon|Gold|Diamond|Ice) Essence");
    private static final Pattern PATTERN_BONUS_ESSENCE = Pattern.compile("^\u00a7.+?[^You] .+?found a .+?(Wither|Spider|Undead|Dragon|Gold|Diamond|Ice) Essence.+?");
    private static final Pattern PATTERN_SALVAGE_ESSENCES = Pattern.compile("\\+(?<essenceNum>[0-9]+) (?<essenceType>Wither|Spider|Undead|Dragon|Gold|Diamond|Ice) Essence!");
    private static final Pattern PATTERN_SECRETS = Pattern.compile("\u00a77([0-9]+)/([0-9]+) Secrets");
    private static final Pattern PATTERN_PLAYER_LINE = Pattern.compile("^\u00a7.\\[(?<classLetter>.)] (?<name>[\\w\u00a7]+) (?:\u00a7.)*?\u00a7(?<healthColor>.)(?<health>[\\w]+)(?:\u00a7c\u2764)?");
    private static final Pattern PLAYER_LIST_INFO_DEATHS_PATTERN = Pattern.compile("Deaths: \\((?<deaths>\\d+)\\)");
    private String lastServerId;
    private DungeonMilestone dungeonMilestone;
    private final Map<EssenceType, Integer> collectedEssences = new EnumMap<EssenceType, Integer>(EssenceType.class);
    private final Map<String, DungeonPlayer> teammates = new HashMap<String, DungeonPlayer>();
    private int secrets = -1;
    private int maxSecrets;
    private EssenceType lastEssenceType;
    private int lastEssenceAmount;
    private int lastEssenceRepeat;
    private int deaths;
    private int alternateDeaths;
    private int playerListInfoDeaths;

    public void reset() {
        this.dungeonMilestone = null;
        this.collectedEssences.clear();
        this.teammates.clear();
        this.deaths = 0;
        this.alternateDeaths = 0;
        this.playerListInfoDeaths = 0;
    }

    public DungeonPlayer getDungeonPlayerByName(String name) {
        return this.teammates.get(name);
    }

    public DungeonMilestone parseMilestone(String message) {
        Matcher matcher = PATTERN_MILESTONE.matcher(message);
        if (!matcher.lookingAt()) {
            return null;
        }
        DungeonClass dungeonClass = DungeonClass.fromDisplayName(matcher.group(1));
        return new DungeonMilestone(dungeonClass, matcher.group(2), matcher.group(3));
    }

    public void addEssence(String message) {
        Matcher matcher = PATTERN_COLLECTED_ESSENCES.matcher(message);
        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            EssenceType essenceType = EssenceType.fromName(matcher.group(2));
            if (this.lastEssenceType != null && this.lastEssenceAmount == amount && this.lastEssenceType == essenceType) {
                ++this.lastEssenceRepeat;
                if (this.lastEssenceRepeat != 3) continue;
                this.lastEssenceType = null;
                continue;
            }
            this.lastEssenceType = essenceType;
            this.lastEssenceAmount = amount;
            this.lastEssenceRepeat = 1;
            if (essenceType == null) continue;
            this.collectedEssences.put(essenceType, this.collectedEssences.getOrDefault((Object)essenceType, 0) + amount);
        }
    }

    public void addBonusEssence(String message) {
        Matcher matcher = PATTERN_BONUS_ESSENCE.matcher(message);
        if (matcher.matches()) {
            EssenceType essenceType = EssenceType.fromName(matcher.group(1));
            this.collectedEssences.put(essenceType, this.collectedEssences.getOrDefault((Object)essenceType, 0) + 1);
        }
    }

    public String addSecrets(String message) {
        Matcher matcher = PATTERN_SECRETS.matcher(message);
        if (!matcher.find()) {
            this.secrets = -1;
            return message;
        }
        this.secrets = Integer.parseInt(matcher.group(1));
        this.maxSecrets = Integer.parseInt(matcher.group(2));
        SkyblockAddons.getInstance().getPlayerListener().getActionBarParser().getStringsToRemove().add(matcher.group());
        return matcher.replaceAll("");
    }

    public void addSalvagedEssences(String message) {
        Matcher matcher = PATTERN_SALVAGE_ESSENCES.matcher(message);
        while (matcher.find()) {
            EssenceType essenceType = EssenceType.fromName(matcher.group("essenceType"));
            int amount = Integer.parseInt(matcher.group("essenceNum"));
            this.collectedEssences.put(essenceType, this.collectedEssences.getOrDefault((Object)essenceType, 0) + amount);
        }
    }

    public void updateDungeonPlayer(String scoreboardLine) {
        Matcher matcher = PATTERN_PLAYER_LINE.matcher(scoreboardLine);
        if (matcher.matches()) {
            String name = TextUtils.stripColor(matcher.group("name"));
            if (name.equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                return;
            }
            DungeonClass dungeonClass = DungeonClass.fromFirstLetter(matcher.group("classLetter").charAt(0));
            ColorCode healthColor = ColorCode.getByChar(matcher.group("healthColor").charAt(0));
            String healthText = matcher.group("health");
            int health = healthText.equals("DEAD") ? 0 : Integer.parseInt(healthText);
            for (DungeonPlayer player : this.teammates.values()) {
                if (!player.getName().startsWith(name)) continue;
                player.setHealthColor(healthColor);
                if (player.getHealth() > 0 && health == 0) {
                    this.addAlternateDeath();
                }
                player.setHealth(health);
                return;
            }
            for (NetworkPlayerInfo networkPlayerInfo : Minecraft.func_71410_x().func_147114_u().func_175106_d()) {
                String profileName = networkPlayerInfo.func_178845_a().getName();
                if (!profileName.startsWith(name)) continue;
                this.teammates.put(profileName, new DungeonPlayer(profileName, dungeonClass, healthColor, health));
            }
        }
    }

    public int getDeathCount() {
        if (SkyblockAddons.getInstance().getDungeonManager().isPlayerListInfoEnabled()) {
            return this.playerListInfoDeaths;
        }
        return Math.max(this.deaths, this.alternateDeaths);
    }

    public void addDeath() {
        ++this.deaths;
    }

    public void addAlternateDeath() {
        ++this.alternateDeaths;
    }

    public void updateDeathsFromPlayerListInfo() {
        String deathDisplayString;
        Matcher deathDisplayMatcher;
        NetHandlerPlayClient netHandlerPlayClient = Minecraft.func_71410_x().func_147114_u();
        NetworkPlayerInfo deathDisplayPlayerInfo = netHandlerPlayClient.func_175104_a("!B-f");
        if (deathDisplayPlayerInfo != null && (deathDisplayMatcher = PLAYER_LIST_INFO_DEATHS_PATTERN.matcher(deathDisplayString = deathDisplayPlayerInfo.func_178854_k().func_150260_c())).matches()) {
            this.playerListInfoDeaths = Integer.parseInt(deathDisplayMatcher.group("deaths"));
        }
    }

    public boolean isPlayerListInfoEnabled() {
        NetHandlerPlayClient netHandlerPlayClient = Minecraft.func_71410_x().func_147114_u();
        if (netHandlerPlayClient == null) {
            return false;
        }
        List networkPlayerInfoList = netHandlerPlayClient.func_175106_d().stream().limit(10L).collect(Collectors.toList());
        for (NetworkPlayerInfo networkPlayerInfo : networkPlayerInfoList) {
            String username = networkPlayerInfo.func_178845_a().getName();
            if (!username.startsWith("!")) continue;
            return true;
        }
        return false;
    }

    public String getLastServerId() {
        return this.lastServerId;
    }

    public void setLastServerId(String lastServerId) {
        this.lastServerId = lastServerId;
    }

    public DungeonMilestone getDungeonMilestone() {
        return this.dungeonMilestone;
    }

    public void setDungeonMilestone(DungeonMilestone dungeonMilestone) {
        this.dungeonMilestone = dungeonMilestone;
    }

    public Map<EssenceType, Integer> getCollectedEssences() {
        return this.collectedEssences;
    }

    public Map<String, DungeonPlayer> getTeammates() {
        return this.teammates;
    }

    public int getSecrets() {
        return this.secrets;
    }

    public void setSecrets(int secrets) {
        this.secrets = secrets;
    }

    public int getMaxSecrets() {
        return this.maxSecrets;
    }

    public void setMaxSecrets(int maxSecrets) {
        this.maxSecrets = maxSecrets;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getAlternateDeaths() {
        return this.alternateDeaths;
    }

    public int getPlayerListInfoDeaths() {
        return this.playerListInfoDeaths;
    }
}

