package cz.upce.fei.bdast.gui.tvurce;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.gui.dialogy.DialogVlozeni;
import cz.upce.fei.bdast.gui.dialogy.DialogovyKomponent;
import cz.upce.fei.bdast.vyjimky.CeleCisloException;
import cz.upce.fei.bdast.vyjimky.PrazdnyRetezecException;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Třída obsahuje metody pro vytvoření nové obce podle údajů zadanými uživateli přes {@link DialogovyKomponent}
 */
public final class TvurceObce implements Tvoritelny<Obec> {

    @Override
    public Optional<Obec> vytvor(@NotNull DialogovyKomponent dialog) {
        try {
            if (dialog instanceof DialogVlozeni dialogVlozeni) {
                final int cisloObce = this.dejCeleCislo(dialogVlozeni.getTfCislo().getText());
                final String nazevObce = this.dejNeprazdnyRetezec(dialogVlozeni.getTfNazev().getText());
                final String pscObce = this.dejNeprazdnyRetezec(dialogVlozeni.getTfPSC().getText());
                final int pocetMuzu = this.dejCeleCislo(dialogVlozeni.getTfPocetMuzu().getText());
                final int pocetZen = this.dejCeleCislo(dialogVlozeni.getTfPocetZen().getText());
                final int celkem = pocetMuzu + pocetZen;

                return Optional.of(new Obec(
                        cisloObce,
                        nazevObce,
                        pscObce,
                        pocetMuzu,
                        pocetZen,
                        celkem));
            }
            return Optional.empty();
        } catch (CeleCisloException | PrazdnyRetezecException ex) {
            return Optional.empty();
        }
    }
}
