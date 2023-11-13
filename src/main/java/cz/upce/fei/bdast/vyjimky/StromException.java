package cz.upce.fei.bdast.vyjimky;

/**
 * Výjimková třída, která rozšiřuje základní obecnou třídu výjimky {@link Exception} aslouží k reprezentaci
 * výjimek, které mohou nastat při práci s datovou strukturou Strom
 */
public final class StromException extends Exception {

    /**
     * Konstruktor předává chybovou zprávu konstruktoru předka {@link Exception}, čímž je možné
     * nastavit popis chyby pro tuto výjimku
     *
     * @param zprava Řetězec jako popis chyby
     */
    public StromException(String zprava) { super(zprava); }
}
