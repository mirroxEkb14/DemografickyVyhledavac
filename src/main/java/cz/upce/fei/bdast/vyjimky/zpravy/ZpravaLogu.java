package cz.upce.fei.bdast.vyjimky.zpravy;

/**
 * Tento {@link Enum} obsahuje zprávy pro zobrazeni uživateli po špatném nastavení polí
 */
public enum ZpravaLogu {
    LOG_TVORENI("Špatně nastavena pole: nesmí být prázdná a čísla musí být větší než nula");

    private final String zprava;

    ZpravaLogu(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
