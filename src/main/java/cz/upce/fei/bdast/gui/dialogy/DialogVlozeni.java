package cz.upce.fei.bdast.gui.dialogy;

import cz.upce.fei.bdast.gui.Titulek;
import cz.upce.fei.bdast.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdast.gui.kontejnery.TitulkovyPanel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Třída reprezentuje dialogové okno pro vkládání nových údajů o obci
 *
 * <p> Implementuje rozhraní {@link DialogovyKomponent} definující metody pro výchozí nastavení/ošetřování
 * prvků tohoto ialogu
 */
public final class DialogVlozeni extends Dialog<ButtonType>
        implements DialogovyKomponent {

    /**
     * Deklarace prvků tohoto dialogu:
     * <ul>
     * <li> {@link TextField}: pro zadávání údajů o obci
     * <li> {@link Label}: pro lepší orientaci uživatele
     * </ul>
     */
    private final TextField tfCislo, tfNazev, tfPSC, tfPocetMuzu, tfPocetZen;
    private final Label lCislo, lNazev, lPSC, lPocetMuzu, lPocetZen;

    /**
     * Konstruktor inicializuje textová pole a popisky, pak volá metodu pro nastavení dialogu
     */
    public DialogVlozeni() {
        this.tfCislo = new TextField();
        this.tfNazev = new TextField();
        this.tfPSC = new TextField();
        this.tfPocetMuzu = new TextField();
        this.tfPocetZen = new TextField();
        this.lCislo = new Label(Titulek.LABEL_CISLO.getNadpis());
        this.lNazev = new Label(Titulek.LABEL_NAZEV.getNadpis());
        this.lPSC = new Label(Titulek.LABEL_PSC.getNadpis());
        this.lPocetMuzu = new Label(Titulek.LABEL_MUZE.getNadpis());
        this.lPocetZen = new Label(Titulek.LABEL_ZENY.getNadpis());

        nastavDialog();
    }

    /**
     * Nastavuje vzhled dialogového okna
     */
    private void nastavDialog() {
        this.setTitle(Titulek.HLAVICKA_DIALOG_VLOZENI.getNadpis());
        this.setDialogPane(this.dejTlacitka(this.getDialogPane()));
        this.getDialogPane().setContent(dejTitulkovyPanel());
    }

    /**
     * Vytváří a vrací titulkový panel {@link TitledPane} pro dialogové okno
     *
     * @return Nově vytvořený titulkový panel {@link TitulkovyPanel}
     */
    private @NotNull TitledPane dejTitulkovyPanel() {
        final TitledPane titulkovyPanel = new TitulkovyPanel();
        titulkovyPanel.setText(Titulek.HLAVICKA_TITULKOVEHO_PANELU_VLOZENI.getNadpis());
        titulkovyPanel.setContent(dejGridPane());
        return titulkovyPanel;
    }

    /**
     * Vytváří a vrací mřížkový panel {@link GridPane} pro rozmístění komponent tohoto dialogového okna
     *
     * @return Nově vytvořený mřížkový panel {@link MrizkovyPanel}
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(lCislo, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(tfCislo, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(lNazev, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(tfNazev, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(lPSC, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(tfPSC, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(lPocetMuzu, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        gridPane.add(tfPocetMuzu, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        gridPane.add(lPocetZen, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PATY);
        gridPane.add(tfPocetZen, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PATY);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Gettery">
    public TextField getTfCislo() { return tfCislo; }

    public TextField getTfNazev() { return tfNazev; }

    public TextField getTfPSC() { return tfPSC; }

    public TextField getTfPocetMuzu() { return tfPocetMuzu; }

    public TextField getTfPocetZen() { return tfPocetZen; }
// </editor-fold>
}
