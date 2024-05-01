package com.utils.shared.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> createMap(Object... keyValues) {
        if (keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("Le nombre d'arguments doit être pair pour les paires clé-valeur.");
        }

        Map<K, V> map = new HashMap<K, V>();

        for (int i = 0; i < keyValues.length; i += 2) {
            K key = (K) keyValues[i];
            V value = (V) keyValues[i + 1];
            map.put(key, value);
        }

        return map;
    }
}
