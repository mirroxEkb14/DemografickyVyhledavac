package cz.upce.fei.bdats.perzistence;

import cz.upce.fei.bdats.data.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.strom.IAbstrTable;
import cz.upce.fei.bdats.vyjimky.StromException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Iterator;

/**
 * Implementace rozhraní {@link IPerzistence} pro perzistenci dat obcí do/z CSV souboru
 */
public final class ObecPerzistence implements IPerzistence<String, Obec> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean nactiCsv(IAbstrTable<String, Obec> strom, String cesta) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(cesta))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] data = line.split(this.ODDELOVAC_ATRIBUTU);
                if (data.length == this.CELKEM_ATRIBUTU) {
                    final int cisloKraje = Integer.parseInt(data[this.INDEX_ATRIBUTU_CISLO_KRAJE]);
                    final String nazevKraje = data[this.INDEX_ATRIBUTU_NAZEV_KRAJE];
                    final String nazevObce = data[this.INDEX_ATRIBUTU_NAZEV_OBCE];
                    final String psc = data[this.INDEX_ATRIBUTU_PSC];
                    final int pocetMuzu = Integer.parseInt(data[this.INDEX_ATRIBUTU_POCET_MUZU]);
                    final int pocetZen = Integer.parseInt(data[this.INDEX_ATRIBUTU_POCET_ZEN]);
                    final int celkem = Integer.parseInt(data[this.INDEX_ATRIBUTU_CELKEM]);

                    Obec obec = new Obec(cisloKraje,
                            nazevKraje,
                            nazevObce,
                            psc,
                            pocetMuzu,
                            pocetZen,
                            celkem);
                    strom.vloz(nazevObce, obec);
                } else {
                    return false;
                }
            }
        } catch (StromException e) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean ulozCsv(@NotNull IAbstrTable<String, Obec> strom) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(IPerzistence.CESTA_ULOZISTE))) {
            final Iterator<Obec> iterator = strom.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                final Obec obec = iterator.next();
                writer.write(obec.getCisloKraje() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getNazevKraje() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getPsc() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getNazevObce() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getPocetMuzu() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getPocetZen() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getCelkem() + this.ODDELOVAC_ATRIBUTU);
                writer.newLine();
            }
        }
        return true;
    }
}
