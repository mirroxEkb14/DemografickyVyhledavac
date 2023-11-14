package cz.upce.fei.bdast.gui.dialogy;

import cz.upce.fei.bdast.gui.Titulek;

/**
 * Tato třída reprezentuje dialogové okénko pro zadávání uživatelem názvu obce (klíče), který chce najít
 *
 * @see DialogKlic
 */
public final class DialogNalezeni extends DialogKlic {

    /**
     * Inicializuje dialog s předdefinovanými titulky
     */
    public DialogNalezeni() {
        super(Titulek.HLAVICKA_DIALOGU_NALEZENI.getNadpis(),
                Titulek.HLAVICKA_TITULKOVEHO_PANELU_NALEZENI.getNadpis());
    }
}
