package kolekce;

import cz.upce.fei.bdast.strom.AbstrTable;
import cz.upce.fei.bdast.strom.ChybovaZprava;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.strom.StromException;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Abychom mohli pokrýt všechny řádky metody najdi(), musíme napsat testy pro různé scénáře:
 * <ol>
 * <li> <b>test_01_</b> Scénáře metody {@link AbstrTable#najdi(Comparable)}
 * <li> <b>test_02_</b> Scénáře metody {@link AbstrTable#vloz(Comparable, Object)}}
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
            assertEquals(ChybovaZprava.PRVEK_NENALEZEN.getZprava(), ex.getMessage());
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
            assertEquals(ChybovaZprava.NULL_KLIC.getZprava(), ex.getMessage());
        }
    }

    /**
     * Test pro vyhledání klíče v prázdném stromu
     */
    @Test
    public void test_01_06_najdi() {
        try {
            String result = strom.najdi(KLIC_A);
            fail(NEOCEKAVANY_STROM_EXCEPTION);
        } catch (StromException ex) {
            assertEquals(ChybovaZprava.PRVEK_NENALEZEN.getZprava(), ex.getMessage());
        }
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
            strom.vloz(KLIC_A, HODNOTA_B); // Aktualizace hodnoty pro existující klíč
            String result = strom.najdi(KLIC_A);
            String expected = HODNOTA_B;
            assertEquals(expected, result);
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
}
