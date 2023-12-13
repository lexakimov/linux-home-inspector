package com.github.lexakimov.home_inspector.util;

import java.io.File;
import java.io.FileNotFoundException;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Preconditions {

    @SneakyThrows
    public static void requireFileExists(File file) {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException(file != null ? file.getName() : null);
        }
    }

}
