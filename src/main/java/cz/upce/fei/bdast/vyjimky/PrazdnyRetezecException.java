package cz.upce.fei.bdast.vyjimky;

/**
 * Tato třída je výjimkou vyvolávající se v případě prázdného řetězce
 */
public final class PrazdnyRetezecException extends Exception {

    public PrazdnyRetezecException() { super("Špatně zadaný textový řetězec"); }
}
