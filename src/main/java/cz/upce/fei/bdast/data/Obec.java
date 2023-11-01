package cz.upce.fei.bdast.data;

/**
 * Třída reprezentuje obec nebo územní jednotku a obsahuje statistická data o této obci. Je nezměnitelná
 * (immutable), což znamená, že její stav nelze po vytvoření změnit
 */
public final class Obec {

    private final int cisloKraje;
    private final String psc;
    private final int pocetMuzu;
    private final int pocetZen;
    private final int celkem;

    /**
     * Konstruktor inicializuje statistická data obce
     */
    public Obec(int cisloKraje,
                String psc,
                int pocetMuzu,
                int pocetZen,
                int celkem) {
        this.cisloKraje = cisloKraje;
        this.psc = psc;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = celkem;
    }

/**
 * Gettery pro získání atributů této třídy
 *
 * @return Vrací hodnoty příslušných atributů
 */
// <editor-fold defaultstate="collapsed" desc="Gettery">
    public int getCisloKraje() { return cisloKraje; }

    public String getPsc() { return psc; }

    public int getPocetMuzu() { return pocetMuzu; }

    public int getPocetZen() { return pocetZen; }

    public int getCelkem() { return celkem; }
// </editor-fold>
}
