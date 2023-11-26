package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;

public class LocalElementGroupExtractor extends ElementGroupExtractor {

    public LocalElementGroupExtractor() {
        super(ElementGroupType.XDG_DIRS);
    }

    @Override
    protected boolean precondition(File file) {
        return file.getName().equals(".local");
    }
}
