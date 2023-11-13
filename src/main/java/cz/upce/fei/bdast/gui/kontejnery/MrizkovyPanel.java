package cz.upce.fei.bdast.gui.kontejnery;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Třída reprezentující mřížkový panel {@link GridPane} pro uspořádání komponent na GUI
 */
public class MrizkovyPanel extends GridPane {

// <editor-fold defaultstate="collapsed" desc="Konstanty pro odsazení u GridPane">
    public final double ODSAZENI_OKRAJE = 10.0;
    public final double VERTIKALNI_MEZERA = 5.0;
    public final double HORIZONTALNI_MEZERA = 5.0;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Konstanty pro umístění prvků u GridPane">
    /**
     * Konstanta vyjadřuje výchozí šiřku tlačítka, které je umístěno jako jedno na celém řádku
     * tohoto mřížkového panelu
     */
    public static final double PREFEROVANA_SIRKA_VELKEHO_TLACITKA = 300.0;
    /**
     * Určuje počet sloupců, které má zabírat prvek na jednotlivém rádku mřížkového panelu
     *
     * @see GridPane#setColumnSpan(Node, Integer)
     */
    public static final int ROZPETI_SLOUPCU = 2;
    /**
     * Konstanty reprezentují sloupcové (columnIndex) a řádkové (rowIndex) indexy pro umístění v mřížce {@link GridPane}
     */
    public static final int SLOUPCOVY_INDEX_PRVNI = 0;
    public static final int RADKOVY_INDEX_PRVNI = 0;
    public static final int SLOUPCOVY_INDEX_DRUHY = 1;
    public static final int RADKOVY_INDEX_DRUHY = 1;
    public static final int RADKOVY_INDEX_TRETI = 2;
// </editor-fold>

    /**
     * Konstruktor inicializující mřížkový panel s výchozím nastavením
     */
    public MrizkovyPanel() {
        this.setPadding(new Insets(ODSAZENI_OKRAJE));
        this.setVgap(VERTIKALNI_MEZERA);
        this.setHgap(HORIZONTALNI_MEZERA);
    }
}
