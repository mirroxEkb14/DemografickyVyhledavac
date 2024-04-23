package cz.upce.fei.bdats.vyjimky;

/**
 * Vyjímka představující chybu v seznamovém panelu používající se pro signalizaci neúspěšných operací v seznamu
 */
public final class SeznamPanelException extends Exception {

    public SeznamPanelException(String message) { super(message); }
}
