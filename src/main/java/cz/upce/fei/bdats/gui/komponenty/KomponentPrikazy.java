package cz.upce.fei.bdats.gui.komponenty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.alerty.ErrorAlert;
import cz.upce.fei.bdats.gui.alerty.InfoAlert;
import cz.upce.fei.bdats.gui.dialogy.DialogGeneratoru;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.Tlacitko;
import cz.upce.fei.bdats.gui.koreny.SeznamPanel;
import cz.upce.fei.bdats.perzistence.IPerzistence;
import cz.upce.fei.bdats.vyjimky.SeznamPanelException;
import cz.upce.fei.bdats.vyjimky.zpravy.ZpravaLogu;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;
// </editor-fold>

/**
 * Tato třída reprezentuje komponent na panelu s příkazy, který obsahuje tlačítka pro generování obcí
 * {@link Titulek#BTN_GENERUJ} a načtení obcí ze souboru {@link Titulek#CB_NACTI}
 *
 * <p> Je Singleton třídou
 */
public class KomponentPrikazy extends TitulkovyPanel {

    /**
     * Deklarace tlačítek
     */
    private final Button generujBtn, ulozBtn;
    private final ChoiceBox<String> nactiCb = new ChoiceBox<>();
    /**
     * Lambdový výraz předefinuje výběrové pole
     */
    final BiConsumer<String, String> tvurceCbNacteni = (t, u) -> {
        this.nactiCb.getItems().clear();
        this.nactiCb.getItems().addAll(
                Titulek.CB_NACTI.getNadpis(), t, u);
        if (IPerzistence.jeSoubor(IPerzistence.CESTA_ULOZISTE))
            this.nactiCb.getItems().add(Titulek.CB_ULOZISTE.getNadpis());
        this.nactiCb.setPrefWidth(PREFEROVANA_SIRKA_POLE);
        this.nactiCb.getSelectionModel().select(Titulek.CB_NACTI.getNadpis());
        this.nactiCb.setOnAction(actionEvent -> nastavUdalostNacteni());
    };
    /**
     * Exponent pro zvětšení šířky tlačítka v případě, když musí zabrat hned několik sloupců na řádku
     */
    private final int SQUARE_EXPONENT = 2;
    /**
     * Instance na {@link SeznamPanel} pro generování nových prvků
     */
    private final SeznamPanel seznamPanel = SeznamPanel.getInstance();

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static KomponentPrikazy instance;

