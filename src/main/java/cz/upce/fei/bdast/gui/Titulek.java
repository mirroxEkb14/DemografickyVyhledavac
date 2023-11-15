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
    CB_ITERATOR("Iterátor"),
    CB_ITERUJ("Iteruj"),
    CB_VRAT("Vrať"),
    BTN_PRAZDNOST("Prázdnost"),
    BTN_ZRUS("Zruš"),
    TLACITKO_FAJN("Fajn"),
    TLACITKO_ZRUSIT("Zrušit"),
    LABEL_CISLO_KRAJE("Číslo kraje: "),
    LABEL_NAZEV_KRAJE("Název kraje: "),
    LABEL_NAZEV_OBCE("Název obce: "),
    LABEL_PSC("PSČ obce: "),
    LABEL_MUZE("Počet mužů: "),
    LABEL_ZENY("Počet žen: "),
    HLAVICKA_DIALOG_VLOZENI("Vytvoření Nové Obce"),
    HLAVICKA_TITULKOVEHO_PANELU_VLOZENI("Zadávání údajů"),
    HLAVICKA_DIALOGU_NALEZENI("Nalezení Obcí"),
    HLAVICKA_TITULKOVEHO_PANELU_NALEZENI("Zadávání názvu obce"),
    HLAVICKA_DIALOGU_ODEBIRANI("Odebírání Obcí"),
    HLAVICKA_TITULKOVEHO_PANELU_ODEBIRANI("Zadávání názvu obce"),
    KOMPONENT_PRIKAZY("Příkazy"),
    BTN_GENERUJ("Generuj"),
    CB_NACTENI("Načtení"),
    CB_VZOR("vzor.csv"),
    CB_KRAJE("kraje.csv"),
    LABEL_POCET_PRVKU("Počet: "),
    HLAVICKA_DIALOGU_GENERATORU("Generátor Obcí"),
    HLAVICKA_TITULKOVEHO_PANELU_GENERATORU("Zadávání počtu obcí");

    private final String nadpis;

    Titulek(String nadpis) { this.nadpis = nadpis; }

    public String getNadpis() { return nadpis; }
}
