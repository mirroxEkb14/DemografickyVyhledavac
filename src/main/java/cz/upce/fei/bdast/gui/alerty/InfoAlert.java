package cz.upce.fei.bdast.gui.alerty;

import javafx.scene.control.Alert;

import java.util.function.Consumer;

/**
 * Tato třída vytvoří dialogové okno obsahující zpracovaný prvek, tj. prvek, který byl buď odebrán anebo nalezen
 */
public final class InfoAlert extends Alert {

    /**
     * Funkce přijímá řetězec a zobrazuje dialogové okno {@link InfoAlert} s tímto vstupním řetězcem
     */
    private static final Consumer<String> infoLog = t -> {
        final Alert infoOkenko = new InfoAlert(t);
        infoOkenko.showAndWait();
    };

    /**
     * Konstanty pro výchozí nastavení dialogového okna
     */
    private final String TITULEK = "Informační Alert";
    private final String ZAHLAVI = "Zpracovaný Prvek";

    /**
     * Konstruktor inicializuje informační alert a nastavuje titulek a nadpis tohoto dialogového okna
     *
     * @param zprava Text, který bude zobrazen v těle alertu
     */
    public InfoAlert(String zprava) {
        super(AlertType.INFORMATION);

        this.setTitle(TITULEK);
        this.setHeaderText(ZAHLAVI);
        this.setContentText(zprava);
    }

    /**
     * Volá funkce {@link InfoAlert#infoLog}, čímž zobrazuje informační dialogové okno s předanou zprávou
     *
     * @param zprava Informační zpráva (určitý prvek seznam)
     */
    public static void nahlasInfoLog(String zprava) { infoLog.accept(zprava); }
}
