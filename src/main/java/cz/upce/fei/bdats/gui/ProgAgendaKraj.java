package cz.upce.fei.bdats.gui;

import cz.upce.fei.bdats.gui.koreny.Okno;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *  Třáda představuje uživatelské formulářové rozhraní, které umožňuje obsluhu programu a volat operace agendy
 *  kraje
 *
 * <p> Celá scéna je instací třídy {@link Okno}, jenž kořenovým prvkem celého tohoto uživatelského rozhraní
 */
public final class ProgAgendaKraj extends Application {

    /**
     * Konstanty pro základní nastavení scény
     */
    private static final int SIRKA_SCENY = 880;
    private static final int VYSKA_SCENY = 625;
    private static final boolean MENITELNOST_SCENY = false;
    private static final String TITULEK_SCENY = "Vyhledávání statistických informací o počtu obyvatel jednotlivých obcí";

    /**
     * Kořenová komponenta, která se dál vloží do konstruktoru třídy {@link Scene}
     */
    private final HBox root;
    /**
     * Konstanta představuje samostatné okénko, do něhož se vloží nastavená instance třídy {@link Scene}
     */
    private Stage primarniOkno;

    public ProgAgendaKraj() { root = new Okno(); }

    @Override
    public void start(Stage stage) {
        primarniOkno = stage;
        nastavPrimarniOkno();
    }

    private void nastavPrimarniOkno() {
        var scene = new Scene(root, SIRKA_SCENY, VYSKA_SCENY);
        primarniOkno.setScene(scene);
        primarniOkno.setTitle(TITULEK_SCENY);
        primarniOkno.setResizable(MENITELNOST_SCENY);
        primarniOkno.show();
    }

    public static void main(String[] args) { launch(args); }
}
