package cz.upce.fei.bdast.gui.dialogy;

import cz.upce.fei.bdast.gui.Titulek;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.jetbrains.annotations.NotNull;

/**
 * Toto rozhraní obsahuje metody pro základní nastavení panelů typu {@link DialogPane}
 */
public interface DialogovyKomponent {

    /**
     * Nastaví výchozí stav pro tlačítka dialogu, předanému v argumentu metody
     *
     * @param dialogovyPanel Instance na {@link DialogPane}, jehož tlačítka {@link ButtonType#OK} a {@link ButtonType#CANCEL}
     *                       budou nastaveny
     *
     * @return Instance na ten samý dialogový panel {@link DialogPane}
     */
    default DialogPane dejTlacitka(@NotNull DialogPane dialogovyPanel) {
        final ButtonType okTlacitko = new ButtonType(
                Titulek.TLACITKO_FAJN.getNadpis(), ButtonBar.ButtonData.OK_DONE);
        final ButtonType cancelTlacitko = new ButtonType(
                Titulek.TLACITKO_ZRUSIT.getNadpis(), ButtonBar.ButtonData.CANCEL_CLOSE);

        dialogovyPanel.getButtonTypes().addAll(okTlacitko, cancelTlacitko);
        return dialogovyPanel;
    }

    /**
     * Ošetřovací metoda zjistí, jaké tlačítko stisknul uživatel
     *
     * @param odpoved Typ tlačítka reprezentuje konkrétní druh: {@link ButtonType#OK}, {@link ButtonType#CANCEL} apod.
     *
     * @return {@code true}, pokud bylo stisknuto {@link ButtonType#OK}, {@code false} v opačném případě
     */
    default boolean jeTlacitkoOk(@NotNull ButtonType odpoved) {
        return odpoved.getButtonData().isDefaultButton();
    }
}
