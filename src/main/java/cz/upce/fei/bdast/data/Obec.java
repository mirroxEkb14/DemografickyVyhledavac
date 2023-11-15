package cz.upce.fei.bdast.data;

import org.jetbrains.annotations.NotNull;

/**
 * Třída reprezentuje obec nebo územní jednotku a obsahuje statistická data o této obci. Je nezměnitelná
 * (immutable), což znamená, že její stav nelze po vytvoření změnit
 */
public final class Obec {

    /**
     * Sada privátních tribubů
     */
    private final int cisloKraje;
    private final String nazevKraje;
    private final String nazevObce;
    private final String psc;
    private final int pocetMuzu;
    private final int pocetZen;
    private final int celkem;

    /**
     * Konstruktor inicializuje statistická data obce
     */
    public Obec(int cisloKraje,
                String nazevKraje,
                String nazevObce,
                String psc,
                int pocetMuzu,
                int pocetZen,
                int celkem) {
        this.cisloKraje = cisloKraje;
        this.nazevKraje = nazevKraje;
        this.nazevObce = nazevObce;
        this.psc = psc;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = celkem;
    }

    @Override
    public @NotNull String toString() {
        return "Obec{" +
                "cisloKraje=" + cisloKraje +
                ", nazevKraje='" + nazevKraje + '\'' +
                ", nazevObce='" + nazevObce + '\'' +
                ", psc='" + psc + '\'' +
                ", pocetMuzu=" + pocetMuzu +
                ", pocetZen=" + pocetZen +
                ", celkem=" + celkem +
                '}';
    }

/**
 * Gettery pro získání atributů této třídy
 *
 * @return Vrací hodnoty příslušných atributů
 */
// <editor-fold defaultstate="collapsed" desc="Gettery">
    public int getCisloKraje() { return cisloKraje; }

    public String getNazevKraje() { return nazevKraje; }

    public String getNazevObce() { return nazevObce; }

    public String getPsc() { return psc; }

    public int getPocetMuzu() { return pocetMuzu; }

    public int getPocetZen() { return pocetZen; }

    public int getCelkem() { return celkem; }
// </editor-fold>
}
