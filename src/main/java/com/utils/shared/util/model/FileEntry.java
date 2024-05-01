package com.utils.shared.util.model;

public class FileEntry {
    private String fileName;
    private byte[] fileData;

    public FileEntry(String fileName, byte[] fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }
}