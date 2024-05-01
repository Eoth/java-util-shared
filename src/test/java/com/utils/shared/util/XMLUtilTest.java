package com.utils.shared.util;

import com.utils.shared.util.container.TesterContainer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class XMLUtilTest {

    @Test
    public void testToStringAsXML() {
        // Given
        TesterContainer obj = new TesterContainer();
        obj.setName("John Doe");
        obj.setAge(30);
        obj.setActive(false);
        // When
        String xml = XMLUtil.toStringAsXML(obj);
        // Then
        String expectedXml = "<TesterContainer><name>John Doe</name><age>30</age><active>false</active></TesterContainer>";
        Assert.assertEquals(xml, expectedXml);
    }

    @Test
    public void testToStringAsXMLWithFormatting() {
        // Given
        TesterContainer obj = new TesterContainer();
        obj.setName("Jane Smith");
        obj.setAge(25);
        // When
        String xml = XMLUtil.toStringAsXML(obj, true);
        // Then
        String expectedXml = "<TesterContainer>\n  <name>Jane Smith</name>\n  <age>25</age>\n</TesterContainer>";
        Assert.assertEquals(xml, expectedXml);
    }

    @Test
    public void testToObject() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><TesterContainer><name>John Doe</name><age>30</age></TesterContainer>";
        // When
        TesterContainer obj = XMLUtil.toObject(xml, TesterContainer.class);
        // Then
        Assert.assertEquals(obj.getName(), "John Doe");
        Assert.assertEquals(obj.getAge(), 30);
    }

    @Test(expectedExceptions = XMLUtil.XMLManipulationException.class)
    public void testToStringAsXMLException() {
        // Given
        MyObjectWithInvalidXml obj = new MyObjectWithInvalidXml();
        // When
        XMLUtil.toStringAsXML(obj);
        // Then (Exception expected)
    }

    @Test(expectedExceptions = XMLUtil.XMLManipulationException.class)
    public void testToObjectException() {
        // Given
        String invalidXml = "<InvalidXml><name>John Doe</name></InvalidXml>";
        // When
        XMLUtil.toObject(invalidXml, TesterContainer.class);
        // Then (Exception expected)
    }

    private static class MyObjectWithInvalidXml {
        // Missing required properties for XML marshalling
    }
}
