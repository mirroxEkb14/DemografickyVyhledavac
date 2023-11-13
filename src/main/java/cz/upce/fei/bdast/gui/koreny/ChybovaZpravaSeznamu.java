package cz.upce.fei.bdast.gui.koreny;

/**
 * {@link Enum} pro chybové zprávy seznamu obsahující konkrétní chybové zprávy spojené s operacemi na seznamu
 */
public enum ChybovaZpravaSeznamu {
    CHYBA_PRI_VLOZENI("Došlo ke chybě při vložení nové obce");

    private final String zprava;

    ChybovaZpravaSeznamu(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
