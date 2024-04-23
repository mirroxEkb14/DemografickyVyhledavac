package cz.upce.fei.bdats.struktury;

import cz.upce.fei.bdats.seznam.AbstrDoubleList;
import cz.upce.fei.bdats.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.StrukturaException;

/**
 * Tato třída představuje implementaci rozhraní {@link IAbstrFifo}, které definuje základní operace pro frontu
 * (FIFO - First-In-First-Out)
 *
 * @param <T> Generická data typu {@code T}
 */
public final class AbstrFifo<T> implements IAbstrFifo<T> {

    private final IAbstrDoubleList<T> fronta;

    /**
     * Konstruktor vytváří instanci fronty
     */
    public AbstrFifo() { fronta = new AbstrDoubleList<>(); }

    @Override
    public void vloz(T data) { fronta.vlozPosledni(data); }

    @Override
    public T odeber() throws StrukturaException {
        if (jePrazdna())
            throw new StrukturaException();
        return fronta.odeberPrvni();
    }

    @Override
    public boolean jePrazdna() { return fronta.jePrazdny(); }

    @Override
    public int mohutnost() { return fronta.mohutnost(); }
}

