package com.utils.shared.util;

import com.utils.shared.util.constant.FormatDateEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * Classe utilitaire pour la manipulation des objets Temporal (java.time).
 */
public class TemporalUtil {

    private TemporalUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Formate un objet Temporal en une chaîne de caractères au format "yyyy-MM-dd" (année-mois-jour).
     *
     * @param temporal l'objet Temporal à formater
     * @return la chaîne de caractères formatée
     */
    public static String formatYearMonthDay(Temporal temporal) {
        return formatDate(temporal, FormatDateEnum.YEAR_MONTH_DAY);
    }

    /**
     * Formate un objet Temporal en une chaîne de caractères au format "dd/MM/yyyy" (jour/mois/année).
     *
     * @param temporal l'objet Temporal à formater
     * @return la chaîne de caractères formatée
     */
    public static String formatDayMonthYear(Temporal temporal) {
        return formatDate(temporal, FormatDateEnum.DAY_MONTH_YEAR);
    }

    /**
     * Formate un objet Temporal en une chaîne de caractères au format "yyyy-MM" (année-mois).
     *
     * @param temporal l'objet Temporal à formater
     * @return la chaîne de caractères formatée
     */
    public static String formatYearMonth(Temporal temporal) {
        return formatDate(temporal, FormatDateEnum.YEAR_MONTH);
    }

    /**
     * Formate un objet Temporal en une chaîne de caractères au format "MM/yyyy" (mois/année).
     *
     * @param temporal l'objet Temporal à formater
     * @return la chaîne de caractères formatée
     */
    public static String formatMonthYear(Temporal temporal) {
        return formatDate(temporal, FormatDateEnum.MONTH_YEAR);
    }

    /**
     * Formate un objet Temporal en une chaîne de caractères au format "yyyy" (année).
     *
     * @param temporal l'objet Temporal à formater
     * @return la chaîne de caractères formatée
     */
    public static String formatYear(Temporal temporal) {
        return formatDate(temporal, FormatDateEnum.YEAR);
    }

    /**
     * Formate un objet Temporal en une chaîne de caractères au format "HH:mm" (heures:minutes).
     *
     * @param temporal l'objet Temporal à formater
     * @return la chaîne de caractères formatée
     */
    public static String formatHourMinutes(Temporal temporal) {
        return formatDate(temporal, FormatDateEnum.HOUR_MINUTES);
    }

    /**
     * Analyse une chaîne de caractères au format "yyyy-MM-dd" (année-mois-jour) et la convertit en un objet LocalDate.
     *
     * @param dateString la chaîne de caractères à analyser
     * @return l'objet LocalDate correspondant à la date analysée
     */
    public static LocalDate parseYearMonthDay(String dateString) {
        return parseDate(dateString, FormatDateEnum.YEAR_MONTH_DAY);
    }

    /**
     * Analyse une chaîne de caractères au format "dd/MM/yyyy" (jour/mois/année) et la convertit en un objet LocalDate.
     *
     * @param dateString la chaîne de caractères à analyser
     * @return l'objet LocalDate correspondant à la date analysée
     */
    public static LocalDate parseDayMonthYear(String dateString) {
        return parseDate(dateString, FormatDateEnum.DAY_MONTH_YEAR);
    }

    /**
     * Analyse une chaîne de caractères au format "HH:mm" (heures:minutes) et la convertit en un objet LocalTime.
     *
     * @param timeString la chaîne de caractères à analyser
     * @return l'objet LocalTime correspondant à l'heure analysée
     */
    public static LocalTime parseHourMinutes(String timeString) {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern(FormatDateEnum.HOUR_MINUTES.getFormat()));
    }

    /**
     * Formate un objet Temporal en une chaîne de caractères selon le format spécifié.
     *
     * @param temporal l'objet Temporal à formater
     * @param format   le format de la date à utiliser
     * @return la chaîne de caractères formatée
     */
    public static String formatDate(Temporal temporal, FormatDateEnum format) {
        return format.getFormatter().format(temporal);
    }

    /**
     * Analyse une chaîne de caractères en une date LocalDate selon le format spécifié.
     *
     * @param dateString la chaîne de caractères à analyser
     * @param format     le format de la date à utiliser
     * @return l'objet LocalDate correspondant à la date analysée
     */
    public static LocalDate parseDate(String dateString, FormatDateEnum format) {
        return LocalDate.parse(dateString, format.getFormatter());
    }

}
