package cz.upce.fei.bdats.gui.dialogy;

import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Třída reprezentuje dialogové okno pro vkládání nových údajů o obci
 *
 * <p> Implementuje rozhraní {@link DialogovyKomponent} definující metody pro výchozí nastavení/ošetřování
 * prvků tohoto dialogu
 *
 * @see Dialog
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
    private final TextField tfCislo, tfNazevKraje, tfNazevObce, tfPSC, tfPocetMuzu, tfPocetZen;
    private final Label lCislo, lNazevKraje, lNazevObce, lPSC, lPocetMuzu, lPocetZen;
    /**
     * Výchozí hodnoty pro textová pole (nápovědy)
     */
    private final String VYCHOZI_NAZEV_KRAJE = "Pardubicky";
    private final String VYCHOZI_CISLO_KRAJE = "10";
    private final String VYCHOZI_INTEGER = "1";

    /**
     * Konstruktor inicializuje textová pole a popisky, pak volá metodu pro nastavení dialogu
     */
    public DialogVlozeni() {
        this.tfCislo = new TextField(VYCHOZI_CISLO_KRAJE);
        this.tfNazevKraje = new TextField(VYCHOZI_NAZEV_KRAJE);
        this.tfNazevObce = new TextField();
        this.tfPSC = new TextField(VYCHOZI_INTEGER);
        this.tfPocetMuzu = new TextField(VYCHOZI_INTEGER);
        this.tfPocetZen = new TextField(VYCHOZI_INTEGER);
        this.lCislo = new Label(Titulek.LABEL_CISLO_KRAJE.getNadpis());
        this.lNazevKraje = new Label(Titulek.LABEL_NAZEV_KRAJE.getNadpis());
        this.lNazevObce = new Label(Titulek.LABEL_NAZEV_OBCE.getNadpis());
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
        gridPane.add(lNazevKraje, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(tfNazevKraje, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(lNazevObce, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(tfNazevObce, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(lPSC, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        gridPane.add(tfPSC, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        gridPane.add(lPocetMuzu, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PATY);
        gridPane.add(tfPocetMuzu, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PATY);
        gridPane.add(lPocetZen, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_SESTY);
        gridPane.add(tfPocetZen, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_SESTY);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Gettery">
    public TextField getTfCislo() { return tfCislo; }

    public TextField getTfNazevKraje() { return tfNazevKraje; }

    public TextField getTfNazevObce() { return tfNazevObce; }

    public TextField getTfPSC() { return tfPSC; }

    public TextField getTfPocetMuzu() { return tfPocetMuzu; }

    public TextField getTfPocetZen() { return tfPocetZen; }
// </editor-fold>
}
