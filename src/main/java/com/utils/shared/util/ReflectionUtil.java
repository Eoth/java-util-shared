package com.utils.shared.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ReflectionUtil {

    private ReflectionUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe
    }

    /**
     * Crée une nouvelle instance d'un type générique.
     *
     * @param clazz la classe du type générique
     * @return une nouvelle instance du type générique ou null si la création échoue
     */
    public static <T> T createInstance(final Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Effectue la recherche d'un champ sur une classe avec un nom de champs donné.
     *
     * @param fieldName   le nom du champ sà chercher
     * @param objectClass la classe sur laquelle chercher le champ
     * @throws ReflectionException si une erreur de réflexion se produit
     */
    public static Field getField(final String fieldName, final Class<?> objectClass) {
        try {
            return objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new ReflectionException("Erreur lors de la récupération du champs", e);
        }
    }

    /**
     * Effectue le setter d'un champ sur un objet avec une valeur donnée.
     *
     * @param object    l'objet sur lequel effectuer le setter
     * @param fieldName le nom du champ sur lequel effectuer le setter
     * @param value     la valeur à définir
     * @throws ReflectionException si une erreur de réflexion se produit
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        setFieldValue(getField(fieldName, object.getClass()), object, value);
    }

    /**
     * Effectue le setter d'un champ sur un objet avec une valeur donnée.
     *
     * @param field  le champ sur lequel effectuer le setter
     * @param object l'objet sur lequel effectuer le setter
     * @param value  la valeur à définir
     * @throws ReflectionException si une erreur de réflexion se produit
     */
    public static void setFieldValue(final Field field, final Object object, final Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new ReflectionException("Erreur lors de l'accès au champ", e);
        }
    }

    /**
     * Effectue le getter d'un champ sur un objet pour obtenir une valeur.
     *
     * @param field  le champ sur lequel effectuer le getter
     * @param object l'objet sur lequel effectuer le getter
     * @return valeur du champ
     * @throws ReflectionException si une erreur de réflexion se produit
     */
    public static Object getFieldValue(final Field field, final Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new ReflectionException("Erreur lors de l'accès au champ", e);
        }
    }

    /**
     * Crée une instance d'une classe de collection spécifiée.
     *
     * @param collectionType Le type de la collection.
     * @return Une instance de la classe de collection spécifiée, ou {@code null} en cas d'erreur.
     */
    public static Collection<Object> createCollection(Class<?> collectionType) {
        if (collectionType.isInterface()) {
            if (List.class.isAssignableFrom(collectionType)) {
                return new ArrayList<Object>();
            } else if (Set.class.isAssignableFrom(collectionType)) {
                return new HashSet<Object>();
            } else if (Queue.class.isAssignableFrom(collectionType)) {
                return new LinkedList<Object>();
            }
        } 
        return (Collection<Object>) createInstance(collectionType);
    }

    /**
     * Récupère le type d'éléments contenu dans une collection spécifiée par un champ.
     *
     * @param field Le champ représentant la collection.
     * @return Le type d'éléments de la collection, ou {@code Object.class} si le type ne peut pas être déterminé.
     */
    public static Class<?> getCollectionType(Field field) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 0) {
                Type typeArgument = typeArguments[0];
                if (typeArgument instanceof Class) {
                    return (Class<?>) typeArgument;
                }
            }
        }
        return Object.class; // Default to Object if unable to determine the collection type
    }

    public static Field[] getFieldsSorted(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        int length = fields.length;
        for (int i = 1; i < length; i++) {
            Field currentField = fields[i];
            String currentFieldName = currentField.getName();
            int j = i - 1;

            while (j >= 0 && fields[j].getName().compareTo(currentFieldName) > 0) {
                fields[j + 1] = fields[j];
                j--;
            }

            fields[j + 1] = currentField;
        }
        return fields;
    }

    public static Field findFieldByName(String fieldName, Field[] fields) {
        int left = 0;
        int right = fields.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Field midField = fields[mid];
            String midFieldName = midField.getName();

            int comparison = fieldName.compareTo(midFieldName);

            if (comparison == 0) {
                // Le champ recherché a été trouvé
                return midField;
            } else if (comparison < 0) {
                // Le champ recherché est dans la moitié inférieure du tableau
                right = mid - 1;
            } else {
                // Le champ recherché est dans la moitié supérieure du tableau
                left = mid + 1;
            }
        }

        // Le champ recherché n'a pas été trouvé
        return null;
    }

    /**
     * Exception personnalisée pour les erreurs de réflexion.
     */
    public static class ReflectionException extends RuntimeException {
        public ReflectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
