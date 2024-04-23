package cz.upce.fei.bdats.gui.dialogy;

import cz.upce.fei.bdats.gui.Titulek;

/**
 * Třída reprezentuje dialog pro zadávání klíče, podle kterého se ze stromu smaže uzel
 *
 * @see DialogKlic
 */
public final class DialogOdebirani extends DialogKlic {

    /**
     * Inicializuje dialog s předdefinovanými titulky
     */
    public DialogOdebirani() {
        super(Titulek.HLAVICKA_DIALOGU_ODEBIRANI.getNadpis(),
                Titulek.HLAVICKA_TITULKOVEHO_PANELU_ODEBIRANI.getNadpis());
    }
}
