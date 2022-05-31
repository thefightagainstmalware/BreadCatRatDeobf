/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.util.AxisAlignedBB
 */
package codes.biscuit.skyblockaddons.features;

import codes.biscuit.skyblockaddons.core.EntityAggregate;
import codes.biscuit.skyblockaddons.utils.ItemUtils;
import codes.biscuit.skyblockaddons.utils.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;

public class JerryPresent
extends EntityAggregate {
    private static final Pattern FROM_TO_PATTERN = Pattern.compile("(?:From:|To:) (?:\\[.*?] )?(?<name>\\w{1,16})");
    private static Map<UUID, JerryPresent> jerryPresents = new HashMap<UUID, JerryPresent>();
    private final boolean isForPlayer;
    private final boolean isFromPlayer;
    private final PresentColor presentColor;

    public JerryPresent(UUID present, UUID fromLine, UUID toLine, PresentColor color, boolean isFromPlayer, boolean isForPlayer) {
        super(present, fromLine, toLine);
        this.presentColor = color;
        this.isFromPlayer = isFromPlayer;
        this.isForPlayer = isForPlayer;
    }

    public UUID getThePresent() {
        return this.getEntities().get(0);
    }

    public UUID getLowerDisplay() {
        return this.getEntities().get(1);
    }

    public UUID getUpperDisplay() {
        return this.getEntities().get(2);
    }

    public boolean shouldHide() {
        return !this.isForPlayer && !this.isFromPlayer;
    }

    public static JerryPresent getJerryPresent(Entity targetEntity) {
        JerryPresent present2;
        if (!(targetEntity instanceof EntityArmorStand) || !targetEntity.func_82150_aj()) {
            return null;
        }
        for (JerryPresent present2 : jerryPresents.values()) {
            if (!present2.getEntities().contains(targetEntity.func_110124_au())) continue;
            return present2;
        }
        List stands = Minecraft.func_71410_x().field_71441_e.func_72872_a(EntityArmorStand.class, new AxisAlignedBB(targetEntity.field_70165_t - 0.1, targetEntity.field_70163_u - 2.0, targetEntity.field_70161_v - 0.1, targetEntity.field_70165_t + 0.1, targetEntity.field_70163_u + 2.0, targetEntity.field_70161_v + 0.1));
        present2 = null;
        EntityArmorStand fromLine = null;
        EntityArmorStand toLine = null;
        PresentColor presentColor = null;
        for (EntityArmorStand stand : stands) {
            PresentColor standColor;
            if (!stand.func_82150_aj()) continue;
            if (stand.func_145818_k_()) {
                String name = TextUtils.stripColor(stand.func_95999_t());
                if (name.startsWith("From: ")) {
                    fromLine = stand;
                    continue;
                }
                if (!name.equals("CLICK TO OPEN") && !name.startsWith("To: ")) continue;
                toLine = stand;
                continue;
            }
            String skullID = ItemUtils.getSkullOwnerID(stand.func_71124_b(4));
            if (skullID == null || (standColor = PresentColor.fromSkullID(skullID)) == null) continue;
            present2 = stand;
            presentColor = standColor;
        }
        if (present2 == null || fromLine == null || toLine == null || ((EntityArmorStand)present2).field_70163_u > fromLine.field_70163_u || fromLine.field_70163_u > toLine.field_70163_u) {
            return null;
        }
        Matcher matcher = FROM_TO_PATTERN.matcher(TextUtils.stripColor(fromLine.func_95999_t()));
        if (!matcher.matches()) {
            return null;
        }
        String name = matcher.group("name");
        boolean fromYou = name.equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_());
        boolean forYou = TextUtils.stripColor(toLine.func_95999_t()).equals("CLICK TO OPEN");
        return new JerryPresent(present2.func_110124_au(), fromLine.func_110124_au(), toLine.func_110124_au(), presentColor, fromYou, forYou);
    }

    public static Map<UUID, JerryPresent> getJerryPresents() {
        return jerryPresents;
    }

    public boolean isForPlayer() {
        return this.isForPlayer;
    }

    public boolean isFromPlayer() {
        return this.isFromPlayer;
    }

    public PresentColor getPresentColor() {
        return this.presentColor;
    }

    private static enum PresentColor {
        WHITE("7732c5e4-1800-3b90-a70f-727d2969254b"),
        GREEN("d5eb6a2a-3f10-3d6b-ba6a-4d46bb58a5cb"),
        RED("bc74cb05-2758-3395-93ec-70452a983604");

        private String skullID;

        private PresentColor(String skullID) {
            this.skullID = skullID;
        }

        public static PresentColor fromSkullID(String skullID) {
            for (PresentColor presentColor : PresentColor.values()) {
                if (!presentColor.skullID.equals(skullID)) continue;
                return presentColor;
            }
            return null;
        }
    }
}

