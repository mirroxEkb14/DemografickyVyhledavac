package cz.upce.fei.bdast.agenda;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.generator.Generator;
import cz.upce.fei.bdast.generator.ObecGenerator;
import cz.upce.fei.bdast.strom.AbstrTable;
import cz.upce.fei.bdast.strom.ETypProhl;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.vyjimky.AgendaKrajException;
import cz.upce.fei.bdast.vyjimky.StromException;
import cz.upce.fei.bdast.vyjimky.zpravy.ChybovaZpravaKraje;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * Tato třída slouží k manipulaci s daty o obcích kraje. Slouží také jako rozhraní mezi uživatelem a datovou
 * strukturou, která uchovává informace o obcích v kraji. Implementuje základní operace pro manipulaci s
 * obcemi
 *
 * <p>Třída používá Singleton návrhový vzor, čímž zabezpečuje jedinou instanci v rámci aplikace
 */
public final class AgendaKraj implements IAgendaKraj {

    private static AgendaKraj instance;

    private IAbstrTable<String, Obec> strom;
    private Generator obecGenerator;

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

    private AgendaKraj() { nastav(); }

    private void nastav() {
        this.strom = new AbstrTable<>();
        this.obecGenerator = new ObecGenerator();
    }

    @Override
    public void importDat() {

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
            strom.vloz(obec.getNazev(), obec);
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
    public Iterator<Obec> vytvorIterator() {
        return strom.vytvorIterator(ETypProhl.HLOUBKA);
    }

    @Override
    public void generuj(int pocet) {
        obecGenerator.generuj(strom, pocet);
    }
}
