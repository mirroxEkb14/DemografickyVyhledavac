package cz.upce.fei.bdast.gui.komponenty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.gui.alerty.ErrorAlert;
import cz.upce.fei.bdast.gui.alerty.InfoAlert;
import cz.upce.fei.bdast.gui.dialogy.*;
import cz.upce.fei.bdast.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdast.gui.Titulek;
import cz.upce.fei.bdast.gui.kontejnery.TitulkovyPanel;
import cz.upce.fei.bdast.gui.kontejnery.Tlacitko;
import cz.upce.fei.bdast.gui.koreny.SeznamPanel;
import cz.upce.fei.bdast.gui.tvurce.TvurceObce;
import cz.upce.fei.bdast.vyjimky.zpravy.ZpravaLogu;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
// </editor-fold>

/**
 * Třída rozšiřující titulkový panel a obsahující odkazy na tlačítka pro manipulaci se stromem
 *
 * <p> Je návrhovým vzorem Singleton
 */
public final class KomponentStrom extends TitulkovyPanel {

    /**
     * Deklarace tlačítek
     */
    private final Button vlozBtn, najdiBtn, odeberBtn, prazdnostBtn, zrusBtn;
    /**
     * Inicializace výběrového pole pro iterátor
     */
    private final ChoiceBox<String> iteratorCb = new ChoiceBox<>();
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
        this.najdiBtn.setOnAction(actionEvent -> nastavUdalostVyhledavani());

        this.odeberBtn = new Tlacitko(Titulek.BTN_ODEBER.getNadpis());
        this.odeberBtn.setDisable(true);
        this.odeberBtn.setOnAction(actionEvent -> nastavUdalostOdebirani());

        this.iteratorCb.setDisable(true);
        nastavVyberovePole();
        nastavVychoziTitulekIteratoru();
        this.iteratorCb.setOnAction(actionEvent -> nastavUdalostIterace());

        this.prazdnostBtn = new Tlacitko(Titulek.BTN_PRAZDNOST.getNadpis());
        this.prazdnostBtn.setDisable(true);
        this.prazdnostBtn.setOnAction(actionEvent -> nastavUdalostPrazdnosti());

