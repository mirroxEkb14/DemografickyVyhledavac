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
        Uzel vlevo;
        Uzel vpravo;
        Uzel rodic;
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
     * Popis logiky:
     * <ol>
     * <li> Pokud je strom prázdný:
     *      <ol>
     *      <li> Vyvolá výjimku {@link StromException}
     *      </ol>
     * <li> Zavolá rekurzivně metodu pro hledání uzlu (prvku)
     * <li> Pokud uzel (prvek) není nalezen:
     *      <ol>
     *      <li> Vyvolá výjimku {@link StromException}
     *      </ol>
     * <li> Vrací hodnotu nalezeného uzlu (prvku)
     * </ol>
     */
    @Override
    public V najdi(K klic) throws StromException {
        if (koren == null)
            throw new StromException();

        final Uzel uzel = najdiRekurzivne(koren, klic);
        if (uzel == null)
            throw new StromException();
        return uzel.hodnota;
    }

    /**
     * Privátní pomocní metoda pro {@link AbstrTable#najdi(Comparable)}
     *
     * <p>Popis logiky:
     * <ol>
     * <li> Pokud uzel (prvek) je {@code null}:
     *      <ol>
     *      <li> Vrací {@code null}, protože hledaný uzel (prvek) nebyl nalezen
     *      </ol>
     * <li> Porovnává klíče aktuálního uzlu (prvku) a hledaného klíče pomocí metod
     * jednotlivých privátních metod pro zjišťování ekvivalence kličů
     * </ol>
     *
     * @see AbstrTable#jeNula(Comparable, Comparable)
     * @see AbstrTable#jeZaporne(Comparable, Comparable)
     * @see AbstrTable#jeKladne(Comparable, Comparable)
     */
    private Uzel najdiRekurzivne(Uzel uzel, K klic) {
        if (uzel == null)
            return null;

        if (jeNula(klic, uzel.klic))
            return uzel;
        if (jeZaporne(klic, uzel.klic))
            return najdiRekurzivne(uzel.vlevo, klic);
        if (jeKladne(klic, uzel.klic))
            return najdiRekurzivne(uzel.vpravo, klic);

        return null;
    }

    /**
     * Metoda najde vhodné místo pro vložení nového prvku do stromu na základě porovnání klíčů
     *
     * <p>Popis logiky:
     * <ol>
     * <li> kontroluje, zda je kořen stromu {@code koren} {@code null}. Pokud je:
     *      <ol>
     *      <li> Vytvoří nový uzel jako kořen stromu, reprezentující zadaný prvek
     *      </ol>
     * <li> Pokud kořen stromu není {@code null}:
     *      <ol>
     *      <li> Volá privátní rekurzivní metodu {@link AbstrTable#vlozRekurzivne(Comparable, Object, Uzel)}
     *      s klíčem, hodnotou a kořenem stromu
     *      </ol>
     * <li> Zvyšuje velikost stromu o hodnotu {@code ZVETSOVAC_VELIKOSTI}
     * </ol>
     */
    @Override
    public void vloz(K klic, V hodnota) {
        if (koren == null)
            koren = new Uzel(klic, hodnota, null);
         else
            vlozRekurzivne(klic, hodnota, koren);
        koren.mohutnost += ZVETSOVAC_MOHUTNOSTI;
    }

    /**
     * Privátní pomocní metoda pro {@link AbstrTable#vloz(Comparable, Object)}}
     *
     * <p> Projde strom až dojde k místu, kde má být nový prvek vložen. Pokud v aktuálním uzlu ještě
     * neexistuje levý nebo pravý podstrom, vytvoří nový uzel reprezentující nový prvek. V opačném
     * případě pokračuje v rekurzivním volání na levém nebo pravém podstromu, dokud nenajde místo pro
     * vložení nového prvku
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Porovnává zadaný klíč s klíčem aktuálního uzlu {@code aktualniUzel}. Pokud je klíč menší,
     * než klíč aktuálního uzlu:
     *      <ol>
     *      <li> Volá se tato metoda rekurzivně na levém podstromu aktuálního uzlu
     *      </ol>
     * <li> Pokud je klíč větší:
     *      <ol>
     *      <li> Volá se tato metoda rekurzivně na pravém podstromu aktuálního uzlu
     *      </ol>
     * </ol>
     *
     * @param klic Klíč, který se má vložit
     * @param hodnota Hodnota, která se má vložit
     * @param aktualniUzel Aktuální uzel, ve kterém se hledá místo pro vložení nového uzlu (prvku)
     */
    private void vlozRekurzivne(K klic, V hodnota, Uzel aktualniUzel) {
        if (jeKladne(aktualniUzel.klic, klic)) {
            if (aktualniUzel.vlevo == null)
                aktualniUzel.vlevo = new Uzel(klic, hodnota, aktualniUzel);
             else
                vlozRekurzivne(klic, hodnota, aktualniUzel.vlevo);
        } else {
            if (aktualniUzel.vpravo == null)
                aktualniUzel.vpravo = new Uzel(klic, hodnota, aktualniUzel);
             else
                vlozRekurzivne(klic, hodnota, aktualniUzel.vpravo);
        }
        zvysMohutnost(aktualniUzel);
    }


