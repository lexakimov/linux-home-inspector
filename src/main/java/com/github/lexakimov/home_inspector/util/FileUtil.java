package com.github.lexakimov.home_inspector.util;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtil {

    public static final String HOME_DIRECTORY_PATH = System.getProperty("user.home");

    public static File userHome() {
        var userHome = new File(HOME_DIRECTORY_PATH);
        Preconditions.requireFileExists(userHome);
        return userHome;
    }

    public static List<File> listFilesOfDirectory(File dir) {
        Objects.requireNonNull(dir);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + " must be directory!");
        }

        var files = dir.listFiles();
        Objects.requireNonNull(files);

        var homeDirectoryFiles = new LinkedList<File>();
        Collections.addAll(homeDirectoryFiles, files);

        return homeDirectoryFiles;
    }

    public static File getFileRelativeTo(File parentFile, String relativePath) {
        return new File(parentFile, relativePath);
    }
}
