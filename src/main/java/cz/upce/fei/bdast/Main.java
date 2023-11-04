package cz.upce.fei.bdast;

import cz.upce.fei.bdast.strom.AbstrTable;
import cz.upce.fei.bdast.strom.StromException;
import cz.upce.fei.bdast.struktury.StrukturaException;

/**
 * @author amirov 11/2/2023
 * @project semestralni_prace_b_amirov
 */
public class Main {

    public static void main(String[] args) throws StrukturaException {
        final AbstrTable<Integer, String> strom = new AbstrTable<>();
        strom.vloz(5, "A");
        strom.vloz(3, "B");
        strom.vloz(7, "C");
        strom.vloz(2, "D");
        strom.vloz(4, "E");
        strom.vloz(6, "F");
        strom.vloz(8, "G");

        System.out.println("Strom před odebráním:");
        System.out.println(strom.vypisStrom());

        try {
            strom.odeber(3); // Odebrání uzlu s klíčem 3
            System.out.println("Strom po odebrání uzlu s klíčem 3:");
            System.out.println(strom.vypisStrom());
        } catch (StromException e) {
            System.err.println("Chyba při odebírání uzlu: Uzel nebyl nalezen.");
        }
    }

}
