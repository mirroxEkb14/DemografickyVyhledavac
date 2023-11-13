package cz.upce.fei.bdast.agenda;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.vyjimky.AgendaKrajException;

import java.util.Iterator;

/**
 * Toto rozhraní slouží k abstrakci funkcionalit pro práci s agendou kraje
 */
public interface IAgendaKraj {

    /**
     * Provede import dat z textového souboru
     */
    void importDat();

    /**
     * Vyhledání obce
     *
     * @param nazevObce Název hledané obce
     *
     * @return {@link Obec} s daným názvem
     *
     * @throws AgendaKrajException Pokud obec byla nalezena
     */
    Obec najdi(String nazevObce) throws AgendaKrajException;

    /**
     * Vložení obce
     *
     * @param obec Obec pro vkládání
     *
     * @throws AgendaKrajException Pokud je klíč prázdný
     */
    void vloz(Obec obec) throws AgendaKrajException;

    /**
     * Odebrání obce
     *
     * @param nazevObce Název hledané obce
     *
     * @return {@link Obec} s daným názvem
     *
     * @throws AgendaKrajException Pokud je klíč (nazev obce) anebo celý strom (kořen) prázdný
     */
    Obec odeber(String nazevObce) throws AgendaKrajException;

    /**
     * Vrací iterátor tabulky
     *
     * @return {@link Iterator} buď s hledáním do šířky anebo do hloubky
     */
    Iterator<Obec> vytvorIterator();

    /**
     * Umožnuje generovat jednotlivé obce
     *
     * @param pocet Počet obcí pro generování
     */
    void generuj(int pocet);
}
