package cz.upce.fei.bdats.vyjimky.zpravy;

/**
 * {@link Enum} pro chybové zprávy seznamu obsahující konkrétní chybové zprávy spojené s operacemi na seznamu
 */
public enum ChybovaZpravaSeznamu {
    CHYBA_PRI_VLOZENI("Došlo ke chybě při vložení nové obce"),
    CHYBA_PRI_OBNOVENI("Došlo ke chybě při obnovení seznamu");

    private final String zprava;

    ChybovaZpravaSeznamu(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
