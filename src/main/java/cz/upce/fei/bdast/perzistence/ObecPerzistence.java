package cz.upce.fei.bdast.perzistence;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.vyjimky.StromException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    public boolean ulozCsv(IAbstrTable<String, Obec> strom) throws IOException {
        return false;
    }
}