// <editor-fold defaultstate="collapsed" desc="Zakomentovaná implementace metody: vloz(K klic, V hodnota)">
//    /**
//     * Popis logiky:
//     * <ol>
//     * <li> Pokud klíč korenu je větší než zadaný klíč, pokusí se vložit do levého podstromu:
//     *      <ol>
//     *      <li> Pokud levý podstrom neexistuje:
//     *          <ol>
//     *          <li> Vytvoří nový uzel (prvek) s daným klíčem a hodnotou
//     *          </ol>
//     *      <li> Jinak:
//     *          <ol>
//     *          <li> Zavolá metodu rekurzivně na levém podstromu
//     *          </ol>
//     *      </ol>
//     * <li> Pokud klíč korenu je menší než nebo roven zadanému klíči, pokusí se vložit do pravého
//     * podstromu:
//     *      <ol>
//     *      <li> Pokud pravý podstrom neexistuje:
//     *          <ol>
//     *          <li> Vytvoří nový uzel (prvek) s daným klíčem a hodnotou
//     *          </ol>
//     *      <li> Jinak:
//     *          <ol>
//     *          <li> Zavolá metodu rekurzivně na pravém podstromu
//     *          </ol>
//     *      </ol>
//     * </ol>
//     */
//    @Override
//    public void vloz(K klic, V hodnota) {
//        if (jeKladne(koren.klic, klic)) {
//            if (koren.vlevo == null) {
//                koren.vlevo = new Uzel(klic, hodnota);
//                koren.velikost += ZVETSOVAC_VELIKOSTI;
//            } else
//                vloz(klic, koren.vlevo.hodnota);
//        } else {
//            if (koren.vpravo == null) {
//                koren.vpravo = new Uzel(klic, hodnota);
//                koren.velikost += ZVETSOVAC_VELIKOSTI;
//            } else
//                vloz(klic, koren.vpravo.hodnota);
//        }
//    }
// </editor-fold>

    @Override
    public V odeber(K klic) throws StromException {
        return null;
    }

