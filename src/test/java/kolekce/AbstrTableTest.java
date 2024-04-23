package kolekce;

import cz.upce.fei.bdats.strom.*;
import cz.upce.fei.bdats.vyjimky.StromException;
import cz.upce.fei.bdats.vyjimky.zpravy.ChybovaZpravaStromu;
import org.junit.*;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Testovací případy pro:
 * <ol>
 * <li> <b>test_01_</b> Scénáře metody {@link AbstrTable#najdi(Comparable)}
 * <li> <b>test_02_</b> Scénáře metody {@link AbstrTable#vloz(Comparable, Object)}}
 * <li> <b>test_03_</b> Scénáře metody {@link AbstrTable#odeber(Comparable)}
 * <li> <b>test_04_</b> Scénáře metody {@code mohutnost}
 * <li> <b>test_05_</b> Scenáře metody {@link AbstrTable#vytvorIterator(ETypProhl)}
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
    /**
     * Index využívaný u testovacích případů <b>test_05_</b>
     */
    private int index;

    public AbstrTableTest() {}

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {
        strom = new AbstrTable<>();
        index = 0;
    }

    @After
    public void tearDown() {
        strom = null;
        index = 0;
    }

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
     * Ověří, zda je mohutnost stromu správně {@code 1}, když se do něj přidá jeden prvek
     */
    @Test
    public void test_04_01_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);

            int result = strom.dejMohutnost(KLIC_A);
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
    public void test_04_02_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);

            int result = strom.dejMohutnost(KLIC_A);
            int expected = 2;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověří, zda se mohutnost správně aktualizuje, když se odebere více prvků
     */
    @Test
    public void test_04_03_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_C, HODNOTA_C);
            strom.odeber(KLIC_A);
            strom.odeber(KLIC_B);

            int result = strom.dejMohutnost(KLIC_C);
            int expected = 1;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověřuje, jak mohutnost uzlu reaguje, když hledá hodnotu, která se v stromu nenachází.
     * Očekává se návrat hodnoty {@code -1}, což znamená, že uzel s požadovaným klíčem v stomu není
     */
    @Test
    public void test_04_04_mohutnost() {
        try {
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_A, HODNOTA_A);
            int result = strom.dejMohutnost(KLIC_C);
            int expected = -1;
            assertEquals(expected, result);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Testuje mohutnost jednotlivých prvků v stromu. Když se do stromu přidává nový uzel, jeho mohutnost je
     * automaticky {@code 1} (může zatím nemít žádné potomky, ale stejně mohutnost je {@code 1})
     *
     * <p> Popis logiky:
     * <ol>
     * <li> <b>strom.vloz(KLIC_A, HODNOTA_A)</b>: Vloží první prvek ({5: A}) do stromu jako kořen (jehož mohutnost je {@code 1})
     * <li> <b>strom.vloz(KLIC_B, HODNOTA_B)</b>: Vloží druhý prvek ({3: B}) jako levého potomka kořene (muhutnost
     * tohoto nově vloženého prvku je {@code 1}). Začne jít nahoru v rámci stromu a zvyšovat mohutnost všech prvků na
     * cestě (v tomto případě dojde k zvyšování mohutnosti pouze u rodiče prvku {3: B}, tj. kořene). Teď mohutnost
     * kořene jsou {@code 2}
     * <li> <b>strom.vloz(KLIC_C, HODNOTA_C)</b>: Vloží třetí prvek ({7: C}) jako pravého potomka kořene (mohutnost
     * tohoto pravého potomka je autimaticky {@code 1}). Zvyší mojutnost kořene o jedničku. Teď mohutnost kořene jsou {@code 3}
     * <li> <b>strom.vloz(KLIC_G, HODNOTA_G)</b>: Vloží čtvrtý prvek ({8: G}) jako pravého potomka prvku {7: C}
     * (mohutnost tohoto prvku {8: G} je automaticky {@code 1}). Zvyší mohutnost prvků nad těm nově vloženým prvkem, tj.
     * mohutnost prvku {7: C} a {5: A} o jedničku. Teď mohutnost prvku {7: C} jsou 2 a mohutnost kořene jsou {@code 4}
     * </ol>
     *
     * <p> Přehled stromu:
     *           5(A)
     *          /   \
     *        3(B)  7(C)
     *                \
     *                8(G)
     */
    @Test
    public void test_04_05_mohutnost() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_B, HODNOTA_B);
            strom.vloz(KLIC_C, HODNOTA_C);
            strom.vloz(KLIC_G, HODNOTA_G);

            int resultA = strom.dejMohutnost(KLIC_A);
            int resultB = strom.dejMohutnost(KLIC_B);
            int resultC = strom.dejMohutnost(KLIC_C);
            int resultG = strom.dejMohutnost(KLIC_G);

            int expectedA = 4;
            int expectedB = 1;
            int expectedC = 2;
            int expectedG = 1;

            assertEquals(expectedA, resultA);
            assertEquals(expectedB, resultB);
            assertEquals(expectedC, resultC);
            assertEquals(expectedG, resultG);
        } catch (StromException ex) {
            fail();
        }
    }


    /**
     * Ověřuje, zda iterátor správně prochází strom do hloubky (in-order) a zda prochází prvky ve správném pořadí.
     * Je tedy strom s několika prvky, které byly vloženy v určitém pořadí: {7: C}, {5: A}, {3: B}, {8: G}, {2: D},
     * {6: F}, {4: E}. Očekává, že iterátor projde strom a vrátí prvky ve správném pořadí, tj. {2: D}, {3: B}, {4: E},
     * {5: A}, {6: F}, {7: C}, {8: G}
     *
     * <p> Přehled stromu:
     *           7(C)
     *          /    \
     *         5(A)   8(G)
     *        /    \
     *       3(B)  6(F)
     *      /   \
     *     2(D) 4(E)
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

            String[] expected = {HODNOTA_D, HODNOTA_B, HODNOTA_E, HODNOTA_A, HODNOTA_F, HODNOTA_C, HODNOTA_G};
            Iterator<String> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                String result = iterator.next();
                assertEquals(expected[index], result);
                index++;
            }
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověřuje, zda iterátor správně prochází strom do hloubky (in-order) a zda prochází prvky ve správném pořadí.
     * Nejprve se do stromu přidají tři prvky s různými klíči a hodnotami ({7: C}, {5: A}, {4: E}). Test vytvoří
     * iterátor a očekává, že bude vracet prvky ve správném pořadí ({4: E}, {5: A}, {7: C})
     *
     * <p> Přehled stromu:
     *         7(C)
     *        /
     *       5(A)
     *      /
     *     4(E)
     */
    @Test
    public void test_05_02_vytvorIterator() {
        try {
            strom.vloz(KLIC_C, HODNOTA_C);
            strom.vloz(KLIC_A, HODNOTA_A);
            strom.vloz(KLIC_E, HODNOTA_E);

            String[] expected = {HODNOTA_E, HODNOTA_A, HODNOTA_C};
            Iterator<String> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                String result = iterator.next();
                assertEquals(expected[index], result);
                index++;
            }
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověřuje, zda iterátor správně zachází s prázdným stromem. Test vytvoří iterátor pro prázdný strom a očekává,
     * že {@code hasNext()} vrátí {@code false}, protože neexistuje žádný prvek k procházení
     */
    @Test
    public void test_05_03_vytvorIterator() {
        Iterator<String> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
        boolean expected = iterator.hasNext();
        assertFalse(expected);
    }

    /**
     * Ověřuje, zda iterátor správně pracuje s stromem obsahujícím pouze jeden prvek. Přidá do stromu jeden prvek
     * (klic A, hodnota A). Očekává, že iterátor vrátí tento jediný prvek a {@code hasNext()} bude {@code true}
     * na začátku a {@code false} po vrácení prvku
     *
     * <p> Přehled stromu:
     *         5(A)
     *        /   \
     *      null  null
     */
    @Test
    public void test_05_04_vytvorIterator() {
        try {
            strom.vloz(KLIC_A, HODNOTA_A);

            Iterator<String> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);

            boolean expectedPredNext = iterator.hasNext();
            assertTrue(expectedPredNext);

            String expectedHodnota = HODNOTA_A;
            String resultHodnota = iterator.next();
            assertEquals(expectedHodnota, resultHodnota);

            boolean expectedPoNext = iterator.hasNext();
            assertFalse(expectedPoNext);
        } catch (StromException ex) {
            fail();
        }
    }

    /**
     * Ověří, zda iterátor vrací očekávané hodnoty v pořadí "a", "c", "b", "p", "t", "s", "x", "w"
     *
     * <p> Přehled stromu:
     *         d
     *        / \
     *       /   \
     *      b     x
     *     / \   / \
     *    a   c s   z
     *           \
     *            t
     */
    @Test
    public void test_05_05_vytvorIterator() {
        try {
            IAbstrTable<String, Integer> novyStrom = new AbstrTable<>();

            novyStrom.vloz("d", 1);
            novyStrom.vloz("x", 2);
            novyStrom.vloz("s", 3);
            novyStrom.vloz("b", 4);
            novyStrom.vloz("a", 5);
            novyStrom.vloz("z", 6);
            novyStrom.vloz("t", 7);
            novyStrom.vloz("c", 8);

            int[] expected = {5, 4, 8, 1, 3, 7, 2, 6};
            Iterator<Integer> iterator = novyStrom.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                int result = iterator.next();
                assertEquals(expected[index], result);
                index++;
            }
        } catch (StromException ex) {
            fail();
        }
    }
}
