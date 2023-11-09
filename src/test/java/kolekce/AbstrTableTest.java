package kolekce;

import cz.upce.fei.bdast.strom.*;
import org.junit.*;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Abychom mohli pokrýt všechny řádky metody najdi(), musíme napsat testy pro různé scénáře:
 * <ol>
 * <li> <b>test_01_</b> Scénáře metody {@link AbstrTable#najdi(Comparable)}
 * <li> <b>test_02_</b> Scénáře metody {@link AbstrTable#vloz(Comparable, Object)}}
 * <li> <b>test_03_</b> Scénáře metody {@link AbstrTable#odeber(Comparable)}
 * <li> <b>test_04_</b> Scénáře metody {@code mohutnost}
 * </ol>
 *
 * @author amirov 10/30/2023
 * @project semestralni_prace_b_amirov
 */
public class AbstrTableTest {

    /**
     * Sada klíčů a hodnot
     */
    private final int KLIC_A = 5;
    private final String HODNOTA_A = "A";
    private final int KLIC_B = 3;
    private final String HODNOTA_B = "B";
    private final int KLIC_C = 7;
    private final String HODNOTA_C = "C";
    private final int KLIC_D = 2;
    private final String HODNOTA_D = "D";
    private final int KLIC_E = 4;
    private final String HODNOTA_E = "E";
    private final int KLIC_F = 6;
    private final String HODNOTA_F = "F";
    private final int KLIC_G = 8;
    private final String HODNOTA_G = "G";

    private final String NEOCEKAVANY_STROM_EXCEPTION = "Očekáváno vyhození výjimky StromException";

    /**
     * Instance datové struktury
     */
    private IAbstrTable<Integer, String> strom;

    public AbstrTableTest() {}

    /**
     * Spočítá mohutnost stromu 9počet prvků0 v celém stromu
     *
     * @param strom Binární strom pro výpočet mohutnosti
     *
     * @return Počet prvků v stromu
     */
    private int mohutnostStromu(IAbstrTable<Integer, String> strom) {
        int pocetPrvku = 0;
        final Iterator<String> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            iterator.next();
            pocetPrvku++;
        }
        return pocetPrvku;
    }

    /**
     * Spočítá mohutnost konkrétního uzlu ve stromu na základě zadané hodnoty
     *
     * @param strom Strom, v rámci kterého se spočítá mohutnost uzlu
     * @param hodnota Hodnota uzlu
     *
     * @return Počet prvků v uzlu s danou hodnotou
     */
    private int mohutnostUzlu(IAbstrTable<Integer, String> strom, String hodnota) {
        int pocetPrvku = 0;
        final Iterator<String> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            final String aktHodnota = iterator.next();
            if (aktHodnota.equals(hodnota))
                pocetPrvku++;
        }
        return pocetPrvku;
    }


    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() { strom = new AbstrTable<>(); }

    @After
    public void tearDown() { strom = null; }

    /**
     * Test pro vyhledání klíče, který se nachází v kořeni stromu
     */
    @Test
    public void test_01_01_najdi() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            String result = strom.najdi(KLIC_A);
            String expected = HODNOTA_A;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Test pro vyhledání klíče, který se nachází v levém podstromu
     */
    @Test
    public void test_01_02_najdi() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            String result = strom.najdi(KLIC_B);
            String expected = HODNOTA_B;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Test pro vyhledání klíče, který se nachází v pravém podstromu
     */
    @Test
    public void test_01_03_najdi() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_C, HODNOTA_C);
            String result = strom.najdi(KLIC_C);
            String expected = HODNOTA_C;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Test pro vyhledání neexistujícího klíče
     */
    @Test
    public void test_01_04_najdi() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            String result = strom.najdi(KLIC_B);
            fail(NEOCEKAVANY_STROM_EXCEPTION);
        } catch (StromException ex) {
            assertEquals(ChybovaZpravaStromu.PRVEK_NENALEZEN.getZprava(), ex.getMessage());
        }
    }

    /**
     * Test pro vyhledání klíče, který má hodnotu {@code null}
     */
    @Test
    public void test_01_05_najdi() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            String result = strom.najdi(null);
            fail(NEOCEKAVANY_STROM_EXCEPTION);
        } catch (StromException ex) {
            assertEquals(ChybovaZpravaStromu.NULL_KLIC.getZprava(), ex.getMessage());
        }
    }

    /**
     * Test pro vyhledání klíče v prázdném stromu
     */
    @Test(expected = StromException.class)
    public void test_01_06_najdi() throws StromException {
        String result = strom.najdi(KLIC_A);
        fail();
    }

    /**
     * Ověřuje, zda metoda vloží prvek do prázdného stromu a zda lze prvek následně najít
     */
    @Test
    public void test_02_01_vloz() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            String result = strom.najdi(KLIC_A);
            String expected = HODNOTA_A;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Kontroluje, zda metoda správně aktualizuje hodnotu, když se vloží prvek s existujícím klíčem
     */
    @Test
    public void test_02_02_vloz() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            assertThrows(StromException.class,
                    () -> strom.vloz(KLIC_A, HODNOTA_B));
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověřuje, zda metoda vloží prvek doleva od existujícího uzlu a zda lze prvek následně najít
     */
    @Test
    public void test_02_03_vloz() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            String result = strom.najdi(KLIC_B);
            String expected = HODNOTA_B;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Kontroluje, zda metoda vloží prvek doprava od existujícího uzlu a zda lze prvek následně najít
     */
    @Test
    public void test_02_04_vloz() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_C, HODNOTA_C);
            String result = strom.najdi(KLIC_C);
            String expected = HODNOTA_C;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověřuje, zda metoda správně vloží více uzlů různě doleva a doprava od existujícího uzlu
     * a zda lze tyto uzly následně najít
     */
    @Test
    public void test_02_05_vloz() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_C, HODNOTA_C);
            strom.vloz(KLIC_D, HODNOTA_D);
            strom.vloz(KLIC_E, HODNOTA_E);
            strom.vloz(KLIC_F, HODNOTA_F);
            strom.vloz(KLIC_G, HODNOTA_G);

            String result1 = strom.najdi(KLIC_D);
            String result2 = strom.najdi(KLIC_E);
            String result3 = strom.najdi(KLIC_F);
            String result4 = strom.najdi(KLIC_G);

            String expected1 = HODNOTA_D;
            String expected2 = HODNOTA_E;
            String expected3 = HODNOTA_F;
            String expected4 = HODNOTA_G;

            assertEquals(expected1, result1);
            assertEquals(expected2, result2);
            assertEquals(expected3, result3);
            assertEquals(expected4, result4);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Testuje, zda metoda správně vyvolá výjimku, když je klíč pro vložení {@code null}
     */
    @Test
    public void test_02_06_vloz() {
        assertThrows(StromException.class,
                () -> strom.vloz(null, HODNOTA_A));
    }

    /**
     * Ověřuje, zda metoda vyhodí výjimku, když se pokusí odebrat prvek z prázdného stromu
     */
    @Test(expected = StromException.class)
    public void test_03_01_odeber() throws StromException {
        strom.odeber(KLIC_A);
        fail();
    }

    /**
     * Kontroluje, zda metoda vyhodí výjimku, když se pokusí odebrat prvek, který v stromu neexistuje
     */
    @Test(expected = StromException.class)
    public void test_03_02_odeber() throws StromException {
        strom.vloz(KLIC_A, HODNOTA_A);
        strom.vloz(KLIC_B, HODNOTA_B);

        strom.odeber(KLIC_C);
        fail();
    }

    /**
     * Ověřuje, že metoda správně odebere prvek (list) ze stromu
     */
    @Test
    public void test_03_03_odeber() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);

            String result = strom.odeber(KLIC_A);
            String expected = HODNOTA_A;
            assertEquals(expected, result);
            assertTrue(strom.jePrazdny());
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Kontroluje, zda metoda správně odebere prvek, který má jednoho potomka vlevo
     */
    @Test
    public void test_03_04_odeber() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);

            String result = strom.odeber(KLIC_B);
            String expected = HODNOTA_B;
            assertEquals(expected, result);
            assertFalse(strom.jePrazdny());

            assertThrows(
                    StromException.class,
                    () -> strom.odeber(KLIC_B)
            );
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověřuje, že metoda správně odebere prvek, který má jednoho potomka vpravo
     */
    @Test
    public void test_03_05_odeber() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_C, HODNOTA_C);

            String result = strom.odeber(KLIC_C);
            String expected = HODNOTA_C;
            assertEquals(expected, result);
            assertFalse(strom.jePrazdny());

            assertThrows(
                    StromException.class,
                    () -> strom.odeber(KLIC_C)
            );
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Kontroluje, zda metoda správně odebere prvek, který má oba potomky
     */
    @Test
    public void test_03_06_odeber() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_C, HODNOTA_C);
            strom.vloz(KLIC_D, HODNOTA_D);

            String result = strom.odeber(KLIC_A);
            String expected = HODNOTA_A;
            assertEquals(expected, result);
            assertFalse(strom.jePrazdny());

            assertThrows(
                    StromException.class,
                    () -> strom.odeber(KLIC_A)
            );
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověří, zda je mohutnost stromu správně {@code 0} po vytvoření prázdného stromu
     */
    @Test
    public void test_04_01_mohutnost() {
        int result = mohutnostStromu(strom);
        int expected = 0;
        assertEquals(expected, result);
    }

    /**
     * Ověří, zda je mohutnost stromu správně {@code 1}, když se do něj přidá jeden prvek
     */
    @Test
    public void test_04_02_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);

            int result = mohutnostStromu(strom);
            int expected = 1;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Zkontroluje, zda je mohutnost {@code 2}, když se do něj přidají dva prvky
     */
    @Test
    public void test_04_03_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);

            int result = mohutnostStromu(strom);
            int expected = 2;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Zkontroluje, zda se mohutnost sníží o {@code 1}, když se odebere jeden prvek ze stromu
     */
    @Test
    public void test_04_04_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.odeber(KLIC_A);

            int result = mohutnostStromu(strom);
            int expected = 0;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověří, zda se mohutnost správně aktualizuje, když se odebere více prvků
     */
    @Test
    public void test_04_05_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_C, HODNOTA_C);
            strom.odeber(KLIC_A);
            strom.odeber(KLIC_B);

            int result = mohutnostStromu(strom);
            int expected = 1;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Zkontroluje, zda mohutnost uzlu správně počítá počet prvků s danou hodnotou v prázdném stromě.
     * Očekává se návrat hodnoty {@code 0}
     */
    @Test
    public void test_04_06_mohutnost() {
        AbstrTable<Integer, String> strom = new AbstrTable<>();
        int result = mohutnostUzlu(strom, HODNOTA_A);
        int expected = 0;
        assertEquals(expected, result);
    }

    /**
     * Ověřuje, zda mohutnost uzlu správně počítá počet prvků s danou hodnotou v stromu s jedním uzlem.
     * Očekává se návrat hodnoty {@code 1}
     */
    @Test
    public void test_04_07_mohutnost() {
        try {
            AbstrTable<Integer, String> strom = new AbstrTable<>();
            strom.vloz(KLIC_B, HODNOTA_B);
            int result = mohutnostUzlu(strom, HODNOTA_B);
            int expected = 1;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Kontroluje, zda mohutnost uzlu správně počítá počet prvků s danou hodnotou v stromu s více uzly.
     * Očekává se návrat počtu prvků se shodnou hodnotou
     */
    @Test
    public void test_04_08_mohutnost() {
        try {
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_C, HODNOTA_B);
            int result = mohutnostUzlu(strom, HODNOTA_B);
            int expected = 2;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověřuje, jak mohutnost uzlu reaguje, když hledá hodnotu, která se v stromu nenachází.
     * Očekává se návrat hodnoty {@code 0}
     */
    @Test
    public void test_04_09_mohutnost() {
        try {
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_A, HODNOTA_A);
            int result = mohutnostUzlu(strom, HODNOTA_C);
            int expected = 0;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     *   C
     *  / \
     * A   D
     *  \   \
     *   B   F
     *      / \
     *     E   G
     */
    @Test
    public void test_05_01_vytvorIterator() {
        try {
            strom.vloz(KLIC_C, HODNOTA_C);
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_G, HODNOTA_G);
            strom.vloz(KLIC_D, HODNOTA_D);
            strom.vloz(KLIC_F, HODNOTA_F);
            strom.vloz(KLIC_E, HODNOTA_E);

            String[] ocekavanyVystup = {HODNOTA_A, HODNOTA_B, HODNOTA_C, HODNOTA_D, HODNOTA_E, HODNOTA_F, HODNOTA_G};
            Iterator<String> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);

            int index = 0;
            while (iterator.hasNext()) {
                String hodnota = iterator.next();
                assertEquals(ocekavanyVystup[index], hodnota);
                index++;
            }
        } catch (StromException ex) {
            fail();
        }
    }
}