// <editor-fold defaultstate="collapsed" desc="Zakomentovaná implementace metody: odeber(K klic)">
//    /**
//     * Popis logiky:
//     * <ol>
//     * <li> Volá privátní rekurzivní metoda {@link AbstrTable#odeberRekurzivne(Uzel, Comparable)} s
//     * kořenem stromu a zadaným klíčem. Vrátí uzel, který reprezentuje odebraný prvek, nebo {@code null},
//     * pokud prvek nebyl nalezen.
//     * <li> Pokud je vrácen {@code null}:
//     *      <ol>
//     *      <li> Vyvolá výjimku `KolekceException` indikující, že prvek nebyl nalezen
//     *      </ol>
//     * <li> Vrátí hodnotu tohoto uzlu pokud je vrácen uzel reprezentující odebraný prvek
//     * </ol>
//     */
//    @Override
//    public V odeber(K klic) throws StromException {
//        final Uzel odebranyUzel = odeberRekurzivne(koren, klic);
//        if (odebranyUzel == null)
//            throw new StromException();
//        return odebranyUzel.hodnota;
//    }
//    /**
//     * Popis logiky:
//     * <ol>
//     * <li> Pokud aktuální uzel je {@code null}:
//     *      <ol>
//     *      <li> Vrátí {@code null}, což znamená, že prvek s daným klíčem nebyl nalezen
//     *      </ol>
//     * <li> Jinak porovnává zadaný klíč s klíčem aktuálního uzlu. Pokud je klíč menší než klíč
//     * aktuálního uzlu:
//     *      <ol>
//     *      <li> Pokračuje v hledání v levém podstromu rekurzivním voláním této metody
//     *      </ol>
//     * <li> Pokud je klíč větší než klíč aktuálního uzlu:
//     *      <ol>
//     *      <li> Pokračuje v hledání v pravém podstromu rekurzivním voláním této metody
//     *      </ol>
//     * <li> Pokud se klíče rovnají, provádí se odebrání prvku:
//     *      <ol>
//     *      <li> Pokud aktuální uzel nemá pravý podstrom:
//     *          <ol>
//     *          <li> Vrátí levý podstrom (odebere aktuální uzel)
//     *          </ol>
//     *      <li> Pokud aktuální uzel nemá levý podstrom:
//     *          <ol>
//     *          <li> Vrátí pravý podstrom (odebere aktuální uzel)
//     *          </ol>
//     *      <li> Jinak:
//     *          <ol>
//     *          <li> <b>následující 2 řádky</b>: Najde nejmenší uzel (prvek) v pravém podstromu pomocí {@link AbstrTable#najdiMin(Uzel)}
//     *          <li> <b>následující 2 řádky</b>: Nahradí aktuální uzel tímto nejmenším uzlem
//     *          </ol>
//     *      </ol>
//     * <li> Aktualizuje velikost aktuálního uzlu jako součet velikostí jeho levého a pravého podstromu plus jedna
//     * </ol>
//     */
//    private Uzel odeberRekurzivne(Uzel uzel, K klic) {
//        if (uzel == null) return null;
//
//        if (jeZaporne(klic, uzel.klic))
//            uzel.vlevo  = odeberRekurzivne(uzel.vlevo,  klic);
//        else if (jeKladne(klic, uzel.klic))
//            uzel.vpravo = odeberRekurzivne(uzel.vpravo, klic);
//        else {
//            if (uzel.vpravo == null) return uzel.vlevo;
//            if (uzel.vlevo  == null) return uzel.vpravo;
//            final Uzel docasnyUzel = uzel;
//            uzel = najdiMin(docasnyUzel.vpravo);
//            uzel.vpravo = odeberMin(docasnyUzel.vpravo);
//            uzel.vlevo = docasnyUzel.vlevo;
//        }
//        uzel.hmotnost = ZVETSOVAC_HMOTNOSTI + hmotnost(uzel.vlevo) + hmotnost(uzel.vpravo);
//        return uzel;
//    }
//
//    /**
//     * Privátní pomocní metoda pro {@link AbstrTable#odeber(Comparable)}
//     *
//     * <p>Najde nejmenší uzel (prvek) v daném podstromu
//     *
//     * @param podstrom Aktuální uzel (prvek), ze kterého metoda začne hledat
//     *
//     * @return Nejmenší uzel (prvek) v podstromu
//     */
//    private Uzel najdiMin(Uzel podstrom) {
//        if (podstrom.vlevo == null)
//            return podstrom;
//        return najdiMin(podstrom.vlevo);
//    }
//
//    /**
//     * Privátní pomocní metoda pro {@link AbstrTable#odeber(Comparable)}
//     *
//     * <p> Popis logiky:
//     * <ol>
//     * <li> Pokud daný uzel nemá levý podstrom:
//     *      <ol>
//     *      <li> Vrátí pravý podstrom
//     *      </ol>
//     * <li> Pokud má levý podstrom:
//     *      <ol>
//     *      <li> Rekurzivně pokračuje v hledání nejmenšího uzlu v levém podstromu
//     *      <li> Aktualizuje velikost uzlu jako součet velikostí jeho levého a pravého podstromu
//     *      plus jedna
//     *      </ol>
//     * </ol>
//     *
//     * @param podstrom Aktuální uzel (prvek), ve kterém se hledá nejmenší prvek
//     *
//     * @return Podstrom s odebraným nejmenším uzlem (prvkem)
//     */
//    private Uzel odeberMin(Uzel podstrom) {
//        if (podstrom.vlevo == null) return podstrom.vpravo;
//        podstrom.vlevo = odeberMin(podstrom.vlevo);
//        podstrom.hmotnost = ZVETSOVAC_HMOTNOSTI + hmotnost(podstrom.vlevo) + hmotnost(podstrom.vpravo);
//        return podstrom;
//    }
//
//    /**
//     * Vrací hmotnost (počet prvků) uzlu včetně jeho podstromů
//     *
//     * @param uzel Uzel stromu, pro který chcete zjistit hmotnost
//     *
//     * @return Hmotnost uzlu, což je počet prvků v podstromu daného uzlu
//     */
//    private int hmotnost(Uzel uzel) { return uzel == null ? NULTA_HODNOTA : uzel.hmotnost; }

