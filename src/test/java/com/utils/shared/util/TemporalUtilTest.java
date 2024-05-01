package com.utils.shared.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.Temporal;

public class TemporalUtilTest {

    @Test
    public void testFormatYearMonthDay() {
        // Given
        LocalDate date = LocalDate.of(2023, Month.JUNE, 27);
        // When
        String formattedDate = TemporalUtil.formatYearMonthDay(date);
        // Then
        Assert.assertEquals("2023-06-27", formattedDate);
    }

    @Test
    public void testFormatDayMonthYear_LocalDate() {
        testFormatDayMonthYear(LocalDate.of(2023, Month.JUNE, 27));
    }

    @Test
    public void testFormatDayMonthYear_Instant() {
        testFormatDayMonthYear(Instant.parse("2023-06-27T10:15:30Z"));
    }

    private static void testFormatDayMonthYear(Temporal temporal) {
        // When
        String formattedInstant = TemporalUtil.formatDayMonthYear(temporal);
        // Then
        Assert.assertEquals(formattedInstant, "27/06/2023");
    }

    @Test
    public void testFormatYearMonth() {
        // Given
        LocalDate date = LocalDate.of(2023, Month.JUNE, 27);
        // When
        String formattedDate = TemporalUtil.formatYearMonth(date);
        // Then
        Assert.assertEquals("2023-06", formattedDate);
    }

    @Test
    public void testFormatMonthYear() {
        // Given
        LocalDate date = LocalDate.of(2023, Month.JUNE, 27);
        // When
        String formattedDate = TemporalUtil.formatMonthYear(date);
        // Then
        Assert.assertEquals("06/2023", formattedDate);
    }

    @Test
    public void testFormatYear() {
        // Given
        LocalDate date = LocalDate.of(2023, Month.JUNE, 27);
        // When
        String formattedDate = TemporalUtil.formatYear(date);
        // Then
        Assert.assertEquals("2023", formattedDate);
    }

    @Test
    public void testFormatHourMinutes() {
        // Given
        LocalTime time = LocalTime.of(12, 30);
        // When
        String formattedTime = TemporalUtil.formatHourMinutes(time);
        // Then
        Assert.assertEquals("12:30", formattedTime);
    }

    @Test
    public void testFormatHourMinutesI() {
        // When
        String formattedTime = TemporalUtil.formatHourMinutes(Instant.parse("2023-06-27T10:15:30Z"));
        // Then
        Assert.assertEquals("10:15", formattedTime);
    }

    @Test
    public void testParseYearMonthDay() {
        // Given
        String dateString = "2023-06-27";
        // When
        LocalDate date = TemporalUtil.parseYearMonthDay(dateString);
        // Then
        Assert.assertEquals(LocalDate.of(2023, Month.JUNE, 27), date);
    }

    @Test
    public void testParseDayMonthYear() {
        // Given
        String dateString = "27/06/2023";
        // When
        LocalDate date = TemporalUtil.parseDayMonthYear(dateString);
        // Then
        Assert.assertEquals(LocalDate.of(2023, Month.JUNE, 27), date);
    }

    @Test
    public void testParseHourMinutes() {
        // Given
        String timeString = "12:30";
        // When
        LocalTime time = TemporalUtil.parseHourMinutes(timeString);
        // Then
        Assert.assertEquals(LocalTime.of(12, 30), time);
    }
}
