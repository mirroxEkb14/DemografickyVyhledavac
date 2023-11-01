package cz.upce.fei.bdast.agenda;

import cz.upce.fei.bdast.strom.IAbstrTable;

import java.util.Iterator;

/**
 *
 */
public interface IAgendaKraj<K extends Comparable<K>, V> extends Iterable<K> {

    /**
     * Provede import dat z textového souboru
     */
    void importDat();

    /**
     * Vyhledání obce
     *
     * @return
     */
    K najdi();

    /**
     * Vložení obce
     *
     * @return
     */
    K vloz();

    /**
     * Odebrání obce
     *
     * @return
     */
    K odeber();

    /**
     * Vrací iterátor tabulky
     *
     * @return
     */
    Iterator<K> vytvorIterator();

    /**
     * Umožnuje generovat jednotlivé obce
     *
     * @return
     */
    IAbstrTable<K, V> generuj();
}
