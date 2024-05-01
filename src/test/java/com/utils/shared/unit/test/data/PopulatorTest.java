package com.utils.shared.unit.test.data;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PopulatorTest {

    @Test
    void testPopulate_shouldPopulateWithoutOption() {
        // When
        MyObject myObject = Populator.populate(new MyObject(), 1);
        // Then
        Assert.assertEquals(myObject.cle, 1);
        Assert.assertEquals(myObject.name, "name1");
        Assert.assertEquals(myObject.age, 1);
        Assert.assertFalse(myObject.isMan);
        Assert.assertEquals(myObject.tall, 1);
    }

    @Test
    void testPopulate_shouldPopulateWithOption() {
        // Given
        Option options = new Option()
                .rename("name", "newName")
                .assignValue("age", 42)
                .assignValue("field4", (byte) 10)
                .assignValue("field5", (short) 1000)
                .assignNull("tall")
                .ignoreFields("cle");
        // When
        MyObject myObject = Populator.populate(new MyObject(), 1, options);
        // Then
        Assert.assertNull(myObject.cle);
        Assert.assertEquals(myObject.name, "newName1");
        Assert.assertEquals(myObject.age, 42);
        Assert.assertFalse(myObject.isMan);
        Assert.assertNull(myObject.tall);
    }

    static class MyObject {
        private Integer cle;
        private String name;
        private int age;
        private boolean isMan;
        private byte field4;
        private short field5;
        private Long tall = 2L;
        private float field7;
        private double field8;
        private char field9;
        private List<String> field10;
        private Set<String> fieldForSetCollection;
        private Map<String, Integer> field11;

        // Constructeur

        // Getter et Setter pour les champs
    }
}