package cz.upce.fei.bdast.gui.kontejnery;

import javafx.scene.control.TitledPane;

/**
 * Třída představující titulkový panel {@link TitledPane}, jenž může být rozložený nebo skládaný
 */
public class TitulkovyPanel extends TitledPane {

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
