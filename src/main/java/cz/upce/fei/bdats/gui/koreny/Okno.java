package cz.upce.fei.bdats.gui.koreny;

import javafx.scene.layout.HBox;

/**
 * Třída představuje kořenový uzel skládající se ze dvou části:
 * <ol>
 * <li> {@link SeznamPanel}: seznam prvků
 * <li> {@link PrikazPanel}: panel s tlačítky pro správu seznamu
 * </ol>
 */
public final class Okno extends HBox {

    /**
     * Atribut, reprezentující levou část okna (panel se seznam prvků)
     */
    private final SeznamPanel seznam;
    /**
     * Atribut, reprezentující pravou část okna (panel se tlačítky)
     */
    private final PrikazPanel panelPrikazu;

    public Okno() {
        seznam = SeznamPanel.getInstance();
        panelPrikazu = PrikazPanel.getInstance();
        nastavOkno();
    }

    private void nastavOkno() {
        this.getChildren().addAll(seznam, panelPrikazu);
    }
}
