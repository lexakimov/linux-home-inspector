package com.github.lexakimov.home_inspector.element.group_extractors;

import static com.github.lexakimov.home_inspector.util.FileUtil.HOME_DIRECTORY_PATH;
import static com.github.lexakimov.home_inspector.util.FileUtil.getFileRelativeTo;
import static com.github.lexakimov.home_inspector.util.FileUtil.userHome;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import lombok.SneakyThrows;

public class XdgDirsElementGroupExtractor extends ElementGroupExtractor {

    public XdgDirsElementGroupExtractor() {
        super(ElementGroupType.XDG_DIRS);
    }

    @Override
    @SneakyThrows
    protected boolean precondition(File file) {
        if (file.isFile()) {
            return false;
        }

        var userDirsConfig = getFileRelativeTo(userHome(), "/.config/user-dirs.dirs");
        if (!userDirsConfig.exists()) {
            return false;
        }

        var userDirsProperties = new Properties();
        try (var reader = new FileReader(userDirsConfig)) {
            userDirsProperties.load(reader);
        }

        for (Object value : userDirsProperties.values()) {
            String path = (String) value;
            path = path.replace("\"", "");
            path = path.replace("$HOME", HOME_DIRECTORY_PATH);
            path = path.replace("~", HOME_DIRECTORY_PATH);
            if (path.equals(file.getAbsolutePath())) {
                return true;
            }
        }

        return false;
    }
}
