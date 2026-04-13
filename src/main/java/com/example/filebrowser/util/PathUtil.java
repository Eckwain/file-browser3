package com.example.filebrowser.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class PathUtil {

    private PathUtil() {
    }

    public static File resolveWithinHome(File homeDir, String pathParam) {
        try {
            Path homePath = homeDir.getCanonicalFile().toPath();

            if (pathParam == null || pathParam.isBlank()) {
                return homePath.toFile();
            }

            Path candidate = new File(pathParam).getCanonicalFile().toPath();

            if (!candidate.startsWith(homePath)) {
                return homePath.toFile();
            }

            File file = candidate.toFile();
            if (file.exists() && file.isDirectory()) {
                return file;
            }

            return homePath.toFile();
        } catch (IOException e) {
            return homeDir;
        }
    }

    public static File resolveFileWithinHome(File homeDir, String pathParam) {
        try {
            Path homePath = homeDir.getCanonicalFile().toPath();
            Path candidate = new File(pathParam).getCanonicalFile().toPath();

            if (!candidate.startsWith(homePath)) {
                return null;
            }

            File file = candidate.toFile();
            if (file.exists() && file.isFile()) {
                return file;
            }

            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
