package com.utils.shared.util.constant;

import java.time.format.DateTimeFormatter;

public enum FormatDateEnum {
    YEAR_MONTH_DAY("yyyy-MM-dd"),
    DAY_MONTH_YEAR("dd/MM/yyyy"),
    YEAR_MONTH("yyyy-MM"),
    MONTH_YEAR("MM/yyyy"),
    YEAR("yyyy"),
    HOUR_MINUTES("HH:mm");

    private final String format;

    private final DateTimeFormatter formatter;

    FormatDateEnum(String format) {
        this.format = format;
        this.formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneIdEnum.UTC.getZoneId());
    }

    public String getFormat() {
        return format;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

}

