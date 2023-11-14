package cz.upce.fei.bdast.gui.tvurce;

import cz.upce.fei.bdast.gui.dialogy.DialogovyKomponent;
import cz.upce.fei.bdast.vyjimky.CeleCisloException;
import cz.upce.fei.bdast.vyjimky.PrazdnyRetezecException;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Rozhraní definuje tvorbu objektů. Implementující třídy implementují metodu pro vytvoření nového objektu
 *
 * @param <T> Typ objektu, který má být vytvořen
 */
public interface Tvoritelny<T> {

    int NULTA_HODNOTA = 0;
    /**
     * Validátor pro ověření, zda zadaný řetězec obsahuje celé kladné číslo větší než nula
     */
    Predicate<String> validatorCelychCisel = t -> {
        if (t.isEmpty()) {
            return false;
        }
        try {
            final int cislo = Integer.parseInt(t);
            return cislo > NULTA_HODNOTA;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    /**
     * Validátor ověří, zda vstupní řetězec je prázdný nebo není
     */
    Predicate<String> validatorPrazdnychRetezcu = String::isEmpty;

    /**
     * Vytvoří objekt typu {@link T}
     *
     * @param dialog Instance dialogu, z něhož se budou volat gettery pro zjišťování dat zadaných uživatelem
     *
     * @return Instance vytvořeného objektu nebo prázdná hodnota {@link Optional}, pokud není možné objekt vytvořit
     * kvůli špatně zadaným údajů uživatelem
     */
    Optional<T> vytvor(DialogovyKomponent dialog);

    /**
     * Převede zadaný řetězec na celé číslo pomocí validátoru {@link Tvoritelny#validatorCelychCisel} pro ověření,
     * zda je zadaný řetězec platným celým kladným číslem větším než nula
     *
     * @param retezec Řetězec, který má být převeden na celé číslo
     *
     * @return Převedené celé číslo
     *
     * @throws CeleCisloException Když zadaný řetězec není platným celým kladným číslem větším než nula
     */
    default int dejCeleCislo(String retezec) throws CeleCisloException {
        if (validatorCelychCisel.test(retezec))
            return Integer.parseInt(retezec);
        throw new CeleCisloException();
    }

    /**
     * Vratí ten samý textový řetězec, pokud není prázdný
     *
     * @param retezec Řetězec, který má být ověřen na prázdnost a být vracen
     *
     * @return Vstupní textový řetězec
     *
     * @throws PrazdnyRetezecException Když je zadaný řetězec prázdný
     *
     */
    default String dejNeprazdnyRetezec(String retezec) throws PrazdnyRetezecException {
        if (!validatorPrazdnychRetezcu.test(retezec))
            return retezec;
        throw new PrazdnyRetezecException();
    }
}
