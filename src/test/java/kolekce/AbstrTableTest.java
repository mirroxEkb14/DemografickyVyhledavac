package kolekce;

import cz.upce.fei.bdast.seznam.AbstrDoubleList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author amirov 10/30/2023
 * @project semestralni_prace_b_amirov
 *
 * 1. v IAbstrTable potřebuju: extends Iterable<K> ?
 * 2. throws StromException
 * 3. AbstrFifo<T> NEBO AbstrFifo<K extends Comparable<K> V>
 * 4. HloubkaIterator a SirkaIterator v rámci AbstrTable?
 * 5. vrchol - zpristupniPrvni() nebo zpristupniPosledni()
 */
public class AbstrTableTest {

    /**
     * Testovací třída pro ověření implementace třídy {@link cz.upce.fei.bdast.seznam.AbstrDoubleList}.
     */
    private static class TestClass {
        int cisloInstance;

        public TestClass(int cisloInstance) { this.cisloInstance = cisloInstance; }

        @Override
        public String toString() { return "T" + cisloInstance; }

    }

    /**
     * Sada instancí testovací třídy.
     */
    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);

    private AbstrDoubleList<TestClass> instance;

    public AbstrTableTest() {}

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() { instance = new AbstrDoubleList<>(); }

    @After
    public void tearDown() { instance = null; }
}
