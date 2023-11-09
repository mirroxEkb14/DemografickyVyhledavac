package cz.upce.fei.bdast.struktury;

import cz.upce.fei.bdast.seznam.IAbstrDoubleList;

import java.util.Stack;

/**
 * Třída představuje implementaci zásobníku (LIFO) postaveného na abstraktní datové struktuře {@link IAbstrDoubleList}.
 * Poskytuje základní operace pro manipulaci se zásobníkem, včetně push, pop, peek, isEmpty, size a clear
 *
 * <p> Zásobník {@link Stack} je dynamická datová struktura umožňující vkládání a odebírání hodnot tak, že
 * naposledy vložená hodnota se odebere jako první
 *
 * @param <T> Typ prvků, které budou uloženy ve zásobníku
 */
public interface IAbstrLifo<T> {

    /**
     * Přidává prvek na vrchol zásobníku (push)
     *
     * @param data Prvek, který bude přidán na vrchol zásobníku
     */
    void vloz(T data);

    /**
     * Odebere a vrátí prvek z vrcholu zásobníku (pop)
     *
     * @return Prvek z vrcholu zásobníku
     *
     * @throws StrukturaException Pokud je zásobník prázdný a nelze odebrat prvek
     */
    T odeber() throws StrukturaException;

    /**
     * Vrátí prvek z vrcholu zásobníku bez jeho odebrání (peek)
     *
     * @return Prvek z vrcholu zásobníku
     */
    T vrchol();

    /**
     * Zjišťuje, zda je zásobník prázdný (isEmpty)
     *
     * @return {@code true}, pokud je zásobník prázdný, jinak {@code false}
     */
    boolean jePrazdny();

    /**
     * Vrací aktuální velikost zásobníku (size)
     *
     * @return Počet prvků v zásobníku
     */
    int mohutnost();

    /**
     * Odstraní všechny prvky ze zásobníku, čímž ho vyprázdní (clear)
     */
    void vycisti();
}
