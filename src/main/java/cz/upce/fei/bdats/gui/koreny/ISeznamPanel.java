package cz.upce.fei.bdats.gui.koreny;

import cz.upce.fei.bdats.agenda.AgendaKraj;
import cz.upce.fei.bdats.data.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.strom.IAbstrTable;
import cz.upce.fei.bdats.vyjimky.SeznamPanelException;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

import java.util.Optional;

/**
 * Toto rozhraní deklaruje základní metody pro implementaci {@link SeznamPanel} obsahující prvky binárního stromu
 */
public interface ISeznamPanel<T> {

    /**
     * Konstanty pro nastavení výchozího stavu seznamu v rámci metody {@link ISeznamPanel#nastavSeznamPanel(ListView)}}
     */
    int MIN_SIRKA_SEZNAMU = 660;
    String NAZEV_SEZNAM_FONTU = "Monospaced";
    int DIMENZE_SEZNAM_FONTU = 13;
    String PRAZDNY_RETEZEC = "";

    /**
     * Přidá novou obec do seznamu {@link ListView} a samotného stromu pomocí její agendy {@link AgendaKraj}
     *
     * @return {@code false}, pokud se vyskytne výjimka během procesu vkládání prvku do bunárního stromu používáním
     * správce tohoto stromu {@link AgendaKraj}, jinak {@code true}
     */
    boolean pridej(Obec obec);

    /**
     * Obnoví obsah seznamů:
     * <ul>
     * <li> Přidá prvky do {@link ListView} seznamu pro zobrazení uživateli
     * <li> Přidá prvky do {@link IAbstrTable} stromu
     * </ul>
     *
     * @param pocet Počet Prvků
     *
     * @throws SeznamPanelException Když se vyvolá výjimka při vkládání prvku do vnitřního stromu přes
     * {@link AgendaKraj}
     */
    void obnovSeznam(int pocet) throws SeznamPanelException;

    /**
     * Ověří, zda vstupní klíč je unikátní pro aktuální obsah seznamu
     *
     * @param klic Klíč pro ověření na unikátnost
     *
     * @return {@code true}, pokud je předaný klíč unikátní, jinak {@code false}
     */
    boolean jeUnikatnimKlicem(String klic);

    /**
     * Nalezne prvek v rámci seznamu podle vstupního klíče
     *
     * @param klic Klíč, podle kterého bude probíhat vyhledávání prvku
     *
     * @return Nalezený prvek seznamu, čímž je instance třídy {@link Obec}
     */
    Optional<Obec> nalezni(String klic);

    /**
     * Vymaže předaný prvek ze seznamu. Nevrací odebranou hodnotu a nekontroluje, zda vstupní obec existuje nebo
     * ne - musí to být zajišťěno před voláním této metody
     *
     * @param obec Instance obce, která bude ze seznamu odebraná
     *
     * @return {@code true}, pokud proces odebírání proběhl úspěšně, v případě, že správce stromu, resp. ten
     * samý strom, vyvolá svoji vlastná výjimku, vratí {@code false}
     */
    boolean vymaz(Obec obec);

    /**
     * Vymaže obsah seznamu a přidá celóu hierarchii stromu do seznamu jako jeden prvek, tj. pouze jedním
     * voláním metody {@code add(E e)}
     *
     * @param typ Typem může být buď do šířky, anebo do hloubky
     */
    void vypisStrom(ETypProhl typ);

    /**
     * Vymaže výpis celého stromu a vratí výchozí stav, tj. ten, který byl uložen před výpisem
     */
    void schovejStrom();

    /**
     * Vyprázdnění celého stromu a grafického seznamu
     */
    void vyprazdni();

    /**
     * Načte data (obce) ze souboru (.csv)
     *
     * @param cesta Cesta do .csv souboru
     *
     * @return {@code true}, pokud se podařilo načíst data bez výjimek, v opačném případě {@code false}
     */
    boolean nacti(String cesta);

    /**
     * Uloží data do .csv souboru
     *
     * @return {@code true}, pokud proces uložení proběhl úspěšně, v případě vyhození {@link IOException}
     * - {@code false}
     */
    boolean uloz();

    /**
     * Pomocní zjišťovací metoda vrací aktuální mohutnost seznamu
     *
     * @return Počet prvků v seznamu
     */
    int dejMohutnost();

    /**
     * Pomocní zjišťovací metoda říká, zda je seznam prázdný nebo není
     *
     * @return {@code true}, pokud je seznam prázdný, {@code false} v opačném případě
     */
    boolean jePrazdny();

    /**
     * Nastavuje výchozí vzhled a chování panelu {@link ListView} seznamu
     */
    default void nastavSeznamPanel(@NotNull ListView<T> seznamPanel) {
        seznamPanel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        seznamPanel.setMinWidth(MIN_SIRKA_SEZNAMU);
            seznamPanel.setCellFactory(cell -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item.toString());
                    setFont(Font.font(NAZEV_SEZNAM_FONTU, DIMENZE_SEZNAM_FONTU));
                } else {
                    setText(PRAZDNY_RETEZEC);
                }
            }
        });
    }
}
