package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.Element;
import com.github.lexakimov.home_inspector.element.ElementGroup;
import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ElementGroupExtractor {

    final ElementGroupType elementGroupType;

    public void tryExtractElementsToGroup(List<File> sourceFiles, List<ElementGroup> resultList) {
        var group = new ElementGroup(elementGroupType);
        var groupElements = group.getElements();
        var indexesToDelete = new ArrayList<Integer>();

        for (int i = 0; i < sourceFiles.size(); i++) {
            var file = sourceFiles.get(i);
            if (precondition(file)) {
                var element = new Element(file);
                element.setDescription("это кеш приложений");
                groupElements.add(element);
                indexesToDelete.add(i);
            }
        }

        for (int j = indexesToDelete.size() - 1; j >= 0; j--) {
            var indexToRemove = indexesToDelete.get(j);
            sourceFiles.remove((int) indexToRemove);
        }

        if (!groupElements.isEmpty()) {
            resultList.add(group);
        }
    }

    protected abstract boolean precondition(File file);

}
