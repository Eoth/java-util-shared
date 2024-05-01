package com.utils.shared.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ArrayUtilTest {

    @Test
    public void testIsEmpty_NullArray_ReturnsTrue() {
        Assert.assertTrue(ArrayUtil.isEmpty(null));
    }

    @Test
    public void testIsEmpty_EmptyArray_ReturnsTrue() {
        Integer[] array = new Integer[0];
        boolean isEmpty = ArrayUtil.isEmpty(array);
        Assert.assertTrue(isEmpty);
    }

    @Test
    public void testIsEmpty_NonEmptyArray_ReturnsFalse() {
        Integer[] array = {1, 2, 3};
        boolean isEmpty = ArrayUtil.isEmpty(array);
        Assert.assertFalse(isEmpty);
    }

    @Test
    public void testContains_NullArray_ReturnsFalse() {
        Assert.assertFalse(ArrayUtil.contains(null, 1));
    }

    @Test
    public void testContains_ElementNotInArray_ReturnsFalse() {
        Integer[] array = {1, 2, 3};
        boolean contains = ArrayUtil.contains(array, 4);
        Assert.assertFalse(contains);
    }

    @Test
    public void testContains_ElementInArray_ReturnsTrue() {
        Integer[] array = {1, 2, 3};
        boolean contains = ArrayUtil.contains(array, 2);
        Assert.assertTrue(contains);
    }

    @Test
    public void testIndexOf_NullArray_ReturnsMinusOne() {
        Integer[] array = null;
        int index = ArrayUtil.indexOf(array, 1);
        Assert.assertEquals(index, -1);
    }

    @Test
    public void testIndexOf_ElementNotInArray_ReturnsMinusOne() {
        Integer[] array = {1, 2, 3};
        int index = ArrayUtil.indexOf(array, 4);
        Assert.assertEquals(index, -1);
    }

    @Test
    public void testIndexOf_ElementInArray_ReturnsCorrectIndex() {
        Integer[] array = {1, 2, 3};
        int index = ArrayUtil.indexOf(array, 2);
        Assert.assertEquals(index, 1);
    }

    @Test
    public void testRemoveElement_NullArray_ReturnsNull() {
        Integer[] array = null;
        Integer[] result = ArrayUtil.removeElement(array, 1);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveElement_ElementNotInArray_ReturnsSameArray() {
        Integer[] array = {1, 2, 3};
        Integer[] result = ArrayUtil.removeElement(array, 4);
        Assert.assertEquals(result, array);
    }

    @Test
    public void testRemoveElement_ElementInArray_ReturnsArrayWithoutElement() {
        Integer[] array = {1, 2, 3};
        Integer[] expected = {1, 3};
        Integer[] result = ArrayUtil.removeElement(array, 2);
        Assert.assertEquals(result, expected);
    }

    @Test
    public void testRemoveDuplicates_NullArray_ReturnsNull() {
        Assert.assertNull(ArrayUtil.removeDuplicates(null));
    }

    @Test
    public void testRemoveDuplicates_ArrayWithoutDuplicates_ReturnsSameArray() {
        Integer[] array = {1, 2, 3};
        Integer[] result = ArrayUtil.removeDuplicates(array);
        Assert.assertEquals(result, array);
    }

    @Test
    public void testRemoveDuplicates_ArrayWithDuplicates_ReturnsArrayWithoutDuplicates() {
        Integer[] array = {1, 2, 2, 3, 3, 3};
        Integer[] expected = {1, 2, 3};
        Integer[] result = ArrayUtil.removeDuplicates(array);
        Assert.assertEquals(result, expected);
    }

    @Test
    public void testReverse_NullArray_ReturnsNull() {
        Assert.assertNull(ArrayUtil.reverse(null));
    }

    @Test
    public void testReverse_NonEmptyArray_ReturnsReversedArray() {
        Integer[] array = {1, 2, 3};
        Integer[] expected = {3, 2, 1};
        Integer[] result = ArrayUtil.reverse(array);
        Assert.assertEquals(result, expected);
    }

    @Test
    public void testMerge_NullArrays_ReturnsNull() {
        Assert.assertNull(ArrayUtil.merge(null, null));
    }

    @Test
    public void testMerge_NullArrayWithNonEmptyArray_ReturnsNonEmptyArray() {
        Integer[] array1 = null;
        Integer[] array2 = {1, 2, 3};
        Integer[] result = ArrayUtil.merge(array1, array2);
        Assert.assertEquals(result, array2);
    }

    @Test
    public void testMerge_NonEmptyArrayWithNullArray_ReturnsNonEmptyArray() {
        Integer[] array1 = {1, 2, 3};
        Integer[] array2 = null;
        Integer[] result = ArrayUtil.merge(array1, array2);
        Assert.assertEquals(result, array1);
    }

    @Test
    public void testMerge_TwoNonEmptyArrays_ReturnsMergedArray() {
        Integer[] array1 = {1, 2, 3};
        Integer[] array2 = {4, 5, 6};
        Integer[] expected = {1, 2, 3, 4, 5, 6};
        Integer[] result = ArrayUtil.merge(array1, array2);
        Assert.assertEquals(result, expected);
    }

    @Test
    public void testGetSubArray_NullArray_ReturnsNull() {
        Integer[] array = null;
        Integer[] result = ArrayUtil.subArray(array, 1, 3);
        Assert.assertNull(result);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetSubArray_InvalidIndexes_ReturnsNull() {
        Integer[] array = {1, 2, 3};
        ArrayUtil.subArray(array, 3, 1);
    }

    @Test
    public void testGetSubArray_ValidIndexes_ReturnsSubArray() {
        Integer[] array = {1, 2, 3, 4, 5};
        Integer[] expected = {2, 3, 4};
        Integer[] result = ArrayUtil.subArray(array, 1, 4);
        Assert.assertEquals(result, expected);
    }

    @Test
    public void testSort_NullArray_ReturnsNull() {
        Integer[] array = null;
        Integer[] result = ArrayUtil.sort(array);
        Assert.assertNull(result);
    }

    @Test
    public void testSort_EmptyArray_ReturnsEmptyArray() {
        Integer[] array = new Integer[0];
        Integer[] result = ArrayUtil.sort(array);
        Assert.assertEquals(result.length, 0);
    }

    @Test
    public void testSort_NonEmptyArray_ReturnsSortedArray() {
        Integer[] array = {5, 2, 4, 1, 3};
        Integer[] expected = {1, 2, 3, 4, 5};
        Integer[] result = ArrayUtil.sort(array);
        Assert.assertEquals(result, expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMax_NullArray_ReturnsNull() {
        Assert.assertNull(ArrayUtil.findMax(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMax_EmptyArray_ReturnsNull() {
        Integer[] array = new Integer[0];
        ArrayUtil.findMax(array);
    }

    @Test
    public void testFindMax_NonEmptyArray_ReturnsMaxElement() {
        Integer[] array = {5, 2, 4, 1, 3};
        Integer max = ArrayUtil.findMax(array);
        Assert.assertEquals(max, Integer.valueOf(5));
    }
}
