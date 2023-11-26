package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;

public class UnknownElementGroupExtractor extends ElementGroupExtractor {

    public UnknownElementGroupExtractor() {
        super(ElementGroupType.UNKNOWN);
    }

    @Override
    protected boolean precondition(File file) {
        return true;
    }
}
