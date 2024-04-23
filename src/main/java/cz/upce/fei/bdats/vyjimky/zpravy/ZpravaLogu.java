package cz.upce.fei.bdats.vyjimky.zpravy;

/**
 * Tento {@link Enum} obsahuje zprávy pro zobrazeni uživateli po špatném nastavení polí
 */
public enum ZpravaLogu {
    LOG_TVORENI_SPATNA_POLE("Špatně nastavena pole: nesmí být prázdná a čísla musí být větší než nula"),
    LOG_TVORENI_DUPLICITNI_KLIC("Název musí být unikátním v rámci stromu"),
    LOG_TVORENI_PRAZDNY_KLIC("Název nesmí být prázdný"),
    LOG_NALEZENI_NENI_PRVEK("Prvek nebyl nelezen"),
    LOG_GENERATOR_SPATNY_POCET("Špatně zvolá hodnota: počet musí být kladné celé číslo"),
    LOG_GENERATOR_OBNOVENI("Chyba při vkládání prvků"),
    LOG_NACTENI_VZORU("Chyba při čtení dat ze vzorového .csv souboru: název obce musí být unikátní"),
    LOG_NACTENI_KRAJE("Chyba při čtení dat ze .csv souboru krajů: název obce musí být unikátní"),
    LOG_NACTENI_ULOZISTE("Chyba při čtení dat ze .csv souboru uložiště: název obce musí být unikátní"),
    LOG_USPESNE_ULOZENI("Data byla uložena do souboru"),
    LOG_CHYBNE_ULOZENI("Chyba: data nebyla uložena");

    private final String zprava;

    ZpravaLogu(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
