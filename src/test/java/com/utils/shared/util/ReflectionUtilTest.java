package com.utils.shared.util;

import com.utils.shared.util.container.TesterContainer;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReflectionUtilTest {

    @Test
    public void testSetFieldValue_intField_setsIntValue() {
        TesterContainer obj = new TesterContainer();
        ReflectionUtil.setFieldValue(obj, "age", 42);
        assertEquals(obj.getAge(), 42);
    }

    @Test
    public void testSetFieldValue_stringField_setsStringValue() {
        TesterContainer obj = new TesterContainer();
        ReflectionUtil.setFieldValue(obj, "name", "John Doe");
        assertEquals(obj.getName(), "John Doe");
    }

    @Test
    public void testSetFieldValue_booleanField_setsBooleanValue() {
        TesterContainer obj = new TesterContainer();
        ReflectionUtil.setFieldValue(obj, "active", true);
        assertTrue(obj.isActive());
    }

    @Test(expectedExceptions = ReflectionUtil.ReflectionException.class)
    public void testSetFieldValue_nonExistingField_throwsReflectionException() {
        TesterContainer obj = new TesterContainer();
        ReflectionUtil.setFieldValue(obj, "nonExistingField", "value");
    }

    @Test
    public void testCreateInstance() {
        TesterContainer instance = ReflectionUtil.createInstance(TesterContainer.class);

        Assert.assertNotNull(instance);
    }

    @Test
    public void testCreateInstance_privateClass_cannotCreateInstance() {
        UnAccessibleTesterContainer instance = ReflectionUtil.createInstance(UnAccessibleTesterContainer.class);

        Assert.assertNull(instance);
    }

    private static class UnAccessibleTesterContainer {
    }
}
