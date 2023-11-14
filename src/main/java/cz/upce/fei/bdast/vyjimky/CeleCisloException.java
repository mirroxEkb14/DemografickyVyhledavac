package cz.upce.fei.bdast.vyjimky;

/**
 * Tato třída reprezentuje vlastní implementaci výjimky {@link NumberFormatException} používající se
 * v případě špatně zadané hodnoty {@link String} pro celé číslo {@link Integer}
 */
public final class CeleCisloException extends Exception {

    public CeleCisloException() { super("Špatně zadané celé číslo"); }
}