    public static KomponentPrikazy getInstance() {
        if (instance == null)
            instance = new KomponentPrikazy();
        return instance;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Nastavení třídy">
    /**
     * Konstruktor inicizlizuje tlačítka a nastaví tento grafický komponent
     */
    public KomponentPrikazy() {
        this.generujBtn = new Tlacitko(Titulek.BTN_GENERUJ.getNadpis());
        this.generujBtn.setOnAction(actionEvent -> nastavUdalostGeneratoru());

        tvurceCbNacteni.accept(
                Titulek.CB_VZOR.getNadpis(),
                Titulek.CB_KRAJE.getNadpis());

        this.ulozBtn = new Tlacitko(Titulek.BTN_ULOZ.getNadpis());
        this.ulozBtn.setOnAction(actionEvent -> nastavUdalostUlozeni());
        this.ulozBtn.setPrefWidth(Math.pow(Tlacitko.getPrefSirka(), SQUARE_EXPONENT));
        this.ulozBtn.setDisable(true);

        nastavKomponentPrikazy();
    }

    /**
     * Provede výchozí nastavení pro tento grafický komponent
     */
    private void nastavKomponentPrikazy() {
        this.setText(Titulek.KOMPONENT_PRIKAZY.getNadpis());
        this.setContent(dejGridPane());
    }

    /**
     * Nastaví a vratí nový {@link GridPane}
     *
     * @return Instance na nově vytvořený mřížkový panel {@link MrizkovyPanel}
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(generujBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(nactiCb, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(ulozBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        GridPane.setColumnSpan(ulozBtn, MrizkovyPanel.ROZPETI_SLOUPCU);
        return gridPane;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Generátoru/Načtení">
    /**
     * Po stisknutí tlačítka {@link Titulek#BTN_GENERUJ} se provede generace nových obcí, případně
     * přidání něchto prvků jak do binárního stromu, tak i do grafického seznamu
     */
    private void nastavUdalostGeneratoru() {
        final DialogGeneratoru dialog = new DialogGeneratoru();
        final Optional<ButtonType> odpoved = dialog.showAndWait();
        if (odpoved.isPresent() && dialog.jeTlacitkoOk(odpoved.get())) {
            try {
                final int pocet = Integer.parseInt(dialog.getTfNazevObce().getText());
                seznamPanel.obnovSeznam(pocet);
                provedObnoveniTlacitekGeneratorNacteni();
            } catch (NumberFormatException ex) {
                ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_GENERATOR_SPATNY_POCET.getZprava());
            } catch (SeznamPanelException ex) {
                ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_GENERATOR_OBNOVENI.getZprava());
            }
        }
    }

    /**
     * Po stisktuní tlačítka {@link Titulek#CB_NACTI} do stromu se načte (přidá se) data ze souboru s příponou
     * {@code .csv}, pak se tyto nové prvky přidají do grafického seznamu
     */
    private void nastavUdalostNacteni() {
        final String zvolenaPolozka = nactiCb.getSelectionModel().getSelectedItem();
        if (Titulek.CB_VZOR.getNadpis().equalsIgnoreCase(zvolenaPolozka)) {
            if (!seznamPanel.nacti(IPerzistence.CESTA_VZOR))
                ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_NACTENI_VZORU.getZprava());
        } else if (Titulek.CB_KRAJE.getNadpis().equalsIgnoreCase(zvolenaPolozka)) {
            if (!seznamPanel.nacti(IPerzistence.CESTA_KRAJE))
                ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_NACTENI_KRAJE.getZprava());
        } else if (Titulek.CB_ULOZISTE.getNadpis().equalsIgnoreCase(zvolenaPolozka)) {
            if (!seznamPanel.nacti(IPerzistence.CESTA_ULOZISTE))
                ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_NACTENI_ULOZISTE.getZprava());
        }
        this.nactiCb.getSelectionModel().select(Titulek.CB_NACTI.getNadpis());
        provedObnoveniTlacitekGeneratorNacteni();
    }

    /**
     * Přepne tlačítka po generování nových prvků a po načtení dat ze souboru
     */
    private void provedObnoveniTlacitekGeneratorNacteni() {
        if (KomponentStrom.getInstance().jeVypnutoBtnNajdi())
            KomponentStrom.getInstance().zapniBtnNajdi();
        if (KomponentStrom.getInstance().jeVypnutoBtnOdeber())
            KomponentStrom.getInstance().zapniBtnOdeber();
        if (KomponentStrom.getInstance().jeVypnutoBtnIteruj())
            KomponentStrom.getInstance().zapniBtnIteruj();
        if (KomponentStrom.getInstance().jeVypnutoBtnPrazdnost())
            KomponentStrom.getInstance().zapniBtnPrazdnost();
        if (KomponentStrom.getInstance().jeVypnutoBtnZrus())
            KomponentStrom.getInstance().zapniBtnZrus();
        if (this.jeVypnutoBtnUloz())
            this.zapniBtnUloz();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Uložení">
    private void nastavUdalostUlozeni() {
        if (seznamPanel.uloz()) {
            InfoAlert.nahlasInfoLog(ZpravaLogu.LOG_USPESNE_ULOZENI.getZprava());
            tvurceCbNacteni.accept(
                    Titulek.CB_VZOR.getNadpis(),
                    Titulek.CB_KRAJE.getNadpis());
            return;
        }
        ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_CHYBNE_ULOZENI.getZprava());
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřejné zjišťovací metody o stavu tlačítek">
    public boolean jeVypnutoBtnGeneruj() { return generujBtn.isDisabled(); }
    public boolean jeVypnutoBtnNacti() { return nactiCb.isDisabled(); }
    public boolean jeVypnutoBtnUloz() { return ulozBtn.isDisabled(); }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřejné přepínače">
    public void zapniBtnGeneruj() { generujBtn.setDisable(false); }
    public void vypniBtnGeneruj() { generujBtn.setDisable(true); }

    public void zapniBtnNacti() { nactiCb.setDisable(false); }
    public void vypniBtnNacti() { nactiCb.setDisable(true); }

    public void zapniBtnUloz() { ulozBtn.setDisable(false); }
    public void vypniBtnUloz() { ulozBtn.setDisable(true); }
// </editor-fold>
}
