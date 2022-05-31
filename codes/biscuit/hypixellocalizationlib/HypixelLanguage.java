/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.hypixellocalizationlib;

public enum HypixelLanguage {
    ENGLISH("en"),
    GERMAN("de"),
    FRENCH("fr"),
    DUTCH("nl"),
    SPANISH("es-ES"),
    ITALIAN("it"),
    CHINESE_SIMPLIFIED("zh-CN"),
    CHINESE_TRADITIONAL("zh-TW"),
    PORTUGUESE_BR("pt-BR"),
    RUSSIAN("ru"),
    KOREAN("ko"),
    POLISH("pl"),
    JAPANESE("ja"),
    PIRATE("en-PT"),
    PORTUGUESE_PT("pt-PT"),
    GREEK("el");

    private String languageId;

    private HypixelLanguage(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageId() {
        return this.languageId;
    }
}

