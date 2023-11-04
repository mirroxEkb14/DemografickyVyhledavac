package cz.upce.fei.bdast.struktury;

import cz.upce.fei.bdast.seznam.AbstrDoubleList;
import cz.upce.fei.bdast.seznam.IAbstrDoubleList;
import java.util.Queue;

/**
 * Třída představuje implementaci fronty (FIFO) postavené na rozhraní {@link IAbstrDoubleList}. Poskytuje
 * základní operace pro manipulaci s frontou, včetně vložení, odebrání, kontrolu prázdnosti a zjištění velikosti
 *
 * <p> Fronta {@link Queue} je dynamická datová struktura, kde se odebírají prvky v tom pořadí, v jakém byly vloženy
 *
 * @param <T> Typ prvků, které budou uloženy ve frontě
 */
public final class AbstrFifo<T> {

    private final IAbstrDoubleList<T> fronta;

    /**
     * Konstruktor vytváří instanci fronty
     */
    public AbstrFifo() { fronta = new AbstrDoubleList<>(); }

    /**
     * Vkládá prvek na konec fronty
     *
     * @param data Prvek, který bude vložen na konec fronty
     */
    public void vloz(T data) { fronta.vlozPosledni(data); }

    /**
     * Odebere a vrátí první prvek z fronty
     *
     * @return Prvek z fronty
     *
     * @throws StrukturaException Pokud je fronta prázdná a nelze odebrat prvek
     */
    public T odeber() throws StrukturaException {
        if (jePrazdna())
            throw new StrukturaException();
        return fronta.odeberPrvni();
    }

    /**
     * Zjišťuje, zda je fronta prázdná
     *
     * @return {@code true}, pokud je fronta prázdná, jinak {@code false}
     */
    public boolean jePrazdna() { return fronta.jePrazdny(); }

    /**
     * Vrací aktuální velikost fronty
     *
     * @return Počet prvků ve frontě
     */
    public int mohutnost() { return fronta.velikost(); }
}

