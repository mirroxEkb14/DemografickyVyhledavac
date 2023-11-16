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
import cz.upce.fei.bdast.strom.ETypProhl;
import cz.upce.fei.bdast.vyjimky.zpravy.ZpravaLogu;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;
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
     * Definice funkcionálního rozhraní {@link BiConsumer} pro vytvoření obsahu výběrového pole {@link ChoiceBox}:
     * <ul>
     * <li> Vyčištění a aktualizace položek
     *      <ul>
     *      <li> <b>iteratorCb.getItems().clear()</b>
     *      <li> <b>iteratorCb.getItems().addAll()</b>
     *      </ul>
     * <li> Nastavení preferované šířky
     *      <ul>
     *      <li> <b>iiteratorCb.setPrefWidth()</b>
     *      </ul>
     * <li> Nastavení výběru na první položku a přidání události pro reakci na výběr
     *      <ul>
     *      <li> <b>iteratorCb.getSelectionModel().select()</b>
     *      <li> <b>iteratorCb.setOnAction()</b>
     *      </ul>
     * </ul>
     */
    final BiConsumer<String, String> tvurceCbIteratoru = (t, u) -> {
        this.iteratorCb.getItems().clear();

        if (t == null || t.isEmpty())
            this.iteratorCb.getItems().addAll(
                    Titulek.CB_ITERATOR.getNadpis(), u);
        else if (u == null || u.isEmpty())
            this.iteratorCb.getItems().addAll(
                    Titulek.CB_ITERATOR.getNadpis(), t);
        else
            this.iteratorCb.getItems().addAll(
                    Titulek.CB_ITERATOR.getNadpis(), t, u);

        this.iteratorCb.setPrefWidth(PREFEROVANA_SIRKA_POLE);
        this.iteratorCb.getSelectionModel().select(Titulek.CB_ITERATOR.getNadpis());
        this.iteratorCb.setOnAction(actionEvent -> nastavUdalostIterace());
    };
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

