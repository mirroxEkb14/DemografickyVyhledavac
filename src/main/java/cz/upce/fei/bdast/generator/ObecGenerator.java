package cz.upce.fei.bdast.generator;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.vyjimky.StromException;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Tato třída implementuje rozhraní {@link Generator} a slouží k generování náhodných dat obcí a jejich ukládání
 * do datové struktury binární strom {@link IAbstrTable}
 */
public final class ObecGenerator implements Generator {

    private final Random random = new Random();

    private final int NULTA_HODNOTA = 0;

    private final String[] KRAJE = {"Hlavni mesto Praha", "Jihocesky", "Jihomoravsky", "Karlovarsky", "Kraj Vysocina",
            "Kralovehradecky", "Liberecky", "Moravskoslezsky", "Olomoucky", "Pardubicky", "Plzensky", "Stredocesky",
            "Ustecky", "Zlinsky"};

    @Override
    public void generuj(@NotNull IAbstrTable<String, Obec> strom, int pocet) {
        do {
            final int cisloKraje = generujNahodneCisloKraje();
            final String nazevKraje = generujNazevKraje(cisloKraje);
            final String nazevObce = generujUnikatniNazevObce(strom);
            final String psc = generujNahodnePsc(cisloKraje);
            final int pocetMuzu = generujNahodnyPocet();
            final int pocetZen = generujNahodnyPocet();
            final int celkem = pocetMuzu + pocetZen;

            final Obec obec = new Obec(cisloKraje,
                    nazevKraje,
                    nazevObce,
                    psc,
                    pocetMuzu,
                    pocetZen,
                    celkem);
            try {
                strom.vloz(nazevObce, obec);
            } catch (StromException ignored) {}
        } while (--pocet != NULTA_HODNOTA);
    }

    /**
     * Generuje náhodné číslo kraje
     *
     * @return Náhodně vygenerované čísla
     */
    private int generujNahodneCisloKraje() {
        return random.nextInt(Generator.CISLO_KRAJE_MIN, Generator.CISLO_KRAJE_MAX);
    }

    /**
     * Vratí název kraje podle jeho čísla
     *
     * @param cisloKraje Číslo vygenerované metodou {@link ObecGenerator#generujNahodneCisloKraje()}
     *
     * @return Řetězec podle předpisu s císlem kraje
     */
    private @NotNull String generujNazevKraje(int cisloKraje) {
        return KRAJE[--cisloKraje];
    }

    /**
     * Generuje unikátní název obce s použitím náhodného generovaného čísla kraje
     *
     * @param strom Strom, ve kterém se kontroluje unikátnost názvu obce
     *
     * @return Unikátní řetězec podle předpisu s císlem kraje
     */
    private @NotNull String generujUnikatniNazevObce(@NotNull IAbstrTable<String, Obec> strom) {
        String nazevObce;
        do {
            nazevObce = generujNahodnyNazevObce();
        } while (strom.obsahuje(nazevObce));
        return nazevObce;
    }

    /**
     * Vytvoří název "ObecX" s použitím náhodného generovaného čísla kraje
     *
     * @return Řetězec podle předpisu s císlem kraje
     */
    private @NotNull String generujNahodnyNazevObce() {
        return Generator.NAZEV_OBCE_PREDPIS + random.nextInt(Generator.CISLO_NAZVU_OBCE_MAX);
    }

    /**
     * Generuje náhodné PSC
     *
     * @param cisloKraje Číslo vygenerované pomocí {@link ObecGenerator#generujNahodneCisloKraje()}
     *
     * @return Řetězec podle předpisu s císly kraje reprezentující její PSC
     */
    private @NotNull String generujNahodnePsc(int cisloKraje) {
        return Generator.PSC_PREDPIS.replaceAll(
                Generator.NAHRADNY_BIT,
                String.valueOf(cisloKraje));
    }

    /**
     * Generuje náhodný počet buď mužů anebo žen
     *
     * @return Náhodný počet lidí (mužů/žen)
     */
    private int generujNahodnyPocet() {
        return random.nextInt(Generator.POCET_LIDI_MAX);
    }
}
