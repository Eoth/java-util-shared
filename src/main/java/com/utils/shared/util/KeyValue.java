package com.utils.shared.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public interface KeyValue {

    String getKey();

    String getValue();

    Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * Convertit un tableau d'objets de type E (qui étend KeyValue) en une liste de tableaux de chaînes de caractères
     * contenant les clés et les valeurs correspondantes.
     *
     * @param values Un tableau d'objets de type E (qui étend KeyValue)
     * @param <E>    Le type générique qui étend KeyValue
     * @return Une liste de tableaux de chaînes de caractères représentant les clés et les valeurs
     */
    static <E extends KeyValue> List<String[]> toKeysValues(E[] values) {
        final List<String[]> keysValues = new ArrayList<>(values.length);
        for (final E e : values) {
            keysValues.add(new String[]{e.getKey(), e.getValue()});
        }
        return keysValues;
    }

    /**
     * Récupère la valeur correspondant à la clé spécifiée dans le tableau d'objets de type E (qui étend KeyValue).
     *
     * @param values Un tableau d'objets de type E (qui étend KeyValue)
     * @param key    La clé à rechercher
     * @param <E>    Le type générique qui étend KeyValue
     * @return La valeur correspondant à la clé, ou une chaîne vide si la clé n'est pas trouvée
     */
    static <E extends KeyValue> String getValueOfKey(E[] values, String key) {
        for (final E e : values) {
            if (e.getKey().equals(key)) {
                return e.getValue();
            }
        }
        return "";
    }

}
