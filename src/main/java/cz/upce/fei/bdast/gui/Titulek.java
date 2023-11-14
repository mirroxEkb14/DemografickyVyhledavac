package cz.upce.fei.bdast.gui;

import cz.upce.fei.bdast.gui.koreny.PrikazPanel;

/**
 * {@link Enum} obsahující titulky pro tlačítka v rámci {@link PrikazPanel}u
 */
public enum Titulek {
    KOMPONENT_STROM("Binární Vyhledávací Strom"),
    BTN_VLOZ("Vlož"),
    BTN_NAJDI("Najdi"),
    BTN_ODEBER("Odeber"),
    BTN_ITERUJ("Iteruj"),
    BTN_PRAZDNOST("Prázdnost"),
    BTN_ZRUS("Zruš"),
    TLACITKO_FAJN("Fajn"),
    TLACITKO_ZRUSIT("Zrušit"),
    LABEL_CISLO("Číslo obce: "),
    LABEL_NAZEV("Název obce: "),
    LABEL_PSC("PSČ obce: "),
    LABEL_MUZE("Počet mužů: "),
    LABEL_ZENY("Počet žen: "),
    HLAVICKA_DIALOG_VLOZENI("Vytvoření Nové Obce"),
    HLAVICKA_TITULKOVEHO_PANELU_VLOZENI("Zadávání údajů");

    private final String nadpis;

    Titulek(String nadpis) { this.nadpis = nadpis; }

    public String getNadpis() { return nadpis; }
}
