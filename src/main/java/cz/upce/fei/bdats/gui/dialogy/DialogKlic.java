package cz.upce.fei.bdats.gui.dialogy;

import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Tato třída je archetypem pro všechny další dialogy, jejichž obsah je jedním labelem {@link Label} a jedním
 * textovým polem {@link TextField} pro zadávání uživatelem názvu určité obce
 *
 * <p> Tuto třídu dědí, například, {@link DialogNalezeni} a {@link DialogOdebirani}, které pro svoji vnitřní logiku
 * potřebuje pouze jeden atribut obce - název, a proto, aby nedublovat stejný kód, je tady tato třída. Tyto dialogy
 * se líší pouze tím, že mají růžné hlavičky dialogu samotného a titulkového panelu, které jsou předany jako
 * argumenty konstruktoru
 *
 * @see Dialog
 * @see DialogovyKomponent
 */
public class DialogKlic extends Dialog<ButtonType>
        implements DialogovyKomponent {

    /**
     * Deklarace tlačítek a labelů
     */
    private final TextField tfNazevObce;
    private final Label lNazevObce;
    /**
     * Hodnoty nadpisů v rámci dialogu
     */
    private final String hlavickaDialogu;
    private final String hlavickaTitulkovehoPanelu;

    /**
     * Konstruktor inicializuje grafické prvky tohoto dialogu a nastaví výchozí nastavení
     */
    public DialogKlic(String hlavickaDialogu,
                      String hlavickaTitulkovehoPanelu) {
        this.tfNazevObce = new TextField();
        this.lNazevObce = new Label(Titulek.LABEL_NAZEV_OBCE.getNadpis());

        this.hlavickaDialogu = hlavickaDialogu;
        this.hlavickaTitulkovehoPanelu = hlavickaTitulkovehoPanelu;

        nastavDialog();
    }

    /**
     * Nastaví vzhled okénka
     */
    private void nastavDialog() {
        this.setTitle(hlavickaDialogu);
        this.setDialogPane(this.dejTlacitka(this.getDialogPane()));
        this.getDialogPane().setContent(dejTitulkovyPanel());
    }

    /**
     * Vratí titulkový panel {@link TitledPane} s výchozím nastavením
     *
     * @return Nový titulkový panel {@link TitulkovyPanel} pro tento dialog
     */
    private @NotNull TitledPane dejTitulkovyPanel() {
        final TitledPane titulkovyPanel = new TitulkovyPanel();
        titulkovyPanel.setText(hlavickaTitulkovehoPanelu);
        titulkovyPanel.setContent(dejGridPane());
        return titulkovyPanel;
    }

    /**
     * Vratí mřížkový panel {@link GridPane} pro rozmístění komponent v rámci {@link TitulkovyPanel}
     *
     * @return Nový mřížkový panel {@link MrizkovyPanel} pro nastavení titulkového panelu
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(lNazevObce, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(tfNazevObce, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Gettery">
    public TextField getTfNazevObce() { return tfNazevObce; }
// </editor-fold>
}
