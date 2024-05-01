package com.utils.shared.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.utils.shared.util.model.FileEntry;

public class ZipFileUtil {
    private static final int BUFFER_SIZE = 4096;

    private ZipFileUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Compresse un répertoire en un fichier ZIP.
     *
     * @param sourceDir Le chemin du répertoire source à compresser.
     * @param zipFile   Le chemin du fichier ZIP de destination.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la
     *                     compression.
     */
    public static void zip(String sourceDir, String zipFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            zipDirectory(sourceDir, sourceDir, zos);
        }
    }

    private static void zipDirectory(String baseDir, String sourceDir, ZipOutputStream zos) throws IOException {
        File dir = new File(sourceDir);
        for (String fileName : dir.list()) {
            String filePath = sourceDir + File.separator + fileName;
            if (new File(filePath).isDirectory()) {
                zipDirectory(baseDir, filePath, zos);
            } else {
                zipFile(baseDir, filePath, zos);
            }
        }
    }

    private static void zipFile(String baseDir, String filePath, ZipOutputStream zos) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            String entryName = filePath.substring(baseDir.length() + 1);
            ZipEntry zipEntry = new ZipEntry(entryName);
            zos.putNextEntry(zipEntry);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
            zos.closeEntry();
        }
    }

    /**
     * Décompresse un fichier ZIP dans un objet en mémoire.
     *
     * @param zipFile Le chemin du fichier ZIP à décompresser.
     * @return Un objet ByteArrayOutputStream contenant les données décompressées.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la
     *                     décompression.
     */
    public static List<FileEntry> unzip(String zipFile) throws IOException {
        List<FileEntry> fileList = new ArrayList<>();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    fileList.add(extractFile(zipEntry, zis));
                }
            }
        }

        return fileList;
    }

    private static FileEntry extractFile(ZipEntry zipEntry, ZipInputStream zis) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = zis.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        return new FileEntry(zipEntry.getName(), baos.toByteArray());
    }
}
