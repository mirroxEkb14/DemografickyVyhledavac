package cz.upce.fei.bdats.vyjimky.zpravy;

/**
 * Tento výčtový typ slouží k definici různých chybových zpráv, které mohou být použity v rámci aplikace
 * pro komunikaci s uživatelem. Usnadňuje správu a konzistentní použití chybových zpráv v rámci této
 * aplikace. Místo opakovaného psaní textových chybových zpráv v různých částech kódu, je možné jednoduše
 * použít příslušnou hodnotu z tohoto {@link Enum}u
 */
public enum ChybovaZpravaKraje {
    NULL_KLIC("Chyba u vstupního názvu obce"),
    PRVEK_NENALEZEN("Chyba při hledání obce"),
    DUPLICITNI_KLIC("Chyba při vracení instance stromu");

    final String zprava;

    ChybovaZpravaKraje(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
