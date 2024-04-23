package cz.upce.fei.bdats.gui.dialogy;

import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Třída reprezentující dialog pro generování prvků
 */
public final class DialogGeneratoru extends Dialog<ButtonType>
        implements DialogovyKomponent {

    /**
     * Deklarace tlačítek a labelů
     */
    private final TextField tfPocet;
    private final Label lPocet;
    /**
     * Defaultní počet prvků pro generování zobrazující se jako nápověda po otevření dialogu
     */
    private final String VYCHOZI_POCET = "10";

    /**
     * Konstruktor inicializuje textové pole {@link TextField} pro zadání počtu prvků a popisek {@link Label}
     * označující toto textové pole
     */
    public DialogGeneratoru() {
        this.tfPocet = new TextField(VYCHOZI_POCET);
        this.lPocet = new Label(Titulek.LABEL_POCET_PRVKU.getNadpis());

        nastavDialog();
    }

    /**
     * Nastavení vzhledu dialogu
     */
    private void nastavDialog() {
        this.setTitle(Titulek.HLAVICKA_DIALOGU_GENERATORU.getNadpis());
        this.setDialogPane(this.dejTlacitka(this.getDialogPane()));
        this.getDialogPane().setContent(dejTitulkovyPanel());
    }

    /**
     * Vytvoření titulkového panelu {@link TitledPane}
     *
     * @return Nově vytvořený titulkový panel {@link TitulkovyPanel}
     */
    private @NotNull TitledPane dejTitulkovyPanel() {
        final TitledPane titulkovyPanel = new TitulkovyPanel();
        titulkovyPanel.setText(Titulek.HLAVICKA_TITULKOVEHO_PANELU_GENERATORU.getNadpis());
        titulkovyPanel.setContent(dejGridPane());
        return titulkovyPanel;
    }

    /**
     * Vytvoření mřížkového panelu {@link GridPane}
     *
     * @return Nově vytvořený mřížkový panel {@link MrizkovyPanel}
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(lPocet, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(tfPocet, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Gettery">
    public TextField getTfNazevObce() { return tfPocet; }
// </editor-fold>
}
