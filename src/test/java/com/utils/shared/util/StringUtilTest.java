package com.utils.shared.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StringUtilTest {

    @Test
    public void testIsNullOrEmpty_NullString_ReturnsTrue() {

        Assert.assertTrue(StringUtil.isNullOrEmpty(null));
    }

    @Test
    public void testIsNullOrEmpty_EmptyString_ReturnsTrue() {
        String emptyString = "";

        Assert.assertTrue(StringUtil.isNullOrEmpty(emptyString));
    }

    @Test
    public void testIsNullOrEmpty_NonEmptyString_ReturnsFalse() {
        String nonEmptyString = "Hello";

        Assert.assertFalse(StringUtil.isNullOrEmpty(nonEmptyString));
    }

    @Test
    public void testIsNotNullOrEmpty_NullString_ReturnsFalse() {
        String nullString = null;

        Assert.assertFalse(StringUtil.isNotNullOrEmpty(nullString));
    }

    @Test
    public void testIsNotNullOrEmpty_EmptyString_ReturnsFalse() {
        String emptyString = "";

        Assert.assertFalse(StringUtil.isNotNullOrEmpty(emptyString));
    }

    @Test
    public void testIsNotNullOrEmpty_NonEmptyString_ReturnsTrue() {
        String nonEmptyString = "Hello";

        Assert.assertTrue(StringUtil.isNotNullOrEmpty(nonEmptyString));
    }

    @Test
    public void testReverse_NullString_ReturnsNull() {
        String nullString = null;

        Assert.assertNull(StringUtil.reverse(nullString));
    }

    @Test
    public void testReverse_EmptyString_ReturnsEmptyString() {
        String emptyString = "";

        Assert.assertEquals(StringUtil.reverse(emptyString), "");
    }

    @Test
    public void testReverse_NormalString_ReturnsReversedString() {
        String normalString = "Hello";

        Assert.assertEquals(StringUtil.reverse(normalString), "olleH");
    }

    @Test
    public void testCountOccurrences_NullString_ReturnsZero() {
        String nullString = null;
        String substring = "hello";

        Assert.assertEquals(StringUtil.countOccurrences(nullString, substring, true), 0);
    }

    @Test
    public void testToCharArrayWithEmptyString() {
        String emptyString = "";
        char[] result = StringUtil.toCharArray(emptyString);
        Assert.assertEquals(result.length, 0);
    }

    @Test
    public void testToCharArrayWithNonEmptyString() {
        String str = "Hello";
        char[] result = StringUtil.toCharArray(str);
        Assert.assertEquals(result.length, str.length());
        for (int i = 0; i < str.length(); i++) {
            Assert.assertEquals(result[i], str.charAt(i));
        }
    }

    @Test
    public void testIsPalindromeWithPalindromeString() {
        String palindrome = "level";
        boolean result = StringUtil.isPalindrome(palindrome);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsPalindromeWithNonPalindromeString() {
        String nonPalindrome = "hello";
        boolean result = StringUtil.isPalindrome(nonPalindrome);
        Assert.assertFalse(result);
    }

    @Test
    public void testIsPalindromeWithEmptyString() {
        String emptyString = "";
        boolean result = StringUtil.isPalindrome(emptyString);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsPalindromeWithSingleCharString() {
        String singleChar = "a";
        boolean result = StringUtil.isPalindrome(singleChar);
        Assert.assertTrue(result);
    }

    @Test
    public void testCountOccurrences_EmptyString_ReturnsZero() {
        String emptyString = "";
        String substring = "hello";

        Assert.assertEquals(StringUtil.countOccurrences(emptyString, substring, true), 0);
    }

    @Test
    public void testCountOccurrences_NullSubstring_ReturnsZero() {
        String str = "Hello, hello, hEllo";
        String nullString = null;

        Assert.assertEquals(StringUtil.countOccurrences(str, nullString, true), 0);
    }

    @Test
    public void testCountOccurrences_EmptySubstring_ReturnsZero() {
        String str = "Hello, hello, hEllo";
        String emptyString = "";

        Assert.assertEquals(StringUtil.countOccurrences(str, emptyString, true), 0);
    }

    @Test
    public void testCountOccurrences_CaseSensitive_ReturnsCorrectCount() {
        String str = "Hello, hello, hEllo";
        String substring = "hello";

        Assert.assertEquals(StringUtil.countOccurrences(str, substring, false), 1);
    }

    @Test
    public void testCountOccurrences_CaseInsensitive_ReturnsCorrectCount() {
        String str = "Hello, hello, hEllo";
        String substring = "hello";

        Assert.assertEquals(StringUtil.countOccurrences(str, substring, true), 3);
    }

    @Test
    public void testTruncate_NullString_ReturnsNull() {
        String nullString = null;
        int length = 10;

        Assert.assertNull(StringUtil.truncate(nullString, length));
    }

    @Test
    public void testTruncate_EmptyString_ReturnsEmptyString() {
        String emptyString = "";
        int length = 10;

        Assert.assertEquals(StringUtil.truncate(emptyString, length), "");
    }

    @Test
    public void testTruncate_LongString_TruncatesAndAddsEllipsis() {
        String longString = "This is a long string";
        int length = 9;

        Assert.assertEquals(StringUtil.truncate(longString, length), "This is a...");
    }

    @Test
    public void testEscape() {
        String input = "Hello\tWorld\n\"Etokan\"";
        String expectedOutput = "Hello\\tWorld\\n\\\"Etokan\\\"";
        String escaped = StringUtil.escape(input);
        Assert.assertEquals(expectedOutput, escaped);
    }

    @Test
    public void testDeCapitalizeWithEmptyString() {
        String result = StringUtil.deCapitalize("");
        Assert.assertEquals("", result);
    }

    @Test
    public void testDeCapitalizeWithLowercaseString() {
        String result = StringUtil.deCapitalize("hello");
        Assert.assertEquals("hello", result);
    }

    @Test
    public void testDeCapitalizeWithUppercaseString() {
        String result = StringUtil.deCapitalize("World");
        Assert.assertEquals("world", result);
    }

    @Test
    public void testDeCapitalizeWithUppercaseCharacter() {
        String result = StringUtil.deCapitalize("X");
        Assert.assertEquals("x", result);
    }

    @Test
    public void testDeCapitalizeWithMultipleWords() {
        String result = StringUtil.deCapitalize("This is a test");
        Assert.assertEquals("this is a test", result);
    }

    @Test
    public void testCapitalizeWithEmptyString() {
        String result = StringUtil.capitalize("");
        Assert.assertEquals("", result);
    }

    @Test
    public void testCapitalizeWithLowercaseString() {
        String result = StringUtil.capitalize("hello");
        Assert.assertEquals("Hello", result);
    }

    @Test
    public void testCapitalizeWithUppercaseString() {
        String result = StringUtil.capitalize("World");
        Assert.assertEquals("World", result);
    }

    @Test
    public void testCapitalizeWithLowercaseCharacter() {
        String result = StringUtil.capitalize("x");
        Assert.assertEquals("X", result);
    }

    @Test
    public void testCapitalizeWithMultipleWords() {
        String result = StringUtil.capitalize("this is a test");
        Assert.assertEquals("This is a test", result);
    }

    @Test
    public void testRemoveDiacriticalMarksWithEmptyString() {
        String result = StringUtil.removeDiacriticalMarks("");
        Assert.assertEquals("", result);
    }

    @Test
    public void testRemoveDiacriticalMarksWithDiacritics() {
        String result = StringUtil.removeDiacriticalMarks("Café");
        Assert.assertEquals("Cafe", result);
    }

    @Test
    public void testRemoveDiacriticalMarksWithOnlyDiacritics() {
        String result = StringUtil.removeDiacriticalMarks("áéíóú");
        Assert.assertEquals("aeiou", result);
    }

    @Test
    public void testRemoveDiacriticalMarksWithNonDiacritics() {
        String result = StringUtil.removeDiacriticalMarks("Hello World");
        Assert.assertEquals("Hello World", result);
    }

}

