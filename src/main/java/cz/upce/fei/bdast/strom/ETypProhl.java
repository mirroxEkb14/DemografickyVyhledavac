package cz.upce.fei.bdast.strom;

/**
 * Třída je výčtovým typem, který slouží k identifikaci způsobu, jakým budou iterátory procházet binární strom.
 * Nabízí dvě hodnoty:
 * <ul>
 * <li> {@link ETypProhl#SIRKA} označuje, že iterátor bude provádět průchod stromem do šířky (breadth-first traversal),
 * což znamená, že nejdříve navštíví všechny uzly na stejné úrovni, než se přesune na další úroveň
 * <li> {@link ETypProhl#HLOUBKA} označuje, že iterátor bude provádět průchod stromem do hloubky (depth-first traversal),
 * což znamená, že bude postupovat co nejhlouběji do stromu, než se vrátí a postupně prochází další uzly
 * </ul>
 * Umožňuje uživateli binárního stromu vybrat preferovaný způsob průchodu stromem tím, že předává tuto hodnotu jako
 * argument při vytváření iterátoru. Tím je umožněno provádět operace na stromu v souladu s preferovaným způsobem
 * průchodu
 */
public enum ETypProhl {
    SIRKA,
    HLOUBKA
}
