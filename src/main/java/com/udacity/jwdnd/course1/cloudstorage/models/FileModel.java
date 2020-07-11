package com.udacity.jwdnd.course1.cloudstorage.models;

public class FileModel {

    private String filename;

    private String contentType;

    private String fileSize;

    private byte[] fileData;

    public FileModel(String filename, String contentType, String fileSize, byte[] fileData) {
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
