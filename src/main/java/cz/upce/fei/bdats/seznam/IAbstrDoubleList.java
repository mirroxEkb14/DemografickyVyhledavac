package cz.upce.fei.bdats.seznam;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Tento interfejs deklaruje metody obousměrně necyklického zřetězeného
 * lineárního seznamu
 * <p>
 * V tomto rozhraní jsou signatury metody zajišťují pohyb, vložení, převzetí
 * a vyjmutí dat do nebo ze seznamu na pozici aktuálního prvku
 * <p>
 * Implementuje implicitní rozhraní {@link Iterable}
 * <p>
 * Výhody spojového seznamu:
 * <ol>
 * <li> kapacita je teoreticky neomezená. Jinymi slovy, nepotřebujeme dopředu
 * vědět, kolik potřebujeme alokovat místa. Například, interně {@link java.util.ArrayList}
 * alokuje při svém vytvoření v parametrickém konstruktoru 16 nebo 32 potic.
 * A když dojdeme na vrchol tohohletoho zásobníku (16 nebo 32 pozic), tak
 * interně realokuje nové místo. Dělá se to všechno v pozadí
 * <li> velikost obsazené paměti je přímo závislá jen na počtu prvků, není
 * zde žádné plýtvání
 * <li> rychlost přidávání a odebrání prvků je vždy stejně vysoká. Je to
 * vlastně přepsání dvou referencí v té datové struktuře
 * </ol>
 * Nevýhody spojového seznamu:
 * <ol>
 * <li> pomalý přístup k prvkům na zadaném indexu
 * <li> uložené hodnoty nejsou v paměti uspořádány za sebou
 * <li> pomalejší procházení (ke každům datům přidáváme ještě nějakou informaci o
 * následujícím a předchozím indexu, kvůli čemuž stoupá paměťová nárošnost)
 * <li> stejné množství dat zabítá více paměti než stejné prvky uložené v
 * poli (kvůli ukazatelům navíc)
 * </ol>
 *
 * @author amirov 10/4/2023
 */
public interface IAbstrDoubleList<T> extends Iterable<T> {

    /**
     * Zrušení celého seznamu
     */
    void zrus();

    /**
     * Zjišťovací metoda
     * <p>
     * Test naplněnosti seznamu
     *
     * @return vrací {@code true}, když je seznam prázdný, jinak {@code false}
     */
    boolean jePrazdny();

    /**
     * Vkládací metoda
     * <p>
     * Vložení prvku do seznamu na první místo
     *
     * @param data datová entita typu T
     */
    void vlozPrvni(T data);

    /**
     * Vkládací metoda
     * <p>
     * Vložení prvku do seznamu na poslední místo
     *
     * @param data datová entita typu T
     */
    void vlozPosledni(T data);

    /**
     * Vkládací metoda
     * <p>
     * Vložení prvku do seznamu jakožto následníka aktuálního prvku
     *
     * @param data datová entita typu T
     */
    void vlozNaslednika(T data);

    /**
     * Vkládací metoda
     * <p>
     * Vložení prvku do seznamu jakožto předchůdce aktuálního prvku
     *
     * @param data datová entita typu T
     */
    void vlozPredchudce(T data);

    /**
     * Přístupová metoda
     * <p>
     * Zpřístupnění aktuálního prvku seznamu: vrací hodnotu aktuálního prvku a
     * přestaví vnitřní aktuální ukazatel na další prvek seznamu
     *
     * @return vrací odkaz na object/datovou entitu typu T z aktuálního prvku seznamu
     */
    T zpristupniAktualni();

    /**
     * Přístupová metoda
     * <p>
     * Zpřístupnění prvního prvku seznamu: vrací hodnotu prvního prvku
     *
     * @return vrací odkaz na object/datovou entitu typu T z prvního prvku seznamu
     */
    T zpristupniPrvni();

    /**
     * Přístupová metoda
     * <p>
     * Zpřístupnění posledního prvku seznamu: vrací hodnotu posledního prvku
     *
     * @return vrací odkaz na objekt/datovou entitu typu T z posledního prvku seynamu
     */
    T zpristupniPosledni();

    /**
     * Přístupová metoda
     * <p>
     * Zpřístupnění následníka aktuálního prvku
     *
     * @return vrací odkaz na object/datovou entitu typu T za aktuálním prvkem seznamu
     */
    T zpristupniNaslednika();

    /**
     * Přístupová metoda
     * <p>
     * Zpřístupnění předchůdce aktuálního prvku
     *
     * @return vrací odkaz na object/datovou entitu typu T před aktuálním prvkem seznamu
     */
    T zpristupniPredchudce();

    /**
     * Odebírací metoda
     * <p>
     * Odebrání (vyjmutí) aktuálního prvku ze seznamu poté je aktuální prvek nastaven na první prvek
     *
     * @return vrací odkaz na odebíraný objekt, datovou entitu typu T
     */
    T odeberAktualni();

    /**
     * Odebírací metoda
     * <p>
     * Odebrání prvního prvku ze seznamu
     *
     * @return vrací odkaz na odebíraný objekt, datovou entitu typu T
     */
    T odeberPrvni();

    /**
     * Odebírací metoda
     * <p>
     * Odebrání posledního prvku ze seznamu
     *
     * @return vrací odkaz na odebíraný objekt, datovou entitu typu T
     */
    T odeberPosledni();

    /**
     * Odebírací metoda
     * <p>
     * Odebrání následníka aktuálního prvku ze seznamu
     *
     * @return vrací odkaz na odebíraný objekt, datovou entitu typu T
     */
    T odeberNaslednika();

    /**
     * Odebírací metoda
     * <p>
     * Odebrání předchůdce aktuálního prvku ze seznamu
     *
     * @return vrací odkaz na odebíraný objekt, datovou entitu typu T
     */
    T odeberPredchudce();

    /**
     * Vytvoří iterátor dle rozhraní {@link Iterable} pro procházení
     * položek objektů seznamu
     *
     * @return vrací iterátor nad prvky typu T
     */
    Iterator<T> iterator();

    /**
     * Vrací aktuální počet dat v seznamu
     *
     * @return vrací hodnotu s počtem dat v seznamu
     */
    int mohutnost();

    /**
     * Metoda převede obsah seznamu na datový proud, který předá při návratu
     * <p>
     * V implementačních třídách se nepřekrývá
     *
     * @return datovy proud
     */
    default Stream<T> stream() { return StreamSupport.stream(spliterator(), false); }
}