//    public V odeber(K klic) throws StromException {
//        AbstrLifo<Uzel> zasobnik = new AbstrLifo<>();
//        Uzel aktualniUzel = koren;
//        Uzel predchoziUzel = null;
//
//        while (aktualniUzel != null || !zasobnik.jePrazdny()) {
//            if (aktualniUzel != null) {
//                zasobnik.vloz(aktualniUzel);
//                predchoziUzel = aktualniUzel;
//                int porovnani = klic.compareTo(aktualniUzel.klic);
//                if (porovnani == 0) {
//                    if (predchoziUzel == null) {
//                        koren = null;
//                    } else if (predchoziUzel.vlevo == aktualniUzel) {
//                        predchoziUzel.vlevo = null;
//                    } else {
//                        predchoziUzel.vpravo = null;
//                    }
//                    koren.mohutnost -= ZVETSOVAC_MOHUTNOSTI;
//                    return aktualniUzel.hodnota;
//                } else if (porovnani < 0) {
//                    aktualniUzel = aktualniUzel.vlevo;
//                } else {
//                    aktualniUzel = aktualniUzel.vpravo;
//                }
//            } else {
//                try {
//                    aktualniUzel = zasobnik.odeber();
//                } catch (StrukturaException e) {
//                    throw new StromException();
//                }
//            }
//        }
//        throw new StromException();
//    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Zakomentovaná implementace metody: vypisStrom()">
    public String vypisStrom() throws StrukturaException {
        if (jePrazdny()) {
            return "Strom je prázdný.";
        }

        StringBuilder stromText = new StringBuilder();
        AbstrLifo<Uzel> zasobnik = new AbstrLifo<>();
        Uzel aktUzel = koren;

        while (aktUzel != null || !zasobnik.jePrazdny()) {
            while (aktUzel != null) {
                zasobnik.vloz(aktUzel);
                aktUzel = aktUzel.vlevo;
            }

            aktUzel = zasobnik.odeber();
            String vztah = "kořen";
            if (aktUzel != koren) {
                if (aktUzel.klic.compareTo(aktUzel.rodic.klic) < 0) {
                    vztah = "je vlevo od " + aktUzel.rodic.klic;
                } else {
                    vztah = "je vpravo od " + aktUzel.rodic.klic;
                }
            }
            stromText.append(aktUzel.klic).append(" (").append(vztah).append(")").append(System.lineSeparator());

            aktUzel = aktUzel.vpravo;
        }

        return stromText.toString();
    }

