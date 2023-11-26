package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;

public class ConfigsElementGroupExtractor extends ElementGroupExtractor {

    public ConfigsElementGroupExtractor() {
        super(ElementGroupType.CONFIGS);
    }

    @Override
    protected boolean precondition(File file) {
        return file.getName().equals(".config");
    }
}
