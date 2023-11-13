package cz.upce.fei.bdast.gui.kontejnery;

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
    BTN_ZRUS("Zruš");

    private final String nadpis;

    Titulek(String nadpis) { this.nadpis = nadpis; }

    public String getNadpis() { return nadpis; }
}