//    /**
//     * Vytvoří textový popis stromu, který zobrazuje jeho strukturu a vztahy mezi uzly
//     *
//     * <p> Kód demonstrující vložení několika prvků do stromu, jejich odebrání a výpis struktury stromu:
//     * <code>
//     *     <pre>
//     *     final AbstrTable&lt;Integer, String&gt; strom = new AbstrTable&lt;&gt;();
//     *     strom.vloz(5, "A");
//     *     strom.vloz(3, "B");
//     *     strom.vloz(7, "C");
//     *     strom.vloz(2, "D");
//     *     strom.vloz(4, "E");
//     *     strom.vloz(6, "F");
//     *     strom.vloz(8, "G");
//     *
//     *     System.out.println("Strom před odebráním:");
//     *     System.out.println(strom.vypisStrom());
//     *
//     *     try {
//     *         strom.odeber(3); // Odebrání uzlu s klíčem 3
//     *         System.out.println("Strom po odebrání uzlu s klíčem 3:");
//     *         System.out.println(strom.vypisStrom());
//     *     } catch (StromException e) {
//     *         System.err.println("Chyba při odebírání uzlu: Uzel nebyl nalezen.");
//     *     }
//     *     </pre>
//     * </code>
//     * @return Textový popis stromu jako řetězec
//     */
//    public String vypisStrom() { return vypisStromRekurzivne(koren, null); }
//
//    /**
//     * Vytvoří textový popis stromu rekurzivně, který zobrazuje jeho strukturu a vztahy mezi uzly
//     *
//     * @param uzel   Aktuální uzel, který se má popsat
//     * @param rodic  Rodič aktuálního uzlu
//     *
//     * @return Textový popis aktuálního uzlu a jeho potomků jako řetězec
//     */
//    private String vypisStromRekurzivne(Uzel uzel, Uzel rodic) {
//        if (uzel == null)
//            return "";
//
//        String popisUzlu = uzel.klic.toString();
//        if (rodic != null) {
//            if (uzel == rodic.vlevo) {
//                popisUzlu += " je vlevo od " + rodic.klic;
//            } else {
//                popisUzlu += " je vpravo od " + rodic.klic;
//            }
//        } else popisUzlu += " (kořen)";
//        popisUzlu += "\n";
//        return popisUzlu +
//                vypisStromRekurzivne(uzel.vlevo, uzel) +
//                vypisStromRekurzivne(uzel.vpravo, uzel);
//    }
// </editor-fold>


    /**
     * Vytvoří nový iterátor pro průchod binárním stromem v zadaném režimu
     *
     * @param typ Způsob průchodu stromem - buď šířka nebo hloubka
     */
    @Override
    public Iterator<K> vytvorIterator(ETypProhl typ) {
        if (typ == ETypProhl.SIRKA)
            return new SirkaIterator();
        if (typ == ETypProhl.HLOUBKA)
            return new HloubkaIterator();
        return null;
    }

    /**
     * Iterátor pro průchod stromem do šířky
     */
    private class SirkaIterator implements Iterator<K> {

        private AbstrFifo<Uzel> fronta;
        private Uzel aktualniUzel;

        /**
         * Konstruktor vytvoří instanci iterátoru a inicializuje frontu, pokud strom není prázdný
         */
        public SirkaIterator() {
            if (koren != null) {
                fronta = new AbstrFifo<>();
                fronta.vloz(koren);
            }
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
        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();
            try {
                aktualniUzel = fronta.odeber();
                if (aktualniUzel.vlevo != null)
                    fronta.vloz(aktualniUzel.vlevo);
                if (aktualniUzel.vpravo != null)
                    fronta.vloz(aktualniUzel.vpravo);
                return aktualniUzel.klic;
            } catch (StrukturaException e) {
                throw new NoSuchElementException();
            }
        }
    }

    /**
     * Iterátor pro průchod stromem do hloubky
     */
    private class HloubkaIterator implements Iterator<K> {

        private AbstrLifo<Uzel> zasobnik;
        private Uzel aktualniUzel;

        /**
         * Konstruktor vytvoří instanci iterátoru a inicializuje zásobník, pokud strom není prázdný
         */
        public HloubkaIterator() {
            if (koren != null) {
                zasobnik = new AbstrLifo<>();
                zasobnik.vloz(koren);
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
         * Vrací další prvek v pořadí pro průchod stromem do hloubky
         *
         * <p> Aktuální uzel (prvek) se nastaví na prvek odebraný ze zásobníku. Tím se získává následující
         * prvek k procházení. Pak jsou kontroly, zda aktuální uzel má levého/pravého potomka.
         * Následně, pokud má aktuální uzel levého/pravého potomka, přidá levého/pravého potomka do
         * zásobníku pro další průchod
         *
         * @return Další prvek pro zpracování
         *
         * @throws NoSuchElementException Pokud není další prvek k dispozici
         */
        @Override
        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();
            try {
                aktualniUzel = zasobnik.odeber();
                if (aktualniUzel.vlevo != null)
                    zasobnik.vloz(aktualniUzel.vlevo);
                if (aktualniUzel.vpravo != null)
                    zasobnik.vloz(aktualniUzel.vpravo);
                return aktualniUzel.klic;
            } catch (StrukturaException e) {
                throw new NoSuchElementException();
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
}
