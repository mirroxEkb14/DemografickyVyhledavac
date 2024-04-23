package cz.upce.fei.bdats.struktury;

import cz.upce.fei.bdats.seznam.AbstrDoubleList;
import cz.upce.fei.bdats.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.StrukturaException;

/**
 * Tato třída představuje implementaci rozhraní {@link IAbstrLifo}, které definuje základní operace pro
 * zásobník (LIFO - Last-In-First-Out)
 *
 * @param <T> Generická data typu {@code T}
 */
public final class AbstrLifo<T> implements IAbstrLifo<T> {

    private final IAbstrDoubleList<T> zasobnik;

    /**
     * Konstruktor vytváří instanci zásobníku
     */
    public AbstrLifo() { zasobnik = new AbstrDoubleList<>(); }

    @Override
    public void vloz(T data) { zasobnik.vlozPrvni(data); }

    @Override
    public T odeber() throws StrukturaException {
        if (jePrazdny())
            throw new StrukturaException();
        return zasobnik.odeberPrvni();
    }

    @Override
    public T vrchol() { return zasobnik.zpristupniPrvni(); }

    @Override
    public boolean jePrazdny() { return zasobnik.jePrazdny(); }

    @Override
    public int mohutnost() { return zasobnik.mohutnost(); }

    @Override
    public void vycisti() { zasobnik.zrus(); }
}