        this.zrusBtn = new Tlacitko(Titulek.BTN_ZRUS.getNadpis());
        this.zrusBtn.setDisable(true);
        this.zrusBtn.setOnAction(actionEvent -> nastavUdalostZruseni());

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
        gridPane.add(iteratorCb, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(prazdnostBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(zrusBtn, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Nastavení Výběrového Pole Iterátoru">
    private static final double PREFEROVANA_SIRKA_POLE = 100.0;

    /**
     * Nastaví chýchozí stav pro {@link ChoiceBox} iterátoru s pouze jednou variantou:
     * <ol>
     * <li> {@link Titulek#CB_ITERUJ}: Vypíše vnitřní hierarchii binárního stromu
     * </ol>
     */
    private void nastavVyberovePole() {
        iteratorCb.getItems().clear();
        iteratorCb.getItems().addAll(
                Titulek.CB_ITERATOR.getNadpis(),
                Titulek.CB_ITERUJ.getNadpis());
        iteratorCb.setPrefWidth(PREFEROVANA_SIRKA_POLE);
    }

    /**
     * Nastaví chýchozí stav pro {@link ChoiceBox} iterátoru s pouze jednou variantou:
     * <ol>
     * <li> {@link Titulek#CB_VRAT}: Vratí stav seznamu před zobrazováním vnitřní struktury stromu
     * </ol>
     */
    private void nastavPouzeVrat() {
        iteratorCb.getItems().clear();
        iteratorCb.getItems().addAll(
                Titulek.CB_ITERATOR.getNadpis(),
                Titulek.CB_VRAT.getNadpis());
        iteratorCb.setPrefWidth(PREFEROVANA_SIRKA_POLE);
        nastavVychoziTitulekIteratoru();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Vložení">
    /**
     * Inicializuje a zobrazuje dialog pro vložení nové obce po stisknutí tlačítka {@link KomponentStrom#vlozBtn}
     *
     * <p> Popis jednotlivých bloků kódu:
     * <ol>
     * <li> Vytvoření a zobrazení dialogu, pak získání odpovědi od uživatele:
     *      <ul>
     *      <li> <b>dialog = new DialogVlozeni()</b>
     *      <li> <b>dialog.showAndWait()</b>
     *      </ul>
     * <li> Zpracování odpovědi od uživatele:
     *      <ul>
     *      <li> <b>if (isPresent() && jeTlacitkoOk())</b>
     *      </ul>
     * <li> Získání názvu obce z textového pole v dialogu
     *      <ul>
     *      <li> <b>klic = dialog.getTfNazev().getText()</b>
     *      </ul>
     * <li> Kontrola, zda klíč je unikátní v seznamu
     *      <ul>
     *      <li> <b>if (jeUnikatnimKlicem())</b>
     *          <ol>
     *          <li> Pokud je klíč unikátní, vytvoření nové obce, přidání do seznamu a obnovení ostatních tlačítek
     *          </ol>
     *      </ul>
     * <li> Pokud klíč není unikátní, zobrazení chybové hlášky
     *      <ul>
     *      <li> <b>ErrorAlert.nahlasErrorLog()</b>
     *      </ul>
     * </ol>
     */
    private void nastavUdalostVlozeni() {
        final DialogVlozeni dialog = new DialogVlozeni();
        final Optional<ButtonType> odpoved = dialog.showAndWait();
        if (odpoved.isPresent() && dialog.jeTlacitkoOk(odpoved.get())) {
            final String klic = dialog.getTfNazev().getText();
            if (seznamPanel.jeUnikatnimKlicem(klic)) {
                new TvurceObce().vytvor(dialog)
                        .ifPresentOrElse(
                                novaObec -> {
                                    seznamPanel.pridej(novaObec);
                                    obnovTlacitkaProVlozeni();
                                },
                                () -> ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_TVORENI_SPATNA_POLE.getZprava()));
                return;
            }
            ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_TVORENI_DUPLICITNI_KLIC.getZprava());
        }
    }

    /**
     * Přepne všechny ostatní tlačítka po vložení prvku do stromu
     */
    private void obnovTlacitkaProVlozeni() {
        if (jeVypnutoBtnNajdi()) zapniBtnNajdi();
        if (jeVypnutoBtnOdeber()) zapniBtnOdeber();
        if (jeVypnutoBtnIteruj()) zapniBtnIteruj();
        if (jeVypnutoBtnPrazdnost()) zapniBtnPrazdnost();
        if (jeVypnutoBtnZrus()) zapniBtnZrus();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Vyhledávání/Odebírání">
    /**
     * Vytváří instanci třídy {@link DialogNalezeni}, která je dialogovým oknem pro vyhledávání. Poté se volá
     * {@link KomponentStrom#provedUdalostDialoguKlice(DialogKlic, Consumer)} s tímto dialogovým oknem a akcí,
     * která v případě nalezení obce zobrazí informace o ní
     */
    private void nastavUdalostVyhledavani() {
        final DialogNalezeni dialog = new DialogNalezeni();
        provedUdalostDialoguKlice(dialog, obec -> InfoAlert.nahlasInfoLog(obec.toString()));
    }

    /**
     * Vytváří instanci třídy {@link DialogOdebirani}, která je dialogovým oknem pro odebírání. Poté se volá
     * {@link KomponentStrom#provedUdalostDialoguKlice(DialogKlic, Consumer)} s tímto dialogovým oknem a akcí,
     * která v případě nalezení obce odstraní tuto obec ze seznamu a aktualizuje tlačítka pro odebírání
     */
    private void nastavUdalostOdebirani() {
        final DialogOdebirani dialog = new DialogOdebirani();
        provedUdalostDialoguKlice(dialog, obec -> {
            seznamPanel.vymaz(obec);
            obnovTlacitkaProOdebirani();
        });
    }

    /**
     * Obstarává společné kroky pro vyhledání obce v seznamu a provedení akce nad nalezeným prvkem
     * (pro operace vyhledávání a odebírání prvku)
     *
     * @param dialog Dialogové okno
     * @param akce Funkce v podobě objektu rozhraní {@link Consumer}, která bude provedena nad nalezeným prvkem
     */
    private void provedUdalostDialoguKlice(@NotNull DialogKlic dialog,
                                           Consumer<Obec> akce) {
        final Optional<ButtonType> odpoved = dialog.showAndWait();
        if (odpoved.isPresent() && dialog.jeTlacitkoOk(odpoved.get())) {
            final String klic = dialog.getTfNazev().getText();
            final Optional<Obec> nalezenaObec = seznamPanel.nalezni(klic);
            nalezenaObec.ifPresentOrElse(
                    akce,
                    () -> ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_NALEZENI_NENI_PRVEK.getZprava()));
        }
    }

    /**
     * Přepne všechny ostatní tlačítka po odebírání prvku ze stromu
     */
    private void obnovTlacitkaProOdebirani() {
        if (seznamPanel.jePrazdny()) {
            vypniBtnNajdi();
            vypniBtnOdeber();
            vypniBtnIteruj();
            vypniBtnPrazdnost();
            vypniBtnZrus();
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Iterace">
    /**
     * Podle zvolené uživatelem položky rozhodne, zda bude postupovat podle {@link Titulek#CB_ITERUJ} a
     * provede vyprázdnění stromu, anebo podle {@link Titulek#CB_VRAT} a vratí předchozí stav seznamu
     */
    private void nastavUdalostIterace() {
        final String zvolenaAkce = iteratorCb.getSelectionModel().getSelectedItem();
        if (Titulek.CB_ITERUJ.getNadpis().equalsIgnoreCase(zvolenaAkce)) {
            seznamPanel.vypisStrom();
            obnovTlacitkaProIteruj();
            return;
        }
        if (Titulek.CB_VRAT.getNadpis().equalsIgnoreCase(zvolenaAkce)) {
            seznamPanel.schovejStrom();
            obnovTlacitkaProVrat();
            nastavVychoziTitulekIteratoru();
        }
    }

    /**
     * Nastaví veškerá tlačítka na "vypnuto", když uživatel zvolí {@link Titulek#CB_ITERUJ}
     */
    private void obnovTlacitkaProIteruj() {
        vypniBtnVloz();
        vypniBtnNajdi();
        vypniBtnOdeber();
        vypniBtnPrazdnost();
        vypniBtnZrus();

        nastavPouzeVrat();
    }

    /**
     * Nastaví veškerá tlačítka na "zapnuto", když uživatel zvolí {@link Titulek#CB_VRAT}
     */
    private void obnovTlacitkaProVrat() {
        zapniBtnVloz();
        zapniBtnNajdi();
        zapniBtnOdeber();
        zapniBtnPrazdnost();
        zapniBtnZrus();

        nastavVyberovePole();
    }

    /**
     * Nastaví výchozí titulek pro výběrové pole, které se zobrazuje uživateli
     */
    private void nastavVychoziTitulekIteratoru() {
        iteratorCb.getSelectionModel().select(Titulek.CB_ITERATOR.getNadpis());
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Prázdnost">
    /**
     * Zobrazí informační dialogové okénko s aktuálním počtem prvků v rámci seznamu
     */
    private void nastavUdalostPrazdnosti() {
        InfoAlert.nahlasInfoLog(
                String.valueOf(seznamPanel.dejMohutnost()));
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Zrušení">
    /**
     * Zruší všechny položky seznamu
     */
    private void nastavUdalostZruseni() {
        seznamPanel.vyprazdni();
        obnovTlacitkaProZruseni();
    }

    /**
     * Vypne všechna tlačítka kromě tlačítka vložení {@link KomponentStrom#vlozBtn} po zrušení celého seznamu
     */
    private void obnovTlacitkaProZruseni() {
        if (jeVypnutoBtnVloz()) zapniBtnVloz();

        vypniBtnNajdi();
        vypniBtnOdeber();
        vypniBtnPrazdnost();
        vypniBtnZrus();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Zjišťovací metody o stavu tlačítek">
    private boolean jeVypnutoBtnVloz() { return vlozBtn.isDisabled(); }
    private boolean jeVypnutoBtnNajdi() { return najdiBtn.isDisabled(); }
    private boolean jeVypnutoBtnOdeber() { return odeberBtn.isDisabled(); }
    private boolean jeVypnutoBtnIteruj() { return iteratorCb.isDisabled(); }
    private boolean jeVypnutoBtnPrazdnost() { return prazdnostBtn.isDisabled(); }
    private boolean jeVypnutoBtnZrus() { return zrusBtn.isDisabled(); }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Přepínače">
    private void zapniBtnVloz() { vlozBtn.setDisable(false); }

    private void vypniBtnVloz() { vlozBtn.setDisable(true); }

    private void zapniBtnNajdi() { najdiBtn.setDisable(false); }

    private void vypniBtnNajdi() { najdiBtn.setDisable(true); }

    private void zapniBtnOdeber() { odeberBtn.setDisable(false); }

    private void vypniBtnOdeber() { odeberBtn.setDisable(true); }

    private void zapniBtnIteruj() { iteratorCb.setDisable(false); }

    private void vypniBtnIteruj() { iteratorCb.setDisable(true); }

    private void zapniBtnPrazdnost() { prazdnostBtn.setDisable(false); }

    private void vypniBtnPrazdnost() { prazdnostBtn.setDisable(true); }

    private void zapniBtnZrus() { zrusBtn.setDisable(false); }

    private void vypniBtnZrus() { zrusBtn.setDisable(true); }
// </editor-fold>
}
