package cz.upce.fei.bdast.agenda;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.generator.Generator;
import cz.upce.fei.bdast.generator.ObecGenerator;
import cz.upce.fei.bdast.perzistence.IPerzistence;
import cz.upce.fei.bdast.perzistence.ObecPerzistence;
import cz.upce.fei.bdast.strom.AbstrTable;
import cz.upce.fei.bdast.strom.ETypProhl;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.vyjimky.AgendaKrajException;
import cz.upce.fei.bdast.vyjimky.StromException;
import cz.upce.fei.bdast.vyjimky.zpravy.ChybovaZpravaKraje;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Iterator;
// </editor-fold>

/**
 * Tato třída slouží k manipulaci s daty o obcích kraje. Slouží také jako rozhraní mezi uživatelem a datovou
 * strukturou, která uchovává informace o obcích v kraji. Implementuje základní operace pro manipulaci s
 * obcemi
 *
 * <p>Třída používá Singleton návrhový vzor, čímž zabezpečuje jedinou instanci v rámci aplikace
 */
public final class AgendaKraj implements IAgendaKraj<String, Obec> {

    private IAbstrTable<String, Obec> strom;
    private Generator obecGenerator;
    private IPerzistence<String, Obec> perzistence;

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static AgendaKraj instance;

    /**
     * Tovární metoda (factory method) pro vytvoření a získání instance Singletonu
     *
     * @return vratí buď již existující anebo nově vytvořenou instanci
     */
    public static AgendaKraj getInstance() {
        if (instance == null)
            instance = new AgendaKraj();
        return instance;
    }
// </editor-fold>

    private AgendaKraj() { nastav(); }

    private void nastav() {
        this.strom = new AbstrTable<>();
        this.obecGenerator = new ObecGenerator();
        this.perzistence = new ObecPerzistence();
    }

    @Override
    public boolean importDat(String cesta) {
        try {
            return perzistence.nactiCsv(strom, cesta);
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public boolean exportDat() {
        try {
            return perzistence.ulozCsv(strom);
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public Obec najdi(String nazevObce) throws AgendaKrajException {
        try {
            return strom.najdi(nazevObce);
        } catch (StromException ex) {
            throw new AgendaKrajException(ChybovaZpravaKraje.PRVEK_NENALEZEN.getZprava());
        }
    }

    @Override
    public void vloz(@NotNull Obec obec) throws AgendaKrajException {
        try {
            strom.vloz(obec.getNazevObce(), obec);
        } catch (StromException ignored) {
            throw new AgendaKrajException(ChybovaZpravaKraje.NULL_KLIC.getZprava());
        }
    }

    @Override
    public Obec odeber(String nazevObce) throws AgendaKrajException {
        try {
            return strom.odeber(nazevObce);
        } catch (StromException ex) {
            throw new AgendaKrajException(ChybovaZpravaKraje.PRVEK_NENALEZEN.getZprava());
        }
    }

    @Override
    public Iterator<Obec> vytvorIterator(ETypProhl typ) {
        return strom.vytvorIterator(typ);
    }

    @Override
    public void generuj(int pocet) {
        obecGenerator.generuj(strom, pocet);
    }

    @Override
    public @NotNull IAbstrTable<String, Obec> dejInstanceStromu() throws AgendaKrajException {
        final IAbstrTable<String, Obec> novaInstance = new AbstrTable<>();
        try {
            final Iterator<Obec> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                final Obec obec = iterator.next();
                novaInstance.vloz(obec.getNazevObce(), obec);
            }
            return novaInstance;
        } catch (StromException ex) {
            throw new AgendaKrajException(ChybovaZpravaKraje.DUPLICITNI_KLIC.getZprava());
        }
    }

    @Override
    public void zrus() { strom.zrus(); }

// <editor-fold defaultstate="collapsed" desc="Metoda: String VypisStrom(ETypProhl typ)">
    private final String POPISEK_VYSTUPU = "Posloupnost výstupu: ";
    private final String ODDELOVAC_VYSTUPU = ", ";

    @Override
    public @NotNull String vypisStrom(ETypProhl typ) {
        return strom.vypisStrom(typ) + dejVystupPosloupnosti(typ);
    }

    /**
     * Vrací textovou posloupnost prvků stromu v zadaném typu průchodu
     *
     * @param typ Typ průchodu stromem ({@link ETypProhl#SIRKA} nebo {@link ETypProhl#HLOUBKA})
     *
     * @return Textový výstup posloupnosti prvků stromu
     */
    private @NotNull String dejVystupPosloupnosti(ETypProhl typ) {
        final StringBuilder sb = new StringBuilder(POPISEK_VYSTUPU);
        final Iterator<Obec> iterator = strom.vytvorIterator(typ);
        while (iterator.hasNext()) {
            sb.append(iterator.next().getNazevObce());
            if (iterator.hasNext())
                sb.append(ODDELOVAC_VYSTUPU);
        }
        return sb.toString();
    }
// </editor-fold>
}
