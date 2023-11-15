package cz.upce.fei.bdast.strom;

import cz.upce.fei.bdast.vyjimky.StromException;

import java.util.Iterator;

/**
 * Strom - je graf, kde mezi každými dvěma vrcholy existuje cesta a kde existuje jeden kořen ({@code root}),
 * který nemá žádného předka a může obsahovat potomky
 *
 * <p>Binární stromy obecně jsou stromy, které mají kořen a každý uzel má nejvýše dva potomky (od toho jsou
 * binární). V každém uzlu je potom uložený právě jeden prvek. Ty jsou buď dalším uzlem, nebo {@code null} –
 * nic. Uzel může mít i jen jednoho potomka
 *
 * <p>Binární vyhledávací strom (zkráceně BST jako Binary Search Tree) má přesně dané, kde jaký prvek leží.
 * Přesněji pravá větev vždy obsahuje prvky větší než hodnota daného uzlu, levá větev potom prvky menší než
 * hodnota daného uzlu. BST je tedy efektivnější, než spojový seznam: například, je zapotřebí vyhledat
 * hodnotu 7, tak začátek je v kořeni stromu a díky definici BST je jasné, že pokud uzel již rovnou
 * neobsahuje hledaný prvek, může tento prvek být vždy jen v jedné z větví uzlu (protože je buď menší nebo
 * větší než hodnota v uzlu). Jedna z větvi tedy je vždy úplně ignorována a pokud je strom dobře vyvážený,
 * je možné odhodnout tak vždy polovinu prvků daného podstromu. Časová složitost tak bude logaritmická se
 * základem 2 (log n)
 *
 * <p>Toto rozhraní umožňuje pracovat s binárním vyhledávacím stromem, který uchovává páry klíč-hodnota a
 * poskytuje metody pro vkládání, vyhledávání a odebírání prvků, a také pro procházení stromu různými
 * způsoby
 */
public interface IAbstrTable<K extends Comparable<K>, V> {

    /**
     * Zrušení celé tabulky
     */
    void zrus();

    /**
     * Test prázdnosti tabulky
     *
     * @return {@code true}, pokud je tabulka prázdná, jinak {@code false}
     */
    boolean jePrazdny();

    /**
     * Vyhledá prvek dle klíče
     *
     * @param klic Klíč, podle kterého se vyhledává prvek
     *
     * @return Hodnota přiřazená k zadanému klíči
     *
     * @throws StromException Pokud prvek s daným klíčem není nalezen
     */
    V najdi(K klic) throws StromException;

    /**
     * Vloží prvek do tabulky
     *
     * @param klic Klíč, pod kterým se má prvek vložit
     * @param hodnota Prvek, který se má vložit do tabulky
     *
     * @throws StromException Pokud daný klíč je prázdný
     */
    void vloz(K klic, V hodnota) throws StromException;

    /**
     * Odebere prvek dle klíče z tabulky
     *
     * @param klic Klíč, pod kterým se má prvek odebrat
     *
     * @return Odebraná hodnota
     *
     * @throws StromException Pokud prvek s daným klíčem není nalezen
     */
    V odeber(K klic) throws StromException;

    /**
     * Vytvoří iterátor, který umožňuje  procházení stromu do šířky/hloubky (in-order)
     *
     * @param typ Typ prohlížení {@link ETypProhl} - může být například in-order, pre-order,
     *            post-order, atd.
     *
     * @return {@link Iterator} pro procházení stromu
     */
    Iterator<V> vytvorIterator(ETypProhl typ);

    /**
     * Vrací mohutnost uzlu (počet jeho potomků včetně něj samotného) se zadaným klíčem
     *
     * @param klic Klíč uzlu, jehož mohutnost se má zjistit
     *
     * @return Mohutnost uzlu se zadaným klíčem nebo {@code 0}, pokud uzel s klíčem není nalezen
     */
    int dejMohutnost(K klic);

    /**
     * Vratí textový řetězec s celým stromem, kde je uvedeno, jaký uzel má jaké potomnky a jaký uzel je čí
     * potomek/rodič (zda má potomky vlevo/vpravo)
     *
     * <p> Pokud se do stromu vloží klíče v následujícím poředí: d, c, b, a, e, f; pak výstup má podobu:
     * <ul>
     * <li> "d" (kořen)
     * <li> "c" je vlevo od "d"
     * <li> "b" je vlevo od "c"
     * <li> "a" je vlevo od "b"
     * <li> "e" je vlevo od "d"
     * <li> "f" je vlevo od "e"
     * </ul>
     *
     * @param typ Typ prohlížení
     *
     * @return Řetězec s informacemi o rodiči a potomcích
     */
    String vypisStrom(ETypProhl typ);

    /**
     * Zjištění, zda strom obsahuje zadaný klíč
     *
     * @param klic Klíč, který se má zjistit, zda je obsažen ve stromu
     *
     * @return {@code true}, pokud strom obsahuje klíč, jinak {@code false}
     */
    boolean obsahuje(K klic);
}
