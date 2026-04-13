package com.example.filebrowser.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileItem {

    private final String name;
    private final String path;
    private final boolean directory;
    private final long size;
    private final long lastModified;

    public FileItem(String name, String path, boolean directory, long size, long lastModified) {
        this.name = name;
        this.path = path;
        this.directory = directory;
        this.size = size;
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return directory;
    }

    public long getSize() {
        return size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getFormattedSize() {
        if (directory) {
            return "—";
        }

        if (size < 1024) {
            return size + " B";
        }
        if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        }
        if (size < 1024L * 1024L * 1024L) {
            return String.format("%.2f MB", size / 1024.0 / 1024.0);
        }
        return String.format("%.2f GB", size / 1024.0 / 1024.0 / 1024.0);
    }

    public String getFormattedDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lastModified));
    }
}