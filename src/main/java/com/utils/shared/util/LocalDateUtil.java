package com.utils.shared.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class LocalDateUtil {


    // Obtient le prochain jour de paie (dernier vendredi du mois) à partir d'une date
    public static LocalDate getNextPayday(LocalDate currentDate) {
        LocalDate lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());
        DayOfWeek lastDayOfWeek = lastDayOfMonth.getDayOfWeek();
        int daysUntilFriday = DayOfWeek.FRIDAY.getValue() - lastDayOfWeek.getValue();
        if (daysUntilFriday <= 0) {
            daysUntilFriday += 7;
        }
        return lastDayOfMonth.plusDays(daysUntilFriday);
    }


}
