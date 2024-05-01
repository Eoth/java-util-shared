package com.utils.shared.util;

import com.utils.shared.util.container.TesterContainer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ObjectUtilTest {

    @Test
    public void testIsNull() {
        String nullString = null;
        String nonNullString = "Hello";

        Assert.assertTrue(ObjectUtil.isNull(nullString));
        Assert.assertFalse(ObjectUtil.isNull(nonNullString));
    }

    @Test
    public void testIsNotNull() {
        String nullString = null;
        String nonNullString = "Hello";

        Assert.assertFalse(ObjectUtil.isNotNull(nullString));
        Assert.assertTrue(ObjectUtil.isNotNull(nonNullString));
    }

    @Test
    public void testEquals() {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = "World";

        Assert.assertTrue(ObjectUtil.equals(str1, str2));
        Assert.assertFalse(ObjectUtil.equals(str1, str3));
    }

    @Test
    public void testCast() {
        Object obj = "Hello";
        String str = ObjectUtil.cast(obj, String.class);
        Integer intValue = ObjectUtil.cast(obj, Integer.class);

        Assert.assertEquals(str, "Hello");
        Assert.assertNull(intValue);
    }

    @Test
    public void testDeepCopy() {
        TesterContainer obj = new TesterContainer();
        obj.setName("Hello");
        TesterContainer copy = ObjectUtil.deepCopy(obj);

        Assert.assertNotNull(copy);
        Assert.assertNotSame(obj, copy);
        Assert.assertEquals(obj.getName(), copy.getName());
    }
}
