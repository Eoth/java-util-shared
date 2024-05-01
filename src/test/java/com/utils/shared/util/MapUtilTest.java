package com.utils.shared.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class MapUtilTest {
    @Test
    public void testCreateMap() {
        // Arrange
        Object[] keyValues = {"key1", 1, "key2", 2, "key3", 3};

        // Act
        Map<String, Integer> result = MapUtil.createMap(keyValues);

        // Assert
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("key1", 1);
        expectedMap.put("key2", 2);
        expectedMap.put("key3", 3);

        Assert.assertEquals(expectedMap, result);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMapWithOddNumberOfArguments() {
        // Arrange
        Object[] keyValues = {"key1", 1, "key2"};

        // Act
        Map<String, Integer> result = MapUtil.createMap(keyValues);
    }

}