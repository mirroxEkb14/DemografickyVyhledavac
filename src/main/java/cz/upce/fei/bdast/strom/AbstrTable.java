package cz.upce.fei.bdast.strom;

import cz.upce.fei.bdast.struktury.*;
import cz.upce.fei.bdast.vyjimky.StromException;
import cz.upce.fei.bdast.vyjimky.StrukturaException;
import cz.upce.fei.bdast.vyjimky.zpravy.ChybovaZpravaStromu;
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
     * Konstanta reprezentuje hodnotu mohutnosti v případě, když není nalezen prvek v rámci stromu. Používá se
     * zejména u metody {@link AbstrTable#dejMohutnost(Comparable)}
     */
    private final int UKAZATEL_ABSENCE = -1;

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
            mohutnost = NULTA_HODNOTA;
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
     * <li> Zajišťuje, že vstupní klíč a strom (koren) nejsou prázdné
     *     <ul>
     *     <li> <b>pozadatNePrazdnyKlic(klic)</b>
     *     <li> <b>pozadatNePrazdnyKoren()</b>
     *     </ul>
     * <li> Rekurzivně vyhledává hodnoty v stromu až nenajde uzel, který obsahuje hledanou hodnotu, následně
     * vrátí hodnotu tohoto uzlu. Pokud ale je nalezený uzel {@code null}, vyvolá výjimku
     *     <ul>
     *     <li> <b>final Uzel uzel = najdiRekurzivne(koren, klic)</b>
     *     <li> <b>if (uzel == null)</b>
     *     <li> <b>return uzel.hodnota</b>
     *     </ul>
     * </ol>
     */
    @Override
    public V najdi(K klic) throws StromException {
        pozadatNePrazdnyKlic(klic);
        pozadatNePrazdnyKoren();

        final Uzel uzel = najdiRekurzivne(koren, klic);
        if (uzel == null)
            throw new StromException(ChybovaZpravaStromu.PRVEK_NENALEZEN.getZprava());
        return uzel.hodnota;
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#vloz(Comparable, Object)}}
     *
     * <p> Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Pokud je {@code uzel} prázdný (neexistuje), vrátí {@code null}
     *     <ul>
     *     <li> <b>if (uzel == null)</b>
     *     </ul>
     * <li> Porovnává klíč, podle kterého se provádí vyhledívaní s klíčem aktuálního uzlu
     *     <ul>
     *     <li> <b>jeNula(klic, uzel.klic)</b>: Pokud klíče jsou ekvivalentní, vrátí nalezený {@code uzel}
     *     <li> <b>jeZaporne(klic, uzel.klic)</b>: Pokud klíč  je menší než klíč uzlu, pokračuje hledat v levém
     *     podstromu rekurzivně
     *     <li> <b>jeKladne(klic, uzel.klic)</b>: Pokud klíč je větší než klíč uzlu, pokračuje hledat v pravém
     *     podstromu rekurzivně
     *     </ul>
     * </ol>
     *
     * @param uzel Aktuální uzel, ve kterém se provádí vyhledávání
     * @param klic Klíč, podle kterého se vyhledává hodnota
     *
     * @return {@link Uzel} odpovídající zadanému klíči nebo {@code null}, pokud hodnota není nalezena
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
     * Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Zkontroluje, zda klíč není prázdný a zda klíč již existuje ve stromu
     *     <ul>
     *     <li> <b>pozadatNePrazdnyKlic(klic)</b>
     *     <li> <b>if (jeExistujicimKlicem(klic))</b>
     *     </ul>
     * <li> Pokud kořen stromu existuje, provede rekurzivní vkládání nového uzlu, v opačmém případě (kořen
     * neexistuje), vytvoří nový uzel
     *     <ul>
     *     <li> <b>if (koren == null)</b>
     *     <li> <b>else</b>
     *     </ul>
     * <li> Zvětší mohutnost kořene stromu o hodnotu konstanty
     *     <ul>
     *     <li> <b>koren.mohutnost</b>
     *     </ul>
     * </ol>
     */
    @Override
    public void vloz(K klic, V hodnota) throws StromException {
        pozadatNePrazdnyKlic(klic);
        if (jeExistujicimKlicem(klic))
            throw new StromException(ChybovaZpravaStromu.EXISTUJICI_KLIC.getZprava());

        if (koren == null) {
            koren = new Uzel(klic, hodnota, null);
            aktualizujMohutnostPoVlozeni(koren);
        }
        else {
            final Uzel novyUzel = vlozRekurzivne(klic, hodnota, koren);
            aktualizujMohutnostPoVlozeni(novyUzel);
        }
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#vloz(Comparable, Object)}}
     *
     * <p> Popis logiký:
     * <ol>
     * <li> Provádí rekurzivní prohledávání stromu, dokud nenalezne uzel se zadaným klíčem {@code klic}
     * </ol>
     *
     * @param klic Klíč, který se má zkontrolovat na existenci ve stromu
     *
     * @return {@code true}, pokud klíč již existuje ve stromu, jinak {@code false}
     */
    private boolean jeExistujicimKlicem(K klic) {
        return najdiRekurzivne(koren, klic) != null;
    }

    /**
     * Pomocná metoda pro {@link AbstrTable#vloz(Comparable, Object)}}
     *
     * <p> Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Porovná klíče a rozhodne, do kterého podstromu nový uzel patří
     *     <ul>
     *     <li> <b>jeKladne(aktualniUzel.klic, klic)</b>: Pokud klíč uzlu je větší než klíč nového uzlu {@code klic},
     *     vloží nový uzel do levého podstromu
     *          <ul>
     *          <li> <b>else</b>: Pokud levý potomek uzlu {@code aktualniUzel.vlevo} není prázdný, volá se rekurzivně
     *          tato metoda pro levého potomka
     *          </ul>
     *     <li> <b>else</b>: Pokud klíč uzlu je menší nebo roven klíči nového uzlu {@code klic}, vloží nový uzel do
     *     pravého podstromu
     *          <ul>
     *          <li> <b>else</b>: Pokud pravý potomek uzlu {@code aktualniUzel.vpravo} není prázdný, volá se
     *          rekurzivně tato metoda pro pravého potomka
     *          </ul>
     *     </ul>
     * </ol>
     *
     * @param klic Klíč, který se má vložit
     * @param hodnota Hodnota, která se má vložit
     * @param aktualniUzel Aktuální uzel, ve kterém se hledá místo pro vložení nového uzlu (prvku)
     *
     * @return Nově vložený uzel
     */
    private Uzel vlozRekurzivne(K klic, V hodnota, Uzel aktualniUzel) {
        Uzel novyUzel;
        if (jeKladne(aktualniUzel.klic, klic)) {
            if (aktualniUzel.vlevo == null)
                novyUzel = aktualniUzel.vlevo = new Uzel(klic, hodnota, aktualniUzel);
            else
                novyUzel = vlozRekurzivne(klic, hodnota, aktualniUzel.vlevo);
        } else {
            if (aktualniUzel.vpravo == null)
                novyUzel = aktualniUzel.vpravo = new Uzel(klic, hodnota, aktualniUzel);
            else
                novyUzel = vlozRekurzivne(klic, hodnota, aktualniUzel.vpravo);
        }
        return novyUzel;
    }

    /**
     * Aktualizuje mohutnost uzlů po vložení nového uzlu do stromu. Postupuje od vloženého uzlu až ke kořeni
     * stromu a zvyšuje mohutnost každého uzlu na cestě
     *
     * @param novyUzel Uzel, od kterého se začíná aktualizace mohutnosti
     */
    private void aktualizujMohutnostPoVlozeni(Uzel novyUzel) {
        while (novyUzel != null) {
            zvysMohutnost(novyUzel);
            novyUzel = novyUzel.rodic;
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

        Uzel uzel = najdiRekurzivne(koren, klic);
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
     * <p> Popis logiký jednotlivých bloků kódu:
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
     * <p> Popis logiký jednotlivých bloků kódu:
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
    private void zvysMohutnost(@NotNull Uzel uzel) { uzel.mohutnost++; }

    private void snizMohutnost(@NotNull Uzel uzel) { uzel.mohutnost--; }
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
     *
     * <p><b>Breadth-First Search (BFS)</b> je strategie pro prohledávání grafů nebo stromů, zaměřující se na
     * prozkoumání všech uzlů v určitém "levelu" od počátečního uzlu nejprve, a teprve poté postupuje k uzlům
     * v následujícím levelu. Tímto způsobem BFS postupuje "široce" v rámci jednoho levelu před tím, než se
     * pohne na další level
     */
    private class SirkaIterator implements Iterator<V> {

        private final IAbstrFifo<Uzel> fronta;

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
        public V next() {
            if (!hasNext())
                throw new NoSuchElementException();
            try {
                final Uzel aktualniUzel = fronta.odeber();
                if (aktualniUzel.vlevo != null)
                    fronta.vloz(aktualniUzel.vlevo);
                if (aktualniUzel.vpravo != null)
                    fronta.vloz(aktualniUzel.vpravo);
                return aktualniUzel.hodnota;
            } catch (StrukturaException e) {
                throw new NoSuchElementException(ChybovaZpravaStromu.PRAZDNA_FRONTA.getZprava());
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
    private class HloubkaIterator implements Iterator<V> {

        private final IAbstrLifo<Uzel> zasobnik;
        private Uzel aktUzel;

        /**
         * Konstruktor vytvoří instanci iterátoru a inicializuje zásobník, pokud strom není prázdný
         */
        public HloubkaIterator() {
            zasobnik = new AbstrLifo<>();
            aktUzel = koren;
        }

        /**
         * Určuje, zda existuje další prvek pro zpracování
         *
         * @return {@code true}, pokud existuje další prvek, jinak {@code false}
         */
        @Override
        public boolean hasNext() { return !zasobnik.jePrazdny() || aktUzel != null; }

        /**
         * Vrací další prvek v pořadí pro průchod stromem do hloubky (in-order)
         *
         * <p> Provádí {@code in-order} procházení stromu tím, že nejprve přidává všechny uzly (prvky)
         * levého podstromu do zásobníku, poté postupně odebírá uzly z vrcholu zásobníku a přidává uzly
         * pravého podstromu, pokud existují. Tímto způsobem budou uzly (prvky) navštěvovány v pořadí
         * od nejmenšího po největší klíč. Tímto způsobem jsou uzly (prvky) navštěvovány v pořadí podle
         * jejich klíčů od nejmenšího po největší
         *
         * <p> Popis logiký jednotlivých bloků kódu:
         * <ol>
         * <li> <b>while (aktUzel != null)</b>: Prochází stromem do hloubky, dokud aktuální uzel není {@code null},
         * tedy dokud má levého potomka, postupně přidává uzly na zásobník a posunuje se na levého potomka stromu,
         * čímž vytváří cestu směrem k nejlevějšímu (nejmenšímu) uzlu stromu
         * <li> Odebere uzel z vrcholu zásobníku (po přidání všech levých uzlů na zásobník), jímž prochází a vrátí
         * jeho hodnotu. Poté přejde na pravého potomka tohoto uzlu a pokračuje v iteraci, čímž vlastně postupně
         * prochází strom v {@code in-order} pořadí (zleva doprava)
         *     <ul>
         *     <li> <b>aktUzel = zasobnik.odeber()</b>
         *     <li> <b>final V hodnota = aktUzel.hodnota</b>
         *     <li> <b>aktUzel = aktUzel.vpravo</b>
         *     </ul>
         * </ol>
         *
         * @return Další prvek pro zpracování
         *
         * @throws NoSuchElementException Pokud není další prvek k dispozici
         */
        @Override
        public V next() {
            try {
                while (aktUzel != null) {
                    zasobnik.vloz(aktUzel);
                    aktUzel = aktUzel.vlevo;
                }
                aktUzel = zasobnik.odeber();
                final V hodnota = aktUzel.hodnota;
                aktUzel = aktUzel.vpravo;
                return hodnota;
            } catch (StrukturaException e) {
                throw new NoSuchElementException(ChybovaZpravaStromu.PRAZDNY_ZASOBNIK.getZprava());
            }
        }
    }

    @Override
    public int dejMohutnost(K klic) {
        final Uzel hledanyUzel = najdiRekurzivne(koren, klic);
        if (hledanyUzel == null)
            return UKAZATEL_ABSENCE;
        return hledanyUzel.mohutnost;
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
    private boolean jeNula(@NotNull K obj1, K obj2) { return obj1.compareTo(obj2) == NULTA_HODNOTA; }

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
    private boolean jeKladne(@NotNull K obj1, K obj2) { return obj1.compareTo(obj2) > NULTA_HODNOTA; }

    /**
     * Porovná výsledek metody compareTo s záporným číslem
     *
     * <p>{@link Comparable#compareTo(Object)} vratí záporné číslo pokud aktuální instance {@code this} je "menší" než druhá instance {@code other}
     *
     * @param obj1 První objekt pro porovnání
     * @param obj2 Druhý objekt pro porovnání
     * @return {@code true}, pokud výsledek je záporný, jinak {@code false}
     */
    private boolean jeZaporne(@NotNull K obj1, K obj2) { return obj1.compareTo(obj2) < NULTA_HODNOTA; }

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
