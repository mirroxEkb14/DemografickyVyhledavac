package cz.upce.fei.bdats.gui.koreny;

import cz.upce.fei.bdats.agenda.AgendaKraj;
import cz.upce.fei.bdats.agenda.IAgendaKraj;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;
import cz.upce.fei.bdats.data.Obec;
import cz.upce.fei.bdats.vyjimky.SeznamPanelException;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;

/**
 * Třída reprezentující seznamový panel. Je rozšířením {@link ListView} s dalšími funkcionalitami. Obsahuje odkaz
 * na instanci binárního vyhledávacího stromu a metody pro manipulaci se seznamem prvků
 *
 * <p>
 * Třída je návrhovým vzorem Singleton pro zajištění jediné instance
 */
public final class SeznamPanel extends ListView<String> implements ISeznamPanel<String> {

    /**
     * Deklarace a inicializace instanci na agendu obcí obsahující základní metody pro správu stromu
     */
    private final IAgendaKraj<String, Obec> agendaKraj = AgendaKraj.getInstance();
    /**
     * Vnitřní {@link ListView} obsahuje stav seznamu před iterací, resp. před zobrazením vnitřní hierarchii
     * binárního vyhledávacího stromu.
     */
    private final ListView<String> ulozenyStav = new ListView<>();

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static SeznamPanel instance;

    public static SeznamPanel getInstance() {
        if (instance == null)
            instance = new SeznamPanel();
        return instance;
    }
// </editor-fold>

    /**
     * Privátní konstruktor voláním privátní pomocní metody {@link ISeznamPanel#nastavSeznamPanel(ListView)} nastaví
     * výchozí vzhled a chování tohoto panelu
     */
    private SeznamPanel() { this.nastavSeznamPanel(this); }

    @Override
    public boolean pridej(@NotNull Obec obec) {
        try {
            agendaKraj.vloz(obec);
            pridejPrvek(obec);
            return true;
        } catch (AgendaKrajException ex) {
            return false;
        }
    }

    /**
     * Pridá prvek uvedený v argumentu do tohoto {@link ListView} seznamu
     *
     * @param obec Instance nově vytvořeného uzlu stromu
     */
    private void pridejPrvek(@NotNull Obec obec) {
        this.getItems().add(obec.toString());
    }

    @Override
    public void obnovSeznam(int pocet) throws SeznamPanelException {
        agendaKraj.generuj(pocet);
        vycistiSeznam();
        pridejStromDoSeznamu();
    }

    /**
     * Přidá celý strom do grafického seznamu
     */
    private void pridejStromDoSeznamu() {
        final Iterator<Obec> iterator = agendaKraj.vytvorIterator(ETypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            pridejPrvek(iterator.next());
        }
    }

    @Override
    public boolean jeUnikatnimKlicem(String klic) {
        try {
            agendaKraj.najdi(klic);
            return false;
        } catch (AgendaKrajException ex) {
            return true;
        }
    }

    @Override
    public Optional<Obec> nalezni(String klic) {
        try {
            return Optional.of(agendaKraj.najdi(klic));
        } catch (AgendaKrajException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean vymaz(@NotNull Obec obec) {
        try {
            agendaKraj.odeber(obec.getNazevObce());
            vymazPrvek(obec);
            return true;
        } catch (AgendaKrajException e) {
            return false;
        }
    }

    /**
     * Odebere obci ze seznamu {@link ListView}
     *
     * @param obec Prvek, který bude odebrán
     */
    private void vymazPrvek(@NotNull Obec obec) {
        this.getItems().remove(obec.toString());
    }

    @Override
    public void vypisStrom(ETypProhl typ) {
        ulozAktualniStav();
        vycistiSeznam();
        this.getItems().add(agendaKraj.vypisStrom(typ));
    }

    @Override
    public void schovejStrom() { nactiPredchoziStav(); }

    /**
     * Uloží aktuální stav seznamu před výpisem vnitřní hierarchii binátního stromu
     */
    private void ulozAktualniStav() {
        ulozenyStav.getItems().addAll(this.getItems());
    }

    /**
     * Vymaže výpis hierarchii stromu a načte uložený stav seznamu
     *
     * <p> <b>Poznámka</b>: Je zapotřebí vyprázdnit uložený stav seznamu po jeho vracení zpátky
     */
    private void nactiPredchoziStav() {
        vycistiSeznam();
        this.getItems().addAll(ulozenyStav.getItems());
        ulozenyStav.getItems().clear();
    }

    @Override
    public void vyprazdni() {
        agendaKraj.zrus();
        vycistiSeznam();
    }

    @Override
    public boolean nacti(String cesta) {
        if (agendaKraj.importDat(cesta)) {
            pridejStromDoSeznamu();
            return true;
        }
        return false;
    }

    @Override
    public boolean uloz() { return agendaKraj.exportDat(); }

    @Override
    public int dejMohutnost() { return this.getItems().size(); }

    @Override
    public boolean jePrazdny() { return this.getItems().isEmpty(); }

    /**
     * Vymaže všechny prvky grafického seznamu
     */
    private void vycistiSeznam() { this.getItems().clear(); }
}
