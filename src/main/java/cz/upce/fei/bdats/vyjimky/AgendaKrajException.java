package cz.upce.fei.bdats.vyjimky;

/**
 * Třída je odvozena od třídy {@link Exception} a slouží k definici vlastního typu výjimky, který je
 * specifický pro chyby v rámci této aplikace pracující s agendou kraje (k zachycení chyb a výjimek)
 */
public final class AgendaKrajException extends Exception {

    public AgendaKrajException(String message) {
        super(message);
    }
}
