package cz.upce.fei.bdast.perzistence;

import cz.upce.fei.bdast.strom.IAbstrTable;

import java.io.IOException;

/**
 * Rozhraní pro perzistenci dat do/z CSV souboru
 *
 * @param <K> Typ klíče
 * @param <V> Typ hodnoty
 */
public interface IPerzistence<K extends Comparable<K>, V> {

    /**
     * Cesta k vzorovému CSV souboru a souboru s kraji
     */
    String CESTA_VZOR = "src/main/java/cz/upce/fei/bdast/util/vzor.csv";
    String CESTA_KRAJE = "src/main/java/cz/upce/fei/bdast/util/kraje.csv";
    /**
     * Oddělovač (delimiter) atributů v CSV souboru.
     */
    String ODDELOVAC_ATRIBUTU = ";";
    /**
     * Indexy atributů podle struktury CSV souboru
     */
    int CELKEM_ATRIBUTU = 7;
    int INDEX_ATRIBUTU_CISLO_KRAJE = 0;
    int INDEX_ATRIBUTU_NAZEV_KRAJE = 1;
    int INDEX_ATRIBUTU_PSC = 2;
    int INDEX_ATRIBUTU_NAZEV_OBCE = 3;
    int INDEX_ATRIBUTU_POCET_MUZU = 4;
    int INDEX_ATRIBUTU_POCET_ZEN = 5;
    int INDEX_ATRIBUTU_CELKEM = 6;

    /**
     * Načte data ze CSV souboru do tabulky
     *
     * @param strom Tabulka, do které se načtou data
     * @param cesta Cesta k CSV souboru
     *
     * @return {@code true}, pokud načtení proběhlo úspěšně, jinak {@code false}
     *
     * @throws IOException Pokud dojde k chybě při čtení ze souboru
     */
    boolean nactiCsv(IAbstrTable<K, V> strom, String cesta) throws IOException;

    /**
     * Uloží data z tabulky do CSV souboru
     *
     * @param strom Tabulka, ze které se data uloží
     *
     * @return {@code true}, pokud uložení proběhlo úspěšně, jinak {@code false}
     *
     * @throws IOException Pokud dojde k chybě při zápisu do souboru
     */
    boolean ulozCsv(IAbstrTable<K, V> strom) throws IOException;
}
