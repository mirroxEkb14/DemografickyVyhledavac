package cz.upce.fei.bdast.gui.koreny;

import cz.upce.fei.bdast.agenda.AgendaKraj;
import cz.upce.fei.bdast.agenda.IAgendaKraj;
import cz.upce.fei.bdast.strom.ETypProhl;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.vyjimky.AgendaKrajException;
import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.vyjimky.zpravy.ChybovaZpravaSeznamu;
import cz.upce.fei.bdast.vyjimky.SeznamPanelException;
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
    private final IAgendaKraj agendaKraj = AgendaKraj.getInstance();
    /**
     *
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
    public void obnovSeznam(@NotNull IAbstrTable<String, Obec> strom) throws SeznamPanelException {
        try {
            final Iterator<Obec> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                final Obec aktualniObec = iterator.next();
                pridejPrvek(aktualniObec);
                agendaKraj.vloz(aktualniObec);
            }
        } catch (AgendaKrajException ex) {
            throw new SeznamPanelException(ChybovaZpravaSeznamu.CHYBA_PRI_OBNOVENI.getZprava());
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
            agendaKraj.odeber(obec.getNazev());
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
    public void vypisStrom() {
        ulozAktualniStav();
        this.getItems().clear();
        this.getItems().add(agendaKraj.vypisStrom());
    }

    /**
     *
     */
    private void ulozAktualniStav() {
        ulozenyStav.getItems().addAll(this.getItems());
    }

    /**
     *
     */
    private void nactiPredchoziStav() {
       this.getItems().addAll(ulozenyStav.getItems());
    }

    @Override
    public int dejMohutnost() { return this.getItems().size(); }

    @Override
    public boolean jePrazdny() { return this.getItems().isEmpty(); }
}
