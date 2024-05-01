package com.utils.shared.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static final String LINE_SEPARATOR = System.lineSeparator();

    private FileUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Copie un fichier d'un emplacement vers un autre en utilisant un buffer.
     *
     * @param sourceFilePath      le chemin du fichier source
     * @param destinationFilePath le chemin du fichier de destination
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la copie du fichier
     */
    public static void copyFile(String sourceFilePath, String destinationFilePath) throws IOException {
        byte[] buffer = new byte[8192];
        try (FileInputStream fis = new FileInputStream(sourceFilePath); FileOutputStream fos = new FileOutputStream(destinationFilePath)) {
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * Déplace un fichier d'un emplacement vers un autre en utilisant un buffer.
     *
     * @param sourceFilePath      le chemin du fichier source
     * @param destinationFilePath le chemin du fichier de destination
     * @throws IOException si une erreur d'entrée/sortie se produit lors du déplacement du fichier
     */
    public static void moveFile(String sourceFilePath, String destinationFilePath) throws IOException {
        copyFile(sourceFilePath, destinationFilePath);
        deleteFile(sourceFilePath);
    }

    /**
     * Supprime un fichier.
     *
     * @param filePath le chemin du fichier à supprimer
     * @return true si le fichier a été supprimé avec succès, false sinon
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * Vérifie si un fichier est exécutable.
     *
     * @param filePath le chemin du fichier
     * @return true si le fichier est exécutable, false sinon
     */
    public static boolean isExecutableFile(String filePath) {
        File file = new File(filePath);
        return file.canExecute();
    }

    /**
     * Renomme un fichier.
     *
     * @param filePath    le chemin du fichier à renommer
     * @param newFileName le nouveau nom du fichier
     * @return true si le fichier a été renommé avec succès, false sinon
     */
    public static boolean renameFile(String filePath, String newFileName) {
        File file = new File(filePath);
        String parentPath = file.getParent();
        File newFile = new File(parentPath, newFileName);
        return file.renameTo(newFile);
    }

    /**
     * Liste tous les fichiers d'un répertoire.
     *
     * @param directoryPath le chemin du répertoire
     * @return une liste de tous les fichiers trouvés
     */
    public static List<File> listFiles(String directoryPath) {
        List<File> files = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] fileList = directory.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile()) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    /**
     * Recherche récursivement tous les fichiers correspondant à une extension donnée dans un répertoire.
     *
     * @param directoryPath le chemin du répertoire de départ
     * @param extension     l'extension de fichier recherchée
     * @return une liste de tous les fichiers trouvés avec l'extension donnée
     */
    public static List<File> findFilesByExtension(String directoryPath, String extension) {
        List<File> files = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            searchFilesByExtension(directory, extension, files);
        }
        return files;
    }

    private static void searchFilesByExtension(File directory, String extension, List<File> files) {
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile() && file.getName().endsWith(extension)) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    searchFilesByExtension(file, extension, files);
                }
            }
        }
    }

    /**
     * Calcule la taille totale d'un répertoire en octets.
     *
     * @param directoryPath le chemin du répertoire
     * @return la taille totale du répertoire en octets
     */
    public static long getDirectorySize(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            return 0;
        }
        long totalSize = 0;
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    totalSize += file.length();
                } else if (file.isDirectory()) {
                    totalSize += getDirectorySize(file.getAbsolutePath());
                }
            }
        }
        return totalSize;
    }

    /**
     * Crée une structure de répertoires imbriqués.
     *
     * @param directoryPath le chemin du répertoire racine
     * @param directories   les noms des répertoires à créer (y compris les sous-répertoires)
     * @return true si la structure de répertoires a été créée avec succès, false sinon
     */
    public static boolean createDirectoryStructure(String directoryPath, String... directories) {
        File rootDirectory = new File(directoryPath);
        if (!rootDirectory.isDirectory()) {
            return false;
        }
        File currentDirectory = rootDirectory;
        for (String directoryName : directories) {
            currentDirectory = new File(currentDirectory, directoryName);
            if (!currentDirectory.exists() && !currentDirectory.mkdirs()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lit le contenu d'un fichier en tant que chaîne de caractères.
     *
     * @param filePath le chemin du fichier
     * @return le contenu du fichier sous forme de chaîne de caractères
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture du fichier
     */
    public static String readFileAsString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(LINE_SEPARATOR);
            }
        }
        return content.toString();
    }

    /**
     * Écrit une chaîne de caractères dans un fichier.
     *
     * @param filePath le chemin du fichier
     * @param content  la chaîne de caractères à écrire dans le fichier
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'écriture dans le fichier
     */
    public static void writeStringToFile(String filePath, String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
            writer.write(content);
        }
    }
}

