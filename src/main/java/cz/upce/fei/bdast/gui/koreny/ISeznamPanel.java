package cz.upce.fei.bdast.gui.koreny;

import cz.upce.fei.bdast.agenda.AgendaKraj;
import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.vyjimky.SeznamPanelException;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

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
     * Obecné kosntanty používané pro vyhnutí se magickým číslem (magic numbers)
     */
    int NULOVA_HODNOTA = 0;
    int JEDNICKA = 1;
    int HODNOTA_INKREMENTU = 1;

    /**
     * Přidá novou obec do seznamu {@link ListView} a samotného stromu pomocí její agendy {@link AgendaKraj}
     *
     * @throws SeznamPanelException Když se vyskytne výjimka během procesu vkládání prvku do bunárního stromu
     * používáním správce tohoto stromu {@link AgendaKraj}
     */
    void pridej(Obec obec) throws SeznamPanelException;

    /**
     * Obnoví obsah seznamů:
     * <ul>
     * <li> Přidá prvky do {@link ListView} seznamu pro zobrazení uživateli
     * <li> Přidá prvky do {@link IAbstrTable} stromu
     * </ul>
     *
     * @param strom Strom prvků pro vkládání
     *
     * @throws SeznamPanelException Když se vyvolá výjimka při vkládání prvku do vnitřního stromu přes
     * {@link AgendaKraj}
     */
    void obnovSeznam(IAbstrTable<String, Obec> strom) throws SeznamPanelException;

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
