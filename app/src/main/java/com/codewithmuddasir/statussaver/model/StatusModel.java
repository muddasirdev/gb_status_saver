package com.codewithmuddasir.statussaver.model;

import com.codewithmuddasir.statussaver.util.FileType;

public class StatusModel {

    public String pathUri;

    public String fileName;
    public FileType type;
    public boolean isDownloaded;

    public StatusModel(String pathUri, String fileName, FileType type, boolean isDownloaded) {
        this.pathUri = pathUri;
        this.fileName = fileName;
        this.type = type;
        this.isDownloaded = isDownloaded;
    }

    public String getPathUri() {
        return pathUri;
    }

    public String getFileName() {
        return fileName;
    }

    public FileType getType() {
        return type;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }
}
