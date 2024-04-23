# Demografický Vyhledávač

Motivační přiklad:
* v rámci agendy jsou uchovávány statistické informace o počtu obyvatel (muži/ženy) pro jednotlivé obce;
* v rámci projektu je vybudována vyhledávací struktura kde bude realizováno vyhledávání podle klíče, názvu obce;

![alt form-image](/resources/form.png)

## Použité datové struktury

Abstraktní datová struktura umožňující vyhledávání dle klíče je realizována jako binární vyhledávací strom (BVS) v dynamické paměti (tabulka na binárním stromu).

Komponenta **AbstrTable** pracuje s generickým prvkem **K extends Comparable<K>, V (K-key,V-value)** a implementuje rozhraní:

  * **void zrus()** - zrušení celé tabulky;
  * **boolean jePrazdny()** - test prázdnosti tabulky;
  * **V najdi(K key)** - vyhledá prvek dle klíče;
  * **void vloz(K key, V value)** - vloží prvek do tabulky;
  * **V odeber(K key)** - odebere prvek dle klíče z tabulky;
  * **Iterator vytvorIterator (eTypProhl typ)** - vytvoří iterátor, který umožňuje  procházení stromu do šířky/hloubky (in-order);

Iterátor využívá ADS zásobník/fronta (**AbstrLifo**/**AbstrFifo**) postavenou nad ADS z projektu [MěřičSpotřeby](https://github.com/mirroxEkb14/MericSpotreby) (jako nová samostatná třída):
  * **void zrus()** - zrušení celé fronty/zásobníku;
  * **boolean jePrazdny()** - test prázdnosti;
  * **void vloz(T data)** - vloží prvek do zásobníku/fronty;
  * **T odeber()** - odebere prvek ze zásobníku/fronty;
  * **Iterator vytvorIterator** - vrací iterátor zásobníku/fronty;

Datová entita **Obec** uchovává:
  * číslo kraje, PSC, Obec, Počet mužů, Počet žen, celkem;

## Agenda kraje

Pro ověření funkčnosti implementovaných ADS je vytvořen modul **AgendaKraj**, který implementuje rozhraní odpovídající následující operacím:
  * **void importDat()** - provede import dat z textového souboru;
  * **Najdi/Vloz/Odeber()** - vyhledání/vložení/odebrání obce;
  * **VytvorIterátor()** - vrací iterátor tabulky;
  * **Generuj** - umožnuje generovat jednotlivé obce;

 ## Demonstrační program

 Pro obsluhu aplikace je vytvořeno uživatelské formulářové rozhraní **ProgAgendaKraj**, která umožňuje obsluhu programu a volat operace agendy kraje.

 **ProgAgendaKraj** nechť dále umožňuje zadávání vstupních dat z klávesnice, ze souboru a z generátoru, výstupy z programu nechť je možné zobrazit na obrazovce a uložit do souboru.

 Pozn.:
   * iterátor využívá ADS zásobník/fronta postavenou nad ADS z [MěřičeSpotřeby](https://github.com/mirroxEkb14/MericSpotreby);
   * pokud se pokoušíme vložit záznam s již existujícím klíčem, metoda typu vlož vyvolá výjimku;
