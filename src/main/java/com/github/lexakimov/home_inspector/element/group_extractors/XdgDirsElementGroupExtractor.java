package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;

public class XdgDirsElementGroupExtractor extends ElementGroupExtractor {

    public XdgDirsElementGroupExtractor() {
        super(ElementGroupType.XDG_DIRS);
    }

    @Override
    protected boolean precondition(File file) {
        return false;
    }
}