// <editor-fold defaultstate="collapsed" desc="Nastavení třídy">
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

        tvurceCbIteratoru.accept(
                Titulek.CB_SIRKA.getNadpis(),
                Titulek.CB_HLOUBKA.getNadpis());
        this.iteratorCb.setDisable(true);

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
            final String klic = dialog.getTfNazevObce().getText();
            if (klic.isEmpty()) {
                ErrorAlert.nahlasErrorLog(ZpravaLogu.LOG_TVORENI_PRAZDNY_KLIC.getZprava());
                return;
            }
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
        if (KomponentPrikazy.getInstance().jeVypnutoBtnUloz())
            KomponentPrikazy.getInstance().zapniBtnUloz();
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
            final String klic = dialog.getTfNazevObce().getText();
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
            KomponentPrikazy.getInstance().vypniBtnUloz();
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Operace Iterace">
    /**
     * Podle zvolené uživatelem položky rozhodne, zda bude postupovat podle {@link Titulek#CB_SIRKA}/
     * {@link Titulek#CB_HLOUBKA} a provede vyprázdnění stromu, anebo podle {@link Titulek#CB_VRAT} a vratí
     * předchozí stav seznamu
     */
    private void nastavUdalostIterace() {
        final String zvolenaAkce = iteratorCb.getSelectionModel().getSelectedItem();
        if (jeVybranaAkceProProhlizeni(zvolenaAkce)) {
            final ETypProhl typ = dejVybranyTyp(zvolenaAkce);
            seznamPanel.vypisStrom(typ);
            provedObnoveniTlacitekSirkaHloubka();
        } else if (jeVybranaAkceProVraceni(zvolenaAkce)) {
            seznamPanel.schovejStrom();
            obnovTlacitkaProVrat();
            this.iteratorCb.getSelectionModel().select(Titulek.CB_ITERATOR.getNadpis());
        }
    }

    /**
     * Zjišťuje, zda je zadaná položka určena pro prohlížení stromu
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return {@code true}, pokud je položka pro prohlížení, jinak {@code false}
     */
    private boolean jeVybranaAkceProProhlizeni(String polozka) {
        return Titulek.CB_SIRKA.getNadpis().equalsIgnoreCase(polozka)
                || Titulek.CB_HLOUBKA.getNadpis().equalsIgnoreCase(polozka);
    }

    /**
     * Vrací typ prohlížení na základě zvolené položky
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return Typ prohlížení na základě zvolené položky
     *
     * @see ETypProhl
     */
    private ETypProhl dejVybranyTyp(@NotNull String polozka) {
        return polozka.equalsIgnoreCase(Titulek.CB_SIRKA.getNadpis())
                ? ETypProhl.SIRKA : ETypProhl.HLOUBKA;
    }

    /**
     * Zjišťuje, zda je zadaná položka určena pro návrat v rámci stromu
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return {@code true}, pokud je položka pro návrat, jinak {@code false}
     */
    private boolean jeVybranaAkceProVraceni(String polozka) {
        return Titulek.CB_VRAT.getNadpis().equalsIgnoreCase(polozka);
    }

    /**
     * Nastaví veškerá tlačítka na "vypnuto", když uživatel zvolí buď {@link Titulek#CB_SIRKA}, anebo
     * {@link Titulek#CB_HLOUBKA}
     *
     * <p> Po stisknutí tlačítka {@link Titulek#CB_SIRKA} nebo {@link Titulek#CB_HLOUBKA} se tyto položky vysmažou
     * a budou teď pouze dvě: {@link Titulek#CB_ITERATOR} a {@link Titulek#CB_VRAT}
     */
    private void provedObnoveniTlacitekSirkaHloubka() {
        vypniBtnVloz();
        vypniBtnNajdi();
        vypniBtnOdeber();
        vypniBtnPrazdnost();
        vypniBtnZrus();
        KomponentPrikazy.getInstance().vypniBtnGeneruj();
        KomponentPrikazy.getInstance().vypniBtnNacti();
        KomponentPrikazy.getInstance().vypniBtnUloz();

        tvurceCbIteratoru.accept(
                Titulek.CB_VRAT.getNadpis(),
                null);
    }

    /**
     * Nastaví veškerá tlačítka na "zapnuto", když uživatel zvolí {@link Titulek#CB_VRAT}
     *
     * <p> Po stisknutí tlačítka {@link Titulek#CB_VRAT} tato položka se vysmaže a budou teď pouze tři:
     * {@link Titulek#CB_ITERATOR}, {@link Titulek#CB_SIRKA} a {@link Titulek#CB_HLOUBKA}
     */
    private void obnovTlacitkaProVrat() {
        zapniBtnVloz();
        zapniBtnNajdi();
        zapniBtnOdeber();
        zapniBtnPrazdnost();
        zapniBtnZrus();
        KomponentPrikazy.getInstance().zapniBtnGeneruj();
        KomponentPrikazy.getInstance().zapniBtnNacti();
        KomponentPrikazy.getInstance().zapniBtnUloz();

        tvurceCbIteratoru.accept(
                Titulek.CB_SIRKA.getNadpis(),
                Titulek.CB_HLOUBKA.getNadpis());
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
        vypniBtnIteruj();
        vypniBtnPrazdnost();
        vypniBtnZrus();
        KomponentPrikazy.getInstance().vypniBtnUloz();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřekné zjišťovací metody o stavu tlačítek">
    public boolean jeVypnutoBtnVloz() { return vlozBtn.isDisabled(); }
    public boolean jeVypnutoBtnNajdi() { return najdiBtn.isDisabled(); }
    public boolean jeVypnutoBtnOdeber() { return odeberBtn.isDisabled(); }
    public boolean jeVypnutoBtnIteruj() { return iteratorCb.isDisabled(); }
    public boolean jeVypnutoBtnPrazdnost() { return prazdnostBtn.isDisabled(); }
    public boolean jeVypnutoBtnZrus() { return zrusBtn.isDisabled(); }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřejné přepínače">
    public void zapniBtnVloz() { vlozBtn.setDisable(false); }
    public void vypniBtnVloz() { vlozBtn.setDisable(true); }

    public void zapniBtnNajdi() { najdiBtn.setDisable(false); }
    public void vypniBtnNajdi() { najdiBtn.setDisable(true); }

    public void zapniBtnOdeber() { odeberBtn.setDisable(false); }
    public void vypniBtnOdeber() { odeberBtn.setDisable(true); }

    public void zapniBtnIteruj() { iteratorCb.setDisable(false); }
    public void vypniBtnIteruj() { iteratorCb.setDisable(true); }

    public void zapniBtnPrazdnost() { prazdnostBtn.setDisable(false); }
    public void vypniBtnPrazdnost() { prazdnostBtn.setDisable(true); }

    public void zapniBtnZrus() { zrusBtn.setDisable(false); }
    public void vypniBtnZrus() { zrusBtn.setDisable(true); }
// </editor-fold>
}
