package com.utils.shared.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtilTest {

    @Test
    public void testToJson_NullObject_ReturnsNull() {
        String json = JsonUtil.toJson(null);
        Assert.assertEquals("null", json);
    }

    @Test
    public void testToJson_StringObject_ReturnsJsonString() {
        String str = "Hello, World!";
        String expectedJson = "\"Hello, World!\"";
        String json = JsonUtil.toJson(str);
        Assert.assertEquals(expectedJson, json);
    }

    @Test
    public void testToJson_NumberObject_ReturnsJsonNumber() {
        int number = 42;
        String expectedJson = "42";
        String json = JsonUtil.toJson(number);
        Assert.assertEquals(expectedJson, json);
    }

    @Test
    public void testToJson_BooleanObject_ReturnsJsonBoolean() {
        boolean bool = true;
        String expectedJson = "true";
        String json = JsonUtil.toJson(bool);
        Assert.assertEquals(expectedJson, json);
    }

    @Test
    public void testToJson_ArrayObject_ReturnsJsonArray() {
        String[] array = {"foo", "bar", "baz"};
        String expectedJson = "[\"foo\",\"bar\",\"baz\"]";
        String json = JsonUtil.toJson(array);
        Assert.assertEquals(expectedJson, json);
    }

    @Test
    public void testToJson_CollectionObject_ReturnsJsonArray() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        list.add("baz");
        String expectedJson = "[\"foo\",\"bar\",\"baz\"]";
        String json = JsonUtil.toJson(list);
        Assert.assertEquals(expectedJson, json);
    }

    @Test
    public void testToJson_MapObject_ReturnsJsonObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "John Doe");
        map.put("age", 30);
        map.put("employed", true);
        String expectedJson = "{\"name\":\"John Doe\",\"age\":30,\"employed\":true}";
        String json = JsonUtil.toJson(map);
        Assert.assertEquals(expectedJson, json);
    }
}
