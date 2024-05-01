package com.utils.shared.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilTest {

    @Test
    public void testFormatDate() {
        Date date = createDate(2023, 6, 22);
        String formattedDate = DateUtil.formatDate(date, "dd/MM/yyyy");
        Assert.assertEquals(formattedDate, "22/06/2023");
    }

    @Test
    public void testFormatDate_NullDate() {
        Assert.assertNull(DateUtil.formatDate(null, "dd/MM/yyyy"));
    }

    @Test
    public void testParseDate() {
        String dateString = "22/06/2023";
        Date parsedDate = DateUtil.parseDate(dateString, "dd/MM/yyyy");
        Date expectedDate = createDate(2023, 6, 22);
        Assert.assertEquals(parsedDate, expectedDate);
    }

    @Test
    public void testParseDate_NullDate() {
        Assert.assertNull(DateUtil.parseDate(null, "dd/MM/yyyy"));
    }

    @Test
    public void testParseDate_InvalidFormat() {
        String dateString = "06-22-2023";
        Date parsedDate = DateUtil.parseDate(dateString, "dd/MM/yyyy");
        Assert.assertNull(parsedDate);
    }

    @Test
    public void testAddDays() {
        Date date = createDate(2023, 6, 22);
        Date newDate = DateUtil.addDays(date, 3);
        Date expectedDate = createDate(2023, 6, 25);
        Assert.assertEquals(newDate, expectedDate);
    }

    @Test
    public void testAreEqualDates() {
        Date date1 = createDate(2023, 6, 22);
        Date date2 = createDate(2023, 6, 22);
        Assert.assertTrue(DateUtil.areEqualDates(date1, date2));
    }

    @Test
    public void testAreEqualDates_DifferentDates() {
        Date date1 = createDate(2023, 6, 22);
        Date date2 = createDate(2023, 6, 23);
        Assert.assertFalse(DateUtil.areEqualDates(date1, date2));
    }

    @Test
    public void testIsBefore() {
        Date date1 = createDate(2023, 6, 22);
        Date date2 = createDate(2023, 6, 23);
        Assert.assertTrue(DateUtil.isBefore(date1, date2));
    }

    @Test
    public void testIsBefore_NotBefore() {
        Date date1 = createDate(2023, 6, 23);
        Date date2 = createDate(2023, 6, 22);
        Assert.assertFalse(DateUtil.isBefore(date1, date2));
    }

    @Test
    public void testIsAfter() {
        Date date1 = createDate(2023, 6, 23);
        Date date2 = createDate(2023, 6, 22);
        Assert.assertTrue(DateUtil.isAfter(date1, date2));
    }

    @Test
    public void testIsAfter_NotAfter() {
        Date date1 = createDate(2023, 6, 22);
        Date date2 = createDate(2023, 6, 23);
        Assert.assertFalse(DateUtil.isAfter(date1, date2));
    }

    @Test
    public void testGetDifferenceInDays() {
        Date date1 = createDate(2023, 6, 22);
        Date date2 = createDate(2023, 6, 25);
        int difference = DateUtil.getDifferenceInDays(date1, date2);
        Assert.assertEquals(difference, 3);
    }

    @Test
    public void testCalculateAge() {
        Date birthDate = createDate(1990, 5, 15);
        int age = DateUtil.calculateAge(birthDate);
        Assert.assertEquals(age, 33);
    }

    // Helper method to create a Date object
    private Date createDate(int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
