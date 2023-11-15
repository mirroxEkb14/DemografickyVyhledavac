package cz.upce.fei.bdast.agenda;

import cz.upce.fei.bdast.data.Obec;
import cz.upce.fei.bdast.strom.ETypProhl;
import cz.upce.fei.bdast.strom.IAbstrTable;
import cz.upce.fei.bdast.vyjimky.AgendaKrajException;
import cz.upce.fei.bdast.vyjimky.StromException;

import java.util.Iterator;

/**
 * Toto rozhraní slouží k abstrakci funkcionalit pro práci s agendou kraje
 *
 * @param <K> Klíč (název obce typu {@link String})
 * @param <V> Hodnota (instance {@link Obec})
 */
public interface IAgendaKraj<K extends Comparable<K>, V> {

    /**
     * Provede import dat z textového souboru
     *
     * @param cesta Cesta do .csv souboru
     *
     * @return {@code true}, pokud nečtení proběhlo úspěšně, v případě vyhození výjinky - {@code false}
     */
    boolean importDat(String cesta);

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
     * @param typ Typ prohlížení
     *
     * @return {@link Iterator} buď s hledáním do šířky anebo do hloubky
     */
    Iterator<Obec> vytvorIterator(ETypProhl typ);

    /**
     * Umožnuje generovat jednotlivé obce
     *
     * @param pocet Počet obcí pro generování
     */
    void generuj(int pocet);

    /**
     * Vratí nově vytvořenou instanci stromu se stejným obsahem
     *
     * @return Nová instance binárního vyhledávacího stromu
     *
     * @throws AgendaKrajException Když se vyhodí výjimka při kopirování obsahu výchozích stromu, tj. při
     * vkládání prvků - {@link StromException}
     */
    IAbstrTable<K, V> dejInstanceStromu() throws AgendaKrajException;

    /**
     * Zruší celý strom
     */
    void zrus();

    /**
     * Vratí text s vnitřním uspořádání binárního stromu podle zvoleného typu prohlížení
     *
     * @return Textový řetězec s popisem struktury stromu
     */
    String vypisStrom(ETypProhl typ);
}
