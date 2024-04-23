package cz.upce.fei.bdats.struktury;

import cz.upce.fei.bdats.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.StrukturaException;

import java.util.Queue;

/**
 * Třída představuje implementaci fronty (FIFO) postavené na rozhraní {@link IAbstrDoubleList}. Poskytuje
 * základní operace pro manipulaci s frontou, včetně vložení, odebrání, kontrolu prázdnosti a zjištění velikosti
 *
 * <p> Fronta {@link Queue} je dynamická datová struktura, kde se odebírají prvky v tom pořadí, v jakém byly vloženy
 *
 * @param <T> Typ prvků, které budou uloženy ve frontě
 */
public interface IAbstrFifo<T> {

    /**
     * Vkládá prvek na konec fronty
     *
     * @param data Prvek, který bude vložen na konec fronty
     */
    void vloz(T data);

    /**
     * Odebere a vrátí první prvek z fronty
     *
     * @return Prvek z fronty
     *
     * @throws StrukturaException Pokud je fronta prázdná a nelze odebrat prvek
     */
    T odeber() throws StrukturaException;

    /**
     * Zjišťuje, zda je fronta prázdná
     *
     * @return {@code true}, pokud je fronta prázdná, jinak {@code false}
     */
    boolean jePrazdna();

    /**
     * Vrací aktuální velikost fronty
     *
     * @return Počet prvků ve frontě
     */
    int mohutnost();
}
