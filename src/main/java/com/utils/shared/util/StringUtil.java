package com.utils.shared.util;

import java.text.Normalizer;

public class StringUtil {

    private StringUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Vérifie si une chaîne de caractères est nulle ou vide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return true si la chaîne est nulle ou vide, false sinon
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Vérifie si une chaîne de caractères est non nulle et non vide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return true si la chaîne est non nulle et non vide, false sinon
     */
    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * Inverse une chaîne de caractères.
     *
     * @param str la chaîne de caractères à inverser
     * @return la chaîne inversée
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }

        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Convertit une chaîne de caractères en tableau de caractères.
     *
     * @param str la chaîne de caractères à convertir
     * @return le tableau de caractères correspondant à la chaîne
     */
    public static char[] toCharArray(String str) {
        char[] chars = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            chars[i] = str.charAt(i);
        }
        return chars;
    }

    /**
     * Vérifie si une chaîne de caractères est un palindrome.
     *
     * @param str la chaîne de caractères à vérifier
     * @return true si la chaîne est un palindrome, false sinon
     */
    public static boolean isPalindrome(String str) {
        int left = 0;
        int right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * Compte le nombre d'occurrences d'une sous-chaîne dans une chaîne.
     *
     * @param str        la chaîne de caractères
     * @param substring  la sous-chaîne à rechercher
     * @param ignoreCase indique si la recherche doit être insensible à la casse
     * @return le nombre d'occurrences de la sous-chaîne
     */
    public static int countOccurrences(String str, String substring, boolean ignoreCase) {
        if (isNullOrEmpty(str) || isNullOrEmpty(substring)) {
            return 0;
        }

        if (ignoreCase) {
            str = str.toLowerCase();
            substring = substring.toLowerCase();
        }

        int count = 0;
        int index = 0;
        while ((index = str.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }

        return count;
    }

    /**
     * Tronque une chaîne de caractères en ajoutant des points de suspension si elle dépasse une certaine longueur.
     *
     * @param str    la chaîne de caractères à tronquer
     * @param length la longueur maximale de la chaîne tronquée
     * @return la chaîne tronquée avec des points de suspension si nécessaire
     */
    public static String truncate(String str, int length) {
        if (isNullOrEmpty(str)) {
            return str;
        }

        if (str.length() <= length) {
            return str;
        }

        return str.substring(0, length) + "...";
    }

    /**
     * Échappe les caractères spéciaux dans une chaîne de caractères.
     *
     * @param input la chaîne de caractères à échapper
     * @return la chaîne de caractères échappée
     */
    public static String escape(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            switch (ch) {
                case '\\':
                    result.append("\\\\");
                    break;
                case '\"':
                    result.append("\\\"");
                    break;
                case '\'':
                    result.append("\\'");
                    break;
                case '\t':
                    result.append("\\t");
                    break;
                case '\n':
                    result.append("\\n");
                    break;
                case '\r':
                    result.append("\\r");
                    break;
                case '\b':
                    result.append("\\b");
                    break;
                case '\f':
                    result.append("\\f");
                    break;
                default:
                    if (Character.isISOControl(ch)) {
                        result.append("\\u").append(String.format("%04x", (int) ch));
                    } else {
                        result.append(ch);
                    }
                    break;
            }
        }
        return result.toString();
    }

    /**
     * Convertit la première lettre de la chaîne en minuscule.
     *
     * @param s la chaîne de caractères
     * @return la chaîne avec la première lettre en minuscule
     */
    public static String deCapitalize(String s) {
        if (s.isEmpty()) {
            return s;
        }
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * Convertit la première lettre de la chaîne en majuscule.
     *
     * @param s la chaîne de caractères
     * @return la chaîne avec la première lettre en majuscule
     */
    public static String capitalize(String s) {
        if (s.isEmpty()) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * Supprime les marques diacritiques des caractères dans la chaîne donnée.
     * Exemple : `Café` -> `Cafe`
     *
     * @param string la chaîne de caractères à traiter
     * @return la chaîne de caractères sans les marques diacritiques
     */
    public static String removeDiacriticalMarks(String string) {
        String normalizedString = Normalizer.normalize(string, Normalizer.Form.NFD);
        return normalizedString.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}

