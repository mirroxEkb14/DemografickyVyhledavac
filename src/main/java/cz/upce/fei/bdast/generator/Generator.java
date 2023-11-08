package cz.upce.fei.bdast.generator;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.strom.IAbstrTable;

/**
 * Toto rozhraní definuje metodu pro generování dat obcí a jejich vkládání do datové struktury
 */
public interface Generator {

    /**
     * Maximální hodnota pro číslo kraje při generování náhodných dat
     */
    int CISLO_KRAJE_MAX = 100;
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
