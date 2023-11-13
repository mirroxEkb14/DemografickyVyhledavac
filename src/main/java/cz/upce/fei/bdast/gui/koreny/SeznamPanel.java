package cz.upce.fei.bdast.gui.koreny;

import cz.upce.fei.bdast.agenda.AgendaKraj;
import cz.upce.fei.bdast.vyjimky.AgendaKrajException;
import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.vyjimky.zpravy.ChybovaZpravaSeznamu;
import cz.upce.fei.bdast.vyjimky.SeznamPanelException;
import javafx.scene.control.ListView;

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
    private final AgendaKraj strom = AgendaKraj.getInstance();

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
    public void pridej(Obec obec) throws SeznamPanelException {
        try {


            strom.vloz(obec);
        } catch (AgendaKrajException ex) {
            throw new SeznamPanelException(ChybovaZpravaSeznamu.CHYBA_PRI_VLOZENI.getZprava());
        }
    }

    @Override
    public void obnovSeznam() throws SeznamPanelException {
//        try {
//
//
//            strom.vloz();
//        } catch (AgendaKrajException ex) {
//            throw new SeznamPanelException(ChybovaZpravaSeznamu.CHYBA_PRI_OBNOVENI.getZprava());
//        }
    }

    @Override
    public int dejMohutnost() { return this.getItems().size(); }

    @Override
    public boolean jePrazdny() { return this.getItems().isEmpty(); }
}
