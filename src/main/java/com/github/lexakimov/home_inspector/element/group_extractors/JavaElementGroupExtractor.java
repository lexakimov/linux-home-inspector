package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;
import java.util.Set;

public class JavaElementGroupExtractor extends ElementGroupExtractor {

    public JavaElementGroupExtractor() {
        super(ElementGroupType.JAVA);
    }

    @Override
    protected boolean precondition(File file) {
        return Set.of(".gradle", ".java", ".m2").contains(file.getName());
    }
}
