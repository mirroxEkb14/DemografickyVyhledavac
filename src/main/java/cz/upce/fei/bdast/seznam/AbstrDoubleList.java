package cz.upce.fei.bdast.seznam;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tato třída implementuje abstraktní datovou strukturu (ADS) obousměrně
 * necyklicky zřetězený lineární seznam v dynamické paměti
 * <p>
 * Reprezentuje dynamickou datovou strukturu, která se skládá z lineárně uspořádaných
 * prvků (nodes) vzájemně propojených ukazateli
 * <p>
 * Implementuje rozhraní {@link IAbstrDoubleList}
 *
 * @author amirov 10/4/2023
 * @param <T> generický parametr, reprezentující budoucí datový typ
 */
public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    /**
     * Ukazatel na první prvek je stupním bodem do spojového seznamu (head).
     */
    private Prvek<T> hlavicka;
    /**
     * Ukazatel na konec seznamu je vhodný pro snadné přidávání prvků na konec seznamu (tail).
     */
    private Prvek<T> paticka;
    /**
     * Vnitřní aktuální ukazatel na data seznamu.
     */
    private Prvek<T> aktualni;
    /**
     * Aktuální počet dat v seznamu.
     */
    private int pocet;

    /**
     * Konstanty, které jsou používany při kontrole rozsahu seznamu:
     * <ol>
     * <li> zda jsou vůbec nějaké prvky
     * <li> zda je alespoň jeden prvek
     * </ol>
     *
     */
    private static final int KONTROLUJICI_NULA = 0;
    private static final int KONTROLUJICI_JEDNA = 1;

    /**
     * Pomocná vnitřní třída {@link Prvek} obsahuje dva propojovací atributy pro
     * uložení odkazu směrem na další a předchozí instanci téže třídy.
     * <p>
     * Každý prvek v sobě nese:
     * <ol>
     * <li> datovou hodnotu - {@link Integer}, {@link String}, {@link Object} apod.
     * <li> ukazatel na následující a předchozí prvky (metadata).
     * </ol>
     * Prvek spojového seznamu "obaluje" datovou hodnotu informacemi o jejím
     * relativním umístění vůči ostatním prvkům. V rámci každého nodu/prvku
     * uchováváme samotná data a současně v tom nodu jsou metadata, ukazující
     * na následující a předchozí nod.
     *
     * @param <T> datová hodnota.
     */
    private static class Prvek<T> {
        Prvek<T> predchozi;
        T polozka;
        Prvek<T> dalsi;

        private Prvek(T polozka) {
            this.polozka = polozka;
        }
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> začne cyklus {@code for} z hlavičky sezmanu:
     *     <ol>
     *     <li> vytvoří prvek, který reprezentuje prvek další
     *     než prvek, který je na dané iteraci (při 1. iteraci je to hlavička)
     *     <li> vynuluje položku prvku, který je na dané interaci
     *     <li> vynuluje prvek další prvku na dané iteraci
     *     <li> vynuluje prvek předchozí prvku na dané iteraci
     *     <li> přestaví prvek na dané iteraci na prvek mu další, aby cyklus
     *     {@code for} se pokračoval
     *     </ol>
     * <li> nastaví hlavičku na patičku, kterou predtím nastaví na aktuální,
     * který předtím vynuluje
     * <li> vynuluje počet prvků v seznamu
     * </ol>
     */
    @Override
    public void zrus() {
        for (Prvek<T> p = hlavicka; p != null;) {
            Prvek<T> dalsi = p.dalsi;
            p.polozka = null;
            p.dalsi = null;
            p.predchozi = null;
            p = dalsi;
        }
        hlavicka = paticka = aktualni = null;
        vynulujPocet();
    }

    @Override
    public boolean jePrazdny() {
        return pocet == 0;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> vytvoří nový uzel (node), který je potřeba vložit do seznamu
     * <li> pokud je seznam prázdný, tak:
     *      <ol>
     *      <li> nastaví hlavičku a patičku seznamu na novy prvek
     *      </ol>
     * <li> pokud není seznam prázdný (hlavička není {@code null}), tak:
     *      <ol>
     *      <li> nastaví další ukazatel nového uzlu na hlavičku
     *      <li> nastaví předchozí ukazatel hlavičky na nový uzel
     *      <li> nastaví hlavičku na nový prvek
     *      <li> <b>poznámka:</b> předchozí ukazatel nového uzlu je
     *      {@code null}, protože to bude nová hlavička
     *      </ol>
     * <li> zvyší atribut {@code pocet} o jednu
     * </ol>
     */
    @Override
    public void vlozPrvni(T data) {
        final Prvek<T> novyPrvek = new Prvek<>(data);
        if (hlavicka == null) {
            hlavicka = paticka = novyPrvek;
        } else {
            novyPrvek.dalsi = hlavicka;
            hlavicka.predchozi = novyPrvek;
            hlavicka = novyPrvek;
        }
        zvysPocet();
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> vytvoří nový node
     * <li> pokud je seznam prázdný, tak:
     *      <ol>
     *      <li> nastaví oboje hlavičku a patičku na nový node
     *      </ol>
     * <li> pokud není seznam prázdný (patička není {@code null}), tak:
     *      <ol>
     *      <li> nastaví další ukazatel posledního uzlu na nový node
     *      <li> nastaví předchozí ukazatel nového uzlu na patičku, což
     *      je posledním uzelem
     *      <li> nastaví patičku na nový node
     *      <li> <b>poznámka:</b> další ukazatel nového uzlu je {@code null},
     *      protože je posledním uzelem v seznamu
     *      </ol>
     * <li> zvyší atribut {@code pocet} o jednu
     * </ol>
     */
    @Override
    public void vlozPosledni(T data) {
        final Prvek<T> novyPrvek = new Prvek<>(data);
        if (paticka == null) {
            hlavicka  = paticka = novyPrvek;
        } else {
            paticka.dalsi = novyPrvek;
            novyPrvek.predchozi = paticka;
            paticka = novyPrvek;
        }
        zvysPocet();
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> ověří, zda je nastaven aktuální prvek
     * <li> vytvoří nový prvek s novými daty
     * <li> nastaví u nového prvku ukazatel na předchozí na aktuální
     * <li> nastaví u nového prvku ukazatel na další na další prvek
     * nyní aktuálního
     * <li> pokud prvek další aktuálního není {@code null}, tak:
     *     <ol>
     *     <li> nastaví ukazatel předchozí u prvku další aktuálního
     *     na nový prvek
     *     <li> nastaví prvek další aktuálnímu na nový prvek
     *     </ol>
     * <li> pokud prvek další aktuálního je {@code null}, tak:
     *     <ol>
     *     <li> nastaví patičku na prvek další aktuálnímu, který
     *     který předtím bude nastaven na nový prvek
     *     </ol>
     * <li> zvyší počet prvků o jedničku
     * </ol>
     */
    @Override
    public void vlozNaslednika(T data) {
        pozadatAktualni();

        final Prvek<T> novyPrvek = new Prvek<>(data);
        novyPrvek.predchozi = aktualni;
        novyPrvek.dalsi = aktualni.dalsi;
        if (aktualni.dalsi != null) {
            aktualni.dalsi.predchozi = novyPrvek;
            aktualni.dalsi = novyPrvek;
        } else {
            paticka = aktualni.dalsi = novyPrvek;
        }
        zvysPocet();
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> ověří, zda je nastaven aktuální prvek
     * <li> vytvoří nový prvek s novými daty
     * <li> nastaví u nového prvku ukazatel na další na aktuální
     * <li> nastaví u nového prvku ukazatel na předchozí na předchozí prvek
     * nyní aktuálního
     * <li> pokud prvek předchozí aktuálního není {@code null}, tak:
     *     <ol>
     *     <li> nastaví ukazatel další u prvku předchozího aktuálnímu
     *     na nový prvek
     *     <li> nastaví ukazatel předchozí u aktuálního na nový prvek
     *     </ol>
     * <li> pokud prvek předchozí aktuálního je {@code null}, tak:
     *      <ol>
     *      <li> nastaví hlavičku na prvek předchozí aktuálnímu, který
     *      předtím bude nastaven na nový prvek
     *      </ol>
     * <li> zvyší počet prvků o jedničku
     * </ol>
     */
    @Override
    public void vlozPredchudce(T data) {
        pozadatAktualni();

        final Prvek<T> novyPrvek = new Prvek<>(data);
        novyPrvek.dalsi = aktualni;
        novyPrvek.predchozi = aktualni.predchozi;
        if (aktualni.predchozi != null) {
            aktualni.predchozi.dalsi = novyPrvek;
            aktualni.predchozi = novyPrvek;
        } else {
            hlavicka = aktualni.predchozi = novyPrvek;
        }
        zvysPocet();
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> pokud není seznam prázdný, a zároveň aktuálně nastavený prvek je nastaven
     * (obsahuje nějakou hodnotu), tak:
     *      <ol>
     *      <li> vytvoří nový uzel, obsahující odkaz na aktuální prvek seznamu
     *      <li> nastaví aktuální ukazatel na další prvek toho aktuálního
     *      <li> vratí položku prvku, který byl aktuálním před bodem (2)
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniAktualni() {
        pozadatAktualni();
        return aktualni.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> pokud není seznam prázdný (je alespoň jeden prvek), tak:
     *      <ol>
     *      <li> nastaví aktuální ukazatel na hlavičku
     *      <li> vratí položku prvního (teď už aktuálního) prvku
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniPrvni() {
        pozadatNePrazdy();
        aktualni = hlavicka;
        return aktualni.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> pokud není seznam prázdný (je alespoň jeden prvek), tak:
     *      <ol>
     *      <li> nastaví aktuální ukazatel na patičku
     *      <li> vratí položku posledního (teď už aktuálního) prvku
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniPosledni() {
        pozadatNePrazdy();
        aktualni = paticka;
        return aktualni.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> pokud není seznam prázdný (je alespoň jeden prvek), a zároveň
     * ten počet prvků v seznamu je více než jedna, tak:
     *      <ol>
     *      <li> nastaví aktuální ukazatel na prvek další aktuálnímu
     *      <li> vratí položku prvku, který je další než aktuální (teď už
     *      aktuální)
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniNaslednika() {
        pozadatNePrazdy();
        pozadatViceNezJedna();

        aktualni = aktualni.dalsi;
        return aktualni.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> pokud není seznam prázdný (je alespoň jeden prvek), a zároveň
     * ten počet prvků v seznamu je více než jedna, tak:
     *      <ol>
     *      <li> nastaví aktuální ukazatel na prvek předchozí aktuálnímu
     *      <li> vratí položku prvku, který je předchozí než aktuální (teď
     *      už aktuální)
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniPredchudce() {
        pozadatNePrazdy();
        pozadatViceNezJedna();

        aktualni = aktualni.predchozi;
        return aktualni.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> ošetří, zda není seznam prázdný
     * <li> ošetří, zda je nastaven aktuální prvek
     * <li> pokud aktuální je zároveň hlavočkou, tak:
     *     <ol>
     *     <li> vratí návratový prvek z metody {@link AbstrDoubleList#odeberPrvni()}
     *     </ol>
     * <li> pokud aktuální je zároveň patičkou, tak:
     *     <ol>
     *     <li> vratí návratový prvek z metody {@link AbstrDoubleList#odeberPosledni()}
     *     </ol>
     * <li> vytvoří nový prvek uchovávahící odkaz na aktuální prvek
     * <li> nastaví okazatel další u prvku předchozího aktuálnímu na prvek další
     * aktuálnímu
     * <li> nastaví předchozí ukazatel u prvku další aktuálního na prvel předchozí
     * aktuálnímu
     * <li> vynyluje ukazatel aktuální
     * <li> sníží počet prvků v seznamu o jedničku
     * <li> vratí položku aktuálního prvky, který byl uchovávan před mazáním
     * </ol>
     */
    @Override
    public T odeberAktualni() {
        pozadatNePrazdy();
        pozadatAktualni();

        if (aktualni == hlavicka)
            return odeberPrvni();
        else if (aktualni == paticka)
            return odeberPosledni();

        final Prvek<T> odebranyPrvek = aktualni;
        aktualni.predchozi.dalsi = aktualni.dalsi;
        aktualni.dalsi.predchozi = aktualni.predchozi;
        aktualni = hlavicka;
        snizPocet();
        return odebranyPrvek.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> ověří, zda je seznam prázdný
     * <li> pokud hlavička je zároveň nastavena jako aktuální, tak:
     *     <ol>
     *     <li> nastaví ukazatel aktuální na hodnotu {@code null},
     *     protože následně hlavička bude smazaná
     *     </ol>
     * <li> vytvoří nový prvek, který uchovává hlavičky, aby metoda
     * následně mohla vrátit její hodnotu
     * <li> pokud prvek další hlavičky je {@code null}, tak:
     *     <ol>
     *     <li> nastaví hlavičku na patičku, která předtím bude
     *     vynulována
     *     <li> vynuluje počet prvků s seznamu
     *     <li> vratí hodnotu hlavičky a ukončí metodu
     *     </ol>
     * <li> nastaví hlavičku na prvek další hlavičky
     * <li> nastaví prvek předchozí hlavičce na {@code null}
     * <li> sníží počet prvků v seznamu o jedničku
     * <li> vratí hodnotu hlavičky a ukončí metodu
     * </ol>
     */
    @Override
    public T odeberPrvni() {
        pozadatNePrazdy();
        if (hlavicka == aktualni) {
            aktualni = null;
        }
        final Prvek<T> odebranyPrvek = hlavicka;
        if (hlavicka.dalsi == null) {
            hlavicka = paticka = null;
            vynulujPocet();
            return odebranyPrvek.polozka;
        }
        hlavicka = hlavicka.dalsi;
        hlavicka.predchozi = null;
        snizPocet();
        return odebranyPrvek.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> ověří, zda je seznam prázdný
     * <li> pokud patička je zároveň nastavena jako aktuální, tak:
     *     <ol>
     *     <li> nastaví ukazatel aktuální na hodnotu {@code null},
     *     protože následně patička bude smazaná
     *     </ol>
     * <li> vytvoří nový prvek, který uchovává patičku, aby metoda
     * následně vrátila tuto hodnotu
     * <li> pokud prvek předchozí patičky je {@code null}, tak:
     *     <ol>
     *     <li> nastaví hlavičku na patičku, která předtím bude
     *     vynulována
     *     <li> vynuluje počet prvků s seznamu
     *     <li> vratí hodnotu patičky a ukončí metodu
     *     </ol>
     * <li> nastaví patičku na prvek předchozí patičce
     * <li> nastaví prvek další hlavičky na {@code null}
     * <li> sníží počet prvků v seznamu o jedničku
     * <li> vratí hodnotu patičky a ukončí metodu
     * </ol>
     */
    @Override
    public T odeberPosledni() {
        pozadatNePrazdy();
        if (paticka == aktualni) {
            aktualni = null;
        }
        final Prvek<T> odebranyPrvek = paticka;
        if (paticka.predchozi == null) {
            hlavicka = paticka = null;
            vynulujPocet();
            return odebranyPrvek.polozka;
        }
        paticka = paticka.predchozi;
        paticka.dalsi = null;
        snizPocet();
        return odebranyPrvek.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> ověří, zda není seznam prázdný
     * <li> ověrí, zda je nastaven aktuální prvek
     * <li> pokud aktuální prvek je zároveň patičkou, tak:
     *     <ol>
     *     <li> vyhodí výjimku a ukončí metodu
     *     </ol>
     * <li> vytvoří nový prvek obsahující prvek, který je
     * potřeba odebrat (tj. prvek další aktuálního)
     * <li> pokud prvek, který musí být odebrán (tj. prvek
     * další aktuálního), je zároveň patičkou, tak:
     *     <ol>
     *     <li> vyvolá metodu {@link AbstrDoubleList#odeberPosledni()}
     *     a vratí hodnotu posledního prvku, tím pak ukončí tuto metodu
     *     </ol>
     * <li> nastavení ukazatele předchozí u prvku, který je další, než
     * prvek, který bude odebrán:
     *     <ol>
     *     <li> {@code aktualni.dalsi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.dalsi.dalsi} - prvek, který je další,
     *     než prvek z bodu (1)
     *     <li> {@code aktualni.dalsi.dalsi.predchozi} - ukazatel
     *     předchozí u prvku z bodu (2)
     *     <li> {@code aktualni.dalsi.predchozi} - ukazatel předchozí u
     *     prvku z bodu (1)
     *     </ol>
     * <li> nastavení ukazatele další u aktuálního prvku (tj. prvku, který
     * je předchozí tomu, který je potřeba odebrat):
     *     <ol>
     *     <li> {@code aktualni.dalsi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.dalsi.predchozi} - prvek, který je
     *     předchozí, než prvek z bodu (1)
     *     <li> {@code aktualni.dalsi.predchozi.dalsi} - ukazatel
     *     další u prvku z bodu (2)
     *     <li> {@code aktualni.dalsi.predchozi} - ukazatel další u
     *     prvku z bodu (1)
     *     </ol>
     * <li> sníží počet prvků v seznamu o jedničku
     * <li> vratí hodnotu odebraného prvku a tím ukončí metodu
     * </ol>
     */
    @Override
    public T odeberNaslednika() {
        pozadatNePrazdy();
        pozadatAktualni();
        if (aktualni == paticka)
            throw new NullPointerException();
        final Prvek<T> odebranyPrvek = aktualni.dalsi;
        if (aktualni.dalsi == paticka) {
            return odeberPosledni();
        }
        aktualni.dalsi.dalsi.predchozi = aktualni;
        aktualni.dalsi = aktualni.dalsi.dalsi;
        snizPocet();
        return odebranyPrvek.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> ověří, zda není seznam prázdný
     * <li> ověrí, zda je nastaven aktuální prvek
     * <li> pokud aktuální prvek je zároveň hlavičkou, tak:
     *     <ol>
     *     <li> vyhodí výjimku a ukončí metodu
     *     </ol>
     * <li> vytvoří nový prvek obsahující prvek, který je
     * potřeba odebrat (tj. prvek předchozí aktuálnímu)
     * <li> pokud prvek, který musí být odebrán (tj. prvek
     * předchozí aktuálnímu), je zároveň hlavičkou, tak:
     *     <ol>
     *     <li> vyvolá metodu {@link AbstrDoubleList#odeberPrvni()}
     *     a vratí hodnotu prvního prvku, tím pak ukončí tuto metodu
     *     </ol>
     * <li> nastavení ukazatele další u prvku, který je předchozí
     * prvku, který bude odebrán:
     *     <ol>
     *     <li> {@code aktualni.predchozi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.predchozi.predchozi} - prvek, který je
     *     předchozí prvku z bodu (1)
     *     <li> {@code aktualni.predchozi.predchozi.dalsi} - ukazatel další
     *     u prvku z bodu (2)
     *     <li> {@code aktualni.predchozi.dalsi} - ukazatel další u
     *     prvku z bodu (1)
     *     </ol>
     * <li> nastavení ukazatele předchozí u aktuálního prvku (tj. prvku, který
     * je další, než prvek, který je potřeba odebrat):
     *     <ol>
     *     <li> {@code aktualni.predchozi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.predchozi.dalsi} - prvek, který je
     *     další, než prvek z bodu (1)
     *     <li> {@code aktualni.predchozi.dalsi.predchozi} - ukazatel
     *     předchozí u prvku z bodu (2)
     *     <li> {@code aktualni.predchozi.predchozi} - ukazatel předchozí u
     *     prvku z bodu (1)
     *     </ol>
     * <li> sníží počet prvků v seznamu o jedničku
     * <li> vratí hodnotu odebraného prvku a tím ukončí metodu
     * </ol>
     */
    @Override
    public T odeberPredchudce() {
        pozadatNePrazdy();
        pozadatAktualni();
        if (aktualni == hlavicka)
            throw new NullPointerException();
        final Prvek<T> odebranyPrvek = aktualni.predchozi;
        if (aktualni.predchozi == hlavicka) {
            return odeberPrvni();
        }
        aktualni.predchozi.predchozi.dalsi = aktualni;
        aktualni.predchozi = aktualni.predchozi.predchozi;
        snizPocet();
        return odebranyPrvek.polozka;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> vratí nový objekt {@link Iterator}:
     *      <ol>
     *      <li> vytvoří nový prvek obsahující odkaz na první objekt seznamu
     *      <li> {@code hasNext()}:
     *          <ol>
     *          <li> vratí {@code true}, pokud aktuální prvek není {@code null}, v opačném
     *          případě {@code false}
     *          </ol>
     *      <li> {@code next()}:
     *          <ol>
     *          <li>.pokud metoda {@code hasNext()} vratí {@code true}, tak:
     *              <ol>
     *              <li> vytvoří novou položku obsahující data z nyní aktuálního prvku
     *              <li> nastaví aktuální prvek na prvek mu další
     *              <li> vratí data, která byla v aktuálním prvku před bodem (2)
     *              </ol>
     *          <li> vyhodí výjimku, protože aktuální prvek obsahuje hodnotu {@code null}
     *          </ol>
     *      </ol>
     * </ol>
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            Prvek<T> aktualniPrvek = hlavicka;

            @Override
            public boolean hasNext() {
                return aktualniPrvek != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T data = aktualniPrvek.polozka;
                    aktualniPrvek = aktualniPrvek.dalsi;
                    return data;
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public int velikost() { return pocet; }

    /**
     * Privátní pomocní metoda.
     * <p>
     * Zajišťuje, zda není seznam prázdný anebo aktuálně
     * nastavený prvek není nastaven.
     *
     * @throws NullPointerException vystaví se, když je seznam
     * prázdný anebo aktuálně nastavený prvek je {@code null}.
     */
    private void pozadatAktualni() throws NullPointerException {
        if (jePrazdny() || !jeAktualni())
            throw new NullPointerException();
    }

    /**
     * Privátní pomocní metoda.
     * <p>
     * Zjišťuje, zda jsou prvky v seznamu, tj. více než jeden (<= 1).
     *
     * @throws NullPointerException vystaví se, když je seznam prázdný
     * nebo je v seznamu pouze jeden prvek.
     */
    private void pozadatViceNezJedna() throws NullPointerException {
        if (jePrazdny() || pocet == KONTROLUJICI_JEDNA)
            throw new NullPointerException();
    }

    /**
     * Privátní pomocní metoda.
     * <p>
     * Zjišťuje, zda jsou prvky v seznamu, tj. alespoň jeden.
     *
     * @throws NullPointerException vystaví se, když je seznam prázdný.
     */
    private void pozadatNePrazdy() throws NullPointerException {
        if (jePrazdny())
            throw new NullPointerException();
    }

    /**
     * Privátní pomocní metoda.
     * <p>
     * Zjišťuje, zda je aktuální prvek nastaven.
     *
     * @return vrací {@code true}, když aktuální prvke má nějakou hodnotu.
     * V opačném případě, vrací {@code false}.
     */
    private boolean jeAktualni() { return aktualni != null; }

    /**
     * Privátní pomocní metoda.
     * <p>
     * Zvyšuje hodnotu atributu {@code pocet} o jedničku - {@code 1}.
     */
    private void zvysPocet() { pocet++; }

    /**
     * Privátní pomocní metoda.
     * <p>
     * Snižuje hodnotu atributu {@code pocet} o jedničku - {@code 1}.
     */
    private void snizPocet() { pocet--; }

    /**
     * Privátní pomocní metoda.
     * <p>
     * Vynuluje atribut {@code pocet}, tj. aktuální počet prvků
     * se bude rovnat {@code 0}.
     */
    private void vynulujPocet() { pocet = KONTROLUJICI_NULA; }
}
