package cz.upce.fei.bdast.gui.komponenty;

import cz.upce.fei.bdast.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdast.gui.kontejnery.Titulek;
import cz.upce.fei.bdast.gui.kontejnery.TitulkovyPanel;
import cz.upce.fei.bdast.gui.kontejnery.Tlacitko;
import cz.upce.fei.bdast.gui.koreny.SeznamPanel;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Třída rozšiřující titulkový panel a obsahující odkazy na tlačítka pro manipulaci se stromem
 *
 * <p> Je návrhovým vzorem Singleton
 */
public final class KomponentStrom extends TitulkovyPanel {

    /**
     * Deklarace tlačítek
     */
    private final Button vlozBtn, najdiBtn, odeberBtn, iterujBtn, prazdnostBtn, zrusBtn;
    /**
     * Deklarace a inicializace instanci na {@link SeznamPanel}
     */
    private final SeznamPanel seznamPanel = SeznamPanel.getInstance();

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static KomponentStrom instance;

    public static KomponentStrom getInstance() {
        if (instance == null)
            instance = new KomponentStrom();
        return instance;
    }
// </editor-fold>

    /**
     * Privátní konstruktor inicializující tlačítka a nastavující události
     */
    private KomponentStrom() {
        this.vlozBtn = new Tlacitko(Titulek.BTN_VLOZ.getNadpis());
        this.vlozBtn.setOnAction(actionEvent -> nastavUdalostVlozeni());

        this.najdiBtn = new Tlacitko(Titulek.BTN_NAJDI.getNadpis());
        this.najdiBtn.setDisable(true);

        this.odeberBtn = new Tlacitko(Titulek.BTN_ODEBER.getNadpis());
        this.odeberBtn.setDisable(true);

        this.iterujBtn = new Tlacitko(Titulek.BTN_ITERUJ.getNadpis());
        this.iterujBtn.setDisable(true);

        this.prazdnostBtn = new Tlacitko(Titulek.BTN_PRAZDNOST.getNadpis());

        this.zrusBtn = new Tlacitko(Titulek.BTN_ZRUS.getNadpis());
        this.zrusBtn.setDisable(true);

        nastavKomponentStrom();
    }

    /**
     * Nastavuje vzhled a chování komponenty stromu představující sadu základích operací nad stromem
     */
    private void nastavKomponentStrom() {
        this.setText(Titulek.KOMPONENT_STROM.getNadpis());
        this.setContent(dejGridPane());
    }

    /**
     * Vrací mřížkový panel s umístěnými tlačítky
     *
     * @return Mřížkový panel {@link GridPane} s tlačítky a výchozím nastavením
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(vlozBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(najdiBtn, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(odeberBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(iterujBtn, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(prazdnostBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(zrusBtn, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        return gridPane;
    }

    /**
     *
     */
    private void nastavUdalostVlozeni() {

    }
}
