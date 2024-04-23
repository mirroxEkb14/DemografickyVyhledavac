package cz.upce.fei.bdats.gui.alerty;

import javafx.scene.control.Alert;

import java.util.function.Consumer;

/**
 * Třída reprezentuje vlastní alert, rozšířený třídou {@link Alert}, s vlastním výchozím nastavením
 */
public final class ErrorAlert extends Alert {

    /**
     * Konstanty určující titulek a záhlaví chybového okna
     */
    private final String TITULEK = "Chybový Alert";
    private final String ZAHLAVI = "Chyba";

    /**
     * {@link Consumer} přijímající jako parametr zprávu o chybě vytváří nový alert typu {@link AlertType#ERROR}
     * a zobrazuje ho uživateli
     */
    private static final Consumer<String> errorLog = t -> {
        final Alert chyboveOkenko = new ErrorAlert(t);
        chyboveOkenko.showAndWait();
    };

    /**
     * Konstruktor vytváří nový chybový alert {@link AlertType#ERROR} s výchozím nastavením
     *
     * @param zprava Zpráva o chybě
     */
    public ErrorAlert(String zprava) {
        super(AlertType.ERROR);

        this.setTitle(TITULEK);
        this.setHeaderText(ZAHLAVI);
        this.setContentText(zprava);
    }

    /**
     * Veřejná statická metoda umožňující zaznamenat chybu do logu
     *
     * @param zprava Zprávu o chybě
     */
    public static void nahlasErrorLog(String zprava) { errorLog.accept(zprava); }
}
