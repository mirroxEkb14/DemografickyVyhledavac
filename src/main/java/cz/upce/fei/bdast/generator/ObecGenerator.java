package cz.upce.fei.bdast.generator;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.strom.ETypProhl;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.strom.StromException;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Random;

/**
 * Tato třída implementuje rozhraní {@link Generator} a slouží k generování náhodných dat obcí a jejich ukládání
 * do datové struktury binární strom {@link IAbstrTable}
 */
public final class ObecGenerator implements Generator {

    private final Random random = new Random();

    private final int NULTA_HODNOTA = 0;

    @Override
    public void generuj(@NotNull IAbstrTable<String, Obec> strom, int pocet) {
        do {
            final int cisloKraje = generujNahodneCisloObce(strom);
            final String nazev = generujNahodnyNazev(cisloKraje);
            final String psc = generujNahodnePsc(cisloKraje);
            final int pocetMuzu = generujNahodnyPocet();
            final int pocetZen = generujNahodnyPocet();
            final int celkem = pocetMuzu + pocetZen;

            final Obec obec = new Obec(cisloKraje,
                    nazev,
                    psc,
                    pocetMuzu,
                    pocetZen,
                    celkem);
            try {
                strom.vloz(nazev, obec);
            } catch (StromException ignored) {}
        } while (--pocet > NULTA_HODNOTA);
    }

    /**
     * Generuje náhodné číslo obce
     *
     * @param strom Strom pro ověření opakování vygenerovaného čísla
     *
     * @return Náhodně vygenerované čísla
     */
    private int generujNahodneCisloObce(@NotNull IAbstrTable<String, Obec> strom) {
        int cisloObce;
        boolean jeUnikatni;
        do {
            jeUnikatni = true;
            cisloObce = random.nextInt(Generator.CISLO_KRAJE_MAX);

            final Iterator<Obec> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                final Obec obec = iterator.next();
                if (obec.getCisloKraje() == cisloObce) {
                    jeUnikatni = false;
                    break;
                }
            }
        } while (!jeUnikatni);
        return cisloObce;
    }

    /**
     * Vytvořte název "ObecX" s použitím náhodného generovaného unikátního čísla obce
     *
     * @param unikatniCisloObce Unikátní číslo vygenerované metodou {@link ObecGenerator#generujNahodneCisloObce(IAbstrTable)}
     *
     * @return Řetězec podle předpisu s unikátním císlem obce
     */
    private @NotNull String generujNahodnyNazev(int unikatniCisloObce) {
        return Generator.NAZEV_OBCE_PREDPIS + unikatniCisloObce;
    }

    /**
     * Generuje náhodné PSC
     *
     * @param unikatniCisloObce Unikátní číslo vygenerované pomocí {@link ObecGenerator#generujNahodneCisloObce(IAbstrTable)}
     *
     * @return Řetězec podle předpisu s unikátními císly obce reprezentující její PSC
     */
    private @NotNull String generujNahodnePsc(int unikatniCisloObce) {
        return Generator.PSC_PREDPIS.replaceAll(
                Generator.NAHRADNY_BIT,
                String.valueOf(unikatniCisloObce));
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
