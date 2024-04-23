package cz.upce.fei.bdats.gui.kontejnery;

import javafx.scene.control.Button;

/**
 * Třída představuje tlačítko ({@link Button}) s výchozím nastavením pro většinu vytvořených tlačítek v rámci programu
 */
public final class Tlacitko extends Button {

    /**
     * Konstanta reprezentuje žádoucí šiřku pro všechna tlačítka, nechť všechna budou s preferovanou (defaultní)
     * šířkou 100 pixelů
     */
    private static final double PREFEROVANA_SIRKA_TLACITKA = 100.0;

    /**
     * Konstruktor vytváří nové tlačítko s textovým popiskem a nastaví textový obsah na toto nově vytvořené tlačítko
     *
     * @param btnText Hodnoty zvenčí vyjadřující textový popisek (label), jenž se zobrazí na tlačítku
     */
    public Tlacitko(String btnText) {
        this.setText(btnText);
        this.setPrefWidth(PREFEROVANA_SIRKA_TLACITKA);
    }

    public static double getPrefSirka() { return PREFEROVANA_SIRKA_TLACITKA; }
}
