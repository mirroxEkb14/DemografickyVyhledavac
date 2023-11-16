package cz.upce.fei.bdast.data;

import org.jetbrains.annotations.NotNull;

import java.io.ObjectStreamClass;
import java.io.Serial;
import java.io.Serializable;

/**
 * Třída reprezentuje obec nebo územní jednotku a obsahuje statistická data o této obci. Je nezměnitelná
 * (immutable), což znamená, že její stav nelze po vytvoření změnit
 *
 * <p> Třída implementuje rozhraní {@link Serializable}, což znamená, že instance této třídy mohou být
 * serializovány. Serializace - je proces převodu objektu do sekvence bajtů, která může být uložena do souboru,
 * přenesena přes síť nebo uložena v paměti
 */
public final class Obec implements Serializable {

    /**
     * Speciální identifikátor, který se používá během serializace a deserializace objektů. Při serializaci je
     * tento identifikátor uložen spolu s objektem a při deserializaci je porovnán s aktuální verzí třídy.
     * Pokud se {@code serialVersionUID} neshoduje, může dojít k chybě deserializace
     */
    @Serial
    private static final long serialVersionUID = ObjectStreamClass.lookup(Obec.class).getSerialVersionUID();

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
