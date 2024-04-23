package cz.upce.fei.bdats.gui.tvurce;

import cz.upce.fei.bdats.data.Obec;
import cz.upce.fei.bdats.gui.dialogy.DialogVlozeni;
import cz.upce.fei.bdats.gui.dialogy.DialogovyKomponent;
import cz.upce.fei.bdats.vyjimky.CeleCisloException;
import cz.upce.fei.bdats.vyjimky.PrazdnyRetezecException;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Třída obsahuje metody pro vytvoření nové obce podle údajů zadanými uživateli přes {@link DialogovyKomponent}
 */
public final class TvurceObce implements Tvoritelny<Obec> {

    /**
     * <b>Poznámka</b>: U této implementace nedochází k ověření názvu obce na prázdnost a unikátnost - to se musí
     * provést před voláním této metody
     */
    @Override
    public Optional<Obec> vytvor(@NotNull DialogovyKomponent dialog) {
        try {
            if (dialog instanceof DialogVlozeni dialogVlozeni) {
                final int cisloKraje = this.dejCeleCislo(dialogVlozeni.getTfCislo().getText());
                final String nazevObce = dialogVlozeni.getTfNazevObce().getText();
                final String nazevKraje = dialogVlozeni.getTfNazevKraje().getText();
                final String pscObce = this.dejNeprazdnyRetezec(dialogVlozeni.getTfPSC().getText());
                final int pocetMuzu = this.dejCeleCislo(dialogVlozeni.getTfPocetMuzu().getText());
                final int pocetZen = this.dejCeleCislo(dialogVlozeni.getTfPocetZen().getText());
                final int celkem = pocetMuzu + pocetZen;

                return Optional.of(new Obec(
                        cisloKraje,
                        nazevKraje,
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
