package com.utils.shared.unit.test.assertions;

import com.utils.shared.util.MapUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ObjectComparatorTest {
    @Test
    public void testCompareObjects() {
        Object expected = "Hello";
        Object actual = "Hello";
        ObjectComparator.compare(expected, actual);
    }

    @Test
    public void testCompareObjects_DifferentTypes() {
        Object expected = "Hello";
        Object actual = 123;
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, actual));
    }

    @Test
    public void testCompareObjects_Null() {
        Object expected = "Hello";
        Object actual = null;
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, actual));
    }

    @Test
    public void testCompareObjects_ImmutableType() {
        Object expected = BigDecimal.valueOf(10.5);
        Object actual = BigDecimal.valueOf(10.5);
        ObjectComparator.compare(expected, actual);
    }

    @Test
    public void testCompareObjects_ImmutableType_NotEqual() {
        Object expected = BigDecimal.valueOf(10.5);
        Object actual = BigDecimal.valueOf(5.0);
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, actual));
    }

    @Test
    public void testCompareObjects_Collections() {
        Collection<String> expected = Arrays.asList("a", "b", "c");
        Collection<String> actual = Arrays.asList("a", "b", "c");
        ObjectComparator.compare(expected, actual);
    }

    @Test
    public void testCompareObjects_Collections_DifferentSizes() {
        Collection<String> expected = Arrays.asList("a", "b", "c");
        Collection<String> actual = Arrays.asList("a", "b");
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, actual));
    }

    @Test
    public void testCompareObjects_Collections_DifferentElements() {
        Collection<String> expected = Arrays.asList("a", "b", "c");
        Collection<String> actual = Arrays.asList("a", "x", "c");
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, actual));
    }

    @Test
    public void testCompareObjects_Collections_Empty() {
        Collection<String> expected = Collections.emptyList();
        Collection<String> actual = Collections.emptyList();
        ObjectComparator.compare(expected, actual);
    }

    @Test
    public void testCompareObjects_Collections_Null() {
        Collection<String> expected = Arrays.asList("a", "b", "c");
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, null));
    }

    @Test
    public void testCompareObjects_ComplexObjects() {
        ComplexObject expected = new ComplexObject("John", 25, Arrays.asList("a", "b", "c"),
                MapUtil.createMap("Marseille", 1000, "Lille", 55), new Double[]{12.5, 25.0});
        ComplexObject actual = new ComplexObject("John", 25, Arrays.asList("b", "a", "c"),
                MapUtil.createMap("Lille", 55, "Marseille", 1000), new Double[]{25.0, 12.5});
        ObjectComparator.compare(expected, actual);
    }

    @Test
    public void testCompareObjects_ComplexObjects_DifferentFields() {
        ComplexObject expected = new ComplexObject("John", 25, Arrays.asList("a", "b", "c"), MapUtil.createMap(),
                new Double[]{});
        ComplexObject actual = new ComplexObject("Jane", 30, Arrays.asList("x", "y", "z"), MapUtil.createMap(),
                new Double[]{});
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, actual));
    }

    @Test
    public void testCompareObjects_ComplexObjects_Null() {
        ComplexObject expected = new ComplexObject("John", 25, Arrays.asList("a", "b", "c"), MapUtil.createMap(),
                new Double[]{});
        Assert.assertThrows(AssertionError.class, () -> ObjectComparator.compare(expected, null));
    }

    private static class ComplexObject {
        private final String name;
        private final int age;
        private final List<String> values;
        private final Map<String, Integer> cityDistanceMap;
        private final Double[] prices;

        public ComplexObject(String name, int age, List<String> values, Map<String, Integer> cityDistanceMap, Double[] prices) {
            this.name = name;
            this.age = age;
            this.values = values;
            this.cityDistanceMap = cityDistanceMap;
            this.prices = prices;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public List<String> getValues() {
            return values;
        }

        public Map<String, Integer> getCityDistanceMap() {
            return cityDistanceMap;
        }

        public Double[] getPrices() {
            return prices;
        }
    }
}
