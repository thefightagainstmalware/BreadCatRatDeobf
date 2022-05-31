/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package codes.biscuit.skyblockaddons.core;

import net.minecraft.util.ResourceLocation;

public enum Language {
    ENGLISH("en_US"),
    CHINESE_TRADITIONAL("zh_TW"),
    CHINESE_SIMPLIFIED("zh_CN"),
    HINDI("hi_IN"),
    SPANISH_SPAIN("es_ES"),
    SPANISH_MEXICO("es_MX"),
    FRENCH("fr_FR"),
    ARABIC("ar_SA"),
    RUSSIAN("ru_RU"),
    PORTUGUESE_PORTUGAL("pt_PT"),
    PORTUGUESE_BRAZIL("pt_BR"),
    GERMAN("de_DE"),
    JAPANESE("ja_JP"),
    TURKISH("tr_TR"),
    KOREAN("ko_KR"),
    VIETNAMESE("vi_VN"),
    ITALIAN("it_IT"),
    THAI("th_TH"),
    BULGARIAN("bg_BG"),
    CROATIAN("hr_HR"),
    CZECH("cs_CZ"),
    DANISH("da_DK"),
    DUTCH("nl_NL"),
    ESTONIAN("et_EE"),
    FINNISH("fi_FI"),
    GREEK("el_GR"),
    HEBREW("he_IL"),
    HUNGARIAN("hu_HU"),
    INDONESIAN("id_ID"),
    LITHUANIAN("lt_LT"),
    MALAY("ms_MY"),
    NORWEGIAN("no_NO"),
    PERSIAN("fa_IR"),
    POLISH("pl_PL"),
    ROMANIAN("ro_RO"),
    SERBIAN_LATIN("sr_CS"),
    SLOVENIAN("sl_SI"),
    SWEDISH("sv_SE"),
    UKRAINIAN("uk_UA"),
    PIRATE_ENGLISH("en_PT"),
    LOLCAT("lol_US"),
    BISCUITISH("bc_BC"),
    OWO("ow_Wo");

    private final ResourceLocation resourceLocation;
    private final String path;

    private Language(String path) {
        this.path = path;
        this.resourceLocation = new ResourceLocation("skyblockaddons", "flags/" + path.toLowerCase() + ".png");
    }

    public static Language getFromPath(String languageKey) {
        for (Language language : Language.values()) {
            String path = language.path;
            if (path == null || !path.equalsIgnoreCase(languageKey)) continue;
            return language;
        }
        return null;
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public String getPath() {
        return this.path;
    }
}

