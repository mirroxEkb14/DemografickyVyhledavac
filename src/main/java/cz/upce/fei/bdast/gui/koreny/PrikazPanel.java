package cz.upce.fei.bdast.gui.koreny;

import cz.upce.fei.bdast.gui.komponenty.KomponentPrikazy;
import cz.upce.fei.bdast.gui.komponenty.KomponentStrom;
import javafx.scene.layout.VBox;

/**
 * Třída reprezentující panel s příkazy. Obsahuje odkaz na instance komponent stromu
 *
 * <p>
 * Třída je vzorem Singleton
 */
public final class PrikazPanel extends VBox {

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static PrikazPanel instance;

    public static PrikazPanel getInstance() {
        if (instance == null)
            instance = new PrikazPanel();
        return instance;
    }
// </editor-fold>

    /**
     * Privátní konstruktor inicializující panel
     */
    private PrikazPanel() {
        nastavPrikazPanel();
    }

    /**
     * Nastavuje vzhled a chování panelu
     */
    private void nastavPrikazPanel() {
        this.getChildren().addAll(
                KomponentStrom.getInstance(),
                KomponentPrikazy.getInstance());
    }
}
