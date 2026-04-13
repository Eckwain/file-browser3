package com.example.filebrowser.util;

import java.io.File;
import java.io.IOException;

public class PathUtil {

    private PathUtil() {
    }

    public static File getDefaultDirectory() {
        return new File(System.getProperty("user.home"));
    }

    public static File resolveDirectory(String pathParam) {
        if (pathParam == null || pathParam.isBlank()) {
            return getDefaultDirectory();
        }

        File file = new File(pathParam);
        try {
            file = file.getCanonicalFile();
        } catch (IOException e) {
            return getDefaultDirectory();
        }

        if (file.exists() && file.isDirectory()) {
            return file;
        }

        return getDefaultDirectory();
    }

    public static File resolveFile(String pathParam) {
        if (pathParam == null || pathParam.isBlank()) {
            return null;
        }

        File file = new File(pathParam);
        try {
            file = file.getCanonicalFile();
        } catch (IOException e) {
            return null;
        }

        if (file.exists() && file.isFile()) {
            return file;
        }

        return null;
    }
}