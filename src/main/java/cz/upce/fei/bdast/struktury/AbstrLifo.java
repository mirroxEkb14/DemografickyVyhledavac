package cz.upce.fei.bdast.struktury;

import cz.upce.fei.bdast.seznam.AbstrDoubleList;
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
public class AbstrLifo<T> {

    private final IAbstrDoubleList<T> zasobnik;

    /**
     * Konstruktor vytváří instanci zásobníku
     */
    public AbstrLifo() { zasobnik = new AbstrDoubleList<>(); }

    /**
     * Přidává prvek na vrchol zásobníku (push)
     *
     * @param data Prvek, který bude přidán na vrchol zásobníku
     */
    public void vloz(T data) { zasobnik.vlozPrvni(data); }

    /**
     * Odebere a vrátí prvek z vrcholu zásobníku (pop)
     *
     * @return Prvek z vrcholu zásobníku
     *
     * @throws StrukturaException Pokud je zásobník prázdný a nelze odebrat prvek
     */
    public T odeber() throws StrukturaException {
        if (jePrazdny())
            throw new StrukturaException();
        return zasobnik.odeberPrvni();
    }

    /**
     * Vrátí prvek z vrcholu zásobníku bez jeho odebrání (peek)
     *
     * @return Prvek z vrcholu zásobníku
     */
    public T vrchol() { return zasobnik.zpristupniPrvni(); }

    /**
     * Zjišťuje, zda je zásobník prázdný (isEmpty)
     *
     * @return {@code true}, pokud je zásobník prázdný, jinak {@code false}
     */
    public boolean jePrazdny() { return zasobnik.jePrazdny(); }

    /**
     * Vrací aktuální velikost zásobníku (size)
     *
     * @return Počet prvků v zásobníku
     */
    public int mohutnost() { return zasobnik.mohutnost(); }

    /**
     * Odstraní všechny prvky ze zásobníku, čímž ho vyprázdní (clear)
     */
    public void vycisti() { zasobnik.zrus(); }
}

