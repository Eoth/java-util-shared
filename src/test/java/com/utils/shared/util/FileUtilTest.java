package com.utils.shared.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtilTest {

    @Test
    public void testListFilesInDirectory() {
        String directoryPath = "path/to/directory";
        List<File> files = FileUtil.listFiles(directoryPath);
        Assert.assertNotNull(files);
        Assert.assertEquals(2, files.size());
        // Additional assertions on the files if necessary
    }

    @Test
    public void testFindFilesByExtension() {
        String directoryPath = "path/to/directory";
        String extension = "txt";
        List<File> files = FileUtil.findFilesByExtension(directoryPath, extension);
        Assert.assertNotNull(files);
        Assert.assertEquals(2, files.size());
        // Additional assertions on the files if necessary
    }

    @Test
    public void testGetDirectorySize() {
        String directoryPath = "path/to/directory";
        long size = FileUtil.getDirectorySize(directoryPath);
        Assert.assertEquals(1024, size);
    }

    @Test
    public void testGetDirectorySize_invalidDirectory() {
        String directoryPath = "invalid/directory";
        long size = FileUtil.getDirectorySize(directoryPath);
        Assert.assertEquals(0, size);
    }

    @Test
    public void testCreateDirectoryStructure() {
        String directoryPath = "path/to/root";
        String[] directories = {"dir1", "dir2", "dir3"};
        boolean success = FileUtil.createDirectoryStructure(directoryPath, directories);
        Assert.assertTrue(success);
        // Additional assertions on the created directories if necessary
    }

    @Test
    public void testCreateDirectoryStructure_invalidRoot() {
        String directoryPath = "invalid/root";
        String[] directories = {"dir1", "dir2", "dir3"};
        boolean success = FileUtil.createDirectoryStructure(directoryPath, directories);
        Assert.assertFalse(success);
    }

    @Test
    public void testReadFileAsString() throws IOException {
        String filePath = "path/to/file.txt";
        String content = FileUtil.readFileAsString(filePath);
        Assert.assertNotNull(content);
        // Additional assertions on the file content if necessary
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFileAsString_invalidFile() throws IOException {
        String filePath = "invalid/file.txt";
        FileUtil.readFileAsString(filePath);
    }

    @Test
    public void testWriteStringToFile() throws IOException {
        String filePath = "path/to/file.txt";
        String content = "Hello, World!";
        FileUtil.writeStringToFile(filePath, content);
        // Additional assertions on the written file if necessary
    }

    @Test(expectedExceptions = IOException.class)
    public void testWriteStringToFile_invalidFile() throws IOException {
        String filePath = "invalid/file.txt";
        String content = "Hello, World!";
        FileUtil.writeStringToFile(filePath, content);
    }
}
