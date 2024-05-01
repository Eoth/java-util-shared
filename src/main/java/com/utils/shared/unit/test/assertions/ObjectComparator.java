package com.utils.shared.unit.test.assertions;

import com.utils.shared.util.JsonUtil;
import com.utils.shared.util.ReflectionUtil;
import com.utils.shared.unit.test.assertions.constantes.IDEInterface;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Une classe utilitaire pour comparer des objets dans des tests unitaires.
 */
public class ObjectComparator {

    private ObjectComparator() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Compare deux objets pour vérifier s'ils sont égaux.
     *
     * @param expected        l'objet attendu
     * @param actual          l'objet réel
     * @param fieldsToExclude les champs à exclure de la comparaison
     * @throws AssertionError si la comparaison échoue
     */
    public static void compare(Object expected, Object actual, String... fieldsToExclude) {
        checkArgsNotNull(expected, actual);
        checkArgsSameTypes(expected, actual);

        Set<String> excludedFields = new HashSet<>(Arrays.asList(fieldsToExclude));

        compare(expected, actual, excludedFields);
    }

    private static void compare(Object expected, Object actual, Set<String> excludedFields) {
        if (isImmutableType(expected)) {
            compareImmutableTypes(expected, actual);
        } else if (isCollectionType(expected)) {
            compareCollections(expected, actual, excludedFields);
        } else {
            compareFields(expected, actual, excludedFields);
        }
    }

    private static void checkArgsSameTypes(Object expected, Object actual) {
        if (!expected.getClass().equals(actual.getClass())) {
            throwError("Objets de types différents : Attendu : " + expected.getClass() + ", Réel : " + actual.getClass());
        }
    }

    private static void checkArgsNotNull(Object expected, Object actual) {
        if (expected == null || actual == null) {
            throwError("Un ou plusieurs objets sont nuls");
        }
    }

    /**
     * Vérifie si l'objet est d'un type immuable.
     *
     * @param obj l'objet à vérifier
     * @return true si l'objet est d'un type immuable, false sinon
     */
    private static boolean isImmutableType(Object obj) {
        return obj instanceof String ||
                obj instanceof Number ||
                obj instanceof Boolean ||
                obj instanceof Temporal;
    }

    /**
     * Vérifie si l'objet est une collection, un tableau ou une map.
     *
     * @param obj l'objet à vérifier
     * @return true si l'objet est une collection, un tableau ou une map, false sinon
     */
    private static boolean isCollectionType(Object obj) {
        return obj instanceof Collection ||
                obj instanceof Map ||
                obj.getClass().isArray();
    }

    /**
     * Compare les types immuables pour vérifier s'ils sont égaux.
     *
     * @param expected l'objet attendu
     * @param actual   l'objet réel
     * @throws AssertionError si la comparaison échoue
     */
    private static void compareImmutableTypes(Object expected, Object actual) {
        if (expected instanceof BigDecimal && actual instanceof BigDecimal) {
            BigDecimal actualBigDecimal = (BigDecimal) actual;
            BigDecimal expectedBigDecimal = (BigDecimal) expected;
            if (expectedBigDecimal.compareTo(actualBigDecimal) != 0) {
                fail(expected, actual);
            }
        } else if (!expected.equals(actual)) {
            fail(expected, actual);
        }
    }

    /**
     * Compare les champs des objets complexes pour vérifier s'ils sont égaux.
     *
     * @param expected       l'objet attendu
     * @param actual         l'objet réel
     * @param excludedFields les champs à exclure de la comparaison
     * @throws AssertionError si la comparaison échoue
     */
    private static void compareFields(Object expected, Object actual, Set<String> excludedFields) {
        Map<String, Object> expectedFields = extractFields(expected);
        Map<String, Object> actualFields = extractFields(actual);

        List<String> differentFields = new ArrayList<>();

        for (Map.Entry<String, Object> entry : expectedFields.entrySet()) {
            String fieldName = entry.getKey();
            if (excludedFields.contains(fieldName)) {
                continue;
            }
            Object expectedValue = entry.getValue();
            Object actualValue = actualFields.get(fieldName);
            try {
                if (expectedValue == null || actualValue == null) {
                    if (expectedValue != actualValue) {
                        throwError("les valeurs doivent être nulle.");
                    }
                }
                compare(expectedValue, actualValue, excludedFields);
            } catch (AssertionError error) {
                differentFields.add(fieldName);
            }
        }

        sendErrorIfHaveDifferentFields(expected, actual, differentFields);
    }

