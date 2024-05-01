package com.utils.shared.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private DateUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Formate une date en une chaîne de caractères selon le format spécifié.
     *
     * @param date   la date à formater
     * @param format le format de la chaîne de caractères de sortie (ex: "dd/MM/yyyy")
     * @return la chaîne de caractères formatée ou une chaîne vide en cas d'erreur
     */
    public static String formatDate(Date date, String format) {
        if (date == null || format == null || format.isEmpty()) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Parse une chaîne de caractères en une date selon le format spécifié.
     *
     * @param dateString la chaîne de caractères à parser
     * @param format     le format de la chaîne de caractères d'entrée (ex: "dd/MM/yyyy")
     * @return la date parsée ou null en cas d'erreur
     */
    public static Date parseDate(String dateString, String format) {
        if (dateString == null || dateString.isEmpty() || format == null || format.isEmpty()) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Ajoute un nombre de jours à une date.
     *
     * @param date    la date à laquelle ajouter les jours
     * @param numDays le nombre de jours à ajouter (utiliser une valeur négative pour soustraire des jours)
     * @return la nouvelle date après l'ajout des jours ou null en cas d'erreur
     */
    public static Date addDays(Date date, int numDays) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, numDays);
        return calendar.getTime();
    }

    /**
     * Compare deux dates pour déterminer si elles sont égales.
     *
     * @param date1 la première date
     * @param date2 la deuxième date
     * @return true si les dates sont égales (même jour, mois et année), false sinon
     */
    public static boolean areEqualDates(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }

        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Vérifie si une date est antérieure à une autre date.
     *
     * @param date1 la première date
     * @param date2 la deuxième date
     * @return true si la date1 est antérieure à la date2, false sinon
     */
    public static boolean isBefore(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        return date1.before(date2);
    }

    /**
     * Vérifie si une date est postérieure à une autre date.
     *
     * @param date1 la première date
     * @param date2 la deuxième date
     * @return true si la date1 est postérieure à la date2, false sinon
     */
    public static boolean isAfter(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        return date1.after(date2);
    }

    /**
     * Calcule la différence en jours entre deux dates.
     *
     * @param date1 la première date
     * @param date2 la deuxième date
     * @return le nombre de jours de différence entre les deux dates
     */
    public static int getDifferenceInDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long diffMillis = date2.getTime() - date1.getTime();
        return (int) (diffMillis / (24 * 60 * 60 * 1000));
    }

    /**
     * Calcule l'âge en années à partir d'une date de naissance.
     *
     * @param birthDate la date de naissance
     * @return l'âge en années ou 0 si la date de naissance est nulle ou future
     */
    public static int calculateAge(Date birthDate) {
        if (birthDate == null || birthDate.after(new Date())) {
            return 0;
        }

        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar nowCalendar = Calendar.getInstance();
        int age = nowCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        if (nowCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (nowCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        nowCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }

    /**
     * Vérifie si une date est un jour de week-end (samedi ou dimanche).
     *
     * @param date la date à vérifier
     * @return true si la date est un jour de week-end, false sinon
     */
    public static boolean isWeekend(Date date) {
        if (date == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Obtient le premier jour du mois de la date spécifiée.
     *
     * @param date la date
     * @return le premier jour du mois de la date spécifiée
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * Obtient le dernier jour du mois de la date spécifiée.
     *
     * @param date la date
     * @return le dernier jour du mois de la date spécifiée
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }


    /**
     * Convertit un objet LocalDate en Date.
     *
     * @param localDate L'objet LocalDate à convertir.
     * @return L'objet Date résultant de la conversion, ou null si localDate est null.
     */
    public static Date asDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
    }

    /**
     * Convertit un objet LocalDateTime en Date.
     *
     * @param localDateTime L'objet LocalDateTime à convertir.
     * @return L'objet Date résultant de la conversion.
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZONE_ID).toInstant());
    }

    /**
     * Convertit un objet Date en LocalDate.
     *
     * @param date L'objet Date à convertir.
     * @return L'objet LocalDate résultant de la conversion, ou null si date est null.
     */
    public static LocalDate asLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDate();
    }

    /**
     * Convertit un objet Date en LocalDateTime.
     *
     * @param date L'objet Date à convertir.
     * @return L'objet LocalDateTime résultant de la conversion.
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDateTime();
    }


}


