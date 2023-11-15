package cz.upce.fei.bdast.generator;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.strom.IAbstrTable;

/**
 * Toto rozhraní definuje metodu pro generování dat obcí a jejich vkládání do datové struktury
 */
public interface Generator {

    /**
     * Maximální hodnota pro číslo kraje při generování náhodných dat
     *
     * <p> Krajů je {@code 14}, ale při generování se používá metoda {@link java.util.Random#nextInt(int)}, které
     * generuje náhodné číslo od {@code 1} (včetně) po {@code int} (vyloučeno)
     */
    int CISLO_KRAJE_MAX = 15;
    int CISLO_KRAJE_MIN = 1;
    /**
     * Název obce má podobu "ObecX," kde x - číslo v rozsahu 1 až 999
     */
    int CISLO_NAZVU_OBCE_MAX = 1000;
    /**
     * Maximální hodnota pro počet lidí
     */
    int POCET_LIDI_MAX = 1000;
    /**
     * Předpis pro název obce, který bude použit při generování názvů obcí
     */
    String NAZEV_OBCE_PREDPIS = "Obec";
    /**
     * Předpis pro PSČ (Poštovní směrovací číslo)
     */
    String PSC_PREDPIS = "XXX XX";
    /**
     * Symbol, který bude použit jako náhrada pro neznámé hodnoty v PSČ ("X").
     */
    String NAHRADNY_BIT = "X";

    /**
     * Generuje náhodná data pro obce a vkládá je do datové struktury strom.
     *
     * @param strom Binární strom pro ukládání obcí
     * @param pocet Počet obcí, které mají být vygenerovány a vloženy
     */
    void generuj(IAbstrTable<String, Obec> strom, int pocet);
}
