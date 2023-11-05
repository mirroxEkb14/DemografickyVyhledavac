package cz.upce.fei.bdast.strom;

import cz.upce.fei.bdast.struktury.AbstrFifo;
import cz.upce.fei.bdast.struktury.AbstrLifo;
import cz.upce.fei.bdast.struktury.StrukturaException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Třída představuje implementaci abstraktního binárního vyhledávacího stromu. Strom obsahuje uzly
 * (prvky) se zadanými klíči a hodnotami. Každý uzel může mít levý a pravý podstrom, což umožňuje
 * rychlé vyhledávání prvků. Používá generické typy pro klíče {@code K} a hodnoty {@code V}, kde {@code K}
 * musí implementovat rozhraní {@link Comparable}. Klíče jsou porovnávány pro uspořádání stromu
 *
 * @param <K> Typ klíče prvků v stromu, musí implementovat {@link Comparable}
 * @param <V> Typ hodnoty
 */
public final class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    private Uzel koren;

    /**
     * Konstanta pro nulu
     */
    private final int NULTA_HODNOTA = 0;
    /**
     * Konstanta pro zvýšení velikosti stromu
     */
    private final int ZVETSOVAC_MOHUTNOSTI = 1;

    /**
     * Privátní třída reprezentující uzel stromu. Každý uzel má klíč, hodnotu,
     * levý a pravý podstrom a velikost
     */
    private class Uzel {
        K klic;
        V hodnota;
        Uzel rodic;
        Uzel vlevo;
        Uzel vpravo;
        int mohutnost;

        Uzel(K klic, V hodnota, Uzel rodic) {
            this.klic = klic;
            this.hodnota = hodnota;
            this.rodic = rodic;
            vlevo = vpravo = null;
            mohutnost = ZVETSOVAC_MOHUTNOSTI;
        }
    }

    /**
     * Konstruktor inicializuje kořen stromu jako {@code null}
     */
    public AbstrTable() { koren = null; }

    /**
     * Nastaví kořen stromu na {@code null} pro zrušení celého stromu
     */
    @Override
    public void zrus() { koren = null; }

    /**
     * Testování prázdnosti stromu
     */
    @Override
    public boolean jePrazdny() { return koren == null; }

    /**
     * Stručný popis logiky:
     * <ol>
     * <li> <b>while (iterator.hasNext())</b>: Vytvoří iterátor umožňující procházet uzly stromu ve formátu
     * {@code in-order} (tj. od nejlevějšího ke kořeni, a poté postupně doprava). Iteruje přes všechny uzly
     * stromu
     * <li> <b>{@link AbstrTable#jeNula(Comparable, Comparable)}</b>: Pokud se klíče shodují, vrátí hodnotu
     * tohoto uzlu {@code aktUzel.hodnota}
     * <li> <b>throw new StromException()</b>: Pokud se klíče nerovnají žádnému uzlu ve stromu, dojde k vyhození výjimky {@link StromException}
     * s chybovou zprávou říkájící, že prvek nebyl nalezen
     * </ol>
     */
    @Override
    public V najdi(K klic) throws StromException {
        pozadatNePrazdnyKlic(klic);
        pozadatNePrazdnyKoren();

        final HloubkaIterator iterator = new HloubkaIterator();
        while (iterator.hasNext()) {
            final Uzel aktUzel = iterator.next();
            if (jeNula(aktUzel.klic, klic))
                return aktUzel.hodnota;
        }
        throw new StromException(ChybovaZprava.PRVEK_NENALEZEN.getZprava());
    }

    /**
     * Stručný popis logiky:
     * <ol>
     * <li> <b>koren == null</b>: Pokud je strom prázdný, vytvoří kořen s novým uzlem a skončí
     * <li> {@link AbstrTable#jeNula(Comparable, Comparable)}: Pokud nový klíč je roven aktuálnímu uzlu,
     * to znamená, že klíč již existuje v stromu, tak aktualizuje hodnotu
     * <li> {@link AbstrTable#jeZaporne(Comparable, Comparable)}: Pokud nový klíč je menší než aktuální
     * uzel, pokračuje doleva. Pokud je levý potomek prázdný, vytvoří nový uzel
     * <li> {@link AbstrTable#jeKladne(Comparable, Comparable)}: Pokud nový klíč je větší než aktuální
     * uzel, pokračuje doprava. Pokud je pravý potomek prázdný, vytvoří nový uzel
     * </ol>
     */
    @Override
    public void vloz(K klic, V hodnota) throws StromException {
        pozadatNePrazdnyKlic(klic);
        if (koren == null) {
            koren = new Uzel(klic, hodnota, null);
            return;
        }
        final HloubkaIterator iterator = new HloubkaIterator();
        Uzel aktUzel;
        while (iterator.hasNext()) {
            aktUzel = iterator.next();
            if (jeNula(klic, aktUzel.klic)) {
                aktUzel.hodnota = hodnota;
                return;
            }
            if (jeZaporne(klic, aktUzel.klic)) {
                if (aktUzel.vlevo == null) {
                    aktUzel.vlevo = new Uzel(klic, hodnota, aktUzel);
                    zvysMohutnost(aktUzel);
                    return;
                }
            }
            if (jeKladne(klic, aktUzel.klic)) {
                if (aktUzel.vpravo == null) {
                    aktUzel.vpravo = new Uzel(klic, hodnota, aktUzel);
                    zvysMohutnost(aktUzel);
                    return;
                }
            }
        }
    }


    /**
     * Stručný popis logiký:
     * <ol>
     * <li> <b>najdiUzel(klic)</b>
     * </ol>
     */
    @Override
    public V odeber(K klic) throws StromException {
        pozadatNePrazdnyKlic(klic);
        pozadatNePrazdnyKoren();

        Uzel uzel = najdiUzel(klic);
        if (uzel == null)
            throw new StromException(ChybovaZprava.PRVEK_NENALEZEN.getZprava());

        final V odebranaHodnota = uzel.hodnota;
        if (jeListem(uzel)) {
            odeberList(uzel);
        } else if (jsouObaPotomky(uzel)) {
            Uzel naslednik = najdiNaslednika(uzel);
            uzel.klic = naslednik.klic;
            uzel.hodnota = naslednik.hodnota;
            odeberNaslednika(naslednik);
        } else if (jeJedenPotomek(uzel)) {
            odeberJednohoPotomka(uzel);
        }
        aktualizujMohutnostPoOdebrani(uzel);
        return odebranaHodnota;
    }

    private Uzel najdiUzel(K klic) {
        final HloubkaIterator iterator = new HloubkaIterator();
        Uzel uzel;
        while (iterator.hasNext()) {
            uzel = iterator.next();
            if (jeNula(uzel.klic, klic))
                return uzel;
        }
        return null;
    }

    private Uzel najdiNaslednika(Uzel uzel) {
        if (uzel.vpravo != null) {
            // Pokud uzel má pravého potomka, najdeme nejlevějšího uzlu v pravém podstromu
            uzel = uzel.vpravo;
            while (uzel.vlevo != null) {
                uzel = uzel.vlevo;
            }
            return uzel;
        } else {
            // Pokud uzel nemá pravého potomka, hledáme prvního předka, jehož levý potomek je tento uzel
            Uzel rodic = uzel.rodic;
            while (rodic != null && uzel == rodic.vpravo) {
                uzel = rodic;
                rodic = uzel.rodic;
            }
            return rodic;
        }
    }

    private void odeberList(Uzel uzel) {
        if (uzel.rodic == null) {
            koren = null;
        } else if (jeLevymPotomkem(uzel)) {
            uzel.rodic.vlevo = null;
        } else {
            uzel.rodic.vpravo = null;
        }
    }

    private void odeberNaslednika(Uzel naslednik) {
        if (naslednik == naslednik.rodic.vlevo) {
            naslednik.rodic.vlevo = naslednik.vpravo;
        } else {
            naslednik.rodic.vpravo = naslednik.vpravo;
        }
        if (naslednik.vpravo != null) {
            naslednik.vpravo.rodic = naslednik.rodic;
        }
    }

    private void odeberJednohoPotomka(Uzel uzel) {
        Uzel potomek = (uzel.vlevo != null) ? uzel.vlevo : uzel.vpravo;
        if (uzel.rodic == null) {
            // Pokud je uzel kořenem stromu
            koren = potomek;
        } else if (uzel == uzel.rodic.vlevo) {
            uzel.rodic.vlevo = potomek;
        } else {
            uzel.rodic.vpravo = potomek;
        }
        if (potomek != null) {
            potomek.rodic = uzel.rodic;
        }
    }

    private void aktualizujMohutnostPoOdebrani(Uzel uzel) {
        while (uzel != null) {
            snizMohutnost(uzel); // Snížíme mohutnost všech předků uzlu o 1
            uzel = uzel.rodic; // Přesuneme se k rodiči
        }
    }

    private boolean jeListem(Uzel uzel) {
        return uzel.vlevo == null && uzel.vpravo == null;
    }

    private boolean jsouObaPotomky(Uzel uzel) {
        return uzel.vlevo != null && uzel.vpravo != null;
    }

    private boolean jeJedenPotomek(Uzel uzel) {
        return uzel.vlevo != null || uzel.vpravo != null;
    }

    private boolean jeKorenem(Uzel uzel) {
        return uzel.rodic == null;
    }

    private boolean jeLevymPotomkem(Uzel uzel) {
        return uzel == uzel.rodic.vlevo;
    }

    private boolean jePravymPotomkem(Uzel uzel) {
        return uzel == uzel.rodic.vpravo;
    }

    /**
     * Vytvoří nový iterátor pro průchod binárním stromem v zadaném režimu
     *
     * @param typ Způsob průchodu stromem - buď šířka nebo hloubka
     */
    @Override
    public Iterator<Uzel> vytvorIterator(ETypProhl typ) {
        return switch (typ) {
            case SIRKA -> new SirkaIterator();
            case HLOUBKA -> new HloubkaIterator();
        };
    }

    /**
     * Iterátor pro průchod stromem do šířky
     */
    private class SirkaIterator implements Iterator<Uzel> {

        private final AbstrFifo<Uzel> fronta;

        /**
         * Konstruktor vytvoří instanci iterátoru a inicializuje frontu, pokud strom není prázdný
         */
        public SirkaIterator() {
            fronta = new AbstrFifo<>();
            if (koren != null)
                fronta.vloz(koren);
        }

        /**
         * Určuje, zda existuje další prvek pro zpracování
         *
         * @return {@code true}, pokud existuje další prvek, jinak {@code false}
         */
        @Override
        public boolean hasNext() { return !fronta.jePrazdna(); }

        /**
         * Vrací další prvek v pořadí pro průchod stromem do šířky
         *
         * <p> Aktuální uzel (prvek) se nastaví na prvek odebraný z fronty. Tím se získává následující
         * prvek k procházení. Pak jsou kontroly, zda aktuální uzel má levého/pravého potomka.
         * Následně, pokud má aktuální uzel levého/pravého potomka, přidá levého/pravého potomka do
         * fronty pro další průchod
         *
         * @return Další prvek pro zpracování
         *
         * @throws NoSuchElementException Pokud není další prvek k dispozici
         */
        @Override
        public Uzel next() {
            if (!hasNext())
                throw new NoSuchElementException();
            try {
                Uzel aktualniUzel = fronta.odeber();
                if (aktualniUzel.vlevo != null)
                    fronta.vloz(aktualniUzel.vlevo);
                if (aktualniUzel.vpravo != null)
                    fronta.vloz(aktualniUzel.vpravo);
                return aktualniUzel;
            } catch (StrukturaException e) {
                throw new NoSuchElementException();
            }
        }
    }

    /**
     * Iterátor pro průchod stromem do hloubky (in-order)
     *
     * <p> <b>Depth-First Search (DFS)</b> je strategie pro procházení stromů a grafů, která se zaměřuje na
     * procházení do hloubky předtím, než se vrací zpět k dalším větvím
     *
     * <p> <b>In-order DFS</b> nejprve navštíví levý podstrom, poté kořenový uzel (prvek) a nakonec pravý podstrom
     */
    private class HloubkaIterator implements Iterator<Uzel> {

        private final AbstrLifo<Uzel> zasobnik;

        /**
         * Konstruktor vytvoří instanci iterátoru a inicializuje zásobník, pokud strom není prázdný
         */
        public HloubkaIterator() {
            zasobnik = new AbstrLifo<>();
            obnovZasobnik(koren);
        }

        /**
         * Inicializuje zásobník pro prohlížení stromu zleva tím, že přidává všechny uzly levého podstromu
         * do zásobníku.
         *
         * <p> Postupně vkládá uzly do zásobníku, začínaje zadaným kořenovým uzlem a postupujíc vlevo k
         * potomkům, dokud existují leví potomci
         *
         * @param uzel Prvek, od kterého začíná procházení stromu zleva
         */
        private void obnovZasobnik(Uzel uzel) {
            while (uzel != null) {
                zasobnik.vloz(uzel);
                uzel = uzel.vlevo;
            }
        }

        /**
         * Určuje, zda existuje další prvek pro zpracování
         *
         * @return {@code true}, pokud existuje další prvek, jinak {@code false}
         */
        @Override
        public boolean hasNext() { return !zasobnik.jePrazdny(); }

        /**
         * Vrací další prvek v pořadí pro průchod stromem do hloubky (in-order)
         *
         * <p> Provádí {@code in-order} procházení stromu tím, že nejprve přidává všechny uzly (prvky)
         * levého podstromu do zásobníku, poté postupně odebírá uzly z vrcholu zásobníku a přidává uzly
         * pravého podstromu, pokud existují. Tímto způsobem budou uzly (prvky) navštěvovány v pořadí
         * od nejmenšího po největší klíč. Tímto způsobem jsou uzly (prvky) navštěvovány v pořadí podle
         * jejich klíčů od nejmenšího po největší
         *
         * @return Další prvek pro zpracování
         *
         * @throws NoSuchElementException Pokud není další prvek k dispozici
         */
        @Override
        public Uzel next() {
            if (!hasNext())
                throw new NoSuchElementException(ChybovaZprava.KONEC_ITERACE.getZprava());
            try {
                final Uzel aktUzel = zasobnik.odeber();
                if (aktUzel.vpravo != null)
                    obnovZasobnik(aktUzel.vpravo);
                return aktUzel;
            } catch (StrukturaException e) {
                throw new NoSuchElementException(ChybovaZprava.PRAZDNY_ZASOBNIK.getZprava());
            }
        }
    }

    private void zvysMohutnost(Uzel uzel) { uzel.mohutnost++; }

    private void snizMohutnost(Uzel uzel) { uzel.mohutnost--; }

    /**
     * Porovná výsledek metody compareTo s nulou
     *
     * <p>{@link Comparable#compareTo(Object)} vratí nulu pokud aktuální instance {@code this} je "rovná" druhé instanci {@code other}
     *
     * @param obj1 První objekt pro porovnání
     * @param obj2 Druhý objekt pro porovnání
     *
     * @return {@code true}, pokud výsledek je roven nule, jinak {@code false}
     */
    private boolean jeNula(K obj1, K obj2) { return obj1.compareTo(obj2) == NULTA_HODNOTA; }

    /**
     * Porovná výsledek metody compareTo s kladným číslem
     *
     * <p>{@link Comparable#compareTo(Object)} vratí kladné číslo pokud aktuální instance {@code this} je "větší" než druhá instance {@code other}
     *
     * @param obj1 První objekt pro porovnání
     * @param obj2 Druhý objekt pro porovnání
     *
     * @return {@code true}, pokud výsledek je kladný, jinak {@code false}
     */
    private boolean jeKladne(K obj1, K obj2) { return obj1.compareTo(obj2) > NULTA_HODNOTA; }

    /**
     * Porovná výsledek metody compareTo s záporným číslem
     *
     * <p>{@link Comparable#compareTo(Object)} vratí záporné číslo pokud aktuální instance {@code this} je "menší" než druhá instance {@code other}
     *
     * @param obj1 První objekt pro porovnání
     * @param obj2 Druhý objekt pro porovnání
     * @return {@code true}, pokud výsledek je záporný, jinak {@code false}
     */
    private boolean jeZaporne(K obj1, K obj2) { return obj1.compareTo(obj2) < NULTA_HODNOTA; }

    /**
     * Ověřuje, zda kořen stromu není prázdný {@code null}
     *
     * @throws StromException Pokud je kořen prázdný
     */
    private void pozadatNePrazdnyKoren() throws StromException {
        if (koren == null)
            throw new StromException(ChybovaZprava.PRAZDNY_KOREN.getZprava());
    }

    /**
     * Ověřuje, zda zadaný klíč není prázdný {@code null}
     *
     * @param klic Vstupní klíč ověřující se na prázdnost
     *
     * @throws StromException Pokud je klíč prázdný
     */
    private void pozadatNePrazdnyKlic(K klic) throws StromException {
        if (klic == null)
            throw new StromException(ChybovaZprava.NULL_KLIC.getZprava());
    }
}
