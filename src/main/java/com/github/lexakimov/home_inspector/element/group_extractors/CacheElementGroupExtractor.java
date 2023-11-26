package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;

public class CacheElementGroupExtractor extends ElementGroupExtractor {

    public CacheElementGroupExtractor() {
        super(ElementGroupType.CACHE);
    }

    @Override
    protected boolean precondition(File file) {
        return file.getName().equals(".cache");
    }
}