    private static void sendErrorIfHaveDifferentFields(Object expected, Object actual, List<String> differentFields) {
        if (!differentFields.isEmpty()) {
            final StringBuilder errorMessage = new StringBuilder("Les champs ne correspondent pas pour l'objet de type ");
            errorMessage.append(expected.getClass().getSimpleName()).append(" : ").append('[');
            int i = 0;
            for (String field : differentFields) {
                if (i > 0) errorMessage.append(", ");
                errorMessage.append('"').append(field).append('"');
                i++;
            }
            errorMessage.append(']');
            fail(expected, actual, errorMessage.toString());
        }
    }

    /**
     * Compare les collections pour vérifier si elles sont égales.
     *
     * @param expected       la collection attendue
     * @param actual         la collection réelle
     * @param excludedFields les champs à exclure de la comparaison
     * @throws AssertionError si la comparaison échoue
     */
    private static void compareCollections(Object expected, Object actual, Set<String> excludedFields) {
        Collection<?> expectedCollection = extractCollectionElements(expected);
        Collection<?> actualCollection = extractCollectionElements(actual);

        int expectedSize = expectedCollection.size();
        int actualSize = actualCollection.size();
        if (expectedSize != actualSize) {
            throwError("Tailles différentes. Attendu : " + expectedSize + ", Réel : " + actualSize);
        }

        if (expectedSize == 0) {
            return;
        }

        String expectedClassName = expectedCollection.iterator().next().getClass().getSimpleName();
        String actualClassName = actualCollection.iterator().next().getClass().getSimpleName();
        if (!expectedClassName.equals(actualClassName)) {
            throwError("Type d'objet différentes. Attendu : " + expectedClassName + ", Réel : " + actualClassName);
        }

        List<Object> remainingElements = new ArrayList<>(actualCollection);
        for (Object expectedElement : expectedCollection) {
            boolean elementFound = false;
            Iterator<Object> iterator = remainingElements.iterator();
            while (iterator.hasNext()) {
                Object actualElement = iterator.next();
                try {
                    compare(expectedElement, actualElement, excludedFields);
                    elementFound = true;
                    iterator.remove();
                    break;
                } catch (AssertionError ignored) {
                }
            }
            if (!elementFound) {
                throwError("Élément manquant : " + expectedElement);
            }
        }
    }

    /**
     * Extrait les champs d'un objet complexe.
     *
     * @param obj l'objet à partir duquel extraire les champs
     * @return la carte des champs avec leurs valeurs
     */
    private static Map<String, Object> extractFields(Object obj) {
        Map<String, Object> fields = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            fields.put(field.getName(), ReflectionUtil.getFieldValue(field, obj));
        }
        return fields;
    }

    /**
     * Extrait les éléments d'une collection, d'un tableau ou d'une map.
     *
     * @param obj l'objet à partir duquel extraire les éléments
     * @return la collection des éléments extraits
     */
    private static Collection<?> extractCollectionElements(Object obj) {
        if (obj instanceof Collection) {
            return (Collection<?>) obj;
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).values();
        } else if (obj.getClass().isArray()) {
            List<Object> elements = new ArrayList<>();
            int length = java.lang.reflect.Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                Object element = java.lang.reflect.Array.get(obj, i);
                elements.add(element);
            }
            return elements;
        }
        return Collections.emptyList();
    }

    private static void fail(Object expected, Object actual) {
        fail(expected, actual, "");
    }

    private static void fail(Object expected, Object actual, String msg) {
        final String message = msg + " " + IDEInterface.ASSERT_LEFT + JsonUtil.toJson(expected, true) +
                IDEInterface.ASSERT_MIDDLE + JsonUtil.toJson(actual, true) + IDEInterface.ASSERT_RIGHT;
        throwError(message);
    }

    private static void throwError(final String message) {
        throw new AssertionError("Comparaison échouée. " + message);
    }
}