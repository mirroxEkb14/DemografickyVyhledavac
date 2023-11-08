package cz.upce.fei.bdast.strom;

import cz.upce.fei.bdast.struktury.AbstrFifo;
import cz.upce.fei.bdast.struktury.AbstrLifo;
import cz.upce.fei.bdast.struktury.StrukturaException;
import org.jetbrains.annotations.NotNull;

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
     * Popis logiky:
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

        final VnitrniHloubkaIterator iterator = new VnitrniHloubkaIterator();
        while (iterator.hasNext()) {
            final Uzel aktUzel = iterator.next();
            if (jeNula(aktUzel.klic, klic))
                return aktUzel.hodnota;
        }
        throw new StromException(ChybovaZpravaStromu.PRVEK_NENALEZEN.getZprava());
    }

    /**
     * Popis logiky:
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
        final VnitrniHloubkaIterator iterator = new VnitrniHloubkaIterator();
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
     * Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Provádí počáteční ověření vstupního klíče {@code klic} a existence kořene stromu
     *      <ul>
     *      <li> <b>pozadatNePrazdnyKlic()</b>
     *      <li> <b>pozadatNePrazdnyKoren()</b>
     *      </ul>
     * <li> Hledá {@code uzel} s odpovídajícím klíčem {@code klic} a zkontroluje ho na {@code null}
     *      <ul>
     *      <li> <b>najdiUzel()</b>
     *      <li> <b>if (uzel == null)</b>
     *      </ul>
     * <li> Připraví proměnnou {@code odebranaHodnota} pro následný návrat. Pak ověří existence obou
     * potomků u uzlu {@code uzel}
     *      <ul>
     *      <li> <b>final V odebranaHodnota = uzel.hodnota</b>
     *      <li> <b>if (jsouObaPotomky(uzel))</b>: Zjišťuje následníka uzlu, tj. uzel s nejnižším
     *      klíčem v pravém podstromu, pak klíč a hodnota následníka jsou přesunuty na aktuální uzel
     *      {@code uzel}, čímž odebere aktuální uzel a přesune se k následníkovi
     *      </ul>
     * <li> Zpracovává případy, kdy uzel má buď levého nebo pravého potomka, nebo je listem
     *      <ul>
     *      <li> <b>if (jeLevyPotomek(uzel))</b>: {@code uzel} (minule následník uzlu) má levého potomka,
     *      a proto tento {@code uzel} bude nahrazen jeho levým potomkem
     *      <li> <b>else if (jePravyPotomek(uzel))</b>: {@code uzel} má pravého potomka, a proto provede
     *      obdobnou operaci s pravým potomkem
     *      <li> <b>else if (jeListem(uzel))</b> {@code uzel} je listem, a proto odstraní ten samý {@code uzel}
     *      </ul>
     * <li> Aktualizuje mohutnost uzlů po odebrání
     *      <ul>
     *      <li> <b>aktualizujMohutnostPoOdebirani(uzel)</b>: Postupně prochází uzly na cestě od odebraného
     *      uzlu ke kořeni a snižuje jejich mohutnost o jedničku, čímž udržuje konzistenci mohutnosti v rámci
     *      stromu
     *      <li> <b>return odebranaHodnota</b>
     *      </ul>
     * </ol>
     */
    @Override
    public V odeber(K klic) throws StromException {
        pozadatNePrazdnyKlic(klic);
        pozadatNePrazdnyKoren();

        Uzel uzel = najdiUzel(klic);
        if (uzel == null)
            throw new StromException(ChybovaZpravaStromu.PRVEK_NENALEZEN.getZprava());

        final V odebranaHodnota = uzel.hodnota;
        if (jsouObaPotomky(uzel)) {
            final Uzel naslednik = najdiNaslednika(uzel);
            uzel.klic = naslednik.klic;
            uzel.hodnota = naslednik.hodnota;
            uzel = naslednik;
        }

        if (jeLevyPotomek(uzel)) {
            odeberUzelSJednimPotomkem(uzel, uzel.vlevo);
        } else if (jePravyPotomek(uzel)) {
            odeberUzelSJednimPotomkem(uzel, uzel.vpravo);
        } else if (jeListem(uzel)) {
            odeberUzelBezPotomku(uzel);
        }

        aktualizujMohutnostPoOdebirani(uzel);
        return odebranaHodnota;
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#odeber(Comparable)}
     *
     * <p> Popis logiky:
     * <ol>
     * <li> <b>iterator.hasNext()</b>: Začíná iterací od kořene stromu a postupně prochází uzly stromu hloubkově
     * (in-order) - nejdříve prochází levé podstromy, pak uzly a nakonec pravé podstromy
     * <li> <b>jeNula()</b>: Pokud nalezne uzel se shodným klíčem, vrátí tento uzel - {@code uzel}
     * <li> <b>return null</b>: Pokud projde celý strom a nenajde uzel shodný s cílovým klíčem, vrátí {@code null}
     * (tj. hledaný uzel v stromu neexistuje)
     * </ol>
     */
    private Uzel najdiUzel(K klic) {
        final VnitrniHloubkaIterator iterator = new VnitrniHloubkaIterator();
        Uzel uzel;
        while (iterator.hasNext()) {
            uzel = iterator.next();
            if (jeNula(uzel.klic, klic))
                return uzel;
        }
        return null;
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#odeber(Comparable)}
     *
     * <p> Popis logiky:
     * <ol>
     * <li> <b>maPravehoPotomka(uzel)</b>: Pokud uzel má pravého potomka, najde nejlevější uzel v rámci tohoto
     * pravého podstromu (tj. najde první uzel v {@code in-order} následování)
     * <li> <b>while()</b>: Pokud uzel nemá pravého potomka, hledá prvního předka, jehož levý potomek je tento uzel.
     * To znamená, že se metoda vrátí k rodiči uzlu a postupně posune vzhůru po stromě a hledá rodiče, kteří ještě
     * nemají tento uzel jako pravého potomka. Jakmile najde takového rodiče nebo dojde k samotnému kořeni stromu,
     * vrátí tento uzel jako následníka (např. samotný kořen)
     * </ol>
     */
    private Uzel najdiNaslednika(Uzel uzel) {
        if (jePravyPotomek(uzel)) {
            uzel = uzel.vpravo;
            while (uzel.vlevo != null)
                uzel = uzel.vlevo;
            return uzel;
        }
        Uzel rodic = uzel.rodic;
        while (rodic != null && uzel == rodic.vpravo) {
            uzel = rodic;
            rodic = uzel.rodic;
        }
        return rodic;
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#odeber(Comparable)}
     *
     * <p> Slouží k odstranění uzlu, který má právě jednoho potomka, a následné připojení tohoto potomka na místo
     * odebraného uzlu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> <b>jeKorenem()</b>: Aktualizuje kořen, čímž se potomek stane novým kořenem stromu
     * <li> <b>jeLevymPotomkem()</b>: Připojí potomka na místo odebraného uzlu (tj. na levý potomek svého rodiče),
     * což znamená, že potomek se stane novým levým potomkem rodiče odebraného uzlu
     * <li> <b>jePravymPotomkem()</b>: Potomek se připojí na místo odebraného uzlu (tj. potomek se stane novým
     * pravým potomkem rodiče odebraného uzlu)
     * <li> <b>potomek.rodic = uzel.rodic</b>: Aktualizuje rodiče potomka tak, aby ukazoval na rodiče odebraného
     * uzlu
     * </ol>
     *
     * @param uzel Prvek, který má být odebrán
     * @param potomek Prvek, který bude připojen na místo odebraného uzlu
     */
    private void odeberUzelSJednimPotomkem(Uzel uzel, Uzel potomek) {
        if (jeKorenem(uzel))
            koren = potomek;
        else if (jeLevymPotomkem(uzel))
            uzel.rodic.vlevo = potomek;
        else if (jePravymPotomkem(uzel))
            uzel.rodic.vpravo = potomek;
        potomek.rodic = uzel.rodic;
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#odeber(Comparable)}
     *
     * <p> Popis logiky:
     * <ol>
     * <li> <b>jeKorenem()</b>: Nastaví kořen stromu na {@code null}, protože odebraný uzel je kořenem stromu.
     * Celý strom je tedy prázdný
     * <li> <b>jeLevymPotomkem()</b>: Nastaví levý potomek rodiče na {@code null}, protože odebraný uzel je
     * levým potomkem svého rodiče
     * <li> <b>jePravymPotomkem()</b>: Nastaví pravý potomek rodiče na {@code null}, protože odebraný uzel je
     * pravým potomkem svého rodiče
     * </ol>
     *
     * @param uzel Prvek, který má být odebrán
     */
    private void odeberUzelBezPotomku(Uzel uzel) {
        if (jeKorenem(uzel))
            koren = null;
        else if (jeLevymPotomkem(uzel))
            uzel.rodic.vlevo = null;
        else if (jePravymPotomkem(uzel))
            uzel.rodic.vpravo = null;
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#odeber(Comparable)}
     *
     * <p> Popis logiky:
     * <ol>
     * <li> <b>snizMohutnost()</b>: Sníží mohutnost aktuálního uzlu uzel o jedničku
     * <li> <b>uzel = uzel.rodic</b>: Přesune se k rodiči aktuálního uzlu tím, že {@code uzel} se nastaví na
     * svého rodiče a pokračuje, dokud se nedostane až ke kořeni stromu (nebo dokud {@code uzel} není {@code null}),
     * čímž zajišťuje procházení všech uzlů na cestě od odebraného uzlu ke kořeni a snižuje jejich mohutnost
     * </ol>
     *
     * @param uzel Prvek, který byl odebrán
     */
    private void aktualizujMohutnostPoOdebirani(Uzel uzel) {
        while (uzel != null) {
            snizMohutnost(uzel);
            uzel = uzel.rodic;
        }
    }

// <editor-fold defaultstate="collapsed" desc="Pomocné zjišťovací metody typu Boolean pro: V odeber(K klic)">
    private boolean jsouObaPotomky(@NotNull Uzel uzel) {
        return uzel.vlevo != null && uzel.vpravo != null;
    }

    private boolean jePravyPotomek(@NotNull Uzel uzel) {
        return uzel.vpravo != null;
    }

    private boolean jeLevyPotomek(@NotNull Uzel uzel) {
        return uzel.vlevo != null;
    }

    private boolean jeKorenem(@NotNull Uzel uzel) {
        return uzel.rodic == null;
    }

    private boolean jeListem(@NotNull Uzel uzel) {
        return uzel.vlevo == null && uzel.vpravo == null;
    }

    private boolean jeLevymPotomkem(@NotNull Uzel uzel) {
        return uzel == uzel.rodic.vlevo;
    }

    private boolean jePravymPotomkem(@NotNull Uzel uzel) {
        return uzel == uzel.rodic.vpravo;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Pomocné metody typu Void pro změnu mohutnosti uzlů">
    private void zvysMohutnost(Uzel uzel) { uzel.mohutnost++; }

    private void snizMohutnost(Uzel uzel) { uzel.mohutnost--; }
// </editor-fold>

    /**
     * Vytvoří nový iterátor pro průchod binárním stromem v zadaném režimu
     *
     * @param typ Způsob průchodu stromem - buď šířka nebo hloubka
     */
    @Override
    public Iterator<V> vytvorIterator(ETypProhl typ) {
        return switch (typ) {
            case SIRKA -> new SirkaIterator();
            case HLOUBKA -> new HloubkaIterator();
        };
    }

    /**
     * Iterátor pro průchod stromem do šířky
     */
    private class VnitrniSirkaIterator implements Iterator<Uzel> {

        private final AbstrFifo<Uzel> fronta;

        /**
         * Konstruktor vytvoří instanci iterátoru a inicializuje frontu, pokud strom není prázdný
         */
        public VnitrniSirkaIterator() {
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
     * Iterátor pro vracení hodnot stromu zvenčí
     */
    private class SirkaIterator implements Iterator<V> {

        private final AbstrFifo<Uzel> fronta;

        public SirkaIterator() {
            fronta = new AbstrFifo<>();
            if (koren != null)
                fronta.vloz(koren);
        }

        @Override
        public boolean hasNext() { return !fronta.jePrazdna(); }

        @Override
        public V next() {
            if (!hasNext())
                throw new NoSuchElementException();
            try {
                Uzel aktualniUzel = fronta.odeber();
                if (aktualniUzel.vlevo != null)
                    fronta.vloz(aktualniUzel.vlevo);
                if (aktualniUzel.vpravo != null)
                    fronta.vloz(aktualniUzel.vpravo);
                return aktualniUzel.hodnota;
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
    private class VnitrniHloubkaIterator implements Iterator<Uzel> {

        private final AbstrLifo<Uzel> zasobnik;

        /**
         * Konstruktor vytvoří instanci iterátoru a inicializuje zásobník, pokud strom není prázdný
         */
        public VnitrniHloubkaIterator() {
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
                throw new NoSuchElementException(ChybovaZpravaStromu.KONEC_ITERACE.getZprava());
            try {
                final Uzel aktUzel = zasobnik.odeber();
                if (aktUzel.vpravo != null)
                    obnovZasobnik(aktUzel.vpravo);
                return aktUzel;
            } catch (StrukturaException e) {
                throw new NoSuchElementException(ChybovaZpravaStromu.PRAZDNY_ZASOBNIK.getZprava());
            }
        }
    }

    /**
     * Iterátor pro vracení hodnot stromu zvenčí
     */
    private class HloubkaIterator implements Iterator<V> {

        private final AbstrLifo<Uzel> zasobnik;

        public HloubkaIterator() {
            zasobnik = new AbstrLifo<>();
            obnovZasobnik(koren);
        }

        private void obnovZasobnik(Uzel uzel) {
            while (uzel != null) {
                zasobnik.vloz(uzel);
                uzel = uzel.vlevo;
            }
        }

        @Override
        public boolean hasNext() { return !zasobnik.jePrazdny(); }

        @Override
        public V next() {
            if (!hasNext())
                throw new NoSuchElementException(ChybovaZpravaStromu.KONEC_ITERACE.getZprava());
            try {
                final Uzel aktUzel = zasobnik.odeber();
                if (aktUzel.vpravo != null)
                    obnovZasobnik(aktUzel.vpravo);
                return aktUzel.hodnota;
            } catch (StrukturaException e) {
                throw new NoSuchElementException(ChybovaZpravaStromu.PRAZDNY_ZASOBNIK.getZprava());
            }
        }
    }

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
            throw new StromException(ChybovaZpravaStromu.PRAZDNY_KOREN.getZprava());
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
            throw new StromException(ChybovaZpravaStromu.NULL_KLIC.getZprava());
    }
}
