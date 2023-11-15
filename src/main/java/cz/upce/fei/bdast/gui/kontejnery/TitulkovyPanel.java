package cz.upce.fei.bdast.gui.kontejnery;

import javafx.scene.control.TitledPane;
import javafx.scene.control.ChoiceBox;

/**
 * Třída představující titulkový panel {@link TitledPane}, jenž může být rozložený nebo skládaný
 */
public class TitulkovyPanel extends TitledPane {

    /**
     * Členská konstanta reprezentující výchozí šířku výběrového pole {@link ChoiceBox}
     * pro tento komponent
     *
     * <p> Je přístupná pouze v rámci stejného balíčku (package) nebo pro podtřídy (dědičnost)
     */
    protected static final double PREFEROVANA_SIRKA_POLE = 100.0;

// <editor-fold defaultstate="collapsed" desc="Konstanty pro nastavení TitledPane">
    private static final boolean JE_ROZLOZEN = true;
    private static final boolean JE_SKLADAN = false;
    private static final boolean JE_ANIMOVANY = true;
// </editor-fold>

    /**
     * Konstruktor inicializující titulkový panel s určitým nastavením
     */
    public TitulkovyPanel() {
        this.setExpanded(JE_ROZLOZEN);
        this.setCollapsible(JE_SKLADAN);
        this.setAnimated(JE_ANIMOVANY);
    }
}
