package com.utils.shared.util;

import java.util.Arrays;

/**
 * Classe utilitaire pour les opérations sur les tableaux.
 */
public class ArrayUtil {

    private ArrayUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Vérifie si un tableau est vide.
     *
     * @param array le tableau à vérifier
     * @return true si le tableau est vide, false sinon
     */
    public static <T> boolean isEmpty(T[] array) {
        if (array == null) {
            return true;
        }
        return array.length == 0;
    }

    /**
     * Vérifie si un tableau n'est pas vide.
     *
     * @param array le tableau à vérifier
     * @return true si le tableau n'est pas vide, false sinon
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * Renvoie la longueur d'un tableau.
     *
     * @param array le tableau
     * @return la longueur du tableau
     */
    public static <T> int length(T[] array) {
        return array != null ? array.length : 0;
    }

    /**
     * Concatène deux tableaux.
     *
     * @param array1 le premier tableau
     * @param array2 le deuxième tableau
     * @return le tableau résultant de la concaténation des deux tableaux
     */
    public static <T> T[] concatenate(T[] array1, T[] array2) {
        if (isEmpty(array1)) {
            return array2;
        } else if (isEmpty(array2)) {
            return array1;
        }
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Récupère un sous-tableau à partir d'un tableau donné.
     *
     * @param array      le tableau d'origine
     * @param startIndex l'indice de début du sous-tableau
     * @param endIndex   l'indice de fin (exclus) du sous-tableau
     * @return le sous-tableau correspondant aux indices spécifiés
     * @throws IllegalArgumentException si les indices sont invalides
     */
    public static <T> T[] subArray(T[] array, int startIndex, int endIndex) {
        if (isEmpty(array)) {
            return null;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > array.length) {
            endIndex = array.length;
        }
        if (startIndex > endIndex) {
            throw new IllegalArgumentException("L'index de début est supérieur à l'index de fin");
        }
        return Arrays.copyOfRange(array, startIndex, endIndex);
    }

    /**
     * Supprime la première occurrence d'un élément dans un tableau.
     *
     * @param array   le tableau
     * @param element l'élément à supprimer
     * @return le tableau modifié sans la première occurrence de l'élément
     */
    public static <T> T[] removeElement(T[] array, T element) {
        if (isEmpty(array)) {
            return array;
        }
        int index = indexOf(array, element);
        if (index == -1) {
            return array;
        }
        int length = array.length - 1;
        T[] result = Arrays.copyOf(array, length);
        System.arraycopy(array, index + 1, result, index, length - index);
        return result;
    }

    /**
     * Recherche l'indice de la première occurrence d'un élément dans un tableau.
     *
     * @param array   le tableau
     * @param element l'élément à rechercher
     * @return l'indice de la première occurrence de l'élément, ou -1 si l'élément n'est pas trouvé
     */
    public static <T> int indexOf(T[] array, T element) {
        if (isEmpty(array) || element == null) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Trie un tableau selon l'ordre naturel des éléments.
     *
     * @param array le tableau à trier
     * @return le tableau trié
     */
    public static <T extends Comparable<? super T>> T[] sort(T[] array) {
        if (isEmpty(array)) {
            return array;
        }
        T[] result = Arrays.copyOf(array, array.length);
        Arrays.sort(result);
        return result;
    }

    /**
     * Échange les éléments de deux positions dans un tableau.
     *
     * @param array  le tableau
     * @param index1 l'indice de la première position
     * @param index2 l'indice de la deuxième position
     * @throws IllegalArgumentException si les indices sont invalides
     */
    public static <T> void swap(T[] array, int index1, int index2) {
        if (array == null || index1 < 0 || index2 < 0 || index1 >= array.length || index2 >= array.length) {
            throw new IllegalArgumentException("Indices invalides ou tableau nul");
        }
        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * Fusionne deux tableaux en un seul tableau.
     *
     * @param array1 premier tableau
     * @param array2 deuxième tableau
     * @return un tableau contenant les éléments des deux tableaux
     */
    public static <T> T[] merge(T[] array1, T[] array2) {
        if (array1 == null && array2 == null) {
            return null;
        }

        if (array1 == null) {
            return Arrays.copyOf(array2, array2.length);
        }

        if (array2 == null) {
            return Arrays.copyOf(array1, array1.length);
        }

        T[] mergedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, mergedArray, array1.length, array2.length);
        return mergedArray;
    }

    /**
     * Inverse l'ordre des éléments dans un tableau.
     *
     * @param array le tableau à inverser
     * @return le tableau avec les éléments inversés
     */
    public static <T> T[] reverse(T[] array) {
        if (array == null) {
            return null;
        }

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            T temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
        return array;
    }

    /**
     * Supprime les doublons d'un tableau.
     *
     * @param array le tableau contenant les doublons
     * @return un tableau sans les doublons
     */
    public static <T> T[] removeDuplicates(T[] array) {
        if (array == null) {
            return null;
        }

        int length = array.length;
        if (length < 2) {
            return array;
        }

        int newSize = length;
        for (int i = 0; i < newSize; i++) {
            for (int j = i + 1; j < newSize; j++) {
                if (array[i] == array[j]) {
                    T[] newArray = Arrays.copyOf(array, length);
                    System.arraycopy(newArray, j + 1, newArray, j, newSize - j - 1);
                    newSize--;
                    array = newArray;
                    j--;
                }
            }
        }

        if (newSize < length) {
            return Arrays.copyOf(array, newSize);
        }

        return array;
    }

    /**
     * Vérifie si un tableau contient une valeur spécifique.
     *
     * @param array le tableau à vérifier
     * @param value la valeur à rechercher
     * @return true si la valeur est présente, false sinon
     */
    public static <T> boolean contains(T[] array, T value) {
        if (array == null || value == null) {
            return false;
        }

        for (T element : array) {
            if (value.equals(element)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Trouve la valeur maximale dans un tableau.
     *
     * @param array le tableau contenant les valeurs
     * @return la valeur maximale du tableau
     * @throws IllegalArgumentException si le tableau est vide ou nul
     */
    public static <T extends Comparable<? super T>> T findMax(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Le tableau est vide ou nul.");
        }

        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }

        return max;
    }
}
