package cz.upce.fei.bdast.vyjimky.zpravy;

/**
 * Tento {@link Enum} obsahuje zprávy pro zobrazeni uživateli po špatném nastavení polí
 */
public enum ZpravaLogu {
    LOG_TVORENI_SPATNA_POLE("Špatně nastavena pole: nesmí být prázdná a čísla musí být větší než nula"),
    LOG_TVORENI_DUPLICITNI_KLIC("Název musí být unikátním v rámci stromu"),
    LOG_NALEZENI_NENI_PRVEK("Prvek nebyl nelezen");

    private final String zprava;

    ZpravaLogu(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
