package com.utils.shared.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.utils.shared.util.model.FileEntry;

public class ZipFileUtilTest {

    @Test
    public void ziptest() throws IOException {
        final String path = Thread.currentThread().getContextClassLoader().getResource("note.xml").getPath();
        final String directory = getParentDirectory(path);
        final String sourceDir = directory + File.separator + "test_note.zip";

        ZipFileUtil.zip(directory, sourceDir);

        Assert.assertNotNull(Thread.currentThread().getContextClassLoader().getResource("test_note.zip"));
    }
    
    @Test
    public void unziptest() throws IOException {
        final String path = Thread.currentThread().getContextClassLoader().getResource("NOTE.zip").getPath();

        final List<FileEntry> actual = ZipFileUtil.unzip(path);

        Assert.assertEquals(actual.size(), 2);
    }

    private static String getParentDirectory(String filePath) {
        String parentDirPath = "";
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                parentDirPath = file.getParent();
            }
        }
        return parentDirPath;
    }

}
