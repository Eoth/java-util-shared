package com.utils.shared.unit.test.data;

import com.utils.shared.util.ReflectionUtil;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * La classe Populator est une classe utilitaire permettant de générer des collections ou des objets peuplés à des fins de tests unitaires.
 * Elle permet de peupler des collections avec un nombre spécifié d'instances d'une classe donnée, et de peupler des objets individuels avec des valeurs générées pour leurs champs.
 */
public class Populator {

    private Populator() {
        // Empêche l'instanciation de la classe utilitaire
    }

    /**
     * Peuple une collection avec un nombre spécifié d'instances de la classe donnée.
     *
     * @param clazz      la classe des objets à peupler la collection
     * @param collection la collection à peupler
     * @param size       le nombre d'instances à créer, peupler et ajouter dans la collection
     * @param <T>        le type des objets dans la collection
     * @param <C>        le type de la collection
     * @return la collection peuplée
     */
    public static <T, C extends Collection<T>> C populate(final Class<T> clazz, final C collection, final int size) {
        return populate(clazz, collection, size, new Option());
    }

    /**
     * Peuple une collection avec un nombre spécifié d'instances de la classe donnée, avec des options personnalisées.
     *
     * @param clazz      la classe des objets à peupler la collection
     * @param collection la collection à peupler
     * @param size       le nombre d'instances à peupler
     * @param option     les options pour peupler les objets
     * @param <T>        le type des objets dans la collection
     * @param <C>        le type de la collection
     * @return la collection peuplée
     */
    public static <T, C extends Collection<T>> C populate(final Class<T> clazz, final C collection, int size, final Option option) {
        for (int i = 0; i < size; i++) {
            collection.add(populate(ReflectionUtil.createInstance(clazz), i, option));
        }
        return collection;
    }

    /**
     * Peuple un objet avec des valeurs générées pour ses champs.
     *
     * @param object l'objet à peupler
     * @param key    la clé utilisée pour générer des valeurs uniques
     * @param <T>    le type de l'objet
     * @return l'objet peuplé
     */
    public static <T> T populate(final T object, final int key) {
        return populate(object, key, new Option());
    }

    /**
     * Peuple un objet avec des valeurs générées pour ses champs, avec des options personnalisées.
     *
     * @param object  l'objet à peupler
     * @param key     la clé utilisée pour générer des valeurs uniques
     * @param options les options pour peupler l'objet
     * @param <T>     le type de l'objet
     * @return l'objet peuplé
     */
    public static <T> T populate(final T object, final int key, final Option options) {
        if (object == null) return null;
        Class<?> clazz = object.getClass();
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (Modifier.isStatic(field.getModifiers()) || options.shouldIgnoreField(fieldName)) {
                continue;  // Ignorer le champ
            }

            OptionAction action = options.getFieldAction(fieldName);
            if (action == null) {
                Class<?> fieldType = field.getType();
                Object value = null;
                if (fieldType == String.class) {
                    value = generateUniqueString(fieldName, key);
                } else if (fieldType == Integer.class || fieldType == int.class) {
                    value = key;
                } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                    value = key % 2 == 0;
                } else if (fieldType == Byte.class || fieldType == byte.class) {
                    value = (byte) key;
                } else if (fieldType == Short.class || fieldType == short.class) {
                    value = (short) key;
                } else if (fieldType == Long.class || fieldType == long.class) {
                    value = (long) key;
                } else if (fieldType == Float.class || fieldType == float.class) {
                    value = (float) key;
                } else if (fieldType == Double.class || fieldType == double.class) {
                    value = (double) key;
                } else if (fieldType == Character.class || fieldType == char.class) {
                    value = (char) (key % 26 + 'A');
                } else if (Collection.class.isAssignableFrom(fieldType) || Map.class.isAssignableFrom(fieldType)) {
                    value = instantiateCollectionOrMap(fieldType);
                }
                ReflectionUtil.setFieldValue(field, object, value);
            } else {
                action.apply(object, key, field);
            }
        }
        return object;
    }

    private static String generateUniqueString(String fieldName, int key) {
        return fieldName + key;
    }

    private static Object instantiateCollectionOrMap(Class<?> fieldType) {
        if (Collection.class.isAssignableFrom(fieldType)) {
            return instantiateCollection(fieldType);
        }
        return instantiateMap(fieldType);
    }

    private static Collection<?> instantiateCollection(Class<?> collectionType) {
        if (List.class.isAssignableFrom(collectionType)) {
            return new ArrayList<Object>();
        } else if (Set.class.isAssignableFrom(collectionType)) {
            return new HashSet<Object>();
        } else if (Queue.class.isAssignableFrom(collectionType)) {
            return new LinkedList<Object>();
        }
        // Gérez d'autres types de collections selon vos besoins
        return null;
    }

    private static Map<?, ?> instantiateMap(Class<?> mapType) {
        if (Map.class.isAssignableFrom(mapType)) {
            return new HashMap<Object, Object>();
        }
        // Gérez d'autres types de maps selon vos besoins
        return null;
    }
}
